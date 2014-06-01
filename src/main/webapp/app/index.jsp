<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap-3.1.1-dist/css/bootstrap.min.css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap-3.1.1-dist/css/bootstrap-theme.min.css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/ajaxloader/loader.css"></link>

<!--  JQuery -->
<script src="${pageContext.request.contextPath}/lib/jquery-1.11.0/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/lib/bootstrap-3.1.1-dist/js/bootstrap.min.js"></script>
<!-- AngularJS -->
<script src="${pageContext.request.contextPath}/lib/angular/angular-1.2.13.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/angular/angular-resource-1.2.13.min.js"></script>
<script> 
	var ctx = "${pageContext.request.contextPath}"
</script>
<style>
body {
	background-color: #F7F7F6;
}

.main {
	/* 	margin-top: 65px; */
	
}

.center {
	float: none;
	margin-left: auto;
	margin-right: auto;
}

#results {
	height: 300px;
	overflow: auto;
	margin-top: 10px;
}

.usernameColumn {
	width: 40%;
}

#users {
	margin-top: 10px;
}

#searchForm {
	margin-top: 10px;
}
</style>

</head>
<body>
	<section>
		<div class="container main">
			<div class="row">
				<div class="col-lg-6 center well">
					<ul id="tabs" class="nav nav-tabs">
						<li class="active"><a href="#users" data-toggle="tab">Users</a></li>
						<li><a href="#roles" data-toggle="tab">Roles</a></li>
						<li><a href="#permissions" data-toggle="tab">Permissions</a></li>
					</ul>
					<div class="tab-content" ng-controller="IdentityTabsController">
						<div class="tab-pane active" id="users" ng-include="templates['users']">
						</div>
						<div class="tab-pane" id="roles"></div>

						<div class="tab-pane" id="permissions"></div>

					</div>
				</div>
			</div>
		</div>
	</section>
	<script src="${pageContext.request.contextPath}/app/dist/app.js"></script>
</body>
</html>