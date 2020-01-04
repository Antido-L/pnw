var SSO = {
	param:{
		url:"localhost:8089",
		cookieName:"PNW_SESSION"
	},
	
	checkLogin:function() {
		console.log("checkLogin");
		$("#token-userId").val(1);
	}
}