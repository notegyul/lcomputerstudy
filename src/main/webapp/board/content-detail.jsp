<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 확인하기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
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
		<input type="hidden" name="b_idx" value="${board.b_idx}">
			
		<textarea rows="8" cols="20" name="b_comment" placeholder="댓글 입력"></textarea>
		<br/>
		<input type=submit value="댓글등록">
	</form>
	<br/>
	<br/>
	
	<table>
		<tr>
			<th>내용</th>
			<th>작성자</th>
			<th>작성일시</th>
			<th>기능</th>
		</tr>
		<c:forEach items="${cList}" var="comment">
			<tr>
				<td>${comment.b_comment}</td>
				<td></td>
				<td>${comment.c_date}</td>
				<td>
					<button class="btn_comment">댓글</button>
					<button class="btn_modify_comment">수정</button>
					<button class="btn_delete_comment">삭제</button>
				</td>
			</tr>
			<tr style="display: none;">
				<td colspan="3">
					<textarea rows="2" cols="80"></textarea>
				</td>
					
				<td>
					<button class="btn_reg_comment">등록</button>
					<button class="btn_cancel_comment">취소</button>
				</td>
			</tr>
			<tr style="display: none;">
				<td colspan="3">
					<textarea rows="2" cols="80">${comment.b_comment}</textarea>
				</td>

				<td>
					<button class="btn_reg_comment">등록</button>
					<button class="btn_cancel_comment">취소</button>
				</td>
			</tr>
		</c:forEach>	
	</table>
	
<script>
$(document).on('click', '.btn_reg_comment', function () {
	let contents = $(this).parent().prev().find('textarea').val();
	console.log(contents);
	
	$.ajax({
		method: "POST",
		url: "edit.do",
		data: { b_comment: contents }
	})
	.done(function( msg ) {
		alert( "Data Saved: " + msg );
	});
});

$(document).on('click', '.btn_comment', function () {
	$(this).parent().parent().next().css('display', '');
	
	
});

$(document).on('click', '.btn_cancel_comment', function(){
	$(this).parent().parent().css('display','none');
});

$(document).on('click', '.btn_modify_comment', function(){
	$(this).parent().parent().next().next().css('display','');
});




</script>
</body>
</html>