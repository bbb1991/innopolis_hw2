<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8"%>

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

                <c:forEach var="book" items="${users}">
                    <tr>
                        <td>${book.id}</td>
                        <td>${book.username}</td>
                        <td>${book.blocked ? "Заблокирован" : "Не заблокирован"}</td>
                        <c:if test="${book.blocked}">
                            <td><a href="${pageContext.request.contextPath}/admin/unblock/${book.id}">Разблокировать</a>
                            </td>
                        </c:if>
                        <c:if test="${not book.blocked}">
                            <td><a href="${pageContext.request.contextPath}/admin/block/${book.id}">Заблокировать</a>
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
