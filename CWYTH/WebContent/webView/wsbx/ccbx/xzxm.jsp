<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>差旅费报销申请信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.btn-link{
	padding: 0px!important;
	margin: 0px!important;
}
	.text-green{
		color:green!important; 
	}
	.box-bottom{
		text-align: center;
		padding-top: 20px;
		padding-bottom: 20px;
		margin-left: -750px;
	}
	.dataTables_scrollHeadInner{
		width:100%! important;
	}
	#dualTd{
 		text-align:center! important;
 		padding-top:20px;
 	} 
/* 	table.dataTable{ */
/* 		width:950px ! important; */
/* 	} */
	/* 如果屏幕大小小于1500 */
	#dualTd{
 			width:100%! important;
 		} 
</style>
</head>

<body>

<div class="fullscreen">

	<div class="search" id="searchBox" style="padding-top: 0px">
	
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		
			<div class="pull-right" style="margin-top:3px;">
			
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
      					<span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
  					</div>
				</div>
				<div class="sub-title pull-left text-primary">选择出差业务&nbsp;></div>
		
<!-- 				<div class="sub-title pull-left text-primary"> -->
<!-- 					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;"> -->
<!-- 		      			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span> -->
<!-- 		  			</div> -->
<!-- 				</div> -->
<!-- 				<div class="sub-title pull-left">选择项目&nbsp;></div> -->
		
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
		      			<span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
		  			</div>
				</div>
				<div class="sub-title pull-left">差旅报销业务办理&nbsp;></div>
				
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
		      			<span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
		  			</div>
				</div>
				<div class="sub-title pull-left">结算方式设置&nbsp;</div>
		
<!-- 				<div class="sub-title pull-left text-primary"> -->
<!-- 				<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;"> -->
<!-- 		      			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">4</span> -->
<!-- 		  			</div> -->
<!-- 				</div> -->
<!-- 				<div class="sub-title pull-left">保存</div> -->
			
	        </div>
	        
			<div class="search-simple">
				
				<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>第一步,选择出差业务</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh"  table="K" placeholder="">
				</div>
				
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				
			</div>
			
		</form>
		
	</div>
	
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
			
				<div class="pull-right">
            		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
					</button>
			   		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
        		</div>
			
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>单据编号</th>
				            <th>申请人</th>
				            <th>所在部门</th>
<!-- 				            <th>经费类型</th> -->
				            <th>项目名称</th>
				            <th>出差类型</th>
				            <th>出差天数</th>
				            <th>出差人数</th>
				            <th>申请日期</th>
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
var target="${ctx}/wsbx/ccbx";
var suffix = "&mkbh=${param.mkbh}";


$("#btn_ywsm").click(function(){
	zysx();
});
function zysx(){
	//业务说明
	select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
}
//加载列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
    	   return '<input type="checkbox" ccts="'+full.CCTS+'" ccrs="'+full.CCRS+'" xmbh="'+full.XMBH+'" syje="'+full.SYJE+'" jflx="'+full.JF+'" xmguid="'+full.XMGUID+'" name="guid" class="keyId ggid" jfbh="'+full.JFBH+'" value="'+data+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "DJBH",defaultContent:"","class":"text-left"},
       {"data": "SQRXM",defaultContent:"","width":100},
       {"data": "SZBM",defaultContent:"","class":"text-left","width":150},
//        {"data": "JFLX",defaultContent:"","width":41},
       {"data": "XMMC",defaultContent:"","width":1000},
       {"data": "CCLX",defaultContent:"","class":"text-left"},
       {"data": "CCTS",defaultContent:"","class":"text-right"},
       {"data": "CCRS",defaultContent:"","class":"text-right"},
       {"data": "SQRQ",defaultContent:"","class":"text-center"},
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/wsbx/ccbx/getCcyw?ccywguid=${param.ccywguid}&operate=${param.operate}",[3,'asc'],columns,0,1,setGroup);
    //返回按钮
 	$("#btn_back").click(function(e){
 		if("${param.look}"!=""){
 			location.href="${ctx}/kylbx/gowdbxListPage";	
 		}else{
 			location.href="${ctx}/wsbx/ccbx/goCcbxListPage?mkbh=${param.mkbh}";
 		}
 	});
//
$(function () {
	$(document).ready(function(){
		$("#mydatatables").after('<div class="oobox-bottomoo" id="dualTd" >'+		
		'<button type="button" id="btn_next" class="btn btn-primary" >下一步</button>'+
		'</div>');
	});
	
	init();
 	function init(){
 		//回显选择的项目
 	 	var guid = "${param.ccywguid}";
 	 	console.log(guid);
 	 	$.each($("[name='guid']"),function(){
 	 		var $this = $(this);
 	 		if($this.val()==guid){
 	 			$this.attr("checked","checked");
 	 		}
 	 	});
 		
 	}
 	
  	$(document).on("click","#btn_next",function(){
  		var checkbox = $("#mydatatables").find(".keyId:checked");
  		var len = checkbox.length;
  		if(len == 0){
//   		alert("请选择事前审批单!!");
//   		return false;
  			confirm("您还没有选择出差业务事前审批，是否直接选择项目？",{title:"提示"},function(z){
				close(z);
				pageSkip(target,"selectXm&ccywguid=${param.ccywguid}",suffix);
			});
  		}else if(len == 1){
  		    var	guid = checkbox.attr("xmguid");
  		    var ccywguid = checkbox.val();
//   			var xmmc = checkbox.parents("tr").find("td").eq(6).text();
  			var xmmc = "";//库里项目名称有值表示非事前审批，下一步的是来自事前审批的出差申请，所以这里传空或者不传
  			var jflx = checkbox.attr("jflx");
  			var money = checkbox.attr("syje");
  			var xmbh = checkbox.attr("xmbh");
  			var ccts = checkbox.parents("tr").find("[name=guid]").attr("ccts");
  			var ccrs = checkbox.parents("tr").find("[name=guid]").attr("ccrs");
  			if("${param.operate}"=="U"){
  				location.href="${ctx}/wsbx/ccbx/goYwblEditPage?sfsqsp=1&mkbh=${param.mkbh}&xmmc="+xmmc+"&xmbh="+xmbh+"&Xmguid="+guid+"&jflx="+jflx+"&zbguid=${param.zbguid}&money="+money+"&look=${param.look}&ccywguid="+ccywguid;	
  			}else{
  				location.href="${ctx}/wsbx/ccbx/ywbl?sfsqsp=1&mkbh=${param.mkbh}&ccts="+ccts+"&ccrs="+ccrs+"&xmmc="+xmmc+"&xmbh="+xmbh+"&Xmguid="+guid+"&jflx="+jflx+"&money="+money+"&look=${param.look}&ccywguid="+ccywguid;
  			}
  			//location.href="${ctx}/webView/wsbx/ccbx/selectXm.jsp?operate=${param.operate}&mkbh=${param.mkbh}&zbguid=${param.zbguid}&look=${param.look}&ccywguid="+guid+"&djbh=${param.djbh}&xmguid="+xmguid;	
  		}else{
  			alert("只能选择一条信息");	
  		}
  	});
});
</script>
</body>
</html>