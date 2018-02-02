(function () {

    'use strict';

    angular.module("BookApp")
        .controller("BookListCtrl", BookListCtrl);


    BookListCtrl.$inject = ['$scope', '$routeParams', 'BookService'];

    function BookListCtrl($scope, routeParams,  BookService) {
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

           $scope.deleteBook = function(index){
                    var id = $scope.books[index].id;
                    BookService.delete(id)
                    .then(function(res){
                        BookService.list()
                            .then(function (res) {
                                $scope.books = res.data;
                            }, function () {
                                $scope.books = [];
                            });
                    });
                };



    }

})();