<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="action.Action"%>
<%@ page import="dto.UserDTO"%>
<%@ page import="dto.CandidateDTO"%>
<%@ page import="dao.CandidateDAO"%>
<%@ page import="dao.VoteDAO"%>
<%@ page import="java.util.ArrayList"%>
<%
	Action action = new Action();
	UserDTO userDTO = new UserDTO();
	CandidateDTO candidateDTO = new CandidateDTO();
	CandidateDAO candidateDAO = new CandidateDAO();
	VoteDAO voteDAO = new VoteDAO();

	Boolean result;
	Object moveObject;

	request.setCharacterEncoding("UTF-8");
	HttpSession httpSession = request.getSession();

	result = (userDTO = action.getUserForSession(httpSession, userDTO)) != null;
	moveObject = result ? userDTO.getIdentification().equals("admin") ? null : action.MainJSP(response)
			: action.LoginJSP(response);

	if (result) {
		ArrayList<CandidateDTO> list;
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet"
	href="//cdn.jsdelivr.net/font-nanum/1.0/nanumbarungothic/nanumbarungothic.css">
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
				<span id="name">관리자</span> <input class="btn btn-gray" type="button"
					id="logout" value="로그아웃" onclick="location.href='../LogoutAction'">
			</div>
		</nav>
		<div id="content" class="shadow-box">
			<div id="header">투표 결과</div>
			<div id="candidate-result">
				<div class="candidate-result-list">
					<div class="list-title">학생회 선거 결과</div>
					<%
						list = candidateDAO.doList("학생회");
							if (list.size() == 1) {
								candidateDTO.setDivision("학생회");
								candidateDTO.setNumber(1);
								candidateDTO = candidateDAO.doDetail(candidateDTO);
								int temp = voteDAO.doResult(candidateDTO);
					%>
					<div class="candidate-statistic-cover">
						<div class="candidate-statistic">
							<div class="statistic-name"><%="기호 1번 " + candidateDTO.getMaster() + " 찬성"%></div>
							<div class="statistic-number">
								<span class="count"><%=temp%></span>표
							</div>
						</div>
					</div>
					<%
								candidateDTO.setNumber(2);
								temp = voteDAO.doResult(candidateDTO);
					%>
					<div class="candidate-statistic-cover">
						<div class="candidate-statistic">
							<div class="statistic-name"><%="기호 1번 " + candidateDTO.getMaster() + " 반대"%></div>
							<div class="statistic-number">
								<span class="count"><%=temp%></span>표
							</div>
						</div>
					</div>
					<%
						} else {
								for (int i = 0; i < list.size(); i++) {
									candidateDTO = list.get(i);
									int temp = voteDAO.doResult(candidateDTO);
					%>
					<div class="candidate-statistic-cover">
						<div class="candidate-statistic">
							<div class="statistic-name"><%="기호 " + candidateDTO.getNumber() + "번 " + candidateDTO.getMaster()%></div>
							<div class="statistic-number">
								<span class="count"><%=temp%></span>표
							</div>
						</div>
					</div>
					<%
						}
							}
					%>
				</div>
				<div class="candidate-result-list">
					<div class="list-title">자치부 선거 결과</div>
					<%
						list = candidateDAO.doList("자치부");
							if (list.size() == 1) {
								candidateDTO.setDivision("자치부");
								candidateDTO.setNumber(1);
								int temp = voteDAO.doResult(candidateDTO);
								candidateDTO = candidateDAO.doDetail(candidateDTO);
					%>
					<div class="candidate-statistic-cover">
						<div class="candidate-statistic">
							<div class="statistic-name"><%="기호 1번 " + candidateDTO.getMaster() + " 찬성"%></div>
							<div class="statistic-number">
								<span class="count"><%=temp%></span>표
							</div>
						</div>
					</div>
					<%
								candidateDTO.setNumber(2);
								temp = voteDAO.doResult(candidateDTO);
					%>
					<div class="candidate-statistic-cover">
						<div class="candidate-statistic">
							<div class="statistic-name"><%="기호 1번 " + candidateDTO.getMaster() + " 반대"%></div>
							<div class="statistic-number">
								<span class="count"><%=temp%></span>표
							</div>
						</div>
					</div>
					<%
						} else {
								for (int i = 0; i < list.size(); i++) {
									candidateDTO = list.get(i);
									int temp = voteDAO.doResult(candidateDTO);
					%>
					<div class="candidate-statistic-cover">
						<div class="candidate-statistic">
							<div class="statistic-name"><%="기호 " + candidateDTO.getNumber() + "번 " + candidateDTO.getMaster()%></div>
							<div class="statistic-number">
								<span class="count"><%=temp%></span>표
							</div>
						</div>
					</div>
					<%
						}
							}
					%>
				</div>
			</div>
			<div id="candidate-download">
				<div>
					<input type="button" class="candidate-btn" value="다운로드"
						style="margin: 0" onclick="location.href='../DownloadAction'">
				</div>
			</div>
		</div>
	</div>
</body>

</html>
<%
	}
%>