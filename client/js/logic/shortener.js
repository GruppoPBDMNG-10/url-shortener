angular.module('logic')
 
/**
 * Service to create short url.
 */
.service('Shortener',
    ['$http',
        function ($http) {
    	
    	//passare in post
            this.tinyUrl = function(data, callback) {
                var url = 'http://localhost:4567/tiny';
                $http.post(url, data).success(callback).error(callback);
            }
            
            this.customUrl = function(data, callback) {
                var url = 'http://localhost:4567/tiny';
                $http.post(url, data).success(callback).error(callback);
            }
            

	        this.statistics = function(data, callback) {
	            var url = 'http://localhost:4567/statistics?tiny='+ data;
	            $http.get(url).success(callback).error(callback);
	        }
	        
	        this.browserStatistics = function(tiny, browser, callback) {
	            var url = 'http://localhost:4567/statistics?tiny='+ tiny+'&userAgent='+browser;
	            $http.get(url).success(callback).error(callback);
	        }
        }
    ]
);