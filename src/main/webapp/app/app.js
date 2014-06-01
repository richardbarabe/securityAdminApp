angular.module("app", ["admin"])

//Main controller for tabs (Users, Roles, Permissions)
.controller("IdentityTabsController", [ '$scope', function($scope) {
	$scope.templates = {
		'users' : ctx + "/app/admin/users/admin-users.jsp"
	};
	$scope.roles = [ "ADMIN", "SYSTEM_ADMIN", "USER" ];
} ]);