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
		<h2 class="text-primary"><strong>Gamblers</strong> <button class="btn btn-md btn-danger" ng-click="deleteGamblerAll()" role="button">DELETE ALL</button></h2>
		<div class="col-xs-6" ng-repeat="gambler in gamblers track by $index">
			<blockquote>
			<h4 class="text-info"><strong>{{gambler.name}}</strong></h4>
<!-- 			<p>{{gambler.addDate | date : 'yyyy-MM-dd hh:mm:ss'}}</p> -->
			<p>
				<a class="btn btn-sm" ng-class="{'btn-info':gambler.active,'btn-success':!gambler.active}" ng-click="activeGambler(gambler)" role="button">
					<span ng-if="gambler.active">
					DEACTIVATE
					</span>
					<span ng-if="!gambler.active">
					ACTIVATE
					</span>
				</a>
				<a class="btn btn-sm btn-danger" ng-click="deleteGambler(gambler,$index)" role="button">DELETE</a>
			</p>
			</blockquote>
		</div>
	</div>
</div>
