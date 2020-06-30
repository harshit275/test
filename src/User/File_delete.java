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
import javax.servlet.http.HttpSession;

import Storage_server.Sql;

import com.ConnectionFactory;
public class File_delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public File_delete() {
        super();
    }
    Connection connection=null;
    PreparedStatement pt=null;
    String path=null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out=response.getWriter();
		path=request.getParameter("path");
		System.out.println(path);
		HttpSession session=request.getSession(false);
		String id=(String) session.getAttribute("ID");
		System.out.println(id);
		int count=0;
		String backup="C:/HD/Backup/";
		
		try 
		{	
			String nfn=path+".aes";
			File file=new File(nfn);
			
			String[] fn=path.split("\\/");
			File fff=new File(backup+fn[3]);
			
			String[] in=fn[3].split("\\.");
			if(in[1].equalsIgnoreCase("jpg")||in[1].equalsIgnoreCase("png")||in[1].equalsIgnoreCase("gif"))
			{
				System.out.println("this is image file");
				request.getRequestDispatcher("image_delete?path="+path).include(request, response);
			}
			else
			{
				System.out.println("this is text file");
				ResultSet rs=Sql.getusercount(path);
				while(rs.next())
				{
					count=rs.getInt(1);
				}
				if(count==1)
				{
					System.out.println("only one owner");
					file.delete();
						
					fff.delete();
					System.out.println("fnnn="+fn[3]);
					File fd=new File(backup+"/"+fn[3]+".aes");
					fd.delete();
					connection=ConnectionFactory.getInstance().getConnection();
					String q="delete from file where Filepath='"+path+"'";
					pt=connection.prepareStatement(q);
					pt.executeUpdate();
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('File Deleted Successfully.');");  
					out.println("</script>");    
					request.getRequestDispatcher("/View_files.jsp").include(request, response);	
				}	
				else
				{
					System.out.println("multiple owner="+count);
					connection=ConnectionFactory.getInstance().getConnection();
					String q="delete from file where id='"+id+"'";
					pt=connection.prepareStatement(q);
					pt.executeUpdate();
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('File Deleted Successfully.');");  
					out.println("</script>");    
					request.getRequestDispatcher("/View_files.jsp").include(request, response);	
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
