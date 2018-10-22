<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目申报</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
  .null{
		background-color: wheat;
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
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="A" placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc"  class="form-control" name="xmmc"  table="A" placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>项目编号</label>
							<div class="input-group">
								<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="A" >
							</div>
						</div>
						<div class="form-group">
							<label>项目名称</label>
							<div class="input-group">
								<input type="text" id="txt_xmmc" class="form-control" name="xmmc" table="A" >
							</div>
						</div>
						<div class="form-group">
							<label>项目类型</label>
							<div class="input-group">
								<input type="text" id="txt_xmlx" class="form-control" name="xmlx" table="A" >
							</div>
						</div>
						<div class="form-group">
							<label>服务专业</label>
							<input type="text" id="txt_fwzy" class="form-control" name="fwzy" table="A" >
						</div>
						<div class="form-group"> 
							<label>服务学科</label>
							<div class="input-group">
								<input type="text" id="txt_fwxk" class="form-control input-radius" name="fwxk"  table="A" >
							</div>
						</div>
						<div class="form-group">
							<label>是否上年度重新论证项目</label>
							<select style="width:70px;" id="txt_sfsndcxlzxm" class="form-control" name="sfsndcxlzxm">
									<option value="">未选择</option>
	                					<option value="1">是</option>
	                					<option value="2">否</option>
								</select>	
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_save">保存</button>
				</div>
			</div>
		</form>
	</div>
	<form id="myform1" action="" method="post">
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>项目排名</th> 
				            <th>调整后排名</th>
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>服务专业</th>
				            <th>服务学科</th>
				            <th>申报年度</th>
				            <th>计划执行时间</th>
				            <th>计划结束时间</th>
				            <th>预算金额(元)</th>
				            <th>是否上年度重新论证项目</th>
				            <th>申报单位</th>
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
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false,"class":"text-center", 'render': function (data, type, full, meta){
         return '<input type="checkbox" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">'+
         '<input type="hidden" name="guid" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "XMPM",defaultContent:"","class":"text-right"}, 
       {"data": "TZHPM",'render': function (data, type, full, meta){
           return '<input type="text" id="txt_tzhpm" name="tzhpm" onkeyup="value=value.replace(/[^0-9]/g,\'\');" class="pmnum number2" style="border:none;text-align:right;width:100%;" placeholder="请输入正整数"  value="'+data+'">';}},
       {"data": "XMBH",defaultContent:""},
       {"data": "XMMC",defaultContent:""},       
       {"data": "FWZY",defaultContent:""},
       {"data": "FWXK",defaultContent:""},
       {"data": "SBND",defaultContent:"","class":"text-center"},
       {"data": "JHZXSJ",defaultContent:"","class":"text-center"},
       {"data": "JHJSSJ",defaultContent:"","class":"text-center"},
       {"data": "YSJE",defaultContent:"","class":"text-right"},
       {"data": "SFSNDCXLZXM",defaultContent:"否",'render': function (data, type, full, meta){
    	   if(data=='1'){
    		   return '是'; 
    	   }else{
    		   return '否';   
    	   }
    	   }},
       {"data": "SBDW",defaultContent:""}
     ];
//     table = getDataTableByListHj("mydatatables","${ctx}/xmpmtz/getPageList",[2,'asc'],columns,0,1,setGroup);
    table = getDataTableByListHj("mydatatables","${ctx}/xmpmtz/getPageList",[0,'asc'],columns,0,1,setGroup);
  	//保存按钮
   $("#btn_save").click(function(){
// 	   var tag = checkNull($("[name='tzhpm']"));
// 		if(tag){
// 			alert("不能为0或空！");
// 			return;
// 		}
// 			var json = $("#myform1").serializeObject1("guid","tzhpm");
// 			var jsonStr = JSON.stringify(json);
// 			console.log(jsonStr);
// 			$.ajax({
// 				type:"post",
// 				url:ADDRESS+"/xmpmtz/update",
// 				data:"json="+jsonStr,
// 				success:function(result){
// 					alert("保存成功");
// 					location.reload();
// 				}
			   
// 			})
       alert("保存成功！");
		});
  	
   $.fn.serializeObject1 = function(start,end){
	  
		var f = {"list":[]};
	    var a = $("#myform1").serializeArray();
	    var o = {};
	    $.each(a, function() {
	    	
	    	if (this.name == start) {
	        	o = {};
	        	o[this.name] = this.value;
	        	
	        } else if(this.name == end){
	        	o[this.name] = this.value;
	    		f["list"].push(o);
	        	
	        }else{
	        	o[this.name] = this.value;
	        }
	    });
	    return f;
	};
 //检查是否为空
  	function checkNull(items){
  		var tag = false;
  		$.each(items,function(){
  			var val = $(this).val();
  			if(val == "" || val == "0"){
  				tag = true;
  				$(this).addClass("null");
  			}
  		});
  		return tag;
  	}
  	$(document).on("focus","input",function(){
  		$(this).removeClass("null");
  	})
});
//
$(document).on("blur", ".number2", function(e){
	$(this).Num(0,true,false,e);
	return false;
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
// $(document).on("keyup",["class*=pmnum"],function(){
// 	var value = $(this).val();
// 	console.log($(this).attr("name"));
// 	var num = /^[1-9]\d*$/;
// 	if(!num.test(value)){
// 		$(this).val("");
// 		return;
// 	}
// });
function checkNum(value,$this){
	var num = /^[1-9]\d*$/;
	console.log(!num.test(value)+"===="+value);
	if(!num.test(value)){
		value = "";
		return;
	}
	$this.val(value);
}
</script>
</body>
</html>