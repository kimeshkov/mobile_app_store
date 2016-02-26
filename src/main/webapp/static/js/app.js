var mainModule = angular.module('storeApp',
    [
        'ngRoute',
        'storeApp.Services',
        'storeApp.Controllers'
    ]);


mainModule.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {

    $routeProvider
        .when('/', {
            templateUrl: 'static/partials/home.html',
            controller: 'HomeCtrl',
            controllerAs: 'homeCtrl'
        })
        .when('/upload', {
            templateUrl: 'static/partials/upload-app.html',
            controller: 'UploadCtrl',
            controllerAs: 'uploadCtrl'
        })
        .when('/download', {
            templateUrl: 'static/partials/download-app.html',
            controller: 'DownloadCtrl',
            controllerAs: 'downloadCtrl'
        })
        .otherwise({redirectTo: '/'});

    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
     * as soon as there is an authenticated user */
    $httpProvider.interceptors.push(function ($q, $rootScope, StorageService) {
            return {
                'request': function (config) {
                    var isRestCall = config.url.indexOf('api') == 0;

                    if (isRestCall && StorageService.getToken()) {
                        config.headers['Authorization'] = 'Bearer ' + StorageService.getToken();

                    }
                    return config;
                }
            };
        }
    );

}]);

mainModule.run(function (StorageService, UserService) {
    if (StorageService.getToken() !== undefined) {
        if (StorageService.getUser() == undefined) {
            StorageService.saveUser(UserService.getCurrentUser())
        }
    }
});






