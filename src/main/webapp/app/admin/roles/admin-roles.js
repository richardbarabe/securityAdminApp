angular.module("admin-roles", [])

.factory("roleService", [ function() {
	return {
		getRoles : function() {
			return [ {
				name : "Agency Administrator",
				code : "AGENCY_ADMIN"
			}, {
				name : "System Administrator",
				code : "SYSTEM_ADMIN"
			}, {
				name : "Advertizer",
				code : "ADVERTIZER"
			} ];
		}
	};
} ])

.controller("RolesController", [ "$scope", "roleService", function($scope, roleService) {
	$scope.roles = roleService.getRoles();
} ]);
