'use strict';

assetsApp.controller("listCtrl", ["$scope", "$http", function($scope, $http){
  $scope.users = [];
  var users = [];


  $http({method: 'GET', url: HOST + 'friends'}).
    success(function(data, status) {
      //$scope.users = data;
      for(var i=0; i < 20; i++){
      	users.push(data[0]);
      }
      $scope.users = users;
    }).
    error(function(data, status) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
    });
}]);
