<%@ page import="Project.Model.Merchant" %>
<%@ page import="Project.Model.Model" %><%--
  Created by IntelliJ IDEA.
  User: Nico
  Date: 20/05/2019
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Merchant m = Merchant.getMerchantByName(request.getParameter("name"), false);
    Model model = new Model((String) request.getSession().getAttribute("auth"));
    assert m != null;
    boolean access = model.user != null && model.user.merchant != null && model.user.merchant.getIdString().equals(m.getIdString());
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
        </div>
        <div class="pure-g">
            <div class="pure-u-1-4">
                <div class="panel">
                    <div class="title color-grey-b"><%= m.name%></div>
                    <div class="article color-grey">
                        Categories:<br>
                        <%
                            if(access){
                        %>
                            <a onclick="show()" class="pure-button button-secondary"><i class="fa fa-plus"></i> categories</a>
                            <div class="popup" onclick="hide()" style="display: none">
                                <div class="pure-g">
                                    <div class="pure-u-1-4"></div>
                                    <div class="pure-u-1-2" style="height: 100vh;">
                                        <div class="vertical-text">
                                            <div class="container">
                                                <div class="panel" onclick="event.stopPropagation();">
                                                    <div class="article color-grey">
                                                        test
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <script>
                                function show(){
                                    $('.popup').css("display", "");
                                }
                                function hide(){
                                    $('.popup').css("display", "none");
                                }
                            </script>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
            <div class="pure-u-3-4">
                <div class="pure-g">
                    <div class="pure-u-1">
                        <div class="panel">
                            <div class="title color-grey-b">Information</div>
                            <div class="description color-grey">
                                Name: <%= m.name%><br>
                                Address: <%= m.address%><br>
                                Link: <a href="<%= m.webLink %>" target="_blank"><%= m.webLink%>
                            </div>
                        </div>
                    </div>
                    <div class="pure-u-1-3">
                        <div class="panel product" onclick="location.href = 'product.html';">
                            <div class="image" style="">
                                <div class="title">Example</div>
                            </div>
                            <div class="description color-grey">
                                Description de l'article
                            </div>
                            <div class="footer color-grey-b">
                                50Zlt
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>