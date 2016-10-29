(function () {

    'use strict';

    function TableController() {

        function notifySuccess(msg) {
            $.notify({
                message: msg
            }, {
                    type: 'success'
                });
        }

        function notifyError(msg) {
            $.notify({
                message: msg
            }, {
                    type: 'danger'
                });
        }

        return {
            notifySuccess: notifySuccess,
            notifyError: notifyError
        };
    }

    $(function () {
        window.ctrlNotify = TableController();
    });

})();