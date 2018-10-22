/**
 * 带有明细列表的列表，显示明细列表的方法
 * dom：当前操作的控件，一般传入$(this)就好
 * ztcol：在主列表的复选框上绑定的状态的属性名称
 * ajaxurl：获取明细列表数据需要的路径
 * ajaxdata：获取明细列表数据需要的参数
 * formatfun：获取明细列表数据后，执行的格式化html的方法，一般是有两个参数，第一个val是通过这里的ajax获取到的数据，另一个ztbz是从主列表上获取到该数据当前的状态，主要是用来处理是否显示操作按钮的
 */
var detailslist = function(dom,ztcol,ajaxurl,ajaxdata,formatfun){
	var tr = dom.closest('tr');
    var row = table.row( tr );
    if ( row.child.isShown() ) {
        row.child.remove();
        tr.removeClass('shown');
        
        scrolldraw(dom);
    } else {
        var ztbz = dom.parents("tr").find(".keyId").attr(ztcol);
        $.ajax({
        	type:"post",
        	data:ajaxdata,
        	url:ajaxurl,
        	dataType:"json",
        	success:function(val){
        		close(idx);
        		row.child( formatfun(val,ztbz) ).show();
                tr.addClass('shown');
                
                scrolldraw(dom);
        	},
        	error:function(){
        		close(idx);
        		alert("抱歉，系统出现问题！");
        	},
			beforeSend:function(){
				idx = loading(2);
			}
        });
    }
};

/**
 * 重绘表格，以适应滚动条
 * dom：当前操作的控件，跟detailslist()里边的dom是一个东西
 * scrollwidth：获取的滚动条的宽度，直接把getScrollWidth()获取到的值传过来就好
 */
var scrolldraw = function(dom){
	var scrollwidth = getScrollWidth();
	var theight = dom.parents("table").height();
	var bodyheight = dom.parents("div.dataTables_scrollBody").height();
	if(theight <= bodyheight){
		scrollwidth = 0;
	}
	
	dom.parents("div.dataTables_scrollBody").prev("div.dataTables_scrollHead").find("div.dataTables_scrollHeadInner").css({"padding-right":scrollwidth+"px","width":dom.parents("table").outerWidth() + "px"});
	dom.parents("div.dataTables_scroll").next("div.DTFC_RightWrapper").css("right",scrollwidth+"px");
	
	var tableth = dom.parents("table").find("thead tr").eq(0).find("th");//table的表头
	var thlist = dom.parents("div.dataTables_scrollBody").prev("div.dataTables_scrollHead").find("thead tr").eq(0).find("th");//head表头
	for(var i = 0; i < thlist.length; i++){
		$(thlist[i]).css("width",$(tableth[i]).width()+"px");
	}
};

/**
 * 获取滚动条的宽度
 */
var getScrollWidth = function() {
	var noScroll, scroll, oDiv = document.createElement("DIV");
	oDiv.style.cssText = "position:absolute; top:-1000px; width:100px; height:100px; overflow:hidden;";
	noScroll = document.body.appendChild(oDiv).clientWidth;
	oDiv.style.overflowY = "scroll";
	scroll = oDiv.clientWidth;
	document.body.removeChild(oDiv);
	return noScroll-scroll;
};
