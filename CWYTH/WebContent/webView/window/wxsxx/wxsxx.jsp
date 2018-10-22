<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>固定资产管理系统维修商信息</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
</head>
<body>
<div id='wrapper'>
<section>
    <div class='row' id='content-wrapper'>
        <div class='col-md-12'>
             <div class="box">
                 <div class='box-content' style="padding-bottom: 0; overflow:visible;">
                 	<div class="alert alert-info">
                 		<strong>提示：</strong>先找到需要的维修商，然后<strong>双击</strong>这条信息
                 	</div>
                 	<hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					         <th><input type="checkbox" value="" class="select-all"/></th>
					         <th>序号</th>
<!-- 				        	 <th>维修商编号</th> -->
				        	 <th>维修商名称</th>
				        	 <th>法人</th>
				        	 <th>联系电话</th>
				        	 <th>联系地址</th>
<!-- 				        	 <th>经营范围</th> -->
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
	//列表数据
 	$('#mydatatables').DataTable({
        "ajax": {
            url:"${pageContext.request.contextPath}/window/wxsxx"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [3,'asc'],
        "serverSide": true,
        "scrollXOption": true,
        "scrollYOption": true,
        "columns":[
        	{"data": "GSBH",orderable:false, "render": function (data, type, full, meta){
            	return '<input type="checkbox" class="keyId" name="wxsbh" value="' + full.MC + '" fr="' + full.FR + '" dh="' + full.PHONE + '" dz="' + full.ADDRESS + '">';  	
        	},"width":10,'searchable': false},
        	{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
        	},"width":41,"searchable": false,"class":"text-center"},
//         	{"data": "GSBH",defaultContent:""},
        	{"data": "MC",defaultContent:""},
        	{"data": "FR",defaultContent:""},
        	{"data": "PHONE",defaultContent:""},
        	{"data": "ADDRESS",defaultContent:""},
//         	{"data": "BUSS",defaultContent:""},
        	{"data": "LXR",defaultContent:""}
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入维修商信息关键字"
        },
        "dom":"<'row'<'col-md-5 col-sm-5 col-xs-5'li><'col-md-7 col-sm-7 col-xs-7'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
//         "dom":"<'row'<'col-md-4 col-sm-4 col-xs-4 button'><'col-md-8 col-sm-8 col-xs-8'f>>t<'row'<'col-md-4 col-sm-4 col-xs-4'i><'col-md-8 col-sm-8 col-xs-8 pull-right'p>>"
	});
 	$("div.button").prop("innerHTML","维修商信息列表").css("font-size","14px");
 	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    //双击事件 
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val = $(this).find("[name='wxsbh']").val();
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
        	getIframeControl("${param.pname}","txt_wxsdh").val($(this).find("[name='dh']").val());
        	getIframeControl("${param.pname}","txt_wxsdz").val($(this).find("[name='dz']").val());
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	var winId = getTopFrame().layer.getFrameIndex(window.name);
        	close(winId);
    	}
    });
});
</script>
</body>
</html>