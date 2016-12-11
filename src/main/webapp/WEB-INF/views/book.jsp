<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@include file="fragment/header.jsp" %>
<body>
<div class="container">
    <c:if test="${not empty book}">
        <h1 class="text-center">Тема: ${book.title}</h1>
        <p>Дата создания: <fmt:formatDate pattern="dd MMMM yyyy HH:mm" value="${book.date}"/></p>
        <p>Автор: ${book.author.username}</p>
        <p>Содержание:</p>
        <p>${book.content}</p>
    </c:if>
</div>
</body>
</html>
