var mainModule = angular.module('mainModule', ['ngRoute']);


mainModule.config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/', {
            templateUrl: 'static/partials/home.html',
            controller: 'HomeController',
            controllerAs: 'homeCtrl'
        })
        .when('/download', {
            templateUrl: 'static/partials/download-app.html',
            controller: 'DownloadController',
            controllerAs: 'downloadCtrl'
        })
        .otherwise({redirectTo: '/'});

}]);
mainModule.controller('HomeController', function ($scope) {

    var homeCtrl = this;

    homeCtrl.checkIndex = 200;
    $scope.scopeIndex = 'scooope';
});

mainModule.controller('DownloadController', function ($scope) {
    var downloadCtrl = this;
});




