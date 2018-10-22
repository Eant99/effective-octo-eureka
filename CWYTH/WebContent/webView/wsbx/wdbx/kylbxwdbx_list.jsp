<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
   	<input type="hidden" name="zl" id="zl" value="${dwbh}" />
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action=""style="padding-top: 8px;padding-bottom: 2px">
    		<div class="search-simple">
    			<div class="form-group">
					<label>审核状态</label>
					<select  class="form-control input-radius" id="shztdm"  table="K" >
						<option value="all" <c:if test="${shzt=='all'}">selected</c:if>>全部</option>
						<option value="00" <c:if test="${shzt=='00'}">selected</c:if>>未提交</option>
	                	<option value="11" <c:if test="${shzt=='11'}">selected</c:if>>审核中</option>
	                	<option value="99" <c:if test="${shzt=='99'}">selected</c:if>>已通过</option>
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_dwbh" class="form-control input-radius" name="djbh" table="K" placeholder="请输入单据编号">
				</div>
				<!-- <div class="form-group">
					<label>报销人</label>
					<input type="text" id="txt_mc" class="form-control input-radius" name="bxrmc" table="K" placeholder="请输入报销人名称">
				</div> -->
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
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
<script>
$(function () {
	var columns = [
	       		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
       				return '<input type="hidden" class="procinstid" taskid="'+full.TASKID+'" name="procinstid" value="'+full.PROCINSTID+'"> <input type="checkbox" class="keyId" lx="'+full.LX+'" ccywguid="'+full.CCYWGUID+'" name="keyId" xmguid="'+full.XMMC+'" value="' + data + '" ccywguid="'+full.CCYWGUID+'" money="'+full.BXZJE+'">';
	       	    },"width":10,'searchable': false},//0
	       	    
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},//1
	       		{"data": "SHZTMC",defaultContent:""},
	       		{"data": "DJBH",defaultContent:""},
	       	 	{"data": "BXRMC",defaultContent:""},
	       	 	{"data": "SZBMMC",defaultContent:""},
	       	 	{"data": "BXZJE",defaultContent:"","class":"text-right"},
	       	 	{"data": "CZRQ",defaultContent:"","class":"text-center"},
	       	 	{"data": "LX",defaultContent:""},
	       	   	
	       	   	{"data": "GUID",'render':function (data, type, full, meta){
	       	   		if(full.CHECKSHZT=="99"){
// 	       	   			return '<a class="btn btn-link btn_look" dwbh = "'+full.GUID+'" xmguid="'+full.XMMC+'" DJBH = "'+full.DJBH+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_print" lx="'+full.LX+'" dwbh = "'+full.GUID+'">打印</a>';
	       	   			return '<a class="btn btn-link btn_look" dwbh = "'+full.GUID+'" xmguid="'+full.XMMC+'" DJBH = "'+full.DJBH+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl">办理记录</a>|<a href="javascript:void(0);" class="btn btn-link btn_print" lx="'+full.LX+'" procinstid="'+full.PROCINSTID+'" dwbh = "'+full.GUID+'">打印</a>';
	       	   		}else if(full.CHECKSHZT=="00"){
	          			return '<a href="javascript:void(0);" class="btn btn-link btn_look" lx="'+full.LX+'" dwbh = "'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl">办理记录</a>';
	       	   		}else{
	       	   		return '<a class="btn btn-link btn_look" dwbh = "'+full.GUID+'" xmguid="'+full.XMMC+'" DJBH = "'+full.DJBH+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl">办理记录</a>';
// 	       	  	 	return '<a class="btn btn-link btn_look" dwbh = "'+full.GUID+'" xmguid="'+full.XMMC+'" DJBH = "'+full.DJBH+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_print" lx="'+full.LX+'" dwbh = "'+full.GUID+'">打印</a>';
	       	   		}
	       		},orderable:false,"width":95,"class":"text-center"}
	       	];
	       	//表数据
          	table = getDataTableByListHj("mydatatables","${ctx}/kylbx/getwdbxPageList?treedwbh=${dwbh}&shzt="+$("#shztdm").val(),[2,'desc'],columns);
   	//新增
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/grjfsz/goEditPage?dwbh=${param.dwbh}","C");//operateType=C
   	});
   	
    //办理记录
   	$(document).on("click",".btn_bljl",function(){
   		var processInstanceId=$(this).parents("tr").find("[name='procinstid']").val();
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=ccyw","C");
   		
   	});
   	
  //编辑
   	$(document).on("click",".btn_upd",function(){
   		var parentTr = $(this).parents("tr").find(":checkbox");
		var xmguid = parentTr.attr("xmguid");
		var guid = $(this).parents("tr").find(".keyId").val();
	   	var zbid = parentTr.val();
   		var dwbh = $(this).attr("dwbh");
   		var DJBH = $(this).attr("DJBH");
   		var zl = parentTr.attr("lx");
   		if(zl=="公务接待报销"){
			doOperate("${ctx}/kylbx/gwjdmxEdit?look=look&guid="+guid,"U");
   		}else if(zl=="差旅费报销"){
   	   		var ccywguid = parentTr.attr("ccywguid");
   			//doOperate("${ctx}/webView/wsbx/ccbx/selectXm.jsp?operate=U&look=look&mkbh=110201&dwbh="+dwbh+"&xmguid="+xmguid+"&zbguid="+zbid+"&djbh="+DJBH,"U");
//    		doOperate("${ctx}/webView/wsbx/ccbx/xzxm.jsp?operate=U&look=look&mkbh=110201&dwbh="+dwbh+"&xmguid="+xmguid+"&zbguid="+zbid+"&djbh="+DJBH+"&ccywguid="+ccywguid,"U");
   			doOperate("${ctx}/wsbx/ccbx/goYwblEditPage?operate=U&look=look&mkbh=110201&dwbh="+dwbh+"&xmguid="+xmguid+"&zbguid="+zbid+"&djbh="+DJBH+"&ccywguid="+ccywguid,"U");
   			//location.href = "${ctx}/webView/wsbx/ccbx/xzxm.jsp?operate=U&xmguid="+xmguid+"&mkbh=${mkbh}"+"&zbguid="+zbid+"&djbh="+djbh+"&ccywguid="+ccywguid;
   		}else{
   			doOperate("${ctx}/webView/wsbx/rcbx/selectXm.jsp?operate=U&look=look&mkbh=110301&dwbh="+dwbh+"&xmguid="+xmguid+"&zbid="+zbid+"&djbh="+DJBH,"U");
   		}
	});
  	//打印
	$(document).on("click",".btn_print",function(){
 		var dwbh = $(this).attr("dwbh");
 		var ccywguid = $(this).parents("tr").find(".keyId").attr("ccywguid");
 		var procinstid = $(this).attr("procinstid"); 
 		console.log("procinstid------------"+procinstid);
 		var lx = $(this).attr("lx"); 
 		if(lx=="差旅费报销"){
 			doOperate("${ctx}/wsbx/ccbx/goPrint?guid="+dwbh+"&procinstid="+procinstid+"&lx="+lx+"&ccywguid="+ccywguid);
 		}else{
 			doOperate("${ctx}/kylbx/singlePrintwdbxByMe?guid="+dwbh+"&procinstid="+procinstid+"&lx="+lx);
 		}		
	});
 	//删除
	$(document).on("click",".btn_delxx",function(){
   		var dwbh = $(this).attr("dwbh");
   		doDel("dwbh="+dwbh,"${ctx}/grjfsz/doDelete",function(val){
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});
	//批量删除
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
   		if(djdw.length>0){
   			alert("包含最顶级单位，不允许删除！");
   		}else{
   			if(checkbox.length>0){
   	   			var dwbh = [];
   	   			checkbox.each(function(){
   	   				dwbh.push($(this).val());
   	   			});
   	   			doDel("dwbh="+dwbh.join("','"),"${ctx}/grjfsz/doDelete",function(val){
//    	   				parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
						location.reload();
   	   	   		},function(val){
   	   	   		},dwbh.length);
   	   		}else{
   	   			alert("请选择至少一条信息删除！");
   	   		}
   		}
   		
   	});
  //查看按钮
  // 	$(".btn_look").click(function(){
   	$(document).on("click",".btn_look",function(){
	  var shztdm = $("[id='shzt']").val();
   			var parentTr = $(this).parents("tr").find(":checkbox");
   			var guid = parentTr.val();
   			var lx = parentTr.attr("lx");
   			var ccywguid = $(this).parents("tr").find(".keyId").attr("ccywguid");
   			if(lx=="日常报销"){
   				location.href = "${ctx}/wsbx/rcbx/goViewJspByMe?mkbh=${mkbh}&guid="+guid+"&shztdm="+shztdm;
   			}else if(lx=="差旅费报销"){
   				location.href = "${ctx}/wsbx/ccbx/goViewJspByMe?guid="+guid+"&type=v&shzt="+shztdm+"&ccywguid="+ccywguid;
   			}else{
   		   		//pageSkip("${ctx}/wsbx/gwjdfbx/gwjdfbxsq","xzxm&guid="+guid+"&type=E");
   		   		pageSkip("${ctx}/wsbx/gwjdfbx/gwjdfbxsq","gwjdmx_viewByMe&guid="+guid+"&link=sq");
   			}
   	});
  	//jsp 页面导出Excel
   	$(document).on("click","#btn_exp",function(){
   		var checkbox =  $("#mydatatables").find("input[name='keyId']").filter(":checked");
//    	 var shzt = $("[id='shzt']").val();
            console.log(checkbox.length);
            var id = [];
   		if(checkbox.length > 0){
   			console.log("111")
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = [];
   		}

   		doExp("","${ctx}/kylbx/expExcelwdbx2?treedwbh=${dwbh}&shzt="+$("#shztdm").val(),"我的报销业务","${pageContext.request.contextPath}",id,"");

   		
//    		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/kylbx/expExcelwdbx2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","我的报销业务.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
			});
   	//Excel
//    	$("#btn_exp").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
// 		var id = [];
// 		checkbox.each(function(){
// 			id.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/kylbx/expExcelwdbx?treedwbh=${dwbh}&id="+id.join("','"),"我的报销业务","${pageContext.request.contextPath}",id.join("','"));
//    	});
   	
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
$("#shztdm").change(function(){
	 var shzt=$("[id='shztdm']").val();
	    table.ajax.url("${ctx}/kylbx/getwdbxPageList?treedwbh=${dwbh}&shzt="+shzt);
	    table.ajax.reload();
});
</script>
</html>

