<%@page import="com.googosoft.controller.zcjz.YshdController"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="com.googosoft.util.Const"%>
<%@page import="com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>财政六分类</title>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/public/googosoft.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/ext/ext-all.css"/>
<link href="${pageContext.request.contextPath}/static/css/public/font-awesome.css" rel="stylesheet" type="text/css" />
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
<body>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/webView/fzgn/sjzd/fldz_czList.jsp?pname=${param.pname}&controlId=${param.controlId}&czmc=${param.czmc}";
//权限标志
$(function(){
		var root = new Ext.tree.AsyncTreeNode({
	        text:"财政分类",
	        id:"00000000",
	        leaf:false,
	        expanded:true,
	        loader:new Ext.tree.TreeLoader({
	            url:"${pageContext.request.contextPath}/zcfltree/czflTree",
	            listeners:{
	                "beforeload":function(treeloader,node){
	                    treeloader.baseParams ={parentid:node.id};
	                }
	            }
	        })
	    });
	
	_winleft = new Ext.tree.TreePanel({
		id:'left_tree',
        title:"财政分类树",
        width:200,
        region: "west",
        bodyStyle:"background:#FFFFFF;",
        root: root,
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
	              $("#selectFlh").attr("src",""+_pageUrl+"&bzdm=" + node.id);
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
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='selectFlh'></iframe>",
         border: true
     });
	/*创建窗体分割器*/
	new Ext.Viewport({
        layout: "border",
        items: [_winleft,_wincenter]
    });
	 //var str = "<div class='input-group'><input type='text' id='txt_flh' placeholder='请输入财政分类信息...' class='form-control'><span class='input-group-btn'><button class='btn btn-default'>查询</button></span></div>";
	   var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_flh' placeholder='输入分类号或分类名称' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";  
	 $("#left_tree .x-panel-body").prepend(str);
	    $(".btn").click(function(){
	 	   var flh = $("#txt_flh").val();
	 	   if(flh != null && flh != ""){
				$("#selectFlh").attr("src",_pageUrl+"&treesearch="+flh);
	 	   }else{
	 		   alert("请输入财政分类信息！");
	 	   }
	    });
});
</script>
</body>
</html>