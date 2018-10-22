<%@page import="com.googosoft.commons.LUser"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>可以多选的单位选择框</title>
	<%@include file="/static/include/public-tree-css.inc"%>
	<%@include file="/static/include/public-tree-js.inc"%>
</head>
<body>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/webView/window/multiSelDwxx/multiSelDwxxList.jsp?pname=${param.pname}&controlId=${param.controlId}&sjdw=";
//权限标志
var _type="all";
Ext.onReady(function(){
	var root = new Ext.tree.AsyncTreeNode({
        id: "root",
        autoScroll: false,
        expanded:true,
        animate: true,
        href:_pageUrl,
        hrefTarget:"iframe_list_dwxx",
        enableDD: false,
        containerScroll: false,
        border: false, 
        icon:"${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/home.gif",
        text:'<%=LUser.getRyxm()%>(管理单位)',
        loader: new Ext.tree.TreeLoader({
        	//单位权限
        	url: "${pageContext.request.contextPath}/tree/multiSelDwTree",
            listeners: {
                "beforeload": function(treeloader, node){
                    treeloader.baseParams = {
                        menu:'get-xjdw',
                        dwbh: node.id,
                        pageUrl:_pageUrl,
                        target:"iframe_list_dwxx",
                        mkbh: "000000",
                        method: 'POST',
                        type:_type
                     };
                }
             }
          })
    });
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	var left=new Ext.tree.TreePanel({
		region:'west',
		width:200,
		autoScroll:true,
		split:true,
		rootVisible:true,
		border: false,
		lines: true,
		frame : true, 
		singleClickExpand:true,
		root: root,
		listeners:{
			"click":function(node,event){
                $("#iframe_list_dwxx").attr("src",_pageUrl+node.id);
			},
			"dblclick":function(node,event){
				if(node.isLeaf()){
					getIframeControl("${param.pname}","${param.controlId}").val(node.text);
	     	    	close(winId);
				}
				else{
					alert("请选择末级单位");
				}
			}
        }
	});
	var right=new Ext.Panel({
         region: "center",
         title:"",
         autoScroll:false,
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='iframe_list_dwxx' name='iframe_list_dwxx'></iframe>",
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