<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="commentList">
	<tr>
		<th>내용</th>
		<th>작성자</th>
		<th>작성일시</th>
		<th>기능</th>
	</tr>
	<c:forEach items="${cList}" var="comment">
		<tr>
			<td>${comment.b_comment}</td>
			<td>${comment.c_group}</td>
			<td>${comment.c_date}</td>
			<td>
				<button class="btn_comment">댓글</button>
				<button class="btn_modify_comment" bidx="${comment.b_idx}">수정</button>
				<button class="btn_delete_comment" bidx="${comment.b_idx}" cidx="${comment.c_idx}">삭제</button>
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
				<button class="btn_edit_reg_comment" bidx="${comment.b_idx}" uidx="${comment.u_idx}"  cidx="${comment.c_idx}" cdate="${comment.c_date}" cgroup="${comment.c_group }" corder="${comment.c_order }" cdepth="${comment.c_depth }" >등록</button>
				<button class="btn_edit_cancel_comment">취소</button>
			</td>
		</tr>
	</c:forEach>
</table>