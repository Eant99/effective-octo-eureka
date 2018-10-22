<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>国别信息</title>
<%@include file="/static/include/public-tree-css.inc"%>
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/webView/window/gbxx/gbList.jsp?pname=${param.pname}&controlId=${param.controlId}";
//权限标志
$(function(){
		var root = new Ext.tree.AsyncTreeNode({
	        text:"国别代码",
	        id:" ",
	        leaf:false,
	        expanded:true,
	        loader:new Ext.tree.TreeLoader({
	            url:"${pageContext.request.contextPath}/tree/gbTree",
	            listeners:{
	                "beforeload":function(treeloader,node){
	                    treeloader.baseParams ={parentid:node.id};
	                }
	            }
	        })
	    });
	
	_winleft = new Ext.tree.TreePanel({
		id:'left_tree',
        title:"国别代码树",
        width:280,
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
	              $("#select").attr("src",""+_pageUrl+"&bzdm=" + node.id);
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
	//var str = "<div class='input-group'><input type='text' id='txt_gb' placeholder='请输入国别信息...' class='form-control'><span class='input-group-btn'><button type='button' class='btn btn-default'>查询</button></span></div>";
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_gb' placeholder='输入国别编号或国别名称' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
	$("#left_tree .x-panel-body").prepend(str);
    $(".btn").click(function(){
 	   var gb = $("#txt_gb").val();
 	   if(gb != null && gb != ""){
			$("#select").attr("src",_pageUrl+"&bzdm="+gb);
 	   }else{
 		   alert("请输入国别信息！");
 	   }
    });
});
</script>
</body>
</html>