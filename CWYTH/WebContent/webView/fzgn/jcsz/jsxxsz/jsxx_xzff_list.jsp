<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>薪资发放已复核的教职工信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>工&emsp;&emsp;号</label>
					<input type="text" id="txt_xh" class="form-control" name="xh" table="A" placeholder="请输入教师工号">
				</div>
				<div class="form-group">
					<label>姓&emsp;&emsp;名</label>
					<input type="text" id="txt_xm" class="form-control" name="xm"  table="A" placeholder="请输入教师姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>工号</th>
				            <th>姓名</th>
				            <th>性别</th>				            
				            <th>所在单位</th>				            
				            <th>职务</th>
				            <th>职称</th>
				            <th>文化程度</th>
				            <th>来校时间</th>
				            <th>出生日期</th>
				            <th>民族</th>
				            <th>身份证件类型</th>
				            <th>身份证件号</th>
				            <th>婚姻状况</th>
				            <th>政治面貌</th>
				            <th>联系方式</th>				            
				            <th>邮箱</th>
				            <th>在职类型</th>
				            <th>在职人员来源</th>
				            <th>参加工作时间</th>
				            <th>工龄（年）</th>
				            <th>排序序号</th>
				            <c:if test="${param.type!='select'}">
				            	<th>操作</th>
				            </c:if>
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
<c:if test="${param.type!='select'}">
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
</c:if>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//收入申报录入传递参数，用于弹窗隐藏编辑、删除
	var type = "${param.type}"; 
	//列表数据
	if(type =='select'){
			var columns = [
               {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
                 return '<input type="checkbox" name="guid" class="keyId" xh="'+full.XH+'" xm="'+full.XM+'" sfzh="'+full.SFZH+'" value="' + data + '" guid = "'+full.GUID+'">';
               },"width":10,'searchable': false},
               {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
                  	return data;},"width":41,"searchable":false,"class":"text-center"},  
               {"data": "XH",defaultContent:""},
               {"data": "XM",defaultContent:""},
               {"data": "XBM",defaultContent:"","class":"text-center"},       
               {"data": "SZDW",defaultContent:""},
               {"data": "ZW",defaultContent:""},
               {"data": "ZC",defaultContent:""},
               {"data": "WHCD",defaultContent:""},
               {"data": "LXSJ",defaultContent:"","class":"text-center"},
               {"data": "CSRQ",defaultContent:"","class":"text-center"},
               {"data": "MZM",defaultContent:""},       
               {"data": "SFZJLXM",defaultContent:""},
               {"data": "SFZH",defaultContent:"","class":"text-center"},
               {"data": "HYZKM",defaultContent:"","class":"text-center"},
               {"data": "ZZMMM",defaultContent:""},
               {"data": "LXFS",defaultContent:""},
               {"data": "YX",defaultContent:""},
               {"data": "ZZLX",defaultContent:""},
               {"data": "ZZRYLY",defaultContent:""},                                                       
               {"data": "CJGZSJ",defaultContent:"","class":"text-center"},
               {"data": "GL",defaultContent:""},   
               {"data": "PXXH",defaultContent:""},   	
             ];
	}else{
	    var columns = [
         {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
           return '<input type="checkbox" name="guid" class="keyId" xh="'+full.XH+'" xm="'+full.XM+'" sfzh="'+full.SFZH+'" value="' + data + '" guid = "'+full.GUID+'">';
         },"width":10,'searchable': false},
         {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
            	return data;},"width":41,"searchable":false,"class":"text-center"},  
         {"data": "XH",defaultContent:""},
         {"data": "XM",defaultContent:""},
         {"data": "XBM",defaultContent:"","class":"text-center"},       
         {"data": "SZDW",defaultContent:""},
         {"data": "ZW",defaultContent:""},
         {"data": "ZC",defaultContent:""},
         {"data": "WHCD",defaultContent:""},
         {"data": "LXSJ",defaultContent:"","class":"text-center"},
         {"data": "CSRQ",defaultContent:"","class":"text-center"},
         {"data": "MZM",defaultContent:""},       
         {"data": "SFZJLXM",defaultContent:""},
         {"data": "SFZH",defaultContent:"","class":"text-center"},
         {"data": "HYZKM",defaultContent:"","class":"text-center"},
         {"data": "ZZMMM",defaultContent:""},
         {"data": "LXFS",defaultContent:""},
         {"data": "YX",defaultContent:""},
         {"data": "ZZLX",defaultContent:""},
         {"data": "ZZRYLY",defaultContent:""},                                                       
         {"data": "CJGZSJ",defaultContent:"","class":"text-center"},
         {"data": "GL",defaultContent:""},   
         {"data": "PXXH",defaultContent:""},   	
         {"data": "GUID",'render':function(data, type, full, meta){
      		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>'; 
        },orderable:false,"width":220}
       ];
	}
    	table = getDataTableByListHj("mydatatables","${ctx}/jsxxs/getxzPageList?ffpc=${param.ffpc}",[22,'asc'],columns,0,1,setGroup);
    	$(function () {
    			$(document).on("dblclick","tbody tr",function(){
    				var xh = $(this).find("[name='guid']").attr("xh");
    				var xm = $(this).find("[name='guid']").attr("xm");
    				var sfzh = $(this).find("[name='guid']").attr("sfzh");
    				var guid = $(this).find("[name='guid']").val();
    				var cdid="${param.controlId}";
    				getIframeControl("${param.pname}","${param.controlId}").val(xh);
    				getIframeControl("${param.pname}","${param.controlId2}").val(xm);
    				getIframeControl("${param.pname}","${param.controlId3}").val(sfzh);
    				getIframeControl("${param.pname}","${param.guid}").val(guid);
    	        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
    	        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
    	        	getIframWindow("${param.pname}").cxyhk(guid,cdid);//
    	        	var winId = getTopFrame().layer.getFrameIndex(window.name);
    	        	close(winId);
    		    });
    	    });
    	 
});
</script>
</body>
</html>