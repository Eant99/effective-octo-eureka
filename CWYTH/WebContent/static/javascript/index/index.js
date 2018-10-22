var $panelOut;
var showTimer;
var hideTimer;
var APP_ITEM_HEIGHT = 38;
var SCROLL_HEIGHT = 4 * APP_ITEM_HEIGHT;
function showPanel($panelIn){
	$panelIn.find(".first-menu-title").addClass("first-menu-title-active");
	$panelIn.find(".first-menu-panel").css("display","block");
}
function hidePanel(){
	$panelOut = $(".first-menu-title-active").parent();
	$panelOut.find(".first-menu-title").removeClass("first-menu-title-active");
	$panelOut.find(".first-menu-panel").css("display","none");
}
$(document).ready(function(){
	$.ajaxSetup({
		type:"post"
	});
	var clientXAry = [];
	$(".menu-item").mousemove(function(e){
		clientXAry.push(e.clientX);
	});
	function setHeight(){
		var heightParent = $("#wrapper").outerHeight();
		var heightHeader = 50;
		//设置#menu_list的高度自适应
		$("#menu_list .left").height(heightParent - heightHeader);
	};
	setHeight();
	function setWidth() {
		var widthParent = $("#menu_list").width();
		var widthLeft = $("#menu_list .left").outerWidth(true);
		$("#menu_list .right").width(widthParent - widthLeft);
		$("#menu_list .right").css({"opacity":"1","filter":"Alpha(opacity=100)"});
	}
	setWidth();
	function seWidthMenuTop() {
		var widthParent = $(".header-inner").width();
		var widthBrand = $(".brand").outerWidth(true);
		var widthAce = $(".ace-nav").outerWidth(true);
		$(".menu-top").width(widthParent - widthBrand - widthAce - 100);
	}
	seWidthMenuTop();
	function setHeightMenu() {
		var heightParent = $("#menu_list .left").outerHeight();
		var heightTop = $("#menu_list .scroll-up").outerHeight(true);
		var heightBottom = $("#menu_list .scroll-down").outerHeight(true);
		//var heightNavigation = $("#menu_list .fast-navigation").outerHeight(true);
		$("#menu_list .left .menu-item").height(heightParent - heightTop - heightBottom);//- heightNavigation
		$(".fast-navigation").css({"opacity":"1","filter":"Alpha(opacity=100)"});
	}
	setHeightMenu();
	$(window).resize(function(){//屏幕自适应
		setHeight();
		setWidth();
		seWidthMenuTop();
		setHeightMenu();
	});
	$(document).on("mouseenter", ".first-menu-item", function(){
		$panelIn = $(this);
		if(showTimer){//如果timer不为undefined
			clearTimeout(showTimer);
		}
		if(clientXAry[clientXAry.length-1] > clientXAry[clientXAry.length-2]) {
			showTimer = setTimeout("showPanel($panelIn)", 500);
		} else {
			showPanel($panelIn);
		}
	});
	$(document).on("mouseleave", ".first-menu-item", function(e){
		if(hideTimer){//如果timer不为undefined
			clearTimeout(hideTimer);
		}
		clearTimeout(showTimer);
		if(clientXAry[clientXAry.length-1] > clientXAry[clientXAry.length-2] && e.clientX < 600){
			hideTimer = setTimeout(hidePanel,500);
		} else {
			hidePanel();
			clientXAry = [];
		}
	});
	/****菜单及导航*******/
	//点击菜单树某个菜单、桌面某个图标、.fast-navigation的某个按钮时，显示菜单
//	$(document).on("click",".menulist span,.screen-span,.fast-navigation i",function(){
//		showMenu($(this));
//	});
	//点击某个导航.menu-li时显示菜单
	$(document).on("click", ".menu-top ul .menu-li a", function() {
		var ifrId = $(this).parent().attr("ifrid");
		//将的样式为已选中的样式去除
		$(".menu-top ul .menu-li-active i").removeClass("close-active");
		$(".menu-top ul .menu-li-active").attr("class","menu-li");
		//给当前导航菜单添加样式
		$(this).parent().attr("class","menu-li-active");
		$(this).next().addClass("close-active");
		for(var i = 0; i < ifrIdAry.length; i++){
			$("#"+ifrIdAry[i]).css("display","none");
		}
		//将当前菜单对应的iframe设置为display:block;
		$("#"+ifrId).css("display","block");
	});
	//点击上方导航菜单的关闭按钮
	$(document).on("click", ".menu-top ul .close-active", function() {
		var ifrId = $(this).parent().attr("ifrid");
		var nextLength = $(this).parent().next().length;
		var thisIndex = 0;
		//visualWidth:可视宽度
		var visualWidth = $(".menu-nav").width();
		var thisWidth = $(this).parent().outerWidth(true);
		var thisLeft = $(this).parent().offset().left - $(".menu-nav").offset().left;
		var thisRight = visualWidth - thisLeft - thisWidth;
		//所有li的总长度
		var totalWidth = 0;
		$("[class^='menu-li']").each(function() {
			totalWidth += $(this).outerWidth(true);
		});
		$(this).parent().remove();
		$("#"+ifrId).remove();
		for(var i = 0; i < ifrIdAry.length; i++){
			if(ifrId == ifrIdAry[i]) {
				ifrIdAry.splice(i,1);
				thisIndex = i;
			}
		}
		var nextIfr;
		if(nextLength > 0) {
			nextIfr = ifrIdAry[thisIndex];
		} else {
			nextIfr = ifrIdAry[ifrIdAry.length - 1];
		}
		$(".menu-top ul [ifrid='"+nextIfr+"']").attr("class","menu-li-active");
		$(".menu-top ul [ifrid='"+nextIfr+"'] i").addClass("close-active");
		var afterWidth = 0;
		$("[class^='menu-li']:gt("+(thisIndex - 1)+")").each(function() {
			afterWidth += $(this).outerWidth(true);
		});
		var beginLeft = $(".menu-top ul").css("left") + "";
		if(beginLeft.indexOf("px") != -1) {
			beginLeft = beginLeft.substring(0, beginLeft.indexOf("px")) * "1";
		}
		//如果点击完关闭时li的总长度大于可视长度，根据情况对导航进行移动
		if(totalWidth > visualWidth) {
			if(totalWidth - visualWidth > thisWidth) {
				if(afterWidth < thisRight) {
					//向右移动
					$(".menu-top ul").css("left", beginLeft + thisRight - afterWidth + "px");
				} else if(afterWidth == thisRight) {
					$(".menu-top ul").css("left", beginLeft + thisWidth + "px");
				}
			} else {
				$(".menu-top ul").css("left", "0px");
				$(".left_scroll,.right_scroll").css("display","none");
			}
		}
		$("#"+nextIfr).css("display","block");
	});
	//点击左边按钮，向右滚动
	$(".left_scroll").click(function() {
		//最初的属性left
		var beginLeft = $(".menu-top ul").css("left") + "";
		if(beginLeft.indexOf("px")) {
			beginLeft = beginLeft.substring(0, beginLeft.indexOf("px")) * "1";
		}
		if(-beginLeft - 150 > 0) {
			$(".menu-top ul").animate({"left" : beginLeft + 150 + "px"}, 500);
		} else {
			$(".menu-top ul").animate({"left" : "0px"}, 500);
		}
	});
	//点击右边按钮，向左滚动
	$(".right_scroll").click(function() {
		var visualWidth = $(".menu-nav").width();
		var totalWidth = 0;
		$("[class^='menu-li']").each(function() {
			totalWidth += $(this).outerWidth(true);
		});
		//最初的属性left
		var beginLeft = $(".menu-top ul").css("left") + "";
		if(beginLeft.indexOf("px")) {
			beginLeft = beginLeft.substring(0, beginLeft.indexOf("px")) * "1";
		}
		leaveRight = totalWidth + beginLeft - visualWidth;
		if(leaveRight - 150 > 0) {
			$(".menu-top ul").animate({"left" : beginLeft - 150 + "px"}, 500);
		} else {
			$(".menu-top ul").animate({"left" : (-(totalWidth - visualWidth)) + "px"}, 500);
		}
	});
	/*************/
	//点击右上角修改资料菜单时 
	$(".editprofile").click(function(){
		//修改右侧导航样式及iframe
		var path = $("#main").attr("path");
		var ifrId = $(this).attr("ifrid");
		var ifrUrl = $(this).attr("ifrurl");
		var userId = $("#main").attr("userid");
		var ifrName = $(this).text() + "";
		var ifrSrc;
		var flag = false;//关闭
		var thisIndex = 0;
		for(var i = 0; i < ifrIdAry.length; i++) {
			if(ifrIdAry[i] == ifrId) {
				flag = true;
				thisIndex = i;
			}
			//隐藏其他的iframe
			$("#"+ifrIdAry[i]).css("display", "none");
		}
		//清除上方导航菜单选中样式
		$(".menu-top ul .menu-li-active i").removeClass("close-active");
		$(".menu-top ul .menu-li-active").attr("class", "menu-li");
		//当该菜单已经处于点开状态
		if(flag) {
			//将点击的菜单对应的上方导航设置为已选中样式
			$(".menu-top ul [ifrid='" + ifrId + "']").attr("class", "menu-li-active");
			$(".menu-top ul [ifrid='" + ifrId + "'] i").addClass("close-active");
			//将iframe移除（刷新作用）
			$("#"+ifrId).remove();
			//当被点击的菜单的上方导航不在可视区域内时，将ul向左或者向右移动到可视区域内
			//visualWidth:可视长度
			var visualWidth = $(".menu-nav").width();
			//可视区域开始位置
			var visualAreaBegin = $(".menu-nav").offset().left;
			//可视区域结束位置
			var visualAreaEnd = visualAreaBegin + visualWidth;
			//此li的位置
			var thisPosition = $(".menu-top ul [ifrid='" + ifrId + "']").offset().left;
			var thisWidth = $(".menu-top ul [ifrid='" + ifrId + "']").outerWidth();
			var prevWidth = 0;
			var afterWidth = 0;
			var totalWidth = 0;
			$("[class^='menu-li']:lt(" + thisIndex + ")").each(function() {
				prevWidth += $(this).outerWidth(true);
			});
			$("[class^='menu-li']:gt(" + (thisIndex - 1) + ")").each(function() {
				afterWidth += $(this).outerWidth(true);
			});
			$("[class^='menu-li']").each(function() {
				totalWidth += $(this).outerWidth(true);
			});
			if(totalWidth > visualWidth) {
				if(!(thisPosition > visualAreaBegin && thisPosition + thisWidth < visualAreaEnd)) {
					//当后面的所有元素的长度大于可视长度
					if(afterWidth > visualWidth) {
						$(".menu-top ul").animate({"left" : beginLeft - prevWidth}, 500);
					} else {
						$(".menu-top ul").animate({"left" : beginLeft - (totalWidth - visualWidth)}, 500);
					}
				}
			}
		} else {
			ifrIdAry.push(ifrId);
			//给.menu-top添加li
			var li = "<li class='menu-li-active' ifrid='" + ifrId + "'><a>" + ifrName;
			li += "</a><i class='close-active'></i></li>";
			$(".menu-top ul").append(li);
			//判断，当li（包括当前li）的总长度大于上方所能显示的长度时，显示滚动条，并且将ul向左移动
			//visualWidth:可视长度
			var visualWidth = $(".menu-nav").width();
			//所有li的总长度
			var totalWidth = 0;
			$("[class^='menu-li']").each(function(){
				totalWidth += $(this).outerWidth(true);
			});
			if(totalWidth > visualWidth){
				$(".menu-top ul").animate({"left" : - (totalWidth - visualWidth)}, 500);
				$(".left_scroll,.right_scroll").css("display", "block");
			}
		}
		ifrSrc = ifrUrl + "?userid=" + userId;
		//给.content-bottom添加iframe
		addIframe($(".content-bottom"), ifrId, ifrSrc);
		var docIfr = document.getElementById(ifrId);
		docIfr.style.width = "100%";
		docIfr.style.height = "100%";
		//隐藏.first-menu-panel
		$(".first-menu-panel").css("display", "none");
	});
	$(".ewm a,.code-wrap").mouseover(function() {
		$(".code-wrap").css("display", "block");
		$(".ewm a").addClass("ewm_hover");
	});
	$(".ewm a,.code-wrap").mouseout(function() {
		$(".code-wrap").css("display", "none");
		$(".ewm a").removeClass("ewm_hover");
	});
	$(".myself,.ace-nav").mouseover(function() {
		$(".ace-nav").css("display", "block");
		$(".myself").addClass("myself_hover");
	});
	$(".myself,.ace-nav").mouseout(function() {
		$(".ace-nav").css("display", "none");
		$(".myself").removeClass("myself_hover");
	});
	$(".wljxpt").mouseover(function() {
		$(".wljxpt").addClass("wljxpt_hover");
	});
	$(".ewm a,.code-wrap").mouseout(function() {
		$(".wljxpt").removeClass("wljxpt_hover");
	});
	$(".wljxpt").mouseover(function() {
		$(".wljxpt_ts").css("display", "block");
	});
	$(".wljxpt").mouseout(function() {
		$(".wljxpt_ts").css("display", "none");
	});
});
function initAppScroll(id) {
   //菜单向上滚动箭头事件
   $('#' + id + ' > .scroll-up:first').hover(
      function(){$(this).addClass('scroll-up-hover');},
      function(){$(this).removeClass('scroll-up-hover');}
   );

   //点击向上箭头
   $('#' + id + ' > .scroll-up:first').click(
      function() {
         var ul = $('#' + id + ' > ul:first');
         ul.animate({'scrollTop':(ul.scrollTop() - SCROLL_HEIGHT)}, 600);
      }
   );

   //向下滚动箭头事件
   $('#' + id + ' > .scroll-down:first').hover(
      function(){$(this).addClass('scroll-down-hover');},
      function(){$(this).removeClass('scroll-down-hover');}
   );

   //点击向下箭头
   $('#' + id + ' > .scroll-down:first').click(
      function() {
         var ul = $('#' + id + ' > ul:first');
         ul.animate({'scrollTop':(ul.scrollTop() + SCROLL_HEIGHT)}, 600);
      }
   );
}

function initAppListScroll() {
   var su = $("#app_cate_list .scroll-up:first");
   var sd = $("#app_cate_list .scroll-down:first");
   var scrollHeight = $("#app_cate_list ul").attr('scrollHeight');
   var orgheight = $("#app_cate_list ul").height();
   if(orgheight < scrollHeight) {
      var height = scrollHeight > MIN_PNAEL_HEIGHT ? MIN_PNAEL_HEIGHT : scrollHeight;
      $("#app_cate_list ul").height(height);
   }
   
   if(orgheight >= scrollHeight) {
      su.hide();
      sd.hide();
   }
   initAppScroll('app_cate_list'); 
}
initAppListScroll();
//显示菜单的方法
var ifrIdAry = ["personalcenter", "mydesktop"];
