/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class createServlet extends HttpServlet {

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
            out.println("<title>Servlet createServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            
            String tableName = request.getParameter("tableName");
            int numColumns = Integer.parseInt(request.getParameter("numColumns"));
            String[] columnNames = request.getParameter("columnNames").split(",");
            
            StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (");
            for (int i = 0; i < numColumns; i++) {
                sql.append(columnNames[i].trim()).append(" VARCHAR(255)");
                if (i < numColumns - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
                Statement stmt = con.createStatement();
                stmt.execute(sql.toString());
                response.getWriter().println("Table " + tableName + " created successfully.");
                
                request.setAttribute("tableName", tableName);
                RequestDispatcher dispatcher = request.getRequestDispatcher("insert.html");
                dispatcher.include(request, response);    
            }
            catch(Exception e)
            {
               e.printStackTrace();
               response.getWriter().println("Error creating table: " + e.getMessage());
            }
            
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
