<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>出差业务申请信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.btn-link{
	padding: 0px!important;
	margin: 0px!important;
}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
				
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
					<div class="form-group">
						<label>审核状态</label>
							<select class="form-control" id="txt_shzt">
								<option value="01">未提交</option>
								<option value="02">审核中</option>
								<option value="03">审核通过</option>
							</select>
					</div>
				<div class="form-group">
					<label>部门名称</label>
					<input type="text" id="txt_djbh" class="form-control" name="BMMCS"  placeholder="请输入部门名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default plugin" id="btn_add">增加</button>
<!-- 	               <button type="button" class="btn btn-default plugin" id="btn_submit">提交</button> -->
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
				        	<th style="text-align: center;"><input type="checkbox" class="select-all" /></th>
				            <th>序号</th>
				            <th>审核状态</th>
				            <th>申报年度</th>
				            <th>部门编号</th>
				            <th>部门名称</th>
				            <th>经办人</th>
				            <th>部门负责人</th>
				            <th>财务负责人</th>
				            <th>收入预算汇总（万元）</th>
				            <th>支出预算汇总（万元）</th>
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
<%@include file="/static/include/public-list-js.inc"%>
<script>
// var target = "${ctx}/wsbx/ccyw/ccywsq";
$(document).on("change","[id='txt_shzt']",function(){
		var shzt = $(this).val();
		table.ajax.url("${ctx}/ysgl/bmyssb/getPageList?shzt="+shzt);
		table.ajax.reload();
	});
$(function () {
	//加载列表数据
    var columns = [
    	{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
				return '<input type="checkbox" class="keyId" name="keyId" SHZTSZ="'+full.SHZTSZ+'" procinstid="'+full.PROCINSTID+'" value="' + data + '">';
   	    },"width":10,'searchable': false},//0
   		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
   	   		return data;
   		},"width":41,"searchable": false,"class":"text-center"},//1
   		{"data": "SHZTMC",defaultContent:"",orderable:false,"width":120},
   		{"data": "ND",defaultContent:"",class:"text-center","width":80},
   		{"data": "BMMC",defaultContent:"","width":120},
   		{"data": "BMMCS",defaultContent:"","width":120},
   	   	{"data": "JBRMC",defaultContent:"","width":120},
   	 	{"data": "DWFZRMC",defaultContent:"","width":120},
   	 	{"data": "CWFZRMC",defaultContent:"","width":120},
   	 	{"data": "SRYSHZ",defaultContent:"-","class":"text-right","width":100},
   		{"data": "ZCYSHZ",defaultContent:"-","class":"text-right","width":100},
   	   	{"data": "GUID",'render':function (data, type, full, meta){
   	   		if(full.SHZTSZ=='0'||full.SHZTSZ=='2'){
  				return '<a class="btn btn-link btn_upd" dwbh = "'+full.GUID+'">编辑</a>|<a class="btn btn-link btn_submit" dwbh = "'+full.GUID+'">提交</a>|<a  class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>';	
  			}else{
  				return '<a class="btn btn-link btn_ck" dwbh = "'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl">办理记录</a>';	
  			}
   		},orderable:false,class:"text-center"}
     ];
   table = getDataTableByListHj("mydatatables","${ctx}/ysgl/bmyssb/getPageList?shzt="+$("#txt_shzt").val(),[2,'asc'],columns,0,1,setGroup);
   $(document).on("click",".btn_ck",function(){
		var guid = $(this).parents("tr").find(":checkbox").val();
		var SRYSHZ=$(this).parents("tr").find(":checkbox").attr("SRYSHZ");
		var ZCYSHZ=$(this).parents("tr").find(":checkbox").attr("ZCYSHZ");
		var procinstid=$(this).parents("tr").find(":checkbox").attr("procinstid");
		doOperate("${ctx}/window/mainBmyssbTree?ck=ck&procinstid="+procinstid+"&pass=true&guid="+guid+"&ZCYSHZ="+ZCYSHZ+"&SRYSHZ="+SRYSHZ+"&mkbh=010301&pageUrl=/xmxxt/goXmxxPage&treeJson=/glqxb/qxdwTree?dwbh=${param.dwbh}", "C");//operateType=C
	});
 //办理记录
  	$(document).on("click",".btn_bljl",function(){
  		var processInstanceId=$(this).parents("tr").find(":checkbox").attr("procinstid");
  		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=fwdb","C");
  		
  	});
	
   		//新增
		$("#btn_add").click(function() {
			$.ajax({
				type:"post",
				url:"${ctx}/ysgl/bmyssb/checkIsAdd",
				data:"",
				async:false,
				success:function(val){
					var result = JSON.getJson(val);
					if(result=="1"){
						$.ajax({
							type:"post",
							url:"${ctx}/ysgl/bmyssb/checkIsAdd2",
							data:"",
							async:false,
							success:function(val){
								var result = JSON.getJson(val);
								if(result=="1"){
									alert("系统检测到本年度您已经提交信息！");
								}else{
									alert("系统检测到您已经录入信息，已进入编辑页面！");
									doOperate("${ctx}/window/mainBmyssbTree?mkbh=010301&pageUrl=/xmxxt/goXmxxPage&treeJson=/glqxb/qxdwTree?dwbh=${param.dwbh}", "C");//operateType=C								
								}
							}
						})
						
// 						alert("系统检测到您已经录入信息，已进入编辑页面！");
// 						doOperate("${ctx}/window/mainBmyssbTree?mkbh=010301&pageUrl=/xmxxt/goXmxxPage&treeJson=/glqxb/qxdwTree?dwbh=${param.dwbh}", "C");//operateType=C
					}else{
						doOperate("${ctx}/window/mainBmyssbTree?mkbh=010301&pageUrl=/xmxxt/goXmxxPage&treeJson=/glqxb/qxdwTree?dwbh=${param.dwbh}", "C");//operateType=C						
					}
				}
			})
		});
		//b编辑
		$(".btn_upd").click(function() {
			doOperate("${ctx}/window/mainBmyssbTree?mkbh=010301&pageUrl=/xmxxt/goXmxxPage&treeJson=/glqxb/qxdwTree?dwbh=${param.dwbh}", "C");//operateType=C
		});

	//提交
		$(".btn_submit").click(function(){
   		var guid = $(this).parents("tr").find(":checkbox").val();
   		confirm("确定提交？","",function(){
   			$.ajax({
   	   			url:"${ctx}/ysgl/bmyssb/submit",
   	   			data:"guid="+guid+"&mkbh=010301",
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert("提交成功");
   	   				table.ajax.reload();
   	   			}
   	   		});
   		});
   	});
    //批量删除
   	$("#btn_submit").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
//    		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
//    		if(djdw.length>0){
//    			alert("包含最顶级单位，不允许删除！");
//    		}else{
   			if(checkbox.length>0){
   	   			var dwbh = [];
   	   			checkbox.each(function(){
   	   				dwbh.push($(this).val());
   	   			});
   	   		doOperate("${ctx}/ysgl/bmyssb/doSubmit?dwbh="+dwbh)
//    	   				parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
						
   	   		}else{
   	   			alert("请选择至少一条信息删除！");
   	   		}
//    		}
   		
   	});
	
	$(document).ready(function(){
		anxs();
	})
	
    
 	//删除
	$(document).on("click",".btn_delxx",function(){
  		var guid = $(this).attr("guid");
  		doDel("guid="+guid,"${ctx}/ysgl/bmyssb/doDeleteAll?guid="+guid,function(val){
  			table.ajax.reload();
  		},function(val){
  		},"1");
  	});
   
    //列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</body>
</html>