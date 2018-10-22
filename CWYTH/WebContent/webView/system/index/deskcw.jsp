<%@page import="com.googosoft.constant.MenuFlag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" uri="/webView/tlds/fns.tld"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	#menu_ysgl :hover{
		cursor: pointer;
		background-repeat:no-repeat;
		background-image: url("/CWYTH/static/images/deskImage/pzsz1.png");
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   		border-bottom-width: 1px!important;
    }
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
	.left{
		float:left;
		width:15%;
	}
	.right{
		float:left;
		width:38%;
	}
	.sx{
	cursor:pointer;
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
	.wz{ 
position:absolute; 
left:810px; 
top:30px; 
} 

.dh1 li{
list-style: none;
float:left;
height: 75px;
margin-top: 10px;
width:20%;
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
int n = 1;
%>
	<div class="container-fluid">
		<div class="row" style="margin-top:5px;margin-left:50px;margin-right:50px;">
			<div class="col-sm-4 col-xs-12" id="block-bsdt">
			<span class="skip" id="skip-left" style="left: 10px;background-image: url(${ctx}/static/images/button/skip-left.png);"></span>
			<span class="skip" id="skip-right" style="right: 10px;background-image: url(${ctx}/static/images/button/skip-right.png);"></span>
			<div class="box bsdt" style="">
					<div class="box-header">
						<span class="title">办事大厅
						</span>
						<div class="actions">
							<a href="javascript:void(0);" class="btn newbox-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up" id="faaaa"></i></a>
						</div>
					</div>
					<div class="box-content bsdt-page" style="margin-top: 1px;">
						<input type="hidden" id="page_count" value="${bsdtPageCount }"/>
						<div class="bsdt-content">
							<c:forEach var="bsdtPage" items="${bsdtPageList }">
								<div class="container-fluid bsdt-show">
									<ul>
									<c:forEach var="zs" items="${bsdtPage.zsList}">
										<c:if test="${empty zs.sfwj}" >												
													<li style="width: ${bsdtPage.liWidth};">
														<div class="item col-xs-3 goMenu" id="${zs.sytz }" style="background-image: url(${ctx}/static/images/deskImage/${zs.sytz}.png);">
															<div>${zs.mkmc}</div>
														</div>
													</li>
													</c:if>
													<c:if test="${zs.sfwj=='1'}">
														<li style="width: ${bsdtPage.liWidth};">
														<div class="item col-xs-3 goWWW" id="${zs.sytz}" url="${zs.url}" rybh="${rybh}" style="background-image: url(${ctx}/static/images/deskImage/${zs.sytz}.png);">
															<div>${zs.mkmc}</div>
														</div>
													</li>
													</c:if>
									</c:forEach>
									</ul>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		
			<div class="col-sm-8 col-xs-12">
				<div class="box jdgz ">
					<div class="box-header">
						<span class="title">最近凭证</span>
						<button class="btn btn_lrpz " style="background-color: #00acec;font-size:13px;color:white;padding:2px 6px 2px 6px !important;position: absolute;right:150px;top:6px;"><span class="title" >待生成凭证（${dscpzsl }）</span></button>
                        <button class="btn btn_lrpz " style="background-color: #00acec;font-size:13px;color:white;padding:2px 6px 2px 6px !important;position: absolute;right:65px;top:6px;"><span class="title" >录入凭证</span></button>
						<div class="actions">
							<a href="javascript:void(0);" id="btn-quick-link" class="btn btn-zjpz" style="color:#A8A8A8;font-size: 12px" title="点击查看更多">更多</a>
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
							
						</div>
					</div>
					<div class="box-content" style="height:260px;">
					<div class="col-sm-12 col-xs-12">
						<table id="mydatatables" class="table table-striped table-bordered table-hover" style="border-collapse:collapse!important;height:95%;border-bottom:none;float:left;">
			                    <thead>
					          <tr>
					            <th style="text-align:center;width:13%;padding-left: 0px;padding-right: 0px">凭证日期</th>
					            <th style="text-align:center;width:12%;padding-left: 0px;padding-right: 0px">凭证号</th>
					            <th style="text-align:center;width:36%;padding-left: 0px;padding-right: 0px">凭证摘要</th>
					            <th style="text-align:center;width:13%;padding-left: 0px;padding-right: 0px">凭证状态</th>
					            <th style="text-align:center;width:8%;padding-left: 0px;padding-right: 0px">分录条数</th>
					            <th style="text-align:center;width:18%;padding-left: 0px;padding-right: 0px">金额(元)</th>
					          </tr>
						  </thead>  
						</table>
						</div>
						<div class="col-sm-2 col-xs-12">
						<div class="" style="float:left;margin-left:5px;width:80%;">
					<div class="row">
				
			</div>
					</div>
					</div>
					</div>
				</div>
			</div>
	</div>
	<div class="row" style="margin-top:5px;margin-left:50px;margin-right:50px;">
	 <div class="col-sm-4 col-xs-12" style="float: left; display: inline-block;">		
	      <div class="row" style="margin-left:0px;margin-right:0px;">		
            <div class="col-sm-12 col-xs-12" style="margin-top:0px;">
				<div class="box grsw">
					<div class="box-header">
						<span class="title">个人事务</span>
						<div class="actions">
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
<!-- 					<div class="box-content" style="height: 210px;overflow:auto;margin-left:0%""> -->
                     <div class="box-content" style="height: 210px;margin-left:0%">
                      <ul class="dh1">
							 <li >
								 <div class="item col-xs-3 sx"
									id="wdbx" style="float: left;height: 90px;padding: 0;width:85px;background-position-x: center;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdbx.png);">
							       <div
										style="padding-left: 60px; font-size: 14px; font-weight: bold;">
										<a class="oth" href="javascript:void(0)"
											onclick="ywdb_xx11()"></a>
									</div>
									<div style="padding-top	: 53px;width:85px;text-align:center;margin: 0 auto;" class="btn-jrbx">我的报销业务</div>
								</div>
							</li>
							 <li>
							  <div class="item col-xs-3 sx"
								id="wdsqsp" style="float: left;height: 90px;padding: 0;background-position-x: center;width:85px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdsqsp.png);">
								<div
									style="padding-left: 60px; font-size: 14px; font-weight: bold;">
									<a class="oth" href="javascript:void(0)"
										onclick="ywdb_xx11()"></a>
								</div>
								<div style="padding-top: 53px;width:85px;;text-align:center;margin: 0 auto;" class="btn-sqsp">我的事前审批</div>
							   </div>
							 </li>
							 <li>
							   <div class="item col-xs-3 sx"
								id="wdxm" style="float: left;padding: 0;background-position-x: center;height: 90px;width:85px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdxm.png);">
								<div
											style="padding-left: 60px; font-size: 14px; font-weight: bold;">
											<a class="oth" href="javascript:void(0)"
												onclick="ywdb_xx11()"></a>
										</div>
								<div style="padding-top: 53px;width:85px;;text-align:center;margin: 0 auto;" class="btn-wdxm">我的项目</div>
							 </div>
							 </li>
							 <li>
							    	<div class="item col-xs-3 sx hidden"
								id="wdxz" style="float: left;padding: 0;background-position-x: center;height: 90px;width:85px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdxz.png);">
								<div
											style="padding-left: 60px; font-size: 14px; font-weight: bold;" >
											<a class="oth" href="javascript:void(0)"
												onclick="ywdb_xx11()"></a>
										</div>
								<div style="padding-top: 53px;width:85px;;text-align:center;margin: 0 auto;" >我的薪资</div>
							</div>
							 </li>
							 <li>
							     <div class="item col-xs-3 sx hidden"
								id="wdzc" style="float: left;padding: 0;background-position-x: center;height: 90px;width:85px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdzc.png);">
								<div
											style="padding-left: 60px; font-size: 14px; font-weight: bold;">
											<a class="oth" href="javascript:void(0)"
												onclick="ywdb_xx11()"></a>
										</div>
								<div style="padding-top: 53px;width:85px;;text-align:center;margin: 0 auto;" >我的资产</div>
							</div> 
							   </li>
							 <li>
							    <div class="item col-xs-3 sx hidden"
								id="wdjf" style="float: left;padding: 0;background-position-x: center;height: 90px;width:85px;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdjf.png);">
								<div
											style="padding-left: 60px; font-size: 14px; font-weight: bold;">
											<a class="oth" href="javascript:void(0)"
												onclick="ywdb_xx11()"></a>
										</div>
								<div style="padding-top: 53px;width:85px;;text-align:center;margin: 0 auto;" >我的缴费</div>
							</div>  
							 
							 </li>
						</ul>
					</div>
				</div>
			</div>
			</div>
            <div class="row" style="margin-top:5px;margin-left:0px;margin-right:0px;">	
			<div class="col-sm-12 col-xs-12">
				<div class="box cygn">
					<div class="box-header">
						<span class="title">通知公告</span>
						<div class="actions">
							<a href="javascript:void(0);" id="btn-quick-link" class="btn btn-sm" style="color:#A8A8A8;" title="点击查看更多">更多</a>
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<div class="box-content" style="height:210px;">
						<div class="container-fluid"  style = "padding-left:0">
							<div id="tbody" class="row" style="margin-left:10px">
							<input type="hidden" name="guid">
					
							</div>
						</div>
					</div>
				  </div>
			     </div>
			 </div>
		</div>
      <div class="col-sm-8 col-xs-12">
			<div class="box yssz">
					<div class="box-header">
						<span class="title">今日报销</span>
						
						<div class="actions">
							<a href="javascript:void(0);" id="btn-quick-link" class="btn btn-jrbx" style="color:#A8A8A8;font-size: 12px" title="点击查看更多">更多</a>
							    <a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
						
					</div>
					<div class="row"
						style="margin-left: 0px; margin-right: 0px;">
						<div class="col-sm-12 col-xs-12">
					<div class="box-content"style="height:454px; ">
						<div style="width: 10%;">
							<select id="bx_sjqj" class="form-control" name="shzt"
									table="K" style="height: 25px;font-size:12px;padding-top: 2px !important;">
									<option value="jr">今日</option>
		                        	<option value="bz">本周</option>
		                        	<option value="by">本月</option>
		                        	<option value="bjd">本季度</option>
		                        	<option value="bn">本年</option>
								</select>
							</div>
		
					
					<div class="" id="yssr" style="height:190px;padding-top: 4px;float: right;width: 50%;">
						</div>
						<div class="" id="yssr1" style="height:190px;padding-top: 4px;float: right;width: 50%;">
						</div>
<!-- 						<div class="" id="yssr1" style="height:193px;float: left"> -->
<!-- 						</div> -->
						<!-- <div class="ycc" id="yszc" style="height:250px;">
						</div> -->
						<table id="mydatatables1" class="table table-striped table-bordered table-hover" style="border-collapse:collapse!important;border-bottom:none;">
							    <thead>
								    <tr>
								        <th >单据编号</th>
									    <th >报销人</th>
									    <th >所在部门</th>
									    <th >单据类型</th>
									    <th >报销时间</th>
									    <th >报销金额（元）</th>
									   
								    </tr>
							    </thead>
							    <tbody>
				                </tbody>
						</table>
					</div>
				</div>	
				</div>
			</div>
		</div>
	</div>
	
	
		<!-- 我的薪酬 ----------------------------------------------------------------------------------------------------------------------------------->
		<div class="row"
			style="margin-top: 5px; margin-left: 50px; margin-right: 50px;">
			<div class="col-sm-12 col-xs-12" style="float: left;">

				<div class="box cygn">
					<div class="box-header">
						<span class="title">我的薪酬</span>
						<div class="actions">
							<a href="javascript:void(0);" id="btn-quick-link"
								class="btn btn-sm" style="color: #A8A8A8;" title="点击查看更多"></a> <a
								href="javascript:void(0);"
								class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i
								class="faw fa-angle-up"></i></a>
						</div>
					</div>


					<div class="box-content" style="height: 250px;">
						<div class="col-sm-12 col-xs-12" style="overflow: auto;">

							<table class=" table-striped table-bordered table-hover"
								style="margin-left: 0%; border-collapse: collapse !important; line-height: 25px; width: 150%;">
								<thead>
									<tr>
										<th style="text-align: center;">工资月份</th>
										<th style="text-align: center;">岗位工资</th>
										<th style="text-align: center;">薪级工资</th>
										<th style="text-align: center;">新住房贴</th>
										<th style="text-align: center;">物业补贴</th>
										<th style="text-align: center;">物业费</th>
										<th style="text-align: center;">独/回</th>
										<th style="text-align: center;">基础绩效</th>
										<th style="text-align: center;">奖励绩效1</th>
										<th style="text-align: center;">博士补贴</th>
										<th style="text-align: center;">岗位补贴</th>
										<th style="text-align: center;">补工资</th>
										<th style="text-align: center;">租房补贴</th>
										<th style="text-align: center;">应发合计</th>
										<th style="text-align: center;">住房积金</th>
										<th style="text-align: center;">医疗保险</th>
										<th style="text-align: center;">代扣税</th>
										<th style="text-align: center;">房租</th>
										<th style="text-align: center;">失业金</th>
										<th style="text-align: center;">社保金</th>
										<th style="text-align: center;">养老金</th>
										<th style="text-align: center;">扣款</th>
										<th style="text-align: center;">扣款合计</th>
										<th style="text-align: center;">实发合计</th>
									</tr>
								</thead>
								<tbody style="text-align: right;" id="doIt">
									<c:forEach var="xzList" items="${xzList }">
										<tr class="doIt">
											<td style="text-align: right;">${xzList.GZYF }</td>
											<td style="text-align: right;">${xzList.GWGZ }</td>
											<td style="text-align: right;">${xzList.XJGZ }</td>
											<td style="text-align: right;">${xzList.XZFT }</td>
											<td style="text-align: right;">${xzList.WYBT }</td>
											<td style="text-align: right;">${xzList.WYF  }</td>
											<td style="text-align: right;">${xzList.DSZF }</td>
											<td style="text-align: right;">${xzList.JCJX }</td>
											<td style="text-align: right;">${xzList.JLJX1 }</td>
											<td style="text-align: right;">${xzList.BSBT }</td>
											<td style="text-align: right;">${xzList.GWBT }</td>
											<td style="text-align: right;">${xzList.BGZ }</td>
											<td style="text-align: right;">${xzList.ZFBT }</td>
											<td style="text-align: right;">${xzList.YFHJ }</td>
											<td style="text-align: right;">${xzList.ZFJJ }</td>
											<td style="text-align: right;">${xzList.YLBX }</td>
											<td style="text-align: right;">${xzList.DKS }</td>
											<td style="text-align: right;">${xzList.FZ }</td>
											<td style="text-align: right;">${xzList.SYJ }</td>
											<td style="text-align: right;">${xzList.SBJ }</td>
											<td style="text-align: right;">${xzList.YLJ }</td>
											<td style="text-align: right;">${xzList.KK }</td>
											<td style="text-align: right;">${xzList.KKHJ }</td>
											<td style="text-align: right;">${xzList.SFHJ }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<!-- 						<div class="col-sm-5 col-xs-12"> -->
						<!-- 							<div style=""> -->
						<!-- 								<div class="" id="yssr" style="height: 193px;"></div> -->
						<!-- 							</div> -->
						<!-- 						</div> -->
					</div>

				</div>
			</div>
		</div>
		
		
		<!-- 预算情况分析    我的项目------------------------------------------------------------------------------------------------------------------->
		<div class="row"
			style="margin-top: 5px; margin-left:50px; margin-right: 50px;">
			<div class="col-sm-12 col-xs-12" style="float: left;">

				<div class="box cygn">
					<div class="box-header">
						<span class="title">我的项目</span>
						<div class="actions">
							<a href="javascript:void(0);" id="btn-quick-link"
								class="btn btn-sm" style="color: #A8A8A8;" title="点击查看更多"></a> <a
								href="javascript:void(0);"
								class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i
								class="faw fa-angle-up"></i></a>
						</div>
					</div>


					<div class="box-content" style="height: 250px;">
						<div class="col-sm-7 col-xs-12">

							<table id="mydatatables1"
								class=" table-striped table-bordered table-hover"
								style="margin-left: 0%; border-collapse: collapse !important; line-height: 25px; width: 100%;">
								<thead>
									<tr>
										<th>序号</th>
							            <th>部门名称</th>
							            <th>项目大类</th>
							            <th>项目类别</th>
							            <th>项目编号</th>
							            <th>项目名称</th>	
							            <th>预算金额（元）</th>
							            <th>剩余金额（元）</th>
									</tr>
								</thead>
								<tbody style="text-align: right;">
									<c:forEach var="wdxmList" items="${wdxmList}">
										<tr>
										  <td style="text-align: center;"><%= n++%></td>
											<td style="text-align: left;">${wdxmList.BMMC }</td>
											<td style="text-align: left;">${wdxmList.XMDLMC }</td>
											<td style="text-align: left;">${wdxmList.XMLBMC }</td>
											<td style="text-align: left;">${wdxmList.XMBH }</td>
											<td style="text-align: left;">${wdxmList.XMMC }</td>
											<td style="text-align: right;">${wdxmList.YSJE }</td>
											<td style="text-align: right;">${wdxmList.SYJE }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

<!-- 						<div class="col-sm-5 col-xs-12"> -->
<!-- 							<div class="box-content" style="height: 250px;"> -->
<%-- 								<center> --%>
<!-- 									<div style="width: 100px;"> -->
<!-- 										<select id="drp_spzt" class="form-control" name="shzt" -->
<!-- 											table="K" style="height: 30px;"> -->
<%-- 											<option value="de"><%=den%>年 --%>
<!-- 											</option> -->
<%-- 											<option value="dy" selected="selected"><%=dyn%>年 --%>
<!-- 											</option> -->

<%-- 											<option value="ds"><%=dsn%>年 --%>
<!-- 											</option> -->
<!-- 										</select> -->
<!-- 									</div> -->
<%-- 								</center> --%>
<!-- 								<div class="" id="ysrfx" style="height: 200px;"></div> -->
<!-- 							</div> -->
<!-- 						</div> -->
					</div>

				</div>
			</div>
		</div>
	
	
	
<!-- 			<div class="col-sm-8 col-xs-12"> -->
<!-- 				<div class="box jdgz"> -->
<!-- 					<div class="box-header"> -->
<!-- 						<span class="title">&emsp;&emsp;</span> -->
<!-- 						<div class="actions"> -->
<!-- 						<a href="javascript:void(0);" id="btn-quick-link" class="btn btn-sm" style="color:#A8A8A8;" title="点击查看更多"">更多</a> -->
<!-- 							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="box-content" style="height:250px;"> -->
						
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
	</div>
	<!-- 首页面的固定菜单跳转，在newDesk.js的最底部  2018-01-24李飞  -->
	<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/static/plugins/datatable/datatables.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/plugins/datetimepicker/bootstrap-datetimepicker.js"></script>
	<script src="${pageContext.request.contextPath}/static/javascript/public/public-js.js"></script>
	<script src="${pageContext.request.contextPath}/static/javascript/public/public-window.js"></script>
    <script src="${pageContext.request.contextPath}/static/plugins/echarts/echarts.js"></script> 
	<script src="${pageContext.request.contextPath}/webView/system/index/newPicture.js"></script> 
	<script src="${pageContext.request.contextPath}/static/javascript/index/newDesk.js"></script> 
	<script src="${ctx}/static/javascript/index/goMenu.js"></script> 
	<script type="text/javascript">
	var clbx=Number("${jrbx.cl}");
	var gwbx=Number("${jrbx.gw}");
	var rcbx=Number("${jrbx.rc}");
	var clsjs=Number("${jrbx.clsjs}");
	var gwsjs=Number("${jrbx.gwsjs}");
	var rcsjs=Number("${jrbx.rcsjs}");
	table();
	yssr(clsjs,gwsjs,rcsjs);
	yssr1(clbx,gwbx,rcbx);
	
		
		
	  var sjqj= $('#bx_sjqj').val();
	  var columns1 = [   	       		
	                    {"data": "DJBH",defaultContent:""},
	       	       	 	{"data": "BXRMC",defaultContent:""},
	       	       	    {"data": "SZBMMC",defaultContent:""},
	       	       	   	{"data": "BXLX",defaultContent:"","class":"text-left"},
	       	       	 	{"data": "CZRQ",defaultContent:"","class":"text-center"},
	       	       	    {"data": "BXZJE",defaultContent:"","class":"text-right","width":140},

	       	       	];
	  table1 = getDataTabless("mydatatables1","${ctx}/index/getjrbxPageList?sjqj="+sjqj,[0,'asc'],columns1);
    //报销时间区间改变
	$(document).on("change","#bx_sjqj",function(){
		var sjqj=$("#bx_sjqj").val();
		$.ajax({
	        type:"post",
	        url:"${pageContext.request.contextPath}/index/getWsbx?sjqj="+sjqj,
	        dataType:"json",
	        async:false,
	        success:function(val){
	        	clbx=val.cl;
	        	gwbx=val.gw;
	        	rcbx=val.rc;
	        	clsjs=val.clsjs;
	        	gwsjs=val.gwsjs;
	        	rcsjs=val.rcsjs;
	        	yssr1(clbx,gwbx,rcbx);
	    		yssr(clsjs,gwsjs,rcsjs);
	    		tableRelod(table1); 
	        },
	    });
		
	});
	var tableRelod = function(table) {
		var sjqj= $('#bx_sjqj').val();
	    table.ajax.url("${ctx}/index/getjrbxPageList?sjqj="+sjqj);
	    table.ajax.reload();
	}
	//处理通知公告标题太长问题
	function resetTzggTitle(title,length){
		if(title.length > length){
			title = title.substring(0,length)+"...";
		}
		return title;
	}
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
						//alert(v.GID+"333");
						var maxlength=window.screen.width;
// 						alert(maxlength);
						
						if(maxlength<=800){
							$("#tbody").append("<ul>"+
									"<input type='hidden' name = 'gid' value="+guid+">"+
									"<li ><div id='dd'><a href='javascript:void(0);' class=' btn_look color'>"+resetTzggTitle(v.TITLE,6)+"</a>  </div></li>"+     								
									"<li class='lli' style='text-align:right;'>"+"["+v.FBSJ+"]"+"</li>" 
							);
						}else if(maxlength>800&&maxlength<=1280){
							$("#tbody").append("<ul>"+
									"<input type='hidden' name = 'gid' value="+guid+">"+
									"<li ><div id='dd'><a href='javascript:void(0);' class=' btn_look color'>"+resetTzggTitle(v.TITLE,20)+"</a> </div></li>"+     								
									"<li class='lli' style='text-align:right;'>"+"["+v.FBSJ+"]"+"</li>" 
							);
						}else if(maxlength>1280&&maxlength<=1600){
							$("#tbody").append("<ul>"+
									"<input type='hidden' name = 'gid' value="+guid+">"+
									"<li ><div id='dd' ><a href='javascript:void(0);' class=' btn_look color'>"+resetTzggTitle(v.TITLE,24)+"</a></div></li>"+     								
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
	//个人薪资查看详情
	$(document).on("dblclick",".doIt",function(){
		window.location.href="${ctx}/xzcx/goGrxzcxPage";
	});
    $(function(){

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
  //外接系统网站
    $(".goWWW").click(function(){
    	var url= $(this).attr("url");
    	var rybh =$(this).attr("rybh");
  	    var url1=url+"?uname="+rybh;
    	window.open(url1);
    	
    	
    	
//   	  var mkmc=$(this).attr("id");
//   	  if(mkmc=="yxgz"){   //迎新工作
//       	window.open("http://www.baidu.com"); 
//   	  }
//   	  if(mkmc=="sfxt"){   //收费系统
//   		  window.open("http://www.hao123.com"); 
//   	  }
//   	  if(mkmc=="sfxt"){    
//   		  window.open("http://www.taobao.com");
//   	  }
//   	  if(mkmc=="gygl"){		//公寓管理
//   		  window.open("http://www.google.com");
//   	  }
//   	  if(mkmc=="kyxm"){		//科研项目
//   		  window.open("http://www.tmall.com");
//   	  }
//   	  if(mkmc=="jcgl"){		//教材管理
//   		  window.open("http://www.baidu.com");
//   	  }
//   	  if(mkmc=="oaxt"){		//OA系统
//   		  window.open("http://www.weipinhui.com");
//   	  }
//   	  if(mkmc=="szda"){		//数字档案
//   		  window.open("http://www.szda.com");
//   	  }
//   	  if(mkmc=="rsgl"){		//人事管理
//   		  window.open("http://www.rsgl.com");
//   	  }
//   	  if(mkmc=="xsgz"){		//学生工作
//   		  window.open("http://www.xsgz.com");
//   	  }

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
    

     function table(){
    	 var columns = [
    	                   
    	                   {"data": "PZRQ",defaultContent:"凭证时间","id":"tr_pzzh","class":"text-center"},
    	                   {"data": "PZBH",defaultContent:"凭证编号","class":"text-center"},
    	                   {"data": "ZY",defaultContent:"无","class":"text-left"},
    	                   {"data": "PZZTMC",defaultContent:"凭证状态","class":"text-center"},
    	                   {"data": "FLTS",defaultContent:"0","id":"tr_cnr","class":"text-right"},
    	                   {"data": "JFJEHJ",defaultContent:"0","id":"tr_cnr","class":"text-right"},
    	                 ];
    	    table = getDataTabless("mydatatables","${ctx}/index/getPageList",[1,'desc'],columns);
     }
   
     function yssr(cl,gw,rc){
    	 var myChart = echarts.init(document.getElementById('yssr'));
		 option = {
				    tooltip: {
				        trigger: 'item',
				        formatter: "{a} <br/>{b}: {c} ({d}%)"
				    },
				    legend: {
				        orient: 'vertical',
				        x: 'right',
				        data:['差旅费报销笔数','公务接待报销笔数','日常报销笔数']
				    },
				    series: [
				        {
				            name:'',
				            type:'pie',
				            radius: ['10%', '70%'],
				            center:['40%','50%'],
				            avoidLabelOverlap: false,
				            label: {
				            	 normal: {
					                    show: true, 
					                    formatter: '{c}' 
					                }, 
				                emphasis: {
				                    show: true,
				                    textStyle: {
				                        fontSize: '12',
				                        fontWeight: 'bold'
				                    }
				                }
				            },
				            
				            labelLine: {
				                normal: {
				                    show: true
				                }
				            },
				            data:[
				                {value:cl, name:'差旅费报销笔数'},
				                {value:gw, name:'公务接待报销笔数'},
				                {value:rc, name:'日常报销笔数'},
				                
				            ]
				        }
				    ]
				};
		
		 myChart.setOption(option); 
     }
 function yssr1(cl,gw,rc){
    	 var myChart = echarts.init(document.getElementById('yssr1'));
		 option = {
				    tooltip: {
				        trigger: 'item',
				        formatter: "{a} <br/>{b}: {c} ({d}%)"
				    },
				    legend: {
				        orient: 'vertical',
				        x: 'right',
				        data:['差旅费报销金额（元）','公务接待报销金额（元）','日常报销金额（元）']
				    },
				    series: [
				        {
				            name:'',
				            type:'pie',
				            radius: ['10%', '70%'],
				            center:['40%','50%'],
				            avoidLabelOverlap: false,
				            label: {
				            	 normal: {
					                    show: true, 
					                    formatter: '{c}' 
					                }, 
				                emphasis: {
				                    show: true,
				                    textStyle: {
				                        fontSize: '12',
				                        fontWeight: 'bold'
				                    }
				                }
				            },
				            
				            labelLine: {
				                normal: {
				                    show: true
				                }
				            },
				            data:[
				                {value:cl, name:'差旅费报销金额（元）'},
				                {value:gw, name:'公务接待报销金额（元）'},
				                {value:rc, name:'日常报销金额（元）'},
				                
				            ]
				        }
				    ]
				};
		
		 myChart.setOption(option);
     }
 $(document).on("click","#menu_ysgl",function(){
	 window.open("http://127.0.0.1:8066/WhcwDemo/");
 })
 $(window).resize(function(){
	 console.log(document.body.clientWidth);
 })
</script>
</body>
</html>