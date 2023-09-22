<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>    
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
					<form:form modelAttribute="memberInfoVO" action="./update" method="POST" enctype="multipart/form-data">
					  	<div class="form-group">
						    <form:label path="name">Name</form:label>
						    <form:input path="name" cssClass="form-control" id="name" />
						    <form:errors path="name" />
						 </div>
						 <div class="form-group">
						    <form:label path="email">Email</form:label>
						    <form:input path="email" cssClass="form-control" id="email" />
						    <form:errors path="email" />
						  </div>
						  <div class="form-group">
						    <form:label path="birth">Birth</form:label>
						    <form:input path="birth" cssClass="form-control" id="birth" />
						    <form:errors path="birth" />
						  </div>
						  <div class="form-group">
						    <label for="photo">Photo</label>
						    <input type="file" name="photo" id="photo" class="form-control" aria-describedby="photoHelp">
						  </div>
						  
						  <form:button>Submit</form:button>
					</form:form>
    	 		</div>
    	 	</div>
    	 	<c:import url="/WEB-INF/views/layout/footer.jsp"></c:import>
    	 </div>
    </div>
    
	<c:import url="/WEB-INF/views/layout/footjs.jsp"></c:import>
</body>
</html>