<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="fragment/header.jsp" %>
</head>
<body>
<div class="container">
    <c:if test="${not empty book}">
        <h1 class="text-center">${book.title}</h1>
        <p>Created date: ${book.date}</p>
        <p>Author: ${book.author.username}</p>
        <p>Content:</p>
        <p>${book.content}</p>
    </c:if>
</div>
</body>
</html>
