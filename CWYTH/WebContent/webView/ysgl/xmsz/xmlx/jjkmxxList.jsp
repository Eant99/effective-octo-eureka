<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>经济科目设置</title>

<%@include file="/static/include/public-manager-css.inc"%> 

</head>
<body>
<div class="fullscreen">
	<section>
		<div class="container-fluid">
			<div class='box-content' style="margin-top: 10px; overflow:visible;">
				<c:choose>
					<c:when  test="${param.dx == 'T' }">
						<div class="alert alert-info">
				          	<strong>提示：</strong>请先找到需要的数据，然后<strong>双击</strong>这条信息。
				        </div>
					</c:when >
					<c:otherwise>
						<div class="alert alert-info">
				          	<strong>提示：</strong>请至少选择一条数据，然后点击确定按钮，或先找到需要的数据，然后<strong>双击</strong>这条信息。
				        </div>
				        <button type="button" class="btn btn-default" id="btn_submit">确定</button>
					</c:otherwise>
				</c:choose>
				
				
		        <hr class="hr-normal" id="hr">
				<div class='responsive-table'>
					<div class='scrollable-area'>
				        <table id="mydatatables" class="table table-striped table-bordered">
							<thead>
				 				<tr>
					 				<th><input type="checkbox" class="select-all"/></th>
					 				<th>序号</th>
								    <th>科目编号</th>
								    <th>科目名称</th>
									<th>科目级次</th>	
		
								    <th>是否启用</th>	
								    <th>说明</th>						    					  
					   			</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
<%@include file="/static/include/public-list-js.inc"%> 
<%@include file="/static/include/public-list-css.inc"%>
<script >
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	var columns = [
		{"data": "guid",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" name="guid" kmbh="'+full.KMBH+'" class="keyId" value="' +full.KMMC + '" guid = "'+full.GUID+'" kmmc = "'+full.KMMC+'">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "KMBH",defaultContent:"","class":"hj"},
	   	{"data": "KMMC",defaultContent:""},
	   	{"data": "KMJC",defaultContent:""},

		{"data": "QYF",defaultContent:""},
		{"data": "SM",defaultContent:""}
	];
  
    table = $('#mydatatables').DataTable({
        ajax: {
            url: "${ctx}/xmlx/getJjkmPageList?treeDm=${param.kmbh}&treesearch=${treesearch}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": columns,
        "language":{
            "search":"",
            "searchPlaceholder":"请输入科目编号或名称"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
   	
	  $("#btn_submit").click(function(){
	    	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	    	var checkbox = $(".keyId").filter(":checked");
	    	if(checkbox.length==0){
	    		alert("请至少选择一条信息！");
	    		return;
	    	}else{
	    		var kmbh = [];
	        	var kmmc=[];	        
	        	$.each(checkbox,function(i,v){
	        		var kmbhList = $(this).attr("kmbh");
	        		var kmmcList = $(this).attr("kmmc");
	   
	        		kmbh.push(kmbhList);
	        		kmmc.push(kmmcList);
	        	});

	        		getIframWindow("${param.pname}").addjjflkm(kmbh,kmmc);
	    
	        	
	        	close(winId);
	    	}
	    	
	    });
	$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
		var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
    	var checkbox = $(this).find(".keyId");
    	if(checkbox.length==0){
    		alert("请至少选择一条信息！");
    		return;
    	}else{
    		var kmbh = [];
        	var kmmc=[];	        
        	$.each(checkbox,function(i,v){
        		var kmbhList = $(this).attr("kmbh");
        		var kmmcList = $(this).attr("kmmc");
   
        		kmbh.push(kmbhList);
        		kmmc.push(kmmcList);
        	});

        		getIframWindow("${param.pname}").addjjflkm(kmbh,kmmc);
    
        	
        	close(winId);
    	}
	});
	
});

</script>
</body>
</html>