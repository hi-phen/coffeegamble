<div>
	<div class="jumbotron">
		<h1></h1>
		<p class="canvas-cantainer">
			<canvas id="bc" class="subcanvs"></canvas>
			<canvas id="c" class="subcanvs"></canvas>
		</p>
		<p>
			<a class="btn btn-lg btn-success" id="gamble" href="#" role="button">GAMBLE</a>
		</p>
	</div>
	
	<div class="row marketing">
		<div class="col-lg-12"ng-repeat="phone in phones">
			<h4>{{phone.name}}</h4>
			<p>{{phone.snippet}}</p>
		</div>
	</div>
</div>