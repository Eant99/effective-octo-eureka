$(function(){
	//返回每一组DOM对象t的宽度和，包括补白和边框
	function t(t){
		var _e=0;
		return $(t).each(
			function(){
				_e+=$(this).outerWidth(!0)
			}),_e
	}
	//
	e = function (ele){
		//a是e前面所有DOM对象的宽度和
		var a=t($(ele).prevAll()),
		//i是e后面所有DOM对象的宽度和
		i=t($(ele).nextAll()),
		//n是除了标签页之外的宽度和
		n=t($(".content-tabs").children().not(".J_menuTabs")),
		//s是留给标签页的宽度
		s=$(".content-tabs").outerWidth(!0)-n,
		//r是标签的个数
		r=0;
		if($(".page-tabs-content").outerWidth()<s)
			r=0;
		else if(i<=s-$(ele).outerWidth(!0)-$(ele).next().outerWidth(!0)){
			if(s-$(ele).next().outerWidth(!0)>i){
				r=a;
				for(var o=ele;r-$(o).outerWidth()>$(".page-tabs-content").outerWidth()-s;)
					r-=$(o).prev().outerWidth(),o=$(o).prev()
			}
		}
		else 
			a>s-$(ele).outerWidth(!0)-$(ele).prev().outerWidth(!0)&&(r=a-$(ele).prev().outerWidth(!0));
		$(".page-tabs-content").animate({marginLeft:0-r+"px"},"fast")
	}
	function a(){
		var _e=Math.abs(parseInt($(".page-tabs-content").css("margin-left"))),
		a=t($(".content-tabs").children().not(".J_menuTabs")),
		i=$(".content-tabs").outerWidth(!0)-a,
		n=0;
		if($(".page-tabs-content").width()<i)
			return!1;
		for(var s=$(".J_menuTab:first"),r=0;r+$(s).outerWidth(!0)<=_e;)
			r+=$(s).outerWidth(!0),
			s=$(s).next();
		if(r=0,t($(s).prevAll())>i)
		{
			for(;r+$(s).outerWidth(!0)<i&&s.length>0;)
				r+=$(s).outerWidth(!0),s=$(s).prev();
			n=t($(s).prevAll())
		}
		$(".page-tabs-content").animate({marginLeft:0-n+"px"},"fast")
	}
	function i(){
		var _e=Math.abs(parseInt($(".page-tabs-content").css("margin-left"))),
		a=t($(".content-tabs").children().not(".J_menuTabs")),
		i=$(".content-tabs").outerWidth(!0)-a,
		n=0;
		if($(".page-tabs-content").width()<i)
			return!1;
		for(var s=$(".J_menuTab:first"),r=0;r+$(s).outerWidth(!0)<=_e;)
			r+=$(s).outerWidth(!0),s=$(s).next();
		for(r=0;r+$(s).outerWidth(!0)<i&&s.length>0;)
			r+=$(s).outerWidth(!0),s=$(s).next();
		n=t($(s).prevAll()),n>0&&$(".page-tabs-content").animate({marginLeft:0-n+"px"},"fast")
	}
	//【加载样式】函数
	function refunc()
	{
		//20180727注释，可能会导致页面一直等待
		layer.load(2);
		$(".J_mainContent iframe:visible").load(
			function(){
				layer.closeAll('loading');
			});
	}
	//打开新标签页面功能函数
	function n(){
		var t=$(this).attr("ifrurl"),
//		t = t.replace("[object HTMLInputElement]","");
		a=$(this).attr("ifrid"),
		t = t.indexOf("?")>0?t+"&mkbh="+a:t+"?mkbh="+a,
		i=$.trim($(this).attr("title")||$(this).data("title")),
		n=!0;
		
		if(t.indexOf("shMain.jsp") > 0){
			$("a.J_menuTab").each(function(){
				if($(this).attr("data-id").indexOf("shMain.jsp") > 0) {
					$(this).remove();
					$("#iframe_"+a).remove();
				}
			});
		}
		if(void 0==t||0==$.trim(t).length)
			return !1;
		//循环遍历选项卡
		var menuflag = true;
		$(".J_menuTab").each(function(){
			if($(this).data("id")==t){
				if(!$(this).hasClass("active")){
					$(this).addClass("active").siblings(".J_menuTab").removeClass("active");
					e(this);
					$(".J_mainContent .J_iframe").each(function(){
						if($(this).data("id")==t){
							$(this).show().siblings(".J_iframe").hide();
						}
					});
				}
				menuflag = false;
			}
		});
		
		if(menuflag)
//		if($(".J_menuTab").each(
//			function(){
//				return $(this).data("id")==(t)?($(this).hasClass("active")||($(this).addClass("active").siblings(".J_menuTab").removeClass("active"),
//					e(this),$(".J_mainContent .J_iframe").each(
//						function(){
//							return $(this).data("id")==(t)?(
//									$(this).show().siblings(".J_iframe").hide(),!1):void 0
//						}
//					)),$(".J_mainContent").find("#iframe_"+a+"").attr("src",""+t+""),refunc(),n=!1,!1):void 0
//			}),n)
		{
			//未打开时
			var s='<a href="javascript:;" class="active J_menuTab" data-id="'+t+'">'+i+' <i class="fa icon-xjifyunsuanchenghao2"></i></a>';
			$(".J_menuTab").removeClass("active");
			var r='<iframe class="J_iframe" name="iframe_'+a+'" id="iframe_'+a+'" width="100%" height="100%" src="'+t+'" frameborder="0" data-id="'+t+'" seamless></iframe>';
			$("#content-main").append(r);
			$("iframe.J_iframe").not("#iframe_"+a).hide();
			//$("#content-main").children().last().remove();
//			$(".J_mainContent").find("#iframe0").attr("ss","2222");
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
	
	function s(){
		if($(".page-tabs-content").children().length == 1){
			window.location.href = $("#contextPath").val() + "/index/login_toIndex";
		}
		else{
			var t=$(this).parents(".J_menuTab").data("id"),
			a=$(this).parents(".J_menuTab").width();
			if($(this).parents(".J_menuTab").hasClass("active"))
			{
				if($(this).parents(".J_menuTab").next(".J_menuTab").size())
				{
					var i=$(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").data("id");
					$(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").addClass("active"),
					$(".J_mainContent .J_iframe").each(
						function(){
							return $(this).data("id")==i?($(this).show().siblings(".J_iframe").hide(),!1):void 0
						});
					var n=parseInt($(".page-tabs-content").css("margin-left"));
					0>n&&$(".page-tabs-content").animate({marginLeft:n+a+"px"},"fast"),
					$(this).parents(".J_menuTab").remove(),
					$(".J_mainContent .J_iframe").each(
						function(){
							return $(this).data("id")==t?($(this).remove(),!1):void 0
						})
				}
				if($(this).parents(".J_menuTab").prev(".J_menuTab").size())
				{
					var i=$(this).parents(".J_menuTab").prev(".J_menuTab:last").data("id");
					$(this).parents(".J_menuTab").prev(".J_menuTab:last").addClass("active"),
					$(".J_mainContent .J_iframe").each(
						function(){
							return $(this).data("id")==i?($(this).show().siblings(".J_iframe").hide(),!1):void 0
						}),
					$(this).parents(".J_menuTab").remove(),
					$(".J_mainContent .J_iframe").each(
						function(){
							return $(this).data("id")==t?($(this).remove(),!1):void 0
						})
				}
			}
			else 
				$(this).parents(".J_menuTab").remove(),
				$(".J_mainContent .J_iframe").each(
					function(){
						return $(this).data("id")==t?($(this).remove(),!1):void 0
					}),
				e($(".J_menuTab.active"));
			return!1
		}
	}
	function r(){
		$(".page-tabs-content").children("[data-id]").not(":first").not(".active").each(
			function(){
				$('.J_iframe[data-id="'+$(this).data("id")+'"]').remove(),$(this).remove()
			}),
		$(".page-tabs-content").css("margin-left","0")
	}
	function o(){
		e($(".J_menuTab.active"))
	}
	function d(){
		if(!$(this).hasClass("active"))
		{
			var t=$(this).data("id");
			$(".J_mainContent .J_iframe").each(function(){
				return $(this).data("id")==t
				?
				($(this).show().siblings(".J_iframe").hide(),!1)
				:
				void 0
			}),
			$(this).addClass("active").siblings(".J_menuTab").removeClass("active"),e(this)
		}
	}
	function c(){
		var t=$('.J_iframe[data-id="'+$(this).data("id")+'"]'),
		_e=t.attr("src");
		//20180727注释，可能会导致页面一直等待
		layer.load(2);
		t.attr("src",_e).load(function(){
			layer.closeAll('loading');
		})
	}	
	$(".menulist").each(function(t){
		$(this).attr("ifrid")||$(this).attr("ifrid",t)
	}),
	$(document).on("click", ".menuList", n),
	$(document).on("click", ".menulist", n),
	$(".J_menuTabs").on("click",".J_menuTab i",s),
	$(".J_tabCloseOther").on("click",r),
	$(".J_tabShowActive").on("click",o),
	$(".J_menuTabs").on("click",".J_menuTab",d),
	$(".J_menuTabs").on("dblclick",".J_menuTab",c),
	$(".J_tabLeft").on("click",a),
	$(".J_tabRight").on("click",i),
	$(".J_tabCloseAll").on("click",function(){
		$(".page-tabs-content").children("[data-id]").not(":first").each(
			function(){
				$('.J_iframe[data-id="'+$(this).data("id")+'"]').remove(),
				$(this).remove()
			}),
		$(".page-tabs-content").children("[data-id]:first").each(
			function(){
				$('.J_iframe[data-id="'+$(this).data("id")+'"]').show(),
				$(this).addClass("active")
			}),
		$(".page-tabs-content").css("margin-left","0")
	});
	var index;
   	$("#popover").mouseover(function(){
   		var title = $("#popover").attr("data-title");
   		index = layer.tips(title,"#popover");
   	});
   	$("#popover").mouseout(function(){
   		close(index);
   	});
   	$(".top_nav").on("click",n);
});

function openMenu(mkbh,url,title){
	alert(1);
	var t=url,
	a=mkbh,
	t = t.indexOf("?")>0?t+"&mkbh="+a:t+"?mkbh="+a,
	i=$.trim(title),
	n=!0;
	
	if(t.indexOf("shMain.jsp") > 0){
		$("a.J_menuTab").each(function(){
			if($(this).attr("data-id").indexOf("shMain.jsp") > 0){
				$(this).remove();
				$("#iframe_"+a).remove();
			}
		});
	}
	
	if(void 0==t||0==$.trim(t).length)
		return !1;
	//循环遍历选项卡
	if($(".J_menuTab").each(
		function(){
			return $(this).data("id")==(t)?($(this).hasClass("active")||($(this).addClass("active").siblings(".J_menuTab").removeClass("active"),
				e(this),$(".J_mainContent .J_iframe").each(
					function(){
						return $(this).data("id")==(t)?(
								$(this).show().siblings(".J_iframe").hide(),!1):void 0
					}
				)),$(".J_mainContent").find("#iframe_"+a+"").attr("src",""+t+""),refunc(),n=!1,!1):void 0
		}),n)
	{
		//未打开时
		var s='<a href="javascript:;" class="active J_menuTab" data-id="'+t+'">'+i+' <i class="fa icon-xjifyunsuanchenghao2"></i></a>';
		$(".J_menuTab").removeClass("active");
		var r='<iframe class="J_iframe" name="iframe_'+a+'" id="iframe_'+a+'" width="100%" height="100%" src="'+t+'" frameborder="0" data-id="'+t+'" seamless></iframe>';
		$("#content-main").children().last().remove();
		$("#content-main").append(r);
		parent.append(r);
		$(".J_mainContent").find("#iframe0").attr("ss","2222");
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