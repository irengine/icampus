'use strict';

angular.module('icampusApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('table', {
                parent: 'entity',
                url: '/table',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'icampusApp.table.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/table/tables.html',
                        controller: 'TableController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('table');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tabeltDetail', {
                parent: 'entity',
                url: '/table/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'icampusApp.table.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/table/table-detail.html',
                        controller: 'TableDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('table');
                        return $translate.refresh();
                    }]
                }
            });
    });
