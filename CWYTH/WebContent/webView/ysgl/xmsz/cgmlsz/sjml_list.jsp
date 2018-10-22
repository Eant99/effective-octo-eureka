<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>采购目录信息</title>
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
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的费用科目，然后<strong>双击</strong>这条信息;
			          	<%-- <c:if test="${param.from == 'wxjfhb'}"><br>&emsp;&emsp;&emsp;（2）当前为<strong><%=Constant.MR_YEAR()%>年</strong>可进行经费划拨的部门。</c:if> --%>
			        </div>
			        <hr class="hr-normal" id="hr">
                    <div class='responsive-table'>
	                    <div class='scrollable-area'>
		                    <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
								<thead>
									<tr>
										<th><input type="checkbox" class="select-all" /></th>
										<th>序号</th>
										<th>目录代码</th>
										<th>目录名称</th>
										<th>上级目录</th>
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
	 //var columns = 
	table = $('#mydatatables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/cgmlsz/getTreePageList?mldms=${param.mldm}"//获取数据的方法
        },
        
        "pagingType":"full_numbers",
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
                return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'" flag = "('+full.MLDM+')" mlmc="'+full.MLMC+'" >';
              },"width":10,'searchable': false},
              {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
		          	return data;},"width":41,"searchable":false,"class":"text-center"},
		       {"data": "MLDM",defaultContent:""},
		       {"data": "MLMC",defaultContent:""},
		       {"data": "SJML",defaultContent:""},                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
            ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入目录代码或名称"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$("div.button").prop("innerHTML","科目信息列表").css("font-size","14px");
    //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var id = $(this).find("[name='guid']").val();
    	var mc1 = $(this).find("[name='guid']").attr("flag");
    	var mc2 = $(this).find("[name='guid']").attr("mlmc");
    	var mc = mc1+mc2;
    	if(mc==''||mc==null||mc=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(mc);
    		getIframeControl("${param.pname}","${param.text_id}").val(id);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	close(winId);
    	}
    });
    if("${param.windowModel}" == "1"){
	   	$("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:20px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");
	}else{
    	$("div.button").prop("innerHTML","费用科目信息列表").css("font-size","14px");
    }
  
});
</script>
</body>
</html>