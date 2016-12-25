<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

    <div class="comments">
        <h2 class="text-center">Comments:</h2>
        <c:choose>
            <c:when test="${not empty comments}">
                <c:forEach items="${comments}" var="comment">
                    <h3>${comment.title}</h3>
                    <p>By user: ${comment.user.username} on: ${comment.date}</p>
                    <p>Rating is: ${comment.rating}</p>
                    <p>${comment.body}</p>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h3>No comments available. Be first!</h3>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="form-group">
        <h2>Add new comment</h2>
        <form:form modelAttribute="comment" action="/book/${book.id}/add_comment" method="post">
            <label for="title">Comment title:</label>
            <spring:bind path="title">
                <form:input path="title" id="title" class="form-control"/>
            </spring:bind>

            <label for="body">Comment body:</label>
            <spring:bind path="body">
                <form:textarea path="body" id="body" class="form-control"/>
            </spring:bind>

            <label for="rating">Rating:</label>
            <spring:bind path="rating">
                <form:input path="rating" id="rating" class="form-control"/>
            </spring:bind>

            <input type="submit" value="Add comment">
        </form:form>
    </div>
</div>
