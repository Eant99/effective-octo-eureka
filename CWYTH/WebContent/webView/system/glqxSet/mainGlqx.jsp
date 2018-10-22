<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理权限设置</title>
</head>
<link type="text/css" href="${pageContext.request.contextPath}/static/js/plugins/ext/ext-all.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/plugins/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/plugins/ext/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/plugins/ext/ext-lang-zh_CN.js"></script>
<body>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = "${pageContext.request.contextPath}/static/js/plugins/ext/resources/images/default/s.gif";
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/webView/system/glqxSet/glqxList.jsp";
//权限标志
var _type="self";
Ext.onReady(function(){
	var root = new Ext.tree.AsyncTreeNode({
        id: "root",
        autoScroll: false,
        expanded:true,
        animate: true,
        href:_pageUrl,
        hrefTarget:"iframe_list_01",
        enableDD: false,
        containerScroll: false,
        border: false, 
        icon:"${pageContext.request.contextPath}/static/js/plugins/ext/resources/images/default/home.gif",
        text:'(管理单位)',
        loader: new Ext.tree.TreeLoader({
        	//单位权限
        	 url: "${pageContext.request.contextPath}/tree",
             listeners: {
                    "beforeload": function(treeloader, node){
                        treeloader.baseParams = {
                            menu:'get-xjdw', 
                            dwbh: node.id,
                            pageUrl:_pageUrl,
                            target:"iframe_list_01",
                            mkbh: "000000",
                            method: 'POST',
                            type:_type
                         };
                    }
               }
          })
    });
     var left=new Ext.tree.TreePanel({
	     region:'west',
	     //collapsible:true,
	     //title:"单位树",
	     width:200,
	     autoScroll:true,
	     split:true,
	     rootVisible:true,
	     border: false,
	     lines: true,
	     frame : true, 
	     singleClickExpand:true,
	     root: root
     });
     var right=new Ext.Panel({
         region: "center",
         title:"",
         autoScroll:false,
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='iframe_list_01'></iframe>",
         border: false
     });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });
});
</script>
</body>
</html>