<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div>
	<div class="jumbotron">
		<h1>STATISTICS</h1>
		<p>
			<select ng-change="getStatistics()" ng-model="year" class="btn-group" ng-options="o for o in yearOption"></select>
			<select ng-change="getStatistics()" ng-model="month" class="btn-group" ng-options="o for o in monthOption"></select>
			
		</p>
	</div>
	<div class="row marketing" >
		<h1 class="text-primary"><strong>Monthly</strong></h1>
		<div class="col-xs-6" ng-repeat="value in monthly track by $index | orderBy:get($index)">
		<blockquote>
			<h3 class="text-info"><strong>{{$index+1 | ordinal}}</strong></h3>
				<h4 class="text-success"><strong>{{gamblerMap[value[0]]}} </strong><span class="text-danger">({{value[1]}})</span></h4>
		</blockquote>
		</div>
	</div>
	
	<div class="row marketing" >
		<h1 class="text-primary"><strong>Weekly</strong></h1>
		<div class="row marketing" ng-repeat="weeklyStr in weeklyText track by $index">
		<blockquote>
			<h2 class="text-muted"><strong>{{$index+1 | ordinal}} WEEK</strong>({{weeklyStr[0]}} ~ {{weeklyStr[1]}})</h2>
			<div class="col-xs-6" ng-repeat="value in weekly[$index] track by $index">
				<blockquote>
				<h3 class="text-info"><strong>{{$index+1 | ordinal}}</strong></h3>
				<h4 class="text-success"><strong>{{gamblerMap[value[0]]}} </strong><span class="text-danger">({{value[1]}})</span></h4>
				</blockquote>
			</div>
		</blockquote>
		</div>
	</div>
</div>
