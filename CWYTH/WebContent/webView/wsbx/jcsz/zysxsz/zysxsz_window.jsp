<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>学籍信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<!-- <style type="text/css">
	/* body{
		overflow-x:hidden;
	} */
</style> -->
<body class="contrast-red" >
<div id='wrapper'>
<section>
    <div class='row' id='content-wrapper'>
        <div class='col-md-12'>
             <div class="box">
             	<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				<div class="search-simple">
					<div class="form-group">
						<label>模块编号</label>
						<input type="text" id="txt_xmbh" class="form-control" name="mkbh" table="K" placeholder="请输入模块编号">
					</div>
					<div class="form-group">
						<label>模块名称</label> 
						<input type="text" id="txt_xmmc" class="form-control" name="mkmc" table="K" placeholder="请输入模块名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
				</div>
			</form>
		</div>
             
                 <div class='box-content' style="padding-bottom: 0; overflow:visible;">
                 	<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的信息，然后<strong>双击</strong>这条信息;
			          	<c:if test="${param.from == 'wxjfhb'}"><br>&emsp;&emsp;&emsp;（2）当前为<strong><%=Constant.MR_YEAR()%>年</strong>可进行经费划拨的部门。</c:if>
			        </div>
			        <hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>模块编号</th>
					          <th>模块名称</th>
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
	var winId = getTopFrame().layer.getFrameIndex(window.name);

	//列表数据
	var rBtnXh = 0;//单选框序号
    var columns = [
       {"data": "MKBH",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
        {"data" : "MKBH",defaultContent : "","render" : function(data, type, full, meta) {
				return '<input type="hidden"  class="keyId" name="mkbh" bz="'+full.BZS+'" value="' + data + '">'+data+'';
						},"width":40
		   },
		 {"data" : "MKMC",defaultContent : "","render" : function(data, type, full, meta) {
				return '<input type="hidden"  class="keyId" name="mkmc" bz="'+full.BZS+'" value="' + data + '">'+data+'';
						}
		   }
  
      
     ];
	
    table = getDataTableByListHj("mydatatables","${ctx}/zysxsz/getMKBList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
    
    
//     table = getDataTableByListHj("mydatatables","${ctx}/json/wsbx/jcsz/zysxsz/zysxsz_list.json",[2,'asc'],columns,0,1,setGroup);
    
   // table = getDataTableByListHj("mydatatables","http://192.168.10.155/apis/app/getMkb",[2,'asc'],columns,0,1,setGroup);
	    
	 // table = getDataTableByListHj("mydatatables","${ctx}/json/wsbx/jcsz/zysxsz/zysxsz_list1.json",[2,'asc'],columns,0,1,setGroup);


	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val="";
		var val = $(this).find("[name='mkbh']").val();
	    var val1 = $(this).find("[name='mkmc']").val();
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
    		getIframeControl("${param.pname}","zysx_mc").val(val1);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
           //var winId = getTopFrame().layer.getFrameIndex(window.name);
        	close(winId);
    	}
    });
    if("${param.windowModel}" == "1"){
	   	$("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:20px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");
	}else{
    	$("div.button").prop("innerHTML","单位信息列表").css("font-size","14px");
    }
  	//确定按钮
   	$("#btn_sure").on("click",function(){
   		var checkbox = $("#mydatatables").find("[name='dwbh']").filter(":checked");
   		if(checkbox.length == 0){
   			alert("请先选择单位！");
   	   		return false;
   		}else{
	   		var selDwbhS = [];
	   		checkbox.each(function(){
	   			selDwbhS.push($(this).val());
	   		});
	   		getIframeControl("${param.pname}","${param.controlId}").val(selDwbhS.join(";"));
	    	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
	    	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
	    	close(winId);
   		}
   	});
	//取消
   	$("#btn_cancel").on("click",function(){
   		close(winId);
   	});  
});
</script>
</body>
</html>