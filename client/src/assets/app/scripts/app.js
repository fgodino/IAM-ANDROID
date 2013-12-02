'use strict';

var assetsApp = angular.module('assetsApp', ['angular-gestures', 'ngResource', 'ngCookies'])
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
      when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      }).
      when('/register', {
        templateUrl: 'views/register.html',
        controller: 'registerCtrl'
      }).
      otherwise({
        redirectTo: '/'
      });
  }]);

assetsApp.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.defaults.useXDomain = true;
  delete $httpProvider.defaults.headers.common['X-Requested-With'];
  }
]);

assetsApp.run(function($rootScope, $location, $window, $cookieStore, $http){

  $http.defaults.headers.common['Authorization'] = 'Basic ' + $cookieStore.get('authdata');

  $rootScope.go = function ( path ) {
    $location.path( path );
  };

  $rootScope.back = function () {
    $window.history.back();
  };
});
