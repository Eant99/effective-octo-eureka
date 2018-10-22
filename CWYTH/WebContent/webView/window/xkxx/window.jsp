<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学科信息</title>
</head>
<%@include file="/static/include/public-tree-css.inc"%>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/webView/window/xkxx/xkList.jsp?pname=${param.pname}&controlId=${param.controlId}";
Ext.onReady(function(){
	var root = new Ext.tree.AsyncTreeNode({
        id: "root",
        autoScroll: false,
        expanded:true,
        animate: true,
        href:_pageUrl,
        hrefTarget:"iframe_list_xkxx",
        enableDD: false,
        containerScroll: false,
        border: false, 
        icon:"${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/home.gif",
        text:"学科代码",
        loader: new Ext.tree.TreeLoader({
        	//单位权限
        	 url: "${pageContext.request.contextPath}/tree/xkTree",
             listeners: {
                    "beforeload": function(treeloader, node) { 
                        treeloader.baseParams = {
                            menu:'get-xkdm',
                            dm: node.id,
                            pageUrl:_pageUrl,
                            target:"iframe_list_xkxx",
                            mkbh: "000000",
                            method: 'POST'
                         };
                    }
               }
          })
    });
     var left=new Ext.tree.TreePanel({
    	 id:'left_tree',
	     region:'west',
	     collapsible:true,
	     title:"学科信息树",
	     width:200,
	     autoScroll:true,
	     split:true,
	     rootVisible:true,
	     border: true,
	     lines: true,
	     frame : true, 
	     singleClickExpand:true,
	     root: root,
	     listeners:{
           "dblclick":function(node){
				getIframeControl("${param.pname}","${param.controlId}").val(node.text);
				var winId = top.layer.getFrameIndex(window.name);
		    	top.layer.close(winId);
		         }
	        }
     });
     var right=new Ext.Panel({
         region: "center",
         title:"",
         autoScroll:false,
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='iframe_list_xkxx' name='iframe_list_xkxx'></iframe>",
         border: true
     });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });
    //var str = "<div class='input-group'><input type='text' id='txt_xkxx' placeholder='请输入学科信息...' class='form-control'><span class='input-group-btn'><button type='button' class='btn btn-default'>查询</button></span></div>";
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_xkxx' placeholder='输入学科代码或学科名称' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
    $("#left_tree .x-panel-body").prepend(str);
    $(".btn").click(function(){
 	   var xkxx = $("#txt_xkxx").val();
 	   if(xkxx != null && xkxx != ""){
			$("#iframe_list_xkxx").attr("src",_pageUrl+"&dm="+xkxx);
 	   }else{
 		   alert("请输入学科信息！");
 	   }
    });
});
</script>
</body>
</html>