angular.module('storeApp.Services')
    .service('StorageService',
    function ($rootScope, $cookies) {
        var service = this;
        var tokenKey = 'authToken';
        var userKey = 'currentUser';

        service.getToken = function () {
            return $rootScope.token ? $rootScope.token : $cookies.get(tokenKey)
        };

        service.saveToken = function (token) {
            $rootScope.token = token;
            $cookies.put(tokenKey, token);

            return token;
        };

        service.deleteToken = function () {
            $rootScope.token = null;
            $cookies.remove(tokenKey);
        };


        service.getUser = function () {
            return $rootScope.user ? $rootScope.user : $cookies.getObject(userKey)
        };

        service.saveUser = function (user) {
            $rootScope.user = user;
            $cookies.putObject(userKey, user);

            return user;
        };

        service.deleteUser = function () {
            $rootScope.user = null;
            $cookies.remove(userKey);
        };

    });
