<%@ page import="Project.Model.Model" %>
<%@ page import="Project.Model.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Model m = new Model((String)request.getSession().getAttribute("auth"));
%>
<html>
    <head lang="en">
        <meta charset="UTF-8">
        <title>Project</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="resources/pure-min.css" type="text/css" rel="stylesheet">
        <link href="resources/design.css" type="text/css" rel="stylesheet">
        <link href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="//fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    </head>
    <body>
        <div class="account">
            <a href="/account" class="pure-button button-secondary">Account</a>
            <%
                if(m.user != null){
                    Cart cart = m.user.getLastCart();
            %>
            <a href='/cart' class='pure-button button-secondary'>Cart <%=cart!=null?"("+cart.cartElements.size()+")":""%></a>
            <a href='/logout' class='pure-button button-secondary'>Logout</a>
            <%
                }
            %>
        </div>
        <div class="vertical-text text-center search">
            <div class="container">
                <div class="title-web">
                    it shop
                </div>
                <form class="pure-form" action="/search" method="get">
                    <fieldset>
                        <input type="text" name="s" placeholder="Product" style="width: 40vw;" required>
                        <input type="submit" value="Search" class="pure-button button-success">
                    </fieldset>
                </form>
            </div>
        </div>
    </body>
</html>