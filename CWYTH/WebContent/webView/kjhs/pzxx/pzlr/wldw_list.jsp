<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title></title>
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
			          	<strong>提示：</strong>先找到需要的信息，然后<strong>双击</strong>这条信息，或先选中需要的信息，然后点击<strong>确定</strong>按钮;
			            <span style="display:inline-block;width: 45%;text-align:right;">
			          	    <strong>提示：</strong>点击<strong style="">增加</strong>按妞可添加往来单位
			          	    <button type="button" class="btn btn-primary" style="padding: 0px !important;" id="btn_add">增加</button>
		          	    </span>
			        </div>
			        <button type="button" class="btn btn-primary" id="btn_qd">确定</button>
			        <hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>单位编号</th>
					          <th>单位名称</th>
					          <th>金额</th>
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
	           	{"data": "WLBH",orderable:false, "render": function (data, type, full, meta){
	        		return '<input type="checkbox" class="keyId"  value="' + "("+full.WLBH+")"+full.DWMC + '">';
				},"width":10,'searchable': false},
	            {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	            	return data;
	            },"width":41,"searchable": false,"class":"text-center"},
	            {"data": "WLBH",defaultContent:""},
	            {"data": "DWMC",defaultContent:""},
	            {"data": "JE",defaultContent:"","class":"text-right",'render':function(data){
	            	if(data==undefined){
	            		return '0.00';
	            	}else{
	          		    return parseFloat(data).toFixed(2);
	            	}
	          	}}
	        ];
	table = $('#mydatatables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/kjhs/pzxx/pzlr/wldw_list?controlId=${param.controlId}&kmbh=${param.kmbh}"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": columns,
        "language":{
            "search":"",
            "searchPlaceholder":"请输入单位编号或者单位名称关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val = $(this).find(".keyId").val();
    	console.log(val);
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControlForPzfz("${param.pname}","${param.controlId}").val(val);
    		getIframeControlForPzfz("${param.pname}","${param.controlId}").focus();//手动触发验证
    		getIframeControlForPzfz("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	close(winId);
    	}
    });
  	//确定按钮
   	$("#btn_qd").on("click",function(){
   		var checkbox = $("#mydatatables").find(".keyId").filter(":checked");
   		if(checkbox.length !=1 ){
   			alert("请最多选择一个单位！");
   	   		return false;
   		}else{
	   		var val = checkbox.val();
	   		getIframeControlForPzfz("${param.pname}","${param.controlId}").val(val);
	   		getIframeControlForPzfz("${param.pname}","${param.controlId}").focus();//手动触发验证
	   		getIframeControlForPzfz("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
	    	close(winId);
   		}
   	});
	//取消
   	$("#btn_cancel").on("click",function(){
   		close(winId);
   	});
	
  //添加按钮
   	$("#btn_add").click(function(){
   		var kmbh = "${param.kmbh}";
   		doOperate("${pageContext.request.contextPath}/kjhs/pzxx/pzlr/goWldwAddPage?kmbh="+kmbh);
	});
	
});
</script>
</body>
</html>