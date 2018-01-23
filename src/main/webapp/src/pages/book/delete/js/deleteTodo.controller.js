$scope.deletePeople = function (id){
  //here comes your delete code.
  //pass id from the ng-click function.
  //based on that id find the record in the $scope.people array and remove that from the array.
  //you can use javascript splice function to remove it.

  var toDelete;
  $scope.people.forEach(function(p, index){
    if(p.id == id){
       userToDelete = index;
    }
  });

  $scope.people.splice(userToDelete, 1);
 }