<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Run title</title>
	<%
		final String DATE_FORMAT_NOW = "yyyy/MM/dd HH:mm:ss";
	%>
</head>
<body>
	<div class="container">
		<h1>Info received</h1>
		<label>Bây giờ là: </label>
		<label><% SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_NOW); 
			String str = df.format(Calendar.getInstance().getTime()); %>
			<%=str %>
		</label>
		<div class="content">
			<label>Your name:</label>
			<input type="text" name="txt_name" value="<%=request.getParameter("txt_name") %>" />
		</div>
	</div>
</body>
</html>