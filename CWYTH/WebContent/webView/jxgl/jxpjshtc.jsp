<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
.tool-fix {
	display: none;
 }
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Oauth应用授权</title>
<%@include file="/static/include/public-manager-css.inc" %>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post">
    <input type="hidden" name="operateType" id="operateType" value="${operateType}">
    <input type="hidden" id="txt_dm" class="form-control input-radius" name="dm" value="${flxx.dm}"/>
    <c:if test="${ operateType == 'U'||operateType == 'C'}">
   				
	  </c:if>
	<div class="container-fluid dialog-body">
				
				
   				  
   				  	<div class="row">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon" >项目负责人 </span>
								<input type="text" id="txt_name" class="form-control input-radius" name="wzmc" readonly value="王建党"/>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><span class="required"></span>项目名称</span>
								<input type="text" id="txt_name" class="form-control input-radius"  name="appid" value="财务一体化"  readonly/>
							</div>
						</div>
					</div>
					
					
					<div class="row">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><span class="required"></span>部门</span>
								<input type="text" id="txt_name" class="form-control input-radius" name="appkey" value="开发一部" readonly/>
							</div>
						</div>
					</div>
					
					
					
					<div class="row">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon">实际得分</span>
                       			<input type="text" id="txt_name" class="form-control input-radius" name="wzym" value="98" readonly/>
                 			  </select>
							</div>
						</div>
						
					
					
					
				<div class='page-bottom clearfix'>
		           <!-- 保存和返回按钮 开始 -->
	                 <div class="pull-right" style="margin-right:20px;">
			            <button class="btn btn-default" id="btn_save" type="button" style="display:;">
			               <i class="fa icon-save"></i>通过
			            </button>
			            	<button class="btn btn-default" id="btn_reject" type="button" style="display:<c:if test="${operateType == 'L'||operateType == 'U'||operateType == 'C'}">none</c:if>;">
						<i class="fa icon-save"></i>退回
						</button>
	                 </div>
	   <!-- 保存和返回按钮 结束 -->  
	              </div>
	          </div>	
     </form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function() {
		//保存按钮
		$("#btn_save").click(function(e){
			alert("审核通过！");
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
	    	
	    	
// 	    	var winId = top.layer.getFrameIndex(parent.window.name);
//         	top.layer.close(winId);
		});
		$("#btn_reject").click(function(e){
			alert("审核未通过！");
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});
		
	});

	</script>
</body>
</html>