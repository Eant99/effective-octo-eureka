﻿<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类选择弹窗</title>	
<style type="text/css">
	.x-panel-body{
		overflow-X:hidden !important;
	}
</style>
</head>
<body>
<%@include file="/static/include/public-tree-css.inc"%>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
var jsons=[];
jsons="${json}".split("&");
//配置iframeUlr
var yqmc = "${yqmc}";
var mkbh = "${mkbh}";
var dqflh = "${dqflh}";
var _pageUrl="${pageContext.request.contextPath}/webView/window/flxx/flxzList.jsp?&post=${post}&yqmc=${yqmc}&pname=${param.pname}&flmc=${param.flmc}&controlId=${param.controlId}&Next=${param.Next}&windowModel=${param.windowModel}&dqflh=${dqflh}&mkbh=${mkbh}&ysdh=${param.ysdh}&lyr=${lyr}&dl=${dl}&flag=${flag}";
//权限标志
var _type="self";
Ext.onReady(function(){
	var root = new Ext.tree.TreeNode({
		text : '${tree_text}',
		id : 'extjs.org.cn',
		autoScroll: true,
        expanded:true,
        animate: true
	});
	if('${dl}'==''){
		for(var i=0;i<jsons.length;i++){
			var json=jsons[i].split(",");
			var j = new Ext.tree.AsyncTreeNode({id:json[0],text:json[1],dmjc:json[2],ffldm:json[3],czflmc:json[4],flbm:json[5],flh:json[7],leaf:true,expanded:false});
			root.appendChild(j);
		}
	}else{
		for(var i=0;i<jsons.length;i++){
			var json=jsons[i].split(",");
			var j ="";
			if(json[6]=='1'){
				j = new Ext.tree.AsyncTreeNode({id:json[0],text:json[1],dmjc:json[2],ffldm:json[3],czflmc:json[4],flbm:json[5],flh:json[7],leaf:true,expanded:false});
			}else{
				j = new Ext.tree.AsyncTreeNode({id:json[0],text:json[1],dmjc:json[2],ffldm:json[3],czflmc:json[4],flbm:json[5],flh:json[7],expanded:false});
			}
			root.appendChild(j);
		}
	}
	// shorthand
	var Tree = Ext.tree;
	var tree = new Tree.TreePanel({
		id:'left_tree',
		width:220,
		region:'west',
		header: true,
		collapsible:true,
		title:"分类树",
        split:true,
        border: true,
        lines: false,
        frame : true, 
        singleClickExpand:true,
        autoScroll:true,
		animate:true,
		enableDD:false,
		rootVisible : true,
		containerScroll: true,
		draggable:false,
		root:root,
        text:'【${fns:getRyXm()}】${tree_text}',
        listeners: {
	        "beforeload":function(node){
	            node.loader=new Ext.tree.TreeLoader({  
	                url:"${pageContext.request.contextPath}/zcfltree/zcflTree",
	                baseParams:{
	                	FLH:node.attributes.flh,
	                	dmjc:node.attributes.dmjc
	                }
	            });  
	        },
            "click":function(node){
            	if('${dl}'!=''){
            		$("#selectFlh").attr("src",""+_pageUrl+"&flbm=" + node.attributes.flbm);
            	}else{
            		$("#selectFlh").attr("src",""+_pageUrl+"&flh=" + node.attributes.flh+"&ffldm="+node.attributes.ffldm);
            	}
                
             },
	             "dblclick":function(node){
	 	         	var bh=node.attributes.flh;//因为分类号有重复的，所以用的使用不能使用flh作为id
	 				var mc = node.text;
	 	         	mc = mc.substring(mc.indexOf("]")+1,mc.length);
	 				$.ajax({
	 					type:"post",
	 		    		url:"${pageContext.request.contextPath}/zcfltree/checkEndFlh",
	 		    		data:"flh="+bh+"&lyr=${lyr}",
	 					dataType:"json",
	 					success:function(val){
	 						var URL="";
	 				    	if("plfz"=='${flag}'){
	 				    		URL= "${pageContext.request.contextPath}/zcfltree/checkSftj_plfz?flh="+bh+"&dqflh="+dqflh;
	 				    	}else{
	 				    		URL= "${pageContext.request.contextPath}/zcfltree/checkSftj?flh="+bh+"&dqflh="+dqflh;
	 				    	}
	 						if(val.success){
	 							$.ajax({
	 								//url:"${pageContext.request.contextPath}/zcfltree/checkSftj?flh="+bh+"&dqflh="+dqflh,
	 								url:URL,
	 								type:"post",
	 								success:function(val){
	 									if(val=="false" && "${param.Next}"=="F"){
	 										getIframWindow("${param.pname}").location.href="${pageContext.request.contextPath}/yshd/goZcflPage?flh="+bh+"&flmc="+mc+"&czfl="+node.attributes.czflmc+"&bzdm="+node.attributes.czbzdm+"&yqmc="+mc.substring(mc.indexOf("]")+1,mc.length)+"&operateType=C&dqflh="+dqflh+"&mkbh="+mkbh;
	 									}else if(val=="false" && "${param.Next}"=="N"){//需要跳转，但是又不允许跳转，例如：资产建账归口审核，选择分类号
	 										alert("当前分类与选中的分类信息项差距较大，不允许选择！");
	 									}else{
	 										if("${param.Next}"=="C"){
	 											getIframeControl("${param.pname}","${param.controlId}").val("(" + bh + ")" + mc);
	 										}else{
		 		 					    		if("plfz"=='${flag}'){
		 		 									getIframeControl("${param.pname}","${param.controlId}").val("("+bh+")"+mc);
		 		 								}else{
		 		 									getIframeControl("${param.pname}","${param.controlId}").val(bh);
		 		 								}
		 		 					    		if(getIframeControl("${param.pname}","txt_yqmc").length > 0){
		 				 							getIframeControl("${param.pname}","txt_yqmc").val(mc);
		 				 							getIframeControl("${param.pname}","txt_yqmc").focus();//手动触发验证
		 								        	getIframeControl("${param.pname}","txt_yqmc").trigger("blur");//手动触发验证
		 		 					    		}
		 		 								if(getIframeControl("${param.pname}","sp_czfl").length > 0){
		 		 									getIframeControl("${param.pname}","sp_czfl").prop("innerHTML",node.attributes.czfl);
		 		 								}
		 		 								if(getIframeControl("${param.pname}","txt_flmc").length > 0){
		 		 									getIframeControl("${param.pname}","txt_flmc").val(mc);
		 				 							getIframeControl("${param.pname}","txt_flmc").focus();//手动触发验证
		 								        	getIframeControl("${param.pname}","txt_flmc").trigger("blur");//手动触发验证
		 		 								}
		 		 								if(getIframeControl("${param.pname}","txt_czfl").length > 0){
		 		 									getIframeControl("${param.pname}","txt_czfl").val(node.attributes.czflmc);
		 				 							getIframeControl("${param.pname}","txt_czfl").focus();//手动触发验证
		 								        	getIframeControl("${param.pname}","txt_czfl").trigger("blur");//手动触发验证
		 		 								}
		 		 								if(getIframeControl("${param.pname}","hid_bzdm").length > 0){
		 		 									getIframeControl("${param.pname}","hid_bzdm").val(node.attributes.czbzdm);
		 		 								}
	 										}
	 									}
	 									var post = "${post}";
	 									var winId;
	 									if(post=='A'){
	 										winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	 									}else{
	 										winId = getTopFrame().layer.getFrameIndex(window.name);
	 									}
			 					    	getTopFrame().close(winId);
	 								}
	 							});
	 						}else{
	 							alert(val.msg);
	 						}
	 					}//,// 	 					beforeSend:function(){
//  	 						idx = loading(2);
// 	 					}

	 				});
	 	         }
           }
    });
     var right=new Ext.Panel({
         region: "center",
         title:"",
         autoScroll:false,
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='selectFlh'></iframe>",
         border: true
     });
	/*创建窗体分割器*/
     new Ext.Viewport({
 	    layout: "border",
 	    items: [tree,right]
 	});
     var str = "<table><tr><td>&ensp;大类&ensp;</td><td><div class='ext-tree-search'><select name='dl' id='dl' class='form-control input-radius' style='margin-top:2px;'><option value=''></option><c:forEach var='list' items='${list}'><option value='${list.FLBM}' <c:if test='${dl == list.FLBM}'>selected</c:if>>${list.FLMC}</option></c:forEach></select></div></td></tr></table>";
     $("#left_tree .x-panel-body").prepend(str);
     $("[name='dl']").change(function(){
    	 window.location.href="${pageContext.request.contextPath}/zcfltree/goWindowFlxz?yqmc=${yqmc}&pname=${param.pname}&controlId=${param.controlId}&post=A&lyr=${param.lyr}&dl="+$(this).val();
     });
});
</script>
</body>
</html>