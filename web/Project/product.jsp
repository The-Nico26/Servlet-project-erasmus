<%@ page import="Project.Model.Model" %><%--
  Created by IntelliJ IDEA.
  User: Nico
  Date: 22/05/2019
  Time: 09:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Model model = new Model((String) request.getSession().getAttribute("auth"));
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
                    out.println("<a href='/cart' class='pure-button button-secondary'>Cart (0)</a>");
                    out.println("<a href='/logout' class='pure-button button-secondary'>Logout</a>");
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
                            <div class="title">Example</div>
                        </div>
                        <div class="article color-white">
                            Category >> <br>
                            <br>
                            Description de l'article
                        </div>
                    </div>
                </div>
                <div class="pure-u-1-3">
                    <div class="panel">
                        <div class="article color-white">
                            Price : 50Zlt<br>
                            Shop : <a href="/shop?id=">IT</a><br>

                            <form class="pure-form" action="/cart">
                                <fieldset>
                                    <input type="hidden" name="action" value="add">
                                    <label for="number">Number:</label>
                                    <input type="number" id="number" value="1" placeholder="Number" required><br>
                                    <input type="submit" value="Add Cart" class="pure-button button-success">
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
