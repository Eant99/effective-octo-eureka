<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证录入</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	#popup{
		text-align: left;
	}
	.pull-right{
 		margin-top: 4px!important;
		margin-right: 10px!important;
	}
	.input-radius2{
	    border-top-right-radius: 4px!important;
	    border-bottom-right-radius: 4px!important;
	}
	select{
		border-radius: 4px;
	}
	.bg-white{
		background-color: white!important;
	}
	.minus{
		background-color: #ff000014!important;
	}
	.non-edit{
		background-color: rgba(204, 204, 204, 0.47058823529411764)!important;
		background-color: #cccccc78!important;
	}
	.t-round{
		width: 90%!important;
		height: 30px;
		line-height: 30px;
		display:inline-block;
		margin: 0px;
	}
	.top_table{
		width:90% !important;
		height: 40px;
		margin:auto;
		margin-top:10px;
		border-collapse:collapse!important;
	}
	.top_table td {
		text-align: center;
   	 	border: 1px solid #b3b0b0;
   	 	height: 40px;
	}
	.top_table td.action {
		width: 4%!important;
		border: none;
	}
	.top_table td.xh{
		width: 6%!important;
	}
	.top_table td.xh + td{
		width: 30%;
	}
	.top_table td.xh + td ~ td{
	}
	.top_table td .zyTextArea{
		height: 100%;
		width: 100%;
 		padding: 0px 5px;
 		margin: 0px;
 		float: left;
 		overflow: hidden;
 		resize: none;
 		border: none;
		outline: none;
	}
	input.input_title{
		border:none;
		height:100% !important;
		width:100% !important;
	}
	.pzfz_info{
		width: 88%;
		margin:auto;
	}
	.btn_delete{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.btn_delete:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	#txt_pzrq{
		height:28px !important;
		line-height: normal;
	}
	#txt_fjzs{
		height:28px !important;
		line-height: normal;
	}
	#txt_pzz{
		height:28px !important;
		
	}
	#txt_pzbh{
		height:28px !important;
	}
	#fzForm{
		margin-left: 50px;
		background-color: white;
	}
	#txt_jfzje,#txt_dfzje{
		padding: 0px 5px;
	}
</style>
</head>
<body>
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>凭证信息录入</span>
		</div>
        <div class="pull-right">
			 <button type="button" class="btn btn-default" id="btn_back">返回</button>
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
				       	<input type="hidden" id="txt_pznd" name="pznd" value="${param.pznd}"/>
				       	<div class="t-round text-right">
				       		<div style="width: 25%;display:inline-block;">
	<!-- 								<label>凭证字</label> -->
	<!-- 								<select class="" id="txt_pzz" name="pzz"> -->
	<%-- 									<c:forEach items="${pzzList }" var="item"> --%>
	<%-- 										<option value="${item.lxbh }" <c:if test="${item.lxbh eq pzz }">selected</c:if>>${item.pzz }</option> --%>
	<%-- 									</c:forEach> --%>
	<!-- 								</select> -->
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
						            </td>
						            <td data-col="2">
						           		<input type="text" class="input_title echo required" id="txt_kmbh_1" name="kmbh" value="${item.kmbh }"/>
						            </td>
						            <td data-col="3">
						           		<input type="text" class="input_title echo required" id="txt_jjfl_1" name="jjfl" value="${item.jjfl }"/>
						            </td>
						            <td data-col="4">
						           		<input type="text" class="input_title echo required" id="txt_bmbh_1" name="bmbh" value="${item.bmbh }"/>
						            </td>
						            <td data-col="5">
						           		<input type="text" class="input_title echo required" id="txt_xmbh_1" name="xmbh" value="${item.xmbh }"/>
						            </td>
						            <td data-col="6">
						            	<input type="hidden" id="" name="jdfx"/>
						            	<input type="text" class="input_title text-right sign-number required" id="" name="jfje" value="<fmt:formatNumber value="${item.jfje }" pattern=".00"/>" />
						            </td>
						            <td data-col="7">
						            	<input type="text" class="input_title text-right sign-number required" id="" name="dfje" value="<fmt:formatNumber value="${item.dfje }" pattern=".00"/>" />
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
							 <span class="input-group-addon">个&emsp;&emsp;人</span>
	                         <input type="text" class="form-control bg-white input-radius2" id="txt_zrr" name="zrr" />
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
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">结算方式</span>
							 <select class="form-control" id="txt_jsfs" name="jsfs">
							 	<c:forEach items="${jsfsList }" var="item">
							 		<option value="${item.dm }" <c:if test="${items2.dm==item.jsfs}"></c:if>>${item.mc }</option>
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
							 	<c:forEach items="${yslyList }" var="item">
							 		<option value="${item.dm }">${item.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算类型</span>
							<select class="form-control" id="txt_yslx" name="yslx">
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
							 	<c:forEach items="${zclxList }" var="item">
							 		<option value="${item.dm }">${item.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
				</div>
				<div class="row">
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
				</div>
				<div class="row">
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
							<span class="input-group-addon">收入科目</span>
							<input type="text" class="form-control" id="txt_srkm" name="srkm" readonly="readonly"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">支出科目</span>
							<input type="text" class="form-control" id="txt_zckm" name="zckm" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目余额（元）</span>
<!-- 							<input type="text" class="form-control" id="txt_xmye" name="xmye" /> -->
							<input type="text" class="form-control" id="txt_xmye" name="xmye" style="text-align:right;" readonly="readonly"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">负责人</span>
							<input type="text" class="form-control" id="txt_fzr" name="fzr" readonly="readonly"/>
						</div>
					</div>
				</div>
				<div class="row">
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
				</div>
				<div class="row">
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
						<div class="input-group">
							<span class="input-group-addon">往来对冲</span>
							<input type="hidden" class="wldc" zrr="${item.zrr}" dfdw="${item.dfdw}" value="${item.wldc}" xs="${empty item.zrr and empty item.dfdw}"/>
							<select class="form-control" id="txt_wldc" wldc="${item.wldc}" name="wldc" <c:if test="${empty item.zrr and empty item.dfdw}">disabled</c:if>>
							 </select>
<%-- 							<input type="text" class="form-control" <c:if test="${empty item.wldc}">disabled readonly</c:if> id="txt_wldc" name="wldc"  onkeyup="value=value.replace(/\W/,'')" value="${item.wldc }"/> --%>
						</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">个&ensp;&ensp;&ensp;&ensp;人</span>
	                         <input type="text" class="form-control bg-white input-radius2" id="txt_zrr_2" name="zrr" value="${item.zrr}" readonly/>
	                         <span class="input-group-btn"><button type="button" id="btn-zrr" class="btn btn-link xz">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">往来单位</span>
							 <input type="text" class="form-control input-radius2 bg-white" id="txt_dfdw_2" name="dfdw" value="${item.dfdw}" readonly/>
							 <span class="input-group-btn"><button type="button" id="btn-dfdw" class="btn btn-link xz">选择</button></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">结算方式</span>
							 <select class="form-control" id="txt_jsfs" name="jsfs">
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
							 	<c:forEach items="${yslyList }" var="items2">
							 		<option value="${items2.dm }">${items2.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
				</div>
				<div class="row wyxx" <c:if test="${empty item.zrr and empty item.dfdw}">style="display:none;"</c:if>>
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
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算类型</span>
							<select class="form-control" id="txt_yslx" name="yslx">
							 	<c:forEach items="${yslxList }" var="items2">
							 		<option value="${items2.dm }">${items2.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">支出类型</span>
	                         <select class="form-control" id="txt_zclx" name="zclx">
							 	<c:forEach items="${zclxList }" var="items2">
							 		<option value="${items2.dm }">${items2.mc }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
				</div>
				<!-- 凭证展示信息start -->
				<div class="row">
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
				</div>
				<div class="row">
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
							<span class="input-group-addon">收入科目</span>
							<input type="text" class="form-control" id="txt_srkm" name="srkm" value="${item.srkm }" readonly/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">支出科目</span>
							<input type="text" class="form-control" id="txt_zckm" name="zckm" value="${item.zckm }" readonly/>
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
				</div>
				<div class="row">
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
							 <input type="text" readonly class="form-control input-radius2 bg-white" id="txt_gnkm" name="gnkm" value="${item.gnkm}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
							<input type="text" class="form-control" id="txt_bz" name="bz" readonly="readonly" value="${item.gnkm}"/>
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
<script type="text/javascript">
var target = "${ctx}/kjhs/pzxx/pzlr";
var pzbh = "${pzbh}";
var pznd = "${param.pznd}";
var kjqj = "${param.kjqj}";
var suffix = "&pzbh="+pzbh+"&pznd="+pznd+"&kjqj="+kjqj;
var suffix_2 = "&pznd="+pznd+"&kjqj="+kjqj;

function isNull(a_val){
	if(a_val == null || a_val == ""){
		return true;
	}else{
		return false;
	}
}
//切换辅助录入展示
function switchPzlrmxShow(a_num){
	$("[data-pzfz]").hide();
	$("[data-pzfz='"+a_num+"']").show();
}
$(function(){
	
	$(document).ready(function(){
		$("[data-line='1'] [data-col='1'] textarea").focus();
		$("input,textarea").attr("readonly","readonly");
		$("#txt_pzrq").attr("disabled","disabled");
		$("select").attr("disabled","disabled");
	})
	$("#btn_back").click(function(){
		history.back(-1);
	})
	//表格内文本框获取焦点事件，切换凭证辅助信息
	$(document).on("focus","[data-col] [name]",function(){
		switchPzlrmxShow($(this).parents("[data-line]").attr("data-line"));
	});
	$("#txt_pzbh").change(function(){
		pzbh = $(this).val();
		pageSkip(target,"pzlr&pzbh="+pzbh+suffix_2);
	});
	//分页
	$("#btn_first,#btn_prev,#btn_next,#btn_last").click(function(){
		var type = $(this).data("type");
		pageSkip(target,"pzlr&type="+type+suffix);
	})
	//打印凭证
	$("[id='btn_print']").click(function(){
		select_commonWin(target+"/pageSkip?pageName=print"+suffix,"凭证打印","1000","650");
	});

});
	
</script>
</body>
</html>