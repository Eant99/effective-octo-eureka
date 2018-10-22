<%@page import="com.googosoft.util.Validate"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>可以多选的单位列表</title>
	<%@include file="/static/include/public-list-css.inc"%>
	<%@include file="/static/include/public-list-js.inc"%>
	<%
		String sjdw = Validate.isNullToDefault(request.getParameter("sjdw"), "") + "";
	%>
<style type="text/css">
#mydatatables_filter input[type=search]{
	display:inline-block;
	width:auto;
}
body{
	overflow:hidden;
}
.go-back{
	display:none;
}
</style>
</head>
<body>
	<input type="hidden" name="sjdw" table="K" value="<%=sjdw%>"/>
	<div id='wrapper'>
		<section>
		    <div class='row' id='content-wrapper'>
		        <div class='col-md-12'>
		             <div class="box">
		                 <div class='box-content' style="padding-bottom: 0; overflow:visible;">
		                     <div class='responsive-table'>
			                     <div class='scrollable-area'>
				                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
									     <thead>
									     <tr id="header">
									          <th><input type="checkbox" value="" class="select-all"/></th>
									          <th>序号</th>
									          <th>部门号</th>
									          <th>部门名称</th>
									          <th>上级单位</th>
									          <th>单位性质</th>
									          <th>单位领导</th>
									     </tr>
									     </thead>
									     <tbody>
									     </tbody>
								     </table>
			                     </div>
		                     </div>
		                  </div>
		              </div>
		         </div>
		      </div>
		</section>
	</div>
<script>
$(function (){
	$('#mydatatables').DataTable({
        "ajax": {
            url:"${pageContext.request.contextPath}/window/multiSelDwxx"//获取数据的方法
        },
        "lengthMenu":[8],
        "order": [3,'asc'],
        "serverSide": true,
        "scrollXOption": true,
        "scrollYOption": true,
        "columns":  [
        	{"data": "DWBH",orderable:false, "render": function (data, type, full, meta){
        		return '<input type="checkbox" class="keyId" name="dwbh" value="' + "("+full.BMH+")"+full.MC + '">';
			},"width":10,'searchable': false},
            {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
            },"width":30,"searchable": false,"class":"text-center"},
            {"data": "BMH",defaultContent:""},
            {"data": "MC",defaultContent:""},
            {"data": "SJDW",defaultContent:""},
            {"data": "DWXZ",defaultContent:""},            
            {"data": "DWLD",defaultContent:""}
        ],
        "language":{
            "lengthMenu": "每页_MENU_条记录",
            "zeroRecords": "没有找到记录",
            "info": "第_PAGE_页/共_PAGES_页(总_MAX_条记录)",
            "infoEmpty": "无记录",
            "infoFiltered": "(从 _MAX_条记录过滤)",
            "paginate": {
                "previous": "上一页",
                "next": "下一页",
                "first": "首页",
                "last": "末页",
                "jump":"跳转"
            },
            "search":"搜索：",
            "searchPlaceholder":"请输入单位信息关键字"
        },
        "dom":"<'row'<'col-md-4 col-sm-4 col-xs-4 button'><'col-md-8 col-sm-8 col-xs-8'f>>t<'row'<'col-md-4 col-sm-4 col-xs-4'i><'col-md-8 col-sm-8 col-xs-8 pull-right'p>>"
	});
	
	$("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:20px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");

	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	
	//取消
   	$("#btn_cancel").on("click",function(){
   		close(winId);
   	});
 	
  	//确定按钮
   	$("#btn_sure").on("click",function(){
   		var checkbox = $("#mydatatables").find("[name='dwbh']").filter(":checked");
   		if(checkbox.length == 0){
   			alert("请先选择单位！");
   	   		return false;
   		}
   		else{
	   		var selDwbhS = [];
	   		checkbox.each(function(){
	   			selDwbhS.push($(this).val());
	   		});
	   		getIframeControl("${param.pname}","${param.controlId}").val(selDwbhS.join(";"));
	    	close(winId);
   		}
   	});
});
</script>
</body>
</html>