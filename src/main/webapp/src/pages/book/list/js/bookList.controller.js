(function () {

    'use strict';

    angular.module("BookApp")
        .controller("BookListCtrl", BookListCtrl);

    BookListCtrl.$inject = ['$scope', 'BookService'];

    function BookListCtrl($scope, BookService) {
        BookService.list()
            .then(function (res) {
                $scope.books = res.data;
            }, function () {
                $scope.books = [];
            });

        $scope.data = {};

        $scope.addBook = function() {
            BookService.post($scope.data)
                .then(function (res) {
                    alert(res.data);

                    BookService.list()
                        .then(function (res) {
                            $scope.books = res.data;
                        }, function () {
                            $scope.books = [];
                        });

                }, function () {
                    alert(res.data);
                });
        }


        $scope.deleteBook = function() {
                    BookService.delete($scope.data)
                        .then(function (res) {
                            alert(res.data);

                            BookService.list()
                                .then(function (res) {
                                    $scope.books = res.data;
                                }, function () {
                                    $scope.books = [];
                                });

                        }, function () {
                            alert(res.data);
                        });
                }



    }

})();