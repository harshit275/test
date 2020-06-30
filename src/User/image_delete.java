package User;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Storage_server.Sql;

import com.ConnectionFactory;

public class image_delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public image_delete() {
        super();
    }
    Connection connection=null;
    PreparedStatement pt=null,ptmt=null;
    String path=null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out=response.getWriter();
		String backup="C:/HD/Backup/";
		String path=request.getParameter("path");
		System.out.println("Path on delete servlet="+path);
		try 
		{
			File file=new File(path);
			file.delete();
			String[] fn=path.split("\\/");
			File fff=new File(backup+fn[3]);
			fff.delete();
			
			String token=null;
			ResultSet rs=Sql.gettoken(path);
			if(rs.next())
			{
				token=rs.getString(6);
			}
			System.out.println("image token="+token);
			
			connection=ConnectionFactory.getInstance().getConnection();
			String q="delete from image where Filepath='"+path+"'";
			pt=connection.prepareStatement(q);
			pt.executeUpdate();
			pt.close();
			
			String query="delete from image_token where Token='"+token+"'";
			ptmt=connection.prepareStatement(query);
			ptmt.executeUpdate();
			ptmt.close();
			
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('File Deleted Successfully.');");  
			out.println("</script>");    
			request.getRequestDispatcher("/View_files.jsp").include(request, response);	
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
