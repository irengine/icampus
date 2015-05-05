'use strict';

angular.module('icampusApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


