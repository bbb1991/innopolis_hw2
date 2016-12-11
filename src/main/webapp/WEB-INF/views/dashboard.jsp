<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@include file="fragment/header.jsp" %>
<body>
<div class="container">
    <h1 class="text-center">Список книг</h1>

    <c:if test="${not empty books}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th>Автор</th>
                <th>Дата создания</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="user" items="${books}">
                <tr class='clickable-row' data-href='${pageContext.request.contextPath}/book/${user.id}'>
                    <td>${user.id}</td>
                    <td>${user.title}</td>
                    <td>${user.author.username}</td>
                    <td><fmt:formatDate pattern="dd MMMM yyyy HH:mm" value="${user.date}"/></td>
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
