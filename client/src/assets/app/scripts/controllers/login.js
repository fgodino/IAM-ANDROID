assetsApp.controller('LoginCtrl', function ($scope, $http, $location, Auth) {
  $scope.user= {};

  $scope.submit = function(){

    $http.post('http://localhost:6001/login')
      .success(function(data){
        $location.path('/');
        Auth.setCredentials($scope.user.username, $scope.user.password);
      })
      .error(function(){
        alert("Error");
        console.log("Error");
      });
  }
});
