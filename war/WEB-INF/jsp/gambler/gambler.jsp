<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div>
	<div class="jumbotron">
		<h1>GAMBLER</h1>
		<p>
			<input type="text" id="nameinput" class="btn-group" ng-model="newGamblerName" placeholder=" Please input name" data-toggle="popover" data-placement="bottom" data-content="Please input name">
		</p>
		<p>
			<button class="btn btn-lg btn-success" ng-click="addGambler()" role="button">ADD</button>
		</p>
	</div>
	<div class="row marketing" >
		<h2><strong>Gambler List</strong></h2>
		<div class="col-xs-6" ng-repeat="gambler in gamblers">
			<p><h4><strong>{{gambler.name}}</strong></h4></p>
<!-- 			<p>{{gambler.addDate | date : 'yyyy-MM-dd hh:mm:ss'}}</p> -->
			<p>
				<a class="btn btn-sm" ng-class="{'btn-info':gambler.active,'btn-success':!gambler.active}" ng-click="activeGambler(gambler.gamblerId.id,gambler.active)" role="button">
					<span ng-if="gambler.active">
					DEACTIVATE
					</span>
					<span ng-if="!gambler.active">
					ACTIVATE
					</span>
				</a>
				<a class="btn btn-sm btn-danger" ng-click="deleteGambler(gambler.gamblerId.id)" role="button">DELETE</a>
			</p>
		</div>
	</div>
</div>
