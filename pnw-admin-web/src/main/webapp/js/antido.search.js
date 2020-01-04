function searchComplete (contextPath) {
	//搜索框自动补全
	$( "#searchInput" ).autocomplete({
		minLength: 1, //最小搜索字符数
		delay:300,
		//数据源
		source: function(request,response) { 
		//request.term里是输入的数据
		//reponse是tm个回调函数是个方法  参数就是数据
		$.ajax({
			url:contextPath+"/search/keywordQuery",
			data:{"keyword":request.term},
			dataType:"json",
			type:"POST",
			success:function(data) {
				if(data.length > 0) {
	  				response(data);
				} 
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
			//alert(ui.item.value);
			$( "#seachInput" ).val( ui.item.name );
			$( "#park-id" ).val( ui.item.id );
			if(ui.item.state =="使用中") {
				window.location.href=contextPath+"/space/list?park_id="+ui.item.id; 
			} else if (ui.item.state == "开发中"){
				window.location.href=contextPath+"/park/spaceEdit?parkId="+ui.item.id;
			}
			return false;
		},
	      
	})

	.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
	  	return $( "<li>" )
	    .append( "<a>"+item.name+"("+item.cityName+")"+"<br>"+"<span style='color:#999;font-size: 10px'>"+item.desc+"</span></a>" )
	    .appendTo( ul );
	};    
}