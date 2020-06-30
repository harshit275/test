package com11;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Storage_server.Sql;

public class forgotpassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public forgotpassword() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out=response.getWriter();
		String email=request.getParameter("emailsignup");
		System.out.println("Email="+email);
		
		try 
		{
			ResultSet rs=Sql.checkmail(email);
			if(rs.next())
			{
				response.sendRedirect("sendpassword.jsp?user="+email+"&password="+rs.getString(3));
			}
			else
			{
				out.println("<script type=\"text/javascript\">");  
				out.println("alert('Mail ID Doesnot Exist. Please Enter Correct Email ID.');");  
				out.println("</script>");    
				request.getRequestDispatcher("/user_signin.jsp").include(request, response);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
