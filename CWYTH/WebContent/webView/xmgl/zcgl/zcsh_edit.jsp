<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支出管理信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    line-height: 25px;
    padding-left: 6px;
}

</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${zcgl.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>审核支出管理信息</span>
		</div>
		<div class="pull-right">	
		    <button type="button" class="btn btn-default" id="btn_tg">通过</button>	
				<button type="button" class="btn btn-default" id="btn_shz">审核中</button>
		     <button type="button" class="btn btn-default" id="btn_th">退回</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>支出内容</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">物品名称</span>
							<input type="text" id="txt_wpmc" name="wpmc" readonly class="form-control input-radius"  value="${zclr.wpmc}"/>
							
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">采购目录</span>
                            <input type="text" id="txt_cgml" readonly class="form-control input-radius" name="cgml" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">计量单位</span>
                            <input type="text" id="txt_jldw" readonly class="form-control input-radius" name="jldw" value="${cgmlsz.sjml}" />
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单&ensp;价(元)</span>
							<input type="text" id="txt_dj" readonly class="form-control input-radius text-right number" name="dj" value="<fmt:formatNumber value='${xtb.GZYQ}' pattern='0.00'/>">
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">计划金额(元)</span>
                           <input type="text" id="txt_jjje" readonly class="form-control input-radius text-right number" name="jjje" value="<fmt:formatNumber value='${xtb.GZYQ}' pattern='0.00'/>">
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">采购金额(元)</span>
                            <input type="text" id="txt_cgje" readonly class="form-control input-radius text-right number" name="cgje" value="<fmt:formatNumber value='${xtb.GZYQ}' pattern='0.00'/>">
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">支出日期</span>
							<input type="text" id="txt_zcrq" readonly class="form-control input-radius date" name="zcrq" value="<fmt:formatDate value="${xtb.BFRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="支出日期">
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">资金编号</span>
                            <input type="text" id="txt_mlmc" readonly class="form-control input-radius" name="zjbh" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">资金来源</span>
                            <input type="text" id="txt_mlmc" readonly class="form-control input-radius" name="zjbh" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">采购日期</span>
							<input type="text" id="txt_cgrq" readonly class="form-control input-radius date" name="cgrq"  value="<fmt:formatDate value="${xtb.BFRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="采购日期">
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">采购合同编号</span>
                            <input type="text" id="txt_mlmc" readonly class="form-control input-radius" name="cghtbh" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">合同名称</span>
                            <input type="text" id="txt_sjml" readonly class="form-control input-radius" name="htmc" value="${cgmlsz.sjml}" />
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">经&ensp;手&ensp;人</span>
							<input type="text" id="txt_jsr" readonly name="jsr" class="form-control input-radius"  value="${cgmlsz.mldm}"/>
						
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">供&ensp;应&ensp;商</span>
                            <input type="text" id="txt_mlmc" readonly class="form-control input-radius" name="gys" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">品&emsp;&emsp;牌</span>
                            <input type="text" id="txt_sjml" readonly class="form-control input-radius" name="pp" value="${cgmlsz.sjml}" />
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">产品型号</span>
							<input type="text" id="txt_cpxh" name="cpxh" readonly class="form-control input-radius"  value="${cgmlsz.mldm}"/>
							
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">采购数量</span>
                            <input type="text" id="txt_cgsl" readonly class="form-control input-radius" name="cgsl" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">产地类型</span>
                             <input type="text" id="txt_cgsl" readonly class="form-control input-radius" name="cgsl" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">存放地点</span>
							<input type="text" id="txt_cfdd" name="cfdd" readonly class="form-control input-radius"  value="${cgmlsz.mldm}"/>
							
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">组织形式</span>
                          <input type="text" id="txt_cfdd" name="cfdd" readonly class="form-control input-radius"  value="${cgmlsz.mldm}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">采购机构</span>
                            <input type="text" id="txt_cfdd" name="cfdd" readonly class="form-control input-radius"  value="${cgmlsz.mldm}"/>
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">采购方式</span>
							<input type="text" id="txt_cfdd" name="cfdd" readonly class="form-control input-radius"  value="${cgmlsz.mldm}"/>
						</div>
                    </div>					
				</div>		
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                           <textarea id="txt_bz" class="form-control" readonly name="bz" >${cgmlsz.bz}</textarea>
						</div>
					</div>
				</div>				
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
                           <textarea id="txt_shyj" class="form-control"  name="shyj" >${cgmlsz.bz}</textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	
	//保存按钮
	$("#btn_save").click(function(){
		doSave1(validate,"myform","${ctx}/cgmlsz/doSave",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
	});
	//审核中按钮
	$("#btn_shz").click(function(){		
		alert("已标记为审核中！");
	});
	
	$("#btn_tg").click(function(){
		alert("审核通过");
	});
	$("#btn_th").click(function(){
		alert("退回");
	});
	function doSave1(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){					
				},
				error:function(val){
					alert("保存成功！");					
				}	
			});
		}
	}
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/xmgl/zcgl/zcsh_list.jsp";
	});
	//提交按钮
	$("#btn_commit").click(function(){
		doSave1(validate,"myform","${ctx}/cgmlsz/doSave",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}"
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"
	    	}
		 }
	});

})

</script>
</body>
</html>