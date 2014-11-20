<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="./bootstrap.min.css">
<title>Access Token</title>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container">


	<form action="./access-token" method="POST">
		Consumer Key: 

		<input class="form-control inputDefault" type="text" name="key" size="30"><br>
		Consumer Secret: 

		<input class="form-control inputDefault" type="text" name="secret" size="30"><br>
		<br> <input type="submit" value="Get Access Token">
	</form>
	
	<br>
	Access Token: ${token}
	<br><br>	
	
</div>

</body>
</html>