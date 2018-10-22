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
<body>
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" id="guid" name="guid" value="${zbMap.guid}">
	<div class='page-header'>
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>凭证信息录入</span>
		</div>
        <div class="pull-right">
        		<c:if test="${zbMap.pzzt eq '已复核'}">
					<button type="button" class="btn btn-default" id="btn_qxfh">
						<i class="fa icon-saves"></i>取消复核
				 	</button>
				</c:if>
			  <button type="button" class="btn btn-default" id="btn_lookMx"><i class="fa icon-saves"></i>银行明细</button>
			 <button type="button" class="btn btn-default" id="btn_exp"><i class="fa icon-saves"></i>导出明细</button>
			 <button type="button" class="btn btn-default" id="btn_first" data-type="first"><i class="fa icon-write"></i>第一张</button>
			 <button type="button" class="btn btn-default" id="btn_prev" data-type="prev"><i class="fa icon-write"></i>上一张</button>
			 <button type="button" class="btn btn-default" id="btn_next" data-type="next"><i class="fa icon-write"></i>下一张</button>
			 <button type="button" class="btn btn-default" id="btn_last" data-type="last"><i class="fa icon-write"></i>最后张</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel" style="text-align:center;">
			<div style="display:inline-block;position:absolute;left:40px;top: 40px;">
<%-- 				<label style="font-size: 15px;color: green;border: 1px solid #b9b7b7;padding: 5px;border-radius: 5px;">${zbMap.pzzt }</label> --%>
				<c:if test="${zbMap.pzzt eq '已复核'}">
					<img src="${ctx }/static/images/pzzt/yfh.png"/>
				</c:if>
				<c:if test="${zbMap.pzzt eq '已记账'}">
					<img src="${ctx }/static/images/pzzt/yjz.png"/>
				</c:if>
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
				       	<input type="hidden" id="txt_bxid" name="bxid"/>
				       	<input type="hidden" id="txt_kjqj" name="kjqj" value="${param.kjqj}"/>
				       		<input type="hidden" name="uploadId" value="${zbMap.guid }"/>
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
									<c:forEach items="${pzbhList }" var="item" varStatus="status">
										<option value="${item }" <c:if test="${item eq pzbh }">selected</c:if>>${item }</option>
									</c:forEach>
								</select>
							</div> 
				       	</div>
				       	<div class="t-round" style="position:relative;">
				       		
							<div style="width: 25%;display:inline-block;">
								<input type="hidden" name="sszt" value="${sszt }"/>
<!-- 								<label >日期</label> -->
								<input type="text" class="date bg-white" id="txt_pzrq" name="pzrq"  style="border-radius:4px;border:1px solid #ccc;width: 90px;text-align: center;" value="<fmt:formatDate value="${zbMap.pzrq }" pattern="yyyy-MM-dd"/>" />
							</div>       	
				       		<div style="width: 10%;display:inline-block;position:absolute;right:0px;">
								<label>单据</label>
								<input type="text" class="int" style="height: 22px!important;width: 30px;display:inline-block;border-radius:4px;border:1px solid #ccc;" id="txt_fjzs" name="fjzs" value="${zbMap.fjzs }"/>&nbsp;张
							</div>
				       	</div>
				       	</form>
				       	<form id="mxForm">
						<table class="top_table">
							<thead>	
								<tr>
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
					   		 		<td class="xh">0</td>
						            <td data-col="1">
						            	<textarea rows="1" cols="" maxlength="50" name="zy" class="zyTextArea"></textarea>
						            </td>
						            <td data-col="2">
						           		<input type="text" class="input_title echo required" id="" name="kmbh" />
						            </td>
						            <td data-col="3">
						           		<input type="text" class="input_title echo required" id="" name="jjfl" />
						            </td>
						            <td data-col="4">
						           		<input type="text" class="input_title echo required" id="" name="bmbh" />
						            </td>
						            <td data-col="5">
						           		<input type="text" class="input_title echo required" id="" name="xmbh" />
						            </td>
						            <td data-col="6">
						           		<input type="hidden" id="" name="jdfx"/>
						            	<input type="text" class="input_title text-right sign-number required" id="" name="jfje"/>
						            </td>
						            <td data-col="7">
						            	<input type="text" class="input_title text-right sign-number required" id="" name="dfje"/>
						            </td>
						        </tr>
						        <c:forEach items="${mxList }" var="item" varStatus="status">
					    		<tr data-line="${status.count }">
					    			<td class="action">
					    				<c:if test="${status.count > 2 }">
					    					<div class="btn_delete" style="display:none;"></div>
					    				</c:if>
					   		 		</td>
					   		 		<td class="xh">${status.count }</td>
						            <td data-col="1">
						            	<textarea rows="1" cols="" maxlength="50" name="zy" class="zyTextArea">${item.zy }</textarea>
						            	<input type="hidden" id="txt_mxid" name="mxid" value="${item.guid}"/>
						            </td>
						            <td data-col="2">
						           		<input type="text" class="input_title echo required" id="txt_kmbh_1" name="kmbh" value="${item.kmbh }"  title="${item.kmmc }" />
						            </td>
						            <td data-col="3">
						           		<input type="text" class="input_title echo required" id="txt_jjfl_1" name="jjfl" value="${item.jjfl }"  title="${item.zcjjflkm }" />
						            </td>
						            <td data-col="4">
						           		<input type="text" class="input_title echo required" id="txt_bmbh_1" name="bmbh" value="${item.bmbh }"  title="${item.bmmc }" />
						            </td>
						            <td data-col="5">
						           		<input type="text" class="input_title echo required" id="txt_xmbh_1" name="xmbh" value="${item.xmbh }"  title="${item.xmmc }" />
						            </td>
						            <td data-col="6">
						            	<input type="hidden" id="" name="jdfx"/>
						            	<input type="text" class="input_title text-right sign-number required" id="txt_jfje_1" name="jfje" value="<fmt:formatNumber value="${item.jfje }" pattern="0.00"/>" />
						            </td>
						            <td data-col="7">
						            	<input type="text" class="input_title text-right sign-number required" id="" name="dfje" value="<fmt:formatNumber value="${item.dfje }" pattern="0.00"/>" />
						            </td>
						        </tr>
						        </c:forEach>
						        <tr class="hj" style="font-size:14px;">
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
								<label>${zbMap.jzr }</label>
							</div>       	
							<div style="width: 30%;display:inline-block;">
								<label>复核：</label>
								<label>${zbMap.fhr }</label>
							</div>       	
							<div style="width: 30%;display:inline-block;">
								<label>制单：</label>
								<label>${zbMap.zdr }</label>
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
							<input type="text" class="form-control" id="txt_wldc" name="wldc" onkeyup="value=value.replace(/\W/,'')"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">个&ensp;&ensp;&ensp;&ensp;人</span>
	                         <input type="text" class="form-control input-radius2" id="txt_zrr" name="zrr" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">往来单位</span>
							 <input type="text" class="form-control input-radius2" id="txt_dfdw" name="dfdw" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">结算方式</span>
							 <select class="form-control" id="txt_jsfs" name="jsfs">
							 	<option value="">-未选择-</option>
							 	<c:forEach items="${jsfsList }" var="item">
							 		<option value="${item.dm }" <c:if test="${items2.dm==item.jsfs}">selected</c:if>>${item.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">结算单号</span>
	                        <input type="text" id="txt_jsdh" class="form-control" name="jsdh" value="" onkeyup="value=value.replace(/\W/,'')">
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
							 <span class="input-group-addon">支出类型</span>
	                         <select class="form-control" id="txt_zclx" name="zclx">
	                         	<option value="">-未选择-</option>
							 	<c:forEach items="${zclxList }" var="item">
							 		<option value="${item.dm }">${item.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4 dqsj" style="display:none;">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>到期时间</span>
							<input type="text" class="form-control date" id="txt_dqsj" name="dqsj" disabled="disabled"/>
						</div>
					</div>
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
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目余额（元）</span>
							<input type="text" class="form-control" id="txt_xmye" name="xmye" style="text-align:right;" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">负责人</span>
							<input type="text" class="form-control" id="txt_fzr" name="fzr" readonly="readonly"/>
						</div>
					</div>
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
							 <input type="text" disabled class="form-control input-radius2 " id="txt_gnkm" name="gnkm" />
							 <span class="input-group-btn"><button type="button" id="btn-gnkm" disabled class="btn btn-link xz">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
							<input type="text" class="form-control" id="txt_bz" name="bz" readonly="readonly"/>
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
							<input type="text" class="form-control input-radius2" name="wldc" value="${item.wldc}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">个&emsp;&emsp;人</span>
	                         <input type="text" class="form-control input-radius2" id="txt_zrr_2" name="zrr" value="${item.zrr}" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">往来单位</span>
							 <input type="text" class="form-control input-radius2" id="txt_dfdw_2" name="dfdw" value="${item.dfdw}" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">结算方式</span>
							 <select class="form-control jsfs" id="txt_jsfs" name="jsfs">
							 	<option value="">-未选择-</option>
							 	<c:forEach items="${jsfsList }" var="items2">
							 		<option value="${items2.dm }" <c:if test="${items2.dm==item.jsfs}">selected</c:if>>${items2.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">结算单号</span>
	                        <input type="text" id="txt_jsdh" <c:if test="${empty item.jsdh}">disabled readonly</c:if> class="form-control" name="jsdh" value="${item.jsdh }" onkeyup="value=value.replace(/\W/,'')">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算来源</span>
							<select class="form-control" id="txt_ysly" name="ysly">
								<option value="">-未选择-</option>
							 	<c:forEach items="${yslyList }" var="items2">
							 		<option value="${items2.dm }">${items2.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算类型</span>
							<select class="form-control" id="txt_yslx" name="yslx">
								<option value="">-未选择-</option>
							 	<c:forEach items="${yslxList }" var="items2">
							 		<option value="${items2.dm }">${items2.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">借款单号</span>
							 <input type="text" class="form-control" id="txt_zclx" name="zclx" value="${item.zclx}"/>
						</div>
					</div>
					<div class="col-md-4 dqsj" <c:if test="${item.dqsj == null }">style="display:none;"</c:if>>
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>到期时间</span>
							<input type="text" class="form-control date" id="txt_dqsj" name="dqsj" <c:if test="${item.dqsj == null }">disabled="disabled"</c:if> value="${item.dqsj}"/>
						</div>
					</div>
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
							<input type="text" class="form-control text-right" id="txt_kmye" name="kmye" value="<fmt:formatNumber value="${item.kmye }" pattern=".00"/>" readonly/>
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
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目余额（元）</span>
							<input type="text" class="form-control" id="txt_xmye" name="xmye" value="<fmt:formatNumber value="${item.xmye }" pattern=".00"/>" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">负责人</span>
							<input type="text" class="form-control" id="txt_fzr" name="fzr" value="${item.fzr }" readonly/>
						</div>
					</div>
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
							 <input type="text" readonly class="form-control input-radius2" id="txt_gnkm" name="gnkm" value="${item.gnkm}"/>
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
<script src="${ctx }/webView/kjhs/pzxx/pzlr/pzlrView.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".zckm").hide().css("margin-bottom","0px");
	$(".srkm").hide().css("margin-bottom","0px");
	var jsfs = $(".jsfs").val();
	if(jsfs == "0005"){
		$(".xingming").show();
		$(".beizhu").hide();
	}else{
		$(".xingming").hide();
		$(".beizhu").show();
	}
});
$("#btn_qxfh").click(function(){
	var guid = $("#guid").val();	
	$.ajax({
		type : "post",
		url : "${ctx}/kjhs/pzxx/pzfh/Saverqqx",
		data : "guid=" + guid,
		dataType : "json",
		success : function(data) {
			alert("取消成功！");
			pageSkip(getBasePath(),"pzlr&pzlx="+$("#txt_pzz").val());
		},
		error: function(){
			alert("抱歉，系统出现错误！");
		}
	});
});
$(document).on("click","#btn_exp",function(){
	var copyTr = $("#pzlr_mx").find(".copyed");
	var mxid = copyTr.find("[name='mxid']").val();
	var zbid = $("[name='uploadId']").val();
	console.log(mxid+"/"+zbid);
	doExp("","${ctx}/kjhs/pzxx/pzlr/expExcel2?zbid="+zbid+"&mxid="+mxid,"银行信息","${pageContext.request.contextPath}",mxid);
});
$("#btn_print_more").click(function(){
	var guid = $("#guid").val();
	var pzz=$("#txt_pzz").val();
	var kjqj=$("#txt_kjqj").val();
	var pznd=$("#txt_kjqj").val();
	select_commonWin("${ctx}/kjhs/pzxx/pzfh/printwindow?guid=" + guid+"&pzz="+pzz+"&kjqj="+kjqj+"&pznd="+pznd,"批量打印", "600", "550");
});
</script>
</body>
</html>