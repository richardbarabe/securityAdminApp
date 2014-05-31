<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
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
<body ng-app="securityManager">
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
<!-- 							<div class="animate-switch-container" ng-switch on="action"> -->
<!-- 								<div class="animate-switch" ng-switch-when="search_user"> -->
<!-- 									<div class="actionBar"> -->
<!-- 										<button type="button" class="btn btn-success" ng-click="startCreateUser()"> -->
<!-- 																					<span class="glyphicon glyphicon-plus-sign"></span> -->
<!-- 											<span class=" glyphicon glyphicon-user"></span> <span class="">New</span> -->
<!-- 										</button> -->
<!-- 										<button type="button" class="btn btn-danger" ng-disabled="true" ng-click="startCreateUser()"> -->
<!-- 																					<span class="glyphicon glyphicon-plus-sign"></span> -->
<!-- 											<span class=" glyphicon glyphicon-user"></span> <span class="">Delete</span> -->
<!-- 										</button> -->
<!-- 									</div> -->
<!-- 									<form id="searchForm" class="form-horizontal" role="search"> -->
<!-- 										<div class="input-group"> -->
<!-- 											<input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term" -->
<!-- 												ng-model="searchField"> -->
<!-- 											<div class="input-group-btn"> -->
<!-- 												<button class="btn btn-default" type="submit" ng-click="search(searchField)"> -->
<!-- 													<i class="glyphicon glyphicon-search"></i> -->
<!-- 												</button> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</form> -->

<!-- 									<div id="results" when-scrolled="loadMore()"> -->
<!-- 										<table class="table"> -->
<!-- 											<tbody> -->
<!-- 												<tr ng-repeat="user in users"> -->
<!-- 													<td class="usernameColumn">{{user.username}}</td> -->
<!-- 													<td><span ng-repeat="role in user.roles">{{role}} </span></td> -->
<!-- 												</tr> -->
<!-- 											</tbody> -->

<!-- 										</table> -->
<!-- 									</div> -->
<!-- 									<div id="escapingBallG" class="center" ng-show="loading"> -->
<!-- 										<div id="escapingBall_1" class="escapingBallG"></div> -->
<!-- 									</div> -->
<!-- 								</div> -->

<!-- 								<div class="animate-switch" ng-switch-when="create_user"> -->
<!-- 									<h3 id="editUserTitle">Create/Edit user</h3> -->
<!-- 									<div> -->
<!-- 										<form name="createUserForm" role="form"> -->
<!-- 											<div class="form-group"> -->
<!-- 												<label for="name">Username</label> -->
<!-- 												<input id="name" ng-model="newUser.username" name="name" type="text" placeholder="username" class="form-control" required>	 -->
												
<!-- 												<label for="password">Password</label> -->
<!-- 												<input id='password' ng-model="newUser.password" name='password' type='password' placeholder="password" class="form-control" required/> -->
												
<!-- 												<label for="confirmPassword">Confirm Password</label> -->
<!-- 												<input id='confirmPassword' ng-model="password_verify" data-password-verify="newUser.password" name='confirmPassword' type='password' class="form-control" placeholder="confirm password" required/> -->
<!-- 											</div> -->
<!-- 											<div class="form-group"> -->
<!-- 												<fieldset> -->
<!-- 													<legend>Rôles</legend> -->
<!-- 													<div class="checkbox" ng-repeat="role in roles"> -->
<!-- 														<label> -->
<!-- 															<input type="checkbox" name="newUser.roles[]" class="form-control" value="{{role}}" -->
<!-- 																   ng-click="newUser.toggleRole(role)">  -->
<!-- 															{{role}}  -->
<!-- 															{{newUser.hasRole(role)}}<br /> -->
<!-- 														</label> -->
<!-- 													</div> -->
<!-- 												</fieldset> -->
<!-- 											</div> -->
<!-- 										</form> -->
<!-- 									</div> -->
<!-- 									<div style="text-align:right"> -->
<!-- 										<button type="button" class="btn btn-default" ng-click="cancelCreateUser()">Cancel</button> -->
<!-- 										<button type="button" class="btn btn-primary" ng-click="createUser()" -->
<!-- 												ng-disabled="createUserForm.$invalid">Create</button> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->

						</div>
						<div class="tab-pane" id="roles"></div>

						<div class="tab-pane" id="permissions"></div>

					</div>
				</div>
			</div>
		</div>
	</section>

	<script src="${pageContext.request.contextPath}/lib/angular/securityManager/main.js"></script>

</body>
</html>