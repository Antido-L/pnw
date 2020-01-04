/**
 * baiduMap
 */
//创建和初始化地图函数
function initMap(ratio){
    /*createMap(ratio);//创建地图
    setMapEvent();//设置地图事件
    addMapControl();//向地图添加控件
    baiduMapSearch();*/
    //addMarker();//向地图中添加marker
    //addPolyline();//向地图中添加线
	var mapUtil = new MapUtil();
	mapUtil.init(ratio);
	window.mapUtil = mapUtil;
}

function MapUtil() {
	this.init = function(ratio) {
		createMap(ratio);//创建地图
	    setMapEvent();//设置地图事件
	    addMapControl();//向地图添加控件
	    //baiduMapSearch();
	    echoTarget();
	}
	
	var default_x = 123.257065;
	var default_y = 41.745769;
	
	function createMap(ratio){
		$("#baiduMap").height($("#baiduMap").width() * ratio);
	    var map = new BMap.Map("baiduMap");//在百度地图容器中创建一个地图
	    var point = null;
	    //获取地图中心点
	    var pos = $.cookie("PNW_POSITION");
	    if(pos == null || pos == "") {
	    	point = new BMap.Point(default_x,default_y); //默认地图中心点
	    } else{
	    	var split = pos.split("-");
	    	if(split.length == 3) {
	    		point = new BMap.Point(split[1],split[2]);
	    	} else {
	    		point = new BMap.Point(default_x,default_y); //默认地图中心点
	    	}
	    }
	    //point = new BMap.Point(123.257065,41.745769);
	    map.centerAndZoom(point,11);//设定地图的中心点和坐标并将地图显示在地图容器中
	    window.map = map;//将map变量存储在全局
	    window.mapRatio = ratio;
	}
	
	var getQueryString = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			//返回第三个参数(参数的值)
			return r[2];
		return null;
	}
	
	//在搜索框回显目标地点信息,在地图中标注目标地点
	function echoTarget() {
		var target = getQueryString("target");
		
		if(target == null || target == "") {
			return;
		}
		var name = decodeURI(decodeURI(target));
		console.log(name);
		$("#mapSearch" ).val(name);
		var x = getQueryString("x");
		var y = getQueryString("y");
		var point = new BMap.Point(x,y)
		map.centerAndZoom(point,12);//设定地图的中心点和坐标并将地图显示在地图容器中
		addTargetMaker (point , name); //设置标注
	}
	
	//地图事件设置函数：
	function setMapEvent(){
	    map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
	    map.enableScrollWheelZoom();//启用地图滚轮放大缩小
	    map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
	}
	
	//地图控件添加函数：
	function addMapControl(){
	    //向地图中添加缩放控件
		//var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_RIGHT,type:BMAP_NAVIGATION_CONTROL_LARGE});
		//map.addControl(ctrl_nav);
	    //向地图中添加缩略图控件
		//var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:0});
		//map.addControl(ctrl_ove);
	    //向地图中添加比例尺控件
		var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
		map.addControl(ctrl_sca);
	}
	
	function addTargetMaker(point, name) {
		//设置标注图标样式
		var targetIcon = new BMap.Icon("../images/map-marker.png", new BMap.Size(18, 25), {    
	        anchor: new BMap.Size(9, 25),    
	        imageOffset: new BMap.Size(-18, -22)   // 设置图片偏移    
	    }); 
		var marker = new BMap.Marker(point, {icon: targetIcon});
		map.addOverlay(marker);
		
		//target标注
		var label = new BMap.Label(name,{});
		label.setStyle({
			fontSize:"15px",
	        borderColor:"#808080",
	        color:"#333",
	        cursor:"pointer",
	        maxWidth:"none" //bootstrap对laber的CSS与百度地图不兼容得解决办法
		});
		var content = label.getContent();
		label.setOffset(new BMap.Size(-content.length * 7,-30));
		marker.setLabel(label);
		
		marker.addEventListener("click", function(){    
		    alert("您点击了标注");    
		}); 
	}
	

	//var showParkInMap = function(x,y,name,i) {
	this.showParkInMap = function(x,y,name,i,desc) {
		if(x == null || x == "" || x == 0.0) {
			return;
		}
		//console.log(i + " " +name+" "+x+" "+y);
		var point = new BMap.Point(x, y);
	    var marker = new BMap.Marker(point);
	    map.addOverlay(marker);
	    
	    //编号标签
	    var numLabel = new BMap.Label(i,{});
	    numLabel.setStyle({
			fontSize:"14px",
			background:'none',
			color:'#fff',
			border:'none',
	        maxWidth:"none" //bootstrap对laber的CSS与百度地图不兼容得解决办法
		});
	    numLabel.setOffset(new BMap.Size(5,2));
		marker.setLabel(numLabel);
		
		var nameLabel = new BMap.Label(name,{});
		nameLabel.setStyle({
			fontSize:"14px",
			background:'none',
			color:'#fff',
			border:'none',
	        maxWidth:"none" //bootstrap对laber的CSS与百度地图不兼容得解决办法
		});
		
		marker.setTitle(name);
	    //numLabel.setOffset(new BMap.Size(5,2));
		//marker.setLabel(nameLabel);
		
		
		//名称标签
		/*var infoWindowOpt = {
			width:0,
			height:0,
		};
		
		var nameInfo = new BMap.InfoWindow(name,infoWindowOpt);
		var content = "<p></p><p style=''>"+name+"</p>"; 
		nameInfo.setContent(content);
		//marker.openInfoWindow(nameInfo);
		
		marker.addEventListener("mouseover", function() {
			marker.openInfoWindow(nameInfo);
		});
		marker.addEventListener("mouseout", function() {
			marker.closeInfoWindow(nameInfo);
		}); */
		
		switch(i) {
			case "1": {
				map.marker_1 = marker;
				break;
			} 
			case "2": {
				map.marker_2 = marker;
				break;
			} 
			case "3": {
				map.marker_3 = marker;
				break;
			} 
			case "4": {
				map.marker_4 = marker;
				break;
			} 
			case "5": {
				map.marker_5 = marker;
				break;
			} 
			case "6": {
				map.marker_6 = marker;
				break;
			} 
			case "7": {
				map.marker_7 = marker;
				break;
			} 
			case "8": {
				map.marker_8 = marker;
				break;
			} 
			default:break;
		}
		
		
	}
	
	//改变URL
	this.changeUrl = function(parameter, value ,url) {
		if(url == null) {
			url = window.location + "";
		}
		//判断当前URL是否含有参数
		//if (window.location.search.substr(0).length == 0) {
		if (url.indexOf("?") == -1) {
			//var url = window.location;
			//把url转为字符串 拼接参数
			url = url + "?" + parameter + "=" + value;
			//重新发送请求
			return url;
		} else {
			// 获取当前URL所含参数字符串对象
			var currentParameter = getQueryString(parameter);
			//判断当前URL是否含有所传入的参数
			if (currentParameter == null) {
				//如果之前并未包含传入的参数,拼接新参数
				url = url + "&" + parameter + "=" + value;
			} else {
				//GetQueryString方法会获得带有&符的参数字符串
				if (currentParameter.indexOf("&") == -1) { //如果是唯一的参数
					//将原参数对象替换
					url = url.replace(currentParameter, parameter + "="
							+ value);
				}
				if (currentParameter.indexOf("&") > 0) { //是第一个,但不是唯一一个
					url = url.replace(currentParameter, parameter + "="
							+ value + "&");
				}
				if (currentParameter.indexOf("&") == 0) { //自己不是第一个参数
					//判断是否是最后一个参数
					if (currentParameter
							.charAt(currentParameter.length - 1) == "&") { //不是最后一个参数
						url = url.replace(currentParameter, "&" + parameter
								+ "=" + value + "&");
					} else { //是最后一个参数
						url = url.replace(currentParameter, "&" + parameter
								+ "=" + value);
					}
				}
			}
			//转到新url
			return url;
		}
	}
}

//地图响应窗口缩放
$(window).resize(function() {
	$("#baiduMap").height($("#baiduMap").width() * window.mapRatio);
});

//创建一个搜索框
function baiduMapSearch() {
	//搜索框自动补全
	$( "#mapSearch" ).autocomplete({
		minLength: 1, //最小搜索字符数
		delay:300,
		  
		//数据源
		source: function(request,response) { 
		//request.term里是输入的数据
		//reponse是tm个回调函数是个方法  参数就是数据
		$.ajax({
			//url:"http://api.map.baidu.com/place/v2/search",
			url:"http://api.map.baidu.com/place/v2/suggestion",
			data:{
				"query":request.term,
				"region":"沈阳",
				"output":"json",
				//"scope":"2",
				"ak":"yI43GzysGKGcADFGUo6etBG6kMbmbO7I",
				},
			dataType:"jsonp",
			type:"GET",
			success:function(data) {
				if(data != null) {
					console.log(data.result);
	  				response(data.result);
				} 
			}
		});
	  },
	      
		//当光标移动到某个选项时
		focus: function( event, ui ) {
			/* var name = ui.item.name.replace("<span style='color:red'>","");
			name = name.replace("</span>",""); */
			$("#mapSearch" ).val(ui.item.name);
			return false;
		},
	      
	    //当一个选项被选中时
		select: function( event, ui ) {
			//同步刷新页面
			var target = getQueryString("target");
			
			if(target != null) {
				var url = window.location + "";
				//如果地名的最后一个字符是&会出现bug
				if(target.charAt(target.length - 1) == "&") {
					var targetLeft = target.split("=")[0];
					url = url.replace(encodeURI(target)+"",targetLeft + "=" + encodeURI(encodeURI(ui.item.name)) + "&");
				} else {
					var targetLeft = target.split("=")[0];
					url = url.replace(encodeURI(target)+"",targetLeft + "=" + encodeURI(encodeURI(ui.item.name)));
				}
				url = url.replace(/x=\d+\.\d+/,"x=" + ui.item.location.lng);
				url = url.replace(/y=\d+\.\d+/, "y=" + ui.item.location.lat);
				
				window.location.replace(url);
			} else {
				var xUrl = mapUtil.changeUrl("x",ui.item.location.lng);
				var yUrl = mapUtil.changeUrl("y",ui.item.location.lat,xUrl);
				var url = mapUtil.changeUrl("target",encodeURI(encodeURI(ui.item.name)),yUrl);
				window.location.replace(url);
			}
			
			return false;
		},
	      
	})
	
	.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		if(item.detail_info != undefined) {
		  	return $( "<li>" )
		    .append( "<a>"+item.name+"("+item.detail_info.tag+")"+"<br>"+"<span style='color:#999;font-size: 10px'>"+item.address+"</span></a>" )
		    .appendTo( ul );
		} else {
			return $( "<li>" )
		    .append( "<a>"+item.name+"<br>"+"<span style='color:#999;font-size: 10px'>"+item.district+"</span></a>" )
		    .appendTo( ul );
		}		  	
	  	
	};    
}

