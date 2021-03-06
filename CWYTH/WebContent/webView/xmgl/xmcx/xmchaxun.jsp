<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
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
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="k"  placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc" table="k"   placeholder="请输入项目名称">
				</div>
				
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>部门名称</label>
							<div class="input-group">
								<input type="text" id="txt_bmmc" class="form-control" name="bmmc" table="k" placeholder="请输入部门名称">
							</div>
						</div>
						<div class="form-group">
							<label>项目负责人</label>
							<div class="input-group">
								<input type="text" id="txt_fzrmc" class="form-control" name="fzrmc" table="k" placeholder="请输入项目负责人">
							</div>	
						</div>
						<div class="form-group">
							<label>项目类别</label>
							<div class="input-group">
								<input type="text" id="txt_xmlbmc" class="form-control" name="xmlbmc" table="k" placeholder="请输入项目类别">
							</div>
						</div>
						
						
						<div class="form-group">
							<label>项目大类</label>
							<input type="text" id="txt_xmdlmc" class="form-control" name="xmdlmc" table="k" placeholder="请输入项目大类">
						</div>
						<!-- <div class="form-group"> 
							<label>项目金额区间</label>
							<div class="input-group">
								<input type="text" id="txt_xmjeqj" class="form-control input-radius" name="xmjeqj"  table="k" placeholder="请输入项目金额区间" value="">
							</div>
						</div> -->
						<div class="search-more-bottom clearfix">
							<div class="pull-right"> 
								<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
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
				            <th>项目编号</th>
				            <th>部门名称</th>
				            <th>项目名称</th>
				            <th>项目大类</th>
				            <th>项目类别</th>
				            <th>项目类型</th>
				            <th>负责人</th>
				            <th>项目属性</th>
				            <th>归口部门</th>
				            <th>是否启用</th>
				            <c:if test="${empty isWindow}">
				            <th>操作</th>
				            </c:if>
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
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
var target = "${ctx}/xmgl/xmcx/xmchaxun";
$(function () {
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//加载列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" values="' +"("+ full.XMBH + ")"+full.XMMC+'" >';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
      // {"data": "XMBH",defaultContent:"","class":"text-left"},
       {"data": "XMBH","render":function (data, type, full, meta){
		   	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
	   }},
       {"data": "BMMC",defaultContent:"","class":"text-left"},
       {"data": "XMMC",defaultContent:"","class":"text-left"},
       {"data": "XMDLMC",defaultContent:"","class":"text-left"},
       {"data": "XMLBMC",defaultContent:"","class":"text-left",},
       {"data": "XMLX",defaultContent:"","width":120},
       {"data": "FZRMC",defaultContent:"","width":120,},
       {"data": "XMSXMC",defaultContent:"","class":"text-left",},
       {"data": "GKBMMC",defaultContent:"","width":120,},
       {"data": "SFQYMC",defaultContent:"","class":"text-left"}, 
       <c:if test="${empty isWindow}">
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="' + data + '">查看</a>';
      },orderable:false,"width":220,"class":"text-center","width":100}
       </c:if>
     ];
    table = getDataTableByListHj("mydatatables",target+"/getXmxxPageData?treesearch=${treesearch}&rybh=${rybh}&bmh=${bmh}",[2,'asc'],columns,0,1,setGroup);
  	//添加,批量删除，导出excel
  	$(document).on("click","#btn-add",function(){
//    	$("#btn-add").click(function(){
	   	window.location.href = target+"/goXmxxAddPage?";
   	});
  	 //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val = $(this).find("[name='guid']").attr("values");
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	close(winId);
    	}
    });
    //变更负责人弹窗
	$(document).on("click","#btn-changeFzr",function(){
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		if(checkbox.length>0){
  			var guid = [];
  			checkbox.each(function(){
  				guid.push($(this).val());
  			});
			select_commonWin("${ctx}/ysgl/xmsz/xmxx/rypage?xmid="+guid.join("','"),"负责人","920","630");
  		}else{
  			alert("请至少选择一个项目");
  		};
	});
   	$(document).on("click",".btn-del",function(){
   		var guid = $(this).attr("guid");
		$.ajax({
	   			url:"${ctx}/ysgl/xmsz/xmxx/selectXmmc",
	   			data:"dwbh="+guid,
	   			type:"post",
	   			dataType:"json",
	   			async:"false",
	   			success:function(val){
	   				if(val){
	   			 		confirm("确定删除？","",function(){
	   		 			$.ajax({
	   		 	   			url:"${ctx}/ysgl/xmsz/xmxx/doDelete",
	   		 	   			data:"dwbh="+guid,
	   		 	   			type:"post",
	   		 	   			dataType:"json",
	   		 	   			async:"false",
	   		 	   			success:function(val){
	   		 	   				if(val.success){
	   		 	   					alert("删除成功！");
	   		 	   				}
	   			   				
	   		 	   				table.ajax.reload();
	   		 	   			},
	   		 	   			error:function(){
	   		 	   				alert("抱歉，系统出现问题！");
	   		 	   			}
	   		 	   		});
	   		 		});
	   				}else{
	   					alert("该项目正在被使用，不允许删除");
	   				}
   				
	   			},
	   			error:function(){
	   				alert("抱歉，系统出现问题！");
	   			}
	   		});

   	});
//    	$("#btn_export").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 		var guid = [];
// 		checkbox.each(function(){
// 			guid.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/ysgl/xmsz/xmxx/expExcel?guid="+guid.join("','"),"项目信息","${ctx}",guid.join(","));
//    	});
  //导出Excel
   	$("#btn_export").click(function() {
   		var id = [];
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp("","${ctx}/ysgl/xmsz/xmxx/expExcel2","项目信息","${pageContext.request.contextPath}",id);
   		
   		
// 				var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/ysgl/xmsz/xmxx/expExcel2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","项目信息.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
			});

   	//编辑,单条删除
   	$(document).on("click",".btn-edit",function(){
   		var guid = $(this).attr("guid");
   		  $.ajax({
	   			url:"${ctx}/ysgl/xmsz/xmxx/selectXmmc",
	   			data:"dwbh="+guid,
	   			type:"post",
	   			dataType:"json",
	   			async:"false",
	   			success:function(val){
	   				if(val){
	   					window.location.href = target+"/goXmxxEditPage?guid="+guid;
	   				}else{
	   					alert("该项目正在被使用，不允许编辑");
	   				}
	   			},
	   			error:function(val){
	   				alert("抱歉，系统错误");
	   			}
   		  });
	   
   	});
   	//查看项目信息
	$(document).on("click",".btn_look",function(){
   		var guid = $(this).attr("guid");
	   	window.location.href = target+"/goXmxxLookPage?guid="+guid; 
   	});
	//部门编号弹窗，负责人弹窗
	$(document).on("click","#btn_dwbh",function(){
// 	$("#btn_dwbh").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwbh","单位信息","920","630");
    });
	
	$(document).on("click","#btn_rybh",function(){
// 	$("#btn_rybh").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_rybh","人员信息","920","630");
    });
});
</script>
</body>
</html>