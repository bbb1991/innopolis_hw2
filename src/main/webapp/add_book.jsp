<%--
  Created by IntelliJ IDEA.
  User: bbb1991
  Date: 11/22/16
  Time: 3:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new Book</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/add_book">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title" placeholder="Your awesome book title here">
    <label for="content">Content:</label>
    <textarea id="content" name="content"></textarea>
    <input type="submit" value="submit!">
</form>
</body>
</html>
