angular.module('userController', [])
	.controller('UserListController', ['$scope', '$http', function($scope, $http) {
		
		$scope.user = {};
		$scope.users = {};
		$scope.permissions = [];
		
		$http.get(appPath + 'user/listUsers').success(function(data) {
			$scope.users = data;
		});
		
		$http.get(appPath + 'user/listMenus').success(function(data) {
			$scope.permissions = data;
		});
	
		$scope.addUser = function(user) {

		};
		
		$scope.reset = function() {
			$scope.user = {};
			$scope.userForm.$setPristine();
		};
	}]);