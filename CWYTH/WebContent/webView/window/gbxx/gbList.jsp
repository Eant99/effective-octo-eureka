<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>固定资产管理系统资产分类信息</title>
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
	<div class="alert alert-info">
         	<strong>提示：</strong>先找到需要的国别，然后<strong>双击</strong>这条信息
       </div>
             <div class='responsive-table'>
                 <div class='scrollable-area'>
                 <table id="example" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
		      <thead>
		      <tr id="header">
		          <th><input type="checkbox" value="" class="select-all"/></th>
		          <th>国别编号</th>
		          <th>国别名称</th>
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
	var bzdm = "${param.bzdm}";//点击左侧树上的节点，传过来的flbm（分类号去掉右侧的0）
    table = $('#example').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/window/gbxx?bzdm="+bzdm//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "DM",orderable:false, 'render': function (data, type, full, meta){
            	return "<input type='checkbox' class='keyId' name='flh' value='"+data+"'><input type='hidden' class='keyId' name='flmc' value='("+data+")"+full.MC+"'>";
		    },"width":10,'searchable': false},
            {"data": "DM",defaultContent:""},
            {"data": "MC",defaultContent:""},
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入国别信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
        "initComplete": function(){
        }
    });
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    //单击事件
    $(document).on("dblclick","#example tr:not(#header)",function(){
    	var flmc = $(this).find("[name='flmc']").val();
    	getIframeControl("${param.pname}","${param.controlId}").val(flmc);
    	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
    	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
    	var winId = top.layer.getFrameIndex(parent.window.name);
    	top.layer.close(winId);
    });
});
</script>
</body>
</html>