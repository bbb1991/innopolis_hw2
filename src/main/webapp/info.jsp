<%
   String username = "Anonymous";
    boolean isBlocked = false;
    boolean isAdmin = false;
    if (userDataSet != null) {
        username = userDataSet.getUsername();
        isBlocked = userDataSet.isBlocked();
        isAdmin = userDataSet.isAdmin();
    }
%>

<p>Username: <%=username%></p>
<p>Is user blocked: <%=isBlocked%></p>
<p>Is user admin: <%=isAdmin%></p>