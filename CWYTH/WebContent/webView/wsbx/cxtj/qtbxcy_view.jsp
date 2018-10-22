<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>其他经费报销申请</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${qtjfbxsq.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑公务接待费报销申请</span>
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
                             <input type="text" id="txt_pjbh" class="form-control input-radius" name="pjbh" value="${qtjfbxsq.pjdh}"/>
                            
						</div>
					</div>
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">经费代码</span>
                             <input type="text" id="txt_jfdm" class="form-control input-radius" name="jfdm" value="${qtjfbxsq.jfdm}"/>
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">经费名称</span>
							 <select id="drp_jfmc" class="form-control input-radius" name="jfmc" disabled>
	                  <option value="1" <c:if test="${dwb.DWBB == dwbblist.DM}">selected</c:if>>差旅费</option>
	                <option value="2" <c:if test="${dwb.DWBB == dwbblist.DM}">selected</c:if>>公务费</option>			
	                        </select>
                           
						</div>
					</div>				
				</div>
			<div class="row">
					  <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报&ensp;销&ensp;人</span>
                              <input type="text" id="txt_bxr" class="form-control input-radius" name="bxr" value="${qtjfbxsq.bxr}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销部门</span>
					  <input type="text" id="txt_bxbm" class="form-control input-radius" name="bxbm" value="${qtjfbxsq.bxbm}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销事由</span>
                            <input type="text" id="txt_bxsy" class="form-control input-radius" name="bxsy" value="${qtjfbxsq.bxsy}"/>
						</div>
					</div>				
				</div>
				<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">附件张数（张）</span>
                          <input type="text" id="txt_fjzs" class="form-control input-radius  text-right int" name="fjzs" value="${qtjfbxsq.fjzs}"/>
					</div>
				</div>
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算年度</span>
                              <input type="text" id="txt_ysnd" class="form-control input-radius text-right number" name="ysnd" value="<fmt:formatNumber value='${qtjfbxsq.ysnd}' pattern='0.00'/>">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申请单据编号</span>
                            <input type="text" id="txt_bxsy" class="form-control input-radius" name="sqdjbh" value="${qtjfbxsq.sqdjbh}"/>
						</div>
					</div>	
				</div>
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销金额（元）</span>
                             <input type="text" id="txt_bxje" class="form-control input-radius text-right number" name="bxje" value="<fmt:formatNumber value='${qtjfbxsq.bxje}' pattern='0.00'/>">
						</div>
					</div>
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销日期</span>
                           <input type="text" id="txt_bxrq" class="form-control date" name="bxrq" value="<fmt:formatDate value="${qtjfbxsq.bxrq}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="报销日期" disabled>
						</div>
					</div>
				</div>
                 <div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                               <textarea id="txt_bz" class="form-control" readonly name="bz" >${qtjfbxsq.bz}</textarea>
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
		window.location.href = "${ctx}/webView/wsbx/cxtj/qtbxcy_list.jsp";
	});
});

</script>
</body>
</html>