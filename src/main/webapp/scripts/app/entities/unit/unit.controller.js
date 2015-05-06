'use strict';

angular.module('icampusApp')
    .controller('UnitController', function ($scope, Unit, User, ParseLinks) {
        $scope.units = [];
        $scope.users = User.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Unit.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.units = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Unit.update($scope.unit,
                function () {
                    $scope.loadAll();
                    $('#saveUnitModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Unit.get({id: id}, function(result) {
                $scope.unit = result;
                $('#saveUnitModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Unit.get({id: id}, function(result) {
                $scope.unit = result;
                $('#deleteUnitConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Unit.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUnitConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.unit = {name: null, uriName: null, category: null, leftId: null, rightId: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
