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
.addBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.deleteBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.text-green{
		color:green!important;
	}
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.add{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.add:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.delete{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.delete:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
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
      
     td{
     	vertical-align: middle!important;
     }
     tr{
background-color: white !important;
}
     
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>差旅费报销信息</span>
		</div>
		
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>差旅报销
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
<!--             		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明"> -->
<!-- 						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i> -->
<!-- 						<font style="font-size:14px;">业务说明</font> -->
						<button type="button" class="btn btn-default" id="btn_back">
	<c:choose>
   <c:when test="${ look=='look'}">  
      返回
   </c:when>
   <c:otherwise> 
  返回列表
   </c:otherwise>
</c:choose></button>
					</button>
			   		
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号</span>
                            <input type="text" id="txt_djbh" readonly="readonly" class="form-control input-radius " name="djbh" value="${clfbxzb.djbh}">
                            <input type="hidden" name="guid" id="guid" value="${clfbxzb.guid}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">出差类型</span>
							<select id="drp_cclx" readonly="readonly" class="form-control input-radius select2" disabled name="cclx"> 
								<c:forEach var="item" items="${cclxList}">
	                        		<option value="${item.DM}" <c:if test="${clfbxzb.cclx== item.DM}">selected</c:if>>${item.MC}</option>
	                        	</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申请人</span>
							<input type="text" id="txt_sqr" readonly class="form-control input-radius" name="sqr" value="${clfbxzb.sqr}"/>
							
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">出差人数</span>
							<input type="text" id="txt_ccrs" readonly class="form-control input-radius sz" name="ccrs" value="${clfbxzb.ccrs}"  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">出差天数</span>
							<input type="text" id="txt_ccts" readonly class="form-control input-radius sz" name="ccts" value="${clfbxzb.ccts}" 
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">附件总张数（张）</span>
							<input type="text" id="txt_fjzzs"  class="form-control input-radius sz" name="fjzzs" value="${clfbxzb.fjzzs}" readonly
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">经费类型</span> -->
<!-- 							<select id="drp_jflx" readonly class="form-control input-radius select2" disabled name="jflx">  -->
<%-- 								<c:forEach var="item" items="${jflxList}"> --%>
<%-- 	                        		<option value="${item.DM}" <c:if test="${clfbxzb.jflx== item.DM}">selected</c:if>>${item.MC}</option> --%>
<%-- 	                        	</c:forEach> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
							<input type="text" id="txt_xxmc" readonly class="form-control input-radius" name="xxmc" value="${clfbxzb.xmmc}"/>
							<input type="hidden" id="txt_xmguid" readonly class="form-control input-radius" name="xmguid" value="${clfbxzb.xmguid}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否科研类报销</span>
							<div  class="radiodiv">&nbsp;&nbsp;&nbsp;
                               <input type="radio" name="sfkylbx" disabled value="1" <c:if test="${clfbxzb.sfkylbx=='1'}">checked</c:if>>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                   <input type="radio" name="sfkylbx" id="ck" value="0" <c:if test="${clfbxzb.sfkylbx=='0'}">checked</c:if>>否
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销总金额（元）</span>
							<input type="text" id="txt_bxzje"  class="form-control input-radius sz" name="bxzje" value="${clfbxzb.bxzje}" readonly
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
				</div>
<!-- 				<div class="row"> -->
<!-- 				<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">附件总张数（张）</span> -->
<%-- 							<input type="text" id="txt_fjzzs"  class="form-control input-radius sz" name="fjzzs" value="${clfbxzb.fjzzs}" readonly --%>
<!-- 							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">出差事由</span>
							<div id="editor" class="border-bottom: 1px solid #ccc;">
								<textarea style="width:100%;height:60px;" id="txt_ccsy" name="ccsy" id="ccsy" data-bv-field="ccsy" readonly class="form-control input-radius">${clfbxzb.ccsy}</textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="box-panel">
						<div class="box-header clearfix">
							<div class="sub-title pull-left text-primary">
							<i class="fa icon-xiangxi"></i>上传附件
							</div>
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
		<form id="myform2" class="form-horizontal" action="" method="post">
<%-- 		<input type="hidden" name="zbguid" id="zbguid" value="${clfbxzb.guid}"> --%>
		<div class="box">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>明细信息项</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content" style="overflow:scroll; position: relative; width:100%">
				<table id="mydatatables" class="table table-striped table-bordered" style="width:2500px">
		        	<thead id="thead">
				        <tr>
				        	
				            <th style="width:70px !important;"><span class="required" style="color:red;">*</span>开始时间</th>
				            <th style="width:70px !important;"><span class="required" style="color:red">*</span>结束时间</th>
				            <th style="width:70px !important;"><span class="required" style="color:red">*</span>出发地点</th>
				            <th style="width:70px !important;"><span class="required" style="color:red">*</span>目的地点</th>
				            <th style="width:60px !important;"><span class="required" style="color:red">*</span>附件数（张）</th>
				            <th style="width:80px !important;">飞机票（元）</th>
				            <th  style="width:80px !important;">火车票（元）</th>
				            <th  style="width:90px !important;">出租车票（元）</th>
				            <th  style="width:80px !important;">汽车票（元）</th>
				            <th  style="width:80px !important;">其他费用（元）</th>
				            <th  style="width:80px !important;">会议费用（元）</th>
				            <th  style="width:80px !important;">培训费用（元）</th>
				            <th  style="width:150px !important;">老师生活补助天数（天）</th>
				            <th style="width:110px !important;">老师生活补助金额（元）</th>
				            <th style="width:110px !important;">学生生活补助天数（天）</th>
				             <th style="width:110px !important;">学生生活补助金额（元）（元）</th>
				             <th style="width:80px !important;">住宿费金额（元）</th>
				             
				        </tr>
					</thead>
					<tbody id="tbody">
					
					<c:if test="${mxList !=null}">
					<c:forEach var="mxList" items="${mxList}" varStatus="i">
				    	<tr id="tr_1">
				            <td>
				               <input type="hidden" name="zbguid" id="zbguid" value="${mxList.djbh}">
				            	<input type="text" id="kssj_1" name="kssj" readonly value="${mxList.kssj}" class="date null"/>
				            </td>
				            <td>
				            	<input type="text" id="jssj_1" name="jssj" readonly class="date null"  value="${mxList.jssj}" />
				            </td>
				            <td>
				            	<input type="text" id="cfdd_1" name="cfdd" readonly value="${mxList.cfdd}" class="null" />
				            </td>
				            <td>
				            	<input type="text" id="mddd_1" name="mddd" readonly class="null" value="${mxList.mddd}"/>
				            </td>
				            <td>
				            	<input type="text" id="fjs_${i.index+100}" readonly name="ffjs" class="integer null" value="${mxList.ffjs}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" id="fjje_${i.index+100}" readonly name="fjje" value="${mxList.fjje}" class="number money " onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" id="hcje_${i.index+100}" readonly name="hcje" value="${mxList.hcje}" class="number money " onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" />
				            </td>
				            <td>
				            	<input type="text" id="czcje_" name="czcje" readonly value="${mxList.czcje}" class="number money " value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" id="qcje_${i.index+100}" readonly name="qcje" value="${mxList.qcje}" class="number money " value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" id="qtfy_${i.index+100}" readonly name="qtfy" value="${mxList.qtfy}" class="number money null" value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" id="hyfy_${i.index+100}" readonly name="hyfy" class="number money null" value="${mxList.hyfy}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" id="pxfy_${i.index+100}" readonly name="pxfy" class="number money null" value="${mxList.pxfy}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" id="lsshbzts_${i.index+100}" readonly name="lsshbzts" value="${mxList.lsshbzts}" class="integer null" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" id="lsshbzje_${i.index+100}" readonly name="lsshbzje" class="number money null" value="${mxList.lsshbzje}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				             <td>
				            	<input type="text" id="xsshbzts_${i.index+100}" readonly name="xsshbzts" value="${mxList.xsshbzts}" class="integer null"  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" id="xsshbzje_${i.index+100}" readonly name="xsshbzje" class="number money null" value="${mxList.xsshbzje}" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				             <td>
				            	<input type="text" id="zsfje_${i.index+100}" readonly name="zdfje" value="${mxList.zdfje}" class="number money null" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            
				    	</tr>				   					
					</c:forEach>
					
					</c:if>								
					</tbody>				  
				</table>
			</div>
		</div>
		</div>
		</form>
		<form id="myform3" class="form-horizontal" action="" method="post">
<%-- 		<input type="hidden" name="zbguid" id="zbguid" value="${clfbxzb.guid}"> --%>
		<div class="box" style="margin-top: -40px;">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>出差人员</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="width:40% !important">
		        	<thead id="thead">
				        <tr>

				        	<th style="width:30%">姓名</th>
<!-- 				            <th>职务</th> -->
				            <th  style="width:40% ">所在部门</th>
				            <th  style="width:60% !important;visibility: hidden;">所在部门</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				     <c:forEach var="ryList" items="${ryList}" varStatus="i">
				       <tr id="trr_1">

				        	<td  >
				        	    <input type="hidden" name="zbguid" id="zbguid2" value="${ryList.txbh}">
				        		<input type="text" style="width:100% !important" id="txt_rybh${i.index+100}" readonly  name="rybh" class="bm_input" value="${ryList.rybh}" />
				        	</td>
<!-- 				            <td> -->
<!-- 				            	<input type="text" name="zw_1" class="bm_input" value="" /> -->
<!-- 				            </td> -->
				            <td >
				            	<input type="text" style="width:100% !important"  id="txt_szbmm${i.index+100}" readonly name="szdw" class="num bm_input" value="${ryList.szdw}" />
				            </td>
				            <td style="width:47% !important;visibility: hidden;"></td>
				    	</tr>
				     
				     </c:forEach>				     
				    </tbody>
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
							<input type="text" name="djbh" class="form-control input-radius" readonly value="${cjkList.djbh}">
							</td>
							<td>
							<input type="text" id="txt_jkrxm${cjk.index+1}" class="form-control input-radius" readonly value="${cjkList.jkrxm}">
							</td>
							<td>
							<input type="text" id="txt_szdw${cjk.index+1}" class="form-control input-radius" readonly value="${cjkList.dwmc}">
							</td>
							<td>
							<input type="text" id="txt_jkje${cjk.index+1}" name="jkje" class="form-control input-radius number" readonly value="${cjkList.jkje}">
							</td>
							<td>
							<input type="text" id="txt_cjkje${cjk.index+1}" name="cjkje" class="form-control input-radius number" readonly value="${cjkList.cjkje}">
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
								<input type="text" id="txt_dwmc${dg.index+1}" readonly class="form-control input-radius"  value="${dgList.dwmc}">
							</td>
							<td>
							<input type="text" id="txt_dfdq${dg.index+1}" name="dfdq" class="form-control input-radius" readonly value="${dgList.dfdq}">
							</td>
							<td>
							<input type="text" class="form-control input-radius" readonly value="${dgList.yhname}">
							</td>
							<td>
							<input type="text" id="txt_dfzh${dg.index+1}" name="dfzh" readonly class="form-control input-radius"  value="${dgList.dfzh}">
							</td>
							<td>
							<input type="text" id="txt_je${dg.index+1}" name="je" class="form-control input-radius"  value="${dgList.je}">
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
							<td class="btn_td" style="text-align:center;">${ds.index+1}</td>
							<td>
								<input type="text" readonly class=" "  <c:if test="${dsList.ryxz=='0'}">value="个人"</c:if> 
									<c:if test="${dsList.ryxz=='1'}">value="项目负责人"</c:if>
									<c:if test="${dsList.ryxz=='2'}">value="其他人"</c:if>
								/>
				    		</td>
				    		<td>
<!-- 				    		<div class="input-group"> -->
				    			<input type="text" readonly class="form-control input-radius" value="${dsList.ryxm}">
<!-- 				    		</div> -->
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius" value="${dsList.klx}">
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius" value="${dsList.dfzh}">
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius number" value="${dsList.je}" >
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
				            <th style="width:15%;">刷卡金额(元)</th>
				            <th style="width:20%;visibility: hidden;"></th>
				            
				        </tr>
					</thead>
				    <tbody id="tb_gwk">
				    	<c:forEach items="${gwkList}" var="gwkList" varStatus="gwk">
				    		<tr class="tr_${gwk.index+1}">
							<td class="btn_td" style="text-align:center;">${gwk.index+1}</td>
							<td>
			    			<input type="text" readonly class="form-control input-radius" value="${gwkList.ryxm}">
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius date" readonly value="${gwkList.skrq}">
							</td>
							
							<td>
							<input type="text" readonly class="form-control input-radius"  value="${gwkList.kh}">
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius number"  value="${gwkList.skje}">
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
	$("#btn_back").click(function(){
		var look = "${look}";
			console.log("loo====="+look);
		if(look=="look"){
			location.href="${ctx}/zkylbx/goCheckListPage";
		}else{
			location.href="${ctx}/wsbx/ccbx/goCcbxListPage?mkbh=${mkbh}&shzt=${shzt}";
		}
		//location.href="${ctx}/wsbx/ccbx/goCcbxListPage?mkbh=${mkbh}";
	});
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