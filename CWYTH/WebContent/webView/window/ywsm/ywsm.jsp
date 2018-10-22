<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>业务说明</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/ckeditor/ckeditor.js"></script>
<style type="text/css">
	.box-header {
		position: fixed;
	    width: 100%;
	    top: 0;
	    background-color: #f3f3f3;
	    z-index: 10;
	}
	.title{
		text-align:center;
		color:#000;
	}
	.fjtitle{
		font-size: 10px;
		text-align:center;
		color:#9D9D9D;
	}
	.container {
    	width: 860px;
    	margin-top: 45px;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div id="d_all" class="box-panel">
		<div class="box-header clearfix">
           	<div class="title">
           		<h4>${title}</h4>
           	</div>
			<hr class="hr-normal">
       	</div>
		<div class="container box-content">
			<div class="row">
				<div class="col-md-12">
<%-- 					<div id="editor">${Content}</div> --%>
						<div id="editor">
							${ywsmb.zysxnr}
						</div>
				</div>
			</div>
		</div>
		<c:if test="${sfbc == '1' && desk != '1'}">
		    <div class='page-bottom clearfix ' style="left:0px;">
	            <div class="pull-right">
				    <span style="color: red;"><input type="checkbox" id="yyd"  value="1"  <c:if test="${wdxx == '1' }">checked</c:if> />已阅读，不再显示&emsp;&emsp;</span>
				    <button type='button' class="btn btn-default " id="btn_comforms">确定</button>
	            </div>
	        </div>
		</c:if>
		<div class='page-bottom clearfix yincang' style="left:0px;display:none;">
	        <div class="pull-right">
				<span style="color: red;"><input type="checkbox" id="prompt" value="1"/>已阅读，不再显示&emsp;&emsp;</span>
				<button type='button' class="btn btn-default " id="btn_comform">确定</button>
	        </div>
	    </div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var sfbc = "${sfbc}";  
	if (sfbc!="1"){
		$(".yincang").show();
	}
	$("#btn_comforms").click(function(){
		var wdxx=$("#yyd:checked").val();
		$.ajax({
			type:"post",
			data:"mkbh=${mkbh}&wdxx="+wdxx,
			url:"${pageContext.request.contextPath}/ywsm/ywSet",
			success:function(val){
				var winId = top.layer.getFrameIndex(window.name);
		    	top.layer.close(winId);
			}
		});
	});
	//确定
	$("#btn_comform").click(function(){
		var sfts=$("#prompt:checked").val();
		if(sfts==undefined){
			var sfts=0;
		}
		$.ajax({
			url:"${ctx}/ywsm/upSfts?mkbh=${mkbh}&sfts="+sfts
		});
		var trunurl = "${url}".replace("\@","&");
		getIframWindow("${pname}").location.href = trunurl + "&type=${type}";
    	var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
</script>
</body>
</html>