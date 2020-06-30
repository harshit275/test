package com11;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import com.ConnectionFactory;
public class Mailcheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Mailcheck() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		HttpSession s=request.getSession(false);
		String e=(String) s.getAttribute("Email");
		System.out.println(e);
		String t=request.getParameter("name");
		
		try 
		{
			Connection con=ConnectionFactory.getInstance().getConnection();
			Statement st=con.createStatement();
			String q="select * from user";
			ResultSet i=st.executeQuery(q);
			while (i.next())
			{
			if(i.getString(4).contains(e))
			{
				HttpSession session=request.getSession(true);
				session.setAttribute("Email", e);
				System.out.println("Email Exist");
				RequestDispatcher rd=request.getRequestDispatcher("sendtoken.jsp?name="+t);
				rd.include(request, response);
			}
			else 
			{
				out.println("<script type=\"text/javascript\">");  
				out.println("alert('Email ID doesnot exist. Provide Correct Email iD');");  
				out.println("</script>");    
				request.getRequestDispatcher("/file_upload.jsp").include(request, response);
			}
			}
		} 
		catch (Exception e2)
		{
			e2.printStackTrace();
		}
	}

}
