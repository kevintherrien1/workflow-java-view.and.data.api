<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
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
	Access Token: ${tokenResponse}
	<br><br>	
	
</div>

</body>
</html>