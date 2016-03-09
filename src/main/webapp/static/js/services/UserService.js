angular.module('storeApp.Services')
    .service('UserService',
    function ($rootScope, $resource, StorageService) {
        var service = this;

        var hasRole = function (role, user) {
            if (user == undefined) {
                return false;
            }
            return user.roles.indexOf(role) != -1;
        };

        var UserResource = $resource('api/users/:action', {},
            {
                authenticate: {
                    method: 'POST',
                    params: {'action': 'authenticate'},
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }
            }
        );

        service.login = function (username, password) {
            UserResource.authenticate($.param({username: username, password: password}), function (tokenTransfer) {
                StorageService.saveToken(tokenTransfer.token);

                UserResource.get(function (user) {
                    StorageService.saveUser(user);
                });
            });
        };

        service.deleteUserAndToken = function () {
            StorageService.deleteUser();
            StorageService.deleteToken();
        };

        service.getCurrentUser = function () {
            UserResource.get(function (user) {
                return StorageService.saveUser(user)
            });
        };

        service.isAdmin = function () {
            return hasRole("ROLE_ADMIN", StorageService.getUser());
        }

    });
