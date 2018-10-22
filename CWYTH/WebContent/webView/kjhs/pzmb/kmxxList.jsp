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
		          	<strong>提示：</strong>请至少选择一条数据，然后点击确定按钮，或先找到需要的数据，然后<strong>双击</strong>这条信息。
		        </div>
		        <button type="button" class="btn btn-default" id="btn_submit">确定</button>
		        <hr class="hr-normal" id="hr">
                <div class='responsive-table'>
                    <div class='scrollable-area'>
                    <input type="hidden" name="guid" value="${guid}"/>
                    <table id="mydataTables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					      <thead>
					      <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>科目编号</th>
					          <th>科目名称</th>
					         <!--  <th>科目属性</th>		 -->	   
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
        	                             
            url: "${pageContext.request.contextPath}/pzmb/getPageList1?treedwbh=${dwbh}&kmnd=${kmnd}&dm=${dm}&jb=${jb}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	             return "<input type='checkbox' class='keyId' name='guid' kmbh='"+full.KMBH+"' kmmc='"+full.KMMC+"'  value='"+data+"'>";
		    },"width":10,'searchable': false
		    },		    
		    {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
            },"width":41,"searchable": false,"class":"text-center"},
            {"data": "KMBH",defaultContent:""},
            {"data": "KMMC",defaultContent:""},
           /*  {"data": "KMSX",defaultContent:""} */            
            
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入科目编号或名称"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });

 $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$("div.button").prop("innerHTML","科目信息列表").css("font-size","14px");
	
	var xzid = "${xzid}";
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
	        	getIframWindow("${param.pname}").addkjkm(kmbh,kmmc,xzid);
	        	close(winId);
	    	}
	    	
	    });
	    $(document).on("dblclick","#mydataTables tr:not(#header)",function(){
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
	        	getIframWindow("${param.pname}").addkjkm(kmbh,kmmc,xzid);
	        	close(winId);
	    	}
	    });
});
function reloadList() {
	table.ajax.reload();
}
</script>
</body>
</html>