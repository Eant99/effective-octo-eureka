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
	<input type="hidden" name="guid" value="${param.guid}" />
    <div class="container-fluid dialog-body">
	<div class="row">
		<div class="col-md-10 col-sm-10">
			<div class="input-group">
<!-- 				<span class="input-group-addon"><span class="required">*</span>目&emsp;&emsp;录</span>
 -->				<c:choose>
					<c:when test="${operateType=='C'}">
						<select id="drp_ywbh" class="form-control input-radius" name="mlbh"  > 
							<option value="">请选择</option> 
							<c:forEach var="Ml_list" items="${Ml_list}">
							<option value="${Ml_list.BH}" <c:if test="${bzxx.MLBH==Ml_list.BH}">selected='selected'</c:if>> ${Ml_list.MC}</option> 
							</c:forEach>
                        </select>
					</c:when>
				    <c:otherwise>
						<c:forEach var="Ml_list" items="${Ml_list}">
							<c:if test="${bzxx.MLBH==Ml_list.BH}">
								<input type="text" class="form-control input-radius" value="${Ml_list.MC}" readonly/>
								<input type="hidden" name="mlbh" value="${bzxx.MLBH}" />
							</c:if>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="input-group">
				<span class="input-group-addon"><span class="required">*</span>模块编号</span>
				<input type="text" name="mkbh" id="zysx_bh" class="form-control input-radius"  value="${bzxx.YWMC}">
			    <span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"></c:if><button type="button" id="btn_mkbh" class="btn btn-link btn-custom">选择</button></span>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="input-group">
				<span class="input-group-addon"><span class="required">*</span>模块名称</span>
				<input type="text" name="mkmc" id="zysx_mc" class="form-control input-radius"  value="${bzxx.YWMC}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6">
						<div class="input-group">
							<span class="input-group-addon">是否显示</span>
							<%-- <input type="text" id="kmsz_xm" class="form-control input-radius text-right int" name="xm"  value="${kmsz.xm}"> --%>
							<div class="radiodiv" >
                                  <div class="pull-left" style="padding-left: 10px;">
								<input type="radio" name="sfxs"  value="1" checked="checked"> 是&ensp;
								<input type="radio" name="sfxs"  value="0"  > 否
							</div>
							</div>
						</div>
					</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="input-group">
				<span class="input-group-addon">注意事项</span>
				<div id="editor">
					<textarea style="width:99%;height:500px;" name="zysxnr" id="content">${Content}</textarea>
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
		var ids = {
	            guid:$("input[name='guid']").val()
	    };
		$.ajax({
			type:"post",
			url:"http://192.168.10.155/apis/app/getDataById",
			data:ids,
			success:function(val){			
				loadData(val);
			}
		});
		
		
//     $.getJSON("http://192.168.10.155/apis/app/getDataById?guid="+$("input[name='guid']").val(),function(data){
// 		$("#content").val(data["ZYSX"]);
// 		$("#zysx_mc").val(data["MC"]);
// 		$("#zysx_bh").val(data["MKBH"]);
// 	});
//弹窗
    $("#btn_mkbh").click(function(){
  		select_commonWin("${ctx}/zysxsz/window?controlId=zysx_bh","模块信息","920","630");
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
// 		var sfxs = "${bzxx.SFXS}";
// 		if(sfxs==''||sfxs==undefined){
// 			$("[name='sfxs']").val("1");
// 		}
		//onoff按扭切换
		$("#btn_sfxs").click(function(){
			if($("[name='sfxs']").val()=='0'){
				$("[name='sfxs']").val("1");
			}else if($("[name='sfxs']").val()=='1'){
				$("[name='sfxs']").val("0");
			}else{
				$("[name='sfxs']").val("1");
			}
		});
		//取消
		$("#btn_cancelw").click(function(){
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});


		//页面验证
		var validate = $('#myform').bootstrapValidator({fields: {
	            mc: {validators: {notEmpty: {message: '模块名称不能为空'}}},
	            zysx: {validators: {notEmpty: {message: '注意事项不能为空'}}},
	            mkbh: {validators: {notEmpty: {message: '模块编号不能为空'}}},
				}
	    });
		$("#btn_save").click(function(){
		doSave1(validate,"myform","http://192.168.10.155/apis/app/updateZysx",function(val){
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
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
					var cc = JSON.parse(val);
					if(cc.success){
		 				alert(cc.msg);
		 				getIframWindow("${param.pname}").table.ajax.reload();
		 				var winId = getTopFrame().layer.getFrameIndex(window.name);
		 		    	close(winId);
// 						parent.location.reload(true);
					}
				},
				error:function(val){
// 					alert("保存成功！");					
				}	
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
	function loadData(val){
		$('input[name=mkbh]').val(val.MKBH);	
		$('input[name=mkmc]').val(val.MKMC);	
//			$('[class=ke-content]').text('dddd');	
		editor.html(val.ZYSXNR);
//			$('input[name=txt_dwdz]').val(val.dwdz);	
	}
</script>
</body>
</html>