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
         <p>You are currently logged in as: <c:out value="${user.fName}" default = "oh, guest mode" /></p>
         

         <c:choose>
                <c:when test = "${user.fName != null}">
                    <p>You are currently logged in as: <c:out value="${user.lName}"/></p>
                </c:when>
                
                <c:otherwise>
                    <c:redirect url="/login.jsp"/>
                </c:otherwise>
        </c:choose>
            
                    
        <c:if test="${user.lName != null}">
            <a href="membership?action=logout">Logout</a>
        </c:if>
                 
       <h2>Products</h2>
        <table>
            <tr><th> Code </th> <th> Description </th> <th> Price </th> 
                <th></th> <th></th> </tr>
            <!-- The For Each that Loops Through the Products -->
            <c:forEach var="Product" items="${products}">
             <tr>
                <td>${Product.code}</td>
                <td>${Product.description}</td> 
                <td>${Product.price}</td>
                <td> 
                    <form action="productManagement?action=displayProduct" method="post">
                        <input type="hidden" name="productCode" value="<c:out value='${Product.code}'/>"/>
                        <input type="submit" value="Edit" />
                    </form>
                </td> 
                <td> 
                    <form action="productManagement?action=deleteProduct" method="post">
                        <input type="hidden" name="productCode" value="<c:out value='${Product.code}'/>"/>
                        <input type="submit" value="Delete" />
                    </form>
                </td> 
            </tr>
             </c:forEach>
        </table>
        <form action="productManagement" method="get">
            <input type="hidden" name="action" value="addProduct">
            <p><input type="submit" value="Add Product"></p>
        </form>
       
        <p>
            <a href='login.jsp'>Back to login</a>
        </p>

    </body>
</html>