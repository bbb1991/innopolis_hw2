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


<%
    UserDataSet userDataSet = (UserDataSet) request.getSession().getAttribute("user");
    if (userDataSet == null) {
        %>
<p>Welcome, guest!, Please log in!</p>
<%
    } else {
        %>
<p>Welcome, <%=userDataSet.getUsername()%>!</p>
<%
    }
%>
<p><a href="${pageContext.request.contextPath}/login">Login</a></p>
<p><a href="${pageContext.request.contextPath}/register">Register</a></p>
<p><a href="${pageContext.request.contextPath}/add_book">Add new Book</a></p>
<p><a href="${pageContext.request.contextPath}/view_book">Find/view book</a></p>
<br>

<%
    String status = (String) request.getSession().getAttribute("status");
    if (status == null) {
        status = "Ready";
    }
%>
<p><b>Status: <%=status%></b></p>
<form method="post" action="${pageContext.request.contextPath}/logout">
    <input type="submit" value="Logout">
</form>
</body>
</html>
