<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>公开查询--树</title>
<%@include file="/static/include/public-tree-css.inc"%>
<%@include file="/static/include/public-tree-js.inc"%>
</head>
<body>
<script type="text/javascript">
$(function() {
	var _pageUrl = "${pageContext.request.contextPath}/select/goListPage?mkbh=${mkbh}&root=${map.jdbh}&jdbh=";
	var root = new Ext.tree.AsyncTreeNode({
        text:"${map.mc}",
        id:"${map.jdbh}",
        leaf:false,
        expanded:true,
        loader:new Ext.tree.TreeLoader({
            url:"${pageContext.request.contextPath}/select/getTree",
            listeners:{
                "beforeload":function(treeloader,node){
                    treeloader.baseParams ={parentid:node.id,mkbh:"${mkbh}"};
                }
            }
        })
    });

	var left = new Ext.tree.TreePanel({
		id:'left_tree',
		width:200,
	    region: "west",
	    bodyStyle:"background:#FFFFFF;",
	    root: root,
	    rootVisible:false,
	    border: true,
	    animate: true,
		lines: true,
	    frame:true,
	    enableDD: false,
	    autoScroll:true,
	    split:true,
	    collapsible: true,
	    margins:'1 1 1 1',
	    cmargins:'1 1 1 1',
	    listeners:{
			"click":function(node){
				$("#iframe_list_${mkbh}").attr("src",_pageUrl + node.id);
			}
	    }
	});
	var right = new Ext.Panel({
         region: "center",
         autoScroll:false,
         border: true,
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='no' src='" + _pageUrl + "${map.jdbh}' id='iframe_list_${mkbh}' name='iframe_list_${mkbh}'></iframe>"
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