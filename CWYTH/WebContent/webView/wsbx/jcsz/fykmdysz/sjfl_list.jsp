<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
#mydataTables_filter input[type=search]{
	display:inline-block;
	width:auto;
}
body{
	overflow-x:hidden;
}

</style>
<body>
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
				<div class="alert alert-info">
		          	<strong>提示：</strong>先找到需要的科目，然后<strong>双击</strong>这条信息。
		        </div>
		        <hr class="hr-normal" id="hr">
                <div class='responsive-table'>
                    <div class='scrollable-area'>
                    <input type="hidden" name="guid" value="${guid}"/>
                    <table id="mydataTables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					      <thead>
					      <tr id="header">
					         <th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>费用分类</th>
								<th>费用名称</th>
								<th>上级费用分类</th>
								<th>借贷方向</th>
								<th>科目名称</th>   
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
	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
    table = $('#mydataTables').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/fykmdysz/getTreePageList?fyfls=${param.fyfl}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": [
        	 {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
                 return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'" flag = "'+full.FYMC+'" >';
               },"width":10,'searchable': false},
		    {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
            },"width":41,"searchable": false,"class":"text-center"},
            {"data": "FYFL",defaultContent:""},
            {"data": "FYMC",defaultContent:""},
            {"data": "SJFL",defaultContent:""},
            {"data": "JDFX",defaultContent:"","class":"text-center"},
            {"data": "KMBH",defaultContent:""} 
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入费用分类或费用名称"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$("div.button").prop("innerHTML","科目信息列表").css("font-size","14px");
	 //双击事件
    $(document).on("dblclick","tr:not(#header)",function(){
    	var id = $(this).find("[name='guid']").val();
    	var mc = $(this).find("[name='guid']").attr("flag");
    	console.log("aaa++"+mc);
    	if(mc==''||mc==null||mc=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(mc);
    		getIframeControl("${param.pname}","${param.text_id}").val(id);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	close(winId);
    	}
    });
    if("${param.windowModel}" == "1"){
	   	$("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:20px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");
	}else{
    	$("div.button").prop("innerHTML","费用科目信息列表").css("font-size","14px");
    }
  
});

</script>
</body>
</html>