<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 달기</title>
</head>
<body>
	<form action="reply-process.test" method="post">
		<input type="hidden" name="u_idx" value="${sessionScope.user.u_idx}">
		<input type="hidden" name="b_group" value="${board.b_group}" >
		<input type="hidden" name="b_order" value="${board.b_order}">
		<input type="hidden" name="b_depth" value="${board.b_depth}">
		<h1>원글에 답글 달기</h1>
		<input type="text" name="title" placeholder="제목 입력">
		<br/>
		<textarea rows="8" cols="20" name="content" placeholder="내용 입력"></textarea>
		<br/>
		<input type="submit" value="등록하기">
	</form>
</body>
</html>