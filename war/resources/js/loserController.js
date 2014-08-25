(function(){
	gambleApp.controller('LoserController',function($scope, $http){
		//fn init
		$scope.addLoser = function(){
			if($scope.loser){
				if(!(/\d{4}-\d{2}-\d{2}/g.test($scope.date))){ //날짜 형식 체크
					$('#dateinput').popover('show');
				}else{
					var date = $scope.date;
					var now = new Date();
					date += " "+(now.getHours()<10?"0"+now.getHours():now.getHours())+":"
							+(now.getMinutes()<10?"0"+now.getMinutes():now.getMinutes())+":"
							+(now.getSeconds()<10?"0"+now.getSeconds():now.getSeconds());
					console.log(date);
					$http.post('gamble/addLoser',$.param({'gamblerKey':$scope.loser.gamblerId.id,'gamblerName':$scope.loser.name,'dateStr':date}))
					.success(function(data){
						if(data > 0){
							$scope.gambleLoser.unshift({
								'gamblerName' : $scope.loser.name
								,'loseDate' : date
								,'loserId' : {id:data}
							});	
						}
					})
					.error(function(data){
						$('#gamblerinput').popover('show');
					});
				}
			}else{
				$('#gamblerinput').popover('show');
			}
		};
		
		$scope.deleteLoser = function(obj,index){
			$http.post('gamble/deleteLoser',$.param({'loserKey':obj.loserId.id}))
			.success(function(data){
				if(data == 'true'){
					$scope.gambleLoser.splice(index,1);
				}
			});
		};
		
		$scope.deleteLoserAll = function(){
			$http.post('gamble/deleteLoserAll')
			.success(function(data){
				if(data == 'true'){
					$scope.gambleLoser = [];
				}
			});
		};
		$scope.getLoser = function(){
			$scope.busy = true;
			$http.post('gamble/getLoser',$.param({'offset':$scope.offset})).success(function(data){
				if(data.length != 0){
					$scope.gambleLoser = $scope.gambleLoser.concat(data);
					$scope.busy = false;
					$scope.offset += 10;
				}
			});
		};
		
		// data init
		$http.get('gambler/getGamblerList').success(function(data) {
			$scope.gamblers = data;
			$scope.loser = data[0];
		});
		
		$scope.offset=0;
		$scope.busy=false;
		$scope.gambleLoser=[];
		
		var now = new Date();
		
		$scope.date = now.getFullYear()+'-'
					+(now.getMonth()+1<10?'0'+(now.getMonth()+1):(now.getMonth()+1))+'-'
					+(now.getDate()<10?'0'+now.getDate():now.getDate());
		
		$('#dateinput,#gamblerinput').popover({trigger: 'manual'})
					.focus(function(){$(this).popover('hide');});
	});
})();