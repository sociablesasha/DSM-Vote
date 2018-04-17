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
	
	result = (userDTO = action.getUserForSession(httpSession, userDTO)) != null;
	moveObject = result ? userDTO.getIdentification().equals("admin") ? action.AdminMainJSP(response) : null : action.LoginJSP(response);
	
	if (result) {
		httpSession.invalidate();
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="//cdn.jsdelivr.net/font-nanum/1.0/nanumbarungothic/nanumbarungothic.css">
	<link rel="stylesheet" href="./css/style.css">
	<title>DSM Vote</title>
	<script>
		function login() {
			location.href="./Login.jsp";
		}
	</script>
</head>

<body onLoad="setTimeout('login()', 3000)">
	<div id="wrapper">
		<div id="done-cover" class="cover shadow-box">
			<a href="#">
				<div id="logo"></div>
			</a>
			<div id="done-detail">소중한 한 표 감사합니다. <br>자동 로그아웃됩니다.</div>
			<input type="button" class="btn" id="done-btn" value="로그인 화면으로" onclick="login()">
		</div>
	</div>
</body>

</html>
<%
	}
%>