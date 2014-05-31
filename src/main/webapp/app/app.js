angular.module("app", ["admin"])
//Main controller for tabs (Users, Roles, Permissions)
.controller("IdentityTabsController", [ '$scope', function($scope) {
	$scope.templates = {
		'users' : ctx + "/identity/users/index.jsp"
	};
	$scope.roles = [ "ADMIN", "SYSTEM_ADMIN", "USER" ];
} ]);