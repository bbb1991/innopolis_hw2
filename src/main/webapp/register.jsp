<%--
  Created by IntelliJ IDEA.
  User: bbb1991
  Date: 11/21/16
  Time: 1:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>

<h1>Register page</h1>


<form method="post" action="${pageContext.request.contextPath}/register">

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" placeholder="j_smith" required><br>
    <label for="password1">Password:</label>
    <input type="password" id="password1" name="password1" required><br>
    <label for="password2">Password:</label>
    <input type="password" id="password2" name="password2" required><br>
    <input type="submit" value="Submit"/>

</form>

</body>
</html>
