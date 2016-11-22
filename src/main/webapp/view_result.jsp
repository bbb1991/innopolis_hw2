<%@ page import="dbService.dataSets.BookDataSet" %><%--
  Created by IntelliJ IDEA.
  User: bbb1991
  Date: 11/23/16
  Time: 3:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search result</title>
</head>
<body>
<%
    BookDataSet bookDataSet = (BookDataSet) request.getAttribute("book");
    String title = "N/A";
    String content = "N/A";
    String author = "N/A";

    if (bookDataSet != null) {
        title = bookDataSet.getTitle();
        content = bookDataSet.getContent();
        author = bookDataSet.getAuthor();
    }
%>

<p>Title: <%=title%></p>
<p>Author: <%=author%></p>
<p>Content: <%=content%></p>

</body>
</html>
