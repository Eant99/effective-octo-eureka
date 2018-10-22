<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>增加科目信息</title>
<%@include file="/static/include/public-manager-css.inc"%> 

<style type="text/css">
	.tool-fix{
		display:none;
	}
	.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    padding-left: 6px;
}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid" value="${guid}" />
	<input type="hidden" name="dm" value="${dm}" />
	<input type="hidden" name="jb" value="${jb}" />
	<input type="hidden" name="flag" value="${flag}" />
		<input type="hidden" name="guid1" value="${guid1}" />
	    
		<input type="hidden" name="sfmjsj" value="${sfmjsj}" />
	
		<input type="hidden" name="sfmjxj" value="${sfmjxj}" />
	
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	<c:choose>
            		<c:when test="${operateType == 'L'}">查看科目信息</c:when>
            		<c:otherwise>编辑科目信息</c:otherwise>
            	</c:choose>
			</span>
		</div>
        <div class="pull-right">
        		
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
			    
        </div>
    </div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		科目信息
            	</div>
            	<!-- 右侧折叠按钮 -->
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目编号</span>
							<input type="hidden" name="guid1" value="${guid1}" />
							<input type="text" id="kmsz_kmbh" <c:if test="${sfkjzd=='1'}">readonly</c:if> class="form-control input-radius window" name="kmbh" value="${dwb.kmbh}" readonly>
						</div>
					</div>					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" >科目名称</span>
							<input type="text" id="kmsz_kmmc" <c:if test="${sfkjzd=='1'}">readonly</c:if> class="form-control input-radius" name="kmmc" value="${dwb.kmmc}" readonly/>
						</div>
					</div>
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">核算类别</span>
							<select id="txt_xmfl" class="form-control input-radius select2" name="hslb" disabled>
                            	<option value="">未选择</option>
                                <c:forEach var="item" items="${hslbList}">
                           			<option value="${item.DM}" <c:if test="${item.DM == dwb.hslb }">selected</c:if>>${item.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div> 
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目属性</span>
							<%-- <input type="text" id="kmsz_zkmdmsx" class="form-control input-radius" name="zkmdmsx" value="${kmsz.zkmdmsx}"/> --%>
							<select id="txt_kmsx" class="form-control input-radius select2" name="kmsx" disabled<c:if test="${sfkjzd=='1'}"></c:if> >
                            	<option value="">未选择</option>
                                <c:forEach var="item" items="${kmsxList}" >
                           			<option value="${item.DM}"  <c:if test="${item.DM == dwb.kmsx }">selected</c:if>>${item.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">余额方向</span>
							<%-- <input type="text" id="kmsz_yefx" class="form-control input-radius text-right int" name="yefx"  value="${kmsz.yefx}"> --%>
							    <div class="radiodiv"> 
                                <div class="pull-left" style="padding-left: 10px;">
                                <c:if test="${operateType == 'C' }" >
									<input type="radio" id="aa" class="aa " name="yefx" value="1" checked="checked" > 贷方&ensp;
									<input type="radio" id="aa"  class="bb "  name="yefx" value="0" > 借方
								</c:if>
								<c:if test="${ operateType == 'U' }">
									<input type="radio" id="aa" disabled<c:if test="${ sfkjzd == '1' }"></c:if> class="aa cc" name="yefx" value="1" <c:if test="${1 == dwb.YEFX }">checked="checked"</c:if>> 贷方&ensp;
									<input type="radio" id="aa" disabled<c:if test="${ sfkjzd == '1' }"></c:if> class="bb cc"  name="yefx" value="0" <c:if test="${0 == dwb.YEFX }">checked="checked"</c:if>> 借方
								</c:if>
							</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目级次</span>
						<%-- 	<c:if test="${flag == '0' }">
								<input type="text" id="kmsz_kmjc" class="form-control input-radius window" name="kmjc" readonly="readonly" value="${dm}" >
							</c:if>
							<c:if test="${flag == '1' }">
								<input type="text" id="kmsz_kmjc" class="form-control input-radius window" name="kmjc" readonly="readonly" value="${jb}" >
							</c:if>
							<c:if test="${flag == '2' }">
								<input type="text" id="kmsz_kmjc" class="form-control input-radius window" name="kmjc" readonly="readonly" value="${dwb.kmjc}" >
							</c:if> --%>
							<input type="text" id="kmsz_kmjc" class="form-control input-radius window" name="kmjc" readonly="readonly" value="${kmjc}" >
							
						</div>
					 </div>
					</div>
					
					<div class="row">
						<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">助&ensp;记&ensp;符</span>
							<c:if test="${operateType == 'C' }">
<!-- 								<input type="text" id="kmsz_zjf" maxlength="2" class="form-control input-radius window" name="zjf"  >
 -->						        <input type="text" id="kmsz_zjf"  class="form-control input-radius window" name="zjf" readonly >
 	
                           </c:if>
							
							<c:if test="${ operateType == 'U' }">
								<input type="text" id="kmsz_zjf"  class="form-control input-radius window" name="zjf"  value="${dwb.zjf}" readonly>
								
<%-- 								<input type="text" id="kmsz_zjf" maxlength="2" class="form-control input-radius window" name="zjf"  value="${dwb.zjf}"> --%>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
							<c:if test="${operateType == 'C' }">
								<input type="text" id="kmsz_zjf" class="form-control input-radius window" name="bz"  >
							</c:if>
							<c:if test="${operateType == 'U' }">
								<input type="text" id="kmsz_zjf" class="form-control input-radius window" name="bz"  value="${dwb.BZ}" readonly>
							</c:if>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-zichanchuzhi01"></i>
            		辅助信息
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
							<span class="input-group-addon">是否启动</span>
							<div class="radiodiv">
                                <div class="pull-left" style="padding-left: 10px;">
									<input type="radio" name="qyf" value="1" class="bc"<c:if test="${1 == dwb.QYF }">checked="checked" </c:if>> 是&ensp;
									<input type="radio" name="qyf" value="0" class="bc"<c:if test="${0 == dwb.QYF }">checked="checked" </c:if>> 否
								</div>
                           </div>
						</div>
					</div>
					<div class="col-md-4">
							<div class="input-group">
							<span class="input-group-addon">是否网银行</span>
							<%-- <input type="text" id="kmsz_dw" class="form-control input-radius text-right int" name="dw"  value="${kmsz.dw}"> --%>
							<div class="radiodiv">
                                <div class="pull-left" style="padding-left: 10px;">
                                <c:if test="${operateType == 'C' }">
									<input type="radio" name="sfwyh" value="1" > 是&ensp;
									<input type="radio" name="sfwyh" value="0"  checked="checked"> 否
								</c:if>
								<c:if test="${ operateType == 'U' }">
									<input type="radio" name="sfwyh" value="1" class="bc"<c:if test="${1 == dwb.SFWYH }">checked="checked" </c:if>> 是&ensp;
									<input type="radio" name="sfwyh" value="0" class="bc"<c:if test="${0 == dwb.SFWYH }">checked="checked" </c:if>> 否
								</c:if>
							</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否经济分类科目</span>
							<%-- <input type="text" id="kmsz_sfjjflkm" class="form-control input-radius text-right int" name="sfjjflkm"  value="${kmsz.sfjjflkm}"> --%>
						     	<div class="radiodiv">
                                <div class="pull-left" style="padding-left: 10px;">
                                <c:if test="${operateType == 'C' }">
									<input type="radio" name="sfjjflkm" value="1" > 是&ensp;
									<input type="radio" name="sfjjflkm" value="0" checked="checked"> 否
								</c:if>
								<c:if test="${ operateType == 'U' }">
									<input type="radio" name="sfjjflkm" value="1" class="bc"<c:if test="${1 == dwb.SFJJFLKM }">checked="checked" </c:if> > 是&ensp;
									<input type="radio" name="sfjjflkm" value="0" class="bc"<c:if test="${0 == dwb.SFJJFLKM }">checked="checked" </c:if>> 否
								</c:if>
							</div>
						</div>
						</div>
					</div>
			   </div>
				  <div class=row>
					
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否功能分类科目</span>
						   <div class="radiodiv">
                               	<div class="pull-left" style="padding-left: 10px;">
                               	<c:if test="${operateType == 'C' }">
									<input type="radio" name="sfgnflkm" value="1" > 是&ensp;
									<input type="radio" name="sfgnflkm" value="0" checked="checked"> 否
								</c:if>
								<c:if test="${ operateType == 'U' }">
									<input type="radio" name="sfgnflkm" value="1" class="bc"<c:if test="${1 == dwb.SFGNFLKM }">checked="checked" </c:if>> 是&ensp;
									<input type="radio" name="sfgnflkm" value="0" class="bc"<c:if test="${0 == dwb.SFGNFLKM }">checked="checked" </c:if>> 否
								</c:if>
							</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">部门核算</span>
							<%-- <input type="text" id="kmsz_bm" class="form-control input-radius text-right int" name="bm"  value="${kmsz.bm}"> --%>
							<div class="radiodiv">
                                <div class="pull-left" style="padding-left: 10px;">
                               	<c:if test="${operateType == 'C' }">
									<input type="radio" name="bmhs" value="1" > 是&ensp;
									<input type="radio" name="bmhs" value="0" checked="checked"> 否
								</c:if>
								<c:if test="${ operateType == 'U' }">
									<input type="radio" name="bmhs" value="1" class="bc"<c:if test="${1 == dwb.BMHS }">checked="checked" </c:if>> 是&ensp;
									<input type="radio" name="bmhs" value="0" class="bc"<c:if test="${0 == dwb.BMHS }">checked="checked" </c:if>> 否
								</c:if>								
							</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目核算</span>
							<%-- <input type="text" id="kmsz_xm" class="form-control input-radius text-right int" name="xm"  value="${kmsz.xm}"> --%>
							<div class="radiodiv" disabled>
                                <div class="pull-left" style="padding-left: 10px;" disabled>
                               
								
									<input type="radio" name="xmhs" value="1" class="bc"<c:if test="${1 == dwb.XMHS }">checked="checked" </c:if>> 是&ensp;
									<input type="radio" name="xmhs" value="0" class="bc"<c:if test="${0 == dwb.XMHS }">checked="checked" </c:if>> 否
						
								
							</div>
							</div>
						</div>
					</div>
			      </div>
			       <div class=row>
					
					
					<div class="col-md-4" id="wldwfz" >
						<div class="input-group">
							<span class="input-group-addon">往来单位辅助</span>
						    <div class="radiodiv"> &nbsp; &nbsp;
                               	<c:if test="${operateType == 'C' }">
									<input type="radio" name="wldwfz" value="1" > 是 &nbsp; &nbsp; &nbsp;
									<input type="radio" name="wldwfz" value="0" checked="checked"> 否
								</c:if>
								<c:if test="${ operateType == 'U' }">
									<input type="radio" name="wldwfz" value="1" <c:if test="${1 == dwb.wldwfz }">checked="checked"</c:if>> 是 &nbsp; &nbsp; &nbsp;
									<input type="radio" name="wldwfz" value="0" <c:if test="${0 == dwb.wldwfz }">checked="checked"</c:if>> 否
								</c:if>
							</div>
						</div>
					</div>
					<div class="col-md-4" id="grfz" >
						<div class="input-group">
							<span class="input-group-addon">个人辅助</span>
							<div class="radiodiv">&nbsp;&nbsp;&nbsp;
                               	<c:if test="${operateType == 'C' }">
									<input type="radio" name="grfz" value="1" > 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="grfz" value="0" checked="checked"> 否
								</c:if>
								<c:if test="${ operateType == 'U' }">
									<input type="radio" name="grfz" value="1" <c:if test="${1 == dwb.grfz }">checked="checked"</c:if>> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="grfz" value="0" <c:if test="${0 == dwb.grfz }">checked="checked"</c:if>> 否
								</c:if>								
							</div>
						</div>
					</div>
			      </div>
		   </div>
		 </div>

		
</form>
<%@include file="/static/include/public-list-js.inc"%>  
</body>
<%-- <%@include file="/static/include/public-manager-js.inc"%> --%>
<script type="text/javascript">

$(function(){
	/* $.getJSON("${ctx}/json/kjhs/kmsz/kjkmsz/kjkmszForUpdate.json",function(data){
		$("#kmsz_kmbh").val(data["fyfl"]);
		$("#kmsz_kmmc").val(data["kmmc"]);
		$("#kmsz_sjkm").val(data["sjkm"]);
		$("#aa").val(data["yefx"]);
		$("#kmsz_zjf").val(data["zjf"]);
	}); */
	$(".bc").attr("disabled", true);
	$("input").attr("disabled", true);
	
	$("#kmsz_kmnd").blur(function(){		
		var length=$("#kmsz_kmnd").val().length;		
		if(length==0 ){
       $(".aa").prop("checked",true);
		}		
		else {
	   $(".bb").prop("checked",true);

		}
	});
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/kjkmsz/goKmszPage";
	});
	//json
	/* $.getJSON("${ctx}/json/kjhs/kmsz/kjkmsz/kjkmszForUpdate.json",function(data){
		$("#kmsz_kmbh").val(data["fyfl"]);
		$("#kmsz_kmmc").val(data["kmmc"]);
		$("#kmsz_kmnd").val(data["kmsx"]);
		$("#drp_dwzt").val(data["kmzt"]);
		$("#kmsz_zjf").val(data["zjf"]);
	}); */
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          kmbh:{validators: {notEmpty: {message: '科目编号不能为空'},stringLength:{message:'编号过长',max:'20'}}},
          kmmc:{validators: {notEmpty: {message: '科目名称不能为空'}}},
   		  yefx:{validators: {notEmpty: {message: '余额方向不能为空'}}},
   		  kmsx:{validators: {notEmpty: {message: '科目属性不能为空'}}},
   			zjf:{validators: {notEmpty: {message: '助记符不能为空'}}}
    }
	      });
	$("#btn_save").click(function(){
		var kmbh = $("#kmsz_kmbh").val();
		var kmmc = $("#kmsz_kmmc").val();
		var zjf = $("#kmsz_zjf").val();
// 		alert("kmbh="+kmbh+"kmmc="+kmmc+"zjf="+zjf);
// 		console.log("zjf==="+zjf);
		$("#txt_kmsx").removeAttr("disabled");
		$(".cc").removeAttr("disabled");
		doSave(validate,"myform","${ctx}/kjkmsz/doSave?kmbh="+kmbh+"&kmmc="+kmmc+"&sfmj=${sfmj}&zjf="+zjf,function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
	});
	$("#btn_savexj").click(function(){
		/* var sfmjsj = "${sfmjsj}";
		var sfmjxj = "${sfmjxj}";
		var guid = "${guid}"; */
		var km = "${param.kmbh}";
		var kmmc = $("#kmsz_kmmc").val();
		var zjf = $("#kmsz_zjf").val();
// 		alert(km);
		doSave(validate,"myform","${ctx}/kjkmsz/doSavexj?km="+km +"&kmmc="+kmmc+"&zjf="+zjf,function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
	});

	
// 	$("#btn_save").click(function(e){
// 		var guid = $("#111111").val();
// 		doSave(validate,"myform","${ctx}/grjfsz/doSave?guid="+guid,function(val){
// 			if(val.success){
// 				alert(val.msg);
// 				window.location.href  = "${ctx}/grjfsz/goListPage";
// 			}
// 		}); 
// 	});
	function doSave1(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
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
	//查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}

	if("000000" == "${dwb.SJDW}"){
		$("#txt_sjdw").attr("readonly","true");
		$("#btn_sjdw").hide();
	}
	//页面加载完后，控制实验室信息模块是否展示
	sysbz();
	//onoff按扭切换
	$("#btn_onoff").click(function(){
		if($("[name='sysbz']").val()=='0'){
			$("[name='sysbz']").val("1");
		}else if($("[name='sysbz']").val()=='1'){
			$("[name='sysbz']").val("0");
		}else{
			$("[name='sysbz']").val("0");
		}
		sysbz();
	});
});

function sysbz(){
	var $this = $("[name='sysbz']").val();
	if($this == '0'){
		$("#sysxx").show();
	}else{
		$("[name='syslx']").val("");
		$("[name='syslb']").val("");
		$("[name='sysmj']").val("0.00");
		$("[name='sysjb']").val("");
		$("[name='ssxk']").val("");
		$("[name='sysgs']").val("");
		$("#sysxx").hide();
	}
}

</script>

</html>