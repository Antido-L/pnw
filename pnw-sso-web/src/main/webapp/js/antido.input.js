/**
 * input框操作工具类
 */
var InputTools = {
				
	minsInput:function(ths) {
		
	},
	
	hoursInput:function(ths) {
		
	},
	
	daysInputBlur:function(ths) {
		var day = $(ths).val();
		if(day == "" || day == '0') {
			$(ths).val("------");
		}
	},
	
	daysInput:function(ths) {
		var day = $(ths).val();
		/* if(day == '') {
			$("#useModel-days").val('------');
		}  */
		day = day.replace(/[^\d]/g,'');
		if(day > 999) day = 999;
		$(ths).val(day);
		
	},
	
	daysInputFocus:function(ths) {
		var val = $(ths).val();
		if(val == "------") {
			$(ths).val('');
		}
	},
		
	/**
	 * 用户操作码输入onkeyup事件
	 */
	codeInput:function(curr,startWith) {
		var next = curr + 1;
		var pre = curr - 1;
		//当输入退格时返回清空前一个 将光标移动到前一个
		var val = $("#"+startWith+"-codeInput-" + curr).val();
		if(val == null || val == '') {
			$("#"+startWith+"-codeInput-" + pre).focus();
			$("#"+startWith+"-codeInput-alert").addClass("hidden");
			return;
		}
		//判断是否为数字 不是数字清空输入框 显示提示
		var isNum = val.search(/[0-9]/) != -1;
		if(!isNum) {
			$("#"+startWith+"-codeInput-alert").removeClass("hidden");
			$("#"+startWith+"-codeInput-" + curr).val("");
			return ;
		}
		if(curr != 5) {
			$("#"+startWith+"-codeInput-" + next).focus();
		} else { //当最后一位密码输入完毕移除光标
			$("#"+startWith+"-codeInput-" + curr).blur();
		}
		$("#"+startWith+"-codeInput-alert").addClass("hidden");
	},	
	
	/**
	 * 获取停车时间
	 */
	getTimeInput:function(startWith) {
		var days = $("#"+startWith+"Model-days").val();
		if(days == "------") 
			days = 0
		var hours = $("#"+startWith+"Model-hours").val();
		var mins = $("#"+startWith+"Model-mins").val();
		
		var patt = /\d+/;
		
		if(patt.test(days) && patt.test(hours) && patt.test(mins)) {
			var sec = days * 24 * 3600 + hours * 3600 + mins * 60;
			return sec;
		}
		
		return -1;
	},
	
	/**
	 * 获取用户操作码
	 */
	getCodeInput:function(startWith) {
		var c0 = $("#"+startWith+"-codeInput-0").val();
		var c1 = $("#"+startWith+"-codeInput-1").val();
		var c2 = $("#"+startWith+"-codeInput-2").val();
		var c3 = $("#"+startWith+"-codeInput-3").val();
		var c4 = $("#"+startWith+"-codeInput-4").val();
		var c5 = $("#"+startWith+"-codeInput-5").val();
		
		var patt = /[0-9]/;
		if(
			patt.test(c0) && 
			patt.test(c1) &&	
			patt.test(c2) &&
			patt.test(c3) &&
			patt.test(c4) &&
			patt.test(c5)
		) {
			return c0 + c1 + c2 + c3 + c4 + c5;
		}
		return -1;
	},
	
	/**
	 * 清空input框
	 */
	clean:function(ths) {
		$(ths).val('');
	},
	
	cleanOpCode:function() {
		for(var i = 0 ; i < 6 ; i++) {
			$("#use-codeInput-" + i).val('');
			$("#reserve-codeInput-" + i).val('');
			$("#leave-codeInput-" + i).val('');
			$("#reserveConfirm-codeInput-" + i).val('');
		}
	}
};