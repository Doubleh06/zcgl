var Backstage = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "backstage"
};

/**
 * jqGrid初始化参数
 */
Backstage.initOptions = function () {
    var options = {
        url : "/backstage/grid",
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
                var total = rowObject["total_action"];
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                if(""!=imgUrl&&null!=imgUrl){
                    str += '<input type="button" class="control-auth btn btn-sm btn-success" data-auth="backstage_photos" value="照片查看" onclick="Backstage.photos(' + id + ')"/>&nbsp;';
                }

                    str += '<input type="button" class="control-auth btn btn-sm btn-warning" data-auth="backstage_delete"  value="删除" onclick="Backstage.delete(' + id + ')"/>&nbsp;';

                if(0==status){
                    str += '<input type="button" class="control-auth btn btn-sm btn-danger" data-auth="backstage_changeStatus"  value="批准" onclick="Backstage.changeStatus(' + id +','+ 1 + ')"/>&nbsp;';
                    str += '<input type="button" class="control-auth btn btn-sm btn-danger" data-auth="backstage_changeStatus"  value="拒绝" onclick="Backstage.changeStatus(' + id +','+ 2 +')"/>&nbsp;';
                }else if (1==status){
                    str += '<input type="button" class="control-auth btn btn-sm btn-info" data-auth="backstage_createAction" value="新增Action" onclick="Backstage.createAction(' + id +')"/>&nbsp;';
                }
                if(0!=total){
                    str += '<input type="button" class="control-auth btn btn-sm btn-info" data-auth="backstage_seeAction" value="查看Action" onclick="Backstage.seeAction(' + id +')"/>&nbsp;';
                }
                return str;
            }}
        ],
        gridComplete: function () {
            refreshPermission(Backstage.domain);
        }
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Backstage.search = function () {
    var searchParam = {};
    searchParam.accidentMan = $("#accidentMan").val();
    searchParam.dept = $("#dept").val();
    searchParam.accidentType = $("#accidentType").val();
    searchParam.address = $("#address").val();
    searchParam.startDate = $("#startDate").val();
    searchParam.endDate = $("#endDate").val();
    Backstage.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Backstage.resetSearch = function () {
    // $("#task").val("");
    // $("#deptmentId").html("请选择");
    // $("#userId").html("请选择");
    // Backstage.search();
    window.location.href = "/backstage/list";
};



/**
 * 导出
 */
Backstage.export = function () {
    $.ajax({
        type : 'POST',
        url: '/backstage/prepareExportData',
        contentType : "application/json" ,
        data: JSON.stringify({
            accidentMan:$("#accidentMan").val(),
            dept:$("#dept").val(),
            address:$("#address").val(),
            startDate:$("#startDate").val(),
            endDate:$("#endDate").val()
        }),
        success : function() {
            window.open("/backstage/export");
        }
    });

}



/**
 * 删除
 *
 * @param id    userId
 */
Backstage.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/backstage/delete?id=" + id, function () {
            success("成功删除");
            Backstage.search();
        });
    })
};

/**
 * 批准 / 拒绝
 *
 * @param id    userId
 */
Backstage.changeStatus = function del(id,status) {
        $.get("/backstage/changeStatus?id=" + id+"&status="+status, function () {
            success("操作成功");
            Backstage.search();
        });
};

/**
 * 照片查看
 *
 * @param id    userId
 */
Backstage.photos = function (id) {
    window.location.href = "/backstage/photos?id="+id;
};
/**
 * 创建行动
 *
 * @param id    userId
 */
Backstage.createAction = function (id) {
    window.location.href = "/action/createAction?id="+id;
};

/**
 * 查看
 */
Backstage.seeAction = function (id) {
    window.location.href = "/action/seeAction?id="+id;
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
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Backstage.initOptions());
    Backstage.table = jqGrid.init();

});