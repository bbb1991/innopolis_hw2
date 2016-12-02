<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--Главная страница со списком книг--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="fragments/header.jsp" %>
</head>
<body>

<%@include file="fragments/navbar.jsp" %>

<div class="container">
    <div class="text-center">

        <h1>E-library</h1>

        <c:choose>
            <c:when test="${not empty username}">
                <p>Welcome, ${username}</p>
            </c:when>
            <c:otherwise>
                <p>Welcome, guest! Please, log in</p>
            </c:otherwise>
        </c:choose>

    </div>

    <c:if test="${not empty pageContext.request.userPrincipal}">
        <p>213213213123</p>
    </c:if>

    <c:if test="${not empty books}">
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

            <c:forEach var="book" items="${books}">
                <tr class='clickable-row' data-href='view_result/${book.id}'>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.content}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </c:if>
    <br>
    <p><b>${status}</b></p>
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
