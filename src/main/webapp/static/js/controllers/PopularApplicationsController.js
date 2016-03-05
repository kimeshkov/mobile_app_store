angular.module('storeApp.Controllers')
    .controller('PopularAppsCtrl', function ($scope, ApplicationService) {
        ApplicationService.getPopular(function (apps) {
            $scope.popularApps = apps;
        });
    })
    .directive('popularApps', function () {
        return {
            templateUrl: 'static/partials/popular-apps.html',
            restrict: 'E',
            controller: 'PopularAppsCtrl'
        }
    });