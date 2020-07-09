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
                        <strong>笔记本</strong>
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
                                        <#--<label>标题</label>-->
                                        <#--<input type="text" class="form-control" id="title" style="width: 150px;">-->
                                        <#--&nbsp&nbsp&nbsp-->
                                        <#--<label>语言</label>-->
                                        <#--<select class="form-control" id="locales">-->
                                            <#--<option value="zh-CN" >中文</option>-->
                                            <#--<option value="zh-TW" >繁体</option>-->
                                            <#--<option value="en-US">English</option>-->
                                        <#--</select>-->
                                    </div>

                                    &nbsp&nbsp&nbsp

                                    <button class="btn btn-success"  id="search" type="button" onclick="CompanyDynamics.search()">搜索</button>&nbsp
                                    <button class="btn btn-success" type="button" onclick="CompanyDynamics.resetSearch()">重置</button>&nbsp
                                    <button class="btn btn-primary" type="button" onclick="CompanyDynamics.create()">新增</button>
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

<script src="/static/modular/tz/notebook/list.js"></script>

<script type="text/javascript">
    $(document).ready(function(){

    });
</script>
</body>
</html>
