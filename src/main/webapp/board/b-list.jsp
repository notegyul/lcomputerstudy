<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
</head>
<style>
	table, div {
		boarder-collapse:collapse;
		margin:40px auto;
	}
	
	div {
		
	}
	table tr td {
		font-weight:700;
	}
	table tr td, table tr th {
		border:1px solid #818181;
		width:200px;
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
		width:500px;
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
	<h1>게시글 목록</h1>
		<table>
			<tr>
				<th>제목</th>
				<th>작성일</th>
			</tr>
			<c:forEach items="${bList}" var="contents">
				<tr>
					<td><a href="content-detail.test?b_idx=${contents.b_idx}&u_idx=${contents.u_idx}">${contents.b_title}</a></td>
					<td>${contents.b_date}</td>
				</tr>
			</c:forEach>
			
		</table>
		
		
		<div>
			<ul>
				<c:choose>
					<%--${pagination.startPage > Pagination.pageUnit } --%>
					<c:when test="${pagination.prevPage <= pagination.startPage && pagination.prevPage > 0}">
						<li style="">
							<a href="title-list.test?page=${pagination.prevPage}&type=${pagination.search.type}&keyword=${pagination.search.keyword}">
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
								<a href="title-list.test?page=${i}&type=${pagination.search.type}&keyword=${pagination.search.keyword}">${i}</a>
							</li>
						</c:when>
					</c:choose>
				</c:forEach>
				<c:choose>
					<c:when test="${ pagination.nextPage <= pagination.lastPage }">
						<li style="">
							<a href="title-list.test?page=${ pagination.nextPage }&type=${pagination.search.type}&keyword=${pagination.search.keyword}">▶</a>
						</li>
					</c:when>
				</c:choose>
			</ul>
		</div>
		
		
		
		
		
		<br/>
		
		<div>	
			<form action="title-list.test" method="get">
				<select name="type">
					<option value="0">검색</option>
					<option value="1">제목</option>
					<option value="2">제목+내용</option>
					<option value="3">작성자</option>
				</select>
				<input type="text" name="keyword">
				<input type="submit" value="검색하기">
			</form>
		</div>
	
	
	
	
</body>
</html>