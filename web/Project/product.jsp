<%@ page import="Project.Model.*" %><%--
  Created by IntelliJ IDEA.
  User: Nico
  Date: 22/05/2019
  Time: 09:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Model model = new Model((String) request.getSession().getAttribute("auth"));
    Product product = Product.getId(request.getParameter("id"));
    assert product != null;
    Collection c = Collection.getId(product.collection);
    assert c != null;
    Merchant m = Merchant.getId(c.merchant);
    assert m != null;
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
                        <input type="text" name="s" placeholder="Product" style="width: 40vw;" required>
                        <input type="submit" value="Search" class="pure-button button-success">
                    </fieldset>
                </form>
            </div>
            <div class="pure-g">
                <div class="pure-u-2-3">
                    <div class="panel">
                        <div class="image" style="">
                            <div class="title"><%=product.name%></div>
                        </div>
                        <div class="article color-white">
                            Category >> <%=c.name%><br>
                            <br>
                            <%=product.description%>
                        </div>
                        <%
                            if(model.user != null && model.user.merchant != null && model.user.merchant.getIdString().equals(m.getIdString())){
                        %>
                        <div class="footer text-right color-grey-b">
                            <a href="/product?id=<%=product.getIdString()%>&action=edit" class="pure-button button-secondary">Edit product</a>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
                <div class="pure-u-1-3">
                    <div class="panel">
                        <div class="article color-white">
                            Price : <%=product.price%>$<br>
                            Shop : <a href="/shop?id=<%=m.getIdString()%>"><%=m.name%></a><br>

                            <form class="pure-form" action="/cart" method="post">
                                <fieldset>
                                    <input type="hidden" name="id_product" value="<%=product.getIdString()%>">
                                    <input type="hidden" name="action" value="add">

                                    <label for="number">Number:</label>
                                    <input type="number" id="number" name="number" value="1" placeholder="Number" required><br><br>
                                    <%
                                        if(!product.options.isEmpty()) {
                                            out.println("Options: <br>");
                                            for (String[] option : product.options) {
                                                if(option.length != 2)continue;
                                                out.println("<label>" + option[0] + "</label>");
                                                out.println("<input type='hidden' name='values[]' value='" + option[0] + "'>");
                                                out.println("<select name='options[]'>");
                                                for (String o : option[1].split(";")) {
                                                    if (o.equals("")) continue;
                                                    out.println("<option value='" + o + "'>" + o + "</option>");
                                                }
                                                out.println("</select><br>");
                                            }
                                        }
                                    %>
                                    <div class="text-right">
                                        <input type="submit" value="Add Cart" class="pure-button button-success">
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
