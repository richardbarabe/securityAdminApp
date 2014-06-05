/**
 * Main application features
 */
angular.module("app", [ "admin", "ngRoute" ])

// Configure the user administration json services
.value("admin.user.url", "http://localhost:8080/shiroExample/api/v1/security/users")

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
      .when('/users', {templateUrl: "admin/users/admin-users.html",   controller: "UserController" })
      .when('/roles', {templateUrl: "admin/roles/admin-roles.html",   controller: "RolesController" })
//      .when('/permissions', {templateUrl: "admin/permissions/admin-permissions.html",   controller: "PermissionController" })
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

