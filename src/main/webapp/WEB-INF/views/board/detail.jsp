<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<title></title>
</head>
<body>
	번호 : ${notice.boardNo}<br>
	제목 : ${notice.boardTitle}<br>
	작성자 : ${notice.boardWriter}<br>
	내용 : ${notice.boardContents}<br>
	날짜 : ${notice.boardDate}<br>
	조회수 : ${notice.boardHit}<br>
	
	<a href="./update?boardNo=${notice.boardNo}">수정</a>
	<a href="./delete">삭제</a>
</body>
</html>