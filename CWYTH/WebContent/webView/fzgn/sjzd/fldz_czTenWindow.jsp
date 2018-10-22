<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>十大类分类树</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/ext/ext-all.css"/>
<link href="${pageContext.request.contextPath}/static/css/public/font-awesome.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/suggest/jquerySuggest.js"></script>
<style type="text/css">
	.input-group {
		padding: 5px;
	}
	.btn.btn-default {
		padding: 3px 10px;
		height: 25px !important;
	}
</style>
</head>
<body>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = "${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/s.gif";
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/webView/fzgn/sjzd/fldz_tenFl.jsp?pname=${param.pname}&controlId=${param.controlId}&zcmc=${param.zcmc}";
//权限标志
var _type="self";
Ext.onReady(function(){
	var root = new Ext.tree.AsyncTreeNode({
        id: "root",
        autoScroll: false,
        expanded:true,
        animate: true,
        pageUrl:_pageUrl,
        target:"iframe_list_${param.mkbh}",
        enableDD: false,
        containerScroll: false,
        border: false, 
        icon:"${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/home.gif",
        text:( "财政部十大类" ),
        loader: new Ext.tree.TreeLoader({
        	 url: "${pageContext.request.contextPath}/tree/czTree",
             listeners: {
                    "beforeload": function(treeloader, node) { 
                        treeloader.baseParams = {
                            menu:'get-flxx', 
                            zcdm: node.id,
                            pageUrl:_pageUrl,
                            target:"iframe_list_${param.mkbh}",
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
	     title:"分类树",
	     autoScroll:true,
	     split:true,
	     rootVisible:true,
	     border: true,
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
         border: true
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