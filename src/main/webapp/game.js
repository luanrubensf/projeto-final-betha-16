(function () {

    'use strict';

    function gameController() {

        var categoriaId = 0;

        function _hideForm() {
            $('#divForm').hide();
        }

        function _showForm() {
            $('#divForm').show();

            var id = $('input[name=id]').val();

            $('#select-categorias').select2({
                ajax: {
                    url: "api/categorias",
                    dataType: 'json',
                    placeholder: {
                        id: id || '-1',
                        text: 'Select an option'
                    },
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