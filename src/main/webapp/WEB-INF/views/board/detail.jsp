<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>

<html>
<head>
	<title></title>
	<c:import url="/WEB-INF/views/layout/headCSS.jsp"></c:import>
</head>
<body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">
    	<!-- sidebar -->
    	<c:import url="/WEB-INF/views/layout/sidebar.jsp"></c:import>
    	
    	 <div id="content-wrapper" class="d-flex flex-column">
    	 	<div id="content">
    	 		
    	 		<c:import url="/WEB-INF/views/layout/topbar.jsp"></c:import>
    	 		
    	 		<div class="container-fluid">
    	 			<div class="row">
    	 				<div>${notice.boardTitle}</div>
    	 				<div>${notice.boardWriter}</div>
    	 				<div>${notice.boardContents}</div>
    	 			</div>
    	 			
    	 			<div class="row">
    	 				<c:forEach items="${notice.noticeFiles}" var="f">
    	 					<img src="../files/${board}/${f.fileName}">
    	 					<a href="./fileDown?fileNo=${f.fileNo}">${f.originalName}</a>
    	 				</c:forEach>
    	 			</div>
    	 		</div>
    	 	
    	 	</div>
    	 	
    	 	<c:import url="/WEB-INF/views/layout/footer.jsp"></c:import>
    	 
    	 </div>
    	
    </div>
	<c:import url="/WEB-INF/views/layout/footjs.jsp"></c:import>
</body>
</html>