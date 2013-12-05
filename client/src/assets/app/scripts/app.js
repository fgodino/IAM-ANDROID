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
        controller: 'RegisterCtrl'
      }).
      when('/addfriend', {
        templateUrl: 'views/addfriend.html',
        controller: 'AddfriendCtrl'
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

assetsApp.run(function($rootScope, $location, $window, $cookieStore, $http, $timeout, $templateCache, $route, Auth){

  $http.defaults.headers.common['Authorization'] = 'Basic ' + $cookieStore.get('authdata');

  $rootScope.scrollPos = [];

  // Cache all templates on App Start
  angular.forEach($route.routes, function(r) {
    if (r.templateUrl) {
      $http.get(r.templateUrl, {cache: $templateCache});
    }
  });

  // Generic solution to save/restore scroll position START
  $rootScope.scrollPos = {}; // scroll position of each view

  $window.addEventListener('scroll', function() {
    if ($rootScope.okSaveScroll) { // false between $routeChangeStart and $routeChangeSuccess
      $rootScope.scrollPos[escape($location.path())] = $window.scrollY;
    }
  });

  $rootScope.$on('$routeChangeStart', function(e, cur, prev) {
    if(prev !== undefined && prev.hasOwnProperty('scope') && prev.scope !== undefined) {
      prev.scope.$destroy();
    }
    $rootScope.okSaveScroll = false;
  });

  $rootScope.$on('$routeChangeSuccess', function() {
    // FIXME add fastclick as a dependency?
    //$timeout(function() {
    //  window.FastClick.attach(document.body);
    //  return;
    //},0);

    $timeout(function() { // wait for DOM, then restore scroll position
      $window.scrollTo(0, ($rootScope.scrollPos[escape($location.path())] !== undefined) ? $rootScope.scrollPos[escape($location.path())] : 0);
      $rootScope.okSaveScroll = true;
      return;
    },0);
  });
  // Generic solution for scroll position END

  $http.post(HOST + 'login')
      .success(function(data){
        $location.path('/');
      })
      .error(function(){
        Auth.clearCredentials();
        $location.path('/login');
      });

  $rootScope.$on('$viewContentLoaded', function(){
    console.log("LOADED");
    friesHello();
  });

  $rootScope.go = function ( path ) {
    $location.path( path );
  };

  $rootScope.back = function () {
    $window.history.back();
  };
});
