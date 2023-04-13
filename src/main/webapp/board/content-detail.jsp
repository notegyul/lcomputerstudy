<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 확인하기</title>
</head>
<style>
	table {
		border-collpase:collapse;
	}
	table tr th {
		font-weigth:700;
	}
	table tr td, table tr, th {
		border:1px solid #818181;
		width:200px;
		text-align:center;
	}
	a {
		text-decoration:none;
		color:#000;
		font-weight:700;
		border:none;
		cursor:pointer;
		padding:10px;
		display:inline-block;		
	}

</style>
<body>
	<h1>게시글 상세</h1>
	<h4>제목: ${board.b_title}</h4>
	<h4>내용: ${board.b_content}</h4>
	<h4>작성자: ${board.b_writer}</h4>
	<h4>작성일: ${board.b_date}</h4>
	
	<table>
		<tr style="heigth:50px;">
			<td style="border:none;">
				<a href="content-edit.test?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:#818181;color:#fff;">수정</a>
			</td>
			<td style="border:none;">
				<form method="post">
					<input type="hidden" name="b_idx" value="${board.b_idx}">
					<a href="content-delete.test?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:red;color:#fff;">삭제</a>
				</form>
			</td>		
			<td style="border:none;">
			
				<input type="hidden" name="b_group" value="${board.b_group}">
				<a href="reply.test?b_group=${board.b_group}&b_order=${board.b_order}&b_depth=${board.b_depth}" style="width:70%;font-weight:700;background-color:#818181;color:#fff;">답글</a>
				
				
			</td>	
		</tr>
		
	</table>
	<br/>
	
	<form action="comment.test" name="com" method="post">
		<input type="hidden" name="u_idx" value="${sessionScope.user.u_idx}">
		
		<textarea rows="8" cols="20" placeholder="댓글 입력"></textarea>
		<br/>
		<input type=submit value="댓글등록">
	</form>
	<br/>
	<br/>
	<h4>${board.b_comment}</h4>	
	
	
	
	
	
	
</body>
</html>