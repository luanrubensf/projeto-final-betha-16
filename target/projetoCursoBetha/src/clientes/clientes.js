(function () {

    function Cliente(id, documento, nome, telefone, celular, email) {
        this.id = id;
        this.documento = documento;
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;

        return this;
    }

    function clienteControler() {

        var registros = [];
        var itemAtual;
        var proximoId = 0;
        var templateTable;

        var _add = function () {
            itemAtual = new Cliente(++proximoId);
            registros.push(itemAtual);
            _preencheForm(itemAtual);
        };

        var _preencheForm = function (objCliente) {
            $('input[name=id]').val(objCliente.id);
            $('input[name=documento]').val(objCliente.documento);
            $('input[name=nome]').val(objCliente.nome);
            $('input[name=telefone]').val(objCliente.telefone);
            $('input[name=celular]').val(objCliente.celular);
            $('input[name=email]').val(objCliente.email);
        };

        var _save = function () {
            itemAtual.id = $('input[name=id]').val();
            itemAtual.documento = $('input[name=documento]').val();
            itemAtual.nome = $('input[name=nome]').val();
            itemAtual.telefone = $('input[name=telefone]').val();
            itemAtual.celular = $('input[name=celular]').val();
            itemAtual.email = $('input[name=email]').val();

            _preencheTable();
        };

        var _preencheTable = function () {
            templateTable = templateTable || $('table.table tbody').html();

            var response = '';

            for (var i = 0; i < registros.length; i++) {
                var linha = registros[i];
                var modelo = templateTable;
                modelo = modelo.replace(/\{\{documento\}\}/g, linha.documento);
                modelo = modelo.replace(/\{\{nome\}\}/g, linha.nome);
                modelo = modelo.replace(/\{\{telefone\}\}/g, linha.telefone);
                modelo = modelo.replace(/\{\{id\}\}/g, linha.id);

                response += modelo;
            }
            $('table.table tbody').html(response);
        };

        var _edit = function (id) {
            for (var i = 0; i < registros.length; i++) {
                if (id == registros[i].id) {
                    itemAtual = registros[i];
                    _preencheForm(itemAtual);
                    break;
                }
            }
        };

        var _remove = function (id) {
            for (var i = 0; i < registros.length; i++) {
                if (id == registros[i].id) {
                    if (confirm('Deseja realmente excluir o registro?'))
                        registros.splice(i, 1);
                        _preencheTable();
                    break;
                }
            }
        };

        var _carrega = function() {
            templateTable = templateTable || $('table.table tbody').html();
            $.getJSON('clientes.json', function(dados){
                registros = dados;
                window.ctrlTable.fillTable(templateTable, 'table.table tbody', registros);
            });
        };

        _carrega();

        return {
            add: _add,
            save: _save,
            edit: _edit,
            remove: _remove
        };
    }

    $(function(){
        window.ctrl = clienteControler();
        $('#btnSalvar').click(function(){
            ctrl.save();
        });
        $('#btnAdicionar').click(function(){
            ctrl.add();
        });
    });

})();