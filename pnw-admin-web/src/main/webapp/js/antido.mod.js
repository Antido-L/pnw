function makeCover() {
	
};

/**
 * 载入车辆模型数据 
 */
function carItemMaker(data) {
	for(var i = 0 ; i < data.length ; i++) {
		var x_position = data[i].position.split(",")[0];
		var y_position = data[i].position.split(",")[1];
		var $div = null;
		if(data[i].type == "0") {
			$div = $("<div class='mod-item-success'></div>");
		} else if (data[i].type == "1") {
			$div = $("<div class='mod-item-warning'></div>");
		} else if (data[i].type == "2") {
			$div = $("<div class='mod-item-danger'></div>");
		}
		$("#item-"+x_position+"-"+y_position).append($div);
	}
}

/**
 * 在mod-space中绘制停车场模型
 * width:最小组件宽度
 * height:最小组件高度
 * row:行个数
 * col:列个数
 * ratio:每次滚动鼠标改变高度值
 */
function modMaker(width,height,row,col,ratio) {
	var $space = $(".mod-space");
	for(var x = 0 ; x < row ; x++) {
		var $rowDiv = $("<div id ='row-"+x+"' class='mod-row'></div>");
		for(var y = 0 ; y < col ; y++) {
			var $item = $("<div id='item-"+x+"-"+y+"' class='mod-item' ondblclick='clickItem("+x+","+y+")' onMouseover='mouOver("+x+","+y+")' onMouseout='mouOut("+x+","+y+")'></div>");
			//设置初始宽高
			$item.width(width);
			$item.height(height);
			$rowDiv.append($item);
		}
		$space.append($rowDiv);
	}
	
	//更改mod内容器尺寸
	changeRowSize(col,width,height);
	
	
	//计算每次改变的宽高值
	var widthChange = ratio * (width / height);
	var heightChange = ratio;
	
	/**
	 * 模型容器鼠标滚动监听事件
	 */
	$('.mod-space').mousewheel(function(event, delta) {
		
		var width = $(".mod-item").width();
		var height = $(".mod-item").height();
		
		//去除获取宽高时数据误差
       	if(width % widthChange != 0) {
       		if(width % widthChange > (widthChange / 2)) {
       			width = width + widthChange - width % widthChange;
       		} else {
       			width = width - width % widthChange;
       		}
   			
   		} 
   		
   		if(height % heightChange != 0) {
   			if(height % heightChange > (heightChange / 2)) {
       			height = height + heightChange - height % heightChange;
       		} else {
       			height = height - height % heightChange;
       		}
   		}
   		
   		//获取鼠标滚轮动作
        var dir = delta > 0 ? 'Up' : 'Down';
        var spaceWidth= $(".mod-space").width();
        var spaceHeight = $(".mod-space").height();
        if (dir == 'Up') {
        	if(width * 7 > spaceWidth) {
        		return ;
        	}
        	
			//改变组件大小		           
            $(".mod-item").width(width + widthChange);
            $(".mod-item").height(height + heightChange);
            //更新行容器大小
            changeRowSize(col, width + widthChange, height + heightChange);
        } else {
        	if(width * col <= spaceWidth && height * row <= spaceHeight) {
        		return ;
        	}
        	
            $(".mod-item").width(width - widthChange);
            $(".mod-item").height(height - heightChange);
            
            changeRowSize(col, width - widthChange, height - heightChange);
        }
        return false;
	});
};

/**
 * 改变行容器大小
 * col:列个数
 * width:最小模块组件宽度
 * height:最小模块组件高度
 */
function changeRowSize(col,width,height) {
	$(".mod-row").width(width * col);
	$(".mod-row").height(height);
}




/**
 * item鼠标悬停事件 
 */
function mouOver(x, y) {
	var rowSize = $(".mod-space > .mod-row").length;
	var colSize = $(".mod-row > .mod-item").length / rowSize;
	
	//console.log($("#item-"+x+"-"+y).find(".mod-item-success"));
	
	//var oldItemColor = toHex($("#item-"+x+"-"+y).css("background-color"));
	var itemColor =  new gradientColor("#eeeeee",'#000000',15);
	var emptyColor =  new gradientColor("#FFFFFF",'#000000',15);
	var successColor =  new gradientColor("#8CC657",'#000000',15);
	var dangerColor =  new gradientColor("#d9534f",'#000000',15);
	var warningColor =  new gradientColor("#f0ad4e",'#000000',15);
	
	//更改列状态
	for(var i = 0 ; i < rowSize ; i++) {
		if($("#item-"+i+"-"+y).children().length == 0) {
			$("#item-"+i+"-"+y).css({"background-color":itemColor[1]});
			continue;
		}
		if($("#item-"+i+"-"+y).find(".mod-item-success").length > 0) {
			$("#item-"+i+"-"+y).find(".mod-item-success").css({"background-color":successColor[1]});
			continue;
		}
		if($("#item-"+i+"-"+y).find(".mod-item-danger").length > 0) {
			$("#item-"+i+"-"+y).find(".mod-item-danger").css({"background-color":dangerColor[1]});
			continue;
		}
		if($("#item-"+i+"-"+y).find(".mod-item-warning").length > 0) {
			$("#item-"+i+"-"+y).find(".mod-item-warning").css({"background-color":warningColor[1]});
			continue;
		}
		if($("#item-"+i+"-"+y).find(".mod-item-empty").length > 0) {
			$("#item-"+i+"-"+y).find(".mod-item-empty").css({"background-color":emptyColor[1]});
			continue;
		}
	}
	
	//更改行状态
	for(var i = 0 ; i < colSize ; i++) {
		if($("#item-"+x+"-"+i).children().length == 0) {
			$("#item-"+x+"-"+i).css({"background-color":itemColor[1]});
			continue;
		}
		if($("#item-"+x+"-"+i).find(".mod-item-success").length > 0) {
			$("#item-"+x+"-"+i).find(".mod-item-success").css({"background-color":successColor[1]});
			continue;
		}
		if($("#item-"+x+"-"+i).find(".mod-item-danger").length > 0) {
			$("#item-"+x+"-"+i).find(".mod-item-danger").css({"background-color":dangerColor[1]});
			continue;
		}
		if($("#item-"+x+"-"+i).find(".mod-item-warning").length > 0) {
			$("#item-"+x+"-"+i).find(".mod-item-warning").css({"background-color":warningColor[1]});
			continue;
		}
		if($("#item-"+x+"-"+i).find(".mod-item-empty").length > 0) {
			$("#item-"+x+"-"+i).find(".mod-item-empty").css({"background-color":emptyColor[1]});
			continue;
		}
		
	}
	
	//更改被选状态
	if($("#item-"+x+"-"+y).children().length == 0) {
		$("#item-"+x+"-"+y).css({"background-color":itemColor[3]});
	} else {
		if($("#item-"+x+"-"+y).find(".mod-item-success").length > 0) {
			$("#item-"+x+"-"+y).find(".mod-item-success").css({"background-color":successColor[3]});
		}
		if($("#item-"+x+"-"+y).find(".mod-item-danger").length > 0) {
			$("#item-"+x+"-"+y).find(".mod-item-danger").css({"background-color":dangerColor[3]});
		}
		if($("#item-"+x+"-"+y).find(".mod-item-warning").length > 0) {
			$("#item-"+x+"-"+y).find(".mod-item-warning").css({"background-color":warningColor[3]});
		}
		if($("#item-"+x+"-"+y).find(".mod-item-empty").length > 0) {
			$("#item-"+x+"-"+y).find(".mod-item-empty").css({"background-color":emptyColor[3]});
		}
	}
}

/**
 * item鼠标离开事件 
 * 恢复初始化时的颜色
 */
function mouOut(x,y) {
	var rowSize = $(".mod-space > .mod-row").length;
	var colSize = $(".mod-row > .mod-item").length / rowSize;
	
	//恢复列状态
	for(var i = 0 ; i < rowSize ; i++) {
		if($("#item-"+i+"-"+y).children().length == 0) {
			$("#item-"+i+"-"+y).css({"background-color":"#eeeeee"});
			continue;
		}
		if($("#item-"+i+"-"+y).find(".mod-item-success").length > 0) {
			$("#item-"+i+"-"+y).find(".mod-item-success").css({"background-color":"#8CC657"});
			continue;
		}
		if($("#item-"+i+"-"+y).find(".mod-item-danger").length > 0) {
			$("#item-"+i+"-"+y).find(".mod-item-danger").css({"background-color":"#d9534f"});
			continue;
		}
		if($("#item-"+i+"-"+y).find(".mod-item-warning").length > 0) {
			$("#item-"+i+"-"+y).find(".mod-item-warning").css({"background-color":"#f0ad4e"});
			continue;
		}
		if($("#item-"+i+"-"+y).find(".mod-item-empty").length > 0) {
			$("#item-"+i+"-"+y).find(".mod-item-empty").css({"background-color":"#FFFFFF"});
			continue;
		}
	}
	
	//恢复行状态
	for(var i = 0 ; i < colSize ; i++) {
		if($("#item-"+x+"-"+i).children().length == 0) {
			$("#item-"+x+"-"+i).css({"background-color":"#eeeeee"});
			continue;
		}
		if($("#item-"+x+"-"+i).find(".mod-item-success").length > 0) {
			$("#item-"+x+"-"+i).find(".mod-item-success").css({"background-color":"#8CC657"});
			continue;
		}
		if($("#item-"+x+"-"+i).find(".mod-item-danger").length > 0) {
			$("#item-"+x+"-"+i).find(".mod-item-danger").css({"background-color":"#d9534f"});
			continue;
		}
		if($("#item-"+x+"-"+i).find(".mod-item-warning").length > 0) {
			$("#item-"+x+"-"+i).find(".mod-item-warning").css({"background-color":"#f0ad4e"});
			continue;
		}
		if($("#item-"+x+"-"+i).find(".mod-item-empty").length > 0) {
			$("#item-"+x+"-"+i).find(".mod-item-empty").css({"background-color":"#FFFFFF"});
			continue;
		}
	}
	//$("#row-"+x).find("div[class='mod-item']").css({"background-color":"#eeeeee"});
}