<%--
  Файл с формой добавления новой книги
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new Book</title>
    <%@include file="header.jsp" %>
</head>
<body>

<%@include file="navbar.jsp" %>

<div class="container">

    <div class="col-md-8 col-md-offset-2">
        <h3 class="text-center">Add book</h3>
        <p>You can add your new awesome book here.</p>
        <form class="form-horizontal" role="form" method="post"
              action="${pageContext.request.contextPath}/add_book">

            <div class="form-group">
                <label for="title" class="col-sm-2 control-label">Title:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="title" name="title" placeholder="Title" value=""
                           required>
                </div>
            </div>

            <div class="form-group">
                <label for="content" class="col-sm-2 control-label">Content</label>
                <div class="col-sm-10">
                    <textarea class="form-control" id="content" rows="10" name="content"
                              placeholder="Content" required></textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-10 col-sm-offset-2">
                    <input id="submit" name="submit" type="submit" value="Add new book" class="btn btn-primary">
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>
