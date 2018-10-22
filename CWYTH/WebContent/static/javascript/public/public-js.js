/**************************************************  ***************************************************/
//获取顶级页面，为了跟其他的系统进行整合时防止出错
function getTopFrame(){
	var parentName='login/login_toIndex';
	var names = this.name;
	var tops=this;
	while(tops.parent!=tops){//顶级的parent和自身相同
		if (tops.location.pathname.indexOf(parentName)>0){
			break;
		}else{
			tops=tops.parent;
			names +=","+tops.name
		}
	}
	return tops;
}
/**
 * 参数转义
 * @param name
 * @returns
 */
function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)
    	 return unescape(r[2]); 
     return null;
}

/*
把字符串解析为JSON
*/
JSON.getJson = function(val)
{
    if(val != ""){
    	val = val.replace(/[\r]/g,"").replace(/[\n]/g,"");
    }
    return (new Function("return " + val))() || {};
};

/*浏览器右键菜单屏蔽*/
//document.oncontextmenu=function(){
//    return false;
//};


$(document).ready(function (){
		ADDRESS="http://localhost/apis";//访问ESB路径
	window.console = window.console || (function () {  
	    var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile  
	    = c.clear = c.exception = c.trace = c.assert = function () { };  
	    return c;  
	})();
	/************************************************** box右边操作按钮(删除和折叠) 开始***************************************************/
    $(".box .box-remove").click(function (e) {
        $(this).parents(".box").first().remove();
        return e.preventDefault();
    });
    $(".box .box-collapse").click(function (e) {
        var box, panel;
        box = $(this).parents(".box");
        if(box.length > 1){
        	box = $(box[0]);
        }
        if($(this).parents(".box-panel").length > 0){
        	box.addClass("box-collapsed");
            panel = $(this).parents(".box-panel");
            if(panel.length > 1){
            	panel = $(panel[0]);
            }
            panel.toggleClass("box-collapsed");
        }else{
        	box.toggleClass("box-collapsed");
        }
        return e.preventDefault();
    });
    $(".box .box-fold").click(function(){
    	var $this = $(this);
    	var box = $this.parents(".box");
    	if(box.find(".box-content:hidden").length > 0) {
    		box.css("padding-bottom", "6px").find(".box-content:hidden").slideDown("fast");
			$this.find(".faw").removeClass("fa-angle-down").addClass("fa-angle-up");
		} else {
			box.css("padding-bottom", "0").find(".box-content:visible").slideUp("fast");
    		$this.find(".faw").removeClass("fa-angle-up").addClass("fa-angle-down");
		}
    });
    /************************************************** box右边操作按钮(删除和折叠) 结束***************************************************/

    /************************************************** 右侧三个辅助按钮(返回，刷新，回到顶部) 开始***************************************************/
	//页面用到这三个按钮功能单独写，用不到就不写<div class=\"go-back\"></div> <a title=\"返回上一页\" href=\"javascript:history.back();\" target=\"_self\"><i class=\"fa icon-back\" style=\"line-height:28px;color:#696969;\"></i></a>
    $("body").append("<div class=\"tool-fix\"><div class=\"refresh\"><a title=\"刷新页面\" href=\"javascript:void(0);\" target=\"_self\" class=\"reload\"><i class=\"fa icon-reset\" style=\"line-height:28px;color:#696969;\"></i></a></div><div id=\"toTop\" class=\"go-top\"><a title=\"返回页面顶部\" href=\"javascript:;\" target=\"_self\"><i class=\"fa icon-xiangshangzhedie\" style=\"line-height:28px;color:#696969;\"></i></a></div></div>");
    $(".reload").click(function() {
    	var url = window.location.href;
    	if(url.indexOf("?") > 0) {
    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016";
    	} else {
    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016";
    	}
    });
    jQuery.fn.gotoTop = function (bottom, scrollHeight) {
        var bindid = $(this);
        scrollHeight = scrollHeight || 100;
        bottom = bottom || 80;
        $("#toTop").css("visibility", "hidden");
        $(window).bind("scroll", function () {
            var b = $(document).scrollTop();
            if (b > scrollHeight) {
                bindid.css({ "bottom": bottom });
                $("#toTop").css("visibility", "visible");
            } else {
                $("#toTop").css("visibility", "hidden");
            }
        });
        $("#toTop").bind('click', function (event) {
            $("html,body").animate({ scrollTop: 0 }, 200);
        });
    };
    $("#toTop").gotoTop(60, 100);
    /************************************************** 右侧三个辅助按钮(返回，刷新，回到顶部) 结束***************************************************/
});

/************************************************** 验证   开始***************************************************/
$(document).ready(function (){
	//日期控件
    $(document).on("focus", ".year", function(){
        $(this).on("click", function() {
        	WdatePicker({dateFmt:'yyyy'});
        });
        $(this).on("keypress", function() {
            if (/[^0-9]/.test(String.fromCharCode(event.keyCode)))
                event.keyCode = 0;
        });
        $(this).on("dragenter", function() {
            return false;
        });
        $(this).on("paste", function() {
            return false;
        });
    });
    $(document).on("focus", ".newdate", function(){
        $(this).on("click", function() {
        	WdatePicker({dateFmt:$(this).data("format")});
        });
        $(this).on("keypress", function() {
            if (/[^0-9-]/.test(String.fromCharCode(event.keyCode)))
                event.keyCode = 0;
        });
        $(this).on("dragenter", function() {
            return false;
        });
        $(this).on("paste", function() {
            return false;
        });
    });
    $(document).on("focus",".nydate",function(){
        $(this).on("click", function() {
        	WdatePicker({dateFmt:'yyyy-MM'});
        });
        $(this).on("keypress", function() {
            if (/[^0-9-]/.test(String.fromCharCode(event.keyCode)))
                event.keyCode = 0;
        });
        $(this).on("dragenter", function() {
            return false;
        });
        $(this).on("paste", function() {
            return false;
        });
    });
    $(document).on("focus",".nydate2",function(){
        $(this).on("click", function() {
        	WdatePicker({dateFmt:'yyyy.MM'});
        });
        $(this).on("keypress", function() {
        	 if (/[^0-9-]/.test(String.fromCharCode(event.keyCode)))
                event.keyCode = 0;
        });
        $(this).on("dragenter", function() {
            return false;
        });
        $(this).on("paste", function() {
            return false;
        });
    });
    $(document).on("focus",".xzpznydate",function(){ //薪资凭证
        $(this).on("click", function() {
        	WdatePicker({dateFmt:'yyyy.MM'});
        });
        $(this).on("keypress", function(e) {
        	if(event.keyCode == "13" || event.keyCode == "108"){
        		//var dateval = $(document).find(".WdateDiv").find(".WdayTable").find(".menuOn").length;
        		//$(this).val(dateval);
        		return false;
        	}else if (/[^0-9-]/.test(String.fromCharCode(event.keyCode))){
                event.keyCode = 0;
        	 }	 
        });
        $(this).on("dragenter", function() {
            return false;
        });
        $(this).on("paste", function() {
            return false;
        });
    });
    $(document).on("focus", ".datetime", function(){
        $(this).on("click", function() {
        	WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});
        });
        $(this).on("keypress", function() {
            if (/[^0-9-]/.test(String.fromCharCode(event.keyCode)))
                event.keyCode = 0
        });
        $(this).on("dragenter", function() {
            return false;
        });
        $(this).on("paste", function() {
            return false;
        });
    });
    $(document).on("focus", ".times", function(){
        $(this).on("click", function() {
        	WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
        });
        $(this).on("keypress", function() {
            if (/[^0-9-]/.test(String.fromCharCode(event.keyCode)))
                event.keyCode = 0
        });
        $(this).on("dragenter", function() {
            return false;
        });
        $(this).on("paste", function() {
            return false;
        });
    });
    $(document).on("focus", ".date", function(){
    	$(this).on("click", function() {
            WdatePicker();
        });
        $(this).on("keypress", function() {
            if (/[^0-9-]/.test(String.fromCharCode(event.keyCode)))
                event.keyCode = 0
        });
        $(this).on("dragenter", function() {
            return false;
        });
        $(this).on("paste", function() {
            return false;
        });
    });
	
	//实现带搜索的select
	var $select2 = $(".select2");
	if($select2.length > 0){
		$(".select2").select2({language: "zh-CN"});
	}
    
    //保留两位小数,无正负
	$(document).on("keyup", ".number", function(e){
		$(this).Num(2,false,false,e);
		return false;
	});
    $(document).on("blur", ".number", function(e){
    	$(this).Num(2,true,false,e);
    	return false;
    });
    //保留两位小数,有正负
    $(document).on("keyup", ".sign-number", function(e){
    	$(this).Num(2,false,true,e);
    	return false;
    });
    $(document).on("blur", ".sign-number", function(e){
    	$(this).Num(2,true,true,e);
    	return false;
    });
    //保留四位小数
    $(document).on("keyup", ".number1", function(e){
    	$(this).Num(4,false,false,e);
    	return false;
    });
    $(document).on("blur", ".number1", function(e){
    	$(this).Num(4,true,false,e);
    	return false;
    });
    //
    $(document).on("keyup", ".int", function(e){
    	$(this).Num(0,false,false,e);
    	return false;
    });
    $(document).on("blur", ".int", function(e){
    	$(this).Num(0,true,false,e);
    	return false;
    });
    $(document).on("keyup", ".allNumber", function(e){
    	$(this).Num(2,false,true,e);
    	return false;
    });
    $(document).on("blur", ".allNumber", function(e){
    	$(this).Num(2,true,true,e);
    	return false;
    });
    $(document).on("keyup", ".allInt", function(e){
    	$(this).Num(0,false,true,e);
    	return false;
    });
    $(document).on("blur", ".allInt", function(e){
    	$(this).Num(0,true,true,e);
    	return false;
    });
    
  //列表页面全选一行事件（左上角的checkbox的class属性必须为select-all）
	$(document).on("click", ".select-all", function(){
		if($(this).prop("checked")){
			$(":checkbox.keyId").prop("checked", true);
			$(":checkbox.keyId").parents("tr").addClass('selected');
		}else{
			$(":checkbox.keyId").prop("checked", false);
			$(":checkbox.keyId").parents("tr").removeClass('selected');
		}
		//类似于资产闲置申请中选择资产的时候，右下角有个蓝色小圆圈，选中列表上的复选框的时候，要把该资产放到蓝色小圆圈里，
		//所以需要执行一下复选框的change事件，这个事件在页面上，如果没有change事件，写在这儿也不受影响
		$(":checkbox.keyId").change();
	});
	//datatables 中鼠标悬停行变色
	$(document).on('mouseover', 'table tr:not(".detailTr")', function(e){
		var checked = $(this).find(".keyId").prop("checked");
		if(!checked){
	    	$(this).addClass("selected");
		}
		e.stopPropagation();
	});
	//datatables 中鼠标离开行恢复原色
	$(document).on('mouseout', 'table tr:not(".detailTr")', function(e){
		var checked = $(this).find(".keyId").prop("checked");
		if(!checked){
	    	$(this).removeClass("selected");
		}
		e.stopPropagation();
	});
	
	//datatables 任意行点击选中
	$(document).on('click', 'table tr', function(){
		var checked = $(this).find(".keyId").prop("checked");
		$(this).find(".keyId").prop("checked", !checked);
		if(!checked){
			$(this).addClass("selected");
		}else{
			$(this).removeClass("selected");
		}
		//类似于资产闲置申请中选择资产的时候，右下角有个蓝色小圆圈，选中列表上的复选框的时候，要把该资产放到蓝色小圆圈里，
		//所以需要执行一下复选框的change事件，这个事件在页面上，如果没有change事件，写在这儿也不受影响
		$(this).find(".keyId").change();
	});
	//datatables 最左侧checkbox选中的默认事件移除
	$(document).on('click','.keyId',function(e){
		return e.stopPropagation();
	});
	
	//datatables  每页记录数事件----防止输入非数字
	$(document).on("keyup", ".dataTables_length input", function(){
		var values = $(this).val();
		var reg = /^[1-9]*[1-9][0-9]*$/;
		if(reg.test(values)){
			 return true;
		}else{  
	        alert("请输入正整数！");  
	        $(this).val("10");
	        return false;  
	    } 
		if($(this).val() == ""){
			$(this).val("10");
		}
		else if(parseInt($(this).val(),10) < 1){
			$(this).val("10");
		}
	});
	//datatables 每页记录数事件----防止输入非数字
	$(document).on("keydown", ".dataTables_length input", function(e){
		var values = $(this).val();
		var re = /^[1-9]*[1-9][0-9]*$/;
		if(re.test(values)){  
	        return true;
		}else{  
	        alert("请输入正整数！");  
	        $(this).val("10");
	        return false;  
	    } 
	});
});
/************************************************** 验证   结束***************************************************/
$("#btn_shlc").click(function(){
	select_commonWin($(this).attr("url"), "审核信息", "700", "450");
});
/************************************************** 综合查询   开始***************************************************/

$(document).ready(function (){ 
	//适应一个页面有多个列表，注意：每个列表的查询按钮必须以btn_search开头，查询外边的div必须以searchBox开头，并且后边要跟查询按钮后边保持一致，table的id要以mydatatables开头，并且后边要跟button的id后边一致	//例：查询按钮的id：btn_search_xxll，则查询外边div的id需要写成searchBox_xxll，table的id需要写成mydatatables_xxll
    //页面只有一个列表的写法跟以前没有任何变化，不受影响
	if($("button[id^='btn_search']").length > 0){
	    $("button[id^='btn_search']").on("click", function(){
			var id = $(this).attr("id").replace("btn_search", ""); 
	   		var json = searchJson("searchBox" + id);
	    	$('#mydatatables'+id).DataTable().search(json, "json").draw();
	    	//点击查询后，自动隐藏高级查询区域
	    	if($("#btn_search_more").length > 0){
		    	$("#btn_search_more").removeClass("btn-search-more");
		   		$(".search-more").css("display", "none");
	    	}
	    });
	}
	//综合查询--高级查询按钮显示隐藏事件
	if($("#btn_search_gjcx").length > 0){
	   	$("#btn_search_gjcx").click(function(){
	   		if($(".search-more").css("display") == "none"){
	   			$("#btn_search_more").addClass("btn-search-more");
	   	   		$(".search-more").css("display", "block");
			} else if($(".search-more").css("display") == "block") {
	   			$("#btn_search_more").removeClass("btn-search-more");
	   	   		$(".search-more").css("display", "none");
			}
	   	});
	   	//高级查询按钮
	   	$("#btn_search_gjcx").mouseover(function(){
	   		$(this).css("cursor", "pointer");
	   	});
	}
   	//综合查询--取消按钮（取消的同时清除了查询条件，数据回到原始数据）
	if($("button[id^='btn_cancel']:not(#btn_cancelw)").length > 0){
	   	$("button[id^='btn_cancel']:not(#btn_cancelw)").click(function(){
	   		if($("#btn_search_more").length > 0){
		   		$("#btn_search_more").removeClass("btn-search-more");
		   		$(".search-more").css("display", "none");
	   		}
	   		var id = $(this).attr("id").replace("btn_cancel","");
	   		$("#myform" + id).find("input:not(.except)").val("");
	   		$("#myform" + id).find("select:not(.except)").val("");
	   		var json = searchJson("searchBox" + id);
	    	$('#mydatatables'+id).DataTable().search(json, "json").draw();
	   	});
	}
	//综合查询--只有一个查询框的时候
	if($("#zhcx").length > 0){
		$("#zhcx").blur(function(){
			$('#mydatatables').DataTable().search($(this).val(), "json").draw();
		});
	}
});
//综合查询拼装json数据方法
function searchJson(id, pname){
	var input;
	if(noNull(pname)){
		input = getIframWindow(pname).$("#" + id).find("input:not(.except)");
	}
	else{
		input = $("#" + id).find("input:not(.except)");
	}
	var select;
	if(noNull(pname)){
		select = getIframWindow(pname).$("#" + id).find("select:not(.except)");
	}
	else{
		select = $("#" + id).find("select:not(.except)");
	}
	var checkbox;
	if(noNull(pname)){
		checkbox = getIframWindow(pname).$("#" + id).find(".checkbox:not(.except)");
	}
	else{
		checkbox = $("#" + id).find(".checkbox:not(.except)");
	}
	
	var data = [];
	input.each(function(){
		var name = $(this).attr("name");
		if(name != undefined){
			var type = ($(this).attr("types") == undefined) ? "" : $(this).attr("types");
			var value = $(this).val();
			var table = ($(this).attr("table") == undefined) ? "" : $(this).attr("table");
			if(value != "" || type == "E"){
				data.push("{\"name\":\""+name+"\",\"value\":\""+value+"\",\"type\":\""+type+"\",\"table\":\""+table+"\"}");
			}
		}
	});
	select.each(function(){
		var name = $(this).attr("name");
		if(name != undefined){
			var type = ($(this).attr("types") == undefined) ? "" : $(this).attr("types");
			var value = $(this).val();
			var table = ($(this).attr("table") == undefined) ? "" : $(this).attr("table");
			if(value != ""){
				console.log(name+"---");
				data.push("{\"name\":\""+name+"\",\"value\":\""+value+"\",\"type\":\""+type+"\",\"table\":\""+table+"\"}");
			}
		}
	});
	checkbox.each(function(){
		var name = $(this).attr("name");
		if(name != undefined){
			var type = ($(this).attr("types") == undefined) ? "" : $(this).attr("types");
			var table = ($(this).attr("table") == undefined) ? "" : $(this).attr("table");
			var active = $(this).find(".active:not(.btn-mark-all)");
			var value = [];
			active.each(function(){
				value.push($(this).attr("data-value"));
			});
			if(value.length > 0 || type == "E"){
				data.push("{\"name\":\""+name+"\",\"value\":\""+value.join(",")+"\",\"type\":\""+type+"\",\"table\":\""+table+"\"}");
			}
		}
	});
	return "[" + data.join(",") + "]";
}
/************************************************** 综合查询 结束***************************************************/

/************************************************** 消息提示框（layer）  开始***************************************************/
//alert confirm open 方法的进一步封装
function alert(content, flag, options, yes){
	var _class = "",_color=""; 
	switch(flag)
	{
		case "W"://警告框
			_class="jinggao";
			_color="#8a6d3b"; 
			break;
		case "E"://错误框
			_class="cuowu";
			_color="#a94442"; 
			break;
		case "C"://正确框
			_class="queren";
			_color="#3c763d"; 
			break;
		default://信息框（默认）
			_class="i";
			_color="#00acec"; 
	}
	var index = getTopFrame().layer.alert("<i class=\"fa icon-" + _class + " \" style=\"padding-right:5px;font-size:20px;color:" + _color + ";\"></i>" + content, options, yes);
	return index;
}
function confirm(content, flag, options, yes, cancel){
	var _class = "",_color=""; 
	switch(flag)
	{
		case "W"://警告框
			_class="jinggao";
			_color="#8a6d3b"; 
			break;
		case "E"://错误框
			_class="cuowu";
			_color="#a94442"; 
			break;
		case "C"://正确框
			_class="queren";
			_color="#3c763d"; 
			break;
		default://信息框（默认）
			_class="i";
			_color="#00acec"; 
	}
	var index = getTopFrame().layer.confirm("<i class=\"fa icon-" + _class +" \" style=\"padding-right:5px;font-size:20px;color:" + _color + ";\"></i>" + content, options, yes, cancel);
	return index;
}
//普通弹窗页面
function setOverHeight(){
	$("body").css("overflow","hidden");  
	var heights = $(window).outerHeight() - $(".page-bottom.clearfix").outerHeight();
    $(".over-hight").height(heights);
};
function msg(content, flag, options, end){
	var _class = "",_color=""; 
	switch(flag)
	{
		case "W"://警告框
			_class="jinggao";
			_color="#8a6d3b"; 
			break;
		case "E"://错误框
			_class="cuowu";
			_color="#a94442"; 
			break;
		case "C"://正确框
			_class="queren";
			_color="#3c763d"; 
			break;
		default://信息框（默认）
			_class="i";
			_color="#00acec"; 
	}
	var index = getTopFrame().layer.msg("<i class=\"fa icon-" + _class +" \" style=\"padding-right:10px;font-size:20px;color:" + _color + ";\"></i>" + content, options, end);
	return index;
}
function loading(icon, options){
	var index = getTopFrame().layer.load(icon, options);
	return index;
}
function close(index){
	var index = getTopFrame().layer.close(index);
	return index;
}
function closeAll(type){
	var index = getTopFrame().layer.closeAll(type);
	return index;
}
/************************************************** 消息提示设置  开始***************************************************/

/************************************************** 操作按钮通用设置  开始***************************************************/
/**
 * 增加、修改、查看方法（这是标准页面下的通用方法，特殊页面不要采用此方法）
 * C增加  U修改   L查看
 */
function doOperate(_url, flag){
	if(flag != "" && flag != undefined){
		if(_url.indexOf("?") > 0){
			window.location.href = _url + "&operateType=" + flag;
		} else {
			window.location.href = _url + "?operateType=" + flag;
		}
	} else {
		window.location.href = _url;
	}
}
/**
 * _validate 验证表单变量validate
 * _formId  表单编号
 */
function doValidatorForm(_validate, _formId){
	var valid = true;
	if(_validate){
		_validate.bootstrapValidator("validate");
		//如果验证成功返回true
		valid = $('#' + _formId).data('bootstrapValidator').isValid();
	} else {
		valid = true;
	}
	return valid;
}
//2017-11-10业务说明是否显示
function getRootPath_dc() {
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    if (webName == "") {
        return window.location.protocol + '//' + window.location.host;
    }
    else {
        return window.location.protocol + '//' + window.location.host + '/' + webName;
    }
}
function operateYwsm(_rootPath){
	var mkbh = GetQueryString("mkbh");
	if(_rootPath==""||_rootPath==null){
		_rootPath = getRootPath_dc();
	}
	var	_url = _rootPath+"/ywsm/findSfts?mkbh="+mkbh;
	$.ajax({
			url:_url,
			dataType:"json",
			type:"post",
			success:function(val){
				if(val.sfts!="1"){
					zysx();//页面上必须有这个方法
				}
			},
			error:function(){
				alert(getPubErrorMsg());
			}
		});
}
/**
 * 带业务说明的模块需采用的此方法
 * @param _url
 * @param flag
 * @param _rootPath
 */
function doOperate_ywsm(_url, _rootPath){
	var mkbh = GetQueryString("mkbh");
	$.ajax({
		url:_rootPath + "/ywsm/findSfts?mkbh=" + mkbh,
		dataType:"json",
		type:"post",
		success:function(val){
			if(val.sfts == 1){
   				window.location.href = _url;
			} else {
				//如果当前这个模块根本没有设置业务说明，就不要弹出对话框了，直接跳转到相应页面
				$.ajax({
					url:_rootPath + "/ywsm/getYwsmByMkbh?mkbh=" + mkbh,
					dataType:"json",
					type:"post",
					success:function(val){
						if(val.success){
							var par = getParent();
							_url = _rootPath + "/ywsm/getYwsmWin?pname=" + String(par["name"]) + "&type=C&mkbh=" + mkbh + "&url=" + _url.replace("&","\@");
							getTopFrame().layer.open({
							 	title:"",
							    type: 2,
							    shadeClose:true,
							    content: [_url, "yes"],
							    area: ["960px", "630px"]
							});
						} else {
							window.location.href = _url;
						}
					}
				});
			}
		},
		error:function(){
			alert(getPubErrorMsg());
		}
	});
}

/**
 * 删除数据（包括批量删除） 返回json格式：{"success":"true","msg":"操作成功"}
 * @param _data     数据
 * @param _url      请求路径
 * @param _success  成功后回调函数
 * @param _fail     失败后回调函数
 * @param _length   T:批量删除   F：非批量删除
 * @param xx		提示消息，没有特殊提示的，这个不用传
 */
function doDel(_data,_url,_success,_fail,_length,xx){
	var index;
	if(xx == undefined || xx == ""){
		xx = "确认要删除这条信息？";
		if(_length!="1"&&_length!="0"){
			xx = "确认要删除这"+_length+"条信息？";
		}
	}
	confirm(xx,{title:"提示"},function(){
		 $.ajax({
			type:"post",
			data:_data,
			url:_url,
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				if(data.success){
					alert(data.msg);
					if(_success != "" && _success != undefined && _success != null){
						_success(data);
					}
				} else {
					alert(data.msg);
					if(_fail != "" && _fail != "" && _fail != ""){
						_fail(data);
					}
				}
			},
			error:function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(){
				index = loading(2);
			}
		});
	}); 
}
function doSures(_data,_url,_success,_fail,_length,xx){
	var index;
	if(xx == undefined || xx == ""){
		xx = "确认要复核这条信息？";
		if(_length!="1"&&_length!="0"){
			xx = "确认要复核这"+_length+"条信息？";
		}
	}
	confirm(xx,{title:"提示"},function(){
		 $.ajax({
			type:"post",
			data:_data,
			url:_url,
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				if(data.success){
					alert(data.msg);
					if(_success != "" && _success != undefined && _success != null){
						_success(data);
					}
				} else {
					alert(data.msg);
					if(_fail != "" && _fail != "" && _fail != ""){
						_fail(data);
					}
				}
			},
			error:function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(){
				index = loading(2);
			}
		});
	}); 
}
function doDeal(_url,_data,_success,flag,_fail){
	var asygn = flag==null?true:flag;
	var index;
	$.ajax({
		type:"post",
		data:_data,
		url:_url,
		dataType:"json",
		asygn:asygn,
		success:function(data){
			close(index);
			if(data.success){
				if(_success != "" && _success != null){
					_success(data);
				}
			} else {
				if(_fail != "" && _fail != null){
					_fail(data);
				}
			}
		},
		error:function(){
			close(index);
			alert(getPubErrorMsg());
		},
		beforeSend:function(){
			index = loading(2);
		}
	});
}
/**
 * 提交数据（包括批量提交） 返回json格式：{"success":"true","msg":"操作成功"}
 * @param _data     数据
 * @param _url      请求路径
 * @param _success  成功后回调函数
 * @param _fail     失败后回调函数
 * @param _length   T:批量提交   F：非批量提交
 * @param xx		提示消息，没有特殊提示的，这个不用传
 */
function doSub(_data, _url, _success, _fail, _length, xx){
	var index;
	if(xx == undefined || xx == ""){
		xx = "确认要提交这条信息？";
		if(_length != "1" && _length != "0"){
			xx = "确认要提交这" + _length + "条信息？";
		}
	}
	confirm(xx, {title:"提示"}, function(){
		 $.ajax({
			type:"post",
			data:_data,
			url:_url,
			success:function(val){
				var data = JSON.getJson(val);
				close(index);
				if(data.success){
					alert(data.msg);
					if(_success != "" && _success != undefined && _success != null){
						_success(data);
					}
				} else {
					alert(data.msg);
					if(_fail != "" && _fail != "" && _fail != ""){
						_fail(data);
					}
				}
			},
			error: function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend: function(){
				index = loading(2);
			}
		});
	}); 
}
/**
 * 提交数据（包括批量提交） 返回json格式：{"success":"true","msg":"操作成功"}
 * @param _data     数据
 * @param _url      请求路径
 * @param _success  成功后回调函数
 * @param _fail     失败后回调函数
 * @param _length   T:批量提交   F：非批量提交
 * @param xx		提示消息，没有特殊提示的，这个不用传
 */
function doSubNoAlert(_data, _url, _success, _fail, _length, xx){
	var index;
	 $.ajax({
		type:"post",
		data:_data,
		url:_url,
		success:function(val){
			var data = JSON.getJson(val);
			close(index);
			if(data.success){
				alert(data.msg);
				if(_success != "" && _success != undefined && _success != null){
					_success(data);
				}
			} else {
				alert(data.msg);
				if(_fail != "" && _fail != "" && _fail != ""){
					_fail(data);
				}
			}
		},
		error: function(){
			close(index);
			alert(getPubErrorMsg());
		},
		beforeSend: function(){
			index = loading(2);
		}
	});
}
/**
 * 撤销数据（包括批量撤销） 返回json格式：{"success":"true","msg":"操作成功"}
 * @param _data     数据
 * @param _url      请求路径
 * @param _success  成功后回调函数
 * @param _fail     失败后回调函数
 * @param _length   
 */
function doBack(_data, _url, _success, _fail, _length, xx){
	var index;
	if(xx == undefined || xx == ""){
		xx = "确认要撤销这条信息？";
		if(_length != "1" && _length != "0"){
			xx = "确认要撤销这" + _length + "条信息？";
		}
	}
	confirm(xx, {title:"提示"}, function(){
		 $.ajax({
			type:"post",
			data:_data,
			url:_url,
			success:function(val){
				var data = JSON.getJson(val);
				close(index);
				if(data.success){
					alert(data.msg);
					if(_success != "" && _success != undefined && _success != null){
						_success(data);
					}
				} else {
					alert(data.msg);
					if(_fail != "" && _fail != "" && _fail != ""){
						_fail(data);
					}
				}
			},
			error:function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(){
				index = loading(2);
			}
		});
	}); 
}
/**
 * 保存方法 返回json格式：{"success":"true","msg":保存成功"}
 * @param _validate   验证类变量
 * @param _formId 表单id
 * @param _url 请求路径
 * @param _success  成功后回调函数
 * @param _fail  失败后回调函数
 */
function doSave(_validate, _formId, _url, _success, _fail){
	var index;
	var valid;
	if(_validate){
		_validate.bootstrapValidator("validate");
		valid = $('#' + _formId).data('bootstrapValidator').isValid();
	} else {
		valid = true;
	}
	if(valid){
		$.ajax({
			type:"post",
			url:_url,
			data:$('#' + _formId).serialize(),
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				alert(data.msg);
				if(data.success == "true"){
					$("#operateType").val("U");
					if(_success != "" && _success != "" && _success != ""){
						_success(data);
					}
				} else {
					if(_fail != "" && _fail != "" && _fail != ""){
						_fail(data);
					}
				}
			},
			error:function(val){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(val){
				index = loading(2);
			}
		});
	}
}
/**
 * 保存方法 返回json格式：{"success":"true","msg":保存成功"}不弹出提示
 * @param _validate   验证类变量
 * @param _formId 表单id
 * @param _url 请求路径
 * @param _success  成功后回调函数
 * @param _fail  失败后回调函数
 */
function doSaveFb(_validate, _formId, _url, _success, _fail){
	var index;
	var valid;
	if(_validate){
		_validate.bootstrapValidator("validate");
		valid = $('#' + _formId).data('bootstrapValidator').isValid();
	} else {
		valid = true;
	}
	if(valid){
		$.ajax({
			type:"post",
			url:_url,
			data:arrayToJson($('#' + _formId).serializeArray()),
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				//alert(data.msg);
				if(data.success == "true"){
					$("#operateType").val("U");
					if(_success != "" && _success != "" && _success != ""){
						_success(data);
					}
				} else {
					if(_fail != "" && _fail != "" && _fail != ""){
						_fail(data);
					}
				}
			},
			error:function(val){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(val){
				index = loading(2);
			}
		});
	}
}
//SEB保存
function doSaveEsb(_validate, _formId, _url, _success, _fail){
	var index;
	var valid;
	if(_validate){
		_validate.bootstrapValidator("validate");
		valid = $('#' + _formId).data('bootstrapValidator').isValid();
	} else {
		valid = true;
	}
	if(valid){
		$.ajax({
			type:"post",
			url:_url,
			data:arrayToJson($('#' + _formId).serializeArray()),
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				//var data = eval("(" + val + ")"); 
				//alert(data.msg);
				//alert(data["msg"]);
				if(data["success"]){
					alert("操作成功");
					$("#operateType").val("U");
					if(_success != "" && _success != "" && _success != ""){
						_success(data);
					}
				} else {
					if(_fail != "" && _fail != "" && _fail != ""){
						_fail(data);
					}
				}
			},
			error:function(val){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(val){
				index = loading(2);
			}
		});
	}
}
//特殊保存（含有字符数组）
function doSave_dlxx(_validate, _formId, _url, _success, _fail){
	var index;
	var valid;
	if(_validate){
		_validate.bootstrapValidator("validate");
		valid = $('#' + _formId).data('bootstrapValidator').isValid();
	} else {
		valid = true;
	}
	if(valid){
		$.ajax({
			type:"post",
			url:_url,
			data:$('#' + _formId).serialize,
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				alert(data.msg);
				if(data.success == "true"){
					$("#operateType").val("U");
					if(_success != "" && _success != "" && _success != ""){
						_success(data);
					}
				}
				else
				{
					if(_fail != "" && _fail != "" && _fail != ""){
						_fail(data);
					}
				}
			},
			error:function(val){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(val){
				index = loading(2);
			}
		});
	}
}
/**
 * 导出Excel方法
 * @param _data json数据 如：查询条件等
 * @param _url 导出执行的controller路径（可能带有参数，比如有单位树的列表，需要带左侧单位树的条件）
 * @param _fileName 导出Excel的文件名 如：单位信息，不用带.xls了
 * @param _path ${pageContext.request.contextPath}
 * @param _selectedId 选择导出的数据，不选的话默认导出当前条件下的所有数据
 */
function doExp(_data, _url, _fileName, _path, _selectedId,xx){
	var index;
	if(xx == undefined || xx == ""){
		xx = "确认导出全部信息？";
		if(_selectedId !=''&& _selectedId !=undefined){
			xx = "确认要导出选中的信息？";
		}
	}
	confirm(xx, {title:"提示"}, function(z){
		$.ajax({
			type:"post",
			data:"searchJson=" + _data + "&xlsname=" + _fileName + "&id=" + _selectedId,
			url:_url,
			dataType:"json",
			success:function(val){
				close(index);
				close(z);
				FileDownload(_path + "/file/fileDownload", _fileName + ".xls", val.url);
			},
			error:function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(){
				index = loading();
			}
		});
	}); 
}
/**
 * 导出Excel方法
 * @param _data json数据 如：查询条件等
 * @param _url 导出执行的controller路径（可能带有参数，比如有单位树的列表，需要带左侧单位树的条件）
 * @param _fileName 导出Excel的文件名 如：单位信息，不用带.xls了
 * @param _path ${pageContext.request.contextPath}
 * @param _selectedId1 选择导出的数据，不选的话默认导出当前条件下的所有数据
 * @param _selectedId2 选择导出的数据，不选的话默认导出当前条件下的所有数据
 * 两个POST条件
 * author:王承馨  目前只针对工资汇总，日后添加参数组的方法
 */
function doExpGzhz(_data, _url, _fileName, _path, _selectedId1,_selectedId2,xx){
	var index;
	if(xx == undefined || xx == ""){
		xx = "确认导出全部信息？";
		if(_selectedId1 !=''&& _selectedId1 !=undefined){
			xx = "确认要导出选中的信息？";
		}
	}
	confirm(xx, {title:"提示"}, function(z){
		$.ajax({
			type:"post",
			data:"searchJson=" + _data + "&xlsname=" + _fileName + "&id1=" + _selectedId1+"&id2="+_selectedId2,
			url:_url,
			dataType:"json",
			success:function(val){
				close(index);
				close(z);
				FileDownload(_path + "/file/fileDownload", _fileName + ".xls", val.url);
			},
			error:function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(){
				index = loading();
			}
		});
	}); 
}
/**
 * 导出Txt方法
 * @param _data json数据 如：查询条件等
 * @param _url 导出执行的controller路径（可能带有参数，比如有单位树的列表，需要带左侧单位树的条件）
 * @param _fileName 导出txt的文件名，不需要扩展名
 * @param _path ${pageContext.request.contextPath}
 */
function doTxt(_data, _url, _fileName, _path){
	$.ajax({
		type:"post",
		data:_data,
		url:_url,
		dataType:"json",
		success:function(val){
			close(index);
			if(val.success){
				FileDownload(_path + "/file/fileDownload", _fileName + ".txt", "txt\\" + val.url);
			} else if(val.msg) {
				alert(val.msg);
			} else {
				confirm("导出txt文件失败，确定要下载错误文件吗？", {title:"提示"}, function(z){
					close(z);
					FileDownload(_path + "/file/fileDownload", "错误信息_" + _fileName + ".txt", "txt\\" + val.url);
				});
			}
		},
		error:function(){
			close(index);
			alert(getPubErrorMsg());
		},
		beforeSend:function(){
			index = loading();
		}
	});
}
function doExp2(_data, _url, _fileName, _path, _selectedId){
	$.ajax({
		type:"post",
		data:"searchJson=" + _data + "&xlsname=" + _fileName + "&id=" + _selectedId,
		url:_url,
		dataType:"json",
		success:function(val){
			FileDownload(_path + "/file/fileDownload", _fileName + ".xls", val.url);
		},
		error:function(){
			alert(getPubErrorMsg());
		},
		beforeSend:function(){
		}
	});
}

/**
 * 确定方法
 * @param parm  json格式
 * {qzfun:function(){},fun:function(){}}
 * {qzfun:function(){},fun:{url:"",data:"",getdata:[{key:"",val:""},{key:"",val:""}],success:function(){},fail:function(){}}}
 * {qzfun:{url:"",data:""},fun:{url:"",data:"",getdata:[{key:"",val:""},{key:"",val:""}],success:function(){},fail:function(){}}}
 * 
 * {fun:function(){}}
 * {fun:{url:"",data:"",getdata:[{key:"",val:""},{key:"",val:""}],success:function(){},fail:function(){}}}
 * 
 */
function doSure(parm){
	var index = loading(2);
	if(parm.qzfun){
		if(typeof parm.qzfun == "function"){
			if(parm.qzfun()){//这个方法必须返回bool值
				if(typeof parm.fun == "function"){
					parm.fun();
					close(index);
				} else {
					doAction(parm,index);
				}
			} else {
				close(index);//在前置方法里已经报错了，后边的都不用走了
			}
		} else {
			$.ajax({
				type:"post",
				url:parm.qzfun.url,
				data:parm.qzfun.data,
				dataType:"json",
				success:function(val){
					if(val.success){
						var d = "";
						if(parm.fun.getdata){
							var data = [];
							for(var i = 0; i < parm.fun.getdata.length; i++){
								data.push(parm.fun.getdata[i].key + "=" + val[parm.fun.getdata[i].val]);
							}
							d = data.join("&");
						}
						if(d != ""){
							if(parm.fun.data){
								parm.fun.data = parm.fun.data + "&" + d;
							} else {
								parm.fun.data = d;
							}
						}
						doAction(parm,index);
					} else {
						close(index);
						alert(val.msg);
					}
				},
				error:function(val){
					close(index);
					alert(getPubErrorMsg());
				}
			});
		}
	} else {
		doAction(parm,index);
	}
}
/**
 * 如果在执行这个方法之前，已经加载了遮罩层，需要把遮罩层的index传过来，如果没有加载，可以只传一个参数
 * @param parm
 * @param index
 */
function doAction (parm, index){
	if(!index){
		index = loading(2);
	}
	if(typeof parm.fun == "function"){
		parm.fun();
		close(index);
	} else {
		$.ajax({
			type:"post",
			url:parm.fun.url,
			data:parm.fun.data,
			dataType:"json",
			success:function(val){
				if(val.success){
					if(parm.fun.success){
						parm.fun.success();
					}
					close(index);
					alert(val.msg);
				} else {
					if(parm.fun.fail){
						parm.fun.fail();
					}
					close(index);
					alert(val.msg);
				}
			},
			error:function(val){
				close(index);
				alert(getPubErrorMsg());
			}
		});
	}
}
/************************************************** 操作按钮通用设置  结束***************************************************/

/**
 * 数值验证（为空的时候不处理任何信息）
 * len：保留小数位数
 * flag：如果需要保留小数，是否补全小数，true：补全，false：不补全，如果len等于0，那么不管这里是true还是false，都不补全
 * mark：正负号,true：允许正负号 false：只能是正数
 * e：点击的按钮
 */
$.fn.Num = function(len,flag,mark,e){
	//上下左右是37-40 home是36 end是35  backspace是8  delete是46
	//shift、ctrl、alt、tab、caps lock是16-19
	if((e.keyCode > 34 && e.keyCode < 41) || (e.keyCode > 15 && e.keyCode < 21) || $(this).val() == ""){
		
	} else {
		//处理保留位数
		len = len > -1 ? len : 2;
		var int = /^[0-9]*$/;
		var length = int.test($(this).attr("data-length")) ? $(this).attr("data-length") : len;
		//如果保留小数位数为0，那么不管flag是什么，统统改成不保留.
		if(flag && length == 0){
			flag = false;
		};
		//获取当前控件的值
		var val = $(this).val();
		var rval = "";//返回的数值

		//处理+-号
		var fh = "";
		//如果设置的是允许正负数，并且第一位是-号的，那么变量fh就是-，正数的+号可以不写，最后会拼接到转换好的数字上
		if(val.substr(0, 1) == "-" || val.substr(0, 1) == "+"){
			if(mark){
				fh = val.substr(0, 1);
			}
			val = val.substr(1,val.length - 1);
		}
	
		var arr = val.split("");
		for(var i = 0; i < arr.length; i++){
			if(rval == ""){
				if(arr[i] == "."){
					rval = "0.";
				} else {
					var ls = parseInt(arr[i],10);
					if(!isNaN(ls)){
						rval = ls + "";
					}
				}
			} else {
				if(arr[i] == "."){
					if(rval.indexOf(".") < 0){
						rval = rval + arr[i];
					} else {
						//如果已经有小数点，则忽略该小数点
					}
				} else {
    				var ls = parseInt(arr[i],10);
    				if(!isNaN(ls)){
    					if(rval == "0" && ls == 0){
    						//如果此时rval里边只有一个0，并且当前这个数字也是0，那么忽略这个数字
    					} else {
    						rval = rval + (ls + "");
    					}
    				}
				}
			}
		}
	
		if(len == 0){//不保留小数
			rval = rval.replace(".", "");
		} else {
			var ind = rval.indexOf(".");
			if(flag){//true是补全
				if(ind < 0){//数据中没有小数点
					rval = (rval == "" ? "0" : rval) + ".";
    				for(var i = 0; i < len; i++){
    					rval = rval + "0";
    				}
				} else {
					//截取小数点后的字符串str
					var str = rval.substring(ind + 1, rval.length);
					//如果str为空，往小数点后添len个0
    				if(str == ""){
    					for(var i = 0; i < len; i++){
	    					rval = rval + "0";
	    				}
    				} else {
    					//如果str长度大于len,则截取str中的前len个字符作为小数点保留位
    					if(str.length > len){
    						str = str.substring(0, len);
    						rval = rval.substr(0,ind + 1) + str;
    					}else{
    						//如果str长度小于等于len，则将其补全
    						for(var i = str.length; i < len; i++){
    							rval = rval + "0";
    						}
    					}
    				}
				}
			} else {//不补全的时候，如果小数位数超长，那么也截取
				if(ind >= 0){
					var str = rval.substring(ind + 1, rval.length);
    				if(str.length > len){
    					str = str.substring(0, len);
	    				rval = rval.substr(0,ind + 1) + str;
    				}
				}
			}
		}
		var b = (rval.substr(0,1) == "0" && rval != "0.00" && rval != "0");
		while(b){
			rval = rval.substr(1,rval.length);
			if(rval.substr(0,1) == "."){
				rval = "0" + rval;
				b = false;
			} else if(rval.substr(0,1) != "0") {
				b = false;
			}
		}
	
		if(rval != $(this).val()){
			$(this).val(fh + rval);
		}
	}
};

/************************************************** DataTable默认设置 开始***************************************************/

/**
 * 列表页面
 * @param _formId  form表单id
 * @param _url 请求路径 
 * @param _order 排序列
 * @param _columns 列数据展示
 * @param _drawCallback 回调函数
 * @returns
 */
function getDataTable(_formId, _url, _order, _columns, _drawCallback){
	var _length = getTopFrame().window.sessionRowNumLength;
	if(_order.length > 0){
		if(!/^\d+$/.test(_order[0])){
			var str = _order[0].toUpperCase();
			for(var i = 0, len = _columns.length; i < len; i++){
				if(_columns[i]["data"] == str){
					_order = [i, _order[1]];
					break;
				}
			}
		}
	}
	return $('#' + _formId).DataTable({
        "ajax": {
        	method:"post",
        	data:arrayToJson($("form").serializeArray()),
        	url:_url,//获取数据的方法
        	async:false,
            complete:function(XMLHttpRequest, textStatus) {
            	if(_drawCallback != undefined && _drawCallback != null && XMLHttpRequest.statusText == "OK"){
            		_drawCallback(XMLHttpRequest.responseJSON);
                }
            }
        },
        "lengthMenu":_length,//每页显示条数设置
        "order": _order,//排序列
        "columns": _columns,//列定义
        //"dom":"<'row fyrow'<'col-md-2 col-sm-2 col-xs-2'i><'col-md-3 col-sm-3 col-xs-3'l><'col-md-7 col-sm-7 col-xs-7 pull-right'p>><'row'<'overflow' t>><'row'<'col-md-5 col-sm-5 col-xs-5'i><'col-md-7 col-sm-7 col-xs-7 pull-right'p>>",
        "dom":"<'row fyrow'<'col-md-5 col-sm-5 col-xs-5'li>><'row'<'overflow' t>><'row'<'pull-right'p><'total'o>>",
//        "drawCallback":function(){
//        	if(_drawCallback!=undefined&&_drawCallback!=null){
//        		_drawCallback();
//        	}
//        },
        error:function(){
        	alert('数据加载失败，用户登录已掉线或出现错误，请尝试重新登录！');  
         }  
	});
}
//数据导入table
function getDataTableByImp(_formId, _url, _order, _columns, _drawCallback){
	var _length = getTopFrame().window.sessionRowNumLength;
	if(_order.length > 0){
		if(!/^\d+$/.test(_order[0])){
			var str = _order[0].toUpperCase();
			for(var i = 0, len = _columns.length; i < len; i++){
				if(_columns[i]["data"] == str){
					_order = [i, _order[1]];
					break;
				}
			}
		}
	}
	return $('#' + _formId).DataTable({
        "ajax": {
        	method:"post",
        	data:arrayToJson($("form").serializeArray()),
        	url:_url,//获取数据的方法
        	async:false,
            complete:function(XMLHttpRequest, textStatus) {
            	if(_drawCallback!=undefined&&_drawCallback!=null&&XMLHttpRequest.statusText == "OK"){
            		_drawCallback(XMLHttpRequest.responseJSON);
                }
            }
        },
        "lengthMenu":_length,//每页显示条数设置
        "order": _order,//排序列
        "columns": _columns,//列定义
        "scrollX": true,
        "dom":"<'row fyrow'<'page-left wxts'li><'pull-right'p >><'row'<t>> <'row bottom'<'total'o>>",
		//"dom":"<'row'<t>> <'row bottom'><'total'o>",
        "drawCallback":function(){
//        	var heights = $(window).outerHeight()-$(".row.fyrow").outerHeight()-$(".search").outerHeight()-50-$(".row.bottom").outerHeight()-$(".dataTables_scrollHead").outerHeight();
//        	$(".dataTables_scrollBody").height(heights);
//        	if(_drawCallback!=undefined&&_drawCallback!=null){
//        		_drawCallback();
//        	}
        } 
	});
}
function getDataTableByGdl(_formId, _url, _order, _columns, lcol, rcol, _drawCallback){
	var _length = getTopFrame().window.sessionRowNumLength;
	if(_order.length > 0){
		if(!/^\d+$/.test(_order[0])){
			var str = _order[0].toUpperCase();
			for(var i = 0, len = _columns.length; i < len; i++){
				if(_columns[i]["data"] == str){
					_order = [i, _order[1]];
					break;
				}
			}
		}
	}
	return $('#' + _formId).DataTable({
        "ajax": {
        	method:"post",
        	data:arrayToJson($("form").serializeArray()),
        	url:_url,//获取数据的方法
        	async:false,
            complete:function(XMLHttpRequest, textStatus) {
            	if(_drawCallback!=undefined&&_drawCallback!=null&&XMLHttpRequest.statusText == "OK"){
            		_drawCallback(XMLHttpRequest.responseJSON);
                }
            }
        },
        "lengthMenu":_length,//每页显示条数设置
        "order": _order,//排序列
        "columns": _columns,//列定义
        "scrollX": true,
        "scrollY": true,
        "fixedColumns": {//固定列
			"leftColumns": lcol,
			"rightColumns": rcol
		},
		"dom":"<'row't><'row bottom'<'col-md-5 col-sm-5 col-xs-5'li><'col-md-7 col-sm-7 col-xs-7 pull-right'p>>",
        "drawCallback":function(){
        	var heights = $(window).outerHeight() - $(".search").outerHeight() - $(".row.bottom").outerHeight() - 20 - $(".dataTables_scrollHead").outerHeight();
        	$(".dataTables_scrollBody").height(heights);
//        	if(_drawCallback!=undefined&&_drawCallback!=null){
//        		_drawCallback();
//        	}
        } 
	});
}
function arrayToJson(val){
	if(val == ""){
		return {};
	} else if(val instanceof Array) {
		var jsonArray = [];
		for(var i = 0, len = val.length; i < len; i++){
			jsonArray.push('"' + val[i].name + '":"' + val[i].value + '"');
		}
		return JSON.getJson("{" + jsonArray.join(",") + "}");
	} else {
		return val;
	}
}
/**
 * 列表页面
 * @param _formId  form表单id
 * @param _url 请求路径 
 * @param _order 排序列
 * @param _columns 列数据展示
 * @param _drawCallback 回调函数
 * @returns
 */
function getDataTableByHj(_formId, _url, _order, _columns, _drawCallback){
	var _length = getTopFrame().window.sessionRowNumLength;
	if(_order.length > 0){
		if(!/^\d+$/.test(_order[0])){
			var str = _order[0].toUpperCase();
			for(var i = 0,len = _columns.length; i < len; i++){
				if(_columns[i]["data"] == str){
					_order = [i, _order[1]];
					break;
				}
			}
		}
	}
	return $('#' + _formId).DataTable({
        "ajax": {
        	method:"post",
        	data:arrayToJson($("form").serializeArray()),
            url:_url,//获取数据的方法
        	async:false,
            complete:function(XMLHttpRequest, textStatus) {
            	if(_drawCallback!=undefined&&_drawCallback!=null&&XMLHttpRequest.statusText == "OK"){
            		_drawCallback(XMLHttpRequest.responseJSON);
                }
            }
        },
        "lengthMenu":_length,//显示记录数
        "order": _order,//排序列
        "columns": _columns,//列定义
        "dom":"<'row'<'col-md-5 col-sm-5 col-xs-5'li><'col-md-7 col-sm-7 col-xs-7 pull-right'p>><'row'<'overflow' t>><'row'<'col-md-12 col-sm-12 col-xs-12 total'o>><'row'<'col-md-7 col-sm-7 col-xs-7 pull-right'p>>",
        "drawCallback":function(){
//        	if(_drawCallback!=undefined&&_drawCallback!=null){
//        		_drawCallback();
//        	}
        } 
	});
}
function getDataTabless(_formId, _url, _order, _columns, _drawCallback){

//	var _length = getTopFrame().window.sessionRowNumLength;
	if(_order.length > 0){
		if(!/^\d+$/.test(_order[0])){
			var str = _order[0].toUpperCase();
			for(var i = 0, len = _columns.length; i < len; i++){
				if(_columns[i]["data"] == str){
					_order = [i, _order[1]];
					break;
				}
			}
		}
	}
	return $('#' + _formId).DataTable({
        "ajax": {
        	method:"post",
//        	dataType: "jsonp", 
//        	jsonp:"callback",
        	data:arrayToJson($("form").serializeArray()),
        	url:_url,//获取数据的方法
        	async:false,
            complete:function(XMLHttpRequest, textStatus) {
            	if(_drawCallback != undefined && _drawCallback != null && XMLHttpRequest.statusText == "OK"){
            		_drawCallback(XMLHttpRequest.responseJSON);
                }
            }
        },
        language: { 
            "sProcessing": "处理中...",
//            "sLengthMenu": "每页 _MENU_ 条记录 ",
            "sInfo": "共_TOTAL_ 条记录",
            "sZeroRecords": "没有匹配结果",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)", 
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            }
        },
        "lengthMenu":15,//每页显示条数设置
        "order": _order,//排序列
        "columns": _columns,//列定义
        //"dom":"<'row fyrow'<'col-md-2 col-sm-2 col-xs-2'i><'col-md-3 col-sm-3 col-xs-3'l><'col-md-7 col-sm-7 col-xs-7 pull-right'p>><'row'<'overflow' t>><'row'<'col-md-5 col-sm-5 col-xs-5'i><'col-md-7 col-sm-7 col-xs-7 pull-right'p>>",
       // "dom":"<'row fyrow'<'col-md-5 col-sm-5 col-xs-5'li>><'row'<'overflow' t>><'row'<'pull-right'p><'total'o>>",
        "dom":"",
//        "drawCallback":function(){
//        	if(_drawCallback!=undefined&&_drawCallback!=null){
//        		_drawCallback();
//        	}
//        },
        error:function(){
        	alert('数据加载失败，用户登录已掉线或出现错误，请尝试重新登录！');  
         }  
	});
}
function getDataTablejz(_formId, _url, _order, _columns, _drawCallback){
	var _length = getTopFrame().window.sessionRowNumLength;
	if(_order.length > 0){
		if(!/^\d+$/.test(_order[0])){
			var str = _order[0].toUpperCase();
			for(var i = 0,len = _columns.length; i < len; i++){
				if(_columns[i]["data"] == str){
					_order = [i,_order[1]];
					break;
				}
			}
		}
	}
	return $('#'+_formId).DataTable({
        "ajax": {
        	method:"post",
        	data:arrayToJson($("form").serializeArray()),
        	url:_url,//获取数据的方法
        	async:false,
            complete:function(XMLHttpRequest, textStatus) {
            	if(_drawCallback != undefined && _drawCallback != null && XMLHttpRequest.statusText == "OK"){
            		_drawCallback(XMLHttpRequest.responseJSON);
                }
            }
        },
        "lengthMenu":_length,//每页显示条数设置
        "order": _order,//排序列
        "columns": _columns,//列定义
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
        "drawCallback":function(){
//        	if(_drawCallback!=undefined&&_drawCallback!=null){
//        		_drawCallback();
//        	}
        } 
	});
}
/************************************************** DataTable默认设置 结束***************************************************/
var isNull = function(val){
	if(val){
		if(val == "" || val == null || val == "null" || val == undefined || val == "undefined")
			return true;
		else
			return false;
	} else {
		return true;
	}
};
var noNull = function(val){
	return !isNull(val);
};
var isNullToDefaultString = function(val, def){
	if(val){
		if(val == "" || val == null || val == "null" || val == undefined || val == "undefined")
			return def;
		else
			return val;
	} else {
		return def;
	}
};
//温馨提示点击移除
$(document).on("click", ".wxtsRemove", function(){
	$(".alert-info").remove();
});
function getDataTableByListHj(_formId, _url, _order, _columns, lcol, rcol, _drawCallback, _num,_createdRow, len){
	//len 为前台传过来的长度
	var _length;
	if(len){
		_length = [len];
	}else{
		_length = getTopFrame().window.sessionRowNumLength;
	}
	if(_order.length > 0){
		if(!/^\d+$/.test(_order[0])){
			var str = _order[0].toUpperCase();
			for(var i = 0, len = _columns.length; i < len; i++){
				if(_columns[i]["data"] == str){
					_order = [i,_order[1]];
					break;
				}
			}
		}
	}
	return $('#' + _formId).DataTable({
        "lengthMenu":_length,//每页显示条数设置
        "order": _order,//排序列
        "columns": _columns,//列定义
        "processing":true,
        "scrollX": true,
        "scrollY": true,
        "fixedColumns": {//固定列
			"leftColumns": lcol,
			"rightColumns": rcol
		},
		"createdRow":_createdRow,
		
		"dom":"<'row fyrow'<'page-left wxts'li>><'row'<t>> <'row bottom'<'pull-right'p ><'total'o>>",
        "ajax": {
        	method:"post",
        	data:arrayToJson($("form").serializeArray()),
        	url:_url,//获取数据的方法
        	async:false,
            complete:function(XMLHttpRequest, textStatus) {
            	if(_drawCallback!=undefined&&_drawCallback!=null&&XMLHttpRequest.statusText == "OK"){
            		_drawCallback(XMLHttpRequest.responseJSON);
                }
            }
        },
        "drawCallback":function(){
        	var heights = $(window).outerHeight() - $(".search").outerHeight() - $(".row.fyrow").outerHeight() - $(".row.bottom").outerHeight() - $(".dataTables_scrollHead").outerHeight()-20;
        	if(_num=='1'){
        		heights = $(window).outerHeight() - $(".row.fyrow").outerHeight() - $(".row.bottom").outerHeight() - $(".dataTables_scrollHead").outerHeight();
        	} else if(_num=='ry') {
	        	heights = $(window).outerHeight() - $(".search").outerHeight() - $(".row.bottom").outerHeight() - $(".row.fyrow").outerHeight() - $(".rybxx").outerHeight() - $(".dataTables_scrollHead").outerHeight();
			} else if(_num=='zjxx') {
				heights = $(window).outerHeight() - $(".rybxx").outerHeight() - $(".search").outerHeight() - $(".row.fyrow").outerHeight() - $(".dataTables_scrollHead").outerHeight() - $(".row.bottom").outerHeight();
			} else if(_num=='yssjzl') {
				heights = $(window).outerHeight() - $(".search").outerHeight() - 2 - $("#yzxx").outerHeight() - $(".row.bottom").outerHeight() - $(".row.fyrow").outerHeight() - $(".dataTables_scrollHead").outerHeight();
				$(".bottom_scroll").css("margin-bottom", "110px");
			} else if(_num == "sjsb") {
				heights = heights - $("#d_tablist").outerHeight();
			}
        	$(".dataTables_scrollBody").height(heights);
        },
        error:function(){
        	alert('网络异常或数据加载失败，请尝试重新登录系统！');    
        }  
	});
}
function pageSkip(a_target,a_pageName,suffix){
	if(suffix == null){
		suffix = "";
	}
	window.location.href = a_target+"/pageSkip?pageName="+a_pageName+suffix;
}
/**
 * 将form表单解析为json字符串，如果单选按钮的name匹配pattern,则将name替换为str
 * pattern,正则表达式
 * str,字符串
 */
$.fn.serializeObject = function(start,end,pattern,str){
	var f = {"list":[]};
    var a = this.serializeArray();
    var o = {};
    if(pattern!=null&&str!=null){
    	var reg = new RegExp(pattern);
    }
    $.each(a, function() {
    	if(pattern!=null&&str!=null){
    		if(reg.test(this.name)){
    			this.name = str;
    		}
    	}
    	if (this.name == start) {
        	o = {};
        	o[this.name] = this.value;
        } else if(this.name == end){
        	o[this.name] = this.value;
        	f["list"].push(o);
        }else{
        	o[this.name] = this.value;
        }
    });
    return f;
};
/**
 *将form表单解析为json字符串(带多个单选按钮)
 *pattern 字符串，以<>分隔开，
 *str 字符串，以<>分隔开，
 */
$.fn.serializeObject2 = function(start,end,pattern,str){
	var f = {"list":[]};
    var a = this.serializeArray();
    var o = {};
    var tag = false;
    if(pattern!=null&&str!=null){
    	var pattern_arr = pattern.split("<>");
	    var reg_arr = [];
    	for(var i in pattern_arr){
    		var p = pattern_arr[i];
    		var reg = new RegExp(p);
    		reg_arr.push(reg);
    	}
    	var str_arr = str.split("<>");
    	tag = true;
    }
    $.each(a, function() {
    	if(tag){
    		for(var i in reg_arr){
    			var reg = reg_arr[i];
	    		if(reg.test(this.name)){
	    			this.name = str_arr[i];
	    		}
    		}
    	}
    	if (this.name == start) {
        	o = {};
        	o[this.name] = this.value;
        } else if(this.name == end){
        	o[this.name] = this.value;
        	f["list"].push(o);
        }else{
        	o[this.name] = this.value;
        }
    });
    return f;
};
//获取上下文路径
function getContextPath(){
	var reg = new RegExp("[^/]*//[^/]*/[^/]*/");
	var ctx =  reg.exec(location.href).toString();
	return ctx.substring(0,ctx.length-1);
}
//获取基准路径
function getBasePath(){
	var i = location.href.lastIndexOf("/");
	return location.href.substring(0,i);
}
//查询是否停止新增
function querySftzxz(mkbh){
	var tag = false;
	if(isNull(mkbh)){
		return tag;
	}
	mkbh = mkbh.substring(0,mkbh.length-2);
	var ctxPath = getContextPath();
	var url_ = ctxPath+"/sqspwh/getSftzxz";
	$.ajax({
		type:"post",
		url:url_,
		data:"mkbh="+mkbh,
		dataType:"json",
		async:false,
		success:function(data){
			if(data.success){
				tag = true;
			}
		},
		error:function(){
			alert("抱歉，系统出现错误！");
		}
	});
	return tag;
}
function isNull(str){
	return str==null||str=="" ? true:false;
}
//页面合计统计展示

function setGroup(json){
	
	if(json==undefined){
		json = table.ajax.json();
	}
	var group = json.groupJson;
	if($(".badge").length!=0){
		$(".badge").each(function(){
			$(this).text("0");
		});
		for(var i=0;i<group.length;i++){
			var ztbzmc = group[i].ZTBZMC;
			var counts = group[i].COUNTS;
			$("."+ztbzmc).text(counts);
		}
	}
	if(json.zjJson!=null && json.zjJson!=undefined){
		var zjjson = json.zjJson[0];
		var hjjson = json.hjJson[0];
		if(zjjson.YFHJ!=null){//总价，单价，数量
			$(".total").prop("innerHTML","应发合计：<font color='red'>" + zjjson.YFHJ + "</font>");
		}else if(hjjson.ZZJHJ!=null&&hjjson.SLHJ!=null){//总价，数量
			$(".total").prop("innerHTML","总价合计：<font color='red'>" + zjjson.ZZJHJ + "</font>&nbsp;当前页总价合计：<font color='red'>" + hjjson.ZZJHJ+"</font>&nbsp;数量合计：<font color='red'>"+zjjson.SLHJ+"</font>&nbsp;当前页数量合计：<font color='red'>"+ hjjson.SLHJ+"</font>");
		}else if(zjjson.ZMJZS!=null&&zjjson.ZCYZS!=null&&zjjson.LJZJS!=null&&zjjson.ZZJS!=null&&zjjson.DJS!=null){//账面净值,资产原值,累计折旧额，总价,单价
			$(".total").prop("innerHTML","账面净值合计：<font color='red'>" + zjjson.ZMJZS + "</font>&nbsp;当前页账面净值合计：<font color='red'>" + hjjson.ZMJZS+"</font>&nbsp;资产原值合计：<font color='red'>"+zjjson.ZCYZS+"</font>&nbsp;当前页资产原值合计：<font color='red'>"+hjjson.ZCYZS+"</font>&nbsp;累计折旧额合计：<font color='red'>"+zjjson.LJZJS+"</font>&nbsp;当前页累计折旧额合计：<font color='red'>"+hjjson.LJZJS+"</font>&nbsp;总价合计：<font color='red'>"+zjjson.ZZJS+"</font>&nbsp;当前页总价合计：<font color='red'>"+hjjson.ZZJS+"</font>&nbsp;单价合计：<font color='red'>"+zjjson.DJS+"</font>&nbsp;当前页单价合计：<font color='red'>"+hjjson.DJS+"</font>");
		}else if(zjjson.ZMJZS!=null&&zjjson.ZCYZS!=null&&zjjson.LJZJS!=null&&zjjson.ZZJS!=null){//账面净值,资产原值,累计折旧额，总价
			$(".total").prop("innerHTML","账面净值合计：<font color='red'>" + zjjson.ZMJZS + "</font>&nbsp;当前页账面净值合计：<font color='red'>" + hjjson.ZMJZS+"</font>&nbsp;资产原值合计：<font color='red'>"+zjjson.ZCYZS+"</font>&nbsp;当前页资产原值合计：<font color='red'>"+hjjson.ZCYZS+"</font>&nbsp;累计折旧额合计：<font color='red'>"+zjjson.LJZJS+"</font>&nbsp;当前页累计折旧额合计：<font color='red'>"+hjjson.LJZJS+"</font>&nbsp;总价合计：<font color='red'>"+zjjson.ZZJS+"</font>&nbsp;当前页总价合计：<font color='red'>"+hjjson.ZZJS+"</font>");
		}else if(zjjson.ZMJZS!=null&&zjjson.LJZJS!=null&&zjjson.ZZJS!=null){//账面净值,累计折旧额，总价
			$(".total").prop("innerHTML","账面净值合计：<font color='red'>" + zjjson.ZMJZS + "</font>&nbsp;当前页账面净值合计：<font color='red'>" + hjjson.ZMJZS+"</font>&nbsp;累计折旧额合计：<font color='red'>"+zjjson.LJZJS+"</font>&nbsp;当前页累计折旧额合计：<font color='red'>"+hjjson.LJZJS+"</font>&nbsp;总价合计：<font color='red'>"+zjjson.ZZJS+"</font>&nbsp;当前页总价合计：<font color='red'>"+hjjson.ZZJS+"</font>");
		}else if(zjjson.DJS!=null && zjjson.ZZJHJ!=null){//总价，单价
			$(".total").prop("innerHTML","总价合计：<font color='red'>" + zjjson.ZZJHJ + "</font>&nbsp;当前页总价合计：<font color='red'>" + hjjson.ZZJHJ+"</font>&nbsp;单价总计：<font color='red'>" + zjjson.DJS + "</font>&nbsp;当前页单价合计：<font color='red'>" + hjjson.DJS+"</font>");
		}else if(zjjson.YGWXFYS!=null){//预估维修费用
			$(".total").prop("innerHTML","预估维修费用总计：<font color='red'>" + zjjson.YGWXFYS + "</font>&nbsp;当前页预估维修费用合计：<font color='red'>" + hjjson.YGWXFYS+"</font>");
		}else if(zjjson.SQJES!=null){//申请金额
			$(".total").prop("innerHTML","申请金额总计：<font color='red'>" + zjjson.SQJES + "</font>&nbsp;当前页申请金额合计：<font color='red'>" + hjjson.SQJES+"</font>");
		}else if(zjjson.WXJES!=null){//维修金额
			$(".total").prop("innerHTML","维修金额总计：<font color='red'>" + zjjson.WXJES + "</font>&nbsp;当前页维修金额合计：<font color='red'>" + hjjson.WXJES+"</font>");
		}else if(zjjson.ZMYZ!=null&&zjjson.PGJZ!=null&&zjjson.CZJZ!=null){//账面总值,评估价值,处置价值
			$(".total").prop("innerHTML","账面总值合计：<font color='red'>" + zjjson.ZMYZ + "</font>&nbsp;当前页账面总值合计：<font color='red'>" + hjjson.ZMYZ+"</font>&nbsp;评估价值合计：<font color='red'>"+zjjson.PGJZ+"</font>&nbsp;当前页评估价值合计：<font color='red'>"+hjjson.PGJZ+"</font>&nbsp;处置价值合计：<font color='red'>"+zjjson.CZJZ+"</font>&nbsp;当前页处置价值合计：<font color='red'>"+hjjson.CZJZ+"</font>");
		}else if(zjjson.HBJES!=null&&zjjson.ZJJES!=null&&zjjson.SYJES!=null){//划拨金额,追加金额已使用金额
			$(".total").prop("innerHTML","划拨金额合计：<font color='red'>"+zjjson.HBJES+"</font>&nbsp;当前页划拨金额合计：<font color='red'>"+hjjson.HBJES+"</font>&nbsp;追加金额合计：<font color='red'>"+zjjson.ZJJES+"</font>&nbsp;当前页追加金额合计：<font color='red'>"+hjjson.ZJJES+"</font>&nbsp;已使用金额合计：<font color='red'>"+zjjson.SYJES+"</font>&nbsp;当前页已使用金额合计：<font color='red'>"+hjjson.SYJES+"</font>");
		}else if(zjjson.HBJES!=null&&zjjson.ZJJES!=null){//划拨金额,追加金额
			$(".total").prop("innerHTML","划拨金额合计：<font color='red'>"+zjjson.HBJES+"</font>&nbsp;当前页划拨金额合计：<font color='red'>"+hjjson.HBJES+"</font>&nbsp;追加金额合计：<font color='red'>"+zjjson.ZJJES+"</font>&nbsp;当前页追加金额合计：<font color='red'>"+hjjson.ZJJES+"</font>");
		}else if(zjjson.BFSLHJ!=null&&zjjson.BDDJS!=null&&zjjson.CZSYS!=null){//处置收益,报废金额,报废数量
			$(".total").prop("innerHTML","处置收益总值合计：<font color='red'>" + zjjson.CZSYS + "</font>&nbsp;当前页处置收益总值合计：<font color='red'>" + hjjson.CZSYS+"</font>&nbsp;报废金额合计：<font color='red'>"+zjjson.BDDJS+"</font>&nbsp;当前页报废金额合计：<font color='red'>"+hjjson.BDDJS+"</font>&nbsp;报废数量合计：<font color='red'>"+zjjson.BFSLHJ+"</font>&nbsp;当前页报废数量合计：<font color='red'>"+hjjson.BFSLHJ+"</font>");
		}else if(zjjson.DJS!=null){//单价
			$(".total").prop("innerHTML","单价合计：<font color='red'>" + zjjson.DJS + "</font>&nbsp;当前页单价合计：<font color='red'>" + hjjson.DJS+"</font>");
		}else if(zjjson.ZZJHJ!=null){//总价
			$(".total").prop("innerHTML","总价合计：<font color='red'>" + zjjson.ZZJHJ + "</font>&nbsp;当前页总价合计：<font color='red'>" + hjjson.ZZJHJ+"</font>");
		}else if(zjjson.ZMYZS!=null){//账面原值
			$(".total").prop("innerHTML","账面原值合计：<font color='red'>" + zjjson.ZMYZS + "</font>&nbsp;当前页账面原值合计：<font color='red'>" + hjjson.ZMYZS + "</font>");
		}else if(zjjson.SLMJHJ!=null && zjjson.YZHJ!=null){//数量/面积,原值（账与表）
			$(".total").prop("innerHTML","原值合计：<font color='red'>" + zjjson.YZHJ + "</font>&nbsp;当前页原值合计：<font color='red'>" + hjjson.YZHJ + "</font>&nbsp;数量/面积合计：<font color='red'>" + zjjson.SLMJHJ + "</font>&nbsp;当前页数量/面积合计：<font color='red'>" + hjjson.SLMJHJ + "</font>");
		}else if(zjjson.YZHJ!=null && zjjson.MJHJ!=null){//原值,面积（账与表）
			$(".total").prop("innerHTML","原值合计：<font color='red'>" + zjjson.YZHJ + "</font>&nbsp;当前页原值合计：<font color='red'>" + hjjson.YZHJ + "</font>&nbsp;面积合计：<font color='red'>" + zjjson.MJHJ + "</font>&nbsp;当前页面积合计：<font color='red'>" + hjjson.MJHJ + "</font>");
		}else if(zjjson.YZHJ!=null && zjjson.SLHJ!=null){//原值,数量（账与表）
			$(".total").prop("innerHTML","原值合计：<font color='red'>" + zjjson.YZHJ + "</font>&nbsp;当前页原值合计：<font color='red'>" + hjjson.YZHJ + "</font>&nbsp;数量合计：<font color='red'>" + zjjson.SLHJ + "</font>&nbsp;当前页数量合计：<font color='red'>" + hjjson.SLHJ + "</font>");
		}else if(zjjson.YZHJ!=null){//原值（账与表）注意跟上边带有原值的合计信息的前后顺序
			$(".total").prop("innerHTML","原值合计：<font color='red'>" + zjjson.YZHJ + "</font>&nbsp;当前页原值合计：<font color='red'>" + hjjson.YZHJ + "</font>");
		}else{
			$(".total").prop("innerHTML","总价合计：<font color='red'>" + zjjson.ZZJHJ + "</font>&nbsp;当前页总价合计：<font color='red'>" + hjjson.ZZJHJ+"</font>");
		}
	}
}

var getPubErrorMsg = function(){
	return "抱歉，系统出现问题！";
};

/**************初始StringBuilder对象*************/
var StringBuilder = function(){
    this._list = [];
};
StringBuilder.prototype.Append = function(){
    if(typeof(arguments[0]) == "string")
        this._list.push(arguments[0]);
};
StringBuilder.prototype.ToString = function(){
    if(arguments.length == 1)
        return this._list.join(arguments[0]);
    return this._list.join("");
};
/**************初始StringBuilder对象*************/

/************ajax操作开始*************************/
/**
 * 列表页面批量操作
 * @param _msg，操作类型
 * @param _url
 * @param _success
 * @param _fail
 * @returns
 */
function doBatchDeal(_action,_url,_success,_fail,_async){
	var checkbox = $("#mydatatables").find(".keyId:checked");
	if(checkbox.length>0){
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
		doDeal(_action,_url,"guid="+guid.join("','"),_success,_fail,checkbox.length,_async)
	}else{
		alert("请选择至少一条信息"+_action+"!");
	}
}
/**
 * 列表页面导出excel
 * @param _msg，操作类型
 * @param _url
 * @param _success
 * @param _fail
 * @returns
 */
function doExportExcel(_url,_success,_fail,_async){
	var _action = "导出";
	var checkbox = $("#mydatatables").find(".keyId:checked");
	var guid = [];
	checkbox.each(function(){
		guid.push($(this).data("guid"));
	});
	if(checkbox.length==0){
		_action += "全部信息";
	}
	doDeal(_action,_url,"guid="+guid.join("','"),_success,_fail,checkbox.length,_async)
}
/**
 * 列表页面操作
 * @param _action	要做的操作，{"删除"}
 * @param _url	请求路径
 * @param _data	附带的数据
 * @param _success	成功后执行的方法
 * @param _fail	失败后执行的方法
 * @param _length	选中的消息长度
 * @param _async	是否异步，默认为：是
 * @returns
 */
function doDeal(_action,_url,_data,_success,_fail,_length,_async){
	_length = _length ? "选中的"+_length+"条信息" : "";
	_msg = "确定要"+_action+_length+"吗？";
	confirm(_msg,{title:"提示"},function(z){
		doAction(_url,_data,function(result){
			table.ajax.reload();
			_success(result);
		},_fail,_async);
	}); 
}
/**
 * 提示返回的信息的ajax操作
 * @param _url	请求路径
 * @param _data	附带的数据
 * @param _success	成功后执行的方法
 * @param _fail	失败后执行的方法
 * @param _async	请求是否异步，默认为是
 * @returns
 */
function doAction(_url,_data,_success,_fail,_async){
	_async = _async ? _async : true;
	var index;
	$.ajax({
		type:"post",
		data:_data,
		url:_url,
		dataType:"json",
		asygn:_async,
		success:function(result){
			close(index);
			alert(result.msg);
			if(result.success){
				if(_success){
					_success(result);
				}
			} else {
				if(_fail){
					_fail(result);
				}
			}
		},
		error:function(){
			close(index);
			alert(getPubErrorMsg());
		},
		beforeSend:function(){
			index = loading(2);
		}
	});
}
/**
 * 封装ajax post请求，返回的格式必须带有{"success":true}
 * @param _url	请求路径
 * @param _data	附带的数据
 * @param _success	成功后执行的方法
 * @param _fail	失败后执行的方法
 * @param _async	请求是否异步，默认为是
 * @returns
 */
function doPost(_url,_data,_success,_fail,_async){
	_async = _async ? _async : true;
	$.ajax({
		type:"post",
		data:_data,
		url:_url,
		dataType:"json",
		asygn:_async,
		success:function(result){
			if(result.success){
				if(_success){
					_success(result);
				}
			} else {
				if(_fail){
					_fail(result);
				}
			}
		},
		error:function(){
			alert(getPubErrorMsg());
		}
	});
}
/**
 * ajax，get方式获取数据
 * @param _url	请求路径
 * @param _data	附带的数据
 * @param _success	成功后执行的方法
 * @param _fail	失败后执行的方法
 * @param _async	请求是否异步，默认为是
 * @returns
 */
function doGetData(_url,_data,_success,_async){
	_async = _async ? _async : true;
	$.ajax({
		type:"get",
		data:_data,
		url:_url,
		dataType:"json",
		asygn:_async,
		success:function(result){
			_success(result);
		},
		error:function(){
			alert(getPubErrorMsg());
		}
	});
}
/***************ajax操作结束****************************/
/**
 * 页面跳转
 * @param _basePath	页面基准路径
 * @param _target	目标路径
 * @param _suffix	后缀
 * @param _operateType	操作类型{view,update,add,check}
 * @returns
 */
function doSkip(_url,_suffix, _operateType){
	_suffix = _suffix ? "&"+_suffix : "";
	_operateType = _operateType ? "&operateType="+_operateType : "";
	var mkbh = GetQueryString("mkbh");
	if(_url.indexOf("?") > 0){
		_url += "&mkbh=" + mkbh;
	} else {
		_url += "?mkbh=" + mkbh;
	}
	window.location.href = _url  + _operateType + _suffix;
}
/**************获取银行标志 alibabab *****************/
/**
 * 页面跳转
 * @param bandCard  银行卡号 6222005865412565805
 * @returns [object Object] {"bank":"ICBC","validated":true,"cardType":"DC","key":"6222005865412565805",
 *           "messages":[],"stat":"ok"}
 */
function bankCardAttribution(bankCard){
	var cardTypeMap = {
	DC: "储蓄卡",
	CC: "信用卡",
	SCC: "准贷记卡",
	PC: "预付费卡"
	};

	function extend(target, source) {
	var result = {};
	var key;
	target = target || {};
	source = source || {};
	for (key in target) {
	if (target.hasOwnProperty(key)) {
	result[key] = target[key];
	}
	}
	for (key in source) {
	if (source.hasOwnProperty(key)) {
	result[key] = source[key];
	}
	}
	return result;
	}

	function getCardTypeName(cardType) {
	if (cardTypeMap[cardType]) {
	return cardTypeMap[cardType]
	}
	return undefined;
	}
	var bankcardList = [{
	bankName: "中国邮政储蓄银行",
	bankCode: "PSBC",
	patterns: [{
	reg: /^(621096|621098|622150|622151|622181|622188|622199|955100|621095|620062|621285|621798|621799|621797|620529|621622|621599|621674|623218|623219)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(62215049|62215050|62215051|62218850|62218851|62218849)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622812|622810|622811|628310|625919)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "中国工商银行",
	bankCode: "ICBC",
	patterns: [{
	reg: /^(620200|620302|620402|620403|620404|620406|620407|620409|620410|620411|620412|620502|620503|620405|620408|620512|620602|620604|620607|620611|620612|620704|620706|620707|620708|620709|620710|620609|620712|620713|620714|620802|620711|620904|620905|621001|620902|621103|621105|621106|621107|621102|621203|621204|621205|621206|621207|621208|621209|621210|621302|621303|621202|621305|621306|621307|621309|621311|621313|621211|621315|621304|621402|621404|621405|621406|621407|621408|621409|621410|621502|621317|621511|621602|621603|621604|621605|621608|621609|621610|621611|621612|621613|621614|621615|621616|621617|621607|621606|621804|621807|621813|621814|621817|621901|621904|621905|621906|621907|621908|621909|621910|621911|621912|621913|621915|622002|621903|622004|622005|622006|622007|622008|622010|622011|622012|621914|622015|622016|622003|622018|622019|622020|622102|622103|622104|622105|622013|622111|622114|622017|622110|622303|622304|622305|622306|622307|622308|622309|622314|622315|622317|622302|622402|622403|622404|622313|622504|622505|622509|622513|622517|622502|622604|622605|622606|622510|622703|622715|622806|622902|622903|622706|623002|623006|623008|623011|623012|622904|623015|623100|623202|623301|623400|623500|623602|623803|623901|623014|624100|624200|624301|624402|623700|624000)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(622200|622202|622203|622208|621225|620058|621281|900000|621558|621559|621722|621723|620086|621226|621618|620516|621227|621288|621721|900010|623062|621670|621720|621379|621240|621724|621762|621414|621375|622926|622927|622928|622929|622930|622931|621733|621732|621372|621369|621763)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(402791|427028|427038|548259|621376|621423|621428|621434|621761|621749|621300|621378|622944|622949|621371|621730|621734|621433|621370|621764|621464|621765|621750|621377|621367|621374|621731|621781)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(9558)\d{15}$/g,
	cardType: "DC"
	}, {
	reg: /^(370246|370248|370249|370247|370267|374738|374739)\d{9}$/g,
	cardType: "CC"
	}, {
	reg: /^(427010|427018|427019|427020|427029|427030|427039|438125|438126|451804|451810|451811|458071|489734|489735|489736|510529|427062|524091|427064|530970|530990|558360|524047|525498|622230|622231|622232|622233|622234|622235|622237|622239|622240|622245|622238|451804|451810|451811|458071|628288|628286|622206|526836|513685|543098|458441|622246|544210|548943|356879|356880|356881|356882|528856|625330|625331|625332|622236|524374|550213|625929|625927|625939|625987|625930|625114|622159|625021|625022|625932|622889|625900|625915|625916|622171|625931|625113|625928|625914|625986|625925|625921|625926|625942|622158|625917|625922|625934|625933|625920|625924|625017|625018|625019)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(45806|53098|45806|53098)\d{11}$/g,
	cardType: "CC"
	}, {
	reg: /^(622210|622211|622212|622213|622214|622220|622223|622225|622229|622215|622224)\d{10}$/g,
	cardType: "SCC"
	}, {
	reg: /^(620054|620142|620184|620030|620050|620143|620149|620124|620183|620094|620186|620148|620185)\d{10}$/g,
	cardType: "PC"
	}, {
	reg: /^(620114|620187|620046)\d{13}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "中国农业银行",
	bankCode: "ABC",
	patterns: [{
	reg: /^(622841|622824|622826|622848|620059|621282|622828|622823|621336|621619|622821|622822|622825|622827|622845|622849|623018|623206|621671|622840|622843|622844|622846|622847|620501)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(95595|95596|95597|95598|95599)\d{14}$/g,
	cardType: "DC"
	}, {
	reg: /^(103)\d{16}$/g,
	cardType: "DC"
	}, {
	reg: /^(403361|404117|404118|404119|404120|404121|463758|519412|519413|520082|520083|552599|558730|514027|622836|622837|628268|625996|625998|625997|622838|625336|625826|625827|544243|548478|628269)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(622820|622830)\d{10}$/g,
	cardType: "SCC"
	}]
	}, {
	bankName: "中国银行",
	bankCode: "BOC",
	patterns: [{
	reg: /^(621660|621661|621662|621663|621665|621667|621668|621669|621666|456351|601382|621256|621212|621283|620061|621725|621330|621331|621332|621333|621297|621568|621569|621672|623208|621620|621756|621757|621758|621759|621785|621786|621787|621788|621789|621790|622273|622274|622771|622772|622770|621741|621041)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(621293|621294|621342|621343|621364|621394|621648|621248|621215|621249|621231|621638|621334|621395|623040|622348)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(625908|625910|625909|356833|356835|409665|409666|409668|409669|409670|409671|409672|512315|512316|512411|512412|514957|409667|438088|552742|553131|514958|622760|628388|518377|622788|628313|628312|622750|622751|625145|622479|622480|622789|625140|622346|622347)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(518378|518379|518474|518475|518476|524865|525745|525746|547766|558868|622752|622753|622755|524864|622757|622758|622759|622761|622762|622763|622756|622754|622764|622765|558869|625905|625906|625907|625333)\d{10}$/g,
	cardType: "SCC"
	}, {
	reg: /^(53591|49102|377677)\d{11}$/g,
	cardType: "SCC"
	}, {
	reg: /^(620514|620025|620026|620210|620211|620019|620035|620202|620203|620048|620515|920000)\d{10}$/g,
	cardType: "PC"
	}, {
	reg: /^(620040|620531|620513|921000|620038)\d{13}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "中国建设银行",
	bankCode: "CCB",
	patterns: [{
	reg: /^(621284|436742|589970|620060|621081|621467|621598|621621|621700|622280|622700|623211|623668)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(421349|434061|434062|524094|526410|552245|621080|621082|621466|621488|621499|622966|622988|622382|621487|621083|621084|620107)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(436742193|622280193)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(553242)\d{12}$/g,
	cardType: "CC"
	}, {
	reg: /^(625362|625363|628316|628317|356896|356899|356895|436718|436738|436745|436748|489592|531693|532450|532458|544887|552801|557080|558895|559051|622166|622168|622708|625964|625965|625966|628266|628366|622381|622675|622676|622677)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(5453242|5491031|5544033)\d{11}$/g,
	cardType: "CC"
	}, {
	reg: /^(622725|622728|436728|453242|491031|544033|622707|625955|625956)\d{10}$/g,
	cardType: "SCC"
	}, {
	reg: /^(53242|53243)\d{11}$/g,
	cardType: "SCC"
	}]
	}, {
	bankName: "中国交通银行",
	bankCode: "COMM",
	patterns: [{
	reg: /^(622261|622260|622262|621002|621069|621436|621335)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(620013)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(405512|601428|405512|601428|622258|622259|405512|601428)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(49104|53783)\d{11}$/g,
	cardType: "CC"
	}, {
	reg: /^(434910|458123|458124|520169|522964|552853|622250|622251|521899|622253|622656|628216|622252|955590|955591|955592|955593|628218|625028|625029)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(622254|622255|622256|622257|622284)\d{10}$/g,
	cardType: "SCC"
	}, {
	reg: /^(620021|620521)\d{13}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "招商银行",
	bankCode: "CMB",
	patterns: [{
	reg: /^(402658|410062|468203|512425|524011|622580|622588|622598|622609|95555|621286|621483|621485|621486|621299)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(690755)\d{9}$/g,
	cardType: "DC"
	}, {
	reg: /^(690755)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(356885|356886|356887|356888|356890|439188|439227|479228|479229|521302|356889|545620|545621|545947|545948|552534|552587|622575|622576|622577|622578|622579|545619|622581|622582|545623|628290|439225|518710|518718|628362|439226|628262|625802|625803)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(370285|370286|370287|370289)\d{9}$/g,
	cardType: "CC"
	}, {
	reg: /^(620520)\d{13}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "中国民生银行",
	bankCode: "CMBC",
	patterns: [{
	reg: /^(622615|622616|622618|622622|622617|622619|415599|421393|421865|427570|427571|472067|472068|622620)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(545392|545393|545431|545447|356859|356857|407405|421869|421870|421871|512466|356856|528948|552288|622600|622601|622602|517636|622621|628258|556610|622603|464580|464581|523952|545217|553161|356858|622623|625912|625913|625911)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(377155|377152|377153|377158)\d{9}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "中国光大银行",
	bankCode: "CEB",
	patterns: [{
	reg: /^(303)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(90030)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(620535)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(620085|622660|622662|622663|622664|622665|622666|622667|622669|622670|622671|622672|622668|622661|622674|622673|620518|621489|621492)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(356837|356838|486497|622657|622685|622659|622687|625978|625980|625981|625979|356839|356840|406252|406254|425862|481699|524090|543159|622161|622570|622650|622655|622658|625975|625977|628201|628202|625339|625976)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "中信银行",
	bankCode: "CITIC",
	patterns: [{
	reg: /^(433670|433680|442729|442730|620082|622690|622691|622692|622696|622698|622998|622999|433671|968807|968808|968809|621771|621767|621768|621770|621772|621773|622453|622456)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622459)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(376968|376969|376966)\d{9}$/g,
	cardType: "CC"
	}, {
	reg: /^(400360|403391|403392|404158|404159|404171|404172|404173|404174|404157|433667|433668|433669|514906|403393|520108|433666|558916|622678|622679|622680|622688|622689|628206|556617|628209|518212|628208|356390|356391|356392|622916|622918|622919)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "华夏银行",
	bankCode: "HXBANK",
	patterns: [{
	reg: /^(622630|622631|622632|622633|999999|621222|623020|623021|623022|623023)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(523959|528709|539867|539868|622637|622638|628318|528708|622636|625967|625968|625969)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "深发/平安银行",
	bankCode: "SPABANK",
	patterns: [{
	reg: /^(621626|623058)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(602907|622986|622989|622298|627069|627068|627066|627067|412963|415752|415753|622535|622536|622538|622539|998800|412962|622983)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(531659|622157|528020|622155|622156|526855|356869|356868|625360|625361|628296|435744|435745|483536|622525|622526|998801|998802)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(620010)\d{10}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "兴业银行",
	bankCode: "CIB",
	patterns: [{
	reg: /^(438589)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(90592)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(966666|622909|438588|622908)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(461982|486493|486494|486861|523036|451289|527414|528057|622901|622902|622922|628212|451290|524070|625084|625085|625086|625087|548738|549633|552398|625082|625083|625960|625961|625962|625963)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(620010)\d{10}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "上海银行",
	bankCode: "SHBANK",
	patterns: [{
	reg: /^(621050|622172|622985|622987|620522|622267|622278|622279|622468|622892|940021)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(438600)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(356827|356828|356830|402673|402674|486466|519498|520131|524031|548838|622148|622149|622268|356829|622300|628230|622269|625099|625953)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "浦东发展银行",
	bankCode: "SPDB",
	patterns: [{
	reg: /^(622516|622517|622518|622521|622522|622523|984301|984303|621352|621793|621795|621796|621351|621390|621792|621791)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(84301|84336|84373|84385|84390|87000|87010|87030|87040|84380|84361|87050|84342)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(356851|356852|404738|404739|456418|498451|515672|356850|517650|525998|622177|622277|628222|622500|628221|622176|622276|622228|625957|625958|625993|625831)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(622520|622519)\d{10}$/g,
	cardType: "SCC"
	}, {
	reg: /^(620530)\d{13}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "广发银行",
	bankCode: "GDB",
	patterns: [{
	reg: /^(622516|622517|622518|622521|622522|622523|984301|984303|621352|621793|621795|621796|621351|621390|621792|621791)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622568|6858001|6858009|621462)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(9111)\d{15}$/g,
	cardType: "DC"
	}, {
	reg: /^(406365|406366|428911|436768|436769|436770|487013|491032|491033|491034|491035|491036|491037|491038|436771|518364|520152|520382|541709|541710|548844|552794|493427|622555|622556|622557|622558|622559|622560|528931|558894|625072|625071|628260|628259|625805|625806|625807|625808|625809|625810)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(685800|6858000)\d{13}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "渤海银行",
	bankCode: "BOHAIB",
	patterns: [{
	reg: /^(621268|622684|622884|621453)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "广州银行",
	bankCode: "GCB",
	patterns: [{
	reg: /^(603445|622467|940016|621463)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "金华银行",
	bankCode: "JHBANK",
	patterns: [{
	reg: /^(622449|940051)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622450|628204)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "温州银行",
	bankCode: "WZCB",
	patterns: [{
	reg: /^(621977)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622868|622899|628255)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "徽商银行",
	bankCode: "HSBANK",
	patterns: [{
	reg: /^(622877|622879|621775|623203)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(603601|622137|622327|622340|622366)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(628251|622651|625828)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "江苏银行",
	bankCode: "JSBANK",
	patterns: [{
	reg: /^(621076|622173|622131|621579|622876)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(504923|622422|622447|940076)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(628210|622283|625902)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "南京银行",
	bankCode: "NJCB",
	patterns: [{
	reg: /^(621777|622305|621259)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622303|628242|622595|622596)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "宁波银行",
	bankCode: "NBBANK",
	patterns: [{
	reg: /^(621279|622281|622316|940022)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(621418)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(625903|622778|628207|512431|520194|622282|622318)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "北京银行",
	bankCode: "BJBANK",
	patterns: [{
	reg: /^(623111|421317|422161|602969|422160|621030|621420|621468)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(522001|622163|622853|628203|622851|622852)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "北京农村商业银行",
	bankCode: "BJRCB",
	patterns: [{
	reg: /^(620088|621068|622138|621066|621560)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(625526|625186|628336)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "汇丰银行",
	bankCode: "HSBC",
	patterns: [{
	reg: /^(622946)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622406|621442)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622407|621443)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622360|622361|625034|625096|625098)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "渣打银行",
	bankCode: "SCB",
	patterns: [{
	reg: /^(622948|621740|622942|622994)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622482|622483|622484)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "花旗银行",
	bankCode: "CITI",
	patterns: [{
	reg: /^(621062|621063)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(625076|625077|625074|625075|622371|625091)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "东亚银行",
	bankCode: "HKBEA",
	patterns: [{
	reg: /^(622933|622938|623031|622943|621411)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622372|622471|622472|622265|622266|625972|625973)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(622365)\d{11}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "广东华兴银行",
	bankCode: "GHB",
	patterns: [{
	reg: /^(621469|621625)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "深圳农村商业银行",
	bankCode: "SRCB",
	patterns: [{
	reg: /^(622128|622129|623035)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "广州农村商业银行股份有限公司",
	bankCode: "GZRCU",
	patterns: [{
	reg: /^(909810|940035|621522|622439)\d{12}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "东莞农村商业银行",
	bankCode: "DRCBCL",
	patterns: [{
	reg: /^(622328|940062|623038)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(625288|625888)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "东莞市商业银行",
	bankCode: "BOD",
	patterns: [{
	reg: /^(622333|940050)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(621439|623010)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622888)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "广东省农村信用社联合社",
	bankCode: "GDRCC",
	patterns: [{
	reg: /^(622302)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622477|622509|622510|622362|621018|621518)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "大新银行",
	bankCode: "DSB",
	patterns: [{
	reg: /^(622297|621277)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622375|622489)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622293|622295|622296|622373|622451|622294|625940)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "永亨银行",
	bankCode: "WHB",
	patterns: [{
	reg: /^(622871|622958|622963|622957|622861|622932|622862|621298)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622798|625010|622775|622785)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "星展银行香港有限公司",
	bankCode: "DBS",
	patterns: [{
	reg: /^(621016|621015)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622487|622490|622491|622492)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622487|622490|622491|622492|621744|621745|621746|621747)\d{11}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "恒丰银行",
	bankCode: "EGBANK",
	patterns: [{
	reg: /^(623078)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622384|940034)\d{11}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "天津市商业银行",
	bankCode: "TCCB",
	patterns: [{
	reg: /^(940015|622331)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(6091201)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622426|628205)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "浙商银行",
	bankCode: "CZBANK",
	patterns: [{
	reg: /^(621019|622309|621019)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(6223091100|6223092900|6223093310|6223093320|6223093330|6223093370|6223093380|6223096510|6223097910)\d{9}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "南洋商业银行",
	bankCode: "NCB",
	patterns: [{
	reg: /^(621213|621289|621290|621291|621292|621042|621743)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(623041|622351)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(625046|625044|625058|622349|622350)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(620208|620209|625093|625095)\d{10}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "厦门银行",
	bankCode: "XMBANK",
	patterns: [{
	reg: /^(622393|940023)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(6886592)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(623019|621600|)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "福建海峡银行",
	bankCode: "FJHXBC",
	patterns: [{
	reg: /^(622388)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(621267|623063)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(620043|)\d{12}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "吉林银行",
	bankCode: "JLBANK",
	patterns: [{
	reg: /^(622865|623131)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(940012)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622178|622179|628358)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "汉口银行",
	bankCode: "HKB",
	patterns: [{
	reg: /^(990027)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(622325|623105|623029)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "盛京银行",
	bankCode: "SJBANK",
	patterns: [{
	reg: /^(566666)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(622455|940039)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(623108|623081)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622466|628285)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "大连银行",
	bankCode: "DLB",
	patterns: [{
	reg: /^(603708)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622993|623069|623070|623172|623173)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622383|622385|628299)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "河北银行",
	bankCode: "BHB",
	patterns: [{
	reg: /^(622498|622499|623000|940046)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622921|628321)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "乌鲁木齐市商业银行",
	bankCode: "URMQCCB",
	patterns: [{
	reg: /^(621751|622143|940001|621754)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622476|628278)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "绍兴银行",
	bankCode: "SXCB",
	patterns: [{
	reg: /^(622486)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(603602|623026|623086)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(628291)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "成都商业银行",
	bankCode: "CDCB",
	patterns: [{
	reg: /^(622152|622154|622996|622997|940027|622153|622135|621482|621532)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "抚顺银行",
	bankCode: "FSCB",
	patterns: [{
	reg: /^(622442)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(940053)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(622442|623099)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "郑州银行",
	bankCode: "ZZBANK",
	patterns: [{
	reg: /^(622421)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(940056)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(96828)\d{11}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "宁夏银行",
	bankCode: "NXBANK",
	patterns: [{
	reg: /^(621529|622429|621417|623089|623200)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628214|625529|622428)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "重庆银行",
	bankCode: "CQBANK",
	patterns: [{
	reg: /^(9896)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(622134|940018|623016)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "哈尔滨银行",
	bankCode: "HRBANK",
	patterns: [{
	reg: /^(621577|622425)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(940049)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(622425)\d{11}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "兰州银行",
	bankCode: "LZYH",
	patterns: [{
	reg: /^(622139|940040|628263)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(621242|621538|621496)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "青岛银行",
	bankCode: "QDCCB",
	patterns: [{
	reg: /^(621252|622146|940061|628239)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(621419|623170)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "秦皇岛市商业银行",
	bankCode: "QHDCCB",
	patterns: [{
	reg: /^(62249802|94004602)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(621237|623003)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "青海银行",
	bankCode: "BOQH",
	patterns: [{
	reg: /^(622310|940068)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622817|628287|625959)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(62536601)\d{8}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "台州银行",
	bankCode: "TZCB",
	patterns: [{
	reg: /^(622427)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(940069)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(623039)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622321|628273)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(625001)\d{10}$/g,
	cardType: "SCC"
	}]
	}, {
	bankName: "长沙银行",
	bankCode: "CSCB",
	patterns: [{
	reg: /^(694301)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(940071|622368|621446)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(625901|622898|622900|628281|628282|622806|628283)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(620519)\d{13}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "泉州银行",
	bankCode: "BOQZ",
	patterns: [{
	reg: /^(683970|940074)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(622370)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(621437)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628319)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "包商银行",
	bankCode: "BSB",
	patterns: [{
	reg: /^(622336|621760)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622165)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622315|625950|628295)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "龙江银行",
	bankCode: "DAQINGB",
	patterns: [{
	reg: /^(621037|621097|621588|622977)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(62321601)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622860)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622644|628333)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "上海农商银行",
	bankCode: "SHRCB",
	patterns: [{
	reg: /^(622478|940013|621495)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(625500)\d{10}$/g,
	cardType: "SCC"
	}, {
	reg: /^(622611|622722|628211|625989)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "浙江泰隆商业银行",
	bankCode: "ZJQL",
	patterns: [{
	reg: /^(622717)\d{10}$/g,
	cardType: "SCC"
	}, {
	reg: /^(628275|622565|622287)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "内蒙古银行",
	bankCode: "H3CB",
	patterns: [{
	reg: /^(622147|621633)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628252)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "广西北部湾银行",
	bankCode: "BGB",
	patterns: [{
	reg: /^(623001)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(628227)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "桂林银行",
	bankCode: "GLBANK",
	patterns: [{
	reg: /^(621456)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(621562)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628219)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "龙江银行",
	bankCode: "DAQINGB",
	patterns: [{
	reg: /^(621037|621097|621588|622977)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(62321601)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622475|622860)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(625588)\d{10}$/g,
	cardType: "SCC"
	}, {
	reg: /^(622270|628368|625090|622644|628333)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "成都农村商业银行",
	bankCode: "CDRCB",
	patterns: [{
	reg: /^(623088)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622829|628301|622808|628308)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "福建省农村信用社联合社",
	bankCode: "FJNX",
	patterns: [{
	reg: /^(622127|622184|621701|621251|621589|623036)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628232|622802|622290)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "天津农村商业银行",
	bankCode: "TRCB",
	patterns: [{
	reg: /^(622531|622329)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622829|628301)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "江苏省农村信用社联合社",
	bankCode: "JSRCU",
	patterns: [{
	reg: /^(621578|623066|622452|622324)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622815|622816|628226)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "湖南农村信用社联合社",
	bankCode: "SLH",
	patterns: [{
	reg: /^(622906|628386|625519|625506)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "江西省农村信用社联合社",
	bankCode: "JXNCX",
	patterns: [{
	reg: /^(621592)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(628392)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "商丘市商业银行",
	bankCode: "SCBBANK",
	patterns: [{
	reg: /^(621748)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628271)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "华融湘江银行",
	bankCode: "HRXJB",
	patterns: [{
	reg: /^(621366|621388)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628328)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "衡水市商业银行",
	bankCode: "HSBK",
	patterns: [{
	reg: /^(621239|623068)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "重庆南川石银村镇银行",
	bankCode: "CQNCSYCZ",
	patterns: [{
	reg: /^(621653004)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "湖南省农村信用社联合社",
	bankCode: "HNRCC",
	patterns: [{
	reg: /^(622169|621519|621539|623090)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "邢台银行",
	bankCode: "XTB",
	patterns: [{
	reg: /^(621238|620528)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "临汾市尧都区农村信用合作联社",
	bankCode: "LPRDNCXYS",
	patterns: [{
	reg: /^(628382|625158)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "东营银行",
	bankCode: "DYCCB",
	patterns: [{
	reg: /^(621004)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(628217)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "上饶银行",
	bankCode: "SRBANK",
	patterns: [{
	reg: /^(621416)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(628217)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "德州银行",
	bankCode: "DZBANK",
	patterns: [{
	reg: /^(622937)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628397)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "承德银行",
	bankCode: "CDB",
	patterns: [{
	reg: /^(628229)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "云南省农村信用社",
	bankCode: "YNRCC",
	patterns: [{
	reg: /^(622469|628307)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "柳州银行",
	bankCode: "LZCCB",
	patterns: [{
	reg: /^(622292|622291|621412)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(622880|622881)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(62829)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "威海市商业银行",
	bankCode: "WHSYBANK",
	patterns: [{
	reg: /^(623102)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(628234)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "湖州银行",
	bankCode: "HZBANK",
	patterns: [{
	reg: /^(628306)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "潍坊银行",
	bankCode: "BANKWF",
	patterns: [{
	reg: /^(622391|940072)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(628391)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "赣州银行",
	bankCode: "GZB",
	patterns: [{
	reg: /^(622967|940073)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628233)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "日照银行",
	bankCode: "RZGWYBANK",
	patterns: [{
	reg: /^(628257)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "南昌银行",
	bankCode: "NCB",
	patterns: [{
	reg: /^(621269|622275)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(940006)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(628305)\d{11}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "贵阳银行",
	bankCode: "GYCB",
	patterns: [{
	reg: /^(622133|621735)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(888)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628213)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "锦州银行",
	bankCode: "BOJZ",
	patterns: [{
	reg: /^(622990|940003)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(628261)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "齐商银行",
	bankCode: "QSBANK",
	patterns: [{
	reg: /^(622311|940057)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(628311)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "珠海华润银行",
	bankCode: "RBOZ",
	patterns: [{
	reg: /^(622363|940048)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628270)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "葫芦岛市商业银行",
	bankCode: "HLDCCB",
	patterns: [{
	reg: /^(622398|940054)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "宜昌市商业银行",
	bankCode: "HBC",
	patterns: [{
	reg: /^(940055)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622397)\d{11}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "杭州商业银行",
	bankCode: "HZCB",
	patterns: [{
	reg: /^(603367|622878)\d{12}$/g,
	cardType: "DC"
	}, {
	reg: /^(622397)\d{11}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "苏州市商业银行",
	bankCode: "JSBANK",
	patterns: [{
	reg: /^(603506)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "辽阳银行",
	bankCode: "LYCB",
	patterns: [{
	reg: /^(622399|940043)\d{11}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "洛阳银行",
	bankCode: "LYB",
	patterns: [{
	reg: /^(622420|940041)\d{11}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "焦作市商业银行",
	bankCode: "JZCBANK",
	patterns: [{
	reg: /^(622338)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(940032)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "镇江市商业银行",
	bankCode: "ZJCCB",
	patterns: [{
	reg: /^(622394|940025)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "法国兴业银行",
	bankCode: "FGXYBANK",
	patterns: [{
	reg: /^(621245)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "大华银行",
	bankCode: "DYBANK",
	patterns: [{
	reg: /^(621328)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "企业银行",
	bankCode: "DIYEBANK",
	patterns: [{
	reg: /^(621651)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "华侨银行",
	bankCode: "HQBANK",
	patterns: [{
	reg: /^(621077)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "恒生银行",
	bankCode: "HSB",
	patterns: [{
	reg: /^(622409|621441)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622410|621440)\d{11}$/g,
	cardType: "DC"
	}, {
	reg: /^(622950|622951)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(625026|625024|622376|622378|622377|625092)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "临沂商业银行",
	bankCode: "LSB",
	patterns: [{
	reg: /^(622359|940066)\d{13}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "烟台商业银行",
	bankCode: "YTCB",
	patterns: [{
	reg: /^(622886)\d{10}$/g,
	cardType: "DC"
	}]
	}, {
	bankName: "齐鲁银行",
	bankCode: "QLB",
	patterns: [{
	reg: /^(940008|622379)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(628379)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "BC卡公司",
	bankCode: "BCCC",
	patterns: [{
	reg: /^(620011|620027|620031|620039|620103|620106|620120|620123|620125|620220|620278|620812|621006|621011|621012|621020|621023|621025|621027|621031|620132|621039|621078|621220|621003)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(625003|625011|625012|625020|625023|625025|625027|625031|621032|625039|625078|625079|625103|625106|625006|625112|625120|625123|625125|625127|625131|625032|625139|625178|625179|625220|625320|625111|625132|625244)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "集友银行",
	bankCode: "CYB",
	patterns: [{
	reg: /^(622355|623042)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(621043|621742)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(622352|622353|625048|625053|625060)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(620206|620207)\d{10}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "大丰银行",
	bankCode: "TFB",
	patterns: [{
	reg: /^(622547|622548|622546)\d{13}$/g,
	cardType: "DC"
	}, {
	reg: /^(625198|625196|625147)\d{10}$/g,
	cardType: "CC"
	}, {
	reg: /^(620072)\d{13}$/g,
	cardType: "PC"
	}, {
	reg: /^(620204|620205)\d{10}$/g,
	cardType: "PC"
	}]
	}, {
	bankName: "AEON信贷财务亚洲有限公司",
	bankCode: "AEON",
	patterns: [{
	reg: /^(621064|622941|622974)\d{10}$/g,
	cardType: "DC"
	}, {
	reg: /^(622493)\d{10}$/g,
	cardType: "CC"
	}]
	}, {
	bankName: "澳门BDA",
	bankCode: "MABDA",
	patterns: [{
	reg: /^(621274|621324)\d{13}$/g,
	cardType: "DC"
	}]
	}]

	function getBankNameByBankCode(bankcode) {
	for (var i = 0, len = bankcardList.length; i < len; i++) {
	var bankcard = bankcardList[i];
	if (bankcode == bankcard.bankCode) {
	return bankcard.bankName;
	}
	}
	return "";
	}

	function _getBankInfoByCardNo(cardNo) {
	for (var i = 0, len = bankcardList.length; i < len; i++) {
	var bankcard = bankcardList[i];
	var patterns = bankcard.patterns;
	for (var j = 0, jLen = patterns.length; j < jLen; j++) {
	var pattern = patterns[j];
	if ((new RegExp(pattern.reg)).test(cardNo)) {
	var info = extend(bankcard, pattern);
	delete info.patterns;
	delete info.reg;
	info['cardTypeName'] = getCardTypeName(info['cardType']);
	return info;//返回银行卡结果
	}
	}
	}
	return 'error';
	}

	//return _getBankInfoByCardNo( bankCard ) 这是返回的对象  类型
	//根据需求 返回key为bank 的 value  银行标志符  CCB 建行
	var bankCodeArr = bankCard.split(",");
	var bankCode = [];
	var size = bankCodeArr.length;
	for(var i=0; i<size; i++){
		var message = _getBankInfoByCardNo( bankCodeArr[i] )
	    message = JSON.stringify(message);
	   var jsonM =  JSON.parse(message);
	   var bankCodei = jsonM.bankCode;
	   bankCode.push(bankCodei);
	}
   	return bankCode;
	}




