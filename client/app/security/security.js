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
