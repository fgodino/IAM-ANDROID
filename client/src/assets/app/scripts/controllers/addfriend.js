assetsApp.controller('AddfriendCtrl', function ($scope, $http, $location) {
  var self = this;

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

  var deleteAdd = function(){
      $scope.search = false;
      $scope.id = "";
    }

  $scope.addform = function(){
    $scope.undo(self, deleteAdd);
    $scope.search = true;
  };
});
