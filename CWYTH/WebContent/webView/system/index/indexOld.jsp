﻿<%@page import="com.googosoft.constant.MenuFlag"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta name="renderer" content="webkit"/>
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<title>${sysname}</title>
<link rel="shortcut icon" href="${ctxStatic}/favicon.ico"/>
<link href="${ctxStatic}/css/bootstrap/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctxStatic}/css/bootstrap/animate.min.css" rel="stylesheet"/>
<link href="${ctxStatic}/css/public/font-awesome.css" rel="stylesheet"/>
<link href="${ctxStatic}/css/public/iconfont.css" rel="stylesheet"/>
<link href="${ctxStatic}/plugins/layer/skin/layer.css" rel="stylesheet"/><!-- 弹窗相关 -->
<link href="${ctxStatic}/css/index/indexOld.css" rel="stylesheet"/>
</head>
<body class="fixed-sidebar full-height-layout fixed-nav">
    <div id="wrapper">
        <!--左侧导航开始-->
        <div id="menu_list">
			<div id="app_cate_list" class="left" >
				<div class="fast-navigation">
					<ul style="margin-top:5px;">
						<li><i class="fa icon-home"></i></li>
						<li><span style="font-size:14px;font-weight: bold;color: #999;padding-left:10px;">功能导航</span></li>
						<li id="popover" data-title="点击折叠菜单">
							<a class="navbar-minimalize minimalize-styl-2 btn-link " style="text-decoration:none;" href="#">
								<i class="fa icon-closemenu" style="font-size: 20px;"></i>
							</a>
						</li>
					</ul>
				</div>
				<div class="menu-scroll scroll-up"></div>
				<ul class="menu-item menu-item-scroll">
					<!--菜单都被放到这个位置了-->
				</ul>
				<div class="menu-scroll scroll-down"></div>
			</div>
        </div>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="container-fluid dashbard-1">
            <div class="row border-bottom">
                <div class="navbar navbar-fixed-top" role="navigation">
                    <div class="navbar-header">
                        <div class='brand'>
                        	<%-- <img alt="${schoolname}${sysname}" src="${ctxStatic}/images/index/logo.png" style="height: 45px;width: auto;"> --%>
                        	<div style="float:right;padding-top:7px;padding-left:10px; word-spacing:10px;letter-spacing: 3px;font-weight:bold;font-size:21px;">${schoolname}${sysname}</div>
                            <input type="hidden" id="contextPath" value="${ctx}"><!--项目根路径-->
                        </div>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        <li class="dropdown">
                        	<button type="button"  title="点击可查看审核信息" class="btn btn-primary dropdown-toggle shxx">
                                <i class="faw fa-bell" style="color:white;"></i>&ensp;待审业务<span class="label label-primary labelmessage">${shsl}</span>
						  	</button>
                            <ul class="dropdown-menu dropdown-alerts">
                                <c:forEach var="shxx" items="${shxx}" varStatus="i" end="2">
                                	<li class="shyw" style="cursor:pointer;" data-url="${ctx}/webView/ywsh/shMain.jsp?djbh=${shxx.DBH}&sqmkbh=${shxx.SQMKBH}" data-mkbh="${shxx.MKBH}" data-mkmc="待审核信息">
                                        <div class="row" style="height:15px;padding-left:10px;margin-top:10px;padding-bottom:22px;">
                                            <div class="col-md-12" style="height:15px;text-overflow:ellipsis;overflow:hidden;">${shxx.mc}</div>
                                        </div>
                                        <div class="row" style="height:15px;padding-left:10px;">
                                            <div class="col-md-7"><strong>单据编号：</strong>${shxx.dbh}</div>
                                        </div>
                                    </li>
									<li class="divider"></li>
								</c:forEach>
                                <li class="shyw" style="cursor:pointer;" data-url="${ctx}/webView/ywsh/shMain.jsp" data-mkbh="<%=MenuFlag.SHCD%>" data-mkmc="待审核信息">
                                    <div class="row text-center link-block">
                                    	<i class="fa icon-xinfeng"></i><strong>查看所有消息</strong>
                                    </div>
                                </li>
                            </ul>
                        </li><li class="dropdown">
                            <button type="button" title="未读通知信息" class="btn btn-primary dropdown-toggle moreMessage">
                                <i class="faw fa-envelope-o" style="color:white;"></i><span class="label label-warning labelmessage" id="wdggsl">${wdsl}</span>
						  	</button>
                            <ul class="dropdown-menu dropdown-messages">
                                   <c:forEach var="wdxx" items="${wdxx}" varStatus="i" end="2">
                                   <li>
                                       <div class="row wdxx" style="height:15px;padding-left:10px;margin-top:10px;padding-bottom:22px;cursor:pointer;">
                                           <input type="hidden" name="gid" value="${wdxx.GID}" />
                                           <div class="col-md-8" style="height:15px;text-overflow:ellipsis;overflow:hidden;">${wdxx.title}</div>
                                           <div class="col-md-3" style="text-align:right"><c:if test="${wdxx.ts < 1 && wdxx.xs > 0}"><strong>${wdxx.xs}小时前</strong></c:if><c:if test="${wdxx.ts < 1 && wdxx.xs == 0}"><strong>${wdxx.fz}分钟前</strong></c:if><c:if test="${wdxx.ts >= 1}"><strong>${wdxx.ts}天前</strong></c:if></div>
                                       </div>
                                     <div class="row" style="height:15px;padding-left:10px">
                                         <div class="col-md-7" >${wdxx.fbsj}</div>
                                     </div>
                                    </li>
                                    <li class="divider"></li>
                                   </c:forEach>
                                <li>
                                    <div class="text-center link-block">
                                        <a class="J_menuItem" id="li_tzgg"><i class="fa icon-xinfeng"></i><strong>查看所有消息</strong></a>
                                    </div>
                                </li>
                            </ul>
                        </li><c:if test="${appDisplay == '1' }"><li class="dropdown">
                            <button type="button" title="手机二维码" class="btn btn-primary dropdown-toggle moreMessage">
                                <i class="faw fa-mobile" style="color:white;font-size: 25px;"></i>
						  	</button>
                            <ul class="dropdown-menu dropdown-messages" style="width: 145px;right: -4px;">
                                <li class="text-center" style="padding-top: 10px;">
	                                <img alt="" width="100" src="${ctx}/uploadFiles/encoderImgId.png"><br>
	                                <p class="text-center" style="padding-top: 10px;">扫一扫，下载手机APP</p>
                                </li>
                            </ul>
                        </li></c:if><li class="dropdown">
                            <button type="button" id="btn_help" title="点击可查看帮助" class="btn btn-primary dropdown-toggle moreMessage">
                                <i class="fa icon-help1" style="color:white;font-size: 20px;"></i>
						  	</button>
						  	<div class="help-panel">
						        <div id="d_search" class="help-search-box">
			                    	<i class="fa icon-sousou soutb" id="i_search"></i>
			                    	<input type="text" class="sousou" placeholder="请您输入资产名称" id="txt_search" name="search"/>
		       					</div> 
						        <div class="help-line"></div>
						        <div class="help-doc-wrap">
						        	<span class="help-top-title">您可能需要以下帮助</span>
						        	<div class="help-doc-list">
						        		<ul>
						        			<li><a href="${ctx}/index/goHelpPage" target="_blank">帮助文档</a></li>
						        			<li><a href="${ctx}/cjwt/goCjwtLookPage" target="_blank">常见问题</a></li>
						        			<li><a href="${ctx}/xzwhb/goGjxzPage" target="_blank">工具下载</a></li>
						        		</ul>
						        	</div>
						        </div>
							</div>
                        </li><li class='dropdown dark user-menu'>
                        	<button type="button" class="btn btn-primary dropdown-toggle img" style="background-color: transparent;">
	                            <span class='user-name hidden-phone'>${loginName}</span>
	                            <span class="caret"></span>
						  	</button>
	                        <ul class="dropdown-menu">
	                             <li>
	                                <a class="J_menuItem top_nav" ifrurl="${ctx}/grsz/goGrxxPage?rybh=${rybh}&operateType=U" ifrid="98" title="个人信息"  href="javascript:void(0);"">
	                                    <i class='fa icon-guanwangicon31315'></i>个人信息
	                                </a>
	                            </li>
	                             <c:if test="${reLoginDisplay == '1' }">
	                             <li>
	                            	<a  class="J_menuItem" href="javascript:void(0);"  id="li_logout"><i style="font-size:16px" class="faw fa-sign-in"></i>重新登录</a>
	                            </li>
	                            </c:if>
	                            <li>
	                            	<a  class="J_menuItem" href="javascript:void(0);"  id="li_loginout"><i class="fa icon-tuichu"></i>系统退出</a>
	                            </li>
	                        </ul>
                    	</li>
                    </ul>
                </div>
            </div>
            <div class="row content-tabs">
                <button type='button' class="roll-nav roll-left J_tabLeft"><i class="fa icon-leftmove"></i></button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:void(0);" class="active J_menuTab" data-id="${ctx}/webView/system/login/main.jsp">桌面</a>
                    </div>
                </nav>
                <button type='button' class="roll-nav roll-right J_tabRight"><i class="fa icon-rightmove"></i></button>
                <div class="btn-group roll-nav roll-right">
                    <button type='button' type='button' class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span></button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a></li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
                    </ul>
                </div>   
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" id="iframe0" width="100%" height="100%" src="${ctx}/index/goMain" frameborder="0" data-id="${ctx}/webView/system/login/main.jsp"></iframe>
            </div>
        </div>
        <!--右侧部分结束-->
    </div>
<script src="${ctxStatic}/javascript/jquery/jquery.js"></script>
<script src="${ctxStatic}/javascript/bootstrap/bootstrap.min.js"></script>
<script src="${ctxStatic}/plugins/datetimepicker/bootstrap-datetimepicker.js"></script>
<script src="${ctxStatic}/javascript/index/index.js"></script><!--首页效果-->
<script src="${ctxStatic}/javascript/index/left-treeold.js"></script><!--菜单树json对象在这加载-->
<script src="${ctxStatic}/plugins/layer/layer.min.js"></script><!--弹窗-->
<script src="${ctxStatic}/javascript/index/contabs.min.js"></script><!--标签-->
<script src="${ctxStatic}/javascript/public/public-js.js"></script>
<script src="${ctxStatic}/javascript/public/public-window.js"></script>
<!-- 左上角的点击折叠菜单及右上角的审核权限信息菜单的查看全部我都移动到了contabs.min.js中 -->
<script type="text/javascript">
var sessionXMM = "${pageContext.request.contextPath}";
var sessionRowNumLength = ["${sessionScope.rownum}"];//全局的分页函数
$(function(){
	scrollMenu();
	//单点登录跳转页面,如果传入模块编号，默认打开该菜单
	var mkbh = "${mkbh}";
	var url = "${mkurl}";
	var mkmc = "${mkmc}";
	if(mkbh!=""){
		openMenu(mkbh,"${pageContext.request.contextPath}/"+url,mkmc);
	}
	//帮助信息
	//帮助信息
	$("#btn_help, .help-panel").hover(
		function() {
    		$( this ).parent().addClass( "active" );
  		}, function() {
    		$( this ).parent().removeClass( "active" );
  		}
  	);
	$(".dropdown-toggle, .dropdown-menu").hover(
			function() {
	    		$( this ).parent().addClass( "open" );
	  		}, function() {
	    		$( this ).parent().removeClass( "open" );
	  		}
	);
	/* $("#btn_help").click(function(){
		window.open("${ctx}/index/goHelpPage");
	}); */
	//搜索信息
	$("#i_search").click(function(){
		 var searchval = $("#txt_search").val();
		if ( searchval!="")
		{
			window.open("${ctx}/index/goSearchPage?keyword="+searchval);
		}
		else
		{
			alert("请输入关键搜索信息！");
		}; 
	});
	$("#img_person").error(function() { 
		$(this).attr("src", "${ctxStatic}/images/systemSet/grxx/person_default.jpg");
		$(this).onerror = null;
		}); 
	//设置信息
	$("#li_szxx").click(function(){
		select_commonWin("${ctx}/index/goSzxxPage","设置选项","450","550");
	});
	//重新登录
	$("#li_logout").click(function(){
		confirm("确认重新登录？",{title:"友情提示"},function(){
			window.location.href="${ctx}/index/logout";
		});
	});
	//退出
	$("#li_loginout").click(function(){
		confirm("是否退出系统？",{title:"友情提示"},function(){
			$.ajax({
				type :"post",
				url:"${ctx}/index/loginout",
				success:function(val)
				{
   					closeWebPage();
				},
				 error:function(){
	               alert("请稍后再试！");
	            },
			});
		});
	});
	function closeWebPage(){  
   		var userAgent = navigator.userAgent;
   		if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1||userAgent.indexOf("IE") !=-1) {
   			window.location.replace("about:blank");
   			event.returnValue=false;
   		} else {
   		   window.opener = null;
   		   window.open("", "_self");
   		   window.close();
   		}
	}  
	//通知公告
	$("#li_tzgg").click(function(){
		select_commonWin("${ctx}/webView/window/tzgg/tzggList.jsp","通知公告","920","630");
	});
	//查看未读消息
   	$(document).on("click",".wdxx",function(){
   		var gid = $(this).find("[name=gid]").val();
   		select_commonWin("${ctx}/window/goTzggEdit?gid="+gid,"通知公告","920","630");
	});
    //查看审核信息
   	$(document).on("click",".shyw",function(){
   		openRightWin($(this).attr("data-url"),$(this).attr("data-mkbh"),$(this).attr("data-mkmc"));
	});
});
var openRightWin = function(_url,_id,title){
	if(_url.indexOf("shMain.jsp") > 0){
		$("a.J_menuTab").each(function(){
			if($(this).attr("data-id").indexOf("shMain.jsp") > 0){
				$(this).remove();
				$("#iframe_"+_id).remove();
			}
		});
	}
	
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
var getPageUrl = function(mkbh){
	var url = "${pageContext.request.contextPath}";
	if("<%=MenuFlag.JMYQ_NSYSH%>" == mkbh){
		url += "/jmyqbSywh/goJmyqbShPage";
	}
	else if("<%=MenuFlag.ZCWX_WXSQSH%>" == mkbh){
		url += "/sbwxsh/goSbwxsqSh";
	}
	else if("<%=MenuFlag.ZCWX_WXBG_SH%>" == mkbh || "<%=MenuFlag.ZCWX_WXBG_CWSH%>" == mkbh){
		url += "/sbwxsh/goWxbgSh";
	}
	else if("<%=MenuFlag.ZCWX_JFZJ_DWLDSH%>" == mkbh || "<%=MenuFlag.ZCWX_JFZJ_CWSH%>" == mkbh){
		url += "/sbwxsh/goJfzjSh";
	}
	else if("<%=MenuFlag.ZCXZLY_GKSH%>" == mkbh){
		url += "/xztjsh/goLygkshPage";
	}
	else if("<%=MenuFlag.ZCXZLY_ZGSH%>" == mkbh){
		url += "/xztjsh/goLyzgshPage";
	}
	else if("<%=MenuFlag.ZCXZ_GKSH%>" == mkbh){
		url += "/xzsqsh/goGkEditPage";
	}
	else if("<%=MenuFlag.ZCXZ_ZGSH%>" == mkbh){
		url += "/xzsqsh/goZcEditPage";
	}
	else if("<%=MenuFlag.ZCQC_ZCSH%>" == mkbh){
		url += "/zczcb/goEditPage";
	}
	else if("<%=MenuFlag.ZCCZ_CWSH%>" == mkbh){
		url += "/zcczsh/goGkAndCwshPage";
	}
	else if("<%=MenuFlag.ZCCZ_GKSH%>" == mkbh){
		url += "/zcczsh/goGkAndCwshPage";
	}
	else{
		url += "/shenhe/goShPage";
	}
	
	return url;
};

</script>
</body>
</html>
