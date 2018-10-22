<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>
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
	tr td:first-child,th{
		text-align:center;
	}
	.dataTables_scrollBody{
		overflow:none!important;
	}
</style>
<body class="contrast-red" >
<div class="container-fluid">

<!-- <div class="search" id="searchBox"> -->
<!-- 		<form id="myform" class="form-inline" action="" style="padding-top:8px"> -->
<!--     		<div class="search-simple"> -->
<!-- 				<div class="form-group"> -->
<!-- 					<label>科目名称</label> -->
<!-- 					<input type="text" class="form-control input-radius" name="kmmc"  table="k" placeholder="请输入科目名称"> -->
<!-- 				</div> -->
<!-- 				<div class="form-group"> -->
<!-- 					<label>科目编号</label> -->
<!-- 					<input type="text" id="txt_xm" class="form-control input-radius" name="kmbh"  table="k" placeholder="请输入科目编号"> -->
<!-- 				</div> -->
<!-- 				<div class="form-group"> -->

<!-- 				</div> -->
<!-- 				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button> -->
				
<!-- 			</div> -->
<!--         </form> -->
<!--     </div> -->

		<div class='responsive-table'>
			<div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>科目编号</th>
					          <th>科目名称</th>
					     </tr>
					     </thead>
					     <tbody>
					     </tbody>
				     </table>
                     </div>
                     </div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
// 	var columns = [
// 	       		{"data": "guid",orderable:false, "render": function (data, type, full, meta){
// 	       	       	return '<input type="checkbox" class="keyId" name="guid"  kmxx="'+full.KMXX+'" kmxxbh="'+full.KMBH+'" >';
// 	       	    },"width":10,'searchable': false},
// 	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
// 	       	   		return data;
// 	       		},"width":41,"searchable": false,"class":"text-center"},
// 	       	   	{"data": "KMBH",defaultContent:""},
// 	       	   	{"data": "KMMC",defaultContent:""},
// 	       	];
// 	table = getDataTableByListHj("mydatatables","${ctx}/kmye/getkmxxPageList?kmbh=${param.kmbh}&year=${param.year}",[2,'asc'],columns,0,1,setGroup);

 table = $("#mydatatables").DataTable({
        ajax: {
            url: "${ctx}/kmye/getkmxxPageList?kmbh=${param.kmbh}&year=${param.year}&mkbh=${param.mkbh}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":[10],
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "scrollXOption": true,
        "scrollYOption": true,
        "columns": [
            {"data": "guid",orderable:false, "render": function (data, type, full, meta){
             	return '<input type="checkbox" class="keyId" name="guid"  kmxx="'+full.KMXX+'" kmxxbh="'+full.KMBH+'" >';
              },"width":10,'searchable': false},
	       {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
  		       return data;
	       },"width":41,"searchable": false,"class":"text-center"},
  	      {"data": "KMBH",defaultContent:""},
  	       {"data": "KMMC",defaultContent:""},
            ],
        "language":{
            "lengthMenu": "每页_MENU_ 条记录",
            "zeroRecords": "没有找到记录",
            "info": "第_PAGE_页/共_PAGES_页(总_MAX_条记录)",
            "infoEmpty": "无记录",
            "infoFiltered": "(从 _MAX_ 条记录过滤)",
            "paginate": {
                "previous": "上一页",
                "next": "下一页",
                "first": "首页",
                "last": "末页",
                "jump":"跳转"
            },
            "search":"",
            "searchPlaceholder":"请输入科目关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'il><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
 $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$(".icon-chaxun").css("position","absolute");
	$(".icon-chaxun").css("margin-top","2%");

	var winId = getTopFrame().layer.getFrameIndex(window.parent.name);
	//双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val = $(this).find("[name='guid']").attr("kmxx");
    	var kmxxbh = $(this).find("[name='guid']").attr("kmxxbh");//科目编号
    	var vamc = $(this).find("[name='guid']").attr("kmmc");
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		console.log("${param.pname}"+"__${param.controlId}_________"+val);
    		getIframeControl("${param.pname}","flag").val(kmxxbh);
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
        	//getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	//getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
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