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
	<div class='page-header'>
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>凭证信息录入</span>
		</div>
        <div class="pull-right">
			 <button type="button" class="btn btn-default" id="btn_save1"><i class="fa icon-saves"></i>保存</button>
			 <button type="button" class="btn btn-default" id="btn_saveMx"><i class="fa icon-saves"></i>银行明细</button>
<!-- 			 <button type="button" class="btn btn-default" id="btn_exp"><i class="fa icon-saves"></i>导出明细</button> -->
			 <button type="button" class="btn btn-default" id="btn_add"><i class="fa icon-saves"></i>增行</button>
			 <button type="button" class="btn btn-default" id="btn_xzmb"><i class="fa icon-saves"></i>选择模板</button>
			 <button type="button" class="btn btn-default" id="btn_copyByFl"><i class="fa icon-saves"></i>复制分录</button>
			 <button type="button" class="btn btn-default" id="btn_bxpzsc"><i class="fa icon-saves"></i>报销凭证生成</button>
			 <button type="button" class="btn btn-default" id="btn_xzpzsc"><i class="fa icon-saves"></i>薪资凭证生成</button>
			 <button type="button" class="btn btn-default" id="btn_jdhh"><i class="fa icon-saves"></i>借贷互换</button>
			 <button type="button" class="btn btn-default" id="btn_first" data-type="first"><i class="fa icon-write"></i>第一张</button>
			 <button type="button" class="btn btn-default" id="btn_prev" data-type="prev"><i class="fa icon-write"></i>上一张</button>
			 <button type="button" class="btn btn-default" id="btn_next" data-type="next"><i class="fa icon-write"></i>下一张</button>
			 <button type="button" class="btn btn-default" id="btn_last" data-type="last"><i class="fa icon-write"></i>最后张</button>
			 <button type="button" class="btn btn-default" id="btn_upload" data-type="last"><i class="fa icon-write"></i>上传单据</button>
			 <button type="button" class="btn btn-default" id="btn_glf"><i class="fa icon-write"></i>管理费</button>
        </div>
    </div>
    <div class="box">
		<div class="box-panel" style="text-align:center;">
			<div style="display:inline-block;position:absolute;left:40px;top: 40px;">
<!-- 				<label style="font-size: 15px;color: red;border: 1px solid #b9b7b7;padding: 5px;border-radius: 5px;">未保存</label> -->
				<img src="${ctx }/static/images/pzzt/wbc.png"/>
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
								<input type="hidden"  name="uploadId" value="${uploadId}"/>
								<input type="text" class="date" id="txt_pzrq" name="pzrq"  style="border-radius:4px;border:1px solid #ccc;width: 90px;text-align: center;"></input>
							</div>       	
				       		<div style="display:inline-block;position:absolute;right:0px;">
								<label>单据</label>
								<input type="text" class="int" onkeydown="return disableEnter(event);" style="height: 22px!important;width: 30px;display:inline-block;border-radius:4px;border:1px solid #ccc;" id="txt_fjzs" name="fjzs" value=""/>&nbsp;张
							</div>
				       	</div>
				       	</form>
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
					    				<div class="btn_up" style="display:none;">上移</div>
					   		 		</td>
					   		 		<td class="xh">0</td>
						            <td data-col="1">
						            	<textarea rows="1" cols="" maxlength="100" name="zy" id="zy" class="zyTextArea echo required"></textarea>
						            	<input type="hidden" id="" name="jdfx"/>
						            	<input type="hidden" id="txt_mxid" name="mxid" value=""/>
						            </td>
						            <td data-col="2">
						           		<input type="text" class="input_title echo required" id="kmbh" name="kmbh" />
						            </td>
						            <td data-col="3">
						           		<input type="text" class="input_title echo required"  id="jjfl" name="jjfl" />
						            </td>
						            <td data-col="4">
						           		<input type="text" class="input_title echo required" id="bmbh" name="bmbh" />
						            </td>
						            <td data-col="5">
						           		<input type="text" class="input_title echo required" id="xmbh" name="xmbh" />
						            </td>
						            <td data-col="6">
						            	<input type="text" class="input_title text-right sign-number required" id="jfje" name="jfje"/>
						            </td>
						            <td data-col="7">
						            	<input type="text" class="input_title text-right sign-number required" id="dfje" name="dfje"/>
						            </td>
						        </tr>
						        <tr class="hj" style="font-size:14px;">
						       		<td class="action">
					   		 		</td>
					   		 		<td class="action">
					   		 		</td>
						        	<td colspan="6" style="padding-left: 50px;">
						        		<label style="color: blue">合计</label>
						        	</td>
						        	<td colspan="1" style="text-align: right;">
						        		<label id="txt_jfzje" style="font-weight: bold;color: blue;">0.00</label>
						        	</td>
						        	<td colspan="1" style="text-align: right;">
						        		<label id="txt_dfzje" style="font-weight: bold;color: blue;">0.00</label>
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
								<label>${ryxm }</label>
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
<!-- 							<input type="text" class="form-control" disabled readonly id="txt_wldc" name="wldc" onkeyup="value=value.replace(/\W/,'')"/> -->
							<select class="form-control" disabled id="txt_wldc" name="wldc">
							 	<option value="">-未选择-</option>
							 	<c:forEach items="${wldcList }" var="item">
							 		<option value="${item.jsdh }">${item.jsdh }</option>
							 	</c:forEach>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">个&emsp;&emsp;人</span>
	                         <input type="text" disabled readonly class="form-control input-radius2" id="txt_zrr" name="zrr" />
	                         <span class="input-group-btn"><button disabled type="button" id="btn-zrr" class="btn btn-link xz">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">往来单位</span>
							 <input type="text" disabled readonly class="form-control input-radius2" id="txt_dfdw" name="dfdw" />
							 <span class="input-group-btn"><button type="button" id="btn-dfdw" disabled class="btn btn-link xz">选择</button></span>
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
							<select class="form-control" id="txt_khyh" name="khyh">
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
<!-- 	                         <select class="form-control" id="txt_zclx" name="zclx"> -->
<!-- 							 	<option value="">-未选择-</option> -->
<%-- 							 	<c:forEach items="${zclxList }" var="item"> --%>
<%-- 							 		<option value="${item.dm }">${item.mc }</option> --%>
<%-- 							 	</c:forEach> --%>
<!-- 							 </select> -->
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
							 <input type="text" disabled readonly class="form-control input-radius2" id="txt_gnkm" name="gnkm" />
							 <span class="input-group-btn"><button type="button" id="btn-gnkm" disabled class="btn btn-link xz">选择</button></span>
						</div>
					</div>
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4" id="beizhu">
						<div class="input-group" >
							<span class="input-group-addon">备注</span>
							<input type="text" class="form-control" id="txt_bz" name="bz" readonly="readonly"/>
<!-- 							<input type="hidden" name="end" value="end" /> -->
						</div>
					</div>
					<div class="col-md-4" id="xingming">
						<div class="input-group"> 
							<span class="input-group-addon">姓名</span>
							<input type="text" class="form-control" id="txt_gwkxm" name="gwkxm"/>
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
							<input type="text" class="form-control  date" id="txt_sksj" name="sksj" value=""/>
							<input type="hidden" name="end" value="end" />
						</div>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${ctx }/webView/kjhs/pzxx/pzlr/pzlr.js"></script>
<script type="text/javascript">
//凭证年度，账套期间
var pznd = "${sessionScope.pznd}";
var kjqj = "${sessionScope.kjqj}";
var doSaveUrl = "/doSave?type=ADD";
$(document).ready(function(){
	$("#xingming").hide();
	$("#txt_jsfs").change(function(){
		var jsfs = $(this).val();
		if(jsfs == "0005"){//debugger;
			$(this).parents(".pzfz_info").find("#xingming").show();
			$(this).parents(".pzfz_info").find("#beizhu").hide();
		}else{
			$(this).parents(".pzfz_info").find("#xingming").hide();
			$(this).parents(".pzfz_info").find("#beizhu").show();
		}
	});
	//调用js增加两行明细、辅助录入，并让第一行显示
	addPzmx();
	addPzmx();
// 	$("#xingming").hide();
// 	change();
	$("#txt_pzz").change(function(){
		change();
	});
	$("[data-line='1'] [data-col='1'] *").focus();
	loadPzrq(pznd,kjqj,new Date().getDate());
	$(".btn_delete").show();
	$("[data-line='1'] .btn_delete").hide();
	$("[data-line='2'] .btn_delete").hide();
	$(".btn_up").show();
	$("[data-line='1'] .btn_up").hide();
	$(".wyxx").hide();
	//数据导入
  	$(document).on("click","#btn_glfimp",function(){
		select_commonWin("${pageContext.request.contextPath}/kjhs/pzxx/pzlr/txl_glfimp.jsp", "导入管理费信息", 450,350);
		return false;
	});
});

</script>
</body>
</html>