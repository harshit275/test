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
			String user=request.getParameter("user");
			System.out.println("user="+user);
 %>       
 <%
   String result;
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        String from = "bestpeerproject@gmail.com";
        String pass = "bestpeer";
        String to = user;
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
        message.setSubject("Secret Key For File Access");
        message.setText("Secret key is successfully sending to you.Please login to system and check response.");
        Transport transport = mailsession.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        result = "Sent message successfully....";
        %>
        <script type="text/javascript">
        alert("Response send successfully")
        <%
        RequestDispatcher rd=request.getRequestDispatcher("view_request.jsp");
        rd.include(request, response);
        %>
        </script>
 <%         
    }catch (MessagingException mex) {
      mex.printStackTrace();
      result = "Error: unable to send message....";
    }
%>
</body>
</html>
