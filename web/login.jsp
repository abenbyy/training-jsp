<%-- 
    Document   : login
    Created on : May 19, 2020, 10:54:04 AM
    Author     : AE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Cookie[] cookies = request.getCookies();
    
    String userEmail = "";
    String userPassword = "";
    for(Cookie c:cookies){
        
        if(c.getName().equals("USER_EMAIL")){
            userEmail = c.getValue();
        }else if (c.getName().equals("USER_PASSWORD")){
            userPassword= c.getValue();
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="css/login.css">
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <form action="/Training/LoginController" method="POST">
            <input type="email" name="email" id="email" value="<%=userEmail%>">
            <br>
            <input type="password" name="password" id="password" value="<%=userPassword%>">
            <br>
            <input type="checkbox" name="remember" id="remember">Remember Me
            <br>
            <input type="submit" value="Login">
        </form>
        
        
        <%
            //Code java here
            int a = 10;
        %>
        
        Value of a is <%=a%>
    </body>
</html>
