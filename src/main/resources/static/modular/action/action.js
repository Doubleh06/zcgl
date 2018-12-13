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
        postData : {
            ehsId : $("#ehsId").val()
        },
        autowidth:true,
        colNames: ['ehsId','描述', '负责人',"地址", '负责部门','负责主管','关闭时间','操作'],
        colModel: [
            {name: 'ehsId', index: 'ehsId', width: 20},
            {name: 'descriptive', index: 'descriptive', width: 80},
            {name: 'responsibleMan', index: 'responsibleMan', width: 60},
            {name: 'address', index: 'address', width: 50, sortable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == 'CZ') {
                        return "常州";
                    }else if(cellvar == 'CQ'){
                        return "重庆"
                    }else{
                        return "";
                    }

                }},
            {name: 'responsibleDept', index: 'responsibleDept', width: 60},
            {name: 'responsibleDirector', index: 'responsibleDirector', width: 60, sortable: false},
            {name: 'closeTime', index: 'closeTime', width: 80,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == "" || cellvar == undefined) {
                        return "";
                    }
                    var da = new Date(cellvar);
                    return dateFtt("yyyy-MM-dd hh:mm:ss", da);
                }},
            {name: 'operations', index: 'operations', width: 100, sortable: false, formatter: function (cellValue, options, rowObject) {
                var imgUrl = rowObject["imgUrl"];
                var id = "'"+rowObject["id"]+"'";
                var ehsId = "'"+rowObject["ehsId"]+"'";
                var realCloseTime = rowObject["realCloseTime"];
                var str = "";
                if(""!=imgUrl&&null!=imgUrl){
                    str += '<input type="button" class=" btn btn-sm btn-success"  value="查看附件" onclick="Action.enclosure(' + id+","+ehsId + ')"/>&nbsp;';
                }
                if (""==realCloseTime||null==realCloseTime){
                    str += '<input type="button" class=" btn btn-sm btn-warning"  value="关闭" onclick="Action.close(' + id + ')"/>&nbsp;';
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
    searchParam.responsibleMan = $("#responsibleMan").val();
    searchParam.responsibleDept = $("#responsibleDept").val();
    searchParam.responsibleDirector = $("#responsibleDirector").val();
    searchParam.address = $("#address").val();
    Action.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Action.resetSearch = function () {
    $("#responsibleMan").val("");
    // $("#responsibleDept").val("---请选择---");
    $("#responsibleDirector").val("");
    $("#address").find("option[value='']").attr("selected",true);
    $("#responsibleDept").empty();//find("option[value='']").attr("selected",true);
    $("#responsibleDept").append("<option value=''>---请选择---</option>");
    Action.search();
};

/**
 *新增
 */
Action.enclosure = function (id,ehsId) {
    window.location.href = "/action/enclosureAction?id="+id+"&ehsId="+ehsId;
}
/**
 * 导出
 */
Action.export = function (ehsId) {
    window.location.href = "/action/export?ehsId="+ehsId;

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
Action.delete = function (id) {
    warning("确定删除吗", "", function () {
        $.get("/action/delete?id=" + id, function () {
            success("成功删除");
            Action.search();
        });
    })
};

/**
 * 关闭
 *
 * @param id    userId
 */
Action.close = function (id) {
    warning("确定关闭吗？", "", function () {
        $.get("/action/close?id=" + id, function () {
            success("关闭成功");
            Action.search();
        });
    })
};

Action.insert = function () {
    var email = $("#email").val();
    if(null==email||""==email){
        error("邮件地址不能为空");
        return;
    }
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
                successthen("保存成功",null,"/backstage/list");
                // $("#create-form")[0].reset();
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