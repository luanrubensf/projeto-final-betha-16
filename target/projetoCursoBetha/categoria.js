(function () {

    function categoriaControler() {

        var templateTable;
        var notifyService = window.ctrlNotify;

        function _hideForm() {
            $('#divForm').hide();
        }

        function _showForm() {
            $('#divForm').show();
        }

        function switchControllButtons(disabled) {
            $('#btnSalvar').prop('disabled', disabled);
            $('#btnCancelar').prop('disabled', disabled);
        }

        function fillForm(categoria) {
            $('input[name=id]').val(categoria.id);
            $('input[name=descricao]').val(categoria.descricao);
        }

        function resetForm() {
            $('#formCadastroCategoria').trigger("reset");
            $('input[type=hidden]').val('');
        }

        function successSave() {
            resetForm();
            switchControllButtons(false);
            notifyService.notifySuccess('Categoria salva com sucesso');
            _hideForm();
            loadData();
        }

        function salvar() {
            switchControllButtons(true);
            var parametros = $('#formCadastroCategoria').serialize();
            save(parametros).then(successSave, notifyError);
        }

        function removeCategoria(id) {
            remove(id)
                .then(function () {
                    notifyService.notifySuccess('Categoria excluida com sucesso');
                    loadData();
                    resetForm();
                    _hideForm();
                }, notifyError);
        }

        function edit(id) {
            get(id).then(function (data) {
                fillForm(data);
                _showForm();
            }, notifyError);
        }

        function loadData() {
            templateTable = templateTable || $('table.table tbody').html();
            getList().then(function (data) {
                window.ctrlTable.fillTable(templateTable, 'table.table tbody', data);
            }, notifyError);
        }

        function notifyError(data){
            notifyService.notifyError(data.responseText);
        }

        loadData();

        return {
            hideForm: _hideForm,
            showForm: _showForm,
            save: salvar,
            remove: removeCategoria,
            edit: edit
        };

        function getList() {
            return $.getJSON('api/categorias');
        }

        function get(id) {
            return $.getJSON('api/categorias?id=' + id);
        }

        function save(params) {
            return $.post('api/categorias', params);
        }

        function remove(id) {
            return $.ajax({
                url: 'api/categorias?id=' + id,
                method: 'DELETE'
            });
        }
    }

    $(function () {
        window.ctrl = categoriaControler();
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