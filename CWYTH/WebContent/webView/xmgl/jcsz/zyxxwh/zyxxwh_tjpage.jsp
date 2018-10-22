<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>专业详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    line-height: 25px;
    padding-left: 6px;
}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑专业信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>专业</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>专业编号</span>
							<input type="text" id="txt_zybh" name="zybh" class="form-control input-radius" maxlength="32" value="${zyxx.ZYBH}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>专业名称</span>
                            <input type="text" id="txt_zymc" class="form-control input-radius" name="zymc" maxlength="60" value="${zyxx.ZYMC}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>所属院系</span>
							<input type="text" id="txt_ssyx" class="form-control input-radius window" name="ssyx" maxlength="32" value="${qc}">
							<span class="input-group-btn"><button type="button" id="btn_ssyx" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>专业方向</span>
	                         <input type="text" id="txt_zyfx" class="form-control input-radius window" name="zyfx" maxlength="60" value="${zyxx.ZYFX}">
	                         <span class="input-group-btn"><button type="button" id="btn_zyfx" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>学&emsp;&emsp;制</span>
	                       <select id="drp_xz" class="form-control input-radius " name="xz">
	                                <c:forEach var="xzList" items="${xzList}"> 
	                                   <option value="${xzList.DM}" <c:if test="${zyxxwh.xz == xzList.DM}">selected</c:if>>${xzList.MC}</option>
									</c:forEach>
	                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
						
							
						<span class="input-group-addon"><span class="required">*</span>专业状态</span>
						<div class="radiodiv">
						<div class="pull-left" style="padding-left: 10px;">
							<input type="radio" name="zyzt" value="1" <c:if test="${xtb.td==1}">checked</c:if>/>禁&emsp;&emsp;用&emsp;&emsp;
							<input type="radio" name="zyzt" value="0" checked <c:if test="${xtb.td==0}">checked</c:if>/>启&emsp;&emsp;用
						</div>
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
	var operate = "${operateType}";
	//联想输入提示
	$("#txt_ssyx").bindChange("${ctx}/suggest/getXx","D");
	//联想输入提示
	$("#txt_zyfx").bindChange("${ctx}/suggest/getXx","ZYFX");
	//单位弹窗 
	$("#btn_ssyx").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_ssyx","单位信息","920","630");
    });
	//专业方向弹窗 
	$("#btn_zyfx").click(function(){
		select_commonWin("${ctx}/window/mainZyfx?controlId=txt_zyfx","单位信息","920","630");
    });
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{   
          zybh:{validators: {notEmpty: {message: '不能为空'},stringLength:{message:'专业编号过长',max:'20'}}},
          zymc:{validators: {notEmpty: {message: '不能为空'},stringLength:{message:'专业名称过长',max:'60'}}},
          ssyx:{validators: {notEmpty: {message: '不能为空'},stringLength:{message:'所属院系过长',max:'32'}}},
          zyfx:{validators:{notEmpty:{message: '不能为空'},stringLength:{message:'专业方向过长',max:'40'}}},
          xz:{validators:{ notEmpty:{message: '不能为空'},stringLength:{message:'学制过长',max:'4'}}},
         
        
          }
	      });
	//保存按钮
	/* $("#btn_save").click(function(){
		doSave1(validate,"myform","${ctx}/jsxxs/doSave",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
	}); */
	//保存
	$("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/zyxxwh/doSave",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
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
				},
				error:function(val){
					alert("保存成功！");					
				}	
			});
		}
	}
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/xmgl/jcsz/zyxxwh/zyxxwh_list.jsp";
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
	
});

</script>
</body>
</html>