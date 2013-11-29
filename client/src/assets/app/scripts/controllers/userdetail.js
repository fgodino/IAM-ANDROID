'use strict';

assetsApp.controller('UserDetailCtrl', function ($scope, $routeParams, $http) {
  $scope.user = {};

  $http({method: 'GET', url: 'http://localhost:6001/friends/' + $routeParams.userId}).
    success(function(data, status) {
      $scope.user = data;
    }).
    error(function(data, status) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
    });
});