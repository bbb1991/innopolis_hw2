<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="fragment/header.jsp" %>
<body>
<div class="container">
    <h1 class="text-center">secret message for admins</h1>
    <c:choose>
        <c:when test="${not empty users}">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th><b>ID</b></th>
                    <th><b>Пользователь</b></th>
                    <th><b>Статус</b></th>
                    <th><b>Заблокировать</b></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.blocked ? "Заблокирован" : "Не заблокирован"}</td>
                        <c:if test="${user.blocked}">
                            <td><a href="${pageContext.request.contextPath}/admin/unblock/${user.id}">Разблокировать</a>
                            </td>
                        </c:if>
                        <c:if test="${not user.blocked}">
                            <td><a href="${pageContext.request.contextPath}/admin/block/${user.id}">Заблокировать</a>
                            </td>
                        </c:if>

                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <h1>No users found</h1>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
