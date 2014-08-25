(function(){
	gambleApp.controller('GamblerController',function($scope, $http){
		//fn init
		$scope.addGambler = function(){
			if($scope.newGamblerName){
				$http.post('gambler/addGambler',$.param({'name':$scope.newGamblerName}))
				.success(function(data){
					if(data > 0){
						$scope.gamblers.unshift(
							{
								"name" : $scope.newGamblerName
								,"active" : true
								,"gamblerId" : {id : data}
							}
						);
						$scope.newGamblerName = "";
					}
					
					
				});
			}else{
				$('#nameinput').popover('show');
			}
		};
		
		$scope.deleteGambler = function(obj,index){
			$http.post('gambler/deleteGambler',$.param({'key':obj.gamblerId.id}))
			.success(function(data){
				if(data == 'true'){
					$scope.gamblers.splice(index,1);
				}
			});
		};
		
		$scope.deleteGamblerAll = function(){
			$http.post('gambler/deleteGamblerAll')
			.success(function(data){
				if(data == 'true'){
					$scope.gamblers = [];
				}
			});
		}
		
		$scope.activeGambler = function(obj){
			var active = !obj.active;
			$http.post('gambler/activeGambler',$.param({'key':obj.gamblerId.id,'active':active}))
			.success(function(data){
				if(data == 'true'){
					obj.active = active;
				}
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