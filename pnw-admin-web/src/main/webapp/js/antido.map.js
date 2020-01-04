/**
 * baiduMap
 */
//创建和初始化地图函数
function initMap(ratio){
    createMap(ratio);//创建地图
    setMapEvent();//设置地图事件
    addMapControl();//向地图添加控件
    baiduMapSearch();
    //addMarker();//向地图中添加marker
    //addPolyline();//向地图中添加线
}

//创建地图函数：
function createMap(ratio){
	$("#baiduMap").height($("#baiduMap").width() * ratio);
    var map = new BMap.Map("baiduMap");//在百度地图容器中创建一个地图
    var point = new BMap.Point(123.257065,41.745769);//定义一个中心点坐标
    map.centerAndZoom(point,18);//设定地图的中心点和坐标并将地图显示在地图容器中
    window.map = map;//将map变量存储在全局
    window.mapRatio = ratio;
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
	var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_RIGHT,type:BMAP_NAVIGATION_CONTROL_LARGE});
	map.addControl(ctrl_nav);
    //向地图中添加缩略图控件
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:0});
	map.addControl(ctrl_ove);
    //向地图中添加比例尺控件
	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
	map.addControl(ctrl_sca);
}

//标注点数组
var markerArr = [
	 //icon-w:图标宽度
	 //icon-h:图标高度
	 //icon-l:在图片中的左像素点
	 //icon-t:在图片中的右像素点,
	 //icon-x:1
	 //icon-lb:标签的X轴偏移}
     //{title:"我的标记",content:"我的备注",point:"116.329813|39.852053",isOpen:0,icon:{w:21,h:21,l:46,t:46,x:1,lb:10}}
];
//创建marker，返回所创建的marker
function addMarker(markerArr){
	var markerList = [];
    for(var i=0;i<markerArr.length;i++){
        var json = markerArr[i];
        //标记位置
        var p0 = json.point.split("|")[0];
        var p1 = json.point.split("|")[1];
        var point = new BMap.Point(p0,p1);
        //生成标记样式
		var iconImg = createIcon(json.icon);
        //生成标记
        var marker = new BMap.Marker(point,{icon:iconImg});
		var iw = createInfoWindow(i, markerArr);
		var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+20,0)});
		marker.setLabel(label);
        map.addOverlay(marker);
        //设置laber的CSS
        label.setStyle({
                    borderColor:"#808080",
                    color:"#333",
                    cursor:"pointer",
                    maxWidth:"none" //bootstrap对laber的CSS与百度地图不兼容得解决办法
        });
		
        //添加事件
		(function(){
			var index = i;
			var _iw = createInfoWindow(i, markerArr);
			var _marker = marker;
			/*//可拖动
			_marker.enableDragging();
			marker.addEventListener("dragend", function(e){    
			    console.log("当前位置：" + e.point.lng + ", " + e.point.lat);    
			})*/
			_marker.addEventListener("click",function(){
			    this.openInfoWindow(_iw);
		    });
		    _iw.addEventListener("open",function(){
			    _marker.getLabel().hide();
		    })
		    _iw.addEventListener("close",function(){
			    _marker.getLabel().show();
		    })
			label.addEventListener("click",function(){
			    _marker.openInfoWindow(_iw);
		    })
			if(!!json.isOpen){
				label.hide();
				_marker.openInfoWindow(_iw);
			}
		})()
		markerList.push(marker);
    }
    return markerList;
}
//创建InfoWindow
function createInfoWindow(i,markerArr){
    var json = markerArr[i];
    var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>"+json.content+"</div>");
    return iw;
}
//创建一个Icon
function createIcon(json){
    var icon = new BMap.Icon("../images/map-marker.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,5),anchor:new BMap.Size(json.w / 2,json.h)})
    return icon;
}

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
			url:"http://api.map.baidu.com/place/v2/search",
			data:{
				"query":request.term,
				"region":"沈阳",
				"output":"json",
				"scope":"2",
				"ak":"yI43GzysGKGcADFGUo6etBG6kMbmbO7I",
				},
			dataType:"jsonp",
			type:"GET",
			success:function(data) {
				if(data != null) {
					console.log(data.results)
	  				response(data.results);
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
			$( "#mapSearch" ).val( ui.item.name );
			//被选中目的地的坐标
			var point = new BMap.Point(ui.item.location.lng, ui.item.location.lat);
			//移动到目标位置
			map.panTo(point);
			// 创建标注    
			var marker = new BMap.Marker(point); 
			//添加标注
			map.addOverlay(marker);
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
		    .append( "<a>"+item.name+"<br>"+"<span style='color:#999;font-size: 10px'>"+item.address+"</span></a>" )
		    .appendTo( ul );
		}		  	
	  	
	};    
}


//地图响应窗口缩放
$(window).resize(function() {
	$("#baiduMap").height($("#baiduMap").width() * mapRatio);
});



var plPoints = [{style:"solid",weight:4,color:"#f00",opacity:0.6,points:["116.286124|39.945476","116.278075|39.884823","116.253928|39.880836","116.296472|39.888809"]}
	 ];
//向地图中添加线函数
function addPolyline(){
	for(var i=0;i<plPoints.length;i++){
		var json = plPoints[i];
		var points = [];
		for(var j=0;j<json.points.length;j++){
			var p1 = json.points[j].split("|")[0];
			var p2 = json.points[j].split("|")[1];
			points.push(new BMap.Point(p1,p2));
		}
		var line = new BMap.Polyline(points,{strokeStyle:json.style,strokeWeight:json.weight,strokeColor:json.color,strokeOpacity:json.opacity});
		map.addOverlay(line);
	}
}