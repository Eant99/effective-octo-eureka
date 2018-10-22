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
	<input type="hidden" name="jsr" value="${param.jsr}"> 
	<input type="hidden" id="sjid"	name="sjid" value="${param.guid}"> 
	<input type="hidden" name="fbr"	value="${loginId}">
	<div class="box">
		<div class="box-panel">		
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-1">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>退回原因</span>
                            <textarea style="width:100%;height:60px;" id="xxnr" name="xxnr" class="form-control input-radius" ></textarea>
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
		
		//确定
		$("#btn_save").click(function(){
			var xxnr = $("#xxnr").val();
			if(xxnr==""||xxnr==null){
				alert("请填写退回原因");
				return;
			}
			doSave(null,"myform","${ctx}/kjhs/pzxx/pzfh/doSave",function(val){
				if(val.success){
					alert("操作成功！");
					close(winId);
				}else{
					alert("操作失败！");
				}
			});
		});
		
		//取消
		$("#btn_reset").click(function(){
	    	close(winId);
		});

});
</script>
</body>
</html>