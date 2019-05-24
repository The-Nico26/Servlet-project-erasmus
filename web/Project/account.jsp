<%@ page import="Project.Model.Model" %><%--
  Created by IntelliJ IDEA.
  User: Nico
  Date: 18/05/2019
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HttpSession hS = request.getSession();
    String error = (hS.getAttribute("error") == null ? "" : (String) hS.getAttribute("error"));
    String error_shop = (hS.getAttribute("error_shop") == null ? "" : (String) hS.getAttribute("error_shop"));
    String screen = (hS.getAttribute("screen") == null ? "" : (String) hS.getAttribute("screen"));
    hS.setAttribute("screen", null);
    hS.setAttribute("error", null);
    hS.setAttribute("error_shop", null);
    Model m = new Model((String) hS.getAttribute("auth"));
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
        <script src="resources/jquery-3.4.1.min.js"></script>
    </head>
    <body>
    <div class="account">
        <a href="/cart" class="pure-button button-secondary">Cart (0)</a>
    </div>
        <div class="text-center">
            <div class="search">
                <form class="pure-form" action="/search" method="get">
                    <fieldset style="text-align: center">
                        <input type="text" name="s" placeholder="Product" required style="width: 40vw;">
                        <input type="submit" value="Search" class="pure-button button-success">
                    </fieldset>
                </form>
            </div>
        </div>
        <div class="pure-g">
            <div class="pure-u-1 text-center">
                <a onclick="showAccount()" class="pure-button button-secondary">Show Account</a> - <a onclick="showMerchant()" class="pure-button button-secondary">Show Merchant</a>
            </div>
            <div class="pure-u-1-4"></div>
            <div class="pure-u-1-2" id="account">
                <div class="panel">
                    <div class="title color-white">
                        Your account
                    </div>
                    <div class="article color-grey">
                        Information:<br>
                        <%= error %><br>
                        <form class="pure-form input-center" action="/account" method="post">
                            <input type="hidden" name="action" value="account">
                            <fieldset>
                                <label for="name">Name:</label><br>
                                <input type="text" id="name" name="name" placeholder="Name and Surname" required value="<%= m.user.name %>"><br><br>

                                <label for="address">Address:</label><br>
                                <input type="text" id="address" name="address" placeholder="Address" required value="<%= m.user.address %>"><br><br>

                                <label for="email">Email:</label><br>
                                <input type="email" id="email" name="email" placeholder="Email" required value="<%= m.user.email %>"><br><br>

                                <label for="passwordrr">Password:</label><br>
                                <input type="password" id="passwordrr" name="password" placeholder="*******" autocomplete="off"><br>

                                <label for="passwordr">Repeat Password:</label><br>
                                <input type="password" id="passwordr" name="password_repeat" placeholder="*******" autocomplete="off">
                            </fieldset>
                            <div class="text-center">
                                <input type="submit" class="pure-button button-success" value="Save">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="pure-u-1-2" id="merchant" style="display: none;">
                <div class="panel">
                    <div class="title color-white">
                        Your shop
                    </div>
                    <div class="article color-grey">
                        <form class="pure-form input-center" action="/account" method="post">
                            <%
                                String name = "", weblink = "", address = "";
                                boolean valid = true, write = true;
                                if(m.user.merchant != null) {
                                    name = m.user.merchant.name;
                                    weblink = m.user.merchant.webLink;
                                    address = m.user.merchant.address;
                                    if (m.user.merchant.valid.equals("1")) {
                                        out.println("Your shop is submitted");
                                        write = false;
                                    }
                                    else if (m.user.merchant.valid.equals("2")) {
                                        out.println("Your shop is not valid");
                                        valid = false;
                                        write = false;
                                    }
                                }
                            %>
                            <input type="hidden" name="action" value="merchant">
                            <%= error_shop %><br>
                            <fieldset>
                                <label for="name_shop">Name:</label><br>
                                <input type="text" id="name_shop" name="name" placeholder="Shop name" required value="<%=name%>" <% if(!write) out.print("disabled"); %>><br><br>

                                <label for="address_shop">Address shop:</label>
                                <input type="text" id="address_shop" name="address" placeholder="Shop address" required value="<%=address%>"><br><br>

                                <label for="weblink">Link shop:</label>
                                <input type="text" id="weblink" name="weblink" placeholder="Shop Link" value="<%=weblink%>"><br><br>
                            </fieldset>
                            <%
                                if(valid){
                            %>
                                <div class="text-center">
                                    <input type="submit" class="pure-button button-success" value="Save Shop">
                                </div>
                            <%
                                }
                                if(m.user.merchant != null && m.user.merchant.valid.equals("3")){%>
                                    <a href="/shop?id=<%=m.user.merchant.getIdString()%>" class="pure-button button-secondary">Go Home Shop</a>
                                <%
                                }
                            %>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function showAccount(){
                $('#merchant').css('display', 'none');
                $('#account').css('display', 'block');
            }
            function showMerchant(){
                $('#merchant').css('display', 'block');
                $('#account').css('display', 'none');
            }
            <%
            if(!screen.equals("")){
                out.println("showMerchant();");
            }
            %>
        </script>
    </body>
</html>