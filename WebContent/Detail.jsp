<%@ page language="java"%>
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
	moveObject = result ? userDTO.getIdentification().equals("admin") ? action.AdminMainJSP(response) : null : action.LoginJSP(response);

	if (result) {
		candidateDTO.setDivision(request.getParameter("division") == null
				? null : request.getParameter("division"));
		candidateDTO.setNumber(Integer.valueOf(request.getParameter("number") == null
				? "0" : request.getParameter("number")));
		
		ArrayList<CandidateDTO> list;
		CandidateDTO listCandidateDTO;
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
<link rel="stylesheet"
	href="//cdn.jsdelivr.net/font-nanum/1.0/nanumbarungothic/nanumbarungothic.css">
<link rel="stylesheet" href="./css/style.css">
<script src="./js/jquery-3.3.1.min.js"></script>
<script src="./js/marked.min.js"></script>
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
			<div class="side-menu cover shadow-box">
				<ul>
					<li>학생회 후보</li>
					<div class="hr"></div>
					<ul>
						<%
						list = candidateDAO.doList("학생회");
						for (int i = 0; i < list.size(); i++) {
							listCandidateDTO = list.get(i);	
						%>
						<li><a href="?division=<%=listCandidateDTO.getDivision()%>&number=<%=listCandidateDTO.getNumber()%>">
								<div class="li-style"></div>기호 <%=listCandidateDTO.getNumber()%>번
						</a></li>
						<%
						}
						%>
					</ul>

					<li>자치부 후보</li>
					<div class="hr"></div>
					<ul>
						<%
						list = candidateDAO.doList("자치부");
						for (int i = 0; i < list.size(); i++) {
							listCandidateDTO = list.get(i);	
						%>
						<li><a href="?division=<%=listCandidateDTO.getDivision()%>&number=<%=listCandidateDTO.getNumber()%>">
								<div class="li-style"></div>기호 <%=listCandidateDTO.getNumber()%>번
						</a></li>
						<%
						}
						%>
					</ul>
				</ul>
				<input type="button" class="btn" id="go_vote" value="투표하러 가기" onclick="location.href='./Vote.jsp'" style="width: 270.5px; height: 50px;">
			</div>
			<div></div>
			<div id="content-detail" class="cover shadow-box">
				<%
					if ((candidateDTO = candidateDAO.doDetail(candidateDTO)) != null) {
							String saveDirectory = getServletContext().getRealPath("/Upload");
				%>
				<div id="super-title"><%=candidateDTO.getDivision() + " 기호 " + candidateDTO.getNumber() + "번"%></div>
				<div id="member">
					<div class="title">구성 인원</div>
					<div class="hr"></div>
					<%
						if (candidateDTO.getDivision().equals("학생회")) {
					%>
					<div id="member-cover">
						<div class="member">
							<img class="member-photo" id="photo1"
								src="<%=action.fileCheck(candidateDTO, saveDirectory, "Picture", "masterGovernment")
								? "./Upload/Picture/학생회/" + candidateDTO.getNumber() + "/masterGovernmentPicture.png?"
								: "./img/noPhoto.png"%>">
							<div class="member-detail">
								<div class="member-position">학생회 회장 후보</div>
								<div class="member-name"><%=candidateDTO.getMaster()%></div>
							</div>
						</div>
						<div class="member">
							<img class="member-photo" id="photo2"
								src="<%=action.fileCheck(candidateDTO, saveDirectory, "Picture", "slave1Government")
								? "./Upload/Picture/학생회/" + candidateDTO.getNumber() + "/slave1GovernmentPicture.png?"
								: "./img/noPhoto.png"%>">
							<div class="member-detail">
								<div class="member-position">학생회 3학년 부회장 후보</div>
								<div class="member-name"><%=candidateDTO.getSlave1()%></div>
							</div>
						</div>
						<div class="member">
							<img class="member-photo" id="photo3"
								src="<%=action.fileCheck(candidateDTO, saveDirectory, "Picture", "slave2Government")
								? "./Upload/Picture/학생회/" + candidateDTO.getNumber() + "/slave2GovernmentPicture.png?"
								: "./img/noPhoto.png"%>">
							<div class="member-detail">
								<div class="member-position">학생회 2학년 부회장 후보</div>
								<div class="member-name"><%=candidateDTO.getSlave2()%></div>
							</div>
						</div>
						<div class="member">
							<img class="member-photo" id="photo4"
								src="<%=action.fileCheck(candidateDTO, saveDirectory, "Picture", "slave3Government")
								? "./Upload/Picture/학생회/" + candidateDTO.getNumber() + "/slave3GovernmentPicture.png?"
								: "./img/noPhoto.png"%>">
							<div class="member-detail">
								<div class="member-position">학생회 1학년 부회장 후보</div>
								<div class="member-name"><%=candidateDTO.getSlave3()%></div>
							</div>
						</div>
					</div>
					<%
						} else if (candidateDTO.getDivision().equals("자치부")) {
					%>
					<div id="member-cover">
						<div class="member">
							<img class="member-photo" id="photo1"
								src="<%=action.fileCheck(candidateDTO, saveDirectory, "Picture", "masterMinistry")
								? "./Upload/Picture/자치부/" + candidateDTO.getNumber() + "/masterMinistryPicture.png?"
								: "./img/noPhoto.png"%>">
							<div class="member-detail">
								<div class="member-position">자치부 부장</div>
								<div class="member-name"><%=candidateDTO.getMaster()%></div>
							</div>
						</div>
						<div class="member">
							<img class="member-photo" id="photo2"
								src="<%=action.fileCheck(candidateDTO, saveDirectory, "Picture", "slaveMinistry")
								? "./Upload/Picture/자치부/" + candidateDTO.getNumber() + "/slaveMinistryPicture.png?"
								: "./img/noPhoto.png"%>">
							<div class="member-detail">
								<div class="member-position">자치부 차장</div>
								<div class="member-name"><%=candidateDTO.getSlave1()%></div>
							</div>
						</div>
						<div class="member"></div>
						<div class="member"></div>
					</div>
					<%
						}
					%>
				</div>
				<div id="promise">
					<div class="title">공약</div>
					<div class="hr"></div>
					<div id="promise-cover"></div>
				</div>
				<div id="poster">
						<div class="title">포스터</div>
						<div class="hr"></div>
						<div id="poster-cover">
							<img class="poster" id="poster1"
								src="<%=action.fileCheck(candidateDTO, saveDirectory, "Poster", "poster1")
							? "./Upload/Poster/" + candidateDTO.getDivision() + "/" + candidateDTO.getNumber()
									+ "/poster1Poster.png?"
							: "./img/noPhoto.png"%>">
							<img class="poster" id="poster2"
								src="<%=action.fileCheck(candidateDTO, saveDirectory, "Poster", "poster2")
							? "./Upload/Poster/" + candidateDTO.getDivision() + "/" + candidateDTO.getNumber()
									+ "/poster2Poster.png?"
							: "./img/noPhoto.png"%>">
						</div>
					</div>
				<%
					} else {
				%>
				<div id="content-detail-default" style="font-size: 19px; line-height: 30px; text-align: center; box-sizing: border-box; padding-top: 100px;">
					<img src="./img/logo.png" alt="" width="150px" style="display: block; margin: 0 auto; margin-bottom: 50px;">
					왼쪽에 있는 목록을 클릭하여 각 후보의 상세보기를 할 수 있습니다. <br><br>
					여러분의 소중한 한표가 필요합니다. 감사합니다. <br><br>
          		</div>
				<%
					}
				%>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function () {
			var promise = "<%=candidateDTO == null ? null : candidateDTO.getCommitment() == null ? null : candidateDTO.getCommitment()%>";
			$('#promise-cover').html(marked(promise));
		});
		
		$('img').click(function(){
		    var src = $(this).attr("src");
		    var appear_img = "<div id='appear_image_div' onclick='closeImage()'>";
		    appear_img = appear_img.concat("<img id='close_image' src='./img/delete.png' onclick='closeImage()'/>");
		    appear_img = appear_img.concat("<img id='appear_image' src='"+src+"'/></div>");
		    $('body').append(appear_img);
		  });
		  function closeImage(){
		    $('#appear_image_div').remove();
		    $('#appear_image').remove();
		    $('#close_image').remove();
		  }
	</script>
</body>

</html>
<%
	}
%>