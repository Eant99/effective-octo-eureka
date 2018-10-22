<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   		border-bottom-width: 1px!important;
    }
   table{
		border-collapse:collapse!important;
	}
	.addBtn{
		width:14mm;
		height:6mm;
		background-image: url("${ctx}/webView/wsbx/rcbx/add.gif");
	}
	.deleteBtn{
		width:14mm;
		height:6mm;
		background-image: url("${ctx}/webView/wsbx/rcbx/del.gif");
	}
	.text-green{
		color:green!important;
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
	th{
		text-align:center;
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
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
          <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>保存</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-green">选择项目&nbsp;></div>
					
					<div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
	    			</div>
					</div>
					<div class="sub-title pull-left  text-green">日常报销业务办理&nbsp;></div>
					
					<div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-green">结算方式设置&nbsp;></div>
					
					<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">4</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-primary">保存</div>
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
			  	 	<button type="button" class="btn btn-default" id="btn_back">返回</button>
       	 		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
					<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报&ensp;销&ensp;人</span>
                            <input type="text" id="txt_bxr" class="form-control input-radius" name="bxr" value="超级管理员">
						</div>
					</div>
					<div class="col-md-4"> 
						<div class="input-group">
							<span class="input-group-addon">部&emsp;&emsp;门</span>
							<input type="text" id="txt_bmmc" class="form-control input-radius" name="bmmc" value="教务处"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
							<input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value=""/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">票据张数（张）</span>
							<input type="text" id="txt_pjzzs" class="form-control input-radius sz" name="pjzzs" value="0" readonly
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">业务单号</span>
							<input type="text" id="txt_cjkje" class="form-control input-radius number sz" name="cjkje" value=""
							readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">业务总金额（元）</span>
							<input type="text" id="txt_hkje" class="form-control input-radius number sz" name="hkje" value="" readonly
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">结算总金额（元）</span>
							<input type="text" id="txt_lkje" class="form-control input-radius number sz" name="lkje" value=""
							readonly onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
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
		        	<thead id="thead">
				        <tr>
				            <th style="width:300px;">费用名称</th>
				            <th style="width:300px;">金额（元）</th>
				            <th style="width:300px;">票据张数（张）</th>
				            <th style="width:550px;">备注</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1">
				    		<td>
				    			<input type="text" id="txt_fymc1" class="form-control input-radius window" name="fymc1" value="1">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_je1" class="form-control input-radius sz number" name="je1" value="2"
				    			onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_pjzs1" class="form-control input-radius sz" name="pjzs1" value="3"
				    			onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_bz" class="form-control input-radius" name="bz" value="4">
				    		</td>
				    	</tr>
				    </tbody>
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
		        	<thead id="thead">
				        <tr>
				            <th style="width:300px;">结算方式</th>
				            <th style="width:300px;">金额（元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1">
				    		<td>
				    			<input type="text" id="txt_fymc1" class="form-control input-radius window" name="fymc1" value="现金结算" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_je1" class="form-control input-radius sz number" name="je1" value="100.00" readonly
				    			onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
				    		</td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
		<div class="container-fluid box-content">
		<div class="row">
			<div class="pull-center" style="text-align:center;">
	    		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
	    		<button type="button" class="btn btn-default" id="btn_next" style="background:#00acec;color: white;">完成</button>
	 		</div>
		</div>
	</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	$("input:not(#txt_shyj)").attr("readonly","readonly");
	//返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/wsbx/rcbx/goRcbxListPage";
	});
	$("#btn_after").click(function(){
		location.href = "${ctx}/webView/wsbx/rcbx/jsfs_operate.jsp";
	});
	$("#btn_next").click(function(){
		alert("保存成功");
		location.href="${ctx}/wsbx/rcbx/goRcbxListPage";
	});
});
</script>

</html>