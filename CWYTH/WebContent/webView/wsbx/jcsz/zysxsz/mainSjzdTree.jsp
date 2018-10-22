<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据字典树</title>
<%@include file="/static/include/public-tree-css.inc"%>
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/dmb/goDmbPage";
//权限标志
var _type="self";
Ext.onReady(function(){
	var root = new Ext.tree.AsyncTreeNode({
		treeid: "root",
        autoScroll: false,
        expanded:true,
        animate: true,
        pageUrl:_pageUrl,
        target:"iframe_list_${param.mkbh}",
        enableDD: false,
        containerScroll: false,
        border: false, 
        icon:"${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/home.gif",
        text:'代码类别22222',
        loader: new Ext.tree.TreeLoader({
        	 url: "http://192.168.10.155/apis/treeDmbNew",
             listeners: {
                    "beforeload": function(treeloader, node) {
                        treeloader.baseParams = {
                            menu:'get-sjzd', 
                            treeid: node.id,
                            pageUrl:_pageUrl,
                            target:"iframe_list_${param.mkbh}",
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
	     title:"数据字典",
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
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_treesearch' placeholder='输入代码类型或编号' /><span class='input-group-btn'><button type='button' title='点击搜索信息' id='btn_search' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>"; 
    $("#left_tree .x-panel-body").prepend(str);;
    $(".btn").click(function(){
 	   var treesearch = $("#txt_treesearch").val();
 	   if(treesearch!=null && treesearch!=""){
 		  $("#iframe_list_${param.mkbh}").attr("src","${pageContext.request.contextPath}/dmb/goDmbPage?treesearch="+treesearch);  
 	   }else{
 		   alert("请输入检索信息！");
 	   }
    });
    $("#txt_treesearch").bindChange("${pageContext.request.contextPath}/suggest/getXx","ZD");
});
</script>
</body>
</html>