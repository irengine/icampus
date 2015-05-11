'use strict';

angular.module('icampusApp')
    .controller('TableDetailController', function ($scope, $stateParams, Table, User) {
        $scope.table = {};
        $scope.load = function (id) {
            Table.get({id: id}, function(result) {
              $scope.table = result;
            });
        };
        $scope.load($stateParams.id);
    });
