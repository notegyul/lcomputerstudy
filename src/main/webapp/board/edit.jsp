<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>
	<form action="content-edit-process.test" method="post">
		<input type="hidden" name="b_idx" value="${board.b_idx}">
		<h1>게시글 수정하기</h1>
		<p>제목: <input type="text" name="edit-title" value="${board.b_title}"></p>
		 
		<textarea rows="8" cols="20" name="edit-content">${board.b_content}</textarea>
		
		<button>수정 완료</button>
	</form>
</body>
</html>

