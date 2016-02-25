angular.module('storeApp.Controllers')
    .controller('HomeCtrl', function($scope, $rootScope, ApplicationService) {
        var home = this;

        ApplicationService.getPopular(function(apps) {
            $scope.popularApps = apps;
        });

    });

