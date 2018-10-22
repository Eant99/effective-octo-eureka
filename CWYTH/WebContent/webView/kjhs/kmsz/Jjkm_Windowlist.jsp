<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>经济科目设置</title>

<%@include file="/static/include/public-manager-css.inc"%> 
<style type="text/css">
	select{
		font-size:12px !important; 
		max-height: 25px;  
		padding: 0px !important;
		padding-left: 5px !important;
	}
	#mydatatables td 
	{
	line-height:10px;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
				
				<div class="form-group">
					<label>科目编号</label>
					<input type="text" id="txt_xm" class="form-control input-radius" name="kmbh"  table="j" placeholder="请输入科目编号">
				</div>
				<div class="form-group">
					<label>科目名称</label>
					<input type="text" class="form-control input-radius" name="kmmc"  table="j" placeholder="请输入科目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
<!-- 				<div class="btn-group pull-right" role="group"> -->
<!-- 					<button type="button" class="btn btn-default" id="btn-del">批量删除</button> -->
<!-- 	                <button class="btn btn-default" type="button" id="btn_addtj">增加同级</button> -->
<!-- 	                <button class="btn btn-default" type="button" id="btn_addxj">增加下级</button> -->
<!-- 	       			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button> -->
<!-- 	         	</div> -->
			</div>
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
		    	<input type="hidden" name="guid" value="${guid}"/>
		        <table id="mydatatables" class="table table-striped table-bordered" >
					<thead>
		 				<tr >
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						    <th>科目编号</th>
						    <th>科目名称</th>
							<th>科目级次</th>	
<!-- 						    <th>类</th>	 -->
<!-- 						    <th>款</th>	 -->
						    <th>是否启用</th>	
						    <th>说明</th>						    					  
			   			</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%> 
<%@include file="/static/include/public-list-css.inc"%>
<script >
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	 //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	console.log("11=============="+"${param.controlId}");
    	var vals = $(this).find("[name='guid']").val();
    	if(vals==''||vals==null||vals==undefined){
    		alert("没有可以选择的数据！");
    	}else{
	    	console.log("${param.controlId}______"+vals);
    		getIframeControl("${param.pname}","${param.controlId}").val(vals);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	close(winId);
    	}
    });
	var columns = [
		{"data": "guid",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" name="guid" kmbh="'+full.KMBH+'" class="keyId" value="' + "("+full.KMBH+")"+full.KMMC + '" guid = "'+full.GUID+'" kmjc = "'+full.KMJC+'">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "KMBH",defaultContent:"","class":"hj"},
	   	{"data": "KMMC",defaultContent:""},
	   	{"data": "KMJC",defaultContent:""},
// 		{"data": "L",defaultContent:""},
// 		{"data": "K",defaultContent:""},
		{"data": "QYF",defaultContent:""},
		{"data": "SM",defaultContent:""}
	];
   table = getDataTableByListHj("mydatatables","${ctx}/kmsz/getJjkmPageList?treeDm=${param.kmbh}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
   	//table = getDataTableByListHj("mydatatables","${ctx}/json/kjhs/kmsz/jjkmsz/jjkmsz_list.json?treeDm=${param.dm}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
   /* 	$("#btn_sskj").click(function(){
		select_commonWin("${pageContext.request.contextPath}/window/gnszpage?controlId=txt_sskj","会计科目信息","920","630");
    });  */
   	
   	$("#btn_sskj").click(function(){
   		select_commonWin("${ctx}/pzlx/window?controlId=txt_sskj","科目信息","920","630");
    });
	
	
});

</script>
</body>
</html>