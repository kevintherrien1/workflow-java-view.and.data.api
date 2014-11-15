<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Simple Java Sample</title>
</head>
<body>

	<jsp:include page="header.jsp" />


	<form action="./access-token" method="POST">
		Consumer Key: <input type="text" name="key" size="50" value=""><br>
		Consumer Secret: <input type="text" name="secret" size="50" value=""><br>
		<br> <input type="submit" value="Get Access Token">
	</form>

	Access Token: ${token}

</body>
</html>