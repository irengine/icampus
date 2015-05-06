'use strict';

angular.module('icampusApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('device', {
                parent: 'entity',
                url: '/device',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'icampusApp.device.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/device/devices.html',
                        controller: 'DeviceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('device');
                        return $translate.refresh();
                    }]
                }
            })
            .state('deviceDetail', {
                parent: 'entity',
                url: '/device/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'icampusApp.device.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/device/device-detail.html',
                        controller: 'DeviceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('device');
                        return $translate.refresh();
                    }]
                }
            });
    });
