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
                    alert("moveEnd");
                    document.getElementById("updateForm:updateData").value = JSON.stringify($scope.data);
                });
                api.tasks.on.resizeEnd($scope, function (task){
                    alert("resizeEnd");
                    document.getElementById("updateForm:updateData").value = JSON.stringify($scope.data);
                });

            });
            api.data.on.change($scope, function (newData, oldData) {
                ///some logic
                // alert("changed");
            });

        };

    }]);
})();

