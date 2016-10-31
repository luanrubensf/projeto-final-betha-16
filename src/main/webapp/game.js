(function () {

    'use strict';

    function gameController() {

        function _hideForm() {
            $('#divForm').hide();
        }

        function _showForm() {
            $('#divForm').show();
        }

        return {
            hideForm: _hideForm,
            showForm: _showForm,
        };
    }

    $(function () {
        window.ctrl = gameController();
        $('#btnSalvar').click(function () {
            ctrl.save();
        });
        $('#btnAdicionar').click(function () {
            ctrl.showForm();
        });
        $('#btnCancelar').click(function () {
            ctrl.hideForm();
        });
    });

})();