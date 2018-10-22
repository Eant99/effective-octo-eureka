<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位机构设置</title>

</head>
<body>
<div class="fullscreen">
   	<input type="hidden" name="zl" id="zl" value="${dwbh}" />
   	<input type="hidden" name="mkbh" id="zl" value="111001" />
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="" style="padding-top: 8px;padding-bottom: 2px">
    		<div class="search-simple">
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_dwbh" class="form-control input-radius" name="djbh" table="K" placeholder="请输入单据编号">
				</div>
				<div class="form-group">
					<label>报销人</label>
					<input type="text" id="txt_mc" class="form-control input-radius" name="bxrmc" table="K" placeholder="请输入报销人名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<!-- <div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>预算金额（万元）</label>
							<input type="text" id="txt_mc" class="form-control input-radius" table="K" name="ysje" placeholder="请输入预算金额">
						</div>
						<div class="form-group">
							<label>剩余金额（万元）</label>
							<input type="text" id="txt_mc" class="form-control input-radius" table="K" name="syje" placeholder="请输入剩余金额">
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div> -->
				<div class="btn-group pull-right" role="group">
	                <button type="button" class="btn btn-default" id="btn_plzf">批量支付</button>
	            </div>
			</div>
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
				        <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>审核状态</th>
				        <th>单据编号</th>
				        <th>单据张数</th>
				        <th>报销人</th>
				        <th>所在部门</th>
				        <th>报销总金额（元）</th>
				        <th>报销日期</th>
				        <th>类型</th>
				        <th>操作</th>
				    </tr>
				    </thead>
				    <tbody>
				    </tbody>
				</table>
            </div>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<script>
var target = "${ctx}/wsbx/bxzf";
var dm = "${param.dm}";
$(function () {
	var columns = [
	       		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
       				return '<input type="checkbox" class="keyId" name="keyId" value="' + data + '">';
	       	    },"width":10,'searchable': false},//0
	       	    
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},//1
	       		{"data": "SHZTMC",defaultContent:""},
	       		{"data": "DJBH",defaultContent:""},
	       	   	{"data": "FJZZS",defaultContent:"","class":"text-right"},
	       	 	{"data": "BXRMC",defaultContent:""},
	       	 	{"data": "SZBMMC",defaultContent:""},
	       	 	{"data": "BXZJE",defaultContent:"","class":"text-right"},
	       	 	{"data": "CZRQ",defaultContent:"","class":"text-center"},
	       	 	{"data": "LX",defaultContent:""},
	       	   	
	       	   	{"data": "GUID",'render':function (data, type, full, meta){
          			return '<a href="javascript:void(0);" class="btn btn-link btn_zf" data-lx="' + full.LX + '" data-guid="' + full.GUID + '">支付</a>|'
          			+ '<a href="javascript:void(0);" class="btn btn-link btn_view" ccywguid="'+full.CCYWGUID+'" data-lx="' + full.LX + '" data-guid="' + full.GUID + '">查看</a>';
	       		},orderable:false,"width":95,"class":"text-center"}
	       	];
	       	//表数据
    	table = getDataTableByListHj("mydatatables",target+"/getPageListData?type=${param.dm}",[4,'asc'],columns);
   	
	//查看按钮
		$(document).on("click","[id='btn_plzf']",function(){
//    	$("#btn_plzf").click(function(){
   		var checkbox = $("#mydatatables").find(".keyId").filter(":checked");
   		var len = checkbox.length;
   		if(len>0){
   			var guid = [];
	   	   	checkbox.each(function(){
	   	   		guid.push($(this).val());
	   	   	});
	   	   	var url = "${ctx}/wsbx/bxzf/doZf";
	   		var bxlx = "rcbx";
	   		switch (dm) {
			case "1":
				bxlx = "rcbx";
				break;
			case "3":
				bxlx = "gwjdbx";
				break;
			case "2":
				bxlx = "clfbx";
				break;
			default:
				break;
			}
	   		alert(bxlx);
		   	   	doDeal("支付",url+"?bxid="+guid+"&bxlx="+bxlx,function(){
		   	   		alert("支付成功！");
		   	   	});
   		}else{
   			alert("请选择至少一条信息支付!");
   		}
   	});
   	$(document).on("click",".btn_zf",function(){
//    	$(".btn_zf").click(function(){
   		var url = target+"/doZf";
   		var guid = $(this).data("guid");
   		var bxlx = $(this).data("lx");
   		switch (bxlx) {
		case "日常报销":
			bxlx = "rcbx";
			break;
		case "公务接待费报销":
			bxlx = "gwjdbx";
			break;
		case "差旅费报销":
			bxlx = "clfbx";
			break;
		default:
			break;
		}
//    		confirm("确定要支付选中的信息吗？","",{title:"提示"},function(){
	   	   	doDeal("支付",url,"bxid="+guid+"&bxlx="+bxlx,function(){
	   	   		alert("支付成功！");
	   	   		table.ajax.reload();
	   	   	});
//    	   	});
   	});
	$(document).on("click",".btn_view",function(){
//    	$(".btn_view").click(function(){
   		var guid = $(this).data("guid");
   		var bxlx = $(this).data("lx");
   		var ccywguid = $(this).parents("tr").find(".btn_view").attr("ccywguid");
   		var href = "";
   		switch (bxlx) {
		case "日常报销":
			href = "${ctx}/wsbx/rcbx/goViewJsp?guid="+guid;
			break;
		case "公务接待费报销":
			href = "${ctx}/wsbx/gwjdfbx/gwjdfbxsq/pageSkip?pageName=gwjdmx_view&guid="+guid;
			break;
		case "差旅费报销":
			href = "${ctx}/wsbx/ccbx/goViewJsp?guid="+guid+"&type=v&ccywguid="+ccywguid;
			break;
		default:
			break;
		}
   		location.href = href;
   	});
   	
   	
    //查询联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	//查询弹窗
   	$("#btn_dwld").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dwld","人员信息","920","630");
    });
});
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</html>

