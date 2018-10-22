<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>结算方式设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.style_div{
		border: 1px solid #dddddd;
		border-radius: 4px;
		margin-top: 20px;
		padding-top: 5px;
		padding-left: 15px;
		padding-bottom: 10px;
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
  			border-bottom-width: 1px!important;
   	}
     table{
		border-collapse:collapse!important;
	}
	.btn_td{
		text-align:center;
	}
	.btn_td{
		width:14mm!important;
		height:6mm!important;
	}
	.selWindow{
		width:280px!important;
	}
	
	.number,.sz{
		text-align:right;
	}
	.yc{
		display:none;
	}
	tbody input{
		border:none;
	}
	thead th{
		text-align:center!important;
	}
	.noBlock{
		margin-bottom: 3px;
    	position: absolute;
    	z-index: 999;
   		display: block;
   		padding-left: 17%;
   		color: red;
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	    
	}
	[class^=addBtn]{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	[class^=addBtn]:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	[class^=deleteBtn]{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	[class^=deleteBtn]:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.text-green{
		color:green!important;
	}
	.tsinput{
		color:blue;
		border:none;
		padding-left:5px;
		font-size:18px!important;
	}
	.tyspan{
		color:black;
	}
	.noselect{
		font-size:14px;
		color:#CCCCCC;
	}
	.radiodiv{
	    border:none;
 	    border-radius:4px 0px 0px 4px;
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	    width:300px;
	}
</style>
</head>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
String sysTime = sdf.format(new Date());
%>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>第三步,结算方式设置</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-green">选择项目&nbsp;></div>
					
					<div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
	    			</div>
					</div>
					<div class="sub-title pull-left  text-green">差旅报销业务办理&nbsp;></div>
					
					<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-primary">结算方式设置&nbsp;</div>
					
<!-- 					<div class="sub-title pull-left text-primary"> -->
<!-- 					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;"> -->
<!-- 	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">4</span> -->
<!-- 	    			</div> -->
<!-- 					</div> -->
<!-- 					<div class="sub-title pull-left">保存</div> -->
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>基本信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
            		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
					</button>
			  		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			  		<button type="button" class="btn btn-default" id="btn_back">返回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据单号：</span>
                            <input type="text" id="txt_ywdh" class="form-control input-radius" readonly value="<%=sysTime%>">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">借款金额(元)：</span>
							<input type="" id="txt_ywzje" class="form-control input-radius number" style="text-align:right;color:green;font-size:18px;" readonly value="${param.total }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">结算金额(元)：</span>
							<input type="" id="txt_jszje" class="form-control input-radius number" style="text-align:right;color:green;font-size:18px;" readonly value=""/>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
		<div class="box" id="operate">
				<div class="box-panel">	
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary" style="font-size:15px;"><i class="fa icon-xiangxixinxi" style="font-size:20px;"></i>结算方式<span style="color:green;">(可多选)</span>
            	</div>
	        </div>
	        <hr class="hr-normal">
			<div class="style_div">
			 <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>现金结算
            	&nbsp;&nbsp;
            	<span class="noselect">
            		<input type="checkbox" class="xjjsbox" value="" />
            		<span>未选择</span>
            	</span>
            	&nbsp;&nbsp;
            	<span class="tyspan xjjs_xj"><strong>小计:</strong><input type="" class="tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="xjjs"></i></a>
            	</div>
	        </div>
	        <div class="container-fluid box-content xjjs-content">
	       		<div class="row">
					<div class="col-md-4">
						<div class="input-group">
						    <div class="radiodiv">
								<input type="radio" id="txt_xjlk" name="xjjsname" class=""/>&nbsp;&nbsp;&nbsp;&nbsp;现金领款
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" id="txt_xjjk" name="xjjsname" class=""/>&nbsp;&nbsp;&nbsp;&nbsp;现金缴款
							</div>
						</div>
				    </div>
				</div>
				<div class="row">
					<div class="col-md-4" style="height:25px;">
						<div class="input-group" style="width:100%;">
							<span class="input-group-addon" style="background-color:transparent;border:none;">金&emsp;&emsp;额</span>
							<input type="text" id="txt_xjjsje" class="form-control input-radius number" name="xjjsje" value="" 
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" style="width:100px;"/>
							&nbsp;&nbsp;<span style="color:red;">(现金金额超过2000元,建议选择汇款、转卡等其他结算方式)</span>
						</div>
					</div>
				</div>
			</div>
			</div>
			<!-- 实时转卡 -->
			<div class="style_div">
			 <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>实时转卡
            	&nbsp;&nbsp;
            	<span class="noselect">
            		<input type="checkbox" class="sszkbox" value="" />
            		<span>未选择</span>
            	</span>
            	&nbsp;&nbsp;
            	<span class="tyspan sszk_xj"><strong>小计:</strong><input type="" class="tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="sszk"></i></a>
            	</div>
	        </div>
	        <div class="container-fluid box-content sszk-content">
	       			<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">操作</th>
				            <th style="width:300px;">部门</th>
				            <th style="width:300px;">姓名</th>
				            <th style="width:500px;">银行名称</th>
				            <th style="width:500px;">银行卡(折)号</th>
				            <th style="width:100px;">金额</th>
				        </tr>
					</thead>
				    <tbody id="tb_sszk">
						<tr class="tr_1">
							<td class="btn_td"><div class="addBtn_ZK"></div></td>
							<td>
				    		<div class="input-group">
				    			<input type="text" id="txt_bm1" class="form-control input-radius" readonly value="">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_bm1" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
				    		</td>
				    		<td>
				    		<div class="input-group">
				    			<input type="text" id="txt_xm1" class="form-control input-radius" readonly value=""">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_xm1" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_yhmc1" class="form-control input-radius"  value="">
							</td>
							<td>
							<input type="text" id="txt_yhkh1" class="form-control input-radius"  value="">
							</td>
							<td>
							<input type="text" id="txt_zkje1" class="form-control input-radius number"  value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
						</tr>
						
				    </tbody>
				</table>
			</div>
			</div>
			<!-- 网银汇款 -->
			<div class="style_div">
			 <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>网银汇款
            	&nbsp;&nbsp;
            	<span class="noselect">
            		<input type="checkbox" class="wyhkbox" value="" />
            		<span>未选择</span>
            	</span>
            	&nbsp;&nbsp;
            	<span class="tyspan wyhk_xj"><strong>小计:</strong><input type="" class="tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="wyhk"></i></a>
            	</div>
	        </div>
	        <div class="container-fluid box-content wyhk-content">
	       		<div class="row">
					<div class="col-md-4">
						<div class="input-group">
						    <div class="radiodiv">
								<input type="radio" id="txt_yhfk" name="wyhkname" class=""/>&nbsp;&nbsp;&nbsp;&nbsp;银行付款
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" id="txt_yhsk" name="wyhkname" class=""/>&nbsp;&nbsp;&nbsp;&nbsp;银行收款
							</div>
						</div>
				    </div>
				</div>
				<div class="row yhsk_row">
					<div class="col-md-4" style="height:25px;">
						<div class="input-group" style="width:100%;">
							<span class="input-group-addon" style="background-color:transparent;border:none;">金&emsp;&emsp;额</span>
							<input type="text" id="txt_yhskje" class="form-control input-radius number" name="yhskje" value="" 
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" style="width:150px;"/>
							&nbsp;&nbsp;
							<span class="input-group-addon" style="background-color:transparent;border:none;">到款编码</span>
							<input type="text" id="" class="form-control input-radius " name="" value=""  style="width:150px;"/>
							&nbsp;&nbsp;
						</div>
					</div>
				</div>
				<table id="mydatatables_wyhk" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">操作</th>
				            <th style="width:300px;">收款单位</th>
				            <th style="width:300px;">开户银行</th>
				            <th style="width:300px;">省份</th>
				            <th style="width:300px;">收款账号</th>
				            <th style="width:300px;">汇款用途</th>
				            <th style="width:100px;">汇款金额</th>
				        </tr>
					</thead>
				    <tbody id="tb_wyhk">
						<tr class="tr_1">
							<td class="btn_td"><div class="addBtn_WY"></div></td>
							<td>
				    			<input type="text" id="txt_skdw1" name="skdw1" class="form-control input-radius" readonly value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_yhmc1" name="yhmc1" class="form-control input-radius" readonly value="">
							</td>
							<td>
							<input type="text" id="txt_wdss1" name="wdss1" readonly class="form-control input-radius"  value="">
							</td>
							<td>
							<input type="text" id="txt_skzh1" name="skzh1" readonly class="form-control input-radius"  value="">
							</td>
							<td>
							<input type="text" id="txt_ytfy1" name="ytfy1" readonly class="form-control input-radius "  value="" >
							</td>
							<td>
							<input type="text" id="txt_hkje1" name="hkje1" readonly class="form-control input-radius number"  value="" >
							</td>
						</tr>
						
				    </tbody>
				</table>
			</div>
			</div>
			<!-- pos还款 -->
			<div class="style_div">
			 <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>pos还款&ensp;
            	&nbsp;&nbsp;
            	<span class="noselect">
            		<input type="checkbox" class="posbox" value="" />
            		<span>未选择</span>
            	</span>
            	&nbsp;&nbsp;
            	<span class="tyspan pos_xj"><strong>小计:</strong><input type="" class="tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="pos"></i></a>
            	</div>
	        </div>
	        <div class="container-fluid box-content pos-content">
				<div class="row">
					<div class="col-md-4" style="height:25px;">
						<div class="input-group" style="width:100%;">
							<span class="input-group-addon" style="background-color:transparent;border:none;">金&emsp;&emsp;额</span>
							<input type="text" id="txt_posje" class="form-control input-radius number" name="posje" value="" 
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" style="width:100px;"/>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
	</div>
	<div class="container-fluid box-content">
		<div class="row">
			<div class="pull-center" style="text-align:center;">
	    		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
	    		<button type="button" class="btn btn-default" id="btn_next" style="background:#00acec;color: white;">提交</button>
	 		</div>
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="kz.js"></script>
<script src="${pageContext.request.contextPath}/webView/wsbx/rcbx/kz.js"></script> 
<script type="text/javascript">
var tag = true;
$(function(){
	//控制显示
	kz();
	//返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/webView/wsbx/jkyw/jkyw_list.jsp?mkbh=${param.mkbh}";
	});
	$("#btn_after").click(function(){
		location.href="${ctx}/webView/wsbx/jkyw/jkyw_add_step2.jsp?mkbh=${param.mkbh}";
	});
	$("#btn_next").click(function(){
		var ywzje = $("#txt_ywzje").val();
		var jszje = $("#txt_jszje").val();
		if(ywzje==jszje&&ywzje!=""){
			alert("操作成功");
			//location.href="${ctx}/wsbx/ccbx/goCcbxListPage";
		}else{
			alert("业务总金额必须等于结算总金额并且不为空！");
			return;
		}
	});
	
	//保存按钮
	$("#btn_save").click(function(e){
		var ywzje = $("#txt_ywzje").val();
		var jszje = $("#txt_jszje").val();
		console.log(jszje+","+ywzje);
		if(ywzje==jszje&&ywzje!=""){
			alert("操作成功");
		}else{
			alert("业务总金额必须等于结算总金额并且不为空！");
			return;
		}
	});	
	function doSave(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(!tag){
			valid = false;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){
				},
				error:function(val){
					alert("保存成功！");					
				}	
			});
		}
	}
	
});
</script>
<!-- 各种计算js -->
<script type="text/javascript">
//默认隐藏的元素
function kz(){
	$(".cxjk-content").css("display","none");
	$(".xjjs-content").css("display","none");
	$(".sszk-content").css("display","none");
	$(".wyhk-content").css("display","none");
	$(".pos-content").css("display","none");
	$(".xnzz-content").css("display","none");
	$(".gwk-content").css("display","none");
	$(".yhsk_row").css("display","none");
	$("#mydatatables_wyhk").css("display","none");
}
//小计字体颜色的变化
function xjcolor(val,clw){
	if(isNaN(val)||val==""){
		val = 0;
	}
	if(val==0){
		$("[class*='"+clw+"']").find("input").css("color","blue");
	}
	if(val>0){
		$("[class*='"+clw+"']").find("input").css("color","green");
	}
	if(val<0){
		$("[class*='"+clw+"']").find("input").css("color","red");
	}
	jszje();
}
//计算结算总金额
function jszje(){
	var money = $(":checkbox").filter(":checked").parents("div").find("[class$=tsinput]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$("[id=txt_jszje]").val(count);
	if(count>0){
		$("[id=txt_jszje]").css("color","green");
	}else if(count<0){
		$("[id=txt_jszje]").css("color","red");
	}else{
		$("[id=txt_jszje]").css("color","blue");
	}
}
</script>
<!-- 冲销借款js -->
<script type="text/javascript">
//借款业务弹窗
$(document).on("click","[class=addBtn_JK]",function(){
	select_commonWin("${ctx}/wsbx/rcbx/window?controlId=jkyw","借款业务","920","630");
});

$(".cxjkbox").click(function(){
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parents(".style_div").find(".cxjk-content").slideDown(500);
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".cxjk-content:hidden").show();
		cxmoney();
	}else{
		$(this).parents(".style_div").find(".cxjk-content").slideUp(500);
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".cxjk-content:visible").hide();
		$(".cx_xj").find(".tsinput").val(Number("0").toFixed(2));
		xjcolor(0,"cx_xj");
	}
});
//冲销借款
var cxjk = 2;
function cxjkJs(){
	var cxjkInfo = ["2017-11-10","20171110112115","超级管理员","001","无","100.00"];
	var parentTr = $("#tb_cxjk").find(".tr_1").clone();
	parentTr.attr("class","tr_"+cxjk);
	parentTr.find("[class^=addBtn]").removeClass("addBtn_JK").addClass("deleteBtn_JK");
	parentTr.find("#txt_jkrq1").attr({"id":"txt_jkrq"+cxjk,value:cxjkInfo[0]});
	parentTr.find("#txt_jkdh1").attr({"id":"txt_jkdh"+cxjk,value:cxjkInfo[1]});
	parentTr.find("#txt_jkr1").attr({"id":"txt_jkr"+cxjk,value:cxjkInfo[2]});
	parentTr.find("#txt_xmh1").attr({"id":"txt_xmh"+cxjk,value:cxjkInfo[3]});
	parentTr.find("#txt_jksy1").attr({"id":"txt_jksy"+cxjk,value:cxjkInfo[4]});
	parentTr.find("#txt_je1").attr({"id":"txt_je"+cxjk,value:cxjkInfo[5]});
	$("#tb_cxjk").find(".tr_1").before(parentTr);
	cxjk++;
	cxmoney();
}
$(document).on("click",".deleteBtn_JK",function(){
	$(this).parents("tr").remove();
	cxmoney();
});
function cxmoney(){
	var money = $("[id^=txt_je]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$(".cx_xj").find(".tsinput").val(count);
	xjcolor(count,"cx_xj");
}
</script>
<!-- 现金结算js -->
<script type="text/javascript">
$(".xjjsbox").click(function(){
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parents(".style_div").find(".xjjs-content").slideDown(500);
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".xjjs-content:hidden").show();
		var money = $("[id=txt_xjjsje]").val();
		xjmoney(money);
	}else{
		$(this).parents(".style_div").find(".cxjk-content").slideUp(500);
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".xjjs-content:visible").hide();
		$(".xjjs_xj").find(".tsinput").val(Number("0").toFixed(2));
		xjcolor(0,"xjjs_xj");
	}
});
//输入金额
$(document).on("keyup","[id=txt_xjjsje]",function(){
	var money = $(this).val();
	xjmoney(money);
});
//点击单选框
$(document).on("click","[name='xjjsname']",function(){
	var money = $("[id=txt_xjjsje]").val();
	xjmoney(money);
});
function xjmoney(money){
	var ck = $("[name='xjjsname']").filter(":checked").attr("id");
	if(ck=="txt_xjlk"){
		money = 0+Number(money);
	}else{
		money = 0-Number(money);
	}
	$(".xjjs_xj").find(".tsinput").val(money.toFixed(2));
	xjcolor(money,"xjjs_xj");
}
</script>
<!-- 实时转卡js -->
<script type="text/javascript">
//部门
$(document).on("click","[id^=btn_bm]",function(){
	var id = $(this).parents("td").find("[id^=txt_bm]").attr("id");
	select_commonWin("${ctx}/window/dwpage?controlId="+id,"部门信息","920","630");
});

//人员弹窗
$(document).on("click","[id^=btn_xm]",function(){
	var id = $(this).parents("td").find("[id^=txt_xm]").attr("id");
	select_commonWin("${ctx}/window/rypage?controlId="+id,"人员信息","920","630");
});
$(".sszkbox").click(function(){
	$(this).parents(".style_div").find(".sszk-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".sszk-content:hidden").show();
		sszkmoney();
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".sszk-content:visible").hide();
		$(".sszk_xj").find(".tsinput").val(Number("0").toFixed(2));
		xjcolor(0,"sszk_xj");
	}
});
//
var sszk = 2;
$(document).on("click",".addBtn_ZK",function(){
	var parentTr = $(this).parents("tr").clone();
	parentTr.attr("class","tr_"+sszk);
	$(this).removeClass("addBtn_ZK").addClass("deleteBtn_ZK");
	parentTr.find("[id^=txt_bm]").attr({"id":"txt_bm"+sszk});
	parentTr.find("[id^=txt_xm]").attr({"id":"txt_xm"+sszk});
	parentTr.find("[id^=txt_yhmc]").attr({"id":"txt_yhmc"+sszk});
	parentTr.find("[id^=txt_yhkh]").attr({"id":"txt_yhkh"+sszk});
	parentTr.find("[id^=txt_zkje]").attr({"id":"txt_zkje"+sszk});
	parentTr.find("input").val("");
	$(this).parents("tr").after(parentTr);
	sszk++;
});
$(document).on("click",".deleteBtn_ZK",function(){
	$(this).parents("tr").remove();
	sszkmoney();
});
//实时转卡输入
$(document).on("keyup","[id^=txt_zkje]",function(){
	sszkmoney();
});
//实时转卡金额计算
function sszkmoney(){
	var money = $("[id^=txt_zkje]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$(".sszk_xj").find(".tsinput").val(count);
	xjcolor(count,"sszk_xj");
}
</script>
<!--网银汇款js -->
<script type="text/javascript">
var wyhk = 2;
$(".wyhkbox").click(function(){
	var clw = $(this).parent("span").attr("class");
	$(this).parents(".style_div").find(".wyhk-content").toggle(500);
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".wyhk-content:hidden").show();
		if($("[name='wyhkname']").filter(":checked").attr("id")=="txt_yhfk"){
			yhfkmoney();
		}else{
			yhskmoney();
		}
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".wyhk-content:visible").hide();
		$(".wyhk_xj").find(".tsinput").val(Number("0").toFixed(2));
		xjcolor(0,"wyhk_xj");
	}
});
$(document).on("click","[name='wyhkname']",function(){
	var id = $(this).attr("id");
	if(id=="txt_yhsk"){
		$(".yhsk_row").css("display","");
		$("#mydatatables_wyhk").css("display","none");
		yhskmoney();
	}else{
		$(".yhsk_row").css("display","none");
		$("#mydatatables_wyhk").css("display","");
		select_commonWin("${ctx}/wsbx/rcbx/window?controlId=hkxx","汇款信息","920","630");
		yhfkmoney();
	}
});
function addHkInfo(data){
	var parentTr = $("#tb_wyhk").find(".tr_1").clone();
	parentTr.find("input").val("");
	parentTr.attr("class","tr_"+wyhk);
	parentTr.find(".addBtn_WY").removeClass("addBtn_WY").addClass("deleteBtn_WY");
	$.each(data,function(i,v){
		var name = v.name;
		var val = v.value;
		parentTr.find("[name^='"+name+"']").attr({"id":"txt_"+name+wyhk,"name":name+wyhk});
		parentTr.find("[name^='"+name+"']").val(val);
	});
	$("#tb_wyhk").find(".tr_1").before(parentTr);
	wyhk++;
	yhfkmoney();
}
$(document).on("click",".addBtn_WY",function(){
	select_commonWin("${ctx}/wsbx/rcbx/window?controlId=hkxx","汇款信息","920","630");
});
$(document).on("click",".deleteBtn_WY",function(){
	$(this).parents("tr").remove();
	yhfkmoney();
});
//计算银行收款
$(document).on("keyup","[id=txt_yhskje]",function(){
	yhskmoney();
});
function yhskmoney(){
	var money = $("[id=txt_yhskje]").val();
	money = 0-Number(money);
	$(".wyhk_xj").find(".tsinput").val(money.toFixed(2));
	xjcolor(money,"wyhk_xj");
}
//计算银行付款
function yhfkmoney(){
	var money = $("[id^=txt_hkje]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$(".wyhk_xj").find(".tsinput").val(count);
	xjcolor(count,"wyhk_xj");
}
</script>
<!-- pos还款js -->
<script type="text/javascript">
$(".posbox").click(function(){
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parents(".style_div").find(".pos-content").toggle(500);
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".pos-content:hidden").show();
		posmoney();
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".pos-content:visible").hide();
		$(".pos_xj").find(".tsinput").val(Number(0).toFixed(2));
		xjcolor(0,"pos_xj");
	}
});
//计算pos金额
$(document).on("keyup","[name=posje]",function(){
	posmoney();
});
function posmoney(){
	var money = $("[name=posje]").val();
	money = 0-money;
	$(".pos_xj").find(".tsinput").val(money.toFixed(2));
	xjcolor(money,"pos_xj");
}
</script>
<!-- 校内转账 -->
<script type="text/javascript">
//转账弹窗
$(document).on("click","[class=addBtn_XN]",function(){
	select_commonWin("${ctx}/wsbx/rcbx/window?controlId=xnzz","转账记录","920","630");
});

$(".xnzzbox").click(function(){
	$(this).parents(".style_div").find(".xnzz-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".xnzz-content:hidden").show();
		xnzzmoney();
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".xnzz-content:visible").hide();
		$(".xnzz_xj").find(".tsinput").val(Number(0).toFixed(2));
		xjcolor(0,"xnzz_xj");
	}
});
var xnzz = 2;
function xnzzJs(){
	var xnzzInfo = ["2017-11-10","教务处","这是个项目","超级管理员","100.00","项目转账经费"];
	var parentTr = $("#tb_xnzz").find(".tr_1").clone();
	parentTr.attr("class","tr_"+xnzz);
	parentTr.find("[class^=addBtn]").removeClass("addBtn_XN").addClass("deleteBtn_XN");
	parentTr.find("#txt_zzrq1").attr({"id":"txt_zzrq"+xnzz,value:xnzzInfo[0]});
	parentTr.find("#txt_bmm1").attr({"id":"txt_bmm"+xnzz,value:xnzzInfo[1]});
	parentTr.find("#txt_jfxm1").attr({"id":"txt_jfxm"+xnzz,value:xnzzInfo[2]});
	parentTr.find("#txt_zzry1").attr({"id":"txt_zzry"+xnzz,value:xnzzInfo[3]});
	parentTr.find("#txt_zzje1").attr({"id":"txt_zzje"+xnzz,value:xnzzInfo[4]});
	parentTr.find("#txt_zzsm1").attr({"id":"txt_zzsme"+xnzz,value:xnzzInfo[5]});
	$("#tb_xnzz").find(".tr_1").before(parentTr);
	xnzz++;
	xnzzmoney();
}
$(document).on("click",".deleteBtn_XN",function(){
	$(this).parents("tr").remove();
	xnzzmoney();
});
function xnzzmoney(){
	var money = $("[id^=txt_zzje]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$(".xnzz_xj").find(".tsinput").val(count);
	xjcolor(count,"xnzz_xj"); 
}
</script>
<!-- 公务卡js -->
<script type="text/javascript">
//转账弹窗
$(document).on("click","[class=addBtn_GWK]",function(){
	select_commonWin("${ctx}/wsbx/rcbx/window?controlId=gwkxx","本人消费记录","920","630");
});

$(".gwkbox").click(function(){
	$(this).parents(".style_div").find(".gwk-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".gwk-content:hidden").show();
		gwkmoney();
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".gwk-content:visible").hide();
		$(".gwk_xj").find(".tsinput").val(Number(0).toFixed(2));
		xjcolor(0,"gwk_xj");
	}
});

var gwk = 2;
function gwkJs(){
	var gwkInfo = ["超级管理员","301258252252","2017-10-05","山东国子软件","100.00","100.00"];
	var parentTr = $("#tb_gwk").find(".tr_1").clone();
	parentTr.attr("class","tr_"+gwk);
	parentTr.find("[class^=addBtn]").removeClass("addBtn_GWK").addClass("deleteBtn_GWK");
	parentTr.find("#txt_ckrxm1").attr({"id":"txt_ckrxm"+gwk,value:gwkInfo[0]});
	parentTr.find("#txt_gwkh1").attr({"id":"txt_gwkh"+gwk,value:gwkInfo[1]});
	parentTr.find("#txt_xfrq1").attr({"id":"txt_xfrq"+gwk,value:gwkInfo[2]});
	parentTr.find("#txt_xfsh1").attr({"id":"txt_xfsh"+gwk,value:gwkInfo[3]});
	parentTr.find("#txt_skje1").attr({"id":"txt_skje"+gwk,value:gwkInfo[4]});
	parentTr.find("#txt_sbje1").attr({"id":"txt_sbje"+gwk,value:gwkInfo[5]});
	$("#tb_gwk").find(".tr_1").before(parentTr);
	gwk++;
	gwkmoney();
}
$(document).on("click",".deleteBtn_GWK",function(){
	$(this).parents("tr").remove();
	gwkmoney();
});
function gwkmoney(){
	var money = $("[id^=txt_sbje]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	count = 0-Number(count);
	$(".gwk_xj").find(".tsinput").val(count.toFixed(2));
	xjcolor(count,"gwk_xj"); 
}
$("#btn_ywsm").click(function(){
	zysx();
});
function zysx(){
	//业务说明
	select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
}
</script>
</html>