<!DOCTYPE html>
<html>

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap-datetimepicker.min.css" />
    <link href="/static/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="/static/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
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
                <h2>需求明细</h2>
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
                    <form class="form-horizontal"  id="create-form">
                        <input type="text" id="id" hidden value="${demand.id}">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">需求类型</label>
                            <div class="col-sm-10">
                                <select class="form-control" name="demandType">
                                        <option value="" >请选择</option>
                                    <#if demand.demandType==0>
                                        <option value="0" selected >QAD需求</option>
                                    <#else >
                                         <option value="0"  >QAD需求</option>
                                    </#if>
                                    <#if demand.demandType==1>
                                        <option value="1" selected>OA需求</option>
                                    <#else>
                                        <option value="1" >OA需求</option>
                                    </#if>


                                </select>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-2 control-label">任务</label>
                            <div class="col-sm-10">
                                <textarea id="task" name="task" placeholder="请填入文章内容" style="width: 100%;height: 150px">${demand.task}</textarea>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">节约人力</label>
                            <div class="col-sm-10">
                                <textarea id="saveManpower" name="saveManpower" placeholder="请填入" style="width: 100%;height: 150px">${demand.saveManpower}</textarea>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">节约费用</label>
                            <div class="col-sm-10">
                                <textarea id="saveCost" name="saveCost" placeholder="请填入" style="width: 100%;height: 150px">${demand.saveCost}</textarea>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">风险防范及其他</label>
                            <div class="col-sm-10">
                                <textarea id="riskPrevention" name="riskPrevention" placeholder="请填入" style="width: 100%;height: 150px">${demand.riskPrevention}</textarea>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">会议纪要</label>
                            <div class="col-sm-10">
                                <textarea id="meetingMinutes" name="meetingMinutes" placeholder="请填入" style="width: 100%;height: 150px">${demand.meetingMinutes}</textarea>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注</label>
                            <div class="col-sm-10">
                                <input id="meno" type="text" class="form-control" name="meno" value="${demand.meno}">
                            </div>
                        </div>
                        <div class="col-sm-4 col-sm-offset-2">
                            <#if demand.files ??>
                            <button type="button" class="btn btn-sm btn-primary" onclick="QadEdit.download()">下载附件</button>
                            </#if>
                            <button type="button" class="btn btn-sm btn-primary" onclick="QadEdit.insert()" disabled>确定</button>
                            <#--<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>-->


                        </div>
                    </form>

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
<script src="/static/modular/qad/edit.js"></script>
<script src="/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/static/js/plugins/summernote/summernote.min.js"></script>
<script src="/static/js/plugins/select2/select2.full.min.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
