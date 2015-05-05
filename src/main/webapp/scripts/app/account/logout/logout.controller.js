'use strict';

angular.module('icampusApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
