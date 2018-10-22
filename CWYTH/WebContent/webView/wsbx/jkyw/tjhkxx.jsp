<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加汇款信息</title>
<%@include file="/static/include/public-list-css.inc"%> 
<style>
	*{
		maring: 0;
		padding: 0;
		font-size: 14px;
	}
	body{
		background-color: white;
	}
	.line{
		height: 50px;
		line-height: 50px;
	}
	input{
		width: 200px;
	} 
	.prompt{
		color: #ccc;
	}
	label{
		font-weight: bold;
		min-width: 100px;
	}
	select{
		width: 200px;
		border-radius: 5px;
	}
</style>
</head>
<body>
	<form action="" id="" method="post" style="padding-left: 30px;">
		<div class="line">
			<label>收款单位：</label>
			<input type="text" class="input-radius" id="" name=""/>
			<button type="button" class="btn btn-primary">常用收款单位</button>
		</div>
		<div class="line">
			<label>汇款金额：</label>
			<input type="text" class="input-radius number" style="text-align: right;" placeholder="00.00" id="" name=""/>
		</div>
		<div class="line">
			<label>用途附言：</label>
			<input type="text" class="input-radius" id="" name=""/><label class="prompt">（限20字以内）</label>
		</div>
		<div class="line">
			<label>收款账号：</label>
			<input type="text" class="input-radius" id="" name=""/><label class="prompt">（限输数字，不含特殊字符）</label>
		</div>
		<div class="line">
			<label>银行名称：</label>
			<select name="">
				<option>--请选择银行--</option>
				<option value="*">--所有银行--</option>
				<option value="cxyh">储蓄银行</option>
				<option value="zxyh">招商银行</option>
				<option value="gsyh">工商银行</option>
				<option value="jsyh">建设银行</option>
			</select>
			<label class="prompt">（如果找不到银行名称，请选择所有银行）</label>
		</div>
		<div class="line">
			<label>网点省市：</label>
			<select name="">
				<option>--请选择省份--</option>
				<option value="1">北京</option>
				<option value="2">上海</option>
				<option value="3">山东</option>
				<option value="4">河南</option>
			</select>
			<label class="prompt">（具体网点所在的省市）</label>
		</div>
		<div class="line">
			<label>网点关键字：</label>
			<input type="text" class="input-radius" id="" name=""/>
			<button type="button" class="btn btn-primary">查询</button>
		</div>
		<div class="line">
			<label>网点名称：</label>
			<select name="">
				<option>--请选择网点名称--</option>
				<option value="1">工商银行北京支行</option>
				<option value="2">工商银行上海支行</option>
				<option value="3">工商银行山东支行</option>
				<option value="4">工商银行河南支行</option>
			</select>
			<label class="prompt">（如果找不到网点，请选择相应的省市分行营业部）</label>
		</div>
		<div class="line" style="text-align: center;">
			<button type="button" class="btn btn-primary" id="btn-save">保存</button>
			<button type="button" class="btn btn-primary" id="btn-cancel">取消</button>
			<label><input type="checkbox" name="" id="" style="width: 13px;height: 13px;"/>保存到常用收款单位</label>
		</div>
	</form>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//返回按钮
	$("#btn-save").click(function(){
		alert("保存成功");
		close(winId);
	});
	$("#btn-cancel").click(function(){
		close(winId);
	});
});
</script>
</html>