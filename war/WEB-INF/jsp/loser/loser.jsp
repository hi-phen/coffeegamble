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
	<div class="row marketing" infinite-scroll='getLoser()' infinite-scroll-disabled='busy' infinite-scroll-distance='0'>
		<h2><strong>Games History</strong> <button class="btn btn-md btn-danger" ng-click="deleteLoserAll()" role="button">DELETE ALL</button></h2>
		<p></p>
		<div class="col-xs-6" ng-repeat="loser in gambleLoser track by $index">
			<p><h3><strong>{{loser.gamblerName}}</strong></h3></p>
			<p>DATE : {{loser.loseDate | date : 'yyyy-MM-dd HH:mm:ss'}}</p>
			<p>
				<a class="btn btn-sm btn-danger" ng-click="deleteLoser(loser,$index)" role="button">DELETE</a>
			</p>
		</div>
	</div>
</div>
