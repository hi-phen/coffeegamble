function batting(){
	drawLottery("eee");
}

function drawLottery(value){
	var canvas = $("#c");
	var bgCanvas = $("#bc");
	
	var container = canvas.parent();
	var width = container.width();
	var height = 200;
	canvas.attr({width:width,height:height});
	var context = canvas[0].getContext("2d");
	var bgContext = bgCanvas[0].getContext("2d");
	var offset = canvas.offset();
	
	//홀로그램 이미지 선택용 랜덤
	function getRandom(min, max) {
        min = min || 1;
        max = max || 10;
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }
	
	//지우개 효과를 내는 도형 그리기
	function drawPoint(x, y) {
        var grid = context.createRadialGradient(x, y, 0, x, y, 50);
        
        grid.addColorStop(0, "rgba(255, 255, 255, .3)");
        grid.addColorStop(1, "transparent");
        context.fillStyle = grid;
        context.beginPath();
        context.arc(x, y, 50, 0, Math.PI * 2, true);
        context.fill();
        context.closePath();
    }
	
	//홀로그램 이미지 
	var imageObj = new Image();
	imageObj.onload = function() {
		context.drawImage(imageObj,0,0,width, height);
		context.globalCompositeOperation = "destination-out";
		
		//이미지를 그린후에 텍스트를 그려야 미리 보이지 않음
		bgContext.font = 'bold 2em Arial';
		bgContext.textAlign = 'center';
		bgContext.fillStyle = 'black';
		bgContext.fillText(value, width/2, height/2);
	};
	imageObj.src = '/resources/img/hologram-0' + getRandom(1, 5)+'.jpg';

	//이벤트 리스너 등록
	canvas[0].addEventListener("touchstart", function (e) {
        drawPoint(e.touches[0].clientX - offset.left, e.touches[0].clientY - offset.top);
    }, false);
    
	canvas[0].addEventListener("touchmove", function (e) {
        e.preventDefault();
        drawPoint(e.touches[0].clientX - offset.left, e.touches[0].clientY - offset.top);
    }, false);
	
	var onDrag = false;
	canvas[0].addEventListener("mousedown", function (e) {
		onDrag = true;
    }, false);
	
	canvas[0].addEventListener("mousemove", function (e) {
		if(onDrag){
			e.preventDefault();
	        drawPoint(e.clientX - offset.left, e.clientY - offset.top);	
		}
    }, false);
	canvas[0].addEventListener("mouseup", function (e) {
		onDrag = false;
    },false);
	
//	canvas[0].addEventListener("mouseleave", function (e) {
//		onDrag = false;
//    },false);
} 