angular.module('shortenerCtrl', []);
angular.module('statisticsCtrl', []);
angular.module('logic', []);
angular.module('chart.js', []);
angular.module('mapCtrl', []);

var module = angular.module('url-shortener', [
	'shortenerCtrl',
	'statisticsCtrl',
	'logic',
	'ngRoute',
	'chart.js',
	'mapCtrl'
	]);

module.config(
	function($routeProvider, $locationProvider) {
		$routeProvider.when('/home', {templateUrl: 'views/home.html', controller: 'shortenerCtrl'});
		$routeProvider.when('/statistics', {templateUrl: 'views/statistics.html', controller: 'statisticsCtrl'});
		$routeProvider.otherwise({redirectTo: '/home'});
		
		$locationProvider.html5Mode(true);
	}
);