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
            <#list imgUrls as imgUrl>
                <a href="/picture/${imgUrl}" target="_blank"><img src="/picture/${imgUrl}" style="width: 150px;height: 150px" ></a>
            </#list>

            <#--<a href="/picture/2.png" target="_blank"><img src="/picture/2.png" style="width: 150px;height: 150px" ></a>-->
        </div>
    <#include "/templates/layout/footer.ftl">
    </div>
</div>

<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<#--<script src="/static/modular/manager/photos.js"></script>-->

<script type="text/javascript">
    $(document).ready(function(){

    });
</script>
</body>
</html>
