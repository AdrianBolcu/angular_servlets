(function () {

    'use strict';

    angular.module("BookApp")
        .controller("BookDeleteCtrl", BookDeleteCtrl);

    BookDeleteCtrl.$inject = ['$scope', '$routeParams', 'BookService'];

    function BookDeleteCtrl($scope, $routeParams, CompanyService) {

             var id = $routeParams.bookId;
             BookService.delete(id)
                 .then(function (res) {
                     $scope.books = res.data;
                 }, function () {
                     $scope.books = [];
                 });
         }

})();