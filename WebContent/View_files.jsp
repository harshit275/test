<!DOCTYPE html>
<%@page import="Storage_server.Sql"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>View Uploaded Files </title>
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
<%
	String nm=(String)session.getAttribute("userName");
%>
<% 
	int count=0;
	if(session.getAttribute("userName")==null)
	{
		 out.print("Please login first");  
         request.getRequestDispatcher("user_signin.jsp").include(request, response);  
	}
	else
	{
		ResultSet rs=Sql.getreqcount((String)session.getAttribute("userName"));
		if(rs.next())
		{
			count=rs.getInt(1);
		}
%>
	<div class="navbar navbar-inverse">
		<div class="container">
		<h4 align="right"> <font color="red">Welcome <%=session.getAttribute("userName")%></font></h4> 
			<div class="navbar-header">
				<!-- Button for smallest screens -->
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
				<h4><a class="navbar-brand" href="Index.jsp">
					Hybrid DEDUPLICATION</a></h4>
			</div>
	<%
		if(count>0)
		{
	%>		
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav pull-right mainNav">
					<li class="active"><a href="user_home.jsp">Home</a></li>
					<li class="active"><a href="file_upload.jsp">Browse File</a></li>
			    	<li class="active"><a href="View_files.jsp">Own Files</a></li>
			    	<li class="active"><a href="View_all.jsp">View Files</a></li>
			    	<li class="active"><a href="view_request.jsp"><span class="badge badge-orange"><%=count %></span>Request</a>
			    	</li>
			    	<li class="active"><a href="View_response.jsp">Response</a></li>
			    	<li class="active"><a href="Profile.jsp">Profile</a></li>
					<li class="active"><a href="LogoutServlet">Logout</a></li>
            </ul>          
			</div>
	<%
		}
		else
		{
	%>
		<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav pull-right mainNav">
					<li class="active"><a href="user_home.jsp">Home</a></li>
					<li class="active"><a href="file_upload.jsp">Browse File</a></li>
			    	<li class="active"><a href="View_files.jsp">Own Files</a></li>
			    	<li class="active"><a href="View_all.jsp">View Files</a></li>
			    	<li class="active"><a href="view_request.jsp">Request</a>
			    	</li>
			    	<li class="active"><a href="View_response.jsp">Response</a></li>
			    	<li class="active"><a href="Profile.jsp">Profile</a></li>
					<li class="active"><a href="LogoutServlet">Logout</a></li>
            </ul>          
			</div>		
	<%
		}
	%>
		</div>
		</div>
<div style="margin-top: 30px;margin-left: 20px;margin-right: 20px">
 <TABLE class="table table-bordered">
            <TR class="danger">            	
               <TH>File Name</TH>
               <TH>Secret Key</TH>
               <Th>Delete</Th>
               <Th>Download</Th>
            </TR>
<% 
	String query="select * from file where Email='"+nm+"' Union select * from image where Email='"+nm+"' ";
	Connection con=ConnectionFactory.getInstance().getConnection();
    Statement st=con.createStatement();
	ResultSet resultset=st.executeQuery(query);
	while(resultset.next())
	{
		String path=resultset.getString(4);
		String id=resultset.getString(1);
		session.setAttribute("ID", id);
%>
 <TR>
               <TD> <%= resultset.getString(2) %></TD>
               <TD> <%= resultset.getString(3) %></TD>
              <TD> <a href="File_delete?path=<%=path %>"><input type="button" name="Delete" value="Delete" class="btn btn-danger"></a></TD>
              <TD><a href="Own_download?id=<%=id%>&filename=<%=path%>&path=<%=resultset.getString(2)%>"> <button type="button" class="btn btn-info">Download</button></a></TD>
           </TR>
<% 
   } 
		
%>
        </TABLE>
<div class="modal fade" id="myModal">
    <div class="modal-dialog modal-lg" style="width: 35%">
 <form action="Key_check" method="post" autocomplete="off">  
      <div class="modal-content">
        <div class="modal-header"  style="background-color: gray;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title" align="center">Enter Secret Key To Get File</h4>
        </div>
        <div class="modal-body">
        <div class="form-group" align="left">
            <input type="text" class="form-control"  placeholder="Enter Your Secret Key Here" name="key" required>
        </div>
        <div align="left">
        <button type="submit" class="btn btn-success">Submit</button>
        </div>
        </div>
        </div>
        </form>
</div>
</div>        
 </div>
 <%
	}
 %>
	<script src="assets/js/modernizr-latest.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
	<script src="assets/js/jquery.cslider.js"></script>
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/jquery-1.3.2.min.js"></script>

</body>
</html>
