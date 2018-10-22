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
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid" id="guid" value="${guid}">
	<input type="hidden" name="ffzje" id="ffzje" value="">
	<input type="hidden" name="mbbz" id="mbbz" value="${map.mbbz}">
	<input type="hidden" name="ffry" id="ffry" value="${rylx}">
	<div class="box">
		<div class="box-panel">		
			<div class="box-header1 clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>学生薪资申报信息
            	</div>
            	<div class="pull-right">
            	<c:if test="${operateType == 'S'}">
			  		<button type="button" class="btn btn-default" id="btn_tg">通过</button>
			  		<button type="button" class="btn btn-default" id="btn_th">退回</button>
			  	</c:if>
			  		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			  		<button type="button" class="btn btn-default" id="btn_savemb">保存模版</button>
			  		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">发放批次</span>
							<input type="text" id="txt_ffpc" readonly class="form-control input-radius" name="ffpc" value="${map.ffpc}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">流&ensp;水&ensp;号</span>
                            <input type="text" id="txt_fflsh" readonly class="form-control input-radius window" name="fflsh" value="${map.fflsh}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>发放方式</span>
							<select class="form-control" id="txt_fffs" name="fffs">
			                	<option value="">--请选择--</option>
			                	<c:forEach items="${zffs}" var="zffs">
									<option value="${zffs.dm}" <c:if test="${zffs.dm == map.fffs}">selected</c:if>>${zffs.mc}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;"><span class="required">*</span>发放时间</span>
							<input type="text" id="txt_ffsj" name="ffsj" class="form-control input-radius date window"  
							style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入发放时间" value="${map.lrsj}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>发放方案</span>
							<input type="text" id="txt_fffa" class="form-control input-radiu" name="fffa" value="${map.fffa }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>摘要</span>
							<input type="text" id="txt_zy" class="form-control input-radiu" name="zy" value="${map.zy}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>财务项目</span>
							<input type="text" id="txt_xmmc" class="form-control input-radiu" name="xmmc" value="${map.xmmc}"/>
							<input type="hidden" id="xmbh" class="form-control input-radiu" name="xmbh" value="${map.xmbh}"/>
							<span class="input-group-btn"><button type="button" id="btn_xmxx" class="btn btn-link">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>发放项目</span>
							<select class="form-control" id="txt_jjkm" name="jjkm">
			                	<option value="">--请选择--</option>
			                	<c:forEach items="${jjkm}" var="jjkm">
									<option value="${jjkm.kmbh}" <c:if test="${jjkm.kmbh == map.jjkm}">selected</c:if>>${jjkm.kmmc}</option>
								</c:forEach>
							</select>
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
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>薪资申报 明细信息
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
				            <th style="width:300px;" id="zfje"><span class="required" style="color:red">*</span>姓名</th>
				            <th style="width:200px;" id="zfje"><span class="required" style="color:red">*</span>身份证号</th>
				            <th style="width:200px;" id="zfje"><span class="required" style="color:red">*</span>银行卡号</th>
				            <th style="width:200px;" id="zfje"><span class="required" style="color:red">*</span>发放金额</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
						<c:choose>
                          <c:when test="${not empty srmx}">
  								<c:forEach var="srmx" items="${srmx}" varStatus="sr"> 
							    	<tr id="tr_${sr.index+1}">
							    		<td class="btn_td">
				                               <div class="addBtn add"></div>
							    		</td>
							    		<td class="xhh hide">${sr.index+1}</td>
							    		<td>
									    <div class="input-group">
										<input type="text" id="txt_xh_${sr.index+1}" a="a"  class=" null window input-radius" name="xh" value="${srmx.xh}">
										<span class="input-group-btn">
							    			<button type="button" id="btn_xsxx" onclick="xsxx(this)" class="btn btn-link btn-custom">选择</button>
						    			</span>
										<input type="hidden" id="xsguid_${sr.index+1}" name="xsguid" value="${srmx.rybh}"/>
										</div>
							    		</td>
							    		<td>
							    			<input type="text" id="txt_xm_${sr.index+1}" a="a" class=" null window input-radius" name="xm" value="${srmx.xm}">
							    		</td>
							    		<td>
							    			<input type="text" id="txt_sfzh_${sr.index+1}" a="a" class=" null window input-radius" name="sfzh" value="${srmx.sfzh}">
							    		</td>
							    		<td>
							    			<select id="txt_yhkh_${sr.index+1}" name="yhkh" class="bk input-radius">
							    					<option value="">--请选择--</option>
													<c:forEach items="${yhklist}" var="yhklist">
													<option value="${yhklist.khyhh}" <c:if test="${yhklist.khyhh == srmx.yhkh }">selected</c:if>>${yhklist.yhxx}</option>
													</c:forEach>
											</select>
							    		</td>
										<td>
										<input type="text" id="txt_ffje_${sr.index+1}" a="a" onblur="jfje()" class=" input-radius sz number null txt_ffje hs" name="ffje" value="${srmx.ffje}"
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
							    			<select id="txt_yhkh_1" name="yhkh" class="bk input-radius">
													<c:forEach items="${yhklist}" var="dlryhklist">
													<option value="${dlryhklist.khyh}" <c:if test="${yhklist.khyhzh == dsList.dfzh }">selected</c:if>>${yhklist.klx}</option>
													</c:forEach>
											</select>
							    		</td>
										<td>
										<input type="text" id="txt_ffje_1"  onblur="jfje()" class=" input-radius sz number null txt_ffje hs" name="ffje" value=""
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
$(function(){
	var operateType = "${operateType}";
	if(operateType == 'L'||operateType == 'S'){
		$('input').attr("readonly","readonly");
		$("[name='ffsj']").attr("disabled","disabled");
		$('select').attr("disabled","disabled");
		$('td').attr("readonly","readonly");
		$('button').hide();
		$("#btn_back").show();
		$("#btn_tg").show();
		$("#btn_th").show();
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
	}
	$("#btn_tg").click(function(){
		var guid = "${guid}";
		var procinstid = "${procinstid}";
		var flag = false;
		select_commonWin("${ctx}/srgl/xzsbsh/check?type=first&guid="+guid+"&procinstid="+procinstid+"&flag="+flag,"通过页面","500","300");
	});
	$("#btn_th").click(function(){
		var guid = "${guid}";
		var procinstid = "${procinstid}";
		select_commonWin("${ctx}/srgl/xzsbsh/check?type=second&guid="+guid+"&procinstid="+procinstid,"通过页面","500","300");
	});
	//图片信息开始
	var validate = $('#myform').bootstrapValidator({fields:{
		ffsj:{validators: {notEmpty: {message: '发放时间不能为空'}}},
	    fffa:{validators: {notEmpty: {message: '发放方案能为空'}}},
	    zy:{validators: {notEmpty: {message: '摘要不能为空'}}},
	    xmmc:{validators: {notEmpty: {message: '财务科目不能为空'}}},
	    fffs:{validators: {notEmpty: {message: '发放方式不能为空'}}},
	    jjkm:{validators: {notEmpty: {message: '发放项目不能为空'}}},
	    bxsy:{validators: {notEmpty: {message: '报销事由不能为空'}}},
       	}
	   });
	//返回按钮
	$("#btn_back").click(function(e){
		var ymbz = "${ymbz}";
		    if(ymbz == 'sh'){
		    	location.href="${ctx}/srgl/xzsbsh/getPageList";
		    }else if(ymbz == 'cx'){
		    	location.href="${ctx}/xzsbcx/goXzsbcxPageList";
		    }else if(ymbz == 'hs'){
				location.href="${ctx}/srgl/xzsbsh/getHsPageList";
		    }else{
				location.href="${ctx}/xzsblr/goPageList";
		    }
	});
	//保存按钮
	$("#btn_save").click(function(e){
		var rybhs = [];	//每一个学生的guid
		var ffjes = []; //每一个学生的发放金额
		var yhkhs = []; //每一个学生的银行卡号
		var bool = false;
		$("input[id^=xsguid]").each(function(j,item){
			rybhs.push(item.value);	
		});
		$("input[id^=txt_ffje]").each(function(j,item){
			if(item.value == ""){
				bool = true;
				return;
			}else{
				ffjes.push(item.value);
			}	
		});
		$("select[id^=txt_yhkh]").each(function(j,item){
			if(item.value == ""){
				bool = true;
				return;
			}else{
				yhkhs.push(item.value);
			}	
		});
	    if(bool){
	    	alert("尚有必填项未进行填写！");
	    	return;
	    }else{
		doSave(validate,"myform","${ctx}/xzsblr/doSave?yhkhs="+yhkhs.join(",")+"&rybhs="+rybhs.join(",")+"&ffjes="+ffjes.join(","),function(val){
			if(val.success){
				tag = true;
				location.href="${ctx}/xzsblr/goPageList";
			}
		});
	    }
	});	
	//保存模版按钮
	$("#btn_savemb").click(function(e){
		confirm("确认将本次录入信息保存为模版吗？",{title:"提示"},function(){
			var guid = $("#guid").val();
	   		$.ajax({  
 		       	type:"post",
 		       	data:"guid="+guid, 
 		        url:"${pageContext.request.contextPath}/xzsblr/doSavemb",
 		       	dataType:"json",
 		       	success:function(val){
 		       	    alert(val.msg);
 		       	},
 		       	error:function(){
 		       		alert("数据请求错误！");
 		       	}
 	        });
	   		});
	});	
	
	//联想输入
	$("#txt_bxr").bindChange("${ctx}/suggest/getXx","R");//报销人
	$("#txt_bmmc").bindChange("${ctx}/suggest/getXx","D");//部门
	//弹窗
	$("#btn_xmxx").click(function(){
		select_commonWin("${ctx}/webView/ysgl/xmsz/xmxx/xmxx_listWindow.jsp?controlId=txt_xmmc&xmbh=xmbh","项目信息","920","630");
    });
	jfje();
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
		$parentTr.find("[name='yhkh']").attr("id","txt_yhkh_"+num);
		$parentTr.find("[name='ffje']").attr("id","txt_ffje_"+num);
		$parentTr.find("[name='xsguid']").attr("id","xsguid_"+num);
		num++;
		$(this).parents("tr").after($parentTr);
	});
	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();
		
	});
});
//选择学生
function xsxx(bz){
	$(bz).parents("tr").find("[name='yhkh']").removeAttr("disabled");
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
		url:"${ctx}/xzsblr/xsyhxx",
		data:"dqdlrguid="+guid,
		dataType:"json",
		async:false,
		success:function(datas){
			parentTr.find("[id^=txt_yhkh]").empty();
			if(datas){
				parentTr.find("[id^=txt_yhkh]").append("<option value=''>请选择...</option>");
				$.each(datas,function(i,v){
					var id = v.GUID;
					var mc = v.YHMC;
					var zh = v.KHYHH;
					parentTr.find("[id^=txt_yhkh]").append("<option value="+zh+" >"+"("+zh+")"+mc+""+"</option>");
				});
			}
		}
	});  
	
}
//计算缴费金额
function jfje(){
	var $jfzj = 0;
	var jfzj = parseFloat($jfzj);
	$("input[id^=txt_ffje]").each(function(j,item){
		if(item.value == ""||item.value == null){
			item.value == 0;
			var jfje =0;
		}else{
			jfje = parseInt(item.value);
		}
		jfzj = jfzj+jfje;
		
	});
	$("#ffzje").val(jfzj);
};
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
		}else{
			
		}
	}
	
});
SmscLoad("${pageContext.request.contextPath}",{"id":"${zbid}","djlx":"000000","fold":"zcxx","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
</script>

</html>