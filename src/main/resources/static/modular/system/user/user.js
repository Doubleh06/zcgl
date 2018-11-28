var User = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "user"
};

/**
 * jqGrid初始化参数
 */
User.initOptions = function () {
    var options = {
        url : "/user/grid",
        autowidth:true,
        colNames: ['编号', '用户名', '昵称', '操作'],//
        colModel: [
            {name: 'id', index: 'id', width: 60, sorttype: "int"},
            {name: 'username', index: 'username', width: 90},
            {name: 'nickname', index: 'nickname', width: 100, sortable: false},

            {name: 'operations', index: 'operations', width: 100, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = rowObject["id"];
                var str = "";
                if (id != 1) {
                    str += '<input type="button" class="control-auth btn btn-sm btn-primary" data-auth="user_permission" value="授权" onclick="User.allocateRoleModal(' + id + ')"/>&nbsp;';
                    str += '<input type="button" class="control-auth btn btn-sm btn-info" data-auth="user_edit" value="编辑" onclick="User.edit(' + id + ')"/>&nbsp;';
                    str += '<input type="button" class="control-auth btn btn-sm btn-danger" data-auth="user_delete" value="删除" onclick="User.delete(' + id + ')"/>';
                }
                return str;
            }}
        ],
        gridComplete: function () {
            refreshPermission(User.domain);
        }
    };
    return options;
};

/**
 * 根据关键词搜索
 */
User.search = function () {
    var searchParam = {};
    searchParam.username = $("#username").val();
    searchParam.nickname = $("#nickname").val();
    User.table.reload(searchParam);
};

/**
 * 重置搜索
 */
User.resetSearch = function () {
    $("#username").val("");
    $("#nickname").val("");
    User.search();
};

/**
 * 新增弹框
 */
User.create = function () {
    $("#createModal").modal();
};

/**
 * 保存用户
 */
User.insert = function () {
    var user = getFormJson($("#create-form"));
    console.log(user)
    $.ajax({
        url: "/user/insert",
        type: 'POST',
        data: JSON.stringify(user),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#createModal").modal("hide");
                success("保存成功");
                User.search();
                $("#create-form")[0].reset();
            }
        }
    })
};

/**
 * 编辑弹框
 */
User.edit = function (id) {
    $("#selectCompany2").hide();
    $.ajax({
        url: "/user/getJoinUserCompany?id=" + id,
        type: 'GET',
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                var user = r.obj;
                var form = $("#edit-form");
                form.find("input[name='username']").val(user.username);
                form.find("input[name='nickname']").val(user.nickname);
                form.find("input[name='id']").val(user.id);
                if(null==user.company||""==user.company){
                    form.find("input[id='radio3']").prop("checked",true);
                }else{
                    $("#selectCompany2").show();
                    form.find("input[id='radio4']").prop("checked",true);
                    var selected = form.find("select[name='company2']").find("option[value='"+user.company+"']").html();
                    form.find("select[name='company2']").val(user.company);
                    $(".chosen-single").find("span").html(selected);
                    // $("#selectCompany2").trigger("chosen:updated");
                    }
                $("#editModal").modal();
            }
        }
    })
};

/**
 * 更新用户
 */
User.update = function () {
    var form = $("#edit-form");
    var user = {};
    user.id = form.find("input[name='id']").val()
    user.username =  form.find("input[name='username']").val();
    user.nickname =  form.find("input[name='nickname']").val();
    user.permission =  form.find("input[name='permission2']:checked").val();
    if(user.permission=="option3"){
        user.company = null;
    }else{
        user.company =  form.find("select[name='company2']").val();
    }
    // console.log(user);
    $.ajax({
        url: "/user/update",
        type: 'POST',
        data: JSON.stringify(user),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#editModal").modal("hide");
                User.search();
                success("保存成功");
            }
        }
    })
};

/**
 * 删除用户
 *
 * @param id    userId
 */
User.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/user/delete?id=" + id, function () {
            success("成功删除");
            User.search();
        });
    })
};

User.allocateRoleModal = function allocateRoleModal(userId) {
    $("#userId").val(userId);
    var roles = [];
    $.ajax({
        url: "/user/role?userId=" + userId,
        type: 'GET',
        async: false,
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                roles = r.obj;
            }
        }
    });
    var str = '';
    for (var i = 0; i < roles.length; i++) {
        var role = roles[i];
        str += '<label><input type="checkbox" name="role" value="' + role.id + '" ';
        if (role.selected) {
            str += 'checked';
        }
        str += '>' + role.name + '</label><br>';
    }
    $("#role_container").html(str);
    $("#allocateRoleModal").modal();
};


/**
 * 授权
 */
User.saveRoles = function () {
    var roleIds = [];
    $.each($("#role_container").find("input[name='role']:checked"), function (index, data) {
        roleIds.push(data.value);
    });
    var userId = $("#userId").val();
    $.ajax({
        url: "/user/role/save",
        type: 'POST',
        data: JSON.stringify({
            userId: userId,
            roleIds: roleIds
        }),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#allocateRoleModal").modal('hide');
                success("保存成功");
            }
        }
    });
};


$(function() {
    $("input[type=radio][name=permission]").change(function(){
        if(this.value == "option2"){
            $("#selectCompany").show();
        }else{
            $("#selectCompany").hide();
        }
    });
    $("input[type=radio][name=permission2]").change(function(){
        if(this.value == "option4"){
            $("#selectCompany2").show();
        }else{
            $("#selectCompany2").hide();
        }
    });
    $('.chosen-select').chosen({width: "100%"});
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", User.initOptions());
    User.table = jqGrid.init();
});