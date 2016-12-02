<%--
  ФАйл с формой для поиска книги
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


        <form class="form-horizontal" role="form" method="post"
              action="${pageContext.request.contextPath}/find_book">
            <%--<input type='hidden' name='type'--%>
                   <%--value='login'/>--%>

                <input type="hidden" name="findBy" value="search_by_title" />
            <div class="form-group">
                <label for="id" class="col-sm-2 control-label">ID:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="id" name="id" disabled>
                </div>
            </div>
            <div class="form-group">
                <label for="title" class="col-sm-2 control-label">Title</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="title" name="title" >
                </div>
            </div>

            <div class="form-group">
                <label for="author" class="col-sm-2 control-label">Author</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="author" name="author" disabled>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-10 col-sm-offset-2">
                    <input id="submit" name="submit" type="submit" value="Submit" class="btn btn-primary">
                </div>
            </div>
        </form>
    </div>
</div>


</body>
</html>
