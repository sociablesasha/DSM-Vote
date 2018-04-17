# DSM Vote

## 목적(현 투표 시스템의 문제점 해결)
1. 투표 참여의 불편함
2. 투표 결과 수합의 불편함
3. 투표 결과 통계의 불편함

## 기대 효과
1. 투표 참여의 간결함
2. 투표 시간의 단축
3. 학생과 선생님의 편의성 증대
<br><br>

# 계획

## 일정
1. 2018.04.09 ~ 2018.04.11 계획 및 설계<br><br>
2. 2018.04.12 ~ 2018.04.13 각 파트 구현<br><br>
3. 2018.04.14 ~ 2018.04.15 통합 및 테스팅

## 라이브러리
1. poi - excel
2. connector - mysql
3. cos - multipart

## 아쉬운 점
1. 너무 짧은 기간이었다.
2. API 통신을 완료하지 못하였다.
<br><br>

# 기능명세서

## 사용자
1. 후보 목록
```java
public ArrayList<CandidateDTO> doList (String division) {
    ...
}
```
2. 후보 상세
```java
public CandidateDTO doDetail (CandidateDTO candidateDTO) {
    ...
}
```
3. 투표 하기
```java
public boolean doVote (UserDTO userDTO, VoteDTO voteDTO) {
    ...
}
```

## 관리자
1. 후보 목록
```java
public ArrayList<CandidateDTO> doList (String division) {
    ...
}
```
2. 후보 작성
```java
public CandidateDTO doWrite (CandidateDTO candidateDTO) {
    ...
}
```
3. 후보 수정
```java
public boolean doUpdate (CandidateDTO candidateDTO) {
    ...
}
```
4. 후보 삭제
```java
public boolean doDelete (CandidateDTO candidateDTO) {
    ...
}
```
5. 투표 통계
```java
public int doResult (CandidateDTO candidateDTO) {
    ...
}
```
6. 투표 결과.xls 다운로드
```java
public void fileDownload(HttpServletResponse response) {
    HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet("testSheet");

	HSSFRow row;
	HSSFCell cell;

	CandidateDTO candidateDTO = new CandidateDTO();
	CandidateDAO candidateDAO = new CandidateDAO();
			
	row = sheet.createRow(0);
	cell = row.createCell(0);
	cell.setCellValue("00");
	cell = row.createCell(1);
	cell.setCellValue("01");

	row = sheet.createRow(1);
	cell = row.createCell(0);
	cell.setCellValue("10");
	cell = row.createCell(1);
	cell.setCellValue("11");

	String fileName = "DSM투표결과.xls";
	response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
	response.setContentType("application/vnd.ms.excel");

	ServletOutputStream servletOutputStream = response.getOutputStream();
	workbook.write(servletOutputStream);

	workbook.close();
	servletOutputStream.close();
}
```

## 공통
1. 로그인
2. 로그아웃