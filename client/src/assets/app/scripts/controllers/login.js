assetsApp.controller('LoginCtrl', function ($scope, $http, $location, Auth) {
  $scope.user= {};

  $scope.submit = function(){

    Auth.login($scope.user.username, $scope.user.password, function(err, res){
      if(err){
        return $location.path('/login');
      }
      $location.path('/');
    });
                
  }
});
