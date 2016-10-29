(function () {

    'use strict';

    function TableController() {
        var fillTable = function (templateTable, selector, registros) {
            var response = '';

            for (var i = 0; i < registros.length; i++) {
                var linha = registros[i];
                var modelo = templateTable;
                
               for (var property in linha)  {
                   var regex = new RegExp('{{' + property + '}}', "g");
                    modelo = modelo.replace(regex, linha[property]);
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