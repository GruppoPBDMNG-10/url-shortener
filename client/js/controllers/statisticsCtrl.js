angular.module('statisticsCtrl', ['chartjs'])

.controller('statisticsCtrl', ['$scope', function($scope){
	
	$scope.statistics = {
		numClicks: 10,
		clicks: [
			{ ip: '192.168.11.100', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
			{ ip: '192.168.11.200', timestamp: '20150706T18:37:45.100', userAgent: 'Firefox'},
			{ ip: '192.168.11.145', timestamp: '20150706T18:37:45.100', userAgent: 'Firefox'},
			{ ip: '192.168.11.101', timestamp: '2015-07-06T18:37:45.100+0200', userAgent: 'Chrome'},
			{ ip: '192.168.11.150', timestamp: '2015-07-06T18:37:45.100+0200', userAgent: 'Firefox'},
			{ ip: '192.168.11.189', timestamp: '20150706T18:37:45.100', userAgent: 'Firefox'},
			{ ip: '192.168.11.178', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
			{ ip: '192.168.11.200', timestamp: '20150706T18:37:45.100', userAgent: 'Firefox'},
			{ ip: '192.168.11.110', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
			{ ip: '192.168.11.200', timestamp: '20150706T18:37:45.100', userAgent: 'Opera'}
		],
		lastClick: { ip: '192.168.11.100', timestamp: '20150706183745100', userAgent: 'Chrome'},
		agentStat: {
			agent: 'Chrome',
			clicks: [
				{ ip: '192.168.11.100', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
				{ ip: '192.168.11.101', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
				{ ip: '192.168.11.178', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
				{ ip: '192.168.11.110', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'}
			],
			numClicks : 4 
		},
		ipStat: {
			ip: '192.168.11.200',
			clicks: [
				{ ip: '192.168.11.100', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
				{ ip: '192.168.11.200', timestamp: '20150706T18:37:45.100', userAgent: 'Firefox'},
				{ ip: '192.168.11.200', timestamp: '20150706T18:37:45.100', userAgent: 'Opera'}
			],
			numClicks : 3 
		},
		ipAgentStat: {
			ip : '192.168.11.200',
			agent : 'Chrome',
			clicks: [
				{ ip: '192.168.11.100', timestamp: '20150706183745100', userAgent: 'Chrome'}
			],
			numClicks : 1 
		}
	}

		
	var data = [ {
		value : 8,
		color : "#F7464A",
		highlight : "#FF5A5E",
		label : "Chrome"
	}, {
		value : 5,
		color : "#46BFBD",
		highlight : "#5AD3D1",
		label : "Firefox"
	}, {
		value : 1,
		color : "#FDB45C",
		highlight : "#FFC870",
		label : "Explorer"
	} ];
	
	
    var ctx = document.getElementById("pie").getContext("2d");
    new Chart(ctx).Pie(data);
	
}]);