/**
 * 根据参数名获取当前url中包含的参数字符串
 */ 
function getQueryString(name)
{
   var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
   var r = window.location.search.substr(1).match(reg);
   if(r!=null) return unescape(r[0]); return null;
}

/**
 * 根据传入的参数名与参数值,改变当前get请求的参数,并重新提交
 */
function changeURL(parameter, value) {
	//判断当前URL是否含有参数
	if (window.location.search.substr(0).length == 0) {
		var url = window.location;
   		//把url转为字符串 拼接参数
   		url = url+"?"+parameter+"="+value;
   		//重新发送请求
   		window.location.replace(url);
	} else {
		// 获取当前URL所含参数字符串对象
   		var currentParameter = getQueryString(parameter);
   		var url = window.location;
   		url = url+""; 
   		//alert(currentParameter);
		//判断当前URL是否含有所传入的参数
		if(currentParameter == null) {
			//如果之前并未包含传入的参数,拼接新参数
			url = url+"&"+parameter+"="+value;
		} else { 
			//GetQueryString方法会获得带有&符的参数字符串
			if(currentParameter.indexOf("&") == -1) { //如果是唯一的参数
  				//将原参数对象替换
		   		url = url.replace(currentParameter,parameter+"="+value);
			} 
			if(currentParameter.indexOf("&") > 0) { //是第一个,但不是唯一一个
				url = url.replace(currentParameter,parameter+"="+value+"&");
			}
			if(currentParameter.indexOf("&") == 0) { //自己不是第一个参数
				//判断是否是最后一个参数
				if(currentParameter.charAt(currentParameter.length-1) == "&") { //不是最后一个参数
					url = url.replace(currentParameter,"&"+parameter+"="+value+"&");
				} else { //是最后一个参数
					url = url.replace(currentParameter,"&"+parameter+"="+value);
				}
			}
		}
   		//转到新url
   		window.location.replace(url);
	}
}

/**
 * 载入下拉区域选择器下拉选数据
 */
function loadSelect(contextPath) {
	//获取URL中的参数
	var parameter = window.location.search.substr(1);
	//正则匹配 url中区域id的数据
	var reg = new RegExp("(.*Id)=([0-9]+)");
	var res = parameter.match(reg);
	if(res == null) { //当页面中没有需要回显的数据时 默认加载provinceSelcet数据
		$.ajax({
  			url:contextPath+"/region/provinceJson",
  			type:"POST",
  			dataType:"json",
  			success:function(data) {
  				var $select = $("#provinceSelect");
  				for (var i = 0; i < data.length; i++) {
					var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
					$select.append($option);
				}
  			}
  		});
	} else {
		var value = res[2];
		//根据url中的参数值先服务端索要回显数据对象 
		$.ajax({
  			url:contextPath+"/region/backJsonByRegionId",
  			type:"POST",
  			data:{"regionId":value},
  			dataType:"json",
  			success:function(data) {
  				//回显streetSelect
  				if(data.streetEcho != null) {
	  				var $streetSelect = $("#streetSelect");
	  				for (var i = 0; i < data.streetEcho.length; i++) {
	  					var $option = "<option value="+data.streetEcho[i].id+">"+data.streetEcho[i].name+"</option>";
						$streetSelect.append($option);
					}
	  				$streetSelect.find("option[value='"+data.selectedEcho.streetSelected+"']").attr("selected","selected");
  				}
  				
  				//回显districtSelect
  				if(data.districtEcho != null) {
	  				var $districtSelect = $("#districtSelect");
	  				for (var i = 0; i < data.districtEcho.length; i++) {
	  					var $option = "<option value="+data.districtEcho[i].id+">"+data.districtEcho[i].name+"</option>";
						$districtSelect.append($option);
					}
	  				$districtSelect.find("option[value='"+data.selectedEcho.districtSelected+"']").attr("selected","selected");
	  				
	  				//如果是需要回显的最后一级数据 , 自动加载下一级下拉选内的数据
	  				$.ajax({
	  		  			url:contextPath+"/region/StreetJson",
	  		  			type:"POST",
	  		  			data:{"district_id":data.selectedEcho.districtSelected},
	  		  			dataType:"json",
	  		  			success:function(data) {
	  		  				var $select = $("#streetSelect");
	  		  				for (var i = 0; i < data.length; i++) {
	  							var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
	  							$select.append($option);
	  						}
	  		  			}
	  		  		});
  				} 
  				
  				//回显citySelect
  				if(data.cityEcho != null) {
	  				var $citySelect = $("#citySelect");
	  				for (var i = 0; i < data.cityEcho.length; i++) {
	  					var $option = "<option value="+data.cityEcho[i].id+">"+data.cityEcho[i].name+"</option>";
						$citySelect.append($option);
					}
	  				$citySelect.find("option[value='"+data.selectedEcho.citySelected+"']").attr("selected","selected");
	  				
	  				//如果是需要回显的最后一级数据 , 自动加载下一级下拉选内的数据
	  				$.ajax({
	  		  			url:contextPath+"/region/getJsonByParentId",
	  		  			type:"POST",
	  		  			data:{"parent_id":data.selectedEcho.citySelected},
	  		  			dataType:"json",
	  		  			success:function(data) {
	  		  				var $select = $("#districtSelect");
	  		  				for (var i = 0; i < data.length; i++) {
	  		  					if(data[i].name == "市辖区") continue;
	  							var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
	  							$select.append($option);
	  						}
	  		  			}
	  		  		});
  				}
  				
  				//回显provinceSelect
  				if(data.provinceEcho != null) {
	  				var $provinceSelect = $("#provinceSelect");
	  				for (var i = 0; i < data.provinceEcho.length; i++) {
	  					//回显被选数据
	  					var $option = "<option value="+data.provinceEcho[i].id+">"+data.provinceEcho[i].name+"</option>";
						$provinceSelect.append($option);
					}
  					$provinceSelect.find("option[value='"+data.selectedEcho.provinceSelected+"']").attr("selected","selected");
  					
  					//如果是需要回显的最后一级数据 , 自动加载下一级下拉选内的数据
  					$.ajax({
  			  			url:contextPath+"/region/getJsonByParentId",
  			  			type:"POST",
  			  			data:{"parent_id":data.selectedEcho.provinceSelected},
  			  			dataType:"json",
  			  			success:function(data) {
  			  				var $select = $("#citySelect");
  			  				for (var i = 0; i < data.length; i++) {
  								var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
  								$select.append($option);
  							}
  			  			}
  			  		});
  				}
  			}
  		}); // ajax 结束
	} //if(res == null) 结束
};


/**
 *	get提交区域选择器数据
 */
function submitRegionChoose() {
	//逐级查看被选项
	var streetId = $("#streetSelect").val();
	if(streetId != "" && streetId != undefined) {
		var URL = window.location.href;
			var URLStr = URL.split("?")[0];
			window.location.href = URLStr+"?regionId="+streetId;
	} else {
		var districtId = $("#districtSelect").val();
		if(districtId != "" && districtId != undefined) {
			var URL = window.location.href;
  			var URLStr = URL.split("?")[0];
  			window.location.href = URLStr+"?regionId="+districtId;
		} else {
			var cityId = $("#citySelect").val();
			if(cityId != "" && districtId != undefined) {
				var URL = window.location.href;
	  			var URLStr = URL.split("?")[0];
	  			window.location.href = URLStr+"?regionId="+cityId;
			} else {
				var provinceId = $("#provinceSelect").val();
				if(provinceId != "" && provinceId != undefined) {
					var URL = window.location.href;
		  			var URLStr = URL.split("?")[0];
		  			window.location.href = URLStr+"?regionId="+provinceId;
				} else {
					alert("请选择一个想要查看的范围");
				}
			}
		}
	} //if(streetId != "" && streetId != undefined) 结束
}




/**
 * 省一级选择加载city一级下拉选数据
 */
function loadCitySelect(contextPath) {
	//清空下级select
	$("#citySelect").empty();
	$("#citySelect").append("<option value=''>---市/市辖区---</option>");
	$("#districtSelect").empty();
	$("#districtSelect").append("<option value=''>---区/县---</option>");
	$("#streetSelect").empty();
	$("#streetSelect").append("<option value=''>---街道/片区---</option>");
	
	//加载下一级区域数据
	var selectId = $("#provinceSelect").find("option:selected").val();
	$.ajax({
		url:contextPath+"/region/getJsonByParentId",
		type:"POST",
		data:{"parent_id":selectId},
		dataType:"json",
		success:function(data) {
			var $select = $("#citySelect");
			for (var i = 0; i < data.length; i++) {
				var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
				$select.append($option);
			}
		}
	});
}


/**
 * 根据city一级所选项加载district下拉选数据
 */
function loadDistrictSelect(contextPath){
	//清空下级select
	$("#districtSelect").empty();
	$("#districtSelect").append("<option value=''>---区/县---</option>");
	$("#streetSelect").empty();
	$("#streetSelect").append("<option value=''>---街道/片区---</option>");
	
	//加载区/县数据
	var selectId = $("#citySelect").find("option:selected").val();
	$.ajax({
		url:contextPath+"/region/getJsonByParentId",
		type:"POST",
		data:{"parent_id":selectId},
		dataType:"json",
		success:function(data) {
			var $select = $("#districtSelect");
			for (var i = 0; i < data.length; i++) {
				if(data[i].name == "市辖区") continue;
				var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
				$select.append($option);
			}
		}
	});
}

/**
 * 根据district一级所选项加载district下拉选数据
 * @param contextPath
 */
function loadStreetSelect(contextPath) {
	//清空下级select
	$("#streetSelect").empty();
	$("#streetSelect").append("<option value=''>---街道/片区---</option>");
	
	//加载街道数据数据
	var selectId = $("#districtSelect").find("option:selected").val();
	$.ajax({
		url:contextPath+"/region/StreetJson",
		type:"POST",
		data:{"district_id":selectId},
		dataType:"json",
		success:function(data) {
			var $select = $("#streetSelect");
			for (var i = 0; i < data.length; i++) {
				var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
				$select.append($option);
			}
		}
	});
}