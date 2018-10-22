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
.sz{
text-align: left ! important;
}
.addBtn3{
	top: -15px;
	}
.addBtn, .addBtn3{
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
.addBtn2, .add{
	text-align:center;
    width: 20px;
    height: 20x;
    background-color: #F3F9FF;
    border-radius: 4px;
    border: 1px solid #cccccb;
    display: inline-block;
    position: relative;
    margin-top:2px;
    margin-left: 12px;
}
.addBtn2:after, .add:after{
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
	<input type="hidden" name="xmguids" id="" value="">
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
            		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
					</button>
			   		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
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
							<input type="text" id="txt_ccts" class="form-control input-radius sz"  name="ccts" value="${clfbxzb.ccts}" 
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>附件总张数（张）</span>
							<input type="text" id="txt_fjzzs"  class="form-control input-radius sz" name="fjzzs" value="${clfbxzb.fjzzs}" readonly
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>是否科研类报销</span>
							<div class="radiodiv">&nbsp;&nbsp;&nbsp;
								<c:choose>
								<c:when test="${clfbxzb.sfkylbx != '0' && clfbxzb.sfkylbx != '1'}">
								   <input type="radio" name="sfkylbx"  id="ck" value="1" >是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                   <input type="radio" name="sfkylbx"  value="0" checked >否
								</c:when>
								<c:otherwise>
	                               <input type="radio" name="sfkylbx"  id="ck" value="1" <c:if test="${clfbxzb.sfkylbx=='1' }">checked</c:if>>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                   <input type="radio" name="sfkylbx"  value="0" <c:if test="${clfbxzb.sfkylbx=='0'}">checked</c:if>>否
								</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>报销总金额（元）</span>
							<input type="text" id="txt_bxzje" class="form-control input-radius text-right" name="bxzje" value="${clfbxzb.bxzje}" readonly
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
				</div>
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
		    		<c:if test="${operate eq 'C'}">
						<tr>
							<td class="btn_td_"><div class="addBtn2"></div></td>
							<td style="text-align:center;vertical-align:middle;"/><span>发票号</span></td>
							<td><input type="text" id="fpha_1" class="input-radius " name="fpha" value="" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphb_1" class="input-radius " name="fphb" value="" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphc_1" class="input-radius " name="fphc" value="" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphd_1" class="input-radius " name="fphd" value="" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphe_1" class="input-radius " name="fphe" value="" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
						</tr>
					</c:if>
			    	<c:forEach items="${fpList}" var="fpMap" varStatus="i">
						<tr>
							<td class="btn_td_">
								<div <c:if test="${fn:length(fpList)!=i.count}">class="deleteBtn"</c:if>
									 <c:if test="${fn:length(fpList)==i.count}">class="addBtn2"</c:if>>
								</div>
							</td>
							<td style="text-align:center;vertical-align:middle;"/><span>发票号</span></td>
							<td><input type="text" id="fpha_${i.index+1}" class="input-radius " name="fpha" value="${fpMap.fph1}" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphb_${i.index+1}" class="input-radius " name="fphb" value="${fpMap.fph2}" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphc_${i.index+1}" class="input-radius " name="fphc" value="${fpMap.fph3}" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphd_${i.index+1}" class="input-radius " name="fphd" value="${fpMap.fph4}" 
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/></td>
							<td><input type="text" id="fphe_${i.index+1}" class="input-radius " name="fphe" value="${fpMap.fph5}" 
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
				            <th style="width:200px;"><span class="required" style="color:red">*</span>可用金额(元)</th>
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
							<input type="hidden"  name="zbguid" value="${clfbxzb.guid}">
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
							<input type="text" id="dxt_kyje${i.index+1}" class=" null window input-radius number" readonly="readonly" name="kyje"  value="<fmt:formatNumber value="${kyje+clfbxzb.bxzje}" pattern="0.00" type="number"/>"
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
				<c:if test="${fn:length(mxList)==0}">
					<tr>
						<td class="btn_td_"><div class="addBtn3"></div></td>
						<td>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>人员类型</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<select id="rylx_1" class="form-control input-radius2" name="rylx">
												<option value="">未选择</option>
				                        		<option value="教师" >教师</option>
				                        		<option value="学生" >学生</option>
				                        		<option value="校外人员" >校外人员</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>姓名</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="xm_1" class="form-control input-radius" name="xm" value="" placeholder="请选择人员"/>
										</div>
										<span class="input-group-btn"><button type="button" id="btnxm_1" name="btn_xm" class="btn btn-link" style="display:none;">选择</button></span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>报销级别</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<select id="bxjb_1" class="form-control input-radius2" name="bxjb">
												<option value="">未选择</option>
				                        		<option value="其他" >其他</option>
				                        		<option value="厅局级" >厅局级</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">其他费用（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="qtfy_1" name="qtfy" class="number money null form-control" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">出发地点</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="cfdd_1" class="form-control input-radius " name="cfdd" value=""/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>附件张数（张）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="fjzs_1" name="fjzs" class="integer null form-control" 
												value="${mxMap.fjzs}"  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>开始时间</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="kssj_1" name="kssj" class="form-control input-radius datetime"  onchange="ckts();" value=""/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>结束时间</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="jssj_1" class="form-control input-radius datetime"   onchange="ckts();"name="jssj" value=""/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
			                    	<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>目的地省份</span>
										<select class="form-control input-radius2" name="provinceid" id="selprovinceid_1">
			                            	<option value="">未选择</option>
			                                <c:forEach var="item" items="${ProvicesList}">
			                           			<option value="${item.provinceid}" >${item.province}</option>
			                            	</c:forEach>
			                            </select>
			                        </div>
								</div>
								<div class="col-md-3">
			                    	<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>目的地市区</span>
										<select class="form-control input-radius2" name="cityid" id="selcityid_1">
			                            	<option value="">未选择</option>
			                            </select>
			                        </div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">会议费用（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="hyfy_1" name="hyfy" class="number money null form-control" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">培训费用（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="pxfy_1" name="pxfy" class="number money null form-control" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">飞机票（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="fjp_1" name="fjp" class="number money null form-control" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">火车票（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="hcp_1" name="hcp" class="number money null form-control" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">出租车票（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="czcp_1" name="czcp" class="number money null form-control" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">汽车票（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="qcp_1" name="qcp" class="number money null form-control" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">市内交通补助费（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="snjtbzf_1" name="snjtbzf" class="number money null form-control" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">住宿费金额（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="zsfje_1" name="zsfje" class="number money null form-control" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">教师生活补助金额（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="jsshbzje_1" name="jsshbzje" class="number money null form-control" 
												value=""  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">学生生活补助金额（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="xsshbzje_1" name="xsshbzje" class="number money null form-control" 
												value=""  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">教师补助标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="jsbzbz_1" name="jsbzbz" class="number null form-control bzbz" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">市内交通补助标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="snjtbzbz_1" name="snjtbzbz" class="number null form-control bzbz" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">住宿费标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="zsfbz_1" name="zsfbz" class="number null form-control bzbz" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">合计金额</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="hjje_1" name="hjje" class="number null form-control " readonly
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
							</div>
							<hr class="hr-normal" style="margin-top:15px">
						</td>
					</tr>
				</c:if>
				<c:forEach items="${mxList}" var="mxMap" varStatus="i">
					<tr>
						<td class="btn_td_">
							<div <c:if test="${fn:length(mxList)!=i.count}">class="deleteBtn"</c:if>
								 <c:if test="${fn:length(mxList)==i.count}">class="addBtn3"</c:if> >
							</div>
						</td>
						<td>
							<div class="row">
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>人员类型</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<select id="rylx_${i.count}" class="form-control input-radius2" name="rylx">
												<option value="">未选择</option>
				                        		<option value="教师" selected >教师</option>
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
											<input type="text" id="xm_${i.count}" class="form-control input-radius" name="xm" value="${mxMap.xm}" placeholder="请选择人员"/>
<%-- 											<input type="text" id="xm_${i.count}" class="form-control input-radius" name="xm" value="${mxMap.xm}" placeholder="请选择人员"/> --%>
										</div>
										<span class="input-group-btn"><button type="button" id="btnxm_${i.count}" name="btn_xm" class="btn btn-link">选择</button></span>
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
											<input type="text" id="qtfy_1" name="qtfy" class="number money null form-control" 
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
											<input type="text" id="kssj_${i.count}" name="kssj" class="form-control input-radius datetime" onblur="ckts();" value="${mxMap.kssj}"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>结束时间</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="jssj_${i.count}" class="form-control input-radius datetime"  onblur="ckts();" name="jssj" value="${mxMap.jssj}"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
			                    	<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>目的地省份</span>
										<select class="form-control input-radius2" name="provinceid" cityid="${mxMap.cityid}" id="selprovinceid_${i.count}">
			                            	<option value="">未选择</option>
			                                <c:forEach var="item" items="${ProvicesList}">
			                           			<option value="${item.provinceid}" <c:if test="${mxMap.provinceid eq item.provinceid}">selected</c:if>>${item.province}</option>
			                            	</c:forEach>
			                            </select>
			                        </div>
								</div>
								<div class="col-md-3">
			                    	<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>目的地市区</span>
										<select class="form-control input-radius2" name="cityid" id="selcityid_${i.count}">
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
											<input type="text" id="jsshbzje_${i.count}" name="jsshbzje" class="number money null form-control" 
												value="${mxMap.jsshbzje}"  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">学生生活补助金额（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="xsshbzje_${i.count}" name="xsshbzje" class="number money null form-control" 
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
											<input type="text" id="jsbzbz_${i.count}" name="jsbzbz" class="number null form-control bzbz" 
												value="${mxMap.jsbzbz}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">市内交通补助标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="snjtbzbz_${i.count}" name="snjtbzbz" class="number null form-control bzbz" 
												value="${mxMap.snjtbzbz}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">住宿费标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="zsfbz_${i.count}" name="zsfbz" class="number null form-control bzbz" 
												value="${mxMap.zsfbzbz}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly/>
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
		<div class="container-fluid box-content">
			<div class="row">
			<div class="pull-center" style="text-align:center;">
				<c:if test="${!(operate eq 'U') }">
		   			<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
		   		</c:if>
		   		<button type="button" class="btn btn-default" id="btn_next" style="background:#00acec;color: white;">下一步</button>
				</div>
			</div>
		</div>
	</div>
</form>		
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<script type="text/javascript">
fz();
function fz(){
	var xmid = $("#dxt_xmid1").val();
	$("[name='xmguids']").val(xmid);
}
var tag = true;
var look="${look}";
//计算出差天数
function ckts(){
	var kssj = $("[name='kssj']").val();
	var jssj = $("[name='jssj']").val();
	var qssjd=new Date(kssj);
	var zzsjd=new Date(jssj);
	var hms = zzsjd.getTime()-qssjd.getTime();//计算相差的毫秒数
	var count=Math.floor(hms / (1000*60*60*24) );//计算相差的天数
	if(count<0){
		$("#txt_ccts").val('');	
	}else if(kssj==null||jssj==null||kssj==''||jssj==''){
		$("#txt_ccts").val('');	
	}else {
		var count0 = hms%(24*3600*1000);   //计算天数后剩余的毫秒数
		var hours=Math.floor(count0 / (3600*1000)); //计算出小时数   count天 hours小时
		$("#txt_ccts").val(count);	
	}
}
function computerKyje(){
	//判断 是否超出 项目 剩余金额+
	debugger;
	var xmguid = $("#dxt_xmid1").val();
// 	var bxje = "${zbxx.bxzje }";
    var bxje = "${clfbxzb.bxzje}"; 
// 	var bxje = $("#dxt_kyje1").val();
	$.ajax({
		type:"post",
		data:"xmguid="+xmguid,
		url:"${ctx}/wsbx/rcbx/getXmkyje_2",
		async:false,
		dataType:"json",
		success:function(val){
			if(bxje!=''){
				kyje = parseFloat(val.kyje) + parseFloat(bxje);		
			}else{
				kyje = parseFloat(val.kyje);
			}
		}
	});
	return kyje;
}
$(function(){
	computer();
	$("[name='provinceid']").trigger("change");
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
        initialPreviewShowDelete:true,
        showBrowse:true,
        showCaption:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"zcxx","id":"${clfbxzb.guid}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
    }).on("filebatchpreupload",function(){
    	load = loading(2);
    }).on("filebatchuploadcomplete",function(){
    	close(load);
    	var  src = "${ctx}/wsbx/ccbx/jsfs?sfbj=${sfbj}&money=${money}&mkbh=${param.mkbh}&zbguid=${clfbxzb.guid}&xmguid="+"${clfbxzb.xmguid}"+"&xmmc="+"${clfbxzb.xmmc}"+"&bxzje="+$("#txt_bxzje").val()+"&djbh="+$("#txt_djbh").val()+"&look="+"${look}&ccywguid=${ccywguid}&kyje="+computerKyje();
    	location.href = src;
    });
	
     SmscLoad("${pageContext.request.contextPath}",{"id":"${clfbxzb.guid}","djlx":"000000","fold":"zcxx","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
   //返回按钮
 	$("#btn_back").click(function(e){
 		if("${look}"!=""){
 			location.href="${ctx}/kylbx/gowdbxListPage";	
 		}else{
 			location.href="${ctx}/wsbx/ccbx/goCcbxListPage?mkbh=${param.mkbh}";
 		}
 		
 	});
	var validate = $('#myform').bootstrapValidator({fields:{
		ccsy:{validators:{notEmpty: {message: '出差事由不能为空'}}},
		ccrs:{validators:{notEmpty: {message: '出差人数不能为空'}}},
		ccts:{validators:{notEmpty: {message: '出差天数不能为空'}}},
   	 	bxzje:{validators:{notEmpty: {message: '报销总金额不能为空'}}},
   		skr:{validators:{notEmpty: {message: '收款人不能为空'}}},
   		sfkylbx:{validators:{notEmpty: {message: '不能为空'}}},
   	 	fjzzs:{validators:{notEmpty: {message: '附件总张数不能为空'},regexp: {regexp: /^[1-9]\d*$/,message: '附件总张必须为正整数'}}},
   	    skr:{validators:{notEmpty: {message: '收款人不能为空'}}},
   	}
   });
	 //序列化表单，用于批量插入
	 $.fn.serializeObject1 = function(start,end){
		 	
	    	var f = {"list":[]};
		    var a = this.serializeArray();
		    var o = {};
		    $.each(a, function() {
		    	if (this.name == start) {
		        	o = {};
		        	o[this.name] = this.value;
		        } else if(this.name == end){
		        	o[this.name] = this.value;
		        	f["list"].push(o);
		        }else{
		        	o[this.name] = this.value;
		        }
	    });
	    return f;
	}; 
	//弹窗
    	$(document).on("click","[class$=btn_imp]",function(){
    	var xzxmguid = $(this).parent("div").parent("td").parent("tr").find("[id^=xzxmguid]").attr("id");
    	var kssj=$(this).parent("div").parent("td").parent("tr").find("[id^=kssj]").attr("id");
		var jssj=$(this).parent("div").parent("td").parent("tr").find("[id^=jssj]").attr("id");
		var cfdd=$(this).parent("div").parent("td").parent("tr").find("[id^=cfdd]").attr("id");
		var mddd=$(this).parent("div").parent("td").parent("tr").find("[id^=mddd]").attr("id");
		select_commonWin("${ctx}/webView/wsbx/ccbx/clfmxList.jsp?kssj="+kssj+"&jssj="+jssj+"&cfdd="+cfdd+"&mddd="+mddd+"&xzxmguid="+xzxmguid+"&zbguid="+"${clfbxzb.guid}","差旅报销明细","920","630");
    });
	$("#btn_bmmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_bmmc","部门信息","920","630");
    });
	$("#btn_xmbh").click(function(){
		select_commonWin("${ctx}/wsbx/rcbx/window?type=xm&controlId=txt_xmbh","信息","920","630");
    });
	$("#btn_after").click(function(){
		location.href = back_url;
	});
	//验证发票号
	function yzfph(){
		var fph = $("input[name^=fph]");
		var flag = false;
		var fphs = [];
		fph.each(function(){
			if($(this).val()!=""){
				flag = true;
				fphs.push($(this).val());
			}
		});
		if(!chachong(fphs)){
			alert("发票号重复");
			return false;
		}else if(!ajaxchachong(fphs)){
			return false;			
 		}else{
 			return true;
 		}
		
	}
	//页面查重函数  
	function chachong(arr){  
	    var res=[];  
	    var json={};  
	    for(var i=0;i<arr.length;i++){  
	       if(!json[arr[i]]){  
	          res.push(arr[i]);  
	          json[arr[i]]=1;  
	       }  
	    }  
	    return res.length==arr.length;  
	}  
	//数据库查重
	function ajaxchachong(arr){ 
		console.log(arr);
		var arrs = arr.join(",");
		var flag = true;
		$.ajax({
   			url:"${ctx}/wsbx/ccbx/checkFph",
   			type:"post",
   			data:"arr="+arrs+"&zbid=${clfbxzb.guid}",
   			async:false,
   			success:function(data){
   				if(data!="0"){
   					alert("发票号“"+data+"”重复");
   					flag = false;
   				}
   			}
		}); 
		return flag;		
	}  
	//验证报销标准
	function yzbxbz(){
		var flag = true;
		var snjtbzf = $("input[name=snjtbzf]");//市内交通补助费
		var zsfje = $("input[name=zsfje]");//住宿费金额
		var bcbxje = $("input[name=bcbxje]");//本次报销金额
		var ccts = $("input[name=ccts]").val();//出差天数 
		var rylx = $("[name=rylx]").val();//人员类型 
		console.log(rylx);
		if(rylx=='教师'){
			snjtbzf.each(function(){
				var snjtbzbz = $(this).parents("tr").find("input[name=snjtbzbz]").val();//市内交通补助标准
				if(parseFloat($(this).val())>parseFloat(snjtbzbz)*parseInt(ccts)){
					alert("市内交通补助费超出标准");
					flag = false;
				}
			});
			console.log(zsfje);
			zsfje.each(function(){
				var zsfbz = $(this).parents("tr").find("input[name=zsfbz]").val();//住宿费标准
				var zsfje_ = parseFloat($(this).val());
				var zsfbz_ = parseFloat(zsfbz);
				var ccts_ = parseFloat(ccts);

				if(zsfje_ > zsfbz_*ccts_){
					alert("住宿费金额超出标准");
					flag = false;
				}
			});
		}

		var money=0;
		bcbxje.each(function() {
			money= money+parseFloat($(this).val());
		});
		if(money<0 || money==0){
			alert("本次报销金额之和必须大于0");
			flag = false;
		}
		var bxzje=$("[name='bxzje']").val();//报销总金额
		if(money!=parseFloat(bxzje)){
			alert("本次报销金额之和与报销总金额不相等 ");
			flag = false;
		}
		return flag;
	}
	//为空验证
	function wkyz(){
		flag = true;
		var rylx = $("[name=rylx]");
		var fjzs = $("[name=fjzs]");
		var xm = $("[name=xm]");
		var bxjb = $("[name=bxjb]");
		var provinceid = $("[name=provinceid]");
		var cityid = $("[name=cityid]");
		var kssj = $("[name=kssj]");
		var jssj = $("[name=jssj]");
		rylx.each(function(){
			var rylx_ = $(this).val();//人员类型
			if(rylx_==''||rylx_== 'undefind'||rylx_== null){
				alert("人员类型不能为空");
				flag = false;
			}
		});
		fjzs.each(function(){
			var fjzs_ = $(this).val();//附件张数
			if(fjzs_==''||fjzs_== 'undefind'||fjzs_== null){
				alert("附件张数不能为空");
				flag = false;
			}
		});
		xm.each(function(){
			var xm_ = $(this).val();//姓名
			if(xm_==''||xm_== 'undefind'||xm_== null){
				alert("姓名不能为空");
				flag = false;
			}
		});
		bxjb.each(function(){
			var bxjb_ = $(this).val();//报销级别
			if(bxjb_==''||bxjb_== 'undefind'||bxjb_== null){
				alert("报销级别不能为空");
				flag = false;
			}
		});
		provinceid.each(function(){
			var provinceid_ = $(this).val();//目的地省份
			if(provinceid_==''||provinceid_== 'undefind'||provinceid_== null){
				alert("目的地省份不能为空");
				flag = false;
			}
		});
		cityid.each(function(){
			var cityid_ = $(this).val();//目的地城市
			if(cityid_==''||cityid_== 'undefind'||cityid_== null){
				alert("目的地城市不能为空");
				flag = false;
			}
		});
		kssj.each(function(){
			var kssj_ = $(this).val();//开始时间
			if(kssj_==''||kssj_== 'undefind'||kssj_== null){
				alert("开始时间不能为空");
				flag = false;
			}
		});
		jssj.each(function(){
			var jssj_ = $(this).val();//结束时间
			if(jssj_==''||jssj_== 'undefind'||jssj_== null){
				alert("结束时间不能为空");
				flag = false;
			}
		});
		return flag;
	}
	$("#btn_next").click(function(){
		var type = "";
		if("${operate}"=="U"||"${param.operateType}"=="U"){
			type = "U";
		}
		var flag = yzfph()&&yzbxbz()&&wkyz();//验证发票号&&验证报销标准&&为空验证
		if(flag){
			doSave1(validate,"myform","${ctx}/wsbx/ccbx/doSaveZb?type="+type,"next",function(val){
			});
		}
		
	});
	function doSave1(_validate, _formId, _url,type, _success, _fail){
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(!tag){
			valid = false;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){
					var json = $('#form4').serializeObject("fpha","fphe");  //json对象	
	   				var jsonStr = JSON.stringify(json);  //json字符串
// 	   				console.log(jsonStr);
	   				$.ajax({
			   			url:"${ctx}/wsbx/ccbx/doSaveFph?zbguid=${clfbxzb.guid}",
			   			type:"post",
			   			data:"json="+jsonStr,
			   			success:function(data){
							next(type);
			   			}
					}); 
				},
				error:function(val){
									
				}	
			});
		}
	}
	function next(type){
		var json = $('#myform2').serializeObject1("rylx","hjje");  //json对象		
		var jsonStr = JSON.stringify(json);  //json字符串
		var xmguid = $("[name='xmguid']").val();
		$.ajax({
   			url:"${ctx}/wsbx/ccbx/doSaveMx?zbguid=${clfbxzb.guid}",
   			type:"post",
   			data:"json="+jsonStr,
   			success:function(data){
   				var json = $('#form3').serializeObject("xmmc","bcbxje");  //json对象	
   				var jsonStr = JSON.stringify(json);  //json字符串
   				$.ajax({
		   			url:"${ctx}/wsbx/ccbx/doSaveXmMx?ccywguid=${ccywguid}&zbguid=${clfbxzb.guid}&xmguid="+xmguid,
		   			type:"post",
		   			data:"json="+jsonStr,
		   			success:function(data){
		   				next2(type);  	   					
		   			}
				}); 
   			}
		}); 
	}
	
	function next2(type){
		var kyje = computerKyje();
		console.log("--------------------------------=="+kyje);
		var json = $('#myform3').serializeObject1("zbguid","szdw");  //json对象				
		var jsonStr = JSON.stringify(json);  //json字符串
		$.ajax({
	   			url:"${ctx}/wsbx/ccbx/doSaveRy?zbguid="+$("#zbguid").val(),
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
	   				var frame = $(".file-live-thumbs").find(".file-preview-frame");
		   			if(type=='next'){
						if(frame.length > 0){
							$("#imageFile").fileinput("upload");
						}else{
							var  src = "${ctx}/wsbx/ccbx/jsfs?sfbj=${sfbj}&money=${money}&mkbh=${param.mkbh}&zbguid=${clfbxzb.guid}&xmguid="+"${clfbxzb.xmguid}"+"&xmmc="+"${clfbxzb.xmmc}"+"&bxzje="+$("#txt_bxzje").val()+"&djbh="+$("#txt_djbh").val()+"&look="+"${look}&ccywguid=${ccywguid}&kyje="+kyje;
							location.href = src;
						}
		   			}else if(type=='after'){
		   				if(frame.length > 0){
							$("#imageFile").fileinput("upload");
						}else{
							var  src = "${ctx}/webView/wsbx/ccbx/xzxm.jsp?mkbh=${param.mkbh}&xmguid="+$("#txt_xmguid").val()+"&zbguid=${clfbxzb.guid}&look="+"${look}&ccywguid=${ccywguid}";
							location.href = src;
						}
		   			}	
	   			}
		}); 
	}
});
$(function(){
	//新增按钮
	var txrynum = 2;
	$(document).on("click","[class$=add]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$parentTr.find(":input").val("");
		$parentTr.find(".add").removeClass("add").addClass("delete");
		$parentTr.find("[name=rybh]").val("");
		$parentTr.find("[name=ryxm]").val("").attr({"readonly":false});
		$parentTr.find("[name=szdw]").val("").attr({"readonly":false});
		$parentTr.find("[id^=btn_txry]").attr({"id":"btn_txry"+txrynum});
		txrynum++;
		
		$(this).parents("table").append($parentTr);
		var rybh=$(this).parent("td").parent("tr").find("[id^=txt_rybh]").attr("id");
		var szbm=$(this).parent("td").parent("tr").find("[id^=txt_szbm]").attr("id");		
		select_commonWin("${ctx}/window/rypage2?controlId="+rybh+"&szbmm="+szbm,"人员信息","920","630");
	});
	//删除按钮
	$(document).on("click","[class$=delete]",function(){
		$(this).parents("tr").remove();
		computerPeople();
	});
});
//明细js操作
$(function(){
	
	//新增按钮
	var num = 2;
	$(document).on("click","[class$=addBtn]",function(){
	
		var $parentTr = $(this).parents("tr").clone();
		$parentTr.find("input").removeClass("border");
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		$parentTr.attr("id","tr_"+num);
		$parentTr.find("[id^=xzxmguid]").attr("id","xzxmguid_"+num);
		$parentTr.find("[id^=kssj]").attr("id","kssj_"+num);
		$parentTr.find("[id^=jssj]").attr("id","jssj_"+num);
		$parentTr.find("[id^=cfdd]").attr("id","cfdd_"+num);
		$parentTr.find("[id^=mddd]").attr("id","mddd_"+num);
		$parentTr.find("[id^=fjje]").attr("id","fjje_"+num).attr("jb","ys_"+num);
		$parentTr.find("[id^=hcje]").attr("id","hcje_"+num).attr("jb","ys_"+num);
		$parentTr.find("[id^=czcje]").attr("id","czcje_"+num).attr("jb","ys_"+num);
		$parentTr.find("[id^=qcje]").attr("id","qcje_"+num).attr("jb","ys_"+num);
		$parentTr.find("[id^=qtfy]").attr("id","qtfy_"+num).attr("jb","ys_"+num);
		$parentTr.find("[id^=jsshbzje]").attr("id","jsshbzje_"+num).attr("jb","ys_"+num);
		$parentTr.find("[id^=xsshbzje]").attr("id","xsshbzje_"+num).attr("jb","ys_"+num);
		$parentTr.find("[id^=zsfje]").attr("id","zsfje_"+num).attr("jb","ys_"+num);
		$parentTr.find("[id^=zbguid]").val($("#zbguid").val());
		$parentTr.find("[id^=fjs]").attr("id","fjs_"+num);
		$parentTr.find("[id^=hyfy]").attr("jb","ys_"+num);
		$parentTr.find("[id^=pxfy]").attr("jb","ys_"+num);
		num++;
		$(this).parents("tr").after($parentTr);
		
	});
	//新增按钮
	var fphnum = 100;
	$(document).on("click","[class$=addBtn2]",function(){
	
		var $parentTr = $(this).parents("tr").clone();
		$parentTr.find("input").removeClass("border");
		$(this).removeClass("addBtn2");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		var input = $parentTr.find(":input");
		input.each(function() {
			$(this).attr("id",$(this).attr("id").split("_")[0]+"_"+fphnum);
		});
		fphnum++;
		$(this).parents("tr").after($parentTr);
		
	});
	//新增按钮
	var mxxxnum = $(".addBtn3").parents("tr").find("[name='rylx']").attr("id").split("_")[1]+1;//加号的所在tr内的元素的id编号最大，取其id后的数字+1；
	$(document).on("click","[class$=addBtn3]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$parentTr.find("input").removeClass("border");
		$(this).removeClass("addBtn3");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		var input = $parentTr.find(":input");
		input.each(function() {
			$(this).attr("id",$(this).attr("id").split("_")[0]+"_"+mxxxnum);
		});
		mxxxnum++;
		$(this).parents("tr").after($parentTr);
		
	});
	//删除按钮
	$(document).on("click","[class$=deleteBtn]",function(){
		$(this).parents("tr").remove();
		//删除后重新计算金额
		computer();
		pjzs();
	});
	//明细信息项  姓名弹窗
	$(document).on("click","[name='btn_xm']",function(){
		var id = $(this).parents("tr").find("[name=xm]").attr("id");
		var type = $(this).parents("tr").find("[name=rylx]").val();
		if(type=="教师"){
			type="T";
		}else if(type=="学生"){
			type="S";
		}
		select_commonWin("/CWYTH/window/allrypage?type="+type+"&controlId="+id,"人员信息","920","630");
	});
});
//输入报销总金额金额  --改
$(document).on("keyup","[class*=money],[name=jsshbzje],[name=xsshbzje]",function(){
	var parent = $(this).parents("tr");
	jscomputer(parent);
});
//计算 报销标准
$(document).on("blur","[name=kssj],[name=jssj]",function(){
	var parent = $(this).parents("tr");
	bxbz(parent);
});
$(document).on("change","[name=rylx],[name=bxjb],[name=cityid]",function(){
	var parent = $(this).parents("tr");
	bxbz(parent);
});
$(document).on("change","[name=rylx]",function(){
	var rylx = $(this);
	var id = $(this).attr("id");   
	var num =id.substr(id.lastIndexOf("_"));
	console.log(num);
	$("#xm"+num).val("");
	var xm = $(this).parents("tr").find("input[name='xm']");
	xm.unbind();//解除原有绑定事件
	if(rylx.val()=="教师"){
		xm.bindChange("${ctx}/suggest/getXx","JS");//明细信息项  姓名联想输入
		$(this).parents("tr").find("button[name='btn_xm']").show();
	}else if(rylx.val()=="学生"){
		xm.bindChange("${ctx}/suggest/getXx","XS");//明细信息项  姓名联想输入
		$(this).parents("tr").find("button[name='btn_xm']").show();
	}else if(rylx.val()==""){
		$(this).parents("tr").find("button[name='btn_xm']").hide();//隐藏选择按钮
	}else if(rylx.val()=="校外人员"){
		$(this).parents("tr").find("button[name='btn_xm']").hide();//隐藏选择按钮
	}
});
function bxbz(parent){
	var rylx=parent.find("select[name='rylx']").val();
	var bxjb=parent.find("select[name='bxjb']").val();
	var provinceid=parent.find("select[name=provinceid]").val();
	var cityid=parent.find("select[name=cityid]").val();
	var kssj=parent.find("input[name=kssj]").val();
	var jssj=parent.find("input[name=jssj]").val();
	if(rylx=="教师"&&bxjb!=""&&kssj!=""&&jssj!=""&&(provinceid!=""||cityid!="")){
		$.ajax({
			url:"${pageContext.request.contextPath}/wsbx/ccbx/getBxbz",
			data:{
				rylx: rylx,
				bxjb: bxjb,
				provinceid: provinceid,
				cityid: cityid,
				kssj: kssj,
				jssj: jssj
				},
			dataType:"json",
			type:"post",
			async:false,
			success:function(val){
				 var json = eval(val);
				 parent.find("input[name=jsbzbz]").val(json.hsf);
				 parent.find("input[name=snjtbzbz]").val(json.jtf);
				 parent.find("input[name=zsfbz]").val(json.zsf);
			},
			error:function(){
				alert("系统繁忙，请稍候重试！");
			}
		});
	}
}
function checkGuid(ggid){
	$.each($.find("[id^=xzxmguid]"),function(){
		var xzguid=$(this).val();
		if(xzguid==ggid){
			alert("所选信息已存在！");
			return;
		}else{
			$.ajax({
				url:"${ctx}/webView/wsbx/ccbx/clfmxList.jsp?flg=success",
	   			type:"post",

			})
			return "success";
		}
	});
};
//计算票据总张数
$(document).on("keyup","[id^=fjzs]",function(){
	pjzs();
});
function pjzs(){
	var pjzs = 0;
	$.each($("[id^=fjzs]"),function(i,v){
		var zs = $(this).val();
		if(zs!=""&&zs!=0&&!isNaN(zs)){
			pjzs = parseInt(pjzs)+parseInt(zs);
		}
	});
	if(pjzs<0){
		pjzs = 0;
	}
	$("[name='fjzzs']").val(pjzs);
}
//扫描上传
$("#scanToServer").click(function(){
	scanToServer();
});
function scanToServer(){
	select_commonWin("${pageContext.request.contextPath}/ywsm/goScanPage?mkbh=${param.mkbh}&sfbc=1&zbid=${clfbxzb.guid}&basePath=<%=basePath%>&fold=zcxx","", "920", "530");
}
$(document).on("focus","[class*=border]",function(){
	$(this).removeClass("border");
});
$("#btn_ywsm").click(function(){
	zysx();
});
function zysx(){
	//业务说明
	select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
}
function computerPeople(){
	var text = $("[id^=trr_]").find("[name='rybh']");
	var count = 0;
	if(text.length!=0){
		$.each(text,function(i,v){
			var val = $(this).val();
			if(val!=""){
				count++;
			}
		});
	}
	if(!isNaN(count)){
		$("[name='ccrs']").val(count);
	}
}
$(document).on("focus",".bm_input",function(){
	computerPeople();
});
$(document).ready(function(){
	$(".tttt tr").last().after($("[data-luser]").attr("class","add").parents("tr"));
});
$(document).on("blur","[name=xm]",function(){
	var rygh = $(this).val();
	rygh = rygh.split(")")[0];
	rygh = rygh.substr(1,rygh.le);
	var bxjb = $(this).parents("tr").find("[name=bxjb]");
	$.ajax({
		url:"${pageContext.request.contextPath}/wsbx/ccbx/getBxjbByRygh?rygh="+rygh,
		type:"post",
		async:false,
		success:function(data){
			console.log(data);
			bxjb.val(data);
			bxjb.find("option[val='"+data+"']").attr("selected",true);
			bxjb.change();
		},
		error:function(data){
			console.log(data);
			alert("系统繁忙，请稍候重试！");
		}
	});
});
$(document).on("change","[id^=selprovinceid]",function(){
	var $parentTr = $(this).parents("tr");
	var cityid = $(this).attr("cityid");
	$.ajax({
		url:"${pageContext.request.contextPath}/provinces/getCitiesByProvince?provinceid="+$(this).val(),
		dataType:"json",
		type:"post",
		async:false,
		success:function(val){
			$parentTr.find("[id^=selcityid]").find("option").remove();
			var cities = '<option value="" >请选择...</option>';
			for(var i = 0; i < val.length; i++){
				var item = val[i];
				if(item.CITYID == cityid){
					cities += '<option value="'+item.CITYID+'" selected>'+item.CITY+'</option>';
				}else{
					cities += '<option value="'+item.CITYID+'" >'+item.CITY+'</option>';
				}
			}
			$parentTr.find("[id^=selcityid]").append(cities);
		},
		error:function(){
			alert("系统繁忙，请稍候重试！");
		}
	});
});
$(document).on("keydown","[id^=selcityid]",function(){
	var $parentTr = $(this).parents("tr");
	$.ajax({
		url:"${pageContext.request.contextPath}/provinces/getCitiesByProvince?provinceid="+$parentTr.find("[id^=selprovinceid]").val(),
		dataType:"json",
		type:"post",
		async:false,
		success:function(val){
			$parentTr.find("[id^=selcityid]").find("option").remove();
			var cities = '<option value="" >请选择...</option>';
			for(var i = 0; i < val.length; i++){
				var item = val[i];
				cities += '<option value="'+item.CITYID+'">'+item.CITY+'</option>';
			}
			$parentTr.find("[id^=selcityid]").append(cities);
		},
		error:function(){
			alert("系统繁忙，请稍候重试！");
		}
	});
});
//添加同行人员
function addTxry($checkbox){
	var txry = {};
	$.each($("[name='rybh']"),function(){
		var rybh = $(this).val();
		txry[rybh] = "1";
	});
	var errMsg = "<br/>";
	//添加的人员
	var addNum = 0;
	$.each($checkbox,function(){
		var rybh = "("+$(this).find(".keyId").attr("rybh")+")"+$(this).find(".xm").text();
		if(txry[rybh] != null){
			errMsg += rybh+"<br/>";
			return;
		}
		var $tr = $(".tttt tr").last().clone();
		$tr.find("[name='rybh']").val(rybh);
		$tr.find("[name='szdw']").val($(this).find(".dwmc").text());
		$tr.find(".add").attr("class","delete");
		$(".tttt tr").last().before($tr);
		addNum ++;
	});
	
	//如果添加的人员为0，直接退出
	if(addNum == 0){
		return;
	}else if(addNum < $checkbox.length){
		alert(errMsg+"已经存在！");
	}
	computerPeople()
}

var back_url;
$(document).ready(function(){
	if("${param.sfsqsp}" == 1){
		back_url = "${ctx}/webView/wsbx/ccbx/xzxm.jsp?operate=${operate}&mkbh=${param.mkbh}&xmguid="+$("#txt_xmguid").val()+"&zbguid="+$("#guid").val()+"&look="+"${look}&ccywguid=${ccywguid}";
	}else{
		back_url = "${ctx}/webView/wsbx/ccbx/selectXm.jsp?operate=${operate}&mkbh=${param.mkbh}&xmguid="+$("#txt_xmguid").val()+"&zbguid="+$("#guid").val()+"&look="+"${look}&ccywguid=${ccywguid}";
		$("#step_1").html("选择项目&nbsp;>");
	}
});
//输入本次报销金额
$(document).on("keyup","[id^=dxt_bcbxje]",function(){
	var bcbxje = $(this).val();	
	var bckyje = parseFloat($("#dxt_kyje1").val());
	//判断 是否超出 项目 剩余金额+
	if(parseFloat(bcbxje) > parseFloat(bckyje)){
		$("#dxt_bcbxje1").val("");
		alert("本次报销金额已超出目前项目的可用金额");
	}
});
// 计算总金额    改
function jscomputer(parent){
	var je = 0;
	var xjje = 0;
	var hjje = 0;
	//合计本明细小计金额
	$.each(parent.find(".money"),function(){
		var $this = $(this);
		je = $this.val();
		if(isNaN(je)||je==""){
			je = 0;
		}
		xjje = parseFloat(xjje) + parseFloat(je);
	});
	if(xjje<0){
		xjje = 0.00;
	}
	parent.find("[name=hjje]").val(xjje.toFixed(2));
	
	//合计总金额
	$.each($("[name=hjje]"),function(){
		var $this = $(this);
		je = $this.val();
		if(isNaN(je)||je==""){
			je = 0;
		}
		hjje = parseFloat(hjje) + parseFloat(je);
	});
	if(hjje<0){
		hjje = 0.00;
	}
	$("[name='bxzje']").val(hjje.toFixed(2));
}
//计算总金额    改
function computer(){
	var je = 0;
	var hjje = 0;
	var fjzzs = 0;
	//合计本明细小计金额
	$.each($(".money"),function(){
		var $this = $(this);
		je = $this.val();
		if(isNaN(je)||je==""){
			je = 0;
		}
		hjje = parseFloat(hjje) + parseFloat(je);
	});
	if(hjje<0){
		hjje = 0.00;
	}
	$("[name='bxzje']").val(hjje.toFixed(2));
	//合计总附件数
	$.each($("[name='fjzs']"),function(){
		var $this = $(this);
		je = $this.val();
		if(isNaN(je)||je==""){
			je = 0;
		}
		fjzzs = Number(fjzzs) + Number(je);
	});
	if(hjje<0){
		hjje = 0;
	}
	$("[name='fjzzs']").val(fjzzs);
}
//计算剩余总金额    --改
function sycomputer(){
	var je = 0;
	var hjje = 0;
	//合计金额
	$.each($("[id^=dxt_syje]"),function(){
		var $this = $(this);
		je = $this.val();
		if(isNaN(je)||je==""){
			je = 0;
		}
		hjje = parseFloat(hjje) + parseFloat(je);
	});
	if(hjje<0){
		hjje = 0.00;
	}
	$("[name='jyzjehj']").val(hjje.toFixed(2));
	
}

</script>

</html>