/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 
        HttpSession session = request.getSession();
        
        String action = request.getParameter("action");
        String url;
       
            switch (action) {
                case "displayProducts":
                    url = "/products.jsp";
                    break;
                case "addProduct":
                    url="/product.jsp";
                    break;
                case "deleteProduct":
                    url = "/confirmDelete.jsp";
                    break;
                case "displayProduct":
                    url = "/product.jsp";
                    break;
                default:
                    url = "/index.jsp";
                    break;
            }
            getServletContext().getRequestDispatcher(url).forward(request, response);

        processRequest(request, response);
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
        String url = "/products.jsp";
       
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
            case "displayProduct":
                
                {
                    url = "/products.jsp";
                    if(request.getParameter("update")!= null){
                    HttpSession session = request.getSession();
                    ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
                    try {
                        String code = request.getParameter("code");
                        String description = request.getParameter("description");
                        Double price = Double.parseDouble(request.getParameter("price"));
                        
                        if(products != null) {
                            for(Product product: products){
                                if(product.getItemCode().equals(code)){
                                    product.setItemCode(code);
                                    product.setItemDescription(description);
                                    product.setItemPrice(price);
                                }
                            }
                        } else {
                            Product newProduct = new Product();
                            newProduct.setItemCode(code);
                            newProduct.setItemDescription(description);
                            newProduct.setItemPrice(price);
                            //ProductIO.insertProduct(product, path); product.setCode(productCode);
                            products.add(newProduct);
                        }
                    } catch (Exception ex) {
                        url = "/productManagement?action=displayProduct";
                        request.setAttribute("error", "Make sure that you have not left any fields blank and"
                                + " filled in the price like: \"15.95\""  + ex);
                    }
                    }
                    break;
                }
            case "addProduct":
                {
                    //Get the the values to put into the new product
                    String code = request.getParameter("code");
                    String desc = request.getParameter("desc");
                    double price = Double.parseDouble(request.getParameter("price"));
                    //Create new product object and put in the values
                    Product newProduct = new Product();
                    newProduct.setItemCode(code);
                    newProduct.setItemDescription(desc);
                    newProduct.setItemPrice(price);
                    //get the product list from the session, if any
                    HttpSession session = request.getSession();
                    ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
                    if(products==null) {
                        products =  new ArrayList<>();
                        
                    }       //add product to list
                    products.add(newProduct);
                    // replacing the old list in the session by a the new list (that contains the new product we just added)
                    session.removeAttribute("products");
                    session.setAttribute("products", products);
                    //Redirect back to products page
                    getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
                    break;
                }
            case "displayProducts":
                {
                    String code = request.getParameter("code");
                    HttpSession session = request.getSession();
                    ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
                    if(products!=null)
                        session.setAttribute("products", products);
                    break;
                }
            case "deleteProduct":
                {
                    url = "/confirmDelete.jsp";
                    String code = request.getParameter("productCode");
                    //get the product list from the session, if any
                    HttpSession session = request.getSession();
                    ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
                    
                    if(products==null) {
                        products =  new ArrayList<>();
                    }
                            for (int i = 0; i < products.size(); i++){
                                Product p = products.get(i);
                                if(products != null){
                                    products.remove(i);
                                }
                                
                            }
            //remove product from list
                        //products.removeIf(newProduct -> newProduct.getItemCode().equals(code));
                    // replacing the old list in the session by a the new list (that contains the new product we just added)
                        session.removeAttribute("products");
                        session.setAttribute("products", products);
//Redirect back to products page
                        getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
                            break;
                        }
                    default:
                        url = "/index.jsp";
                        break;
                }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
            
    }

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
