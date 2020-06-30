<%@ page import="java.io.*,java.util.*,javax.mail.*"%>
<%@ page import="javax.mail.internet.*,javax.activation.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="java.sql.*" %>
<html>
<head>
<title>Send Email using JSP</title>
</head>
<body>
<%
			String e=(String)session.getAttribute("Email");
			System.out.println("here in sendtoken");
			String i=request.getParameter("name");
      %>       
 <%
 final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
   String result;
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        String from = "newton2faraday@gmail.com";
        String pass = "newton2faraday";
        String to = e;
          
        
        
        
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        
        
        /* props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");     
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.timeout", "25000");
         */
        
        Session mailsession = Session.getDefaultInstance(props);
   try
   {
        MimeMessage message = new MimeMessage(mailsession);       
        message.setFrom(new InternetAddress(from));   
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Token for file upload");
        message.setText("Your token for uploading file is:"+i);
        Transport transport = mailsession.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        result = "Sent message successfully....";
        HttpSession s=request.getSession(true);
        s.setAttribute("userName", e);
        %>
        <script type="text/javascript">
        alert("Token send to mail Successfully.Please check mail for token")
        <%
        RequestDispatcher rd=request.getRequestDispatcher("Check_token.jsp");
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
