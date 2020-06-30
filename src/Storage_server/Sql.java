package Storage_server;

import java.sql.*;

import com.ConnectionFactory;

public class Sql 
{
	static Connection conn=null;
	static Statement stmt=null;
	static ResultSet rs=null;
	static PreparedStatement ps=null;
	
	
	public static ResultSet getcount() throws SQLException
	{
		String q="select count(*) from token";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(q);
		return rs;
	}
	
	public static int insertfile(String Filename,String Tag,String Filepath,String Email,String Token)
	{
		int i=0;
		try
		{			
			conn=ConnectionFactory.getInstance().getConnection();
			stmt=(Statement) conn.createStatement();
			 i=stmt.executeUpdate("insert into file (Filename,Tag,Filepath,Email,Token) values('"+Filename+"','"+Tag+"','"+Filepath+"','"+Email+"','"+Token+"')");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	
	public static ResultSet checktoken(String token,String email) throws SQLException
	{
		String query="select * from file where Token='"+token+"' and Email='"+email+"'";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(query);
		return rs;
	}
	
	public static ResultSet checktag(String tag) throws SQLException
	{
		String query="select * from file where Tag='"+tag+"'";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(query);
		return rs;
	}
	
	public static ResultSet getmail(String email) 
	{
		try 
		{
			String query="select * from user where Email='"+email+"'";
			conn=ConnectionFactory.getInstance().getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(query);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ResultSet getfiles(String filename) throws SQLException
	{
		String q="select * from file where Filename='"+filename+"'";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(q);
		return rs;
	}
	
	public static ResultSet getusercount(String path) throws SQLException
	{
		String q="select count(*) from file where Filepath='"+path+"'";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(q);
		return rs;
	}
	
	public static int request(String Filename,String username,String ownername,String Status)
	{
		int i=0;
		try
		{			
			conn=ConnectionFactory.getInstance().getConnection();
			stmt=(Statement) conn.createStatement();
			 i=stmt.executeUpdate("insert into request (username, Filename, Ownername, Status) values('"+username+"','"+Filename+"','"+ownername+"','"+Status+"')");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	
	public static int updatestatus(String username,String filename)
	{
		int i=0;
		try
		{			
			conn=ConnectionFactory.getInstance().getConnection();
			stmt=(Statement) conn.createStatement();
			 i=stmt.executeUpdate("delete from request where username='"+username+"' and Filename='"+filename+"';");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	
	public static int response(String Filename,String username,String ownername,String key)
	{
		int i=0;
		try
		{			
			conn=ConnectionFactory.getInstance().getConnection();
			stmt=(Statement) conn.createStatement();
			i=stmt.executeUpdate("insert into response (user, Filename, FileKey, sender) values ('"+username+"','"+Filename+"','"+key+"','"+ownername+"')");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	
	public static ResultSet getreqcount(String username) throws SQLException
	{
		String q="select count(*) from request where Ownername='"+username+"'";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(q);
		return rs;
	}
	
	public static int inserttoken(String tag,String token,String Name)
	{
		int i=0;
		try
		{	
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/HD","root","root");
			stmt=(Statement) conn.createStatement();
			i=stmt.executeUpdate("insert into token (Tag,Token,Name) values('"+tag+"','"+token+"','"+Name+"')");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	
	public static ResultSet getallfiles() throws SQLException
	{
		String q="select * from file";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(q);
		return rs;
	}
	
	public static ResultSet checkmail(String email) throws SQLException
	{
		String q="select * from user where Email='"+email+"'";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(q);
		return rs;
	}
	
	public static int insertimagetoken(String tag,String token,String Name)
	{
		int i=0;
		try
		{	
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/HD","root","root");
			stmt=(Statement) conn.createStatement();
			i=stmt.executeUpdate("insert into image_token (Tag,Token,Name) values('"+tag+"','"+token+"','"+Name+"')");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	
	public static ResultSet getimgcount() throws SQLException
	{
		String q="select count(*) from image_token";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(q);
		return rs;
	}
	
	public static int insertimage(String Filename,String Tag,String Filepath,String Email,String Token)
	{
		int i=0;
		try
		{			
			conn=ConnectionFactory.getInstance().getConnection();
			stmt=(Statement) conn.createStatement();
			 i=stmt.executeUpdate("insert into image (Filename,Tag,Filepath,Email,Token) values('"+Filename+"','"+Tag+"','"+Filepath+"','"+Email+"','"+Token+"')");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	
	public static ResultSet getallimages() throws SQLException
	{
		String q="select * from image";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(q);
		return rs;
	}
	
	public static ResultSet gettoken(String path) throws SQLException
	{
		String q="select * from image where Filepath='"+path+"'";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(q);
		return rs;
	}
	public static ResultSet getimgfiles(String filename) throws SQLException
	{
		String q="select * from image where Filename='"+filename+"'";
		conn=ConnectionFactory.getInstance().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(q);
		return rs;
	}
	
}
