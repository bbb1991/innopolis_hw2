<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="fragment/header.jsp" %>
<body>
<div class="container">
    <h1 class="text-center">Books list</h1>

    <c:if test="${not empty books}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Created Date</th>
                <th>Content</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="book" items="${books}">
                <tr class='clickable-row' data-href='${pageContext.request.contextPath}/book/${book.id}'>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.author.username}</td>
                    <td>${book.date}</td>
                    <td>${book.content}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <br>
    </c:if>
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
