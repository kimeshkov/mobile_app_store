angular.module('storeApp.Controllers')
    .controller('HomeCtrl', function ($scope, $rootScope, ApplicationService) {
        var home = this;

        var selectedCategory;

        $scope.displayedApps = [];

        ApplicationService.getPopular(function (apps) {
            $scope.popularApps = apps;
        });

        ApplicationService.getCategories(function (categories) {
            $scope.categories = categories;
            selectedCategory = categories[0];

            ApplicationService.getByCategory(selectedCategory.id, function (apps) {
                $scope.displayedApps = apps;
            })

        });

        $scope.isSelected = function (category) {
            return category == selectedCategory;
        };

        $scope.selectCategory = function (category) {
            selectedCategory = category;

            ApplicationService.getByCategory(selectedCategory.id, function (apps) {
                $scope.displayedApps = apps;
            })
        }

    });

