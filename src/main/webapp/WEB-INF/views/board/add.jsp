<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<head>
	<title></title>
	<c:import url="/WEB-INF/views/layout/headCSS.jsp"></c:import>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/layout/sidebar.jsp"></c:import>
		
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/layout/topbar.jsp"></c:import>
				
				<div class="container-fluid">
					<div class="row">
						<form action="add" method="POST">
							<div class="mb-3">
								<label for="boardTitle" class="form-label">Title</label>
								<input type="text" class="form-control" name="boardTitle" id="boardTitle" placeholder="제목">
							</div>
							<div class="mb-3">
								<label for="boardWriter" class="form-label">Writer</label>
								<input type="text" class="form-control" name="boardWriter" id="boardWriter" placeholder="작성자">
							</div>
							<div class="mb-3">
								<label for="boardContents" class="form-label">Contents</label>
								<textarea class="form-control" name="boardContents" id="boardContents" rows="3"></textarea>
							</div>
							
							<button class="btn btn-danger">Add</button>
						</form>
					</div>
				</div>
			</div>
			
			<c:import url="/WEB-INF/views/layout/footer.jsp"></c:import>
		</div>
	</div>
	
	<c:import url="/WEB-INF/views/layout/footjs.jsp"></c:import>
</body>
</html>