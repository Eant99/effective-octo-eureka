<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>个人经费设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
<!-- 				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
		          	<div class="btn-group pull-right" role="group">
		          	<button type="button" class="btn btn-primary" id="btn_add">确定</button>
				</div> -->
				<div class="alert alert-info" style="height:29px;">
		          	<strong>提示：</strong>先找到需要的项目，然后<strong>双击</strong>这条信息。
		        </div>
		        <hr class="hr-normal" id="hr">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>项目编号</th>
				        <th>项目名称</th>
				        <th>负责人</th>
				        <th>项目大类</th>
				        <th>经费类型</th>
				        <th>剩余金额（元）</th>
				            
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
	var target = "${ctx}/wsbx/ccyw/ccywsq";

	//列表数据
    var columns = [
{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
		return '<input type="checkbox" class="keyId" name="keyId" value="' + data + '" gid="'+data+'" jflx="'+full.JFLXMC+'" xmmc="'+full.XMMC+'" syje="'+full.YE+'"  ktmc="' + "("+full.XMBH+")"+full.XMMC + '">';
   },"width":10,'searchable': false},//0
   
	{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
  		return data;
	},"width":41,"searchable": false,"class":"text-center"},//1
	
	{"data": "XMBH",defaultContent:"","width":40},
	{"data": "XMMC",defaultContent:"","class":"ktmc","width":300},
	{"data": "FZRXM",defaultContent:""},
	{"data": "XMDLMC",defaultContent:""},
	{"data": "JFLXMC",defaultContent:"","class":"jflx"},
  	{"data": "YE",defaultContent:"","width":80,"class":"text-right syje",'render':function(data){
  		return parseFloat(data).toFixed(2);
  	}}
      ];
	
	
    table = $('#mydatatables').DataTable({
        ajax: {
            url: target+"/getjFPageList?guid=${guid}&xmguid=${xmguid}&fromPage=${param.fromPage}&ryid=${param.ryid}&type=${param.type}"//获取数据的方法
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
	$("div.button").prop("innerHTML","项目信息列表").css("font-size","14px");
	   //双击事件
	 $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	    	//var ktmc = $(this).find(".ktmc").text();
	    	var ktmc = $(this).find("[name='keyId']").attr("ktmc");
	    	var xmmc = $(this).find("[name='keyId']").attr("xmmc");
	    	var jflx = $(this).find(".jflx").text();
	    	var syje =  $(this).find(".syje").text();
	    	var guid = $(this).find(".keyId").val();
	    	if(ktmc==''||ktmc==null||ktmc=='undefined'){
	    		alert("没有可以选择的数据！");
	    	}else{
	    		if("${param.flag}"==1){
	    			getIframeControl("${param.pname}","${param.cId_1}").val(ktmc);
		    		getIframeControl("${param.pname}","${param.cId_3}").val(syje);
		    		getIframeControl("${param.pname}","${param.cId_4}").val(guid);
	    		}else{
		    		var pname = "${param.pname}";
		    		if(pname=="iframe_110201"){
		    			getIframeControl("${param.pname}","${param.cId_1}").val(xmmc);
		    		}else{
			    		getIframeControl("${param.pname}","${param.cId_1}").val(ktmc);
		    		}
		    		getIframeControl("${param.pname}","${param.cId_2}").val(jflx);
		    		getIframeControl("${param.pname}","${param.cId_3}").val(syje);
		    		getIframeControl("${param.pname}","${param.cId_4}").val(guid);
	    		}
// 	     	    var $xm = [];
// 	    	    $xm.push(ktmc);
// 	     	    $xm.push(jflx);
// 	     	    $xm.push(syje);
// 	     	    $xm.push(guid);
// 	    		getIframWindow("${param.pname}").addXmxx($xm);
	    		var winId = getTopFrame().layer.getFrameIndex(window.name);
	   			close(winId);
	    	}
	    });
	//点击确定，可多选
		$(document).on("click","#btn_add",function(){
	   		var $arr_tr = $("body tr").has(".keyId:checked");
	   		if($arr_tr.length==1){
	   			getIframWindow("${param.pname}").addXmxx($arr_tr);
	   			var winId = getTopFrame().layer.getFrameIndex(window.name);
	   			close(winId);
	   		}else{
	   			alert("请选择一条信息！");
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