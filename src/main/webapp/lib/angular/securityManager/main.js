'use strict';

angular.module("SecurityManagementApp", []);
angular.module("PermissionManager", []);
angular.module("RoleManager", ["PermissionManager"]);
angular.module("UserManager", ["RoleManager"]);



var securityManagerModule = angular.module('securityManager', [ 'ngResource' ]);

var newUser = function() {
	return {
		username:"",
		password:"",
		roles:[],
		hasRole: function(rolename) {
			return $.inArray(rolename, this.roles) > -1;
		},
		toggleRole : function(rolename) {
			if(this.hasRole(rolename)) {
				var index = this.roles.indexOf(rolename);    // <-- Not supported in <IE9 ??
				if (index !== -1) {
					this.roles.splice(index, 1);
				}
			} else {
				this.roles.push(rolename);
			}
		}
	};
};

securityManagerModule.directive("whenScrolled", function() {
	return function(scope, elm, attr) {
		var raw = elm[0];

		elm.bind('scroll', function() {
			if (raw.scrollTop + raw.offsetHeight >= raw.scrollHeight - 1) {
				if (!scope.waitingForResponse) {
					scope.$apply(attr.whenScrolled);
				}
			}
		});
	};
});

// Main controller for tabs (Users, Roles, Permissions)
securityManagerModule.controller("IdentityTabsController", [ '$scope', function($scope, $timeout, userService, $filter) {
	$scope.templates = {'users':ctx+"/identity/users/index.jsp"};
	$scope.roles = ["ADMIN", "SYSTEM_ADMIN", "USER"];
}]);

var userController = function($scope, $timeout, userService, $filter) {
	var page = 0;
	var pageSize = 10;
	$scope.searchField = "";
	$scope.users = [];
	$scope.loading = false;
	$scope.waitingForResponse = false;
	$scope.action = "search_user";
	$scope.$on('changeView', function(event, data) {
		$scope.action = data;
	});
	
	$scope.createUser = function() {
		$scope.action="create_user";
	};
	
	$scope.search = function(searchString) {
		$scope.action="search_user";
		page = 0;
		$scope.users = [];
		$scope.searchField = searchString;
		$scope.loadMore();
	};
	
	$scope.loadMore = function() {
		$scope.waitingForResponse = true;
		// Show ajax after some waiting time
		// This avoid having ajax request status flicker if
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
				user.isNew=false;
				user.username = results[i].username;
				
				$scope.users.push(results[i]);
			}
			page = page + 1;
			$scope.waitingForResponse = false;
		}, function() {
			$scope.waitingForResponse = false;
		});

	};
	
	$scope.loadMore();

};
securityManagerModule.controller("UserController", [ '$scope', '$timeout', 'userService', userController]);

var createUserController = function($scope, userService) {
	$scope.newUser = newUser();
	
	$scope.save = function() {
		var username = $scope.newUser.username;
		userService.create($scope.newUser, function() {
			$scope.search(username);
			$scope.newUser = null;
			$scope.passwordVerify = null;
		});
	};
	
	$scope.cancel = function() {
		$scope.newUser = null;
		$scope.$emit('changeView', 'search_user');
		$scope.passwordVerify = null;
	};
	
};
securityManagerModule.controller("CreateUser", [ '$scope', 'userService', createUserController]);

var userService = function($resource, $filter, $timeout) {
	return $resource(ctx + '/api/v1/security/users', {}, {
		'query' : {
			url : ctx + '/api/v1/security/users?searchString=:searchString&page=:page&pageSize=:pageSize',
			params : {
				page : '@page',
				pageSize : '@pageSize'
			},
			method : 'GET',
			isArray : true
		},
		'create' : {
			method : 'POST',
		}
	});
};
// Provides users operation
securityManagerModule.factory('userService', [ '$resource', '$filter', '$timeout', userService ]);
var passwordVerifyDirective = function() {
    return {
        require: "ngModel",
        scope: {
            passwordVerify: '='
        },
        link: function(scope, element, attrs, ctrl) {
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
};
securityManagerModule.directive("passwordVerify", passwordVeriryDirective);