<%@ page import="dbService.dataSets.UserDataSet" %>
<%--
  Created by IntelliJ IDEA.
  User: bbb1991
  Date: 11/18/16
  Time: 11:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
    <script src="${pageContext.request.contextPath}/js/script.js" type="application/javascript"></script>
</head>
<body>

<h1>Index page</h1>
<p><a href="${pageContext.request.contextPath}/login">Login</a></p>
<p><a href="${pageContext.request.contextPath}/register">Register</a></p>
<br>
<%
    UserDataSet userDataSet = ((UserDataSet) request.getSession().getAttribute("user"));
%>

<c:choose>
    <c:when test="${empty user}">
        I see! You don't have a name... well... Hello, no name
    </c:when>
    <c:otherwise>
        <%@ include file="info.jsp" %>
    </c:otherwise>
</c:choose>

<form method="post" action="${pageContext.request.contextPath}/logout">
    <input type="submit" value="Logout">
</form>
</body>
</html>
