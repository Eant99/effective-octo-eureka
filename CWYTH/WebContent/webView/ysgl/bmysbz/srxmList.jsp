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
             			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
				<div class="alert alert-info">
		          	<strong>提示：</strong>请至少选择一条数据，然后点击确定按钮。
		        </div>
		        <button type="button" class="btn btn-default" id="btn_submit">确定</button>
			        <hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>收入项目编号</th>
					          <th>收入项目名称</th>
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
	       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="checkbox" name="guid" class="keyId" flag="' + "("+full.SRXMBH+")"+full.SRXMMC + '"  value="' + data+ '">';
	       },"width":10,'searchable': false},
	       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
	          	return data;},"width":41,"searchable":false,"class":"text-center"},
	            {"data": "SRXMBH",defaultContent:"","class":"text-center"},
	            {"data": "SRXMMC",defaultContent:""}
	     ];
	   // table = getDataTableByListHj("mydatatables",target+"/getSrxmPageData",[2,'asc'],columns,0,1,setGroup);
	
	   table = $('#mydatatables').DataTable({
     ajax: {
         url: "${ctx}/ysgl/xmsz/srxm/getSrxmPageData"//获取数据的方法
     },
      "lengthMenu":getTopFrame().window.sessionRowNumLength,
      "order": [ 2, 'asc' ],
    "serverSide": true,
     "columns": columns,
     /*    "language":{
      	"search":"",
          "searchPlaceholder":"请输入部门信息关键字"
     },    */
     "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
 });
	   
	   
	   //双击事件
   /*  $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var winId = getTopFrame().layer.getFrameIndex(window.name);
    	var val = $(this).find("[name='guid']").attr("flag");
    	var guid = $(this).find("[name='guid']").val();
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
    		getIframeControl("${param.pname}","${param.controlId2}").val(guid);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	//getIframWindow("${param.pname}").fz("${param.controlId}");
        	close(winId);
    	}
    }); */
    $("#btn_submit").click(function(){
    	var winId = getTopFrame().layer.getFrameIndex(window.name);
    	var checkbox = $(".keyId").filter(":checked");
    	console.log("checkbox==="+checkbox.length);
    	if(checkbox.length==0){
    		alert("请至少选择一条信息！");
    		return;
    	}else{
    		var guid = [];
        	var srxm=[];
        	$.each(checkbox,function(i,v){
        		var guidList = $(this).attr("value");
        		var srxmList = $(this).attr("flag");
        		/* if(kmbh!=""){
        			kmxx.push("("+kmbh+")"+kmmc);
        			kmbhs.push(kmbh);
        		} */
        		guid.push(guidList);
        		srxm.push(srxmList);
        	});
        	getIframWindow("${param.pname}").addsrys(guid,srxm);
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