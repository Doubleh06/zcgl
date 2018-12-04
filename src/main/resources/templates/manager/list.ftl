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
                <h2>故障列表</h2>
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
                                        <label>涉及人员</label>
                                        <input type="text" class="form-control" id="accidentMan" style="width: 150px;">
                                        &nbsp&nbsp&nbsp
                                        <label>部门</label>
                                        <select class="form-control" id="dept">
                                            <option value="">---请选择---</option>
                                            <#list depts as dept>
                                                <option value="${dept.id}">${dept.deptName}</option>
                                            </#list>
                                        </select>
                                        &nbsp&nbsp&nbsp
                                        <label>事故种类</label>
                                        <select class="form-control" id="accidentType">
                                            <option value="">---请选择---</option>
                                             <#list accidentTypes as accidentType>
                                                <option value="${accidentType.id}">${accidentType.name}</option>
                                             </#list>
                                        </select>
                                    </div>
                                    &nbsp&nbsp&nbsp

                                    <button class="btn btn-success"  id="search" type="button" onclick="Manager.search()">搜索</button>&nbsp
                                    <button class="btn btn-success" type="button" onclick="Manager.resetSearch()">重置</button>&nbsp
                                    <#--<button class="btn btn-primary" type="button" onclick="Manager.create()">新增</button>-->
                                    <button class="btn btn-danger" type="button" onclick="Manager.export()">导出</button>
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

<#--&lt;#&ndash;导出弹框&ndash;&gt;-->
<#--<div class="modal fade" id="exportModal" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">-->
    <#--<div class="modal-dialog">-->
        <#--<div class="modal-content">-->
            <#--<div class="modal-header">-->
                <#--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                <#--<h4 class="modal-title" id="modalTitle">导出excel</h4>-->
            <#--</div>-->
            <#--<div class="modal-body">-->
                <#--<form class="form-horizontal" id="export-form">-->
                    <#--<div class="form-group">-->
                        <#--<label class="col-sm-3 control-label">ip</label>-->
                        <#--<div class="col-sm-9">-->
                            <#--<div class="checkbox checkbox-success">-->
                                <#--<input id="checkbox2" name="" type="checkbox" checked="">-->
                                <#--<label for="checkbox2">-->
                                    <#--Primary-->
                                <#--</label>-->
                            <#--</div>-->
                        <#--</div>-->
                    <#--</div>-->
                    <#--<div class="form-group">-->
                        <#--<label class="col-sm-3 control-label">端口</label>-->
                        <#--<div class="col-sm-9">-->
                            <#--<input type="text" class="form-control" name="port">-->
                        <#--</div>-->
                    <#--</div>-->
                    <#--<div class="form-group">-->
                        <#--<label class="col-sm-3 control-label">终端服务器ip</label>-->
                        <#--<div class="col-sm-9">-->
                            <#--<input type="text" class="form-control" name="terminalIp">-->
                        <#--</div>-->
                    <#--</div>-->
                    <#--<div class="form-group">-->
                        <#--<label class="col-sm-3 control-label">最大并发</label>-->
                        <#--<div class="col-sm-9">-->
                            <#--<input type="text" class="form-control" name="maxline">-->
                        <#--</div>-->
                    <#--</div>-->
                <#--</form>-->

            <#--</div>-->
            <#--<div class="modal-footer">-->
                <#--<button type="button" class="btn btn-sm btn-primary" onclick="manager.insert()">确定</button>-->
                <#--<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>-->
            <#--</div>-->
        <#--</div><!-- /.modal-content &ndash;&gt;-->
    <#--</div><!-- /.modal &ndash;&gt;-->
<#--</div>-->
<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<script src="/static/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/static/js/plugins/select2/select2.full.min.js"></script>
<script src="/static/modular/manager/manager.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        $(".select2_demo_1").select2();
    });
</script>
</body>
</html>
