<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.googosoft.util.DateUtil"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>教职工基础信息分析</title>
	<%@include file="/static/include/public-draw-css.inc"%>
	<%@include file="/static/include/public-draw-js.inc"%>
	<%
	String today = DateUtil.getDay();
	%>
</head>
<body >
<form id="form" class="form-horizontal" action="" method="post">    
    <div class="page-header" > 
        <div class="row">
            <div class=" col-md-3" style="margin-left: 5px;">
                <div class="input-group" >
                    <span class="input-group-addon">统计日期</span>
                    <input type="text" id="txt_jzrq" class="form-control date" name="rq" data-format="yyyy-mm-dd" placeholder="请输入统计日期"  value="<%=today %>">
                    <span class="input-group-addon"> 
                       <i class="glyphicon glyphicon-calendar"></i>
                    </span>
                </div>
            </div>
            <div  class=" alert alert-info " style="padding: 6px;margin-bottom: 3px; margin-right:10px; float: left;">
                <span><b>提示：</b>切换日期页面自动刷新，统计截止到所选日期的在职教职工情况。</span>
            </div>
        </div>
        <div class="row" >
            <div class="col-md-12" style="margin-top:-2px; margin-left: 5px;">
                <div class='box-panel' style="height:30px;line-height:30px;background: #d9edf7;color: #31708f;" id="all">
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid" >
	    <div class="row" style="padding-right: 15px;  margin-top: 85px; ">
		    <div class="col-md-6"  >
			    <div class="box">
				    <div class='box-panel'>
			     	    <div class='box-header clearfix'>
			         	    <div class="sub-title pull-left text-primary">
			            	    <i class='fa icon-jibenxinxi'></i>单位分析
			         	    </div>
				   		    <div class='actions'>
				       		    <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
				   		    </div>
			     	    </div>
					    <hr class="hr-normal">
					    <div class="container-fluid box-content" style="height:350px;" id="d_dw">
					    </div>
			 	    </div>
			    </div>
		    </div>
            <div class="col-md-6">
                <div class="box">
                    <div class='box-panel'>
                        <div class='box-header clearfix'>
                            <div class="sub-title pull-left text-primary">
                                <i class='fa icon-jibenxinxi'></i> 师生比分析
                            </div>
                            <div class='actions'>
                                <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
                            </div>
                        </div>
                        <hr class="hr-normal">
                        <div class="container-fluid box-content" style="height:350px;" id="d_ssb">
                        </div>
                    </div>
                </div>
            </div>
        </div>
	    <div class="row" style="margin-top: 0px; padding-right: 15px;">
		    <div class="col-md-6">
			    <div class="box">
				    <div class='box-panel'>
			     	    <div class='box-header clearfix'>
			         	    <div class="sub-title pull-left text-primary">
			            	    <i class='fa icon-jibenxinxi'></i>文化程度分析
			         	    </div>
				   		    <div class='actions'>
				       		    <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
				   		    </div>
			     	    </div>
					    <hr class="hr-normal">
					    <div class="container-fluid box-content" style="height:300px;" id="d_whcd">
					    </div>
			 	    </div>
			    </div>
		    </div>
		    <div class="col-md-6">
                <div class="box">
                    <div class='box-panel'>
                        <div class='box-header clearfix'>
                            <div class="sub-title pull-left text-primary">
                                <i class='fa icon-jibenxinxi'></i>    职称分析
                            </div>
                            <div class='actions'>
                                <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
                            </div>
                        </div>
                        <hr class="hr-normal">
                        <div class="container-fluid box-content" style="height:300px;" id="d_zc">
                        </div>
                    </div>
                </div>
            </div>
	    </div>
	    <div class="row" style="margin-top: 0px; padding-right: 15px;">
		    <div class="col-md-6">
                <div class="box">
                    <div class='box-panel'>
                        <div class='box-header clearfix'>
                            <div class="sub-title pull-left text-primary">
                                <i class='fa icon-jibenxinxi'></i>   政治面貌分析
                            </div>
                            <div class='actions'>
                                <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
                            </div>
                        </div>
                        <hr class="hr-normal">
                        <div class="container-fluid box-content" style="height:300px;" id="d_zzmm">
                        </div>
                    </div>
                </div>
            </div>
		    <div class="col-md-6">
			    <div class="box">
				    <div class='box-panel'>
			     	    <div class='box-header clearfix'>
			         	    <div class="sub-title pull-left text-primary">
			            	    <i class='fa icon-jibenxinxi'></i>编制类别分析
			         	    </div>
				   		    <div class='actions'>
				       		    <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
				   		    </div>
			     	    </div>
					    <hr class="hr-normal">
					    <div class="container-fluid box-content" style="height:300px;" id="d_bzlb">
					    </div>
			 	    </div>
			    </div>
		    </div>
	    </div>
	    <div class="row">
		    <div class="col-md-12">
			    <div class="box">
				    <div class='box-panel'>
			     	    <div class='box-header clearfix'>
			         	    <div class="sub-title pull-left text-primary">
			            	    <i class='fa icon-jibenxinxi'></i>民族分析
			         	    </div>
				   		    <div class='actions'>
				       		    <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
				   		    </div>
			     	    </div>
					    <hr class="hr-normal">
					    <div class="container-fluid box-content" style="height:300px;" id="d_mz">
					    </div>
			 	    </div>
			    </div>
		    </div>
	    </div>
	    <div class="row">
		    <div class="col-md-12">
			    <div class="box">
				    <div class='box-panel'>
			     	    <div class='box-header clearfix'>
			         	    <div class="sub-title pull-left text-primary">
			            	    <i class='fa icon-jibenxinxi'></i>年龄分析
			         	    </div>
				   	        <div class='actions'>
				       		    <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
				   		    </div>
			     	    </div>
					    <hr class="hr-normal">
					    <div class="container-fluid box-content" style="height:300px;" id="d_nl">
					    </div>
			 	    </div>
			    </div>
		    </div>
	    </div>
	    <div class="row" style="margin-top: 0px; padding-right: 15px;">
		    <div class="col-md-6">
			    <div class="box">
				    <div class='box-panel'>
			     	    <div class='box-header clearfix'>
			         	    <div class="sub-title pull-left text-primary">
			            	    <i class='fa icon-jibenxinxi'></i>籍贯分析
			         	    </div>
				   		    <div class='actions'>
				       		    <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
				   		    </div>
			     	    </div>
					    <hr class="hr-normal">
					    <div class="container-fluid box-content" style="height:500px;" id="d_jg">
					    </div>
			 	    </div>
			    </div>
		    </div>
		    <div class="col-md-6">
			    <div class="box">
				    <div class='box-panel'>
			     	    <div class='box-header clearfix'>
			         	    <div class="sub-title pull-left text-primary">
			            	    <i class='fa icon-jibenxinxi'></i>国籍分析
			         	    </div>
				   		    <div class='actions'>
				       		    <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
				   		    </div>
			     	    </div>
					    <hr class="hr-normal">
					    <div class="container-fluid box-content" style="height:500px;" id="d_gj">
					    </div>
			 	    </div>
			    </div>
		    </div>
	    </div>
  </div>
</form>
<script type="text/javascript">
$(function(){
	operate();
	
	$(document).on("change","#txt_jzrq",function(){
		operate();
	});
});
function operate(){
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/jzgjcxxfx/getJzgjcxxfx",
		data:$("form").serialize(),
		success:function(val){
			close(index);
			val = $.trim(val);
			val = JSON.getJson(val);
			
			var tjrq = $("#txt_jzrq").val();
			if(val.success){
				if(val.all){//统计到目前为止的数据
					$("#all").html("<div>&emsp;截止到"+val.all.day+"，在职教职工："+val.all.zrs+"人，男性教职工："+val.all.boys+"人，女性教职工："+val.all.girls+"人。</div>");
				}else{
					$("#all").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
				if(val.dwoption){//单位
					setBarTb($("#d_dw")[0],val.dwoption);
				}else{
					$("#d_dw").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
				if(val.whcdoption){//文化程度
					setBarTb($("#d_whcd")[0],val.whcdoption);
				}else{
					$("#d_whcd").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
 				if(val.zcoption){//职称
 					var zc = setPieTb($("#d_zc")[0],val.zcoption);
 					//开始点击事件
					zc.on("click",function(params){
						select_commonWin("${pageContext.request.contextPath}/webView/window/jzggkfx/jzgmxwindows.jsp?rq="+tjrq+"&zc="+params.name,"<b>"+params.name+"</b>职称教职工详情","1000","450");
						console.log(params);
					});
					//结束点击事件
 				}else{
					$("#d_zc").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
				if(val.bzlboption){//编制类别
//  					setScatterTb($("#d_bzlb")[0],val.bzlboption);
 					setPieTb($("#d_bzlb")[0],val.bzlboption);
 				}else{
					$("#d_bzlb").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
 				if(val.zzmmoption){//政治面貌
 					//setFunnelTb($("#d_zzmm")[0],val.zzmmoption);
 					//setBarTb($("#d_zzmm")[0],val.zzmmoption);
 					setRadarTb($("#d_zzmm")[0],val.zzmmoption);
 				}else{
					$("#d_zzmm").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
 				if(val.mzoption){//民族
 					var mz = setPieTb($("#d_mz")[0],val.mzoption);
 					// 开始点击事件
					mz.on("click",function(params){
						var str ="mz="+params.name+"&xb="+((params.seriesIndex==0) ? '' : (params.seriesIndex==1 ? '男' : '女'));
						select_commonWin("${pageContext.request.contextPath}/webView/window/jzggkfx/jzgmxwindows.jsp?rq="+tjrq+"&"+str,"<b>"+params.name+"</b>职工详情","1000","450");
						console.log(params);
					});
					//结束点击事件
 				}else{
					$("#d_mz").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
 				if(val.gjoption){//国籍
 					setMapTb($("#d_gj")[0],val.gjoption);
 				}else{
					$("#d_gj").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
 				if(val.nloption){//年龄
 					setBarTb($("#d_nl")[0],val.nloption);
 				}else{
					$("#d_nl").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
 				if(val.jgoption){//籍贯
 					var jg = setMapTb($("#d_jg")[0],val.jgoption);
 					//开始点击事件
					jg.on("click",function(params){
						if(params.name=="内蒙古"){
							select_commonWin("${pageContext.request.contextPath}/webView/window/jzggkfx/jzgmx_map_windows.jsp?rq="+tjrq+"&jgs="+params.name,"<b>"+params.name+"</b> 籍职工分布","800","600");
						}else{
							select_commonWin("${pageContext.request.contextPath}/webView/window/jzggkfx/jzgmxwindows.jsp?rq="+tjrq+"&jgs="+params.name,"<b>"+params.name+"</b> 籍职工明细","1000","450");
						}
					});
					//结束点击事件
 				}else{
					$("#d_jg").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
 				if(val.ssboption){//师生比
 					setBarTb($("#d_ssb")[0],val.ssboption);
 				}else{
					$("#d_ssb").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
			}else{
				alert(val.msg);
			}
		},
		error:function(val){
			close(index);
			alert(val);
		},
		beforeSend:function(val){
			index = loading();
		}
	});
}
</script>
</body>
</html>