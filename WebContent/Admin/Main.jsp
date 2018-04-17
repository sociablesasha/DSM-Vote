<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="action.Action"%>
<%@ page import="dto.UserDTO"%>
<%@ page import="dto.CandidateDTO"%>
<%@ page import="dao.CandidateDAO"%>
<%@ page import="java.util.ArrayList"%>
<%
	Action action = new Action();
	UserDTO userDTO = new UserDTO();
	CandidateDTO candidateDTO = new CandidateDTO();
	CandidateDAO candidateDAO = new CandidateDAO();

	Boolean result;
	Object moveObject;

	request.setCharacterEncoding("UTF-8");
	HttpSession httpSession = request.getSession();

	result = (userDTO = action.getUserForSession(httpSession, userDTO)) != null;
	moveObject = result ? userDTO.getIdentification().equals("admin") ? null : action.MainJSP(response) : action.LoginJSP(response);

	if (result) {
		ArrayList<CandidateDTO> list;
		CandidateDTO listCandidateDTO;
		String saveDirectory = getServletContext().getRealPath("/Upload");
%>
		
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<!-- TODO no cache -->
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<link rel="stylesheet" href="//cdn.jsdelivr.net/font-nanum/1.0/nanumbarungothic/nanumbarungothic.css">
	<link rel="stylesheet" href="./css/style.css">
	<script src="./js/jquery-3.3.1.min.js"></script>
	<title>DSM Vote</title>
</head>

<body>
	<div id="wrapper">
		<nav class="shadow-box">
			<a href="">
				<div id="nav-logo">
					<img src="./img/logo.png" alt="">
					<div>관리자 페이지</div>
				</div>
			</a>
			<div id="nav-list">
				<ul>
					<li><a href="./Main.jsp">후보자 명단</a></li>
					<li><a href="./Result.jsp">투표 결과</a></li>
				</ul>
			</div>
			<div id="nav-user">
				<span id="name">관리자</span>
				<input class="btn btn-gray" type="button" id="logout" value="로그아웃" onclick="location.href='../LogoutAction'">
			</div>
		</nav>
		<div id="content" class="shadow-box">
			<div id="header">후보자 명단</div>
			<div class="candidate-list">
				<div class="list-title">학생회 회장 후보</div>
				<div class="hr"></div>
				<div class="candidate=card-cover">
				<%
					list = candidateDAO.doList("학생회");
					for (int i = 0; i < list.size(); i++) {
					listCandidateDTO = list.get(i);
				%>
					<div class="candidate-card">	
						<img class="candidate-photo"
							src="<%=action.fileCheck(listCandidateDTO, saveDirectory, "Picture", "masterGovernment")
								? "../Upload/Picture/학생회/" + listCandidateDTO.getNumber() + "/masterGovernmentPicture.png?ver=1"
								: "./img/noPhoto.png"%>">
						<div class="candidate-explane">
							<div class="candidate-name"><%="기호 " + listCandidateDTO.getNumber() + "번"%><span><%=listCandidateDTO.getMaster()%></span></div>
							<div class="candidate-btns">
								<input type="button" class="candidate-edit" value="후보자 수정" onclick="location.href='./Update.jsp?division=<%=listCandidateDTO.getDivision()%>&number=<%=listCandidateDTO.getNumber()%>'">
								<input type="button" class="candidate-delete" value="삭제" onclick="location.href='../DeleteAction?division=<%=listCandidateDTO.getDivision()%>&number=<%=listCandidateDTO.getNumber()%>'">
							</div>
						</div>
					</div>
				<%
					}
				%>
				</div>
			</div>
			<div class="candidate-list">
				<div class="list-title">자치부 부장 후보</div>
				<div class="hr"></div>
				<div class="candidate=card-cover">
									<%
					list = candidateDAO.doList("자치부");
					for (int i = 0; i < list.size(); i++) {
					listCandidateDTO = list.get(i);	
				%>
					<div class="candidate-card">	
						<img class="candidate-photo"
							src="<%=action.fileCheck(listCandidateDTO, saveDirectory, "Picture", "masterMinistry")
								? "../Upload/Picture/자치부/" + listCandidateDTO.getNumber() + "/masterMinistryPicture.png?ver=1"
								: "./img/noPhoto.png"%>">
						<div class="candidate-explane">
							<div class="candidate-name"><%="기호 " + listCandidateDTO.getNumber() + "번"%><span><%=listCandidateDTO.getMaster()%></span></div>
							<div class="candidate-btns">
								<input type="button" class="candidate-edit" value="후보자 수정" onclick="location.href='./Update.jsp?division=<%=listCandidateDTO.getDivision()%>&number=<%=listCandidateDTO.getNumber()%>'">
								<input type="button" class="candidate-delete" value="삭제" onclick="location.href='../DeleteAction?division=<%=listCandidateDTO.getDivision()%>&number=<%=listCandidateDTO.getNumber()%>'">
							</div>
						</div>
					</div>
				<%
					}
				%>
				</div>
			</div>
			<div id="candidate-btn-cover">
				<input type="button" value="후보자 등록" id="candidate-apply" class="candidate-btn" onclick="location.href='./Write.jsp'">
			</div>
		</div>
	</div>
</body>

</html>
<%
	}
%>