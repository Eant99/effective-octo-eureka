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
		{"data": "guid",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" sm="'+full.SM+'" name="guid" kmbh="'+full.KMBH+'" class="keyId" value="' + "("+full.KMBH+")"+full.KMMC + '" guid = "'+full.GUID+'">';
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
           url: "${ctx}/bmysbz/getJjkmPageList?treeDm=${param.kmbh}&treesearch=${treesearch}"//获取数据的方法
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
    	//var winId = getTopFrame().layer.getFrameIndex(window.name);
    	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);

    	var checkbox = $(".keyId").filter(":checked");
    	console.log("checkbox==="+checkbox.length);
    	if(checkbox.length==0){
    		alert("请至少选择一条信息！");
    		return;
    	}else{
    		var guid = [];
        	var jjkmmcbh=[];
        	var sm=[]
        	$.each(checkbox,function(i,v){
        		var guidList = $(this).attr("guid");
        		var jjkmmcbhList = $(this).attr("value");
        		var smList = $(this).attr("sm");
        		console.log("smlist======="+smList);
        		/* if(kmbh!=""){
        			kmxx.push("("+kmbh+")"+kmmc);
        			kmbhs.push(kmbh);
        		} */
        		guid.push(guidList);
        		jjkmmcbh.push(jjkmmcbhList);
        		sm.push(smList);
        		
        	});
        	getIframWindow("${param.pname}").addxmzc(guid,jjkmmcbh,sm);
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