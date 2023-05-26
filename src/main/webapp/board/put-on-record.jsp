<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 등록하기</title>
</head>
<body>

	<form action="register-process.test" name="board" method="post" enctype="multipart/form-data">
		<h1>게시판에 글 올리기</h1>
		<input type="text" name="title" placeholder="제목 입력">
		<br/>
		<textarea rows="8" cols="20" name="content" placeholder="내용 입력"></textarea>
		<br/>
		
		<p>file1: <input type="file" name="file_origin"></p>
		
		<br/>
		<input type="submit" value="등록하기">
	</form>
	
</body>
</html>