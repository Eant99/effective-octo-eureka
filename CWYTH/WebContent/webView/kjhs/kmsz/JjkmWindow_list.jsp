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
<title>经济科目信息</title>
<%@include file="/static/include/public-list-css.inc"%>

<%@include file="/static/include/public-list-js.inc"%>
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
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的单位，然后<strong>双击</strong>这条信息;
			          	<c:if test="${param.from == 'wxjfhb'}"><br>&emsp;&emsp;&emsp;（2）当前为<strong><%=Constant.MR_YEAR()%>年</strong>可进行经费划拨的部门。</c:if>
			        </div>
			        <hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr>
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						    <th>科目编号</th>
						    <th>科目名称</th>
							<th>科目级次</th>	
						    <th>是否启用</th>	
						    <th>说明</th>					

	    					  						 
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
<%@include file="/static/include/public-list-js.inc"%>
<script>
var target = "${ctx}/ysgl/xmsz/srxm";
$(function (){
	
	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" flag="'+full.KMMC+'" guid1="'+data+'" sm="'+full.SM+'" name="guid" kmbh="'+full.KMBH+'" class="keyId" value="' + "("+full.KMBH+")"+full.KMMC + '" guid = "'+full.GUID+'">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "KMBH",defaultContent:""},
	   	{"data": "KMMC",defaultContent:""},
	   	{"data": "KMJC",defaultContent:""},	
		{"data": "QYF",defaultContent:""},
		{"data": "SM",defaultContent:""},
	];
   table = $('#mydatatables').DataTable({
       ajax: {
           url: "${ctx}/kmsz/getJjkmPageList?treeDm=${param.kmbh}&treesearch=${treesearch}"//获取数据的方法
       },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
      "serverSide": true,
       "columns": columns,
         "language":{
        	"search":"",
            "searchPlaceholder":"请输入部门信息关键字"
       },  
       "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
/*        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
 */   });
	   
	   //双击事件
   $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	
    	
    	var val = $(this).find("[name='guid']").attr("flag");
    	var guid = $(this).find("[name='guid']").attr("guid1");
    	console.log("val========="+val);
    	console.log("guid========="+guid);
    	
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
    		getIframeControl("${param.pname}","${param.controlId1}").val(guid);
        	
        	//getIframWindow("${param.pname}").fz("${param.controlId}");
        	close(winId);
    	}
    }); 

	//取消
   	$("#btn_cancel").on("click",function(){
   		close(winId);
   	});
});
</script>
</body>
</html>