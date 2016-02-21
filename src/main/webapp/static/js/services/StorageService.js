angular.module('storeApp.Services')
    .service('StorageService',
    function ($rootScope, $cookieStore) {
        var service = this;
        var tokenKey = 'authToken';
        var userKey = 'currentUser';

        service.getToken = function () {
            return $rootScope.token ? $rootScope.token : $cookieStore.get(tokenKey)
        };

        service.saveToken = function (token) {
            $rootScope.token = token;
            $cookieStore.put(tokenKey, token);
        };


        service.getUser = function () {
            return $rootScope.user ? $rootScope.user : $cookieStore.get(userKey)
        };

        service.saveUser = function (user) {
            $rootScope.user = user;
            $cookieStore.put(userKey, user);
        };

    });
