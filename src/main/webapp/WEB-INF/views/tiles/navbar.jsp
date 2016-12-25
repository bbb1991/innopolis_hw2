<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html; charset=utf-8" %>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">e-library</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/">Main page</a></li>
                <li><a href="${pageContext.request.contextPath}/add_book">Add book</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize var="loggedIn" access="isAuthenticated()"/>
                <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
                <c:choose>
                    <c:when test="${loggedIn}">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                               aria-haspopup="true"
                               aria-expanded="false">Username <span class="caret"></span></a>
                                <%-- todo add user name --%>
                            <ul class="dropdown-menu">
                                <c:if test="${isAdmin}">
                                    <li><a href="${pageContext.request.contextPath}/admin/">Admin panel</a></li>
                                </c:if>
                                <li><a href="${pageContext.request.contextPath}/dashboard/">Dashboard</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/logout/">Logout</a></li>

                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/login/">Login</a></li>
                        <li><a href="${pageContext.request.contextPath}/register/">Register</a></li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div><!--/.nav-collapse -->
    </div><!--/.container-fluid -->
</nav>