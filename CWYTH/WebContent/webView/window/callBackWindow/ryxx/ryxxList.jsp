<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>固定资产管理系统--二级单位人员信息（单位内和单位间调拨）</title>
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
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
					<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong>先找到需要的人员，然后<strong>双击</strong>这条信息
			        </div>
			        <hr class="hr-normal">
                	<div class='responsive-table'>
	                    <div class='scrollable-area'>
		                    <table id="example" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
							      <thead>
							      <tr>
							          <th><input type="checkbox" value="" id="example-select-all"/></th>
							          <th>人员编号</th>
							          <th>姓名</th>
							          <th>性别</th>
							          <th>所在单位</th>
							          <th>出生日期</th>
							          <th>人员状态</th>
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
    table = $('#example').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/window/ejdwRyxx?ejdw=${param.ejdw}&dwbh=${param.dwbh}&flag=${param.flag}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":[10],
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "RYGH",orderable:false, 'render': function (data, type, full, meta){
            	return "<input type='checkbox' class='keyId' name='rygh' value='"+JSON.stringify(full)+"'>";
		    },"width":10,'searchable': false},
            {"data": "RYGH",defaultContent:""},
            {"data": "XM",defaultContent:""},
            {"data": "XB",defaultContent:""},
            {"data": "DWBH",defaultContent:""},
            {"data": "CSRQ",defaultContent:""},
            {"data": "RYZT",defaultContent:""}
        ],
        "language":{
            "lengthMenu": "_MENU_ 条记录每页",
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
            "searchPlaceholder":"请输入人员信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'il><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
    });
    $("input[type=search]").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width","180px");
	$(".icon-chaxun").css("position","absolute").css("top","13px").css("right","43px");
    //单击事件
    $(document).on("dblclick","#example tr",function(){
    	var val = $(this).find("[name='rygh']").val();
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		//这里的val是json格式的一堆数据，不能直接放在文本框上，否则验证会有问题
//         	getIframeControl("${param.pname}","${param.controlId}").val(val);
//         	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
//         	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	getIframWindow("${param.pname}").callBack(val);
        	var winId = top.layer.getFrameIndex(parent.window.name);
        	top.layer.close(winId);
    	}
    });
});
</script>
</body>
</html>