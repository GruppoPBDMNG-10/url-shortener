angular.module('statisticsCtrl', ['chart.js'])

.controller('statisticsCtrl', ['$scope', 'Shortener', function($scope, Shortener){
	
    
//    /**
//     * Object urlDetails.
//     */
//    $scope.urlDetails = {
//      'url' : 'http://www.facebook.it',
//      'custom' : 'facebook',
//      'urlTiny' : 'http://localhost:5467/facebook',
//      'thumb' : 'https://api.thumbalizr.com/?url=www.facebook.it&width=250',
//      'QRCode' : 'http://www.qrstuff.com/images/default_qrcode.png'
//    }
//	
//	$scope.urlDetails.statistics = {
//		numClicks: 10,
//		clicks: [
//			{ ip: '192.168.11.100', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
//			{ ip: '192.168.11.200', timestamp: '20150706T18:37:45.100', userAgent: 'Firefox'},
//			{ ip: '192.168.11.145', timestamp: '20150706T18:37:45.100', userAgent: 'Firefox'},
//			{ ip: '192.168.11.101', timestamp: '2015-07-06T18:37:45.100+0200', userAgent: 'Chrome'},
//			{ ip: '192.168.11.150', timestamp: '2015-07-06T18:37:45.100+0200', userAgent: 'Firefox'},
//			{ ip: '192.168.11.189', timestamp: '20150706T18:37:45.100', userAgent: 'Firefox'},
//			{ ip: '192.168.11.178', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
//			{ ip: '192.168.11.200', timestamp: '20150706T18:37:45.100', userAgent: 'Firefox'},
//			{ ip: '192.168.11.110', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
//			{ ip: '192.168.11.200', timestamp: '20150706T18:37:45.100', userAgent: 'Opera'}
//		],
//		lastClick: { ip: '192.168.11.100', timestamp: '20150706183745100', userAgent: 'Chrome'},
//		agentStat: {
//			agent: 'Chrome',
//			clicks: [
//				{ ip: '192.168.11.100', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
//				{ ip: '192.168.11.101', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
//				{ ip: '192.168.11.178', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
//				{ ip: '192.168.11.110', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'}
//			],
//			numClicks : 4 
//		},
//		ipStat: {
//			ip: '192.168.11.200',
//			clicks: [
//				{ ip: '192.168.11.100', timestamp: '20150706T18:37:45.100', userAgent: 'Chrome'},
//				{ ip: '192.168.11.200', timestamp: '20150706T18:37:45.100', userAgent: 'Firefox'},
//				{ ip: '192.168.11.200', timestamp: '20150706T18:37:45.100', userAgent: 'Opera'}
//			],
//			numClicks : 3 
//		},
//		ipAgentStat: {
//			ip : '192.168.11.200',
//			agent : 'Chrome',
//			clicks: [
//				{ ip: '192.168.11.100', timestamp: '20150706183745100', userAgent: 'Chrome'}
//			],
//			numClicks : 1 
//		}
//	}

		
//	var browserStat = [ {
//		value : 0,
//		color : "#F7464A",
//		highlight : "#FF5A5E",
//		label : "Chrome"
//	}, {
//		value : 0,
//		color : "#46BFBD",
//		highlight : "#5AD3D1",
//		label : "Firefox"
//	}, {
//		value : 0,
//		color : "#FDB45C",
//		highlight : "#FFC870",
//		label : "Explorer"
//	}, {
//		value : 0,
//		color : "#FDB0FC",
//		highlight : "#FDB0EC",
//		label : "Safari"
//	} ];
	
    
//    /**
//     * Function to execute after http request.
//     */
//    var browserCallback = function(browser, response){
//    	if(response){
//    		if(response.result.returnCode == '0'){
//    			browser = response.statistics.agentStat.numClicks;
//    		} else {
//    			alert('Contenuto non valito');
//    		}
//    	} else {
//    		alert('404 - Not found');
//    	}
//    }
//    
//    var setBrowserChart = function(data, tiny){
//		for (i in data){
//			Shortener.browserStatistics(tiny, data[i].label, browserCallback(data[i]));
//		}
//			
//		var ctx = document.getElementById("pie").getContext("2d");
//		new Chart(ctx).Pie(data);
//	};
	
	
    /**
     * remove domain from tiny url.
     */
    var checkUrl = function(url){
    	newUrl = url;
    	if(url.substr(0, 22) == 'http://localhost:4567/') {
    		newUrl = url.replace('http://localhost:4567/', '');
    	}
    	return newUrl;
    }
    
    /**
     * Function to execute after http request.
     */
    var callback = function(response){
    	if(response){
    		switch(response.result.returnCode) {
    	    case 0:
    			$scope.urlDetails = response;
    			break;
    	    case -3:
        		alert('Tiny not available!');
    			break;
    	    default:
        		alert('Generic Error!');
    		}
    	} else {
    		alert('404 - Not found!');
    	}
    }
    
    $scope.statistics = function(){
    	var tiny = checkUrl($scope.urlDetails.urlTiny);
    	Shortener.statistics(tiny, callback);
//    	setBrowserChart(browserStat, tiny);
    }  
    
}]);