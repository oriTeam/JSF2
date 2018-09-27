(function() {
    'use strict';

    var app = angular.module('plnkrGanttStable',
        ['gantt',
            'gantt.sortable',
            'gantt.movable',
            'gantt.drawtask',
            'gantt.tooltips',
            'gantt.bounds',
            'gantt.progress',
            'gantt.table',
            'gantt.tree',
            'gantt.groups',
            'gantt.resizeSensor',
            'gantt.overlap',
            'gantt.dependencies'
        ]);

    app.controller('Ctrl', ['$scope', function ($scope) {
        $scope.data = ganttData;
        $scope.registerApi = function(api) {
            api.core.on.ready($scope, function (api) {
                // Call API methods and register events.
                api.tasks.on.moveEnd($scope, function (task){
                    console.log("moveEnd");
                    document.getElementById("updateForm:updateData").value = JSON.stringify($scope.data);
                    datatable_init();
                });
                api.tasks.on.resizeEnd($scope, function (task){
                    console.log("resizeEnd");
                    document.getElementById("updateForm:updateData").value = JSON.stringify($scope.data);
                    datatable_init();
                });

            });
            api.data.on.change($scope, function (newData, oldData) {
                ///some logic
                // alert("changed");
            });

        };

    }]);

    function datatable_init() {
        var data = JSON.parse($('.update-data').val());
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
            dom: 'Bfrt',
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
    }

})();

