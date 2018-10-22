<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>帮助信息设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
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
	.text-green{
		color:green!important;
	}
</style>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}"> 
	<input type="hidden" id="guid"	name="guid" value="${guid}"> 
	<input type="hidden" name="czr"	value="${loginId}">
	<div class="box">
		<div class="box-panel">		
			<div class="box-header1 clearfix">
            	<div class="sub-title pull-left text-primary">
            		<input type="radio" id="xz_pzbh" name="xz">按凭证编号选择
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-1">
						<div class="input-group">
							<span class="input-group-addon">起始凭单号</span>
                            <select id="txt_qspdh" class="form-control input-radius select2" name="qspdh">
                            	<option value="">未选择</option>
                                <c:forEach var="item" items="${zbList}">
                           			<option value="${item.MC}">${item.MC}</option>
                            	</c:forEach>
                            </select>
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-1">
						<div class="input-group">
							<span class="input-group-addon">结束凭单号</span>
                            <select id="txt_jspdh" class="form-control input-radius select2" name="jspdh">
                            	<option value="">未选择</option>
                               <c:forEach var="item" items="${zbList}">
                           			<option value="${item.MC}">${item.MC}</option>
                            	</c:forEach>
                            </select>
                        </div>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">		
			<div class="box-header1 clearfix">
            	<div class="sub-title pull-left text-primary">
            		<input type="radio" id="xz_pzrq" name="xz">按凭证日期选择
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-1">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; ">起始日期</span>
							<input type="text" id="txt_qsrq" name="qsrq" class="form-control input-radius date" 
							style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入起始日期" />
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; ">结束日期</span>
							<input type="text" id="txt_jsrq" name="jsrq" class="form-control input-radius date" 
							style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入结束日期" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">		
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-1">
						<div class="input-group">
							<span class="input-group-addon">制单人</span>
                            <select id="txt_zdr" class="form-control input-radius select2" name="zdr">
                            	<option value="">未选择</option>
                               <c:forEach var="item" items="${zdrList}">
                           			<option value="${item.ZDR}">${item.ZDR1}</option>
                            	</c:forEach>
                            </select>
                        </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save" style="background:#00acec;color: white;">确定</button>
	    	<button type="reset" class="btn btn-default" id="btn_reset" style="background:#00acec;color: white;">取消</button>
		 </div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var target = "${ctx}/kjhs/pzxx/pzfh";
var winId = getTopFrame().layer.getFrameIndex(window.name);

	$(function(){
		
// 		$(document).ready(function(){
// 			if($("#xz_pzbh").attr('checked')=='checked'){
// 				$("[name='qsrq']").attr("disabled","disabled");
// 				$("[name='jsrq']").attr("disabled","disabled");
// 			}
// 		})
		
		$("#xz_pzbh").bind('change',function(){
			if($(this).val()=='on'){
				$("[name='qspdh']").removeAttr("disabled");
				$("[name='jspdh']").removeAttr("disabled");
				$("[name='qsrq']").attr("disabled","disabled");
				$("[name='jsrq']").attr("disabled","disabled");
				$("[name='qsrq']").val("");
				$("[name='jsrq']").val("");
			}
		});
		$("#xz_pzrq").bind('change',function(){
			if($(this).val()=='on'){
				$("[name='qsrq']").removeAttr("disabled");
				$("[name='jsrq']").removeAttr("disabled");
				$("[name='qspdh']").attr("disabled","disabled");
				$("[name='jspdh']").attr("disabled","disabled");
				$("[name='qspdh']").val("");
				$("[name='jspdh']").val("");
			}
		});
		//保存
		$("#btn_save").click(function(){
			var guid = $("[name='guid']").val();
			var qspdh = $("#txt_qspdh").val();
			var jspdh = $("#txt_jspdh").val();
			var zdr = $("#txt_zdr").val();
			var bh = $("#xz_pzbh").val();
			var rq = $("#xz_pzrq").val();
			console.log("&&&&&&&&&&&&" + bh);
			console.log("&&&&&&&&&&&&" + rq);
			var qsrq = $("[name='qsrq']").val();
			var jsrq = $("[name='jsrq']").val();
			if((qspdh !== "" && jspdh !== "") || (qsrq != "" && jsrq != "")){
				getIframWindow("${param.pname}").select_commonWin(target+"/pzdy?qspdh="+qspdh+"&jspdh="+jspdh+"&qsrq="+qsrq+"&jsrq="+jsrq+"&zdr="+zdr,"凭证打印","1000","650");
			  	close(winId);
			}else if(qspdh == "" || jspdh == "" || qsrq == "" || jsrq == ""){
				alert("数据不能为空！！！")
			}else if(qspdh !== "" && jspdh !== "" && qsrq !== "" && jsrq !== ""){
				alert("只能选择一种复核方式！！！")
			}
		});
		
		//取消
		$("#btn_reset").click(function(){
	    	close(winId);
		});

});
</script>
</body>
</html>