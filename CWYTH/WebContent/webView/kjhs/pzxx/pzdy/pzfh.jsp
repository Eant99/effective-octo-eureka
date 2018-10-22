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
	input[disabled]{
			background-color:white!important;
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
		background-color: #cccccc78!important;
	}
	.t-round{
		width: 50%!important;
		height: 40px;
		line-height: 40px;
		display:inline-block;
		margin: 10px;
	}
	.top_table{
		width:70% !important;
		height: 40px;
		margin:auto;
		border-collapse:collapse!important;
	}
	.top_table td {
		text-align: center;
   	 	border: 1px solid #b3b0b0;
   	 	height:40px;
	}
	td.action {
		width: 40px!important;
		border: none;
	}
	td .xh{
		width: 40px!important;
	}
	input.input_title{
		border:none;
		height:100% !important;
		width:100% !important;
	}
	.pzfz_info{
		width:70%;
		margin:auto;
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
			 <button type="button" class="btn btn-default" id="btn_print"><i class="fa icon-saves"></i>打印凭证</button>
			 <button type="button" class="btn btn-default" id="btn_first" data-type="first"><i class="fa icon-write"></i>第一张</button>
			 <button type="button" class="btn btn-default" id="btn_prev" data-type="prev"><i class="fa icon-write"></i>上一张</button>
			 <button type="button" class="btn btn-default" id="btn_next" data-type="next"><i class="fa icon-write"></i>下一张</button>
			 <button type="button" class="btn btn-default" id="btn_last" data-type="last"><i class="fa icon-write"></i>最后张</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel" style="text-align:center;">
			<div class="box-header clearfix" style="margin-top:20px;">
            	<div class="sub-title pull-left text-primary" style="width:100%;text-align: center;">
            		<span style="font-size:25px;">记账凭证</span>
            	</div>
        	</div>
			<!-- table start -->
			<div class="container-fluid" id="pzlr_zb">
				<div class='responsive-table'>
					<div class='scrollable-area' style="text-align:center;"> 
				       	<form id="zbForm">
				       	<div class="t-round">
							<div style="width: 33%;display:inline-block;">
								<input type="hidden" name="sszt" value="${sszt }"/>
								<label >日期</label>
								<input type="text" class="date" style="border-radius:4px;border:1px solid #ccc;text-align: center;" id="txt_pzrq" name="pzrq" value=<fmt:formatDate value="${zbMap.pzrq }"/> readonly/>
							</div>       	
							<div style="width: 33%;display:inline-block;">
								<label>凭证字</label>
								<select class="" id="txt_pzz" name="pzz">
									<c:forEach items="${pzzList }" var="item">
										<option value="${item.lxbh }" <c:if test="${item.lxbh eq pzz }">selected</c:if>>${item.pzz }</option>
									</c:forEach>
								</select>
								<select class="" id="txt_pzbh" name="pzbh">
									<c:forEach items="${pzbhList }" var="item" varStatus="status">
										<option value="${item }" <c:if test="${item eq pzbh }">selected</c:if>>${item }</option>
									</c:forEach>
								</select>
							</div>       	
							<div style="width: 33%;display:inline-block;">
								<label>单据</label>
								<input type="text" class="int" style="width:25px;display:inline-block;border-radius:4px;border:1px solid #ccc;" id="txt_fjzs" name="fjzs" value="${zbMap.fjzs }"/>&nbsp;张
							</div>       	
				       	</div>
				       	</form>
				       	<form id="mxForm">
						<table class="top_table">
						    <tbody>
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
					      	  	<c:forEach items="${mxList }" var="item" varStatus="status">
					    		<tr data-line="${status.count }">
					    			<td class="action">
					    				<div class="btn_delete"></div>
					   		 		</td>
					   		 		<td>
					   		 			<div class="xh">${status.count }</div>
					   		 		</td>
						            <td data-col="1">
						            	<input type="text" class="input_title required" id="" name="zy" value="${item.zy }"/>
						            </td>
						            <td data-col="2">
						           		<input type="text" class="input_title echo required" id="" name="kmbh"  value="${item.kmbh }"/>
						            </td>
						            <td data-col="3">
						           		<input type="text" class="input_title echo required" id="" name="jjfl"  value="${item.jjfl }"/>
						            </td>
						            <td data-col="4">
						           		<input type="text" class="input_title echo required" id="" name="bmbh"  value="${item.bmbh }"/>
						            </td>
						            <td data-col="5">
						           		<input type="text" class="input_title echo required" id="" name="xmbh"  value="${item.xmbh }"/>
						            </td>
						            <td data-col="6">
						           		<input type="hidden" id="" name="jdfx"/>
						            	<input type="text" class="input_title text-right sign-number" id="" name="jfje" value="${item.jfje }"/>
						            </td>
						            <td data-col="7">
						            	<input type="text" class="input_title text-right sign-number" id="" name="dfje" value="${item.dfje }"/>
						            </td>
						        </tr>
						        </c:forEach>	
						        <tr style="font-size:14px;">
						       		<td class="action">
					   		 		</td>
						        	<td colspan="4" style="border-right:none;">
						        		<label>合计</label>
						        	</td>
						        	<td colspan="2" style="border-left:none;border-right:none;">
						        		<label>借方总金额（元）：</label>
						        		<label id="txt_jfzje" style="font-weight: bold;color: blue;">${zbMap.jfzje }</label>
						        	</td>
						        	<td colspan="2" style="border-left:none;">
						        		<label>贷方总金额（元）：</label>
						        		<label id="txt_dfzje" style="font-weight: bold;color: blue;">${zbMap.dfzje }</label>
						        	</td>
						        </tr>
						    </tbody>
						</table>
						</form>
						<div class="t-round">
							<div style="width: auto;display:inline-block;">
								<label>记账：</label>
								<label></label>
							</div>       	
							<div style="width: auto;display:inline-block;">
								<label>复核：</label>
								<label></label>
							</div>       	
							<div style="width: auto;display:inline-block;">
								<label>制单：</label>
								<label></label>
							</div>       	
							<div style="width: auto;display:inline-block;">
								<label>出纳：</label>
								<label></label>
							</div>       	
				       	</div>
				    </div>
				</div>
			</div>
			<!-- table end -->
		</div>
		<form id="fzForm">
		<c:forEach items="${mxList }" var="item" varStatus="status">
		<div class="box-panel pzfz_info" <c:if test="${status.count > 1 }">style="display:none"</c:if> data-pzfz="${status.count }">
			<div class="box-header clearfix" style="margin-top:20px;">
            	<div class="sub-title pull-left text-primary"><i class='fa icon-xiangxixinxi'></i>辅助录入，第<label id="pzfz_xh" style="padding: 4px;">${status.count }</label>行</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">往来对冲</span>
							<input type="text" class="form-control" id="txt_wldc" name="wldc" onkeyup="value=value.replace(/\W/,'')" value="${item.wldc } "/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">个&ensp;&ensp;&ensp;&ensp;人</span>
	                         <input type="text" class="form-control bg-white input-radius2" id="txt_zrr" name="zrr" readonly value="${item.zrr } "/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">往来单位</span>
	                         <input type="text" class="form-control bg-white input-radius2"  id="txt_wldw" name="wldw"  readonly value="${item.wldw } "> 
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">结算方式</span>
							 <input type="text" class="form-control bg-white input-radius2" readonly value="${item.jsfs } "/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">结算单号</span>
	                        <input type="text" id="txt_jsdh" class="form-control" name="jsdh"  onkeyup="value=value.replace(/\W/,'')" value="${item.jsdh }">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">对方单位</span>
							 <input type="text" class="form-control input-radius2 bg-white" id="txt_dfdw" name="dfdw" readonly value="${item.dfdw }"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算类型</span>
							<input type="text" class="form-control bg-white input-radius2" readonly value="${item.yslx }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">支出类型</span>
	                         <input type="text" class="form-control bg-white input-radius2" readonly value="${item.zclx }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算来源</span>
							<input type="text" class="form-control bg-white input-radius2" readonly value="${item.ysly }"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目名称</span>
							<input type="text" class="form-control" id="txt_kmmc" name="kmmc" readonly value="${item.kmmc }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">支出经济分类科目</span>
							<input type="text" class="form-control" id="txt_zcjjflkm" name="zcjjflkm" readonly value="${item.zcjjflkm }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目余额（元）</span>
							<input type="text" class="form-control text-right" id="txt_kmye" name="kmye" readonly value="${item.kmye }"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">部门名称</span>
							<input type="text" class="form-control" id="txt_bmmc" name="bmmc" readonly value="${item.bmmc }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
							<input type="text" class="form-control" id="txt_xmmc" name="xmmc" readonly value="${item.xmmc}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">收入科目</span>
							<input type="text" class="form-control" id="txt_srkm" name="srkm" readonly value="${item.srkm }"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">支出科目</span>
							<input type="text" class="form-control" id="txt_zckm" name="zckm" readonly value="${item.zckm }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目余额</span>
							<input type="text" class="form-control" id="txt_xmye" name="xmye" readonly value="${item.xmye }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">负责人</span>
							<input type="text" class="form-control" id="txt_fzr" name="fzr" readonly value="${item.fzr }"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目国库信息码</span>
							<input type="text" class="form-control" id="txt_xmgkxxm" name="xmgkxxm" readonly value="${item.xmgkxxm }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目类型</span>
							<input type="text" class="form-control" id="txt_xmlx" name="xmlx" readonly value="${item.xmlx }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
							<input type="text" class="form-control" id="txt_bz" name="bz" readonly value="${item.bz }"/>
						</div>
					</div>
				</div>
			</div>
		</div>
		</c:forEach>
		</form>
	</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var target = "${ctx}/kjhs/pzxx/pzlr";
var pzbh = "${pzbh}";
var pzz = "${pzz}";
var ztqj = "${ztxx.kjnd}-${ztxx.qjs}"
function switchPzlrmxShow(a_num){
	$("[data-pzfz]").hide();
	$("[data-pzfz='"+a_num+"']").show();
}
$(function(){
	$(document).ready(function(){
		$("input,select").attr("disabled","disabled");
	})
	//表格内文本框获取焦点事件
	$(document).on("focus","[data-col] input",function(e){
		switchPzlrmxShow($(this).parents("[data-line]").data("line"));
	});
	//切换凭证
	$("#txt_pzz").change(function(){
		var pzz = $(this).val();
		window.location.href = target+"/pageSkip?pageName=pzlr&pzz="+pzz;
	});
	$("#txt_pzbh").change(function(){
		var pzz = $("#txt_pzz").val();
		var pzbh = $(this).val();
		window.location.href = target+"/pageSkip?pageName=pzlr&pzz="+pzz+"&pzbh="+pzbh;
	});
	$("#btn_first,#btn_prev,#btn_next,#btn_last").click(function(){
		var pzz = $("#txt_pzz").val();
		var pzhb = $("#txt_pzbh").val();
		var type = $(this).data("type");
		window.location.href = target+"/pageSkip?pageName=pzlr&type="+type+"&pzz="+pzz+"&pzbh="+pzbh;
	})

})
</script>
</body>
</html>