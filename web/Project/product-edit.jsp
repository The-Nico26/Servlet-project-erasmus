<%@ page import="Project.Model.Model" %>
<%@ page import="Project.Model.Product" %>
<%@ page import="Project.Model.Collection" %>
<%@ page import="Project.Model.TypeProduct" %><%--
  Created by IntelliJ IDEA.
  User: Nico
  Date: 22/05/2019
  Time: 09:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HttpSession hS = request.getSession();
    String error = (hS.getAttribute("error") == null ? "" : (String) hS.getAttribute("error"));
    hS.setAttribute("error", null);

    Model model = new Model((String) request.getSession().getAttribute("auth"));
    Product product = null;
    String id = request.getParameter("id");
    if(id != null)
        product = Product.getId(id);

    if(product == null)
        product = new Product();

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
            <div class="pure-u-1-4"></div>
            <div class="pure-u-1-2">
                <div class="panel">
                    <div class="title color-grey-b">
                        Product : <%= product.name%>
                    </div>
                    <div class="article color-white">
                        <form class="pure-form" method="post" action="/product">
                            <fieldset>
                                <%
                                    if(id != null) {
                                        out.println("<input type='hidden' name='id' value='" + id + "'>");
                                    }
                                    if(!error.equals(""))
                                        out.println(error+"<br>");
                                %>
                                <input type="hidden" value="<%=id==null?"new":"edit"%>" name="action">
                                <label for="name">Name Product</label>
                                <input type="text" value="<%=product.name%>" id="name" name="name" placeholder="Name product" required><br><br>

                                <label for="price">Price ($)</label>
                                <input type="number" value="<%=product.price%>" id="price" name="price" placeholder="Price ($)" required><br><br>

                                <label for="typeproduct">Type of product</label>
                                <select name="typeproduct" id="typeproduct" required>
                                    <%
                                        for(TypeProduct typeProduct : TypeProduct.getAll()){
                                            boolean selec = !(product.typeProduct == null || !product.typeProduct.getIdString().equals(typeProduct.getIdString()));
                                    %>
                                        <option value="<%=typeProduct.getIdString()%>" <%=selec?"selected":""%>><%=typeProduct.name%></option>
                                    <%
                                        }
                                    %>
                                </select><br><br>
                                <label for="collection">Collection</label>
                                <select name="collection" id="collection" required>
                                    <%
                                        for(Collection c : model.user.merchant.collections){
                                    %>
                                        <option value="<%=c.getIdString()%>" <%= (c.getIdString().equals(product.collection)?"selected":"")%>><%=c.name%></option>
                                    <%
                                        }
                                    %>
                                </select><br><br>
                                <label>Options</label>
                                <%
                                    for(String[] option : product.options){
                                        if(option.length != 2) continue;
                                        %>
                                    <div class="pure-g">
                                        <div class="pure-u-3-8">
                                            <input type="text" value="<%=option[0]%>" placeholder="Name option" name="options[]">
                                        </div>
                                        <div class="pure-u-3-8">
                                            <input type="text" value="<%=option[1]%>" placeholder="Values (;)" name="values[]">
                                        </div>
                                        <div class="pure-u-1-4">
                                            <a onclick="$(this).parent().parent().remove();">Delete this</a>
                                        </div>
                                    </div>
                                <%
                                    }
                                %>
                                <div class="pure-g">
                                    <div class="pure-u-2-5">
                                        <input type="text" placeholder="Name option" name="options[]">
                                    </div>
                                    <div class="pure-u-2-5">
                                        <input type="text" placeholder="Values (;)" name="values[]">
                                    </div>
                                    <div class="pure-u-1-5">
                                        <a onclick="$(this).parent().parent().remove();" class="pure-button button-warning">Delete this</a>
                                    </div>
                                </div>
                                <span id="newElement">

                                </span>
                                <br><br>
                                <a onclick="addElement()" class="pure-button button-success">Add new option</a>
                                <br><br>

                                <label for="description">Description</label>
                                <textarea id="description" name="description" placeholder="Description product" required><%=product.description%></textarea><br><br>
                                <input type="submit" value="Save" class="pure-button button-success">
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function addElement(){
                var html =
                    "<div class=\"pure-g\">\n" +
                    "    <div class=\"pure-u-2-5\">\n" +
                    "        <input type=\"text\" placeholder=\"Name option\" name=\"options[]\" required>\n" +
                    "    </div>\n" +
                    "    <div class=\"pure-u-2-5\">\n" +
                    "        <input type=\"text\" placeholder=\"Values (;)\" name=\"values[]\" required>\n" +
                    "    </div>\n" +
                    "    <div class=\"pure-u-1-5\">\n" +
                    "        <a onclick=\"$(this).parent().parent().remove();\" class='pure-button button-warning'>Delete this</a>\n" +
                    "    </div>\n" +
                    "</div>";
                $("#newElement").append(html);
            }
        </script>
    </body>
</html>
