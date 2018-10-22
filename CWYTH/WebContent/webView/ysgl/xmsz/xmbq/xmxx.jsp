<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
 .dataTables_scrollBody{
    height:415px !important;
 }
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
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
				<button type="button" class="btn btn-primary" id="btn_submit"><i class="fa icon-chaxun"></i>确定</button>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<div class="alert alert-info">
		          	<strong>提示：</strong>先找到需要的信息，然后<strong>双击</strong>这条信息，或先选中需要的信息，然后点击<strong>确定</strong>按钮。
	        	</div>
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>项目编号</th>
				            <th>部门名称</th>
				            <th>项目名称</th>
				            <th>项目类型</th>
				            <th>负责人</th>
				            <th>归口部门</th>
				            <th>是否启用</th>
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
	//var winId = getTopFrame().layer.getFrameIndex(window.name);
	//加载列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" xmbh="'+full.XMBH+'" value="' + data + '" xmmc="'+full.XMMC+'" bmmc="'+full.BMBHMC+'" >';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "XMBH","render":function (data, type, full, meta){
		   	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
	   }},
       {"data": "BMBHMC",defaultContent:"","class":"text-left"},
       {"data": "XMMC",defaultContent:"","class":"text-left"},
       {"data": "XMLX",defaultContent:"","width":120},
       {"data": "FZRMC",defaultContent:"","width":120,},
       {"data": "GKBMMC",defaultContent:"","width":120,},
       {"data": "SFQYMC",defaultContent:""}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/xmbq/getXmxxPageData?bmbh=${param.bmbh}",[2,'asc'],columns,0,1,setGroup);
    $("#btn_submit").click(function(){
    	var winId = getTopFrame().layer.getFrameIndex(window.name);
    	var checkbox = $(".keyId").filter(":checked");
    	if(checkbox.length==0){
    		alert("请至少选择一条信息！");
    		return;
    	}else{
    		var xmid = [];
    		var xmbh = [];
        	var xmmc = [];
        	var bmmc = [];
        	$.each(checkbox,function(i,v){
        		var xmidList = $(this).val();
        		var xmbhList = $(this).attr("xmbh");
        		var xmmcList = $(this).attr("xmmc");
        		var bmmcList = $(this).attr("bmmc");
        		
                xmid.push(xmidList);  
        		xmbh.push(xmbhList);
        		xmmc.push(xmmcList);
        		bmmc.push(bmmcList);
        	});
        	getIframWindow("${param.pname}").addxmxx(xmid,xmbh,xmmc,bmmc);
        	close(winId);
    	}
    	
    });
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var winId = getTopFrame().layer.getFrameIndex(window.name);
    	var checkbox = $(this).find(".keyId");
    	if(checkbox.length==0){
    		alert("请至少选择一条信息！");
    		return;
    	}else{
    		var xmid = [];
    		var xmbh = [];
        	var xmmc = [];
        	var bmmc = [];
        	
        	$.each(checkbox,function(i,v){
        		var xmidList = $(this).val();
        		var xmbhList = $(this).attr("xmbh");
        		var xmmcList = $(this).attr("xmmc");
        		var bmmcList = $(this).attr("bmmc");
        		
                xmid.push(xmidList);
        		xmbh.push(xmbhList);
        		xmmc.push(xmmcList);
        		bmmc.push(bmmcList);
        	});
        	getIframWindow("${param.pname}").addxmxx(xmid,xmbh,xmmc,bmmc);
        	close(winId);
    	}
    });
    function reloadList() {
    	table.ajax.reload();
    }
});

</script>
</body>
</html>