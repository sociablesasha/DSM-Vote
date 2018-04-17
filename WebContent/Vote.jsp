<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="action.Action"%>
<%@ page import="dto.UserDTO"%>
<%@ page import="dao.UserDAO"%>
<%@ page import="dto.CandidateDTO"%>
<%@ page import="dao.CandidateDAO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%
	Action action = new Action();	
	UserDTO userDTO = new UserDTO();
	UserDAO userDAO = new UserDAO();
	CandidateDTO candidateDTO = new CandidateDTO();
	CandidateDAO candidateDAO = new CandidateDAO();
	
	Boolean result;
	Object moveObject;

	request.setCharacterEncoding("UTF-8");
	HttpSession httpSession = request.getSession();
	
	result = (userDTO = action.getUserForSession(httpSession, userDTO)) != null;
	moveObject = result ? userDTO.getIdentification().equals("admin") ? action.AdminMainJSP(response) : null : action.LoginJSP(response);
	
	userDTO = userDAO.doGet(userDTO);
	
	result = result ? userDTO.getDone().equals("n") : false;
	moveObject = moveObject == null ? (result ? null : action.MainJSP(response)) : moveObject;

	if (result) {
		ArrayList<CandidateDTO> list;
		Iterator iterator;
		CandidateDTO listCandidateDTO;
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
		<div class="cover nav shadow-box">
			<a href="./Main.jsp">
				<div id="nav-logo"></div>
			</a>
			<div id="user-cover">
				<div id="name"><%=userDTO.getIdentification() + " " + userDTO.getName()%></div>
				<input type="button" class="btn" value="로그아웃" id="logout" onclick="location.href='./LogoutAction'">
			</div>
		</div>
		<div class="content">
			<div id="content-detail" class="cover shadow-box">
				<div id="super-title"> 투표장 </div>
				<div id="vote-detail"> 저희 2018년도 학교임원 선거 웹사이트는
					<br> 선거의 4대 원칙인 보통선거, 평등선거, 직접선거, 비밀선거가 모두 보장되며
					<br> 페이지 관리자조차 개개인이 누구를 뽑았는지 알 수 없습니다. </div>
				<div id="vote-paper-cover">
					<div class="vote-paper" id="student">
						<div class="title">학생회 회장 투표</div>
						<table border="1px solid black">
							<thead>
								<tr>
									<th class="num">기호</th>
									<th class="votename">후보자명</th>
									<th class="area">투표란</th>
								</tr>
							</thead>
							<tbody>
							<%
							list = candidateDAO.doList("학생회");
							if (list.size() == 1) {
								listCandidateDTO = list.get(0);	
							%>
								<tr>
								<td>찬성</td>
								<td><%=listCandidateDTO.getMaster()%> 찬성</td>
								<td class="vote-area" id="stu<%=listCandidateDTO.getNumber()%>"></td>
							</tr>
							<tr>
								<td>반대</td>
								<td><%=listCandidateDTO.getMaster()%> 반대</td>
								<td class="vote-area" id="stu<%=listCandidateDTO.getNumber() + 1%>"></td>
							</tr>
							<%
							} else {
								for (int i = 0; i < list.size(); i++) {
									listCandidateDTO = list.get(i);	
							%>
								<tr>
									<td><%=listCandidateDTO.getNumber()%></td>
									<td><%=listCandidateDTO.getMaster()%></td>
									<td class="vote-area" id="stu<%=listCandidateDTO.getNumber()%>"></td>
								</tr>
							<%
								}
							}
							%>
							</tbody>
						</table>
					</div>
					<div class="vote-paper" id="autonomy">
						<div class="title"> 자치부 부장 투표 </div>
						<table border="1px solid black">
							<thead>
								<tr>
									<th class="num">기호</th>
									<th class="votename">후보자명</th>
									<th class="area">투표란</th>
								</tr>
							</thead>
							<tbody>
															<%
							list = candidateDAO.doList("자치부");
							if (list.size() == 1) {
								listCandidateDTO = list.get(0);	
							%>
								<tr>
								<td>찬성</td>
								<td><%=listCandidateDTO.getMaster()%></td>
								<td class="vote-area" id="auto<%=listCandidateDTO.getNumber()%>"></td>
							</tr>
							<tr>
								<td>반대</td>
								<td><%=listCandidateDTO.getMaster()%></td>
								<td class="vote-area" id="auto<%=listCandidateDTO.getNumber() + 1%>"></td>
							</tr>
							<%
							} else {
								for (int i = 0; i < list.size(); i++) {
									listCandidateDTO = list.get(i);	
							%>
								<tr>
									<td><%=listCandidateDTO.getNumber()%></td>
									<td><%=listCandidateDTO.getMaster()%></td>
									<td class="vote-area" id="auto<%=listCandidateDTO.getNumber()%>"></td>
								</tr>
							<%
								}
							}
							%>
							</tbody>
						</table>
					</div>
				</div>
				<div id="vote-bottom">
					<div id="warning"> ※ 투표하기 버튼을 누르시면 다시 결과를 바꿀 수 없습니다. </div>
					<div id="vote-btn">
						<input type="button" class="btn" id="go_see" value="후보자 보러가기" onclick="location.href='./Detail.jsp'">
						<input type="button" value="투표하기" class="btn" id="votingbtn"> </div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var stuclick = "";
		var stuclickid = "";
		var autoclick = "";
		var autoclickid = "";
		$(document).ready(function () {
			$(".vote-area").click(function () {
				var choice = $(this).attr('id')
				if (choice.indexOf('stu') != -1) {
					$(stuclickid).css("background-image", "none");
					stuclick = $(this).attr('id');
					stuclickid = "#" + stuclick;
					$(stuclickid).css("background-image", "url(./img/vote.png)");
				}
				else {
					$(autoclickid).css("background-image", "none");
					autoclick = $(this).attr('id');
					autoclickid = "#" + autoclick;
					$(autoclickid).css("background-image", "url(./img/vote.png)");
				}
			});
			$("#votingbtn").click(function(){
				var $form = $('<form></form>');
				$form.attr('action', './VoteAction');
				$form.attr('method', 'post');
				$form.appendTo('body');
			    
				var government = $('<input type="hidden" value="' + stuclick.substring(3) + '" name="government">');
				var ministry = $('<input type="hidden" value="' + autoclick.substring(4) + '" name="ministry">');
				
				$form.append(government).append(ministry);
				$form.submit();
			});
		});
	</script>
</body>

</html>
<%
	}
%>