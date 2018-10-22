<%@page import="com.googosoft.util.Validate"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产建账详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<link href="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="ysdh" value="${yshd.YSDH}">
	<input type="hidden" name="flgbm" value="${yshd.FLGBM}">
	<input type="hidden" name="gbmid" value="${yshd.GBMID}">
	<input type="hidden" name="operate" value="${operate}">
	<input type="hidden" name="mkbh" value="${mkbh}">
	<div class="row" style="margin-left: 0px;margin-right: 0px;">
		<div class="col-md-12 tabs" style="padding-left: 0px;padding-right: 0px;">
			<ul id="tabTop" class="nav nav-tabs" role="tablist">
				<li role="presentation"><a id="tab_card">卡片信息</a></li>
				<li role="presentation" class="active"><a id="tab_ysd">验收单</a></li>
				<li role="presentation"><a id="tab_bd">变动</a></li>
				<li role="presentation"><a id="tab_cz">处置</a></li>
				<li role="presentation"><a id="tab_fj">附件</a></li>
				<li role="presentation"><a id="tab_zclsb">资产流水表</a></li>
			</ul>
		</div>
	</div>
		<c:if test="${param.ysdh != ''}">
		<div class="box-panel" style="width: 97%;margin-left: 1px;">
			<div class="box-header clearfix" style="margin-top: 10px;margin-left: 10px">
				<div class="row">
					<div class="col-md-5">
	            		<span>验收单号：</span><span style="font-weight: bold;font-size: 13px;"><c:out value="${yshd.YSDH}"></c:out></span> 
	            	</div>
	            	<div class="col-md-2">
	           			金额单位：（元）
	           		</div>
		           	<div class="col-md-2">
		           		面积单位：（平方米）
		           	</div>
		           	<div class="col-md-3">
		           		<span style="color: red;">财政分类：</span> <span id="sp_czfl"><c:out value="${yshd.CZFLMC}"></c:out></span>
		           	</div>
          		</div>
      		</div>
      		<hr class="hr-normal">
			<!-- 验收单信息详细信息开始 -->
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>资产名称</span>
							<input type="text" id="txt_yqmc" class="form-control input-radius" name="yqmc"  value="${yshd.YQMC}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>分类代码</span>
							<input type="text" id="txt_flh" class="form-control input-radius" name="flh" value="${yshd.FLH}" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<!-- 不带必填项的label元素 -->
							<span class="input-group-addon"><span class="required">*</span>分类名称</span>
							<input type="text" id="txt_flmc" class="form-control input-radius" name="yqbh" value="${yshd.YQBH}" readonly="readonly"/>
						</div>
					</div>
				</div>
				<jsp:include page="../${url}.jsp"></jsp:include>
				<div class="row" style="height: 40px;"></div>
			</div>
			</div>
			</c:if>
	
	<c:if test="${param.ysdh != ''}">
	<div class='page-bottom clearfix'>
		 <div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save" onclick="next()">
				保存
			</button>
			<button type="button" class="btn btn-default" id="btn_back">
				退回
			</button>
         </div>
   </div>
   </c:if>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
function next(){
	
}
$(function(){
	//联想输入提示
	$("#txt_xk").bindChange("${pageContext.request.contextPath}/suggest/getXx","X");
	$("#txt_gbm").bindChange("${pageContext.request.contextPath}/suggest/getXx","G");
	$("#txt_cgr").bindChange("${pageContext.request.contextPath}/suggest/getXx","CR");
	$("[id*=txt_sybm]").bindChange("${pageContext.request.contextPath}/suggest/getXx","SD");
	$("[id*=txt_syr]").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
	$("[id*=txt_bzxx]").bindChange("${pageContext.request.contextPath}/suggest/getXx","P");
	//yyyy
	$(".year").datetimepicker({
		startView: 'decade',
		minView: 'decade',
		format: 'yyyy',
		autoclose: true
	});
	//折旧状态为不提折旧，折旧方法和净残值率只读
	var zjzt = $("#drp_zjjt").val();
	if(zjzt=='01'){
		$("#txt_jczl").attr("readonly","true");
		$("#drp_zjff").attr("disabled","true");
	}
	$("#drp_zjjt").click(function(){
		var zjzt = $("#drp_zjjt").val();
		if(zjzt=='01'){
			$("#txt_jczl").attr("readonly","true");
			$("#drp_zjff").attr("disabled","true");
			$("#drp_zjff").val("");
			$("#txt_jczl").val("0.00");
		}else{
			$("#txt_jczl").removeAttr("readonly");
			$("#drp_zjff").removeAttr("disabled");
		}
	});

	//附件信息操作结束
	//弹窗开始
	//申购单位
	$("#btn_shgdw").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/dwxx/window.jsp?controlId=txt_shgdw","单位信息","920","630");
    });
	//使用单位
	$("[class*=btn_sybm]").click(function(){
		var id = $(this).parent("span").prev().attr("id");
		select_commonWin("${pageContext.request.contextPath}/zcfltree/goZjbWindow?controlId="+id,"单位信息","920","630");
    });
	//使用人
	$("[class*=btn_syr]").click(function(){
		var id = $(this).parent("span").prev().attr("id");
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId="+id,"人员信息","920","630");
    });
	//存放地点
	$("[class*=btn_bzxx]").click(function(){
		var id = $(this).parent("span").prev().attr("id");
		select_commonWin("${pageContext.request.contextPath}/webView/window/ddxx/window.jsp?controlId="+id,"地点信息","920","630");
    });
	//经手人
	$("#btn_jsr").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_jsr","人员信息","920","630");
    });
	//采购人
	$("#btn_cgr").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_cgr&flag=All","人员信息","920","630");
    });
	//学科
	$("#btn_xk").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/xkxx/window.jsp?controlId=txt_xk","学科信息","920","630");
    });
	//国别
	$("#btn_gbm").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/gbxx/window.jsp?controlId=txt_gbm","国别信息","920","630");
    });
	//保存按钮
	$("#btn_save").click(function(){	
		if(saveCheck()){
			doSave(validate,"myform","${pageContext.request.contextPath}/xxtz/doSaveyshd",function(val){
				
			},function(val){
				
			});
		}
	}); 
	
	//退回按钮
	$("#btn_back").click(function(){	
		 $.ajax({
				url:"${pageContext.request.contextPath}/xxtz/yshdBack",
				type:"POST",
				data:"ysdh=${param.ysdh}&jzrlx=${yshd.JZRLX}",
				success:function(result){
					var data = JSON.getJson(result);
					if(data.success){
						alert(data.msg);
						return;
					}else{
						alert(data.msg);
						return;
					}
				 },
				 error:function(){
					 alert(getPubErrorMsg());
				 }
			  }); 
	}); 

	var validate = $('#myform').bootstrapValidator({
        fields: {
        	<%=Validate.isNullToDefault(request.getAttribute("validate"), "")%> 
        <%=Validate.isNullToDefault(request.getAttribute("json"), "")%> 
        }
    });
	//保存时验证填写信息项
	function saveCheck(){
		var url = "${url}";
		var flbz = url.substring(url.indexOf("/")+1,url.indexOf("_"));
		if("fw" == flbz){//房屋
			var fw_fzrq = $("[name='fw_fzrq']").val();//发证日期
			var yshrq = $("[name='yshrq']").val();//验收日期
			if(fw_fzrq != "" && yshrq != "" && fw_fzrq > yshrq){
				alert("验收日期应大于等于发证日期");
				return false;
			}else{
				var jzmj = $("[name='fjzj']").val();//建筑面积
				var symj = $("[name='symj']").val();//使用面积
				if(jzmj != "" && symj != "" && (parseFloat(jzmj)<parseFloat(symj))){
					alert("使用面积必须小于等于建筑面积！");
					return false;
			   	}else{
			   		return true;
			   	}
			}
		}else if("td" == flbz){//土地
			var td_fzrq = $("[name='td_fzrq']").val();//发证日期
			var yshrq = $("[name='yshrq']").val();//验收日期
			if(td_fzrq != "" && yshrq != "" && td_fzrq > yshrq){
				alert("验收日期应大于等于发证日期");
				return false;
			}else{
				var savezzj = $("[name='dj']").val();//总价
				savezzj = parseFloat(savezzj);
				if(isNaN(savezzj)){
				 	savezzj = 0;
				}
				var savezyjz = $("[name='zyjz']").val();//自用价值
				savezyjz = parseFloat(savezyjz);
				if(isNaN(savezyjz)){
					savezyjz = 0;
				}
				var savecjjz = $("[name='cjjz']").val();//出借价值
				savecjjz = parseFloat(savecjjz);
				if(isNaN(savecjjz)){
					savecjjz = 0;
				}
				var saveczjz = $("[name='czjz']").val();//出租价值
				saveczjz = parseFloat(saveczjz);
				if(isNaN(saveczjz)){
					saveczjz = 0;
				}
				var savejyjz = $("[name='jyjz']").val();//对外投资价值
				savejyjz = parseFloat(savejyjz);
				if(isNaN(savejyjz)){
					savejyjz = 0;
				}
				var saveqtjz = $("[name='qtjz']").val();//其他价值
				saveqtjz = parseFloat(saveqtjz);
				if(isNaN(saveqtjz)){
					saveqtjz = 0;
				}
			 	if(savezzj != (savezyjz + savecjjz + saveczjz + savejyjz + saveqtjz)){
					alert("总价必须等于自用价值+出借价值+出租价值+对外投资价值+其他价值");
					return false;
			 	}else{
			 		var syqmj = $("[name='zmmj']").val();//使用权面积
					syqmj = parseFloat(syqmj);
					if(isNaN(syqmj)){
						syqmj = 0;
					}
					var savezymj = $("[name='td_zymj']").val();//自用面积
					savezymj = parseFloat(savezymj);
					if(isNaN(savezymj)){
						savezymj = 0;
					}
					var savecjmj = $("[name='td_cjmj']").val();//出借面积
					savecjmj = parseFloat(savecjmj);
					if(isNaN(savecjmj)){
						savecjmj = 0;
					}
					var saveczmj = $("[name='td_czmj']").val();//出租面积
					saveczmj = parseFloat(saveczmj);
					if(isNaN(saveczmj)){
						saveczmj = 0;
					}
					var savejymj = $("[name='td_jymj']").val();//对外投资面积
					savejymj = parseFloat(savejymj);
					if(isNaN(savejymj)){
						savejymj = 0;
					}
					var saveqtmj = $("[name='td_qtmj']").val();//其他面积
					saveqtmj = parseFloat(saveqtmj);
					if(isNaN(saveqtmj)){
						saveqtmj = 0;
					}
					if(syqmj != (savezymj + savecjmj + saveczmj + savejymj + saveqtmj)){
						alert("使用权面积必须等于自用面积+出借面积+出租面积+对外投资面积+其他面积");
						return false;
					}else{
						return true;
					}
			 	}
			}
		}else if("wxzc" == flbz){//无形资产
			var trsyrq = $("[name='qsrq']").val();
			var date = new Date();
			var month = (date.getMonth()+1).length==2?(date.getMonth()+1):("0"+(date.getMonth()+1));
			var d = (date.getDate()).length==2?date.getDate():("0"+date.getDate());
			var day = date.getFullYear()+"-"+month+"-"+d;
			if(trsyrq != "" && trsyrq > day){
				alert("投入使用日期不能晚于系统当前日期");
				return false;
			}else {
				return true;
			}
		}else if("tysb" == flbz || "cl" == flbz || "jj" == flbz){//通用设备、车辆、家具、被服装具
			var ccrq = $("[name='ccrq']").val();//出厂日期
			var yshrq = $("[name='yshrq']").val();//验收日期
			if(ccrq != "" && yshrq != "" && ccrq>yshrq){
				alert("验收日期应大于等于出厂日期");
				return false;
			}else{
				return true;
			}
		}else {
			return true;
		}
	}
	
	//卡片
   	$("#tab_card").click(function(){
   		parent.document.getElementById("div_bh").innerHTML="请输入资产编号";
   		parent.document.getElementById("txt_lx").value="0";
   		window.location.href ="${pageContext.request.contextPath}/xxtz/goCardPage?yqbh=${param.yqbh}";
   	});
    //变动
   	$("#tab_bd").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/changelist.jsp?yqbh=${param.yqbh}&ysdh=${param.ysdh}";
   	});
    //处置
   	$("#tab_cz").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/assetdolist.jsp?yqbh=${param.yqbh}&ysdh=${param.ysdh}";
   	});
    //附件
   	$("#tab_fj").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/assetfjlist.jsp?yqbh=${param.yqbh}&ysdh=${param.ysdh}";
   	});
    //资产流水表
   	$("#tab_zclsb").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/zclslist.jsp?yqbh=${param.yqbh}&ysdh=${param.ysdh}";
   	});
});
</script>
</body>
</html>