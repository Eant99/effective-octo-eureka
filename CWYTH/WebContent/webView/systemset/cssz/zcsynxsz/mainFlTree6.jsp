<%@page import="com.googosoft.util.CommonUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产使用年限设置-框架页</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/ext/ext-all.css"/>
<link href="${pageContext.request.contextPath}/static/css/public/font-awesome.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = "${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/s.gif";
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/webView/systemset/cssz/zcsynxsz/zcsynxSix_list.jsp?pname=${param.pname}&controlId=${param.controlId}&Next=${param.Next}&windowModel=${param.windowModel}";
var _type="self";//目前分类没控制权限
Ext.onReady(function(){
	var root = new Ext.tree.AsyncTreeNode({
		id: "root",
		autoScroll: false,
		expanded:true,
		animate: true,
		href:_pageUrl,
		hrefTarget:"iframe_list_yszczjcsh",
		enableDD: false,
		containerScroll: false,
		border: false, 
		icon:"${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/home.gif",
		text:("财政部六大类"),
		loader: new Ext.tree.TreeLoader({
      	//单位权限
			url: "${pageContext.request.contextPath}/tree/flTree",
          	listeners: {
              "beforeload": function(treeloader, node){
					treeloader.baseParams = {
						bzdm: node.id,//由于教育部16中flh有重复的信息，所以这里统一使用bzdm字段
						flh: node.attributes.flh,
						flmc: node.attributes.flmc,
						zjff:"1",
						pageUrl:_pageUrl,
						target:"iframe_list_yszczjcsh",
						mkbh: "000000",
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
		     collapsible:true,
		     title:"类别选择",
		     width:200,
		     autoScroll:true,
		     split:true,
		     rootVisible:true,
		     border: true,
		     lines: true,
		     frame : true, 
		     singleClickExpand:true,
		     root: root
	     });
   	var right = new Ext.Panel({
        region: "center",
        title:"",
        autoScroll:false,
        html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"&flh=${flh}&flzd=flh' id='iframe_list_yszczjcsh' name='iframe_list_yszczjcsh'></iframe>",
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