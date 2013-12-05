'use strict';

assetsApp.controller('MainCtrl', function ($scope, geolocation, $http, $location) {
  /*geolocation.getCurrentPosition(function (position) {
    alert('Latitude: '              + position.coords.latitude          + '\n' +
          'Longitude: '             + position.coords.longitude         + '\n' +
          'Altitude: '              + position.coords.altitude          + '\n' +
          'Accuracy: '              + position.coords.accuracy          + '\n' +
          'Altitude Accuracy: '     + position.coords.altitudeAccuracy  + '\n' +
          'Heading: '               + position.coords.heading           + '\n' +
          'Speed: '                 + position.coords.speed             + '\n' +
          'Timestamp: '             + position.timestamp                + '\n');
  });*/
  $scope.search = false;

  $scope.add = function(){
    $http({method: 'POST', url: HOST + 'friends', data : {id : $scope.id}}).
    success(function(data, status) {
      $scope.search = false;
    }).
    error(function(data, status) {
      alert("MAL")
    });
  };

  $scope.addform = function(){
    $scope.addStack(function(){
      alert("me ejecutan");
      $scope.search = false;
      $scope.id = "";
    });
    $scope.search = true;
  };
});


