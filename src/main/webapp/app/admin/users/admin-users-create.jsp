<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div ng-controller="CreateUser">
		<h3 id="editUserTitle">Create/Edit user</h3>
		<div>
			<form name="createUserForm" role="form">
				<div class="form-group">
					<label for="name">Username</label> 
						<input id="name" ng-model="newUser.username" name="name" type="text"
						placeholder="username" class="form-control" required> 
					<label for="password">Password</label> 
						<input
							id='password' ng-model="newUser.password" name='password' type='password' placeholder="password"
							class="form-control" required /> 
					<label for="confirmPassword">Confirm Password</label> 
						<input
							id='confirmPassword' ng-model="password_verify" data-password-verify="newUser.password" name='confirmPassword'
							type='password' class="form-control" placeholder="confirm password" required />
				</div>
				<div class="form-group">
					<fieldset>
						<legend>Rôles</legend>
						<div class="checkbox" ng-repeat="role in roles">
							<label> <input type="checkbox" name="newUser.roles[]" class="form-control" value="{{role}}"
								ng-click="newUser.toggleRole(role)"> {{role.name}} {{newUser.hasRole(role)}}<br />
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