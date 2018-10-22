	var miniMenuWidth = 50;
	var commonMenuWidth = 210;
	$(function(){
	//解析json数据生成节点树
	$.ajaxSettings.async = false; 
	var contextPath = $("#contextPath").val();//项目根路径
	var json2 = null;
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
		var mgid = $(this).data("mgid");
		var classes = $(this).data("classes");
		var mname = $(this).data("name");
		var leaf = $(this).data("leaf");
		$(this).parents("#first-menu").find("li.active").removeClass("active");
		$(this).parent().addClass("active");
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
						
						var icon = $('<i>', {class: menu[i].ISLEAF ? "faw fa-dot-circle-o" : (menu[i].MCLASS == "icon-cogs" ? classes : menu[i].MCLASS)});
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
	});
});
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
