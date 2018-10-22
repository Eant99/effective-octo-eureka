<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>日常报销</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
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
	.addBtn{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	.addBtn:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	}
	#tbody input{
		border:none;
		width:100%;
	} 
	.deleteBtn{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
	    position: relative;
	    cursor: pointer;
/* 	    top:3px; */
	}
	.text-green{
		color:green!important;
	}
	tr{
background-color: white !important;
}
tbody td{
	   padding:4px !important;
	}
	.hs{
/* 	  background-color:#cccccc; */
	}
</style>
</head>
<body>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println(path+"@"+basePath);
%>
123123
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid" id="guid" value="${guid}">
	<input type="hidden" name="ffzje" id="ffzje" value="">
	<input type="hidden" name="mbbz" id="mbbz" value="${map.mbbz}">
	<input type="hidden" name="ffry" id="ffry" value="${rylx}">
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	<c:choose>
            		<c:when test="${operateType == 'L'}">查看补助学金录入信息</c:when>
            		<c:otherwise>查看补助学金录入信息</c:otherwise>
            	</c:choose>
			</span>
		</div>
        <div class="pull-right">
			<c:if test="${operateType == 'U' or operateType == 'C'}">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			</c:if>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>补助学金录入信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
			
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">方案编号</span>
							<c:if test="${operateType == 'C' }">
								<input type="text" id="txt_fabh" class="form-control input-radius" readonly="readonly" style="text-align: left;" name="fabh"  value="系统自动生成"/>
							</c:if>
							<c:if test="${ operateType == 'U' }">
								<input type="text" id="txt_fabh" class="form-control input-radius " readonly="readonly" style="text-align: left;" name="fabh" value="${bzxj.fabh}"/>
							</c:if>
							<c:if test="${ operateType == 'L' }">
								<input type="text" id="txt_fabh" class="form-control input-radius " readonly="readonly" style="text-align: left;" name="fabh" value="${bzxj.fabh}"/>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">方案名称</span>
							<c:if test="${operateType == 'C' }">
								<input type="text" id="txt_famc" class="form-control input-radius "  style="text-align: left;" name="famc" value="" />
							</c:if>
							<c:if test="${ operateType == 'U' }">
								<input type="text" id="txt_famc" class="form-control input-radius " readonly="readonly" style="text-align: left;" name="famc" value="${bzxj.famc}"/>
							</c:if>
							<c:if test="${ operateType == 'L' }">
								<input type="text" id="txt_famc" class="form-control input-radius " readonly="readonly" style="text-align: left;" name="famc" value="${bzxj.famc}"/>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>发放金额</span>
							<c:if test="${operateType == 'C' }">
								<input type="text" id="txt_ffje" class="form-control input-radius number" style="text-align: right;" name="ffje" value=""  readonly="readonly"/>
							</c:if>
							<c:if test="${ operateType == 'U' }">
								<input type="text" id="txt_ffje" class="form-control input-radius number" style="text-align: right;" name="ffje" value="${bzxj.ffje}" readonly="readonly"/>
							</c:if>
							<c:if test="${ operateType == 'L' }">
								<input type="text" id="txt_ffje" class="form-control input-radius number"  style="text-align: right;" name="ffje" value="${bzxj.ffje}" readonly="readonly"/>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>摘要</span>
							<c:if test="${operateType == 'C' }">
								<input type="text" id="txt_zy" class="form-control input-radius " style="text-align: left;" name="zy" value="" />
							</c:if>
							<c:if test="${ operateType == 'U' }">
								<input type="text" id="txt_zy" class="form-control input-radius " style="text-align: left;" name="zy" value="${bzxj.zy}"/>
							</c:if>
							<c:if test="${ operateType == 'L' }">
								<input type="text" id="txt_zy" class="form-control input-radius " readonly style="text-align: left;" name="zy" value="${bzxj.zy}"/>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目编号</span>
							<input type="text" id="txt_xmmc" class="form-control input-radiu" name="xmmc" value="${bzxj.xmmc}"/>
							<input type="hidden" id="xmbh" class="form-control input-radiu" name="xmbh" value="${bzxj.xmbh}"/>
							<span class="input-group-btn"><button type="button" id="btn_xmxx" class="btn btn-link">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>发放方式</span>
							<select class="form-control" id="txt_fffs" name="fffs">
			                	<option value="">--请选择--</option>
			                	<c:forEach items="${zffs}" var="zffs">
									<option value="${zffs.dm}" <c:if test="${zffs.mc == bzxj.fffs}">selected</c:if>>${zffs.mc}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required"></span>备注</span>
							<c:if test="${operateType == 'C' }">
								<textarea style="width:100%;height:100px" name="bz" class="form-control input-radius"></textarea>
							</c:if>
							<c:if test="${operateType == 'U' }">
							<textarea style="width:100%;height:100px" name="bz" class="form-control input-radius" >${bzxj.bz}</textarea>
							</c:if>
							<c:if test="${operateType == 'L' }">
							<textarea style="width:100%;height:100px" name="bz" class="form-control input-radius" readonly >${bzxj.bz}</textarea>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		</form>
		<form id="form2" class="myform2" action="">
		<div class="box" id="operate">
			<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>学生补助学金信息
	            	</div>
	            	<div class="actions">
	            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	            	</div>
	        </div>
	        <hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">操作</th>
				            <th style="width:300px;" id="xh"><span class="required" style="color:red">*</span>学号</th>
				            <th style="width:300px;" id="xm"><span class="required" style="color:red">*</span>姓名</th>
				            <th style="width:200px;" id="sfzh"><span class="required" style="color:red">*</span>身份证号</th>
				            <th style="width:200px;" id="yhmc"><span class="required" style="color:red">*</span>银行名称</th>
				            <th style="width:200px;" id="yhkh"><span class="required" style="color:red">*</span>银行卡号</th>
				            <th style="width:200px;" id="bzbh"><span class="required" style="color:red">*</span>标准编码</th>
				            <th style="width:200px;" id="ffje"><span class="required" style="color:red">*</span>发放金额</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
						<c:choose>
                          <c:when test="${not empty list}">
  								<c:forEach var="list" items="${list}" varStatus="sr"> 
							    	<tr id="tr_${sr.index+1}">
							    		<td class="btn_td">
				                               <div class="addBtn add"></div>
							    		</td>
							    		<td class="xhh hide">${sr.index+1}</td>
							    		<td>
									    <div class="input-group">
										<input type="text" id="txt_xh_${sr.index+1}" a="a"  class=" null window input-radius" name="xh" value="${list.xh}">
										<span class="input-group-btn">
							    			<button type="button" id="btn_xsxx" onclick="xsxx(this)" class="btn btn-link btn-custom">选择</button>
						    			</span>
										<input type="hidden" id="xsguid_${sr.index+1}" name="xsguid" value="${list.rybh}"/>
										</div>
							    		</td>
							    		<td>
							    			<input type="text" id="txt_xm_${sr.index+1}" a="a" class=" null window input-radius" name="xm" value="${list.xm}">
							    		</td>
							    		<td>
							    			<input type="text" id="txt_sfzh_${sr.index+1}" a="a" class=" null window input-radius" name="sfzh" value="${list.sfzh}">
							    		</td>
							    		<td>
							    			<select id="txt_yhlx_${sr.index+1}" name="yhmc" class="bk input-radius">
							    					<option value="">--请选择--</option>
													<c:forEach items="${yhklist}" var="yhklist">
													<option value="${yhklist.yhmc}" zh="${yhklist.khyhh}" zhbguid="" <c:if test="${yhklist.yhmc == list.yhmc }">selected</c:if>>${yhklist.yhmc}</option>
													</c:forEach>
											</select>
							    		</td>
							    		<td>
							    			<input type="text" id="txt_yhkh_${sr.index+1}" a="a" class=" null window input-radius" name="yhkh" value="${list.yhkh}" >
							    		</td>
							    		<td>
										<select class="form-control" id="txt_bzjb_${sr.index+1}" name="bzbh">
											<option value="">--请选择--</option>
						                	<c:forEach items="${bzdjList}" var="bzdjList">
												<option value="${bzdjList.bzbh}"  je="${bzdjList.BZJE}" <c:if test="${bzdjList.bzbh == list.bzbh }">selected</c:if>>${bzdjList.bzmc}</option>
											</c:forEach>
										</select>
										</td>
										<td>
										<input type="text" id="txt_ffje1_${sr.index+1}" a="a"  class=" input-radius sz number null txt_ffje hs" name="ffje1" value="${list.ffje}" 
												  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
										</td>
							    	</tr>
				    			</c:forEach>
                          </c:when>
                          <c:otherwise>
	                          	<tr id="tr_1">
							    		<td class="btn_td">
				                               <div class="addBtn add"></div>
							    		</td>
							    		<td class="xhh hide">1</td>
							    		<td>
									    <div class="input-group">
										<input type="text" id="txt_xh_1" a="a"  class=" null window input-radius" name="xh" value="">
										<span class="input-group-btn">
							    			<button type="button" id="btn_xsxx" onclick="xsxx(this)" class="btn btn-link btn-custom">选择</button>
						    			</span>
										<input type="hidden" id="xsguid_1" name="xsguid" value=""/>
										</div>
							    		</td>
							    		<td>
							    			<input type="text" id="txt_xm_1" a="a" class=" null window input-radius" name="xm" value="">
							    		</td>
							    		<td>
							    			<input type="text" id="txt_sfzh_1" a="a" class=" null window input-radius" name="sfzh" value="">
							    		</td>
							    		<td>
							    			<select id="txt_yhlx_1" name="yhlx" class="bk input-radius">
													<c:forEach items="${yhklist}" var="yhklist">
													<option value="${yhklist.yhmc}" <c:if test="${yhklist.yhmc == list.yhmc }">selected</c:if>>${yhklist.yhmc}</option>
													</c:forEach>
											</select>
							    		</td>
							    		<td>
							    			<input type="text" id="txt_yhkh_1" a="a" class=" null window input-radius" name="yhkh" value="" >
							    		</td>
							    		<td>
										<select id="txt_bzjb" name="bzbh" class="bk input-radius">
											<option value="">请选择</option>
											<c:forEach var="bzdjList" items="${bzdjList}"> 
		                                   		<option value="${bzdjList.BZBH}"  je="${bzdjList.BZJE}"  <c:if test="${list.bzbh == bzdjList.BZBH}">selected</c:if>>${bzdjList.BZMC}</option>
											</c:forEach>		                
										</select>
										</td>
										<td>
										<input type="text" id="txt_ffje1_1"  class=" input-radius sz number null txt_ffje hs" name="ffje1" value=""
										onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
										</td>
							    	</tr>
                           </c:otherwise>
                    </c:choose>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<script type="text/javascript">
var basePath = "${ctx}/wsbx/ccyw/ccywsq";
var tag = true;
//保存按钮
$("#btn_save").click(function(e){
	var fabh = $("#txt_fabh").val();
	var ffzje = $("#txt_ffje").val();
	var guids = [];	//每一个学生的guid
	var xhs = [];//每一个学生的学号
	var yhmcs = [];//每一个学生的银行名称
	var yhkhs = [];//每一个学生的银行卡号
	var bzbhs = []; //每一个学生的补助标准编号
	var ffjes = []; //每一个学生的发放金额
	var bool = false;
	$("input[id^=xsguid]").each(function(j,item){
		guids.push(item.value);	
	});
	$("input[id^=txt_xh]").each(function(j,item){
		if(item.value == ""){
			bool = true;
			return;
		}else{
			xhs.push(item.value);
		}	
	});
	$("select[id^=txt_yhlx]").each(function(j,item){
		yhmcs.push(item.value);	
	});
	$("input[id^=txt_yhkh]").each(function(j,item){
		yhkhs.push(item.value);	
	});
	$("select[id^=txt_bzjb]").each(function(j,item){
		bzbhs.push(item.value);	
	});
	$("input[id^=txt_ffje1]").each(function(j,item){
		if(item.value == ""){
			bool = true;
			return;
		}else{
			ffjes.push(item.value);
		}	
	});
    console.log(yhmcs);
    console.log($("select[id^=txt_yhlx]").length);
    if(bool){
    	alert("请选择学生和补助学金等级！");
    	return;
    }else{
	doSave(validate,"myform","${ctx}/xsbzxjlr/doSave?fabh="+fabh+"&ffzje="+ffzje+"&xhs="+xhs.join(",")+"&yhmcs="+yhmcs.join(",")+"&yhkhs="+yhkhs.join(",")+"&bzbhs="+bzbhs.join(",")+"&ffjes="+ffjes.join(","),function(val){
		if(val.success){
			tag = true;
			location.href="${ctx}/xsbzxjlr/goListPage";
		}
	});
    }
});	
//保存时的验证
var validate = $('#myform').bootstrapValidator({fields:{
    famc:{validators: {notEmpty: {message: '方案名称不能为空'}}},
    zy:{validators: {notEmpty: {message: '摘要不能为空'}}},
    xmmc:{validators: {notEmpty: {message: '项目编号不能为空'}}}
   	}
   });
//页面展示情况
$(function(){
	var operateType = "${operateType}";
	if(operateType == 'L'){
		$('input').attr("readonly","readonly");
		$("[name='ffsj']").attr("disabled","disabled");
		$('select').attr("disabled","disabled");
		$('td').attr("readonly","readonly");
		$('button').hide();
		$("#btn_back").show();
		$("[name='yhkh']").attr("disabled","disabled");
		$("[name='ffje1']").attr("disabled","disabled");
	}else if(operateType == 'T'){
		$('input').attr("readonly","readonly");
		$("[name='ffsj']").attr("disabled","disabled");
		$('select').attr("disabled","disabled");
		$('td').attr("readonly","readonly");
		$('button').hide();
		$("#btn_back").show();
		$("#btn_save").show();
		$("#btn_xmxx").show();
		$("button[id^=btn_xsxx]").show();
		$("[name='zy']").removeAttr("readonly");
		$("[name='xmmc']").removeAttr("readonly");
		$("[name='jjkm']").removeAttr("disabled");
		$("[name='ffje']").removeAttr("readonly");
	}else if(operateType == 'U'){
		var num = $("#mydatatables").find("tr");
		num.each(function(){
			$(this).find("td").eq(0).find("div").removeClass("addBtn");
			$(this).find("td").eq(0).find("div").addClass("deleteBtn");
	   		});
		$("#mydatatables").find("tr").last().find("td").eq(0).find("div").removeClass("deleteBtn");
		$("#mydatatables").find("tr").last().find("td").eq(0).find("div").addClass("addBtn");
		$("[name='yhkh']").attr("disabled","disabled");
		$("[name='ffje1']").attr("disabled","disabled");
		$("[name='yhmc']").attr("disabled","disabled");
	}else if(operateType == 'C'){
		$("[name='yhkh']").attr("disabled","disabled");
		$("[name='ffje1']").attr("disabled","disabled");
	}
	//返回按钮
	$("#btn_back").click(function(e){
			location.href="${ctx}/xsbzxjlr/goListPage";
	});
	//联想输入
	$("#txt_bxr").bindChange("${ctx}/suggest/getXx","R");//报销人
	$("#txt_bmmc").bindChange("${ctx}/suggest/getXx","D");//部门
	//弹窗
	$("#btn_xmxx").click(function(){
		select_commonWin("${ctx}/webView/bzjgl/xsbzxjlr/xmxx_select_list.jsp?controlId=txt_xmmc&xmbh=xmbh","项目信息","920","630");
    });
});
//明细js操作
$(function(){
	//新增按钮
	$(document).on("click","[class*=addBtn]",function(){
		var num = $("#mydatatables").find("tr").last().find("td").eq(1).text()*1+1*1;
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		$parentTr.find(":input").removeClass("border");
		$parentTr.attr("id","tr_"+num);
		$parentTr.find(".xhh").text(num);
		$parentTr.find("[name='xh']").attr("id","txt_xh_"+num);
		$parentTr.find("[name='xm']").attr("id","txt_xm_"+num);
		$parentTr.find("[name='sfzh']").attr("id","txt_sfzh_"+num);
		$parentTr.find("[name='yhlx']").attr("id","txt_yhlx_"+num);
		$parentTr.find("[name='yhkh']").attr("id","txt_yhkh_"+num);
		$parentTr.find("[name='bzbh']").attr("id","txt_bzjb_"+num);
		$parentTr.find("[name='ffje']").attr("id","txt_ffje1_"+num);
		$parentTr.find("[name='xsguid']").attr("id","xsguid_"+num);
		num++;
		$(this).parents("tr").after($parentTr);
	});
	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		var je = $(this).parents("tr").find("[name='ffje1']").attr("value");
		var ffzje = $("#txt_ffje").val();
		hjje = parseFloat(ffzje) - parseFloat(je);
		$("#txt_ffje").val(hjje.toFixed(2));
		$(this).parents("tr").remove();
	});
});
//选择学生
function xsxx(bz){
	$(bz).parents("tr").find("[name='yhmc']").removeAttr("disabled");
	var num = $(bz).parents("tr").find(".xhh").text();
	var controlId = "txt_xh_"+num;
	var controlId2 = "txt_xm_"+num;
	var controlId3 = "txt_sfzh_"+num;
	var guid = "xsguid_"+num;
	select_commonWin("${ctx}/webView/fzgn/jcsz/xsxxsz/xsxx_select_list.jsp?controlId="+controlId+"&controlId2="+controlId2+"&controlId3="+controlId3+"&guid="+guid,"学生信息","900","620");
}
//查询银行卡
function cxyhk(guid,cdid){
	var parentTr=$("#"+cdid).parents("tr");
	console.log(guid);
	$.ajax({
		url:"${ctx}/xsbzxjlr/xsyhxx",
		data:"dqdlrguid="+guid,
		dataType:"json",
		async:false,
		success:function(datas){
			parentTr.find("[id^=txt_yhlx]").empty();
			if(datas){
				parentTr.find("[id^=txt_yhlx]").append("<option value=''>请选择</option>");
				$.each(datas,function(i,v){
					var id = v.GUID;
					var mc = v.YHMC;
					var zh = v.KHYHH;
					parentTr.find("[id^=txt_yhlx]").append("<option value="+mc+" zh="+zh+" zhbguid="+id+">"+mc+"</option>");
				});
			}
		}
	});  
}
//获得对应银行的银行卡号
$(document).on("change","[id^=txt_yhlx]",function(){
	var zh = $(this).find("option:selected").attr("zh");
	console.log("zh"+zh);
	$(this).parents("tr").find("[id^=txt_yhkh]").val(zh);
});
//获得对应补助级别的补助金额
$(document).on("change","[id^=txt_bzjb]",function(){
	var je = $(this).find("option:selected").attr("je");
	console.log("je"+je);
	$(this).parents("tr").find("[id^=txt_ffje1]").val(je);
});
//根据补助类型填充金额
$(document).on("change","[id^=txt_bzjb]",function(){
	var je = $(this).find("option:selected").attr("je");
	console.log("je"+je);
	$(this).parents("tr").find("[id^=txt_ffje1]").val(je);
	var je = 0;
	var hjje = 0;
	$.each($("[id^=txt_ffje1]"),function(){
		var $this = $(this);
		je = $this.val();
		if(isNaN(je)||je==""){
			je = 0;
		}
		hjje = parseFloat(hjje) + parseFloat(je);
	});
	if(hjje<0){
		hjje = 0.00;
	}
	$("#txt_ffje").val(hjje.toFixed(2));
});
$(function(){
	addbutton();
	function addbutton(){
		var a = $(".tbody").find("tr").length;
		var aaa="${xmlist}";
		if(aaa.length>0){
			$(".add").removeClass("addBtn");
			$(".add").addClass("deleteBtn");		
			$(".tbody tr:last").find(".add").removeClass("deleteBtn");
			$(".tbody tr:last").find(".add").addClass("addBtn");
		}
	}
	
});
SmscLoad("${pageContext.request.contextPath}",{"id":"${zbid}","djlx":"000000","fold":"zcxx","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
</script>

</html>