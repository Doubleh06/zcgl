<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/style.css" rel="stylesheet">
</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>笔记本台账</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/main">Home</a>
                    </li>
                    <li class="active">
                        <strong>笔记本详情</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>

        <div class="wrapper wrapper-content">
            <div class="row">
                <div class="col-lg-12">
                    <form class="form-horizontal"  id="detail-form" enctype="multipart/form-data" method="post" >
                        <div class="form-group">
                            <label class="col-sm-2 control-label">设备编码</label>
                            <div class="col-sm-10">
                                <input id="ssbm" class="form-control" name="ssbm" value="${notebookDetail.sbbm}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">规格型号</label>
                            <div class="col-sm-4">
                                <input id="ggxh"  class="form-control" name="ggxh" value="${notebookDetail.ggxh}">
                            </div>
                            <label class="col-sm-2 control-label">CPU</label>
                            <div class="col-sm-4">
                                <input id="ssbm" class="form-control" name="ssbm" value="${notebookDetail.cpu}">
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-2 control-label">内存</label>
                            <div class="col-sm-4">
                                <input id="nc" class="form-control" name="nc" value="${notebookDetail.nc}">
                            </div>
                            <label class="col-sm-2 control-label">硬盘</label>
                            <div class="col-sm-4">
                                <input id="yp" class="form-control" name="yp" value="${notebookDetail.yp}">
                            </div>
                        </div>

                    </form>
            </div>
        </div>
        <#include "/templates/layout/footer.ftl">
    </div>
</div>


<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">

<script src="/static/modular/tz/notebook/list.js"></script>

<script type="text/javascript">
    $(document).ready(function(){

    });
</script>
</body>
</html>
