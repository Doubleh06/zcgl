var ActionWithoutEhsId = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "actionWithoutEhsId"
};

/**
 * jqGrid初始化参数
 */
ActionWithoutEhsId.initOptions = function () {
    var options = {
        url : "/actionWithoutEhsId/grid",
        postData : {
            ehsId : $("#ehsId").val()
        },
        autowidth:true,
        colNames: ['id','ehsId','描述', '负责人',"地址", '负责部门','负责主管','关闭时间','实际关闭时间','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
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
            {name: 'realCloseTime', index: 'realCloseTime', width: 80,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
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
                    str += '<input type="button" class=" btn btn-sm btn-success"  value="查看附件" onclick="ActionWithoutEhsId.enclosure(' + id + ')"/>&nbsp;';
                }
                if (""==realCloseTime||null==realCloseTime){
                    str += '<input type="button" class=" btn btn-sm btn-warning"  value="关闭" onclick="ActionWithoutEhsId.close(' + id + ')"/>&nbsp;';
                }


                // str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="ActionWithoutEhsId.edit(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="ActionWithoutEhsId.delete(' + id + ')"/>';
                return str;
            }}
        ],
        gridComplete: function () {
            var ids = $("#grid-table").getDataIDs();
            for(var i=0;i<ids.length;i++){
                var rowData = $("#grid-table").getRowData(ids[i]);
                var realCloseTime = rowData.realCloseTime;
                if(null==realCloseTime||""==realCloseTime){
                    var flag = dateDiff(rowData.closeTime);
                    if("Y"==flag){
                        $('#'+ids[i]).find("td").css("color","#d2cf1d");
                    }else if("R"==flag){
                        $('#'+ids[i]).find("td").css("color","#d23122");
                    }

                }
            }
        }

    };
    return options;
};

/**
 * 根据关键词搜索
 */
ActionWithoutEhsId.search = function () {
    var searchParam = {};
    searchParam.responsibleMan = $("#responsibleMan").val();
    searchParam.responsibleDept = $("#responsibleDept").val();
    searchParam.responsibleDirector = $("#responsibleDirector").val();
    searchParam.address = $("#address").val();
    searchParam.startDate = $("#startDate").val();
    searchParam.endDate = $("#endDate").val();
    ActionWithoutEhsId.table.reload(searchParam);
};

/**
 * 重置搜索
 */
ActionWithoutEhsId.resetSearch = function () {
    $("#responsibleMan").val("");
    $("#responsibleDirector").val("");
    $("#address").empty();
    $("#address").append("<option value=''>---请选择---</option> <option value='CZ' >常州</option><option value='CQ'>重庆</option>");
    $("#responsibleDept").empty();
    $("#responsibleDept").append("<option value=''>---请选择---</option>");
    $("#startDate").val("");
    $("#endDate").val("");
    ActionWithoutEhsId.search();
};
/**
 *附件查看
 */
ActionWithoutEhsId.create = function () {
    window.location.href = "/actionWithoutEhsId/createAction";
}
/**
 *附件查看
 */
ActionWithoutEhsId.enclosure = function (id) {
    window.location.href = "/actionWithoutEhsId/enclosureAction?id="+id;
}
/**
 * 导出
 */
ActionWithoutEhsId.export = function () {
    window.location.href = "/actionWithoutEhsId/export";
}



/**
 * 删除
 *
 * @param id    userId
 */
ActionWithoutEhsId.delete = function (id) {
    warning("确定删除吗", "", function () {
        $.get("/actionWithoutEhsId/delete?id=" + id, function () {
            success("成功删除");
            ActionWithoutEhsId.search();
        });
    })
};

/**
 * 关闭
 *
 * @param id    userId
 */
ActionWithoutEhsId.close = function (id) {
    input("确定关闭吗？", "请输入关闭理由", function (inputValue) {
        if (inputValue === false) return false;
        if (inputValue === "") {
            swal.showInputError("内容不能为空，请输入关闭理由！");
            return false
        }
        $.get("/actionWithoutEhsId/close?id=" + id+"&closeReason="+inputValue, function(){
            success("关闭成功");
            ActionWithoutEhsId.search();
        });
    })
};

ActionWithoutEhsId.insert = function (btn) {
    var email = $("#email").val();
    if(null==email||""==email){
        error("责任人邮件地址不能为空");
        return;
    }
    var directorEmail = $("#directorEmail").val();
    if(null==directorEmail||""==directorEmail){
        error("责任主管邮件地址不能为空");
        return;
    }
    var actionWithoutEhsId = getFormJson($("#create-form"));
    var l = $(btn).ladda();
    l.ladda('start');
    $.ajax({
        url: "/actionWithoutEhsId/insert",
        type: 'POST',
        data: JSON.stringify(actionWithoutEhsId),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                // $("#createModal").modal("hide");
                l.ladda('stop');
                successthen("保存成功",null,"/actionWithoutEhsId/seeAction");
                // $("#create-form")[0].reset();
            }
        }
    })
}





function dateFtt(fmt,date) { //author: meizz
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

function dateDiff(date) { //author: meizz
    //指定关闭时间
    var time = Date.parse(new Date(date));
    //当前时间
    var ctime = Date.parse(new Date());
    var diff = (time-ctime)/1000;
    // console.log(diff)
    if(-86400*7<diff&&diff<86400*3){
        return "Y";
    }else if (diff<-86400*7){
        return "R";
    }else{
        return "N";
    }

}

$(function() {
    $('.chosen-select').chosen({width: "100%"});
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", ActionWithoutEhsId.initOptions());
    ActionWithoutEhsId.table = jqGrid.init();

});