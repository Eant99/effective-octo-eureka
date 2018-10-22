<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收据 开票</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
	<form id="form" class="form-horizontal" action="" method="post" >
		<input type="hidden" name="gid" id="gid" value="${gid}">
	    <div class="container-fluid dialog-body">
			<div class="row">
				<div  class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>入账日期</span> 
						<input type="text" class="form-control Wdate"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="cprq"  value="${pj.cprq}" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>票据号</span> 
						<input type="text" class="form-control" name="pjh" value="${pj.pjh}" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>金额</span> 
						<input type="text" class="form-control number" name="je"  value="${pj.je}" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>交款单位</span> 
						<input type="text" class="form-control" name="skr" id="skr" value="${pj.skr}" />
						<span class="input-group-btn"><button type="button" id="btn_skr" class="btn btn-link">选择</button></span>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>收款方式</span> 
						<input type="text" class="form-control" name="yt"  value="${pj.yt}" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>年月日</span> 
						<input type="text" class="form-control date"  data-format="yyyy-mm-dd" name="nyr"  value="${pj.nyr}"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>收款事由</span> 
						<textarea class="form-control" name="fjxx">${pj.fjxx}</textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>开票人</span> 
						<input type="text" class="form-control" name="kpr" id="kpr" value="${pj.kpr}" />
						<span class="input-group-btn"><button type="button" id="btn_kpr" class="btn btn-link">选择</button></span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>制单人</span> 
						<input type="text" class="form-control" name="zdr"  value="${pj.zdr}" />
					</div>
				</div>
			</div>
		</div>
		<div class='page-bottom clearfix'>
	        <div class="pull-right">
				<button type='button' class="btn btn-default" id="btn_save">保存</button>
				<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">取消</button>
	        </div>
	    </div>
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var validate = $('#form').bootstrapValidator({fields: {
		cprq:{validators:{notEmpty:{message:'不能为空'}}},
		pjh:{validators:{notEmpty:{message:'不能为空'}}},
        je:{validators:{notEmpty:{message:'不能为空'}}},
        skr:{validators:{notEmpty:{message:'不能为空'}}},
        yt:{validators:{notEmpty:{message:'不能为空'}}},
        fjxx:{validators:{notEmpty:{message:'不能为空'}}},
        nyr:{validators:{notEmpty:{message:'不能为空'}}},
        kpr:{validators:{notEmpty:{message:'不能为空'}}},
        zdr:{validators:{notEmpty:{message:'不能为空'}}}
        }
    });
	//缴款 单位 选择
	$("#btn_skr").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=skr","单位信息","920","630");	
	});
	//开票人 选择
	$("#btn_kpr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=kpr","人员信息","920","630");
    });
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"form","${ctx}/pjgl/rcyw/pjdy/doSaveSj",
		function(val){//成功
			$("#operateType").val("U");
			getIframWindow("${param.pname}").$('#mydatatables').DataTable().ajax.reload();
 			var winId = getTopFrame().layer.getFrameIndex(window.name);
 	    	close(winId);
		},function(val){//失败
			
		});
	}); 
	//取消
	$("#btn_cancelw").click(function(){
		//getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});</script></body></html>