<%-- 
    Document   : signup
    Created on : Feb 20, 2019, 11:20:12 AM
    Author     : tyler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up</title>
        <link rel="stylesheet" href="style.css" type="text/css"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <h1>Sign-up form</h1>

            <form action="/Project/membership?action=signup" method="post">
                <input type="hidden" name="action" value="add">        
                <label class="pad_top">First Name</label>
                <input type="text" name="fName" required><br>
                <label class="pad_top">Last Name</label>
                <input type="text" name="lName" required><br>
                <label class="pad_top">Email</label>
                <input type="email" name="email" required><br>
                <label class="pad_top">Username</label>
                <input type="text" name="username" required><br>
                <label class="pad_top">Password</label>
                <input type="password" name="password" required><br>
                <label>&nbsp;</label>
                <input type="submit" value="Sign up" class="margin_left">
            </form>
    </body>
</html>
