<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
</head>
<style>
	table {
		border-collapse:collapse;
		margin:40px auto;
	}
	table tr th {
		font-weight:700;
	}
	
	table tr td, table tr, th {
		border: 1px solid #818181;
		width: 200px;
		text-align:center;
	}
	a {
		text-decoration:none;
		color:#000;
		font-weigth:700;
	}
	h1 {
		text-align:center;
	}
	
	ul{
		width:400px;
		height:50px;
		margin:10px auto;
	}
	li{
		list-style:none;
		width:50px;
		line-height:50px;
		border:1px solid #ededed;
		float:left;
		text-align:center;
		margin:0 5px;
		border-radius:5px;
	}
	
</style>

<body>

<h1>회원 목록</h1>
	<table>
		<tr>
			<td colspan="3">전체 회원 수: ${pagination.count}</td>
			<td colspan="2">회원 레벨 정보</td>
		<tr>
			<th>NO</th>
			<th>ID</th>
			<th>이름</th>
			<th>LEVEL</th>
			<th>권한 변경</th>
		</tr>
		<c:forEach items="${list}" var="user" varStatus="status">
			<tr>
				<td><a href="user-detail.test?u_idx=${user.u_idx}">${user.rownum}</a></td>
				<td>${user.u_id}</td>
				<td>${user.u_name}</td>
				<td>${user.u_manage}</td>
				<td>
				<c:set var = "level" scope ="session" value = "${user.u_manage}"/>
					<c:choose>
						<c:when test = "${level == 9}">
							<form action="change-authority.test" method="post">
								<input type="hidden" name="u_manage" value="${user.u_manage}">
								<input type="hidden" name="u_idx" value="${user.u_idx}">
								<input type="submit" value="일반">
							</form>
						</c:when>
						
						<c:when test = "${level != 9}">
							<form action="change-authority.test" method="post">
								<input type="hidden" name="u_manage" value="${user.u_manage}">
								<input type="hidden" name="u_idx" value="${user.u_idx}">	
								<input type="submit" value="관리">
							</form>
						</c:when>
					</c:choose>
				</td>
				
				
			</tr>
			
		</c:forEach>
		
	</table>	
<!-- 아래부터 pagination -->
	<div>
		<ul>
			<c:choose>
				<%--${pagination.startPage > Pagination.pageUnit } --%>
				<c:when test="${pagination.prevPage <= pagination.startPage && pagination.prevPage > 0}">
					<li style="">
						<a href="user-list.test?page=${pagination.prevPage}">
							◀
						</a>
					</li>
				</c:when>
			</c:choose>
			<c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}" step="1">
				
				<c:choose>
					<c:when test="${pagination.page == i}">
						<li style="background-color:#ededed;">
							<span>${i}</span>
						</li>
					</c:when>
					<c:when test="${pagination.page != i}">
						<li>
							<a href="user-list.test?page=${i}">${i}</a>
						</li>
					</c:when>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${ pagination.nextPage <= pagination.lastPage }">
					<li style="">
						<a href="user-list.test?page=${ pagination.nextPage }">▶</a>
					</li>
				</c:when>
			</c:choose>
		</ul>
	</div>





	
</body>
</html>