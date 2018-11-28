<!DOCTYPE html>
<html>

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap-datetimepicker.min.css" />
    <link href="/static/css/plugins/select2/select2.min.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">
</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>QAD需求内容</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/main">Home</a>
                    </li>
                    <li class="active">
                        <strong>列表</strong>
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
                                        <label>任务</label>
                                        <input type="text" class="form-control" id="task" style="width: 150px;">
                                    </div>
                                    <#--<div class="form-group">-->
                                        <#--<label>类型</label>-->
                                            <#--<select class="form-control" name="demandType" id="demandType">-->
                                                <#--<option value="" selected>请选择</option>-->
                                                <#--<option value="0">QAD需求</option>-->
                                                <#--<option value="1">OA需求</option>-->
                                            <#--</select>-->
                                    <#--</div>-->
                                    <div class="form-group">
                                        <label>提出部门</label>
                                            <select class="select2_demo_1 form-control"  name="deptmentId" id="deptmentId">
                                                <option value="" selected>请选择</option>
                                                <#list deptments as deptment>
                                                     <option value=${deptment.id}>${deptment.deptName}</option>
                                                </#list>
                                            </select>
                                    </div>

                                    <div class="form-group">
                                        <label>提出人</label>
                                        <select class="select2_demo_1 form-control"  name="userId"  id="userId">
                                            <option value="" selected>请选择</option>
                                                <#list users as user>
                                                    <option value=${user.id}>${user.nickname}</option>
                                                </#list>
                                        </select>
                                    </div>

                                    <button class="btn btn-success"  id="search" type="button" onclick="Oa.search()">搜索</button>
                                    <button class="btn btn-success" type="button" onclick="Oa.resetSearch()">重置</button>
                                    <#--<button class="btn btn-primary" onclick="Qad.create()">新增</button>-->

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
<script src="/static/modular/oa/oa.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        $(".select2_demo_1").select2();
    });
</script>
</body>
</html>
