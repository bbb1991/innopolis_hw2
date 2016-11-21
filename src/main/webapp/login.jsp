<%--
  Created by IntelliJ IDEA.
  User: bbb1991
  Date: 11/19/16
  Time: 10:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login page</h1>
<form method="post" action="${pageContext.request.contextPath}/login">

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" placeholder="j_smith" required><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>
    <input type="submit" value="Submit" />

</form>
</body>
</html>
