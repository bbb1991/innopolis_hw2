<%@ page import="dbService.dataSets.UserDataSet" %>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}">E-library</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="${pageContext.request.contextPath}">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/add_book">Add new book</a></li>
                <li><a href="${pageContext.request.contextPath}/find_book">Search book</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">


                <%
                    UserDataSet u = (UserDataSet) request.getSession().getAttribute("user");
                    if (u == null) {
                %>

                <li><a href="${pageContext.request.contextPath}/login"><span class="glyphicon glyphicon-log-in"></span>
                    Login</a></li>
                <li><a href="${pageContext.request.contextPath}/register"><span
                        class="glyphicon glyphicon-register"></span> Register</a></li>
                <% } else { %>
                <li><a href="#"><span
                        class="glyphicon glyphicon-register"></span> <%=u.getUsername()%></a></li>
                <li><a href="${pageContext.request.contextPath}/logout?logout"><span
                        class="glyphicon glyphicon-register"></span> Logout</a></li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>