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
 .dataTables_scrollBody{
    height:450px !important;
 }
</style>
</head>
<body>
<div class="fullscreen">
<!--     <div class="search" id="searchBox"> -->
<!--     	<form id="myform" class="form-inline" action=""> -->
<!--     		<div class="search-simple"> -->
<!-- 			</div> -->
<!--         </form> -->
<!--     </div> -->
	<div class="container-fluid">
		<div class="alert alert-info">
					<span style="display:inline-block;width: 45%;">
			          	<strong>提示：</strong>先找到需要的信息，然后双击鼠标添加这条信息。
					</span>
<!-- 		          	<span style="display:inline-block;width: 45%;text-align:right;"> -->
<!-- 			          	<strong>提示：</strong>点击<strong style="padding: 0px 5px;">增加</strong>按妞可添加往来单位 -->
<!-- 			          	<button type="button" class="btn btn-primary" id="btn_add">增加</button> -->
<!-- 		          	</span> -->
        </div>
        <div class='responsive-table'>
            <div class='scrollable-area' style="margin-top: 5px">
				<table id="mydatatables" class="table table-striped table-bordered">
					<thead>
					    <tr>
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
					        <th>人员工号</th>
				            <th>姓名</th>
				            <th>性别</th>
				            <th>负责项目</th>
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
var flag = "${param.flag}";
//列表数据
var columns = [
   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
     return "<input type='checkbox' name='guid' data-rybh='"+full.RYBH+"' ryguid='"+full.GUID+"' data-ryxm='"+full.RYXM+"' class='keyId' value='("+full.RYBH+")"+full.RYXM+"' >";
   },"width":10,'searchable': false},
   {"data": "_XH",defaultContent:"","class":"text-center","width":20},
   {"data": "RYBH",defaultContent:"","width":50},
   {"data": "RYXM",defaultContent:"","width":50},
   {"data": "XB",defaultContent:"","width":30},
   {"data": "XMMC",defaultContent:"","width":180}
 ];
 if(flag == 1){
	 table = getDataTableByListHj("mydatatables","${ctx}/jksq/getXmfzrPageList?ccywguid=${ccywguid}",[2,'desc'],columns,0,1,setGroup);
 }else if(flag == 2){
	 table = getDataTableByListHj("mydatatables","${ctx}/wsbx/rcbx/getXmfzrPageList?ccywguid=${ccywguid}",[2,'desc'],columns,0,1,setGroup);
 }
 else{
table = getDataTableByListHj("mydatatables","${ctx}/wsbx/ccbx/getXmfzrPageList?ccywguid=${ccywguid}",[2,'desc'],columns,0,1,setGroup);
 }

	$(function () {
		var winId = getTopFrame().layer.getFrameIndex(window.name);
// 		winId = top.layer.getFrameIndex(window.name);
		$(document).on("dblclick","tbody tr",function(){
			var ryxm = $(this).find("[name=guid]").val();
			var ryguid=$(this).find("[name=guid]").attr("ryguid");
// 			var dwmc = $(this).find("[name=guid]").data("dwmc");
// 			var dwdz = $(this).find("[name=guid]").data("dwdz");
			var cdid="${param.id}";
        	getIframeControl("${param.pname}","${param.id}").val(ryxm);
//         	close(winId);
        	getIframWindow("${param.pname}").xmfzrdsqtr(ryguid,cdid);//
        	console.log(ryguid);
        	close(winId);
	    });
		
	    //双击事件
// 	    $(document).on("dblclick","#mydataTables tr:not(#header)",function(){
// 	    	var guid = $(this).find("[name='rygh']").attr("guid");
// 	    	var dwmc = $(this).find("[name='rygh']").attr("dwmc");
// 	    	var ry = $(this).find("[name='rygh']").val();
// 	    	var rydom = $(this).find("[name='rygh']");
// 	    	if(guid==''||guid==null||guid=='undefined'){
// 	    		alert("没有可以选择的数据！");
// 	    	}else{
// 	    		getIframeControl("${param.pname}","${param.id}").val(ry);
// 	    		if("${param.ryid}" !=''){
// 	    			getIframeControl("${param.pname}","${param.ryid}").val(guid);
// 	    		}
// 	    		if(typeof getIframWindow("${param.pname}").ryWindowCallBack == "function"){//岗位交接时获取该人员的角色信息
// 	    			getIframWindow("${param.pname}").ryWindowCallBack("${param.controlId}",rydom);
// 	    		}
// 	    		getIframWindow(pname).xnzzJs(wlbh,dwmc,dwdz,id);//
// 	        	close(winId);
// 	    	}
// 	    });
		
		
		
		$(window).resize(function(){
	    	$("div.dataTables_wrapper").width($("#searchBox").width());
	    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
	    	$(".dataTables_scrollBody").height(heights);
	    	table.draw();
		});
	});
	//添加按钮
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/wldwsz/goSkdwAddPage?pname="+pname+"&id="+id);
   		//select_commonWin("${ctx}/wldwsz/goSkdwAddPage?pname="+pname+"&id="+id,"","900","650");
	});
	function toUrl(){
		console.log("123");
	}
	
</script>
</body>
</html>