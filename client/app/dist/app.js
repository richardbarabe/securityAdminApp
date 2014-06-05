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


angular.module('admin', ['admin-users', "admin-roles"/*, "admin-permissions"*/]);

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

/**
 * To use this module, you need to set the value "admin.user.url".
 * It's the url of the json service for managing users (search,create,delete).
 */

angular.module("admin-users", [ "admin-roles", "ngResource", "ngRoute" ])

.value('newUser', function() {
	return {
		username : "",
		password : "",
		roles : [],
		hasRole : function(role) {
			return $.inArray(role, this.roles) > -1;
		},
		toggleRole : function(role) {
			if (this.hasRole(role)) {
				var index = this.roles.indexOf(role); // <-- Not
				// supported in
				// <IE9 ??
				if (index !== -1) {
					this.roles.splice(index, 1);
				}
			} else {
				this.roles.push(role);
			}
		}
	};
})

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/users/create', {
		templateUrl : "admin/users/admin-users-create.html",
		controller : "CreateUser"
	});
} ])

.controller(
		"UserController",
		[ '$scope', '$timeout', '$location', 'userService', 'newUser',
				function($scope, $timeout, $location, userService, newUser) {
					var page = 0;
					var pageSize = 10;
					$scope.searchField = "";
					$scope.users = [];
					$scope.loading = false;
					$scope.waitingForResponse = false;

					$scope.createUser = function() {
						$location.path("/users/create");
					};

					$scope.search = function(searchString) {
						page = 0;
						$scope.users = [];
						$scope.searchField = searchString;
						$scope.loadMore();
					};

					$scope.loadMore = function() {
						if (!$scope.waitingForResponse) {
							$scope.waitingForResponse = true;
							// Show ajax after some waiting time
							// This avoid having ajax request status
							// flicker if
							// server answer too fast.
							$timeout(function() {
								if ($scope.waitingForResponse)
									$scope.loading = true;
							}, 300);

							userService.query({
								'page' : page,
								'pageSize' : pageSize,
								'searchString' : $scope.searchField
							}, function(results) {
								$scope.loading = false;
								for (var i = 0; i < results.length; i++) {
									var user = newUser();
									user.isNew = false;
									user.username = results[i].username;

									$scope.users.push(results[i]);
								}
								page = page + 1;
								$scope.waitingForResponse = false;
							}, function() {
								$scope.waitingForResponse = false;
							});
						}
					};

					$scope.loadMore();
				} ])

.controller(
		"CreateUser",
		[ '$scope', '$location', 'userService', 'newUser', 'roleService',
				function($scope, $location, userService, newUser, roleService) {
					$scope.newUser = newUser();
					$scope.roles = roleService.getRoles();
					$scope.save = function() {
						userService.create($scope.newUser, function() {
							$scope.newUser = null;
							$scope.passwordVerify = null;
							$location.path("/users");
						});
					};

					$scope.cancel = function() {
						$scope.newUser = null;
						$scope.passwordVerify = null;
						$location.path("/users");
					};

				} ])

// Provides users operation
.factory('userService', [ '$resource', 'admin.user.url', function($resource, serviceUrl) {
	return $resource('api/v1/security/users', {}, {
		'query' : {
			url : serviceUrl+'?searchString=:searchString&page=:page&pageSize=:pageSize',
			params : {
				page : '@page',
				pageSize : '@pageSize'
			},
			method : 'GET',
			isArray : true
		},
		'create' : {
			method : 'POST',
			url : serviceUrl
		}
	});
} ])

.directive("passwordVerify", function() {
	return {
		require : "ngModel",
		scope : {
			passwordVerify : '='
		},
		link : function(scope, element, attrs, ctrl) {
			scope.$watch(function() {
				var combined;

				if (scope.passwordVerify || ctrl.$viewValue) {
					combined = scope.passwordVerify + '_' + ctrl.$viewValue;
				}
				return combined;
			}, function(value) {
				if (value) {
					ctrl.$parsers.unshift(function(viewValue) {
						var origin = scope.passwordVerify;
						if (origin !== viewValue) {
							ctrl.$setValidity("passwordVerify", false);
							return undefined;
						} else {
							ctrl.$setValidity("passwordVerify", true);
							return viewValue;
						}
					});
				}
			});
		}
	};
});

angular.module("app").directive("whenScrolled", function() {
	return function(scope, elm, attr) {
		var raw = elm[0];
		elm.bind("scroll", function() {
			if (raw.scrollTop + raw.offsetHeight >= raw.scrollHeight - 1) {
				scope.$apply(attr.whenScrolled);
			}
		});
	};
});