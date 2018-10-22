<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产管理系统信息调整</title>
<%@include file="/static/include/public-manager-css.inc"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/ext/ext-all.css"/>
</head>
<body class="sm_edit_body">
	<div id="d_left" style="overflow:auto;">
		<div id="div_bh" style="margin-left:15px;margin-top:10px;">请输入资产编号</div>
		<div class='ext-tree-search input-group' style="margin-left:10px;margin-right:10px;">
			<input class='form-control' type='text' id='txt_yqbh' style="background:white;" >
			<span class='input-group-btn'>
				<button type='button' title='点击搜索信息' id='btn_search' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button>
			</span>
		</div>
		<input type="hidden" id="txt_lx" value="0"/>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript">
/* function sure(){
	var mkbh = "${param.mkbh}";
	alert(mkbh);
	var yqbh = $("#txt_yqbh").val();
	if(yqbh == ""){
		alert("资产编号不能为空");
	}else{
		$.ajax({
    		url:"${pageContext.request.contextPath}/xxtz/getObjectByCount?yqbh="+yqbh,
			type:"post",
			success:function(b){
				var f=$.trim(b);
				if(f=="true"){
					if(mkbh == '980301'){
						
					}else if(mkbh == '980302'){
						location.href="${pageContext.request.contextPath}/xxtz/goLookPage?yqbh="+yqbh+"&mkbh="+mkbh;
					}else if(mkbh == '980303'){
						location.href="${pageContext.request.contextPath}/xxtz/goLookPage?yqbh="+yqbh+"&mkbh="+mkbh;
					}else if(mkbh == '980304'){
						
					}
				}else{
					alert("不存在的资产编号");
				}
			}
		});
	}
} */


$(function() {
	var _pageUrl="";
	new Ext.Viewport({
        layout: "border",
        items: [{
            region: "west",
            title:"",
            border: false,
            width:210,
            autoScroll:true,
            applyTo: "d_left"
        },{
            region: "center",
            title:"",
            autoScroll:true,
            html: "<iframe frameborder='0' width='100%' height='100%' scrolling='hidden' src='" + _pageUrl + "' id='zcxx' name='zcxx'></iframe>",
            border: false
        }]
    });
   $("#d_left .x-panel-body").hide();
   
    $("#btn_search").click(function(){
    	var yqbh = $("#txt_yqbh").val();
    	var lx = $("#txt_lx").val();
  	   if(yqbh != null && yqbh != ""){
  		  $.ajax({
 				url:"${pageContext.request.contextPath}/xxtz/checkYqbh",
 				type:"POST",
 				data:"yqbh="+yqbh+"&lx="+lx,
 				success:function(result){
 					if(result.trim()== "-1"){
 						alert("不存在该资产编号或未通过财务审核！");
 						return;
 					}else if(result.trim()== "-2"){
 						alert("不存在该验收单编号或未通过财务审核！");
 						return;
 					}else if(result.trim()== "-3"){
 						alert("系统异常！");
 						return;
 					}else{
	 					$("#zcxx").attr("src","${pageContext.request.contextPath}/xxtz/goLookPage?yqbh="+ $("#txt_yqbh").val()+"&mkbh=${param.mkbh}");
 						return;
 					}
 				 },
 				 error:function(){
 					 alert("抱歉，系统出现问题！");
 				 }
 			  }); 
  	   }else{
  		   alert($("#div_bh").html());
  	   }
     });
});	
</script>
</body>
</html>