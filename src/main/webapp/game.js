(function () {

    'use strict';

    function gameController() {

        var categoriaId = 0;
        var templateTable;
        var notifyService = window.ctrlNotify;

        function resetForm() {
            $('#formCadastroGame').trigger("reset");
            $('input[type=hidden]').val('');
            $('#select-categorias').select2().val(null);
        }

        function _hideForm() {
            $('#divForm').hide();
        }

        function switchControllButtons(disabled) {
            $('#btnSalvar').prop('disabled', disabled);
            $('#btnCancelar').prop('disabled', disabled);
            $('#select-categorias').select2('data', null);
        }

        function _showForm() {
            $('#divForm').show();

            var id = $('input[name=id]').val();

            $('#select-categorias').select2({
                ajax: {
                    url: "api/categorias",
                    dataType: 'json',
                    placeholder: "Select a customer",
                    initSelection: function (element, callback) {
                    },
                    allowClear: true,
                    processResults: function (data, params) {
                        var result = [];

                        if (!params.term) {
                            params.term = '';
                        }

                        data.forEach(function (item) {
                            item.text = item.descricao;

                            if (item.text.toLowerCase().indexOf(params.term) >= 0) {
                                result.push(item);
                            }
                        });
                        return {
                            results: result
                        };
                    }
                }
            }).on("select2:select", function (e) {
                categoriaId = $(e.currentTarget).val();
            });
        }

        function successSave() {
            resetForm();
            switchControllButtons(false);
            _hideForm();
            loadData();
            notifyService.notifySuccess('Game salvo com sucesso');
        }

        function errorSave(data){
            notifyError(data);
            switchControllButtons(false);
        }

        function salvar() {
            switchControllButtons(true);
            var parametros = $('#formCadastroGame').serialize();

            if (categoriaId) {
                parametros += '&categoria=' + categoriaId;
            }

            save(parametros).then(successSave, errorSave);
        }

        function removeGame(id) {
            remove(id)
                .then(function () {
                    notifyService.notifySuccess('Game exluído com sucesso');
                    loadData();
                    resetForm();
                    _hideForm();
                }, notifyError);
        }

        function loadData() {
            templateTable = templateTable || $('table.table tbody').html();
            getList().then(function (data) {
                window.ctrlTable.fillTable(templateTable, 'table.table tbody', data);
            }, notifyError);
        }

        function notifyError(data) {
            notifyService.notifyError(data.responseText);
        }

        loadData();

        return {
            hideForm: _hideForm,
            showForm: _showForm,
            save: salvar,
            remove: removeGame,
            //edit: edit
        };

        function getList() {
            return $.getJSON('api/games');
        }

        function get(id) {
            return $.getJSON('api/games?id=' + id);
        }

        function save(params) {
            return $.post('api/games', params);
        }

        function remove(id) {
            return $.ajax({
                url: 'api/games?id=' + id,
                method: 'DELETE'
            });
        }
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