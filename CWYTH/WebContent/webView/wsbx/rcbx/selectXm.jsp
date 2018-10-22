<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>选择项目</title>
<%@include file="/static/include/public-list-css.inc"%>
<%--表头样式--%>
<style type="text/css">
	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
/*     	border-bottom-width: 0px; */
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}
	
 	.dualTd{
 		text-align:center!important;
/*  		padding-top:10px; */
		padding-bottom: 20px;
 	} 
	.dualTd button{
		background:#00acec;
		color: white;
	}
	#btn_save{
		background-color: #00acec;
    	color: white;
	}
	#btn_save i {
   	 	color: white;
	}
	.dataTables_scrollHeadInner{
		width:100% ! important;
	}
	.dualTd{
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
				<div class="sub-title pull-left text-primary">选择项目&nbsp;></div>
				
				<!-- <div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
        				<span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    				</div>
				</div>
				<div class="sub-title pull-left">费用填写&nbsp;></div> -->
				
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
        				<span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    				</div>
				</div>
				<div class="sub-title pull-left">费用填写&nbsp;></div>
				
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
        				<span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
    				</div>
				</div>
				<div class="sub-title pull-left">结算方式设置&nbsp;</div>

	        </div>
	        
			<div class="search-simple">
			
				<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>第一步,选择项目</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh"   placeholder="请输入项目编号查询">
				</div>
				
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc"  placeholder="请输入项目名称查询">
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
<!-- 					<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button> -->
		  			<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
     	 		</div>
     	 		
				<table id="mydatatables" class="table table-striped table-bordered dataTable ">
		        	<thead>
				        <tr>
				       		<th>&nbsp;</th>
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>项目类型</th>
				            <th>项目类别</th>
				            <th>项目大类</th>
				            <th>项目负责人</th>
				            <th>预算金额(元)</th>
				            <th>余额(元)</th>
				            <th>授权截止日期</th>
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
	//业务说明
	operateYwsm("${ctx}");
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
			{"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
				
    			return '<input type="checkbox" name="guid" class="keyId" YE="'+full.YE+'"  ZFCGSYJE="'+full.ZFCGSYJE+'" FZFCGSYJE="'+full.FZFCGSYJE+'" XMMC="'+full.XMMC+'" XMLBMC="'+full.XMLBMC+'" value="' + data + '"  xmguid="'+data+'"  guid = "'+full.XMBH+'" jflxdm="'+full.JFLXDM+'">';
  			},"width":10,'searchable': false},
       {"data": "XMBH",defaultContent:""},
       {"data": "XMMC",defaultContent:""},
       {"data": "JFLX",defaultContent:""},
       {"data": "XMLBMC",defaultContent:"","width":280},
       {"data": "XMDLMC",defaultContent:"","width":280},
       {"data": "FZR",defaultContent:"","width":300},
       {"data": "YSJE",orderable:false,defaultContent:"0.00","width":200,"class":"text-right"},
       {"data": "YE",defaultContent:"0.00","class":"text-right","width":200},
       {"data": "JZSJ",defaultContent:"","class":"text-center","width":30},
     ];
  	table = getDataTableByListHj("mydatatables","${ctx}/wsbx/rcbx/getPageListByLoginXm?treedwbh=${dwbh}",[6,'DESC'],columns,0,1,setGroup);
//    table = getDataTableByListHj("mydatatables","${ctx}/json/wsbx/rcbx/rcbxList.json",[2,'asc'],columns,0,1,setGroup);
	
});
$(function() {
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
	$("#mydatatables").after("<br/><div class='dualTd'>"+
	"<button class='btn btn-default' id='btn_next' >下一步</button>"+
	"</div>");
});
//选中项目，点击下一步
$(document).on("click","[id='btn_next']",function(){
	var checkbox = $(".keyId:checkbox").filter(":checked");
	var xmmc = checkbox.parents("tr").find("td").eq(2).text();
	var money = checkbox.parents("tr").find("[name='guid']").attr("YE");
	var xmguid = checkbox.attr("xmguid");
	var jflxdm = checkbox.parents("tr").find("[name='guid']").attr("jflxdm");
	var xmlbmc = checkbox.parents("tr").find("[name='guid']").attr("XMLBMC");
	var zfcgsyje = checkbox.parents("tr").find("[name='guid']").attr("ZFCGSYJE");
	var fzfcgsyje = checkbox.parents("tr").find("[name='guid']").attr("FZFCGSYJE");
	var flag=true;
	if(checkbox.length==1){
   		var xmguids = [];
   		var moneys = [];
   		var xmmcs = [];
   		var jflxdms = [];
   		var xmlbmcs = [];
   		var zfcgsyjes = [];
   		var fzfcgsyjes = [];
   		checkbox.each(function(){
  			xmguids.push($(this).val());
  			moneys.push($(this).attr("YE"));
  			xmmcs.push($(this).attr("XMMC"));
  			jflxdms.push($(this).attr("jflxdm"));
  			xmlbmcs.push($(this).attr("XMLBMC"));
  			zfcgsyjes.push($(this).attr("ZFCGSYJE"));
  			fzfcgsyjes.push($(this).attr("FZFCGSYJE"));
   		});
   		if(xmlbmcs.indexOf("科研")>0){
   			 checkbox.each(function(){
   				if($(this).attr("XMLBMC")!="科研"){
   					flag=false;
   				}
   			});
   		}
   		if(xmlbmcs.indexOf("科研")<0){
   			flag=2;
   		}
   		if(checkbox.length==1){
   			if(xmlbmc=="科研"){
   				flag=1;
   			}else{
   				flag=2;
   			}
   		}
   		if(flag==false){
   			alert("请选择相同的项目类别");
   		}else{
   			if("1"=="${param.sfbj}"){//若编辑
		   		var src = "${ctx}/wsbx/rcbx/ywbl?sfxz=0&look=${param.look}&bjzt=${param.bjzt}&zbid=${param.zbid}&xmmc="+xmmcs+"&xmguid="+xmguids+"&mkbh=${param.mkbh}&djbh=${param.djbh}&money="+moneys+"&jflxdm="+jflxdms+"&bz=delete&flag="+flag+"&zfcgsyje="+zfcgsyjes+"&fzfcgsyje="+fzfcgsyjes;
   			}else{//若新建
   			   	var src = "${ctx}/wsbx/rcbx/ywbl?sfxz=1&look=${param.look}&bjzt=${param.bjzt}&zbid=${param.zbid}&xmmc="+xmmcs+"&xmguid="+xmguids+"&mkbh=${param.mkbh}&djbh=${param.djbh}&money="+moneys+"&jflxdm="+jflxdms+"&bz=delete&flag="+flag+"&zfcgsyje="+zfcgsyjes+"&fzfcgsyje="+fzfcgsyjes;
   			}
	   		location.href = src;
   		}
	}else{
		alert("请选择一条项目信息进行操作!");
	}
});
//返回列表操作
$(document).on("click","[id='btn_back']",function(){
	var look = "${param.look}";
	if(look=="look"){
		location.href="${ctx}/kylbx/gowdbxListPage";
	}else{
		location.href="${ctx}/wsbx/rcbx/goRcbxListPage?mkbh=${param.mkbh}";
	}
	
});
//点击查看业务说明
$(document).on("click","[id='btn_ywsm']",function(){
	zysx();
});
//查看业务说明弹窗
function zysx(){
	//业务说明
	select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
}
$(function(){
	huixian();
});
//回显选择的项目
function huixian(){
	var guid = "${param.xmguid}";
	var flag = "${param.flag}";
	if(flag=="1"){
		var guids = guid.split(",");
		for (var i=0;i<guids.length;i++){
			var gid = guids[i];
			var i = 0;
			$.each($("[name='guid']"),function(){
				var $this = $(this);
				i = i+1;
				if($this.attr("xmguid")==gid){
					$this.attr("checked","checked");
				}
			});
		}
	}else{
		$.each($("[name='guid']"),function(){
			var $this = $(this);
			if($this.attr("xmguid")==guid){
				$this.attr("checked","checked");
			}
		});
	}
};
//项目单选方法
$(document).on("change","[name='guid']",function(){
	$("[name='guid']").not(this).attr("checked", false);  
});
</script>
</body>
</html>