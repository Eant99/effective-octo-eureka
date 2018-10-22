<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>经费信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		
		<div class="container-fluid">
			<div class='responsive-table'>
				<div class='scrollable-area'>
				<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的经费，然后<strong>双击</strong>这条信息;
			        </div>
					<table id="mydatatables" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>							
								<th>经费代码</th>
								<th>经费名称</th>								
								
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/static/include/public-list-js.inc"%>
	<script>
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
            {"data": "JFDM",defaultContent:"",
				"render" : function(data, type, full, meta) {
					return '<input type="hidden"  class="keyId" name="jfdm" bz="'+full.BZS+'" value="' + data + '">'+data+'';
				}},
            {"data": "JFMC",defaultContent:"",
					"render" : function(data, type, full, meta) {
						return '<input type="hidden"  class="keyId" name="jfmc" bz="'+full.BZS+'" value="' + data + '">'+data+'';
					}}
     ];
    table = $('#mydatatables').DataTable({
        ajax: {
            url: "${ctx}/qtjfbxsq/getPageList"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": columns,
        "language":{
            "search":"",
            "searchPlaceholder":"请输入经费名称"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
   
    //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val1 = $(this).find("[name='jfdm']").val();
    	var val2 = $(this).find("[name='jfmc']").val();

    	if(val1==''||val1==null||val1=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		
    		getIframeControl("${param.pname}","${param.controlId}").val(val1);
    		getIframeControl("${param.pname}","txt_jfmc").val(val2);
        	//getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	//getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
    		var winId = getTopFrame().layer.getFrameIndex(window.name);
        	//alert(val2);
        	close(winId);
    	}
    });
   
	
});
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</body>
</html>