$(document).ready(function () {
    $('.save-excel-btn').click(function () {
        let data = JSON.parse($('.update-data').val());
        console.log(data);
        $('#hidden-table').DataTable({
            data : data,
            columns: [
                { "data": "name" },
                { "data": "tasks[0].name" },
                { "data": "tasks[0].from" },
                { "data": "tasks[0].to" },
                { "data": "tasks[0].progress.percent" },
            ],
            dom: 'Bfrtip',
            buttons: [
                {
                    extend: 'excel',
                    text: 'Save in excel format',
                    className: 'exportExcel',
                    filename: 'UpdatedGanttChart',
                    exportOptions: {
                        modifier: {
                            page: 'all'
                        }
                    }
                },
                {
                    extend: 'pdf',
                    text: 'Save in pdf format',
                    className: 'exportPdf',
                    filename: 'UpdatedGanttChart',
                    exportOptions: {
                        modifier: {
                            page: 'all'
                        }
                    }
                }
            ],
            destroy: true,
            searching: false
        });
    });

});