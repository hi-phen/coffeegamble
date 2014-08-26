(function(){
	//파일 분리를 위해 전역으로 설정
	//규모가 커지면 require.js,ocLazyLoad.js 로 교체하는게 좋을듯..
	gambleApp = angular.module('gambleApp',['ngRoute','infinite-scroll'])
				.config(function($controllerProvider,$compileProvider, $filterProvider, $provide,$routeProvider,$httpProvider){
				  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';

					gambleApp.controller = $controllerProvider.register;
					gambleApp.service = $provide.service;
					gambleApp.factory = $provide.factory;
					gambleApp.filter = $filterProvider.register;
					gambleApp.directive = $compileProvider.directive;

					$routeProvider
						.when('/',
						{
							templateUrl : '/gamble'
							,controller:'GambleController'
						})
						.when('/gambler',
						{
							templateUrl : '/gambler'
							,controller:'GamblerController'
						})
						.when('/history',
						{
							templateUrl : '/gamble/loser'
							,controller:'LoserController'
						})
						.when('/statics',
						{
							templateUrl : '/gamble/statics'
							,controller:'StaticsController'
						})
						.otherwise({redirectTo : '/'});
				})
				.filter('ordinal', function() {
				  var ordinal = function(input) {
					    // Only process numeric values.
					    if (isNaN(input) || input === null) return input;

					    var s=["th","st","nd","rd"],
					    v=input%100;
					    return input+(s[(v-20)%10]||s[v]||s[0]);
					  }
					  return ordinal;
					})
				.filter('arrayOrder',function(){
					return function(input){
						
					};
				});
	gambleApp.controller('SiteController',function($scope,$route){
		$scope.$route = $route;
	});
	
//	.filter('reverse', function() {
//	    return function(input, uppercase) {
//	      input = input || '';
//	      var out = "";
//	      for (var i = 0; i < input.length; i++) {
//	        out = input.charAt(i) + out;
//	      }
//	      // conditional based on optional argument
//	      if (uppercase) {
//	        out = out.toUpperCase();
//	      }
//	      return out;
//	    };
//	  })
	
    angular.bootstrap(document, ['gambleApp']);
})();