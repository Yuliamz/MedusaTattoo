(function() {
    'use strict';

    angular
        .module('medusaTattooApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('password', {
            parent: 'account',
            url: '/password',
            data: {
                authorities: ['ROLE_SECRETARIA','ROLE_CLIENTE'],
                pageTitle: 'global.menu.account.password'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/password/password.html',
                    controller: 'PasswordController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('password');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
