angular.module('storeApp.Services')
    .service('ApplicationService', function ($resource, $location) {
        var service = this;
        var appResource = $resource('api/app/:action', {},
            {
                popular: {
                    method: 'GET',
                    isArray: true,
                    params: {'action': 'popular'}
                }
            }
        );

        var domainUrl = $location.protocol() + "://" + $location.host() + ":" + $location.port() + '/';

        service.getPopular = function (callback) {
            appResource.popular().$promise.then(function (apps) {
                $.each(apps, function (i, app) {
                    app.image128 = domainUrl + app.image128;
                    app.image512 = domainUrl + app.image512;
                });
                callback(apps);
            });
        }
    });