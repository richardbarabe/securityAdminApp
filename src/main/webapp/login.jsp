<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap-3.1.1-dist/css/bootstrap.min.css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap-3.1.1-dist/css/bootstrap-theme.min.css"></link>

<!--  JQuery -->
<script src="${pageContext.request.contextPath}/lib/jquery-1.11.0/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/lib/bootstrap-3.1.1-dist/js/bootstrap.min.js"></script>

<style>
body {
	background-color: #F7F7F6;
}

.login {
	padding-top: 65px;
}

.center {
	float: none;
	margin-left: auto;
	margin-right: auto;
}
</style>

</head>
<body>
	<section>
		<div class="container login">
			<div class="row">
				<div class="col-lg-4 center well">
					<legend> Login </legend>
					<c:if test="${param.error == 'true'}">
						<div class="form-group bg-danger">
							<span class="text-danger">Incorrect username or password.</span>
						</div>
					</c:if>

					<form class="form-horizontal" name="loginForm" method='POST'>
						<div class="form-group">
							<div class="col-lg-11 center">
								<input type='text' id='username' name='username' value='' class="form-control" placeholder="Username">
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-11 center">
								<input type='password' id='password' name='password' class="form-control" placeholder="password" />
							</div>
						</div>
						<!--                   <div class="form-group"> -->
						<!--                     <label> -->
						<!--                       <div class="col-lg-12 center"> -->
						<%--                         <input type='checkbox' id='_spring_security_remember_me' name='_spring_security_remember_me'> ${rememberMeLabel} --%>
						<!--                       </div> -->
						<!--                     </label> -->
						<!--                   </div> -->
						<input name="submit" type="submit" value="Login" class="btn btn-primary btn-block" />
					</form>
				</div>
			</div>
		</div>
	</section>

	<!-- Charge à la fin pour que ce soit plus rapide -->
	<!-- AngularJS -->
	<script src="${pageContext.request.contextPath}/lib/angular/angular-1.2.13.min.js"></script>
	<script src="${pageContext.request.contextPath}/lib/angular/angular-resource-1.2.13.min.js"></script>
	<script src="${pageContext.request.contextPath}/app/dist/app.js"></script>
</body>
</html>