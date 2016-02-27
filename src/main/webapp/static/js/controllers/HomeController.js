angular.module('storeApp.Controllers')
    .controller('HomeCtrl', function ($scope, $rootScope, ApplicationService) {
        var home = this;

        var selectedCategory;

        var pagination = {page: 0, size: 2};

        var resetPagination = function (categoryId) {
            ApplicationService.getCountByCategory(categoryId, function (count) {
                $scope.pages = new Array(Math.ceil(count/pagination.size));
            });
            pagination.page = 0;
        };

        var displayApps = function () {
            ApplicationService.getByCategory(selectedCategory.id, pagination, function (apps) {
                $scope.displayedApps = apps;
            })
        };

        $scope.displayedApps = [];

        ApplicationService.getPopular(function (apps) {
            $scope.popularApps = apps;
        });

        ApplicationService.getCategories(function (categories) {
            $scope.categories = categories;
            selectedCategory = categories[0];
            resetPagination(selectedCategory.id);
            displayApps();
        });

        $scope.isSelected = function (category) {
            return category == selectedCategory;
        };

        $scope.selectCategory = function (category) {
            selectedCategory = category;
            resetPagination(category.id);
            displayApps();
        };

        $scope.isCurrentPage = function (page) {
            return pagination.page == page;
        };

        $scope.pageClick = function (page) {
            pagination.page = page;
            displayApps();
        }

    });

