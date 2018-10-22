//登录页面js
$(function(){
	window.console = window.console || (function () {  
	    var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile  
	    = c.clear = c.exception = c.trace = c.assert = function () { };  
	    return c;  
	})();
	//检测用户使用的浏览器
	getOs();
	//注意事项
	$("#notice").click(function(){
		layer.open({
			title:"注意事项",
		    type: 2,
		    shadeClose:true,
		    content: [path+"/login/getZysx","no"],
		    area: ["650px","330px"]
		});
	});
	//忘记密码找回
	$("#forgetPassword").click(function(){
		$("#d_yhdl").slideUp("normal",function(){
			$("#d_zhmm").slideDown("normal");
			//$("#username").popover('hide');
		});
	});
	//切换登录方式（扫描二维码或输入用户名和密码）
	$("#d_ewmqh").click(function(){
		$("#d_yhdl").slideUp("normal",function(){
			$("#d_smdl").slideDown("normal");
//			$("#username").popover('hide');
		});
	});
	//切换登录方式（扫描二维码或输入用户名和密码）
	$("#pc").click(function(){
		$("#d_smdl").slideUp("normal",function(){
			$("#d_yhdl").slideDown("normal");
		});
	});
	//切换登录方式（密码找回或输入用户名和密码）
	$("#zhmm_back").click(function(){
		$("#d_zhmm").slideUp("normal",function(){
			$("#d_yhdl").slideDown("normal");
		});
	});
//	//返回
//	$("#back").click(function(){
//		$(".forgetBox").slideUp("normal",function(){
//			$(".rightBox").slideDown("normal");
//		});
//	});
	//记住密码
	$("#remberPwd").click(function(){
		if($(this).prop("checked")){
			if($("#username").val()!=''&&$("#password").val()!=''){
				document.cookie="username="+$("#username").val()+";password="+$("#password").val();
			}
		}
	});
	//加载验证码
	changeCode();
	//切换验证码
	$("#codeImg").click(function(){
		changeCode();
	});
	//回车提交事件
	$(document).keydown(function(e){
		if(e.keyCode == 13){
			$('#submit').click();
		}
	});
	//用户名输入框的鼠标移除事件
	$("#username").blur(function(){
		var username = $.trim($(this).val());
		var dlfs = $("#dlfs").val();
		if(username!=""&&username!="undefined"){
			$.ajax({
				url:path+"/login/checkUsername",
				data:"username="+username+"&method="+dlfs,
				type:"post",
				cache:false,
				success:function(val){
					var json = JSON.getJson(val);
					if(json.code=='1'){
						$("#username").attr("data-content",json.msg);
						$("#username").popover('show');
					}else{
						layer.msg(json.msg+"",{icon:2});
						$("#username").val("");
						$("#password").val("");
						$("#username").popover('hide');
					}
				}
			});
		}else{
			$("#username").popover('hide');
		}
	});
	//登录提交事件
	$("#submit").click(function(){
		if(checkSubmit()){//登录提交前验证
			var username = $.trim($("#username").val());
			var password = $.trim($("#password").val());
			var verifycode = $.trim($("#verifycode").val());
			var dlfs = $.trim($("#dlfs").val());
			var code = username+","+password+","+verifycode;
			var rememberme=false;//是否记住用户名
			if($('#chk_rememberMe').is(':checked')) {
				rememberme=true;
			 }
			$.ajax({
				type:"post",
				url:path+"/login/checkLogin",
				data:{code:code,dlfs:dlfs,rememberme:rememberme},
				dataType:"json",
				success:function(val){
					//0验证码为空1登录成功2验证码错误3用户名或密码错误4缺少参数5系统异常
					if("1" == val.code){
						window.location.href = path+"/index/login_toIndex";
					}else if("3" == val.code){
						layer.msg(val.msg,{icon:2});
						$("#username").focus();
					}else if("2" == val.code||"0"==val){
						layer.msg(val.msg,{icon:2});
						$("#verifycode").focus();
					}else if("7" == val.code){
						layer.msg(val.msg,{icon:2});
						closeWebPage();
					}else if(val.code = "6"){
						layer.confirm("正在使用默认密码登录，请先修改密码！",{icon:3,title:'修改密码'},function(index){
							layer.close(index);
							layer.open({
							 	title:"修改默认密码",
							    type: 2,
							    shadeClose:false,
							    content: [path+"/login/goUpdPwdPage?rybh="+val.msg,"no"],
							    area: ["600px","400px"]
							});
						});
					}else{
						layer.msg(val.msg,{icon:2});
						return false;
					}
				}
			});			
		}
	});
	//获取修改密码验证码
	$("#btn_hqyzm").click(function(){
		var txt_mobile=$("#txt_mobile").val();
		if(txt_mobile==""){
		  	layer.msg("请输入手机号！",{icon:2});
		}else{
			$.ajax({
				cache:false,  
				url:path+"/login/doYzm",  
				data:{phone:txt_mobile}
			});	
			updateTime(60);
		}
	});
});
//登录提交前客户端验证
function checkSubmit(){
	if ($("#username").val() == "") {
		layer.msg("用户名不能为空",{icon:2});
		$("#username").focus();
		return false;
	}
	if ($("#password").val() == "") {
		layer.msg("密码不能为空！",{icon:2});
		$("#password").focus();
		return false;
	}
	if($("#verifycode").val()==""){
		layer.msg("验证码不能为空！",{icon:2});
		$("#verifycode").focus();
		return false;
	}
	return true;
}
//找回密码验证按钮被点击
$("#yz").click(function(){
	if(checkYz()){
		var rygh = $.trim($("#rygh").val());
		var mmda = $.trim($("#mmda").val());
		var mmwt = $.trim($("#mmwt").val());
		$.ajax({
			type:"post",
			url:path+"/login/checkMmzh",
			data:"rygh="+rygh+"&mmwt="+mmwt+"&mmda="+mmda,
			success:function(val){
				var json = JSON.getJson(val);
				if(json.success=='true'){
					layer.msg(json.msg,{icon:2});
					$("#xmm").removeAttr("readonly");
					$("#qrmm").removeAttr("readonly");
					$("#xmm").focus();
				}else{
					layer.msg(json.msg,{icon:2});
					$("#xmm").val("");
					$("#qrmm").val("");
					$("#xmm").attr({ readonly: 'true' });
					$("#qrmm").attr({ readonly: 'true' });
					return false;
				}
			}
		});			
	}
});
//找回密码验证
function checkYz(){
	if ($("#rygh").val() == "") {
		layer.msg("人员工号不能为空！",{icon:2});
		$("#rygh").focus();
		return false;
	}
	if ($("#mmwt").val() == "") {
		layer.msg("答案不能为空！",{icon:2});
		$("#mmwt").focus();
		return false;
	}
	if ($("#mmda").val() == "") {
		layer.msg("答案不能为空！",{icon:2});
		$("#mmda").focus();
		return false;
	}
	return true;
}
//找回密码用户名输入框的鼠标移除事件
$("#rygh").blur(function(){
	var username = $.trim($(this).val());
	var dlfs = $("#dlfs").val();
	if(username!=""&&username!="undefined"){
		$.ajax({
			url:path+"/login/checkUsername",
			data:"username="+username+"&method="+dlfs,
			type:"post",
			cache:false,
			success:function(val){
				var json = JSON.getJson(val);
				if(json.code=='1'){
					$("#rygh").attr("data-content","");
					$("#rygh").popover('show');
				}else{
					layer.msg("人员工号不存在！",{icon:2});
					$("#rygh").val("");
					$("#rygh").popover('hide');
				}
			}
		});
	}else{
		$("#rygh").popover('hide');
	}
});
//找回密码保存验证
function checkSave(){
	if ($("#rygh").val() == "") {
		layer.msg("人员工号不能为空！",{icon:2});
		$("#rygh").focus();
		return false;
	}
	if ($.trim($("#mmda").val()) == "") {
		layer.msg("答案不能为空！",{icon:2});
		$("#mmda").focus();
		return false;
	}
	if ($.trim($("#mmwt").val()) == "") {
		layer.msg("密保问题不能为空！",{icon:2});
		$("#mmwt").focus();
		return false;
	}
	if ($.trim($("#xmm").val()) == "") {
		layer.msg("新密码不能为空！",{icon:2});
		$("#xmm").focus();
		return false;
	}else if($.trim($("#xmm").val()).length<6 ||$.trim($("#xmm").val()).length>16 ){
		layer.msg("新密码长度在6到16之间！",{icon:2});
		$("#xmm").val("");
		$("#xmm").focus();
		return false;
	}
	if ($.trim($("#qrmm").val()) == "") {
		layer.msg("确认密码不能为空！",{icon:2});
		$("#qrmm").focus();
		return false;
	}else if($.trim($("#qrmm").val()).length<6 ||$.trim($("#qrmm").val()).length>16 ){
		layer.msg("确认密码长度在6到16之间！",{icon:2});
		$("#qrmm").val("");
		$("#qrmm").focus();
		return false;
	}
	if($.trim($("#qrmm").val())!=$.trim($("#xmm").val())){
		layer.msg("两次输入的密码不一致！",{icon:2});
		$("#xmm").val("");
		$("#qrmm").val("");
		return false;
	}
	return true;
}
//找回密码【保存】按钮被点击
$("#zhmm_save").click(function(){
	if(checkSave()){
		var rygh = $.trim($("#rygh").val());
		var xmm = $.trim($("#xmm").val());
		$.ajax({
			type:"post",
			url:path+"/index/doUpdPwdByZhmm",
			data:"rygh="+rygh+"&xmm="+xmm,
			success:function(val){
				var json = JSON.getJson(val);
				if(json.success=='true'){
					layer.msg(json.msg,{icon:2});
					$("#xmm").val("");
					$("#qrmm").val("");
					$("#rygh").val("");
					$("#mmda").val("");
					$("#zhmm_back").click();
				}else{
					layer.msg(json.msg,{icon:2});
					$("#xmm").val("");
					$("#qrmm").val("");
					$("#xmm").attr({ readonly: 'true' });
					$("#qrmm").attr({ readonly: 'true' });
					return false;
				}
			}
		});			
	}
});

/* -------验证码处理开始------ */
function updateTime(i){
	setInterval(function(){
		var obj = document.getElementById("btn_hqyzm");  
        if(i > 0){
	        i--;
            obj.innerHTML  = "<span style='color:red'>" +i+ "</span>秒后获取";  
            obj.disabled = true;  
        }else{  
            obj.innerHTML = "获取验证码";  
            obj.disabled = false; 
            clearInterval();
        } 
	},1000);
}  
/* -------验证码处理结束------ */	
//验证码更新方法
function changeCode() {  
	$("#codeImg").attr("src", path+"/login/getVerifyCode?time=" + genTimestamp());
}
//保存密码前客户端验证
function checkReSubmit(){
	if ($("#wjmmusername").val() == "") {
		layer.msg("用户名不能为空",{icon:2});
		$("#wjmmusername").focus();
		return false;
	}
	if ($("#wjmmpassword").val() == "") {
		layer.msg("密码不能为空！",{icon:2});
		$("#wjmmpassword").focus();
		return false;
	}
	if($("#wjmmverifycode").val()==""){
		layer.msg("验证码不能为空！",{icon:2});
		$("#wjmmverifycode").focus();
		return false;
	}
	if ($("#txt_mobile").val() == "") {
		layer.msg("手机号不能为空！",{icon:2});
		$("#txt_mobile").focus();
		return false;
	}
	if($("#txt_yzm").val()==""){
		layer.msg("短信验证码不能为空！",{icon:2});
		$("#txt_yzm").focus();
		return false;
	}
	return true;
}
//生成日期时间戳
function genTimestamp() {
	var time = new Date();
	return time.getTime();
}
function getOs(){
	var explorer = window.navigator.userAgent.toLowerCase() ;
	 //ie 
	 if (explorer.indexOf("msie") >= 0) {
	    var ver=explorer.match(/msie ([\d.]+)/)[1];
	    //低于ie8 给予提示
	    if(parseFloat(ver)<=8)
	    location.href= path+"/browser.jsp?type=msie&version="+ver;
	 }
	 return false;
}
function closeWebPage(){  
		var userAgent = navigator.userAgent;
		/* if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1||userAgent.indexOf("IE") !=-1) {
			window.location.replace("about:blank");
			event.returnValue=false;
		} else {
		   window.opener = null;
		   window.open("", "_self");
		   window.close();
		} */
		window.location.replace("about:blank");
	event.returnValue=false;
}  