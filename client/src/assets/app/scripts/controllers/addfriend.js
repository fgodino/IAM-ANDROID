assetsApp.controller('AddfriendCtrl', function ($scope, $http, $location) {

  var selected = 0;
  var options = ["Username", "Telephone"];
  $scope.option = "Username";

  $scope.changeInput = function(input){
    selected = input;
    $scope.option = options[selected];
  }

  $scope.add = function(){

  }
});
