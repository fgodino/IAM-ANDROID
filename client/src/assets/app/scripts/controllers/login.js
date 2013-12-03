assetsApp.controller('LoginCtrl', function ($scope, $http, $location, Auth) {
  $scope.user= {};

  $scope.submit = function(){

    Auth.setCredentials($scope.user.username, $scope.user.password);

    $http.post(HOST + 'login')
      .success(function(data){
        $location.path('/');
      })
      .error(function(){
        Auth.clearCredentials();
        alert("Error");
        console.log("Error");
      });
  }
});
