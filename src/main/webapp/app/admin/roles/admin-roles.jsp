<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="RolesController">
	<div>
		<div class="actionBar">
			<button type="button" class="btn btn-success" ng-click="createRole()">
				<span class="glyphicon glyphicon-plus-sign"></span> <span class=" glyphicon glyphicon-user"></span> <span class="">New</span>
			</button>
			<button type="button" class="btn btn-danger" ng-disabled="true" ng-click="createRole()">
				<span class="glyphicon glyphicon-plus-sign"></span> <span class=" glyphicon glyphicon-user"></span> <span class="">Delete</span>
			</button>
		</div>
		
		<div id="roles">
			<table class="table">
				<tbody>
					<tr ng-repeat="role in roles">
						<td>{{role.name}}</td>
						<td>{{role.code}}</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>