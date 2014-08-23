<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div>
	<div class="jumbotron">
		<h1>LOSER</h1>
		<p>
			<select id="gamblerinput" ng-model="loser" class="btn-group" ng-options="gambler.name for gambler in gamblers" data-toggle="popover" data-placement="bottom" data-content="Gambler is not valid">
			</select>
			<input id="dateinput" class="btn-group" ng-model="date" placeholder="yyyy-MM-dd" data-toggle="popover" data-placement="bottom" data-content="Date format is not valid">
		</p>
		<p>
			<button class="btn btn-lg btn-success" ng-click="addLoser()" role="button" >ADD</button>
		</p>
	</div>
	<div class="row marketing" >
		<h2><strong>Games History List</strong></h2>
		<div class="col-xs-6" ng-repeat="loser in gambleLoser">
			<p><h3><strong>{{loser.gamblerName}}</strong></h3></p>
			<p>DATE : {{loser.loseDate | date : 'yyyy-MM-dd hh:mm:ss'}}</p>
			<p>
				<a class="btn btn-sm btn-danger" ng-click="deleteLoser(loser.loserId.id)" role="button">DELETE</a>
			</p>
		</div>
	</div>
</div>
