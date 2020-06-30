<%@page import="com.ConnectionFactory"%>
<%@ page import="java.io.*,java.util.*,javax.mail.*"%>
<%@ page import="javax.mail.internet.*,javax.activation.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="java.sql.*" %>
<html>
<head>
<title>Send File Storage Path</title>
</head>
<body>
<%
	String token=(String)session.getAttribute("Token");
	System.out.println(token);
	String email=request.getParameter("identity");
	Connection con=ConnectionFactory.getInstance().getConnection();
	String query="select * from file where Token='"+token+"' and Email='"+email+"'";
	Statement st=con.createStatement();
	ResultSet rs=st.executeQuery(query);
	if(rs.next())
	{	
		String path=rs.getString(4);
   		String result;
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        String from = "bestpeerproject@gmail.com";
        String pass = "bestpeer";
        String to = email;
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");     
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.timeout", "25000");
        Session mailsession = Session.getDefaultInstance(props);
   try
   {
        MimeMessage message = new MimeMessage(mailsession);       
        message.setFrom(new InternetAddress(from));   
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("File pointer");
        message.setText("The File you are rying to upload is already availabel at location:"+path);
        Transport transport = mailsession.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        result = "Sent message successfully....";
        %>
        <script type="text/javascript">
        alert("Path send to mail Successfully.Please check mail for File Path")
        <%
        RequestDispatcher rd=request.getRequestDispatcher("file_upload.jsp");
        rd.include(request, response);
        %>
        </script>
 <%         
    }
   catch (MessagingException mex) 
   {
      mex.printStackTrace();
      result = "Error: unable to send message....";
    }
	}
	else
	{
%>
	<script type="text/javascript">
        alert("Please Enter Correct Details")
        <%
        RequestDispatcher rd=request.getRequestDispatcher("Pow.jsp");
        rd.include(request, response);
        %>
        </script>	
<% 		
	}
%>
</body>
</html>
