<%@page import="com.googosoft.constant.MenuFlag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" uri="/webView/tlds/fns.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产管理系统主页</title>
<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/public/iconfont.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/index/mainNew.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/public/font-awesome.css" rel="stylesheet" type="text/css">
<style type="text/css">
	body{
		font-size: 12px;
	}
	.col-xs-3{
		width: 32%;
	}
	.col-xs-1{
		width: 1%;
	}
	.p1notice{
		margin-left:3%;
	}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-6 col-xs-12">
				<div class="box grxx">
					<div class="box-header">
						<span class="title">个人信息</span>
						<div class="actions">
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<div class="box-content" style="padding-bottom:2px;">
						<div class="row">
							<div class="col-xs-4">
								<div class="media">
									<div class="media-left">
										<a href="javascript:void(0);" onclick="grxx_xx()">
											<img class="media-object img-thumbnail" style="width:64px;height:64px;" src="${url}" alt="头像">
										</a>
									</div>
									<div class="media-body">
										<p>欢迎您，${fns:getRyXm()}</p>
										<p>今天是<span id="date"></span></p>
										<p><span id="week"></span></p>
									</div>
								</div>
							</div>
							<div class="col-xs-8">
								<div class="container-fluid">
									<div class="row">
										<div class="item col-xs-3" style="float: left;height: 70px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/index/db.png);">
											<div style="padding-left: 74px;padding-top: 6px;font-size: 14px;font-weight: bold;">
												<a class="oth" href="javascript:void(0)" onclick="ywdb_xx11()">0</a>
											</div>
											<div style="padding-left: 78px;padding-right: 10px;">待办业务</div>
										</div>
										<div  class="item col-xs-1" style="float: left;">&ensp;</div>
										<div class="item col-xs-3" style="float: left;height: 70px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/index/th.png);">
											<div style="padding-left: 74px;padding-top: 6px;font-size: 14px;font-weight: bold;">
												<a class="oth" href="javascript:void(0)" onclick="ywbh_xx11()">0</a>
											</div>
											<div style="padding-left: 78px;padding-right: 10px;">驳回业务</div>
										</div>
										<div class="item col-xs-1" style="float: left;">&ensp;</div>
										<div class="item col-xs-3" style="float: left;height: 70px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/index/cg.png);">
											<div style="padding-left: 74px;padding-top: 6px;font-size: 14px;font-weight: bold;">
												<a class="oth" href="javascript:void(0)" onclick="ywcg_xx11()">0</a>
											</div>
											<div style="padding-left: 78px;padding-right: 10px;">业务草稿</div>
										</div>
										<%-- <div class="col-md-4 item">本月建账
											<a href="javascript:void(0)" onclick="zcjz_xx('m')">${mjzsl}</a>
										</div>
										<div class="col-md-4 item">本年建账
											<a href="javascript:void(0)" onclick="zcjz_xx('y')">${yjzsl }</a>
										</div>
										<div class="col-md-4 item">本月变动
											<a href="javascript:void(0)" onclick="zcbd_xx('m')">${mbdsl }</a>
										</div>
										<div class="col-md-4 item">本年变动
											<a href="javascript:void(0)" onclick="zcbd_xx('y')">${ybdsl }</a>
										</div>
										<div class="col-md-4 item">本月处置
											<a href="javascript:void(0)" onclick="zccz_xx('m')">${mczsl }</a>
										</div>
										<div class="col-md-4 item">本年处置
											<a href="javascript:void(0)" onclick="zccz_xx('y')">${yczsl }</a>
										</div> --%>
										<%--  新版业务清单
										<div class="col-md-11 item">
											<a href="javascript:void(0)" onclick="ywdb_xx()">${shsl }</a>
											待办理的业务清单
										</div><div class="col-md-4 item">
											本月处置
											<a href="javascript:void(0)" onclick="zccz_xx('m')">${mczsl }</a>
										</div>
										<div class="col-md-4 item">
											本年处置
											<a href="javascript:void(0)" onclick="zccz_xx('y')">${yczsl }</a>
										</div> --%>
										<%-- <div class="col-md-11 item">
											<a href="javascript:void(0)" onclick="ywbh_xx()">${bhsl }</a>
											被驳回的业务清单
										</div>
										<div class="col-md-11 item">
											<a href="javascript:void(0)" onclick="ywcg_xx()">${cgsl }</a>
											业务草稿集锦
										</div> --%>
									</div>
								</div>
							</div>
							<div class="col-xs-12">
								<div class="zntx">
									<div class="zntx-header">
										<span class="title"><!-- <i class="faw fa-exclamation-circle"></i> -->智能提醒</span>
<!-- 										<button class="btn btn-link btn-sm more pull-right">更多></button> -->
									</div>
									<hr class="hr-normal">
									<div class="zntx-content" style="min-height:71px;">
										<ul class="list-unstyled">
											<c:choose>
												<c:when test="${syrqr.SL == 0 && zclssl >= 20}">
													<li><span>暂无提醒信息</span></li>
												</c:when>
												<c:otherwise>
<%-- 													<c:if test="${glyjz.SL > 0}"> --%>
<!-- 														<li> -->
<%-- 															<span>您有&ensp;<span class="text-danger">${glyjz.SL}</span>&ensp;条待处理的【管理员建账】信息</span> --%>
<%-- 															<small><a class="pull-right" data-url="${pageContext.request.contextPath}${glyjz.URL}" data-mkbh="${glyjz.mkbh }" data-mkmc="${glyjz.mkmc }">处理></a></small> --%>
<!-- 														</li> -->
<%-- 													</c:if> --%>
													<c:if test="${syrqr.SL > 0}">
														<li>
															<span>您有&ensp;<span class="text-danger">${syrqr.SL}</span>&ensp;条待确认的【使用人确认】信息</span>
															<small><a class="pull-right" data-url="${pageContext.request.contextPath}${syrqr.URL}" data-mkbh="${syrqr.mkbh }" data-mkmc="${syrqr.mkmc }">处理></a></small>
														</li>
													</c:if>
													<c:if test="${zclssl < 20}">
														<li>
															<span>资产流水号仅剩&ensp;<span class="text-danger">${zclssl}</span>&ensp;个，为避免影响使用，请尽快到“系统设置--系统运行参数设置”中设置</span>
														</li>
													</c:if>
												</c:otherwise> 
											</c:choose>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-xs-12">
				<div class="box cygn">
					<div class="box-header">
						<span class="title">通知公告</span>
						<div class="actions">
							<a href="javascript:void(0);" id="btn-quick-link" class="btn btn-sm" style="color:#A8A8A8;" title="点击设置常用功能" data-url="${pageContext.request.contextPath}/desk/goSetrcyw"><i class="fa icon-zititubiao20"></i></a>
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<div class="box-content" style="height:197px;">
						<div class="container-fluid"  style = "padding-left:0">
							<div class="row">
								<p class="p1notice">[科研经费管理科]&nbsp;2017学年本科生（在校生）交费通知<span style="float:right;">2017-09-21</span></p>
								<p class="p1notice">[资金管理科]&nbsp;2017学年本科生（在校生）交费通知<span style="float:right;">2017-09-18</span></p>
								<p class="p1notice">[资金管理科]&nbsp;2017学年研究生（在校生）交费通知<span style="float:right;">2017-09-14</span></p>
								<p class="p1notice">[资金管理科]&nbsp;2017学年本科生（在校生）交费通知<span style="float:right;">2017-09-11</span></p>
								<p class="p1notice">[科研经费管理科]&nbsp;2017学年本科生（在校生）交费通知<span style="float:right;">2017-08-25</span></p>
								<p class="p1notice">[科研经费管理科]&nbsp;2017学年本科生（在校生）交费通知<span style="float:right;">2017-06-21</span></p>
<%-- 								<c:if test="${rcyw.size()==0}"> --%>
<!-- 									<div class="col-xs-12 " style="color:#747474;font-size:12px">暂无常用功能，请点击右上角【设置】按钮进行设置！</div> -->
<%-- 								</c:if> --%>
<%-- 								<c:forEach var="rcyw" items="${rcyw}" varStatus="i"> --%>
<%-- 									<div class="col-xs-2 text-center box-quick-link" data-url="${pageContext.request.contextPath}${rcyw.URL}" data-mkbh="${rcyw.MKBH}" style="margin-top:10px;color:#747474;"> --%>
<%-- 										<div><i class='fa ${rcyw.ICON}'></i></div> --%>
<%-- 										<div class="text-center"> <span class="name" style="white-space:nowrap;width:100%;display:inline-block;overflow:hidden;text-overflow:ellipsis;" title="${rcyw.MKMCNEW}">${rcyw.MKMCNEW}</span></div> --%>
<!-- 									</div> -->
<%-- 								</c:forEach> --%>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6 col-xs-12">
				<div class="box jdgz">
					<div class="box-header">
						<span class="title">预算执行情况分析</span>
						<div class="actions">
<!-- 							<a href="javascript:void(0);" class="btn btn-sm more" onclick="getJdgzList();" title="点击查看更多信息">更多></a>  -->
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-striped table-bordered table-hover">
<%-- 						    <c:if test="${jdgz.size() != 0}"> --%>
							    <thead>
								    <tr>
									    <th>机构</th>
									    <th>预算金额（万元）</th>
									    <th>支出金额（万元）</th>
									    <th>执行率</th>
								    </tr>
							    </thead>
							    <tbody>
							  		  <tr>
											<td>党政办公室</td>
											<td style="text-align: right;">2350.00</td>
											<td style="text-align: right;">1780.00</td>
											<td style="text-align: right;">85%</td>
										</tr>
										 <tr>
											<td>教学科研处</td>
											<td style="text-align: right;">2546.00</td>
											<td style="text-align: right;">2045.00</td>
											<td style="text-align: right;">75%</td>
										</tr>
										<tr>
											<td>学生工作处</td>
											<td style="text-align: right;">2350.00</td>
											<td style="text-align: right;">1654.00</td>
											<td style="text-align: right;">84%</td>
										</tr>
										<tr>
											<td>后勤保卫处</td>
											<td style="text-align: right;">1800.00</td>
											<td style="text-align: right;">1232.00</td>
											<td style="text-align: right;">79%</td>
										</tr>
										<tr>
											<td>教务处</td>
											<td style="text-align: right;">2546.00</td>
											<td style="text-align: right;">2232.00</td>
											<td style="text-align: right;">89%</td>
										</tr>
										<tr>
											<td>校长办公室</td>
											<td style="text-align: right;">1546.00</td>
											<td style="text-align: right;">1032.00</td>
											<td style="text-align: right;">85%</td>
										</tr>
<%-- 									<c:forEach var="jdgz" items="${jdgz}" varStatus="i" end="5"> --%>
<!-- 										<tr> -->
<%-- 											<td>${jdgz.LX}</td> --%>
<%-- 											<td>${jdgz.BH}</td> --%>
<%-- 											<td>${jdgz.MC}</td> --%>
<%-- 											<td>${jdgz.JD}</td> --%>
<!-- 										</tr> -->
<%-- 									</c:forEach> --%>
<%-- 								</c:if> --%>
							</tbody>
<%-- 							<c:if test="${jdgz.size() == 0}"> --%>
<!-- 								<div class="col-xs-12" style="margin-top:10px;color:#747474;font-size:12px">暂无进度跟踪信息！</div> -->
<%-- 							</c:if> --%>
						</table>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-xs-12">
				<div class="box mxzc">
					<div class="box-header">
						<span class="title">收支情况分析</span>
<%-- 						<div class="alert alert-info" style="padding:0px 4px;margin-left:10px;font-size:13px;display:inline-block;">共${grzchj.sl}套/件，总价值${grzchj.zzj}元</div> --%>
						<div class="actions">
<!-- 							<a href="javascript:void(0);" class="btn btn-sm more" onclick="zcxx_list();" title="点击查看更多信息">更多></a>  -->
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<div class="box-content" id="echartsnew" style="height:260px;">
<!-- 						<table class="table table-striped table-bordered table-hover"> -->
						<%-- <c:if test="${grzc.size() != 0}">
							<thead>
								<tr>
									<th>资产编号</th>
									<th>资产名称</th>
									<th>套/件数</th>
<!-- 									<th>总价</th> -->
									<th>存放地点</th>
									<th>规格</th>
									<th>型号</th>
								</tr>
							</thead>
						<tbody>
						<c:forEach var="grzc" items="${grzc}" end="5">
							<tr>
								<td><span class="btn-link zclook" style="cursor: pointer" yqbh="${grzc.YQBH}" path="${pageContext.request.contextPath}">${grzc.YQBH}</span></td>
								<td>${grzc.YQMC}</td>
								<td class="text-right">${grzc.SYKH}</td>
								<td class="text-right">${grzc.ZZJ}</td>
								<td>${grzc.BZXX}</td>
								<td>${grzc.GG}</td>
								<td>${grzc.XH}</td>
							</tr>
						</c:forEach>
					</tbody>
				    </c:if> --%>
<%-- 					<c:if test="${grzc.size() == 0}"> --%>
<!-- 						<div class="col-xs-12" style="margin-top:10px;color:#747474;font-size:12px">暂无个人名下资产信息！</div> -->
<%-- 					</c:if> --%>
<!-- 				</table> -->
			</div>
		    </div>
			</div>
<!-- 			<div class="col-xs-12"> -->
<!-- 				<div class="box"> -->
<!-- 					<div class="box-header"> -->
<!-- 						<span class="title">资产生命周期</span> -->
<!-- 						<div class="actions"> -->
<!-- 							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="box-content"> -->
<!-- 						<div class="timeline"> -->
<!-- 							<div class="container-fluid"> -->
<!-- 								<div class="row"> -->
<!-- 									<div class="line"></div> -->
<!-- 									<div class="col-xs-3"> -->
<!-- 										<div class="item center-block" data-title="zcjz" title="点击查看业务说明"> -->
<!-- 											<i class="fa icon-shouhuoruku"></i> <span>资产建账</span> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="col-xs-3"> -->
<!-- 										<div class="item center-block" data-title="zcbd" title="点击查看业务说明"> -->
<!-- 											<i class="fa icon-shujutubiao17"></i> <span>资产变动</span> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="col-xs-3"> -->
<!-- 										<div class="item center-block" data-title="zcwx" title="点击查看业务说明"> -->
<!-- 											<i class="fa icon-weixiu"></i> <span>资产维修</span> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="col-xs-3"> -->
<!-- 										<div class="item center-block" data-title="zccz" title="点击查看业务说明"> -->
<!-- 											<i class="fa icon-17feiqi"></i> <span>资产处置</span> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/static/plugins/datatable/datatables.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/plugins/datetimepicker/bootstrap-datetimepicker.js"></script>
	<script src="${pageContext.request.contextPath}/static/javascript/public/public-js.js"></script>
	<script src="${pageContext.request.contextPath}/static/javascript/public/public-window.js"></script>
	<script src="${pageContext.request.contextPath}/static/plugins/echarts/echarts.js"></script>  
	<script type="text/javascript">
	     $(function(){
	    	 
	    	 var myChart = echarts.init(document.getElementById('echartsnew'));
	    	 
	    	 
	    	 option = {
	    			    title: {
// 	    			        text: '折线图堆叠'
	    			    },
	    			    tooltip: {
	    			        trigger: 'axis'
	    			    },
	    			    legend: {
	    			        data:['收入','支出']
	    			    },
	    			    grid: {
	    			        left: '3%',
	    			        right: '4%',
	    			        bottom: '3%',
	    			        containLabel: true
	    			    },
	    			    toolbox: {
	    			        feature: {
	    			            saveAsImage: {}
	    			        }
	    			    },
	    			    xAxis: {
	    			        type: 'category',
	    			        boundaryGap: false,
	    			        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	    			    },
	    			    yAxis: {
	    			        type: 'value'
	    			    },
	    			    series: [
	    			        {
	    			            name:'收入',
	    			            type:'line',
	    			            stack: '总量',
	    			            data:[-10000,-5000,15000,35000,12000,0,30010,61000,65000,45000,35000,15000]
	    			        },
	    			        {
	    			            name:'支出',
	    			            type:'line',
	    			            stack: '总量',
	    			            data:[25000,5000,-10000,-30000,12000,0,40010,41000,55000,35000,25000,5000]
	    			        }
	    			    ]
	    			};
	    	 myChart.setOption(option);
	    	bindDate();
	        /* $("#kj .item").click(function(){
	        	var cxtj = $(this).attr("id");
	        	if("tmdy" == cxtj || "txlcx" == cxtj){
	        		select_commonWin("${pageContext.request.contextPath}/webView/system/index/select_kj.jsp?cxtj="+cxtj,"", "400", "130");
	        	} else {
	        		select_commonWin("${pageContext.request.contextPath}/webView/system/index/select_kj.jsp?cxtj="+cxtj,"", "400", "92");
	        	}
	        }); */
	        $("#kj .group li").click(function() {
	        	$("#kj .group li").removeClass("cur");
	        	$(this).addClass("cur");
	        	$("#txt_bh").attr("placeholder", "按" + $(this).attr("title") + "查询");
	        });
	        $("#btn_cx").click(function(e){
	        	/* getTopFrame().$("#dshsl").text("123"); */
	    	    var bh = $("#txt_bh").val();
	    		var $name_data = $("#kj .group li.cur").data();
	    		var tj = $("#kj .group li.cur").attr("id");
	    		var name = $("#kj .group li.cur").attr("title");
	       		var path = $name_data.path;
	       		var name2 = $name_data.title;
	       		
	       		if(bh == ""){
	       			if(tj == "txlcx"){
	       				select_commonWin("${pageContext.request.contextPath}" + path,name2,"900","630","yes");
	       			} else {
	       				alert("请先输入要查询的" + name);
	       			}
	       		} else {
	       			$.ajax({
	       				type:"post",
	       				url:"${pageContext.request.contextPath}/index/check?title=" + name + "&cxtj=" + tj + "&dbh="+bh,
	       				dataType:"json",
	       				success:function(val){
	       					close(index);
	       					$("#txt_bh").val("");
	       					if(val.success){
	       			   			select_commonWin("${pageContext.request.contextPath}" + path + bh,name2,"1200","630","yes");
	       					} else {
	       						alert(val.msg);
	       					}
	       				},
	       				error:function(){
	       					close(index);
	       					alert(getPubErrorMsg());
	       				},
	       				beforeSend:function(){
	       					index = loading(2);
	       				}
	       			});
	       		}
	       		return false;
	    	});
			$(".zntx .list-unstyled a").click(function(){
				var $data = $(this).data(); 
				var url = $data.url;
				var mkbh = $data.mkbh;
				var title = $data.mkmc;
				openRightWin(url,mkbh,title);
			});
	        $(".timeline .item").click(function(){
	        	switch($(this).data("title")){
	        		case "zcjz":
	        			select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=<%=MenuFlag.ZCJZ_LYR%>&sfbc=1&desk=1","", "920", "530");
	        			break;
	        		case "zcbd":
	        			select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=<%=MenuFlag.ZCBD_XMBD%>&sfbc=1&desk=1","", "920", "530");
	        			break;
	        		case "zcwx":
	        			select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=<%=MenuFlag.ZCWX_WXSQ%>&sfbc=1&desk=1","", "920", "530");
	        			break;
	        		case "zccz":
	        			select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=<%=MenuFlag.ZCCZ_ZCCZ%>&sfbc=1&desk=1","", "920", "530");
	        			break;
	        		default: break;
	        	}
	        });
	        $("a[name='dbyw']").click(function(){
	    		parent.openRightWin($(this).attr("data-url"),$(this).attr("data-mkbh"),$(this).attr("data-mkmc"));
	    	});
	        $("#btn-quick-link").on("click",function(){
	    		var url = $(this).data("url");
	    		var content = $(this).text();
	    	    select_commonWin(url,content,"900","700","yes");
	    	});
	        $(".box-quick-link").on("click",function(){
	        	var data = $(this).data();
	    		var url = data["url"];
	    		var method = data["method"];
	    		var mkbh = data["mkbh"];
	    		var title = $(this).find(".name").text();
	    		if(method == 'window'){
	    			var content = $(this).find(".content").text();
	    			select_commonWin(url,content,"600","530","yes");
	    		} else {
	    			$.ajax({
	    				type :"post",
	    				url:"${pageContext.request.contextPath}/desk/getSmzqMenu",
	    				data:"mkbh="+mkbh,
	    				success:function(val){
	    					val = $.trim(val);
	    					val = JSON.getJson(val);
    	   					if(val.success == 'true'){
    	   						openRightWin(url,mkbh,title);
    						}else{
    							alert(val.msg);
    						}
	    				},
    					error:function(){
    		                alert("请稍后再试！");
    		            },
	    			});
	    		}
	    	});
	     });
	     function bindDate() {
	    	 var date = new Date();
	    	 var week = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
	    	 var dateStr = date.getFullYear() + "年" + (date.getMonth()+1) + "月" + date.getDate() + "日";
	    	 $("#date").text(dateStr);
	    	 $("#week").text(week[date.getDay()]);
	     }
	     function zcxx_list(){
	    	openRightWin("${pageContext.request.contextPath}/select/goMainPage","060101","我的名下资产");
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
	     var ywcg_xx = function(){
	    	select_commonWin("${pageContext.request.contextPath}/window/dtjxx","业务草稿","400","550");
	     }
	     var ywdb_xx = function(){
	    	select_commonWin("${pageContext.request.contextPath}/window/dbxx","待办业务","400","550");
	     }
	     var ywbh_xx = function(){
	    	select_commonWin("${pageContext.request.contextPath}/window/bhxx","驳回业务","400","550");
	     }
	     var zcjz_xx = function(flag){
	    	openRightWin("${pageContext.request.contextPath}/window/goJzList?flag="+flag,"zcjz_"+flag,"资产建账");
	     }
	     var zcbd_xx = function(flag){
	    	openRightWin("${pageContext.request.contextPath}/window/goBdList?flag="+flag,"zcbd_"+flag,"资产变动");
	     }
	     var zccz_xx = function(flag){
	    	openRightWin("${pageContext.request.contextPath}/window/goCzList?flag="+flag,"zccz_"+flag,"资产处置");
	     }
	     var grxx_xx = function(){
	    	 openRightWin("${pageContext.request.contextPath}/grsz/goGrxxPage?rybh=${rybh}&operateType=U","98","个人信息");
	     }
	     function openRightWin(_url,_id,title){
	    	_url = _url.indexOf("?")>0?_url+"&mkbh="+_id:_url+"?mkbh="+_id;
    		//循环遍历选项卡
    		if(parent.window.$(".J_menuTab").each(function(){
    			return $(this).data("id")==(_url)?($(this).hasClass("active")||($(this).addClass("active").siblings(".J_menuTab").removeClass("active"),
    				parent.e(this),parent.window.$(".J_mainContent .J_iframe").each(
    					function(){
    						return $(this).data("id")==(_url)?($(this).show().siblings(".J_iframe").hide(),!1):void 0
    					}
    				)),parent.window.$(".J_mainContent").find("#iframe_"+_id+"").attr("src",""+_url+""),refunc(),openRightWin=!1,!1):void 0;
    			})
    		){
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