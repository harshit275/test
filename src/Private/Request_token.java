package Private;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;


public class Request_token extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Request_token() {
        super();
    }
    String nm=null;
    Connection con=null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
	
		
		HttpSession session=request.getSession(false);  
        nm=(String)session.getAttribute("Username");
        System.out.println(nm);
        String t=request.getParameter("name");
        try
        {
        	
        	String token= Tokengen.calculateRFC2104HMAC(t, nm);
        	System.out.println("token : "+token);
        	Class.forName("com.mysql.jdbc.Driver");
        	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/HD","root","root");
        	String q="insert into token (Tag,Token,Name) values(?,?,?)";
        	PreparedStatement pt=con.prepareStatement(q);
        	pt.setString(1, t);
        	pt.setString(2, token);
        	pt.setString(3, nm);        
        	pt.executeUpdate();
        	
        	
        	HttpSession session1=request.getSession(true);
        	session1.setAttribute("Email", nm);
        	RequestDispatcher rd = request.getRequestDispatcher("/sendtoken.jsp?name="+token); 
        	
        	rd.include(request, response);
        	System.out.println("line 53");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
	}

}
