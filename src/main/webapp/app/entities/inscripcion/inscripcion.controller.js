(function() {
    'use strict';

    angular
        .module('medusaTattooApp')
        .controller('InscripcionController', InscripcionController);

    InscripcionController.$inject = ['DataUtils', 'Inscripcion', 'ParseLinks', 'AlertService', 'paginationConstants','Rayaton','HasRayaton','Principal','LastRayaton'];

    function InscripcionController(DataUtils, Inscripcion, ParseLinks, AlertService, paginationConstants,Rayaton,HasRayaton,Principal,LastRayaton) {

        var vm = this;

        vm.inscripcions = [];
        vm.rayatons=Rayaton.query();
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = false;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.hasRayatons = HasRayaton.get();
        vm.account = {};
        vm.rayaton = LastRayaton.get();
        vm.filtrar="SIN_ELEGIR";
        getAccount();
        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
            });
        }
        loadAll();

        vm.setSede=function (inscripcion) {
            inscripcion.sede=vm.account.sede;
            Inscripcion.update(inscripcion, onSaveSuccess, onSaveError);
        };

        vm.cleanSede=function (inscripcion) {
            inscripcion.sede=null;
            Inscripcion.update(inscripcion, onSaveSuccess, onSaveError);
        };

        vm.subscribe=function (inscripcion) {
            inscripcion.sede=vm.account.sede;
            inscripcion.estado="INSCRITO";
            Inscripcion.update(inscripcion, onSaveSuccess, onSaveError);
        };
        vm.unSubscribe=function (inscripcion) {
            inscripcion.sede=null;
            inscripcion.estado="PRE_INSCRITO";
            Inscripcion.update(inscripcion, onSaveSuccess, onSaveError);
        };

        function onSaveSuccess (result) {
            //$scope.$emit('medusaTattooApp:inscripcionUpdate', result);
            //$uibModalInstance.close(result);
            console.log("trabajo cambiado");
            vm.isSaving = false;
        }

        function onSaveError () {
            console.log("No se pudo cambiar el trabajo");
            vm.isSaving = false;
        }

        function loadAll () {
            Inscripcion.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.inscripcions.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.inscripcions = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
