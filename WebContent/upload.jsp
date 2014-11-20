<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charsxet=UTF-8">
<link type="text/css" rel="stylesheet" href="./bootstrap.min.css">
<title>Upload</title>
</head>

<body>
<jsp:include page="header.jsp" />
<div class="container">
<br>
<form action="./upload" method="POST">
	<div>
		<input type="file" id="files" name="file" />
	</div>
	<br>
	<span>
    <input type="submit" value="Upload File">
	</span>
</form>
</div>
</body>
</html>