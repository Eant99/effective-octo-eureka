<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>预约详细设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-js.inc"%>
<%@include file="/static/include/public-list-js.inc"%>

</head>
<body>
<div class="fullscreen">
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${fykmdysz.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="box">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>本日详细信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
				<div class="container-fluid">
			<div class='responsive-table'>
				<div class='scrollable-area'>					
					<table id="mydatatables" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>允许预约人数/总共预约人数</th>						
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
		</div>
	</div>
</form>
</div>

<script type="text/javascript">
$(function(){
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
       
          }
	      });
	//保存按钮
	$("#btn_save").click(function(){		
		alert("生成成功！");
	});
	
	

	//列表数据
	var columns = [
			{
				"data" : "GUID",
				orderable : false,
				"render" : function(data, type, full, meta) {
					return '<input type="checkbox" class="keyId" name="guid" bz="'+full.BZS+'" value="' + data + '">';
				},
				"width" : 10,
				'searchable' : false
			},	
			{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
            },"width":41,"searchable": false,"class":"text-center"},
			{
				"data" : "KSSJ",
				defaultContent : "",
					"render" : function(data, type, full, meta) {
						return '<input type="hidden"  class="keyId" name="kssj" bz="'+full.BZS+'" value="' + data + '">'+data+'';
					}
			},
			{
				"data" : "JSSJ",
				defaultContent : ""
			},
			{
				"data" : "YXYYRS",						
				defaultContent : "",class:"text-right",
					"render" : function(data, type, full, meta) {
						return '<input type="hidden"  class="keyId" name="yxyyrs" bz="'+full.BZS+'" value="' + data + '">'+data+'';
					}
			}
			
			 ];
	table = $('#mydatatables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/yysz/getPageList"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": columns,
        "language":{
            //"search":"",
            //"searchPlaceholder":"请输入种类名称"
        },
        "dom":"<'row'<'col-sm-7 col-sm-7 col-xs-7'li><'col-sm-5 col-sm-5 col-xs-5'f>>t<'row'<'col-sm-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/wsbx/jcsz/fykmdysz/fykmdysz_list.jsp";
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}"
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"
	    	}
		 }
	});

	
});

</script>
</body>
</html>