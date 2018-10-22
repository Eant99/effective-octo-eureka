<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>地点信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body class="contrast-blue" style="background-color: #ffffff">
	<form id="myform" class="form-horizontal" action="" method="post" >
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<div class="container-fluid dialog-body">
			<!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置,此处表示一行三列   开始 -->
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<!-- 带有必填项的label元素 -->
						<span class="input-group-addon"><span class="required">*</span>地&ensp;点&ensp;号</span>
						<input type="text" id="txt_ddh" class="form-control input-radius" name="ddh" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value="${ddb.DDH}"/>
						<input type="hidden" name="ddbh"  value="${ddb.DDBH}"/>
					</div>
				</div>
			</div>
			<div class="row">	
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>地点名称</span>
						<input type="text" id="txt_mc" class="form-control input-radius" name="mc" value="${ddb.MC}"/>
					</div>
				</div>
			</div>
			<div class="row">	
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon">所属单位</span>
						<input type="text" id="txt_dwbh" class="form-control input-radius window" name="dwbh" value="${ddb.dwmc}"/>
						<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"><button type="button" id="btn_dwbh" class="btn btn-link btn-custom">选择</button></c:if></span>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>上级地点</span>
						<input type="text" id="txt_sjdd" class="form-control input-radius window" name="sjdd" value="${ddb.sjddmc}"/>
						<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"><button type="button" id="btn_sjdd" class="btn btn-link btn-custom">选择</button></c:if></span>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon">地点状态</span>
						<select id="drp_ddzt" class="form-control input-radius" name="ddzt">
							<option value="1" <c:if test="${ddb.DDZT == 1}">selected</c:if>>正常</option>
							<option value="0" <c:if test="${ddb.DDZT == 0}">selected</c:if>>禁用</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">	
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">排序序号</span>
						<input type="text" id="txt_pxxh" class="form-control input-radius text-right int" name="pxxh" value="${ddb.pxxh}">
					</div>
				</div>
			</div>
		</div>
		<div class='page-bottom clearfix'>
			<!-- 保存和返回按钮 开始 -->
	        <div class="pull-right">
				<button type='button' class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
					<i class="fa icon-save"></i>
					保存
				</button>
				<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">
					取消
				</button>
	        </div>
       		<!-- 保存和返回按钮 结束 -->
   		</div>	
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function(){
		//查看时处理所有控件
		if($("[name='operateType']").val()=='L'){
			$("input,select,textarea").attr("readonly","true");
			$("select").attr("disabled","true");
		}
		//如果上级地点是顶级地点，则不能修改上级地点
		if("000000" == "${ddb.SJDD}"){
			$("#txt_sjdd").attr("readonly","true");
			$("#btn_sjdd").hide();
		}
		//当不为查看页面时
		if($("[name='operateType']").val()!='L'){
			//所属单位弹窗
			$("#btn_dwbh").click(function(){
				select_commonWin("${ctx}/ddb/szdwWin?controlId=txt_dwbh","单位信息","920","630");
		    });
			//上级地点弹窗
			$("#btn_sjdd").click(function(){
				select_commonWin("${ctx}/ddb/sjddWin?controlId=txt_sjdd","地点信息","920","630");
		    });
		}
		//联想输入提示
	   	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
		$("#txt_sjdd").bindChange("${ctx}/suggest/getXx","P");
		//非空验证
		var validate = $('#myform').bootstrapValidator({fields: {
            ddh: {validators:{notEmpty: { message: '不能为空'}}},
             mc: {validators: {notEmpty: { message: '不能为空'}}}, 
           sjdd: {validators: {notEmpty: { message: '不能为空'}}},}
	    });
		//取消
		$("#btn_cancelw").click(function(){
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});
		//保存按钮
		$("#btn_save").click(function(e){
			doSave(validate,"myform","${ctx}/ddb/doSave",function(val){
// 				getIframWindow("${param.pname}").table.ajax.reload();
				getIframWindow("${param.pname}").parent.location.href = "${ctx}/window/mainDdTree?pageUrl=/ddb/goDdbPage&treeJson=/glqxb/ddTree";
				var winId = getTopFrame().layer.getFrameIndex(window.name);
		    	close(winId);
			},function(val){
				$("#ddh").val("");
				$("#ddh").focus();
			});
		  });
		});
	$(function(){
		$("[name='ddzt']").change(function(){
			if($("[name='ddzt']").val()=='0'){
			 $.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/ddb/getNewStatus",
				data:"sjdd=${ddb.SJDD}",
				success:function(val){
					var data = JSON.getJson(val);
					 if(data.success=='false'){
						alert(data.msg);
						$("#drp_ddzt option").eq(0).attr("selected",true);
						$("#drp_ddzt option").eq(1).attr("selected",false);
					} 
				},
			}); 
				
			}
		});
	});
	</script>
</body>
</html>