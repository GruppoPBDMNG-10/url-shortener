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
        }
    ]
)

/**
 * Service to get statistics
 */
.service('Statistics',
    ['$http',
        function ($http) {
            
        }
    ]
);