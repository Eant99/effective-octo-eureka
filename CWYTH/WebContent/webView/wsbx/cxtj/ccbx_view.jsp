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
		width:13mm!important;
		height:5mm!important;
		background-image: url("${ctx}/webView/wsbx/rcbx/add.gif");
	}
	.deleteBtn{
		width:13mm!important;
		height:5mm!important;
		background-image: url("${ctx}/webView/wsbx/rcbx/del.gif");
	}
	.add{
		width:13mm!important;
		height:5mm!important;
		background-image: url("${ctx}/webView/wsbx/rcbx/add.gif");
	}
	.delete{
		width:13mm!important;
		height:5mm!important;
		background-image: url("${ctx}/webView/wsbx/rcbx/del.gif");
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
		width:80px;
	}
	th{
		text-align:center;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
         <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>查看差旅报销信息</span>
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>差旅报销信息
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
							<span class="input-group-addon">报&ensp;销&ensp;人</span>
                            <input type="text" id="txt_bxr" class="form-control input-radius window" name="bxr" value="超级管理员">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">部&emsp;&emsp;门</span>
							<input type="text" id="txt_bmmc" class="form-control input-radius window" name="bmmc" value="教务处"/>
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
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">出差事由</span>
							<div id="editor" class="border-bottom: 1px solid #ccc;">
								<textarea style="width:100%;height:100px;" id="txt_ccsy" readonly name="ccsy" id="ccsy" data-bv-field="ccsy" class="form-control input-radius"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
							<div id="editor" class="border-bottom: 1px solid #ccc;">
								<textarea style="width:100%;height:100px;" id="txt_ccsy" readonly name="ccsy" id="ccsy" data-bv-field="ccsy" class="form-control input-radius"></textarea>
							</div>
						</div>
					</div>
				</div>							
			</div>
							
			</div>
		<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>差旅信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				            <th>费用项目</th>
				            <th>出发时间</th>
				            <th>出发地点</th>
				            <th>到达时间</th>
				            <th>到达地点</th>
				            <th>出差天数（天）</th>
				            <th>车船/机票费（元）</th>
				            <th>交通工具</th>
				            <th>票据张数（张）</th>
				            <th>住宿费（元）</th>
				            <th>补助费（元）</th>
				            <th>发票号码</th>
				            <th>其他费用（元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1">
				            <td>
				            	<input type="text" name="fyxm_1" value="" />
				            </td>
				            <td>
				            	<input type="text" name="cfsj_1" class="date" value="2017-10-16" />
				            </td>
				            <td>
				            	<input type="text" name="cfdd_1" value="" />
				            </td>
				            <td>
				            	<input type="text" name="ddsj_1" class="date" value="" />
				            </td>
				            <td>
				            	<input type="text" name="dddd_1" value="" />
				            </td>
				            <td>
				            	<input type="text" name="ccts_1" value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" name="ccjp_1" value="" />
				            </td>
				            <td>
				            	<input type="text" name="jtgj_1" value="" />
				            </td>
				            <td>
				            	<input type="text" name="pjzs_1" class="integer" value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" name="zsf_1" class="num" value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" name="bzf_1" class="num" value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" name="fphm_1" value="125225222" />
				            </td>
				            <td>
				            	<input type="text" name="qtfy_1" class="num" value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
		<div class="box-panel" id="">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>出差人员</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th></th>
				        	<th>姓名</th>
				            <th>部门</th>
				            <th>人员级别</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	
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
				    			<input type="text" id="txt_fymc1" class="form-control input-radius window" style="width:100%;" name="fymc1" value="现金结算" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_je1" class="form-control input-radius sz number" style="width:100%;" name="je1" value="100.00" readonly
				    			onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
				    		</td>
				    	</tr>
				    </tbody>
				</table>
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
	$("#btn_back").click(function(){
		location.href="${ctx}/wsbx/cxtj/ccbxcy";
	});
});
</script>

</html>