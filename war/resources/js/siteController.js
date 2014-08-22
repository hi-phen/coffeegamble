(function(){
	//파일 분리를 위해 전역으로 설정
	//규모가 커지면 require.js,ocLazyLoad.js 로 교체하는게 좋을듯..
	gambleApp = angular.module('gambleApp',['ngRoute'])
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
						.otherwise({redirectTo : '/'});
				});
	gambleApp.controller('SiteController',function($scope,$route){
		$scope.$route = $route;
	});
    angular.bootstrap(document, ['gambleApp']);
})();