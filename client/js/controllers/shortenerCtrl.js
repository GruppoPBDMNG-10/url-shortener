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
      'tiny' : '',
      'thumb' : ''
    }
    
    /**
     * Function to execute after http request.
     */
    var callback = function(response){
      alert('risposta');
    }
    
    
    /**
     * Calls the service "Shortener" to make an HTTP request to create tiny.
     */
    $scope.makeTinyUrl = function(){
      if ($scope.urlDetails.custom == '') {
        Shortener.tinyUrl($scope.urlDetails, callback);
      } else {
        Shortener.tinyUrl($scope.urlDetails, callback);
      }
    }
	
}]);