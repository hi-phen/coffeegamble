(function(){
	gambleApp.controller('GamblerController',function($scope, $http){
		//fn init
		/**
		 * 참가자 추가
		 */
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
		
		/**
		 * 참가자 삭제
		 */
		$scope.deleteGambler = function(obj,index){
			$http.post('gambler/deleteGambler',$.param({'key':obj.gamblerId.id}))
			.success(function(data){
				if(data == 'true'){
					$scope.gamblers.splice(index,1);
				}
			});
		};
		
		/**
		 * 참가자 삭제 (전체)
		 */
		$scope.deleteGamblerAll = function(){
			$http.post('gambler/deleteGamblerAll')
			.success(function(data){
				if(data == 'true'){
					$scope.gamblers = [];
				}
			});
		};
		
		/**
		 * 참가자 활성화/비활성화
		 */
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
		
		//popover 이벤트 등록
		$('#nameinput').popover({trigger: 'manual'})
			.focus(function(){$('#nameinput').popover('hide');});
	});
})();