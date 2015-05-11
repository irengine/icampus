'use strict';

angular.module('icampusApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('form', {
                parent: 'entity',
                url: '/form',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'icampusApp.form.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/form/forms.html',
                        controller: 'FormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('form');
                        return $translate.refresh();
                    }]
                }
            })
            .state('formDetail', {
                parent: 'entity',
                url: '/form/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'icampusApp.form.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/form/form-detail.html',
                        controller: 'FormDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('form');
                        return $translate.refresh();
                    }]
                }
            });
    });
