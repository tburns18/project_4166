/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class MembershipServlet extends HttpServlet {

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
            out.println("<title>Servlet MembershipServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MembershipServlet at " + request.getContextPath() + "</h1>");
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

        //Get action parameter
        String action = request.getParameter("action");
        String url = "/index.jsp";
        //Make a big decision
        if(action.equals("login")){
            url = "/login.jsp";
            request.getServletContext().getRequestDispatcher("/login.jsp").forward(request,response);
            doPost(request,response);
        }
        else if(action.equals("signup")){
            url = "/signup.jsp";
            request.getServletContext().getRequestDispatcher("/signup.jsp").forward(request, response);
            doPost(request,response);
        }
        else if(action.equals("logout")){
            HttpSession session = request.getSession();
            session.invalidate();
            request.getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
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
        
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String url;
        
        if(action.equals("signup")){
        
            String fName = request.getParameter("fName");
            String lName = request.getParameter("lName");
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
                
        if(fName.isEmpty() || lName.isEmpty() || (email.isEmpty() || !(email.contains("@")))){
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet MembershipControllerServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Membership Servlet</h1>");
                if(fName.isEmpty()){
                    out.println("<p>Enter a first name.</p>");
                }
                if(lName.isEmpty()){
                    out.println("<p>Enter a last name.</p>");
                }
                if(email.isEmpty() || !(email.contains("@"))){
                    out.println("<p>Enter a valid email address.</p>");

                }
                out.println("</body>");
                out.println("</html>");

            }
        }
        else if(username.isEmpty() || password.length() <= 8){
            try (PrintWriter out = response.getWriter()){
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet MembershipControllerServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Membership Servlet</h1>");
                if(username.isEmpty()){
                    out.println("<p>Enter a username</p>");
                }
                if(password.length()<= 8){
                    out.println("<p>Password must be at least 8 characters</p>");
                }
                out.println("</body>");
                out.println("</html>");
            }
            
        }
            User user = new User();

            user.setfName(fName);
            user.setlName(lName);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
        
            UserTable.addRecord(user);
            session.setAttribute("user", user);
            url = "/login.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
                        
        }else if(action.equals("login")){
            
                String loggedEmail = request.getParameter("email");
                String loggedPassword = request.getParameter("password");
                
                User user = UserTable.getUser(loggedEmail);
                if(loggedEmail.isEmpty() || loggedPassword.isEmpty()) {
                    try (PrintWriter out = response.getWriter()){
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet MembershipControllerServlet</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Membership Servlet</h1>");
                        if(loggedEmail.isEmpty()){
                            out.println("<p>Enter your email</p>");
                        }
                        if(loggedPassword.isEmpty()){
                            out.println("<p>Enter your password</p>");
                        }
                    }
                }
                if(user.getPassword().equals(request.getParameter("password"))){
                    session.setAttribute("user", user);

                    // Replace the old list in the session with the new list (that contains the new product we just added)
                    ArrayList<Product> productList = (ArrayList<Product>) ProductTable.selectProducts();
                    session.removeAttribute("products");
                    session.setAttribute("products", productList);
                    
                    request.getServletContext().getRequestDispatcher("/products.jsp").forward(request,response);
                    ArrayList<User> users = (ArrayList<User>) session.getAttribute("user");
                }
                else {
                    request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                }
            }
        else if(action.equals("logout") ){
            request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            session.invalidate();
        }
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
