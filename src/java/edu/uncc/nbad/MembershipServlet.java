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
        HttpSession session = request.getSession();
        String url = "/index.jsp";
        //Make a big decision
        if (!action.isEmpty() || action != null) {
            if (action.equals("login")) {
                // If action is equal to login go to login.jsp
                url = "/login.jsp";
            } else if (action.equals("signup")) {
            // If action is equal to signup go to signup.jsp
                url = "/signup.jsp";
            } 
            else if (action.equals("logout")) {
                // invalidates the session
                session.removeAttribute("user");
                session.invalidate();
                url = "/index.jsp";
            }
             getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        }
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

        //Get the action and session
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String url = "/index.jsp";

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MembershipControllerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Membership Servlet</h1>");

           
            if (action.equals("login")) {
                // Validate user
                String email = request.getParameter("email").trim();
                String password = request.getParameter("password").trim();
                User user = (User) session.getAttribute("user");
                if (email.isEmpty()) {
                    //Didn't provide a username
                    out.println("<p>Username is invalid.</p>");
                } else if (email == null ||!email.equals(user.getEmail())) {
                    //user didn't match any user in the text file
                    out.println("User does not exist");
                } else if (password == null || !password.equals(user.getPassword())) {
                    //password didn't match
                    out.println("<p>Incorrect Password.</p>");
                } else {
                    //valid user, so go to display products
                    url = "/products.jsp";
                    out.println("<p>Successfully logged in " + user.getfName() + " " + user.getlName() + "</p>");
                    out.println("<p><a href=\"index.jsp\">Return to Index</a></p>");
                }
                
               
            } else if ("signup".equals(action)) {

                //Validate Method will print out error if any of the input from parameters is invalid
 
                
                //Get the parameters
                String fName = request.getParameter("fName");
                String lName = request.getParameter("lName");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                //Create new user object and put the information in it
                User user = new User();
                user.setEmail(email);
                user.setfName(fName);
                user.setlName(lName);
                user.setPassword(password);

                //Put user object into session attribute
                session.setAttribute("user", user);
                getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
                

                out.println("<p>Successfully created new user " + user.getfName() + "</p>");
                out.println("<p><a href=\"index.jsp\">Return to Index</a></p>");

            out.println("</body>");
            out.println("</html>");
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
        
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
