<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%--<tiles:insertDefinition name="base"/>--%>
<html>
<tiles:insertAttribute name="header"/>
<body>

<div class="container">
    <tiles:insertAttribute name="navbar"/>
    <tiles:insertAttribute name="content"/>
</div>
</body>
</html>