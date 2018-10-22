<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/static/include/public-list-css.inc"%> 
<style>
	.text-green{
		color:green!important; 
	}
	.btn-actions{
		text-align: center;
		padding-top: 20px;
		padding-bottom: 20px;
	}
</style>
</head>
<body>
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑借款业务信息</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        				 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    				</div>
				</div>
				<div class="sub-title pull-left text-primary">选择项目&nbsp;></div>
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
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
	<div class="box" style="overflow-y: hidden;margin-bottom: 0px;">
		<div class="box-panel" id="stepPage_1">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>第一步，选择项目
				</div>
				<div class="pull-left search">
					<form id="myform" class="form-inline" action="">
						<div class="search-simple">
							<div class="form-group">
								<label>项目编号</label> <input type="text" id="txt_xmbh"
									name="txt_xmbh" class="form-control" placeholder="请输入项目编号" />
							</div>
							<div class="form-group">
								<label>项目名称</label> <input type="text" id="txt_xmmc"
									name="txt_xmmc" class="form-control" placeholder="请输入项目名称" />
							</div>
							<button type="button" class="btn btn-primary" id="btn_search">
								<i class="fa icon-chaxun"></i>查询
							</button>
						</div>
					</form>
				</div>
			</div>
			<hr class="hr-normal" />
			<div class="container-fluid box-content">
				<div class='responsive-table'>
					<div class='scrollable-area'>
					<div class="pull-right">
	        		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
					</button>
					<button type="button" class="btn btn-primary" id="btn_save"><i class="fa icon-save"></i>保存</button>
		  			<button type="button" class="btn btn-default" id="btn_back">返回</button>
		  			</div>
						<table id="mydatatables"
							class="table table-striped table-bordered">
							<thead>
								<tr>
									<th></th>
									<th>序号</th>
									<th>项目编号</th>
									<th>项目名称</th>
									<th>余额（元）</th>
									<th>截止日期</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		
	</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function(){
	//业务说明按妞
	operateYwsm("${ctx}");
//列表数据
var columns = [
	{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
       	return '<input type="checkbox" class="keyId" name="guid" value="' + data + '">';
    },"width":10,'searchable': false},
	{"data":"_XH",orderable:false,"searchable": false,"class":"text-center","width":50},
   	{"data": "XMBH",defaultContent:""},
   	{"data": "XMMC",defaultContent:""},
   	{"data": "KJE",defaultContent:"0.00","class":"text-right"},
   	{"data": "SQJZRQ",defaultContent:"","class":"text-center"}
];
	table = getDataTableByListHj("mydatatables","${ctx}/json/wsbx/jkyw/xzxm.json?",[2,'asc'],columns,0,1,setGroup);
	$("#mydatatables").after('<div class="btn-actions">\
								<button class="btn btn-primary btn-next" data-id="1">下一步</button>\
			</div>');
	//按钮点击事件
	//下一步按妞
	$(".btn-next").click(function(){
		var checkBox = $("#mydatatables").find('[name="guid"]').filter(":checked");
		if(checkBox.length == 0){
			alert("请选择一条记录！");
		}else if(checkBox.length > 1){
			alert("只允许选择一条记录！");
		}else{
			window.location.href = "${ctx}/jkyw/goAddPage?stepNum=2&mkbh=${param.mkbh}";
		}
	});
	//业务说明按妞
	$(document).on("click","#btn_ywsm",function(){
		zysx();
	});
	//保存按妞
	$("#btn_save").click(function(){
		alert("操作成功！");
	})
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/jkyw/goJkywPage?mkbh=${param.mkbh}";
	})
	//查询按钮
	$("#btn_search").click(function(){
		window.location.reload();
	});
})
function zysx(){
	//业务说明
	select_commonWin("${ctx}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
} 
</script>
</body>
</html>