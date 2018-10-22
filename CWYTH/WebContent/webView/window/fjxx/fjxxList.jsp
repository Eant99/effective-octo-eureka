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
</head>
<style type="text/css">
#example_filter input[type=search]{
	display:inline-block;
	width:auto;
}
body{
	overflow:hidden;
}
.go-back{
	display:none;
}
</style>
<body class="contrast-red">
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
                <div class='responsive-table'>
                    <div class='scrollable-area'>
                    <table id="example" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					      <thead>
					      <tr>
					          <th><input type="checkbox" value="" id="example-select-all"/></th>
					          <th>分类代码</th>
					          <th>分类名称</th>
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
		</div>
	</div>
</section>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	var flbm = "${param.flbm}";//点击左侧树上的节点，传过来的flbm（分类号去掉右侧的0）
    table = $('#example').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/window/flxx?flbm="+flbm//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":[10],
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "FLH",orderable:false, 'render': function (data, type, full, meta){
            	return "<input type='checkbox' class='keyId' name='flh' value='"+data+"'><input type='hidden' class='keyId' name='flmc' value='"+full.FLMC+"'><input type='hidden' class='keyId' name='czfl' value='"+full.CZFLMC+"'> <input type='hidden' class='keyId' name='czdm' value='"+full.FFLDM+"'> <input type='hidden' class='keyId' name='bzdm' value='"+full.CZBZDM+"'>";
		    },"width":10,'searchable': false},
            {"data": "FLH",defaultContent:""},
            {"data": "FLMC",defaultContent:""},
            {"data": "CZFLMC",defaultContent:""},
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
            "search":"搜索："
        },
        "dom":"<'row'<'col-md-6 col-sm-6 col-xs-6'i><'col-md-6 col-sm-6 col-xs-6'f>>t<'row'<'col-md-6 col-sm-6 col-xs-6'><'col-md-6 col-sm-6 col-xs-6'p>>",
        "initComplete": function(){
        }
    });
    //单击事件
    $(document).on("dblclick","#example tr",function(){
    	var flmc = $(this).find("[name='flmc']").val();
    	if(flmc==''||flmc==null||flmc=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","txt_flmc").val(flmc);
        	getIframeControl("${param.pname}","txt_mc").val(flmc);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
    		var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
    		getTopFrame().close(winId);
    	}
    });
});
</script>
</body>
</html>