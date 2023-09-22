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
					<form:form modelAttribute="memberVO" action="./login" method="POST">
						<div class="form-group">
							<form:label path="userName">User name</form:label>
						    <form:input path="userName" cssClass="form-control" id="userName" />
						    <form:errors path="userName" />
						</div>
						<div class="form-group">
						    <form:label path="password">Password</form:label>
						    <form:password path="password" cssClass="form-control" id="password" />
						    <form:errors path="password" />
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