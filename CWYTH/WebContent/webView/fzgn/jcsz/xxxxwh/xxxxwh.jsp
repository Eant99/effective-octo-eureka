<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学校信息维护</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script>

init();
function init(){
	if("${flg}"=="1"){
	
		
	}else{
		location.href ="${ctx}/xxxxwh/goEditPage";	
	}
	 
}

</script>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">	
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑学校信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_reset">重置</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>学校信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
        	
			<hr class="hr-normal">
			<div style="border:1px solid rgb(226, 220, 220);margin-left:15px;margin-right:15px;margin-top:15px;">
			<div class="container-fluid box-content" style="margin-top:5px;margin-bottom:5px;">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>学校代码 </span>
							<input type="text" id="txt_xxdm" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" name="xxdm" value="${xxxxwh.xxdm}"/>
							
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>学校名称</span>
                            <input type="text" id="txt_xxmc" class="form-control input-radius" name="xxmc" value="${xxxxwh.xxmc}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">学校地址</span>
						 <input type="text" id="txt_xxdz" class="form-control input-radius" name="xxdz" value="${xxxxwh.xxdz}"/>
						</div>
					</div>
					
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">学校邮编</span>
	                         <input type="text" id="txt_xxyzbm" class="form-control input-radius window" name="xxyzbm" value="${xxxxwh.xxyzbm}">	                    
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>学校性质</span>
                                  <select id="drp_xxxzm" class="form-control input-radius" name="xxxzm">
									<option value="1" <c:if test="${xxxxwh.xxxzm == '1'}">selected</c:if>>公办</option>
									<option value="2" <c:if test="${xxxxwh.xxxzm == '2'}">selected</c:if>>私立</option>
								</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>联系电话</span>
                            <input type="text" id="txt_lxdh" class="form-control input-radius" name="lxdh" value="${xxxxwh.lxdh}"/>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>法定代表人</span>
							<input type="text" id="txt_fddbrh" class="form-control input-radius window" name="fddbrh" value="${xxxxwh.fddbrh}">
							<span class="input-group-btn"><button type="button" id="btn_fddbrh" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
          
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>校长</span>
							<input type="text" id="txt_xzxm" class="form-control input-radius window" name="xzxm" value="${xxxxwh.xzxm}">
							<span class="input-group-btn"><button type="button" id="btn_xzxm" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>行政区划</span>
                        <input type="text" id="txt_xzqym" class="form-control input-radius" name="xzqym" value="${xxxxwh.xzqym}"/>
					</div>
				</div>
				</div>	
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">传真电话</span>
                           <input type="text" id="txt_czdh" class="form-control input-radius" name="czdh" value="${xxxxwh.czdh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">电子信箱</span>
	                          <input type="text" id="txt_dzxx" class="form-control input-radius" name="dzxx" value="${xxxxwh.dzxx}"/>
						</div>
					</div>
				</div>
				</div>	
			</div>	
	</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{ 
		xxdm:{validators:{notEmpty: {message: '学校代码不能为空'},regexp: {regexp: /^[a-zA-Z0-9_]+$/,message: '学校代码只能包含大写、小写、数字和下划线'}}},
    	xxmc:{validators:{ notEmpty: {message: '学校名称不能为空'}}},
    	xzqym:{validators:{ notEmpty: {message: '行政区划码不能为空'}}},
    	xxzgbmm:{validators:{ notEmpty: {message: '学校主管部门码不能为空'}}},
    	xxxzm:{validators:{ notEmpty: {message: '学校性质码不能为空'}}},
    	fddbrh:{validators:{ notEmpty: {message: '法定代表人号不能为空'}}},
    	dzxx:{validators:{emailAddress:{message: '请输入有效的E-mail地址'}}},
    	czdh:{validators:{ tel:{message: '请输入有效的传真电话'}}},
    	lxdh:{validators:{ tel:{message: '请输入有效的联系电话'},notEmpty: {message: '联系电话不能为空'}}},
    	//xzgh:{validators:{ notEmpty: {message: '校长工号不能为空'}}},
    	xzxm:{validators:{ notEmpty: {message: '校长姓名不能为空'}}} 
        }
	      });
	//保存按钮
	$("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/xxxxwh/doUpdate",function(val){
			$("#txt_xxdm").focus();
		});
	});
	//重置按钮
	$("#btn_reset").click(function(){
		
	  $("#txt_xxdm").val("");
	  $("#txt_xxmc").val("");
	  $("#txt_xxdz").val("");
	  $("#txt_xxyzbm").val("");
	  $("#txt_xxxzm").val("");
	  $("#txt_lxdh").val("");
	  $("#txt_fddbrh").val("");
	  $("#txt_xzxm").val("");
	  $("#txt_xzqym").val("");
	  $("#txt_czdh").val("");
	  $("#txt_dzxx").val("");	
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.xxdm}"
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"
	    	}
		 }
	});
	//联想输入
	$("#txt_fddbrh").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_xzxm").bindChange("${ctx}/suggest/getXx","R");
	
	//弹框
	$("#btn_fddbrh").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_fddbrh","人员信息","920","630");
    });
	$("#btn_xzxm").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_xzxm","人员信息","920","630");
    });
	//onoff按扭切换
	$("#btn_onoff1").click(function(){
		if($("[name='xxgczk']").val()=='0'){
			$("[name='xxgczk']").val("1");
		}else if($("[name='xxgczk']").val()=='1'){
			$("[name='xxgczk']").val("0");
		}else{
			$("[name='xxgczk']").val("1");
		}
		
	});

	$("#btn_onoff2").click(function(){
		if($("[name='zdxxzk']").val()=='0'){
			$("[name='zdxxzk']").val("1");
		}else if($("[name='zdxxzk']").val()=='1'){
			$("[name='zdxxzk']").val("0");
		}else{
			$("[name='zdxxzk']").val("1");
		}
		
	});

	$("#btn_onoff3").click(function(){
		if($("[name='yjsyzk']").val()=='0'){
			$("[name='yjsyzk']").val("1");
		}else if($("[name='yjsyzk']").val()=='1'){
			$("[name='yjsyzk']").val("0");
		}else{
			$("[name='yjsyzk']").val("1");
		}
	
	});

	$("#btn_onoff4").click(function(){
		if($("[name='jbwljyzk']").val()=='0'){
			$("[name='jbwljyzk']").val("1");
		}else if($("[name='sysbz']").val()=='1'){
			$("[name='jbwljyzk']").val("0");
		}else{
			$("[name='jbwljyzk']").val("1");
		}
	
	});

	$("#btn_onoff5").click(function(){
		if($("[name='jbcrjyzk']").val()=='0'){
			$("[name='jbcrjyzk']").val("1");
		}else if($("[name='jbcrjyzk']").val()=='1'){
			$("[name='jbcrjyzk']").val("0");
		}else{
			$("[name='jbcrjyzk']").val("1");
		}
	
	});
	
	$("#btn_onoff6").click(function(){
		if($("[name='gjsfxgzyxzk']").val()=='0'){
			$("[name='gjsfxgzyxzk']").val("1");
		}else if($("[name='gjsfxgzyxzk']").val()=='1'){
			$("[name='gjsfxgzyxzk']").val("0");
		}else{
			$("[name='gjsfxgzyxzk']").val("1");
		}
		
	});
	
	$("#btn_onoff7").click(function(){
		if($("[name='gcyxzk']").val()=='0'){
			$("[name='gcyxzk']").val("1");
		}else if($("[name='gcyxzk']").val()=='1'){
			$("[name='gcyxzk']").val("0");
		}else{
			$("[name='gcyxzk']").val("1");
		}
		
	});
});
</script>
</body>
</html>