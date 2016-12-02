<%--
Файл со формой входа пользователя
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="fragments/header.jsp" %>
</head>
<body>

<%@include file="fragments/navbar.jsp" %>

<div class="container">

    <div class="mainbox col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-4">

        <h3 class="text-center">Login page</h3>


        <form class="form-horizontal" role="form" method="post" action="${pageContext.request.contextPath}/login">
            <input type='hidden' name='type'
                   value='login'/>
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">Username</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="username" name="username" placeholder="username"
                           required>
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">Password</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-10 col-sm-offset-2">
                    <input id="submit" name="submit" type="submit" value="Log in" class="btn btn-primary">
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>
