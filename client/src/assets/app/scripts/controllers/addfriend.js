assetsApp.controller('AddfriendCtrl', function ($scope, $http, $location) {

  $scope.search = true;

  $scope.add = function(){
  	$http({method: 'POST', url: HOST + 'friends', data : {id : $scope.id}}).
    success(function(data, status) {
    	alert("GUAY")
    }).
    error(function(data, status) {
      alert("MAL")
      $scope.search = false;
    });
  };

  $scope.addform = function(){
    alert("pasa algi");
    $scope.search = true;
  };
});
