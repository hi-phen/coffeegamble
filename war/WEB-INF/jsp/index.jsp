<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<script src="/resources/js/common/jquery-1.11.1.min.js"></script>
<script	src="/resources/js/common/bootstrap.min.js"></script>
<script	src="/resources/js/common/angular.min.js"></script>
<script	src="/resources/js/common/angular-route.min.js"></script>

<link rel="stylesheet"	href="/resources/css/bootstrap.min.css">
<link rel="stylesheet"	href="/resources/css/bootstrap-theme.min.css">
<link rel="stylesheet"  href="/resources/css/jumbotron-narrow.css">

<title>COFFEE GAMBLE</title>
</head>
<body>
	<div class="container" ng-controller="SiteController">
		<div class="header">
			<ul class="nav nav-pills pull-right">
				<li ng-class="{active:$route.current.templateUrl=='/gamble'}"><a href="#">Home</a></li>
				<li ng-class="{active:$route.current.templateUrl=='/gambler'}"><a href="#gambler">Gambler</a></li>
				<li><a href="#">Contact</a></li>
			</ul>
			<h3 class="text-muted">Coffee Gamble</h3>
		</div>
		<ng-view></ng-view>
		
		<div class="footer">
			<p>&copy; Yobin</p>
		</div> 
	</div>
	<script src="/resources/js/siteController.js"></script>
	<script src="/resources/js/gamble.js"></script>
	<script src="/resources/js/gambleController.js"></script>
	<script src="/resources/js/gamblerController.js"></script>
</body>
</html>