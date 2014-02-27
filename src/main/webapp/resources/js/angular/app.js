'use strict';

var appPath = location.pathname;

angular.module('securityApp', ['ngResource', 'ngRoute',
                               'ui.bootstrap', 'ui.date',
                               'itemController', 'userController',
                               'Custom.Route'])

	.controller('IndexController', ['$scope', '$window', function($scope, $window) {
		$scope.back = function() {
			$window.history.back();
		};
	}])
	
	.factory('MenuFactory', ['$http', function($http) {
		return {
			getMenuAsync: function(callback) {
				$http.get(appPath + 'menus').success(callback);
			}
		};
	}])
	
	.directive('choiceTree', function() {
		return {
			template : 
				'<choice ng-repeat="choice in tree">',
			replace : true,
			transclude : true,
			restrict : 'E',
			scope : {
				tree : '=ngModel'
			}
		};
	})

	.directive('choice', ['$compile', '$location', function($compile, $location) {
		return {
			restrict : 'E',
			replace : true,
			template : 
				'<li class="dropdown">' +
		    	'	<a ng-click="go(choice.path)" ng-show="choice.children.length == 0">{{choice.name}}</a>' +
		    	'	<a ng-click="go(choice.path)" ' +
		    	'		ng-show="choice.children.length > 0" ' + 
		    	'		class="dropdown-toggle" ' +
		    	'		data-toggle="dropdown">{{choice.name}}<b class="caret"></b> ' +
		    	'	</a> ' +
			    '</li>',
			link : function(scope, elm, attrs) {
				scope.go = function(where) {
					if (where != null) {
						$location.path(where);
					}
					return;
				};
	
				if (scope.choice.children.length > 0) {
					var childChoice = $compile('<ul class="dropdown-menu"><choice-tree ng-model="choice.children"></choice-tree></ul>')(scope);
					elm.append(childChoice);
				};
			}
		};
	}])
	
	.controller('MenuController', ['$scope', '$http', '$filter', 'MenuFactory', function($scope, $http, $filter, MenuFactory) {
		
		$scope.myTree = [];
		
		MenuFactory.getMenuAsync(function(data) {
			var subArray = [];
			
			angular.forEach(data, function(parent) {
				
				if (parent.menus.length > 0) {
					var subs = [];
					
					angular.forEach(parent.menus, function(sub) {
						var c = new Choice(sub.nome, sub.path);
						subs.push(c);
						subArray.push(c);
					});
					
					var p = new Choice(parent.nome, parent.path, subs);
					$scope.myTree.push(p);
					
				} else {
					var p = new Choice(parent.nome, parent.path);
					var found = $filter('filter')(subArray, {name: p.name}, true);
					
					if (found[0] == null) $scope.myTree.push(p);
				}
			});
		});
	}]);

	var ModalDemoCtrl = function($scope, $modal, $log, $rootScope) {
		$rootScope.openModal = function() {
			var modalInstance = $modal.open({
				templateUrl : 'myModalContent.html',
				controller : ModalInstanceCtrl,
				backdrop: 'static',
				keyboard : false
			});
	
			modalInstance.result.then(function() {
				//do anything with result
			}, function() {
				//$log.info('Modal dismissed at: ' + new Date());
			});
		};
	};

	var ModalInstanceCtrl = function($scope, $modalInstance, $rootScope) {
		$rootScope.cancelModal = function() {
			$modalInstance.dismiss('cancel');
		};
	};

	function Choice(name, path, children) {
		this.name = name;
		this.path = path;
		this.children = children || [];
	}