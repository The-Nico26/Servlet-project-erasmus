<%@ page import="Project.Model.Model" %>
<%@ page import="Project.Model.Cart" %>
<%@ page import="Project.Model.CartElement" %><%--
  Created by IntelliJ IDEA.
  User: Nico
  Date: 24/05/2019
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Model model = new Model((String) request.getSession().getAttribute("auth"));
    assert model.user != null;

    Cart cart = model.user.getLastCart();
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
            <a href='/cart' class='pure-button button-secondary'>Cart <%=cart!=null?"("+cart.cartElements.size()+")":""%></a>
            <a href='/logout' class='pure-button button-secondary'>Logout</a>

        </div>
        <div class="text-center">
            <div class="search">
                <form class="pure-form" action="/search" method="get">
                    <fieldset>
                        <input type="text" name="s" placeholder="Product" style="width: 40vw;" required>
                        <input type="submit" value="Search" class="pure-button button-success">
                    </fieldset>
                </form>
            </div>
        </div>
        <div class="pure-g">
            <div class="pure-u-1-4"></div>
            <div class="pure-u-1-2">
                <%
                    if(cart != null){
                %>
                <div class="panel">
                    <div class="article color-white">
                        <table class="pure-table" style="min-width: 100%">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Number</th>
                                <th>Price (Per unit)</th>
                                <th>Option</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                for(CartElement cartElement : cart.cartElements){
                            %>
                                <tr>
                                    <td><%=cartElement.productName%></td>
                                    <td><%=cartElement.productNumber%></td>
                                    <td><%=cartElement.productPrice%>$</td>
                                    <td class="text-center">
                                        <a href="/cart?id=<%=cartElement.getIdString()%>&action=delete" class="pure-button button-warning">Delete</a>
                                    </td>
                                </tr>
                            <%
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                    <div class="footer color-grey">
                        <span class="float-right">
                            <a href="/cart?action=checkout" class="pure-button button-success">Checkout =></a>
                        </span>
                        <a href="/index" class="pure-button button-secondary">HomePage</a>
                    </div>
                </div>
                <%
                    }else{
                %>
                <div class="panel">
                    <div class="article color-white">
                        You haven't add recently a product in your cart. <br>
                        <a href="/index" class="pure-button button-secondary">Go shopping</a>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </body>
</html>
