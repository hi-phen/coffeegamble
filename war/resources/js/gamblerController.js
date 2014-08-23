(function(){
	gambleApp.controller('GamblerController',function($scope, $http){
		$scope.addGambler = function(){
			if($scope.newGamblerName){
				$http.post('gambler/addGambler',$.param({'name':$scope.newGamblerName}))
				.success(function(data){
					$scope.gamblers = data;
					$scope.newGamblerName = "";
				});
			}
		};
		
		$scope.deleteGambler = function(key){
			$http.post('gambler/deleteGambler',$.param({'key':key}))
			.success(function(data){
				$scope.gamblers = data;
			});
		};
		
		$scope.activeGambler = function(key,active){
			active = !active;
			console.log(key,active);
			$http.post('gambler/activeGambler',$.param({'key':key,'active':active}))
			.success(function(data){
				$scope.gamblers = data;
			});
		};
		
		
		$http.get('gambler/getGamblerList').success(function(data) {
			$scope.gamblers = data;
		});
	});
})();