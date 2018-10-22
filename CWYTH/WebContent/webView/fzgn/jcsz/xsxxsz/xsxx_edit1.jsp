<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学生详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${xsxx.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑学生基本信息</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-primary">
				<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-primary">学生基本信息&nbsp;></div>
				<div class="sub-title pull-left text-primary">
				<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    			</div>
				</div>
				<div class="sub-title pull-left">学生学籍信息&nbsp;</div>
			
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>第一步，学生基本信息</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
            		<button type="button" class="btn btn-default" id="btn_save" >保存</button>
            		<button type="button" class="btn btn-default" id="btn_del" style="background:#00acec;color: white;">删除</button>
					<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>学&emsp;&emsp;号</span>
							<input type="text" id="txt_xh" name="xh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${xsxx.xh}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>姓&emsp;&emsp;名</span>
                            <input type="text" id="txt_xm" class="form-control input-radius" name="xm" value="${xsxx.xm}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>性&emsp;&emsp;别</span>
							<select id="drp_xb" class="form-control input-radius select2" name="xbm"> 
								<c:forEach var="sexList" items="${sexList}">
	                        		<option value="${sexList.DM}" <c:if test="${xsxx.xb == sexList.DM}">selected</c:if>>${sexList.MC}</option>
	                        
	                        	</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>所在院系</span>
	                         <input type="text" id="txt_szyx" class="form-control input-radius window" name="szyx" value="${xsxx.szyx}">
	                         <span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C'}"><button type="button" id="btn_szyx" class="btn btn-link btn-custom">选择</button></c:if></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>年&emsp;&emsp;级</span>
	                        <input type="text" id="txt_nj" class="form-control input-radius window" name="nj" value="${xsxx.nj}"/>
	                        <span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C'}"><button type="button" id="btn_nj" class="btn btn-link btn-custom">选择</button></c:if></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>专&emsp;&emsp;业</span>
							<input type="text" id="txt_zy" class="form-control input-radius window" name="zy" value="${xsxx.zy}" />
							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C'}"><button type="button" id="btn_zy" class="btn btn-link btn-custom">选择</button></c:if></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>班&emsp;&emsp;级</span>
							<input type="text" id="txt_bj" class="form-control input-radius window" name="bj" value="${xsxx.bj}" />
							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C'}"><button type="button" id="btn_bj" class="btn btn-link btn-custom">选择</button></c:if></span>
						</div>
                  </div>
                  <div class="col-md-4">                 	                 	
                  	<div class="input-group">
							<span class="input-group-addon">健康状况</span>
                            <select id="txt_jkzkm" class="form-control input-radius select2" name="jkzkm">
                               <c:forEach var="jkzkList" items="${jkzkList}">
                           			<option value="${jkzkList.DM}" <c:if test="${xsxx.jkzkm == jkzkList.DM}">selected</c:if>>${jkzkList.MC}</option>
                            	</c:forEach>
                            </select>
					</div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>学生类别</span>
                            <select id="txt_xxlb" class="form-control input-radius select2" name="xxlb">
                               <c:forEach var="xxlbList" items="${xxlbList}">
                           			<option value="${xxlbList.DM}" <c:if test="${xsxx.xxlb == xxlbList.DM}">selected</c:if>>${xxlbList.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">出&ensp;生&ensp;地</span>
                            <select id="txt_csdm" class="form-control input-radius select2" name="csdm">
                               <c:forEach var="qydmList" items="${qydmList}">
                           			<option value="${qydmList.DM}" <c:if test="${xsxx.csdm == qydmList.DM}">selected</c:if>>${qydmList.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">民&emsp;&emsp;族</span>
                            <select id="txt_mz" class="form-control input-radius select2" name="mzm">
                               <c:forEach var="mzList" items="${mzList}">
                           			<option value="${mzList.DM}" <c:if test="${xsxx.mz == mzList.DM}">selected</c:if>>${mzList.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">籍&emsp;&emsp;贯</span>
							<input type="text" id="txt_jg" class="form-control input-radius" name="jg" value="${xsxx.jg}" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">国籍/地区</span>
                            <select id="txt_gj" class="form-control input-radius select2" name="gjdqm">
                               <c:forEach var="gjList" items="${gjList}">
                           			<option value="${gjList.DM}" <c:if test="${xsxx.gj == gjList.DM}">selected</c:if>>${gjList.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>身份证件类型</span>
                            <select id="txt_sfzjlxm" class="form-control input-radius select2" name="sfzjlxm">
                               <c:forEach var="zjlxmList" items="${zjlxmList}">
                           			<option value="${zjlxmList.DM}" <c:if test="${xsxx.sfzjlxm == zjlxmList.DM}">selected</c:if>>${zjlxmList.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>身份证件号</span>
							<input type="text" id="txt_sfzh" class="form-control input-radius" name="sfzh" value="${xsxx.sfzh}" />
						</div>
					</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="input-group">
							<span class="input-group-addon">婚姻状况</span>
                            <select id="txt_hyzkm" class="form-control input-radius select2" name="hyzkm">
                               <c:forEach var="hyzkList" items="${hyzkList}">
                           			<option value="${hyzkList.DM}" <c:if test="${xsxx.hyzkm == hyzkList.DM}">selected</c:if>>${hyzkList.MC}</option>
                            	</c:forEach>
                            </select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
							<span class="input-group-addon">政治面貌</span>
                            <select id="txt_zzmmm" class="form-control input-radius select2" name="zzmmm">
                               <c:forEach var="zzmmList" items="${zzmmList}">
                           			<option value="${zzmmList.DM}" <c:if test="${xsxx.zzmmm == zzmmList.DM}">selected</c:if>>${zzmmList.MC}</option>
                            	</c:forEach>
                            </select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
                  		<span class="input-group-addon">出生日期</span>
                        <input type="text" id="txt_csrq" class="form-control date" name="csrq" value="<fmt:formatDate value="${xsxx.csrq}" pattern="yyyy-MM-dd"/>"  data-format="yyyy-MM-dd" placeholder="出生日期">
                        <span class='input-group-addon'>
                            <i class="glyphicon glyphicon-calendar"></i>
                        </span>
                  	</div>
				</div>
			</div>
			<div class="row">
			    <div class="pull-center" style="text-align:center;">
            		<button type="button" class="btn btn-default" id="btn_next" style="background:#00acec;color: white;">下一步</button>
        		</div>
			</div>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//身份证号获取出生年月	
	$("#txt_sfzh").blur(function(){
		var IDcard = $(this).val();
		var length = IDcard.length;
		//身份证号输完自动填写出生日期
		if(length == 18){
        var birthday = IDcard.substr(6, 4)+ "-" + IDcard.substr(10, 2) + "-" + IDcard.substr(12, 2);		
		$("#txt_csrq").val(birthday);
		}      
	});
});
$(function(){
	
    var tag=false;
	//联想输入提示院系
	$("#txt_szyx").bindChange("${ctx}/suggest/getXx","D");
	//联想输入提示专业
	$("#txt_zy").bindChange("${ctx}/suggest/getXx","ZY");
	//联想输入提示班级
	$("#txt_bj").bindChange("${ctx}/suggest/getXx","BJ");
	//联想输入提示年级
	$("#txt_nj").bindChange("${ctx}/suggest/getXx","NJ");
	//单位弹窗 
	$("#btn_szyx").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_szyx","院系信息","920","630");
    });
	//专业弹窗 
	$("#btn_zy").click(function(){
		var szyx = $("[name='szyx']").val();
		select_commonWin("${ctx}/xsxx/zypageto?controlId=txt_zy","专业信息","920","630");
    });
	//年级弹窗 
	$("#btn_nj").click(function(){
		select_commonWin("${ctx}/xsxx/njpageto?controlId=txt_nj","年级信息","920","630");
    });
	//班级弹窗 
	$("#btn_bj").click(function(){
		select_commonWin("${ctx}/xsxx/bjpageto?controlId=txt_bj","班级信息","920","630");
    });
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          xh:{validators:{notEmpty: {message: '学号不能为空'},regexp: {regexp: /^[a-zA-Z0-9_]+$/,message: '学号只能包含大写、小写、数字和下划线'},stringLength:{message:'学号过长',max:'20'}}},
          xm:{validators:{ notEmpty: {message: '姓名不能为空'},stringLength:{message:'姓名过长',max:'8'}}},
          szyx:{validators: {notEmpty: {message: '院系不能为空'}}},
          nj:{validators: {notEmpty: {message: '年级不能为空'}}},
          zy:{validators: {notEmpty: {message: '专业不能为空'}}},
          bj:{validators: {notEmpty: {message: '班级不能为空'}}},
          xxlb:{validators: {notEmpty: {message: '学生类别不能为空'}}},
          sfzjlxm:{validators:{notEmpty:{message: '身份证件类型不能为空'}}},
          xbm:{validators:{ notEmpty:{message: '性别不能为空'},}},
          sfzh:{validators:{cardId:{message: '请输入有效的身份证号'},notEmpty:{message:"身份证件号不能为空"}}}
          }
	      });
	//保存按钮

   $("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/jsxxs/doSave",function(val){
			if(val.success){
                tag=true;
				//alert(val.msg);
				//parent.location.reload(true);
			}
		});
	});
	//下一步
   $("#btn_next").click(function(){
      if(tag){
		window.location.href = "${ctx}/xsxx/after";
}else{
         doSave1(validate,"myform","${ctx}/xsxx/doSave",function(val){
            if(val.success){
				window.location.href = "${ctx}/xsxx/after";
			}
					
			
			
		},function(val){
			alert(val.msg);
		});
}
		
	});
  
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
				close(index);
				var data = JSON.getJson(val);
				//alert(data.msg);
				if(data.success == "true"){
					$("#operateType").val("U");
					if(_success != "" && _success != "" && _success != ""){
						_success(data);
					}
				} else {
					if(_fail != "" && _fail != "" && _fail != ""){
						_fail(data);
					}
				}
			},
			error:function(val){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(val){
				index = loading(2);
			}
		});
	}
}
	
    
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/xsxx/goXsxxListPage";
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}"
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"
	    	}
		 }
	});
	$("[name='zzzt']").change(function(){
		if($(this).val() == '3'){
			$("#txrq").show();
		}else{
			$("#txrq").hide();
		}
	});
	//页面加载完后，退休日期是否可编辑
	txrq();
	function txrq(){
		var $this = $("[name='zzzt']").val();
		if($this == '3'){
			$("#txrq").show();
		}else{
			$("#txrq").hide();
		}
	}
});
</script>
</body>
</html>