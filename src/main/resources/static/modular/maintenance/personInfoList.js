var PersonInfo = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "personInfo"
};

/**
 * jqGrid初始化参数
 */
PersonInfo.initOptions = function () {
    var options = {
        url : "/maintenance/personInfo/grid",
        autowidth:true,
        colNames: ['编号','工号','姓名','邮箱','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'userId', index: 'userId', width: 150},
            {name: 'name', index: 'name', width: 150},
            {name: 'email', index: 'email', width: 150},
            {name: 'operations', index: 'operations', width: 80, sortable: false, formatter: function (cellValue, options, rowObject) {

                var id = "'"+rowObject["id"]+"'";
                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="PersonInfo.delete(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="PersonInfo.modify(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="PersonInfo.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
PersonInfo.search = function () {
    var searchParam = {};
    searchParam.userId = $("#userId").val();
    searchParam.name = $("#name").val();
    searchParam.email = $("#email").val();
    PersonInfo.table.reload(searchParam);
};

/**
 * 重置搜索
 */
PersonInfo.resetSearch = function () {
    window.location.href = "/maintenance/personInfo/list";
};

/**
 *新增
 */
PersonInfo.create = function () {
    $("#createModal").modal();
}
PersonInfo.insert = function () {
    var personInfo = getFormJson($("#create-form"));
    $.ajax({
        url: "/maintenance/personInfo/insert",
        type: 'POST',
        data: JSON.stringify(personInfo),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#createModal").modal("hide");
                success("保存成功");
                PersonInfo.search();
                $("#create-form")[0].reset();
            }
        }
    })
}

/**
 *编辑
 */
PersonInfo.modify = function (id) {
    $.ajax({
        url: "/maintenance/personInfo/get?id=" + id,
        type: 'GET',
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                var personInfo = r.obj;
                var form = $("#modify-form");
                form.find("input[name='name']").val(personInfo.name);
                form.find("input[name='userId']").val(personInfo.userId);
                form.find("input[name='email']").val(personInfo.email);
                form.find("input[name='id']").val(personInfo.id);
                $("#modifyModal").modal();
            }
        }
    })
    $("#modifyModal").modal();
}
PersonInfo.update = function () {
    var personInfo = getFormJson($("#modify-form"));
    $.ajax({
        url: "/maintenance/personInfo/update",
        type: 'POST',
        data: JSON.stringify(personInfo),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#modifyModal").modal("hide");
                success("编辑成功");
                PersonInfo.search();
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
PersonInfo.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/maintenance/personInfo/delete?id=" + id, function () {
            success("成功删除");
            PersonInfo.search();
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
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", PersonInfo.initOptions());
    PersonInfo.table = jqGrid.init();

});