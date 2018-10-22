<%@page import="com.googosoft.util.DateUtil"%>
<%@page import="com.googosoft.constant.MenuFlag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>资产管理系统主页</title>
<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/css/public/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/index/main.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/public/font-awesome.css" rel="stylesheet"/>
<style type="text/css">
.header .fa{
	color:white;
}
.bgColor1{
	background-color: #00acec !important;
}
.bgColor2{
	background-color: #fcd052 !important;
}
.bgColor3{
	background-color: #49bf67 !important;
}
.bgColor4{
	background-color: #f34541 !important;
}
.bgColor5{
	background-color: #9564e2 !important;
}
.bgColor6{
	background-color: #8a6d3b !important;
}
.more{
	font-size:12px;
}
u{
text-decoration: none;
}
</style>
</head>
<body class="contrast-red">
	<div class="container-fluid">
    <div class="box">
		<div class='box-header'>
             <div class="row">
                 <div class="col-md-4">
                     <div class='pull-left title'>
                         <i class='fa icon-chaxun1'></i>
                         <span>我的日常业务</span>
                     </div>
                 </div>
                 <div class="col-md-8" >
                     <div class='alert alert-info'>
                         <strong>我的日常业务</strong>&emsp;你可以在这里找到您常用的一些功能，还可以单击<a id="btn-quick-link" class="btn btn-link" data-url="${pageContext.request.contextPath}/index/goSetrcyw">设置我的日常业务</a>设置我的业务
                     </div>
                 </div>
             </div>
         </div>
         <div class="box-content">
             <div class='row'>
	             <c:forEach var="rcyw" items="${rcyw}" varStatus="i" >
	                 <div class='col-md-2 box-quick-link bgColor${i.index+1}' data-url="${pageContext.request.contextPath}${rcyw.URL}" data-mkbh="${rcyw.MKBH}">
	                   	 <div class='header'>
	                   	 	<i class='fa ${rcyw.ICON}'></i>
	                   	 </div>
	                     <div class='content'>${rcyw.MKMCNEW}</div>
	                 </div>
	             </c:forEach>
             </div>
         </div>
     </div>
     <div class='row'>
         <div class='col-md-6 tb_col'>
             <!-- 我的待办事项 -->
             <div class="box">
                 <div class='box-header'>
                     <div class='title'>
                         <i class='fa icon-daibanxiang'></i>
                         <span>我的待办事项</span>
                     </div>
                     <div class='actions'>
                         <a href="javascript:void(0);" onclick="getDbsxList()" class="btn btn-mini btn-link more" title="点击查看更多信息">更多>></a>
                         <a href="#" class="btn box-remove btn-mini btn-link" title="点击关闭">
                             <i class='faw fa-close'></i>
                         </a>
                         <a href="#" class="btn box-collapse btn-mini btn-link" title="点击折叠或展开"><i></i></a>
                     </div>
                 </div>
                 <div class='box-content'>
                     <c:if test="${dbsx.size() != 0}">
                     <div id='stats-chart1'>
                         <table class="table table-condensed table-hover">
                             <thead></thead>
                             <tbody>
                             <c:forEach var="dbsx" items="${dbsx}" end="7">
                                 <tr>
                                     <td class="text-left">${dbsx.MC}&emsp;单据编号：<em>${dbsx.DBH}</em></td>
                                     <td class="text-left"><button type='button' class="btn btn-link" data-url="${pageContext.request.contextPath}/webView/ywsh/shMain.jsp?pageUrl=${dbsx.TZLJ}?djbh=${dbsx.DBH}&sqmkbh=${dbsx.SQMKBH}" data-mkbh="${dbsx.MKBH}" data-mkmc="待审核信息" name="dbsx"><i class="fa icon-right"></i>处理</button></td>
                               	</tr>
                             </c:forEach>
                             </tbody>
                         </table>
                     </div>
                     </c:if>
                     <c:if test="${dbsx.size() == 0}">
                     <div class='col-md-12' style="padding-left:19px;height:55px;padding-top:18px;border-bottom: 1px solid #eee;border-top: 1px solid #eee">暂无待办事项信息！
                     </div>
                    </c:if>
                 </div>
             </div>
             <!--进度跟踪  -->
             <div class="box">
                 <div class='box-header'>
                     <div class='title'>
                         <i class='fa icon-look'></i>
                         <span>进度跟踪</span>
                     </div>
                     <div class='actions'>
                         <a href="javascript:void(0);" onclick="getJdgzList()" class="btn btn-mini btn-link more" title="点击查看更多信息">更多>></a>
                         <a href="#" class="btn box-remove btn-mini btn-link" title="点击关闭">
                             <i class='faw fa-close'></i>
                         </a>
                         <a href="#" class="btn box-collapse btn-mini btn-link" title="点击折叠或展开"><i></i></a>
                     </div>
                </div>
				<div class="box-content">
					<div class='row'>
					<c:if test="${jdgz.size() != 0}">
						<c:forEach var="jdgz" items="${jdgz}" varStatus="i" end="3">
							<c:choose>
								<c:when test="${i.index % 2==0}">
									<div class='col-md-6' style="padding-right:3px;">
										<div class='box-content box-statistic'>
											<div class='title ${jdgz.CSS}'>${jdgz.LX}</div>
		                                 	<small>编号：${jdgz.BH}</small><br />
		                                 	<small>名称：${jdgz.MC}</small><br />
		                                 	<small>进度：${jdgz.JD}</small>
		                                 	<div class='text-error  align-right'></div>
										</div>
									 </div>
								</c:when>
								<c:otherwise>
								<div class='col-md-6' style="padding-left:4px;">
										<div class='box-content box-statistic '>
		                                 	<div class='title ${jdgz.CSS}'>${jdgz.LX}</div>
		                                 	<small>编号：${jdgz.BH}</small><br />
		                                 	<small>名称：${jdgz.MC}</small><br />
		                                 	<small>进度：${jdgz.JD}</small>
		                                 	<div class='text-error align-right'></div>
		                           		</div>
		                           		</div>
								</c:otherwise> 
							</c:choose>
						</c:forEach>
						</c:if>
						<c:if test="${jdgz.size() == 0}">
						<div class='col-md-12' style="padding-left:32px;height:55px;padding-top:18px;border-bottom: 1px solid #eee;border-top: 1px solid #eee">暂无进度跟踪信息！
						</div>
						</c:if>
                     </div>
                 </div>
            </div>
            <!--资产生命周期  -->
            <div class="box">
                 <div class='box-header'>
                     <div class='title'><i class='fa icon-zichan'></i><span>资产生命周期</span></div>
                     <div class='actions'>
                         <a href="#" class="btn box-remove btn-mini btn-link" title="点击关闭">
                             <i class='faw fa-close'></i>
                         </a>
                         <a href="#" class="btn box-collapse btn-mini btn-link" title="点击折叠或展开"><i></i></a>
                     </div>
                 </div>
                 <div class="box-content">
                     <div class='timeline'>
                         <ol class='unstyled'>
                             <li>
                                 <div class='icon green-background'><i class='fa icon-shouruzhangdan text-white'></i></div>
                                 <div class='title'>资产建账
                                     <small class='muted'>通过<a name="smzq" href='javascript:void(0)' ifrid='<%=MenuFlag.ZCJZ_GLY%>' ifrurl='' title=''><strong>管理员建账</strong></a>或者<a name="smzq" href='javascript:void(0)' ifrid='<%=MenuFlag.ZCJZ_LYR%>' ifrurl='' title=''><strong>领用人建账</strong></a>两种方式进行资产的入账</small>
                                 </div>
                                 <div class='content green-background text-white'>
                                     <div class="text-white">验收建账</div>
                                     <i class="fa icon-i text-white"></i>&ensp;验收建账完成资产的入库和报账工作；
                                 </div>
                             </li>
                             <li>
                                 <div class='icon blue-background'>
                                     <i class='fa icon-biangeng text-white'></i>
                                 </div>
                                 <div class='title'>资产变动
                                     <small class='muted'>
                                     <a name="smzq" href='javascript:void(0)' ifrid='<%=MenuFlag.ZCBD_DJBD%>' ifrurl='' title=''><strong>单价变动</strong></a>、
                                     <a name="smzq" href='javascript:void(0)' ifrid='<%=MenuFlag.ZCBD_DWNDB%>' ifrurl='' title=''><strong>单位内调拨</strong></a>、
                                     <a name="smzq" href='javascript:void(0)' ifrid='<%=MenuFlag.ZCBD_DWJDB%>' ifrurl='' title=''><strong>单位间调拨</strong></a>、
                                     <a name="smzq" href='javascript:void(0)' ifrid='<%=MenuFlag.ZCBD_XMBD%>' ifrurl='' title=''><strong>项目变动</strong></a>等
                                     </small>
                                 </div>
                                 <div class='content blue-background text-white'>
                                     <div class="text-white">资产变动</div>
                                     <i class="fa icon-i text-white"></i>
                                     &ensp;资产变动分为单价变动、单位内调拨、单位间调拨、项目变动等等；
                                 </div>
                             </li>
                             <li>
                                 <div class='icon orange-background'>
                                     <i class='fa icon-weixiu text-white'></i>
                                 </div>
                                 <div class='title'>资产维修
                                     <small class='muted'><a name="smzq" href='javascript:void(0)' ifrid='<%=MenuFlag.ZCWX_WXSQ%>' ifrurl='' title=''><strong>维修申请</strong></a></small>
                                 </div>
                                 <div class='content orange-background text-white'>
                                     <div class="text-white">资产维修</div>
                                     <i class="fa icon-i text-white"></i>&ensp;免费维修逾期后，需要办理资产维修业务的入口
                                 </div>
                             </li>
                             <li>
                                 <div class='icon red-background'>
                                     <i class='fa icon-zichanchuzhi01 text-white'></i>
                                 </div>
                                 <div class='title'>资产处置
                                     <small class='muted'>
                                     <a name="smzq" href='javascript:void(0)' ifrid='<%=MenuFlag.ZCCZ_ZCCZ%>' ifrurl='' title=''><strong>资产报废、报损、出售</strong></a>等
                                     </small>
                                 </div>
                                 <div class='content red-background text-white'>
                                     <div class="text-white">资产处置</div>
                                     <i class="fa icon-i text-white"></i>&ensp;资产处置是设备无法正常使用，需要进行处理，或者设备报损、进行出售等；
                                 </div>
                             </li>
                         </ol>
                     </div>
                 </div>
             </div>
          </div>
         <div class='col-md-6'>
             <!-- 通知公告 -->
              <div class="box">
                 <div class='box-header'>
                     <div class='title'>
                         <i class='fa icon-xinfeng'></i>
                         <span>通知公告</span>
                     </div>
                     <div class='actions'>
                         <a href="javascript:void(0);" class="btn btn-mini btn-link more" onclick="tzgg_list();" title="点击查看更多信息">更多>></a>
                         <a href="#" class="btn box-remove btn-mini btn-link" title="点击关闭">
                             <i class='faw fa-close'></i>
                         </a>
                         <a href="#" class="btn box-collapse btn-mini btn-link" title="点击折叠或展开"><i></i></a>
                     </div>
                 </div>
                 <div class="box-content">
                 <c:if test="${tzgg.size() != 0}">
                     <c:forEach var="tzgg" items="${tzgg}" varStatus="i">
                         <div class='row tzgg'>
	                     	<div class="tzgg_left col-md-2">
	                     		<ul>
	                     			<li class="mmdd"><fmt:formatDate value="${tzgg.FBSJ}" pattern="MM.dd"/></li>
	                     			<li class="bottom"><fmt:formatDate value="${tzgg.FBSJ}" pattern="yyyy"/></li>
	                     		</ul>
	                     	</div>
	                     	<div class="tzgg_right col-md-10">
	                     		<ul>
	                     			<li class="title" >
	                     			<a href="javascript:void(0)" onclick="tzgg_xx('${tzgg.GID}')">${tzgg.TITLE}</a>
	                     			</li>
	                     			<li class="content">发布人：${tzgg.FBR}</li>
	                     		</ul>
	                     	</div>
	                     </div>
                     </c:forEach>
                     </c:if>
                     <c:if test="${tzgg.size() == 0}">
                     <div  class="col-md-12" style="padding-left:19px;height:55px;padding-top:18px;padding-top:18px;border-bottom: 1px solid #eee;border-top: 1px solid #eee">暂无通知公告信息！
                     </div>
                     </c:if>
                 </div>
             </div>
             <div class="box">
                 <div class='box-header'>
                     <div class='title'>
                         <i class='fa icon-liushui'></i>
                         <span>我的业务流水</span>
                     </div>
                     <div class='actions'>
                         <select style="font-size:12px;" id="sel_month" >
									<option value="01" <%if(DateUtil.getMonth().equals("01")){%>selected<%}%>>一月份</option>
									<option value="02" <%if(DateUtil.getMonth().equals("02")){%>selected<%}%>>二月份</option>
									<option value="03" <%if(DateUtil.getMonth().equals("03")){%>selected<%}%>>三月份</option>
									<option value="04" <%if(DateUtil.getMonth().equals("04")){%>selected<%}%>>四月份</option>
									<option value="05" <%if(DateUtil.getMonth().equals("05")){%>selected<%}%>>五月份</option>
									<option value="06" <%if(DateUtil.getMonth().equals("06")){%>selected<%}%>>六月份</option>
									<option value="07" <%if(DateUtil.getMonth().equals("07")){%>selected<%}%>>七月份</option>
									<option value="08" <%if(DateUtil.getMonth().equals("08")){%>selected<%}%>>八月份</option>
									<option value="09" <%if(DateUtil.getMonth().equals("09")){%>selected<%}%>>九月份</option>
									<option value="10" <%if(DateUtil.getMonth().equals("10")){%>selected<%}%>>十月份</option>
									<option value="11" <%if(DateUtil.getMonth().equals("11")){%>selected<%}%>>十一月</option>
									<option value="12" <%if(DateUtil.getMonth().equals("12")){%>selected<%}%>>十二月</option>
						 </select>
                         <a href="javascript:void(0);" onclick="getYwlsList()" class="btn btn-mini btn-link more" title="点击查看更多信息">更多>></a>
                         <a href="#" class="btn box-remove btn-mini btn-link" title="点击关闭">
                             <i class='faw fa-close'></i>
                         </a>
                         <a href="#" class="btn box-collapse btn-mini btn-link" title="点击折叠或展开"><i></i></a>
                     </div>
                 </div>
                 <div class="box-content">
					<ul class="ywls">
						<li>
							<div class="text-muted pull-left"></div>
                            <div class="pull-right">
								
                            </div>
						</li>
                     </ul>
                 </div>
             </div>
             <div class="box">
                 <div class='box-header'>
                     <div class='title'>
                         <i class='fa icon-svg33'></i>
                         <span>我的名下资产</span>
                     </div>
                     <div class='actions'>
                         <a href="javascript:void(0);" class="btn btn-mini btn-link more"   onclick="zcxx_list();" title="点击查看更多信息">更多>></a>
                         <a href="#" class="btn box-remove btn-mini btn-link"  title="点击关闭">
                             <i class='faw fa-close'></i>
                         </a>
                         <a href="#" class="btn box-collapse btn-mini btn-link" title="点击折叠或展开"><i></i></a>
                     </div>
                 </div>
                 <div class='box-content'>
                     <div id='stats-chart2'>
                         <table class="table table-striped table-bordered table-hover">
                         <c:if test="${grzc.size() != 0}">
                             <thead>
                                 <tr>
                                     <th>资产编号</th>
                                     <th>资产名称</th>
                                     <th>入账日期</th>
                                     <th>总    价</th>
                                     <th>存放地点</th>
                                 </tr>
                             </thead>
                             <tbody>
                             <c:forEach var="grzc" items="${grzc}" end="4">
                                 <tr>
                                     <td style="text-align:left;">${grzc.YQBH}</td>
                                     <td style="text-align:left;">${grzc.YQMC}</td>
                                     <td style="text-align:left;"><fmt:formatDate value="${grzc.RZRQ}" pattern="yyyy-MM-dd"/></td>
                                     <td style="text-align:right;"><fmt:formatNumber value="${grzc.ZZJ}" pattern='#0.00'/> </td>
                                     <td style="text-align:left;">${grzc.BZXX}</td>
                                 </tr>
                             </c:forEach>
                             </tbody>
                             </c:if>
                             <c:if test="${grzc.size() == 0}">
                                 <div  class="col-md-12"style="padding-left:19px;height:55px;padding-top:18px;padding-top:18px;border-bottom: 1px solid #eee;border-top: 1px solid #eee">暂无个人名下资产信息！
                                 </div>
                             </c:if>
                         </table>
                     </div>
                 </div>
             </div>
             
         </div>
     </div>
 </div>
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/datatable/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/datetimepicker/bootstrap-datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-js.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-window.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-json.js"></script>
<script type="text/javascript">
$(function(){
	getYwls();
	$("#sel_month").change(function(){
		getYwls();
	});
	
	$(".box-quick-link").on("click",function(){
		var url = $(this).attr("data-url");
		var method = $(this).attr("data-method");
		var mkbh = $(this).attr("data-mkbh");
		var title = $(this).find("div.content").prop("innerHTML");
		if(method == 'window'){
			var content = $(this).find(".content").text();
			select_commonWin(url,content,"600","530","yes");
		}else{
			$.ajax({
				type :"post",
				url:"${pageContext.request.contextPath}/index/getSmzqMenu",
				data:"mkbh="+mkbh,
				success:function(val){
					val = $.trim(val);
					val = JSON.getJson(val);
	   					if(val.success == 'true'){
	   						openRightWin(url,mkbh,title);
						}else{
							alert(val.msg);
						}
	   					close(index);
					},
					 error:function(){
		                	alert("请稍后再试！");
		             },
			});
		}
	});
	$("#btn-quick-link").on("click",function(){
		var url = $(this).attr("data-url");
		var content = $(this).text();
	    select_commonWin(url,content,"600","530","yes");
	});
	$("a[name='smzq']").on("click",function(){
		var dom = $(this);
		var mkbh = dom.attr("ifrid");
		if(dom.attr("ifrurl") == ""){
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/index/getSmzqMenu",
				data:"mkbh="+mkbh,
				success:function(val){
					val = $.trim(val);
					val = JSON.getJson(val);
					if(val.success=='true'){
						dom.attr("title",val.title);
						dom.attr("ifrurl","${pageContext.request.contextPath}" + val.url);
						openRightWin(dom.attr("ifrurl"),mkbh,dom.attr("title"));
					}
					else{
						alert(val.msg);
					}
				},
				error:function(val){
					alert(val);
				}
			});
		}else{
			openRightWin(dom.attr("ifrurl"),mkbh,dom.attr("title"));
		}
	});
	
	$("button[name='dbsx']").click(function(){
		openRightWin($(this).attr("data-url"),$(this).attr("data-mkbh"),$(this).attr("data-mkmc"));
	});
	//设置日常业务模块效果
	$(".box-quick-link").hover(function(){
		$(this).css("cursor","pointer");
		$(this).find(".fa").css({"font-size":"50px"});
	},function(){
		$(this).find(".fa").css("font-size","30px");
	});
});

var getDbsxList = function(){
	select_commonWin("${pageContext.request.contextPath}/webView/window/dbsx/dbsxList.jsp","待办事项","920","630");
}

var getJdgzList = function(){
	select_commonWin("${pageContext.request.contextPath}/webView/window/jdgz/jdgzList.jsp","进度跟踪","920","630");
}

var tzgg_list = function(){
	select_commonWin("${pageContext.request.contextPath}/webView/window/tzgg/tzggList.jsp","通知公告","920","630");
}
var tzgg_xx = function(id){
	select_commonWin("${pageContext.request.contextPath}/window/goTzggEdit?gid="+id,"通知公告","920","630");
}
//我的名下资产-更多按钮
var zcxx_list = function(){
	select_commonWin("${pageContext.request.contextPath}/webView/window/zcxx/zcxxList.jsp","我的名下资产","920","630");
}

var getYwls = function(){
	$("ul.ywls li[name='ywls']").remove();
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/index/getYwls",
		data:"month=" + $("#sel_month").val(),
		success:function(val){
			val = $.trim(val);
			val = JSON.getJson(val);
			if(val.success){
				for(var i = 0; i <  val.items.length; i++){
					var html = "";
					if(i < 10){
						var items = val.items;
						html = "<li name='ywls' style='height:55px;" + (i == 0 ? "border-top:solid 1px #eee;" : "") + "'>";
							html += "<div class='pull-left'>";
								html += "<div class='text-center'>" + items[i].rq + "</div><div class='text-center'>" + items[i].sj + "</div>";
							html += "</div>";
							html += "<div class='pull-left' style='margin-left:10px; margin-right:10px;'>";
								html += "<div class='img-circle center-block text-center'><i class='fa " + items[i].tb + "' style='font-size:34px;'></i></div>";
							html += "</div>";
							html += "<div class='pull-left'>";
								html += "<div><strong class='text-primary'>" + items[i].lx + "</strong></div>";
								html += "<div class='text-muted'>";
								if(items[i].url == ""){
									html += items[i].sm;
								}
								else{
									html += "<a href='javascript:void(0);' style='color:#999999;' xqlj='http://${pageContext.request.contextPath}/" + items[i].url + "' onclick='getYwlsXq(this)'>" + items[i].sm + "</a>"; 
								}
								html +=  "</div>";
							html += "</div>";
						html += "</li>";
					}
					else{
						html = "<li name='ywls' style='height:55px;'></li>";
					}
					$("ul.ywls").append(html);
				}
			}
			else{
				$("ul.ywls").append("<li name='ywls' style='height:55px;padding-top:18px;'>" + val.msg + "</li>");
// 				for(var i = 0; i < 9; i++){
					//$("ul.ywls").append("<li name='ywls' style='height:55px;'></li>");
// 				}
			}
		},
		error:function(val){
			alert(val);
		}
	});
}

var getYwlsXq = function(ele){
	var dom = $(ele);
	select_commonWin(dom.attr("xqlj"),"我的业务流水详细信息","920","630");
}

var getYwlsList = function(){
	select_commonWin("${pageContext.request.contextPath}/webView/window/ywls/ywlsList.jsp?month=" + $("#sel_month").val(),"我的业务流水","920","630");
}

var openRightWin = function(_url,_id,title){
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
</body>
</html>

