<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=utf-8" %>

<div>
    <c:if test="${not empty book}">
        <h1 class="text-center">Тема: ${book.title}</h1>
        <p><b>Дата создания:</b> <fmt:formatDate pattern="dd MMMM yyyy HH:mm" value="${book.date}"/></p>
        <p><b>Автор: </b>
            <a href="${pageContext.request.contextPath}/books?by_author=${book.author.username}">${book.author.username}</a>
        </p>
        <p><b>Содержание:</b></p>
        <p>${book.content}</p>
    </c:if>

    <br>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal.username" var="username"/>
        <c:if test="${book.author.username == username}">
            <p><a href="${pageContext.request.contextPath}/edit_book/${book.id}">Изменить книгу</a></p>
            <p><a href="${pageContext.request.contextPath}/delete_book/${book.id}">Удалить книгу</a></p>
        </c:if>
    </sec:authorize>
</div>
