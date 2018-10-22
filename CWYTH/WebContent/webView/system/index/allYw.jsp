<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>业务地图</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.box{
		margin: 0px;
	}
	body{
		font-size: 13px;
	}
 	#d_all{ 
 		width: 99.82%; 
 	} 
</style>
</head>
<body>
    <div id="d_all" class="fullscreen"></div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//解析json数据展示各业务功能
	var contextPath = "${ctx}";
	$.ajaxSettings.async = false; 
	$.getJSON("${ctx}/index/getMenuListnew", function(json){
		$.each(json, function(i, item){//循环一级菜单
			var $item = item;
			var mname = item.MNAME;
			var html = "<div class='box'>";
				html += "<div class='box-panel'>";
					html += "<div class='box-header clearfix'>";
						html += "<div class='sub-title pull-left text-primary'>&emsp;"+ mname +"</div>";
						html += "<div class='actions'>";
							html += "<a href='javascript:void(0)' class='btn box-fold btn-mini btn-link'><i class='faw fa-angle-up'></i></a>";
						html += "</div>";
					html += "</div>";
					html += "<hr class='hr-normal'>";
					html += "<div class='container-fluid box-content'>";
						$item = $item.children;
						$.each($item, function(i, item){//循环二级菜单
							$item = item;
							html += "<div class='row' style='padding-top: 5px;padding-bottom: 5px;'>";
								if(item.MGID == '060901'){//使用人与所在单位不符的资产浏览
									html += "<div class='col-md-3 item'>&emsp;";
								}else{
									html += "<div class='col-md-2 item'>&emsp;";
								}
										html += item.MNAME;
									html += "</div>";
								if($item.ISLEAF && $item.MGID.length == 6) {//当二级菜单下只有一个三级菜单时
									mname = item.MNAME;
									var mclass =  item.MCLASS;
									var mgid = item.MGID;
									var isleaf = item.ISLEAF;
									html += "<div class='col-md-2 item'>";
										html += "<a style='border: 1px solid #eee;padding: 4px;' href='javascript:void(0);' data-name="+ mname +" data-classes="+ mclass +" class='goMain menuList' data-title="+ $item.MNAME +" ifrurl="+ ( contextPath + $item.MURL ) +" ifrid="+ $item.MGID +" data-leaf=" + isleaf + " data-mgid="+ mgid +">"+ mname +"</a>";
									html += "</div>";
								}else{
									$item = $item.children;
									$.each($item, function(i, item){//循环三级菜单
										$item = item;
										mname = item.MNAME;
										var mclass =  item.MCLASS;
										var mgid = item.MGID;
										var isleaf = item.ISLEAF;
										html += "<div class='col-md-2 item'>";
											html += "<a style='border: 1px solid #eee;padding: 4px;' href='javascript:void(0);' data-name="+ mname +" data-classes="+ mclass +" class='goMain menuList' data-title="+ $item.MNAME +" ifrurl="+ ( contextPath + $item.MURL ) +" ifrid="+ $item.MGID +" data-leaf=" + isleaf + " data-mgid="+ mgid +">"+ mname +"</a>";
										html += "</div>";
										//综合分析和参数设置有换行
										if(mgid.substring(0,4) == '0708' || mgid.substring(0,4) == '9901'){
											if(i == 4 || i == 9){
												html += "</div>";//换行时row闭合
												html += "<div class='row' style='padding-top: 5px;padding-bottom: 5px;'>";//换行时row开始
												html += "<div class='col-md-2 item'></div>";//换行时空出第一列二级菜单的位置
											}
										}
									});
								}
							html += "</div>";//row闭合
						});
					html += "</div>";//box-content闭合
				html += "</div>";//box-panel闭合
			html += "</div>";//box闭合
			$("#d_all").append(html);
		});
	});
	$.ajaxSettings.async = true;
	//进入每个业务功能
	 $("a").not($(".btn")).not($(".reload")).click(function(){
		 openRightWin($(this).attr("ifrurl"),$(this).attr("ifrid"),$(this).attr("data-name"));
	 });
	//刷新按钮
	$(".reload").click(function(){
		var url = window.location.href;
		console.log(url);
    	if(url.indexOf("?")>0){
    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016";
    	}else{
    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016";
    	}
	});
	//一级标题右侧小箭头的折叠展示功能
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
});
var openRightWin = function(_url,_id,title){
	console.log(_url);
	_url = _url.indexOf("?")>0?_url+"&mkbh="+_id:_url+"?mkbh="+_id;
	//循环遍历选项卡
	if(parent.window.$(".J_menuTab").each(function(){
		return $(this).data("id")==(_url)?($(this).hasClass("active")||($(this).addClass("active").siblings(".J_menuTab").removeClass("active"),
			parent.e(this),parent.window.$(".J_mainContent .J_iframe").each(
				function(){
					return $(this).data("id")==(_url)?($(this).show().siblings(".J_iframe").hide(),!1):void 0
				}
			)),parent.window.$(".J_mainContent").find("#iframe_"+_id+"").attr("src",""+_url+""),refunc(),openRightWin=!1,!1):void 0;
		}
	)){
		var s = '<a href="javascript:;" class="J_menuTab active" data-id="'+_url+'">'+title+' <i class="fa icon-xjifyunsuanchenghao2"></i></a>';
		parent.window.$(".J_menuTab").removeClass("active");
		var r = '<iframe class="J_iframe" name="iframe_'+_id+'" id="iframe_'+_id+'" width="100%" height="100%" src="'+_url+'" frameborder="0" data-id="'+_url+'" seamless></iframe>';
		parent.window.$(".J_mainContent").find("iframe.J_iframe").hide().parents(".J_mainContent").append(r);
		parent.window.$(".J_mainContent").find("#iframe0").attr("ss","2222");
		var o = parent.window.layer.load();
		parent.window.$(".J_mainContent iframe:visible").load(
			function(){
				parent.window.layer.close(o)
			}),
		parent.window.$(".J_menuTabs .page-tabs-content").append(s),parent.e(parent.window.$(".J_menuTab.active"));
	}
	return !1;	
}
</script>
</html>