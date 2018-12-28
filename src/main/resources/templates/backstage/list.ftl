<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap-datetimepicker.min.css" />
    <link href="/static/css/plugins/select2/select2.min.css" rel="stylesheet">
    <link href="/static/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">
</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>EHS申请表</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/main">Home</a>
                    </li>
                    <li class="active">
                        <strong>EHS申请表</strong>
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
                                    <div class="form-group" id="dataRange">
                                        <label>涉及人员</label>
                                        <input type="text" class="form-control" id="accidentMan" style="width: 150px;">
                                        &nbsp&nbsp&nbsp
                                        <label>地点</label>
                                        <select class="form-control" id="address">
                                            <option value="" >---请选择---</option>
                                            <option value="CZ" >常州</option>
                                            <option value="CQ">重庆</option>
                                        </select>
                                        &nbsp&nbsp&nbsp
                                        <label>部门</label>
                                        <select class="form-control" id="dept">
                                            <option value="" >---请选择---</option>
                                            <#--<#list depts as dept>-->
                                                <#--<option value="${dept.dept}">${dept.dept}</option>-->
                                            <#--</#list>-->
                                        </select>
                                        &nbsp&nbsp&nbsp
                                        <label>事故种类</label>
                                        <select class="form-control" id="accidentType">
                                            <option value="">---请选择---</option>
                                             <#list accidentTypes as accidentType>
                                                <option value="${accidentType.id}">${accidentType.name}</option>
                                             </#list>
                                        </select>
                                        &nbsp&nbsp&nbsp
                                        <label>时间段</label>
                                        <div class="input-daterange input-group" id="datepicker">
                                            <input type="text" class="input-sm form-control" name="startDate"  id="startDate"/>
                                            <span class="input-group-addon">to</span>
                                            <input type="text" class="input-sm form-control" name="startDate"  id="endDate"/>
                                        </div>
                                    </div>

                                    &nbsp&nbsp&nbsp

                                    <button class="btn btn-success"  id="search" type="button" onclick="Backstage.search()">搜索</button>&nbsp
                                    <button class="btn btn-success" type="button" onclick="Backstage.resetSearch()">重置</button>&nbsp
                                    <#--<button class="btn btn-primary" type="button" onclick="Backstage.create()">新增</button>-->
                                    <button class="control-auth btn btn-danger" data-auth="backstage_export" type="button" onclick="Backstage.export()">导出</button>
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
        <#include "/templates/layout/footer.ftl">
    </div>
</div>


<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<#--新增弹框-->
<div class="modal fade" id="acceptModal" tabindex="-1"  role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalTitle">分配事故等级</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="accept-form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">事故等级</label>
                        <div class="col-sm-10">
                            <input id="ehsId" type="hidden" name="ehsId"/>
                            <select class="form-control" id="accidentLevel" name="accidentLevel">
                                <option value="">---请选择---</option>
                                    <#list accidentLevels as accidentLevel>
                                        <option value="${accidentLevel.id}">${accidentLevel.name}</option>
                                    </#list>
                            </select>
                        </div>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-sm btn-primary" onclick="Backstage.accept()">确定</button>
                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script src="/static/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/static/js/plugins/select2/select2.full.min.js"></script>
<script src="/static/modular/backstage/backstage.js"></script>
<script src="/static/js/plugins/jsKnob/jquery.knob.js"></script>
<script src="/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="/static/js/plugins/cropper/cropper.min.js"></script>
<script src="/static/js/plugins/daterangepicker/daterangepicker.js"></script>
<script src="/static/js/plugins/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        $("#address").change(function () {
            $.ajax({
                url:"/employee/address?address="+$("#address").val(),
                type: 'GET',
                contentType: "application/json",
                success:function (r) {
                    var depts = r.obj;
                    $("#dept").empty();
                    var option = "";
                    option += "<option  value=''>"+"---请选择---"+"</option>";
                    for(var i=0;i<depts.length;i++){
                        option += "<option  value='"+depts[i].dept+"'>"+depts[i].dept+"</option>";
                    }
                    $("#dept").append(option);
                }
            });
        });
        $('#dataRange .input-daterange').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true
        });
    });
</script>
</body>
</html>
