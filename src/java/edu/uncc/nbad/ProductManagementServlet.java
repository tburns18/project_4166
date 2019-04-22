/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

/**
 *
 * @author tyler
 */
public class ProductManagementServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductManagementServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductManagementServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String action = request.getParameter("action");
        String url;
       
            switch (action) {
                case "displayProducts": {
                    HttpSession session = request.getSession();
                    session.removeAttribute("products");
                    ArrayList<Product> productList = (ArrayList<Product>) ProductTable.selectProducts();
                    
                    session.setAttribute("products", productList);
                    getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
                    }
                    break;

                case "addProduct": {
                    HttpSession session = request.getSession();
                    session.removeAttribute("product");
                    System.out.println(" forwarding to new product");
                    getServletContext().getRequestDispatcher("/addProduct.jsp").forward(request, response);
                    }
                    break;
                case "updateProduct": {
                    String code = request.getParameter("itemCode");
                    HttpSession session = request.getSession();
                    ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
                    for(Product p: products) {
                        if(p.getItemCode().equals(code)){
                            session.setAttribute("product", p);
                        }
                    }
                    getServletContext().getRequestDispatcher("/updateProduct.jsp").forward(request, response);
                    }
                    break;
                case "deleteProduct": {
                    String code = request.getParameter("itemCode");
                    HttpSession session = request.getSession();
                    ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
                    for(Product p: products) {
                        if(p.getItemCode().equals(code)){
                            session.setAttribute("product", p);
                            System.out.println(p.getItemCode() + " " + p.getItemDescription() + " " + p.getItemPrice());
                            getServletContext().getRequestDispatcher("/confirmDelete.jsp").forward(request, response);
                            break;
                        }
                    }
                    getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
                    }
                    break;
                
                case "actuallyDelete":
                    System.out.println("Requested to Delete" + request.getParameter("itemCode"));
                    {
                        String code = request.getParameter("itemCode");
                        HttpSession session = request.getSession();
                        
                        // Delete product from database
                        Product product = new Product();
                        product.setItemCode(code);
                        ProductTable.deleteProduct(product);

                        ArrayList<Product> productList = (ArrayList<Product>) ProductTable.selectProducts();

                        session.removeAttribute("products");
                        session.setAttribute("products", productList);
                        getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
                    }
                    break;
                default:
                    break;
        }
    }
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        String action = request.getParameter("action");
        System.out.println("action " + action);
        
       
        if (action.equals("login")){
            
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");

            if (user.getPassword()==null){
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
            }
        }
       
        switch (action) {
            case "addProduct":
                {
                    // Get the the values to put into the new product
                    String code = request.getParameter("code");
                    String desc = request.getParameter("desc");
                    String price = request.getParameter("price");
                    double numPrice = 0;
                    boolean alreadyExists = ProductTable.exists(code);
                    
                    if(code.isEmpty() || desc.isEmpty() || price.isEmpty() || desc.trim().isEmpty() || alreadyExists){
                        response.setContentType("text/html;charset=UTF-8");
                        try (PrintWriter out = response.getWriter()){
                            response.setContentType("text/html");
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Servlet MembershipServlet</title>");            
                            out.println("</head>");
                            out.println("<h1>ERROR! Make sure all fields are filled out!</h1>");
                            out.println("<h2>The following entries are invalid: </h2>");
                            out.println("<body>");
                            if(code.isEmpty() || alreadyExists) {
                                if(alreadyExists){
                                    out.println("<p>Code: product with code " + code + " already exists!</p>");
                                } else {
                                    out.println("<p>Code</p>");
                                }
                            }
                            if(desc.isEmpty() || desc.trim().isEmpty()) {
                                out.println("<p>Description</p>");
                            }
                            if(price.isEmpty()) {
                                out.println("<p>Price</p>");
                            }
                            out.println("</body>");
                            out.println("</html>");
                        }
                    } else if (!price.isEmpty()){
                        numPrice = Double.parseDouble(price);
                        if(numPrice<0){
                            response.setContentType("text/html;charset=UTF-8");
                                try (PrintWriter out = response.getWriter()) {
                            /* TODO output your page here. You may use following sample code. */
                                response.setContentType("text/html");
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Servlet MembershipServlet</title>");            
                                out.println("</head>");
                                out.println("<h1>ERROR! Make sure all fields are filled out!</h1>");
                                out.println("<h2>The following entries are invalid: </h2>");
                                out.println("<body>");
                                out.println("<p>Price must be greater than 0</p>");
                                out.println("</body>");
                                out.println("</html>");
                            }
                        } else {
                            // Create new product object and put in the values
                            Product newProduct = new Product();
                            newProduct.setItemCode(code);
                            newProduct.setItemDescription(desc);
                            newProduct.setItemPrice(numPrice);
                            
                            // Add the new product to the database
                            ProductTable.insertProduct(newProduct);

                            HttpSession session = request.getSession();
                            ArrayList<Product> productList = (ArrayList<Product>) ProductTable.selectProducts();
                    
                            // Replace the old list in the session with the new list (that contains the new product we just added)
                            session.removeAttribute("products");
                            session.setAttribute("products", productList);
                            // Redirect back to products page
                        }
                    }
                }
                getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
                break;
            
            case "updateProduct":
                {
                    // Get the the values to put into the new product
                    String code = request.getParameter("code");
                    String desc = request.getParameter("desc");
                    String price = request.getParameter("price");
                    double numPrice = 0;
                    boolean alreadyExists = ProductTable.exists(code);
                    
                    if(code.isEmpty() || desc.isEmpty() || price.isEmpty() || desc.trim().isEmpty() || !alreadyExists){
                        response.setContentType("text/html;charset=UTF-8");
                        try (PrintWriter out = response.getWriter()){
                            response.setContentType("text/html");
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Servlet MembershipServlet</title>");            
                            out.println("</head>");
                            out.println("<h1>ERROR! Make sure all fields are filled out!</h1>");
                            out.println("<h2>The following entries are invalid: </h2>");
                            out.println("<body>");
                            if(code.isEmpty() || !alreadyExists) {
                                if(!alreadyExists){
                                    out.println("<p>Code: product with code " + code + " does not exist!</p>");
                                } else {
                                    out.println("<p>Code</p>");
                                }
                            }
                            if(desc.isEmpty() || desc.trim().isEmpty()) {
                                out.println("<p>Description</p>");
                            }
                            if(price.isEmpty()) {
                                out.println("<p>Price</p>");
                            }
                            out.println("</body>");
                            out.println("</html>");
                        }
                    } else if (!price.isEmpty()){
                        numPrice = Double.parseDouble(price);
                        if(numPrice<0){
                            response.setContentType("text/html;charset=UTF-8");
                                try (PrintWriter out = response.getWriter()) {
                            /* TODO output your page here. You may use following sample code. */
                                response.setContentType("text/html");
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Servlet MembershipServlet</title>");            
                                out.println("</head>");
                                out.println("<h1>ERROR! Make sure all fields are filled out!</h1>");
                                out.println("<h2>The following entries are invalid: </h2>");
                                out.println("<body>");
                                out.println("<p>Price must be greater than 0</p>");
                                out.println("</body>");
                                out.println("</html>");
                            }
                        } else {
                            // Create new product object and put in the values
                            Product newProduct = new Product();
                            newProduct.setItemCode(code);
                            newProduct.setItemDescription(desc);
                            newProduct.setItemPrice(numPrice);
                            
                            // Update the product in the database
                            ProductTable.updateProduct(newProduct);
                            
                            HttpSession session = request.getSession();
                            ArrayList<Product> productList = (ArrayList<Product>) ProductTable.selectProducts();
                    
                            // Replace the old list in the session with the new list (that contains the product we just updated)
                            session.removeAttribute("products");
                            session.setAttribute("products", productList);
                            // Redirect back to products page
                        }
                    }
                }
                getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
                break;    
                
            default:
                System.err.println("Error");
                break;
        }
    }

//            case "displayProducts":
//                {
//                    String code = request.getParameter("code");
//                    HttpSession session = request.getSession();
//                    ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
//                    if(products!=null)
//                        session.setAttribute("products", products);
//                    break;
//                }
//            case "deleteProduct":
//                {
//                    url = "/confirmDelete.jsp";
//                    String code = request.getParameter("productCode");
//                    //get the product list from the session, if any
//                    HttpSession session = request.getSession();
//                    ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
//                    
//                    if(products==null) {
//                        products =  new ArrayList<>();
//                    }
//                            for (int i = 0; i < products.size(); i++){
//                                Product p = products.get(i);
//                                if(products != null){
//                                    products.remove(i);
//                                }
//                                
//                            }
//            //remove product from list
//                        //products.removeIf(newProduct -> newProduct.getItemCode().equals(code));
//                    // replacing the old list in the session by a the new list (that contains the new product we just added)
//                        session.removeAttribute("products");
//                        session.setAttribute("products", products);
////Redirect back to products page
//                        getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
//                            break;
//                        }
//                    default:
//                        url = "/index.jsp";
//                        break;
//                }
//        getServletContext()
//                .getRequestDispatcher(url)
//                .forward(request, response);
//            
//    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
