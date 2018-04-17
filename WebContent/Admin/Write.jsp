<%@ page language="java"%>
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
	moveObject = result
			? userDTO.getIdentification().equals("admin") ? null : action.MainJSP(response)
			: action.LoginJSP(response);

	if (result) {
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
				<span id="name">관리자</span>
				<input class="btn btn-gray" type="button" id="logout" value="로그아웃" onclick="location.href='../LogoutAction'">
			</div>
		</nav>
		<form action="../WriteAction" method="post" enctype="multipart/form-data">
			<div id="content" class="shadow-box">
				<div id="header">후보자 등록</div>
				<div id="content-detail">
					<div id="apply-explain">
						<div id="apply-alert">※실제 사용자에게 보여지는 페이지 모습입니다.</div>
						<div id="apply-choice">
							<input name="division" type="radio" id="student" value="학생회"
								checked> <label for="student">학생회</label> <input
								name="division" type="radio" id="autonomy" value="자치부"> <label
								for="autonomy">자치부</label>
						</div>
					</div>
					<div id="super-title">기호 1번</div>
					<div id="member">
						<div class="title">
							구성 인원<span>※업로드 파일 형식은 png, jpg, jpeg 입니다. 우측 하단에 이미지를 눌러
								파일을 업로드 해주세요.</span>
						</div>
						<div class="hr"></div>
						<div class="member-cover" id="student-member-cover">
							<div class="member">
								<div class="member-photo file-upload">
									<img id="s-photo1" src="./img/3x4.png" alt="">
									<div class="photo-ico">
										<label for="s-file1"></label> <input type="file" id="s-file1"
											class="photo" name="masterGovernmentPicture">
									</div>
								</div>
								<div class="member-detail">
									<div class="member-position">학생회 회장 후보</div>
									<div class="member-name candidate-input">
										<input type="text" name="masterGovernment">
									</div>
								</div>
							</div>
							<div class="member">
								<div class="member-photo file-upload">
									<img id="s-photo2" src="./img/3x4.png" alt="">
									<div class="photo-ico">
										<label for="s-file2"></label> <input type="file"
											style="display: none;" id="s-file2" class="photo" name="slave1GovernmentPicture">
									</div>
								</div>
								<div class="member-detail">
									<div class="member-position">학생회 3학년 부회장 후보</div>
									<div class="member-name candidate-input">
										<input type="text" name="slave1Government">
									</div>
								</div>
							</div>
							<div class="member">
								<div class="member-photo file-upload">
									<img id="s-photo3" src="./img/3x4.png" alt="">
									<div class="photo-ico">
										<label for="s-file3"></label> <input type="file"
											style="display: none;" id="s-file3" class="photo" name="slave2GovernmentPicture">
									</div>
								</div>
								<div class="member-detail">
									<div class="member-position">학생회 2학년 부회장 후보</div>
									<div class="member-name candidate-input">
										<input type="text" name="slave2Government">
									</div>
								</div>
							</div>
							<div class="member">
								<div class="member-photo file-upload">
									<img id="s-photo4" src="./img/3x4.png" alt="">
									<div class="photo-ico">
										<label for="s-file4"></label> <input type="file"
											style="display: none;" id="s-file4" class="photo" name="slave3GovernmentPicture">
									</div>
								</div>
								<div class="member-detail">
									<div class="member-position">학생회 1학년 부회장 후보</div>
									<div class="member-name candidate-input">
										<input type="text" name="slave3Government">
									</div>
								</div>
							</div>
						</div>
						<div class="member-cover" id="autonomy-member-cover"
							style="display: none;">
							<div class="member">
								<div class="member-photo file-upload">
									<img id="a-photo1" src="./img/3x4.png" alt="">
									<div class="photo-ico">
										<label for="a-file1"></label> <input type="file"
											style="display: none;" id="a-file1" class="photo" name="masterMinistryPicture">
									</div>
								</div>
								<div class="member-detail">
									<div class="member-position">자치부 부장 후보</div>
									<div class="member-name candidate-input">
										<input type="text" name="masterMinistry">
									</div>
								</div>
							</div>
							<div class="member">
								<div class="member-photo file-upload">
									<img id="a-photo2" src="./img/3x4.png" alt="">
									<div class="photo-ico">
										<label for="a-file2"></label> <input type="file"
											style="display: none;" id="a-file2" class="photo" name="slaveMinistryPicture">
									</div>
								</div>
								<div class="member-detail">
									<div class="member-position">자치부 차장 후보</div>
									<div class="member-name candidate-input">
										<input type="text" name="slaveMinistry">
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="promise">
						<div class="title">
							공약 <span>※markdown 형식으로 작성하실 수 있습니다.</span>
						</div>
						<div class="hr"></div>
						<textarea rows="15" id="promise-cover" name="commitment"></textarea>
					</div>
					<div id="poster">
						<div class="title">
							포스터 <span>※업로드 파일 형식은 png, jpg, jpeg 입니다. 우측 하단에 이미지를 눌러
								파일을 업로드 해주세요.</span>
						</div>
						<div class="hr"></div>
						<div id="poster-cover">
							<div class="poster file-upload">
								<img id="poster1" src="./img/a4.png" alt="">
								<div class="photo-ico">
									<label for="poster-file-1"></label> <input type="file"
										style="display: none;" id="poster-file-1" class="photo" name="poster1Poster">
								</div>
							</div>
							<div class="poster file-upload">
								<img id="poster2" src="./img/a4.png" alt="">
								<div class="photo-ico">
									<label for="poster-file-2"></label> <input type="file"
										style="display: none;" id="poster-file-2" class="photo" name="poster2Poster">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="candidate-btn-cover">
					<input type="button" value="취소" class="candidate-btn" id="candidate-cancel" onclick="location.href='./Main.jsp'">
					<input type="submit" value="후보자 등록" class="candidate-btn" id="candidate-apply">
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	var stu_mem_cover = $('#student-member-cover');
	var auto_mem_cover = $('#student-member-cover');
	$('.photo').change(
			function readURL() {
				var parent_img = $(this).parents(".file-upload")
						.children("img").attr("id");
				parent_img = "#" + parent_img;
				console.log(parent_img);
				if (this.files && this.files[0]) {
					var reader = new FileReader();
					reader.onload = function(e) {
						$(parent_img).attr('src', e.target.result);
					};
					reader.readAsDataURL(this.files[0]);
				}
			});
	$(document).ready(function() {
		$('input[type=radio][name=division]').change(function() {
			if (this.value == "학생회") {
				$('#student-member-cover').css("display", "block");
				$('#autonomy-member-cover').css("display", "none");
			} else if (this.value == "자치부") {
				$('#student-member-cover').css("display", "none");
				$('#autonomy-member-cover').css("display", "block");
			}
		});
	});
</script>

</html>
<%
	}
%>