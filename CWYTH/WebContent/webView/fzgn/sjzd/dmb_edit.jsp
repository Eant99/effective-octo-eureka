<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>数据字典信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden"  name="dmxh" value="${dmb.DMXH}"/>
	<input type="hidden"  id="jb"  name="jb" value="${dmb.jb}"/>
	<div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>代码编号</span>
					<input type="text" id="txt_dm" class="form-control input-radius" name="dm"  value="${dmb.DM}"/>
					<input type="hidden" class="form-control" name="yzdm"  value="${dmb.DM}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<!-- 带有必填项的label元素 -->
					<span class="input-group-addon"><span class="required">*</span>代码名称</span>
					<input type="text" id="txt_mc" class="form-control input-radius" name="mc"  value="${dmb.MC}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
						<div class="input-group">

							<span class="input-group-addon"><span class="required">*</span>上级代码</span>
                            
                            <input type="text" id="txt_zl" class="form-control input-radius window" name="zl" value="${dmb.SJQC}">
							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"><button type="button" id="btn_zl" class="btn btn-link btn-custom">选择</button></c:if></span>
						</div>
					</div>
		</div>
<!-- 		<div class="row"> -->
<!-- 			<div class="col-sm-12"> -->
<!-- 				<div class="input-group"> -->
<!-- 					<span class="input-group-addon">代码属性</span> -->
<!-- 					<select id="drp_dmsx" class="form-control input-radius" name="dmsx"> -->
<%-- 									<c:forEach var="dmsxlist" items="${dmsxlist}"> --%>
<%-- 									 <option value="${dmsxlist.DM}" <c:if test="${dwb.DMSX == dmsxlist.DM}">selected</c:if>>${dmsxlist.MC}</option>								 --%>
<%-- 									</c:forEach> --%>
<!-- 								</select> -->
<!-- 				</div> -->
<!-- 			</div>  -->
<!-- 		</div> -->
		<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">备&emsp;&emsp;注</span>
                        <textarea id="txt_bz" class="form-control" name="bz" >${dmb.bz}</textarea>
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
	//点击保存
	$("#btn_save").click(function(){
		var mc=$("#txt_mc").val();
		var validate = $('#myform').bootstrapValidator({fields: {
            dm: {validators: {notEmpty: {message: '不能为空'},stringLength: {min: 1,max: 6, message: '请输入1-6位的代码'},}},
            mc: {validators: {notEmpty: {message: '不能为空'}}}}
        });
		
		$.ajax({
			//检查代码名称是否重复
			type: "post",
			url: "${ctx}/dmb/checkMc",
			async: false,
			data:{ mc:mc},
			success:function(val){
			if(val!=0&&1==2){
				alert("同一类别下代码名称不能重复");
			}else{
				doSave(validate,"myform","${ctx}/dmb/doSave",function(val){
					alert(val.msg);
					var pname = "${param.pname}";
					var win = getIframWindow(pname);
					//win.parent.location.href ="${pageContext.request.contextPath}/dmb/goDmbPage";
					var winId = getTopFrame().layer.getFrameIndex(window.name);
					getIframWindow("${param.pname}").table.ajax.reload();
			    	close(winId);
				},function(val){
					
				});								
			}
		  }
		});	
	});
	//上级代码弹窗
	$("#btn_zl").click(function(){
		select_commonWin("${ctx}/window/zdpage?controlId=txt_zl","字典信息","720","630");
    });
	//上级代码联想
	$("#txt_zl").bindChange("${pageContext.request.contextPath}/suggest/getXx","ZD");
	//关闭按钮
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});	
}); 
</script>
</body>
</html>