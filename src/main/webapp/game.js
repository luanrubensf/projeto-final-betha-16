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

        function setSelect2(data) {
            $('#select-categorias').select2({
                data: [data],
                placeholder: 'Selecione uma categoria',
                allowClear: true,
                ajax: {
                    url: "api/categorias",
                    dataType: 'json',
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

        function _showForm() {
            $('#divForm').show();

            setSelect2();
        }

        function successSave() {
            resetForm();
            switchControllButtons(false);
            _hideForm();
            loadData();
            notifyService.notifySuccess('Game salvo com sucesso');
        }

        function errorSave(data) {
            notifyError(data);
            switchControllButtons(false);
        }

        function fillForm(game) {
            $('input[name=id]').val(game.id);
            $('input[name=nome]').val(game.descricao);
            $('textarea[name=descricao]').val(game.descricao);
            $('input[name=ano]').val(game.ano);
            $('input[name=finalizado]').prop('checked', game.finalizado);

            if (game.categoria) {
                game.categoria.text = game.categoria.descricao;
                setSelect2(game.categoria);
            }
        }

        function edit(id) {
            get(id).then(function (data) {
                fillForm(data);
                _showForm();
            }, notifyError);
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
            edit: edit
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