angular.module('storeApp.Services')
    .service('UserService',
    function ($rootScope, $resource, $cookieStore, StorageService) {
        var service = this;

        var userResource = $resource('api/users/:action', {},
            {
                authenticate: {
                    method: 'POST',
                    params: {'action': 'authenticate'},
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }
            }
        );

        service.login = function (username, password) {
            userResource.authenticate($.param({username: username, password: password}), function (tokenTransfer) {
                StorageService.saveToken(tokenTransfer.token);

                userResource.get(function (user) {
                    StorageService.saveUser(user);
                });
            });
        };

        service.logout = function () {

        };

        service.getCurrentUser = function () {
            var user = StorageService.getUser();

            if (!user && StorageService.getToken()) {
                user = userResource.get();
            }
            return user;
        }

    });
