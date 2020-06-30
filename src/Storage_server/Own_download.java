package Storage_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
public class Own_download extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Own_download() {
        super();
    }
	@SuppressWarnings("resource")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out=response.getWriter();
		boolean bool=false;
		
		String id=request.getParameter("id");
		System.out.println("File Id="+id);
		
		String path=request.getParameter("filename");
		System.out.println(path);
		
		String backup="C:/HD/Backup/";
		String recover="C:/HD/Recover/";
		File fl=new File(recover);
		if(!fl.exists())
		{
			fl.mkdir();
		}
		
		String[] imm=path.split("\\/");
		String[] ext=imm[3].split("\\.");
		if(ext[1].equalsIgnoreCase("jpg")||ext[1].equalsIgnoreCase("png")||ext[1].equalsIgnoreCase("gif"))
		{
			System.out.println("image");
			String imgback=backup+imm[3];
			File f=new File(path);
			bool=f.exists();
			if(bool==true)
			{
				System.out.println("image exist");
				response.setContentType("APPLICATION/OCTET-STREAM"); 
				response.setHeader("Content-Disposition","attachment; filename=\"" + imm[3] + "\"");
				FileInputStream fi=new FileInputStream(path);
				int i;
				while ((i=fi.read()) != -1) 
				{
				   out.write(i); 
				} 		
			}
					
			else
			{
				System.out.println("image doesnot exist");
				File source=new File(imgback);
				File dest=new File(path);
				FileUtils.copyFile(source, dest);
				
				response.setContentType("APPLICATION/OCTET-STREAM"); 
				response.setHeader("Content-Disposition","attachment; filename=\"" + imm[3] + "\"");
				FileInputStream fi=new FileInputStream(path);
				int i;
				while ((i=fi.read()) != -1) 
				{
				   out.write(i); 
				} 	
			}
		}
		else
		{
			System.out.println("continue");
			String nfn=path+".aes";
			System.out.println(nfn);
			String dn=recover+imm[3]+".aes.dec";
			try
			{
				File f=new File(nfn);
				bool=f.exists();
				if(bool==true)
					{
						System.out.println("File Exists");
						String fn=request.getParameter("path");
						
							AESdecryptor ae=new AESdecryptor();
							ae.decrypt(nfn,recover+imm[3]+".aes");
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
						System.out.println("File Doesnot Exist");	
						String[] fn=path.split("\\/");
						System.out.println("Filename="+fn[3]);
						
						File source=new File(backup+fn[3]+".aes");
						File dest=new File(nfn);
						FileUtils.copyFile(source, dest);
						
						String fnn=request.getParameter("path");
						
							AESdecryptor ae=new AESdecryptor();
							ae.decrypt(nfn,recover+imm[3]+".aes");
							dn=nfn+".dec";
							System.out.println(dn);
							response.setContentType("APPLICATION/OCTET-STREAM"); 
							response.setHeader("Content-Disposition","attachment; filename=\"" + fnn + "\"");
							FileInputStream fi=new FileInputStream(dn);
							int i;
							while ((i=fi.read()) != -1) 
							{
							   out.write(i); 
							} 
						}
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}	
	}

}
