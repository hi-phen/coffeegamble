(function(){
	gambleApp.controller('LoserController',function($scope, $http){
		//fn init
		$scope.addLoser = function(){
			if($scope.loser){
				if(!(/\d{4}-\d{2}-\d{2}/g.test($scope.date))){ //날짜 형식 체크
					$('#dateinput').popover('show');
				}else{
					$http.post('gamble/addLoser',$.param({'gamblerKey':$scope.loser.gamblerId.id,'gamblerName':$scope.loser.name,'dateStr':$scope.date}))
					.success(function(data){
						$scope.gambleLoser = data;
					})
					.error(function(data){
						$('#gamblerinput').popover('show');
					});
				}
			}else{
				$('#gamblerinput').popover('show');
			}
		}
		
		$scope.deleteLoser = function(key){
			$http.post('gamble/deleteLoser',$.param({'loserKey':key}))
			.success(function(data){
				$scope.gambleLoser = data;
			});
		}
		
		// data init
		$http.get('gambler/getGamblerList').success(function(data) {
			$scope.gamblers = data;
			$scope.loser = data[0];
		});	
		
		$http.get('gamble/getLoser').success(function(data){
			$scope.gambleLoser = data;
		});
		
		var now = new Date();
		
		$scope.date = now.getFullYear()+'-'
					+(now.getMonth()+1<10?'0'+(now.getMonth()+1):(now.getMonth()+1))+'-'
					+(now.getDate()<10?'0'+now.getDate():now.getDate());
		
		$('#dateinput,#gamblerinput').popover({trigger: 'manual'})
					.focus(function(){$(this).popover('hide');});
	});
})();