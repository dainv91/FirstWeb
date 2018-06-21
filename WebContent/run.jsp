<%@page import="java.nio.file.FileSystems"%>
<%@page import="java.nio.file.FileSystem"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Run title</title>
	<%
		final String DATE_FORMAT_NOW = "yyyy/MM/dd HH:mm:ss";
	%>
</head>
<body>
	<div class="container">
		<h1>Changing info in ws</h1>
		<label>Bây giờ là: </label>
		<label><% SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_NOW); 
			String str = df.format(Calendar.getInstance().getTime()); %>
			<%
				str = Paths.get("").toAbsolutePath().toString();
				//str = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
			%>
			<%=str %>
		</label>
		<div class="content">
			<form action="update.do" method="post">
				<label>Your name:</label>
				<input type="text" name="txt_name" />
				<input type="submit" value="Update info" />
			</form>
		</div>
	</div>
</body>
</html>