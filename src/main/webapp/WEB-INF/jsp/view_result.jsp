<%-- Файл для просмотра книги --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="fragments/header.jsp" %>
</head>
<body>

<%@include file="fragments/navbar.jsp" %>

<div class="container">

    <c:choose>
        <c:when test="${not empty book}">
            <h1>Title: ${book.title}</h1>
            <p>Author: ${book.author}</p>
            <p>Content:</p>
            <p>${book.content}</p>
        </c:when>
        <c:otherwise>
            <p>No books available.</p>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>
