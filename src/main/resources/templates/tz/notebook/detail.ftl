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
                        <div class="form-group">

                            <label class="col-sm-2 control-label">显示器</label>
                            <div class="col-sm-4">
                                <input id="xsq" class="form-control" name="xsq" value="${notebookDetail.xsq}">
                            </div>
                            <label class="col-sm-2 control-label">操作系统</label>
                            <div class="col-sm-4">
                                <input id="czxt" class="form-control" name="czxt" value="${notebookDetail.czxt}">
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-2 control-label">数量</label>
                            <div class="col-sm-4">
                                <input id="num" class="form-control" name="num" value="${notebookDetail.num}">
                            </div>
                            <label class="col-sm-2 control-label">单位</label>
                            <div class="col-sm-4">
                                <input id="dw" class="form-control" name="dw" value="${notebookDetail.dw}">
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-2 control-label">使用日期</label>
                            <div class="col-sm-4">
                                <input id="num" class="form-control" name="num" value="${notebookDetail.num}">
                            </div>

                        </div>
                        <div class="form-group">

                            <label class="col-sm-2 control-label">一级部门</label>
                            <div class="col-sm-4">
                                <input id="yjbm" class="form-control" name="yjbm" value="${notebookDetail.yjbm}">
                            </div>
                            <label class="col-sm-2 control-label">二级部门</label>
                            <div class="col-sm-4">
                                <input id="ejbm" class="form-control" name="ejbm" value="${notebookDetail.ejbm}">
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-2 control-label">使用人</label>
                            <div class="col-sm-4">
                                <input id="syr" class="form-control" name="syr" value="${notebookDetail.syr}">
                            </div>
                            <label class="col-sm-2 control-label">购买价格</label>
                            <div class="col-sm-4">
                                <input id="gmjg" class="form-control" name="gmjg" value="${notebookDetail.gmjg}">
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-2 control-label">供应商</label>
                            <div class="col-sm-4">
                                <input id="gys" class="form-control" name="gys" value="${notebookDetail.gys}">
                            </div>
                            <label class="col-sm-2 control-label">使用年限</label>
                            <div class="col-sm-4">
                                <input id="synx" class="form-control" name="synx" value="${notebookDetail.synx}">
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-2 control-label">现在残值</label>
                            <div class="col-sm-4">
                                <input id="xzcz" class="form-control" name="xzcz" value="${notebookDetail.xzcz}">
                            </div>
                            <label class="col-sm-2 control-label">维修记录</label>
                            <div class="col-sm-4">
                                <input id="wxjl" class="form-control" name="wxjl" value="${notebookDetail.wxjl}">
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-2 control-label">备注</label>
                            <div class="col-sm-10">
                                <textarea id="memo" name="memo" placeholder="" style="width: 100%;height: 100px"></textarea>
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
