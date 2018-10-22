<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产编号保留位设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.zclb_div{
	float: left;
	width: 150px;
}
</style>
</head>
<body  style="background-color: white;">
<form id="zcbh_form" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="flmc" id="flmc" value="${hsortset.FLMC}">
	<input type="hidden" name="dlhOld" id="dlhOld" value="${hsort.DLH}">
    <div class="container-fluid dialog-body" style="padding-left: 30px;padding-right: 30px;margin-top: 2px;">
        <!-- <div class="row">
			 <div class="col-sm-12">
			     <div class="alert alert-info pull-right" style="padding: 4px;margin-bottom:10px;margin-left: 0px;">
           		<span><b>温馨提示：</b>如果复选框不能被选中，说明该资产类别已被其他[组编号]使用，请先删除后，再添加。</span>
           	</div>
			</div>
		</div> -->
        <div class="row">
			 <div class="col-sm-12">
			     <div class="input-group">
					 <span class="input-group-addon"><span class="required">*</span>组&ensp;编&ensp;号</span> 
					 <input type="text" class="form-control" name="zbh"  value="${hsortset.ZBH}" <c:if test="${operateType =='U'}" >readonly</c:if> />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
			    <div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>组&ensp;名&ensp;称</span> 
					<input type="text" class="form-control" name="zmc"  value="${hsortset.ZMC}"  />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
			    <div class="input-group">
					<span class="input-group-addon">保&ensp;留&ensp;位</span> 
					<input type="text" class="form-control" name="blw"  value="${hsortset.BLW}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-6">
				<label class="control-label col-md-3" for="dlh">资产类别</label>
				<div class="col-md-9" style="overflow: hidden;">
					<c:forEach var="zclb_list" items="${zclb_list}">
						<div class="zclb_div"><input type="checkbox" name="dlh" id="zclb_list" value="${zclb_list.DLH}" flmc = "${zclb_list.MC}"<c:if test="${zclb_list.ZBH == hsortset.ZBH && zclb_list.ZBH !=null && zclb_list.ZBH !='' && zclb_list.ZBH !='null' &&zclb_list.ZBH !='undefined' }">checked</c:if>>${zclb_list.MC}</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
	    <div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
				<i class="fa icon-save"></i>
				保存
			</button>
			<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">
				取消
			</button>
	    </div>
	</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
		$("[name='dlh']").attr("disabled","true");
	}
	var validate = $("#zcbh_form").bootstrapValidator({fields:{
		     zbh:{message:'代码不可用',validators:{notEmpty:{message:'组编号不能为空'}}},
		     zmc:{validators:{notEmpty: { message: '组名称不能为空'}}},
		     blw:{validators:{stringLength: {min: 2, max: 2,message: '输入两位保留位'},regexp: {regexp: /^[a-zA-Z0-9]+$/,message: '保留位只能包含字母和数字'}}}
	     }
	});
	//取消
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
	    close(winId);
	});
	//保存按钮
	$("#btn_save").click(function(e){
		var checkbox = $(".zclb_div").find("[name='dlh']").filter(":checked");
		if(checkbox.length>0){
		    var dlh =[];
		    var flmc =[];
		    checkbox.each(function(){
			    flmc.push($(this).attr("flmc"));
			    dlh.push($(this).val());
		});
		 $("[name = 'flmc']").val(flmc);
		doSave(validate,"zcbh_form","${pageContext.request.contextPath}/hsortset/doSave?dlhArr="+dlh.join("','")+"&dlhOld="+$("[name = 'dlhOld']").val(),function(val){
		    getIframWindow("${param.pname}").table.ajax.reload();//刷新
			var winId = getTopFrame().layer.getFrameIndex(window.name);
		    close(winId);//关闭
		},function(val){
		}); 
		
		}else{
			 alert("请选择相应的资产类别！");
		}
	});
});
</script>