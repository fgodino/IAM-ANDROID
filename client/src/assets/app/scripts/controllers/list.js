'use strict';

assetsApp.controller("listCtrl", ["$scope", "$http", function($scope, $http){
  $scope.users = [];


  $http({method: 'GET', url: HOST + 'friends'}).
    success(function(data, status) {
      $scope.users = data;
    }).
    error(function(data, status) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
    });
}]);
