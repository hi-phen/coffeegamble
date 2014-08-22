(function(){
	gambleApp.controller('GamblerController',function($scope, $http){
		$scope.addGambler = function(){
			if($scope.newGamblerName){
				$http.post('gambler/addGambler',$.param({'name':$scope.newGamblerName}))
				.success(function(data){
					$scope.gamblers = data;
				});
			}
		};
		
		$scope.deleteGambler = function(key){
			$http.post('gambler/deleteGambler',$.param({'key':key}))
			.success(function(data){
				$scope.gamblers = data;
			});
		};
		
		$scope.updateGambler = function(key,active,name){
			active = !active;
			console.log(key,active);
			$http.post('gambler/updateGambler',$.param({'key':key,'active':active,'name':name}))
			.success(function(data){
				$scope.gamblers = data;
			});
		};
		
		
		$http.get('gambler/getGamblerList').success(function(data) {
			$scope.gamblers = data;
		});
	});
})();