<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>在职薪资管理</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.table>thead>tr>th {
    padding: 12px;
/*     padding-top: 7px; */
/*     padding-right: 7px; */
/*     padding-bottom: px; */
/*     padding-left: 7px; */
}
	.bottom {
    width: 99%;
    position: absolute;
    bottom: 27px;
    background-color: #f3f3f3;
}
	.td_input{
		border:none;
		width:100%;
	}
	.text-green{
		color:green!important;
	}
	th{
		text-align:center;
	}
	.yc{
		display:none!important; 
	}
	.table>tbody>tr.selected:nth-of-type(odd) {
 	background-color: #f9f9f9!important;
	}
	.table>tbody>tr.selected:nth-of-type(even) {
 	background-color: #f3f3f3!important;
	}

</style>
</head>
<body style="height: 100%">
<div class="" style="background-color: white;">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 0px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>人员编号或姓名</label>
			        <input type="text" id="text_rybh" class="form-control input-radius" style="width:130px" placeholder="请输入人员编号或姓名">
			        <button type="button" id="btn_rybh" class="btn btn-link ">选择</button>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label>姓名</label> -->
<!-- 			        <input type="text" id="text_ryxm" class="form-control input-radius"  style="width: 80px"  table="t" placeholder="请输入姓名"> -->
<!-- 			        <button type="button" id="btn_ryxm" class="btn btn-link ">选择</button> -->
<!-- 				</div> -->
				<div class="form-group">
					<label>部门编号或名称</label>
			        <input type="text" id="text_bm" class="form-control input-radius" style="width:130px" table="t" placeholder="请输入部门编号或名称">
			        <button type="button" id="btn_bm" class="btn btn-link ">选择</button>
				</div>
				<div class="form-group">
					<label>审核状态</label>
					<select class="form-control input-radius" id="shzt" >
						<option value="">全部</option>
						<option value="0" selected="selected">未提交</option>
						<option value="5">已核算</option>
						<option value="1">待审核</option>
						<option value="2">审核通过</option>
						<option value="3">已退回</option>
					</select>
				</div>
				<div class="form-group">
					<label>月份</label>
<!-- 					<span class="input-group-addon" style="width: 40px">月份</span> -->
					<input type="text" id="gzyf" style="display:inline-block;width: 60px;" class="form-control input-radius nydate2"  value="${yfMap.ffyf}"/>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search1"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
				    <button type='button' class="btn btn-primary" id="btn_save">保存</button>
				    <button type='button' class="btn btn-default" id="btn_plfz">批量赋值</button>
					<button type='button' class="btn btn-default" id="btn_tianjia">添加</button>
					<button type='button' class="btn btn-default" id="btn_shanchu">删除</button>
				    <button type='button' class="btn btn-default" id="btn_fzsygz">复制上月工资</button>
				    <button type='button' class="btn btn-default" id="btn_hs">核算</button>
					<button type='button' class="btn btn-default" id="btn_tj">提交</button>
					<button type='button' class="btn btn-default" id="btn_imp">导入</button>
 					<button id="btn_exp" type="button" class="btn btn-default">导出</button>
					<button id="btn_lxz" class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">列选择</button>
				</div>
			</div>
		</form>
	</div>

<form id="mysave" name="mysave" method="post"  >
<div class="container-fluid " >
		<div class='responsive-table' >
			<div class='scrollable-area' > 
			<table id="mydatatables" class="table table-striped table-bordered" >
		        	<thead>
				        <tr>
				            <th flag="tr_sh111"><input type="checkbox" class="select-all"/></th>
					        <th flag="tr_sh222">序号</th>
					           <c:forEach items="${gzwhlist}" var="li">
					        	 <th style="text-align:center;" >${li.xmmc}</th>
					           </c:forEach>
				        </tr>
					</thead>
				    <tbody>
				   </tbody>
				</table>					
		</div>
			</div>
		</div>	
	</form>		
		</div>
<input type="hidden" id="pzcxs" class="form-control input-radius window" name="pzcxs" value="">
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/download.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/public/columnsExp.js"></script>
<script>
//添加功能   弹出人员信息树，进行人员信息的添加，如果当前发放月份存在当前人员则不能添加；
$("#btn_tianjia").click(function(){
	var gzyf=$("#gzyf").val();
	select_commonWin("${ctx}/xzgl/rypage?controlId=zzry&gzyf="+gzyf,"人员信息","920","630");
});
	//查询人员编号或者姓名
	$(document).on("click","#btn_rybh",function(){			
		select_commonWin("${ctx}/xzgl/rypage?controlId=text_rybh&model=xzcx","人员信息","920","630");
	});
	//查询单位编号或者名称
	$(document).on("click","[id=btn_bm]",function(){			
		select_commonWin("${ctx}/window/dwpage?controlId=text_bm","部门信息","920","630");
	});
	//查询人员编号或者姓名
	$("#text_rybh").bindChange("${ctx}/suggest/getXx","R");
	//查询单位编号或者名称
	$("#text_bm").bindChange("${ctx}/suggest/getXx","D");
$(document).on("click","[id=btn_plfz]",function(){
	var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
	var msg = '';
	if(checkbox.length>0){
		var dwbh = [];
		checkbox.each(function(){
			dwbh.push($(this).val());
			if("1"==$(this).val()){
				msg ='1';
// 				alert("合计不允许赋值！")
				return;
			}else if("未提交"!=$(this).attr("shzt")){
				msg = '2';
				return;
			}
		});
		if(msg=="1"){
			alert("合计不允许赋值！");
			return false;
		}else if(msg=="2"){
			alert("只能选择未提交的信息进行赋值！");
			return false;
		}else{
			confirm("确认批量赋值"+dwbh.length+"条信息？",{title:"提示"},function(index){
				select_commonWin("${ctx}/xzgl/goPlfuzhiPage?dwbh="+dwbh.join(","),"批量赋值","400","450");
				close(index);
			}); 
		}
	}else{
		var rybh = $("#text_rybh").val();
		var shzt = $("#shzt").val();
		var gzyf = $("#gzyf").val();
		var xm = $("#text_ryxm").val();
		var bm = $("#text_bm").val();
// 		if("0"==shzt){
		confirm("点击确认将会确认批量赋值所有未提交信息!",{title:"注意！"},function(index){
			select_commonWin("${ctx}/xzgl/goPlfuzhiPage?dwbh=nonono&rybh="+rybh+"&shzt="+shzt+"&gzyf="+gzyf+"&xm11="+xm+"&bm11="+bm,"批量赋值","400","450");
			close(index);
		});
// 		}else{
// 			alert("只能选择未提交的信息进行赋值！");
// 			return false;
// 		}
		
	}
// 	select_commonWin("${ctx}/window/dwpage?controlId=text_bm","部门信息","920","630");
});
$("#text_rybh").change(function() {
	   		var json = searchJson("searchBox");
	    	$('#mydatatables').DataTable().search(json,"json").draw();
	    	var rybh = $("#text_rybh").val();
	    	rybhs = rybh.join(",")
});
//审核状态
$("#shzt").change(function(){
	if($("#shzt").val()=='0' || $("#shzt").val()=='2'){
		$(".btn").show();  
	}else if($("#shzt").val()=='1'){
		$(".btn").hide();
		$("#btn_rybh").show();
		$("#btn_bm").show();
		$("#btn_lxz").show();  
		$("#btn_lxz").show(); 
		$("#btn_imp").show();
		$("#btn_exp").show();
		$("#btn_search1").show();
	}else if($("#shzt").val()=='3'){
		$(".btn").show(); 
		$("#btn_fzsygz").hide(); 
	}else{
		$(".btn").show(); 
		$("#btn_hs").hide();
		$("#btn_tianjia").hide();
	}
	var rybh = $("#text_rybh").val();
	var shzt = $("#shzt").val();
	var gzyf = $("#gzyf").val();
	var xm = $("#text_ryxm").val();
	var bm = $("#text_bm").val();
	table.ajax.url("${ctx}/xzgl/getPageList?rybh="+rybh+"&shzt="+shzt+"&gzyf="+gzyf+"&xm11="+xm+"&bm11="+bm);
	table.ajax.reload();
});
//删除
$("#btn_shanchu").click(function(){
	var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
	if(checkbox.length>0){
	   		var guid = [];
	   		flag=true;
	   		checkbox.each(function(){
	   			guid.push($(this).val());
		   		 if($(this).attr("shzt") != "未提交"){
		   			 flag=false;
		   		 }
	   		});
	   		if(flag){
	   	       confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/xzgl/doDelete",
	   	   			data:"guid="+guid.join(","),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("操作成功！");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
	   		}else{
	   			alert("请选择未提交数据删除！");
	   		}
		}else{
			alert("请选择一条信息进行删除！");
		}
});
//人员弹窗 查找上级方法
	function dsqtr(rybh,xl,dwbh){
		var gzyf=$("#gzyf").val();
		$.ajax({
   			url:"${ctx}/xzgl/zzryCheck",//验证是否为在职人员
   			data:"rybh="+rybh,
   			type:"post",
   			async:"false",
   			success:function(val){
   				if(val=="true"){
   					$.ajax({
   			   			url:"${ctx}/xzgl/addryxx",
   			   			data:"rybh="+rybh+"&gzyf="+gzyf+"&xl="+xl+"&dwbh="+dwbh,
   			   			type:"post",
   			   			async:"false",
   			   			success:function(val){
   			   				if(val==1){
   			   				alert("添加成功！");
   			   				table.ajax.reload();
   			   				}else{
   			   					alert("添加失败,请联系管理员!");
   			   				}
   			   			}
   		   		});
   				}else{
   					alert("该人员不是在职人员，请重新选择!");
   				}
   			}
		});
	}
function selectCol(){
	var cols = $("#pzcxs").val();//隐藏域的值
	var trs = $("[flag^=tr_]");
	if(cols==""){//隐藏域的值为空
		$("[flag^=tr_]").removeClass("yc");//都能显示
		return;
	}
	$.each(trs,function(i,v){
		var cs = $(this).attr("flag");
		if(cols.indexOf(cs)<0){//不在里面
			$(this).addClass("yc");//隐藏
			$(this).parent("td").addClass("yc");//隐藏
		}else{
// 			$(this).removeClass("yc");//显示
			$(this).parent("td").removeClass("yc");//显示
		}
	});
}
 $(function () {
	 	var columns = [
	 	      		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	 	      	       	return '<input type="hidden" name="gid" value="' + full.GUID + '" ><input type="checkbox" class="keyId" name="guid" shzt="'+full.SHZT+'" value="' + full.GUID + '">';
// 	 	      	       	return '<input type="checkbox" class="keyId" name="gid" shzt="'+full.SHZT+'" value="' + full.GUID + '"> <input type="hidden" class="keyId" name="guid" value="' + full.GUID + '">';
	 	      	    },"width":10,'searchable': false},
	 	      		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	 	      	   		return data;
	 	      		},"width":41,"searchable": false,"class":"text-center"},
	 	      		{"data": "SHZT","zhpxname":"审核状态",defaultContent:"",'render': function(data, type, full, meta){
	 	       		return '<input type="type" flag="tr_shzt" class="tr_shzt" name="shzt" style="width:100%;border:none;" readonly value = "'+full.SHZT+'">';
	 	        	}},
	 	      		{"data": "RYBH","zhpxname":"人员编号",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a =full.RYBH;
	 	      			if(full.RYBH==null || full.RYBH=='' || full.RYBH=='undefined'){
		 	      			a='';
		 	       		}
	 	      			return '<input type="type" flag="tr_rybh" class="tr_rybh" name="rybh" style="width:100%;border:none;" readonly value = "'+a+'">';
	 	        	}},
	 	      		{"data": "XM","zhpxname":"姓名",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a =full.XM;
		 	        	if(full.XM==null || full.XM=='' || full.XM=='undefined'){
		 	      			a='';
		 	       		}
	 	       		return '<input type="type" flag="tr_xm" class="tr_xm" name="xm" style="width:100%;border:none;" readonly value = "'+a+'">';
	 	      		}},
	 	        	{"data": "XL","zhpxname":"学历",defaultContent:"",'render': function(data, type, full, meta){
		 	      		var a =full.XL;
		 	        	if(full.XL==null || full.XL=='' || full.XL=='undefined'){
		 	      			a='';
		 	       		}
		 	       		return '<input type="type" flag="tr_xl" class="tr_xl" name="xl" style="width:100%;border:none;" readonly value = "'+a+'">';
		 	        }},
		 	       {"data": "BM","zhpxname":"部门",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a =full.BM;
	 	      			if(full.BM==null || full.BM=='' || full.BM=='undefined'){
		 	      			a='';
		 	       		}
	 	      			return '<input type="type" flag="tr_bm" class="tr_bm" name="bm" style="width:100%;border:none;" readonly readonly value = "'+a+'">';
	 	        	}},
	 	      		{"data": "RYLB","zhpxname":"人员级别",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.RYLB;
	 	      			if(full.RYLB==null||full.RYLB==""||full.RYLB=="undefined"){
	 	      				a='';
	 	      			}
	 	      			return '<input type="type" flag="tr_rylb" class="tr_rylb" name="rylb" readonly style="width:100%;border:none;"  value = "'+a+'">';
	 	        	}},
	 	      		{"data": "RYLX","zhpxname":"人员类型",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.RYLX;
	 	      			if(full.RYLX==null||full.RYLX==""||full.RYLX=="undefined"){
	 	      				a='';
	 	      			}
	 	      			return '<input type="type" flag="tr_rylx" class="tr_rylx" readonly name="rylx" style="width:100%;border:none;"  value = "'+a+'">';
	 	        	}},
	 	        	
	 	      		{"data": "GWGZ","zhpxname":"岗位工资",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.GWGZ;
	 	      			if(full.GWGZ==null||full.GWGZ==""||full.GWGZ=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      				return '<input type="type" flag="tr_xjgz" class="tr_xjgz" name="gwgz" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	      				return '<input type="type" flag="tr_gwgz" class="tr_gwgz" name="gwgz" style="width:100%;border:none;text-align:right;" readonly   value = "'+a+'">';
	 	       			}else{
	 	       			return '<input type="type" flag="tr_xjgz" class="tr_xjgz" name="gwgz" style="width:100%;border:none;text-align:right;" readonly value = "'+a+'">';
	 	      				return '<input type="type" flag="tr_gwgz" class="tr_gwgz" name="gwgz" style="width:100%;border:none;text-align:right;" readonly   value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "XJGZ","zhpxname":"薪级工资",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.XJGZ;
	 	      			if(full.XJGZ==null||full.XJGZ==""||full.XJGZ=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      				return '<input type="type" flag="tr_xjgz" class="tr_xjgz" name="xjgz" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	       			return '<input type="type" flag="tr_xjgz" class="tr_xjgz" name="xjgz" style="width:100%;border:none;text-align:right;" readonly value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "XZFT","zhpxname":"新住房贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.XZFT;
	 	      			if(full.XZFT==null||full.XZFT==""||full.XZFT=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      				return '<input type="type" flag="tr_xzft" class="tr_xzft" name="xzft" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	       			    return '<input type="type" flag="tr_xzft"  readonly class="tr_xzft" name="xzft" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "WYBT","zhpxname":"物业补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.WYBT;
	 	      			if(full.WYBT==null||full.WYBT==""||full.WYBT=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      				return '<input type="type" flag="tr_wybt" class="tr_wybt" name="wybt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	       			return '<input type="type" flag="tr_wybt" readonly class="tr_wybt" name="wybt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "DSZF","zhpxname":"独生子费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.DSZF;
	 	      			if(full.DSZF==null||full.DSZF==""||full.DSZF=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      				return '<input type="type" flag="tr_dszf" class="tr_dszf" name="dszf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	       			return '<input type="type" flag="tr_dszf" readonly class="tr_dszf" name="dszf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "JCJX","zhpxname":"基础绩效",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.JCJX;
	 	      			if(full.JCJX==null||full.JCJX==""||full.JCJX=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      				return '<input type="type" flag="tr_jcjx" class="tr_jcjx" name="jcjx" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	       			return '<input type="type" flag="tr_jcjx" readonly class="tr_jcjx" name="jcjx" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "JLJX1","zhpxname":"奖励绩效",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.JLJX1;
	 	      			if(full.JLJX1==null||full.JLJX1==""||full.JLJX1=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
		 	      			return '<input type="type" flag="tr_jljx1" class="tr_jljx1" name="jljx1" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
		 	      			return '<input type="type" flag="tr_jljx1" readonly class="tr_jljx1" name="jljx1" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "BSBT","zhpxname":"博士补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.BSBT;
	 	      			if(full.BSBT==null||full.BSBT==""||full.BSBT=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			  return '<input type="type" flag="tr_bsbt" class="tr_bsbt" name="bsbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			  return '<input type="type" flag="tr_bsbt" readonly class="tr_bsbt" name="bsbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "GWBT","zhpxname":"岗位补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.GWBT;
	 	      			if(full.GWBT==null||full.GWBT==""||full.GWBT=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      				return '<input type="type" flag="tr_gwbt" class="tr_gwbt" name="gwbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      				return '<input type="type" flag="tr_gwbt" readonly class="tr_gwbt" name="gwbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "SYBT","zhpxname":"生育补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.SYBT;
	 	      			if(full.SYBT==null||full.SYBT==""||full.SYBT=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      				return '<input type="type" flag="tr_sybt" class="tr_sybt" name="sybt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      				return '<input type="type" flag="tr_sybt" readonly class="tr_sybt" name="sybt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "JHBT","zhpxname":"教护补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.JHBT;
	 	      			if(full.JHBT==null||full.JHBT==""||full.JHBT=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_jhbt" class="tr_jhbt" name="jhbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_jhbt" readonly class="tr_jhbt" name="jhbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "ZJBT","zhpxname":"驻济补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.ZJBT;
	 	      			if(full.ZJBT==null||full.ZJBT==""||full.ZJBT=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_zjbt" class="tr_zjbt" name="zjbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_zjbt" readonly class="tr_zjbt" name="zjbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "HTBT","zhpxname":"合同补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.HTBT;
	 	      			if(full.HTBT==null||full.HTBT==""||full.HTBT=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_htbt" class="tr_htbt" name="htbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_htbt" readonly class="tr_htbt" name="htbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "QTBT","zhpxname":"其他补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.QTBT;
	 	      			if(full.QTBT==null||full.QTBT==""||full.QTBT=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_qtbt" class="tr_qtbt" name="qtbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_qtbt" readonly class="tr_qtbt" name="qtbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "BGZ","zhpxname":"补工资",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.BGZ;
	 	      			if(full.BGZ==null||full.BGZ==""||full.BGZ=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bgz" class="tr_bgz" name="bgz" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_bgz" readonly class="tr_bgz" name="bgz" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "JKF","zhpxname":"监考费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.JKF;
	 	      			if(full.JKF==null||full.JKF==""||full.BGZ=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_jkf" class="tr_jkf" name="jkf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_jkf" readonly class="tr_jkf" name="jkf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "FZGZL","zhpxname":"辅助工作量",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.FZGZL;
	 	      			if(full.FZGZL==null||full.FZGZL==""||full.FZGZL=="undefined"){
	 	      				a="";
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_fzgzl" class="tr_fzgzl" name="fzgzl" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_fzgzl" readonly class="tr_fzgzl" name="fzgzl" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "ZSJL","zhpxname":"招生奖励",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.ZSJL;
	 	      			if(full.ZSJL==null||full.ZSJL==""||full.ZSJL=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_zsjl" class="tr_zsjl" name="zsjl" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_zsjl" readonly class="tr_zsjl" name="zsjl" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "FDYYJZBBT","zhpxname":"辅导员夜间值班补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.FDYYJZBBT;
	 	      			if(full.FDYYJZBBT==null||full.FDYYJZBBT==""||full.FDYYJZBBT=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_fdyyjzbbt" class="tr_fdyyjzbbt" name="fdyyjzbbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_fdyyjzbbt" readonly class="tr_fdyyjzbbt" name="fdyyjzbbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "KHJ","zhpxname":"考核奖",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.KHJ;
	 	      			if(full.KHJ==null||full.KHJ==""||full.KHJ=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_khj" class="tr_khj" name="khj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_khj" readonly class="tr_khj" name="khj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "DHF","zhpxname":"电话费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.DHF;
	 	      			if(full.DHF==null||full.DHF==""||full.DHF=="undefined"){
	 	      				a=0;
	 	      			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_dhf" class="tr_dhf" name="dhf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      			return '<input type="type" flag="tr_dhf" readonly class="tr_dhf" name="dhf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	        	{"data": "BT","zhpxname":"补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	        		var a=full.BT;
	 	      			if(full.BT==null||full.BT==""||full.BT=="undefined"){
	 	      				a=0;
	 	      			}
	 	        		if(full.SHZT=='未提交'){
	 	        		return '<input type="type" flag="tr_bt" class="tr_bt" name="bt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	        		return '<input type="type" flag="tr_bt" readonly class="tr_bt" name="bt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "ZFBT","zhpxname":"租房补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.ZFBT;
	 	      			if(full.ZFBT==null||full.ZFBT==""||full.ZFBT=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_zfbt" class="tr_zfbt" name="zfbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	      			return '<input type="type" flag="tr_zfbt" readonly class="tr_zfbt" name="zfbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "YFHJ","zhpxname":"应发合计",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.YFHJ;
	 	      			if(full.YFHJ==null||full.YFHJ==""||full.YFHJ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_yfhj" class="tr_yfhj" name="yfhj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	      			return '<input type="type" flag="tr_yfhj" readonly class="tr_yfhj" name="yfhj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "JIANGKEF","zhpxname":"讲课费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.JIANGKEF;
	 	      			if(full.JIANGKEF==null||full.JIANGKEF==""||full.JIANGKEF=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_jiangkef" class="tr_jiangkef" name="jiangkef" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	      			return '<input type="type" flag="tr_jiangkef" readonly class="tr_jiangkef" name="jiangkef" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "BGZKS","zhpxname":"补工资扣税",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.BGZKS;
	 	      			if(full.BGZKS==null||full.BGZKS==""||full.BGZKS=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bgzks" class="tr_bgzks" name="bgzks" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	      			return '<input type="type" flag="tr_bgzks" readonly class="tr_bgzks" name="bgzks" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "JSX","zhpxname":"计税项",defaultContent:"",'render': function(data, type, full, meta){
	 	      			
	 	      			if(full.SHZT=='未提交'){
							if(full.JSX==''||full.JSX=='undefined'||full.JSX==null){
		 	      				return '<input type="type" flag="tr_jsx" class="tr_jsx" name="jsx" style="width:100%;border:none;text-align:right;"  value = "0.00">';
							}else{
		 	      				return '<input type="type" flag="tr_jsx" class="tr_jsx" name="jsx" style="width:100%;border:none;text-align:right;"  value = "'+full.JSX+'">';
							}
		 	       		}else{
			 	       		if(full.JSX==''||full.JSX=='undefined'||full.JSX==null){
			 	       			return '<input type="type" flag="tr_jsx" readonly class="tr_jsx" name="jsx" style="width:100%;border:none;text-align:right;"  value = "0.00">';
							}else{
								return '<input type="type" flag="tr_jsx" readonly class="tr_jsx" name="jsx" style="width:100%;border:none;text-align:right;"  value = "'+full.JSX+'">';
							}
		 	       		}
	 	        	}},
	 	      		{"data": "QNJSX","zhpxname":"全年计税项",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.QNJSX;
	 	      			if(full.QNJSX==null||full.QNJSX==""||full.QNJSX=="undefined"){
	 	      				a='';
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_qnjsx" class="tr_qnjsx" name="qnjsx" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	      			return '<input type="type" flag="tr_qnjsx" readonly class="tr_qnjsx" name="qnjsx" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "QNJSX1","zhpxname":"全年计税项1",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.QNJSX1;
	 	      			if(full.QNJSX1==null||full.QNJSX1==""||full.QNJSX1=="undefined"){
	 	      				a='';
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_qnjsx1" class="tr_qnjsx1" name="qnjsx1" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	      			return '<input type="type" flag="tr_qnjsx1" readonly class="tr_qnjsx1" name="qnjsx1" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "QNJSX2","zhpxname":"全年计税项2",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.QNJSX2;
	 	      			if(full.QNJSX2==null||full.QNJSX2==""||full.QNJSX2=="undefined"){
	 	      				a='';
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_qnjsx2" class="tr_qnjsx2" name="qnjsx2" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	      			return '<input type="type" flag="tr_qnjsx2" readonly class="tr_qnjsx2" name="qnjsx2" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "QNJSX3","zhpxname":"全年计税项3",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.QNJSX3;
	 	      			if(full.QNJSX3==null||full.QNJSX3==""||full.QNJSX3=="undefined"){
	 	      				a='';
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_qnjsx3" class="tr_qnjsx3" name="qnjsx3" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	      			return '<input type="type" flag="tr_qnjsx3" readonly class="tr_qnjsx3" name="qnjsx3" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "BJCXJXGZ2014JSJS","zhpxname":"补基础性绩效工资2014计税基数",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.BJCXJXGZ2014JSJS;
	 	      			if(full.BJCXJXGZ2014JSJS==null||full.BJCXJXGZ2014JSJS==""||full.BJCXJXGZ2014JSJS=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bjcxjxgz2014jsjs" class="tr_bjcxjxgz2014jsjs" name="bjcxjxgz2014jsjs" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	      			return '<input type="type" flag="tr_bjcxjxgz2014jsjs" readonly class="tr_bjcxjxgz2014jsjs" name="bjcxjxgz2014jsjs" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "BXBT2014N1D10YJSJS","zhpxname":"北校补贴2014年1到10月计税基数",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.BXBT2014N1D10YJSJS;
	 	      			if(full.BXBT2014N1D10YJSJS==null||full.BXBT2014N1D10YJSJS==""||full.BXBT2014N1D10YJSJS=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bxbt2014n1d10yjsjs" class="tr_bxbt2014n1d10yjsjs" name="bxbt2014n1d10yjsjs" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}else{
	 	      			return '<input type="type" flag="tr_bxbt2014n1d10yjsjs" readonly class="tr_bxbt2014n1d10yjsjs" name="bxbt2014n1d10yjsjs" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
		 	       			}
	 	        	}},
	 	      		{"data": "JSJS","zhpxname":"计税基数",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.JSJS;
	 	      			if(full.JSJS==null||full.JSJS==""||full.JSJS=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_jsjs" class="tr_jsjs" name="jsjs" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_jsjs" readonly class="tr_jsjs" name="jsjs" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "ZFJJ","zhpxname":"住房积金",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.ZFJJ;
	 	      			if(full.ZFJJ==null||full.ZFJJ==""||full.ZFJJ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_zfjj" class="tr_zfjj" name="zfjj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_zfjj" readonly class="tr_zfjj" name="zfjj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "PGJJ","zhpxname":"聘公积金",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.PGJJ;
	 	      			if(full.PGJJ==null||full.PGJJ==""||full.PGJJ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_pgjj" class="tr_pgjj" name="pgjj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_pgjj" readonly class="tr_pgjj" name="pgjj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "YLBX","zhpxname":"医疗保险",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.YLBX;
	 	      			if(full.YLBX==null||full.YLBX==""||full.YLBX=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_ylbx" class="tr_ylbx" name="ylbx" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_ylbx" readonly class="tr_ylbx" name="ylbx" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "BGJJ","zhpxname":"补公积金",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.BGJJ;
	 	      			if(full.BGJJ==null||full.BGJJ==""||full.BGJJ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bgjj" class="tr_bgjj" name="bgjj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_bgjj" readonly class="tr_bgjj" name="bgjj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "DKS","zhpxname":"代扣税",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.DKS;
	 	      			if(full.DKS==null||full.DKS==""||full.DKS=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_dks" class="tr_dks" name="dks" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_dks" readonly class="tr_dks" name="dks" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	        	{"data": "BXQZBBT","zhpxname":"北校区值班补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	        		var a=full.BXQZBBT;
	 	      			if(full.BXQZBBT==null||full.BXQZBBT==""||full.BXQZBBT=="undefined"){
	 	      				a=0;
	 	      			};
	 	        		if(full.SHZT=='未提交'){
	 	      				return '<input type="type" flag="tr_bxqzbbt" class="tr_bxqzbbt" name="bxqzbbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}else{
	 	      				return '<input type="type" flag="tr_bxqzbbt" readonly class="tr_bxqzbbt" name="bxqzbbt" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "BNSE","zhpxname":"本年税额",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.BNSE;
	 	      			if(full.BNSE==null||full.BNSE==""||full.BNSE=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bnse" class="tr_bnse" name="bnse" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_bnse" readonly class="tr_bnse" name="bnse" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "SNSE","zhpxname":"上年税额",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.SNSE;
	 	      			if(full.SNSE==null||full.SNSE==""||full.SNSE=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_snse" class="tr_snse" name="snse" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_snse" readonly class="tr_snse" name="snse" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "BS","zhpxname":"补税",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.BS;
	 	      			if(full.BS==null||full.BS==""||full.BS=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bs" class="tr_bs" name="bs" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_bs" readonly class="tr_bs" name="bs" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "FZ","zhpxname":"房租",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.FZ;
	 	      			if(full.FZ==null||full.FZ==""||full.FZ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_fz" class="tr_fz" name="fz" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_fz" readonly class="tr_fz" name="fz" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "SYJ","zhpxname":"失业金",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.SYJ;
	 	      			if(full.SYJ==null||full.SYJ==""||full.SYJ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_syj" class="tr_syj" name="syj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_syj" readonly class="tr_syj" name="syj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "NQF","zhpxname":"暖气费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.NQF;
	 	      			if(full.NQF==null||full.NQF==""||full.NQF=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_nqf" class="tr_nqf" name="nqf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_nqf" readonly class="tr_nqf" name="nqf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "NQF1","zhpxname":"暖气费1",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.NQF1;
	 	      			if(full.NQF1==null||full.NQF1==""||full.NQF1=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_nqf1" class="tr_nqf1" name="nqf1" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_nqf1" readonly class="tr_nqf1" name="nqf1" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "WYF","zhpxname":"物业费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.WYF;
	 	      			if(full.WYF==null||full.WYF==""||full.WYF=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_wyf" class="tr_wyf" name="wyf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_wyf" readonly class="tr_wyf" name="wyf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "SBJ","zhpxname":"社保金",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.SBJ;
	 	      			if(full.SBJ==null||full.SBJ==""||full.SBJ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_sbj" class="tr_sbj" name="sbj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}else{
	 	      			return '<input type="type" flag="tr_sbj" readonly class="tr_sbj" name="sbj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
			 	       			}
	 	        	}},
	 	      		{"data": "SJDCK","zhpxname":"四季度菜款",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.SJDCK;
	 	      			if(full.SJDCK==null||full.SJDCK==""||full.SJDCK=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_sjdck" class="tr_sjdck" name="sjdck" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}else{
	 	      			return '<input type="type" flag="tr_sjdck" readonly class="tr_sjdck" name="sjdck" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}
	 	        	}},
	 	      		{"data": "KK","zhpxname":"扣款",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.KK;
	 	      			if(full.KK==null||full.KK==""||full.KK=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_kk" class="tr_kk" name="kk" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}else{
	 	      			return '<input type="type" flag="tr_kk" readonly class="tr_kk" name="kk" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}
	 	        	}},
	 	      		{"data": "YLJ","zhpxname":"养老金",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.YLJ;
	 	      			if(full.YLJ==null||full.YLJ==""||full.YLJ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_ylj" class="tr_ylj" name="ylj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}else{
	 	      			return '<input type="type" flag="tr_ylj" readonly class="tr_ylj" name="ylj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}
	 	        	}},
	 	      		{"data": "JK","zhpxname":"借款",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.JK;
	 	      			if(full.JK==null||full.JK==""||full.JK=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_jk" class="tr_jk" name="jk" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}else{
	 	      			return '<input type="type" flag="tr_jk" readonly class="tr_jk" name="jk" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}
	 	        	}},
	 	      		{"data": "AXYRJ","zhpxname":"爱心一日捐",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.AXYRJ;
	 	      			if(full.AXYRJ==null||full.AXYRJ==""||full.AXYRJ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_axyrj" class="tr_axyrj" name="axyrj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}else{
	 	      			return '<input type="type" flag="tr_axyrj" readonly class="tr_axyrj" name="axyrj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}
	 	        	}},
	 	      		{"data": "KKHJ","zhpxname":"扣款合计",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.KKHJ;
	 	      			if(full.KKHJ==null||full.KKHJ==""||full.KKHJ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_kkhj" class="tr_kkhj" name="kkhj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}else{
	 	      			return '<input type="type" flag="tr_kkhj" readonly class="tr_kkhj" name="kkhj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}
	 	        	}},
	 	      		{"data": "SFHJ","zhpxname":"实发合计",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.SFHJ;
	 	      			if(full.SFHJ==null||full.SFHJ==""||full.SFHJ=="undefined"){
	 	      				a=0;
	 	      			};
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_sfhj" class="tr_sfhj" name="sfhj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}else{
	 	      			return '<input type="type" flag="tr_sfhj" readonly class="tr_sfhj" name="sfhj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
				 	       			}
	 	        	}},
	 	      		{"data": "GZYF","zhpxname":"工资月份",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.GZYF;
	 	      			if(full.GZYF==null||full.GZYF==""||full.GZYF=="undefined"){
	 	      				a='';
	 	      			}
	 	      			return '<input type="type" flag="tr_gzyf" class="tr_gzyf" name="gzyf" style="width:100%;border:none;" readonly value = "'+a+'">';
	 	        	}},
	 	      		{"data": "BH","zhpxname":"编号",defaultContent:"",'render': function(data, type, full, meta){
	 	       			var a = full.BH;
	 	      			if(full.BH==null || full.BH=='' || full.BH=='undefined'){
	 	       				a='';
	 	       			}
	 	       			return '<input type="type" flag="tr_bh" class="tr_bh" name="bh" style="width:100%;border:none;"  value = "'+a+'">';
	 	        	}},
	 	      		{"data": "XH","zhpxname":"序号",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a= full.XH;
	 	      			if(full.XH==null || full.XH=='' || full.XH=='undefined'){
	 	      				a='';
	 	       			}
	 	       			return '<input type="type" flag="tr_xh" class="tr_xh" name="xh" style="width:100%;border:none;" readonly value = "'+a+'">';
	 	        	}},
	 	      		{"data": "JTF","zhpxname":"交通费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a= full.JTF;
	 	      			if(full.JTF==null || full.JTF=='' || full.JTF=='undefined'){
	 	      				a=0;
	 	       			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_jtf" class="tr_jtf" name="jtf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
					 	       			}else{
	 	      			return '<input type="type" flag="tr_jtf" readonly class="tr_jtf" name="jtf" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
					 	       			}
	 	        	}},
	 	      		{"data": "NZJ","zhpxname":"年终奖",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a= full.NZJ;
	 	      			if(full.NZJ==null || full.NZJ=='' || full.NZJ=='undefined'){
	 	      				a=0;
	 	       			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_nzj" class="tr_nzj" name="nzj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
					 	       			}else{
	 	      			return '<input type="type" flag="tr_nzj" readonly class="tr_nzj" name="nzj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
					 	       			}
	 	        	}},
	 	      		{"data": "NZJDKS","zhpxname":"年终奖代扣税",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a= full.NZJDKS;
	 	      			if(full.NZJDKS==null || full.NZJDKS=='' || full.NZJDKS=='undefined'){
	 	      				a=0;
	 	       			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_nzjdks" class="tr_nzjdks" name="nzjdks" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
					 	       			}else{
	 	      			return '<input type="type" flag="tr_nzjdks" readonly class="tr_nzjdks" name="nzjdks" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
					 	       			}
	 	        	}},
	 	      		{"data": "GZDKS","zhpxname":"工资代扣税",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a= full.GZDKS;
	 	      			if(full.GZDKS==null || full.GZDKS=='' || full.GZDKS=='undefined'){
	 	      				a=0;
	 	       			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_gzdks" class="tr_gzdks" name="gzdks" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
					 	       			}else{
	 	      			return '<input type="type" flag="tr_gzdks" readonly class="tr_gzdks" name="gzdks" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
					 	       			}
	 	        	}},
	 	      		{"data": "KSHJ","zhpxname":"扣税合计",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a= full.KSHJ;
	 	      			if(full.KSHJ==null || full.KSHJ=='' || full.KSHJ=='undefined'){
	 	      				a=0;
	 	       			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_kshj" class="tr_kshj" name="kshj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
					 	       			}else{
	 	      			return '<input type="type" flag="tr_kshj" readonly class="tr_kshj" name="kshj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
					 	       			}
	 	        	}},
	 	      		{"data": "GH","zhpxname":"工号",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.GH;
	 	      			if(full.GH==null||full.GH==""||full.GH=="undefined"){
	 	      				a='';
	 	      			}
	 	      			return '<input type="type" flag="tr_gh" class="tr_gh" name="gh" style="width:100%;border:none;" readonly value = "'+a+'">';
	 	        	}},
	 	      		{"data": "SFZB","zhpxname":"是否在编",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a=full.SFZB;
	 	      			if(full.SFZB==null || full.SFZB=='' || full.SFZB=='undefined'){
	 	      				a='';
	 	       			}
	 	       			return '<input type="type" flag="tr_sfzb" class="tr_sfzb" name="sfzb" style="width:100%;border:none;"  value = "'+a+'">';
	 	        	}},
	 	      		{"data": "BKYLBX","zhpxname":"补扣医疗保险",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a= full.BKYLBX;
	 	      			if(full.BKYLBX==null || full.BKYLBX=='' || full.BKYLBX=='undefined'){
	 	      				a=0;
	 	       			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bkylbx" class="tr_bkylbx" name="bkylbx" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
						 	       			}else{
	 	      			return '<input type="type" flag="tr_bkylbx" readonly class="tr_bkylbx" name="bkylbx" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
						 	       			}
	 	        	}},
	 	      		{"data": "BKSYJ","zhpxname":"补扣失业金",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a= full.BKSYJ;
	 	      			if(full.BKSYJ==null || full.BKSYJ=='' || full.BKSYJ=='undefined'){
	 	      				a=0;
	 	       			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bksyj" class="tr_bksyj" name="bksyj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
						 	       			}else{
	 	      			return '<input type="type" flag="tr_bksyj" readonly class="tr_bksyj" name="bksyj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
						 	       			}
	 	        	}},
	 	      		{"data": "BKYLJ","zhpxname":"补扣养老金",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a= full.BKYLJ;
	 	      			if(full.BKYLJ==null || full.BKYLJ=='' || full.BKYLJ=='undefined'){
	 	      				a=0;
	 	       			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bkylj" class="tr_bkylj" name="bkylj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
						 	       			}else{
	 	      			return '<input type="type" flag="tr_bkylj" readonly class="tr_bkylj" name="bkylj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
						 	       			}
	 	        	}},
	 	      		{"data": "BKSBJ","zhpxname":"补扣社保金",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a= full.BKSBJ;
	 	      			if(full.BKSBJ==null || full.BKSBJ=='' || full.BKSBJ=='undefined'){
	 	      				a=0;
	 	       			}
	 	      			if(full.SHZT=='未提交'){
	 	      			return '<input type="type" flag="tr_bksbj" class="tr_bksbj" name="bksbj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
						 	       			}else{
	 	      			return '<input type="type" flag="tr_bksbj" readonly class="tr_bksbj" name="bksbj" style="width:100%;border:none;text-align:right;"  value = "'+a+'">';
						 	       			}
	 	        	}},
	 	      		{"data": "SFDY","zhpxname":"是否党员",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a = full.SFDY;
	 	      			if(full.SFDY==null || full.SFDY=='' || full.SFDY=='undefined'){
	 	      				a='';
	 	       			}
	 	      			return '<input type="type" flag="tr_sfdy" class="tr_sfdy" name="sfdy" style="width:100%;border:none;"  value = "'+a+'">';
	 	        	}},
	 	      		{"data": "RDQK","zhpxname":"入党情况",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a =full.RDQK;
	 	      			if(full.RDQK==null || full.RDQK=='' || full.RDQK=='undefined'){
	 	      				a='';
	 	       			}
	 	      			return '<input type="type" flag="tr_rdqk" class="tr_rdqk" name="rdqk" style="width:100%;border:none;"  value = "'+a+'">';
	 	        	}}
	 	   
	 	      	];
	 	        table = getDataTableByListHj("mydatatables","${ctx}/xzgl/getPageList?rybh="+$("#text_rybh").val()+"&shzt="+$("#shzt").val()+"&gzyf="+$("#gzyf").val(),[3,'asc'],columns,0,1,setGroup);
    
	 	       lxzExp(columns,"#mydatatables","#searchBox",table,"${pageContext.request.contextPath}/xzgl/doExp","管理员建帐","${pageContext.request.contextPath}");
	 //导入数据
     $("#btn_imp").click(function(){
    	 var texts = [];
   		var name = $("table").find("th:gt(1)");
   		$.each(name,function(){
   			var name = $(this).text();
   			texts.push(name);
   		});
          select_commonWin("${pageContext.request.contextPath}/webView/xzgl/xzgl_imp.jsp?texts="+texts.join(","),"导入工资信息","550","450");
           return false;
     });
   //把所有undefined改为空值
	$('input[value=undefined]').val('');
	window.onresize=function(){
		$('input[value=undefined]').val('');
	}

     $("#btn_selcolums").click(function(){
		 select_commonWin("${ctx}/xzgl/col?controlId=pzcxs","列信息","500","700");
	 });

	 //复制上月工资
	 $("#btn_fzsygz").click(function(){
		 var gzyf = $("#gzyf").val();
		if(gzyf=="" || gzyf == null ){
			alert("请选择月份！")
		}else{
			confirm("确定复制？","",function(){
			$.ajax({
				url:"${ctx}/xzgl/checkGzyf",
				data:"gzyf="+gzyf+"&type=zzry",
				dataType:"json",
				type:"post",
				success:function(date){
					if(date.success=='true'){
						$.ajax({
							url:"${ctx}/xzgl/doFzsygz",
							data:"gzyf="+gzyf,
							dataType:"json",
							type:"post",
							success:function(date){
								location.reload();
								alert(date.lzry+"数据复制成功！");
							}
						});
					}else{
						alert("当月数据已存在!");
					}
				}
			});
			});
		}
	 });
	 //核算
	 $("#btn_hs").click(function(){
// 		 var json = $("#mysave").serializeObject("rybh","rdqk");  //json对象				
// 		 var jsonStr = JSON.stringify(json);
			var rybh = $("#text_rybh").val();
	   		var bm = $("#text_bm").val();
	   		var gzyf = $("#gzyf").val();
			confirm("确定核算？","",function(){
				$.ajax({
					url:"${ctx}/xzgl/doHs",
					data:"rybh="+rybh+"&bm="+bm+"&gzyf="+gzyf,
					dataType:"json",
					type:"post",
					success:function(date){
						location.reload();
						alert("核算成功！");
					}
				});
			});
	 });
	    //提交，只有已核算的数据才能提交
	   	$("#btn_tj").click(function(){
	   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
	   		var flag = true;
	   		var rybh = $("#text_rybh").val();
	   		var bm = $("#text_bm").val();
	   		var gzyf = $("#gzyf").val();
   			if(checkbox.length>0){
	   	   		var guid = [];
	   	   		checkbox.each(function(){
	   	   			guid.push($(this).val());
		   	   		 if($(this).attr("shzt") != "已核算"){
		   	   			 flag=false;
		   	   		 }
	   	   		});
	   	   		if(flag){
	   	   	       confirm("确定提交？","",function(){
		   			$.ajax({
		   	   			url:"${ctx}/xzgl/doTj",
		   	   			data:"guid="+guid.join(","),
		   	   			type:"post",
		   	   			async:"false",
		   	   			success:function(val){
		   	   				alert("操作成功！");
		   	   				table.ajax.reload();
		   	   			}
		   	   		});
		   		});
	   	   		}else{
	   	   			alert("存在未核算的数据！");
	   	   		}
	   		}else{
	   			var hszt = $("[name='shzt']");
		   		hszt.each(function(){
		   			if($(this).val()!='已核算'&&$(this).val()!='合计'){
		   				flag=false;
		   			}
		   		})
		   		if(flag){
		   		  	confirm("确定全部提交？","",function(){
			   			$.ajax({
			   	   			url:"${ctx}/xzgl/doTj",
			   	   			data:"gzyf="+gzyf+"&rybh="+rybh+"&bm="+bm,
			   	   			type:"post",
			   	   			async:"false",
			   	   			success:function(val){
			   	   				alert("操作成功！");
			   	   				table.ajax.reload();
			   	   			}
			   	   		});
			   		});
		   		}else{
		   			alert("存在未核算的数据！")
		   		}
   			}
	   });
 		//点击保存按钮
		$("#btn_save").click(function(){
			var end = $("table").last("tr").find("input:last").attr("name");
			if(end==""||end=="undefined"||end==null){
				alert("列选择错误无法保存，请重新选择！");
				return;
			}
			var json = $("#mysave").serializeObject("gid",end);  //json对象				
			var jsonStr = JSON.stringify(json);
			confirm("确定保存？","",function(){
			$.ajax({
				url:"${ctx}/xzgl/doSave",
				data:"jsonStr="+jsonStr,
				dataType:"json",
				async:"false",
				type:"post",
				success:function(msg){
					if($.trim(msg)=="true"){
						table.ajax.reload();
						alert("保存成功!");
					}else{
						alert("保存失败!");
					}
				}
			});
			});
		});
	 
	//导出Excel
	$("#btn_exp").click(function() {
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
  		var ryxm=$("#ryxm").val();
  		var texts = [];
  		var name = $("table").find("th:gt(1)");
  		$.each(name,function(){
  			var name = $(this).text();
  			texts.push(name);
  		});
  		var rybh = $("#text_rybh").val();
  		var shzt = $("#shzt").val();
  		var gzyf = $("#gzyf").val();
		var xm = $("#text_ryxm").val();
		var bm = $("#text_bm").val();
  		doExp("","${ctx}/xzgl/ZzryexpExcel?rybh="+rybh+"&ryxm="+ryxm+"&shzt="+shzt+"&gzyf="+gzyf+"&texts="+texts.join("','")+"&bm="+bm,"在职人员薪资","${pageContext.request.contextPath}",id);
	});
	
	//列表右侧悬浮按钮
	$(window).resize(function(){
		   $("div.dataTables_wrapper").width($("#searchBox").width());
		   heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
		   $(".dataTables_scrollBody").height(heights);
		   table.draw();
	});
	
	});
	//查询按钮
	$(document).on("click","#btn_search1",function(){
		var rybh = $("#text_rybh").val();
		var shzt = $("#shzt").val();
		var gzyf = $("#gzyf").val();
		var xm = $("#text_ryxm").val();
		var bm = $("#text_bm").val();
		table.ajax.url("${ctx}/xzgl/getPageList?rybh="+rybh+"&shzt="+shzt+"&gzyf="+gzyf+"&xm11="+xm+"&bm11="+bm);
		table.ajax.reload();
	});
</script>
</body>
</html>