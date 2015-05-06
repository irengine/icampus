'use strict';

angular.module('icampusApp')
    .controller('DeviceController', function ($scope, Device, User, ParseLinks) {
        $scope.devices = [];
        $scope.users = User.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Device.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.devices = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Device.update($scope.device,
                function () {
                    $scope.loadAll();
                    $('#saveDeviceModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Device.get({id: id}, function(result) {
                $scope.device = result;
                $('#saveDeviceModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Device.get({id: id}, function(result) {
                $scope.device = result;
                $('#deleteDeviceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Device.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDeviceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.device = {category: null, serialNumber: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
