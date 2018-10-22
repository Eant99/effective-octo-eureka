<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>差旅报销 凭证退回</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
	border-bottom-width: 1px!important;
}
table{
	border-collapse:collapse!important;
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
	<div class="box">
		<div class="box-panel1">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>差旅报销
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
            	   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			  		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单据编号</span>
                            <input type="text" id="txt_djbh" class="form-control input-radius" readonly name="djbh" value="${clfbxzb.djbh}">
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
							<input type="text" id="txt_ccts" class="form-control input-radius sz"  name="ccts" value="${clfbxzb.ccts}" readonly
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
							<div class="radiodiv" style="background:#eeeeee">&nbsp;&nbsp;&nbsp;
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
							<input type="text" id="txt_bxzje" class="form-control input-radius sz" name="bxzje" value="${clfbxzb.bxzje}" readonly
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
<!-- 							<td class="btn_td_"><div class="addBtn2"></div></td> -->
							<td style="text-align:center;vertical-align:middle;"><span>发票号</span></td>
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
<!-- 							<td class="btn_td_"> -->
<%-- 								<div <c:if test="${fn:length(fpList)!=i.count}">class="deleteBtn"</c:if> --%>
<%-- 									 <c:if test="${fn:length(fpList)==i.count}">class="addBtn2"</c:if>> --%>
<!-- 								</div> -->
<!-- 							</td> -->
							<td style="text-align:center;vertical-align:middle;"><span>发票号</span></td>
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
							<input type="text" id="dxt_bcbxje${i.index+1}" class=" null window input-radius number" readonly="readonly" name="bcbxje" value="${list.bcbxje}"
									  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
				    		</c:if>
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
										<span class="input-group-addon">教师生活补助金额（元）（元）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="jsshbzje_1" name="jsshbzje" class="number money null form-control" 
												value=""  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">学生生活补助金额（元）(元)</span>
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
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">市内交通补助标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="snjtbzbz_1" name="snjtbzbz" class="number null form-control bzbz" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">住宿费标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="zsfbz_1" name="zsfbz" class="number null form-control bzbz" 
												value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
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
											<input type="hidden" name="guid" value="${mxMap.guid}">	
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
			                           			<option value="${item.provinceid}" <c:if test="${mxMap.province eq item.province}">selected</c:if>>${item.province}</option>
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
												value="${mxMap.jsbzbz}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">市内交通补助标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="snjtbzbz_${i.count}" name="snjtbzbz" class="number null form-control bzbz" 
												value="${mxMap.snjtbzbz}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">住宿费标准（元/天）</span>
										<div id="editor" class="border-bottom: 1px solid #ccc;">
											<input type="text" id="zsfbz_${i.count}" name="zsfbz" class="number null form-control bzbz" 
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
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<script type="text/javascript">
$(function(){
	//返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/bxpzth/goclfbx?mkbh=${param.mkbh}";
	});

	//是编辑还是查看
	$("select, input[type='radio']").attr("disabled","disabled");
	$("input[name^='fph']").attr("readonly","readonly");
	$("#btnxm_1").hide();
	$("#myform2").find("tbody").find("input").attr("readonly","readonly");
	$("#myform2").find("tbody").find("input[name='fjzs']").removeAttr("readonly");
	if("${operateType}"=="L"){
		$("#btn_save").hide();
		$(".btn-custom").hide();
		$("input").attr("readonly","readonly");
		$("textarea").attr("readonly","readonly");
	}
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
    });
 	var validate = $('#myform').bootstrapValidator({fields:{
		ccsy:{validators:{notEmpty: {message: '出差事由不能为空'}}},
   	 	fjzzs:{validators:{notEmpty:{message: '附件总张数不能为空'},
   	 		regexp: {regexp: /^[1-9]\d*$/,message: '附件总张必须为正整数'}}},
   	 	}
   });
	$("#btn_save").click(function(){
		doSave1(validate,"myform","${ctx}/bxpzth/doUpdateCLF",function(){});
	});
	function doSave1(_validate, _formId, _url, _success, _fail){
		if(_validate){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){					
					Mxfjzs();
				},
			});
		}//if
	}
	function Mxfjzs(){
		var json = $('#myform2').serializeObject("fjzs","guid");  //json对象		
		var jsonStr = JSON.stringify(json);  //json字符串
		$.ajax({
   			url:"${ctx}/bxpzth/doUpdateCLFMX",
   			type:"post",
   			data:"jsonStr="+jsonStr,
   			dataType:"json",
   			success:function(data){
   				if(data.success){
	   				$("#imageFile").fileinput("upload");//上传附件信息	
			 		location.href="${ctx}/bxpzth/goclfbx";
			 		alert(data.msg);
   				}
   			}
		}); 
	}
	
  SmscLoad("${pageContext.request.contextPath}",{"id":"${clfbxzb.guid}","djlx":"000000","fold":"zcxx","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
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
});
</script>
</html>