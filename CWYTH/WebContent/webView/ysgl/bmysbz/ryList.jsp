<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.googosoft.constant.Constant"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>科目信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
	body{
		overflow-x:hidden;
	}
	tr td:first-child,th{
		text-align:center;
	}
</style>
<body class="contrast-red" >
<div id='wrapper'>
<section>
    <div class='row' id='content-wrapper'>
        <div class='col-md-12'>
             <div class="box">
                 <div class='box-content' style="padding-bottom: 0; overflow:visible;">
                 	<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的信息，然后<strong>双击</strong>这条信息;
			        </div>
			        <hr class="hr-normal" id="hr">
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
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" flag="'+data+'" class="keyId" value="' + "("+full.XH+")"+full.XM + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
          	
       {"data": "XH",defaultContent:""},
       {"data": "XM",defaultContent:""},
       {"data": "XBM",defaultContent:"","class":"text-center"},       
       {"data": "SZDW",defaultContent:""}, 
       {"data": "ZC",defaultContent:""},
       {"data": "ZW",defaultContent:""}
       
     ];
/*     	table = getDataTableByListHj("mydatatables","${ctx}/jsxxs/getPageList?treedwbh=${param.dwbh}",[2,'asc'],columns,0,1,setGroup);
 */
 
 
 table = $('#mydatatables').DataTable({
     ajax: {
         url: "${ctx}/jsxxs/getPageList?treedwbh=${param.dwbh}"//获取数据的方法
     },
      "lengthMenu":getTopFrame().window.sessionRowNumLength,
      "order": [ 2, 'asc' ],
    "serverSide": true,
     "columns": columns,
       "language":{
      	"search":"",
          "searchPlaceholder":"请输入部门信息关键字"
     },  
     "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
 });
 
 $("input[type=search]").parent("label").hide();
    	//双击事件
        $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
        	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
        	var val = $(this).find("[name='guid']").val();
        	var guid =  $(this).find("[name='guid']").attr("flag");
        	//alert("${param.controlId}");
        	if(val==''||val==null||val=='undefined'){
        		alert("没有可以选择的数据！");
        	}else{
        		getIframeControl("${param.pname}","${param.controlId}").val(val);
        		getIframeControl("${param.pname}","${param.controlId1}").val(guid);
            	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
            	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
            	//getIframWindow("${param.pname}").fz("${param.controlId}");
            	close(winId);
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