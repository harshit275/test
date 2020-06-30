package User;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Storage_server.Sql;

public class Request_keyservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Request_keyservlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out=response.getWriter();
		String filename=request.getParameter("filename");
		System.out.println("Filename="+filename);
		
		String ownername=request.getParameter("ownername");
		System.out.println("owner="+ownername);
		
		HttpSession session=request.getSession(false);
		String username=(String) session.getAttribute("userName");
		System.out.println("user="+username);
		
		String status="Pending";
		
		int i=Sql.request(filename, username, ownername,status);
		if(i>0)
		{
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Request Send Successfully.......');");  
			out.println("</script>");    
			request.getRequestDispatcher("/View_all.jsp").include(request, response);
		}
		else
		{
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Request Sending Failed.......');");  
			out.println("</script>");    
			request.getRequestDispatcher("/View_all.jsp").include(request, response);
		}
	}
}
