var app = angular.module('app', ['ngRoute']);

// ======================================
//           Config
// ======================================
app.config(function ($routeProvider) {
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
});

app.run(function($rootScope) {
	$rootScope.airportServiceEndpoint = 'http://localhost:8085/';
});

// ======================================
//           Controllers
// ======================================
app.controller('airportSearchController', function($scope, airportFactory) {
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
});

app.controller('airportDetailsController', function($scope, $routeParams, airportFactory, weatherFactory) {
	
	$scope.selectedAirport = {};
	$scope.report = {};
	$scope.forecast = {};
	$scope.charts = {};
	
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

	getCharts = function (icaoCode) {
		airportFactory.getChartsByIcao(icaoCode)
			.then(
				function(result) { $scope.charts = result; }
			);
	};
	
	// Chain promises to get weather and charts only after getting single airport
	$scope.init = function() {
		$scope.getAirport()
			.then(getWeather);
			//.then(getCharts);
	}
	
	$scope.init();
});

// ======================================
//           Factories
// ======================================
app.factory('airportFactory', function($http, $q, $log, $rootScope) {
	var factory = {};
	
	var airportResults = {};
	var airport = {};
	var airportCharts = {};
	
	var urlRootSearch = $rootScope.airportServiceEndpoint + "airports/search/";
	var urlRootSingle = $rootScope.airportServiceEndpoint + "airports/get/";
	var urlRootCharts = $rootScope.airportServiceEndpoint + "airports/charts/";

	factory.getAirportsByQuery = function(query) {
		var defer = $q.defer(); 
		
		$http.get(urlRootSearch + query)
			.success(function(response) {
				airportResults = response;
				defer.resolve(response);
			})
			.error(function(error) {
				defer.reject(error);
				if (error)
					$log.error("Error! " + error.message);
				else
					$log.error("Error! Unable to contact airport service. Ensure that it is currently running");
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
				if (error)
					$log.error("Error! " + error.message);
				else
					$log.error("Error! Unable to contact airport service. Ensure that it is currently running");
			});

		return defer.promise;
	};

	factory.getChartsByIcao = function(icaoCode) {
		var defer = $q.defer();

		$http.get(urlRootCharts + icaoCode)
			.success(function(response) {
				airportCharts = response;
				defer.resolve(response);
			})
			.error(function(error) {
				defer.reject(error);
				if (error)
					$log.error("Error! " + error.message);
				else
					$log.error("Error! Unable to contact airport service. Ensure that it is currently running");
			});

		return defer.promise;
	};
	
	return factory;
});

app.factory('weatherFactory', function($http, $q, $log, $rootScope) {
	var factory = {};
	
	var report = {};
	var forecast = {};
	
	var urlRoot = $rootScope.airportServiceEndpoint + "weather/";
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
				if (error)
					$log.error("Error! " + error.message);
				else
					$log.error("Error! Unable to contact airport service. Ensure that it is currently running");
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
				if (error)
					$log.error("Error! " + error.message);
				else
					$log.error("Error! Unable to contact airport service. Ensure that it is currently running");
			});
		
		return defer.promise;
	};

	/* Translates the forecast start/end times from DDHH to "DD/MM at HHMM UTC" (or MM/DD if a US Date) */
	function translateStartEndTime(forecast) {
		var now = Date.now;

		var isUSDate = false;

		var translatedStartDate = isUSDate ? 
			now.getUTCMonth() + "/" + forecast["Start-Time"].substring(0, 1) : 
			forecast["Start-Time"].substring(0, 1) + "/" + now.getUTCMonth();

		var translatedEndDate = isUSDate ? 
			now.getUTCMonth() + "/" + forecast["End-Time"].substring(0, 1) : 
			forecast["End-Time"].substring(0, 1) + "/" + now.getUTCMonth();

		var translatedStartTime = forecast["Start-Time"].substring(2, 3) + "00 UTC";

		var translatedEndTime = forecast["End-Time"].substring(2, 3) + "00 UTC";

		forecast["Start-Time"] = translatedStartDate + " at " + translatedStartTime;
		forecast["End-Time"] = translatedEndDate + " at " + translatedEndTime;

		return forecast;
	}

	/* Adds line breaks before each forecast time, as expected on a normal TAF */
	function beautifyRawTaf(forecast) {
		var raw = forecast["Raw-Report"];

		forecast["Raw-Report"] = raw.replace(/FM/,    "<br />FM")
									.replace(/BECMG/, "<br />BECMG")
									.replace(/TEMPO/, "<br />TEMPO");

		return forecast;
	}
	
	return factory;
});