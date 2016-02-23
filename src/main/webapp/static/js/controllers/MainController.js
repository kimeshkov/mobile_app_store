angular.module('storeApp.Controllers')
    .controller('MainCtrl', function ($scope, $rootScope, StorageService, UserService) {
        var main = this;

        main.isLoggedIn = function () {
            return StorageService.getUser() != undefined;
        };

        main.isAdmin = function () {
            return UserService.isAdmin();
        };

        main.login = function () {
            UserService.login($scope.username, $scope.password);
            //main.currentUser = UserService.getCurrentUser();
        };

        main.logout = function () {
            UserService.logout();
            //main.currentUser = null;
        };
    });

