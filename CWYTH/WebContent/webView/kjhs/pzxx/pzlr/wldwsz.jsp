<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>往来单位信息</title>
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
		          	<strong>提示：</strong>先找到需要的信息，然后<strong>双击</strong>这条信息。
		        </div>
		        <hr class="hr-normal" id="hr">
                <div class='responsive-table'>
                    <div class='scrollable-area'>
                    <table id="mydataTables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					      <thead>
					      <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>往来编号</th>
					          <th>单位名称</th>
					          <th>单位地址</th>
					          <th>联系人</th>
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
            url: "${pageContext.request.contextPath}/window/getWldwPageList"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "WLBH",orderable:false, 'render': function (data, type, full, meta){
	             return "<input type='checkbox' class='keyId' data-wlbh='"+data+"' data-dwmc='"+full.DWMC+"'>";
		    },"width":10,'searchable': false
		    },
		    {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
            },"width":41,"searchable": false,"class":"text-center"},
            {"data": "WLBH",defaultContent:""},
            {"data": "DWMC",defaultContent:""},
            {"data": "DWDZ",defaultContent:""},
            {"data": "LXR",defaultContent:""},
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入往来编号"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$("div.button").prop("innerHTML","往来单位信息列表").css("font-size","14px");
    //双击事件
    $(document).on("dblclick","#mydataTables tr:not(#header)",function(){
    	var wlbh = $(this).find(".keyId").data("wlbh");
    	var dwmc = $(this).find(".keyId").data("dwmc");
    	if(wlbh==''||wlbh==null){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControlForPzfz("${param.pname}","${param.controlId}").val("("+wlbh+")"+dwmc);
    		getIframeControlForPzfz("${param.pname}","${param.controlId}").focus();//手动触发验证
    		getIframeControlForPzfz("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	var winId = top.layer.getFrameIndex(window.name);
        	close(winId);
    	}
    });
});
</script>
</body>
</html>