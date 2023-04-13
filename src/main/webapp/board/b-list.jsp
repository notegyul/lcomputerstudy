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
	table {
		boarder-collapse:collapse;
	}
	table tr td {
		font-weight:700;
	}
	table tr td, table tr th {
		border:1px solid #818181;
		width:200px;
		text-align:center;
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
					<td><a href="content-detail.test?b_idx=${contents.b_idx}">${contents.b_title}</a></td>
					<td>${contents.b_date}</td>
				</tr>
			</c:forEach>
			
		</table>
	
	
	
	
	
	
</body>
</html>