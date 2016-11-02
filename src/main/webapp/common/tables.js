(function () {

    'use strict';

    function TableController() {
        var fillTable = function (templateTable, selector, registros) {
            var response = '';

            for (var i = 0; i < registros.length; i++) {
                var linha = registros[i];
                var modelo = templateTable;

                for (var property in linha) {
                    var regex = new RegExp('{{' + property + '}}', "g");
                    
                    var value = '';

                    if(typeof(linha[property]) === 'object'){
                        value = linha[property].nome || linha[property].descricao; 
                    } else {
                        value = linha[property];
                    }

                    modelo = modelo.replace(regex, value);
                }

                response += modelo;
            }
            $(selector).html(response);
        };

        return {
            fillTable: fillTable
        };
    }


    $(function () {
        window.ctrlTable = TableController();
    });

})();