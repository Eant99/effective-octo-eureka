<%@page import="com.googosoft.util.CommonUtils"%>
<%@page import="com.googosoft.commons.LUser"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>归档范围分类树</title>
<%@include file="/static/include/public-tree-css.inc"%>
<style type="text/css">
#left .x-panel-body {
	position: static;
}
</style>
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/dwb/goGdfwflPage?mkbh=${param.mkbh}&controlId=${controlId}&controlName=${controlName}&pname=${param.pname}";
//权限标志
var _type="self";
Ext.onReady(function(){
	var root = new Ext.tree.AsyncTreeNode({
		id: "root",
        autoScroll: false,
        expanded:true,
        animate: true,
        href:_pageUrl,
        hrefTarget:"iframe_list_${param.mkbh}",
        enableDD: false,
        containerScroll: false,
        border: false, 
        icon:"${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/home.gif",
        text:'归档范围分类树',
        loader: new Ext.tree.TreeLoader({
        	//单位权限
        	 url: "${pageContext.request.contextPath}/dwb/gdfwflTree",
             listeners: {
                    "beforeload": function(treeloader, node) { 
                        treeloader.baseParams = {
                            menu:'get-xjdw', 
                            dwbh: node.id,
                            pageUrl:_pageUrl,
                            target:"iframe_list_${param.mkbh}",
                            mkbh: '${param.mkbh}',
                            method: 'POST',
                            type:_type
                         };
                    }
               }
          })
    });
     var left=new Ext.tree.TreePanel({
	     id:"left_tree",
	     region:'west',
	     width:200,
	     collapsible:true,
	     title:"归档范围分类树",
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
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='iframe_list_${param.mkbh}' name='iframe_list_${param.mkbh}'></iframe>",
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