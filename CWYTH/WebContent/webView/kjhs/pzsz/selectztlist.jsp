<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>账套信息管理</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
 <div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>账套名称</label>
					<input type="text" id="txt_mbbh" class="form-control" name="ztmc" table="k" placeholder="请输入账套名称">
				</div>
				<!-- <div class="form-group">
					<label>账套内容</label>
					<input type="text" id="txt_mbnr" class="form-control" name="mbnr"  table="A" placeholder="请输入账套内容">
				</div> -->
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>

				<div class="btn-group pull-right" role="group">
<!-- 	               <button type="button" class="btn btn-default" id="btn_add">增加</button> -->
<!-- 	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button> -->
	            <!--    <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button> -->
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
				            <th>账套名称</th>
				            <th>会计年度</th>
				            <th>期间数</th>
				            <th>启用日期</th>
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
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" flag="'+full.ZTMC+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "ZTMC",defaultContent:""}, 
       {"data": "KJND",defaultContent:""},
       {"data": "QJS",defaultContent:""},     
       {"data": "QYRQ",defaultContent:""}   
      
     ];
 //table = getDataTableByListHj("mydatatables","${ctx}/pzmb/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
	 // table = getDataTableByListHj("mydatatables","${ctx}/json/kjhs/pzsz/pzmbgl/pzmbgl_list.json",[2,'asc'],columns,0,1,setGroup);
	 table = getDataTableByListHj("mydatatables","${ctx}/ztxx/kmxx",[2,'asc'],columns,0,1,setGroup);
	 //table = getDataTableByListHj("mydatatables","${ctx}/pzsz/ztxx",[2,'asc'],columns,0,1,setGroup);


  	//添加按钮
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/ztxx/goEditPage?operateType=C","C");
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/jsxxs/expExcel?treedwbh=${dwbh}","教师信息","${ctx}",guid.join(","));
   	});
   	//修改按钮
   /* 	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	doOperate("${ctx}/webView/kjhs/pzmb/pzmb_edit.jsp","C");
   	});*/ 
	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();

   		doOperate("${ctx}/ztxx/goEditPage?operateType=U&guid="+guid,"U");
   	});
    //单击事件
    $(document).on("dblclick","#mydatatables tr",function(){
    	var val = $(this).find("[name='guid']").val();
    	var name= $(this).find("[name='guid']").attr("flag");
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		
    		$.ajax({
   	   			//url:ADDRESS+"/srxm/delete",
   	   			url:"${ctx}/selectzt/add",
   	   			data:"guid="+val+"&name="+name,
   	   			type:"post",
   	   			async:"true",
   	   			success:function(data){
   	   			 parent.location.reload();   
   	   			},
   	   			error:function(){
   	   				alert("抱歉，系统出现错误！");
   	   			}
   	   		});

//         	getIframWindow("${param.pname}").callBack(val);
//         	var winId = top.layer.getFrameIndex(parent.window.name);
//         	top.layer.close(winId);
    	}
    });
  
    /* //批量删除按钮
    $("#btn_del").click(function(){
    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
    		if(checkbox.length>0){
    	   		confirm("确定删除？","{title:提示信息}",function(){
    	   			alert("删除成功");
    	   		})
    		}else{
    			alert("请选择至少一条信息删除!");
    		}
    	}); */
    //批量删除按钮
   	/* $("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		$(this).parents("tr").remove();
   	   	     px();
   	   		});
	   		
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	}); */
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			var guid = [];
	   	   	checkbox.each(function(){
	   	   		guid.push($(this).val());
	   	   	});
	   	   	var url = "${ctx}/ztxx/doDelete";
	   	   	doDel("guid="+guid.join("','"),url,function(){
	   	   		table.ajax.reload();
	   	   	},"",checkbox.length);
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
 	//编辑,单条删除
   	$(document).on("click",".ztxx_edit",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	window.location.href = target+"/goSrxmEditPage?guid="+guid;
   	});
   	$(document).on("click",".btn_delxx",function(){
  		var guid = $(this).parents("tr").find("[name='guid']").val();
		confirm("确定删除该条信息？","",function(){
   			$.ajax({
   	   			//url:ADDRESS+"/srxm/delete",
   	   			url:"${ctx}/ztxx/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"true",
   	   			success:function(data){
   	   				var result = JSON.getJson(data);
  	   				if(result.success){
						alert("删除成功！");  	   					
	   	   				table.ajax.reload();
  	   				}
   	   			},
   	   			error:function(){
   	   				alert("抱歉，系统出现错误！");
   	   			}
   	   		});
   		});
	});
 	//单条删除
	/* $(document).on("click",".btn_delxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
   		confirm("确定删除？","",function(){
   			$.ajax({
   	   			url:"http://localhost:9999/app/pzbmgldelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert(val);
   	   				table.ajax.reload();
   	   			}
   	   		});
   			alert("删除成功");
   		});
	}); */
	//查询
	
	
	
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
</body>
</html>