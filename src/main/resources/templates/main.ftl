<!DOCTYPE html>
<html>

<head>
<#include "/templates/layout/meta.ftl">
</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

    <#include "/templates/layout/footer.ftl">
    </div>

<#include "/templates/layout/commonjs.ftl">
</div>
<script>
    $(function () {
        var permissions = '${permissions}';
        console.log(permissions);
        window.localStorage.setItem("permissions", permissions);
    });
</script>
</body>
</html>
