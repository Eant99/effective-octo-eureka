<%@page import="com.googosoft.util.DateUtil"%>
<%@page import="com.googosoft.constant.MenuFlag"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>系统帮助信息</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/ext/ext-all.css"/>
<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/public/googosoft.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.input-group {
		padding: 5px;
	}
	.btn.btn-default {
		padding: 3px 10px;
		height: 25px !important;
	}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/suggest/jquerySuggest.js"></script>
</head>
<body>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/index/goHelpListPage";
//权限标志
$(function(){
		var root = new Ext.tree.AsyncTreeNode({
	        text:"目录",
	        id:"02",
	        leaf:false,
	        expanded:true,
	        loader:new Ext.tree.TreeLoader({
	            url:"${pageContext.request.contextPath}/tree/mlTree",
	            listeners:{
	                "beforeload":function(treeloader,node){
	                    treeloader.baseParams ={parentid:node.id};
	                }
	            }
	        })
	    });
	
	_winleft = new Ext.tree.TreePanel({
		id:'left_tree',
        title:"目录",
        width:260,
        region: "west",
        bodyStyle:"background:#FFFFFF;",
        root: root,
        //rootVisible: true,
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
	            $("#select").attr("src",""+_pageUrl+"?bzdm=" + node.id);
	           },
		        "dblclick":function(node){
					getIframeControl("${param.pname}","${param.controlId}").val(node.text);
					var winId = top.layer.getFrameIndex(window.name);
				    top.layer.close(winId);
	         }
        }
    });
	var _wincenter = new Ext.Panel({
		 region: "center",
         enableTabScroll: true,
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='select'></iframe>",
         border: true
     });
	/*创建窗体分割器*/
	new Ext.Viewport({
        layout: "border",
        items: [_winleft,_wincenter]
    });
});
</script>
</body>
</html>

