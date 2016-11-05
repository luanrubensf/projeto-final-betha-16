(function () {

    'use strict';

    function emprestimoController() {

        var gameId = 0;
        var templateTable;
        var notifyService = window.ctrlNotify;

        setDateTimePicker();

        function setDateTimePicker() {
            $('#emissao').datetimepicker({
                format: 'dd/mm/yyyy HH:ii:ss'
            });
            $('#devolucao').datetimepicker({
                format: 'dd/mm/yyyy HH:ii:ss'
            });
        }

        function resetForm() {
            $('#formCadastroEmprestimo').trigger("reset");
            $('input[type=hidden]').val('');
            $('#select-games').select2().val(null);
        }

        function _hideForm() {
            $('#divForm').hide();
             $('#select-games').html('');
        }

        function switchControllButtons(disabled) {
            $('#btnSalvar').prop('disabled', disabled);
            $('#btnCancelar').prop('disabled', disabled);
            $('#select-games').select2('data', null);
        }

        function setSelect2() {
            $('#select-games').select2({
                placeholder: 'Selecione um game',
                allowClear: true,
                ajax: {
                    url: "api/games",
                    dataType: 'json',
                    processResults: function (data, params) {
                        var result = [];

                        if (!params.term) {
                            params.term = '';
                        }

                        data.forEach(function (item) {
                            item.text = item.nome;

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
                gameId = $(e.currentTarget).val();
            })
            .on('select2:unselect', function(){
                gameId = null;
            })
                .trigger('change');
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
            notifyService.notifySuccess('Empréstimo salvo com sucesso');
        }

        function errorSave(data) {
            notifyError(data);
            switchControllButtons(false);
        }

        function fillForm(emprestimo) {
            $('input[name=id]').val(emprestimo.id);
            $('input[name=destino]').val(emprestimo.destino);
            $('textarea[name=observacao]').val(emprestimo.observacao);
            $('#emissao').val(moment(emprestimo.emissao).format('DD/MM/YYYY HH:mm:ss'));
            
            if(emprestimo.devolucao) {
                $('#devolucao').val(moment(emprestimo.devolucao).format('DD/MM/YYYY HH:mm:ss'));
            }

            if (emprestimo.game) {
                var templateSelect2 = '<option value="' + emprestimo.game.id + '" selected="true">' + emprestimo.game.nome + '</option>';
                $('#select-games').html(templateSelect2);
                gameId = emprestimo.game.id;
            } else {
                $('#select-games').html('');
            }

            setSelect2();
        }

        function edit(id) {
            get(id).then(function (data) {
                _showForm();
                fillForm(data);
            }, notifyError);
        }

        function salvar() {
            switchControllButtons(true);
            var parametros = $('#formCadastroEmprestimo').serialize();

            if (gameId) {
                parametros += '&game=' + gameId;
            }

            save(parametros).then(successSave, errorSave);
        }

        function removeEmprestimo(id) {
            remove(id)
                .then(function () {
                    notifyService.notifySuccess('Empréstimo exluído com sucesso');
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
            remove: removeEmprestimo,
            edit: edit
        };

        function getList() {
            return $.getJSON('api/emprestimos');
        }

        function get(id) {
            return $.getJSON('api/emprestimos?id=' + id);
        }

        function save(params) {
            return $.post('api/emprestimos', params);
        }

        function remove(id) {
            return $.ajax({
                url: 'api/emprestimos?id=' + id,
                method: 'DELETE'
            });
        }
    }

    $(function () {
        window.ctrl = emprestimoController();
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