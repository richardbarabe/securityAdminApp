<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="UserController">
	<div>
		<div class="actionBar">
			<button type="button" class="btn btn-success" ng-click="createUser()">
				<span class="glyphicon glyphicon-plus-sign"></span> <span class=" glyphicon glyphicon-user"></span> <span class="">New</span>
			</button>
			<button type="button" class="btn btn-danger" ng-disabled="true" ng-click="createUser()">
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
						<td><span ng-repeat="role in user.roles">{{role.name}} </span></td>
					</tr>
				</tbody>

			</table>
		</div>
		<div id="escapingBallG" class="center" ng-show="loading">
			<div id="escapingBall_1" class="escapingBallG"></div>
		</div>
	</div>
</div>

