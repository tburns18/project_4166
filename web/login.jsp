<%-- 
    Document   : login
    Created on : Feb 20, 2019, 11:20:00 AM
    Author     : tyler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="style.css"/>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <h1>Login</h1>
        <br/>  
        <form action="membership?action=login" method="post">  
            <label for="email">Email:</label>  
            <input type="text" name="email"/>  <br />
            <label for="password">Password:</label>  
            <input type="password" name="password"/>  <br />
            
            <input type="submit" value="Login"/>  <br />
        </form>
        <p>
            <a href="signup.jsp">New user? Click here to register</a>

        </p>
    </body>
</html>
