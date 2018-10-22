<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>日常报销</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
		.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   		border-bottom-width: 1px!important;
    }
     table{
		border-collapse:collapse!important;
	}
	.btn_td{
		text-align:center;
	}
/* 	.addBtn{ */
/* 		width:14mm; */
/* 		height:6mm; */
/* 		background-image: url("${ctx}/webView/wsbx/rcbx/add.gif"); */
/* 	} */
/* 	.deleteBtn{ */
/* 		width:14mm; */
/* 		height:6mm; */
/* 		background-image: url("${ctx}/webView/wsbx/rcbx/del.gif"); */
/* 	} */
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
	#tbody input{
		border:none;
		width:100%;
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
	.text-green{
		color:green!important;
	}
	tr{
background-color: white !important;
}
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
	<div class="box">
		<div class="box-panel1">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>日常报销信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
			  		<button type="button" class="btn btn-default" id="btn_back"><c:if test="${look=='look'}"> 返回</c:if><c:if test="${look!='look'}"> 返回列表</c:if></button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号</span>
							<input type="text" id="txt_djbh" readonly class="form-control input-radius" readonly name="djbh" value="${zbMap.djbh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报&ensp;销&ensp;人</span>
                            <input type="text" id="txt_bxr" readonly class="form-control input-radius window" name="" value="${zbMap.bxrname}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">部&emsp;&emsp;门</span>
							<input type="text" id="txt_bmmc" readonly class="form-control input-radius window" name="" value="${zbMap.dwname}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">附件总张数（张）</span>
							<input type="text" readonly id="txt_fjzzs" class="form-control input-radius sz" name="fjzzs" value="${zbMap.fjzzs}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销总金额（元）</span>
							<input type="text" id="txt_bxzje" class="form-control input-radius number sz" name="bxzje" value="${zbMap.bxzje}" 
							 onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly/>
						</div>
					</div>
<!-- 						<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">是否科研类报销</span> -->
<!-- 							<div class="radiodiv">&nbsp;&nbsp;&nbsp; -->
<%-- 								<input type="radio" disabled  name="sfkylbx" value="1" <c:if test="${zbMap.sfkylbx=='1'}" >checked</c:if>/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
<%-- 								<input type="radio" disabled name="sfkylbx" value="0" <c:if test="${zbMap.sfkylbx!='1'}" >checked</c:if>/>否 --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">报销事由</span>
							<textarea style="width:100%;height:60px;" name="bxsy" readonly class="form-control input-radius">${zbMap.bxsy}</textarea>
						</div>
					</div>
				</div>
			<%-- 	<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
							<textarea style="width:100%;height:60px;" name="bz" readonly class="form-control input-radius">${zbMap.bz}</textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
							<textarea style="width:100%;height:60px;" readonly class="form-control input-radius">${zbMap.shyj}</textarea>
						</div>
					</div>
				</div>	 --%>	
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
		<form id="form3" action="">
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
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="width:100%!important;">
		        	<thead id="thead">
				        <tr>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<c:forEach items="${fpxxList}" var="list" varStatus="i">
				    		<c:if test="${list.zbid!=null and list.fph1!=null}">
			    			<tr class="tr_${i.index+1}">
<!-- 				    		<td class="btn_td"><div class="deleteBtn"></div></td> -->
								<td class="btn_td" style="line-height: 22px;">${i.index+1}</td>
								<td>
									<span>发票号</span>
<%-- 								<input type="text" id="dxt_fp${i.index+1}" class=" input-radius window null" readonly name="fp" value=""> --%>
								</td>
								<td>
								<input type="text" id="dxt_fpxx${i.index+1}" class=" input-radius window null" name="fpxx1" value="${list.fph1}">
								</td>
								<td>
								<input type="text" id="dxt_fpxx${i.index+1}" class=" input-radius window null" name="fpxx2" value="${list.fph2}">
								</td>
								<td>
								<input type="text" id="dxt_fpxx${i.index+1}" class=" input-radius window null" name="fpxx3" value="${list.fph3}">
								</td>
								<td>
								<input type="text" id="dxt_fpxx${i.index+1}" class=" input-radius window null" name="fpxx4" value="${list.fph4}">
								</td>
								<td>
								<input type="text" id="dxt_fpxx${i.index+1}" class=" input-radius window null" name="fpxx5" value="${list.fph5}">
								</td>
							</tr>
				    		</c:if>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form>
		<form id="form2" action="">
		<div class="box" id="operate">
			<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>报销明细信息
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
				        	<th style="width:51px;">操作</th>
				            <th >项目名称</th>
				            <th >剩余金额（元）</th>
<!-- 				            <th >政府采购剩余金额（元）</th> -->
<!-- 				            <th >非政府采购剩余金额（元）</th> -->
				            <th >费用名称</th>
				            <th >报销金额（元）</th>
				            <!-- <th >政府采购金额（元）</th>
				            <th >非政府采购金额（元）</th> -->
				            <th >票据张数（张）</th>
				            <th >备注</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				    	<c:forEach items="${xmhxlist}" var="list" varStatus="i">
				    		<c:if test="${list.xmguid!=null and list.xmmc!=null}">
<!-- 							<td class="btn_td"><div class="deleteBtn"></div></td> -->
							<tr class="tr_${i.index+1}">
<%-- 								<td class="btn_td" style="line-height: 22px;">${i.index+1}</td> --%>
								<td class="btn_td"><div class="addBtn add"></div></td>
								<td>
								<div class="input-group">
								<input type="text" id="dxt_xmmc${i.index+1}" class=" input-radius window null" readonly name="xmmc" value="${list.xmmc}">
								<input type="hidden" id="dxt_xmid${i.index+1}"   name="xmguid" value="${list.xmguid}">
								<input type="hidden" id=""   name="jsbxzje" value="">
								<input type="hidden" id=""   name="jyzjehj" value="">
								<input type="hidden"  name="zbid" value="${zbid}">
								<span class="input-group-btn">
					    			<button type="button" id="btn_xmmc1"  class="btn btn-link btn-custom"></button>
				    			</span>
								</div>
								</td>
								<td>
								<input type="text" id="dxt_syje${i.index+1}" class=" null window input-radius number" readonly="readonly" name="syje" value="${list.syje}"
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								</td>
<!-- 								<td> -->
<%-- 								<input type="text" id="dxt_zfcgsyje${i.index+1}" class=" null window input-radius number" readonly="readonly" name="zfcgsyje" value="${list.zfcgsyje}" --%>
<!-- 									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"> -->
<!-- 								</td> -->
<!-- 								<td> -->
<%-- 								<input type="text" id="dxt_fzfcgsyje${i.index+1}" class=" null window input-radius number" readonly="readonly" name="fzfcgsyje" value="${list.fzfcgsyje}" --%>
<!-- 									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"> -->
<!-- 								</td> -->
<!-- 								<td> -->
<%-- 								<input type="text" id="dxt_bcbxje${i.index+1}" class=" null window input-radius number" name="bcbxje" value="${list.bcbxje}" --%>
<!-- 										  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"> -->
<!-- 								</td> -->
								<td>
								<div class="input-group">
								<input type="text" id="txt_fymc${i.index+1}" a="a" style="background-color: white !important;" name="fymc" readonly class=" input-radius window null" value="${list.fymc}">
								<input type="hidden" id="txt_fyid${i.index+1}" a="a"   name="fyid" value="${list.fyid}">
<!-- 								<span class="input-group-btn"> -->
<!-- 					    			<button type="button" id="btn_fymc1"  class="btn btn-link btn-custom">选择</button> -->
<!-- 				    			</span> -->
								</div>
								</td>
								<td>
								<input type="text" id="txt_bxje${i.index+1}" a="a" class=" input-radius sz number null txt_bxje" readonly name="bxje" value="${list.bxje}"
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								</td>
								<%-- <td>
								<input type="text" id="txt_zfcgje${i.index+1}" a="a" class=" input-radius sz number null txt_zfcgje" name="zfcgje" value="${list.zfcgje}"
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								</td>
								<td>
								<input type="text" id="txt_fzfcgje${i.index+1}" a="a" class=" input-radius sz number null txt_fzfcgje" name="fzfcgje" value="${list.fzfcgje}"
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								</td> --%>
								<td>
								<input type="text" id="txt_pjzs${i.index+1}" a="a"  class=" input-radius sz null int" readonly name="fjzs" value="${list.fjzs}"
										  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								</td>
								<td>
								<input type="hidden"  name="zbid" value="${zbid}">
								<input type="text" id="txt_bz${i.index+1}" a="a"  class=" null window input-radius" readonly name="bz" value="${list.bz}">
								</td>
							</tr>
				    		</c:if>
				    	</c:forEach>
<!-- 						<tr class="tr_0"> -->
<!-- 							<td class="btn_td"><div class="addBtn"></div></td> -->
<!-- 							<td> -->
<!-- 								<div class="input-group"> -->
<!-- 									<input type="text" id="txt_ktmc" class=" input-radius window null" readonly name="xmmc" value=""> -->
<!-- 									<input type="hidden" id="txt_jfbh"   name="xmguid" value=""> -->
<!-- 									<span class="input-group-btn"> -->
<!-- 						    			<button type="button" id="btn-ktmc"  class="btn btn-link btn-ktmc">选择</button> -->
<!-- 					    			</span> -->
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<input type="text" id="txt_syje" class="bk null window input-radius number" name="syje" value="" -->
<!-- 								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<div class="input-group"> -->
<!-- 							<input type="text" id="txt_fymc0" style="background-color: white !important;" class=" input-radius window null" readonly name="fymc" value=""> -->
<%-- 							<input type="hidden"  name="zbid" value="${zbid}"> --%>
<!-- 							<input type="hidden" id="txt_fyid" name="fyid" value=""> -->
<!-- 							<span class="input-group-btn"> -->
<!-- 				    			<button type="button" id="btn_fymc"  class="btn btn-link btn-custom">选择</button> -->
<!-- 			    			</span> -->
<!-- 							</div> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<input type="text" id="txt_bxje" class=" input-radius sz number null" name="bxje" value="" -->
<!-- 								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<input type="text" id="txt_fjzs" class=" input-radius sz null int" name="fjzs" value="" -->
<!-- 									  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<input type="text" id="txt_bz" class=" input-radius" name="bz" value="" placeholder=""> -->
<!-- 							</td> -->
<!-- 						</tr> -->
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form>
		<%-- <div class="box" id="operate">
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
				            <th style="width:300px;">项目名称</th>
				            <th style="width:300px;">剩余金额(元)</th>
				            <th style="width:300px;">本次报销金额</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<c:forEach items="${xmhxlist}" var="list" varStatus="i">
				    		<c:if test="${list.bcbxje!=null and list.xmmc!=null}">
				    		<tr class="tr_${i.index+1}">
							<td class="btn_td" style="text-align:center;line-height: 22px;text-align: center;">${i.index+1}</td>
							<td>
							<input type="text" readonly style="width:100%" class="input-radius window null " value="${list.xmmc}">
							</td>
							<td>
							<input type="text" style="width:100%" readonly class="null window input-radius number"  value="${list.syje}">
							</td>
							<td>
							<input type="text" readonly style="width:100%" class="null window input-radius number" required="required" value="${list.bcbxje}">
							</td>
				    		</c:if>
						</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
	</div> --%>
		<%-- <div class="box" id="operate">
			<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>报销明细信息
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
				        	<th style="width:51px;">序号</th>
				            <th style="width:300px;">费用名称</th>
				            <th style="width:300px;">金额（元）</th>
				            <th style="width:300px;">票据张数（张）</th>
				            <th style="width:550px;">备注</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<c:forEach items="${list}" var="list" varStatus="i">
				    		<c:if test="${list.bxje!=null and list.fjzs!=null}">
				    		<tr class="tr_${i.index+1}">
							<td class="btn_td" style="vertical-align: middle;">${i.index+1}</td>
							<td>
							<input type="text" readonly class="bk input-radius " value="${list.fymc}">
							</td>
							<td>
							<input type="text" readonly class="bk input-radius sz number null"  value="${list.bxje}">
							</td>
							<td>
							<input type="text" readonly class="bk input-radius sz null int" value="${list.fjzs}">
							</td>
							<td>
							<input type="text" readonly class="bk input-radius" name="bz" value="${list.bz}">
							</td>
				    		</c:if>
						</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
	</div> --%>
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
				        	<th style="width:51px;">序号</th>
				            <th style="width:300px;">借款单号</th>
				            <th style="width:300px;">借款人</th>
				            <th style="width:300px;">所在部门</th>
				            <th style="width:100px;">借款金额(元)</th>
				            <th style="width:100px;">冲借款金额(元)</th>
				        </tr>
					</thead>
				    <tbody id="tb_cxjk">
				    	<c:forEach items="${cxList}" var="cjkList" varStatus="cjk">
				    		<tr class="tr_${cjk.index+1}">
							<td class="btn_td" style="vertical-align: middle;">${cjk.index+1}</td>
							<td>
							<input type="text" name="djbh" class="bk input-radius" readonly value="${cjkList.djbh}">
							</td>
							<td>
							<input type="text" id="txt_jkrxm${cjk.index+1}" class="bk input-radius" readonly value="${cjkList.jkrxm}">
							</td>
							<td>
							<input type="text" id="txt_szdw${cjk.index+1}" class="bk input-radius" readonly value="${cjkList.dwmc}">
							</td>
							<td>
							<input type="text" id="txt_jkje${cjk.index+1}" name="jkje" class="bk input-radius number" readonly value="${cjkList.jkje}">
							</td>
							<td>
							<input type="text" id="txt_cjkje${cjk.index+1}" name="cjkje" class="bk input-radius number" readonly value="${cjkList.cjkje}">

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
							<td class="btn_td" style="vertical-align: middle;">${dg.index+1}</td>
							<td>
								<input type="text" id="txt_dwmc${dg.index+1}" readonly class="bk input-radius"  value="${dgList.dwmc}">
							</td>
							<td>
							<input type="text" id="txt_dfdq${dg.index+1}" name="dfdq" class="bk input-radius" readonly value="${dgList.dfdq}">
							</td>
							<td>
							<input type="text" class="bk input-radius" readonly value="${dgList.yhname}">
							</td>
							<td>
							<input type="text" id="txt_dfzh${dg.index+1}" name="dfzh" readonly class="bk input-radius"  value="${dgList.dfzh}">
							</td>
							<td>
							<input type="text" id="txt_je${dg.index+1}" readonly name="je" class="bk input-radius number"  value="${dgList.je}">
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
							<td class="btn_td" style="vertical-align: middle;">${ds.index+1}</td>
							<td>
								<input type="text" class="bk input-radius" <c:if test="${dsList.ryxz=='0'}">value="个人"</c:if> 
									<c:if test="${dsList.ryxz=='1'}">value="项目负责人"</c:if>
									<c:if test="${dsList.ryxz=='2'}">value="其他人"</c:if> readonly
								/>
				    		</td>
				    		<td>
<!-- 				    		<div class="input-group"> -->
				    			<input type="text" readonly class="bk input-radius" value="${dsList.ryxm}">
<!-- 				    		</div> -->
							</td>
							<td>
							<input type="text" readonly class="bk input-radius" value="${dsList.klx}">
							</td>
							<td>
							<input type="text" readonly class="bk input-radius" value="${dsList.dfzh}">
							</td>
							<td>
							<input type="text" readonly class="bk input-radius number" value="${dsList.je}" >
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
				            <th style="width:15%;">报销金额(元)</th>
				            <th style="width:15%;">刷卡金额(元)</th>
				            <th style="width:20%;visibility: hidden;"></th>
				           
				        </tr>
					</thead>
				    <tbody id="tb_gwk">
				    	<c:forEach items="${gwkList}" var="gwkList" varStatus="gwk">
				    		<tr class="tr_${gwk.index+1}">
							<td class="btn_td bk" style="vertical-align: middle;">${gwk.index+1}</td>
							<td>
			    			<input type="text" readonly class="bk input-radius" value="${gwkList.ryxm}">
							</td>
							<td>
							<input type="text" readonly class="bk input-radius date" readonly value="${gwkList.skrq}">
							</td>
							
							<td>
							<input type="text" readonly class="bk input-radius"  value="${gwkList.kh}">
							</td>
							<td>
							<input type="text" readonly class="bk input-radius number"  value="${gwkList.skje}">
							</td>
							<td>
							<input type="text" readonly class="bk input-radius number"  value="${gwkList.sjskje}">
							</td>
						</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
	
	<!-- 零余额 -->
	<div class="box" id="lye">
			<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>零余额信息
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
				            <th style="width:25%;">户名</th>
				            <th style="width:25%;">银行</th>
				            <th style="width:25%;">银行卡号</th>
				            <th style="width:20%;">金额(元)</th>
				        </tr>
					</thead>
				    <tbody id="tb_lye">
				    	<c:forEach items="${lyeList}" var="lyeList" varStatus="lye">
				    		<tr class="tr_${lye.index+1} gd" >
				    		 <input type="hidden" name="start" value=""/>
							<td class="btn_td" style="padding:4px;">${gwk.index+1}</td>
							<td >
							  <input type="text" id="txt_hm${lye.index+1}" name="hm" class="bk input-radius  lyehm" value="${lyeList.hm}">
							</td>
							<td >
							  <input type="text" id="txt_yh${lye.index+1}" name="yh" class="bk input-radius  lyeyh" value="${lyeList.yh}">
							</td >
							
							<td >
							<input type="text" id="txt_yhkh${lye.index+1}" name="yhkh" class="bk input-radius int lyeyhkh"  value="${lyeList.yhkh}">
							</td>
							<td >
							<input type="text" id="txt_lyeje${lye.index+1}" name="je" class="bk input-radius number lyeje"  value="${lyeList.je}"
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
							</td>

							 <input type="hidden" name="end" value=""/>
						</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
	
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
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
// 		console.log("look======"+look);
// 		if (look=="look"){
			
// 			location.href="${ctx}/zkylbx/goCheckListPage";
// 		}else{
// 			location.href="${ctx}/wsbx/rcbx/goRcbxListPage?mkbh=${mkbh}&shztdm=${shztdm}";
// 		}
		history.back();
		//location.href="${ctx}/wsbx/rcbx/goRcbxListPage?mkbh=${mkbh}";
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
	//零余额
	if("${zbMap.SFLYE}"!="1"){
		$("#lye").css("display","none");
	}
}
</script>

</html>