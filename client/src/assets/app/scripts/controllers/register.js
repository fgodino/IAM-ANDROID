assetsApp.controller('RegisterCtrl', function ($scope, $http, $location, Auth) {

  var _number, _code;

  var templates = {
    validate : 'validatetpl',
    check : 'checktpl',
    register : 'registertpl'
  }

  $scope.registerValues = {};
  $scope.template = templates.validate;

  $scope.validate = function(number){

    _number = number;

    $http.post(HOST + 'validate?number=' + number)
      .success(function(data){
        $scope.template = templates.check;
      })
      .error(function(){
        alert("Error");
        console.log("Error");
      });
  }

  $scope.check = function(code){

    _code = code;

    $http.post(HOST + 'check?number=' + _number + '&code=' + code)
      .success(function(data){
        $scope.template = templates.register;
      })
      .error(function(){
        alert("Error");
        console.log("Error");
      });
  }

  $scope.register = function(){

    $http.post(HOST + 'registry?number=' + _number + '&code=' + _code, $scope.registerValues)
      .success(function(data){
        $location.path('/login');
      })
      .error(function(){
        alert("Error");
        console.log("Error");
      });
  }
});
