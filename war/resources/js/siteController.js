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
						.when('/statistics',
						{
							templateUrl : '/gamble/statistics'
							,controller:'StatisticsController'
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
				.config(['$httpProvider', function($httpProvider) {
					    $httpProvider.interceptors.push(function(){
					    	return {
					    		request : function(request){
					    			$('.container').waiting({fixed:true});
					    			return request;
					    		}
					    		,response : function(response){
					    			$('.container').waiting('done');
					    			return response;
					    		}
					    	};
					    });
					}]);
	gambleApp.controller('SiteController',function($scope,$route){
		$scope.$route = $route;
	});
	
    angular.bootstrap(document, ['gambleApp']);
})();