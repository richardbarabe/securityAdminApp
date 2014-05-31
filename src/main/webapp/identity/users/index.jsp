<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="animate-switch-container" ng-switch on="action" ng-controller="UserController">
	<div class="animate-switch" ng-switch-when="search_user">
		<div class="actionBar">
			<button type="button" class="btn btn-success" ng-click="createUser()">
				<span class="glyphicon glyphicon-plus-sign"></span> <span class=" glyphicon glyphicon-user"></span> <span class="">New</span>
			</button>
			<button type="button" class="btn btn-danger" ng-disabled="true" ng-click="startCreateUser()">
				<span class="glyphicon glyphicon-plus-sign"></span> <span class=" glyphicon glyphicon-user"></span> <span class="">Delete</span>
			</button>
		</div>
		<form id="searchForm" class="form-horizontal" role="search">
			<div class="input-group">
				<input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term" ng-model="searchField">
				<div class="input-group-btn">
					<button class="btn btn-default" type="submit" ng-click="search(searchField)">
						<i class="glyphicon glyphicon-search"></i>
					</button>
				</div>
			</div>
		</form>

		<div id="results" when-scrolled="loadMore()">
			<table class="table">
				<tbody>
					<tr ng-repeat="user in users">
						<td class="usernameColumn">{{user.username}}</td>
						<td><span ng-repeat="role in user.roles">{{role}} </span></td>
					</tr>
				</tbody>

			</table>
		</div>
		<div id="escapingBallG" class="center" ng-show="loading">
			<div id="escapingBall_1" class="escapingBallG"></div>
		</div>
	</div>

	<div class="animate-switch" ng-switch-when="create_user" ng-controller="CreateUser">
		<h3 id="editUserTitle">Create/Edit user</h3>
		<div>
			<form name="createUserForm" role="form">
				<div class="form-group">
					<label for="name">Username</label> <input id="name" ng-model="newUser.username" name="name" type="text"
						placeholder="username" class="form-control" required> <label for="password">Password</label> <input
						id='password' ng-model="newUser.password" name='password' type='password' placeholder="password"
						class="form-control" required /> <label for="confirmPassword">Confirm Password</label> <input
						id='confirmPassword' ng-model="password_verify" data-password-verify="newUser.password" name='confirmPassword'
						type='password' class="form-control" placeholder="confirm password" required />
				</div>
				<div class="form-group">
					<fieldset>
						<legend>Rôles</legend>
						<div class="checkbox" ng-repeat="role in roles">
							<label> <input type="checkbox" name="newUser.roles[]" class="form-control" value="{{role}}"
								ng-click="newUser.toggleRole(role)"> {{role}} {{newUser.hasRole(role)}}<br />
							</label>
						</div>
					</fieldset>
				</div>
			</form>
		</div>
		<div style="text-align: right">
			<button type="button" class="btn btn-default" ng-click="cancel()">Cancel</button>
			<button type="button" class="btn btn-primary" ng-click="save()" ng-disabled="createUserForm.$invalid">Save</button>
		</div>
	</div>
</div>

