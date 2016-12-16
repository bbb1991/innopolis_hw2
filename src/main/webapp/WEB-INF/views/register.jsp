<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="fragment/header.jsp" %>
<body>

<div class="container">
    <%@include file="fragment/navbar.jsp"%>

    <form:form method="POST" modelAttribute="userForm">
        <h2>Создание аккаунта</h2>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="username" class="form-control" placeholder="Username" autofocus="true"/>
                <form:errors path="username"/>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" class="form-control" placeholder="Password"/>
                <form:errors path="password"/>
            </div>
        </spring:bind>

        <spring:bind path="confirmPassword">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="confirmPassword" class="form-control"
                            placeholder="Confirm your password"/>
                <form:errors path="confirmPassword"/>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Регистрация</button>
    </form:form>

</div>

</body>
</html>
