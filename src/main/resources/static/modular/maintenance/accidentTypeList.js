var AccidentType = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "accidentType"
};

/**
 * jqGrid初始化参数
 */
AccidentType.initOptions = function () {
    var options = {
        url : "/maintenance/accidentType/grid",
        autowidth:true,
        colNames: ['编号','事故类型','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'name', index: 'name', width: 150},
            {name: 'operations', index: 'operations', width: 80, sortable: false, formatter: function (cellValue, options, rowObject) {

                var id = "'"+rowObject["id"]+"'";
                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="AccidentType.delete(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="AccidentType.modify(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="AccidentType.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
AccidentType.search = function () {
    var searchParam = {};
    searchParam.name = $("#name").val();
    console.log(searchParam);
    AccidentType.table.reload(searchParam);
};

/**
 * 重置搜索
 */
AccidentType.resetSearch = function () {
    window.location.href = "/maintenance/accidentType/list";
};

/**
 *新增
 */
AccidentType.create = function () {
    $("#createModal").modal();
}
AccidentType.insert = function () {
    var accidentType = getFormJson($("#create-form"));
    $.ajax({
        url: "/maintenance/accidentType/insert",
        type: 'POST',
        data: JSON.stringify(accidentType),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#createModal").modal("hide");
                success("保存成功");
                AccidentType.search();
                $("#create-form")[0].reset();
            }
        }
    })
}

/**
 *编辑
 */
AccidentType.modify = function (id) {
    $.ajax({
        url: "/maintenance/accidentType/get?id=" + id,
        type: 'GET',
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                var accidentType = r.obj;
                var form = $("#modify-form");
                form.find("input[name='name']").val(accidentType.name);
                form.find("input[name='id']").val(accidentType.id);
                $("#modifyModal").modal();
            }
        }
    })
    $("#modifyModal").modal();
}
AccidentType.update = function () {
    var accidentType = getFormJson($("#modify-form"));
    $.ajax({
        url: "/maintenance/accidentType/update",
        type: 'POST',
        data: JSON.stringify(accidentType),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#modifyModal").modal("hide");
                success("编辑成功");
                AccidentType.search();
                $("#modify-form")[0].reset();
            }
        }
    })
}

/**
 * 删除
 *
 * @param id    userId
 */
AccidentType.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/maintenance/accidentType/delete?id=" + id, function () {
            success("成功删除");
            AccidentType.search();
        });
    })
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
    // $('.chosen-select').chosen({width: "100%"});
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", AccidentType.initOptions());
    AccidentType.table = jqGrid.init();

});