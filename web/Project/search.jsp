<%@ page import="Project.Model.Cart" %>
<%@ page import="Project.Model.Model" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Project.Model.Collection" %>
<%@ page import="Project.Model.Product" %><%--
  Created by IntelliJ IDEA.
  User: Nico
  Date: 24/05/2019
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Model model = new Model((String) request.getSession().getAttribute("auth"));
    String name = request.getParameter("s");
    assert name != null;

    ArrayList<Collection> collections = Collection.searchByName(name);
    ArrayList<Product> products = Product.searchByName(name);
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
                if(model.user != null){
                    Cart cart = model.user.getLastCart();
            %>
            <a href='/cart' class='pure-button button-secondary'>Cart <%=cart!=null?"("+cart.cartElements.size()+")":""%></a>
            <a href='/logout' class='pure-button button-secondary'>Logout</a>
            <%
                }
            %>
        </div>
        <div class="text-center">
            <div class="search">
                <form class="pure-form" action="/search" method="get">
                    <fieldset>
                        <input type="text" name="s" placeholder="Product" style="width: 40vw;" value="<%=name%>" required>
                        <input type="submit" value="Search" class="pure-button button-success">
                    </fieldset>
                </form>
            </div>
        </div>
        <div class="pure-g">
            <div class="pure-u-1">
                <div class="panel">
                    <div class="title color-grey-b">
                        Collections (<%=collections.size()%>)
                    </div>
                </div>
            </div>
            <%
                for (Collection collection : collections){
            %>
            <div class="pure-u-1-5">
                <div class="panel product" onclick="location.href = '/shop?collection=<%=collection.getIdString()%>';">
                    <div class="image" style="">
                        <div class="title"><%=collection.name%></div>
                    </div>
                    <div class="description color-grey">
                        <%=collection.description%>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
            <div class="pure-u-1">
                <div class="panel">
                    <div class="title color-grey-b">
                        Products (<%=products.size()%>)
                    </div>
                </div>
            </div>
            <%
                for (Product product : products){
            %>
            <div class="pure-u-1-5">
                <div class="panel product" onclick="location.href = '/product?id=<%=product.getIdString()%>';">
                    <div class="image" style="">
                        <div class="title"><%=product.name%></div>
                    </div>
                    <div class="description color-grey">
                        <%=product.description%>
                    </div>
                    <div class="footer color-grey-b">
                        <%=product.price%>$
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </body>
</html>
