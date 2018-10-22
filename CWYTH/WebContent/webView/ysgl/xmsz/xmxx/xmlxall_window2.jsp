<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金分配方案</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
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
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>项目类型编号</th>
				            <th>项目类型名称</th>
				            <th>预算类型</th>
				            <th>是否财政支出</th>
				            
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
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
    	  {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
    	         return '<input type="checkbox" name="guid" mc="'+full.XMLXMC+'" bmc="'+full.XMLXMC+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
    	       },"width":10,'searchable': false},
    	       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
    	          	return data;},"width":41,"searchable":false,"class":"text-center"},
    	       {"data": "XMLXBH",defaultContent:"","class":"text-center"},
    	       {"data": "XMLXMC",defaultContent:""},
    	       {"data": "YSLXMC",defaultContent:""},
    	       {"data": "SFCZZCMC",defaultContent:"","class":"text-center"},
      ];
	
	
    table = $('#mydatatables').DataTable({
        ajax: {
            url: "${ctx}/xmlx/getPageListTc"//获取数据的方法
        },
         "lengthMenu":getTopFrame().window.sessionRowNumLength,
         "order": [ 2, 'asc' ],
       "serverSide": true,
        "columns": columns,
          "language":{
         	"search":"",
             "searchPlaceholder":"请输入项目类型编号或项目类型名称"
        },  
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
 /*        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
  */   });
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$("div.button").prop("innerHTML","科目信息列表").css("font-size","14px");
	   //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
     	var winId = getTopFrame().layer.getFrameIndex(window.name);
     	var val = $(this).find("[name='guid']").attr("mc");
     	var guid = $(this).find("[name='guid']").val();
     	console.log("guid========"+guid);
     	console.log("qmc==="+val);
     	
     	if(val==''||val==null||val=='undefined'){
     		alert("没有可以选择的数据！");
     	}else{
     		getIframeControl("${param.pname}","${param.controlId}").val(val);
     		getIframeControl("${param.pname}","${param.id}").val(guid);
         	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
         	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
//          	getIframWindow("${param.pname}").addkm(guid);
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