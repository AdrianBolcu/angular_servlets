(function () {
    'use strict';

    angular.module('BookApp', ['ngRoute'])
        .config(Config);

    Config.$inject = ['$routeProvider', '$locationProvider'];

    function Config($routeProvider, $locationProvider) {
        $locationProvider.hashPrefix('');

        $routeProvider
            .when('/',
            {
                templateUrl: 'src/pages/book/list/list.html',
                controller: 'BookListCtrl'
            })
            .when('/bookDetail/:bookId',
                {
                    templateUrl: 'src/pages/book/details/detail.html',
                    controller: 'BookDetailCtrl'
                })
            .otherwise({redirectTo: '/'});
    }

})();
