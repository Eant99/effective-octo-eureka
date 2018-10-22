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
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc"  table="A" placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>项目类型</label>
							<div class="input-group">
								<input type="text" id="txt_xmlx" class="form-control" name="xmlx" table="A" placeholder="请输入项目类型">
							</div>
						</div>
						<div class="form-group">
							<label>服务专业</label>
							<input type="text" id="txt_fwzy" class="form-control" name="fwzy" table="A" placeholder="服务专业">
						</div>
						<div class="form-group"> 
							<label>服务学科</label>
							<div class="input-group">
								<input type="text" id="txt_fwxk" class="form-control input-radius" name="fwxk"  table="A" placeholder="服务学科">
							</div>
						</div>
						<div class="form-group">
							<label>是否上年度重新论证项目</label>
							<select style="width:70px;" id="txt_sfsndcxlzxm" class="form-control" name="sfsndcxlzxm">
									<option value="">未选择</option>
                					<option value="是">是</option>
                					<option value="否">否</option>
							</select>	
						</div>
						<div class="form-group">
							<label>申报单位</label>
							<input type="text" id="txt_sbdw" class="form-control" name="sbdw" table="A" placeholder="申报单位" value="" >
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
	<form id="tableform" action="" method="post">
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>项目排名</th> 
				            <th>调整后排名</th>
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>服务专业</th>
				            <th>服务学科</th>
				            <th>申报日期</th>
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
var basePath = "${ctx}/xmgl/xmsp";
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false,"class":"text-center", 'render': function (data, type, full, meta){
         return '<input type="checkbox" class="keyId"/>'+
         '<input type="hidden" name="guid" value="'+data+'"/>';
       },"width":10,'searchable': false},
       {"data":"_XH",width:40,"class":"text-center"},
       {"data": "XMPM",defaultContent:"","class":"text-right"}, 
       {"data": "TZHPM",'render': function (data, type, full, meta){
    	   if(full.TZHPM==undefined){
           return '<input type="text"  name="tzhpm" class="int" style="border:none;text-align:right;width:100%;" placeholder="请输入正整数" />';
    	   }else{
    		   return '<input type="text" value="'+data+'" name="tzhpm" class="int" style="border:none;text-align:right;width:100%;" />';
    	   }
    	   
       }},
       {"data": "XMBH",defaultContent:""},
       {"data": "XMMC",defaultContent:""},       
       {"data": "FWZY",defaultContent:""},
       {"data": "FWXK",defaultContent:""},
       {"data": "SBRQ",defaultContent:"","class":"text-center"},
       {"data": "JHZXSJ",defaultContent:"","class":"text-center"},
       {"data": "JHJSSJ",defaultContent:"","class":"text-center"},
       {"data": "YSJE",defaultContent:"","class":"text-right"},
       {"data": "SFSNDCXLZXM",defaultContent:"","class":"text-center"},
       {"data": "SBDW",defaultContent:""}
     ];
    table = getDataTableByListHj("mydatatables",basePath+"/getListPageData?splx=${param.splx}",[0,'asc'],columns,0,1,setGroup);
});
//检查输入的调整后排名（不能为空），如果存在空信息，返回false，否则返回true
function checkTzhpm(){
	var nullMsg = {"size":0,"msg":{}};
	var msg = nullMsg.msg;
	$.each($("[name='tzhpm']"),function(i){
		var val = $(this).val();
		var xh = i+1;
		if(isNull(val)){
			msg[xh] = "1";
			nullMsg.size ++;
			return;
		}
	});
	return nullMsg;
}
//检查是否重复
function checkReply($obj){
	var json = {};
	$.each($("[name='tzhpm']"),function(i){
		var val = $(this).val();
		if(noNull(val)){
			if(isNull(json[val])){
				json[val] = "1";
				return;
			}
			$obj.val("");
			alert("排名"+val+"已经存在，不能重复！");
		}
	});
}
$(document).on("change","[name='tzhpm']",function(){
	var val = $(this).val();
	if(isNull(val)){
		return;
	}
	checkReply($(this));
});
$("#btn_save").click(function(){
	var result = checkTzhpm();
	if(result.size == 0){
		var url = basePath + "/doTzhpmSave";
		var jsonStr = JSON.stringify($("#tableform").serializeObject("guid","tzhpm"));
		doDeal("保存",url,"data="+jsonStr,function(){
			table.ajax.reload();
		});
	}else{
		var msg = "<br/>";
		for(var name in result.msg){
			msg += "第"+name+"行，调整后项目排名不能为空！<br/>";
		}
		alert(msg);
	}
})
</script>
</body>
</html>