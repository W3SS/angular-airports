var app = angular.module('app', ['ngRoute']);

app.config(function ($routeProvider) {
	$routeProvider
		.when('/view1',
			{
				controller: 'controller',
				templateUrl: '../templates/view1.html'
			})
		.when('/view2',
			{
				controller: 'controller',
				templateUrl: '../templates/view2.html'
			})
		.otherwise({ redirectTo: '/view1' });
});

app.controller('controller', function($scope, capitalsFactory) {
	$scope.capitals = [];
	
	init();
	
	function init() {
		$scope.capitals = capitalsFactory.getCapitals();
	}
	
	$scope.addCapital = function() {
		capitalsFactory.addCapital($scope.newCapital.city, $scope.newCapital.country);
	};
});

app.factory('capitalsFactory', function() {
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