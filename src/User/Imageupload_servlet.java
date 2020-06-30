package User;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

import Private.Tokengen;
import Storage_server.Sql;
import Storage_server.imagecheck;

public class Imageupload_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Imageupload_servlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		PrintWriter out=response.getWriter();
		
		ArrayList<String>al=new ArrayList<>();
		
		HttpSession session=request.getSession(false);
		String username=(String) session.getAttribute("Name");
		System.out.println("user="+username);
		String filename=(String) session.getAttribute("Filename");
		System.out.println("filename="+filename);
		Part part=(Part) session.getAttribute("Filedata");
		
		String imagepath="C:/HD/image";
		String backuppath="C:/HD/Backup";
	
		File fileSaveDir=new File(imagepath);
        if(!fileSaveDir.exists())
        {
            fileSaveDir.mkdir();
        }
        
        String img=imagepath+"/"+filename;
        part.write(img);
        
  
		try 
		{
			String tag = HashGeneratorUtils.generateSHA256(new File(img));
			System.out.println("Tag: " + tag);
			
			String token=Tokengen.calculateRFC2104HMAC(tag, username);
			System.out.println("token="+token);
			
			Sql.insertimagetoken(tag, token, username);
			
			ResultSet rs=Sql.getimgcount();
			while(rs.next())
			{
				if(rs.getInt(1)==1)
				{
					File source=new File(imagepath+"/"+filename);
					File dest=new File(backuppath+"/"+filename);
					FileUtils.copyFile(source, dest);
					Sql.insertimage(filename,tag,img,username,token);
					out.println("<script type=\"text/javascript\">");  
                	out.println("alert('File Uploaded successfully');");  
                	out.println("</script>");    
                    getServletContext().getRequestDispatcher("/file_upload.jsp").include(request, response);
				}
				else
				{
					ResultSet rt=Sql.getallimages();
					while(rt.next())
					{
						String file=rt.getString(2);
						
						String[] ff=file.split("\\.");
						if(ff[1].equalsIgnoreCase("jpg")||ff[1].equalsIgnoreCase("png")||ff[1].equalsIgnoreCase("gif"))
						{
							al.add(file);
						}	
					}
					
					for(String s:al)
					{
						System.out.println("File path="+s);
						File fileone=new File(imagepath+"/"+s);
						File filetwo=new File(img);
						boolean b=false;
						b=imagecheck.compareTwoImages(fileone, filetwo);
						System.out.println("match or not="+b);
						double match=imagecheck.p;
						double notmatch=imagecheck.pr;
						if(match>=70||notmatch<=30)
						{
							File ff=new File(img);
							ff.delete();
							out.println("<script type=\"text/javascript\">");  
	        				out.println("alert('File is Duplicate');");  
	        				out.println("</script>");    
	        				request.getRequestDispatcher("/file_upload.jsp").include(request, response);
						}
						else
						{
							File source=new File(imagepath+"/"+filename);
							File dest=new File(backuppath+"/"+filename);
							FileUtils.copyFile(source, dest);
							Sql.insertimage(filename,tag,img,username,token);
							out.println("<script type=\"text/javascript\">");  
		                	out.println("alert('File Uploaded successfully');");  
		                	out.println("</script>");    
		                    getServletContext().getRequestDispatcher("/file_upload.jsp").include(request, response);
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
