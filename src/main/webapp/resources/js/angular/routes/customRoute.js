'use strict';

angular.module('Custom.Route', ['ngResource', 'ngRoute', 'homeController'])
	
	.config(['$routeProvider', function($routeProvider) {
			
			$routeProvider
				.when('/', {
					templateUrl : 'layout/home', 
					controller : 'HomeListController'
				})
				.when('/item', { 
					templateUrl : 'layout/item',
					controller : 'ItemListController'
				})
				.when('/user', { 
					templateUrl : 'layout/user', 
					controller : 'UserListController'
				})
				.otherwise({
					redirectTo: '/'
				});
		}]);