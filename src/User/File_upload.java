package User;

import java.io.File;
import java.io.PrintWriter;

import java.io.IOException;

import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import Storage_server.Sql;



@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)

public class File_upload extends HttpServlet 
{
	private static final long serialVersionUID = -1445651683541116182L;
	private static final String SAVE_DIR="HD";
	Connection con=null;
	String message = null;
	String nm=null;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
            {
    	PrintWriter out=response.getWriter();
    	try
    	{
    		response.setContentType("text/html;charset=UTF-8");
            	HttpSession session=request.getSession(false);  
                nm=(String)session.getAttribute("Username");
                System.out.println(nm);
                String savePath = "C:/" + SAVE_DIR+"/"+"File";
                    File fileSaveDir=new File(savePath);
                    if(!fileSaveDir.exists()){
                        fileSaveDir.mkdir();
                    }
                Part part=request.getPart("file");
                long length=part.getSize();
                
                System.out.println(length);
                String fileName=extractFileName(part);
                String filePath= savePath + "/" + fileName ;
               //String filePath = fileName;
                
                String[] fn=fileName.split("\\.");
                System.out.println("file extension="+fn[1]);
                
                if(fn[1].equalsIgnoreCase("jpg")||fn[1].equalsIgnoreCase("png")||fn[1].equalsIgnoreCase("gif"))
                {
                	ResultSet rs=Sql.getfiles(fileName);
                	if(rs.next())
                	{
                		out.println("<script type=\"text/javascript\">");  
            			out.println("alert('File name is already available.Please change filename');");  
            			out.println("</script>");    
            			request.getRequestDispatcher("/file_upload.jsp").include(request, response);
                	}
                	else
                	{
                		System.out.println("this is image file");
                		HttpSession s=request.getSession(true);
                		s.setAttribute("Name",nm);
                		s.setAttribute("Filedata", part);
                		s.setAttribute("Filename", fileName);
                		request.getRequestDispatcher("Imageupload_servlet").include(request, response);
                	}
                }
                else
                {
                	System.out.println("text file");
    			
                	ResultSet rs=Sql.getfiles(fileName);
                	if(rs.next())
                		{
                			out.println("<script type=\"text/javascript\">");  
                			out.println("alert('File name is already available.Please change filename');");  
                			out.println("</script>");    
                			request.getRequestDispatcher("/file_upload.jsp").include(request, response);
                		}
                	else
                		{
                		System.out.println("line 94 : "+filePath);
                			part.write(filePath); 
                			System.out.println("line 96 : "+filePath);
                			File file = new File(filePath);
                			
                			String tag = HashGeneratorUtils.generateSHA256(file);
                			System.out.println("Tag: " + tag);
                			HttpSession s=request.getSession(true);
                			s.setAttribute("Name",nm);
                			s.setAttribute("Filepath", filePath);
                			s.setAttribute("Filedata", part);
                			s.setAttribute("Tag", tag);
                			s.setAttribute("Filename", fileName);
                			RequestDispatcher rd=request.getRequestDispatcher("Request_token?name="+tag);
                			rd.include(request, response);
                		}	
              }
    	}
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    	
    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}