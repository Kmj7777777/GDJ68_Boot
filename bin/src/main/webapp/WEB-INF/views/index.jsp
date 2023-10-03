<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
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
    	 			<h1>Welcome : <spring:message code="hello"></spring:message></h1>
					
					<sec:authorize access="isAuthenticated()">
						<sec:authentication property="name" var="name"></sec:authentication>
						<sec:authentication property="principal.username" var="vo"></sec:authentication>
		    	 		<h1><spring:message code="login.welcome" arguments="${name}"></spring:message></h1>
					</sec:authorize>
				</div>
			</div>
			
			<c:import url="/WEB-INF/views/layout/footer.jsp"></c:import>
		</div>
	</div>
	
	<c:import url="/WEB-INF/views/layout/footjs.jsp"></c:import>
</body>
</html>