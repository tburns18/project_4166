<%-- 
    Document   : confirmDelete
    Created on : Feb 20, 2019, 11:19:46 AM
    Author     : tyler
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Management</title>
        <link rel="stylesheet" href="style.css"/>
        
    </head>
    <body>
        <c:if test="${user.fName != null}">
            <p>You are currently logged in as: <c:out value="${user.fName} ${user.lName}"/></p>
            <a href="membership?action=logout">User Logout</a>
        </c:if>
            
        <h1>Are you sure you want to delete this product?</h1>
        
        <section>
            
            <span class="leftHeading" >Code:</span> <c:out value="${product.itemCode}"/> <br>
            <span class="leftHeading" >Description:</span> <c:out value="${product.itemDescription}"/> <br>
            <span class="leftHeading" >Price:</span> <c:out value="${product.itemPrice}"/> <br>
            

            <%-- Form to Delete the product --%>
            <form action="productManagement" method="get"> 
                <input type="hidden" name="action" value="deleteProduct">
                <input type="hidden" name="productCode" value="<c:out value="${product.itemCode}"/>">
                <input type="submit" value="Yes" id="button1">
            </form>

            <%-- Form to go back to product display the product --%>
            <form action="productManagement" method="get">       
                <input type="hidden" name="action" value="displayProducts">
                <input type="submit" value = "No" id="button1">
            </form>


        </section>    

    </body>
</html>
