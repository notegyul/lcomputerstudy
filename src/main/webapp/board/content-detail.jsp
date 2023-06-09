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

	<h2>게시글 상세</h2>
	<table border="1" >
		<colgroup>
			<col width="15%" /> <col width="35%" />
			<col width="15%" /> <col width="*" />

		</colgroup>
		
		<tr>
			<td>번호</td><td>${board.b_idx}</td>
			<td>작성자</td><td>${board.b_writer}</td>
		</tr>
		<tr>
			<td>조회수</td><td>${board.b_count}</td>
			<td>작성일</td><td>${board.b_date}</td>
		</tr>
		<tr>
			<td>제목</td><td>${board.b_title}</td>
		</tr>
		<tr>
			<td >내용</td>
			<td colspan="3" height="100" >${board.b_content}</td>
		</tr>
		
		<tr>
			<td>첨부파일</td>
			<td>
				<c:if test="${not empty board.file_origin}">
				${board.file_origin}
				<a href="download.test?file_origin=${board.file_origin}&file_serv=${board.file_serv}&b_idx=${board.b_idx}">[다운로드]</a>
				</c:if>
			</td>
		</tr>
		
		
		
		
	
	</table>
	
	<div>
		<img src="http://localhost:8080/lcomputerstudy/img/${board.file_serv}"/>
	</div>
		
	
	<br/>
	<br/>
	<br/>
	<table>
		<tr style="heigth:50px;">
			<c:set var="auth" value="${board.u_idx}"/>
			<c:if test="${auth == sessionScope.user.u_idx}">
				<td style="border:none;">
					<a href="content-edit.test?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:#818181;color:#fff;">수정</a>
				</td>
			</c:if>
			
			<c:set var="auth" value="${board.u_idx}"/>
			<c:set var="level" value="${user.u_manage}"/>
			<c:choose>
				<c:when test="${level == 9}">
					<td style="border:none;">
						<input type="hidden" name="b_idx" value="${board.b_idx}">
						<a href="content-delete.test?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:red;color:#fff;">삭제</a>
					</td>
				</c:when>
				<c:when test="${auth == sessionScope.user.u_idx}">
					<td style="border:none;">
						<input type="hidden" name="b_idx" value="${board.b_idx}">
						<a href="content-delete.test?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:red;color:#fff;">삭제</a>
					</td>
				</c:when>
			</c:choose>
				
			<td style="border:none;">
				<a href="reply.test?b_group=${board.b_group}&b_order=${board.b_order}&b_depth=${board.b_depth}" style="width:70%;font-weight:700;background-color:#818181;color:#fff;">답글</a>
			</td>
			
			<td style="border:none;">
				<a href="title-list.test" style="width:70%;font-weight:700;background-color:brown;color:#fff;">글 목록</a>
			</td>	
			
		</tr>
		
	</table>
	<br/>
	<br/>
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
	
	<table id="commentList">
		<tr>
			<th>내용</th>
			<th>작성자</th>
			<th>작성일시</th>
			<th>기능</th>
		</tr>
		<%--여기 cList는 comment.test에서 content-detail.test?b_idx=~~~로 redirect해서(view.jsp 아님) 그쪽 컨트롤러에서 cList 이름으로 setAttribute 한 거임  --%>
		<c:forEach items="${cList}" var="comment">
			<tr bidx="${comment.b_idx}">
				<td>${comment.b_comment}</td>
				<td></td>
				<td>${comment.c_date}</td>
				<td>
					<button class="btn_comment" cgroup="${comment.c_group}" corder="${comment.c_order}" cdepth="${c_depth}">댓글</button>
					<button class="btn_modify_comment">수정</button>
					<button class="btn_delete_comment" bidx="${comment.b_idx}" uidx="${comment.u_idx}" cidx="${comment.c_idx}">삭제</button>
				</td>
			</tr>
			
			<tr style="display: none;">
				<td colspan="3">
					<textarea rows="2" cols="80"></textarea>
				</td>
					
				<td>
					<button class="btn_re_reg_comment"  bidx="${comment.b_idx}" uidx="${comment.u_idx}" cgroup="${comment.c_group }" corder="${comment.c_order }" cdepth="${comment.c_depth }" >등록</button>
					<button class="btn_cancel_comment">취소</button>
				</td>
			</tr>
			
			<tr style="display: none;">
				<td colspan="3">
					<textarea rows="2" cols="80">${comment.b_comment}</textarea>
				</td>

				<td>
					<button class="btn_edit_reg_comment" bidx="${comment.b_idx}" uidx="${comment.u_idx}" cidx="${comment.c_idx}" cdate="${comment.c_date}">등록</button>
					<button class="btn_cancel_comment">취소</button>
				</td>
			</tr>
		</c:forEach>	
	</table>
	
<script>
$(document).on('click', '.btn_reg_comment', function () {
	let contents = $(this).parent().prev().find('textarea').val();
	//console.log(contents);
	//let cIdx = $(this).attr('cidx');
	let bIdx = $(this).attr('bidx');
	let uIdx = $(this).attr('uidx');
	let cGroup = $(this).attr('cgroup');
	let cOrder = $(this).attr('corder');
	let cDepth = $(this).attr('cdepth');
	
	
	$.ajax({
		method: "POST",
		url: "aj-write-comment.test",
		data: { b_comment: contents,  b_idx: bIdx, u_idx:uIdx, c_group:cGroup, c_order:cOrder, c_depth:cDepth }
	})
	.done(function( data ) {
		$('#commentList').html(data);
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

$(document).on('click', '.btn_edit_reg_comment', function(){
	let contents = $(this).parent().prev().find('textarea').val();
	let bIdx = $(this).attr('bidx');
	let uIdx = $(this).attr('uidx');
	let cIdx = $(this).attr('cidx');
	let cDate = $(this).attr('cdate');
	
	$.ajax({
		method: "POST",
		url: "aj-edit-comment.test",
		data: {b_comment:contents, b_idx: bIdx, u_idx:uIdx, c_idx:cIdx, c_date:cDate}
	})
	.done(function(data){
		$('#commentList').html(data);
	});
});

$(document).on('click', '.btn_delete_comment', function(){
	
	let contents = $(this).parent().parent().prev().find('th').text();
	
	let uIdx = $(this).attr('uidx');

	let bIdx = $(this).attr('bidx');
	let cIdx = $(this).attr('cidx');
	
	$.ajax({
		method: "POST",
		url: "aj-delete-comment.test",
		data: {c_idx:cIdx,b_idx:bIdx,u_idx:uIdx,b_comment:contents}
	})
	.done(function(data){
		$('#commentList').html(data);
	});
});

$(document).on('click','.btn_re_reg_comment', function(){
	let contents = $(this).parent().prev().find('textarea').val();
	let bIdx = $(this).attr('bidx');
	let uIdx = $(this).attr('uidx');
	let cGroup = $(this).attr('cgroup');
	let cOrder = $(this).attr('corder');
	let cDepth = $(this).attr('cdepth');
	
	$.ajax({
		method: "POST",
		url: "aj-re-comment.test",
		data: {b_comment:contents, b_idx:bIdx, u_idx:uIdx, c_group:cGroup, c_order:cOrder, c_depth:cDepth}
	})
	.done(function(data){
		$('#commentList').html(data);
	});
	
});


</script>
</body>
</html>