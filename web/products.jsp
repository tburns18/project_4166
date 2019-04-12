<%-- 
    Document   : products
    Created on : Feb 14, 2019, 7:17:19 PM
    Author     : Tyler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
        <link rel="stylesheet" href="style.css"/>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <p> ${user.fName} </p>
         <p>You are currently logged in as: <c:out value="${user.fName}" default = "Guest" /></p>
         

         <c:choose>
                <c:when test = "${user.fName != null}">
                <c:out value="${user.lName}"/>
                </c:when>
                
                <c:otherwise>
                    <c:redirect url="/login.jsp"/>
                </c:otherwise>
        </c:choose>
                 
       <h2>Products</h2>
        
        <table>
            <tr>
                <th>Code</th>
                <th>Description</th>
                <th class="right">Price</th>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
            </tr>
            <c:forEach var="element" items="${products}">
                <tr>
                    <td><c:out value="${element.itemCode}"/></td>
                    <td><c:out value="${element.itemDescription}"/></td>
                    <td><c:out value="${element.priceCurrencyFormat}"/></td>
                    <td><a href="/Project/productManagement?action=updateProduct&amp;itemCode=${element.itemCode}">Edit</a></td>
                    <td><a href="/Project/productManagement?action=deleteProduct&amp;itemCode=${element.itemCode}">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
        
        

        
        <form action="/Project/productManagement" method="get">
            <input type="hidden" name="action" value="addProduct">
            <p><input type="submit" value="Add Product"></p>
        </form>

        <p>
            <a href='login.jsp'>Back to login</a>
        </p>

    </body>
</html>