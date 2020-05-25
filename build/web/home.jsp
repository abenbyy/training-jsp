<%-- 
    Document   : home
    Created on : May 19, 2020, 3:16:22 PM
    Author     : AE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>
    <body>
        <h1>WELCOME!</h1>
        <h3>this is the home page</h3>
        
        <h1>Insert Product</h1>
        <form actions="/Training/ProductController" method="POST">
            <input type="hidden" name="action" value="insert">
            
            <!-- Continue the form -->
        </form>
    </body>
</html>
