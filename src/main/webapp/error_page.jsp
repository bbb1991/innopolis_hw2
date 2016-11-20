<%--
  Created by IntelliJ IDEA.
  User: bbb1991
  Date: 11/21/16
  Time: 1:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error!</title>
</head>
<body>
<% String errorMsg = (String) request.getAttribute("error"); %>
<h1>Error!</h1>
<p>Error is: <%=errorMsg%></p>
</body>
</html>
