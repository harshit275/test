package Storage_server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;


import com.ConnectionFactory;

import User.AESencryptor;

public class Token_check extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public Token_check() {
        super();
    }
    Connection con=null;
	String message = null;
	String nm=null;
	String path=null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession(false);  
        nm=(String)session.getAttribute("Username");
        System.out.println(nm);
        path=(String) session.getAttribute("Filepath");
        System.out.println(path);
        Part part=(Part) session.getAttribute("Filedata");
        String tag=(String) session.getAttribute("Tag"); 
        System.out.println(tag);
        String fn=(String) session.getAttribute("Filename");
        System.out.println(fn);
        
      	String backup="C:\\HD\\Backup";
      	
      	 File fileSaveDir=new File(backup);
         if(!fileSaveDir.exists()){
             fileSaveDir.mkdir();
         }
        
        String token=request.getParameter("token");
        System.out.println(token);
        
        HttpSession session2=request.getSession(true);
        session2.setAttribute("Token", token);
        
        
        try 
        {
        	con=ConnectionFactory.getInstance().getConnection();
        	byte[]key=tag.getBytes("UTF-8");
          	MessageDigest sha=MessageDigest.getInstance("SHA-1");
          	key=sha.digest(key);
          	key=Arrays.copyOf(key, 16);
          	SecretKeySpec k=new SecretKeySpec(key,"AES");
          	System.out.println("Key="+k);
          	
        	ResultSet rst=Sql.getcount();
        	while(rst.next())
        	{	
        		if(rst.getInt(1)==1)
        			{
        				part.write(path);
        				
        				AESencryptor ae=new AESencryptor();
        				ae.encrypt(path, k);
        				
        				File file=new File(path);
        				file.delete();
        				
        				File source=new File(path+".aes");
        				File dest=new File(backup+"\\"+fn+".aes");
        				FileUtils.copyFile(source, dest);
        				
        				Sql.insertfile(fn,tag,path,nm,token);
        				{
        					System.out.println("File Write successfully");   
        					out.println("<script type=\"text/javascript\">");  
	                    	out.println("alert('File Uploaded successfully');");  
	                    	out.println("</script>");    
	                        getServletContext().getRequestDispatcher("/file_upload.jsp").include(request, response);
        				}	
        			}
        		else
        		{
                	ResultSet rs=Sql.checktoken(token, nm);
                	if(rs.next())
                	{	
                		File file=new File(path);
                		file.delete();
                		out.println("<script type=\"text/javascript\">");  
        				out.println("alert('File is Duplicate.Prove your IDENTITY to Get File Path');");  
        				out.println("</script>");    
        				request.getRequestDispatcher("/Pow.jsp").include(request, response);
                	}
                	else
                	{
                		ResultSet rst1=Sql.checktag(tag);
                		if(rst1.next())
                		{
                			System.out.println("file is already available with user="+rst1.getString(5));
                			File ff=new File(path);
                			ff.delete();
                			Sql.insertfile(fn,tag,rst1.getString(4),nm,token);
                			out.println("<script type=\"text/javascript\">");  
	                    	out.println("alert('File Uploaded successfully');");  
	                    	out.println("</script>");    
	                        request.getRequestDispatcher("/file_upload.jsp").include(request, response);
                		}
                		else
                		{	
                			AESencryptor ae=new AESencryptor();
                			ae.encrypt(path, k);
                			File file=new File(path);
                			file.delete();
                			
                			File source=new File(path+".aes");
            				File dest=new File(backup+"\\"+fn+".aes");
            				FileUtils.copyFile(source, dest);
            				
                			int i=Sql.insertfile(fn, tag, path, nm, token);
                			if(i>0)
                				{
                					System.out.println("File Write successfully"); 
                					out.println("<script type=\"text/javascript\">");  
        	                    	out.println("alert('File Uploaded successfully');");  
        	                    	out.println("</script>");    
        	                        getServletContext().getRequestDispatcher("/file_upload.jsp").include(request, response);
                				}	
                		}	
                	}

        		}
        	}	       	
		} 
        catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
