var Action = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "action"
};

/**
 * jqGrid初始化参数
 */
Action.initOptions = function () {
    var options = {
        url : "/action/grid",
        autowidth:true,
        colNames: ['编号','事故类型', '涉及人员',"人员所在部门/人员接待部门", '事发地点','事发时间','提交时间','事故情况','汇报人','状态','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'accident_type_name', index: 'accident_type_name', width: 80},
            {name: 'accident_man', index: 'accident_man', width: 60},
            {name: 'dept', index: 'dept', width: 60},
            {name: 'accident_place', index: 'accident_place', width: 60, sortable: false},
            {name: 'accident_time', index: 'accident_time', width: 80,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == "" || cellvar == undefined) {
                        return "";
                    }
                    var da = new Date(cellvar);
                    return dateFtt("yyyy-MM-dd hh:mm:ss", da);
                }},
            {name: 'submit_time', index: 'submit_time', width: 80,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == "" || cellvar == undefined) {
                        return "";
                    }
                    var da = new Date(cellvar);
                    return dateFtt("yyyy-MM-dd hh:mm:ss", da);
                }},
            {name: 'accident_situation', index: 'accident_situation', width: 80, sortable: false},
            {name: 'report_man', index: 'report_man', width: 60, sortable: false},
            {name: 'status', index: 'status', width: 40, sortable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == 0) {
                        return "处理中";
                    }else if(cellvar == 1){
                        return "批准"
                    }else{
                        return "拒绝";
                    }

                }},
            {name: 'operations', index: 'operations', width: 150, sortable: false, formatter: function (cellValue, options, rowObject) {
                var imgUrl = rowObject["img_url"];
                var status = rowObject['status'];

                var id = "'"+rowObject["id"]+"'";
                var str = "";
                if(""!=imgUrl&&null!=imgUrl){
                    str += '<input type="button" class=" btn btn-sm btn-success"  value="照片查看" onclick="Action.photos(' + id + ')"/>&nbsp;';
                }
                    str += '<input type="button" class=" btn btn-sm btn-warning"  value="删除" onclick="Action.delete(' + id + ')"/>&nbsp;';
                if(0==status){
                    str += '<input type="button" class=" btn btn-sm btn-danger"  value="批准" onclick="Action.changeStatus(' + id +','+ 1 + ')"/>&nbsp;';
                    str += '<input type="button" class=" btn btn-sm btn-danger"  value="拒绝" onclick="Action.changeStatus(' + id +','+ 2 +')"/>&nbsp;';
                }else if (1==status){
                    str += '<input type="button" class=" btn btn-sm btn-info"  value="新增Action" onclick="Action.createAction(' + id +')"/>&nbsp;';
                    str += '<input type="button" class=" btn btn-sm btn-info"  value="查看Action" onclick="Action.seeAction(' + id +')"/>&nbsp;';
                }
                // str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="Action.edit(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="Action.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Action.search = function () {
    var searchParam = {};
    searchParam.accidentMan = $("#accidentMan").val();
    searchParam.dept = $("#dept").val();
    searchParam.accidentType = $("#accidentType").val();
    searchParam.address = $("#address").val();
    console.log(searchParam);
    Action.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Action.resetSearch = function () {
    // $("#task").val("");
    // $("#deptmentId").html("请选择");
    // $("#userId").html("请选择");
    // Action.search();
    window.location.href = "/action/list";
};

/**
 *新增
 */
Action.create = function () {
    window.location.href = "/createDemand/create";
}
/**
 * 导出
 */
Action.export = function () {
    window.location.href = "/action/export";

    // $("#exportModal").modal();
    // $.ajax({
    //     type : 'POST',
    //     url: '/Action/export',
    //     contentType : "application/json" ,
    //     // data : JSON.stringify({
    //     //     "keys" : keys
    //     // }),
    //     success : function(data) {
    //          window.open("/leads/download?key="+data.obj);
    //     }
    // });
}



/**
 * 删除
 *
 * @param id    userId
 */
Action.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/action/delete?id=" + id, function () {
            success("成功删除");
            Action.search();
        });
    })
};

Action.insert = function () {
    var action = getFormJson($("#create-form"));
    $.ajax({
        url: "/action/insert",
        type: 'POST',
        data: JSON.stringify(action),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#createModal").modal("hide");
                success("保存成功");
                Menu.search();
                $("#create-form")[0].reset();
            }
        }
    })
}





    function dateFtt(fmt,date)
    { //author: meizz
        var o = {
            "M+" : date.getMonth()+1,                 //月份
            "d+" : date.getDate(),                    //日
            "h+" : date.getHours(),                   //小时
            "m+" : date.getMinutes(),                 //分
            "s+" : date.getSeconds(),                 //秒
            "q+" : Math.floor((date.getMonth()+3)/3), //季度
            "S"  : date.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }

$(function() {
    $('.chosen-select').chosen({width: "100%"});
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Action.initOptions());
    Action.table = jqGrid.init();

});