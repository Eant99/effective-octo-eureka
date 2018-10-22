<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>反查分类弹窗</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
.icon-reset {
    line-height: 20px !important;
    color: rgb(105, 105, 105);
    position: absolute;
    top: 12px !important;
    right: 6px !important;
}
	.box {
    margin-left: 2px !important;
    margin-right: 0px !important;
    position: relative;
    margin-top: 0px !important;
    margin-bottom: 33px;
    background-color: white;
    padding: 0px !important;
    border-radius: 2px;
}
</style>
<body>
<div class="fullscreen">
	<div class="container-fluid">
		<div class="list" id="fcjg">
			<div class='box-content' style="padding-bottom: 0px; overflow:visible;">
				<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;"> 
		       	<strong>提示：</strong>先找到需要的分类信息，然后<strong>双击</strong>这条信息
			    </div>
			</div>
			<hr class="hr-normal">
            <div class='responsive-table'>
               <div class='scrollable-area'>
                  <table id="example" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
				      <thead>
				      <tr id="header">
				      	  <th><input type="checkbox" value="" class="select-all"/></th>
				          <th>分类号</th>
				          <th>分类名称</th>
				          <th>财政分类</th>
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
<%@include file="/static/include/public-list-js.inc"%>
<script type="text/javascript">
$(function(){
 	table = $('#example').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/cyflsz/getPageFlxxList"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":[10],
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "columns": [
			{"data": "FLH",orderable:false, 'render': function (data, type, full, meta){
				return "<input type='checkbox' class='keyId' name='flh' value='"+data+"'><input type='hidden' class='keyId' name='flmc' value='"+full.FLMC+"'>";
			},"width":10,'searchable': false},
            {"data": "FLH",defaultContent:""},
            {"data": "FLMC",defaultContent:""},
            {"data": "FFLDM",defaultContent:"",'render': function (data, type, full, meta){
            	return full.FFLMC;
		    }}
        ],
        "language":{
            "lengthMenu": "每页_MENU_条记录",
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
            "searchPlaceholder":"请输入资产分类信息关键字"
        },
        "dom":"<'row'<'col-md-4 col-sm-4 col-xs-4'i><'col-md-3 col-sm-3 col-xs-3 button'><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12'p>>",
        "initComplete": function(){
        }
    });
 	 $("input[type=search]").addClass("zhxxcx");
     $("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width","180px");
     $(".icon-chaxun").css("position","absolute").css("top","13px").css("right","43px");
    //双击事件
    $(document).on("dblclick","#example tr:not(#header)",function(){
    	var flh = $(this).find("[name='flh']").val();
    	var flmc = $(this).find("[name='flmc']").val();
   		getIframeControl("${param.pname}","${param.controlId}").val(flh);
       	getIframeControl("${param.pname}","txt_flmc").val(flmc);
	    var winId = top.layer.getFrameIndex(window.name);
    	close(winId);
    });
});
</script>
</body>
</html>