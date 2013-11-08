'use strict';

var assetsApp = angular.module('assetsApp', ['angular-gestures'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      }).
      when('/users/:userId', {
        templateUrl: 'views/user-detail.html',
        controller: 'UserDetailCtrl'
      }).
      otherwise({
        redirectTo: '/'
      });
  }]);

assetsApp.run(function($rootScope, $location, $window){
  $rootScope.go = function ( path ) {
    $location.path( path );
  };

  $rootScope.back = function () {
    $window.history.back();
  };
});
