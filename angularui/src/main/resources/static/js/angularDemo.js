var app = angular.module('app', ['ngRoute']);

// ======================================
//           Config
// ======================================
app
	.config(function ($routeProvider) {
		$routeProvider
			.when('/view1',
				{
					controller: 'capitalController',
					templateUrl: '../templates/view1.html'
				})
			.when('/view2',
				{
					controller: 'capitalController',
					templateUrl: '../templates/view2.html'
				})
			.when('/view3',
				{
					controller: 'airportController',
					templateUrl: '../templates/view3.html'
				})
			.otherwise(
				{ 
					redirectTo: '/view1' 
				}
			);
	})
	
	.run(function($rootScope) {
		$rootScope.airportServiceEndpoint = 'http://localhost:9000/';
		$rootScope.weatherServiceEndpoint = 'http://localhost:8085/';
	});

// ======================================
//           Controllers
// ======================================
app
	.controller('capitalController', function($scope, capitalFactory) {
		$scope.capitals = [];
		
		init();
		
		function init() {
			$scope.capitals = capitalFactory.getCapitals();
		}
		
		$scope.addCapital = function() {
			capitalFactory.addCapital($scope.newCapital.city, $scope.newCapital.country);
		};
	})

	.controller('airportController', function($scope, airportFactory, weatherFactory) {
		$scope.init = function() {
			$scope.airports = airportFactory.airports;
			$scope.report = weatherFactory.report;
			$scope.forecast = weatherFactory.forecast;			
		}
		
		$scope.init();
		
		$scope.getAirports = function () {
			airportFactory.getAirportsByQuery($scope.query)
				.then(
					function(result) { $scope.airports = result; },
					function(error) { }
				);
		};
		
		$scope.getReport = function () {
			airportFactory.getReportByIcao($scope.icaoCode)
				.then(
					function(result) { $scope.report = result; },
					function(error) { }
				);
		};
		
		$scope.getForecast = function () {
			airportFactory.getForecastByIcao($scope.icaoCode)
				.then(
					function(result) { $scope.report = result; },
					function(error) { }
				);
		};
	});

// ======================================
//           Factories
// ======================================
app
	.factory('airportFactory', ['$http', '$q', '$log', '$rootScope', function($http, $q, $log, $rootScope) {
		var factory = {};
		
		var airports = {};
		
		var urlRoot = $rootScope.airportServiceEndpoint + "airports/search/";
		
		factory.getAirportsByQuery = function(query) {
			var defer = $q.defer(); 
			
			$http.get(urlRoot + query)
				.success(function(response) {
					airports = response;
					defer.resolve(response);
				})
				.error (function(error) {
					defer.reject(error);
					$log.error("Error! " + error.message);
				});
			
			return defer.promise;
		};
		
		return factory;
	}])

	.factory('weatherFactory', function($http, $rootScope) {
		var factory = {};
		
		var report = {};
		var forecast = {};
		
		var urlRoot = $rootScope.weatherServiceEndpoint + "/weather/";
		var type = ["report", "forecast"];
		
		factory.getReportByIcao = function(icaoCode) {
			var defer = $q.defer(); 
			
			$http.get(urlRoot + type[0] + "/icao/" + icaoCode)
				.success(function(response) {
					report = response;
					defer.resolve(response);
				})
				.error (function(error) {
					defer.reject(error);
					$log.error("Error! " + error.message);
				});
			
			return defer.promise;
		};
		
		factory.getForecastByIcao = function(icaoCode) {
			var defer = $q.defer(); 
			
			$http.get(urlRoot + type[1] + "/icao/" + icaoCode)
				.success(function(response) {
					forecast = response;
					defer.resolve(response);
				})
				.error (function(error) {
					defer.reject(error);
					$log.error("Error! " + error.message);
				});
			
			return defer.promise;
		};
		
		return factory;
	})

	.factory('capitalFactory', function() {
		var factory = {};
		var capitals = [
			{city:'Berlin', country:'Germany'}, 
			{city:'Tokyo', country:'Japan'}, 
			{city:'Paris', country:'France'}, 
			{city:'Beijing', country:'China'}, 
			{city:'Canberra', country:'Australia'}
		];
		
		factory.getCapitals = function() {
			return capitals;
		};
		
		factory.addCapital = function(newCity, newCountry) {
			capitals.push({
				city: newCity,
				country: newCountry
			});
		};
		
		return factory;
	});