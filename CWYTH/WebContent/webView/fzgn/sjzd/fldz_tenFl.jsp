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
</head>
<style type="text/css">
#example_filter input[type=search]{
	display:inline-block;
	width:auto;
}
body{
	overflow-x:hidden;
}
</style>
<body class="contrast-red">
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
				<div class="alert alert-info">
			         <strong>提示：</strong>先找到需要的分类，然后<strong>双击</strong>这条信息
			     </div>
			     <hr class="hr-normal" id="hr">
                <div class='responsive-table'>
                    <div class='scrollable-area'>
                    <table id="example" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					      <thead>
					      <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>财政分类号</th>
					          <th>财政分类名称</th>
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
	var zcdm = "${param.zcdm}";//点击左侧树上的节点，传过来的flbm（分类号去掉右侧的0）
    table = $('#example').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/window/czten?dlh=${param.dlh}&zcdm="+zcdm//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":[10],
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "scrollXOption": true,
        "scrollYOption": true,
        "columns": [
            {"data": "ZCDM",orderable:false, 'render': function (data, type, full, meta){
            	return "<input type='checkbox' class='keyId' name='zcdm' value='"+data+"'><input type='hidden' class='keyId' name='mc' value='"+full.MC+"'>";
		    },"width":10,'searchable': false},
		    {"data": "ZCDM",defaultContent:""},
            {"data": "MC",defaultContent:""},
        ],
        "language":{
            "lengthMenu": "_MENU_ 条记录每页",
            "zeroRecords": "没有找到记录",
            "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
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
            "searchPlaceholder":"请输入分类信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'il><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
 	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width","175");
    //单击事件
    $(document).on("dblclick","#example tr:not(#header)",function(){
    	var zcdm = $(this).find("[name='zcdm']").val()+"";
    	var mc = $(this).find("[name='mc']").val()+"";
    	getIframeControl("${param.pname}","${param.controlId}").val(zcdm);
    	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
    	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
    	getIframeControl("${param.pname}","${param.zcmc}").val(mc);
    	getIframeControl("${param.pname}","${param.zcmc}").focus();//手动触发验证
    	getIframeControl("${param.pname}","${param.zcmc}").trigger("blur");//手动触发验证
    	var winId = top.layer.getFrameIndex(parent.window.name);
    	top.layer.close(winId);
    });
});
</script>
</body>
</html>