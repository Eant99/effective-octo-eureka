<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>年级信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
	body{
		overflow-x:hidden;
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
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的年级，然后<strong>双击</strong>这条信息;
			        </div>
			        <hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>年级编号</th>
					          <th>年级名称</th>
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
$(function (){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	var columns = [
	           	{"data": "NJBH",orderable:false, "render": function (data, type, full, meta){
	        		return '<input type="checkbox" class="keyId" name="njbh" value="' + "("+full.NJBH+")"+full.NJMC + '">';
				},"width":10,'searchable': false},
	            {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	            	return data;
	            },"width":41,"searchable": false,"class":"text-center"},
	            {"data": "NJBH",defaultContent:""},
	            {"data": "NJMC",defaultContent:""},
	        ];
	table = $('#mydatatables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/xsxx/njpage"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": columns,
        "language":{
            "search":"",
            "searchPlaceholder":"请输入年级信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val = $(this).find("[name='njbh']").val();
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	close(winId);
    	}
    });
    if("${param.windowModel}" == "1"){
	   	$("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:20px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");
	}else{
    	$("div.button").prop("innerHTML","年级信息列表").css("font-size","14px");
    }
  	//确定按钮
   	$("#btn_sure").on("click",function(){
   		var checkbox = $("#mydatatables").find("[name='njbh']").filter(":checked");
   		if(checkbox.length == 0){
   			alert("请先选择年级！");
   	   		return false;
   		}else{
	   		var selDwbhS = [];
	   		checkbox.each(function(){
	   			selDwbhS.push($(this).val());
	   		});
	   		getIframeControl("${param.pname}","${param.controlId}").val(selDwbhS.join(";"));
	    	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
	    	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
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