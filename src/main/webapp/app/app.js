/**
 * Main application features
 */
angular.module("app", [ "admin", "ngRoute" ])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
      .when('/users', {templateUrl: ctx + "/app/admin/users/admin-users.jsp",   controller: "UserController" })
      .when('/roles', {templateUrl: ctx + "/app/admin/roles/admin-roles.jsp",   controller: "RolesController" })
//      .when('/permissions', {templateUrl: ctx + "/app/admin/permissions/admin-permissions.jsp",   controller: "PermissionController" })
      .otherwise({redirectTo: '/users'});
}])

// Main controller for tabs (Users, Roles, Permissions)
.controller("IdentityTabsController",
		[ '$scope', function($scope) {
			$scope.tabs = [ {
				link : '#/users',
				label : 'Users'
			}, {
				link : '#/roles',
				label : 'Roles'
			}, {
				link : '#/permissions',
				label : 'Permissions'
			} ];

			$scope.selectedTab = $scope.tabs[0];
			$scope.setSelectedTab = function(tab) {
				$scope.selectedTab = tab;
			};

			$scope.tabClass = function(tab) {
				if ($scope.selectedTab == tab) {
					return "active";
				} else {
					return "";
				}
			};
		} ]);

