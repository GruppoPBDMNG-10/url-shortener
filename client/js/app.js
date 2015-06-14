angular.module('home', ['ui.bootstrap']);
angular.module('shortener', ['ui.bootstrap']);

var module = angular.module('url-shortener', [
	'home',
	'shortener',
	'ngRoute'
	]);

module.config(
	function($routeProvider, $locationProvider) {
		$routeProvider.when('/home', {templateUrl: 'views/home.html', controller: 'homeCtrl'});
		$routeProvider.otherwise({redirectTo: '/home'});
		
		$locationProvider.html5Mode(true);
	}
);