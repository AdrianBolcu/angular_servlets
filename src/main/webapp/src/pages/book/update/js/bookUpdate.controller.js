(function () {

    'use strict';

    angular.module("BookApp")
        .controller("BookUpdateCtrl", BookUpdateCtrl);

    BookUpdateCtrl.$inject = ['$scope', '$routeParams', 'BookService'];

    function BookUpdateCtrl($scope, $routeParams, BookService) {
        BookService.update($routeParams.bookId)
            .then(function (res) {
                $scope.book = res.data;
            }, function () {
                $scope.book = {};
            });
    }

})();