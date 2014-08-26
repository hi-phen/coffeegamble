(function(){
	gambleApp.controller('LoserController',function($scope, $http){
		//fn init
		/**
		 * 당첨자 추가
		 */
		$scope.addLoser = function(){
			if($scope.loser){
				if(!(/\d{4}-\d{2}-\d{2}/g.test($scope.date))){ //날짜 형식 체크
					$('#dateinput').popover('show');
				}else{
					var date = $scope.date;
					var now = new Date();
					//날짜만 입력받고 시간은 현재 시간을 기준으로 입력
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
		
		/**
		 * 당첨자 삭제
		 */
		$scope.deleteLoser = function(obj,index){
			$http.post('gamble/deleteLoser',$.param({'loserKey':obj.loserId.id}))
			.success(function(data){
				if(data == 'true'){
					$scope.gambleLoser.splice(index,1);
				}
			});
		};
		
		/**
		 * 당첨자 삭제 (전체)
		 */
		$scope.deleteLoserAll = function(){
			$http.post('gamble/deleteLoserAll')
			.success(function(data){
				if(data == 'true'){
					$scope.gambleLoser = [];
				}
			});
		};
		/**
		 * 당첨자 조회 (스크롤 페이징 by ng-infinite-scroll)
		 */
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
		//참가자 리스트 조회
		$http.get('gambler/getGamblerList').success(function(data) {
			$scope.gamblers = data;
			$scope.loser = data[0];
		});
		
		//페이징 property 세팅
		$scope.offset=0;
		$scope.busy=false;
		$scope.gambleLoser=[];
		
		//default 입력 일 설정
		var now = new Date();
		$scope.date = now.getFullYear()+'-'
					+(now.getMonth()+1<10?'0'+(now.getMonth()+1):(now.getMonth()+1))+'-'
					+(now.getDate()<10?'0'+now.getDate():now.getDate());
		
		//popover 이벤트 등록
		$('#dateinput,#gamblerinput').popover({trigger: 'manual'})
					.focus(function(){$(this).popover('hide');});
	});
})();