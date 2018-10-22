<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>差旅报销</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
	border-bottom-width: 1px!important;
}
table{
	border-collapse:collapse!important;
}
.addBtn, .addBtn2, .addBtn3{
	text-align:center;
    width: 20px;
    height: 20px;
    background-color: #F3F9FF;
    border-radius: 4px;
    border: 1px solid #cccccb;
    display: inline-block;
    position: relative;
    margin-left:12px;
    margin-top:2px;
}
.addBtn:after, .addBtn2:after, .addBtn3:after{
    content: "+";
    color:#9c9c9c!important;
    color:#337ab7!important;
    position: relative;
    cursor: pointer;
}
.deleteBtn{
	text-align:center;
    width: 20px;
    height: 20px;
    background-color: #F3F9FF;
    border-radius: 4px;
    border: 1px solid #cccccb;
    display: inline-block;
    position: relative;
    margin-left:12px;
    margin-top:2px;
}
.text-green{
	color:green!important;
}
.deleteBtn:after{
    content: "\2014";
    color:#9c9c9c!important;
    color:#337ab7!important;
    position: relative;
    cursor: pointer;
}
.add{
	text-align:center;
    width: 20px;
    height: 20x;
    background-color: #F3F9FF;
    border-radius: 4px;
    border: 1px solid #cccccb;
    display: inline-block;
    position: relative;
    margin-top:2px;
}
.add:after{
   content: "+";
   color:#9c9c9c!important;
   color:#337ab7!important;
   position: relative;
   cursor: pointer;
}
.delete{
text-align:center;
   width: 20px;
   height: 20px;
   background-color: #F3F9FF;
   border-radius: 4px;
   border: 1px solid #cccccb;
   display: inline-block;
   position: relative;
}
.delete:after{
   content: "\2014";
   color:#9c9c9c!important;
   color:#337ab7!important;
   position: relative;
   cursor: pointer;
}
.btn_td{
	width:14mm!important;
	height:6mm!important;
}
.selWindow{
	width:280px!important;
}

.number,.sz{
	text-align:right;
}
.bm_input{
	width:400px!important;
	border:none;
}
#tbody input{
	border:none;
	width:100%;
}
th{
	text-align:center!important;
}
.noBlock{
	margin-bottom: 3px;
   	position: absolute;
   	z-index: 999;
	display: block;
	padding-left: 17%;
	color: red;
}
.border{
	border:1px solid #a94442!important;
	background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
}
.btn_add_{
	text-align:center;
}
.radiodiv{
   border: 1px solid #ccc;
   border-top-right-radius:4px;
   border-bottom-right-radius:4px;
   height: 28px;
   line-height: 28px;
   padding-left: 6px;
} 
.integer{
	text-align: right;
}
tr{
	background-color: white !important;
}
tbody td{
	padding:4px !important;
}
</style>
</head>
<body>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println(path+"@"+basePath);
%>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="ccywguid" id="" value="${ccywguid}">
	<input type="hidden"  id="txt_shzt" value="${clfbxzb.shzt}">
	<input type="hidden"  id="txt_guid" value="${clfbxzb.guid}">
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>第二步,差旅报销业务办理</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
		    <div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-green" id="step_1">选择出差业务&nbsp;></div>
					
<!-- 					<div class="sub-title pull-left text-green"> -->
<!-- 					<div style="width:26px; height:26px; background-color:green; border-radius:13px;"> -->
<!-- 	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span> -->
<!-- 	    			</div> -->
<!-- 					</div> -->
<!-- 					<div class="sub-title pull-left text-green">选择项目&nbsp;></div> -->
					
					<div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
	    			</div>
					</div>
					<div class="sub-title pull-left  text-primary">差旅报销业务办理&nbsp;></div>
					
					<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
	    			</div>
					</div>
					<div class="sub-title pull-left">结算方式设置&nbsp;</div>
					
<!-- 					<div class="sub-title pull-left text-primary"> -->
<!-- 					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;"> -->
<!-- 	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">4</span> -->
<!-- 	    			</div> -->
<!-- 					</div> -->
<!-- 					<div class="sub-title pull-left">保存</div> -->
        </div>
    </div>
	<div class="box">
		<div class="box-panel1">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>差旅报销
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
			   		<button type="button" class="btn btn-default" id="btn_tg"><i class="fa icon-tg"></i>通过</button>
				    <button type="button" class="btn btn-default" id="btn_th"><i class="fa icon-th"></i>退回</button>
				    <button type="button" class="btn btn-default" id="btn_back">返回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单据编号</span>
                            <input type="text" id="txt_djbh" class="form-control input-radius" readonly name="djbh" value="系统自动生成">
                            <input type="hidden" name="guid" id="guid" value="${clfbxzb.guid}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>出差类型</span>
							<select id="drp_cclx" class="form-control input-radius select2" name="cclx"> 
								<c:forEach var="item" items="${cclxList}">
	                        		<option value="${item.DM}" <c:if test="${clfbxzb.cclx== item.DM}">selected</c:if>>${item.MC}</option>
	                        	</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申请人</span>
							<input type="text" id="txt_sqr" readonly class="form-control input-radius" name="sqr" value="${clfbxzb.sqr}"/>
							
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>出差人数</span>
							<input type="text" id="txt_ccrs" class="form-control input-radius sz" name="ccrs" readonly value="${clfbxzb.ccrs==null?1:clfbxzb.ccrs}"  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>出差天数</span>
							<input type="text" id="txt_ccts" class="form-control input-radius sz" name="ccts" value="${clfbxzb.ccts}" 
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon"><span class="required">*</span>经费类型</span> -->
<!-- 							<select id="drp_jflx" class="form-control input-radius select2" name="jflx">  -->
<%-- 								<c:forEach var="item" items="${jflxList}"> --%>
<%-- 	                        		<option value="${item.DM}" <c:if test="${clfbxzb.jflx== item.DM}">selected</c:if>>${item.MC}</option> --%>
<%-- 	                        	</c:forEach> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>附件总张数（张）</span>
							<input type="text" id="txt_fjzzs"  class="form-control input-radius sz" name="fjzzs" value="${clfbxzb.fjzzs}" readonly
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
				</div>
				<div class="row">
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">项目名称</span> -->
<%-- 							<input type="text" id="txt_xxmc" readonly class="form-control input-radius" name="xxmc" value="${clfbxzb.xmmc}"/> --%>
<!-- 							<input type="hidden" id="txt_xmguid" readonly class="form-control input-radius" name="xmguid" -->
<%-- 								 value="<c:if test="${param.xmguid == null }" >${clfbxzb.xmguid}</c:if> --%>
<%-- 								 		<c:if test="${param.xmguid != null }">${param.xmguid}</c:if>" /> --%>
<%-- 										value="${param.Xmguid }"/> --%>
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>是否科研类报销</span>
							<div class="radiodiv">&nbsp;&nbsp;&nbsp;
                               <input type="radio" name="sfkylbx"  id="ck" value="1" <c:if test="${clfbxzb.sfkylbx=='1' }">checked</c:if>>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                   <input type="radio" name="sfkylbx"  value="0" <c:if test="${clfbxzb.sfkylbx=='0'}">checked</c:if>>否
<%-- 			                   <input type="hidden" name="sfkylbx" <c:if test="${jflxs=='01'}" >value="0"</c:if> <c:if test="${jflxs!='01'}" >value="1"</c:if> /> --%>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>报销总金额（元）</span>
							<input type="text" id="txt_bxzje" class="form-control input-radius sz" name="bxzje" value="${clfbxzb.bxzje}" readonly
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
				</div>
<!-- 				<div class="row"> -->
<!-- 				<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon"><span class="required">*</span>附件总张数（张）</span> -->
<%-- 							<input type="text" id="txt_fjzzs"  class="form-control input-radius sz" name="fjzzs" value="${clfbxzb.fjzzs}" readonly --%>
<!-- 							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>出差事由</span>
							<div id="editor" class="border-bottom: 1px solid #ccc;">
								<textarea style="width:100%;height:60px;text-indent: 2em;" id="txt_ccsy" name="ccsy" id="ccsy" data-bv-field="ccsy" class="form-control input-radius">${clfbxzb.ccsy}</textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="box-panel">
					<div class="box-header clearfix">
						<div class="sub-title pull-left text-primary">
							<i class="fa icon-xiangxi"></i>上传附件
						</div>
						<button type="button" class="btn btn-default" style="margin-left:10px;" id="scanToServer">扫描上传</button>
					</div>
					<div class="container-fluid box-content">
						<div class="row">
							<div class="col-md-12">
								<input id="imageFile" name="path" type="file" multiple class="file-loading"/>
								<div id="errorBlock" class="help-block"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<form id="form4" action="">
	<div class="box" id="operate">
		<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>发票信息
	            	</div>
	            	<div class="actions">
	            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	            	</div>
	        </div>
        	<hr class="hr-normal">
				<table id="mydatatables" class="table table-striped table-bordered" >
				    <tbody id="tbody">
					<c:if test="${fn:length(fpList)==0}">
						<tr>
							<td style="text-align:center;vertical-align:middle;"/><span>发票号</span></td>
							<td><input type="text" id="fpha_1" class="input-radius sz" name="fpha" value="" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphb_1" class="input-radius sz" name="fphb" value="" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphc_1" class="input-radius sz" name="fphc" value="" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphd_1" class="input-radius sz" name="fphd" value="" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphe_1" class="input-radius sz" name="fphe" value="" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
						</tr>
					</c:if>
			    	<c:forEach items="${fpList}" var="fpMap" varStatus="i">
						<tr>
							<td style="text-align:center;vertical-align:middle;"/><span>发票号</span></td>
							<td><input type="text" id="fpha_1" class="input-radius " name="fpha" value="${fpMap.fph1}" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphb_1" class="input-radius " name="fphb" value="${fpMap.fph2}" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphc_1" class="input-radius " name="fphc" value="${fpMap.fph3}" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphd_1" class="input-radius " name="fphd" value="${fpMap.fph4}" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphe_1" class="input-radius " name="fphe" value="${fpMap.fph5}" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
						</tr>
					</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
</form>
<form id="form3" action="">
	<div class="box" id="operate">
		<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目经费支出信息
	            	</div>
	            	<div class="actions">
	            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	            	</div>
	        </div>
        	<hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="width:50%!important;">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">序号</th>
				            <th style="width:300px;"><span class="required" style="color:red">*</span>项目名称</th>
				            <th style="width:200px;"><span class="required" style="color:red">*</span>剩余金额(元)</th>
				            <th style="width:120px;"><span class="required" style="color:red">*</span>本次报销金额</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<c:forEach items="${xmlist}" var="list" varStatus="i">
				    		<c:if test="${list.JFBH!=null and list.guid!=null}">
			    			<tr class="tr_${i.index+1}">
							<td class="btn_td" style="line-height: 22px;">${i.index+1}</td>
							<td>
							<div class="input-group">
							<input type="text" id="dxt_xmmc${i.index+1}" class=" input-radius window null" readonly name="xmmc" value="${list.xmmc}">
							<input type="hidden" id="dxt_xmid${i.index+1}"   name="xmguid" value="${list.JFBH}">
							<input type="hidden" id=""   name="jsbxzje" value="">
							<input type="hidden" id=""   name="jyzjehj" value="">
							<input type="hidden"  name="zbguid" value="${param.zbguid}">
							<span class="input-group-btn">
				    			<button type="button" id="btn_xmmc1"  class="btn btn-link btn-custom"></button>
			    			</span>
							</div>
							</td>
							<td>
							<input type="text" id="dxt_syje${i.index+1}" class=" null window input-radius number" readonly="readonly" name="syje" value="${list.ye}"
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
							<td>
							<input type="text" id="dxt_bcbxje${i.index+1}" class=" null window input-radius number" name="bcbxje" value="${list.bcbxje}"
									  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
				    		</c:if>
						</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form>
<form id="myform2" class="form-horizontal" action="" method="post">
	<div class="box" id="operate" >
		<div class="box-panel">	
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>明细信息项
            	</div>
	        </div>
			<hr class="hr-normal">
			<div class="container-fluid box-content" style="padding-bottom:10px; width: 1177px; margin-left: 0px;">
				<table style="width:100%">
				<c:forEach items="${mxList}" var="mxMap" varStatus="i">
					<tr>
						<td>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>人员类型</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<select id="rylx_${i.count}" class="form-control input-radius2" name="rylx">
												<option value="">未选择</option>
				                        		<option value="教师" <c:if test="${mxMap.rylx eq '教师'}">selected</c:if>>教师</option>
				                        		<option value="学生" <c:if test="${mxMap.rylx eq '学生'}">selected</c:if>>学生</option>
				                        		<option value="校外人员" <c:if test="${mxMap.rylx eq '校外人员'}">selected</c:if>>校外人员</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>姓名</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="xm_${i.count}" class="form-control input-radius" name="xm" value="${mxMap.xm}"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>报销级别</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<select id="bxjb_${i.count}" class="form-control input-radius2" name="bxjb">
												<option value="">未选择</option>
				                        		<option value="其他" <c:if test="${mxMap.bxjb eq '其他'}">selected</c:if>>其他</option>
				                        		<option value="厅局级" <c:if test="${mxMap.bxjb eq '厅局级'}">selected</c:if>>厅局级</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">其他费用（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="qtfy_${i.count}" name="qtfy" class="number money null form-control" 
												value="${mxMap.qtfy}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">出发地点</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="cfdd_${i.count}" class="form-control input-radius " name="cfdd" value="${mxMap.cfdd}"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>附件张数（张）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="fjzs_${i.count}" name="fjzs" class="integer null form-control" 
												value="${mxMap.fjzs}"  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>开始时间</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="kssj_${i.count}" name="kssj" class="form-control input-radius datetime" value="${mxMap.kssj}"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>结束时间</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="jssj_${i.count}" class="form-control input-radius datetime" name="jssj" value="${mxMap.jssj}"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
			                    	<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>目的地省份</span>
										<select class="form-control input-radius2" name="provinceid" id="selprovinceid_${i.count}">
			                            	<option value="">未选择</option>
			                                <c:forEach var="item" items="${ProvicesList}">
			                           			<option value="${item.provinceid}" <c:if test="${mxMap.province eq item.province}">selected</c:if>>${item.province}</option>
			                            	</c:forEach>
			                            </select>
			                        </div>
								</div>
								<div class="col-md-3">
			                    	<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>目的地市区</span>
										<select class="form-control input-radius2" name="cityid" id="selcityid_${i.count}">
											<c:if  test="${not empty mxMap.cityid}"><option value="${mxMap.cityid}" selected>${mxMap.city}</option></c:if>
			                            	<option value="">未选择</option>
			                            </select>
			                        </div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">会议费用（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="hyfy_${i.count}" name="hyfy" class="number money null form-control" 
												value="${mxMap.hyfy}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">培训费用（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="pxfy_${i.count}" name="pxfy" class="number money null form-control" 
												value="${mxMap.pxfy}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">飞机票（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="fjp_${i.count}" name="fjp" class="number money null form-control" 
												value="${mxMap.fjp}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">火车票（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="hcp_${i.count}" name="hcp" class="number money null form-control" 
												value="${mxMap.hcp}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">出租车票（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="czcp_${i.count}" name="czcp" class="number money null form-control" 
												value="${mxMap.czcp}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">汽车票（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="qcp_${i.count}" name="qcp" class="number money null form-control" 
												value="${mxMap.qcp}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">市内交通补助费（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="snjtbzf_${i.count}" name="snjtbzf" class="number money null form-control" 
												value="${mxMap.snjtbzf}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">住宿费金额（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="zsfje_${i.count}" name="zsfje" class="number money null form-control" 
												value="${mxMap.zsfje}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">教师生活补助金额（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="jsshbzje_${i.count}" name="jsshbzje" class="integer null form-control" 
												value="${mxMap.jsshbzje}"  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">学生生活补助金额（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="xsshbzje_${i.count}" name="xsshbzje" class="integer null form-control" 
												value="${mxMap.xsshbzje}"  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">教师补助标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="jsbzbz_${i.count}" name="jsbzbz" class="number null form-control bzbz" readonly
												value="${mxMap.jsbzbz}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">市内交通补助标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="snjtbzbz_${i.count}" name="snjtbzbz" class="number null form-control bzbz" readonly
												value="${mxMap.snjtbzbz}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">住宿费标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="zsfbz_${i.count}" name="zsfbz" class="number null form-control bzbz" readonly
												value="${mxMap.zsfbzbz}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">合计金额</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="hjje_${i.count}" name="hjje" class="number null form-control " readonly
												value="${mxMap.hjje}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
							</div>
							<hr class="hr-normal" style="margin-top:15px">
						</td>
					</tr>
				</c:forEach>
				</table>
			</div>
		</div>
	</div>
</form>
<!-- 冲销借款 -->
<div class="box" id="cxjk">
		<div class="box-panel">		
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>冲销借款
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
        <hr class="hr-normal">
		<div class="container-fluid box-content">
			<table id="mydatatables" class="table table-striped table-bordered">
	        	<thead id="thead">
			        <tr>
			        	<th style="width:5%;">序号</th>
			            <th style="width:20%;">借款单号</th>
			            <th style="width:20%;">借款人</th>
			            <th style="width:20%;">所在部门</th>
			            <th style="width:20%;">借款金额(元)</th>
			            <th style="width:15%;">冲借款金额(元)</th>
			        </tr>
				</thead>
			    <tbody id="tb_cxjk">
			    	<c:forEach items="${cxList}" var="cjkList" varStatus="cjk">
			    		<tr class="tr_${cjk.index+1}">
						<td class="btn_td" style="text-align:center;">${cjk.index+1}</td>
						<td>
						<input type="text" name="djbh" class="bk input-radius" readonly value="${cjkList.djbh}">
						</td>
						<td>
						<input type="text" id="txt_jkrxm${cjk.index+1}" class="bk input-radius" readonly value="${cjkList.jkrxm}">
						</td>
						<td>
						<input type="text" id="txt_szdw${cjk.index+1}" class="bk input-radius" readonly value="${cjkList.dwmc}">
						</td>
						<td>
						<input type="text" id="txt_jkje${cjk.index+1}" name="jkje" class="bk input-radius number" readonly value="${cjkList.jkje}">
						</td>
						<td>
						<input type="text" id="txt_cjkje${cjk.index+1}" name="cjkje" class="bk input-radius number" readonly value="${cjkList.cjkje}">
						</td>
					</tr>
			    	</c:forEach>
			    </tbody>
			</table>
		</div>
	</div>
</div>

<!-- 对公支付 -->
<div class="box" id="dgzf">
		<div class="box-panel">		
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>对公支付
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
        <hr class="hr-normal">
		<div class="container-fluid box-content">
			<table id="mydatatables" class="table table-striped table-bordered">
	        	<thead id="thead">
			        <tr>
			        	<th style="width:5%;">序号</th>
			            <th style="width:20%;">对方单位</th>
			            <th style="width:20%;">对方地区</th>
			            <th style="width:20%;">对方银行</th>
			            <th style="width:20%;">对方账号</th>
			            <th style="width:15%;">金额(元)</th>
			        </tr>
				</thead>
			    <tbody id="tb_xnzz">
			    	<c:forEach items="${dgList}" var="dgList" varStatus="dg">
			    		<tr class="tr_${dg.index+1}">
						<td class="btn_td" style="text-align:center;">${dg.index+1}</td>
						<td>
							<input type="text" id="txt_dwmc${dg.index+1}" readonly class="bk input-radius"  value="${dgList.dwmc}">
						</td>
						<td>
						<input type="text" id="txt_dfdq${dg.index+1}" name="dfdq" class="bk input-radius" readonly value="${dgList.dfdq}">
						</td>
						<td>
						<input type="text" class="bk input-radius" readonly value="${dgList.yhname}">
						</td>
						<td>
						<input type="text" id="txt_dfzh${dg.index+1}" name="dfzh" readonly class="bk input-radius"  value="${dgList.dfzh}">
						</td>
						<td>
						<input type="text" id="txt_je${dg.index+1}" name="je" class="bk input-radius number"  value="${dgList.je}">
						</td>
					</tr>
			    	</c:forEach>
					
			    </tbody>
			</table>
		</div>
	</div>
</div>

<!-- 对私支付 -->
<div class="box" id="dszf">
		<div class="box-panel">		
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>对私支付
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
        <hr class="hr-normal">
		<div class="container-fluid box-content">
			<table id="mydatatables" class="table table-striped table-bordered">
	        	<thead id="thead">
			        <tr>
			        	<th style="width:5%;">序号</th>
			            <th style="width:20%;">人员选择</th>
			            <th style="width:20%;">人员姓名</th>
			            <th style="width:20%;">银行名称</th>
			            <th style="width:20%;">卡号</th>
			            <th style="width:15%;">金额（元）</th>
			        </tr>
				</thead>
			    <tbody id="tb_sszk">
			    
			    	<c:forEach items="${dsList}" var="dsList" varStatus="ds">
			    		<tr class="tr_${ds.index+1}">
						<td class="btn_td bk" style="text-align:center;">${ds.index+1}</td>
						<td>
							<input type="text" readonly class=" bk"  <c:if test="${dsList.ryxz=='0'}">value="个人"</c:if> 
								<c:if test="${dsList.ryxz=='1'}">value="项目负责人"</c:if>
								<c:if test="${dsList.ryxz=='2'}">value="其他人"</c:if>
							/>
			    		</td>
			    		<td>
<!-- 				    		<div class="input-group"> -->
			    			<input type="text" readonly class="bk input-radius" value="${dsList.ryxm}">
<!-- 				    		</div> -->
						</td>
						<td>
						<input type="text" readonly class="bk input-radius" value="${dsList.klx}">
						</td>
						<td>
						<input type="text" readonly class="bk input-radius" value="${dsList.dfzh}">
						</td>
						<td>
						<input type="text" readonly class="bk input-radius number" value="${dsList.je}" >
						</td>
					</tr>
			    	</c:forEach>
			    </tbody>
			</table>
		</div>
	</div>
</div>

<!-- 公务卡 -->
<div class="box" id="gwk">
		<div class="box-panel">		
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>公务卡信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
        <hr class="hr-normal">
		<div class="container-fluid box-content">
			<table id="mydatatables" class="table table-striped table-bordered">
	        	<thead id="thead">
			        <tr>
			        	<th style="width:5%;">序号</th>
			            <th style="width:20%;">人员姓名</th>
			            <th style="width:20%;">刷卡日期</th>		          
			            <th style="width:20%;">卡号</th>
			            <th style="width:15%;">报销金额(元)</th>
			            <th style="width:15%;">刷卡金额(元)</th>
			            <th style="width:20%;visibility: hidden;"></th>
			            
			        </tr>
				</thead>
			    <tbody id="tb_gwk">
			    	<c:forEach items="${gwkList}" var="gwkList" varStatus="gwk">
			    		<tr class="tr_${gwk.index+1}">
						<td class="btn_td" style="text-align:center;">${gwk.index+1}</td>
						<td>
		    			<input type="text" readonly class="bk input-radius" value="${gwkList.ryxm}">
						</td>
						<td>
						<input type="text" readonly class="bk input-radius date" readonly value="${gwkList.skrq}">
						</td>
						
						<td>
						<input type="text" readonly class="bk input-radius"  value="${gwkList.kh}">
						</td>
						<td>
						<input type="text" readonly class="bk input-radius number"  value="${gwkList.skje}">
						</td>
						<td>
						<input type="text" readonly class="bk input-radius number"  value="${gwkList.sjskje}">
						</td>
					</tr>
			    	</c:forEach>
			    </tbody>
			</table>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	kz();
	//图片信息开始
	var fjView = [<%=request.getAttribute("fjView")%>];
	var fjConfig = [<%=request.getAttribute("fjConfig")%>];
	//附件信息
     $("#imageFile").fileinput({
       	fileActionSettings:{
    		showUpload:false//隐藏上传按钮
    	},
    	initialPreview:fjView,
    	initialPreviewAsData:true,
    	initialPreviewConfig:fjConfig,
    	uploadUrl: '${pageContext.request.contextPath}/file/fileUpload',//上传请求路径
        maxFilePreviewSize: 10240,//最大上传文件的大小
        showUpload:false,//是否显示上传按钮
        initialPreviewShowDelete:false,
        showBrowse:false,
        showCaption:false,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"zcxx","id":"${zbxx.guid}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
    });
   //返回按钮
 	$("#btn_back").click(function(e){
 		location.href="${ctx}/wsbx/ccbx/goCheckListPage";
 	});
 	//通过
 	$("#btn_tg").click(function(e){
 		var shzt = $("[id='txt_shzt']").val();
 		var money = $("[name='bxzje']").val();
 		var flag = false;
 		var guid = $("[id='txt_guid']").val();
 		if((shzt==="01"||shzt==="04")&&money<=5000){
 			$.ajax({
 				url:"${ctx}/wsbx/process/selectWho",
 				data:"guid="+guid+"&type=clfbx",
 				type:"post",
 				async:false,
 				dataType:"json",
 				success:function(data){
 					if(data){
 						flag = true;
 					}
 				}
 			});
 		}
 		select_commonWin("${ctx}/wsbx/ccbx/check?type=first&ccywguid=${ccywguid}&guid="+"${guid}"+"&procinstid="+"${procinstid}"+"&flag="+flag,"通过页面","500","300");
 		//location.href="${ctx}/wsbx/ccbx/goCheckListPage";
 	});	
 	//退回
 	$("#btn_th").click(function(e){
 		select_commonWin("${ctx}/wsbx/ccbx/check?type=second&ccywguid=${ccywguid}&guid="+"${guid}"+"&procinstid="+"${procinstid}","退回页面","500","300");
 		//location.href="${ctx}/wsbx/ccbx/goCheckListPage";
 	});	
	
	$('input').attr("readonly",true);
	$('textarea').attr("readonly",true);
	$('select').attr("disabled","disabled");
	
	
});
function kz(){
	
	//冲销
	if("${zbMap.SFCJK}"!="1"){
		$("#cxjk").css("display","none");
	}
	//对私
	if("${zbMap.SFDSZF}"!="1"){
		$("#dszf").css("display","none");
	}
	//公务卡
	if("${zbMap.SFGWK}"!="1"){
		$("#gwk").css("display","none");
	}
	//对公
	if("${zbMap.SFDGZF}"!="1"){
		$("#dgzf").css("display","none");
	}
}
//SmscLoad("${pageContext.request.contextPath}",{"id":"${zbxx.guid}","djlx":"000000","fold":"zcxx","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
</script>

</html>