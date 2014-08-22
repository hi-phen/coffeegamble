(function(){
	gambleApp.controller('GambleController',function($scope, $http){
		$scope.phones = [
		                 {'name': 'Nexus S',
		                  'snippet': 'Fast just got faster with Nexus S.'},
		                 {'name': 'Motorola XOOM™ with Wi-Fi',
		                  'snippet': 'The Next, Next Generation tablet.'},
		                 {'name': 'MOTOROLA XOOM™',
		                  'snippet': 'The Next, Next Generation tablet.'}
		               ];
		
		$http.get('gambler/getGamblerList').success(function(data) {
			console.log(data);
		});
	});
})();