assetsApp.controller('AddfriendCtrl', function ($scope, $http, $location) {

  var selected = 0;
  var options = ["Username", "Telephone"];
  $scope.option = "Username";

  $scope.changeInput = function(input){
    selected = input;
    $scope.option = options[selected];
  }

  $scope.add = function(){
  	var option = (selected === 0) ? 'username' : 'number';  
  	$http({method: 'POST', url: HOST + 'friends', data : {id : $scope.id}, params : {option : option}}).
    success(function(data, status) {
    	alert("GUAY")
    }).
    error(function(data, status) {
      alert("MAL")
    });
  }
});
