var FormatUtils = {
	/**
	 * 将Date格式的数据转换为便于阅读的格式
	 * @param fmt
	 * @param date
	 * @returns
	 */
	dateFmt : function(fmt,date){
		var o = {   
			    "M+" : date.getMonth()+1,                 //月份   
			    "d+" : date.getDate(),                    //日   
			    "h+" : date.getHours(),                   //小时   
			    "m+" : date.getMinutes(),                 //分   
			    "s+" : date.getSeconds(),                 //秒   
			    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
			    "S"  : date.getMilliseconds()             //毫秒   
			  };   
			  if(/(y+)/.test(fmt))   
			    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
			  for(var k in o)   
			    if(new RegExp("("+ k +")").test(fmt))   
			  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
			  return fmt;  
	},
	
	/**
	 * 将Data格式数据转换为时间,最大单位为:天
	 * @param time
	 */
	timeFmt:function(time) {
		var sec = parseInt(time / 1000);
		var min = parseInt(sec / 60);
		sec = sec % 60;
		var hours = parseInt(min / 60);
		min = min % 60;
		var day = parseInt(hours / 24);
		hours = hours % 60;
		var result = "";
		if(day != 0) {
			result += day + "天";
		}
		result += hours + "小时" + min + "分钟" + sec + "秒";
		return result;
		
	}
	
}
