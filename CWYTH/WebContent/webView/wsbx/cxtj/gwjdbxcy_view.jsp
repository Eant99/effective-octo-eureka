<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>公务接待费报销</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${gwjdfsq.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑公务接待费报销</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>报销详细</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号</span>
                             <input type="text" id="txt_ysdw" readonly class="form-control input-radius " name="ysdw" value="${gwjdfsq.djbh}"/>                      
						</div>
					</div>
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算单位</span>
                             <input type="text" id="txt_ysdw" class="form-control input-radius" name="ysdw" value="${gwjdfsq.ysdw}"/>
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销部门</span>
							 <input type="text" id="txt_bxbm" class="form-control input-radius" name="bxbm" value="${gwjdfsq.bxbm}"/>
						</div>
					</div>				
				</div>
			<div class="row">
					  <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报&ensp;销&ensp;人</span>
                              <input type="text" id="txt_bxr" class="form-control input-radius" name="bxr" value="${gwjdfsq.bxr}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">来宾人数（人）</span>
					  <input type="text" id="txt_bxrs" class="form-control input-radius  text-right int" name="bxrs" value="${gwjdfsq.bxrs}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">接待场所</span>
                            <input type="text" id="txt_jdcs" class="form-control input-radius" name="jdcs" value="${gwjdfsq.jdcs}"/>
						</div>
					</div>				
				</div>
				<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">陪同人数（人）</span>
                          <input type="text" id="txt_ptrs" class="form-control input-radius  text-right int" name="bxrs" value="${gwjdfsq.ptrs}"/>
					</div>
				</div>
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销金额（元）</span>
                              <input type="text" id="txt_bxje" class="form-control input-radius text-right number" name="bxje" value="<fmt:formatNumber value='${gwjdfsq.bxje}' pattern='0.00'/>">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销类型</span>
                             <select id="drp_s" class="form-control"name="spzt" table="K" disabled>
								<option value="">未选择</option>
							    <option value="1"<c:if test="${gwjdfsq.bxlx==1}">selected</c:if>>公费</option>
							</select>
						</div>
					</div>	
				</div>
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">接待日期</span>
                           <input type="text" id="txt_jdrq" class="form-control date" name="jdrq" value="<fmt:formatDate value="${gwjdfsq.JDRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="接待日期" disabled>
						</div>
					</div>
				
				</div>
                 <div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                               <textarea id="txt_bz" class="form-control" readonly name="bz" >${gwjdfsq.bz}</textarea>
						</div>
					</div>	
                 </div>
                  <div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
                               <textarea id="txt_bz" class="form-control" readonly name="bz" >${qtjfbxsq.bz}</textarea>
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
	$("input").attr("readonly","readonly");
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/wsbx/cxtj/ggjdbxcy_list.jsp";
	});
	
	
});

</script>
</body>
</html>