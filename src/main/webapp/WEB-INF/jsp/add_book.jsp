<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="fragment/header.jsp" %>
</head>
<body>
<div class="container">

    <form:form method="POST" modelAttribute="book">
        <h2>Add new book</h2>
        <spring:bind path="title">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="title" placeholder="title" autofocus="true" required="true"/>
                <form:errors path="title"/>
            </div>
        </spring:bind>

        <spring:bind path="content">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea path="content"  placeholder="content" required="true"/>
                <form:errors path="content"/>
            </div>
        </spring:bind>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" >
        <button class="btn btn-lg btn-success" type="submit">Submit</button>
    </form:form>

</div>
</body>
</html>
