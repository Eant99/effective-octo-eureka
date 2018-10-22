<%@page import="com.googosoft.commons.LUser"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员信息</title>
<%@include file="/static/include/public-tree-css.inc"%>
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
var jsons=[];
jsons="${json}".split("&");
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/webView/window/fjxx/fjxxList.jsp?pname=${param.pname}&controlId=${param.controlId}&Next=${param.Next}";
//权限标志
var _type="self";
Ext.onReady(function(){
	var root = new Ext.tree.TreeNode({
		text : '【<%=LUser.getRyxm()%>】按资产类别查询)',
		id : 'extjs.org.cn',
		autoScroll: true,
        expanded:true,
        animate: true
	});
	for(var i=0;i<jsons.length;i++){
		var json=jsons[i].split(",");
		var j = new Ext.tree.AsyncTreeNode({id:json[0],text:json[1],dmjc:json[2],ffldm:json[3],czflmc:json[4],flbm:json[5],flh:json[7],expanded:false});
		root.appendChild(j);
	}
	// shorthand
	var Tree = Ext.tree;
	var tree = new Tree.TreePanel({
		width:270,
		region:'west',
		header: true,
		collapsible:true,
		title:"分类树",
        split:true,
        border: false,
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
        text:'【<%=LUser.getRyxm()%>】按资产类别查询',
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
	                $("#selectFlh").attr("src",""+_pageUrl+"&flbm=" + node.attributes.flbm);
	             },
	             "dblclick":function(node){
	 	         	var bh=node.attributes.flh;//因为分类号有重复的，所以用的使用不能使用flh作为id
	 				var mc=node.text;				
	 				$.ajax({
	 					type:"post",
	 		    		url:"${pageContext.request.contextPath}/zcfltree/checkEndFlh",
	 		    		data:"flh="+bh,
	 					dataType:"json",
	 					success:function(val){
	 						close(idx);
	 						if(val.success){
	 							if("${param.Next}"=="F"){//如果next为F，则双击完成直接跳转到分类页面
	 								getIframWindow("${param.pname}").location.href="${pageContext.request.contextPath}/yshd/goZcflPage?flh="+bh+"&flmc="+mc.substring(mc.indexOf("]")+1,mc.length)+"&czfl="+node.attributes.czflmc+"&bzdm="+node.attributes.czbzdm+"&yqmc="+mc.substring(mc.indexOf("]")+1,mc.length)+"&operateType=C";
	 					    	}else{
	 					    		getIframeControl("${param.pname}","${param.controlId}").val(bh);
		 							getIframeControl("${param.pname}","txt_flmc").val(mc.substring(mc.indexOf("]")+1,mc.length));
		 							getIframeControl("${param.pname}","txt_czfl").val(node.attributes.czflmc);
		 							getIframeControl("${param.pname}","hid_bzdm").val(node.attributes.czbzdm);
		 							getIframeControl("${param.pname}","txt_yqmc").val(mc.substring(mc.indexOf("]")+1,mc.length));
	 					    	}
	 					    	var winId = getTopFrame().layer.getFrameIndex(window.name);
	 					    	getTopFrame().close(winId);
	 						}else{
	 							alert(val.msg);
	 						}
	 					},
	 					beforeSend:function(){
	 						idx = loading(2);
	 					}
	 				});
	 	         }
           }
    });
     var right=new Ext.Panel({
         region: "center",
         title:"",
         autoScroll:true,
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='selectFlh'></iframe>",
         border: false
     });
	/*创建窗体分割器*/
     new Ext.Viewport({
 	    layout: "border",
 	    items: [tree,right]
 	});
});
</script>
</body>
</html>