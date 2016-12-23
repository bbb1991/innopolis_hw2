<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>

    <h3>Current avatar:</h3>
    <img src="${pageContext.request.contextPath}/${user.avatar}" width="250" height="350" class="img-responsive">
    <br>
    <hr>

    <h3>Upload avatar</h3>
    <form method="post" action="${pageContext.request.contextPath}/dashboard/avatar?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
        <label for="upload">Choose image for upload:</label>
        <input id="upload" type="file" name="avatar" accept="image/*"><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <input type="submit" value="Upload image">
    </form>
    <br>
    <hr>

    <h3>Change password:</h3>
    <form method="post" action="${pageContext.request.contextPath}/dashboard/change_password">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div class="form-group">
            <label for="oldPassword">Old Password:</label>
            <input type="password" id="oldPassword" name="oldPassword" class="form-control" placeholder="Current password">
            <label for="p1">Password: </label>
            <input id="p1" name="password" type="password" class="form-control" placeholder="Password" autofocus/>
            <label for="p2">Confirm password: </label>
            <input id="p2" name="confirmPassword" type="password" class="form-control" placeholder="Password again"/>
        </div>
        <input type="submit" value="Change Password">
    </form>
    <br>
    <hr>


</div>