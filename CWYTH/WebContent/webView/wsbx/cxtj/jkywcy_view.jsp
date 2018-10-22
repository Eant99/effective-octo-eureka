<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>查看借款业务</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.tool-fix{
		display:none;
	}
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	    background-color:#eee;
	}
	input{
		disabled:disabled;
		readonly:readonly;
	}
	table{
		border-collapse:collapse;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	
	
	<div class='page-header'>
       	<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>查看借款业务信息</span>
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box" style="top:36px">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>借款业务信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">借&ensp;款&ensp;人</span>
							<input type="text" id="txt_jkr" class="form-control input-radius window" name="jkr" disabled="disabled" value="张强">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">借款单位</span>
							<input type="text" id="txt_jkje" class="form-control input-radius text-left number" name="jkje" disabled="disabled"  value="教务处">
						</div>
					</div>
					
				</div>
				<div class="row">	
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" >借款时间</span>
							<input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" disabled="disabled" value="2017-08-08"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" >项目名称</span>
							<input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" disabled="disabled" value="测试测试"/>
						</div>
					</div>
					</div>
					<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">借款金额（元）</span>
							<input type="text" id="txt_jkje" class="form-control input-radius text-right number" name="jkje" disabled="disabled"  value="5000.00">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">票据张数（张）</span>
							<input type="text" id="txt_pjzs" class="form-control input-radius text-right int" name="pjzs" disabled="disabled" value="6">
						</div>
					</div>
				</div>
				  <!---->
				  <div class="row">
				  <div class="col-md-12">
			      <div class="input-group">
				<span class="input-group-addon"> 审核状态</span>
				<div id="editor" style="height: 100px;">
					<textarea style="width:100%;height:100px;" name="fy" id="fy" data-bv-field="fy" disabled="disabled">待审核</textarea>
				</div>
			    </div>
				</div>
			</div>
			<div class="row">
				  <div class="col-md-12">
			      <div class="input-group">
				<span class="input-group-addon"> 审核意见</span>
				<div id="editor" style="height: 100px;">
					<textarea style="width:100%;height:100px;" name="fy" id="fy" data-bv-field="fy" disabled="disabled">测试测试</textarea>
				</div>
			    </div>
				</div>
			</div>
			<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>报销明细信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	
				        <tr>
				            <th style="width:300px;">费用名称</th>
				            <th style="width:300px;">金额（元）</th>
				            <th style="width:300px;">票据张数（张）</th>
				            <th style="width:550px;">备注</th>
				        </tr>
					
				    
				    	<tr id="tr_1">
				    		<td>
				    			<input type="text" id="txt_fymc1" class="form-control input-radius window" name="fymc1" value="1" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_je1" class="form-control input-radius sz number" name="je1" value="2"
				    			onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_pjzs1" class="form-control input-radius sz" name="pjzs1" value="3"
				    			onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_bz" class="form-control input-radius" name="bz" value="4" readonly>
				    		</td>
				    	</tr>
				    
				</table>
			</div>
		</div>
			<div class="box-panel" id="">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>结算信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	
				        <tr>
				            <th style="width:300px;">结算方式</th>
				            <th style="width:300px;">金额（元）</th>
				        </tr>
					
				    
				    	<tr id="tr_1">
				    		<td>
				    			<input type="text" id="txt_fymc1" class="form-control input-radius window" name="fymc1" value="现金结算" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_je1" class="form-control input-radius sz number" name="je1" value="100.00" readonly
				    			onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
				    		</td>
				    	</tr>
				    
				</table>
			</div>
		</div>
			</div>
			</div>
		</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
//获取json
$.getJSON("${ctx}/json/wsbx/jkyw/jkywView.json",function(data){
	/* $("#txt_jkr").val(data["JKR"]);
	$("#txt_bmmc").val(data["BMMC"]);
	$("#txt_xmmc").val(data["XMMC"]);
	$("#txt_jkje").val(data["JKJE"]);
	$("#txt_zffs").val(data["ZFFS"]);
	$("#txt_pjzs").val(data["PJZS"]);
	$("#txt_sklx").val(data["SKLX"]);
	$("#txt_skr").val(data["SKR"]);
	$("#txt_khyh").val(data["KHYH"]);
	$("#txt_skryhzh").val(data["SKRYHZH"]);
	$("#jkyy").val(data["JKYY"]);
	$("#fy").val(data["FY"]);
	$("#bz").val(data["BZ"]); */
});
$(function(){
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/wsbx/cxtj/jkywcy";			
	});
});
</script>

</html>