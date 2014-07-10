/**
 * Main application features
 */
angular.module("app", [ "admin", "security","ngRoute" ])

// Configure Authentication Service
//.value("security.login.url", "http://localhost:8080/shiroExample/api/v1/security/login")
.value("security.login.url", "http://localhost:8080/spring-security-server-example/api/v1/security/login")

// Configure the user administration json services
//.value("admin.user.url", "http://localhost:8080/shiroExample/api/v1/security/users")
.value("admin.user.url", "http://localhost:8080/spring-security-server-example/api/v1/security/users")

.config(['$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {
  $routeProvider
      .when('/login', {
		  templateUrl: "login.html",   
		  controller: "LoginController",
		  data: {
					public:true,
					authorizedRoles: [USER_ROLES.all]
				}
	  })
      .when('/users', {
		  templateUrl: "admin/users/admin-users.html",   
		  controller: "UserController",
		  data: {
					authorizedRoles: [USER_ROLES.sysadmin]
				}
	  })
      .when('/roles', {
		  templateUrl: "admin/roles/admin-roles.html",   
		  controller: "RolesController", 
		  data: {
					authorizedRoles: [USER_ROLES.sysadmin]
				}
	  })
//      .when('/permissions', {templateUrl: "admin/permissions/admin-permissions.html",   controller: "PermissionController" })
      .otherwise({
		  redirectTo: '/users'
		  
	  });
}])

// Main controller for tabs (Users, Roles, Permissions)
.controller("ApplicationController",
		[ '$scope', 'USER_ROLES', 'AuthService', function($scope, USER_ROLES, AuthService) {
			$scope.currentUser = null;
			$scope.userRoles = USER_ROLES;
			$scope.isAuthorized = AuthService.isAuthorized;
			$scope.isAuthenticated = AuthService.isAuthenticated;
			
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

