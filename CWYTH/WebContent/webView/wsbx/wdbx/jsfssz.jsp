<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>结算方式设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.style_div{
		border: 1px solid #dddddd;
		border-radius: 4px;
		margin-top: 20px;
		padding-top: 5px;
		padding-left: 15px;
		padding-bottom: 10px;
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
  			border-bottom-width: 1px!important;
   	}
     table{
		border-collapse:collapse!important;
	}
	td{
		text-align: center;
	}
	.btn_td{
		text-align:center;
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
	.yc{
		display:none;
	}
	tbody input{
		border:none;
	}
	thead th{
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
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	    
	}
	[class^=addBtn]{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	[class^=addBtn]:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	[class^=deleteBtn]{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	[class^=deleteBtn]:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.text-green{
		color:green!important;
	}
	.tsinput{
		color:blue;
		border:none;
		padding-left:5px;
		font-size:18px!important;
	}
	.tyspan{
		color:black;
	}
	.noselect{
		font-size:14px;
		color:#CCCCCC;
	}
	.radiodiv{
	    border:none;
 	    border-radius:4px 0px 0px 4px;
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	    width:300px;
	}
	.box-bottom{
		text-align: center;
		padding-top: 20px;
		padding-bottom: 20px;
	}
	.bg-white{
		background-color: white!important;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="guid" id="txt_guid" value="${guid}" />
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>公务接待费报销申请</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
        				 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    				</div>
				</div>
				<div class="sub-title pull-left text-green">公务接待明细&nbsp;></div>
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        				 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    				</div>
				</div>
				<div class="sub-title pull-left text-primary" style="padding-right: 20px;">结算方式</div>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>第二步，选择结算方式
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
            		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
					</button>
			  		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			  		<button type="button" class="btn btn-default" id="btn_reset"><i class="fa icon-save"></i>重置</button>
			  		<button type="button" class="btn btn-default" id="btn_back">返回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号：</span>
                            <input type="text" id="txt_ywdh" name="djbh" class="form-control input-radius" readonly value="${gwjdmx.djbh}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销总金额(元)：</span>
							<input type="text" id="txt_ywzje" name="bxzje" class="form-control input-radius number" style="text-align:right;color:green;font-size:18px;" readonly value="${gwjdmx.bxje}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">结算总金额(元)：</span>
							<input type="text" id="txt_jszje" class="form-control input-radius number" style="text-align:right;color:green;font-size:18px;" readonly value=""/>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
		</form>
		<div class="box" id="operate">
			<div class="box-panel">	
	        <!-- 冲销借款 -->
	        <form id="cxForm">
	        <div class="style_div">
	        <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>冲借款
            	&nbsp;&nbsp;
            	<span class="noselect">
            		<input type="checkbox" class="cxjkbox" mc="addCjk" value="" />
            		<span>未选择</span>
            	</span>
            	&nbsp;&nbsp;
            	<span class="tyspan cx_xj"><strong>小计:</strong><input type="text" class="tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="cxjk"></i></a>
            	</div>
	        </div>
			<div class="container-fluid box-content cxjk-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">操作</th>
				            <th style="width:300px;">借款单号</th>
				            <th style="width:300px;">借款人</th>
				            <th style="width:300px;">所在部门</th>
				            <th style="width:100px;">借款金额(元)</th>
				            <th style="width:100px;">冲借款金额(元)</th>
				        </tr>
					</thead>
				    <tbody id="tb_cxjk">
				    	<c:forEach items="${cjkList}" var="cjkList" varStatus="cjk">
				    		<tr class="tr_${cjk.index+1}">
				    		<input type="hidden" name="start" value=""/>
							<td class="btn_td"><div class="deleteBtn_JK"></div></td>
							<td>
							<input type="hidden" name="jkdh" value="${guid}" />
							<input type="text" id="txt_jkdh${cjk.index+1}" name="djbh" class="form-control input-radius" readonly value="${cjkList.djbh}">
							</td>
							<td>
							<input type="text" id="txt_jkrxm${cjk.index+1}" class="form-control input-radius" readonly value="${cjkList.jkrxm}">
							<input type="hidden" id="txt_jkr${cjk.index+1}" name="jkr" class="form-control input-radius" readonly value="${cjkList.jkr}">
							</td>
							<td>
							<input type="text" id="txt_szdw${cjk.index+1}" class="form-control input-radius" readonly value="${cjkList.dwmc}">
							<input type="hidden" id="txt_szbm${cjk.index+1}" name="szbm" class="form-control input-radius" readonly value="${cjkList.szbm}">
							</td>
							<td>
							<input type="text" id="txt_jkje${cjk.index+1}" name="jkje" class="form-control input-radius number" readonly value="${cjkList.jkje}">
							</td>
							<td>
							<input type="text" id="txt_cjkje${cjk.index+1}" name="cjkje" class="form-control input-radius number"  value="${cjkList.cjkje}">
							</td>
							 <input type="hidden" name="end" value=""/>
						</tr>
				    	</c:forEach>
						<tr class="tr_0">
						 <input type="hidden" name="start" value=""/>
							<td class="btn_td"><div class="addBtn_JK"></div></td>
							<td>
							<input type="hidden" name="jkdh" value="${guid}" />
							<input type="text" id="txt_jkdh0" name="djbh" class="form-control input-radius" readonly value="">
							</td>
							<td>
							<input type="text" id="txt_jkrxm0" class="form-control input-radius" readonly value="">
							<input type="hidden" id="txt_jkr0" name="jkr" class="form-control input-radius" readonly value="">
							</td>
							<td>
							<input type="text" id="txt_szdw0" class="form-control input-radius" readonly value="">
							<input type="hidden" id="txt_szbm0" name="szbm" class="form-control input-radius" readonly value="">
							</td>
							<td>
							<input type="text" id="txt_jkje0" name="jkje" class="form-control input-radius number" readonly value="">
							</td>
							<td>
							<input type="text" id="txt_cjkje0" name="cjkje" class="form-control input-radius number"  value="">
							</td>
							 <input type="hidden" name="end" value=""/>
						</tr>
				    </tbody>
				</table>
			</div>
			</div>
			</form>
			<!-- 对私支付 -->
			<form id="dsForm">
			<div class="style_div">
			 <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>对私支付
            	&nbsp;&nbsp;
            	<span class="noselect">
            		<input type="checkbox" mc="addDs" class="sszkbox" value="" />
            		<span>未选择</span>
            	</span>
            	&nbsp;&nbsp;
            	<span class="tyspan sszk_xj"><strong>小计:</strong><input type="text" class="tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="sszk"></i></a>
            	</div>
	        </div>
	        <div class="container-fluid box-content sszk-content">
	       			<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">操作</th>
				            <th style="width:300px;">人员选择</th>
				            <th style="width:300px;">人员姓名</th>
				            <th style="width:500px;">卡类型/银行</th>
				            <th style="width:500px;">卡号</th>
				            <th style="width:100px;">金额</th>
				        </tr>
					</thead>
				    <tbody id="tb_sszk">
				    
				    	<c:forEach items="${dszfList}" var="dsList" varStatus="ds">
				    		<tr class="tr_${ds.index+1}">
				    		<input type="hidden" name="start" value=""/>
							<td class="btn_td"><div class="deleteBtn_ZK"></div></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='0'}">checked</c:if>
				    				class="form-control input-radius" value="0">个人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='1'}">checked</c:if>
				    				class="form-control input-radius" value="1">项目负责人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='2'}">checked</c:if>
				    				class="form-control input-radius" value="2">其他人
				    				<input type="hidden" name="zfdh" value="${guid}" />
				    		</div>
				    		</td>
				    		<td>
				    		<div class="input-group plugin">
				    			<input type="text" id="txt_ryxm${ds.index+1}" name="ryxm" class="form-control input-radius" value="${dsList.ryxm}" readonly>
				    			<span class="input-group-btn plugin">
				    			<button type="button" id="bbtn_ryxm${ds.index+1}" class="btn btn-link btn-custom <c:if test="${dsList.ryxz!='2'}">yc</c:if>">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_klx${ds.index+1}" name="klx" class="form-control input-radius" value="${dsList.klx}">
							</td>
							<td>
							<input type="text" id="txt_dfzh${ds.index+1}" name="dfzh" class="form-control input-radius" value="${dsList.dfzh}">
							</td>
							<td>
							<input type="text" id="txt_dsje${ds.index+1}" name="je" class="form-control input-radius number" value="${dsList.je}" 
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
							 <input type="hidden" name="end" value=""/>
						</tr>
				    	</c:forEach>
				    
						<tr class="tr_0">
							<input type="hidden" name="start" value=""/>
							<td class="btn_td"><div class="addBtn_ZK"></div></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" class="txt_ryxz0" name="ryxz0" class="form-control input-radius" value="0">个人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" class="txt_ryxz0" name="ryxz0" class="form-control input-radius" value="1">项目负责人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" class="txt_ryxz0" name="ryxz0" class="form-control input-radius" value="2">其他人
				    		</div>
				    		</td>
				    		<td>
				    		<div class="input-group">
				    			<input type="text" id="txt_ryxm0" name="ryxm" class="form-control input-radius" value="">
				    			<span class="input-group-btn">
				    			<input type="hidden" name="zfdh" value="${guid}" />
				    			<button type="button" id="bbtn_ryxm0" class="btn btn-link btn-custom yc">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_klx0" name="klx" class="form-control input-radius"  value="">
							</td>
							<td>
							<input type="text" id="txt_dfzh0" name="dfzh" class="form-control input-radius"  value="">
							</td>
							<td>
							<input type="text" id="txt_dsje0" name="je" class="form-control input-radius number"  value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
							<input type="hidden" name="end" value=""/>
						</tr>
						
				    </tbody>
				</table>
			</div>
			</div>
			</form>
			<!-- 对公支付 -->
			<form id="dgForm">
			<div class="style_div">
	        <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>对公支付
            	&nbsp;&nbsp;
            	<span class="noselect">
            		<input type="checkbox" mc="addDg" class="xnzzbox" value="1" />
            		<span>未选择</span>
            	</span>
            	&nbsp;&nbsp;
            	<span class="tyspan xnzz_xj"><strong>小计:</strong><input type="text" class="tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="xnzz"></i></a>
            	</div>
	        </div>
			<div class="container-fluid box-content xnzz-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">操作</th>
				            <th style="width:200px;">对方单位</th>
				            <th style="width:300px;">对方地区</th>
				            <th style="width:300px;">对方银行</th>
				            <th style="width:300px;">对方账号</th>
				            <th style="width:100px;">金额(元)</th>
				        </tr>
					</thead>
				    <tbody id="tb_xnzz">
				    	<c:forEach items="${dgzfList}" var="dgList" varStatus="dg">
				    		<tr class="tr_${dg.index+1}">
				    		<input type="hidden" name="start" value=""/>
							<td class="btn_td"><div class="deleteBtn_XN"></div></td>
							<td>
							<div class="input-group">
								<input type="hidden" name="zfdh" value="${guid}" />
								<input type="text" id="txt_dwmc${dg.index+1}"  name="dwmc" class="form-control input-radius"  value="${dgList.dwmc}">
				    			<input type="hidden" id="txt_dfdw${dg.index+1}" name="dfdw" class="form-control input-radius"  value="${dgList.wlbh}">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_dfdw${dg.index+1}" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_dfdq${dg.index+1}" name="dfdq" class="form-control input-radius" readonly value="${dgList.dfdq}">
							</td>
							<td>
							<select id="txt_dfyh${dg.index+1}" name="dfyh" dfyh="${dgList.dfyh}" class="form-control input-radius">
								<option value="">未选择</option>
								<c:forEach items="${yhList }" var="item">
									<option value="${item.DM }" <c:if test="${item.DM == dgList.dfyh }">selected</c:if> >${item.MC }</option>
								</c:forEach>
							</select>
							</td>
							<td>
							<input type="text" id="txt_dfzh${dg.index+1}" name="dfzh" readonly class="form-control input-radius"  value="${dgList.dfzh}">
							</td>
							<td>
							<input type="text" id="txt_je${dg.index+1}" name="je" class="form-control input-radius"  value="${dgList.je}">
							</td>
							<input type="hidden" name="end" value=""/>
						</tr>
				    	</c:forEach>
				    
						<tr class="tr_0">
						<input type="hidden" name="start" value=""/>
							<td class="btn_td"><div class="addBtn_XN"></div></td>
							<td>
							<div class="input-group">
								<input type="hidden" name="zfdh" value="${guid}" />
								<input type="text" id="txt_dwmc0" name="dwmc" class="form-control input-radius"  value="">
				    			<input type="hidden" id="txt_dfdw0" name="dfdw" class="form-control input-radius"  value="">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_dfdw0" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_dfdz0" name="dfdz" class="form-control input-radius" readonly value="">
							</td>
							<td>
							<select id="txt_dfyh0" name="dfyh" class="form-control input-radius">
								<option value="">未选择</option>
								<c:forEach items="${ yhList}" var="item">
									<option value="${item.DM }">${item.MC }</option>
								</c:forEach>
							</select>
							</td>
							<td>
							<input type="text" id="txt_dfzh0" name="dfzh" readonly class="form-control input-radius"  value="">
							</td>
							<td>
							<input type="text" id="txt_je0" name="je" class="form-control input-radius number"  value="">
							</td>
							<input type="hidden" name="end" value="" />
						</tr>
						
				    </tbody>
				</table>
			</div>
			</div>
			</form>
			<!-- 公务卡还款 -->
			<form id="gwkForm">
			<div class="style_div">
	        <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>公务卡
            	&nbsp;&nbsp;
            	<span class="noselect">
            		<input type="checkbox" mc="addGwk" class="gwkbox" value="" />
            		<span>未选择</span>
            	</span>
            	&nbsp;&nbsp;
            	<span class="tyspan gwk_xj"><strong>小计:</strong><input type="text" class="tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="gwk"></i></a>
            	</div>
	        </div>
			<div class="container-fluid box-content gwk-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">操作</th>
				            <th style="width:300px;">人员姓名</th>
				            <th style="width:220px;">刷卡日期</th>
				            <th style="width:200px;">刷卡金额(元)</th>
				            <th >卡号</th>
				        </tr>
					</thead>
				    <tbody id="tb_gwk">
				    	<c:forEach items="${gwkList}" var="gwk" varStatus="status">
				    		<tr class="tr_${gwk.index+1}">
				    		 <input type="hidden" name="start" value=""/>
							<td class="btn_td"><div class="deleteBtn_GWK"></div></td>
							<td>
							<div class="input-group">
								<input type="hidden" name="skdh" value="${guid}" />
				    			<input type="text" id="ttxt_ryxm${status.count}" name="ryxm" class="form-control input-radius" value="(${gwk.rybh})${gwk.ryxm}">
				    			<span class="input-group-btn">
				    			<button type="button" id="tbtn_ryxm${status.count}" class="btn btn-link btn-custom ry">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_skrq${status.count}" name="skrq" class="form-control input-radius date"  value="${gwk.skrq}">
							</td>
							<td>
							<input type="text" id="txt_skje${status.count}" name="skje" class="form-control input-radius number"  value="${gwk.skje}"
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
							<td>
							<input type="text" id="txt_kh${status.count}" name="kh" class="form-control input-radius"  value="${gwk.kh}">
							</td>
							 <input type="hidden" name="end" value=""/>
						</tr>
				    	</c:forEach>
				    	
						<tr class="tr_0">
						 <input type="hidden" name="start" value=""/>
							<td class="btn_td"><div class="addBtn_GWK"></div></td>
							<td>
							<div class="input-group">
				    			<input type="text" id="ttxt_ryxm0" name="ryxm" class="form-control input-radius" value="" placeholder="请选择人员姓名" readonly>
				    			<span class="input-group-btn">
				    			<button type="button" id="tbtn_ryxm0" class="btn btn-link btn-custom ry">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="hidden" name="skdh" value="${guid}" />
							<input type="text" id="txt_skrq0" name="skrq" class="form-control input-radius date"  value="">
							</td>
							<td>
							<input type="text" id="txt_skje0" name="skje" class="form-control input-radius number"  value=""
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
							<td>
							<input type="text" id="txt_kh0" name="kh" class="form-control input-radius"  value="">
							</td>
							<input type="hidden" name="end" />
						</tr>
				    </tbody>
				</table>
			</div>
			</div>
			<div class="box-bottom">
				<button type="button" id="btn_prev" class="btn btn-primary" >上一步</button>
				<button type="button" id="btn_next" class="btn btn-primary" >完成</button>
			</div>
			</form>
		</div>
	</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var target = "${ctx}/wsbx/gwjdfbx/gwjdfbxsq";
var guid = $("#txt_guid").val();
var today = "${today}";
var tag = true;
$(document).ready(function(){
	$("[name='ryxm'],[name='dwmc']").attr("readonly","readonly").addClass("bg-white");
	$(".number,.number1").blur();
})
$(function(){
	//控制显示
	kz();
	//返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/webView/wsbx/wdbx/kylbxwdbx_list.jsp"
	});
	$("#btn_prev").click(function(){
		
		window.location.href="${ctx}/kylbx/gwjdmxEdit?guid="+guid;
	});
	$("#btn_reset").click(function(){
		window.location.reload();
	});
	$("#btn_next,#btn_save").click(function(){
		var ywzje = parseFloat($("#txt_ywzje").val());
		var jszje = parseFloat($("#txt_jszje").val());
		if(ywzje==jszje&&ywzje!=""){
			doAddAll("finish");
		}else{
			alert("业务总金额必须等于结算总金额并且不为空！");
			return;
		}
	});
	
});
</script>
<!-- 各种计算js -->
<script type="text/javascript">
function checkRequired(){
	
}
//默认隐藏的元素
function kz(){
	//冲销
	if("${gwjdmx.sfcjk}" == "1"){
		$(".cxjk-content").css("display","none");
		$(".cxjkbox").click();
	}else{
		$(".cxjk-content").css("display","none");
	}
	//对私
	if("${gwjdmx.sfdszf}" == "1"){
		$(".sszk-content").css("display","none");
		$(".sszkbox").click();
	}else{
		$(".sszk-content").css("display","none");
	}
	//公务卡
	if("${gwjdmx.sfgwk}" == "1"){
		$(".gwk-content").css("display","none");
		$(".gwkbox").click();
	}else{
		$(".gwk-content").css("display","none");
	}
	//对公
	if("${gwjdmx.sfdgzf}" == "1"){
		$(".xnzz-content").css("display","none");
		$(".xnzzbox").click();
	}else{
		$(".xnzz-content").css("display","none");
	}
	//控制往来银行的显示
	$.each($("[id^=txt_dfdw]"),function(){
		var $this = $(this);
		var wlbh = $(this).val();
		var select = $this.parents("tr").find("select");
		if(wlbh!=""){
			select.empty();
			var dfyh = select.attr("dfyh");
			$.getJSON("${ctx}/wsbx/rcbx/dfyh","wlbh="+wlbh,function(m){
				if(m){
					$.each(m,function(i,v){
						if(i==0){
							var id = v.GUID;
							var mc = v.KHYH;
							var zh = v.KHYHZH;
							if(dfyh==id){
								select.append("<option value="+id+" zh"+zh+" selected>"+mc+"</option>");
							}else{
								select.append("<option value="+id+" zh"+zh+">"+mc+"</option>");
							}
						}
					});
				}
			});
		}
	});
}
//小计字体颜色的变化
function xjcolor(val,clw){
	if(isNaN(val)||val==""){
		val = 0;
	}
	if(val==0){
		$("[class*='"+clw+"']").find("input").css("color","blue");
	}
	if(val>0){
		$("[class*='"+clw+"']").find("input").css("color","green");
	}
	if(val<0){
		$("[class*='"+clw+"']").find("input").css("color","red");
	}
	jszje();
}
//计算结算总金额
function jszje(){
	var money = $(":checkbox").filter(":checked").parents("div").find("[class$=tsinput]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$("[id=txt_jszje]").val(count);
	if(count>0){
		$("[id=txt_jszje]").css("color","green");
	}else if(count<0){
		$("[id=txt_jszje]").css("color","red");
	}else{
		$("[id=txt_jszje]").css("color","blue");
	}
}
</script>
<!-- 冲销借款js -->
<script type="text/javascript">
//借款业务弹窗
$(document).on("click","[class=addBtn_JK]",function(){
	select_commonWin("${ctx}/wsbx/rcbx/window?controlId=jkyw","借款业务","920","630");
});

$(".cxjkbox").click(function(){
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parents(".style_div").find(".cxjk-content").slideDown(500);
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".cxjk-content:hidden").show();
		cxmoney();
	}else{
		$(this).parents(".style_div").find(".cxjk-content").slideUp(500);
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".cxjk-content:visible").hide();
		$(".cx_xj").find(".tsinput").val(Number("0").toFixed(2));
		xjcolor(0,"cx_xj");
	}
});
//冲销借款
var cxjk = "${csjkList.size()+1}";
function cxjkJs(guid){
	var parentTr = $("#tb_cxjk").find(".tr_0").clone();
	parentTr.attr("class","tr_"+cxjk);
	parentTr.find("#txt_cjkje0").val("");
	parentTr.find("[class^=addBtn]").removeClass("addBtn_JK").addClass("deleteBtn_JK");
	$.ajax({
		url:"${ctx}/wsbx/rcbx/cxjk",
		data:"guid="+guid,
		dataType:"json",
		success:function(data){
			parentTr.find("#txt_jkdh0").attr({"id":"txt_jkdh"+cxjk,"value":data.DJBH});
			parentTr.find("#txt_jkrxm0").attr({"id":"txt_jkrxm"+cxjk,"value":data.JKRXM});
			parentTr.find("#txt_jkr0").attr({"id":"txt_jkr"+cxjk,"value":data.JKR});
			parentTr.find("#txt_szdw0").attr({"id":"txt_szdw"+cxjk,"value":data.SZDW});
			parentTr.find("#txt_szbm0").attr({"id":"txt_szbm"+cxjk,"value":data.SZBM});
			parentTr.find("#txt_jkje0").attr({"id":"txt_jkje"+cxjk,"value":data.JKJE});
			parentTr.find("#txt_cjkje0").attr({"id":"txt_cjkje"+cxjk});
		}
	});
	$("#tb_cxjk").find(".tr_0").before(parentTr);
	cxjk++;
	cxmoney();
}
$(document).on("click",".deleteBtn_JK",function(){
	$(this).parents("tr").remove();
	cxmoney();
});
function cxmoney(){
	var money = $("[id^=txt_cjkje]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$(".cx_xj").find(".tsinput").val(count);
	xjcolor(count,"cx_xj");
}
$(document).on("keyup","[id^=txt_cjkje]",function(){
	var val = $(this).val();
	if(val==""||isNaN(val)){
		val = 0;
	}
	var jkje = $(this).parents("tr").find("[id^=txt_jkje]").val();
	if(Number(val)>Number(jkje)){
		alert("冲借款金额应该小于等于借款金额！");
		$(this).val("");
		return;
	}
	cxmoney();
});
</script>

<!-- 对私支付js -->
<script type="text/javascript">
//部门
$(document).on("click","[id^=btn_bm]",function(){
	var id = $(this).parents("td").find("[id^=txt_bm]").attr("id");
	select_commonWin("${ctx}/window/dwpage?controlId="+id,"部门信息","920","630");
});
$(document).on("click","[class*=txt_ryxz]",function(){
	var val = $(this).val();
	if(val=="0"){
		$(this).parents("tr").find("[id^=txt_ryxm]").val("${ryxm}");
		$(this).parents("tr").find("[id^=bbtn_ryxm]").addClass("yc");
	}else if(val=="1"){
		$(this).parents("tr").find("[id^=txt_ryxm]").val("${ryxm}");
		$(this).parents("tr").find("[id^=bbtn_ryxm]").addClass("yc");
	}else{
		$(this).parents("tr").find("[id^=txt_ryxm]").val("");
		$(this).parents("tr").find("[id^=bbtn_ryxm]").removeClass("yc");
	}
});
//人员弹窗
$(document).on("click","[id^=bbtn_ryxm]",function(){
	var id = $(this).parents("td").find("[id^=txt_ryxm]").attr("id");
	select_commonWin("${ctx}/window/rypage?controlId="+id,"人员信息","920","630");
});
$(".sszkbox").click(function(){
	$(this).parents(".style_div").find(".sszk-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".sszk-content:hidden").show();
		sszkmoney();
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".sszk-content:visible").hide();
		$(".sszk_xj").find(".tsinput").val(Number("0").toFixed(2));
		xjcolor(0,"sszk_xj");
	}
});
//
var sszk = "${dsList.size()+1}";
$(document).on("click",".addBtn_ZK",function(){
	var val = $(this).parents("tr").find("[name='ryxm']").val();
	if(val == ""){
		return;
	}
	var parentTr = $("#tb_sszk").find(".tr_0").clone();
	$("#tb_sszk").find(".tr_0").find(":text").val("");
	parentTr.attr("class","tr_"+sszk);
	parentTr.find(".addBtn_ZK").removeClass("addBtn_ZK").addClass("deleteBtn_ZK");
	parentTr.find("[class^=txt_ryxz]").attr({"class":"txt_ryxz"+sszk});
	parentTr.find("[class^=txt_ryxz]").attr({"name":"ryxz"+sszk});
	parentTr.find("[id^=txt_ryxm]").attr({"id":"txt_ryxm"+sszk});
	parentTr.find("[id^=txt_klx]").attr({"id":"txt_klx"+sszk});
	parentTr.find("[id^=bbtn_ryxm]").attr("id","bbtn_ryxm"+sszk);
	parentTr.find("[id^=txt_dfzh]").attr({"id":"txt_dfzh"+sszk});
	parentTr.find("[id^=txt_dsje]").attr({"id":"txt_dsje"+sszk});
	$("#tb_sszk").find(".tr_0").before(parentTr);
	sszk++;
});
$(document).on("click",".deleteBtn_ZK",function(){
	$(this).parents("tr").remove();
	sszkmoney();
});
//实时转卡输入
$(document).on("keyup","[id^=txt_dsje]",function(){
	sszkmoney();
});
//实时转卡金额计算
function sszkmoney(){
	var money = $("[id^=txt_dsje]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$(".sszk_xj").find(".tsinput").val(count);
	xjcolor(count,"sszk_xj");
}
</script>

<!-- 对公支付 -->
<script type="text/javascript">
$(document).on("change","[id^=txt_dfyh]",function(){
	var zh = $(this).find("option:selected").attr("zh");
	$(this).parents("tr").find("[id^=txt_dfzh]").val(zh);
});
//转账弹窗
$(document).on("click","[id^=btn_dfdw]",function(){
	var id = $(this).attr("id");
	select_commonWin("${ctx}/wsbx/rcbx/window?controlId=xnzz&id="+id,"往来单位","920","630");
});
$(".xnzzbox").click(function(){
	$(this).parents(".style_div").find(".xnzz-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".xnzz-content:hidden").show();
		xnzzmoney();
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".xnzz-content:visible").hide();
		$(".xnzz_xj").find(".tsinput").val(Number(0).toFixed(2));
		xjcolor(0,"xnzz_xj");
	}
});
var xnzz = "${wldwList.size()+1}";
$(document).on("click","[class*=addBtn_XN]",function(){
	var val = $(this).parents("tr").find("[name='dwmc']").val();
	if(val == ""){
		return;
	}
	var parentTr = $("#tb_xnzz").find(".tr_0").clone();
	$("#tb_xnzz").find(".tr_0").find("input").val("");
	$("#tb_xnzz").find(".tr_0").find("select").empty();
	parentTr.attr("class","tr_"+xnzz);
	parentTr.find("[class^=addBtn]").removeClass("addBtn_XN").addClass("deleteBtn_XN");
	parentTr.find("#txt_dfdw0").attr({"id":"txt_dfdw"+xnzz});
	parentTr.find("#txt_dwmc0").attr({"id":"txt_dwmc"+xnzz});
	parentTr.find("#btn_dfdw0").attr({"id":"btn_dfdw"+xnzz});
	parentTr.find("#txt_dfdz0").attr({"id":"txt_dfdz"+xnzz});
// 	parentTr.find("#txt_dfyh0").empty();
	parentTr.find("#txt_dfyh0").attr({"id":"txt_dfyh"+xnzz});
	parentTr.find("#txt_dfzh0").attr({"id":"txt_dfzh"+xnzz});
	parentTr.find("#txt_je0").attr({"id":"txt_je"+xnzz});
	$("#tb_xnzz").find(".tr_0").before(parentTr);
	xnzz++;
	xnzzmoney();
});
function xnzzJs(wlbh,dwmc,dwdz,id){
	var parentTr = $("[id='"+id+"']").parents("tr");
	$.ajax({
		url:"${ctx}/wsbx/rcbx/dfyh",
		data:"wlbh="+wlbh,
		dataType:"json",
		async:false,
		success:function(datas){
			parentTr.find("[id^=txt_dfyh]").empty();
			if(datas){
				parentTr.find("[id^=txt_dfyh]").append("<option value=''>请选择...</option>");
				$.each(datas,function(i,v){
					var id = v.GUID;
					var mc = v.KHYH;
					var zh = v.KHYHZH;
					parentTr.find("[id^=txt_dfdw]").val(wlbh);
					parentTr.find("[id^=txt_dwmc]").val(dwmc);
					parentTr.find("[id^=txt_dfdz]").val(dwdz);
					parentTr.find("[id^=txt_dfyh]").append("<option value="+id+" zh="+zh+">"+mc+"</option>");
				});
			}
		}
	});
}
$(document).on("click",".deleteBtn_XN",function(){
	$(this).parents("tr").remove();
	xnzzmoney();
});
$(document).on("keyup","[id^=txt_je]",function(){
	xnzzmoney();
});
function xnzzmoney(){
	var money = $("[id^=txt_je]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$(".xnzz_xj").find(".tsinput").val(count);
	xjcolor(count,"xnzz_xj"); 
}
</script>
<!-- 公务卡js -->
<script type="text/javascript">
$(document).on("click","[id^=tbtn_ryxm]",function(){
	var id = $(this).parents("tr").find("[id^=ttxt_ryxm]").attr("id");
	select_commonWin("${ctx}/window/rypage?controlId="+id,"人员信息","920","630");
});

$(".gwkbox").click(function(){
	$(this).parents(".style_div").find(".gwk-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".gwk-content:hidden").show();
		gwkmoney();
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".gwk-content:visible").hide();
		$(".gwk_xj").find(".tsinput").val(Number(0).toFixed(2));
		xjcolor(0,"gwk_xj");
	}
});

var gwk = "${gwkList.size()+1}";
function gwkJs(){
	var parentTr = $("#tb_gwk").find(".tr_0").clone();
	parentTr.attr("class","tr_"+gwk);
	parentTr.find("[class^=addBtn]").removeClass("addBtn_GWK").addClass("deleteBtn_GWK");
	parentTr.find("#ttxt_ryxm0").attr({"id":"ttxt_ryxm"+gwk});
	parentTr.find("#tbtn_ryxm0").attr({"id":"tbtn_ryxm"+gwk});
	parentTr.find("#txt_skrq0").attr({"id":"txt_skrq"+gwk});
	parentTr.find("#txt_skje0").attr({"id":"txt_skje"+gwk});
	parentTr.find("#txt_kh0").attr({"id":"txt_kh"+gwk});
	parentTr.find(":input").val("");
	$("#tb_gwk").find(".tr_0").before(parentTr);
	gwk++;
	gwkmoney();
}
$(document).on("keyup","[id^=txt_skje]",function(){
	gwkmoney();
});
$(document).on("click",".addBtn_GWK",function(){
	var ryxm = $(this).parents("tr").find("name='ryxm'").val();
	if(ryxm == ""){
		return;
	}
	gwkJs();
});
$(document).on("click",".deleteBtn_GWK",function(){
	$(this).parents("tr").remove();
	gwkmoney();
});
function gwkmoney(){
	var money = $("[id^=txt_skje]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$(".gwk_xj").find(".tsinput").val(count);
	xjcolor(count,"gwk_xj"); 
}
$("#btn_ywsm").click(function(){
	zysx();
});
function zysx(){
	select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
}
</script>
<script type="text/javascript">
	function doAddAll(type){
		var checkBox = $("[class$=box]").find(":checkbox").filter(":checked");
		if(checkBox.length==0){
			alert("请选择支付方式！");
			return;
		}
		$("[name^=ryxz]").attr("name","ryxz");
		var formJson = arrayToJson($("#myform").serializeArray());
		$.each(checkBox,function(i,v){
			var $this = $(this);
			var id = $this.parents("form").attr("id");
			var json = $("#"+id+"").serializeObject("start","end");  //json对象
			var jsonStr = JSON.stringify(json);
			formJson[id] = jsonStr;
		});
		$.ajax({
  			url:target+"/updateJsfs",
  			data: formJson,
  			type:"post",
  			success:function(data){
  				var result = JSON.getJson(data);
  				if(result.success){
  					alert("信息保存成功！");

  					location.href="${ctx}/webView/wsbx/wdbx/kylbxwdbx_list.jsp"
  				}
  			}
  		});
	}
	$("[name='skrq']").blur(function(){
		var date = $(this).val();
		if(date > today){
			alert("刷卡日期不能大于当前日期！");
			$(this).val("");
		}
	})
</script>
</html>