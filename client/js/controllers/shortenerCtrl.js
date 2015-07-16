angular.module('shortenerCtrl', [])

.controller('shortenerCtrl', ['$scope', '$http', 'Shortener',
  function($scope, $http, Shortener){
    
	/**
	 * Regex expression to check longUrl field.
	 */
    $scope.longUrlRegex = /^(http[s]?:\/\/(www\.)?|ftp:\/\/(www\.)?|(www\.)?){1}([0-9A-Za-z-\.@:%_\‌​+~#=]+)+((\.[a-zA-Z]{2,3})+)(\/(.)*)?(\?(.)*)?$/;
    
    /**
     * Regex expression to check customUrl field.
     */
    $scope.customUrlRegex = /^([0-9a-zA-Z-]{0,30})$/;
    
    /**
     * Object urlDetails.
     */
    $scope.urlDetails = {
      'url' : '',
      'custom' : '',
      'urlTiny' : '',
      'thumb' : ''
    }
    
    /**
     * checkUrl function add http protocol if it is omitted.
     */
    var checkUrl = function(url){
    	newUrl = url;
    	if(url.substr(0, 8) != 'https://' && url.substr(0, 7) != 'http://') {
    		newUrl = 'http://' + url;
    	}
    	return newUrl;
    }
    
    /**
     * Function to execute after http request.
     */
    var callback = function(response){
    	if(response){
    		if(response.result.returnCode = '0'){
    			$scope.urlDetails.url = response.url;
    			$scope.urlDetails.custom = response.custom;
    			$scope.urlDetails.urlTiny = response.urlTiny;
    			$scope.urlDetails.thumb = 'https://api.thumbalizr.com/?url='+ $scope.urlDetails.url +'&width=250';
    		} else if(response.result.returnCode = '-2') {
    			alert('Custom non disponibile!');
    		} else {
    			alert('Contenuto non valito');
    		}
    	} else {
    		$scope.urlDetails.thumb = 'https://api.thumbalizr.com/?url='+ $scope.urlDetails.url +'&width=250';
			alert('404 - Not found');
    	}
    }
    
    
    /**
     * Calls the service "Shortener" to make an HTTP request to create tiny.
     */
    $scope.makeTinyUrl = function(){
    	$scope.urlDetails.url = checkUrl($scope.urlDetails.url);
      if ($scope.urlDetails.custom == '') {
        Shortener.tinyUrl($scope.urlDetails, callback);
      } else {
        Shortener.tinyUrl($scope.urlDetails, callback);
      }
    }
	
}]);