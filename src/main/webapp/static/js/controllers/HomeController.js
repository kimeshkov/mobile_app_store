angular.module('storeApp.Controllers')
    .controller('HomeCtrl', function ($scope, $rootScope, ApplicationService) {
        var home = this;

        var selectedCategory;

        $scope.sortByValues = [
            {value: 'creationDate', name: 'Creation date'},
            {value: 'downloads', name: 'Downloads'}
        ];

        var pagination = {page: 0, size: 2};

        var resetPagination = function (categoryId) {
            ApplicationService.getCountByCategory(categoryId, function (count) {
                $scope.pages = new Array(Math.ceil(count / pagination.size));
            });
            pagination.page = 0;
        };

        var displayApps = function () {
            pagination.sortBy = $scope.selectedSortBy;
            ApplicationService.getByCategory(selectedCategory.id, pagination, function (apps) {
                $scope.displayedApps = apps;
            })
        };

        $scope.displayedApps = [];

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

        $scope.changeSortBy = function () {
            resetPagination(selectedCategory.id);
            displayApps();
        };

        $scope.isCurrentPage = function (page) {
            return pagination.page == page;
        };

        $scope.pageClick = function (page) {
            pagination.page = page;
            displayApps();
        };

        $scope.rateStars = [1, 2, 3, 4, 5];

        $scope.rateApp = function (appResource) {
            ApplicationService.rate(appResource, appResource.newRate, function (app) {
                appResource = app;
                appResource.newRate = $scope.rateStars[0];
            })
        }

    });

