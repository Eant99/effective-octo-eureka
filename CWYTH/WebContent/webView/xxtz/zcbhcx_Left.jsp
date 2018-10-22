<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改已审核资产</title>
<%@include file="/static/include/public-manager-css.inc"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/ext/ext-all.css"/>
</head>
<body class="sm_edit_body">
	<div id="d_left" style="overflow:auto;">
		<div id="div_bh" style="margin-left:15px;margin-top:20px;">请输入资产编号</div>
		<div class='ext-tree-search input-group' style="margin-left:10px;width: 220px;">
			<input class='form-control' type='text' id='txt_yqbh' style="background:white;" >
			<span class='input-group-btn'>
				<button type='button' title='点击搜索信息' id='btn_search' class='btn'><i class='faw fa-search'></i></button>
			</span>
		</div>
		<div style="color:red;width:90%;margin-left:10px;margin-top:10px;">
			<span>注意：</span><br/>
			<span>1、资产只能修改验收单信息，系统自动将相应的信息修改到卡片中</span><br/><br/>
			<span>2、退回将把单据打回到归口审核处。</span><br/><br/>
			<span>3、对于没有做过变动的原始数据可以修改原始数据的使用单位、使用人、存放地点 </span><br/><br/>
			<span>4、对于修改前记录不做备份、请修改前核实数据的正确性 </span>
		</div>
		<input type="hidden" id="txt_lx" value="0"/>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-lang-zh_CN.js"></script>
	<script type="text/javascript">
		$(function() {
			var _pageUrl="${pageContext.request.contextPath}/xxtz/getRight?yqbh="+ $("#txt_yqbh").val();
			new Ext.Viewport({
		        layout: "border",
		        items: [{
		            region: "west",
		            title:"",
		            border: false,
		            width:280,
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
		 						if(lx=="0"){
			 						$("#zcxx").attr("src","${pageContext.request.contextPath}/xxtz/goCardPage?yqbh="+ $("#txt_yqbh").val());
		 						}else{
			 						$("#zcxx").attr("src","${pageContext.request.contextPath}/xxtz/goYsdPage?ysdh="+ $("#txt_yqbh").val());
		 						}
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