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
	.box {
    margin-left: 2px !important;
    margin-right: 0px !important;
    position: relative;
    margin-top: 0px !important;
    margin-bottom: 0px;
    background-color: white;
    padding: 0px !important;
    border-radius: 2px;
}
.box .box-content{
    margin-bottom: 0px!important;
}
body{
	overflow-x:hidden;
}
</style>
</head>
<div class="box">
	<div class='box-content' style="padding-bottom: 0; overflow:visible;">
	<div class="alert alert-info">
         <strong>提示：</strong>先找到需要的分类，然后<strong>双击</strong>这条信息。
     </div>
     <hr class="hr-normal" id="hr">
             <div class='responsive-table'>
                 <div class='scrollable-area'>
                 <table id="example" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
		      <thead>
		      <tr id="header">
		          <th><input type="checkbox" value="" id="example-select-all"/></th>
		          <th>财政分类</th>
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
        	url: "${pageContext.request.contextPath}/window/czfl?treesearch=${param.treesearch}&bzdm="+bzdm//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":[14],
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "FLH",orderable:false, 'render': function (data, type, full, meta){
            	return "<input type='checkbox' class='keyId' name='flh' value='"+data+"'><input type='hidden' class='keyId' name='flmc' value='"+full.FLMC+"'>";
		    },"width":10,'searchable': false},
            {"data": "FLMC",defaultContent:""},
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入分类信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun' style='margin-top:6px;'></i>").css("width",search_wid);
	$(".icon-chaxun").css("position","absolute");
    //单击事件
    $(document).on("dblclick","#example tr:not(#header)",function(){
    	var flmc = $(this).find("[name='flmc']").val();
    	if(flmc==''||flmc==null||flmc=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(flmc);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	var winId = top.layer.getFrameIndex(parent.window.name);
        	top.layer.close(winId);
    	}
    });
});
</script>
</body>
</html>