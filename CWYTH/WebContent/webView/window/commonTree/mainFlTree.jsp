<%@page import="com.googosoft.util.CommonUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产分类树（十六大类）</title>
<%@include file="/static/include/public-tree-css.inc"%>
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/fldz/goFldzPage?pname=${param.pname}&controlId=${param.controlId}&Next=${param.Next}&windowModel=${param.windowModel}";//权限标志
var _type="self";//目前分类没控制权限
Ext.onReady(function(){
	var flroot = new Ext.tree.AsyncTreeNode({
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
		text:( "教育部十六大类" ),
		loader: new Ext.tree.TreeLoader({
      	//单位权限
			url: "${pageContext.request.contextPath}/tree/flTree",
          	listeners: {
              "beforeload": function(treeloader, node){
					treeloader.baseParams = {
						bzdm: node.id,//由于教育部16中flh有重复的信息，所以这里统一使用bzdm字段
						flh: node.attributes.flh,
						flmc: node.attributes.flmc,
						zjff:"0",
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

	var left = new Ext.tree.TreePanel({
		id:"left_tree",
		region: "west",
		width:200,
	    collapsible:true,
	    title:"类别选择",
	    autoScroll:true,
		split: true,
		rootVisible:true,
	    border: true,
	    lines: true,
	    frame : true, 
	    singleClickExpand:true,
		root: flroot	
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
	
	 var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_flh' placeholder='输入分类号或分类名称' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
     $("#left_tree .x-panel-body").prepend(str);
     $(".btn").click(function(){
    	   var flh = $("#txt_flh").val();
    	   if(flh != null && flh != ""){
    		   if(typeof flh =="number"){
    			   $("#iframe_list_yszczjcsh").attr("src",_pageUrl+"&flh="+flh+"&flzd=flh");
    		   }else if(typeof flh =="string"){
   				   $("#iframe_list_yszczjcsh").attr("src",_pageUrl+"&flmc="+flh+"&flzd=flh");
    		   }
				$("#iframe_list_yszczjcsh").attr("src",_pageUrl+"&flh="+flh+"&flzd=flh");
	 	   }else{
	 		   alert("请输入分类号或分类名称！");
	 	   }
       });
     $("#txt_flh").bindChange("${pageContext.request.contextPath}/suggest/getXx","FQ");
	
});
</script>
</body>
</html>