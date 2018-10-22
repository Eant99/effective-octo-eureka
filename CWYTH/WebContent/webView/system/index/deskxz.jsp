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
<title>安徽财贸职业学院网上报销审批系统主页</title>
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
	.dh1 li{
list-style: none;
float:left;
height: 75px;
margin-top: 10px;
width:20%;

}
	.color{
	  color:black;
	}
	.wz{ 
position:absolute; 
left:810px; 
top:30px; 
} 
.dh li{
list-style: none;
float:left;
height: 75px;
margin-left: 17px;
padding-left: 10px;
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
			
			<div class="col-sm-4 col-xs-12" style="margin-top:0px;">
				<div class="box grsw">
					<div class="box-header">
						<span class="title">个人事务</span>
						<div class="actions">
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<div class="box-content" style="height:244px;overflow:auto;margin-left:0%"">
                         <ul class="dh1">
							 <li >
								 <div class="item col-xs-3 sx"
									id="wdbx" style="float: left;height: 90px;padding: 0;width:100%;background-position-x: center;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdbx.png);">
							       <div
										style="padding-left: 60px; font-size: 14px; font-weight: bold;">
										<a class="oth" href="javascript:void(0)"
											onclick="ywdb_xx11()"></a>
									</div>
									<div style="padding-top	: 53px;width:100%;text-align:center;margin: 0 auto;" class="btn-jrbx">我的报销业务</div>
								</div>
							</li>
							 <li>
							  <div class="item col-xs-3 sx"
								style="float: left;height: 90px;padding: 0;background-position-x: center;width:100%;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdsqsp.png);">
								<div
									id="wdsqsp" style="padding-left: 60px; font-size: 14px; font-weight: bold;">
									<a class="oth" href="javascript:void(0)"
										onclick="ywdb_xx11()"></a>
								</div>
								<div style="padding-top: 53px;width:100%;;text-align:center;margin: 0 auto;" class="btn-sqsp">我的事前审批</div>
							   </div>
							 </li>
							 <li>
							   <div class="item col-xs-3 sx"
								id="wdxm" style="float: left;padding: 0;background-position-x: center;height: 90px;width:100%;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdxm.png);">
								<div
											style="padding-left: 60px; font-size: 14px; font-weight: bold;">
											<a class="oth" href="javascript:void(0)"
												onclick="ywdb_xx11()"></a>
										</div>
								<div style="padding-top: 53px;width:100%;;text-align:center;margin: 0 auto;" class="btn-wdxm">我的项目</div>
							 </div>
							 </li>
							 <li>
							    	<div class="item col-xs-3 sx hidden"
								id="wdxz" style="float: left;padding: 0;background-position-x: center;height: 90px;width:100%;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdxz.png);">
								<div
											style="padding-left: 60px; font-size: 14px; font-weight: bold;">
											<a class="oth" href="javascript:void(0)"
												onclick="ywdb_xx11()"></a>
										</div>
								<div style="padding-top: 53px;width:100%;;text-align:center;margin: 0 auto;" class="hidden">我的薪资</div>
							</div>
							 </li>
							 <li>
							     <div class="item col-xs-3 sx hidden
								id="wdzc" style="float: left;padding: 0;background-position-x: center;height: 90px;width:100%;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdzc.png);">
								<div
											style="padding-left: 60px; font-size: 14px; font-weight: bold;">
											<a class="oth" href="javascript:void(0)"
												onclick="ywdb_xx11()"></a>
										</div>
								<div style="padding-top: 53px;width:100%;;text-align:center;margin: 0 auto;" >我的资产</div>
							</div> 
							   </li>
							 <li>
							    <div class="item col-xs-3 sx hidden"
								id="wdjf" style="float: left;padding: 0;background-position-x: center;height: 90px;width:100%;background-repeat:no-repeat;background-image: url(${pageContext.request.contextPath}/static/images/deskImage/wdjf.png);">
								<div
											style="padding-left: 60px; font-size: 14px; font-weight: bold;">
											<a class="oth" href="javascript:void(0)"
												onclick="ywdb_xx11()"></a>
										</div>
								<div style="padding-top: 53px;width:100%;;text-align:center;margin: 0 auto;" >我的缴费</div>
							</div>  
							 
							 </li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-sm-4 col-xs-12">
				<div class="box cygn">
					<div class="box-header">
						<span class="title">通知公告</span>
						<div class="actions">
							<a href="javascript:void(0);" id="btn-quick-link" class="btn btn-sm" style="color:#A8A8A8;" title="点击查看更多"">更多</a>
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<div class="box-content" style="height:244px;">
						<div class="container-fluid"  style = "padding-left:0">
							<div id="tbody" class="row" style="margin-left:10px">
							<input type="hidden" name="guid">
					
							</div>
						</div>
					</div>
<!-- 					<div class="box-content" style="height:170px;"> -->
<!-- 						<div class="container-fluid"  style = "padding-left:0"> -->
<!-- 							<div class="row"> -->
<!-- 								<p class="p1notice">[科研经费管理科]&nbsp;2017学年本科生（在校生）交费通知<span style="float:right;">2017-09-21</span></p> -->
<!-- 								<p class="p1notice">[资金管理科]&nbsp;2017学年本科生（在校生）交费通知<span style="float:right;">2017-09-18</span></p> -->
<!-- 								<p class="p1notice">[资金管理科]&nbsp;2017学年研究生（在校生）交费通知<span style="float:right;">2017-09-14</span></p> -->
<!-- 								<p class="p1notice">[资金管理科]&nbsp;2017学年本科生（在校生）交费通知<span style="float:right;">2017-09-11</span></p> -->
<!-- 								<p class="p1notice">[科研经费管理科]&nbsp;2017学年本科生（在校生）交费通知<span style="float:right;">2017-08-25</span></p> -->
<!-- 								<p class="p1notice">[科研经费管理科]&nbsp;2017学年本科生（在校生）交费通知<span style="float:right;">2017-06-21</span></p> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
			</div>
	</div>	
	<div class="row" style="margin-top:5px;margin-left:50px;margin-right:50px;">
	    <div class="col-sm-6 col-xs-12">
				<div class="box qkfx">
					<div class="box-header">
						<span class="title">培养层次分析</span>
						<div class="actions">
							<!-- <span style="color:#3ea077;">目前全校学生总数76622人,其中专科生22321人，本科生48742人，研究生21031人，博士生15200人。</span> -->
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<div class="box-content" style="height:250px;margin-left:20px;">
					   <div><span style="color:#3ea077;font-size:15px;margin-left:13px;">目前全校学生总数72294人,其中专科生22321人，本科生48742人，研究生21031人，博士生15200人。</span>
					   </div>
						<div class="box-content left" id="xmzs" style="height:200px;width:20%;border:none;">
						</div>
						<div class="box-content left" id="zks" style="height:200px;width:20%;border:none;">
						</div>
						<div class="box-content left" id="bks" style="height:200px;width:20%;border:none;">
						</div>
						<div class="box-content left" id="yjs" style="height:200px;width:20%;border:none;">
						</div>
						<div class="box-content left" id="bss" style="height:200px;width:20%;border:none;">
						</div>
						
					</div>
		    	</div>
			</div>
	    
	    <div class="col-sm-6 col-xs-12">
			<div class="box xsrsfx">
					<div class="box-header">
						<span class="title">学生人数分析</span>
						
						<div class="actions">
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" style="padding-left: 5px;" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>				
					</div>				
					<div class="box-content">
						<div class="" id="xsrsfx" style="height:233px;">
						</div>
					</div>
					
				</div>
			</div>
	</div>
	<div class="row" style="margin-top:5px;margin-left:50px;margin-right:50px;">
	   <div class="col-sm-6 col-xs-12">
			<div class="box yssz">
					<div class="box-header">
						<span class="title">教师学历分析</span>
						
						<div class="actions">
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" style="padding-left: 5px;" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>				
					</div>				
					<div class="box-content">
						<div class="" id="jsxlfx" style="height:233px;">
						</div>
					</div>
					
				</div>
			</div>
			<div class="col-sm-6 col-xs-12">
			<div class="box yssz">
					<div class="box-header">
						<span class="title">教师年龄分析</span>	
						<div class="actions">
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" style="padding-left: 5px;" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>				
					</div>				
					<div class="box-content" style="height:250px;">
						<div class="box-content left" id="jsnlfx" style="height:250px;width:50%;border:none;">
						</div>
						 <div class="box-content left" id="jszs" style="height:250px;width:20%;border:none;">
						</div>
						<div class="box-content left" id="zrjs" style="height:250px;width:20%;border:none;">
						</div>					
					</div>
				</div>
			</div>
	</div>
	<div class="row" style="margin-top:5px;margin-left:50px;margin-right:50px;">
	   <div class="col-sm-6 col-xs-12">
			<div class="box yssz">
					<div class="box-header">
						<span class="title">生师比分析</span>
						
						<div class="actions">
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" style="padding-left: 5px;" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>				
					</div>				
					<div class="box-content">
						<div class="" id="jsblfx" style="height:233px;">
						</div>
					</div>
					
				</div>
			</div>
			<div class="col-sm-6 col-xs-12">
			<div class="box yssz">
					<div class="box-header">
						<span class="title">高校科技经费分析</span>
						
						<div class="actions">
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" style="padding-left: 5px;" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>				
					</div>				
					<div class="box-content">
						<div class="" id="ryjffx" style="height:233px;">
						</div>
					</div>
					
				</div>
			</div>	
	</div>
	<div class="row" style="margin-top:5px;margin-left:50px;margin-right:50px;">
	<div class="col-sm-12 col-xs-12">
				<div class="box jdgz ">
					<div class="box-header">
						<span class="title">项目执行情况分析</span>
						<div class="actions">
							<a href="javascript:void(0);" class="btn box-fold btn-mini btn-link" title="点击折叠或展开"><i class="faw fa-angle-up"></i></a>
						</div>
					</div>
					<div class="box-content" style="height:250px;">
					<div class="col-sm-6 col-xs-12">
					<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse">
						<thead>
							<tr>
								<th style="padding-left: 0px;padding-right: 0px;text-align: center">部门</th>
								<th style="padding-left: 0px;padding-right: 0px;text-align: center">项目数</th>
								<th style="padding-left: 0px;padding-right: 0px;text-align: center">预算总金额（元）</th>
								<th style="padding-left: 0px;padding-right: 0px;text-align: center">执行总金额（元）</th>
								<th style="padding-left: 0px;padding-right: 0px;text-align: center">执行比率</th>

							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					
						
						</div>
						<div class="col-sm-6 col-xs-12">
						<div class="" style="float:left;margin-left:5px;width:100%;">
					<div class="row">
				<div class="pull-center" style="text-align:center;margin-top:25px;">
            		目前预算总金额${xmzx.yszje}万元，执行总金额${xmzx.zxzje }万元，执行比率${xmzx.zxbl}%。
        		</div>
			</div>
			<div class="row">
				<div class="box-content">
						<div class="box-content left" id="yszje" style="height:210px;width:33%;border:none;">
						</div>
						<div class="box-content left" id="zxzje" style="height:210px;width:33%;border:none;">
						</div>
						<div class="box-content left" id="zxbl" style="height:210px;width:33%;border:none;">
						</div>
					</div>
			</div>
			
					</div>
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
    <script src="${pageContext.request.contextPath}/static/plugins/echarts/echarts.js"></script> 
	<script src="${pageContext.request.contextPath}/webView/system/index/xzPicture.js"></script> 
	<%-- <script src="${pageContext.request.contextPath}/webView/system/index/newPicture.js"></script>  --%>
	<script src="${pageContext.request.contextPath}/static/javascript/index/newDesk.js"></script>  
	<script type="text/javascript">
	
	$(function () {
		yszje();
		zxzje();
		zxbl();
	   	 var columns = [
	   	                   
	   	                   {"data": "BMBH1",defaultContent:"","class":"text-center","width":40},
	   	                   {"data": "SL",defaultContent:"","class":"text-right","width":50},
	   	                   {"data": "YSJE",defaultContent:"","class":"text-right","width":50},
	   	                   {"data": "XZYE",defaultContent:"","class":"text-right","width":50},
	   	                   {"data": "DD",defaultContent:"","class":"text-right","width":50},
// 	   	                   {"data": "JFJEHJ",defaultContent:"0","id":"tr_cnr","class":"text-right","width":30},
	   	                 ];
	   	    table = getDataTabless("mydatatables","${ctx}/index/getPagexzList",[1,'desc'],columns);
 	});
	
	//myChart();
	
// 	$(function(){
//  	$.ajax({
// 		url:'${ctx}/index/getPyccfx',//培养层次分析
// 		data:{sjid:sjid },
// 		dataType:'json',
// 		type:'post',
// 		success:function(data){
// 			renderPaper(data)
// 		}
// 	}) 	
// })
	
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
	    		
	    		$.ajax({
	    			url:"${ctx}/index/getTzggList?js=xz",
	    			data:"",
	    			async:false,
	    			dataType:"json",
	    			success:function(data){	    				
	    				if(data){
	    					$.each(data,function(i,v){
	    						var index = i+1;
	    						var guid =v.GID;
	    						var maxlength=window.screen.width;
//	     						alert(maxlength);
	    						
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
	    									"<li ><div id='dd' ><a href='javascript:void(0);' class=' btn_look color'>"+resetTzggTitle(v.TITLE,25)+"</a></div></li>"+     								
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
//    					guid = guid.substring(0,guid.lastIndexOf(","));
	//    					$("[name='gid']").val(guid);
 	  //  					alert(guid);
	    					//autoRowSpan(tbody,0,0);
	    				}
	    			}
	    		});
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
	    		var myChart = echarts.init(document.getElementById('yszc'));
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
	//查看操作
	 $(document).on("click",".btn_look",function(){
    		
 		var gid = $(this).parents("ul").find("[name='gid']").val();
 		select_commonWin("${ctx}/window/goTzggEdit?operateType=L&gid="+gid,"通知公告", "920", "630");
 	});
    $(function(){

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
function yszje(){
	var myChart = echarts.init(document.getElementById('yszje'));
	var itemStyle0 = {
			normal:{color:'#FF8483'}
	};
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'left',
		        data:['预算总金额']
		    },
		    series: [
		        {
// 		            name:'预算总金额${xmzx.yszje}万元',
		            name:'预算总金额',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            itemStyle:itemStyle0,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center',
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
		                {value:Number('${xmzx.yszje}'), name:'${xmzx.yszje}万元'},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
}
function zxzje(){
	var myChart = echarts.init(document.getElementById('zxzje'));
	var itemStyle0 = {
			normal:{color:'#358AEB'}
	};
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'left',
		        data:['执行总金额']
		    },
		    series: [
		        {
		            name:'执行总金额',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            itemStyle:itemStyle0,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center',
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
		                {value:Number('${xmzx.zxzje}'), name:'${xmzx.zxzje}万元'},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
}
function zxbl(){
	var myChart = echarts.init(document.getElementById('zxbl'));
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'left',
		        data:['执行比率']
		    },
		    color:['#3EEDE7','#E8E8E8'],
		    series: [
		        {
		            name:'执行比率',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		                normal: {
		                    show: true,
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
		                {value:Number('${xmzx.zxbl}'), name:'${xmzx.zxbl}%'},
// 		                {value:100-Number('${xmzx.zxbl}'), name:'${xmzx.zxbl}%'},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
}
$(document).on("click","#menu_xqfx",function(){
	 window.open("http://127.0.0.1:8081/zhxqfx/");
});
$(document).on("click","#menu_ysgl",function(){
	window.open("http://127.0.0.1:8066/WhcwDemo/");
})
</script>
</body>
</html>