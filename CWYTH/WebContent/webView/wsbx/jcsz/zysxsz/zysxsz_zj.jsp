<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>帮助信息设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
<style type="text/css">
	
	.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    padding-left: 6px;
}
</style>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}"> 
	<input type="hidden" id="guid"	name="guid" value="${dwb.guid}"> 
	<input type="hidden" name="czr"	value="${loginId}">
    <div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>模块编号</span>
					<input type="text" name="mkbh" readonly id="zysx_bh" class="form-control input-radius"  value="${dwb.mkbh}">
				    <span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"></c:if><button type="button" id="btn_mkbh" class="btn btn-link btn-custom">选择</button></span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>模块名称</span>
					<input type="text" name="mc" id="zysx_mc" readonly class="form-control input-radius"  value="${dwb.mkmc}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>是否展示</span>
					<c:if test="${operateType == 'U' or operateType == 'C' }">
				 		<input type="hidden" name="xm" value="${dwb.sfxs}">
	              		<div class="switch">
	                  		<div class="onoffswitch">
	                      		<input type="checkbox"   <c:if test="${dwb.sfxs == '1'}">checked</c:if> class="onoffswitch-checkbox" <c:if test="${operateType != 'L'}">id="btn_nnoff" </c:if>>
	                   			<label class="onoffswitch-label" for="btn_nnoff">
	                      			<span class="onoffswitch-inner"></span>
	                      			<span class="onoffswitch-switch"></span>
	                       		</label>
	                   		</div>
	                  	</div>
	                </c:if>
					<c:if test="${operateType == 'L'}">
						<input type="text" class="form-control input-radius" <c:choose>
							<c:when test="${dwb.sfxs == '1'}">value="是"</c:when>
							<c:otherwise>value="否"</c:otherwise>
						</c:choose> >
					</c:if>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>注意事项</span>
					<div id="editor">
						<textarea class="zysx" style="width:99%;height:500px;" name="zysx" id="content">${dwb.zysxnr}</textarea>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
		<div class="pull-right">
			<button class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
				 <i class="fa icon-save"></i>
					保存
			</button>
			<button class="btn btn-default btn-without-icon" id="btn_cancelw">
					取消
			</button>
		 </div>
	</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function(){
  
//弹窗
    $("#btn_mkbh").click(function(){
  		select_commonWin("${ctx}/zysxsz/window?controlId=zysx_bh","模块信息","920","730");
   }); 
    $("#btn_mkmc").click(function(){
  		select_commonWin("${ctx}/zysxsz/window?controlId=zysx_mc","模块信息","920","630");
    }); 

		editor();
		//查看页面时处理函数
		if($("[name='operateType']").val()=='L'){
			$("input,select,textarea").attr("readonly","true");
			$("select").attr("disabled","true");
			$(".onoffswitch-checkbox").attr("id","true");
		}
		var sfxs = "${bzxx.SFXS}";
		if(sfxs==''||sfxs==undefined){
			$("[name='sfxs']").val("1");
		}
		//onoff按扭切换
		$("#btn_nnoff").click(function(){
			if($("[name='xm']").val()=='0'){
				$("[name='xm']").val("1");
			}else if($("[name='xm']").val()=='1'){
				$("[name='xm']").val("0");
			}else{
				$("[name='xm']").val("1");
			}
		});
		//取消
		$("#btn_cancelw").click(function(){
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});


		//页面验证
		var validate = $('#myform').bootstrapValidator({fields:{
			 mc: {validators: {notEmpty: {message: '模块名称不能为空'}}},
// 	           zysx: {validators: {notEmpty: {message: '注意事项不能为空'}}},
	           mkbh: {validators: {notEmpty: {message: '模块编号不能为空'}}}
		}});

		//保存
		$("#btn_save").click(function(){
// 			var check = $("[name='zysx']").val();
// 			console.log(check+"______");
// 			if(check == ''){
// 				alert("注意事项不能为空!!!!");
// 			}else if(check != ''){
// 				doSave1($("#myform"),"${ctx}/zysxsz/doSave?operateType=U",function(val){
// 					getIframWindow("${param.pname}").table.ajax.reload();
// 					var winId = getTopFrame().layer.getFrameIndex(window.name);
// 					alert("信息保存成功！！！");
// 			    	close(winId);
// 				});
// 			}	
			
			var mkbh = $("[name='mkbh']").val();
			var mkmc = $("[name='mc']").val();
			var gid = $("[name='gid']").val();
			var sfzs = $("[name='xm']").val();
			var zysxnr = encodeURIComponent($("[name='zysx']").val());
// 			var zysxnr = $("[name='zysx']").val();
// 			alert(zysxnr);
			var operateType = $("[name='operateType']").val();
			doSave1(validate,"myform","${ctx}/zysxsz/doSave?zysx="+zysxnr+"&mc="+mkmc+"&xm="+sfzs+"&operateType="+operateType+"&gid="+gid+"&mkbh="+mkbh,function(val){
			    getIframWindow("${param.pname}").table.ajax.reload();
				var winId = getTopFrame().layer.getFrameIndex(window.name);
		 	    close(winId);
			  },function(val){
				});
// 		 	$.ajax({
// 				type:"post",
// 				url:"${ctx}/zysxsz/doSave",
// 				data:"zysx="+zysxnr+"&mc="+mkmc+"&xm="+sfzs+"&operateType="+operateType+"&gid="+gid+"&mkbh="+mkbh,
// 				dataType:"json",
// 				success:function(val){
// 					alert(val.msg);
// 					getIframWindow("${param.pname}").table.ajax.reload();
// 		 			var winId = getTopFrame().layer.getFrameIndex(window.name);
// 		 	    	close(winId);
// 				}
// 			}); 	
			
	});
		//保存方法	
		function doSave1(_validate,_formId, _url, _success, _fail){
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
				success:function(val){
					var data = JSON.getJson(val);
					alert(data.msg);
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
					alert("抱歉，系统出现问题！");
				},
			});	
		}
		}

	});
	function editor(){
		KindEditor.ready(function(K) {
	        editor = K.create('#content',{
	        	afterBlur : function() {
	    			this.sync();
	    		}
	        });
		});
	}
</script>
</body>
</html>