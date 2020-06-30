package Storage_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ConnectionFactory;
public class Key_check extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Key_check() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out=response.getWriter();
		String key=request.getParameter("key");
		System.out.println(key);
		
		HttpSession session=request.getSession(false);
		String fn=(String) session.getAttribute("Filename");
		System.out.println(fn);
		String pt=(String) session.getAttribute("Path");
		System.out.println(pt);
		String nfn=pt+".aes";
		System.out.println(nfn);
		
		String recover="C:/HD/Recover/";
		File fl=new File(recover);
		if(!fl.exists())
		{
			fl.mkdir();
		}
		
		String[] fnn=fn.split("\\.");
		System.out.println("Filename="+fnn[1]);
		try 
		{
			if(fnn[1].equalsIgnoreCase("jpg")||fnn[1].equalsIgnoreCase("png")||fnn[1].equalsIgnoreCase("gif"))
			{
				System.out.println("Image");
				Connection con=ConnectionFactory.getInstance().getConnection();
				String q="select * from image where Filename='"+fn+"' and Tag='"+key+"'";
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery(q);
				if(rs.next())
					{
						response.setContentType("APPLICATION/OCTET-STREAM"); 
						response.setHeader("Content-Disposition","attachment; filename=\"" + fn + "\"");
						FileInputStream fi=new FileInputStream(pt);
						int i;
						while ((i=fi.read()) != -1) 
							{
								out.write(i); 
							} 
					}
				else
					{
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Enter Correct Secret Key');");  
						out.println("</script>");    
						request.getRequestDispatcher("/View_files.jsp").include(request, response);
					}
			}	
			else
			{	
				Connection con=ConnectionFactory.getInstance().getConnection();
				String q="select * from file where Filename='"+fn+"' and Tag='"+key+"'";
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery(q);
				if(rs.next())
					{
						AESdecryptor ae=new AESdecryptor();
						ae.decrypt(nfn,recover+fn+".aes");
						String dn=recover+fn+".aes.dec";
						System.out.println(dn);
						response.setContentType("APPLICATION/OCTET-STREAM"); 
						response.setHeader("Content-Disposition","attachment; filename=\"" + fn + "\"");
						FileInputStream fi=new FileInputStream(dn);
						int i;
						while ((i=fi.read()) != -1) 
							{
								out.write(i); 
							} 
					}
				else
					{
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Enter Correct Secret Key');");  
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
