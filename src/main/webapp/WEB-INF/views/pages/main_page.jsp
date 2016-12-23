<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

            <c:forEach var="book" items="${books}">
                <tr class='clickable-row' data-href='${pageContext.request.contextPath}/book/${book.id}'>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.author.username}</td>
                    <td><fmt:formatDate pattern="dd MMMM yyyy HH:mm" value="${book.date}"/></td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <br>
    </c:if>
</div>
