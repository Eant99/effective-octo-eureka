<%@page import="com.googosoft.util.Validate"%>
<%@page import="java.util.*"%>
<%@page import="com.googosoft.controller.kjhs.pzxx.PzlrController"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证录入</title>
<%@include file="/static/include/public-manager-css.inc"%>
<link href="${ctx }/webView/kjhs/pzxx/pzlr/pzlr.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
	.col-md-4{margin-bottom:8px;}
</style>
</head>
<%
PzlrController pz = new PzlrController();
%>
<body>
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="dqrxm" id="dqrxm" value="${ryxm}">
	<div class='page-header'>
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>凭证信息录入</span>
		</div>
        <div class="pull-right">
			 <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-saves"></i>保存</button>
			 <button type="button" class="btn btn-default" id="btn_saveMx"><i class="fa icon-saves"></i>银行明细</button>
			 <button type="button" class="btn btn-default" id="btn_exp"><i class="fa icon-saves"></i>导出明细</button>
			 <button type="button" class="btn btn-default" id="btn_add"><i class="fa icon-saves"></i>增行</button>
			 <button type="button" class="btn btn-default" id="btn_xzmb"><i class="fa icon-saves"></i>选择模板</button>
			 <button type="button" class="btn btn-default" id="btn_copyByFl"><i class="fa icon-saves"></i>复制分录</button>
			 <button type="button" class="btn btn-default" id="btn_saveToMb"><i class="fa icon-saves"></i>保存模板</button>
			 <button type="button" class="btn btn-default" id="btn_copyByPz" pznd="${sessionScope.pznd}" kjqj="${sessionScope.kjqj}";><i class="fa icon-saves"></i>复制凭证</button>
			 <button type="button" class="btn btn-default" id="btn_bxpzsc"><i class="fa icon-saves"></i>报销凭证生成</button>
			 <button type="button" class="btn btn-default" id="btn_xzpzsc"><i class="fa icon-saves"></i>薪资凭证生成</button>
			 <button type="button" class="btn btn-default" id="btn_jdhh"><i class="fa icon-saves"></i>借贷互换</button>
			 <c:if test="${('Y' eq sfyxscpz) and (true eq sfkscpz)}">
			 	<button type="button" class="btn btn-primary" id="btn_deletePz">删除凭证</button>
			 </c:if>
			 <button type="button" class="btn btn-default" id="btn_first" data-type="first"><i class="fa icon-write"></i>第一张</button>
			 <button type="button" class="btn btn-default" id="btn_prev" data-type="prev"><i class="fa icon-write"></i>上一张</button>
			 <button type="button" class="btn btn-default" id="btn_next" data-type="next"><i class="fa icon-write"></i>下一张</button>
			 <button type="button" class="btn btn-default" id="btn_last" data-type="last"><i class="fa icon-write"></i>最后张</button>
			 <button type="button" class="btn btn-default" id="btn_upload"><i class="fa icon-saves"></i>查看单据</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel" style="text-align:center;">
			<div style="display:inline-block;position:absolute;left:40px;top: 40px;">
<%-- 				<label style="font-size: 15px;color: green;border: 1px solid #b9b7b7;padding: 5px;border-radius: 5px;">${zbMap.pzzt }</label> --%>
				<img src="${ctx }/static/images/pzzt/ybc.png"/>
			</div>
			<div class="box-header clearfix" style="margin-top:40px;">
            	<div class="sub-title pull-left text-primary" style="width:100%;text-align: center;">
            		<span style="font-size:25px;">记&emsp;账&emsp;凭&emsp;证</span>
            	</div>
        	</div>
			<!-- table start -->
			<div class="container-fluid" id="pzlr_zb">
				<div class='responsive-table'>
					<div class='scrollable-area' style="text-align:center;"> 
				       	<form id="zbForm">
				       	<input type="hidden" id="txt_bxid" name="bxid" value="${zbMap.guid }"/>
				       	<input type="hidden"  name="uploadId" value="${zbMap.guid }"/>
				       	<input type="hidden" id="txt_kjqj" name="kjqj" value="${param.kjqj}"/>
				       	<input type="hidden" id="txt_pznd" name="pznd" value="${param.pznd}"/>
				       
				       <div class="t-round text-right">
				       		<div style="width: 25%;display:inline-block;">
									<label>凭证类型</label>
									<select class="" id="txt_pzz" name="pzz">
										<c:forEach items="${pzlxList }" var="item">
											<option value="${item.pzlxbh }" <c:if test="${item.pzlxbh eq pzz }">selected</c:if>>${item.pzlxmc }</option>
										</c:forEach>
									</select>
							</div> 
				       	</div>
				       	<div class="t-round text-right">
				       		<div style="width: 25%;display:inline-block;">
								<label>凭证编号</label>
								<select class="" id="txt_pzbh" name="pzbh" style="height:22px!important;">
									<c:forEach items="${pzbhList}" var="item" varStatus="status">
										<option value="${item }" <c:if test="${item eq pzbh }">selected</c:if>>${item}</option>
									</c:forEach>
								</select>
							</div> 
				       	</div>
				       	<div class="t-round" style="position:relative;">
				       		
							<div style="width: 25%;display:inline-block;">
								<input type="hidden" name="sszt" value="${sszt }"/>
<!-- 								<label >日期</label> -->
								<input type="text" class="date" id="txt_pzrq" name="pzrq"  style="border-radius:4px;border:1px solid #ccc;width: 90px;text-align: center;" value="<fmt:formatDate value="${zbMap.pzrq }" pattern="yyyy-MM-dd"/>" />
							</div>       	
				       		<div style="display:inline-block;position:absolute;right:0px;">
								<label>单据</label>
								<input type="text" class="int" onkeydown="return disableEnter(event);" style="height: 22px!important;width: 30px;display:inline-block;border-radius:4px;border:1px solid #ccc;" id="txt_fjzs" name="fjzs" value="${zbMap.fjzs }"/>&nbsp;张
							</div>
				       	</div>
				       	</form>
				       	<div id="delmxid" style="display:none">
				      	  <!-- 减掉的mxid --> 
				      	</div>
				       	<form id="mxForm">
						<table class="top_table">
							<thead>	
								<tr>
					   		 		<td class="action"></td>
					   		 		<td class="action"></td>
						            <td><b>序号</b></td>
						            <td><b style="padding:3px;color:red;font-size:10px;">*</b><b>摘要</b></td>
						            <td><b style="padding:3px;color:red;font-size:10px;">*</b><b>科目编号</b></td>
						            <td><b style="padding:3px;color:red;font-size:10px;"></b><b>经济分类</b></td>
						            <td><b style="padding:3px;color:red;font-size:10px;"></b><b>部门编号</b></td>
						            <td><b style="padding:3px;color:red;font-size:10px;"></b><b>项目编号</b></td>
						            <td><b>借方金额（元）</b></td>
						            <td><b>贷方金额（元） </b></td>
					      	  	</tr>	
					      	</thead>  	
						    <tbody id="pzlr_mx">
					    		<tr data-line="0" style="display:none;">
					    			<td class="action">
					    				<div class="btn_delete" style="display:none;"></div>
					   		 		</td>
					   		 		<td class="action">
					    				<div class="btn_up">上移</div>
					   		 		</td>
					   		 		<td class="xh">0</td>
						            <td data-col="1">
						            	<textarea rows="1" cols="" maxlength="100" name="zy" class="zyTextArea required"></textarea>
						            	<input type="hidden" id="txt_mxid" name="mxid" value=""/>
						            </td>
						            <td data-col="2">
						           		<input type="text" class="input_title echo required" id="kmbh" name="kmbh" />
						            </td>
						            <td data-col="3">
						           		<input type="text" class="input_title echo required" id="jjfl" name="jjfl" />
						            </td>
						            <td data-col="4">
						           		<input type="text" class="input_title echo required" id="bmbh" name="bmbh" />
						            </td>
						            <td data-col="5">
						           		<input type="text" class="input_title echo required" id="xmbh" name="xmbh" />
						            </td>
						            <td data-col="6">
						           		<input type="hidden" id="" name="jdfx"/>
						            	<input type="text" class="input_title text-right sign-number required" id="jfje" name="jfje"/>
						            </td>
						            <td data-col="7">
						            	<input type="text" class="input_title text-right sign-number required" id="dfje" name="dfje"/>
						            </td>
						        </tr>
						        <c:forEach items="${mxList }" var="item" varStatus="status">
					    		<tr data-line="${status.count }">
					    			<td class="action">
					    				<c:if test="${status.count > 2 }">
					    					<div class="btn_delete" style="display:none;"></div>
					    				</c:if>
					   		 		</td>
					   		 		<td class="action">
					   		 		    <c:if test="${status.count > 1 }">
					    				<div class="btn_up">上移</div>
					    				</c:if>
					   		 		</td>
					   		 		<td class="xh">${status.count }</td>
						            <td data-col="1">
						            	<textarea rows="1" cols="" maxlength="50" name="zy" class="zyTextArea required">${item.zy }</textarea>
						            	<input type="hidden" id="txt_mxid" name="mxid" value="${item.guid}"/>
						            </td>
						            <td data-col="2">
						           		<input type="text" class="input_title echo required" id="txt_kmbh_${status.count }" name="kmbh" value="${item.kmbh }"/>
						            </td>
						            <td data-col="3">
						           		<input type="text" class="input_title echo required" id="txt_jjfl_${status.count }" name="jjfl" value="${item.jjfl }"/>
						            </td>
						            <td data-col="4">
						           		<input type="text" class="input_title echo required" id="txt_bmbh_${status.count }" name="bmbh" value="${item.bmbh }"/>
						            </td>
						            <td data-col="5">
						           		<input type="text" class="input_title echo required" id="txt_xmbh_${status.count }" name="xmbh" value="${item.xmbh }"/>
						            </td>
						            <td data-col="6">
						            	<input type="hidden" id="" name="jdfx" value="${item.jdfx}"/>
						            	<input type="text" class="input_title text-right sign-number required" id="" name="jfje" value="<fmt:formatNumber value="${item.jfje }" pattern="0.00"/>" />
						            </td>
						            <td data-col="7">
						            	<input type="text" class="input_title text-right sign-number required" id="" name="dfje" value="<fmt:formatNumber value="${item.dfje }" pattern="0.00"/>" />
						            </td>
						        </tr>
						        </c:forEach>
						        <tr class="hj" style="font-size:14px;">
						       		<td class="action">
					   		 		</td>
					   		 		<td class="action">
					   		 		</td>
						        	<td colspan="6" style="padding-left: 50px;">
						        		<label style="color: blue">合计</label>
						        	</td>
						        	<td colspan="1" style="text-align: right;">
						        		<label id="txt_jfzje" style="font-weight: bold;color: blue;"><fmt:formatNumber value="${zbMap.jfzje }" pattern=".00"/></label>
						        	</td>
						        	<td colspan="1" style="text-align: right;">
						        		<label id="txt_dfzje" style="font-weight: bold;color: blue;"><fmt:formatNumber value="${zbMap.dfzje }" pattern=".00"/></label>
						        	</td>
						        </tr>
						    </tbody>
						</table>
						</form>
						<div class="t-round">
							<div style="width: 30%;display:inline-block;">
								<label>记账：</label>
								<label></label>
							</div>       	
							<div style="width: 30%;display:inline-block;">
								<label>复核：</label>
								<label></label>
							</div>       	
							<div style="width: 30%;display:inline-block;">
								<label>制单：</label>
								<label id="zdr">${zbMap.zdr }</label>
							</div>       	
				       	</div>
				    </div>
				</div>
			</div>
			<!-- table end -->
		</div>
		<form id="fzForm">
		<div class="box-panel pzfz_info" style="display:none;" data-pzfz="0">
			<div class="box-header clearfix" style="margin-top:20px;">
            	<div class="sub-title pull-left text-primary"><i class='fa icon-xiangxixinxi'></i>辅助录入，第<label id="pzfz_xh" style="padding: 4px;">0</label>行</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">往来对冲</span>
							<input type="hidden" name="start" value="start" />
							<select class="form-control" disabled id="txt_wldc" name="wldc">
							 	<option value="">-未选择-</option>
<%-- 							 	<c:forEach items="${wldcList }" var="item"> --%>
<%-- 							 		<option value="${item.jsdh }">${item.jsdh }</option> --%>
<%-- 							 	</c:forEach> --%>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">个&emsp;&emsp;人</span>
	                         <input type="text" class="form-control input-radius2" id="txt_zrr" name="zrr" />
	                         <span class="input-group-btn"><button type="button" id="btn-zrr" class="btn btn-link xz">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">往来单位</span>
							 <input type="text" class="form-control input-radius2 bg-white" id="txt_dfdw" name="dfdw" />
							 <span class="input-group-btn"><button type="button" id="btn-dfdw" class="btn btn-link xz">选择</button></span>
						</div>
					</div>
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">结算方式</span>
							 <select class="form-control" id="txt_jsfs" name="jsfs">
							 	<option value="">-未选择-</option>
							 	<c:forEach items="${jsfsList }" var="item">
							 		<option value="${item.dm }">${item.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">结算单号</span>
	                        <input type="text" id="txt_jsdh" placeholder="系统自动生成" class="form-control" disabled readonly name="jsdh" value="" onkeyup="value=value.replace(/\W/,'')">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算来源</span>
							<select class="form-control" id="txt_ysly" name="ysly">
								<option value="">-未选择-</option>
							 	<c:forEach items="${yslyList }" var="item">
							 		<option value="${item.dm }">${item.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
<!-- 				</div> -->
				<!-- <div class="row wyxx">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">开户银行</span>
							<select class="form-control" id="txt_khyh" name="khyh" ">
							 	<option value="">-未选择-</option>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">户名</span>
							<input type="text" class="form-control" id="txt_hm" name="hm" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">账号</span>
							<input type="text" class="form-control" id="txt_zh" name="zh" />
						</div>
					</div>
				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算类型</span>
							<select class="form-control" id="txt_yslx" name="yslx">
								<option value="">-未选择-</option>
							 	<c:forEach items="${yslxList }" var="item">
							 		<option value="${item.dm }">${item.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">借款单号</span>
	                        <input type="text" class="form-control" id="txt_zclx" name="zclx" />
						</div>
					</div>
					<div class="col-md-4 dqsj" style="display:none;">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>到期时间</span>
							<input type="text" class="form-control date" id="txt_dqsj" name="dqsj" disabled="disabled"/>
						</div>
					</div>
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目名称</span>
							<input type="text" class="form-control" id="txt_kmmc" name="kmmc" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">支出经济分类科目</span>
							<input type="text" class="form-control" id="txt_zcjjflkm" name="zcjjflkm" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目余额（元）</span>
							<input type="text" class="form-control text-right" id="txt_kmye" name="kmye" readonly="readonly"/>
						</div>
					</div>
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">部门名称</span>
							<input type="text" class="form-control" id="txt_bmmc" name="bmmc" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
							<input type="text" class="form-control" id="txt_xmmc" name="xmmc" readonly="readonly"/>
						</div>
					</div>
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">收入科目</span> -->
<!-- 							<input type="text" class="form-control" id="txt_srkm" name="srkm" readonly="readonly"/> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">支出科目</span> -->
<!-- 							<input type="text" class="form-control" id="txt_zckm" name="zckm" readonly="readonly"/> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目余额（元）</span>
<!-- 							<input type="text" class="form-control" id="txt_xmye" name="xmye" /> -->
							<input type="text" class="form-control text-right" id="txt_xmye" name="xmye" style="text-align:right;" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">可用金额</span>
							<input type="text" class="form-control" id="txt_kyje" name="kyje" style="text-align:right;" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">负责人</span>
							<input type="text" class="form-control" id="txt_fzr" name="fzr" readonly="readonly"/>
						</div>
					</div>
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目国库信息码</span>
							<input type="text" class="form-control" id="txt_xmgkxxm" name="xmgkxxm" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目类型</span>
							<input type="text" class="form-control" id="txt_xmlx" name="xmlx" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">功能科目</span>
							 <input type="text" disabled class="form-control input-radius2 bg-white" id="txt_gnkm" name="gnkm" />
							 <span class="input-group-btn"><button type="button" id="btn-gnkm" disabled class="btn btn-link xz">选择</button></span>
						</div>
					</div>
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
							<input type="text" class="form-control" id="txt_bz" name="bz" readonly="readonly"/>
<!-- 							<input type="hidden" name="end" value="end" /> -->
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">公务卡号</span>
							<input type="text" class="form-control" id="txt_gwk" name="gwkh" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">刷卡时间</span>
							<input type="text" class="form-control date" id="txt_sksj" name="sksj" value=""/>
							<input type="hidden" name="end" value="end" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<c:forEach items="${mxList }" var="item" varStatus="status">
		<div class="box-panel pzfz_info" style="display:none;" data-pzfz="${status.count }">
			<div class="box-header clearfix" style="margin-top:20px;">
            	<div class="sub-title pull-left text-primary"><i class='fa icon-xiangxixinxi'></i>辅助录入，第<label id="pzfz_xh" style="padding: 4px;">${status.count }</label>行</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">往来对冲</span>
							<input type="hidden" name="start" value="start" />
							<input type="hidden" class="wldc" zrr="${item.zrr}" dfdw="${item.dfdw}" value="${item.wldc}" xs="${empty item.zrr and empty item.dfdw}"/>
							<select class="form-control" id="txt_wldc" wldc="${item.zclx}" name="wldc" <c:if test="${(empty item.zrr and empty item.dfdw) or !empty item.zclx}">disabled</c:if>>
							  <option> ${item.wldc}</option>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">个&emsp;&emsp;人</span>
	                         <input type="text" class="form-control input-radius2" <c:if test="${empty item.zrr}">disabled readonly</c:if> id="txt_zrr_2" name="zrr" value="${item.zrr}" readonly/>
	                         <span class="input-group-btn"><button type="button" id="btn-zrr" <c:if test="${empty item.zrr}">disabled</c:if> class="btn btn-link xz">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">往来单位</span>
							 <input type="text" class="form-control input-radius2" <c:if test="${empty item.dfdw}">disabled readonly</c:if> id="txt_dfdw_2" name="dfdw" value="${item.dfdw}" readonly/>
							 <span class="input-group-btn"><button type="button" id="btn-dfdw" class="btn btn-link xz" <c:if test="${empty item.dfdw}">disabled</c:if>>选择</button></span>
						</div>
					</div>
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">结算方式</span>
							 <select class="form-control jsfs" id="txt_jsfs" name="jsfs">
							 	<option value="">-未选择-</option>
							 	<c:forEach items="${jsfsList }" var="items2">
							 		<option value="${items2.dm}" <c:if test="${items2.dm==item.jsfs}">selected</c:if>>${items2.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">结算单号</span>
	                        <input type="text" id="txt_jsdh" readonly<c:if test="${empty item.jsdh}">disabled</c:if> class="form-control" name="jsdh" value="${item.jsdh }" onkeyup="value=value.replace(/\W/,'')">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算来源</span>
							<select class="form-control" id="txt_ysly" name="ysly">
								<option value="">-未选择-</option>
							 	<c:forEach items="${yslyList }" var="items2">
							 		<option value="${items2.dm }"<c:if test="${items2.mc==item.ysly}">selected</c:if>>${items2.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
<!-- 				</div> -->
				<%-- <div class="row wyxx" hh="${item.jsfs!='0002' and item.jsfs!='0003'}" <c:if test="${item.jsfs!='0002' and item.jsfs!='0003'}">style="display:none;"</c:if>>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">开户银行</span>
							<input type="hidden" class="khyh" zrr="${item.zrr}" dfdw="${item.dfdw}" value="${item.khyh}" xs="${empty item.zrr and empty item.dfdw}"/>
							<select class="form-control" id="txt_khyh" khyh="${item.khyh}" name="khyh" <c:if test="${empty item.zrr and empty item.dfdw}">disabled</c:if>>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">户名</span>
							<input type="text" class="form-control" id="txt_hm" name="hm" value="${empty item.zrr?item.dfdw:item.zrr}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">账号</span>
							<input type="text" class="form-control" id="txt_zh" name="zh" value="${item.zh}"/>
						</div>
					</div>
				</div> --%>
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算类型</span>
							<select class="form-control" id="txt_yslx" name="yslx">
								<option value="">-未选择-</option>
							 	<c:forEach items="${yslxList }" var="items2">
							 		<option value="${items2.dm }" <c:if test="${items2.mc==item.yslx}">selected</c:if>>${items2.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">借款单号</span>
	                         <input type="text" class="form-control" id="txt_zclx" name="zclx" value="${item.zclx }" <c:if test="${!empty item.wldc}">readonly</c:if>/>
							 </select>
						</div>
					</div>
					<div class="col-md-4 dqsj" <c:if test="${item.dqsj == null }">style="display:none;"</c:if>>
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>到期时间</span>
							<input type="text" class="form-control date" id="txt_dqsj" name="dqsj" <c:if test="${item.dqsj == null }">disabled="disabled"</c:if> value="${item.dqsj}"/>
						</div>
					</div>
<!-- 				</div> -->
				<!-- 凭证展示信息start -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目名称</span>
							<input type="text" class="form-control" id="txt_kmmc" name="kmmc" value="${item.kmmc }" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">支出经济分类科目</span>
							<input type="text" class="form-control" id="txt_zcjjflkm" name="zcjjflkm" value="${item.zcjjflkm }" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目余额（元）</span>
							<input type="text" class="form-control text-right num" id="txt_kmye" name="kmye" value="<fmt:formatNumber value="${item.kmye }" pattern=".00"/>" readonly/>
						</div>
					</div>
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">部门名称</span>
							<input type="text" class="form-control" id="txt_bmmc" name="bmmc" value="${item.bmmc }" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
							<input type="text" class="form-control" id="txt_xmmc" name="xmmc" value="${item.xmmc }" readonly/>
						</div>
					</div>
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">收入科目</span> -->
<%-- 							<input type="text" class="form-control" id="txt_srkm" name="srkm" value="${item.srkm }" readonly/> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">支出科目</span> -->
<%-- 							<input type="text" class="form-control" id="txt_zckm" name="zckm" value="${item.zckm }" readonly/> --%>
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目余额（元）</span>
							<input type="text" style="text-align:right" class="form-control " id="txt_xmye" name="xmye" value="<fmt:formatNumber value="${item.xmye }" pattern="0.00"/>" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">可用金额</span>
							<input type="text" class="form-control" id="txt_kyje" name="kyje" style="text-align:right;" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">负责人</span>
							<input type="text" class="form-control" id="txt_fzr" name="fzr" value="${item.fzr }" readonly/>
						</div>
					</div>
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目国库信息码</span>
							<input type="text" class="form-control" id="txt_xmgkxxm" name="xmgkxxm" value="${item.xmgkxxm }" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目类型</span>
							<input type="text" class="form-control" id="txt_xmlx" name="xmlx" value="${item.xmlx }" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">功能科目</span>
							 <input type="text" <c:if test="${empty item.gnkm }">disabled</c:if> class="form-control input-radius2" id="txt_gnkm" name="gnkm" value="${item.gnkm}"/>
							 <span class="input-group-btn"><button type="button" id="btn-gnkm" <c:if test="${empty item.gnkm }">disabled readonly</c:if> class="btn btn-link xz">选择</button></span>
						</div>
					</div>
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4 beizhu" id="beizhu">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
							<input type="text" class="form-control" id="txt_bz" name="bz" readonly="readonly" value="${item.bz }"/>
<!-- 							<input type="hidden" name="end" value="end" /> -->
						</div>
					</div>
					<div class="col-md-4 xingming" id="xingming">
						<div class="input-group"> 
							<span class="input-group-addon">姓名</span>
<%-- 							<input type="text" class="form-control" id="txt_xm" name="xm" value="${item.lrbbz}" /> --%>
							<input type="text" class="form-control" id="txt_gwkxm" name="gwkxm" value="${item.lrbbz}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">公务卡号</span>
							<input type="text" class="form-control" id="txt_gwk" name="gwkh" value="${item.gwkh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">刷卡时间</span>
							<input type="text" class="form-control date" id="txt_sksj" name="sksj" value="${item.sksj}"/>
							<input type="hidden" name="end" value="end" />
						</div>
					</div>
				</div>
				<!-- 凭证展示信息end -->
			</div>
		</div>
		</c:forEach>
		</form>
	</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/download.js"></script>
<script src="${ctx }/webView/kjhs/pzxx/pzlr/pzlr.js"></script>
<script type="text/javascript">
var pznd = "${sessionScope.pznd}";
var kjqj = "${sessionScope.kjqj}";
var doSaveUrl = "/doSave?type=UPDATE";//&zdr=${zbMap.zdrId}
$(document).ready(function(){
	//重写页面刷新方法
	$(".refresh").find(".reload").click(function(){
		var pzlx=$("#txt_pzz").val();
		pageSkip(basePath,"pzlr&type=self&pzlx="+pzlx);
	});
// 	$("#xingming").hide();
	var jsfs = $(".jsfs").val();
	if(jsfs == "0005"){
		$(".xingming").show();
		$(".beizhu").hide();
	}else{
		$(".xingming").hide();
		$(".beizhu").show();
	}
	$(".jsfs").change(function(){
		var jsfs = $(this).val();
		if(jsfs == "0005"){//debugger;
			$(this).parents(".pzfz_info").find("#xingming").show();
			$(this).parents(".pzfz_info").find("#beizhu").hide();
		}else{
			$(this).parents(".pzfz_info").find("#xingming").hide();
			$(this).parents(".pzfz_info").find("#beizhu").show();
		}
	});
	change();
	$("#txt_pzz").change(function(){
		change();
	});
	$("[data-line='1'] [data-col='1'] *").focus();
	$("[name='kmbh']:visible").change();
	$(".echo:not([name='kmbh'],.non-edit):visible").change();
	$(".btn_delete").show();
	$("[data-line='1'] .btn_delete").hide();
	$("[data-line='2'] .btn_delete").hide();
	wldc();
});

//上移
// $(document).on("click",".btn_up",function(){
	
// });
$("#btn_deletePz").click(function(){
	confirm("是否删除该凭证？","W",{title:"提示"},function(){	
		$.ajax({
			url:basePath+"/doCheckBxpz",
			type:"post",
			dataType:"json",
			async:false,
			success:function(val){
				if(val.success){
				    confirm("是否同时退回报销单？","W",{title:"提示"},
				    	function(){	//确定
				    	    console.log("对报销的操作+删除");
				    	    //获取报销的类型和guid 修改状态 审核完后退回
				    	    doAction(getBasePath()+"/ChangeBxzt","",function(){
				    	    	 doAction(getBasePath()+"/doDeleteAndTh?pzly=4","",function(){
										location.reload();
									});
							});
				    	},
				    	function(){//取消
				    		console.log("删除");
				    		doAction(getBasePath()+"/doDelete?pzly=4","",function(){
								location.reload();
							});
				    	}
				    );											
				}else{
					doAction(getBasePath()+"/doDelete?pzly=1","",function(){
						location.reload();
					});
				}
			}
		});//ajax					
	});//confirm
});
function wldc(){	
    var wldcObj = $(".wldc");
	$.each(wldcObj,function(){
		var xs = $(this).attr("xs");
		if(xs!="true"){
			compareToWldc($(this));
			compareToKhyh($(this).parent("div").parent("div").parent("div"));
		}
	});
}
//往来对冲
function compareToWldc(obj){
	//获取明细对应 的  科目编号
	var num = obj.parents(".pzfz_info").attr("data-pzfz");
	var zrr = obj.attr("zrr");
	var dfdw = obj.attr("dfdw");
	var wl = obj.parent("div").find("[name='wldc']");
	var wldc = wl.attr("wldc");
	var selfDh = obj.val();
	wl.find("option").remove(); 
	wl.append("<option value=''>--未选择--</option>");
	var pzid = $("[name='uploadId']").val();
	$.ajax({
		url:basePath+"/getWldc",
		data:"zrr="+zrr+"&dfdw="+dfdw+"&pzid="+pzid,
		type:"post",
		dataType:"json",
		async:false,
		success:function(data){
			if(data){
				
				debugger
				$.each(data,function(i,v){
						if(selfDh==v.ZCLX){
							wl.append("<option value="+v.ZCLX+" selected>"+v.ZCLX+"</option>");
						}else{
							wl.append("<option value="+v.ZCLX+">"+v.ZCLX+"</option>");
						}
				});
			}
		}
	});
}
//开户银行
function compareToKhyh(obj){
	var zrr = obj.parent("div").find("[name='zrr']").val();
	var dfdw = obj.parent("div").find("[name='dfdw']").val();
	var kh = obj.parent("div").find("[name='khyh']");
	var khyh = obj.parent("div").find("[name='khyh']").attr("khyh");
	kh.find("option").remove(); 
	kh.append("<option value=''>--未选择--</option>");
	var type = "";
	var hm = "";
	if(isNull(zrr)&&isNull(dfdw)) return;
	if(!isNull(zrr)){
		type="zrr";
		hm = zrr;
	}
	if(!isNull(dfdw)){
		type="dfdw";
		hm = dfdw;
	}
	$.ajax({
		url:basePath+"/getKhyh",
		data:"type="+type+"&hm="+hm,
		type:"post",
		dataType:"json",
		async:false,
		success:function(data){
			if(data){
				$.each(data,function(i,v){
					if(khyh==v.GUID){
						kh.append("<option value="+v.GUID+" zh="+v.KHYHZH+" selected>"+v.KHYH+"</option>");
					}else{
						kh.append("<option value="+v.GUID+" zh="+v.KHYHZH+">"+v.KHYH+"</option>");
					}
				});
			}
		}
	});
}
$(document).on("click","#btn_exp",function(){
	var copyTr = $("#pzlr_mx").find(".copyed");
	var mxid = copyTr.find("[name='mxid']").val();
	var zbid = $("[name='uploadId']").val();
	doExp("","${ctx}/kjhs/pzxx/pzlr/expExcel2?zbid="+zbid+"&mxid="+mxid,"银行信息","${pageContext.request.contextPath}",mxid);
});

	
</script>
</body>
</html>