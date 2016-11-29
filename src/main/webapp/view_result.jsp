<%@ page import="dbService.dataSets.BookDataSet" %>

<%-- Файл для просмотра книги --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search result</title>
    <%@include file="header.jsp" %>
</head>
<body>

<%@include file="navbar.jsp" %>

<div class="container">
    <%
        BookDataSet bookDataSet = (BookDataSet) request.getAttribute("book");
        if (bookDataSet != null) {
    %>

    <h1><%=bookDataSet.getTitle()%>
    </h1>
    <p>Author: <%=bookDataSet.getAuthor()%>
    </p>
    <p>Content: <%=bookDataSet.getContent()%>
    </p>

    <%
    } else {
    %>
    <h1>No data available</h1>
    <%
        }
    %>
</div>
</body>
</html>
