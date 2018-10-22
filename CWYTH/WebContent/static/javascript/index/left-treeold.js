$(function(){
	//解析json数据生成节点树
	$.ajaxSettings.async = false; 
	var contextPath = $("#contextPath").val();//项目根路径
	$.getJSON("../index/getMenuList",function(json) {
		$.each(json,function(i,item){
			var mgid = item.MGID;
			var mname = item.MNAME;
			var mparent = item.MPARENT;
			var mclass = item.MCLASS;
//			var mfix = item.MFIX;  
			var murl = item.MURL;
			var level = item.LEVELS;
			var isleaf = item.ISLEAF;
			var li;
			//一级菜单，没有url的最顶级菜单
			if(level == "1") {
				li = '<li class="first-menu-item"><span class="first-menu-title" title="' + mname + '">';
				li += '<i class="' + mclass + '"></i>' + mname + '</span>';
				li += '<div class="first-menu-panel"><h4 class="first-menu-panel-title">' + mname + '</h4>';
				li += '<ul class="first-menu" firstid="' + mgid + '"></ul>';
				$(".menu-item").append(li);
			}
			//二级菜单
			if(level == "2") {
				if(isleaf == "1") {
					//一级菜单下面的不可展开的菜单 ifrfix="'+mfix+'"
					li = '<li class="menulist" ifrid="' + mgid + '" ifrurl="' + murl + '" mclass="' + mclass + '" ';
					li += 'title="' + mname + '"><span>' + mname + '</span></li>';
				} else {
					//一级菜单下面的可展开的菜单
					li = '<li class="menuexpand"><a class="second-menu-panel-title">' + mname + '</a>';
					li += '<ul class="second-menu" secondid="' + mgid + '"></ul></li>';
				}
				$(".first-menu[firstid='"+mparent+"']").append(li);
			}
			//三级菜单
			if(level == "3") {
				if(isleaf == "1") {
					//一级菜单下面的不可展开的菜单    ifrfix="'+mfix+'"
					li = '<li class="menulist" ifrid="' + mgid + '" ifrurl="' + contextPath + murl + '" mclass="' + mclass + '" ';
					li += 'title="' + mname + '"><span>' + mname + '</span></li>';
				} else {
					//一级菜单下面的可展开的菜单
					li = '<li class="menuexpand"><a class="third-menu-panel-title">' + mname + '</a>';
					li += '<ul class="third-menu" thirdid="' + mgid + '"></ul></li>';
				}
				$(".second-menu[secondid='" + mparent + "']").append(li);
			}
			//四级菜单
			if(level == "4") {
				//三级菜单下的不可展开菜单        ifrfix="'+mfix+'" 
				li = '<li class="menulist" ifrid="' + mgid + '" ifrurl="' + murl + '" mclass="' + mclass + '" ';
				li += 'title="' + mname + '"><span>' + mname + '</span></li>';
				//此处不可再添加下级菜单
				$(".third-menu[thirdid='" + mparent + "']").append(li);
			}
		});
	});
	$.ajaxSettings.async = true;
	//导航栏缩进事件
	$(".navbar-minimalize").click(function() {
		scrollMenu();
	});
});
//导航栏缩进效果函数
function  scrollMenu(){
	var w = $(".left").width();
	if(w=='49') {
		$(".left").css("width","200px");
		$(".fast-navigation").find("li").eq(0).show();
		$(".fast-navigation").find("li").eq(1).show();
		$(".fast-navigation").find("li").eq(2).css("margin-left","40px");
		$(".fast-navigation").find("li").eq(2).attr("data-title","点击折叠菜单");
		$(".first-menu-item>span").removeClass("first-menu-itemH");
		$(".menu-scroll").show();
		$("#page-wrapper").removeClass("page-wrapperLeft");
		$(".first-menu-panel").removeClass("first-menu-panelLeft");
		$("#popover i.fa").removeClass("icon-openmenu").addClass("icon-closemenu");
	} else {
		$(".left").css("width","50px");
		$(".fast-navigation").find("li").eq(0).hide();
		$(".fast-navigation").find("li").eq(1).hide();
		$(".fast-navigation").find("li").eq(2).css("margin-left","0");
		$(".fast-navigation").find("li").eq(2).attr("data-title","点击展开菜单");
		$(".first-menu-item>span").addClass("first-menu-itemH");
		$(".first-menu-title i").css("left","5px");
		$("#page-wrapper").addClass("page-wrapperLeft");
		$(".first-menu-panel").addClass("first-menu-panelLeft");
		$("#popover i.fa").removeClass("icon-closemenu").addClass("icon-openmenu");
	}
}
