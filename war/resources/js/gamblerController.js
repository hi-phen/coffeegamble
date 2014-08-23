(function(){
	gambleApp.controller('GamblerController',function($scope, $http){
		//fn init
		$scope.addGambler = function(){
			if($scope.newGamblerName){
				$http.post('gambler/addGambler',$.param({'name':$scope.newGamblerName}))
				.success(function(data){
					$scope.gamblers = data;
					$scope.newGamblerName = "";
				});
			}else{
				$('#nameinput').popover('show');
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
			$http.post('gambler/activeGambler',$.param({'key':key,'active':active}))
			.success(function(data){
				$scope.gamblers = data;
			});
		};
		
		//data init
		$http.get('gambler/getGamblerList').success(function(data) {
			$scope.gamblers = data;
		});
		
		$('#nameinput').popover({trigger: 'manual'})
			.focus(function(){$('#nameinput').popover('hide');});
	});
})();