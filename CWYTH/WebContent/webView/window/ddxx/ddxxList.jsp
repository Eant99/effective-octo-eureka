<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>地点信息</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
<style type="text/css">
	body{
		overflow-x:hidden;
	}
</style>
</head>
<body>
           <div class="box">
               <div class='box-content' style="padding-bottom: 0; overflow:visible;">
                   <div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
	          	<strong>提示：</strong>先找到需要的地点，然后<strong>双击</strong>这条信息。
	        </div>
	        <hr class="hr-normal">
                   <div class='responsive-table'>
                   <div class='scrollable-area'>
                   <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
			     <thead>
			     <tr id="header">
			         <th><input type="checkbox" value="" class="select-all"/></th>
			         <th>序号</th>
		        	 <th>地点号</th>
		        	 <th>地点名称</th>
		        	 <th>所属单位</th>
		        	 <th>上级地点</th>
			     </tr>
			     </thead>
			     <tbody>
			     </tbody>
		     </table>
                   </div>
                   </div>
                </div>
</div>
<script>
$(function (){
	//列表数据
 	$('#mydatatables').DataTable({
        "ajax": {
            url:"${pageContext.request.contextPath}/window/ddxx?ddbh=${param.ddbh}"//获取数据的方法
        },
        "order": [2,'asc'],
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "serverSide": true,
        "scrollXOption": true,
        "scrollYOption": true,
        "columns":[
        	{"data": "DDBH",orderable:false, "render": function (data, type, full, meta){
            	return '<input type="checkbox" class="keyId" name="ddbh" value="' + "("+full.DDH+")"+full.MC + '">';  	
        	},"width":10,'searchable': false},
        	{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
        	},"width":41,"searchable": false,"class":"text-center"},
        	{"data": "DDH",defaultContent:""},
        	{"data": "MC",defaultContent:""},
        	{"data": "DWMC",defaultContent:"",orderable:false},
        	{"data": "SJDDMC",defaultContent:"",orderable:false}
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入地点信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
	});
 	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    //单击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val = $(this).find("[name='ddbh']").val();
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
        	close(winId);
    	}
    });
});
</script>
</body>
</html>