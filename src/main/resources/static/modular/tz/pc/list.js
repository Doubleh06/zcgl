var PC = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "pc"
};

/**
 * jqGrid初始化参数
 */
PC.initOptions = function () {
    var options = {
        url : "/first/tz/computers?type=1",
        autowidth:true,
        colNames:
       ['序号','设备编码','规格型号','ＣＰＵ','内存','硬盘','显示器大小','操作系统','数量','单位','使用日期','一级部门','二级部门','使用人','购买价格','供应商','使用年限','现在残值','维修记录','备注'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'sbbm', index: 'sbbm', width: 30},
            {name: 'ggxh', index: 'ggxh', width: 30},
            {name: 'cpu', index: 'cpu', width: 30},
            {name: 'nc', index: 'nc', width: 30},
            {name: 'yp', index: 'yp', width: 30},
            {name: 'xsq', index: 'xsq', width: 30},
            {name: 'czxt', index: 'czxt', width: 30},
            {name: 'num', index: 'num', width: 30},
            {name: 'dw', index: 'dw', width: 30},
            {name: 'yjbm', index: 'yjbm', width: 30},
            {name: 'ejbm', index: 'ejbm', width: 30},
            {name: 'syr', index: 'syr', width: 30},
            {name: 'gmjg', index: 'gmjg', width: 30},
            {name: 'gys', index: 'gys', width: 30},
            {name: 'synx', index: 'synx', width: 30},
            {name: 'xzcz', index: 'xzcz', width: 30},
            {name: 'wxjl', index: 'wxjl', width: 30},
            {name: 'memo', index: 'memo', width: 30},
            {name: 'operations', index: 'operations', width:50 , sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                    str += '<input type="button" class="btn btn-sm btn-warning"   value="编  辑" onclick="PC.preEdit(' + id +')"/>&nbsp;';
                    str += '<input type="button" class="btn btn-sm btn-danger"   value="删  除" onclick="PC.delete(' + id +')"/>&nbsp;';

                return str;
            }}
        ]
        // ,
        // gridComplete: function () {
        //     refreshPermission(PC.domain);
        // }
    };
    return options;
};

/**
 * 根据关键词搜索
 */
PC.search = function () {
    var searchParam = {};
    searchParam.title = $("#title").val();
    searchParam.locales = $("#locales").val();
    PC.table.reload(searchParam);
};

/**
 * 重置搜索
 */
PC.resetSearch = function () {
    $("#title").val("");
    $("#locales").empty();
    $("#locales").append("<option value=\"zh-CN\" >中文</option> <option value=\"zh-TW\" >繁体</option> <option value=\"en-US\">English</option>");
    PC.search();
};



/**
 * 删除
 *
 * @param id    userId
 */
PC.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/backstageApi/aboutUs/companyDynamics/delete?id=" + id, function () {
            success("成功删除");
            PC.search();
        });
    })
};

/**
 * 编辑跳转
 *
 * @param id    userId
 */
PC.preEdit = function (id) {
    window.location.href = "/backstageApi/aboutUs/companyDynamics/preEdit?id=" + id;
};
/**
 * 新增
 */
PC.create = function(){
    window.location.href = "/backstageApi/aboutUs/companyDynamics/preEdit?id=";
}


$(function() {
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", PC.initOptions());
    PC.table = jqGrid.init();

});