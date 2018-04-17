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
</head>

<body>
	<div id="wrapper">
		<div id="main-cover" class="cover shadow-box">
			<a href="./Main.jsp">
				<div id="logo"></div>
			</a>
			<div id="main-detail">2018년도 학생임원 선거 페이지 입니다. <br>무엇을 도와드릴까요?</div>
			<div id="main-btns">
				<input type="button" class="btn" id="go_see" value="후보자 보러가기" onclick="location.href='./Detail.jsp'">
				<input type="button" class="btn" id="go_vote" value="투표하러 가기" onclick="location.href='./Vote.jsp'">
			</div>
		</div>
	</div>
	

</body>

</html>
<%
	}
%>