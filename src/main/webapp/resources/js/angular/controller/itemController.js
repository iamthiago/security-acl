angular.module('itemController', [])
	.controller('ItemListController', ['$scope', '$http', function($scope, $http) {
		
		$scope.items = {};
		listItems();
		
		$scope.addItem = function(item) {
			$http.post('item/addItem', item)
				.success(function(data) {
					if (data == "") { //response null from server = ok
						listItems();
					} else { //response not null = error
						resultMessageModal(data);
					}
		
					$scope.item = '';
					$scope.itemForm.$setPristine();
					
			}).error(function(data) {
				console.log('error');
			});
		};
		
		$scope.deleteItem = function(item) {
			$http.post('item/deleteItem', item).success(function(data) {
				$scope.items = data;
			});
		};
		
		function listItems() {
			showLoading();
			$http.get(appPath + 'item/listItems').success(function(data) {
				$scope.items = data;
			});
			hideLoading();
		};
	}]);