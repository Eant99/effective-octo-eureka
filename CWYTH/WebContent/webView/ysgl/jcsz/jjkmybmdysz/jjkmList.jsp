<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta charset="utf-8"/> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>经济科目信息</title>
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
<body>
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
				<div class="alert alert-info">
		          	<strong>提示：</strong>请至少选择一条数据，然后点击确定按钮。
		        </div>
		        <button type="button" class="btn btn-default" id="btn_submit" style="background:#00acec;color: white;">确定</button>
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
           // url: "${pageContext.request.contextPath}/Jjkmybmdysz/jjkmPageList?dm=${dm}&kmbh=${param.kmbh}&flag=${param.flag}"//获取数据的方法
                   url: "${pageContext.request.contextPath}/bmysbz/getJjkmPageList?dm=${dm}&treeDm=${param.kmbh}&flag=${param.flag}"//获取数据的方法

           // url: "${ctx}/bmysbz/getJjkmPageList?treeDm=${param.kmbh}&treesearch=${treesearch}"//获取数据的方法

        },
        "pagingType":"full_numbers",
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "KMBH",orderable:false, 'render': function (data, type, full, meta){
	             return "<input type='checkbox' class='keyId' name='kmbh' kmbh='"+full.KMBH+"' kmmc='"+(full.KMMC==undefined?'':full.KMMC)+"'  value='("+data+")"+full.KMMC+"'>";
		    },"width":10,'searchable': false
		    },
		    {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
            },"width":41,"searchable": false,"class":"text-center"},
            {"data": "KMBH",defaultContent:""},
            {"data": "KMMC",defaultContent:""},
        ],
//         "language":{
//             "search":"",
//             "searchPlaceholder":"请输入科目编号或名称"
//         },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$("div.button").prop("innerHTML","经济科目信息列表").css("font-size","14px");
    $("#btn_submit").click(function(){
    	var checkbox = $("#mydataTables").find("[name='kmbh']").filter(":checked");
    	
    	if(checkbox.length==0){
    		alert("请至少选择一条信息！");
    		return;
    	}
    	var kmxx = [];
    	var kmbhs=[];
    	$.each(checkbox,function(i,v){
    		var kmbh = $(this).attr("kmbh");
    		var kmmc = $(this).attr("kmmc");
    		if(kmbh!=""){
    			kmxx.push("("+kmbh+")"+kmmc);
    			kmbhs.push(kmbh);
    		}
    	});
    	
    	var id = "${param.controlId}";
    	var type = "${param.type}";
    	
    	if("jb"==type){
    		getIframWindow("${param.pname}").addJb(kmxx,id,kmbhs);
    	}
    	var winId = getTopFrame().layer.getFrameIndex(window.parent.name);
    	getIframWindow("${param.pname}").autosave();
    	console.log("---");
    	close(winId);
    });
});
function reloadList() {
	table.ajax.reload();
}
</script>
</body>
</html>