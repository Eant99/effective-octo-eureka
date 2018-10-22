<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%> 
<style type="text/css">
	html,body{
		height: 100%;
	}
 	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th { 
      	border-bottom-width: 0px;  
     	border-bottom:1px solid #ddd; 
 	} 
 	 table{ 
 		border-collapse:collapse!important; 
 	} 
 	.search-simple .select2-container--default .select2-selection--single { 
    		background-color: #fff; 
    		border: 1px solid #ccc; 
     	border-radius: 4px 4px 4px 4px; 
 	} 
 	th{ 
 		text-align:center;  
 	} 
 	.bottom{
 	bottom: 0px;
 	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="container-fluid">
		<div class="alert alert-info">
					<span style="display:inline-block;width: 45%;">
			          	<strong>提示：</strong>先找到需要的信息，然后双击鼠标添加这条信息。
					</span>
		          	<span style="display:inline-block;width: 45%;text-align:right;">
			          	<strong>提示：</strong>点击<strong style="">增加</strong>按妞可添加往来单位
			          	<button type="button" class="btn btn-primary" style="padding: 0px !important;" id="btn_add">增加</button>
		          	</span>
        </div>
        <div class="search" id="searchBox" style="text-align:right;">
	    	<form id="myform" class="form-inline" action="">
	    		<div class="search-simple">
	    			<div class="form-group">
						<label>户名</label>
						<input type="text" id="txt_djbh" class="form-control" name="dwmc" table="T" placeholder="" onkeydown="if(event.keyCode==13)return false;">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				</div>
	        </form>
	    </div>
        <div class='responsive-table'>
            <div class='scrollable-area' style="">
				<table id="mydatatables" class="table table-striped table-bordered">
					<thead>
					    <tr>
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
					        <th>户名</th>
				            <th>单位地址</th>
				            <th>联系人</th>
				            <th>办公电话</th>
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
var id = "${param.id}";
var controlId = "${param.controlId}";
var pname = "${param.pname}";

//列表数据
var columns = [
   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
     return '<input type="checkbox" name="guid" data-dwmc="'+full.DWMC+'" data-wlbh="'+full.WLBH+'" class="keyId" value="'+data+'" data-dwdz="'+full.DWDZ+'">';
   },"width":10,'searchable': false},
   {"data": "_XH",defaultContent:"","class":"text-center","width":40},
   {"data": "DWMC",defaultContent:"","width":100},
   {"data": "DWDZ",defaultContent:"","width":40},
   {"data": "LXR",defaultContent:"","width":180},
   {"data": "BGDH",defaultContent:"","class":"text-center"}
 ];
table = getDataTableByListHj("mydatatables","${ctx}/wsbx/rcbx/getWldwPageList",[2,'desc'],columns,0,1,setGroup);


	$(function () {
		var winId = getTopFrame().layer.getFrameIndex(window.name);
		$(document).on("dblclick","tbody tr",function(){
			var wlbh = $(this).find("[name=guid]").data("wlbh");
			var dwmc = $(this).find("[name=guid]").data("dwmc");
			var dwdz = $(this).find("[name=guid]").data("dwdz");
			var guid = $(this).find("[name=guid]").val();
			if(dwdz=="undefined"){
				dwdz="";
			}
			if(dwmc=="undefined"){
				dwmc="";
			}
			if(wlbh=="undefined"){
				wlbh="";
			}
			console.log(wlbh);
			console.log(dwmc);
			console.log(dwmc);
			if("${param.flag}"==1){
				getIframeControl("${param.pname}","${param.gid}").val(wlbh);
				getIframeControl("${param.pname}","${param.id}").val(dwmc);
				
				getIframeControl("${param.pname}","${param.id}").val(dwmc);
	        	getIframeControl("${param.pname}","${param.id}").focus();//手动触发验证
	        	getIframWindow("${param.pname}").xnzzJs(wlbh,dwmc,dwdz,id);//

	        	close(winId);
			}
			if("${param.flag}"==2){
				getIframeControl("${param.pname}","${param.gid}").val(guid);
				getIframeControl("${param.pname}","${param.id}").val(dwmc);
				
				getIframeControl("${param.pname}","${param.id}").val(dwmc);
	        	getIframeControl("${param.pname}","${param.id}").focus();//手动触发验证
	        	//getIframWindow(pname).xnzzJs(wlbh,dwmc,dwdz,id);//
	        	close(winId);
			}
    		
	    });
		$(window).resize(function(){
	    	$("div.dataTables_wrapper").width($("#searchBox").width());
	    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
	    	$(".dataTables_scrollBody").height(heights);
	    	table.draw();
		});
	});
	//添加按钮
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/wldwsz/goSkdwAddPage");
   		//select_commonWin("${ctx}/wldwsz/goSkdwAddPage?pname="+pname+"&id="+id,"","900","650");
	});
	
	
</script>
</body>
</html>