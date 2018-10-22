<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>基本支出预算编辑</title>
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
	.btn_td{
		width:14mm!important;
		height:6mm!important;
		
	}
	.selWindow{
		width:280px!important;
	}
	
	.number1{
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
</style>
</head>
<body>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
String date = sdf.format(new Date());
int dyn = Integer.parseInt(date)+1;
int den = Integer.parseInt(date)+2;
int dsn = Integer.parseInt(date)+3;
%>
<div id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>基本支出预算</span>
		</div>
        <div class="pull-right">
		
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>基本支出预算信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<form id="table2">
			  <input type="hidden" name="guid" id="guid" value="${guid}">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申报人员</span>
                            <input readonly type="text" id="txt_sbry1" class="form-control input-radius" name="sbry1" value="${srys.sbry1 }" readonly>
                            <input readonly type="hidden" id="txt_sbry" class="form-control input-radius" name="sbry" value="${srys.sbry }">
<!--                             <span class="input-group-btn"><button type="button" id="btn_sbry" class="btn btn-link btn-custom">选择</button></span>
 -->                            
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span readonly class="input-group-addon">申报部门</span>
							<input readonly type="text" id="txt_sbbm1" class="form-control input-radius window" name="sbbm1" value="${srys.sbbm1 }"/>
							<input readonly type="hidden" id="txt_sbbm" class="form-control input-radius window" name="sbbm" value="${srys.sbbm }"/>
<!-- 							<span class="input-group-btn"><button type="button" id="btn_sbbm" class="btn btn-link btn-custom">选择</button></span>
 -->						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申报年度</span>
							<input readonly type="text" id="txt_sbnd" class="form-control input-radius year" name="sbnd" value="${srys.sbnd }" data-format="yyyy"/>
							 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><%=dyn %>年支出汇总（万元）</span>
							<input readonly type="text" id="" class="form-control input-radius number1" name="dynzchz" value="<fmt:formatNumber value="${srys.dynzchz  }"  pattern=".0000"/>" readonly/>
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><%=den %>年支出汇总（万元）</span>
							<input readonly type="text" id="" class="form-control input-radius number1" name="denzchz" value="<fmt:formatNumber value="${srys.denzchz }" pattern=".0000"/>" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><%=dsn %>年支出汇总（万元）</span>
							<input readonly type="text" id="" class="form-control input-radius number1" name="dsnzchz" value="<fmt:formatNumber value="${srys.dsnzchz }" pattern=".0000"/>" readonly/>
						</div>
					</div>
				</div>
				 <div class="row">
				   <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">测算依据</span>
							<textarea readonly id="txt_csyj" style="width:100%;height:80px" name="csyj" class="form-control input-radius" >${srys.csyj }</textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">开支范围</span>
							<textarea readonly id="txt_kzfw" style="width:100%;height:80px" name="kzfw" class="form-control input-radius" >${srys.kzfw }</textarea>
						</div>
					</div>					
			  </div>
			  
			  </form>
			</div>
		</div>
		<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>基本支出预算明细信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
			<div class="container-fluid box-content">
			<form id="table3">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				       <tr>
				        <!-- 	<th>操作</th> -->
				            <th style="width:300px;">经济科目</th>
				            <th style="width:250px;"><%=dyn %>年支出（万元）</th>
				            <th style="width:250px;"><%=den %>年支出（万元）</th>
				            <th style="width:250px;"><%=dsn %>年支出（万元）</th>
				            <th style="width:450px;">说明</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				       <c:forEach var="srysmx" items="${srysmx}"> 
				    	<tr id="tr_1" class="tr1">
				    		<!-- <td class="btn_td">
							  <div class="addBtn add"></div>
				    		</td> -->
				    		<td>
				    		<!-- <div class="input-group"> -->
				    		
				    			<input readonly type="text" id="txt_jjkm${srysmx.guid }" class="form-control input-radius window null"  name="jjkm1" readonly value="${srysmx.jjkmbh1 }">
				    			<input readonly type="hidden" id="txt_jjkbh"  name="jjkmbh" value="${srysmx.jjkmbh }">
				    			<input readonly type="hidden" id="txt_guid" class="bz" name="guid" value="${srysmx.guid }">
				    			<input readonly type="hidden" id="txt_jbzcbh"  name="jbzcbh" value="${guid}">
				    		
				    			<%-- <span class="input-group-btn">
 				    			<button type="button" id="btn_jjkm" aj="txt_bz${srysmx.guid }" sj="txt_jjkm${srysmx.guid }" bj="txt_jjkbh${srysmx.guid }" class="btn btn-link btn-custom">选择</button>
 				    			</span> --%>
				    	<!-- 	</div> -->
				    		</td>
				    		<td>
				    			<input readonly type="text" id="txt_dynzc" class="form-control input-radius number1 null zc" name="dynzc" value="<fmt:formatNumber value="${srysmx.dynzc }"  pattern=".0000"/>">
				    		</td>
				    		<td>
				    			<input readonly type="text" id="txt_denzc" class="form-control input-radius number1 null zc" name="denzc" value="<fmt:formatNumber value="${srysmx.DENZC }"  pattern=".0000"/>"  >
				    		</td>
				    		<td>
				    			<input readonly type="text" id="txt_dsnzc" class="form-control input-radius number1 null zc" name="dsnzc" value="<fmt:formatNumber value="${srysmx.DSNZC }"  pattern=".0000"/>" >
				    		</td>
				    		<td>
				    			<input readonly type="text" id="txt_bz${srysmx.guid }" class="form-control input-radius bz" name="bz" value="${srysmx.BZ }" readonly>
				    		</td>
				    	</tr>
				    	</c:forEach>
				    </tbody>
				</table>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//返回按钮
	$("#btn_back").click(function(e){ 
		location.href="${ctx}/webView/ysgl/bmysbz/jbzcys/jbzcys_list.jsp";
	});	
});

</script>

</html>