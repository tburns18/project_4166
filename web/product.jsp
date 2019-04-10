<%-- 
    Document   : product
    Created on : Feb 20, 2019, 11:19:07 AM
    Author     : tyler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Management</title>
        <link rel="stylesheet" href="style.css"/>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <c:if test="${user.fName != null}">
            <p>You are currently logged in as: <c:out value="${user.fName} ${user.lName}"/></p>
            <a href="membership?action=logout">User Logout</a>
        </c:if>
            <h2>Product</h2>
           
        <c:out value="${error}" />
        <form action="productManagement?action=displayProducts" method="POST">
            <label> Code: </label>
            <input type="text" name="code" value="${product.code}"/> <br />
            <label> Description: </label>
            <textarea name="description" height="50px;">${product.description}</textarea> <br />
            <label> Price: </label>
            <input type="text" name="price" value="${product.price}"/> <br />
            
            <input type="hidden" name="action" value="addProduct"/>
            <input type="submit" value="Add Product" />
        </form>
            
            <a href="productManagement?action=displayProducts"> <button class="left"> View All Products </button></a>
    </body>
</html>
