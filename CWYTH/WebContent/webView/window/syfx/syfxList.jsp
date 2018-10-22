<%@page import="com.googosoft.constant.Constant"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<title>可以多选的使用方向</title>
	<%@include file="/static/include/public-list-css.inc"%>
	<%@include file="/static/include/public-list-js.inc"%>
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
					<div class='box-content' style="padding-bottom: 0px; overflow:visible;">
	                <div class='responsive-table'>
	                    <div class='scrollable-area'>
	                    <table id="mytable" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
						      <thead>
							      <tr>
							          <th><input type="checkbox" value="" class="select-all"/></th>
							          <th>代码</th>
							          <th>名称</th>
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
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	
    var table = $('#mytable').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/window/multiDmbList?zl=<%=Constant.SYFX%>"
        },
        "pagingType":"full_numbers",
        "lengthMenu":[10],
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "DMMC",orderable:false, 'render': function (data, type, full, meta){
            	return "<input type='checkbox' class='keyId' name='syfx' value='"+data+"'>";
		    },"width":10,'searchable': false},
		    {"data": "DM",defaultContent:""},
            {"data": "MC",defaultContent:""}
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
        "dom":"<'row'<'col-md-4 col-sm-4 col-xs-4 button'><'col-md-8 col-sm-8 col-xs-8'f>>t<'row'<'col-md-4 col-sm-4 col-xs-4'i><'col-md-8 col-sm-8 col-xs-8'p>>"
    });
	
    //双击事件
    $(document).on("dblclick","#mytable tr",function(){
    	var flh = $(this).find("[name='syfx']").val();
    	if(flh==''||flh==null||flh=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(flh);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	getTopFrame().close(winId);
    	}
    	
    });
	
    $("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:4px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");
    
  	//确定按钮
   	$("#btn_sure").on("click",function(){
   		var checkbox = $("#mytable").find("[name='syfx']").filter(":checked");
   		if(checkbox.length == 0){
   			alert("请先选择使用方向！");
   	   		return false;
   		}
   		else{
	   		var selSyfxS = [];
	   		checkbox.each(function(){
	   			selSyfxS.push($(this).val());
	   		});
	   		getIframeControl("${param.pname}","${param.controlId}").val(selSyfxS.join(";"));
	    	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
	    	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
	    	getTopFrame().close(winId);
   		}
   	});
  	
	//取消
   	$("#btn_cancel").on("click",function(){
   		getTopFrame().close(winId);
   	});
});
</script>
</body>
</html>