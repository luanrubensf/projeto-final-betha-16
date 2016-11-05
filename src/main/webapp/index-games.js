(function () {

    'use strict';

    var templateTable;
    var notifyService = window.ctrlNotify;

    loadData();

    function loadData() {
        templateTable = templateTable || $('table.table tbody').html();
        getList().then(function (data) {
            window.ctrlTable.fillTable(templateTable, 'table.table tbody', data);
        }, notifyError);
    }

    function getList() {
        return $.getJSON('api/games?finalizado=true');
    }

    function notifyError(data) {
        notifyService.notifyError(data.responseText);
    }
})();