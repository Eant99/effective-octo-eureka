<%@page import="com.googosoft.constant.MenuFlag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" uri="/webView/tlds/fns.tld"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>网上报销审批系统主页</title>
<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/public/iconfont.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/index/mainNew.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/public/font-awesome.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/index/desk.css" rel="stylesheet" type="text/css">
<style type="text/css">
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   		border-bottom-width: 1px!important;
    }
	body{
		font-size: 12px;
	}
	.col-xs-3{
		width: 32%;
	}
	.sx{
	   cursor:pointer;
	}
	.col-xs-1{
		width: 1%;
	}
	.p1notice{
		margin-left:3%;
	}
	.left{
		float:left;
		width:15%;
	}
	.right{
		float:left;
		width:38%;
	}
/* 	div[class|="col"] { */
/* 		margin:1px 1px 1px 1px; */
/* 	} */
	.box{
		border:1px solid #E5E5E5!important;
	}
	.bg{
		background-color:#dadfe4;
	}
	.sbg{
		color:#358aeb;
	}
	.yc{
		display:none!important;
	}
	.ycc{
		display:none;
	}
	.goMenu{
		cursor:pointer;
	}
	.toBtn{
		font-size:14px;
   		font-weight: bold;
/*     	color: #eae9e9; */
	}
	.toEnd{
		font-size:15px;
   		font-weight: bold;
/*    		color: gray; */
	}
	@media screen and (max-width:1920px) {
   		#autoFloat{
   			margin-right:72%!important;
   		}
	}
	@media screen and (min-width:1920px) and (max-width:3000px) {
	    #autoFloat{
   			margin-right:80%!important;
   		}
	}
		.dh1 li{
list-style: none;
float:left;
height: 75px;
    width: 25%;

}
		.lc{ 
		width:1650px;
 		height:260px;
 		algin:center;
 		background-image: url("/CWYTH/webView/system/index/lc.png"); 
 	} 
	#tbody ul{list-style:none;}
	.lli{
	float: right;margin-top: -17px;
	}
	.color{
	  color:black;
	}
	
</style>
</head>
<body style="background-color:#FEFEFE;">
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
String date = sdf.format(new Date());
int dyn = Integer.parseInt(date);
int den = Integer.parseInt(date)-1;
int dsn = Integer.parseInt(date)+1;
%>
	<div class="container-fluid">
		<div class="row"
			style="margin-top: 5px; margin-left: 50px; margin-right: 50px;">
			<div class="col-sm-4 col-xs-12"
				style="float: left; display: inline-block">
				<!------------ 办事大厅 ------------------------------------------------------------------------------------------------------>
				<div class="row"
					style="margin-top: 0px; margin-left: 0px; margin-right: 10px;">
					<div class="col-sm-4 col-xs-12" id="block-bsdt">
						<span class="skip" id="skip-left"
							style="left: 10px;background-image: url(${ctx}/static/images/button/skip-left.png);"></span>
						<span class="skip" id="skip-right"
							style="right: 10px;background-image: url(${ctx}/static/images/button/skip-right.png);"></span>
						<div class="box bsdt" style="">
							<div class="box-header">
								<span class="title">办事大厅 </span>
								<div class="actions">
									<a href="javascript:void(0);"
										class="btn newbox-fold btn-mini btn-link" title="点击折叠或展开"><i
										class="faw fa-angle-up" id="faaaa"></i></a>
								</div>
							</div>
							<div class="box-content bsdt-page" style="margin-top: 1px;">
						<input type="hidden" id="page_count" value="${bsdtPageCount }"/>
						<div class="bsdt-content">
							<c:forEach var="bsdtPage" items="${bsdtPageList }">
								<div class="container-fluid bsdt-show">
									<ul>
									<c:forEach var="zs" items="${bsdtPage.zsList}">
										<li style="width: ${bsdtPage.liWidth};">
										<div class="item col-xs-3 goMenu" id="${zs.sytz }" style="background-image: url(${ctx}/static/images/deskImage/${zs.sytz}.png);">
											<div>${zs.mkmc}</div>
										</div>
										</li>
									</c:forEach>
									</ul>
								</div>
							</c:forEach>
						</div>
					</div>
						</div>
					</div>
				</div>

				<!-- 个人事务 ------------------------------------------------------------------------------------------------------------------------------->
				<div class="row"
					style="margin-top: 5px; margin-left:0px; margin-right: 10px;">
					<div class="col-sm-12 col-xs-12"
						style="float: left; display: inline-block">
						<div class="box grsw">
							<div class="box-header">
								<span class="title">个人事务</span>
								<div class="actions">
									<a href="javascript:void(0);"
										class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i
										class="faw fa-angle-up"></i></a>
								</div>
							</div>
							<div class="box-content"
								style="height: 131px; overflow: auto; margin-left: 0%">
								<ul class="dh1">
									<li>
										<div class="item col-xs-3 sx" id="wdbx"
											style="float: left;height: 90px;padding: 0;width:80px;background-position-x: center;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdbx.png);">
											<div
												style="padding-left: 60px; font-size: 14px; font-weight: bold;">
												<a class="oth" href="javascript:void(0)"
													onclick="ywdb_xx11()"></a>
											</div>
											<div
												style="padding-top: 53px; width: 80px; text-align: center; margin: 0 auto;" class="btn-jrbx">我的报销业务</div>
										</div>
									</li>
									<li>
										<div class="item col-xs-3 sx" id="wdsqsp"
											style="float: left;height: 90px;padding: 0;background-position-x: center;width:80px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdsqsp.png);">
											<div
												style="padding-left: 60px; font-size: 14px; font-weight: bold;">
												<a class="oth" href="javascript:void(0)"
													onclick="ywdb_xx11()"></a>
											</div>
											<div
												style="padding-top: 53px; width: 80px; text-align: center; margin: 0 auto;" class="btn-sqsp">我的事前审批</div>
										</div>
									</li>
									<li>
										<div class="item col-xs-3 sx" id="wdxm"
											style="float: left;padding: 0;background-position-x: center;height: 90px;width:80px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdxm.png);">
											<div
												style="padding-left: 60px; font-size: 14px; font-weight: bold;">
												<a class="oth" href="javascript:void(0)"
													onclick="ywdb_xx11()"></a>
											</div>
											<div
												style="padding-top: 53px; width: 80px;; text-align: center; margin: 0 auto;" class="btn-wdxm">我的项目</div>
										</div>
									</li>
									<li>
										<div class="item col-xs-3 sx hidden" id="wdxz"
											style="float: left;padding: 0;background-position-x: center;height: 90px;width:80px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdxz.png);">
											<div
												style="padding-left: 60px; font-size: 14px; font-weight: bold;">
												<a class="oth" href="javascript:void(0)"
													onclick="ywdb_xx11()"></a>
											</div>
											<div
												style="padding-top: 53px; width: 80px;; text-align: center; margin: 0 auto;" class="hidden">我的薪资</div>
										</div>
									</li>
									<li>
										<div class="item col-xs-3 sx hidden" id="wdzc"
											style="float: left;padding: 0;background-position-x: center;height: 90px;width:80px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdzc.png);">
											<div
												style="padding-left: 60px; font-size: 14px; font-weight: bold;">
												<a class="oth" href="javascript:void(0)"
													onclick="ywdb_xx11()"></a>
											</div>
											<div
												style="padding-top: 53px; width: 80px;; text-align: center; margin: 0 auto;" >我的资产</div>
										</div>
									</li>
									<li>
										<div class="item col-xs-3 sx hidden" id="wdjf"
											style="float: left;padding: 0;background-position-x: center;height: 90px;width:80px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdjf.png);">
											<div
												style="padding-left: 60px; font-size: 14px; font-weight: bold;">
												<a class="oth" href="javascript:void(0)"
													onclick="ywdb_xx11()"></a>
											</div>
											<div
												style="padding-top: 53px; width: 80px;; text-align: center; margin: 0 auto;" >我的缴费</div>
										</div>

									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<!------------------------------- 通知公告 --------------------------------------------------------------------------------------->
				<div class="row"
					style="margin-top: 5px; margin-left: 0px; margin-right: 10px;">
					<!-- <div class="col-sm-12 col-xs-12" style="float: left;display:inline-block">
							<div class="box cygn">
								<div class="box-header">
									<span class="title">通知公告</span>
									<div class="actions">
										<a href="javascript:void(0);" id="btn-quick-link" class="btn btn-sm" style="color:#A8A8A8;" title="点击查看更多""></a>
										<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
									</div>
								</div>
								<div class="box-content" style="height:110px;">
									<div class="container-fluid"  style = "padding-left:0">
										<div id="tbody" class="row" style="margin-left:10px">
										<input type="hidden" name="guid">
								
										</div>
									</div>
								</div>
							</div>
						</div> -->
				</div>

			</div>
			<!-- 今日报销 ---------------------------------------------------------------------------------------------------->
			<div class="col-sm-8 col-xs-12">
				<div class="box cygn">
					<div class="box-header">
						<span class="title">缴费情况分析</span>
						<div class="actions">
							<a href="javascript:void(0);"
								class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i
								class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<!-- <div class="row" style="margin-top:10px;margin-left:40px;margin-right:10px;">
							<div class="col-sm-12 col-xs-12">
								<div class="box-content">
									<div class="" id="jrbx" style="height:153px;"></div>
								</div>
							</div>
						</div> -->
					<div class="row"
						style="margin-top: 5px; margin-left: 0px; margin-right: 0px;">
						<div class="box-content" style="height: 290px">
							<div class="col-sm-12 col-xs-12"
								style="float: left; height: 301px">
								<!-- <table style="border: 1px;line-height: 30px; width:100%;" > -->
								<table class=" table-striped table-bordered table-hover"
									style="border-collapse: collapse !important; line-height: 30px; width: 100%;">

									<tr style="text-align: center;">
										<td colspan="2">学费（元）</td>

										<td colspan="2">住宿费（元）</td>

										<td colspan="2">教材费（元）</td>
										<td rowspan="2"
											style="display: table-cell; vertical-align: middle">学期</td>

									</tr>
									<tr style="text-align: center;">
										<td>应缴费用</td>
										<td>实缴费用</td>
										<td>应缴费用</td>
										<td>实缴费用</td>
										<td>应缴费用</td>
										<td>实缴费用</td>
									</tr>
									<tbody style="text-align: right;">
										<tr>
											<td>4500.00</td>
											<td>4500.00</td>
											<td>800.00</td>
											<td>800.00</td>
											<td>3562.00</td>
											<td>3562.00</td>
											<td style="text-align: center;">2017年下半学期</td>
										</tr>
										<tr>
											<td>4500.00</td>
											<td>4500.00</td>
											<td>800.00</td>
											<td>800.00</td>
											<td>3125.00</td>
											<td>3125.00</td>
											<td style="text-align: center;">2017年上半学期</td>
										</tr>
										<tr>
											<td>4500.00</td>
											<td>4500.00</td>
											<td>800.00</td>
											<td>800.00</td>
											<td>3214.00</td>
											<td>3214.00</td>
											<td style="text-align: center;">2016年下半学期</td>
										</tr>
										<tr>
											<td>4500.00</td>
											<td>4500.00</td>
											<td>800.00</td>
											<td>800.00</td>
											<td>4123.00</td>
											<td>4123.00</td>
											<td style="text-align: center;">2016年上半学期</td>
										</tr>
										<tr>
											<td>4500.00</td>
											<td>4500.00</td>
											<td>800.00</td>
											<td>800.00</td>
											<td>3641.00</td>
											<td>3641.00</td>
											<td style="text-align: center;">2015年下半学期</td>
										</tr>
										<tr>
											<td>4500.00</td>
											<td>4500.00</td>
											<td>800.00</td>
											<td>800.00</td>
											<td>2145.00</td>
											<td>2145.00</td>
											<td style="text-align: center;">2015年上半学期</td>
										</tr>
										<tr>
											<td>4500.00</td>
											<td>4500.00</td>
											<td>800.00</td>
											<td>800.00</td>
											<td>4361.00</td>
											<td>4361.00</td>
											<td style="text-align: center;">2014年下半学期</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row"
			style="margin-top: 5px; margin-left: 50px; margin-right: 50px;">
			<div class="col-sm-4 col-xs-12"
				style="float: left; display: inline-block">

				<!------------------------------- 通知公告 --------------------------------------------------------------------------------------->
				<div class="row"
					style="margin-top: 5px; margin-left: 40px; margin-right: 10px;">
					<div class="col-sm-12 col-xs-12"
						style="float: left; display: inline-block">
						<div class="box cygn">
							<div class="box-header">
								<span class="title">通知公告</span>
								<div class="actions">
									<a href="javascript:void(0);" id="btn-quick-link"
										class="btn btn-sm" style="color: #A8A8A8;" title="点击查看更多">更多</a>
									<a href="javascript:void(0);"
										class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i
										class="faw fa-angle-up"></i></a>
								</div>
							</div>
							<div class="box-content" style="height: 305px;">
								<div class="container-fluid" style="padding-left: 0">
									<div id="tbody" class="row" style="margin-left: 10px">
										<input type="hidden" name="guid">

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
			<!-- 今日报销 ---------------------------------------------------------------------------------------------------->
			<div class="col-sm-8 col-xs-12">
				<div class="row" style="">
					<div class="col-sm-6 col-xs-12"
						style="float: left; display: inline-block">
						<div class="box cygn" style="margin-top: 7px; margin-left: 12px">
							<div class="box-header">
								<span class="title">消费情况监控</span>
								<div class="actions">
									<a href="javascript:void(0);"
										class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i
										class="faw fa-angle-up"></i></a>
								</div>
							</div>
							<div class="box-content" style="height: 305px;">
								<div class="row"
									style="margin-top: 5px; margin-left: 0px; margin-right: 0px;">
									<div class="col-sm-12 col-xs-12"
										style="float: left; height: 300px">
										<table class=" table-striped table-bordered table-hover"
											style="border-collapse: collapse !important; line-height: 30px; width: 100%;">
											<thead>
												<tr style="text-align: center;">
													<td style="width: 30%;">消费时间</td>
													<td style="width: 50%;">消费地点</td>
													<td style="width: 20%;">消费金额</td>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td style="text-align: center;">2017-12-13 18:32</td>
													<td style="text-align: center;">第四餐厅</td>
													<td style="text-align: right;">12.00</td>

												</tr>
												<tr>
													<td style="text-align: center;">2017-12-13 17:32</td>
													<td style="text-align: center;">第四餐厅</td>
													<td style="text-align: right;">12.00</td>

												</tr>
												<tr>
													<td style="text-align: center;">2017-12-11 15:32</td>
													<td style="text-align: center;">超市</td>
													<td style="text-align: right;">52.00</td>

												</tr>
												<tr>
													<td style="text-align: center;">2017-12-10 14:32</td>
													<td style="text-align: center;">第一餐厅</td>
													<td style="text-align: right;">10.50</td>

												</tr>
												<tr>
													<td style="text-align: center;">2017-12-10 12:32</td>
													<td style="text-align: center;">超市</td>
													<td style="text-align: right;">35.50</td>

												</tr>
												<tr>
													<td style="text-align: center;">2017-12-07 11:32</td>
													<td style="text-align: center;">第四餐厅</td>
													<td style="text-align: right;">12.00</td>

												</tr>
												<tr>
													<td style="text-align: center;">2017-12-07 10:32</td>
													<td style="text-align: center;">第四餐厅</td>
													<td style="text-align: right;">41.00</td>

												</tr>
												<tr>
													<td style="text-align: center;">2017-12-03 08:32</td>
													<td style="text-align: center;">第四餐厅</td>
													<td style="text-align: right;">11.50</td>

												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12"
						style="float: left; display: inline-block">
						<!------------ 奖助学金分布 ------------------------------------------------------------------------------------------------------>
						<div class="box cygn"
							style="margin-left: 12px; margin-top: 7px; margin-right: 10px">
							<div class="box-header">
								<span class="title">奖助学金分布</span>
								<div class="actions">
									<a href="javascript:void(0);"
										class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i
										class="faw fa-angle-up"></i></a>
								</div>
							</div>
							<div class="row"
								style="margin-top: 10px; margin-left: 40px; margin-right: 10px;">
								<div class="col-sm-12 col-xs-12">
									<div class="box-content" style="height: 295px">
										<div class="" id="yssr" style="height: 250px;"></div>
									</div>
								</div>
							</div>

						</div>

					</div>
				</div>
			</div>
		</div>





		<!-- 				//------------------------------------------------------- -->
		<!-- 			<div class="row" style="margin-top:5px;margin-left:90px;margin-right:10px;"> -->
		<!-- 				<div class="col-sm-12 col-xs-12"> -->
		<!-- 					<div class="box yssz"> -->
		<!-- 						<div class="box-header"> -->
		<!-- 							<span class="title">我的项目</span> -->
		<!-- 							<div class="actions"> -->
		<!-- 								<a href="javascript:void(0);" id="btn-quick-link" class="btn btn-sm" style="color:#A8A8A8;" title="点击查看更多""></a> -->
		<!-- 								<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a> -->
		<!-- 							</div>	 -->
		<!-- 						</div> -->
		<!-- 						<div class="box-content" style="height:250px;"> -->
		<%-- 							<center> --%>
		<!-- 								<div style="width:100px;"> -->
		<!-- 								<select id="drp_spzt" class="form-control"name="shzt" table="K" style="height:30px;"> -->
		<%-- 									    <option value="de" ><%=den %>年</option>	 --%>
		<%-- 										<option value="dy" selected="selected"><%= dyn%>年</option> --%>

		<%-- 										<option value="ds" ><%=dsn %>年</option>					 --%>
		<!-- 									</select> -->
		<!-- 							    </div> -->
		<%-- 						    </center> --%>
		<!-- 						<div class="" id="ysrfx" style="height:200px;"> -->

		<!-- 						</div> -->
		<!-- 						</div> -->

		<!-- 					</div> -->
		<!-- 				</div> -->
		<!-- 			</div> -->

		<!-- 报销流程 -------------------------------------------------------------------------------------------------------------------------------->
		<!-- <div class="col-sm-12 col-xs-12">
				<div class="box jdgz">
					<div class="box-header">
						<span class="title">报销流程</span>
						<div class="actions">
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<div class="box-content" style="height:260px;">
						<div class="lc" style="text-algin:center;">
						</div>
                    <img alt="" src="/AHCW/webView/system/index/lc.png" width="100%" height="120%";>
					</div>
				</div>
			</div> -->


	</div>
	<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/static/plugins/datatable/datatables.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/plugins/datetimepicker/bootstrap-datetimepicker.js"></script>
	<script src="${pageContext.request.contextPath}/static/javascript/public/public-js.js"></script>
	<script src="${pageContext.request.contextPath}/static/javascript/public/public-window.js"></script>
    <script src="${pageContext.request.contextPath}/static/plugins/echarts/echarts.js"></script> 
	<script src="${pageContext.request.contextPath}/webView/system/index/newPicture.js"></script>
	<script src="${pageContext.request.contextPath}/static/javascript/index/newDesk.js"></script>  
	<script type="text/javascript">
// 	$("td").css("text-align","center");
// myChart();

	 var myChart1 = echarts.init(document.getElementById('yssr'));
	 option1 = {
			    title : {
// 			        text: '某站点用户访问来源',
// 			        subtext: '纯属虚构',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: ['奖学金(元)','助学金(元)']
			    },
			    series : [
			        {
// 			            name: '',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[
			                {value:119, name:'奖学金(元)'},
			                {value:105, name:'助学金(元)'}
			            ],
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};

 	myChart1.setOption(option1);

	 $(function(){
		 var myChart = echarts.init(document.getElementById('jzxj'));
		 option = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        x: 'right',
			        data:['日常报销占总比例','公务接待占总比例','差旅费报销占总比例']
			    },
			    series: [
			        {
			            name:'',
			            type:'pie',
			            radius: ['50%', '70%'],
			            center:['40%','50%'],
			            avoidLabelOverlap: false,
			            label: {
			                normal: {
			                    show: false,
			                    position: 'center'
			                },
			                emphasis: {
			                    show: true,
			                    textStyle: {
			                        fontSize: '15',
			                        fontWeight: 'bold'
			                    }
			                }
			            },
			            labelLine: {
			                normal: {
			                    show: false
			                }
			            },
			            data:[
			                {value:119, name:'日常报销占总比例'},
			                {value:368, name:'公务接待占总比例'},
			                {value:234, name:'差旅费报销占总比例'},
			            ]
			        }
			    ]
			};
	 	myChart.setOption(option);
	});
	 $(function(){
		 $("#drp_spzt").change(function(){
			 myZztChart(); 
		 });
		
		 datas();
		 myZztChart();
	    	//表格的绘制
	    	var istbodynull;
	    	function datas(){
	    		//var sel = $("#sel").val();
	    		
	    		
	    	}
	    	function myZztChart(){
	    		var sbnd=$("#drp_spzt").val();
	    		if(sbnd=="dy"){
	    			sbnd1=<%=dyn%>
	    		}else if(sbnd=="de"){
	    			sbnd1=<%=den%>
	    		}else{
	    			sbnd1=<%=dsn%>
	    		}
	    		var myChart = echarts.init(document.getElementById('ysrfx'));
	    		myChart.setOption({
	    			tooltip : {
				         trigger: 'axis',
				          axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				             type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				         } 
				     },
				     legend: {
				         data:['收入预算','基本支出预算','项目支出预算']
				     }, 
				      grid: {
				         left: '3%',
				         right: '4%',
				         bottom: '3%',
				         containLabel: true
				     }, 
				     xAxis : [
				         {
				             type : 'category',
				             data : []
				         }
				     ],
				     yAxis : [
				         {
				        	 name: '单位（万元）',
				 	        // 坐标轴名字位置，支持'start' | 'middle' | 'end'
				 	       //nameLocation: 'end',
				             type : 'value'
				         }
				     ], 
				     series : [      
				         {
				             name:'收入预算',
				              type:'bar',
				             stack: '广告', 
				             data:[]
				         },
				         {
				             name:'基本支出预算',
				             type:'bar',
				             stack: '广告',
				             data:[]
				         },
				         {
				             name:'项目支出预算',
				             type:'bar',
				             stack: '广告',
				             data:[]
				         }
				    ] 
	    		    
	    		});
	    		$.ajax({
	    			url:"${ctx}/index/getZztData123",
	    			data:"sbnd="+sbnd1,
	    			async:false,
	    			dataType:"json",
	    			success:function(data1){
	    				var mc=data1[0].MC;
	    				var sr=data1[0].SR;
	    				var strs= new Array();
	    				sr= sr.split(",");
	    				
	    				var jb=data1[0].JB;
	    				var strsjb = new Array();
	    				
	    				var jb=jb.split(",");
	    				var xm= data1[0].XM;
	    				var strsxm= new Array();
	    				var xm=xm.split(",") ;
	    			    				
	    				var mclist = new Array();
	    				var mclist1= new Array();
	    				mclist = mc.split(",");	    				
	    				myChart.setOption({
	    					
	    					
					         xAxis: {
						            data: mclist

					        }, 
					        series: [ {
					             name:'收入预算',
					              type:'bar',
					             stack: '广告', 
					             data:sr
					         },
					         {
					             name:'基本支出预算',
					             type:'bar',
					             stack: '广告',
					             data:jb
					         },
					         {
					             name:'项目支出预算',
					             type:'bar',
					             stack: '广告',
					             data:xm
					         }
					        
					        
					        ]
					    });
	    				
	    				
	    			}
	    			
	    			
	    		});
	    
	    	
	    	}
	 });
	 $.ajax({
			url:"${ctx}/index/getTzggList",
			data:"",
			async:false,
			dataType:"json",
			success:function(data){	    				
				if(data){
					$.each(data,function(i,v){
						var index = i+1;
						var guid =v.GID;
						var maxlength=window.screen.width;
// 						alert(maxlength);
						
						if(maxlength<=800){
							$("#tbody").append("<ul>"+
									"<input type='hidden' name = 'gid' value="+guid+">"+
									"<li ><div id='dd'><a href='javascript:void(0);' class=' btn_look color'>"+resetTzggTitle(v.TITLE,3)+"</a>  </div></li>"+     								
									"<li class='lli' style='text-align:right;'>"+"["+v.FBSJ+"]"+"</li>" 
							);
						}else if(maxlength>800&&maxlength<=1280){
							$("#tbody").append("<ul>"+
									"<input type='hidden' name = 'gid' value="+guid+">"+
									"<li ><div id='dd'><a href='javascript:void(0);' class=' btn_look color'>"+resetTzggTitle(v.TITLE,16)+"</a> </div></li>"+     								
									"<li class='lli' style='text-align:right;'>"+"["+v.FBSJ+"]"+"</li>" 
							);
						}else if(maxlength>1280&&maxlength<=1600){
							$("#tbody").append("<ul>"+
									"<input type='hidden' name = 'gid' value="+guid+">"+
									"<li ><div id='dd' ><a href='javascript:void(0);' class=' btn_look color'>"+resetTzggTitle(v.TITLE,20)+"</a></div></li>"+     								
									"<li class='lli' style='text-align:right;'>"+"["+v.FBSJ+"]"+"</li>" 
							);	
						}else{
							$("#tbody").append("<ul>"+
									"<input type='hidden' name = 'gid' value="+guid+">"+
									"<li ><div id='dd' ><a href='javascript:void(0);' class=' btn_look color'>"+resetTzggTitle(v.TITLE,37)+"</a></div></li>"+     								
									"<li class='lli' style='text-align:right;'>"+"["+v.FBSJ+"]"+"</li>" 
							);	
						}
					});
//				guid = guid.substring(0,guid.lastIndexOf(","));
//    					$("[name='gid']").val(guid);
//  					alert(guid);
					//autoRowSpan(tbody,0,0);
				}
			}
		});
	//查看操作
	 $(document).on("click",".btn_look",function(){
    		
 		var gid = $(this).parents("ul").find("[name='gid']").val();
 		select_commonWin("${ctx}/window/goTzggEdit?operateType=L&gid="+gid,"通知公告", "920", "630");
 	});
    $(function(){
   //通知公告more
   	 $(document).on("click",".btn-sm",function(){
   		 var menuName = '通知公告';
   		 var parentObj = $("[data-name='"+menuName+"']",window.parent.document);
   			parent.kz(parentObj);
   			parent.n(parentObj);
   	 	}); 
    	
    	
    	
    	
    	
   	 
  // 	 var myChart = echarts.init(document.getElementById('echartsnew'));
   	 
   	 
   	 option = {
   			    title: {
//    			        text: '折线图堆叠'
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
   			            data:[10000,50000,15000,35000,12000,0,30010,61000,65000,45000,35000,15000]
   			        },
   			        {
   			            name:'支出',
   			            type:'line',
   			            stack: '总量',
   			            data:[25000,5000,10000,30000,12000,0,40010,41000,55000,35000,25000,5000]
   			        }
   			    ]
   			};
   	// myChart.setOption(option);
   	bindDate();
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
  //点击菜单时，让返回主页按钮展示
    $(".goMenu").click(function(){
    	$("#li_goBackIndex",parent.document).show(); 
    	$("#d_index_tabs",parent.document).show();
    	$("#d_index_tabs",parent.document).find(".page-tabs-content").html("");
    });
    //点击菜单时，让返回主页按钮展示
    $(".btn-sm").click(function(){
    	$("#li_goBackIndex",parent.document).show(); 
    });
  ////
    function getDataTableByListHj(_formId, _url, _order, _columns, lcol, rcol, _drawCallback, _num){
    	var _length = getTopFrame().window.sessionRowNumLength;
    	if(_order.length > 0){
    		if(!/^\d+$/.test(_order[0])){
    			var str = _order[0].toUpperCase();
    			for(var i = 0, len = _columns.length; i < len; i++){
    				if(_columns[i]["data"] == str){
    					_order = [i,_order[1]];
    					break;
    				}
    			}
    		}
    	}
    	return $('#' + _formId).DataTable({
            "lengthMenu":_length,//每页显示条数设置
            "order": _order,//排序列
            "columns": _columns,//列定义
            "processing":true,
            "scrollX": true,
            "scrollY": true,
            "fixedColumns": {//固定列
    			"leftColumns": lcol,
    			"rightColumns": rcol
    		},
    		//"paging":false,
    		"bLengthChange": false,
    		//"bAutoWidth": true,
    		//"bPaginate": false,
    		"iDisplayLength": 3,
    		"dom":"<'row fyrow'<'page-left wxts'li>><'row'<t>> <'row bottom'<'pull-right'p ><'total'o>>",
            "ajax": {
            	method:"post",
            	data:arrayToJson($("form").serializeArray()),
            	url:_url,//获取数据的方法
            	async:false,
                complete:function(XMLHttpRequest, textStatus) {
                	if(_drawCallback!=undefined&&_drawCallback!=null&&XMLHttpRequest.statusText == "OK"){
                		_drawCallback(XMLHttpRequest.responseJSON);
                    }
                }
            },
            "drawCallback":function(){
            	var heights = $(window).outerHeight() - $(".search").outerHeight() - $(".row.fyrow").outerHeight() - $(".row.bottom").outerHeight() - $(".dataTables_scrollHead").outerHeight();
            	if(_num=='1'){
            		heights = $(window).outerHeight() - $(".row.fyrow").outerHeight() - $(".row.bottom").outerHeight() - $(".dataTables_scrollHead").outerHeight();
            	} else if(_num=='ry') {
    	        	heights = $(window).outerHeight() - $(".search").outerHeight() - $(".row.bottom").outerHeight() - $(".row.fyrow").outerHeight() - $(".rybxx").outerHeight() - $(".dataTables_scrollHead").outerHeight();
    			} else if(_num=='zjxx') {
    				heights = $(window).outerHeight() - $(".rybxx").outerHeight() - $(".search").outerHeight() - $(".row.fyrow").outerHeight() - $(".dataTables_scrollHead").outerHeight() - $(".row.bottom").outerHeight();
    			} else if(_num=='yssjzl') {
    				heights = $(window).outerHeight() - $(".search").outerHeight() - 2 - $("#yzxx").outerHeight() - $(".row.bottom").outerHeight() - $(".row.fyrow").outerHeight() - $(".dataTables_scrollHead").outerHeight();
    				$(".bottom_scroll").css("margin-bottom", "110px");
    			} else if(_num == "sjsb") {
    				heights = heights - $("#d_tablist").outerHeight();
    			}
            	$(".dataTables_scrollBody").height(heights);
            },
            error:function(){
            	alert('网络异常或数据加载失败，请尝试重新登录系统！');    
            }  
    	});
    }
  //处理通知公告标题太长问题
	function resetTzggTitle(title,length){
		if(title.length > length){
			title = title.substring(0,length)+"...";
		}
		return title;
	}
</script>
</body>
</html>