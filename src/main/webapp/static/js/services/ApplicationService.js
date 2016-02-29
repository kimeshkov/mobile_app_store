angular.module('storeApp.Services')
    .service('ApplicationService', function ($resource, $location) {
        var service = this;
        var AppResource = $resource('api/app/:action/:packageName/:categoryId/:page/:size/:sortBy', {},
            {
                popular: {
                    method: 'GET',
                    isArray: true,
                    params: {'action': 'popular'}
                },

                upload: {
                    method: 'POST',
                    params: {'action': 'upload'},
                    headers: {'Content-Type': undefined},
                    transformRequest: []
                },

                categories: {
                    method: 'GET',
                    isArray: true,
                    params: {'action': 'categories'}
                },

                count: {
                    method: 'GET',
                    isArray: true,
                    params: {
                        'action': 'categories',
                        'categoryId': '@id'
                    }
                },

                updateRate: {
                    method: 'PUT',
                    params: {
                        'action': 'updateRate'
                    }
                }
            }
        );

        var domainUrl = $location.protocol() + "://" + $location.host() + ":" + $location.port() + '/';

        service.getPopular = function (callback) {
            AppResource.popular().$promise.then(function (apps) {
                $.each(apps, function (i, app) {
                    app.image128 = domainUrl + app.image128;
                    app.image512 = domainUrl + app.image512;
                });
                callback(apps);
            });
        };

        service.getCategories = function (callback) {
            AppResource.categories().$promise.then(function (categories) {
                callback(categories);
            });
        };

        service.getCountByCategory = function (categoryId, callback) {
            AppResource.get(
                {
                    action: 'category',
                    categoryId: categoryId
                },

                function (countResponse) {
                    callback(countResponse.count);
                })
        };

        service.getByCategory = function (categoryId, pagination, callback) {
            AppResource.query(
                {
                    action: 'category',
                    categoryId: categoryId,
                    page: pagination.page,
                    size: pagination.size,
                    sortBy: pagination.sortBy ? pagination.sortBy : 'none'
                },

                function (apps) {
                    $.each(apps, function (i, app) {
                        app.image128 = domainUrl + app.image128;
                        app.image512 = domainUrl + app.image512;
                    });
                    callback(apps);
                })
        };

        service.upload = function (name, description, categoryId, file) {
            var data = new FormData();
            data.append('name', name);
            data.append('description', description);
            data.append('categoryId', categoryId);
            data.append('file', file);

            AppResource.upload({}, data, function (value, responseHeaders) {
                console.log(value);
                console.log(responseHeaders);
            });
        };

        service.rate = function (resource, value, callback) {
            resource.rate = value;
            var packageName = resource.packageName;
            resource.$updateRate({packageName: packageName}, function (app) {
                app.image128 = domainUrl + app.image128;
                app.image512 = domainUrl + app.image512;

                callback(app);
            })
        }
    });