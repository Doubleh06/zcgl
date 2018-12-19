<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap-datetimepicker.min.css" />
    <link href="/static/css/plugins/select2/select2.min.css" rel="stylesheet">
    <link href="/static/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">
</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>Action列表</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/main">Home</a>
                    </li>
                    <#--<li>-->
                        <#--<a href="/actionWithoutEhsId/seeAction">EHS申请表</a>-->
                    <#--</li>-->
                    <li class="active">
                        <strong>Action列表</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>

        <div class="wrapper wrapper-content">
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox ">
                        <div class="ibox-content">
                            <div class="bar search-bar">
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label>负责人</label>
                                        <input type="text" class="form-control" id="responsibleMan" style="width: 150px;">
                                        <#--<input type="hidden" class="form-control" id="ehsId" value="${ehsId}">-->
                                        &nbsp&nbsp&nbsp
                                        <label>地点</label>
                                        <select class="form-control" id="address">
                                            <option value="">---请选择---</option>
                                            <option value="CZ" >常州</option>
                                            <option value="CQ">重庆</option>
                                        </select>
                                        &nbsp&nbsp&nbsp
                                        <label>部门</label>
                                        <select class="form-control" id="responsibleDept">
                                            <option value="" >---请选择---</option>
                                        </select>
                                        &nbsp&nbsp&nbsp
                                        <label>负责主管</label>
                                        <input type="text" class="form-control" id="responsibleDirector" style="width: 150px;">
                                    </div>
                                    &nbsp&nbsp&nbsp

                                    <button class="btn btn-success"  id="search" type="button" onclick="ActionWithoutEhsId.search()">搜索</button>&nbsp
                                    <button class="btn btn-success" type="button" onclick="ActionWithoutEhsId.resetSearch()">重置</button>&nbsp
                                    <button class="btn btn-danger" type="button" onclick="ActionWithoutEhsId.export()">导出</button>
                                    <button class="btn btn-primary" type="button" onclick="ActionWithoutEhsId.create()">新建</button>
                                    <#--<button class="btn btn-primary" onclick="Qad.create()">新增</button>-->
                                </div>
                            </div>
                            </div>
                            <div class="jqGrid_wrapper">
                            <#--jqgrid 表格栏-->
                                <table id="grid-table"></table>
                            <#--jqgrid 分页栏-->
                                <div id="grid-pager"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <#include "/templates/layout/footer.ftl">
    </div>
</div>


<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<script src="/static/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/static/js/plugins/select2/select2.full.min.js"></script>
<script src="/static/modular/actionWithoutEhsId/action.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        $("#address").change(function () {
            $.ajax({
                url:"/action/address?address="+$("#address").val(),
                type: 'GET',
                contentType: "application/json",
                success:function (r) {
                    var depts = r.obj;
                    $("#responsibleDept").empty();
                    var option = "";
                    option += "<option  value=''>"+"---请选择---"+"</option>";
                    for(var i=0;i<depts.length;i++){
                        option += "<option  value='"+depts[i].dept+"'>"+depts[i].dept+"</option>";
                    }
                    $("#responsibleDept").append(option);
                }
            });
        });
    });
</script>
</body>
</html>
