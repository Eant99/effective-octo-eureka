<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>单位信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
	body{
		overflow-x:hidden;
	}
</style>
<body class="contrast-red" >
<div id='wrapper'>
<section>
    <div class='row' id='content-wrapper'>
        <div class='col-md-12'>
             <div class="box">
                 <div class='box-content' style="padding-bottom: 0; overflow:visible;">
                 	<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的单位，然后<strong>双击</strong>这条信息,或先选中需要的信息，然后点击<strong>确定</strong>按钮;
			          	<c:if test="${param.from == 'wxjfhb'}"><br>&emsp;&emsp;&emsp;（2）当前为<strong><%=Constant.MR_YEAR()%>年</strong>可进行经费划拨的部门。</c:if>
			        </div>
			        <button type="button" class="btn btn-primary" id="btn_qd">确定</button>
			        <hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>部门号</th>
					          <th>部门名称</th>
					          <th>上级单位</th>
					          <th>单位性质</th>
					          <th>地址</th>
					          <th>排序序号</th>
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
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	var columns = [
	           	{"data": "DWBH",orderable:false, "render": function (data, type, full, meta){
	        		return '<input type="checkbox" class="keyId" data-dwbh="'+data+'" name="dwbh" value="' + "("+full.BMH+")"+full.MC + '">';
				},"width":10,'searchable': false},
	            {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	            	return data;
	            },"width":41,"searchable": false,"class":"text-center"},
	            {"data": "BMH",defaultContent:""},
	            {"data": "MC",defaultContent:""},
	            {"data": "SJDW",defaultContent:""},
	            {"data": "DWXZ",defaultContent:""},            
	            {"data": "DZ",defaultContent:""},
	            {"data": "PXXH",defaultContent:""}
	        ];
	table = $('#mydatatables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/window/dwxx?dwbh=${param.dwbh}&treesearch=${param.treesearch}&from=${param.from}"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 7, 'asc' ],
        "serverSide": true,
        "columns": columns,
        "language":{
            "search":"",
            "searchPlaceholder":"请输入部门信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val = $(this).find("[name='dwbh']").val();
    	if(val.indexOf(")")>0){
    		val = val.substring(1,val.indexOf(")"));
    	}
    	if(val==''||val==null){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControlForPzlr("${param.pname}","${param.controlId}").val(val);
    		getIframeControlForPzlr("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframWindow("${param.pname}").triggerChange("${param.controlId}");//手动触发验证
        	close(winId);
    	}
    });
    $(document).on("click","#btn_qd",function(){
    	var checkbox = $(".keyId").filter(":checked");
    	if(checkbox.length==0){
    		alert("请选择一条信息！");
    		return;
    	}else if(checkbox.length>1){
    		alert("只能选择一条信息！");
    		return;
    	}else{
    		var val = $("#mydatatables").find(".keyId").filter(":checked").val();
    		if(val.indexOf(")")>0){
        		val = val.substring(1,val.indexOf(")"));
        	}
    		getIframeControlForPzlr("${param.pname}","${param.controlId}").val(val);
    		getIframeControlForPzlr("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframWindow("${param.pname}").triggerChange("${param.controlId}");//手动触发验证
        	close(winId);
    	}
    })                                                
    if("${param.windowModel}" == "1"){
	   	$("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:20px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");
	}else{
    	$("div.button").prop("innerHTML","单位信息列表").css("font-size","14px");
    }
});
</script>
</body>
</html>