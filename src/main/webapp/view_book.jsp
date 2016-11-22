<%@ page import="dbService.dataSets.BookDataSet" %><%--
  Created by IntelliJ IDEA.
  User: bbb1991
  Date: 11/23/16
  Time: 1:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View book</title>
</head>
<body>
<h1>Find/view book</h1>
<form action="${pageContext.request.contextPath}/view_book" method="post">
    <label for="id">ID:</label>
    <input type="text" placeholder="583496956d23b55095a864c1" id="id" name="id"><br>
    <label for="title">Title:</label>
    <input type="text" placeholder="Title" name="title" id="title" disabled><br>
    <label for="author">Author:</label>
    <input type="text" id="author" name="author" placeholder="j_smith" disabled>

    <input type="submit" name="Submit!">
</form>


</body>
</html>
