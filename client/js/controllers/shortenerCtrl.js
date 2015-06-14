angular.module('shortener')

.controller('shortenerCtrl',
            ['$rootScope', '$scope', '$http', '$location',
             function ($rootScope, $scope, $http, $location){
	
    //qui tutte le funzioni per la pagina home

    $scope.get = function(){
      //var url = "https://api.thumbalizr.com/?url="+ $scope.url +"&width=250"
      var json = '{url :\''+ $scope.url + '\'}';
      var url = 'http://localhost:4567/tiny';
      alert(url);
      $http.post(url, json)
        .success(
          function(response){
            $scope.page = response.urlTiny;
          })
        .error(
          function(response){
       		alert('error, url: ' + $scope.url);
          }
        )
      ;
    }
	
}]);