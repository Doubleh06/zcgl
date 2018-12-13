var Enclosure = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "enclosure"
};

/**
 * jqGrid初始化参数
 */
Enclosure.initOptions = function () {
    var options = {
        url : "/action/enclosure/grid",
        postData : {
            id : $("#id").val()
        },
        autowidth:true,
        colNames: ['文件名称','操作'],
        colModel: [
            {name: 'imgName', index: 'imgName', width: 100, sortable: false},
            {name: 'operations', index: 'operations', width: 100, sortable: false, formatter: function (cellValue, options, rowObject) {
                var imgName = "'"+rowObject["imgName"]+"'";
                var str = "";
                    str += '<input type="button" class=" btn btn-sm btn-success"  value="下载" onclick="Enclosure.download(' + imgName + ')"/>&nbsp;';
                    // str += '<input type="button" class=" btn btn-sm btn-warning"  value="删除" onclick="Enclosure.delete(' + id + ')"/>&nbsp;';

                // str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="Enclosure.edit(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="Enclosure.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Enclosure.search = function () {
    var searchParam = {};
    searchParam.responsibleMan = $("#responsibleMan").val();
    searchParam.responsibleDept = $("#responsibleDept").val();
    searchParam.responsibleDirector = $("#responsibleDirector").val();
    searchParam.address = $("#address").val();
    searchParam.ehsId = $("#ehsId").val();
    Enclosure.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Enclosure.resetSearch = function () {
    $("#responsibleMan").val("");
    // $("#responsibleDept").val("---请选择---");
    $("#responsibleDirector").val("");
    $("#address").find("option[value='']").attr("selected",true);
    $("#responsibleDept").empty();//find("option[value='']").attr("selected",true);
    $("#responsibleDept").append("<option value=''>---请选择---</option>");
    Enclosure.search();
};





/**
 * 删除
 *
 * @param id    userId
 */
Enclosure.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/action/delete?id=" + id, function () {
            success("成功删除");
            Enclosure.search();
        });
    })
};


/**
 * 下载
 * @param fmt
 * @param date
 * @returns {*}
 */
Enclosure.download = function (imgName) {
    window.location.href = "/action/enclosure/download?imgName="+imgName;
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
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Enclosure.initOptions());
    Enclosure.table = jqGrid.init();

});