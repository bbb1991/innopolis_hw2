<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=utf-8"%>
<div>
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
                    <td>${user.date}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <br>
    </c:if>
    <script>
        jQuery(document).ready(function ($) {
            $(".clickable-row").click(function () {
                window.document.location = $(this).data("href");
            });
        });
    </script>
</div>
