package User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Storage_server.Sql;
public class response extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public response() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out=response.getWriter();
		String filename=request.getParameter("filename");
		System.out.println("Filename="+filename);
		String user=request.getParameter("user");
		System.out.println("user="+user);
		
		HttpSession session=request.getSession(false);
		String ownername=(String) session.getAttribute("userName");
		
		String key=null;
		
		String[] fn=filename.split("\\.");
		System.out.println("Filename="+fn[1]);
		try
		{
			if(fn[1].equalsIgnoreCase("jpg")||fn[1].equalsIgnoreCase("png")||fn[1].equalsIgnoreCase("gif"))
			{
				System.out.println("Image");
				ResultSet rs=Sql.getimgfiles(filename);
				if(rs.next())
				{
					key=rs.getString(3);
				}
				System.out.println("key="+key);
				
				int i=Sql.response(filename, user, ownername, key);
				if(i>0)
				{
					int k=Sql.updatestatus(user, filename);
					if(k>0)
					{
						System.out.println("Status Updated");
					}
					else
					{
						System.out.println("Status not updated");
					}
					response.sendRedirect("Send_path.jsp?user="+user);
				}
				else
				{
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Response Send Failed.......');");  
					out.println("</script>");    
					request.getRequestDispatcher("/view_request.jsp").include(request, response);
				}
			}
			else
			{
				System.out.println("text");
				ResultSet rs=Sql.getfiles(filename);
				if(rs.next())
				{
					key=rs.getString(3);
				}
				System.out.println("key="+key);
				
				int i=Sql.response(filename, user, ownername, key);
				if(i>0)
				{
					int k=Sql.updatestatus(user, filename);
					if(k>0)
					{
						System.out.println("Status Updated");
					}
					else
					{
						System.out.println("Status not updated");
					}
					response.sendRedirect("Send_path.jsp?user="+user);
				}
				else
				{
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Response Send Failed.......');");  
					out.println("</script>");    
					request.getRequestDispatcher("/view_request.jsp").include(request, response);
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
