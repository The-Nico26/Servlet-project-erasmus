<%@ page import="Project.Model.*" %><%--
  Created by IntelliJ IDEA.
  User: Nico
  Date: 20/05/2019
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Merchant m = null;
    Collection c = null;
    if(request.getParameter("collection") != null){
        c = Collection.getId(request.getParameter("collection"));
        assert c != null;
        m = Merchant.getId(c.merchant);
    }else
        m = Merchant.getId(request.getParameter("id"));

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
        <script src="resources/jquery-3.4.1.min.js"></script>
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
        </div>
        <div class="pure-g">
            <div class="pure-u-1-4">
                <div class="panel">
                    <div class="title color-grey-b">Information</div>
                    <div class="article color-grey">
                        Name: <%= m.name%><br>
                        Address: <%= m.address%><br>
                        Link: <a href="<%= m.webLink %>" target="_blank"><%= m.webLink%></a>
                        <%
                            if(access && c == null) {
                                out.println("<br><br>");
                                out.println("<a onclick=\"show('.popup-new')\" class=\"pure-button button-secondary\"><i class=\"fa fa-plus\"></i> categories</a>");
                            }
                        %>
                    </div>
                    <%
                        if(c != null){
                    %>
                    <div class="footer color-grey-b">
                        <a href="/shop?id=<%=m.getIdString()%>" class="pure-button button-secondary">Return home shop</a>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                    if(c != null) {
                %>
                <div class="panel">
                    <div class="article color-grey">
                        Categories:<br>
                        <%
                            for (Collection collection : m.collections){
                                %>
                            -> <a href="/shop?collection=<%=collection.getIdString()%>"><%=collection.name%></a><br>
                        <%
                            }
                            if(access){
                        %>
                            <a onclick="show('.popup-new')" class="pure-button button-secondary"><i class="fa fa-plus"></i> categories</a>
                        <%
                            }
                        %>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
            <div class="pure-u-3-4">
                <div class="pure-g">
                    <%
                        if (c != null){
                    %>
                    <div class="pure-u-1">
                        <div class="panel">
                            <div class="title color-grey-b">Information of collection</div>
                            <div class="article color-grey">
                                Name Collection: <%= c.name%><br>
                                Description: <%= c.description%>
                                <%
                                    if(access){
                                        %>
                                        <br><a href="/product?action=new" class="pure-button button-secondary">Add new product</a> - <a onclick="show('.popup-edit')" class="pure-button button-secondary"><i class="fa fa-pencil"></i> categories</a>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                    <%
                            for (Product product : c.products){
                                %>
                        <div class="pure-u-1-3">
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
                        }else{
                            for (Collection collection : m.collections){
                    %>
                        <div class="pure-u-1-3">
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
                        }
                    %>
                </div>
            </div>
        </div>
        <%
            if (access){
                %>

        <div class="popup-new" onclick="hide('.popup-new')" style="display: none">
            <div class="pure-g">
                <div class="pure-u-1-4"></div>
                <div class="pure-u-1-2" style="height: 100vh;">
                    <div class="vertical-text">
                        <div class="container">
                            <div class="panel" onclick="event.stopPropagation();">
                                <div class="title color-" style="background-color: rgb(120,120,120);">
                                    Add new Categories
                                </div>
                                <div class="article color-" style="background-color: rgb(200,200,200);">
                                    <form action="/shop?id=<%=m.getIdString()%>" method="post" class="pure-form input-center">
                                        <fieldset>
                                            <input type="hidden" name="action" value="add_categories">
                                            <label for="name">Name</label><br>
                                            <input type="text" name="name" id="name" placeholder="Name" required><br><br>

                                            <label for="description">Description</label>
                                            <textarea name="description" id="description" placeholder="Description" required></textarea><br><br>

                                            <input type="submit" class="pure-button button-success" value="Add">
                                        </fieldset>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            <%
                if(c != null){
            %>
        <div class="popup-edit" onclick="hide('.popup-edit')" style="display: none">
            <div class="pure-g">
                <div class="pure-u-1-4"></div>
                <div class="pure-u-1-2" style="height: 100vh;">
                    <div class="vertical-text">
                        <div class="container">
                            <div class="panel" onclick="event.stopPropagation();">
                                <div class="title color-" style="background-color: rgb(120,120,120);">
                                    Edit : <%=c.name%>
                                </div>
                                <div class="article color-" style="background-color: rgb(200,200,200);">
                                    <form action="/shop?collection=<%=c.getIdString()%>" method="post" class="pure-form input-center">
                                        <fieldset>
                                            <input type="hidden" name="action" value="edit_categories">
                                            <label for="name_collection">Name</label><br>
                                            <input type="text" name="name" id="name_collection" placeholder="Name" required value="<%=c.name%>"><br><br>

                                            <label for="description_categories">Description</label>
                                            <textarea name="description" id="description_categories" placeholder="Description" required><%=c.description%></textarea><br><br>

                                            <input type="submit" class="pure-button button-success" value="Save">
                                        </fieldset>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%
            }
        %>
        <script>
            function show(el){
                $(el).css("display", "");
            }
            function hide(el){
                $(el).css("display", "none");
            }
        </script>
        <%
            }
        %>
    </body>
</html>