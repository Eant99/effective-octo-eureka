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
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	[class^=addBtn]:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	}
	[class^=deleteBtn]{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	[class^=deleteBtn]:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
	    position: relative;
	    cursor: pointer;
/* 	    top:3px; */
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
	tr{
     background-color: white !important;
    }
   /*  #tb_xnzz,#tb_sszk input not .aa{
		border:none;
		width:100%;
	} */
	.bk{
		border:none;
		width:100%;
	}
	tbody td{
	   padding:4px !important;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="zbid" id="dual" value="${zbid}" />
	<input type="hidden" name="sfbj" id="sfbj" value="${sfbj}" />
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>第三步,结算方式设置</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
		 <div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-green">选择出差业务&nbsp;></div>
					
<!-- 				    <div class="sub-title pull-left text-green"> -->
<!-- 					<div style="width:26px; height:26px; background-color:green; border-radius:13px;"> -->
<!-- 	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span> -->
<!-- 	    			</div> -->
<!-- 					</div> -->
<!-- 					<div class="sub-title pull-left text-green">选择项目&nbsp;></div> -->
					
					<div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-green">差旅报销业务办理&nbsp;></div>
					
					<div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
	    			</div>
					</div>
					
					<div class="sub-title pull-left text-primary">结算方式设置&nbsp;</div>
					
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>基本信息
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
<!-- 			  		<button type="button" class="btn btn-default" id="btn_reset"><i class="fa icon-save"></i>重置</button> -->
			  		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">业务单号：</span>
                            <input type="text" id="txt_ywdh" class="form-control input-radius" readonly value="${djbh}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销总金额(元)：</span>
							<input type="" id="txt_ywzje" class="form-control input-radius number" style="text-align:right;color:green;font-size:18px;" readonly value="${bxzje}"/>
							<input type="hidden" id="txt_bxzje" class="form-control input-radius number" style="text-align:right;color:green;font-size:18px;" readonly value="${map.BXZJE}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">结算总金额(元)：</span>
							<input type="" id="txt_jszje" class="form-control input-radius number" style="text-align:right;color:green;font-size:18px;" readonly value=""/>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
		</form>
		<div class="box" id="operate">
			<div class="box-panel">	
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary" style="font-size:15px;"><i class="fa icon-xiangxixinxi" style="font-size:20px;"></i>结算方式<span style="color:green;">(可多选)</span>
            	</div>
	        </div>
	        <hr class="hr-normal">
	        <!-- 冲销借款 -->
	        <form id="cxForm">
	      
	        <div class="style_div">
	        <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
<!--             	<i class="fa icon-xiangxi"></i> -->
				<span class="noselect">
            		<input type="checkbox" class="cxjkbox" mc="addCjk" value="" />
<!--             		<span>未选择</span> -->
            	</span>
            	冲借款
            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	
            	&nbsp;&nbsp;
            	<span class="tyspan cx_xj"><strong>小计（元）:</strong><input type="" class="cjkxj tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="cxjk"></i></a>
            	</div>
	        </div>
			<div class="container-fluid box-content cxjk-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:5%;">操作</th>
				            <th style="width:20%;">借款单号</th>
				            <th style="width:20%;">借款人</th>
				            <th style="width:20%;">所在部门</th>
				            <th style="width:20%;">借款金额(元)</th>
				            <th style="width:15%;">冲借款金额(元)</th>
				        </tr>
					</thead>
				    <tbody id="tb_cxjk">
				    	<c:forEach items="${cjkList}" var="cjkList" varStatus="cjk">
				    	 
				    		<tr class="tr_${cjk.index+1}">
							<td class="btn_td"><div class="deleteBtn_JK"></div></td>
							<td>
							 <input type="hidden" name="start" value=""/>
							<input type="hidden" name="jkdh" value="${zbid}" />
							<input type="text" id="txt_jkdh${cjk.index+1}" name="djbh" style="background-color: white !important;" class="bk input-radius cjkyz1" readonly value="${cjkList.djbh}">
							</td>
							<td>
							<input type="text" id="txt_jkrxm${cjk.index+1}" style="background-color: white !important;" class="bk input-radius cjkyz2" readonly value="${cjkList.jkrxm}">
							<input type="hidden" id="txt_jkr${cjk.index+1}" name="jkr" class="form-control input-radius" readonly value="${cjkList.jkr}">
							</td>
							<td>
							<input type="text" id="txt_szdw${cjk.index+1}" style="background-color: white !important;" class="bk input-radius cjkyz3" readonly value="${cjkList.dwmc}">
							<input type="hidden" id="txt_szbm${cjk.index+1}" name="szbm" class="form-control input-radius" readonly value="${cjkList.szbm}">
							</td>
							<td>
							<input type="text" id="txt_jkje${cjk.index+1}" name="jkje" style="background-color: white !important;" class="bk input-radius number je cjkyz4" readonly value="${cjkList.jkje}">
							</td>
							<td>
							<input type="text" id="txt_cjkje${cjk.index+1}" name="cjkje" class="bk input-radius number cjkje"  value="${cjkList.cjkje}">
							<input type="hidden" name="end" value=""/>
							</td>
							
						</tr>
						
				    	</c:forEach>
				    	  
						<tr class="tr_0">
						<input type="hidden" name="start" value=""/>
							<td class="btn_td"><div class="addBtn_JK"></div></td>
							<td>
							
							<input type="hidden" name="jkdh" value="${zbid}" />
							<input type="text" id="txt_jkdh0" name="djbh" style="background-color: white !important;" class="bk input-radius cjkyz1" readonly value="">
							</td>
							<td>
							<input type="text" id="txt_jkrxm0" style="background-color: white !important;" class="bk input-radius cjkyz2" readonly value="">
							<input type="hidden" id="txt_jkr0" name="jkr" class="form-control input-radius" readonly value="">
							</td>
							<td>
							<input type="text" id="txt_szdw0" style="background-color: white !important;" class="bk input-radius cjkyz3" readonly value="">
							<input type="hidden" id="txt_szbm0" name="szbm" class="form-control input-radius" readonly value="">
							</td>
							<td>
							<input type="text" id="txt_jkje0" style="background-color: white !important;" name="jkje" class="bk input-radius number cjkyz4" readonly value="">
							</td>
							<td>
							<input type="text" id="txt_cjkje0" name="cjkje" class="bk input-radius number  je cjkje"  value="">
							
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
            	<div class="sub-title pull-left text-primary">
<!--             	<i class="fa icon-xiangxi"></i> -->
            	<span class="noselect">
            		<input type="checkbox" mc="addDs" class="sszkbox" value="" />
<!--             		<span>未选择</span> -->
            	</span>
            	对私支付
            	&nbsp;&nbsp;
            	&nbsp;&nbsp;
            	<span class="tyspan sszk_xj"><strong>小计（元）:</strong><input type="" class="dszfxj tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="sszk"></i></a>
            	</div>
	        </div>
	        <div class="container-fluid box-content sszk-content">
	       			<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:5%;">操作</th>
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
							<td class="btn_td"><div class="deleteBtn_ZK"></div></td>
							<td>&nbsp;&nbsp;
							  <input type="hidden" name="start" value=""/>
				    			<input type="radio" disabled="disabled" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='0'}">checked</c:if>
				    				class="form-control input-radius" value="0">个人&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" disabled="disabled" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='1'}">checked</c:if>
				    				class="form-control input-radius" value="1">项目负责人&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" disabled="disabled" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='2'}">checked</c:if>
				    				class="form-control input-radius" value="2">其他人
				    				<input type="hidden" name="zfdh" value="${zbid}" />
				    				<input type="hidden" name="ryxz" value="${dsList.ryxz}" />
				    		</div>
				    		</td>
				    		<td>
				    		<div class="input-group" style="width:100%;" >
<%-- 				    			<input type="" id="txt_ryxm${ds.index+1}" name="ryxm" class="form-control input-radius" value="${dsList.ryxm}"> --%>
<%-- 				    			<span class="input-group-btn <c:if test="${dsList.ryxz!='2'}">yc</c:if>"> --%>
<%-- 				    			<button type="button" id="bbtn_ryxm${ds.index+1}" class="btn btn-link btn-custom ">选择</button> --%>
<!-- 				    			</span> -->
								<input type="text" id="txt_ryxm${ds.index+1}" name="ryxm" class="bk input-radius dszfyz1" style="height:27px;" value="${dsList.ryxm}">
				    			<span class="input-group-btn ">
				    			<button type="button" id="bbtn_ryxm${ds.index+1}" class="btn btn-link btn-custom <c:if test="${dsList.ryxz=='0'}">yc</c:if>">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
<%-- 							<input type="text" id="txt_klx${ds.index+1}" name="klx" class="bk input-radius" value="${dsList.klx}"> --%>
								<select id="txt_klx${dg.index+1}" name="klx" disabled="disabled" class="bk input-radius dszfyz2">
										<c:forEach items="${dlryhklist}" var="item">
										<option value="${item.khyh}" zh="${item.khyhzh}" zhbguid="${item.guid }"<c:if test="${item.khyhzh == dsList.dfzh }">selected</c:if>>${dsList.klx}</option>
										</c:forEach>
								</select>
							</td>
							<td>
							<input type="text" id="txt_dfzh${ds.index+1}" name="dfzh" class="bk input-radius null dszfyz3" readonly="readonly" value="${dsList.dfzh}">
							<input type="hidden" id="txt_guid${ds.index+1}" name="zhbguid" class="bk input-radius null" readonly="readonly" value="${dsList.zhbguid}">
							</td>
							<td>
							<input type="text" id="txt_dsje${ds.index+1}" name="je" class="bk input-radius number  je dszfje" value="${dsList.je}" 
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
									<input type="hidden" name="end" />
							</td>
						</tr>
				    	</c:forEach>
				    
						<tr class="tr_0">
							<td class="btn_td"><div class="addBtn_ZK"></div></td>
							<td>&nbsp;&nbsp;
							  	<input type="hidden" name="start" value=""/>
				    			<input type="hidden" name="ryxz" value="0"/>
				    			<input type="radio" class="txt_ryxz0" name="ryxz0" class="form-control input-radius"  value="0">个人&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" class="txt_ryxz0" name="ryxz0" class="form-control input-radius" checked value="1">项目负责人&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" class="txt_ryxz0" name="ryxz0" class="form-control input-radius"  value="2">其他人
				    		</div>
				    		</td>
				    		<td>
				    		<div class="input-group" style="width:100%;">
<%-- 				    			<input type="" id="txt_ryxm0" name="ryxm" class="form-control input-radius" value="${loginInfo}"> --%>
<!-- 				    			<span class="input-group-btn  yc"> -->
<%-- 				    			<input type="hidden" name="zfdh" value="${zbid}" /> --%>
<!-- 				    			<button type="button" id="bbtn_ryxm0" class="btn btn-link btn-custom">选择</button> -->
<!-- 				    			</span> -->
<%-- 								<input type="" id="txt_ryxm0" name="ryxm" class="bk input-radius " style="height:27px;" value="${loginInfo}"> --%>
								<input type="text" id="txt_ryxm0" name="ryxm" class="bk input-radius dszfyz1" style="height:27px;" value="">
				    			<span class="input-group-btn">
				    			<input type="hidden" name="zfdh" value="${zbid}" />
				    			<button type="button" id="bbtn_ryxm" class="btn btn-link btn-custom  bbtn_ryxm0">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
<!-- 							<input type="text" id="txt_klx0" name="klx" class="bk input-radius"  value=""> -->
								<select id="txt_klx0" name="klx"  class="bk input-radius dszfyz2">
							</td>
							<td>
							<input type="text" id="txt_dfzh0" name="dfzh" class="bk input-radius null dszfyz3" readonly="readonly" value="">
							<input type="hidden" id="txt_guid0" name="zhbguid" class="bk input-radius null" readonly="readonly" value="">
							</td>
							<td>
							<input type="text" id="txt_dsje0" name="je" class="bk input-radius number  je dszfje"  value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								<input type="hidden" name="end" />
							</td>
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
            	<div class="sub-title pull-left text-primary">
<!--             	<i class="fa icon-xiangxi"></i> -->
            	<span class="noselect">
            		<input type="checkbox" mc="addDg" class="xnzzbox" value="1" />
<!--             		<span>未选择</span> -->
            	</span>
            	对公支付
            	&nbsp;&nbsp;
            	&nbsp;&nbsp;
            	<span class="tyspan xnzz_xj"><strong>小计（元）:</strong><input type="" class="dgzfxj tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="xnzz"></i></a>
            	</div>
	        </div>
			<div class="container-fluid box-content xnzz-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:5%;">操作</th>
				            <th style="width:20%;">对方单位</th>
				            <th style="width:20%;"><span style="color:red">*</span>对方地区</th>
				            <th style="width:20%;">对方银行</th>
				            <th style="width:20%;">对方账号</th>
				            <th style="width:15%;">金额(元)</th>
				        </tr>
					</thead>
				    <tbody id="tb_xnzz">
				    	<c:forEach items="${dgList}" var="dgList" varStatus="dg">
				    		<tr class="tr_${dg.index+1}">
							<td class="btn_td"><div class="deleteBtn_XN"></div></td>
							<td>
							<div class="input-group" style="width:100%;">
							   <input type="hidden" name="start" value=""/>
								<input type="hidden" name="zfdh" value="${zbid}" />
								<input type="text" id="txt_dwmc${dg.index+1}" name="dfdw1" class="bk input-radius dgzfyz1"  value="${dgList.dwmc}">
				    			<input type="hidden" id="txt_dfdw${dg.index+1}" name="dfdw" class="form-control input-radius"  value="${dgList.wlbh}">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_dfdw${dg.index+1}" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_dfdz${dg.index+1}" style="background-color: white !important;" name="dfdq" class="bk input-radius dgzfyz2" readonly value="${dgList.dfdq}">
							</td>
							<td>
							<select id="txt_dfyh${dg.index+1}" name="dfyh" dfyh="${dgList.dfyh}" class="bk input-radius dgzfyz3">
							</select>
							</td>
							<td>
							<input type="text" id="txt_dfzh${dg.index+1}" style="background-color: white !important;" name="dfzh" readonly class="bk input-radius dgzfyz4"  value="${dgList.dfzh}">
							</td>
							<td>
							<input type="text" id="txt_je${dg.index+1}" name="je" class="bk input-radius number  je dgzfje"  value="${dgList.je}">
							<input type="hidden" name="end" value="" />
							</td>
							
						</tr>
				    	</c:forEach>
				    
						<tr class="tr_0">
							<td class="btn_td"><div class="addBtn_XN"></div></td>
							<td>
							<div class="input-group" style="width:100%;">
							   <input type="hidden" name="start" value=""/>
								<input type="hidden" name="zfdh" value="${zbid}" />
								<input type="text" name="dfdw1" id="txt_dwmc0" class="bk input-radius dgzfyz1"  value="">
				    			<input type="hidden" id="txt_dfdw0" name="dfdw" class="form-control input-radius"  value="">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_dfdw0" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_dfdz0" style="background-color: white !important;" name="dfdz" class="bk input-radius dgzfyz2" readonly value="">
							</td>
							<td>
							<select id="txt_dfyh0" name="dfyh"  class="bk input-radius dgzfyz3">
							</select>
							</td>
							<td>
							<input type="text" id="txt_dfzh0" name="dfzh" style="background-color: white !important; " readonly class="bk input-radius dgzfyz4"  value="">
							</td>
							<td>
							<input type="text" id="txt_je0" name="je" class="bk input-radius number  je dgzfje"  value="">
							<input type="hidden" name="end" value="" />
							</td>
							
						</tr>
						
				    </tbody>
				</table>
			</div>
			</div>
			
			</form>
			<!-- 公务卡还款 -->
			<form id="gwForm">
	     
			<div class="style_div">
	        <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
<!--             	<i class="fa icon-xiangxi"></i> -->
            	<span class="noselect">
            		<input type="checkbox" mc="addGwk" class="gwkbox" value="" />
<!--             		<span>未选择</span> -->
            	</span>
            	公务卡
            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	&nbsp;&nbsp;
            	<span class="tyspan gwk_xj"><strong>小计（元）:</strong><input type="" class="gwkxj tsinput" readonly value="0.00" /></span>
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="gwk"></i></a>
            	</div>
	        </div>
			<div class="container-fluid box-content gwk-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:5%;">操作</th>
				            <th style="width:20%;">人员姓名</th>
				            <th style="width:20%;">刷卡日期</th>		          
				            <th style="width:20%;">卡号</th>
				            <th style="width:15%;">报销金额(元)</th>
				            <th style="width:15%;">刷卡金额(元)</th>
				        </tr>
					</thead>
				    <tbody id="tb_gwk">
				    	<c:forEach items="${gwkList}" var="gwkList" varStatus="gwk">
				    		<tr class="tr_${gwk.index+1}"> 
							<td class="btn_td"><div class="deleteBtn_GWK"></div></td>
							<td>
							<div class="input-group">
							 <input type="hidden" name="start" value=""/>
								<input type="hidden" name="skdh" value="${zbid}" />
				    			<input type="text" id="ttxt_ryxm${gwk.index+1}" name="ryxm" class="bk input-radius gwkyz1" value="${gwkList.ryxm}">
				    			<span class="input-group-btn">
				    			<button type="button" id="tbtn_ryxm${gwk.index+1}" class="btn btn-link btn-custom ry">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_skrq${gwk.index+1}" style="background-color: white !important;" name="skrq" class="bk input-radius date gwkyz2"  readonly value="${gwkList.skrq}">
							</td>
							
							<td>
							<input type="text" id="txt_kh${gwk.index+1}" name="kh" class="bk input-radius int gwkyz3"  value="${gwkList.kh}">
							</td>
							<td>
							<input type="text" id="txt_skje${gwk.index+1}" name="skje" class="bk input-radius number  je gwkje"  value="${gwkList.skje}"
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
							<td>
							<input type="text" id="txt_sjskje${gwk.index+1}" name="sjskje" class="bk input-radius number  je gwkyz4"  value="${gwkList.sjskje}"
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							<input type="hidden" name="end" />
							</td>
						</tr>
				    	</c:forEach>
				    	
						<tr class="tr_0">
						<input type="hidden" name="start" value=""/>
							<td class="btn_td"><div class="addBtn_GWK"></div></td>
							<td>
							<div class="input-group">
				    			<input type="text" id="ttxt_ryxm0" name="ryxm" class="bk input-radius gwkyz1" value="">
				    			<span class="input-group-btn">
				    			<button type="button" id="tbtn_ryxm0" class="btn btn-link btn-custom ry">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							   
							<input type="hidden" name="skdh" value="${zbid}" />
							<input type="text" id="txt_skrq0" name="skrq" class="bk input-radius date gwkyz2"  value="">
							</td>
							
							<td>
							<input type="text" id="txt_kh0" name="kh" class="bk input-radius int gwkyz3"  value="">
							<input type="hidden" name="end" />
							</td>
							<td>
							<input type="text" id="txt_skje0" name="skje" class="bk input-radius number je gwkje"  value=""
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
							<td>
							<input type="text" id="txt_sjskje0" name="sjskje" class="bk input-radius number je gwkyz4"  value=""
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>
						</tr>
				    </tbody>
				</table>
			</div>
			</div>
			
			</form>
		</div>
		<div class="container-fluid box-content">
			<div class="row">
				<div class="pull-center" style="text-align:center;">
		    		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
		    		<button type="button" class="btn btn-default" id="btn_next" style="background:#00acec;color: white;">完成</button>
		    		<button type="button" class="btn btn-default" id="btn_second" style="background:#00acec;color: white;">保存并提交</button>
		 		</div>
			</div>
		</div>
	</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var tag = true;
$(function(){
	//控制显示
	kz();
	kzje();
	//返回按钮
	$("#btn_back").click(function(e){
		if("${look}"=="look"){
			location.href="${ctx}/kylbx/gowdbxListPage";
		}else{
			location.href="${ctx}/wsbx/ccbx/goCcbxListPage?mkbh=${param.mkbh}";
		}
		
	});
	$("#btn_after").click(function(){
		var checkBox = $("[class$=box]").find(":checkbox").filter(":checked");
// 		if(checkBox.length==0){
// 			alert("请选择支付方式！");
// 			return;
// 		}
		doAddAll("after");
	});
	$("#btn_reset").click(function(){
		var url = window.location.href;
		if(url.indexOf("?") > 0) {
			window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016";
		} else {
			window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016";
		}
		return;
	});
	$("#btn_next").click(function(){
		checkAutoInput();
		var ywzje = $("#txt_ywzje").val();
		var jszje = $("#txt_jszje").val();
// 		var kyje = "${kyje}";//可用金额
		var kyje = $("#txt_bxzje").val();
		var cjkje = sumCjkje();//冲借款金额
		if(ywzje==jszje&&ywzje!=""){
			if((parseFloat(jszje)-parseFloat(cjkje))<=parseFloat(kyje)){
				doAddAll("next");
			}else{
				alert("报销总金额要小于等于可用金额！");
				return;
			}
		}else{
			alert("业务总金额必须等于结算总金额并且不为空！");
			return;
		}
	});
	$("#btn_second").click(function(){//保存并提交
		checkAutoInput();
		var ywzje = $("#txt_ywzje").val();
		var jszje = $("#txt_jszje").val();
// 		var kyje = "${kyje}";//可用金额
		var kyje = $("#txt_bxzje").val();
		var cjkje = sumCjkje();//冲借款金额
		if(ywzje==jszje&&ywzje!=""){
			if((parseFloat(jszje)-parseFloat(cjkje))<=parseFloat(kyje)){
				seconddoAddAll("next");
			}else{
				alert("报销总金额要小于等于可用金额！");
				return;
			}
		}else{
			alert("业务总金额必须等于结算总金额并且不为空！");
			return;
		}
	});
	//保存按钮
	$("#btn_save").click(function(e){
		checkAutoInput();
		console.log("tag+++"+tag);
		var ywzje = $("#txt_ywzje").val();
		var jszje = $("#txt_jszje").val();
// 		var kyje = "${kyje}";//可用金额
		var kyje = $("#txt_bxzje").val();
		console.log("-----------====="+kyje);
		var cjkje = sumCjkje();//冲借款金额
		console.log("tag========="+tag);
		if(ywzje==jszje&&ywzje!=""){
			if((parseFloat(jszje)-parseFloat(cjkje))<=parseFloat(kyje)){
				doAddAll("save");
			}else{
				alert("报销总金额要小于等于可用金额！");
				return;
			}
		}else{
			alert("业务总金额必须等于结算总金额并且不为空！");
			return;
		}
	});	
	function checkAutoInput(){
		tag = true;
		var checkBox = $("[class$=box]").find(":checkbox").filter(":checked");
		var ys = checkBox.parents("form").find(".null");
		var value = "";
		$.each(ys,function(){
			value = $(this).val();
			if(value==""){
				console.log($(this).attr("name"));
				//$(this).addClass("border");
				tag = false;
			}
		});
	}
	$(document).on("focus","[class*=border]",function(){
		//$(this).removeClass("border");
	});
});

</script>
<!-- 各种计算js -->
<script type="text/javascript">
function kzje(){
	if($("[mc='addDs']").is(":checked")){
		var s1 = Number($(".dszfxj").val())-Number($("#txt_dsje0").val());
		$(".dszfxj").val((Number(s1)).toFixed(2));
		$("#txt_dsje0").val("");
	}
	if($("[mc='addDg']").is(":checked")){
		var s1 = Number($(".dgzfxj").val())-Number($("#txt_je0").val());
		$(".dgzfxj").val((Number(s1)).toFixed(2));
		$("#txt_je0").val("");
	}
	if($("[mc='addGwk']").is(":checked")){
		var s1 = Number($(".gwkxj").val())-Number($("#txt_sjskje0").val());
		$(".gwkxj").val((Number(s1)).toFixed(2));
		$("#txt_sjskje0").val("");
	}
	
}
//默认隐藏的元素
function kz(){
	//冲销
	if("${map.SFCJK}"=="1"){
		$(".cxjk-content").css("display","none");
		$(".cxjkbox").click();
	}else{
		$(".cxjk-content").css("display","none");
	}
	//对私
	if("${map.sfdszf}"=="1"){
		$(".sszk-content").css("display","none");
		$(".sszkbox").click();
	}else{
		$(".sszk-content").css("display","none");
	}
	//公务卡
	if("${map.SFGWK}"=="1"){
		$(".gwk-content").css("display","none");
		$(".gwkbox").click();
	}else{
		$(".gwk-content").css("display","none");
	}
	//对公
	if("${map.SFDGZF}"=="1"){
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
//结算冲借款金额
function sumCjkje(){
	debugger;
	var cjkje = $(".cjkje");
	var sumCjkje = 0;
	$.each(cjkje,function(){
		if($(this).val!=""||!isNaN($(this).val())){
			sumCjkje=Number(sumCjkje)+Number($(this).val());
		}
	});
	return sumCjkje;
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
	var jkdh = $(this).parents("tr").find("[id^='txt_jkdh']").attr("id");
	var jkrxm = $(this).parents("tr").find("[id^='txt_jkrxm']").attr("id");
	var jkr = $(this).parents("tr").find("[name='jkr']").attr("id");
	var szdw = $(this).parents("tr").find("[id^='txt_szdw']").attr("id");
	var szbm = $(this).parents("tr").find("[id^='txt_szbm']").attr("id");
	var jkje = $(this).parents("tr").find("[id^='txt_jkje']").attr("id");
	var cjkje = $(this).parents("tr").find("[id^='txt_cjkje']").attr("id");
	select_commonWin("${ctx}/wsbx/rcbx/window?controlId=jkyw&djbh="+jkdh+"&jkrxm="+jkrxm+"&jkr="+jkr+"&szdw="+szdw+"&szbm="+szbm+"&jkje="+jkje,"借款业务","920","630");
});

$(".cxjkbox").click(function(){
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parents(".style_div").find(".cxjk-content").slideDown(500);
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".cxjk-content:hidden").show();
		var txt_ywzje=$("#txt_ywzje").val();//获取总金额
// 		var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqcjkcount = 0;
		var schqcount = 0;
		var schqdgcount = 0;
		var schqgwkcount = 0;
// 		$.each(schqcjkmoney,function(){//冲借款
// 			if($(this).val()!=""||!isNaN($(this).val)){
// 				schqcjkcount = Number(schqcjkcount)+Number($(this).val());
// 			}
// 		});
		$.each(schqmoney,function(){//对私
			if($(this).val()!=""||!isNaN($(this).val)){
				schqcount = Number(schqcount)+Number($(this).val());
			}
		});
		$.each(schqdgmoney,function(){//对公
			if($(this).val()!=""||!isNaN($(this).val)){
				schqdgcount = Number(schqdgcount)+Number($(this).val());
			}
		});
		$.each(schqgwkmoney,function(){//公务卡
			if($(this).val()!=""||!isNaN($(this).val)){
				schqgwkcount = Number(schqgwkcount)+Number($(this).val());
			}
		});
		var syje;
		syje=Number(txt_ywzje)-Number(schqcount)-Number(schqdgcount)-Number(schqgwkcount);
		if(syje<0){
			syje=0;
		}
		var v1 = $("#txt_ywzje").val();
		var v2 = $("#txt_jszje").val();
		if(v1!=v2){
			console.log("________1____"+Number(syje).toFixed(2))
			$("#txt_cjkje0").val(Number(syje).toFixed(2));
// 			$(this).val(Number(syje).toFixed(2));
		}
		var val = $("#txt_cjkje0").parents("tr").prev("tr").find("[name='cjkje']").val();
		console.log("______"+val)
		if((val!="")&&(!isNaN(val))&&("undefined"!=val)){
			$("#txt_cjkje0").val("");
		}
 		cxmoney();
	}else{
		$(this).parents(".style_div").find(".cxjk-content").slideUp(500);
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".cxjk-content:visible").hide();
		$(".cx_xj").find(".tsinput").val(Number("0").toFixed(2));
		$("[id^='txt_cjkje']").val("");
		xjcolor(0,"cx_xj");
	}
});
//冲销借款
var cxjk = "${csjkList.size()+1}";
function cxjkJs(guid){
	var txt_ywzje=$("#txt_ywzje").val();//获取总金额
	var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqcjkcount = 0;
	var schqcount = 0;
	var schqdgcount = 0;
	var schqgwkcount = 0;
	$.each(schqcjkmoney,function(){//冲借款
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcjkcount = Number(schqcjkcount)+Number($(this).val());
		}
	});
	$.each(schqmoney,function(){//对私
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcount = Number(schqcount)+Number($(this).val());
		}
	});
	$.each(schqdgmoney,function(){//对公
		if($(this).val()!=""||!isNaN($(this).val)){
			schqdgcount = Number(schqdgcount)+Number($(this).val());
		}
	});
	$.each(schqgwkmoney,function(){//公务卡
		if($(this).val()!=""||!isNaN($(this).val)){
			schqgwkcount = Number(schqgwkcount)+Number($(this).val());
		}
	});
	var syje;
	syje=Number(txt_ywzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqdgcount)-Number(schqgwkcount);
	if(syje<0){
		syje=0;
	}
	var parentTr = $("#tb_cxjk").find(".tr_0").clone();
	parentTr.attr("class","tr_"+cxjk);
// 	parentTr.find("#txt_cjkje0").val("");
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
			if(data.JKJE<syje){
				alert("冲借款金额应该小于等于借款金额！");	
// 				parentTr.find("#txt_cjkje0").attr({"id":"txt_cjkje"+cxjk,"value":data.JKJE});
			}else{
		    parentTr.find("#txt_cjkje0").attr({"id":"txt_cjkje"+cxjk,"value":syje});
			}
		}
	});
	
	cxmoney1(syje);//点击事件传值
	$("#tb_cxjk").find(".tr_0").before(parentTr);
	cxjk++;
// 	cxmoney();
}
$(document).on("click",".deleteBtn_JK",function(){
	$(this).parents("tr").remove();
	cxmoney();
});
function cxmoney1(syje){
	var money = $("[id^=txt_cjkje]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count=count+syje;
	count = parseFloat(count).toFixed(2);
	$(".cx_xj").find(".tsinput").val(count);
	xjcolor(count,"cx_xj");
}
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
$(document).on("change","[id^=txt_klx]",function(){
	var zh = $(this).find("option:selected").attr("zh");
	var zhbguid = $(this).find("option:selected").attr("zhbguid");
	$(this).parents("tr").find("[id^=txt_dfzh]").val(zh);
	$(this).parents("tr").find("[id^=txt_guid]").val(zhbguid);
});
//部门
$(document).on("click","[id^=btn_bm]",function(){
	var id = $(this).parents("td").find("[id^=txt_bm]").attr("id");
	select_commonWin("${ctx}/window/dwpage?controlId="+id,"部门信息","920","630");
});
$(document).on("click","[class*=txt_ryxz]",function(){
	var parentTr=$(this).parents("tr");
	var val = $(this).val();
	if(val=="0"){
		var dqdlrguid="${dqdlrguid}";
		$(this).parents("tr").find("[id^=txt_ryxm]").val("${loginInfo}");
		$(this).parents("tr").find("[id^=bbtn_ryxm]").addClass("yc");
		$.ajax({
			url:"${ctx}/wsbx/rcbx/dszfyhxx",
			data:"dqdlrguid="+dqdlrguid,
			dataType:"json",
			async:false,
			success:function(datas){
				parentTr.find("[id^=txt_klx]").empty();
				if(datas){
					parentTr.find("[id^=txt_klx]").append("<option value=''>请选择...</option>");
					$.each(datas,function(i,v){
						var id = v.GUID;
						var mc = v.KHYH;
						var zh = v.KHYHZH;
						var zhbguid = v.GUID;
						parentTr.find("[id^=txt_klx]").append("<option value="+mc+" zh="+zh+" zhbguid="+zhbguid+">"+mc+"</option>");
					});
				}
			}
		});
	}else if(val=="1"){
		$(this).parents("tr").find("[id^=txt_ryxm]").val("${xmfzr}");
// 		$(this).parents("tr").find("[id^=bbtn_ryxm]").addClass("yc");
		$(this).parents("tr").find("[id^=bbtn_ryxm]").removeClass("yc");
		$(this).parents("tr").find("[id^=txt_dfzh]").val("");
		parentTr.find("[id^=txt_klx]").empty();
	}else{
		$(this).parents("tr").find("[id^=txt_ryxm]").val("");
		$(this).parents("tr").find("[id^=bbtn_ryxm]").removeClass("yc");
		$(this).parents("tr").find("[id^=txt_dfzh]").val("");
		parentTr.find("[id^=txt_klx]").empty();
	}
});
//人员弹窗
// $(document).on("click","[id^=bbtn_ryxm]",function(){
// 	var id = $(this).parents("td").find("[id^=txt_ryxm]").attr("id");
// 	select_commonWin("${ctx}/window/rypage?controlId="+id,"人员信息","920","630");
// });
//项目负责人弹窗
$(document).on("click","[id^=bbtn_ryxm]",function(){
	console.log($(this).parents("td").siblings().children("[name^='ryxz']:checked").val());
    if( $(this).parents("td").siblings().children("[name^='ryxz']:checked").val()=="1"){
    	var id = $(this).parents("td").find("[id^=txt_ryxm]").attr("id");
	    select_commonWin("${ctx}/wsbx/ccbx/window?controlId=xmfzr&ccywguid=${ccywguid}&id="+id,"项目负责人信息","820","630");
	}	
    if($(this).parents("td").siblings().children("[name^='ryxz']:checked").val()=="2"){
    	var id = $(this).parents("td").find("[id^=txt_ryxm]").attr("id");
//     	select_commonWin("${ctx}/window/rypage?controlId="+id,"人员信息","920","630");
    	select_commonWin("${ctx}/wsbx/rcbx/rypage?controlId="+id,"人员信息","920","630");
	}	
});
//项目负责人弹窗 查找上级方法
function xmfzrdsqtr(ryguid,cdid){
	var parentTr=$("#"+cdid).parents("tr");
	$.ajax({
		url:"${ctx}/wsbx/rcbx/dszfyhxx",
		data:"dqdlrguid="+ryguid,
		dataType:"json",
		async:false,
		success:function(datas){
			parentTr.find("[id^=txt_klx]").empty();
			if(datas){
				parentTr.find("[id^=txt_klx]").append("<option value=''>请选择...</option>");
				$.each(datas,function(i,v){
					var id = v.GUID;
					var mc = v.KHYH;
					var zh = v.KHYHZH;
					parentTr.find("[id^=txt_klx]").append("<option value="+mc+" zh="+zh+" zhbguid="+id+">"+mc+"</option>");
				});
			}
		}
	});
}
//人员弹窗 查找上级方法
function dsqtr(guid,dwmc,ry,cdid){
	var parentTr=$("#"+cdid).parents("tr");
	$.ajax({
		url:"${ctx}/wsbx/rcbx/dszfyhxx",
		data:"dqdlrguid="+guid,
		dataType:"json",
		async:false,
		success:function(datas){
			parentTr.find("[id^=txt_klx]").empty();
			if(datas){
				parentTr.find("[id^=txt_klx]").append("<option value=''>请选择...</option>");
				$.each(datas,function(i,v){
					var id = v.GUID;
					var mc = v.KHYH;
					var zh = v.KHYHZH;
					parentTr.find("[id^=txt_klx]").append("<option value="+mc+" zh="+zh+" zhbguid="+id+">"+mc+"</option>");
				});
			}
		}
	});
}
$(".sszkbox").click(function(){
	$(this).parents(".style_div").find(".sszk-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".sszk-content:hidden").show();
		var txt_ywzje=$("#txt_ywzje").val();//获取总金额
		var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqcjkcount = 0;
		var schqcount = 0;
		var schqdgcount = 0;
		var schqgwkcount = 0;
		$.each(schqcjkmoney,function(){//冲借款
			if($(this).val()!=""||!isNaN($(this).val)){
				schqcjkcount = Number(schqcjkcount)+Number($(this).val());
			}
		});
// 		$.each(schqmoney,function(){//对私
// 			if($(this).val()!=""||!isNaN($(this).val)){
// 				schqcount = Number(schqcount)+Number($(this).val());
// 			}
// 		});
		$.each(schqdgmoney,function(){//对公
			if($(this).val()!=""||!isNaN($(this).val)){
				schqdgcount = Number(schqdgcount)+Number($(this).val());
			}
		});
		$.each(schqgwkmoney,function(){//公务卡
			if($(this).val()!=""||!isNaN($(this).val)){
				schqgwkcount = Number(schqgwkcount)+Number($(this).val());
			}
		});
		var syje;
		syje=Number(txt_ywzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqdgcount)-Number(schqgwkcount);
		if(syje<0){
			syje=0;
		}
		var v1 = $("#txt_ywzje").val();
		var v2 = $("#txt_jszje").val();
// 		console.log(v1+"____v1v2___"+v2);
		if(v1!=v2){
			$("#txt_dsje0").val(Number(syje).toFixed(2));
		}else{
			$("#txt_dsje0").val("0.00");
		}
		var val = $("#txt_dsje0").parents("tr").prev("tr").find("[name='je']").val();
// 		console.log("______"+val)
		if((val!="")&&(!isNaN(val))&&("undefined"!=val)){
			$("#txt_dsje0").val("");
		}
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
	
	var txt_ywzje=$("#txt_ywzje").val();//获取总金额
	var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqcjkcount = 0;
	var schqcount = 0;
	var schqdgcount = 0;
	var schqgwkcount = 0;
	$.each(schqcjkmoney,function(){//冲借款
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcjkcount = Number(schqcjkcount)+Number($(this).val());
		}
	});
	$.each(schqmoney,function(){//对私
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcount = Number(schqcount)+Number($(this).val());
		}
	});
	$.each(schqdgmoney,function(){//对公
		if($(this).val()!=""||!isNaN($(this).val)){
			schqdgcount = Number(schqdgcount)+Number($(this).val());
		}
	});
	$.each(schqgwkmoney,function(){//公务卡
		if($(this).val()!=""||!isNaN($(this).val)){
			schqgwkcount = Number(schqgwkcount)+Number($(this).val());
		}
	});
	var syje;
	syje=Number(txt_ywzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqdgcount)-Number(schqgwkcount);
	if(syje<0){
		syje=0;
	}
	var parentTr = $("#tb_sszk").find(".tr_0").clone();
// 	parentTr.find(":text").val("");
// 	parentTr.find("select").empty();
	parentTr.attr("class","tr_"+sszk);
	parentTr.find(".addBtn_ZK").removeClass("addBtn_ZK").addClass("deleteBtn_ZK");
	parentTr.find("[class^=txt_ryxz]").attr({"class":"txt_ryxz"+sszk});
	parentTr.find("[class^=txt_ryxz]").attr({"name":"ryxz"+sszk});
	parentTr.find("[id^=txt_ryxm]").attr({"id":"txt_ryxm"+sszk});
// 	parentTr.find("[id^=txt_ryxm]").attr({"id":"txt_ryxm"+sszk,"value":"${loginInfo}"});
	parentTr.find("[id^=txt_klx]").attr({"id":"txt_klx"+sszk});
	parentTr.find("[id^=bbtn_ryxm]").attr("id","bbtn_ryxm"+sszk);
	parentTr.find("[id^=txt_dfzh]").attr({"id":"txt_dfzh"+sszk});
	parentTr.find("[id^=txt_dsje]").attr({"id":"txt_dsje"+sszk}).val(Number(syje).toFixed(2));
	$("#tb_sszk").find(".tr_0").before(parentTr);
	sszk++;
	sszkmoney();
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
// $(document).on("click","[id^=btn_dfdw]",function(){
// 	//var id = $(this).attr("id");
// 	var id = $(this).parents("tr").find("[name='dfdw1']").attr("id");
// 	select_commonWin("${ctx}/wsbx/rcbx/window?flag=1&controlId=xnzz&id="+id,"往来单位","920","630");
// });
$(document).on("click","[id^=btn_dfdw]",function(){
	var id = $(this).parents("tr").find("[name='dfdw']").attr("id");
	var ids = $(this).parents("tr").find("[name='dfdw1']").attr("id");
	select_commonWin("${ctx}/wsbx/rcbx/window?flag=1&controlId=xnzz&id="+ids+"&gid="+id,"往来单位","920","630");
});
$(".xnzzbox").click(function(){
	$(this).parents(".style_div").find(".xnzz-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".xnzz-content:hidden").show();
		var txt_ywzje=$("#txt_ywzje").val();//获取总金额
		var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqcjkcount = 0;
		var schqcount = 0;
		var schqdgcount = 0;
		var schqgwkcount = 0;
		$.each(schqcjkmoney,function(){//冲借款
			if($(this).val()!=""||!isNaN($(this).val)){
				schqcjkcount = Number(schqcjkcount)+Number($(this).val());
			}
		});
		$.each(schqmoney,function(){//对私
			if($(this).val()!=""||!isNaN($(this).val)){
				schqcount = Number(schqcount)+Number($(this).val());
			}
		});
// 		$.each(schqdgmoney,function(){//对公
// 			if($(this).val()!=""||!isNaN($(this).val)){
// 				schqdgcount = Number(schqdgcount)+Number($(this).val());
// 			}
// 		});
		$.each(schqgwkmoney,function(){//公务卡
			if($(this).val()!=""||!isNaN($(this).val)){
				schqgwkcount = Number(schqgwkcount)+Number($(this).val());
			}
		});
		var syje;
		syje=Number(txt_ywzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqgwkcount);
// 		console.log("_____________"+schqdgcount);
// 		console.log("_____________"+schqcount);
// 		console.log("_____________"+schqgwkcount);
		
		if(syje<0){
			syje=0;
		}
		var v1 = $("#txt_ywzje").val();
		var v2 = $("#txt_jszje").val();
		if(v1!=v2){
			$("#txt_je0").val(Number(syje).toFixed(2));
		}
		var val = $("#txt_je0").parents("tr").prev("tr").find("[name='je']").val();
// 		console.log("______"+val)
		if((val!="")&&(!isNaN(val))&&("undefined"!=val)){
			$("#txt_je0").val("");
		}
		
		xnzzmoney();
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".xnzz-content:visible").hide();
		$(".xnzz_xj").find(".tsinput").val(Number(0).toFixed(2));
		xjcolor(0,"xnzz_xj");
	}
});
var xnzz = "${dgList.size()+1}";
$(document).on("click","[class*=addBtn_XN]",function(){
	var txt_ywzje=$("#txt_ywzje").val();//获取总金额
	var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqcjkcount = 0;
	var schqcount = 0;
	var schqdgcount = 0;
	var schqgwkcount = 0;
	$.each(schqcjkmoney,function(){//冲借款
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcjkcount = Number(schqcjkcount)+Number($(this).val());
		}
	});
	$.each(schqmoney,function(){//对私
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcount = Number(schqcount)+Number($(this).val());
		}
	});
	$.each(schqdgmoney,function(){//对公
		if($(this).val()!=""||!isNaN($(this).val)){
			schqdgcount = Number(schqdgcount)+Number($(this).val());
		}
	});
	$.each(schqgwkmoney,function(){//公务卡
		if($(this).val()!=""||!isNaN($(this).val)){
			schqgwkcount = Number(schqgwkcount)+Number($(this).val());
		}
	});
	var syje;
	syje=Number(txt_ywzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqdgcount)-Number(schqgwkcount);
	if(syje<0){
		syje=0;
	}
	var parentTr = $("#tb_xnzz").find(".tr_0").clone();
// 	parentTr.find("input").val("");
	parentTr.find("[name='zfdh']").val("${zbid}");
// 	parentTr.find("select").empty();
	parentTr.attr("class","tr_"+xnzz);
	parentTr.find("[class^=addBtn]").removeClass("addBtn_XN").addClass("deleteBtn_XN");
	parentTr.find("#txt_dfdw0").attr({"id":"txt_dfdw"+xnzz});
	parentTr.find("#txt_dwmc0").attr({"id":"txt_dwmc"+xnzz});
	parentTr.find("#btn_dfdw0").attr({"id":"btn_dfdw"+xnzz});
	parentTr.find("#txt_dfdz0").attr({"id":"txt_dfdz"+xnzz});
// 	parentTr.find("#txt_dfyh0").empty();
	parentTr.find("#txt_dfyh0").attr({"id":"txt_dfyh"+xnzz});
	parentTr.find("#txt_dfzh0").attr({"id":"txt_dfzh"+xnzz});
	parentTr.find("#txt_je0").attr({"id":"txt_je"+xnzz}).val(Number(syje).toFixed(2));
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
					if(wlbh==null||wlbh=="undefined") wlbh="";
					if(dwmc==null||dwmc=="undefined") dwmc="";
					if(dwdz==null||dwdz=="undefined") dwdz="";
					if(mc==null||mc=="undefined") mc="";
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
	select_commonWin("${ctx}/window/allrypage?type=T&controlId="+id,"人员信息","920","630");
});

$(".gwkbox").click(function(){
	$(this).parents(".style_div").find(".gwk-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".gwk-content:hidden").show();
		var txt_ywzje=$("#txt_ywzje").val();//获取总金额
		var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqcjkcount = 0;
		var schqcount = 0;
		var schqdgcount = 0;
		var schqgwkcount = 0;
		$.each(schqcjkmoney,function(){//冲借款
			if($(this).val()!=""||!isNaN($(this).val)){
				schqcjkcount = Number(schqcjkcount)+Number($(this).val());
			}
		});
		$.each(schqmoney,function(){//对私
			if($(this).val()!=""||!isNaN($(this).val)){
				schqcount = Number(schqcount)+Number($(this).val());
			}
		});
		$.each(schqdgmoney,function(){//对公
			if($(this).val()!=""||!isNaN($(this).val)){
				schqdgcount = Number(schqdgcount)+Number($(this).val());
			}
		});
		$.each(schqgwkmoney,function(){//公务卡
			if($(this).val()!=""||!isNaN($(this).val)){
				schqgwkcount = Number(schqgwkcount)+Number($(this).val());
			}
		});
		var syje;
		syje=Number(txt_ywzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqdgcount);
		if(syje<0){
			syje=0;
		}
		var v1 = $("#txt_ywzje").val();
		var v2 = $("#txt_jszje").val();
		if(v1!=v2){
			$("#txt_skje0").val(Number(syje).toFixed(2));
		}
		var val = $("#txt_skje0").parents("tr").prev("tr").find("[name='skje']").val();
		if((val!="")&&(!isNaN(val))&&("undefined"!=val)){
// 		console.log("___val2___"+val)
			$("#txt_skje0").val("");
		}
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
	var txt_ywzje=$("#txt_ywzje").val();//获取总金额
	var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqcjkcount = 0;
	var schqcount = 0;
	var schqdgcount = 0;
	var schqgwkcount = 0;
	$.each(schqcjkmoney,function(){//冲借款
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcjkcount = Number(schqcjkcount)+Number($(this).val());
		}
	});
	$.each(schqmoney,function(){//对私
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcount = Number(schqcount)+Number($(this).val());
		}
	});
	$.each(schqdgmoney,function(){//对公
		if($(this).val()!=""||!isNaN($(this).val)){
			schqdgcount = Number(schqdgcount)+Number($(this).val());
		}
	});
	$.each(schqgwkmoney,function(){//公务卡
		if($(this).val()!=""||!isNaN($(this).val)){
			schqgwkcount = Number(schqgwkcount)+Number($(this).val());
		}
	});
	var syje;
	syje=Number(txt_ywzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqdgcount)-Number(schqgwkcount);
	if(syje<0){
		syje=0;
	}
	var parentTr = $("#tb_gwk").find(".tr_0").clone();
// 	parentTr.find("input").val("");
// 	parentTr.find("select").empty();
	parentTr.attr("class","tr_"+gwk);
	parentTr.find("[class^=addBtn]").removeClass("addBtn_GWK").addClass("deleteBtn_GWK");
	parentTr.find("#ttxt_ryxm0").attr({"id":"ttxt_ryxm"+gwk});
	parentTr.find("#tbtn_ryxm0").attr({"id":"tbtn_ryxm"+gwk});
	parentTr.find("#txt_skrq0").attr({"id":"txt_skrq"+gwk});
	parentTr.find("#txt_skje0").attr({"id":"txt_skje"+gwk}).val(Number(syje).toFixed(2));
	parentTr.find("#txt_kh0").attr({"id":"txt_kh"+gwk});
	parentTr.find(":input").val("");
	parentTr.find("[name='skdh']").val("${zbid}");
	$("#tb_gwk").find(".tr_0").before(parentTr);
	gwk++;
	gwkmoney();
}
$(document).on("keyup","[id^=txt_skje]",function(){
	gwkmoney();
});
$(document).on("click",".addBtn_GWK",function(){
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
		if(checkBox.length==0&&type!="after"){
			alert("请选择支付方式！");
			return;
		}
		/*公务卡验证金额*/
		var flag = true;
// 		if(id=="gwForm"){
			$("#gwForm tr[class^=tr_]").each(function(){
				var bxje=$(this).find("[name='skje']").val();
				var skje=$(this).find("[name='sjskje']").val();
				if(parseFloat(bxje)>parseFloat(skje) && parseFloat(bxje)!=0.00)
			    {
					alert("报销金额必须小于或等于刷卡金额！");
					flag=false;
					return false;
		        }
		    });    		
// 		}
		if(checkBox.length==0&&type=="after"){
			location.href="${ctx}/wsbx/ccbx/goYwblEditPage?mkbh=${mkbh}&xmguid=${xmguid}&zbguid=${zbid}&xmmc=${xmmc}&djbh=${djbh}&money=${money}&ccywguid=${ccywguid}";
		}
		//checkbox被选中验证是不是空
		var isChecked1 = $('.cxjkbox').prop('checked');
		var isChecked2 = $('.sszkbox').prop('checked');
		var isChecked3 = $('.xnzzbox').prop('checked');
		var isChecked4 = $('.gwkbox').prop('checked');
		var flag1 = "";
		var flag2 = "";
		var flag3 = "";
		var flag4 = "";
		//冲借款   checkbox--------------------------------------------
		if("true"==isChecked1+""){
// 			console.log("__________cjk_"+isChecked1);
// 			console.log("____je___"+$(".cjkje").val())
			$(".cjkje").each(function(i){
				if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
					flag1="2";//只有0.00
// 					console.log("cjk 都是零数据");
				}else{
// 					console.log("cjk 有非零数据");
					flag1="1";return false;//有
				}
			});
			if(flag1=="2"){
				alert("选择了冲借款但数据填写无效！");return;
			}
		}
		//对私支付    checkbox--------------------------------------------
		if("true"==isChecked2+""){
			console.log("__________cxjkbox12_"+isChecked2);
			$(".dszfje").each(function(i){
				if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
					console.log("_2_")
					flag2="2";//只有0.00
				}else{
					console.log("_1_")
					flag2="1";return false;//有
				}
			});
			if(flag2=="2"){
				alert("选择了对私支付但数据填写无效！");return;
			}
		}
		//对公支付    checkbox--------------------------------------------
		if("true"==isChecked3+""){
			console.log("__________cxjkbox12_"+isChecked3);
			$(".dgzfje").each(function(i){
				if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
					console.log("_2_")
					flag3="2";//只有0.00
				}else{
					console.log("_1_")
					flag3="1";return false;//有
				}
			});
			if(flag3=="2"){
				alert("选择了对公支付但数据填写无效！");return;
			}
		}
		//公务卡 checkbox-------------------------------------------------
		if("true"==isChecked4+""){
			$(".gwkje").each(function(i){
				if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){
					flag4="2";
				}else{
					flag4="1";return false;//有
				}
			});
			if(flag4=="2"){
				alert("选择了公务卡但数据填写无效！");return;
			}
		}
		
		
		
		
		//冲借款 验证开始---------------------------------------------
		var fc ="";
		$(".cjkje").each(function(i){
			var cjkyz4 = $(this).parents("tr").find(".cjkyz4").val();
			var cjkyz3 = $(this).parents("tr").find(".cjkyz3").val();
			var cjkyz2 = $(this).parents("tr").find(".cjkyz2").val();
			var cjkyz1 = $(this).parents("tr").find(".cjkyz1").val();
			console.log("验证2_________"+cjkyz1+"__"+cjkyz2+"__"+cjkyz3+"__"+cjkyz4)
			if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
				console.log("公务卡中还有未填写数据！1111111")
			}else if(cjkyz4==""||cjkyz3==""||cjkyz2==""||cjkyz1==""||cjkyz4=="undefined"||cjkyz3=="undefined"||cjkyz2=="undefined"||cjkyz1=="undefined"||cjkyz4==null||cjkyz3==null||cjkyz2==null||cjkyz1==null){
				console.log("公务卡中还有未填写数据！")
				fc="1";return false;
			}
		});
		if(fc=="1"){
			alert("公务卡中还有未填写数据！");return;
		}
		//对私支付验证开始---------------------------------------------
		var f ="";
		$(".dszfje").each(function(i){
			var dszfyz3 = $(this).parents("tr").find(".dszfyz3").val();
			var dszfyz2 = $(this).parents("tr").find(".dszfyz2").val();
			var dszfyz1 = $(this).parents("tr").find(".dszfyz1").val();
			if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
			}else if(dszfyz3==""||dszfyz2==""||dszfyz1==""){
				f="1";return false;
			}
		});
		if(f=="1"){
			alert("对私支付中还有未填写数据！");return;
		}
		//对公支付验证开始---------------------------------------------
		var fk ="";
		$(".dgzfje").each(function(i){
			var dgzfyz4 = $(this).parents("tr").find(".dgzfyz4").val();
			var dgzfyz3 = $(this).parents("tr").find(".dgzfyz3").val();
			var dgzfyz2 = $(this).parents("tr").find(".dgzfyz2").val();
			var dgzfyz1 = $(this).parents("tr").find(".dgzfyz1").val();
			if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
			}else if(dgzfyz4==""||dgzfyz3==""||dgzfyz2==""||dgzfyz1==""){
				fk="1";return false;
			}
		});
		if(fk=="1"){
			alert("对公支付中还有未填写数据！");return;
		}
		//公务卡验证开始---------------------------------------------
		var fku ="";
		$(".gwkje").each(function(i){
			var gwkyz4 = $(this).parents("tr").find(".gwkyz4").val();
			var gwkyz3 = $(this).parents("tr").find(".gwkyz3").val();
			var gwkyz2 = $(this).parents("tr").find(".gwkyz2").val();
			var gwkyz1 = $(this).parents("tr").find(".gwkyz1").val();
			if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
			}else if(gwkyz4==""||gwkyz3==""||gwkyz2==""||gwkyz1==""){
				fku="1";return false;
			}
		});
		if(fku=="1"){
			alert("公务卡中还有未填写数据！");return;
		}
		//--------------
		var cc = $("[name^=ryxz]");
		$.each(cc,function(){
// 			console.log($(this).prop("checked"));
			var val = $(this).prop("checked");
			if(val){
				$(this).parents("tr").find("[name='ryxz']").val($(this).val());
			}
		}); 
		var aryId = [];
		$.each(checkBox,function(i,v){
			var $this = $(this);
			var _je =$this.parents("form").find(".je");
			var je = '0';
			_je.each(function(){
				je+=$(this).val();
			});
			if(parseInt(je) != 0){
				var id = $this.parents("form").attr("id");
			      
				aryId.push(id);
				var val = $this.val();
				var _url = "${ctx}/wsbx/rcbx/"+$this.attr("mc");
				var json = $("#"+id+"").serializeObject("start","end");  //json对象				
		  		var jsonStr = JSON.stringify(json);  //json字符串	 
		  		console.log(jsonStr);
		  		if(flag){
		  		$.ajax({
		  			url:_url,
		  			data:"jsonStr="+jsonStr+"&zbid="+$("[name='zbid']").val(),
		  			dataType:"json",
		  			type:"post",
		  			complete:function(){
		  				updateDatas(aryId,type);
		  			}
		  		});
		  		}
			}
		});
	}
	//保存并提交
	function seconddoAddAll(type){
		var checkBox = $("[class$=box]").find(":checkbox").filter(":checked");
		if(checkBox.length==0&&type!="after"){
			alert("请选择支付方式！");
			return;
		}
		/*公务卡验证金额*/
		var flag = true;
			$("#gwForm tr[class^=tr_]").each(function(){
				var bxje=$(this).find("[name='skje']").val();
				var skje=$(this).find("[name='sjskje']").val();
				if(parseFloat(bxje)>parseFloat(skje) && parseFloat(bxje)!=0.00)
			    {
					alert("报销金额必须小于或等于刷卡金额！");
					flag=false;
					return false;
        }
    });    		
		if(checkBox.length==0&&type=="after"){
			location.href="${ctx}/wsbx/ccbx/goYwblEditPage?mkbh=${mkbh}&xmguid=${xmguid}&zbguid=${zbid}&xmmc=${xmmc}&djbh=${djbh}&money=${money}&ccywguid=${ccywguid}";
		}
		var cc = $("[name^=ryxz]");
		$.each(cc,function(){
			var val = $(this).prop("checked");
			if(val){
				$(this).parents("tr").find("[name='ryxz']").val($(this).val());
			}
		}); 
		
		//checkbox被选中验证是不是空
		var isChecked1 = $('.cxjkbox').prop('checked');
		var isChecked2 = $('.sszkbox').prop('checked');
		var isChecked3 = $('.xnzzbox').prop('checked');
		var isChecked4 = $('.gwkbox').prop('checked');
		var flag1 = "";
		var flag2 = "";
		var flag3 = "";
		var flag4 = "";
		//冲借款   checkbox--------------------------------------------
		if("true"==isChecked1+""){
// 			console.log("__________cjk_"+isChecked1);
// 			console.log("____je___"+$(".cjkje").val())
			$(".cjkje").each(function(i){
				if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
					flag1="2";//只有0.00
// 					console.log("cjk 都是零数据");
				}else{
// 					console.log("cjk 有非零数据");
					flag1="1";return false;//有
				}
			});
			if(flag1=="2"){
				alert("选择了冲借款但数据填写无效！");return;
			}
		}
		//对私支付    checkbox--------------------------------------------
		if("true"==isChecked2+""){
			console.log("__________cxjkbox12_"+isChecked2);
			$(".dszfje").each(function(i){
				if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
					console.log("_2_")
					flag2="2";//只有0.00
				}else{
					console.log("_1_")
					flag2="1";return false;//有
				}
			});
			if(flag2=="2"){
				alert("选择了对私支付但数据填写无效！");return;
			}
		}
		//对公支付    checkbox--------------------------------------------
		if("true"==isChecked3+""){
			console.log("__________cxjkbox12_"+isChecked3);
			$(".dgzfje").each(function(i){
				if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
					console.log("_2_")
					flag3="2";//只有0.00
				}else{
					console.log("_1_")
					flag3="1";return false;//有
				}
			});
			if(flag3=="2"){
				alert("选择了对公支付但数据填写无效！");return;
			}
		}
		//公务卡 checkbox-------------------------------------------------
		if("true"==isChecked4+""){
			$(".gwkje").each(function(i){
				if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){
					flag4="2";
				}else{
					flag4="1";return false;//有
				}
			});
			if(flag4=="2"){
				alert("选择了公务卡但数据填写无效！");return;
			}
		}
		
		
		
		
		//冲借款 验证开始---------------------------------------------
		var fc ="";
		$(".cjkje").each(function(i){
			var cjkyz4 = $(this).parents("tr").find(".cjkyz4").val();
			var cjkyz3 = $(this).parents("tr").find(".cjkyz3").val();
			var cjkyz2 = $(this).parents("tr").find(".cjkyz2").val();
			var cjkyz1 = $(this).parents("tr").find(".cjkyz1").val();
			console.log("验证2_________"+cjkyz1+"__"+cjkyz2+"__"+cjkyz3+"__"+cjkyz4)
			if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
				console.log("公务卡中还有未填写数据！1111111")
			}else if(cjkyz4==""||cjkyz3==""||cjkyz2==""||cjkyz1==""||cjkyz4=="undefined"||cjkyz3=="undefined"||cjkyz2=="undefined"||cjkyz1=="undefined"||cjkyz4==null||cjkyz3==null||cjkyz2==null||cjkyz1==null){
				console.log("公务卡中还有未填写数据！")
				fc="1";return false;
			}
		});
		if(fc=="1"){
			alert("公务卡中还有未填写数据！");return;
		}
		//对私支付验证开始---------------------------------------------
		var f ="";
		$(".dszfje").each(function(i){
			var dszfyz3 = $(this).parents("tr").find(".dszfyz3").val();
			var dszfyz2 = $(this).parents("tr").find(".dszfyz2").val();
			var dszfyz1 = $(this).parents("tr").find(".dszfyz1").val();
			if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
			}else if(dszfyz3==""||dszfyz2==""||dszfyz1==""){
				f="1";return false;
			}
		});
		if(f=="1"){
			alert("对私支付中还有未填写数据！");return;
		}
		//对公支付验证开始---------------------------------------------
		var fk ="";
		$(".dgzfje").each(function(i){
			var dgzfyz4 = $(this).parents("tr").find(".dgzfyz4").val();
			var dgzfyz3 = $(this).parents("tr").find(".dgzfyz3").val();
			var dgzfyz2 = $(this).parents("tr").find(".dgzfyz2").val();
			var dgzfyz1 = $(this).parents("tr").find(".dgzfyz1").val();
			if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
			}else if(dgzfyz4==""||dgzfyz3==""||dgzfyz2==""||dgzfyz1==""){
				fk="1";return false;
			}
		});
		if(fk=="1"){
			alert("对公支付中还有未填写数据！");return;
		}
		//公务卡验证开始---------------------------------------------
		var fku ="";
		$(".gwkje").each(function(i){
			var gwkyz4 = $(this).parents("tr").find(".gwkyz4").val();
			var gwkyz3 = $(this).parents("tr").find(".gwkyz3").val();
			var gwkyz2 = $(this).parents("tr").find(".gwkyz2").val();
			var gwkyz1 = $(this).parents("tr").find(".gwkyz1").val();
			if(Number($(this).val())=="0.00"||Number($(this).val())=="0"){//不用判断
			}else if(gwkyz4==""||gwkyz3==""||gwkyz2==""||gwkyz1==""){
				fku="1";return false;
			}
		});
		if(fku=="1"){
			alert("公务卡中还有未填写数据！");return;
		}
		
		var aryId = [];
		$.each(checkBox,function(i,v){
			var $this = $(this);
			var id = $this.parents("form").attr("id");
			aryId.push(id);
			var val = $this.val();
			var _url = "${ctx}/wsbx/rcbx/"+$this.attr("mc");
			var json = $("#"+id+"").serializeObject("start","end");  //json对象				
	  		var jsonStr = JSON.stringify(json);  //json字符串	 
	  		if(flag){
	  		$.ajax({
	  			url:_url,
	  			data:"jsonStr="+jsonStr+"&zbid="+$("[name='zbid']").val(),
	  			dataType:"json",
	  			type:"post",
	  			complete:function(){
	  				$.ajax({
	  		  			url:"${ctx}/wsbx/ccbx/update",
	  		  			data:"id="+aryId.join(",")+"&zbid="+$("#dual").val(),
	  		  			dataType:"json",
	  		  			type:"post",
	  		  			complete:function(){
	  		  				console.log("保存成功");
	  		  			}
	  		  		});
	  			}
	  		});
	  		}
		});
		//保存并提交
		var alert1 = "确认提交？";
  		var guid=$("[name='zbid']").val();
  		var tagg = false;
		$.ajax({
			url:"${ctx}/wsbx/process/checkWhoSh",//验证下一个审核人
			data:"guid="+guid+"&type=clfbx",
			type:"post",
			async:false,
			dataType:"json",
			success:function(val){
				if(val.XZ=="submit"){
					alert1 = "单据提交至:"+val.XM+"审核是否提交？";
					tagg = true;
				}else if(val.XZ=="hq"){
					alert1 = "单据提交至:"+val.XM+"审核是否提交？";
					tagg = true;
				}else{//选择审核人
					select_commonWin("${ctx}/webView/wsbx/rcbx/jumpWindow.jsp?type=too&guid="+guid,"选择页面","240","200");
					return;
				}
			}
		});
		//提交操作
		if(tagg){
			confirm(alert1,"",function(){
	   			$.ajax({
	   	   			url:"${ctx}/wsbx/process/submit",
	   	   			data:"guid="+guid+"&type=clfbx",
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("信息保存并提交成功！");
	   	   				updatesfwqbc();
	   	   				location.href="${ctx}/wsbx/ccbx/goCcbxListPage";
	   	   			}
	   	   		});
	   		});
		}
	}
	function updatesfwqbc(){
		//保存更新字段
  		$.ajax({
  			type:"post",
  			url:"${ctx}/wsbx/ccbx/updatesfwqbc",
  			data:"zbid="+$("[name='zbid']").val(),
  			async:false,
  			success:function(val){
  				
  			}
  		});
	}
	function updateDatas(aryId,type){
		$.ajax({
  			url:"${ctx}/wsbx/ccbx/update",
  			data:"id="+aryId.join(",")+"&zbid="+$("#dual").val(),
  			dataType:"json",
  			type:"post",
  			complete:function(){
  				if(type=="save"){
  					updatesfwqbc();
  					alert("操作成功！");
  				}else if(type=="next"){
  					updatesfwqbc();
  					alert("操作成功！");
  					if("${look}"=="look"){
  						location.href="${ctx}/kylbx/gowdbxListPage";
  					}else{
  						location.href="${ctx}/wsbx/ccbx/goCcbxListPage?mkbh=${param.mkbh}";
  					}
  				}else{
  					location.href="${ctx}/wsbx/ccbx/goYwblEditPage?mkbh=${mkbh}&xmguid=${xmguid}&zbguid=${zbid}&xmmc=${xmmc}&djbh=${djbh}&money=${money}&look=${look}&ccywguid=${ccywguid}";
  				}
  			}
  		});
	}
	$(document).on("keydown","[name=ryxm]",function(){
		$(this).bindChange("${ctx}/suggest/getXx","R");
	});
	$(document).on("keydown","[name=dfdw1]",function(){
		var $this = $(this);
		$(this).bindChange("${ctx}/suggest/getXx","WLDW",null,null,function(){
			var dwmc = $this.val();
			var parentTr = $this.parents("tr")
			$.ajax({
				url:"${ctx}/wsbx/rcbx/getdwjc",
				data:"dwmc="+dwmc,
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
							var wlbh = v.WLBH;
						    var dwdz = v.DWDZ;
	
							parentTr.find("[id^=txt_dfdw]").val(wlbh);
							//parentTr.find("[id^=txt_dwmc]").val(dwmc);
							parentTr.find("[id^=txt_dfdz]").val(dwdz);
							parentTr.find("[id^=txt_dfyh]").append("<option value="+id+" zh="+zh+">"+mc+"</option>");
						});
					}
				}
			});
			
		});

	});
	  //选择的方法
	function select(guid,xz,type){
		 $.ajax({
			url:"${ctx}/wsbx/process/submit",
			data:"guid="+guid+"&type=clfbx&xz="+xz,
			type:"post",
			async:"false",
			success:function(val){
				alert("提交成功");
				location.href="${ctx}/wsbx/ccbx/goCcbxListPage?mkbh=${param.mkbh}";
			}
		});
	}
</script>
</html>