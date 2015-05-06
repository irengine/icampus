'use strict';

angular.module('icampusApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('unit', {
                parent: 'entity',
                url: '/unit',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'icampusApp.unit.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/unit/units.html',
                        controller: 'UnitController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('unit');
                        return $translate.refresh();
                    }]
                }
            })
            .state('unitDetail', {
                parent: 'entity',
                url: '/unit/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'icampusApp.unit.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/unit/unit-detail.html',
                        controller: 'UnitDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('unit');
                        return $translate.refresh();
                    }]
                }
            });
    });
