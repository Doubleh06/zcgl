var Manager = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "manager"
};

/**
 * jqGrid初始化参数
 */
Manager.initOptions = function () {
    var options = {
        url : "/manager/grid",
        autowidth:true,
        colNames: ['编号','事故类型', '涉及人员',"人员所在部门/人员接待部门", '事发地点','事发时间','事故情况','汇报人','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'accident_type_name', index: 'accident_type_name', width: 80},
            {name: 'accident_man', index: 'accident_man', width: 60},
            {name: 'dept_name', index: 'dept_name', width: 60},
            {name: 'accident_place', index: 'accident_place', width: 200, sortable: false},
            {name: 'accident_time', index: 'accident_time', width: 100,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == "" || cellvar == undefined) {
                        return "";
                    }
                    var da = new Date(cellvar);
                    return dateFtt("yyyy-MM-dd hh:mm:ss", da);
                }},
            {name: 'accident_situation', index: 'accident_situation', width: 200, sortable: false},
            {name: 'report_man', index: 'report_man', width: 60, sortable: false},
            {name: 'operations', index: 'operations', width: 100, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-info"  value="照片查看" onclick="Manager.photos(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="Manager.delete(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="Manager.edit(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="Manager.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Manager.search = function () {
    var searchParam = {};
    searchParam.accidentMan = $("#accidentMan").val();
    searchParam.dept = $("#dept").val();
    searchParam.accidentType = $("#accidentType").val();
    console.log(searchParam);
    Manager.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Manager.resetSearch = function () {
    // $("#task").val("");
    // $("#deptmentId").html("请选择");
    // $("#userId").html("请选择");
    // Manager.search();
    window.location.href = "/manager/list";
};

/**
 *新增
 */
Manager.create = function () {
    window.location.href = "/createDemand/create";
}
/**
 * 导出
 */
Manager.export = function () {
    window.location.href = "/manager/export";

    // $("#exportModal").modal();
    // $.ajax({
    //     type : 'POST',
    //     url: '/Manager/export',
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
Manager.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/manager/delete?id=" + id, function () {
            success("成功删除");
            Manager.search();
        });
    })
};

/**
 * 照片查看
 *
 * @param id    userId
 */
Manager.photos = function (id) {
    window.location.href = "/manager/photos?id="+id;
};



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
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Manager.initOptions());
    Manager.table = jqGrid.init();

});