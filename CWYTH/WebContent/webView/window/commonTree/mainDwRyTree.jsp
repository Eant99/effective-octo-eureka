<%@page import="com.googosoft.commons.LUser"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门人员信息</title>
<%@include file="/static/include/public-tree-css.inc"%>
<style type="text/css">
	.scrollRemove{
		overflow: visible !important;
	}
</style>
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//权限标志
var _type="self";
var sql;
if("${sql}"=='undefined'){
	sql='';
}else{
	sql="${sql}";
}
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}${param.pageUrl}?mkbh=${mkbh}&sql="+sql;
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
        text:'<%=LUser.getRybh()%>(管理单位人员)',
        loader: new Ext.tree.TreeLoader({
        	//单位权限
        	 url: "${pageContext.request.contextPath}${param.treeJson}",
             listeners: {
                    "beforeload": function(treeloader, node) { 
                        treeloader.baseParams = {
                            menu:'get-xjdw', 
                            dwbh: node.id,
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
	     width:200,
	     collapsible:true,
	     title:"单位人员树",
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
         autoScroll:true,
         html: "<iframe frameborder=\"0\" width=\"100%\" height=\"100%\" scrolling=\"auto\" src=\""+_pageUrl+"\" id=\"iframe_list_${param.mkbh}\" name=\"iframe_list_${param.mkbh}\"></iframe>",
         border: true
     });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });
	
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_rybh' placeholder='输入人员编号或人员姓名' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
    $("#left_tree .x-panel-body").prepend(str);
    $(".btn").click(function(){
 	   var rymc = $("#txt_rybh").val();
 	   if(rymc != null && rymc != ""){
   		   $.ajax({
				url:"${pageContext.request.contextPath}/ryb/getRybh",
				type:"POST",
				data:"RYMC="+rymc,
				async:false,
				success:function(result){
					if(result.trim() != "" && result.trim() != "F"){
						$("#iframe_list_${param.mkbh}").attr("src",_pageUrl+"&rybh="+result.trim());
					}else if(result.trim() == "F"){
						alert("您输入的人员信息不存在！");
						return;
					}
				 },
				 error:function(){
					 alert(getPubErrorMsg());
				 }
			  });   
 	   }else{
 		   alert("请输入人员信息！");
 	   }
    });
    $("#txt_rybh").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
});
</script>
</body>
</html>