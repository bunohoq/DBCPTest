<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="http://bit.ly/3WJ5ilK" />
	<style>
		
	</style>
</head>
<body>
	<!-- dbcptest.jsp -->
	<h1>DBCP Test</h1>
	
	<div>
		<form method="GET" action="/dbcp/dbcptest.do">
			<input type="submit" value="데이터 요청하기">
			<input type="hidden" name="no" value="${no}">
		</form>
		<hr>
		<div>
			<div>no: ${no}</div>
			<div>name: ${name}</div>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
		
	</script>
</body>
</html>

