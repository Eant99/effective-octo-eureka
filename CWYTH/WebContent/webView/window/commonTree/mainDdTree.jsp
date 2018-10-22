<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>地点信息</title>
<%@include file="/static/include/public-tree-css.inc"%>
<style type="text/css">
	#left_tree .x-panel-body {
		position: static;
	}
	.x-panel-body-noheader{
		overflow:hidden !important;
	}
</style>
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//权限标志
var sql;
if("${sql}"=='undefined'){
	sql='';
}else{
	sql="${sql}";
}
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}${param.pageUrl}?mkbh=${mkbh}&value=${value}&sql="+sql;
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
        text:'${fns:getRyXm()}(地点信息)',
        loader: new Ext.tree.TreeLoader({
        	//单位权限
        	 url: "${pageContext.request.contextPath}${param.treeJson}",
             listeners: {
                "beforeload": function(treeloader, node) { 
                    treeloader.baseParams = {
                        menu:'get-xjdw', 
                        ddbh: node.id,
                        pageUrl:_pageUrl,
                        target:"iframe_list_${param.mkbh}",
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
	     width:220,
	     collapsible:true,
	     title:"地点树",
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
         html: "<iframe frameborder=\"0\" width=\"100%\" height=\"100%\" scrolling=\"auto\" src=\""+_pageUrl+"\" id=\"iframe_list_${param.mkbh}\" name=\"iframe_list_${param.mkbh}\"></iframe>",
         border: true
     });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });
	
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_ddbh' placeholder='输入地点编号或地点名称' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";

    $("#left_tree .x-panel-body").prepend(str);
    $(".btn").click(function(){
 	   var ddmc = $("#txt_ddbh").val();
 	   if(ddmc != null && ddmc != ""){
   		   $.ajax({
				url:"${pageContext.request.contextPath}/ddb/getDdbh",
				type:"POST",
				data:"DDMC="+ddmc,
				async:false,
				success:function(result){
					if(result.trim() != "" && result.trim() != "F"){
						$("#iframe_list_${param.mkbh}").attr("src",_pageUrl+"&ddbh="+result.trim());
					}else if(result.trim() == "F"){
						alert("您输入的地点信息不存在！");
						return;
					}
				 },
				 error:function(){
					 alert(getPubErrorMsg());
				 }
			  });   
 	   }else{
 		   alert("请输入地点信息！");
 	   }
    });
    $("#txt_ddbh").bindChange("${pageContext.request.contextPath}/suggest/getXx","P");
});
</script>
</body>
</html>