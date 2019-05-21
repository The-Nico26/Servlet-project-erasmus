
<%--
  Created by IntelliJ IDEA.
  User: Nico
  Date: 18/05/2019
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HttpSession hS = request.getSession();
    String registration = (hS.getAttribute("error") == null ? "" : (String) hS.getAttribute("error"));
    hS.setAttribute("error", null);
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
<div class="vertical-text text-center">
    <div class="container">
        <div class="title-web">
            IT Shop
        </div>
        <div class="panel" style="width: 50vw">
            <div class="title color-grey-b text-center">
                Login
            </div>
            <div class="article color-grey">
                <%
                    if(!registration.equals("")){
                        out.println(registration);
                    }
                %>
                <form class="pure-form input-center" action="/registration" method="post">
                    <fieldset>
                        <label for="name">Name:</label><br>
                        <input type="text" id="name" name="name" placeholder="Name and Surname" required value=""><br><br>

                        <label for="email">Email:</label><br>
                        <input type="email" id="email" name="email" placeholder="Email" required value=""><br><br>

                        <label for="password">Password:</label><br>
                        <input type="password" id="password" name="password" placeholder="*******" required value=""><br>

                        <label for="passwordr">Repeat Password:</label><br>
                        <input type="password" id="passwordr" name="password_repeat" placeholder="*******" required value="">
                    </fieldset>
                    <div class="text-center">
                        <a href="/index" class="pure-button button-secondary">Homepage</a> - <input type="submit" class="pure-button button-success" value="Registration"> - <a href="/login" class="pure-button button-secondary">Login</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>