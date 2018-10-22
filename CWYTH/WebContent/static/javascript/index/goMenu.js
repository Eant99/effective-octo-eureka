	var miniMenuWidth = 50;
	var commonMenuWidth = 210;
	var json2 = null;
	var contextPath = $("#contextPath").val();//项目根路径
	$(function(){
	//解析json数据生成节点树
//	var contextPath = getContextPath();//项目根路径
	$.ajaxSettings.async = false; 
//	$.getJSON("../index/getMenuListnew", function(json){
	$.getJSON(contextPath+"/index/getMenuListnew", function(json){
		json2 = json;
		$.each(json, function(i, item){
			var $item = item;
			while(!$item.ISLEAF) {
				$item = $item.children[0];
			}
			var mname = item.MNAME;
			var mclass =  item.MCLASS;
			var mgid = item.MGID;
			var isleaf = item.ISLEAF;
			$("#first-menu").append('<li><a href="javascript:void(0);" data-name="'+ mname +'" data-classes="'+ mclass +'" class="goMain menuList" data-title="'+ $item.MNAME +'" ifrurl="'+ ( contextPath + $item.MURL ) +'" ifrid="'+ $item.MGID +'" data-leaf=' + isleaf + ' data-mgid="'+ mgid +'">'+ mname +'</a></li>');
		});
	});
	$.ajaxSettings.async = true;
	//导航栏缩进事件
	$(".navbar-minimalize").click(function(){
		scrollMenu();
	});
	$(document).on("click", ".goMain", function() {
		kz($(this));
		n($(this));
	});
});
	function kz($this){
		var mgid = $this.data("mgid");
		var classes = $this.data("classes");
		var mname = $this.data("name");
		var leaf = $this.data("leaf");
//		$this.parents("#first-menu").find("li.active").removeClass("active");
//		$this.parent().addClass("active");
		$(".J_tabCloseAll").click();
		if(mgid === "00" || leaf) {
			$(".main-sidebar:visible").hide();
			$("#page-wrapper").css("margin-left", "0");
			(mgid === "00") && $(".page-tabs-content .J_menuTab:first-child").click();
			$("#page-wrapper").removeClass("page-wrapperLeft");
		} else { 
			$(".main-sidebar:hidden").show();
			var w = $(".sidebar").width();
			w == miniMenuWidth ? $("#page-wrapper").css("margin-left", miniMenuWidth + "px") : $("#page-wrapper").css("margin-left", commonMenuWidth + "px");
			$("#js_icon").attr("class", classes);
			$("#js_firstname").text(mname);
			$.each(json2, function(i, item) {
				var $item = item;
				if($item.MGID == mgid && !$item.ISLEAF) {
					$item = $item.children;
					$("#menu-left").html(recursive( $item, 1 ));
				}
				function recursive( menu, index ) {
					var ul = $("<ul>", {class: index == 1 ? "sidebar-menu" : "treeview-menu"});
					for( var i = 0; i < menu.length; i++ ) {
						//var icon = $('<i>', {class: menu[i].ISLEAF ? "faw fa-dot-circle-o" : "faw fa-cube"});
						
//						var icon = $('<i>', {class: menu[i].ISLEAF ? "faw fa-dot-circle-o" : (menu[i].MCLASS == "icon-cogs" ? classes : menu[i].MCLASS)});
						var icon = $('<i>',{class: menu[i].MCLASS});
						var name = $('<span>' + menu[i].MNAME + '</span>');
						var arrow = $('<span class="pull-right-container"><i class="faw fa-angle-left pull-right"></i></span>');
						var link = $('<a>', {
							class: menu[i].ISLEAF ? "menuList" : "", 
							href: "javascript:void(0);", 
							ifrid: menu[i].MGID,
							ifrurl: contextPath + menu[i].MURL, 
							title: menu[i].MNAME
						});
						link.append(icon).append(name).append(menu[i].ISLEAF ? null : arrow);
						var li = $('<li>', {class: (index == 1 && menu[i].ISLEAF ? "" : index == 1 ? "treeview" : "") + (!menu[i].ISLEAF && i == 0 ? " active" : "")}).append(link);
						if(!menu[i].ISLEAF) {
							li.append(recursive( menu[i].children, index+1 ));
						}
						ul.append(li);
					}
					return ul;
				}
			});
			if(w == miniMenuWidth) {
				$("#page-wrapper").addClass("page-wrapperLeft");
				$(".metismenu > ul ul li a").css("padding-left", "12px");
			}
		}
	}
	//返回首页按钮
	$(document).on("click",".goBackIndex",function(){
		$(".main-sidebar:visible").hide();
		$("#page-wrapper").css("margin-left", "0");
		var mgid = "00";
		(mgid === "00") && $(".page-tabs-content .J_menuTab:first-child").click();
		$("#page-wrapper").removeClass("page-wrapperLeft");
		//js控制首页的显示速度快，但是页面不刷新
		$("#content-main").find("iframe").css("display","none");
		$("#content-main").find("iframe:first-child").css("display","block");
		//location.href = contextPath+"/index/login_toIndex";通过刷新页面控制首页的显示，速度慢
	});
	//点击办事大厅，弹出右侧的页面；
	function n($this){
		var t=$this.attr("ifrurl"),
		a=$this.attr("ifrid"),
		t = t.indexOf("?")>0?t+"&mkbh="+a:t+"?mkbh="+a,
		i=$.trim($this.attr("title")||$this.data("title")),
		n=!0;
		if(t.indexOf("shMain.jsp") > 0){
			$("a.J_menuTab").each(function(){
				if($this.attr("data-id").indexOf("shMain.jsp") > 0) {
					$this.remove();
					$("#iframe_"+a).remove();
				}
			});
		}
		if(void 0==t||0==$.trim(t).length)
			return !1;
		//循环遍历选项卡
		if($(".J_menuTab").each(
			function(){
				return $this.data("id")==(t)?($this.hasClass("active")||($this.addClass("active").siblings(".J_menuTab").removeClass("active"),
					e(this),$(".J_mainContent .J_iframe").each(
						function(){
							return $this.data("id")==(t)?(
									$this.show().siblings(".J_iframe").hide(),!1):void 0
						}
					)),$(".J_mainContent").find("#iframe_"+a+"").attr("src",""+t+""),refunc(),n=!1,!1):void 0
			}),n)
		{
			//未打开时
			var s='<a href="javascript:;" class="active J_menuTab" data-id="'+t+'">'+i+' <i class="fa icon-xjifyunsuanchenghao2"></i></a>';//
			$(".J_menuTab").removeClass("active");
			var r='<iframe class="J_iframe" name="iframe_'+a+'" id="iframe_'+a+'" width="100%" height="100%" src="'+t+'" frameborder="0" data-id="'+t+'" seamless></iframe>';
			$(".J_iframe").hide();
			//如果最后一个iframe不是首页，则移出该frame
			if($("#content-main").children().last().attr("id") != "iframe0"){
				$("#content-main").children().last().remove();
			};
			$("#content-main").append(r);				
			//20180727注释，可能会导致页面一直等待
			layer.load(2);
			$(".J_mainContent iframe:visible").load(
				function(){
					layer.closeAll('loading');
				});
			$(".J_menuTabs .page-tabs-content").append(s);
			e($(".J_menuTab.active"));
		}
		return !1;
	}
	//跳转子页面的方法
	//打开新标签页面功能函数
	function goChildPage(child){
		var $this = $("[ifrid='"+child+"']");
		//取消其他菜单的展开项
		$(".treeview,.menuList,.J_menuTab").removeClass("active");
		$(".menu-open").removeClass("active").css("display","none");
		var $li = $this.parents("li");
			$li.addClass("active");
		$this.addClass("active menuList").siblings(".J_menuTab");
//		$this.click();
		var t=$this.attr("ifrurl"),
//		t = t.replace("[object HTMLInputElement]","");
		a=$this.attr("ifrid"),
		t = t.indexOf("?")>0?t+"&mkbh="+a:t+"?mkbh="+a,
		i=$.trim($this.attr("title")||$this.data("title")),
		n=!0;
		if(t.indexOf("shMain.jsp") > 0){
			$("a.J_menuTab").each(function(){
				if($this.attr("data-id").indexOf("shMain.jsp") > 0) {
					$this.remove();
					$("#iframe_"+a).remove();
				}
			});
		}
		if(void 0==t||0==$.trim(t).length)
			return !1;
		//循环遍历选项卡
		if($(".J_menuTab").each(
			function(){
				return $this.data("id")==(t)?($this.hasClass("active")||($this.addClass("active").siblings(".J_menuTab").removeClass("active"),
					e(this),$(".J_mainContent .J_iframe").each(
						function(){
							return $this.data("id")==(t)?(
									$this.show().siblings(".J_iframe").hide(),!1):void 0
						}
					)),$(".J_mainContent").find("#iframe_"+a+"").attr("src",""+t+""),refunc(),n=!1,!1):void 0
			}),n)
		{
			//未打开时
			var s='<a href="javascript:;" class="active J_menuTab" data-id="'+t+'">'+i+' <i class="fa icon-xjifyunsuanchenghao2"></i></a>';
			$(".J_menuTab").removeClass("active");
			var r='<iframe class="J_iframe" name="iframe_'+a+'" id="iframe_'+a+'" width="100%" height="100%" src="'+t+'" frameborder="0" data-id="'+t+'" seamless></iframe>';
//			$("#content-main").children().last().remove();
			$("#content-main").append(r);
			$(".J_mainContent").find("#iframe0").attr("ss","2222");
			//只显示选择的菜单，其他的菜单全部隐藏
			$(".J_mainContent").find("[name!=iframe_"+child+"]").css("display","none");
			//20180727注释，可能会导致页面一直等待
			layer.load(2);
			$(".J_mainContent iframe:visible").load(
				function(){
					layer.closeAll('loading');
			});
			$(".J_menuTabs .page-tabs-content").append(s);
			e($(".J_menuTab.active"));
			
		}
		return !1;
	}
//导航栏缩进效果函数
function  scrollMenu(){
	var w = $(".sidebar").width();
	if(w == miniMenuWidth) {
		$("body").removeClass("sidebar-collapse");
		$(".first-menu-item > span").removeClass("first-menu-itemH");
		$(".menu-scroll").show();
		$("#page-wrapper").removeClass("page-wrapperLeft");
		$("#page-wrapper").css("margin-left", commonMenuWidth + "px");
		$("#popover i.fa").removeClass("icon-openmenu").addClass("icon-closemenu");
		setTimeout('$(".fast-navigation").find("li").eq(0).show();$(".fast-navigation").find("li").eq(1).show();', '350');
		$(".fast-navigation").find("li").eq(2).attr("data-title", "点击折叠菜单");
		
		//设置菜单距离左边的距离
		$(".sidebar-menu .treeview-menu").css("padding-left","15px");
	} else {
		$("body").addClass("sidebar-collapse");
		$(".fast-navigation").find("li").eq(0).hide();
		$(".fast-navigation").find("li").eq(1).hide();
		$(".fast-navigation").find("li").eq(2).attr("data-title", "点击展开菜单");
		$(".first-menu-item>span").addClass("first-menu-itemH");
		$(".first-menu-title i").css("left", "5px");
		$("#page-wrapper").addClass("page-wrapperLeft");
		$("#popover i.fa").removeClass("icon-closemenu").addClass("icon-openmenu");

		//设置菜单距离左边的距离
		$(".sidebar-menu .treeview-menu").css("padding-left","0px");
	}
}
