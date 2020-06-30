<!DOCTYPE html>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>View Stored Files Page</title>
	<link rel="favicon" href="assets/images/favicon.png">
	<link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/css/font-awesome.min.css">
	<link rel="stylesheet" href="assets/css/bootstrap-theme.css" media="screen">
	<link rel="stylesheet" type="text/css" href="assets/css/da-slider.css" />
	<link rel="stylesheet" href="assets/css/style.css">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
</head>
<body>
	<div class="navbar navbar-inverse">
		<div class="container">
		<h4 align="right"> <font color="red">Welcome <%=session.getAttribute("userName")%></font></h4> 
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
				<h4><a class="navbar-brand" href="Index.jsp">
					Hybrid DEDUPLICATION</a></h4>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav pull-right mainNav">
					<li class="active"><a href="Admin_home.jsp">Home</a></li>
			    	<li class="active"><a href="View_user.jsp">View Users</a></li>
			    	<li class="active"><a href="View_datastorage.jsp">View Files</a></li>
			    	<li class="active"><a href="View_imagestorage.jsp">View Images</a></li>
					<li class="active"><a href="admin_signin.jsp">Logout</a></li>
            </ul>                             
			</div>
		</div>
		</div>
<div style="margin-top: 30px;margin-left: 20px;margin-right: 20px">
 <TABLE class="table table-bordered">
            <TR class="danger">            	
               <TH>ID</TH>
               <TH>File Name</TH>
               <Th>File Path</Th>
               <Th>Tag</Th>
               <Th>Token</Th>
               <Th>User Name</Th>
            </TR>
<% 
	String query="select * from file";
	Connection con=ConnectionFactory.getInstance().getConnection();
    Statement st=con.createStatement();
	ResultSet resultset=st.executeQuery(query);
	while(resultset.next())
	{
%>
 <TR>
               <TD> <%= resultset.getString(1) %></TD>
               <TD> <%= resultset.getString(2) %></TD>
               <TD> <%= resultset.getString(4) %></TD>
               <TD> <%= resultset.getString(3) %></TD>
               <TD> <%= resultset.getString(6) %></TD>
               <TD> <%= resultset.getString(5) %></TD>
           </TR>
<% 
   } 
		
%>
        </TABLE>
</div>
	<script src="assets/js/modernizr-latest.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
	<script src="assets/js/jquery.cslider.js"></script>
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/jquery-1.3.2.min.js"></script>
</body>
</html>
