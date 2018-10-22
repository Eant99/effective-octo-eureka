<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理信息权限</title>
<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/public/googosoft.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugins/ext/ext-all.css"/>
<link href="${ctxStatic}/css/public/font-awesome.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.input-group {
	padding: 5px;
}
.btn.btn-default {
	padding: 4px 10px;
    height: 25px !important;
}
#left .x-panel-body {
	position: static;
}
.x-panel-body-noheader{
	overflow:hidden !important;
}
.tool-fix{
	display: none;
}
</style>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugins/ext/ext-all.css"/>
<script type="text/javascript" src="${ctxStatic}/javascript/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctxStatic}/plugins/suggest/jquerySuggest.js"></script>
<script type="text/javascript" src="${ctxStatic}/plugins/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctxStatic}/plugins/ext/ext-all.js"></script>
<script type="text/javascript" src="${ctxStatic}/plugins/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctxStatic}/javascript/public/public-js.js"></script>
</head>
<body>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = "${ctxStatic}/plugins/ext/resources/images/default/s.gif";
//配置iframeUlr
var _pageUrl="${ctx}/xsbzjtj/goListPage";
//权限标志
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
        icon:"${ctxStatic}/plugins/ext/resources/images/default/home.gif",
        text:'${fns:getRyXm()}(管理单位)',
        loader: new Ext.tree.TreeLoader({
	        //单位权限
	        url: "${ctx}/glqxb/glqxszTree",
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
    var left = new Ext.tree.TreePanel({
	     region:'west',
	     collapsible:true,
	     title:"单位树",
	     width:220,
	     autoScroll:true,
	     split:true,
	     id:"left_tree",
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
         autoScroll:true,
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' id='iframe_list_${param.mkbh}' src='"+_pageUrl+"' name='iframe_list_${param.mkbh}'></iframe>",
         border: true
    });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_rybh' placeholder='输入人员工号或姓名' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
    $("#left_tree .x-panel-body").prepend(str);
    $(".btn").click(function(){
 	   var ryxm = $("#txt_rybh").val();
 	   if(ryxm!=null && ryxm!=""){
   		   $.ajax({
				url:"${ctx}/glqxb/getRybh",
				type:"POST",
				data:"RYXM="+ryxm,
				async:false,
				success:function(result){
					if(result.trim() != ""&&result.trim() != "F"){
						$("#iframe_list_${param.mkbh}").attr("src","${ctx}/czqxb/findCzqxszList?rybh="+result);
					}else if(result.trim() == "F"){
						alert("您输入的人员信息不存在！");
						return;
					}else{
						alert("设置信息权限不足！");
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
    $("#txt_rybh").bindChange("${ctx}/suggest/getXx","R");
});
</script>
</body>
</html>