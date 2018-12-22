var Email = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "email"
};

/**
 * jqGrid初始化参数
 */
Email.initOptions = function () {
    var options = {
        url : "/maintenance/email/grid",
        autowidth:true,
        colNames: ['id','smtpAuth','transportProrocol','sendCharset','smtpPort','isSsl','host','authName','authPassword','smtpTimeout','地址','isUsing','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'smtpAuth', index: 'smtpAuth', width: 150},
            {name: 'transportProrocol', index: 'transportProrocol', width: 150},
            {name: 'sendCharset', index: 'sendCharset', width: 150},
            {name: 'smtpPort', index: 'smtpPort', width: 150},
            {name: 'isSsl', index: 'isSsl', width: 150},
            {name: 'host', index: 'host', width: 150},
            {name: 'authName', index: 'authName', width: 150},
            {name: 'authPassword', index: 'authPassword', width: 150},
            {name: 'smtpTimeout', index: 'smtpTimeout', width: 150},

            {name: 'address', index: 'address', width: 50, sortable: false,formatter: function (cellValue, options, rowObject) {
                    if (cellValue == 'CZ') {
                        return "常州";
                    }else if(cellValue == 'CQ'){
                        return "重庆"
                    }else{
                        return "";
                    }

                }},
            {name: 'isUsing', index: 'isUsing', width: 150, sortable: false, formatter: function (cellValue, options, rowObject) {
                    var id = "'"+rowObject["id"]+"'";
                    var address = "'"+rowObject["address"]+"'";
                    var isUsing = rowObject["isUsing"];
                    var str = "";
                    if(0==isUsing){
                        str += '<div class="onoffswitch"><input type="checkbox" checked class="onoffswitch-checkbox" id="'+id+'" onclick="Email.clickSwitch('+id+','+isUsing+','+address+')" ><label class="onoffswitch-label" for="'+id+'"><span class="onoffswitch-inner"></span><span class="onoffswitch-switch"></span></label></div>'
                    }else{
                        str += '<div class="onoffswitch"><input type="checkbox" class="onoffswitch-checkbox" id="'+id+'" onclick="Email.clickSwitch('+id+','+isUsing+','+address+')"><label class="onoffswitch-label" for="'+id+'"><span class="onoffswitch-inner"></span><span class="onoffswitch-switch"></span></label></div>'
                    }
                    return str;
                }},
            // {name: 'isUsing', index: 'isUsing', width: 150, sortable: false, formatter: function (cellValue, options, rowObject) {
            //         if (cellValue == '0') {
            //             return "启用";
            //         }else if(cellValue == '1'){
            //             return "未启用"
            //         }else{
            //             return "";
            //         }return str;
            //     }},
            {name: 'operations', index: 'operations', width: 150, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="Email.delete(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="Email.modify(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="Email.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Email.search = function () {
    var searchParam = {};
    searchParam.name = $("#name").val();
    searchParam.address = $("#address").val();
    Email.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Email.resetSearch = function () {
    window.location.href = "/maintenance/email/list";
};

/**
 *新增
 */
Email.create = function () {
    $("#createModal").modal();
}
Email.insert = function () {
    var email = getFormJson($("#create-form"));
    $.ajax({
        url: "/maintenance/email/insert",
        type: 'POST',
        data: JSON.stringify(email),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#createModal").modal("hide");
                success("保存成功");
                Email.search();
                $("#create-form")[0].reset();
            }
        }
    })
}

/**
 *编辑
 */
Email.modify = function (id) {
    $.ajax({
        url: "/maintenance/email/get?id=" + id,
        type: 'GET',
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                var email = r.obj;
                var form = $("#modify-form");
                form.find("input[name='smtpAuth']").val(email.smtpAuth);
                form.find("input[name='id']").val(email.id);
                form.find("input[name='transportProrocol']").val(email.transportProrocol);
                form.find("input[name='sendCharset']").val(email.sendCharset);
                form.find("input[name='smtpPort']").val(email.smtpPort);
                form.find("input[name='isSsl']").val(email.isSsl);
                form.find("input[name='host']").val(email.host);
                form.find("input[name='authName']").val(email.authName);
                form.find("input[name='authPassword']").val(email.authPassword);
                form.find("input[name='smtpTimeout']").val(email.smtpTimeout);
                form.find("select[name='address']").val(email.address);
                $("#modifyModal").modal();
            }
        }
    })
    $("#modifyModal").modal();
}
Email.update = function () {
    var email = getFormJson($("#modify-form"));
    $.ajax({
        url: "/maintenance/email/update",
        type: 'POST',
        data: JSON.stringify(email),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#modifyModal").modal("hide");
                success("编辑成功");
                Email.search();
                $("#modify-form")[0].reset();
            }
        }
    })
}
//选择发件邮箱
Email.chooseEmail = function (address) {
    $("#emailAddress").val(address);
    $.ajax({
        url: "/maintenance/email/chooseEmail?address="+address,
        type: 'GET',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#radio").empty();
                var radios = r.obj;
                var radio = "";
                for(var i=0;i<radios.length;i++){
                    if(radios[i].isUsing==0){
                        radio += '<input id="'+radios[i].authName+'" type="radio" name="radio" value="'+radios[i].authName+'" checked><label for="'+radios[i].authName+'">'+radios[i].authName+'</label><br>';
                    }else{
                        radio += '<input id="'+radios[i].authName+'" type="radio" name="radio" value="'+radios[i].authName+'" ><label for="'+radios[i].authName+'">'+radios[i].authName+'</label><br>';
                    }
                }
                $("#radio").append(radio);
                    $("#addressModal").modal();

            }
        }
    })
}

Email.changeEmail = function () {
    var addressForm = getFormJson($("#address-form"));
    $.ajax({
        url: "/maintenance/email/changeEmail",
        type: 'POST',
        data:JSON.stringify(addressForm),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#addressModal").modal("hide");
                success("切换成功");
                Email.search();
                $("#address-form")[0].reset();
            }
        }
    })
}


Email.clickSwitch = function (id,isUsing,address) {
    $.ajax({
        url: "/maintenance/email/clickSwitch",
        type: 'POST',
        data:JSON.stringify({
            id:id,
            isUsing:isUsing,
            address:address
        }),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                if(r.obj){
                    success("切换成功");
                    Email.search();
                }else {
                    error("切换失败，一个地区不能同时打开多个发件邮箱")
                    Email.search();
                }

            }
        }
    })
}


/**
 * 删除
 *
 * @param id    userId
 */
Email.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/maintenance/email/delete?id=" + id, function () {
            success("成功删除");
            Email.search();
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

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Email.initOptions());
    Email.table = jqGrid.init();

});