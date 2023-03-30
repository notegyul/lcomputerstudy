<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action ="user-editProcess.test" method="post">
		<h1>회원 정보 수정</h1>
		<p>아이디: <input type="text" name="edit_id" ></p>
		<p>비밀번호: <input type="password" name="edit_pw" ></p>
		<p>이름: <input type="text" name="edit_name" ></p>
		<p>연락처: <input type="text" maxlength="4" size="4" name="edit_tel1" >-<input type="text" maxlength="4" size="4" name="edit_tel2" >-<input type="text" maxlength="4" size="4" name="edit_tel3" ></p>
		<p>나이: <input type="text" name="edit_age" ></p>
		<p><button>수정완료</button></p> 
			
	</form>

</body>
</html>



