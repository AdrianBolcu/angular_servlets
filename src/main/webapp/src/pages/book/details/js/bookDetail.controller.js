(function () {

    'use strict';

    angular.module("BookApp")
        .controller("BookDetailCtrl", BookDetailCtrl);

    BookDetailCtrl.$inject = ['$scope', '$routeParams', 'BookService'];

    function BookDetailCtrl($scope, $routeParams, BookService) {
        BookService.get($routeParams.bookId)
            .then(function (res) {
                $scope.book = res.data;
            }, function () {
                $scope.book = {};
            });
    }

})();