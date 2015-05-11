'use strict';

angular.module('icampusApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aside', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
                views: {
                    'aside@':{
                        templateUrl: 'scripts/app/aside/aside.html',
                        controller: 'AsideController'
        }


                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('aside');
                        return $translate.refresh();
                    }]
                }
            });
    });
