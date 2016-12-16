<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="fragment/header.jsp" %>
<body>
<div class="container">
    <%@include file="fragment/navbar.jsp"%>

    <form method="POST" action="${pageContext.request.contextPath}/login">
        <h2 class="form-heading">Вход в систему</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="username" type="text" class="form-control" placeholder="Username" autofocus/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Вход</button>
            <h4 class="text-center"><a href="${pageContext.request.contextPath}/register">Создание аккаунта</a></h4>
        </div>

    </form>

</div>
</body>
</html>
