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

angular.module("admin-users", [ "admin-roles", "ngResource", "ngRoute", "security" ])

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
.factory('userService', [ '$resource', 'admin.user.url', 'Session', function($resource, serviceUrl, Session) {
	return $resource('api/v1/security/users', {}, {
		'query' : {
			url : serviceUrl+'?searchString=:searchString&page=:page&pageSize=:pageSize',
			params : {
				page : '@page',
				pageSize : '@pageSize',
			},
			method : 'GET',
			isArray : true,
			headers : {'auth-token':Session.token}
		},
		'create' : {
			method : 'POST',
			url : serviceUrl,
			headers : {'auth-token':Session.token}
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

angular.module("security", [ "ngResource", "ngRoute" ])
.constant('AUTH_EVENTS', {
  loginSuccess: 'auth-login-success',
  loginFailed: 'auth-login-failed',
  logoutSuccess: 'auth-logout-success',
  sessionTimeout: 'auth-session-timeout',
  notAuthenticated: 'auth-not-authenticated',
  notAuthorized: 'auth-not-authorized'
})

.constant('USER_ROLES', {
  all: '*',
  sysadmin: 'SYSTEM_ADMIN',
  editor: 'editor',
  guest: 'guest'
})

.service('Session', function () {
  this.create = function (sessionId, userId, userRole, token) {
    this.id = sessionId;
    this.userId = userId;
    this.userRole = userRole;
    this.token = token;
  };
  this.destroy = function () {
    this.id = null;
    this.userId = null;
    this.userRole = null;
    this.token = null;
  };
  return this;
})

.factory('AuthService', ["$http", "Session", "security.login.url", function ($http, Session, loginUrl) {
  return {
    login: function (credentials) {
      return $http
        .post(loginUrl, credentials)
        .then(function (res) {
          Session.create(res.data.id, res.data.userid, res.data.role, res.data.token);
        });
    },
    isAuthenticated: function () {
      return !!Session.userId;
    },
    isAuthorized: function (authorizedRoles) {
      if (!angular.isArray(authorizedRoles)) {
        authorizedRoles = [authorizedRoles];
      }
      return (this.isAuthenticated() &&
        authorizedRoles.indexOf(Session.userRole) !== -1);
    }
  };
}])

.run(function ($rootScope, AUTH_EVENTS, AuthService) {
	// $stateChangeStart if using ui-router (anglar-ui)
	$rootScope.$on('$routeChangeStart', function (event, next) {
	if(typeof next.data === 'undefined' || typeof next.data.authorizedRoles === 'undefined') {
		return;
	}
    var authorizedRoles = next.data.authorizedRoles;
    if (!AuthService.isAuthorized(authorizedRoles)) {
      event.preventDefault();
      if (AuthService.isAuthenticated()) {
        // user is not allowed
        $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
      } else {
		$rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
      }
    }
  });
})

.run(function ($rootScope, AUTH_EVENTS, AuthService, $location) {
	// $stateChangeStart if using ui-router (anglar-ui)
	$rootScope.$on(AUTH_EVENTS.notAuthenticated, function (event) {
		$location.path("/login");
  });
  
	$rootScope.$on(AUTH_EVENTS.loginSuccess, function (event) {
		$location.path("/users");
  });
})

.config(function ($httpProvider) {
  $httpProvider.interceptors.push([
    '$injector',
    function ($injector) {
      return $injector.get('AuthInterceptor');
    }
  ]);
})

.factory('AuthInterceptor', function ($rootScope, $q,
                                      AUTH_EVENTS) {
  return {
    responseError: function (response) {
	  alert(response.status);
      if (response.status === 401) {
        $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated,
                              response);
      }
      if (response.status === 403) {
        $rootScope.$broadcast(AUTH_EVENTS.notAuthorized,
                              response);
      }
      if (response.status === 419 || response.status === 440) {
        $rootScope.$broadcast(AUTH_EVENTS.sessionTimeout,
                              response);
      }
      return $q.reject(response);
    }
  };
})


.controller('LoginController', ['$scope', '$rootScope', 'AUTH_EVENTS', 'AuthService',function ($scope, $rootScope, AUTH_EVENTS, AuthService) {
  $scope.credentials = {
    username: '',
    password: ''
  };
  $scope.login = function (credentials) {
    AuthService.login(credentials).then(function () {
      $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
    }, function () {
      $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
    });
  };
}]);

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