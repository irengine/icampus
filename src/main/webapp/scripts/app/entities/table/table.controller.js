'use strict';

angular.module('icampusApp')
    .controller('TableController', function ($scope, Table, User, ParseLinks) {
        $scope.tables = [];
        $scope.users = User.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Table.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tables = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Table.update($scope.table,
                function () {
                    $scope.loadAll();
                    $('#saveTableModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Table.get({id: id}, function(result) {
                $scope.table = result;
                $('#saveTableModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Table.get({id: id}, function(result) {
                $scope.table = result;
                $('#deleteTableConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Table.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTableConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.table = {name: null, uriName: null, category: null, leftId: null, rightId: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
