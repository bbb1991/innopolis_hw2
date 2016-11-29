<%@ page import="dbService.dataSets.UserDataSet" %>
<%@ page import="java.util.List" %>
<%@ page import="dbService.dataSets.BookDataSet" %>

<%--Главная страница со списком книг--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index page</title>
    <%@include file="header.jsp" %>
</head>
<body>

<%@include file="navbar.jsp" %>

<div class="container">
    <div class="text-center">

        <h1>E-library</h1>

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

    </div>

    <table class="table table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Content</th>
        </tr>
        </thead>
        <tbody>


        <% List<BookDataSet> books = (List<BookDataSet>) request.getAttribute("books");
            if (books != null) {
                for (BookDataSet book : books) {
        %>
        <tr class='clickable-row' data-href='view_result?id=<%=book.getId()%>'>
            <td><%=book.getId()%>
            </td>
            <td><%=book.getTitle()%>
            </td>
            <td><%=book.getAuthor()%>
            </td>
            <td><%=book.getContent()%>
            </td>
        </tr>
        <%
                }
            }%>
        </tbody>
    </table>

    <%
        String status = (String) request.getSession().getAttribute("status");
        if (status == null) {
            status = "Ready";
        }
    %>
    <br>
    <p><b>Status: <%=status%>
    </b></p>
</div>
<script>
    jQuery(document).ready(function ($) {
        $(".clickable-row").click(function () {
            window.document.location = $(this).data("href");
        });
    });
</script>
</body>
</html>
