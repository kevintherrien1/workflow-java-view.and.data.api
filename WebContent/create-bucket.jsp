<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="./bootstrap.min.css">
<title>Create a Bucket</title>
</head>
<body>
<jsp:include page="header.jsp" />
<div class="container">
<form action="./create-bucket" method="POST">
	Create a bucket: <br>
	<input class="form-control inputDefault" type="text" name="bucket-name" size="30" placeholder="bucket name"><br>
	<input type="submit" value="Create a Bucket">
</form>
	<br>
	 ${create-bucket-response}

</div>
</body>
</html>