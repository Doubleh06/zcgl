var CompanyDynamics = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "companyDynamics"
};

/**
 * jqGrid初始化参数
 */
CompanyDynamics.initOptions = function () {
    var options = {
        url : "/backstageApi/aboutUs/companyDynamics/grid",
        autowidth:true,
        colNames: ['编号','标题', '内容','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'title', index: 'title', width: 80},
            {name: 'content', index: 'content', width: 200},
            {name: 'operations', index: 'operations', width:50 , sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                    str += '<input type="button" class="btn btn-sm btn-warning"   value="编  辑" onclick="CompanyDynamics.preEdit(' + id +')"/>&nbsp;';
                    str += '<input type="button" class="btn btn-sm btn-danger"   value="删  除" onclick="CompanyDynamics.delete(' + id +')"/>&nbsp;';

                return str;
            }}
        ]
        // ,
        // gridComplete: function () {
        //     refreshPermission(CompanyDynamics.domain);
        // }
    };
    return options;
};

/**
 * 根据关键词搜索
 */
CompanyDynamics.search = function () {
    var searchParam = {};
    searchParam.title = $("#title").val();
    searchParam.locales = $("#locales").val();
    CompanyDynamics.table.reload(searchParam);
};

/**
 * 重置搜索
 */
CompanyDynamics.resetSearch = function () {
    $("#title").val("");
    $("#locales").empty();
    $("#locales").append("<option value=\"zh-CN\" >中文</option> <option value=\"zh-TW\" >繁体</option> <option value=\"en-US\">English</option>");
    CompanyDynamics.search();
};



/**
 * 删除
 *
 * @param id    userId
 */
CompanyDynamics.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/backstageApi/aboutUs/companyDynamics/delete?id=" + id, function () {
            success("成功删除");
            CompanyDynamics.search();
        });
    })
};

/**
 * 编辑跳转
 *
 * @param id    userId
 */
CompanyDynamics.preEdit = function (id) {
    window.location.href = "/backstageApi/aboutUs/companyDynamics/preEdit?id=" + id;
};
/**
 * 新增
 */
CompanyDynamics.create = function(){
    window.location.href = "/backstageApi/aboutUs/companyDynamics/preEdit?id=";
}


$(function() {
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", CompanyDynamics.initOptions());
    CompanyDynamics.table = jqGrid.init();

});