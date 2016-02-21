angular.module('storeApp.Controllers')
    .controller('MainCtrl', function ($scope, $rootScope, UserService) {
        var main = this;

        main.isLoggedIn = function () {
            return UserService.getCurrentUser() != undefined;
        };

        main.login = function () {
            UserService.login($scope.username, $scope.password);
            main.currentUser = UserService.getCurrentUser();
        };

        main.logout = function () {
            UserService.logout();
            main.currentUser = null;
        };
    });

