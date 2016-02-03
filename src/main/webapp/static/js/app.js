angular.module('mainModule', ['ngRoute'])
    .config(['$routeProvider'], function ($routeProvider) {

        $routeProvider.when('/', {
            templateUrl: 'partials/home.html',
            controller: DowloadController
        });

        $routeProvider.when('/dowload/:appId', {
            templateUrl: 'partials/download-app.html',
            controller: DowloadController
        });

        $routeProvider.otherwise({
            templateUrl: 'partials/home.html',
            controller: HomeController
        });

    });

function HomeController($scope, $routeParams, $location, NewsService) {

    var homeCtrl = this;

    homeCtrl.checkIndex = 200;

}

function DowloadController($scope, $routeParams, $location, NewsService) {

    var downloadCtrl = this;

}



