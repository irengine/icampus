'use strict';

angular.module('icampusApp')
    .controller('UnitDetailController', function ($scope, $stateParams, Unit, User) {
        $scope.unit = {};
        $scope.load = function (id) {
            Unit.get({id: id}, function(result) {
              $scope.unit = result;
            });
        };
        $scope.load($stateParams.id);
    });
