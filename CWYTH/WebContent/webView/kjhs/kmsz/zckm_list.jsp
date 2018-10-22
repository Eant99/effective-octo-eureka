<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>会计科目设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	select{
		font-size:12px !important; 
		max-height: 25px !important;  
		padding: 0px !important;
		padding-left: 5px !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
		    	<input type="hidden" name="guid" value="${guid}"/>
		        <table id="mydatatables" class="table table-striped table-bordered">
					<thead>
		 				<tr>
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						 <!--    <th>项目类型编号</th> -->
						    <th>支出科目编号</th>
						    <th>支出科目名称</th>
			   			</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
 <%@include file="/static/include/public-list-js.inc"%>  
<script >
$(function(){
	//添加按钮
   	$("#btn_addtj").click(function(){
   		doOperate("${ctx}/kmsz/goEditKmPage","C");
   	});
	$("#btn_addxj").click(function(){
   		doOperate("${ctx}/kmsz/goEditKmPage","C");
   	});
	$("#btn_copy").click(function(){
		
		select_commonWin("${ctx}/window/multiSrkmList?operateType=C","复制信息","400","450");
	});
	
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var shzt = checkbox.attr("shzt");
   		if(shzt=="已提交"||shzt=="通过"){
   			alert("已提交或者审核通过的无法修改");
   			return;
   		}
		confirm("确定删除？","{title:提示信息}",function(){
			alert("删除成功");
		});
	});
	
	$(document).on("click",".btn_upd",function(){
		
		//var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/kjhs/kmsz/km_edit.jsp", "C");
	});
	
	var columns = [
		{"data": "XMLXBH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="XMLXBH" bz="'+full.BZS+'"value="' + "("+full.ZCKMBH+")"+full.KMMC + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	  /*  	{"data": "XMLXBH",defaultContent:"","class":"text-center"}, */
	   	{"data": "ZCKMBH",defaultContent:"","class":"text-center"},
	   	{"data": "KMMC",defaultContent:"","class":"text-center"},
	   	
	];
	
   	table = getDataTableByListHj("mydatatables","${ctx}/window/multiZckmList?treeDm=${param.dm}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
//    table = getDataTableByListHj("mydatatables","${ctx}/json/kjhs/kmsz/kjkmsz/kjkmsz_list.json",[2,'asc'],columns,0,1,setGroup);
  
   $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
   	var val = $(this).find("[name='XMLXBH']").val();
   	if(val==''||val==null||val=='undefined'){
   		alert("没有可以选择的数据！");
   	}else{
   		getIframeControl("${param.pname}","${param.controlId}").val(val);
       	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
       	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
       	close(winId);
   	}
   });
	
	
});

</script>
</body>
</html>