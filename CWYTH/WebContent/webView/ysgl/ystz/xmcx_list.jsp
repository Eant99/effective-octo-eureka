<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目查询</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的项目名称，然后<strong>双击</strong>这条信息;
			        </div>
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>审核状态</th>
				            <th>项目编号</th>
				            <th>项目名称</th>			            
				            <th>预算金额(元)</th>

				            <th>初审人</th>
				            <th>初审日期</th>
				            <th>复审人</th>
				            <th>复审日期</th>
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
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "SHZT",defaultContent:"已通过"},
       {"data": "XMBH",defaultContent:"0001"},
       {"data": "XMMC",defaultContent:"",
			"render" : function(data, type, full, meta) {
				return '<input type="hidden"  class="keyId" name="xmmc" bz="'+full.BZS+'" value="建设项目">建设项目';
			}},     
       {"data": "YSJE",defaultContent:"1000.25","class":"text-right",
				"render" : function(data, type, full, meta) {
					return '<input type="hidden"  class="keyId" name="ysje" bz="'+full.BZS+'" value="1000.25">1000.25';
				}},        
       {"data": "CSR",defaultContent:"超级管理员",},
       {"data": "CSRQ",defaultContent:"2017-10-19","class":"text-center"},
       {"data": "FSR",defaultContent:"",
			"render" : function(data, type, full, meta) {
				return '<input type="hidden"  class="keyId" name="fsr" bz="'+full.BZS+'" value="超级管理员">超级管理员';
			}},
       {"data": "FSRQ",defaultContent:"2017-10-19","class":"text-center"},
      
     ];
    //table = getDataTableByListHj("mydatatables","${ctx}/xmsb/xmsb/getPageList",[2,'asc'],columns,0,1,setGroup);
  table = $('#mydatatables').DataTable({
		        ajax: {
		            url: "${ctx}/xmsb/xmsb/getPageList"//获取数据的方法
		        },
		        "lengthMenu":getTopFrame().window.sessionRowNumLength,
		        "order": [ 2, 'asc' ],
		        "serverSide": true,
		        "columns": columns,
		        "language":{
		            "search":"",
		            "searchPlaceholder":"请输入项目名称"
		        },
		        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
		    });
			$("input[type=search]").parent("label").addClass("zhxxcx");
			$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	//查询
	$("#btn_search").click(function(){
		window.location.reload();
	});
	 //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val1 = $(this).find("[name='xmmc']").val();
    	var val2 = $(this).find("[name='ysje']").val();
    	var val3 = $(this).find("[name='fsr']").val();
    	if(val1==''||val1==null||val1=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		
    		getIframeControl("iframe_011001","txt_xmmc").val(val1);
    		getIframeControl("iframe_011001","txt_ysje").val(val2);
    		getIframeControl("iframe_011001","txt_fzr").val(val3);
    		winId = top.layer.getFrameIndex(parent.window.name);
        	//alert();
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