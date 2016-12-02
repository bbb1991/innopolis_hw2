<%--
  Created by IntelliJ IDEA.
  User: bbb1991
  Date: 12/3/16
  Time: 4:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <%@include file="fragments/header.jsp" %>
</head>
<body>
<%@include file="fragments/navbar.jsp" %>
<div class="container">
    <div class="text-center">
        <h2>Error occurred!</h2>

        <h3>${exception.statusCode}</h3>
        <p>${exception.statusText}</p>
    </div>
</div>
<h1></h1>
</body>
</html>
