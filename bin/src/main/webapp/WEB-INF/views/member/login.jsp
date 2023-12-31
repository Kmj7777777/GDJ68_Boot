<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
					<div>
						<h3>${param.message}</h3>
	    	 				<spring:message code="${param.message}" var="msg"></spring:message>
    		 				<h3>${msg}</h3>
					</div>
					
					<form:form modelAttribute="memberVO" action="./login" method="POST">
						<div class="form-group">
							<form:label path="username">User name</form:label>
						    <form:input path="username" cssClass="form-control" id="username" />
						    <form:errors path="username" />
						</div>
						<div class="form-group">
						    <form:label path="password">Password</form:label>
						    <form:password path="password" cssClass="form-control" id="password" />
						    <form:errors path="password" />
					  	</div>
					  	<div class="form-group">
					  		<label for="remember">Remember Me</label>
						    <input type="checkbox" name="remember-me" id="remember" class="form-control">
					  	</div>
						  
						  <form:button>Submit</form:button>
					</form:form>
					
					<a href="/oauth2/authorization/kakao">KakaoLogin</a>
    	 		</div>
    	 	</div>
    	 	<c:import url="/WEB-INF/views/layout/footer.jsp"></c:import>
    	 </div>
    </div>
    
	<c:import url="/WEB-INF/views/layout/footjs.jsp"></c:import>

	<script type="text/javascript">
		let msg = '${msg}';
		
		if(msg != ""){
			alert(msg);
		}
		
		// history.replaceState({}, null, location.pathname);
	</script>
</body>
</html>