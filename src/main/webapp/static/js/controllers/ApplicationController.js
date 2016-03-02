angular.module('storeApp.Controllers')
    .controller('ApplicationCtrl', function ($scope, $location, $routeParams, ApplicationService) {
        var domainUrl = $location.protocol() + "://" + $location.host() + ":" + $location.port() + '/'

        ApplicationService.getByPackageName($routeParams.packageName, function (app) {
            $scope.app = app;
            $scope.downloadUrl = domainUrl + 'api/app/download/' + $scope.app.packageName;
        });

    });

