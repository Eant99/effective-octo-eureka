/**
 * 带有全选、反选的公共方法
 * 对控件的id有要求，下级checkbox的id必须以上级checkbox的id开头
 * 例如：全选按钮是chk，能控制的按钮的id就必须是chk_开头（例如：chk_01），还可以继续往下分，chk_01可以控制所有以chk_01_开头的checkbox
 * 
 */
//选择、全部选择
//dom：点击的按钮，例：$(this)
//allid：全选按钮的id，例："chk"
//fxid：反选按钮的id，例："cqf"
//rqid：容器选择器，例："#d_chkbox"或者".rq"等，可以不传，这个参数是为了一个页面有多种选的，如果一个页面上只有一种，只需要传入前三个参数就好
var allChecked = function(dom,allid,fxid,rqid){
	if(rqid == undefined || rqid == null || rqid == "null"){
		rqid = "";
	}
	else{
		rqid = rqid + " ";
	}
	
	$("#" + fxid).prop("checked",false);
	var domid = dom.attr("id");
	$(rqid + ":checkbox[id^='" + domid + "_']").prop("checked",dom.prop("checked"));
	
	var domlist = domid.split("_");
	for(var i = domlist.length; i > 0; i--){
		var chkid = [];
		for(var j = 0; j < (i - 1); j++){
			chkid.push(domlist[j]);
		}
		var id = chkid.join("_");
		if($(rqid + ":checkbox[id^='" + id + "_']").filter(":checked").length == $(rqid + ":checkbox[id^='" + id + "_']").length){
			$("#" + id).prop("checked",true);
		}
		else{
			$("#" + id).prop("checked",false);
		}
	}
}

//反向选择
//allid：全选按钮的id，例："chk"
//fxid：反选按钮的id，例："cqf"
//rqid：容器选择器，例："#d_chkbox"或者".rq"等，可以不传，这个参数是为了一个页面有多种选的，如果一个页面上只有一种，只需要传入前三个参数就好
var fxChecked = function(allid,fxid,rqid){
	if(rqid == undefined || rqid == null || rqid == "null"){
		rqid = "";
	}
	else{
		rqid = rqid + " ";
	}
	
//	if($("#" + fxid).prop("checked")){
		$(rqid + ":checkbox:not('#" + fxid + "')").each(function(){
			$(this).prop("checked",!$(this).prop("checked"));
		});
//	}
	
	$(rqid + ":checkbox[id^='chk_']").each(function(){
		var id = $(this).attr("id");
		if($(rqid + ":checkbox[id^='" + id + "_']").length > 0){
			if($(rqid + ":checkbox[id^='" + id + "_']").filter(":checked").length == $(rqid + ":checkbox[id^='" + id + "_']").length){
				$("#" + id).prop("checked",true);
			}
			else{
				$("#" + id).prop("checked",false);
			}
		}
	});
	
	if($(rqid + ":checkbox:not('#" + allid + ",#" + fxid + "')").filter(":checked").length == $(rqid + ":checkbox:not('#" + allid + ",#" + fxid + "')").length){
		$("#" + allid).prop("checked",true);
	}
	else{
		$("#" + allid).prop("checked",false);
	}
}
//折旧查询的反向选择
//allid：全选按钮的id，例："chk"
//fxid：反选按钮的id，例："cqf"
//notid：需要去除的全选id和以此id开头的id 例：zt 和   zt_
//notqf：需要去除的取反按钮id  例：ztqf
//rqid：容器的id，可以不传，这个参数是为了一个页面有多种选的，如果一个页面上只有一种，只需要传入前三个参数就好
var fxCheck = function(allid,fxid,notid,notqf,rqid){
	if(rqid == undefined || rqid == null || rqid == "null"){
		rqid = "";
	}
	else{
		rqid = rqid + " ";
	}
	
//	if($("#" + fxid).prop("checked")){
		$(rqid + ":checkbox:not('#" + fxid + ",#" + notid + ",#" + notqf + "'):not([id^='" + notid + "_'])").each(function(){
			$(this).prop("checked",!$(this).prop("checked"));
		});
//	}
	
	$(rqid + ":checkbox[id^='chk_']").each(function(){
		var id = $(this).attr("id");
		if($(rqid + ":checkbox[id^='" + id + "_']").length > 0){
			if($(rqid + ":checkbox[id^='" + id + "_']").filter(":checked").length == $(rqid + ":checkbox[id^='" + id + "_']").length){
				$("#" + id).prop("checked",true);
			}
			else{
				$("#" + id).prop("checked",false);
			}
		}
	});
	
	if($(rqid + ":checkbox:not('#" + allid + ",#" + fxid + "')").filter(":checked").length == $(rqid + ":checkbox:not('#" + allid + ",#" + fxid + "')").length){
		$("#" + allid).prop("checked",true);
	}
	else{
		$("#" + allid).prop("checked",false);
	}
}

/**
 * 类似于列表上的单据状态这种标签的选中方法
 * 注意：如果是全选标签，请加上btn-mark-all样式
 * dom：当前点击的标签按钮，一般传入$(this)就好
 * clickdom：需要在改变完标签状态后，执行click事件的按钮，如果不需要执行click，第二个参数可以不传
 */
var markcheck = function(dom,clickdom){
	var sjdom = dom.parent();//为了防止一个页面上有多组这种标签
	if(dom.hasClass("btn-mark-all")){//点击的是全选标签
		if(dom.hasClass("active")){
			sjdom.find(".btn-mark").each(function(){
	   			$(this).removeClass("active");
			});
		} else {
			sjdom.find(".btn-mark").each(function(){
				$(this).addClass("active");
			});
		}
	}
	else{
		if(dom.hasClass("active")){
			dom.removeClass("active");
			sjdom.find(".btn-mark-all").removeClass("active");
		} else {
			var len = sjdom.find(".btn-mark:not(.btn-mark-all)").length;//代表单据状态的个数
			dom.addClass("active");
			//如果所有状态都选择了，则全部按钮也勾选上
			if(sjdom.find(".btn-mark:not(.btn-mark-all)").filter(".active").length == len){//len代表单据状态的个数
				if(!sjdom.find(".btn-mark-all").hasClass("active")){
					sjdom.find(".btn-mark-all").addClass("active");
				}
			}else{//有的状态勾选，有的状态没有勾选，则全部按钮不勾选
				sjdom.find(".btn-mark-all").removeClass("active");
			}
		}
	}
	if(clickdom && clickdom.length > 0){
		clickdom.click();
	}
};