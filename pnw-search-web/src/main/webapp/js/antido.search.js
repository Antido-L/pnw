/**
 * 查询条件回显对象
 */
function FilterEcho() {
	this.init = function() {
		parkType();
		spaceType();
		serviceType();
		chargeType();
		priceOrder();
		remainOrder();
		distanceOrder();
		priceLimit();
	}
	
	//获取url上指定参数的值
	var getQueryString = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			//返回第三个参数(参数的值)
			return unescape(r[2]);
		return null;
	}		
	
	//为指定id的对象新增query-checked属性,变为选中状态
	var queryChecked = function(id) {
		$("#"+id).removeClass("query-free").addClass("query-checked");
	}
	
	var orderChecked = function(id) {
		$("#"+id).removeClass("parkList-queryOrder").addClass("parkList-queryOrder-checked");
		$("#parkList-defaultOrder").removeClass("parkList-queryOrder-checked").addClass("parkList-queryOrder");
	}
	
	//回显parkType的选中状态
	var parkType = function() {
		var parkTypeQ = getQueryString("parkType");
		if(parkTypeQ != null) {
			queryChecked("parkInfo-type-"+parkTypeQ);
		} else {
			queryChecked("parkInfo-type-0");
		}
	}
	
	//回显spaceType的选中状态
	var spaceType = function() {
		var spaceTypeQ = getQueryString("spaceType");
		if(spaceTypeQ != null) {
			queryChecked("parkInfo-space-"+spaceTypeQ);
		} else {
			queryChecked("parkInfo-space-0");
		}
	}
	
	//回显serviceType的选中状态
	var serviceType = function() {
		var serviceTypeQ = getQueryString("serviceType");
		if(serviceTypeQ != null) {
			queryChecked("parkInfo-service-"+serviceTypeQ);
		} else {
			queryChecked("parkInfo-service-0");
		}
	}
	
	//回显chargeType的选中状态
	var chargeType = function() {
		var chargeTypeQ = getQueryString("chargeType");
		if(chargeTypeQ != null) {
			queryChecked("parkInfo-charge-"+chargeTypeQ);
		} else {
			queryChecked("parkInfo-charge-0");
		}
	}
	
	//回显priceOrder的选中状态
	var priceOrder = function() {
		var q = getQueryString("priceOrder");
		if(q != null && q == "true") {
			orderChecked("parkList-priceOrder");
		} 
	}
	
	//回显remainOrder的选中状态
	var remainOrder = function() {
		var q = getQueryString("remainOrder");
		if(q != null && q == "true") {
			orderChecked("parkList-remainOrder");
		} 
	}
	
	//回显distanceOrder的选中状态
	var distanceOrder = function() {
		var q = getQueryString("distanceOrder");
		if(q != null && q == "true") {
			orderChecked("parkList-distanceOrder");
		} 
	}
	
	//回显priceLimit的值
	var priceLimit = function() {
		var q = getQueryString("priceLimit");
		if(q != null) {
			//获取两个值
			var split = q.split("-");
			$("#lowPrice").val(split[0]);
			if(split.length > 1) {
				$("#highPrice").val(split[1]);	
			}
			
		} 
	}
}



/**
 * 根据参数名获取当前url中包含的参数字符串
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[0]);
	return null;
}

/**
 * 根据传入的参数名与参数值,改变当前get请求的参数,并重新提交
 */
function changeURL(parameter, value ,url) {
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
		window.location.replace(url);
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
		window.location.replace(url);
	}
}


//新增停车场类型过滤条件
function parkTypeQuery(q) {
	var url = window.location + "";
	url = removeOrder(getQueryString("currentPage"),url);
	if(url.charAt(url.length - 1) == "?") {
		url = url.substring(0,url.length - 1);
	}
	changeURL("parkType", q , url);
}

//新增车位类型过滤条件
function spaceTypeQuery(q) {
	var url = removeCurrentPage();
	changeURL("spaceType", q , url);
}

//新增停车场收费方式过滤条件
function serviceTypeQuery(q) {
	var url = removeCurrentPage();
	changeURL("serviceType", q, url);
}

//新增是否收费过滤条件
function chargeTypeQuery(q) {
	var url = removeCurrentPage();
	changeURL("chargeType", q , url);
}

//按照价格升序获取列表数据
function priceQueryOrder() {
	var q = getQueryString("priceOrder");
	//去除URL中的currentPage条件
	var url = removeCurrentPage();
	if(q == null) { //当还没有查询条件时,加入此查询条件
		changeURL("priceOrder", true , url);
	} else { //当已经有此查询条件时
		var isOrder = q.split("=")[1];
		if(isOrder == "true") changeURL("priceOrder", false, url);
		else changeURL("priceOrder", true, url);
	}
}

//按照剩余车位降序排列
function remainQueryOrder() {
	var q = getQueryString("remainOrder");
	var url = removeCurrentPage();
	if(q == null) { //当还没有查询条件时,加入此查询条件
		changeURL("remainOrder", true, url);
	} else { //当已经有此查询条件时
		var isOrder = q.split("=")[1];
		if(isOrder == "true") changeURL("remainOrder", false, url);
		else changeURL("remainOrder", true, url);
	}
	
}

//按照距离升序排列
function distanceQueryOrder() {
	var q = getQueryString("distanceOrder");
	var url = removeCurrentPage();
	if(q == null) { //当还没有查询条件时,加入此查询条件
		changeURL("distanceOrder", true, url);
	} else { //当已经有此查询条件时
		var isOrder = q.split("=")[1];
		if(isOrder == "true") changeURL("distanceOrder", false, url);
		else changeURL("distanceOrder", true, url);
	}
}

//新增价格区间过滤条件
function priceLimitQuery() {
	var lo = $("#lowPrice").val();
	var hi = $("#highPrice").val();
	//验证输入合法性
	/* var re1 = new RegExp("^\\d+$");
	var re2 = new RegExp("\\d+\.\\d+"); */
	
	var re = new RegExp("(^\\d+$)|(^\\d+\.\\d+$)")
	if(lo != "" && !re.test(lo)) {
		$("#priceLimitAlert").removeClass("hidden");
		return;
	}
	if(hi != "" && !re.test(hi)) {
		$("#priceLimitAlert").removeClass("hidden");
		return;
	}
	
	if(lo != "") {
		var url = removeCurrentPage();
		if(hi != "") changeURL("priceLimit", lo+"-"+hi , url); //低位加高位
		else changeURL("priceLimit", lo , url); //只有低位
	} else {
		if(hi != "") changeURL("priceLimit", "0-"+hi , removeCurrentPage()); //0 - 高位
		else { // 啥也没写 去除此过滤条件 刷新页面
			var url = window.location + "";
			var limitStr = getQueryString("priceLimit");
			if(limitStr == null) window.reload();
			
			if(limitStr.charAt(limitStr.length - 1) == "&") {
				limitStr = limitStr.substring(0, limitStr.length - 1);
			}
			url = removeOrder(limitStr, url);
			if(url.charAt(url.length - 1) == "?") {
				url = url.substring(0,url.length - 1);
			}
			window.location.replace(url);
		}
	}
}

/*
 * 恢复默认排序
 */
function defaultQueryOrder() {
	//将在URL中所有order相关参数去除
	var url = window.location + "";
	var priceStr = getQueryString("priceOrder");
	//好坑爹
	if(priceStr != null && priceStr.charAt(priceStr.length - 1) == "&") {
		priceStr = priceStr.substring(0, priceStr.length - 1);
	}
	
	var remainStr = getQueryString("remainOrder");
	if(remainStr != null && remainStr.charAt(remainStr.length - 1) == "&") {
		remainStr = remainStr.substring(0, remainStr.length - 1);
	}
	
	var distanceStr = getQueryString("distanceOrder");
	if(distanceStr != null && distanceStr.charAt(distanceStr.length - 1) == "&") {
		distanceStr = distanceStr.substring(0, distanceStr.length - 1);
	}
	
	url = removeOrder(priceStr, url);
	url = removeOrder(remainStr, url);
	url = removeOrder(distanceStr, url);
	//如果没有其他参数存在,去除"?"
	if(url.charAt(url.length - 1) == "?") {
		url = url.substring(0,url.indexOf("?")) + url.substring(url.indexOf("?") + 1 , url.length);
	}
	window.location.replace(url);
}

/**
 * 将传入URL中的 target 字符串去除后返回新的URL字符串
 */
function removeOrder(target,url) {
	if(target == null) return url;
	var start = url.indexOf(target);
	var lo = url.substring(0, start);
	var hi = url.substring(start + target.length, url.length);
	return lo + hi;
}

function removeCurrentPage() {
	var url = window.location + "";
	url = removeOrder(getQueryString("currentPage"),url);
	if(url.charAt(url.length - 1) == "?") {
		url = url.substring(0,url.length - 1);
	} 
	
	return url;

}