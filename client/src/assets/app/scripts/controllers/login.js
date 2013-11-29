assetsApp.controller('LoginCtrl', function ($scope, $http, $location, Auth) {
  $scope.user= {};

  $scope.submit = function(){

    Auth.setCredentials($scope.user.username, $scope.user.password);

    $http.post('http://localhost:6001/login')
      .success(function(data){
        $location.path('/');
      })
      .error(function(){
        alert("Error");
        console.log("Error");
      });
  }
});