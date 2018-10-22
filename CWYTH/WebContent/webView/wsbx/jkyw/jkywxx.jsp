<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/static/include/public-manager-css.inc"%> 
<style>
	.border{
		border:1px solid #a94442!important;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
	.btn-del{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.btn-del:after{
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.btn_td{
		width:14mm!important;
		height:6mm!important;
		text-align: center;
	}
	.text-green{
		color:green!important; 
	}
	.btn-actions{
		text-align: center;
		padding-top: 20px;
		padding-bottom: 20px;
	}
	th,td{
		text-align: center;
	}
	.text-red{
		color: red;
	}
	.text-bold{
		font-weight: bold;
	}
	.text-blue{
		color: blue;
	}
	.border-red{
		border: 1px solid red;
	}
	.required{
		color: red;
		font-size: 14px;
		padding: 3px;
	}
	.form-control{
		border-top-right-radius: 4px!important;
		border-bottom-right-radius: 4px!important;
	}
</style>
</head>
<body>
<form action="" id="myform" method="post">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑借款信息</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
        				 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    				</div>
				</div>
				<div class="sub-title pull-left text-green">选择项目&nbsp;></div>
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        				 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    				</div>
				</div>
				<div class="sub-title pull-left">借款业务办理&nbsp;></div>
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
        				 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
    				</div>
				</div>
				<div class="sub-title pull-left">结算方式设置</div>
        </div>
	</div>
	<div class="box">
		<div class="box-panel">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>第二步，借款业务办理
				</div>
				<div class="pull-right">
	        		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
					</button>
					<button type="button" class="btn btn-primary" id="btn_save"><i class="fa icon-save"></i>保存</button>
		  			<button type="button" class="btn btn-default" id="btn_back">返回</button>
		  			</div>
			</div>
			<hr class="hr-normal" />
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>借&ensp;款&ensp;人</span>
                           	<input type="text" id="txt_jkr" class="form-control window" name="jkr" readonly="readonly">
							<span class="input-group-btn"><button type="button" id="btn_jkr" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>借款单位</span>
							<input type="text" id="txt_jkdw" class="form-control window" name="jkdw" readonly="readonly"/>
							<span class="input-group-btn">
								<button type="button" id="btn_jkdw" class="btn btn-link btn-custom">选择</button>
							</span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>借款时间</span>
							<input type="text" id="txt_jksj" name="jksj"
								class="form-control window date" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>借款金额</span>
							<input type="text" id="txt_jkje" name="jkje"
								class="form-control number text-blue text-bold" style="text-align: right"
								placeholder="0.00" readonly />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
							<textarea id="txt_bz" class="form-control" name="bz"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
			<!-- 借款明细 -->
		<div class="box-panel">	
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">借款明细<label style="color: text-primary">（请点击左侧经济科目进行添加）</label></div>			
			</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mytable" class="table table-striped table-bordered table-alterable"
					style="border-collapse: collapse; width: 70%;">
					<tr>
						<th>操作</th>
						<th>序号</th>
						<th><span class="required">*</span>费用名称</th>
						<th><span class="required">*</span>借款金额</th>
						<th><span class="required">*</span>附件（张）</th>
					</tr>
					<tr id="line_0" data-line="0" style="display:none;">
						<td class="btn_td">
							<div class="btn-del"></div>
						</td>
						<td class="xh" style="vertical-align: middle;">1</td>
						<td><input type="text" class="form-control fymc"
							name="fymc" disabled/></td>
						<td>
							<input type="text"
							class="form-control number money notNull" style="text-align: right;"
							name="fyje" placeholder="0.00" value="0"/></td>
						<td>
							<input type="text" class="form-control int notNull" style="text-align: right;" name="fj"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
			<div class="btn-actions">
				<button class="btn btn-default btn-prev" data-id="2">上一步</button>
				<button class="btn btn-primary btn-next" data-id="2">下一步</button>
			</div>
			</form>
	<%@include file="/static/include/public-list-js.inc"%> 
<script>
//必填项验证
var validate = $('#myform').bootstrapValidator({fields:{
	 	jkr:{validators:{notEmpty: {message: '借款人不能为空'}}},
	    jkdw:{validators:{notEmpty: {message: '借款单位不能为空'}}},
		jksj:{validators:{notEmpty: {message: '借款时间不能为空'}}}		
	}
});
//计算总金额
function computerTotal(items){
	var total = 0;
	$.each(items,function(){
		var value = $(this).val();
		total += (value == "") ? 0 : parseFloat(value);
	});
	return total.toFixed(2);
}
//检查bootstrap必填项是否为空
function checkRequire(formId,a_validate){
	a_validate.bootstrapValidator("validate");
	return !$(formId).data("bootstrapValidator").isValid();
}
//检查表格内必填项是否为空
function checkNull(items){
	var tag = false;
	$.each(items,function(){
		var val = $(this).val();
		if(val == "" || val == "0"){
			tag = true;
			$(this).addClass("border-red");
		}
	});
	return tag;
}
//检查是否存在
function checkExist(items,text){
	var tag = false;
	$.each(items,function(){
		if($(this).val() == text){
			tag = true;
		}
	});
	return tag;
}
//保存
function doSave(validate, formId, ajaxUrl, targetUrl,success){
	var tag = false;
	tag = checkNull($(".notNull:visible"));
	tag |= checkRequire(formId,validate);
	if(tag){
		alert("必填项不能为空！");
		return;
	}
	var total = $("#txt_jkje").val();
	if(total == 0){
		alert("借款总金额必须大于0！");
	}else{
		/* $.ajax({
			type:"post",
			url:_url,
			data:arrayToJson($(formId).serializeArray()),
			success:function(val){
				
			},
			error:function(val){
				
			}	
		}); */
		targetUrl += total;
		success(targetUrl);
	}
}
//按钮点击事件
$(function(){
	//删除借款明细记录
	$(document).on("click",".btn-del",function(){
		$(this).parents("tr").nextAll().each(function() {
			var $td = $(this).find(".xh");
			var line = $td.text();
			line--;
			$(this).attr("data-line", line);
			$td.text(line);
		});
		$(this).parents("tr").remove();
		var total = computerTotal($(".money"));
		$("#txt_jkje").val(total);
	})
	//增加借款明细记录
	$(parent.document).on("click","a",function(){
		var txt = $(this).find("span").text();
		if(checkExist($("[name='fymc']"),txt)){
			alert("("+txt+")科目已经存在！");
			return;
		}
		var line = $("tr:last-child").attr("data-line");
		line++;
		var $tr = $("#line_0").clone();
		$tr.show();
		$tr.attr("data-line",line);
		$tr.find("input").val("");
		$tr.find(".xh").text(line);
		$tr.find("[name='fymc']").val(txt);
		$("#mytable").append($tr);
	});
	//返回按钮
	$("#btn_back").click(function(){
		parent.window.location.href = "${ctx}/jkyw/goJkywPage?mkbh=${param.mkbh}";
	})
	//业务说明按妞
	$(document).on("click","#btn_ywsm",function(){
		select_commonWin("${ctx}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
	});
	//保存按妞
	$("#btn_save").click(function(){
		doSave(validate,"#myform","","",function(toUrl){
			alert("保存成功！");
		});
	})
	//移除必选项红框
	$(document).on("focus",".notNull",function(){
		$(this).removeClass("border-red");
	})
	//下一步按妞
	$(document).on("click",".btn-next",function(){
		var url = "${ctx}/jkyw/goAddPage?stepNum=3&mkbh=${param.mkbh}&total=";
		doSave(validate,"#myform","url",url,function(toUrl){
			parent.window.location.href = toUrl;
		});
	});
	//上一步按妞
	$(".btn-prev").click(function(){
		parent.window.location.href = "${ctx}/jkyw/goAddPage?stepNum=1&mkbh=${param.mkbh}"; 		
	});
	//统计总金额
	$(document).on("change",".money",function(){
		var total = computerTotal($(".money"));
		$("#txt_jkje").val(total);
	});
   });
//弹窗
$("#btn_jkr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_jkr","人员信息","920","630");
    });
$("#btn_jkdw").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_jkdw","部门信息","920","630");
    });
</script>
</body>
</html>