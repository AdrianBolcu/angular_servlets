(function () {

    'use strict';

    angular.module("BookApp")
        .service("BookService", BookService);

    BookService.$inject = ['$http'];

    function BookService($http) {
        return {
            list: function () {
                return $http.get('books');
            },

            get: function (id) {
                var requestConfig = {
                    params: {id: id}
                };
                return $http.get('books', requestConfig);
            },

            post: function (data) {
                return $http.post('books', data, null);
            },

            put: function (data) {
                return $http.put('books', data, null);
            },

            delete: function(id) {
                   var requestConfig = {
                       params: {id: id}
                   };
                   return $http.delete('books' , requestConfig);
            }

        };
    }

})();