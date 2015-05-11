
'use strict';

angular.module('icampusApp')
    .controller('FormController', function ($scope, Form, User, ParseLinks) {
        $scope.forms = [];
        $scope.users = User.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Form.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.forms = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Form.update($scope.form,
                function () {
                    $scope.loadAll();
                    $('#saveFormModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Form.get({id: id}, function(result) {
                $scope.form = result;
                $('#saveFormModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Form.get({id: id}, function(result) {
                $scope.form = result;
                $('#deleteFormConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Form.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFormConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.form = {name: null, uriName: null, category: null, leftId: null, rightId: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
