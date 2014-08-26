<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div>
	<div class="jumbotron">
		<h1>GAMBLE</h1>
		<div ng-show="gamblers.length == 0" class="alert alert-danger" role="alert"><strong>No GAMBLER!!</strong> <a href="#gambler" class="alert-link">let's add gambler</a></div>
		<p>LOSER CHANCE MULTIPLE
			<select ng-model="chanceMultipleValue" ng-change="getGambleEntry()">
				<option value='1'>1</option>
				<option value='0.5'>1/2</option>
				<option value='0.33'>1/3</option>
			</select>
		</p>
		<p class="canvas-cantainer">
			<canvas id="bc" class="subcanvs"></canvas>
			<canvas id="c" class="subcanvs"></canvas>
		</p>
		<p>
			<button class="btn btn-lg btn-success" ng-click="gamble()">GAMBLE</button>
		</p>
	</div>
	
	<div class="row marketing" >
		<h2 class="text-primary"><strong>Gamblers</strong></h2>
			<div class="col-xs-6" ng-repeat="gambler in gamblers">
			<blockquote>
				<h3 class="text-info"><strong>{{gambler.name}}</strong> (<strong>{{gambler.chance}}</strong>%)</h3>
				<p><input type="checkbox" ng-model="showChecked" id="chk_{{gambler.gamblerId.id}}"> <label for="chk_{{gambler.gamblerId.id}}">History</label></p>
				<p ng-show="showChecked" ng-repeat="msg in gambler.weightLog.slice().reverse() track by $index" ng-class="{'bg-success':msg.indexOf('미당첨')>0,'bg-danger':msg.indexOf('미당첨')<0}">
					{{msg}}
				</p>
			</blockquote>
			</div>
		
	</div>
</div>