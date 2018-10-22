<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh" placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc" placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>项目编号</label>
							<div class="input-group">
								<input type="text" id="txt_xmbh" class="form-control" name="xmbh"  placeholder="请输入项目编号">
							</div>
						</div>
						<div class="form-group">
							<label>项目名称</label>
							<div class="input-group">
								<input type="text" id="txt_xmmc" class="form-control" name="xmmc"  placeholder="项目名称">
							</div>
						</div>
						<div class="form-group">
							<label>分类名称</label>
							<div class="input-group">
								<input type="text" id="txt_bxr" class="form-control" name="bxr"  placeholder="请输入分类名称">
							</div>
						</div>
						<div class="form-group">
							<label>服务专业</label>
							<input type="text" id="txt_bxr" class="form-control" name="bxr"  placeholder="服务专业">
						</div>
						<div class="form-group"> 
							<label>服务学科</label>
							<div class="input-group">
								<input type="text" id="txt_bmmc" class="form-control input-radius" name="bmmc"   placeholder="服务学科">
							</div>
						</div>
						<div class="form-group">
							<label>项目名称</label>
							<input type="text" id="txt_xmbh" class="form-control" name="xmbh"  placeholder="项目名称">
						</div>
						<div class="form-group">
							<label>是否上年度重新论证项目</label>
							<select style="width:70px;" id="txt_zffs" class="form-control" name="zffs">
									<option value="">未选择</option>
	                					<option value="1">是</option>
	                					<option value="2">否</option>
								</select>	
						</div>
						<div class="form-group">
							<label>申报单位</label>
							<input type="text" id="txt_bxrq" class="form-control" name="bxrq"  placeholder="申报单位" value="" >
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
	               <button type="button" class="btn btn-primary" id="btn_add">确定</button>
	             
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
			<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的项目，然后<strong>点击确定按钮</strong>这条信息;
			        </div> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>审核状态</th>
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>申报单位</th>
				            <th>预算金额(元)</th>
				            <th>计划执行时间</th>
				            <th>资金来源</th>
				            <th>资金编号</th>
				            <th>已分配金额</th>
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
<%@include file="/static/include/public-manager-js.inc"%>
<script>
$(function () {
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">'+
                 '<input type="hidden" class="guid" value="'+data+'"/>'
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:"","class":"text-center xh"},
       {"data": "SHZT",defaultContent:"","class":"text-center shzt"},
       {"data": "XMBH",defaultContent:"","class":"xmbh"},
       {"data": "XMMC",defaultContent:"","class":"xmmc"},
       {"data": "SBDW",defaultContent:"","class":"sbdw"},
       {"data": "YSJE",defaultContent:"","class":"text-right ysje"},
       {"data": "JHZXSJ",defaultContent:"","class":"text-center jhzxsj"},
       {"data": "ZJLY",defaultContent:"",'render':function(data,type,full,meta){
    	   if(data == null){
    		   data = "";
    	   }
    	   return '<label>'+data+'</label>'+
    	   '<label style="display: none" class="zjly">'+full.ZJLYBH+'</label>'
       }},
       {"data": "ZJBH",defaultContent:"","class":"zjbh"},
       {"data": "YFPJE",defaultContent:"0.00","class":"text-right yfpje"},
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/zjfpfa/getXmList",[2,'asc'],columns,0,1,setGroup);
	
   
	//查询
	$("#btn_search").click(function(){
		window.location.reload();
	});
	//确定按钮
   	$(document).on("click","#btn_add",function(){
   		var $arr_tr = $("#mydatatables").find("tr").has("[name='guid']:checked");
   		if($arr_tr.length>0){
   			getIframWindow("${param.pname}").addItem($arr_tr);
   			var winId = getTopFrame().layer.getFrameIndex(window.name);
   			close(winId);
   		 //parent.select();
   		}else{
   			alert("请选择至少一条信息！");
   		}
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
</body>
</html>