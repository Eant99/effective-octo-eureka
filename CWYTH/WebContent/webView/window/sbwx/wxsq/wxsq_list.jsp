<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>维修申请</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
</head>
<body>
    <div class='row' id='content-wrapper'>
        <div class='col-md-12'>
             <div class="box">
                 <div class='box-content' style="padding-bottom: 0; overflow:visible;">
                 	<div class="alert alert-info">
			          	<strong>提示：</strong>先找到需要的申请，然后<strong>双击</strong>这条信息
			        </div>
			        <hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>申请编号</th>
					          <th>申请人</th>
					          <th>申请单位</th>
<!-- 					          <th>申请时间</th> -->
					          <th>预估维修费用</th>
<!-- 					          <th>维修商编号</th> -->
					          <th>维修商名称</th>
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
<script>
$(function (){
	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	
   	$('#mydatatables').DataTable({
        "ajax": {
            url:"${pageContext.request.contextPath}/window/wxsq?dwbh=${param.dwbh}"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [3,'asc'],
        "serverSide": true,
        "columns":  [
        	{"data": "REPORTID",orderable:false, "render": function (data, type, full, meta){
        		return "<input type='checkbox' class='keyId' name='wxsq' value='"+data+"' wxsbh='" + full.WXSBH + "' wxsmc='" + full.WXSMC + "' sqrbh='" + full.REPLYPERSON + "' sqr='" + full.REPLYPERSONMC + "' sqdwbh='" + full.REPLYCOMPANY + "' sqdw='" + full.REPLYCOMPANYMC + "' wxsdh='" + (!full.PHONE?"":full.PHONE) + "' wxsdz='" + (!full.ADDRESS?"":full.ADDRESS) + "' yjfy='" + full.ABOUTMONEY + "'>";
			},"width":10,'searchable': false},
            {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
            },"width":41,"searchable": false,"class":"text-center"},
            {"data": "REPORTID",defaultContent:""},
            {"data": "REPLYCOMPANYMC",defaultContent:""},
            {"data": "REPLYPERSONMC",defaultContent:""},
//             {"data": "REPLYTIME",defaultContent:""},        
            {"data": "ABOUTMONEY",defaultContent:""},        
//             {"data": "WXSBH",defaultContent:""},
            {"data": "WXSMC",defaultContent:""}
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入申请信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
	});
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var sqdh = getIframeControl("${param.pname}","txt_reportid").val();
    	var val = $(this).find("[name='wxsq']").val();
    	if(sqdh!=val){
    		getIframeControl("${param.pname}","zc__").remove();
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
        	
        	getIframeControl("${param.pname}","txt_repaircompany").val($(this).find("[name='wxsq']").attr("sqdw"));
        	getIframeControl("${param.pname}","txt_wxcj").val($(this).find("[name='wxsq']").attr("wxsmc"));
        	getIframeControl("${param.pname}","hid_wxcjbh").val($(this).find("[name='wxsq']").attr("wxsbh"));
        	getIframeControl("${param.pname}","txt_repaircontact").val($(this).find("[name='wxsq']").attr("wxsdh"));
        	getIframeControl("${param.pname}","txt_repairaddress").val($(this).find("[name='wxsq']").attr("wxsdz"));
//         	getIframeControl("${param.pname}","txt_repairmoney").val($(this).find("[name='wxsq']").attr("yjfy"));
        	
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("keydown");//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("keyup");//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	
    	}
    	
    	var winId = top.layer.getFrameIndex(parent.window.name);
    	close(winId);
    });
	
    if("${param.windowModel}" == "1"){
	   	$("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:20px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");
	}
    else{
    	$("div.button").prop("innerHTML","维修申请信息列表").css("font-size","14px");
    }
    
  	//确定按钮
   	$("#btn_sure").on("click",function(){
   		var checkbox = $("#mydatatables").find("[name='dwbh']").filter(":checked");
   		if(checkbox.length == 0){
   			alert("请先选择单位！");
   	   		return false;
   		}
   		else{
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