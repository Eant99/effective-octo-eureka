<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位机构设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
  #mydatatables input{
		border:none;
		width:100%;
	    padding:4px !important;
	}
	
</style>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>分类名称</label>
					<input type="text" id="txt_dwbh" class="form-control input-radius" name="flmcs" table="K" placeholder="请输入分类名称">
				</div>
				<div class="form-group">
					<label>业务相关部门</label>
					<input type="text" id="txt_mc" class="form-control input-radius" name="ywglbmmc" table="K" placeholder="请输入业务相关部门">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button type="button" class="btn btn-default" id="btn_add">增加</button>
	                <button type="button" class="btn btn-default" id="btn_save">保存</button>
	                <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	            </div>
			</div>
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
            <div class='scrollable-area'>
            	<form id="infoform" >
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
				        <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>分类名称</th>
				        <th>业务关联部门</th>
				        <th>备注</th>
				    </tr>
				    </thead>
				    <tbody id="bod">
				    </tbody>
				</table>
				</form>
            </div>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
			if(data=='000001'){
				return '<input type="checkbox" style="width:13px important;height:13px !important;" class="keyId djdw" name="guid" value="' + data + '" flmc="'+full.FLMCS+'">'
				+ '<input type="hidden" name="guid222" value="' + data + '">';
			}else{
				return '<input type="checkbox" style="width:13px important;height:13px !important;" class="keyId" name="guid" value="' + data + '" flmc="'+full.FLMCS+'">'
				+ '<input type="hidden" name="guid222" value="' + data + '">';
			}
	    },"width":10,'searchable': false,"class":"text-center"},
		{"data":"XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":20,"searchable": false,"class":"text-center xh"},//1
		
	   	{"data": "FLMCS",'render':function (data, type, full, meta){
	   		if(""==full.FLMCS||null==full.FLMCS||"undefined"==full.FLMCS){
	   			return '<input class="uid flmc"  guid = "'+full.GUID+'" id="flmc_'+full.XH+'" name="FLMCS" value="" style="width:100%;" />';
	   		}else{
	   			return '<input class="uid flmc"  guid = "'+full.GUID+'" id="flmc_'+full.XH+'" name="FLMCS" value="'+full.FLMCS+'" style="width:100%;" />';
	   		}
		},orderable:false,"width":200},
	   	{"data": "YWGLBMMC",'render':function (data, type, full, meta){
	   		if("undefined"==full.YWGLBMMC||null==full.YWGLBMMC||"undefined"==full.YWGLBMMC){
	   		return '<input class="" guid = "'+full.GUID+'" name="YWGLBMMC" id="ywglbm_'+full.XH+'" readonly value="" style="width:91%;"/> <span> <button type="button" id="btn_fddbrh" class="btn btn-link btn-custom">选择</button></span>';
	   		}else{
   			return '<input class="" guid = "'+full.GUID+'" name="YWGLBMMC" id="ywglbm_'+full.XH+'" readonly value="'+full.YWGLBMMC+'" style="width:91%;"/> <span> <button type="button" id="btn_fddbrh" class="btn btn-link btn-custom">选择</button></span>';
	   		}
		},orderable:false,"width":250},
	   	{"data": "BZ",'render':function (data, type, full, meta){
   			if("undefined"==full.BZ||null==full.BZ||"undefined"==full.BZ){
   			return '<input class="" guid = "'+full.GUID+'" name="bz" value="" style="width:100%;"/>';
	   		}else{
   			return '<input class="" guid = "'+full.GUID+'" name="bz" value="'+full.BZ+'" style="width:100%;"/>';
	   		}
		},orderable:false,"width":250}
	];
	//表数据
   	table = getDataTableByListHj("mydatatables","${ctx}/xmflc/getPageList?treedwbh=${dwbh}",[1,'asc'],columns,0,1,setGroup);
    //保存
    $("#btn_save").click(function(){
 	 	var checkbox = $("#mydatatables").find("[name='guid']");
 	   	var url = "${ctx}/xmflc/doAdd";
		if(checkbox.length>0){
   			var guid = [];
   			checkbox.each(function(){
   				guid.push($(this).val());
   			});
   			url += "?guids="+guid.join("','"); 
		}
 		doSave1("#myform",url,function(val){
 			var result = JSON.getJson(val);
 			if(result.success){
 				alert("保存成功！");
 				window.location = "${ctx}/xmflc/goXmflListPage";
 			}
 		}); 
 	});
   	function doSave1($form, _url, _success, _fail){
//    		var validator = $('#myform').bootstrapValidator(fields);
//    		if(validator){
//    			validator.bootstrapValidator("validate");
//    			var valid = $form.data('bootstrapValidator').isValid();
//    			if(!valid){
//    				return;
//    			}
//    		}
//    		if(checkDjbhExist()){
//    			return;
//    		}
//    		$("#imageFile").fileinput("upload");
// 		var json = $("#infoform").serializeArray();
// 		console.log(json);return;
   		var json = $("#infoform").serializeObject("guid222","bz");
   		var jsonStr = JSON.stringify(json);
   		console
   		var formJson = arrayToJson( $("#infoform").serializeArray());
   		formJson.json = jsonStr;
   		$.ajax({
   			type:"post",
   			url:_url,
   			data:formJson,
   			success:function(data){
   				_success(data);
   			},
   			error:function(val){
   				alert("抱歉，系统出现问题！");
   			},
   		});	
   	}
    //添加按钮
	$("#btn_add").click(function() {
		var index = $(".xh:last").text();
		if (index == null) {
			index = 1; 
		} else {
			index++;
		}
		var trObj = document.createElement("tr");
		trObj.setAttribute("id", index);
		if (index % 2 == 0 || index == 1) {
			trObj.setAttribute("class", "odd");
		} else {
			trObj.setAttribute("class", "even");
		}

		trObj.setAttribute("role", "row");
		trObj.innerHTML = "<td class='text-center'><input type='checkbox' name='guid' value='0'>"
					+ '<input type="hidden" name="guid222"></td>'
				+ "<td class='text-center xh'> <input type='hidden'  class='' id='"+index+"'>"+ index +" </td>"
				+ "<td><input type='text' class='flmc' id='flmc_"+index+"' name='FLMCS' style='width:100%;border:none;' value=''/></td>"
				+ "<td><div class='input-group'><input type='text' name='YWGLBMMC' id='txt_fddbrh_"+index+"' readonly class='form-control input-radius window' name='zckmbh' value=''><span class='input-group-btn'><button type='button' id='btn_fddbrh_"+index+"' flag="+index+" class='btn btn-link btn-custom'>选择</button></span></div></td>"
				+ "<td><input type='textarea' name='bz'  style='width:100%;border:none;' value=''/></td>";

		document.getElementById("bod").appendChild(trObj);
		$("#flmc_"+index).bindChange("${ctx}/suggest/getXx","FLMC");
	});
	//批量删除
   	$("#btn_del").click(function(){
   		var checkbox = $("#infoform").find("[name='guid']").filter(":checked");
		if(checkbox.length>0){
			var dwbh = [];
			var flmc = [];
			checkbox.each(function(){
				dwbh.push($(this).val());
				flmc.push($(this).attr("flmc"));
			});
			doDel("guids="+dwbh.join("','"),"${ctx}/xmflc/doDelete?flmcs="+flmc.join("','"),function(val){
				window.location = "${ctx}/xmflc/goXmflListPage";
		   	},function(val){
		   	},dwbh.length);
		}else{
			alert("请选择至少一条信息删除！");
		}
   	});
   	
    //查询联想输入
// 	$("#flmc_1").bindChange("${ctx}/suggest/getXx","FLMC");
    $("[id^='flmc_']").bindChange("${ctx}/suggest/getXx","FLMC");
	//查询弹窗
	$(document).on("click","[id^='btn_fddbrh']",function(){
		var contorlId = $(this).parent().prev().attr("id");
		select_commonWin("${ctx}/window/dwpage?controlId="+contorlId+"","部门信息","920","630");
	});
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
</html>