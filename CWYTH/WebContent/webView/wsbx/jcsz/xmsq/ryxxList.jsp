<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>固定资产管理系统人员信息</title>
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
         <div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_qd">确定</button>
        </div>
        </div>
        </div>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
				<div class="alert alert-info">
		          	<strong>提示：</strong>先找到一个或多个需要的人员，然后点击<strong>确定</strong>按钮。
		        </div>
		        <hr class="hr-normal" id="hr">
                <div class='responsive-table'>
                    <div class='scrollable-area'>
                    <table id="mydataTables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					      <thead>
					      <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>人员工号</th>
					          <th>姓名</th>
					          <th>性别</th>
					          <th>所在单位</th>
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
    table = $('#mydataTables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/window/ryxx?treesearch=${param.treesearch}&dwbh=${param.dwbh}&flag=${param.flag}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "RYGH",orderable:false,  'render': function (data, type, full, meta){
                return '<input type="checkbox" name="rygh" class="keyId" value="('+data+')'+full.XM+'" rygh = "'+full.RYGH+'">';
            },"width":10,'searchable': false},
		    {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
            },"width":41,"searchable": false,"class":"text-center"},
            {"data": "RYGH",defaultContent:""},
            {"data": "XM",defaultContent:""},
            {"data": "XB",defaultContent:""},
            {"data": "DWMC",defaultContent:"",orderable:false}
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入人员工号或姓名"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$("div.button").prop("innerHTML","人员信息列表").css("font-size","14px");
    //单击事件
   //确定按钮
   	$(document).on("click","#btn_qd",function(){
   		var checkbox = $(":checkbox").filter(":checked");

   		if(checkbox.length>0){
   			var guid = [];
   			var ryghs=[];
   			checkbox.each(function(){
   				guid.push($(this).val());
   			});
   			checkbox.each(function(){
   				ryghs.push($(this).attr("rygh"));
   			});
   			var str0=ryghs.join(",")
   			var str=guid.join(",");
   			getIframeControl("${param.pname}","${param.controlId}").val(str);
   			getIframeControl("${param.pname}","txt_sqdxs").val(str0);
   		var	winId = top.layer.getFrameIndex(parent.window.name);

   			close(winId);
   		 //parent.select();
   		}else{
   			alert("请选择至少一条信息！");
   		}
   	});

});
</script>
</body>
</html>