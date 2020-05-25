/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helper.hibernateutils.UsersHBU;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;

/**
 *
 * @author AE
 */
public class LoginController extends HttpServlet {

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
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(Exception e){}
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember");
            
            PrintWriter out = response.getWriter();

            if(remember == null){
                Cookie[] cookies = request.getCookies();
                for(Cookie c : cookies){
                    if(c.getName().equals("USER_EMAIL")){
                        c.setMaxAge(0); //invalidate
                        response.addCookie(c);
                    }else if (c.getName().equals("USER_PASSWORD")){
                        c.setMaxAge(0); //invalidate
                        response.addCookie(c);
                    }
                    
                }
            }else{
                Cookie emailCookie = new Cookie("USER_EMAIL", email);
                Cookie passwordCookie = new Cookie("USER_PASSWORD", password);
                
                emailCookie.setMaxAge(3600 * 24 * 7);
                passwordCookie.setMaxAge(3600 * 24 * 7);
                
                response.addCookie(emailCookie);
                response.addCookie(passwordCookie);
                
            }
            Users u = UsersHBU.auth(email, password);

//            out.println("<h1>"+u.getEmail()+"</h1>");
//            out.println("<h1>"+u.getPassword()+"</h1>");
//            out.println("<h1>"+u.getName()+"</h1>");
        
            if(u == null){
                response.sendRedirect(request.getContextPath() + "/login?error=1");
                return;
            }
            
            HttpSession sess = request.getSession();
            sess.setAttribute("LOGIN_USER", u);
            
            response.sendRedirect(request.getContextPath() + "/home");
            
            //destroy all session
            //sess.invalidate();
        
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
        processRequest(request, response);
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
