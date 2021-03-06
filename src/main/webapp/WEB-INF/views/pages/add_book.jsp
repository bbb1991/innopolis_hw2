<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=utf-8" %>
<div>
    <form:form method="POST" modelAttribute="book" action="${pageContext.request.contextPath}/add_book">
        <%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
        <h2>Add new book</h2>
        <spring:bind path="title">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="title" placeholder="title" autofocus="true" required="true"
                            class="form-control"/>
                <form:errors path="title"/>
            </div>
        </spring:bind>

        <spring:bind path="content">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea path="content" placeholder="content" required="true" class="form-control"/>
                <form:errors path="content"/>
            </div>
        </spring:bind>

        <spring:bind path="draft">
            <form:checkbox path="draft" class="form-control"/>
        </spring:bind>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button class="btn btn-lg btn-success btn-block" type="submit">Submit</button>
    </form:form>

</div>
