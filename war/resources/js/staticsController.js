(function(){
	
	gambleApp.controller('StaticsController',function($scope, $http, $filter,drawLottery){
		var now = new Date();
		$scope.year = now.getFullYear();
		$scope.month = now.getMonth()+1;
		
		$scope.yearOption = [];
		$scope.monthOption = [];
		
		for(var i=2014 ; i<=$scope.year ; i++){
			$scope.yearOption.push(i);	
		}
		
		for(var i=1 ; i<=12 ; i++){
			$scope.monthOption.push(i);	
		}

		$scope.getStatics = function(){
			$http.post("gamble/getStatics",$.param({"year":$scope.year,"month":$scope.month}))
			.success(function(data){
				//정렬이 가능한 형태로 변형 및 정렬
				var sortable = [];
				for(var i in data.monthly){
					sortable.push([i,data.monthly[i]]);
				}
				sortable.sort(function(a, b) {return b[1] - a[1];});
				data.monthly = sortable.slice();
				
				for(var i in data.weekly){
					sortable = [];
					for(var j in data.weekly[i]){
						sortable.push([j,data.weekly[i][j]]);	
					}
					sortable.sort(function(a, b) {return b[1] - a[1];});
					data.weekly[i] = sortable.slice();
				}
				
				$scope.monthly = data.monthly;
				$scope.weekly = data.weekly;
				$scope.weeklyText = data.weeklyText;
				$scope.gamblerMap = data.gamblerMap;
			});
		};
		
		
		$scope.getStatics();
	});
	
	
	
})();