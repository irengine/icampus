'use strict';

angular.module('icampusApp')
    .controller('FormDetailController', function ($scope, $stateParams, Form, User) {
        $scope.form = {};
        $scope.load = function (id) {
            Form.get({id: id}, function(result) {
              $scope.form = result;
            });
        };
        $scope.load($stateParams.id);
    });
