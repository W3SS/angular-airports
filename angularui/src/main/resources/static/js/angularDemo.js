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
			.when('/search',
				{
					controller: 'airportSearchController',
					templateUrl: '../templates/airportsearch.html'
				})
			.when('/view/:airportId',
				{
					controller: 'airportDetailsController',
					templateUrl: '../templates/viewairport.html'
				})
			.otherwise(
				{ 
					redirectTo: '/search' 
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

	.controller('airportSearchController', function($scope, airportFactory) {
		$scope.init = function() {
			$scope.airportResults = airportFactory.airportResults;
		}
		
		$scope.init();
		
		$scope.getAirportResults = function () {
			airportFactory.getAirportsByQuery($scope.query)
				.then (
					function(result) { $scope.airportResults = result; },
					function(error) { }
				);
		};
	})

	.controller('airportDetailsController', function($scope, $routeParams, airportFactory, weatherFactory) {
		$scope.init = function() {
			$scope.selectedAirport = airportFactory.getAirportById($routeParams.airportId);
			/*
			$scope.report = airportFactory.report;
			weatherFactory.getReportByIcao($scope.selectedAirport.icaoCode);

			$scope.forecast = airportFactory.forecast;
			weatherFactory.getForecastByIcao($scope.selectedAirport.icaoCode);
			*/
		}
		
		$scope.init();

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
					function(result) { $scope.forecast = result; },
					function(error) { }
				);
		};
	});

// ======================================
//           Factories
// ======================================
app
	.factory('airportFactory', function($http, $q, $log, $rootScope) {
		var factory = {};
		
		var airportResults = {};
		var airport = {};
		
		var urlRootSearch = $rootScope.airportServiceEndpoint + "airports/search/";
		var urlRootSingle = $rootScope.airportServiceEndpoint + "airports/get/";
		
		factory.getAirportsByQuery = function(query) {
			var defer = $q.defer(); 
			
			$http.get(urlRootSearch + query)
				.success(function(response) {
					airportResults = response;
					defer.resolve(response);
				})
				.error(function(error) {
					defer.reject(error);
					$log.error("Error! " + error.message);
				});
			
			return defer.promise;
		};

		factory.getAirportById = function(id) {
			var defer = $q.defer();

			$http.get(urlRootSingle + id)
				.success(function(response) {
					airport = response;
					defer.resolve(response);
				})
				.error(function(error) {
					defer.reject(error);
					$log.error("Error! " + error.message);
				});

			return defer.promise;
		}
		
		return factory;
	})

	.factory('weatherFactory', function($http, $q, $log, $rootScope) {
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
				.error(function(error) {
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
				.error(function(error) {
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