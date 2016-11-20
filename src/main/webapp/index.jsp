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
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/js/script.js" type="application/javascript"></script>
</head>
<body>

<h1>It's alive!</h1>
<%
    String username = (String) request.getAttribute("username");
    String message;
    if (username == null) {
        message = "You aren't logged in";
    } else {
        message = "Welcome " + username;
    }
%>
<p><%=message%></p>
</body>
</html>
