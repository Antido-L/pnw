/**
 * 模拟图工具
 */
var ModUtils = {
		
	param:{
		contextPath:null,
		parkId:null
	},
		
	/**
	 * 初始化模拟图
	 * 异步向后台请求停车场模拟图数据
	 * 请求成功后绘制停车场模拟图
	 * parkId:目标停车场
	 */
	init:function(contextPath, parkId) {
		this.param.contextPath = contextPath;
		this.parkId = parkId;
		console.log(this);
		$.ajax({
			url:this.param.contextPath + "/getParkMapByParkId",
  			type:"POST",
  			data:{"parkId":parkId},
  			dataType:"json",
  			success:function(data) { //data:当前停车场内mod数据
  				console.log(data);
  				if(data == null) {
  					return;
  				}
  				$(".mod-space").removeClass("hidden");
				$(".mod-space").empty();
				$(".mod-space").height($(".mod-space").width() * 0.618);
				//判断车位方向
				if(data.direction == '0') {
					//绘制模拟图背景板
					ModUtils.modMaker(100,60,data.row,data.col,10);
				} else{
					ModUtils.modMaker(60,100,data.row,data.col,10);
				}
				
				//绘制模型项
				ModUtils.itemMaker(data.items, data.direction);
				
				//加载车位数据
				ModUtils.makeSpaceMod(parkId);
  			}
		});
		//寻找当前用户的车位
		this.findUserSpaceMod();
	},
		
		
	/**
	 * 载入车辆模型数据 
	 */	
	carItemMaker:function(data) {
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
	},	
	
	/**
	 * 在mod-space中绘制停车场模型
	 * width:最小组件宽度
	 * height:最小组件高度
	 * row:行个数
	 * col:列个数
	 * ratio:每次滚动鼠标改变高度值
	 */
	modMaker:function(width,height,row,col,ratio) {
		var $space = $(".mod-space");
		for(var x = 0 ; x < row ; x++) {
			var $rowDiv = $("<div id ='row-"+x+"' class='mod-row'></div>");
			for(var y = 0 ; y < col ; y++) {
				var $item = $("<div id='item-"+x+"-"+y+"' class='mod-item' ondblclick='ModUtils.clickItem("+x+","+y+")' onMouseover='ModUtils.mouOver("+x+","+y+")' onMouseout='ModUtils.mouOut("+x+","+y+")'></div>");
				//设置初始宽高
				$item.width(width);
				$item.height(height);
				$rowDiv.append($item);
			}
			$space.append($rowDiv);
		}
		
		//更改mod内容器尺寸
		this.changeRowSize(col,width,height);
		
		
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
	            ModUtils.changeRowSize(col, width + widthChange, height + heightChange);
	        } else {
	        	if(width * col <= spaceWidth && height * row <= spaceHeight) {
	        		return ;
	        	}
	        	
	            $(".mod-item").width(width - widthChange);
	            $(".mod-item").height(height - heightChange);
	            
	            ModUtils.changeRowSize(col, width - widthChange, height - heightChange);
	        }
	        return false;
		});
	}, //modMaker结束
	
	/**
	 * 改变行容器大小
	 * col:列个数
	 * width:最小模块组件宽度
	 * height:最小模块组件高度
	 */
	changeRowSize:function(col,width,height) {
		$(".mod-row").width(width * col);
		$(".mod-row").height(height);
	},
	
	/**
	 * item鼠标悬停事件 
	 * 在鼠标悬停后改变加深所在行列的背景颜色
	 * x:行坐标
	 * y:列坐标
	 */
	mouOver:function(x,y) {
		var rowSize = $(".mod-space > .mod-row").length;
		var colSize = $(".mod-row > .mod-item").length / rowSize;
		
		//console.log($("#item-"+x+"-"+y).find(".mod-item-success"));
		
		
		//计算悬浮后的模型背景色
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
	},
	
	/**
	 * item鼠标离开事件 
	 * 恢复初始化时的颜色
	 * x:行坐标
	 * y:列坐标
	 */
	mouOut:function(x,y) {
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
	},
	
	/**
	 * 绘制模型项
	 * 根据模型数据逐个绘制有效的模型项
	 * item:模型数据
	 * direction:模型方向
	 */
	itemMaker:function(items, direction) {
		//遍历模型项
		//根据坐标插入模型HTML代码
		for(var i = 0 ; i < items.length ; i++) {
			var $div = $("<div class='mod-item-empty'></div>");
			if(items[i].state == '1') {  //空槽
				if(direction == '0') {
					$div.append($("<img class='mod-item-img' src='"+this.param.contextPath+"/images/car-empty-x.png'/>"));
				} else {
					$div.append($("<img class='mod-item-img' src='"+this.param.contextPath+"/images/car-empty-y.png'/>"));
				}
				$("#item-"+items[i].xAxis+"-"+items[i].yAxis).append($div);
				$("#item-"+items[i].xAxis+"-"+items[i].yAxis).attr("itemtype","1");
				
			} else if (items[i].state == '2') { //静止使用
				if(direction == '0') {
					$div.append($("<img class='mod-item-img' src='"+this.param.contextPath+"/images/car-unable-x.png'/>"));
				} else {
					$div.append($("<img class='mod-item-img' src='"+this.param.contextPath+"/images/car-unable-y.png'/>"));
				}
				$("#item-"+items[i].xAxis+"-"+items[i].yAxis).append($div);
				$("#item-"+items[i].xAxis+"-"+items[i].yAxis).attr("itemtype","2");
			} 
		}
	},
	
	/**
	 * 加载车位数据
	 * 根据停车场ID向服务端异步请求停车场内车位列表
	 * 根据车位数据绘制车位模拟图
	 * @param parkId
	 */
	makeSpaceMod: function(parkId) {
		//加载车位情况
		$.ajax({
			url:this.param.contextPath + "/allSpaceByParkId",
  			type:"POST",
  			data:{"parkId":parkId},
  			dataType:"JSON",
  			success:function(data) { //包含当前停车场内车位列表
  				console.log(data);
  				var reservingSize = 0;
  				var usingSize = 0;
  				var total = 0;
  				for(var i = 0 ; i < data.length ; i++) {
  					if (data[i].node != null && data[i].node.is_online) { //有节点,切节点在线无异常
  						total++;
  						if(data[i].using_state == '1') { //车位正在被使用
  							$("#item-"+data[i].x_axis+"-"+data[i].y_axis).find("div[class='mod-item-empty']").attr("class","mod-item-danger");
  							usingSize++;
  						} else if(data[i].using_state == '2') { //车位被预约
  							$("#item-"+data[i].x_axis+"-"+data[i].y_axis).find("div[class='mod-item-empty']").attr("class","mod-item-warning");
  							reservingSize++;
  						}
  					} else { //节点异常 车位无法使用
						$("#item-"+data[i].x_axis+"-"+data[i].y_axis).find("div[class = 'mod-item-empty']").find("img[class = 'mod-item-img']").attr("src",ModUtils.param.contextPath + "/images/car-unable-y.png");
  						$("#item-"+data[i].x_axis+"-"+data[i].y_axis).find("div[class = 'mod-item-empty']").removeClass('mod-item-empty').addClass("mod-item-unable");
  						
  					}
  					//在每一个有车的的节点上添加spaceId
  					//在编辑车位是需要spaceId来判断是新增操作还是更新操作
  					$("#item-"+data[i].x_axis+"-"+data[i].y_axis).attr("spaceId",data[i].idStr);
  					//加载进度条
  					//加点延时更有逼格
  					setTimeout(function(){ModUtils.loadProgress(total, usingSize, reservingSize)},1500);
  				}
  			}
		});
	},
	
	/**
	 * 加载进度条
	 * total:总数
	 * usingSize:正在使用的个数
	 * reservingSize:被预约的个数
	 */
	loadProgress:function(total, usingSize, reservingSize) {
		var used = usingSize / total * 100;
		var reserved = reservingSize / total * 100;
		var free = 100 - used - reserved;
		$("#progress-used").css({"width":used+"%"})
		$("#progress-reserved").css({"width":reserved+"%"})
		$("#progress-free").css({"width":free+"%"})
	},
	
	/**
	 * 模拟图单击事件
	 * 异步加载被点击的车位数据,与用户使用情况作比较
	 * 根据使用情况弹出模态框
	 * @param x 坐标
	 * @param y 坐标
	 * @returns
	 */
	clickItem:function (x,y) {
		//空白双击无效
		if($("#item-"+x+"-"+y).children().length == 0) {
			return ;
		}
		//车位无法使用
		if($("#item-"+x+"-"+y).children().attr('class') == "mod-item-unable") {
			//弹出提示框
			$('#item-unableCheck-model').modal({
					keyboard: false
				});
			return;
		}
			
		var spaceId = $("#item-"+x+"-"+y).attr("spaceId");
		
		if(spaceId == undefined) {
			//弹出提示框
			$('#item-unableCheck-model').modal({
					keyboard: false
				});
			return;
		}
		$("#space-modal-title").html("车位编号:"+spaceId);
		InputTools.cleanOpCode();
		//异步请求model数据
  		$.ajax({
  			url:this.param.contextPath + "/modelJsonBySpaceId",
  			data:{"spaceId":spaceId},
  			dataType:"json",
  			type:"POST",
  			success:function(data){
  				if(data == null) {
  					return ;
  				}
  				console.log(data);
  				//显示数据
  				$("#model-title-spaceCode").html(data.space.code);
  				
  				//分情况弹出模态框
  				if(data.space.using_state == 0) { //该车位空闲
	  				//向隐藏域中添加操作所需数据
	  				$("#model-nodeId").val(data.space.node.id);
	  				$("#model-controllerId").val(data.space.node.parent_id);
	  				$("#model-spaceId").val(data.space.idStr);
	  				
	  				$("#use-model-spaceCode").html(data.space.code);
	  				$("#reserve-model-spaceCode").html(data.space.code);
	  				//弹出操作框
	  				$('#item-freeCheck-model').modal({
	  					keyboard: false
	  				})
  				} else if(data.space.using_state == 2) { //已经被预约
  					//判断使用者是否为当前用户
  					if(UserUtils.user != null) {
  						if(UserUtils.user.id == data.user.id) {
  							ModUtils.showUsingState(data.space,UserUtils.user);
  							return;
  						}
  					}
  					//弹出被预约提示
  					$("#reserveCheck-title-spaceCode").html(data.space.code);
	  				$('#item-reserveCheck-model').modal({
	  					keyboard: false
	  				})
  				} else if(data.space.using_state == 1) { //车位正在被使用
  					//判断使用者是否为当前用户
  					if(UserUtils.user != null) {
  						if(UserUtils.user.id == data.user.id) {
  							ModUtils.showUsingState(data.space,UserUtils.user);
  							return;
  						}
  					}
  					
  					//显示车位被使用信息
  					$("#usingCheck-title-spaceCode").html(data.space.code);
  					//弹出操作框
	  				$('#item-usingCheck-model').modal({
	  					keyboard: false
	  				})
  				}
  				return;
  			}
  		});
	},//item双击事件结束
	
	/**
	 * 当前用户双击的是自己正在使用的车位
	 * 向用户展示当前车位使用情况
	 * @param space
	 * @param user
	 */
	showUsingState:function(space,user) {
		//当用户未确认时提醒确认
		
		$("#model-user-parkInfo-hidden-controllerId").val(space.node.parent_id);
		//车位在预约状态时显示预约状态
		//用户处于离开时显示离开提醒
		console.log(space);
		console.log(user);
		
		var state = user.state;
		if(state == 0) { //用户处于正常状态显示停车状况
			var car = UserUtils.user.car.name + "-" + UserUtils.user.car.license;
			var parkTime = FormatUtils.dateFmt("yyyy年MM月dd日  hh:mm:ss",new Date(UserUtils.user.space.parked_time));
			
			var timeCount = FormatUtils.timeFmt(new Date() - UserUtils.user.space.parked_time);
			
			var price = UserUtils.user.space.park.price;
			var priceCount = "";
			if(price == 0) {
				price = "免费"
				priceCount = "0";
			} else {
				//计算小计
				// 创建时间 - 当前时间  = 使用的时间
				var timeUsed = (new Date() - UserUtils.user.space.parked_time) / 1000; //秒
				console.log("timeUsed" + timeUsed);
				// 使用的时间 - 免费时间 = 计费时间
				var time = parseInt(timeUsed) - UserUtils.user.space.park.free_time; //秒
				if(time <= 0) {
					priceCount =  "未满"+ parseInt(UserUtils.user.space.park.free_time/60) +"分钟不计费";
				} else {
					//不足1小时 按照1小时收费
					priceCount = parseInt((((time / 3600) + 1) * price)/100) + "元";
				}
			}
			
			var leaveTime = UserUtils.user.space.leaving_time;
			var leave = "暂无";
			if(leaveTime != null && leaveTime > 0) {
				leave = FormatUtils.dateFmt("yyyy年MM月dd日  hh:mm:ss",new Date(UserUtils.user.space.leaving_time));
			}
			
			$("#model-user-parkInfo-car").text(car);
			$("#model-user-parkInfo-parkTime").text(parkTime);
			$("#model-user-parkInfo-timeCount").text(timeCount);
			$("#model-user-parkInfo-price").text(price / 100 + "元/小时");
			$("#model-user-parkInfo-priceCount").text(priceCount);
			$("#model-user-parkInfo-leave").text(leave);
			$('#model-user-parkInfo').modal({
				keyboard: false
			});
			
		} else if(state == 1) {//等待停车确认
			//弹出提示框要求用户确认停车
			var time = 8 - (new Date() - UserUtils.user.space.parked_time) / 1000 / 60;
			
			$("#model-user-checkWait-time").text(parseInt(time));
			$("#model-user-checkWait-name").text(UserUtils.user.nick_name);
			$("#model-user-checkWait-parkName").text(UserUtils.user.space.park.name);
			$("#model-user-checkWait-spaceCode").text(UserUtils.user.space.code);
			$('#model-user-checkWait').modal({
				keyboard: false
			});
		} else if(state == 2){//等待停车确认超时
			//弹出提示框要求用户驶离车位
			$("#alert-model-content-mid").html("<p style='color:#d43f3a'>本次停车已经超时，请空出车位。</p><p>空出车位后该订单会自动取消，不影响您下次使用。<p/>");
			$('#alert-model-mid').modal({
				keyboard: false
			});
		} else if(state == 3) {//等待车辆离开
			//弹出提示框
			$("#alert-model-content-mid").html("<p style='color:#d43f3a'>离开请求已经确认。</p><p>请尽快驶离车位，车辆离开后会自动完成订单结算。<p/>");
			$('#alert-model-mid').modal({
				keyboard: false
			});
		} else if(state == 4) { //车辆离开超时
			
		} else if(state == 5) {
			
		} else if(state == 6) { //正在预约
			$('#space-reserveConfirm-spaceCode').text(space.code);
			$('#space-reserveConfirm-model').modal({
				keyboard: false
			});
		}
	},
	/*
	$.ajax({
			url:this.param.contextPath+"/leaveSpace",
			data:{
				"nodeId":space.node.id,
				"controllerId":space.node.parent_id,
				"parkId":space.park.id,
				"spaceId":space.idStr,
				"userId":user.id,
				"token":UserUtils.getToken(),
				"opCode":123456
			},
			dataType:"json",
			type:"POST",
			success:function(data){
				console.log(data);
			}
	});
	*/
	
	/**
	 * 根据用户使用的车位更改模拟图类型
	 * User对象是异步获取的,为防止此方法在user加载完毕之前运行,定时获取 
	 */
	findUserSpaceMod:function() {
		if(UserUtils.user == null) {
			setTimeout(this.changeUserSpaceMod,1500);
		} else {
			this.changeUserSpaceMod(UserUtils.user);
		}
	},
	
	/**
	 * 根据用户使用的车位更改模拟图类型
	 * @param user
	 */
	changeUserSpaceMod:function() {
		//console.log(UserUtils)
		if(UserUtils.user == null || UserUtils.user.space == null) return ;
		//$(".mod-item").find("div[spaceId='"+ user.space.id +"']").attr("class","mod-item-success");
		//console.log(UserUtils.user.space.id);
		$(".mod-item[spaceId='"+UserUtils.user.space.idStr+"'] > div").attr("class","mod-item-success");
	}
};

