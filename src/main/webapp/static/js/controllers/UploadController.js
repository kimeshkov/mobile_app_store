angular.module('storeApp.Controllers')
    .directive('appUpload', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.appUpload);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    })
    .controller('UploadCtrl', function ($scope, $rootScope, ApplicationService) {
        var uploadCtrl = this;

        ApplicationService.getCategories(function (categories) {
            $scope.categories = categories;
            $scope.categoryId = categories[0].id;
        });

        uploadCtrl.doUpload = function () {
            var name = $scope.name;
            var description = $scope.description;
            var categoryId = $scope.categoryId;
            var file = $scope.appFile;

            ApplicationService.upload(name, description, categoryId, file);

            $scope.uploadSuccess = true;
        };
    });

