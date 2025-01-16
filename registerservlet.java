package com.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registerservlet")
public class registerservlet extends HttpServlet {
    private static final String QUERY = "INSERT INTO book_data (bookname, bookedition, bookprice) VALUES (?, ?, ?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String bookname = req.getParameter("bookname");
        String bookedition = req.getParameter("bookedition");
        float bookprice;

        try {
            bookprice = Float.parseFloat(req.getParameter("bookprice"));
        } catch (NumberFormatException e) {
            out.println("<h2>Invalid price value</h2>");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            out.println("<h2>Database Driver not found</h2>");
            e.printStackTrace();
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/kiran", "root", "karan2002");
             PreparedStatement pstmt = con.prepareStatement(QUERY)) {

            pstmt.setString(1, bookname);
            pstmt.setString(2, bookedition);
            pstmt.setFloat(3, bookprice);

            int count = pstmt.executeUpdate();
            if (count == 1) {
                out.println("<h2>Record inserted successfully</h2>");
            } else {
                out.println("<h2>Record not inserted successfully</h2>");
            }

        } catch (SQLException e) {
            out.println("<h2>Error inserting record</h2>");
            out.println("<h3>" + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }
}
