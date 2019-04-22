<%-- 
    Document   : updateProduct
    Created on : April 22, 2019
    Author     : austin
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
        <c:choose>
            <c:when test="${user.fName != null}">
                <c:out value="${user.lName}"/>
            </c:when>
            <c:otherwise>
                <c:redirect url="/login.jsp"/>
            </c:otherwise>
        </c:choose>
            <h2>Product</h2>
           
        <form action="productManagement" method="post">
            <p>    
                <label class="leftHeading">Code</label>
                <input type="text" readonly="readonly" name="code" value="<c:out value="${product.itemCode}"/>">
            </p>

            <p>
                <label class="leftHeading">Description</label>
                <textarea rows="4" cols="25" name="desc"><c:out value="${product.itemDescription}"/></textarea>
            </p>

            <p>
                <label class="leftHeading">Price</label>
                <input type="text" name="price" value="<c:out value="${product.itemPrice}"/>">
            </p>

            <div class="rightButton">
                <input type="hidden" name="action" value="updateProduct">          
                <input type="submit" value="Update Product">

            </div>
        </form>
            <form action ="productManagement" method ="get">
                <input type ="hidden" name="action" value="displayProducts">
                <input type ="submit" value ="View Products">
            </form>
    </body>
</html>
