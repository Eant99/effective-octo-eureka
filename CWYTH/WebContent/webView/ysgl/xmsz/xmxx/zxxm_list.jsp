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
				<c:if test="${empty isWindow}">
					<div class="btn-group pull-right" role="group">
		              <!--  <button type="button" class="btn btn-default" id="btn-add">增加</button>
		               <button type="button" class="btn btn-default" id="btn_del">批量删除</button> -->
		               <button type="button" class="btn btn-default" id="btn_export">导出Excel</button>
					</div>
				</c:if>
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
<!-- 				            <th>部门名称</th> -->
				            <th>项目大类</th>
				            <th>项目类别</th>
<!-- 				            <th>项目类型</th> -->
				            <th>预算金额</th>
				            <th>政府采购金额</th>
				            <th>非政府采购金额</th>
				            <th>审核通过金额（元）</th>
				            <th>待审核金额（元）</th>
				            <th>剩余金额（元）</th>
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
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
var target = "${ctx}/ysgl/xmsz/xmxx";
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
	   {"data": "BMBH",defaultContent:"","class":"text-left"},
       {"data": "XMMC",defaultContent:"","class":"text-left"},
       {"data": "XMDLMC",defaultContent:"","class":"text-left"},
       {"data": "XMLBMC",defaultContent:"","class":"text-left",},
       {"data": "YSJE",defaultContent:"","width":120,"class":"text-right"},
       {"data": "ZFCGJE",defaultContent:"","width":120,"class":"text-right"},
       {"data": "FZFCGJE",defaultContent:"","class":"text-right"},
       {"data": "ZCJE",'render':function(data, type, full, meta){
    	   if(full.ZCJE==undefined){
	   		return '<a href="javascript:void(0);" guid='+full.GUID+' zcje = '+full.ZCJE+' class="btn btn-link btn_look">0.00</a>';
    	   }else{
    	    return '<a href="javascript:void(0);" guid='+full.GUID+' zcje = '+full.ZCJE+' class="btn btn-link btn_look">'+data+'</a>';
    	   }
       },orderable:false,"class":"text-right","width":220},
       {"data": "DSHJE",'render':function(data, type, full, meta){
    	   if(full.DSHJE==undefined){
	   		return '<a href="javascript:void(0);" guid='+full.GUID+' dshje = '+full.DSHJE+' class="btn btn-link btn_dsh">0.00</a>';
    	   }else{
    	    return '<a href="javascript:void(0);" guid='+full.GUID+' dshje = '+full.DSHJE+' class="btn btn-link btn_dsh">'+data+'</a>';
    	   }
       },orderable:false,"class":"text-right","width":220},
       {"data": "SYJE",defaultContent:"","class":"text-right"},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid = "'+full.GUID+'">查看</a>';
      },orderable:false,"width":220,"class":"text-center","width":100}
     ];
    table = getDataTableByListHj("mydatatables",target+"/getWdxmPageData",[2,'asc'],columns,0,1,setGroup);
  	//添加,批量删除，导出excel
  	$(document).on("click","#btn-add",function(){
//    	$("").click(function(){
	   	window.location.href = target+"/goXmxxAddPage?";
   	});
	//批量删除
	$(document).on("click","#btn_del",function(){
//   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
   			if(checkbox.length>0){
   	   			var guid = [];
   	   			checkbox.each(function(){
   	   				guid.push($(this).val());
   	   			});
				$.ajax({
	   			url:"${ctx}/ysgl/xmsz/xmxx/selectXmmc",
	   			data:"dwbh="+guid.join("','"),
	   			type:"post",
	   			dataType:"json",
	   			async:"false",
	   			success:function(val){
	   				if(val){
	   	    	   		confirm("确定要删除选中的这"+checkbox.length+"条信息吗？","",function(){
	    				$.ajax({
	    		   			url:"${ctx}/ysgl/xmsz/xmxx/doDelete",
	    		   			data:"dwbh="+guid.join("','"),
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
	   					alert("存在正在被使用的项目信息，不允许删除");
	   				}
	   				
	   				
	   			},
	   			error:function(){
	   				alert("抱歉，系统出现问题！");
	   			}
	   		});


   	   		}else{
   	   			alert("请选择至少一条信息删除!");
   	   		}
   		
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
  //导出Excel
  	$(document).on("click","#btn_export",function(){
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
   		doExp("","${ctx}/ysgl/xmsz/xmxx/expExcel3","项目执行情况信息","${pageContext.request.contextPath}",id);
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
   	//查看
		$(document).on("click",".btn_look",function(){
   		var guid = $(this).attr("guid");
	   	window.location.href = target+"/goXmxxLookPage?guid="+guid+"&flag=1"; 
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