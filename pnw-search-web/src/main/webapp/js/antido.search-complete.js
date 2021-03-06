/*
<form id="antido-search-form" class="navbar-form navbar-right">
    <div style="width: 100%" class="form-group">
      <input id="searchInput" style="width: 100%" type="text" class="form-control" placeholder="搜索..." autocomplete="off">
    </div>
</form> 
*/

function searchComplete (contextPath) {
	var position = $.cookie("PNW_POSITION");
	var cityId = null;
	if(position != null) {
		cityId = position.split("-")[0];
	}
	
	
	//搜索框自动补全
	$( "#searchInput" ).autocomplete({
		minLength: 1, //最小搜索字符数
		delay:300,
		//数据源
		source: function(request,response) { 
			//request.term里是输入的数据
			//reponse是tm个回调函数是个方法  参数就是数据
			var keyword = encodeURI(request.term);
			$.ajax({
				url:"http://localhost:8083/search/keywordQuery",
				data:{"keyword":keyword,"cityId":cityId},
				dataType:"jsonp",
				jsonp:"callback",
				type:"GET",
				success:function(data) {
					if(data.length > 0) {
		  				response(data);
					} else response([{"id":-1}]);
				}
			});
		},
	      
		//当光标移动到某个选项时
		focus: function( event, ui ) {
			var name = ui.item.name.replace("<span style='color:red'>","");
			name = name.replace("</span>","");
			$("#seachInput" ).val(name);
			return false;
		},
	      
	     //当一个选项被选中时
		select: function( event, ui ) {
			window.open("http://localhost:8086/?parkId=" + ui.item.id);
			return false;
		},
	      
	})

	.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		if(item.id == -1) return $("<li>").append("<span style='color:#aaa;'>找不到到相符的停车场...<span/>").appendTo( ul );
	  	return $( "<li>" )
	    .append( "<a>" + item.name + "(剩余:" + item.remainCount + ")"  +
	    		 "<br>"+"<span style='color:#999;font-size: 10px'>" + item.districtName +"-"+item.desc+"</span></a>" )
	    .appendTo( ul );
	};    
}