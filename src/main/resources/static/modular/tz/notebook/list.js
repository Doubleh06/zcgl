var Notebook = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "notebook"
};

/**
 * jqGrid初始化参数
 */
Notebook.initOptions = function () {
    var options = {
        url : "/zcgl/tz/computers",
        autowidth:true,
        colNames:
       ['序号','设备编码','规格型号','ＣＰＵ','内存','硬盘','操作系统','使用日期','一级部门','二级部门','使用人','购买价格','供应商','使用年限','现在残值','操作'],
       // ['序号','设备编码','规格型号','ＣＰＵ','内存','硬盘','显示器大小','操作系统','数量','单位','使用日期','一级部门','二级部门','使用人','购买价格','供应商','使用年限','现在残值','维修记录','备注'],
        colModel: [
            {name: 'id', index: 'id', width: 15},
            {name: 'sbbm', index: 'sbbm', width: 35},
            {name: 'ggxh', index: 'ggxh', width: 30},
            {name: 'cpu', index: 'cpu', width: 30},
            {name: 'nc', index: 'nc', width: 20},
            {name: 'yp', index: 'yp', width: 30},
            {name: 'czxt', index: 'czxt', width: 20},
            {name: 'syrq', index: 'syrq', width: 40,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == "" || cellvar == undefined) {
                        return "";
                    }
                    var da = new Date(cellvar);
                    return dateFtt("yyyy-MM-dd", da);
                }},
            {name: 'yjbm', index: 'yjbm', width: 30},
            {name: 'ejbm', index: 'ejbm', width: 30},
            {name: 'syr', index: 'syr', width: 30},
            {name: 'gmjg', index: 'gmjg', width: 30},
            {name: 'gys', index: 'gys', width: 30},
            {name: 'synx', index: 'synx', width: 20},
            {name: 'xzcz', index: 'xzcz', width: 20},
            {name: 'operations', index: 'operations', width:50 , sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                    str += '<input type="button" class="btn btn-sm btn-warning"   value="编  辑" onclick="Notebook.preEdit(' + id +')"/>&nbsp;';
                    str += '<input type="button" class="btn btn-sm btn-danger"   value="删  除" onclick="Notebook.delete(' + id +')"/>&nbsp;';

                return str;
            }}
        ]
        // ,
        // gridComplete: function () {
        //     refreshPermission(Notebook.domain);
        // }
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Notebook.search = function () {
    var searchParam = {};
    searchParam.title = $("#title").val();
    searchParam.locales = $("#locales").val();
    Notebook.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Notebook.resetSearch = function () {
    $("#title").val("");
    $("#locales").empty();
    $("#locales").append("<option value=\"zh-CN\" >中文</option> <option value=\"zh-TW\" >繁体</option> <option value=\"en-US\">English</option>");
    Notebook.search();
};



/**
 * 删除
 *
 * @param id    userId
 */
Notebook.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/backstageApi/aboutUs/companyDynamics/delete?id=" + id, function () {
            success("成功删除");
            Notebook.search();
        });
    })
};

/**
 * 编辑跳转
 *
 * @param id    userId
 */
Notebook.preEdit = function (id) {
    window.location.href = "/backstageApi/aboutUs/companyDynamics/preEdit?id=" + id;
};
/**
 * 新增
 */
Notebook.create = function(){
    window.location.href = "/backstageApi/aboutUs/companyDynamics/preEdit?id=";
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
$(function() {
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Notebook.initOptions());
    Notebook.table = jqGrid.init();

});