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
<title>收入预算查看</title>
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
	
	.number{
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
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String date = sdf.format(new Date());
%>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>查看收入预算信息</span>
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>收入预算申报信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号</span>
                            <input type="text" id="txt_djbh" class="form-control input-radius" name="djbh" readonly value="20171030">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位名称</span>
							<input type="text" id="txt_dwmc" class="form-control input-radius window" name="dwmc" value="教务处" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">编制年度</span>
							<input type="text" id="txt_bznd" class="form-control input-radius" name="bznd" value="2017" readonly data-format="yyyy"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">上年预算总额（万元）</span>
							<input type="text" id="" class="form-control input-radius number" name="snysze" value="" readonly "/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">上年收入总额（万元）</span>
							<input type="text" id="" class="form-control input-radius number" name="snsrze" value=""
							readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">本年预算总额（万元）</span>
							<input type="text" id="" class="form-control input-radius number" name="bnysze" value="" readonly/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">填&ensp;报&ensp;人</span>
							<input type="text" id="txt_tbr" class="form-control input-radius" name="tbr" value="超级管理员" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">填报时间</span>
							<input type="text" id="txt_tbsj" class="form-control input-radius" name="tbsj" value="<%=date%>" readonly/>
						</div>
					</div>
			  </div>
				
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">测算依据</span>
							<textarea style="width:100%;height:100px" name="csyj" class="form-control input-radius" readonly></textarea>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
							<textarea style="width:100%;height:100px" name="csyj" class="form-control input-radius" readonly></textarea>
						</div>
					</div>
				</div>					
			</div>
		</div>
		<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>预算申报明细信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				            <th style="width:300px;"><font color="">预算科目</font></th>
				            <th style="width:300px;"><font color="">上年预算（万元）</font></th>
				            <th style="width:300px;"><font color="">上年收入（万元）</font></th>
				            <th style="width:300px;"><font color="">本年预算（万元）</font></th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1">
				    		<td>
				    			<input type="text" id="" class="form-control input-radius " name="" value="" readonly/>
				    		</td>
				    		<td>
				    		<input type="text" id="" class="form-control input-radius " name="" value="" readonly/>
				    		</td>
				    		<td>
				    		<input type="text" id="" class="form-control input-radius " name="" value="" readonly/>
				    		</td>
				    		<td>
				    		<input type="text" id="" class="form-control input-radius " name="" value="" readonly/>
				    		</td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/webView/ysgl/srys/check_list.jsp";
	});
});
</script>

</html>