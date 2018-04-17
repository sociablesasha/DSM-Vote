<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="action.Action"%>
<%@ page import="dto.UserDTO"%>
<%
	Action action = new Action();	
	UserDTO userDTO = new UserDTO();

	Boolean result;
	Object moveObject;

	request.setCharacterEncoding("UTF-8");
	HttpSession httpSession = request.getSession();
	
	/*
	*/
	//httpSession.setAttribute("identification", "30402");
	//httpSession.setAttribute("password", "kim123");
	//httpSession.setAttribute("name", "김용현");
	/*
	*/
	
	result = (userDTO = action.getUserForSession(httpSession, userDTO)) == null;
	moveObject = result ? null : userDTO.getIdentification().equals("admin") ? action.AdminMainJSP(response) : action.MainJSP(response);
	
	if (result) {
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="//cdn.jsdelivr.net/font-nanum/1.0/nanumbarungothic/nanumbarungothic.css">
	<link rel="stylesheet" href="./css/style.css">
	<script src="./js/jquery-3.3.1.min.js"></script>
	<title>DSM Vote</title>
</head>

<body>
	<div id="wrapper">
		<div id="login-cover" class="cover shadow-box">
			<a href="#">
				<div id="logo"></div>
			</a>
			<form id="login-form" action="./LoginAction" method="post">
				<div class="login-group">
					<label for="identification">아이디</label>
					<input type="text" id="identification" name="identification" required="required"> </div>
				<div class="login-group">
					<label for="password">비밀번호</label>
					<input type="password" id="password" name="password" required="required"> </div>
				<div class="login-group">
					<label for="name">이름</label>
					<input type="text" id="name" name="name" required="required"> </div>
				<input type="submit" id="login-btn" class="btn" value="로그인"> </form>
		</div>
	</div>
	
</body>
</html>
<%
	}
%>