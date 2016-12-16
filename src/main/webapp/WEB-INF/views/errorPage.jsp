
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="fragment/header.jsp" %>
<body>

<div class="container">
    <%@include file="fragment/navbar.jsp" %>
    <h1 class="text-center">${errorTitle}</h1>
    <p>${errorMessage}</p>
</div>
</body>
</html>
