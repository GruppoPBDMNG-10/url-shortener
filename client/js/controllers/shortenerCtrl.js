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
    		switch(response.result.returnCode) {
    	    case 0:
    			$scope.urlDetails.url = response.url;
    			$scope.urlDetails.custom = response.custom;
    			$scope.urlDetails.urlTiny = response.urlTiny;
    			$scope.urlDetails.QRCode = response.QRCode;
    			$scope.urlDetails.thumb = 'https://api.thumbalizr.com/?url='+ $scope.urlDetails.url +'&width=250';
    	        break;
    	    case -2:
    			alert('Custom not available!');
    	        break;
    	    case -4:
    			alert('Not valid custom!');
    	        break;
    	    default:
        		alert('Generic Error!');
    		}
    	} else {
    		alert('404 - Not found!');
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