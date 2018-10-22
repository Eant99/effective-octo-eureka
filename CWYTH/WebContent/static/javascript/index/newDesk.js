//点击办事大厅按钮
$(document).on("click",".goMenu",function(){
	var menuName = $(this).find("div:last").text();
	var parentObj = $("[data-name='"+menuName+"']",window.parent.document);
	parent.kz(parentObj);
	parent.n(parentObj);
});

//办事大厅页面切换
var page_count = $("#page_count").val();
var curr_page = 1;
var width = $(".bsdt-page").width();
$(".bsdt-show").width(width);
$(document).on("click","#skip-left",function(){
	if(curr_page==1){
		return;
	}
	curr_page--;
	width = $(".bsdt-page").width();
	$(".bsdt-content").animate({
		left:"+="+width+"px"
	});
})
$(document).on("click","#skip-right",function(){
	if(curr_page == page_count){
		return;
	}
	curr_page++;
	width = $(".bsdt-page").width();
	$(".bsdt-content").animate({
		left:"-="+width+"px"
	});
});

////////桌面菜单的跳转控制
//右侧录入凭证按钮
$(document).on("click",".btn_lrpz",function(){
//	 $("#li_goBackIndex",parent.document).show();
	 var menuName = '040101';
	 var parentObj = $("[ifrid='"+menuName+"']",window.parent.document);
	parent.kz(parentObj);
	parent.n(parentObj);
	$("#li_goBackIndex",parent.document).show(); 
});
//通知公告more
$(document).on("click",".btn-sm",function(){
	var child = '980120';
	 var menuName = '980120';
	 var parentObj = $("[ifrid='"+menuName+"']",window.parent.document);
		parent.kz(parentObj);
		parent.n(parentObj);
		parent.goChildPage(child);
		$("#li_goBackIndex",parent.document).show(); 
	});
//最近凭证more
$(document).on("click",".btn-zjpz",function(){
	 var child = '040901';
	 var menuName = '040101';
	 var parentObj = $("[ifrid='"+menuName+"']",window.parent.document);
		parent.kz(parentObj);
		parent.n(parentObj);
		parent.goChildPage(child);
		$("#li_goBackIndex",parent.document).show();
	});
//今日报销more
$(document).on("click",".btn-jrbx",function(){
	 var menuName = '030101';
	 var parentObj = $("[ifrid='"+menuName+"']",window.parent.document);
		parent.kz(parentObj);
		parent.n(parentObj);
		$("#li_goBackIndex",parent.document).show(); 
	});
//我的报销more
$(document).on("click",".btn-wdbx",function(){
	 var menuName = '030101';
	 var parentObj = $("[ifrid='"+menuName+"']",window.parent.document);
		parent.kz(parentObj);
		parent.n(parentObj);
		$("#li_goBackIndex",parent.document).show(); 
	});
//事前审批
$(document).on("click",".btn-sqsp",function(){
	 var child = '111301';
	 var menuName = '110201';
	 var parentObj = $("[ifrid='"+menuName+"']",window.parent.document);
		parent.kz(parentObj);
		parent.n(parentObj);
		parent.goChildPage(child);
		$("#li_goBackIndex",parent.document).show(); 
	});
//我的项目
$(document).on("click",".btn-wdxm",function(){
	 var child = '150104';
	 var menuName = '150101';
	 var parentObj = $("[ifrid='"+menuName+"']",window.parent.document);
		parent.kz(parentObj);
		parent.n(parentObj);
		parent.goChildPage(child);
		$("#li_goBackIndex",parent.document).show();
	});
