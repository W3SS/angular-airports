var app = angular.module('app', ['ngRoute']);

// ======================================
//           Config
// ======================================
app
	.config(function ($routeProvider) {
		$routeProvider
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
	.controller('airportSearchController', function($scope, airportFactory) {
		$scope.airportResults = [];
		
		$scope.init = function() {
			$scope.airportResults = airportFactory.airportResults;
		}

		$scope.getAirportResults = function () {
			airportFactory.getAirportsByQuery($scope.query)
				.then (
					function(result) { $scope.airportResults = result; },
					function(error) { }
				);
		};
		
		$scope.init();
	})

	.controller('airportDetailsController', function($scope, $routeParams, airportFactory, weatherFactory) {
		
		$scope.selectedAirport = {};
		$scope.report = {};
		$scope.forecast = {};
		
		$scope.getAirport = function () {
			return airportFactory.getAirportById($routeParams.airportId)
				.then(
					function(result) { 
						$scope.selectedAirport = result;
						return result.icaoCode;
					}
				);
		}, 
		
		getWeather = function (icaoCode) {
			weatherFactory.getReportByIcao(icaoCode)
				.then(
					function(result) { $scope.report = result; }
				);
				
			weatherFactory.getForecastByIcao(icaoCode)
				.then(
					function(result) { $scope.forecast = result; }
				);
		};
		
		$scope.init = function() {
			$scope.getAirport()
				.then(getWeather);
		}
		
		$scope.init();
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
		
		var urlRoot = $rootScope.weatherServiceEndpoint + "weather/";
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
	});