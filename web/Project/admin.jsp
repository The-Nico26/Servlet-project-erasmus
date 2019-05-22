<%@ page import="java.util.ArrayList" %>
<%@ page import="Project.Model.Merchant" %><%--
  Created by IntelliJ IDEA.
  User: Nico
  Date: 21/05/2019
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<Merchant> merchants = Merchant.getAll();
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
        <div class="pure-g">
            <%
                for (Merchant merchant : merchants){
                    if(!merchant.valid.equals("1"))continue;
                %>
                <div class="pure-u-1-4">
                    <div class="panel">
                        <div class="title color-grey-b">
                            <%= merchant.name%>
                        </div>
                        <div class="article color-white">
                            <form class="pure-form" action="/admin" method="post">
                                <fieldset>
                                    <input type="hidden" name="action" value="valid_shop">
                                    <input type="hidden" name="id" value="<%=merchant.getIdString()%>">
                                    <label for="name">Name Shop</label><br>
                                    <input type="text" name="name" id="name" value="<%=merchant.name%>" placeholder="Name" required><br><br>

                                    <label for="address">Address Shop</label><br>
                                    <input type="text" name="address" id="address" value="<%=merchant.address%>" placeholder="Address" required><br><br>

                                    <label for="weblink">Link Shop</label><br>
                                    <input type="text" name="weblink" id="weblink" value="<%=merchant.webLink%>" placeholder="link"><br><br>

                                    <button type="submit" name="valid_value" value="3" class="pure-button button-success">Validate</button>
                                    <button type="submit" name="valid_value" value="2" class="pure-button button-success">Invalidate</button>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
                <%
                }
            %>
        </div>
    </body>
</html>
