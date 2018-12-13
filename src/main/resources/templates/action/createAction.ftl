<!DOCTYPE html>
<html>

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap-datetimepicker.min.css" />
    <link href="/static/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="/static/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
    <link href="/static/css/plugins/select2/select2.min.css" rel="stylesheet">
    <link href="/static/css/plugins/dropzone/dropzone.css" rel="stylesheet">
    <link href="/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="/static/css/plugins/clockpicker/clockpicker.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">
    <link href="/static/css/plugins/ladda/ladda-themeless.min.css" rel="stylesheet">

</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>创建行动</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/main">Home</a>
                    </li>
                    <li>
                        <a href="/backstage/list">EHS申请表</a>
                    </li>
                    <li class="active">
                        <strong>创建行动</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>

        <div class="wrapper wrapper-content">
            <div class="row">
                <div class="col-lg-12">
                    <form class="form-horizontal"  id="create-form" enctype="multipart/form-data" method="post" >
                        <div class="form-group">
                            <label class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10">
                                <textarea id="descriptive" name="descriptive" placeholder="请填入" style="width: 100%;height: 100px"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">责任人</label>
                            <div class="col-sm-4">
                                <input id="responsibleMan" type="text" class="form-control" name="responsibleMan">
                                <input id="ehsId" type="hidden" class="form-control" name="ehsId" value="${ehsId}">
                                <input id="uuid" type="hidden" class="form-control" name="uuid" value="${uuid}">
                            </div>
                            <label class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-4">
                                <#--<input id="email" type="text" class="form-control" name="email">-->
                                    <#--<select class="form-control" name="email" id="email">-->
                                    <select class="select2_demo_1 form-control" name="email" id="email">
                                        <#--<option value="">---请选择---</option>-->
                                    </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">地址</label>
                            <div class="col-sm-4">
                                <select class="form-control" name="address" id="address">
                                    <option value="CZ" selected>常州</option>
                                    <option value="CQ">重庆</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">责任人所在部门</label>
                            <div class="col-sm-4">
                                <select class="form-control" name="responsibleDept" id="responsibleDept">
                                   <#list depts as dept>
                                       <option value="${dept.dept}">${dept.dept}</option>
                                   </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">责任主管</label>
                            <div class="col-sm-4">
                                <input id="responsibleDirector" type="text" class="form-control" name="responsibleDirector">
                            </div>
                            <label class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-4">
                            <#--<input id="email" type="text" class="form-control" name="email">-->
                            <#--<select class="form-control" name="email" id="email">-->
                                <select class="select2_demo_1 form-control" name="directorEmail" id="directorEmail">
                                <#--<option value="">---请选择---</option>-->
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">关闭时间</label>
                            <div class="col-sm-5">
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="closeDate" name="closeDate" type="text" class="form-control" value="${today}">
                                </div>
                            </div>
                            <div class="col-sm-5">
                                <div class="input-group clockpicker" data-autoclose="true">
                                    <input type="text" class="form-control" placeholder="xx:xx" id="closeTime" name="closeTime">
                                    <span class="input-group-addon">
                                    <span class="fa fa-clock-o"></span>
                                </span>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="col-sm-10 col-sm-offset-2">
                        <form action="#" class="dropzone " id="dropzoneForm">
                            <div class="fallback">
                                <input name="subImgs" type="file"  multiple />
                            </div>
                        </form>
                        <br>
                        <div class="">
                            <button type="button" class="btn btn-sm btn-primary ladda-button" data-style="slide-left" onclick="Action.insert(this)">确  定</button>
                        <#--<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>-->
                        </div>
                    </div>


                </div>
            </div>
        </div>
    <#include "/templates/layout/footer.ftl">
    </div>
</div>

a
<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<script src="/static/js/plugins/ladda/spin.min.js"></script>
<script src="/static/js/plugins/ladda/ladda.min.js"></script>
<script src="/static/js/plugins/ladda/ladda.jquery.min.js"></script>
<script src="/static/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/static/modular/action/action.js"></script>
<script src="/static/js/plugins/select2/select2.full.min.js"></script>
<script src="/static/js/plugins/dropzone/dropzone.js"></script>
<script src="/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="/static/js/plugins/clockpicker/clockpicker.js"></script>

<script type="text/javascript">
    Dropzone.autoDiscover = false;
    var myDropzone ;
    $("#dropzoneForm").dropzone({
        url: "/action/uploadFile?uuid=${uuid}", //必须填写
        method: "post",  //也可用put
        paramName: "files", //默认为file
        maxFiles: 5,//一次性上传的文件数量上限
        maxFilesize: 50, //MB
        acceptedFiles: ".jpg,.png,.jpeg,.doc,.docx,.pdf", //上传的类型
        parallelUploads: 3,
        addRemoveLinks: true,
        uploadMultiple:true,//是否在一个请求中发送多个文件
        dictMaxFilesExceeded: "您最多只能上传5个文件！",
        dictResponseError: '文件上传失败!',
        dictRemoveFile:'移除文件',
        dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.jpg,*.png,*.jpeg",
        dictFallbackMessage: "浏览器不受支持",
        dictFileTooBig: "文件过大上传文件最大支持.",
        dictDefaultMessage: "<strong>点击此处上传图片 上传图片最大不能超过1M</strong>",
        init: function () {
            myDropzone=this;
            this.on("addedfile", function (file) {
                //上传时触发的方法
            });
            this.on("queuecomplete", function (file) {
                //上传完成后触发的方法
            });
            this.on("removedfile", function (file) {
                //删除文件时触发的方法
                // alert(file.name);
                $.get("/action/deleteFile?imgSourceName=" + file.name+"&uuid=${uuid}", function () {
                    success("图片删除成功");
                });
            });
        },
        success: function(file, data) {
            // console.log(file.name);
            // console.log(data.obj);
            $("#subImgs").val(data.obj);
        }

    });
    $(document).ready(function() {
        $('#closeDate').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $("#address").change(function () {
            $.ajax({
                url:"/action/address?address="+$("#address").val(),
                type: 'GET',
                contentType: "application/json",
                success:function (r) {
                    var depts = r.obj;
                    $("#responsibleDept").empty();
                    var option = "";
                    for(var i=0;i<depts.length;i++){
                        option += "<option  value='"+depts[i].dept+"'>"+depts[i].dept+"</option>";
                    }
                    $("#responsibleDept").append(option);
                }
            });
        });
        $("#responsibleMan").blur(function () {
            $.ajax({
                url:"/action/getEmail?name="+$("#responsibleMan").val(),
                type: 'GET',
                contentType: "application/json",
                success:function (r) {
                    var emails = r.obj;
                    $("#email").empty();
                    var option = "";
                    if(emails.length==1){
                        option += "<option  value='"+emails[0].email+"'>"+emails[0].email+"</option>";
                    }else{
                        for(var i=0;i<emails.length;i++){
                            option += "<option  value='"+emails[i].email+"'>"+emails[i].email+"</option>";
                        }
                    }
                    // console.log(option);
                    $("#email").append(option);
                }
            });
        });
        $("#responsibleDirector").blur(function () {
            $.ajax({
                url:"/action/getEmail?name="+$("#responsibleDirector").val(),
                type: 'GET',
                contentType: "application/json",
                success:function (r) {
                    var emails = r.obj;
                    $("#directorEmail").empty();
                    var option = "";
                    if(emails.length==1){
                        option += "<option  value='"+emails[0].email+"'>"+emails[0].email+"</option>";
                    }else{
                        for(var i=0;i<emails.length;i++){
                            option += "<option  value='"+emails[i].email+"'>"+emails[i].email+"</option>";
                        }
                    }
                    // console.log(option);
                    $("#directorEmail").append(option);
                }
            });
        });
    });
    $('.clockpicker').clockpicker();
    // $(".select2_demo_1").select2();
    // $(".select2_demo_3").select2({
    //     placeholder: "请选择",
    //     allowClear: true
    // });
</script>
</body>
</html>
