﻿<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门信息</title>
</head>
<%@include file="/static/include/public-tree-css.inc"%>
<style type="text/css">
	#txt_dwbh{
		width:160px;
	}
</style>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/webView/window/wxzcxx/wxzcxx.jsp?pname=${param.pname}&reportid=${param.reportid}&flag=${param.flag}";
//权限标志
var _type="self";
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
        icon:"${ctx}/static/plugins/ext/resources/images/default/home.gif",
        text:'${fns:getRyXm()}(管理单位)',
        loader: new Ext.tree.TreeLoader({
        	//单位权限
        	url: "${ctx}/tree/dwTree",
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
     var left=new Ext.tree.TreePanel({
    	 id:"left_tree",
	     region:'west',
	     collapsible:true,
	     title:"单位信息树",
	     width:200,
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
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='iframe_list_dwxx' name='iframe_list_dwxx'></iframe>",
         border: true
     });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });
     var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_dwbh' placeholder='输入部门号或单位名称'><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
    $("#left_tree .x-panel-body").prepend(str);
    $(".btn").click(function(){
   	   var dwbhmc = $("#txt_dwbh").val();
   	   if(dwbhmc != null && dwbhmc != ""){
   		  $.ajax({
  				url:"${ctx}/dwb/getDwbh",
  				type:"POST",
  				data:"DWMC="+dwbhmc,
  				async:false,
  				success:function(result){
  					if(result.trim() != "" && result.trim() != "F"){
  						$("#iframe_list_dwxx").attr("src",_pageUrl+"&dwbh="+result.trim());
  					}else if(result.trim() == "F"){
  						alert("您输入的单位信息不存在！");
  						return;
  					}
  				 },
  				 error:function(){
  					 alert(getPubErrorMsg());
  				 }
  			  }); 
   	   }else{
   		   alert("请输入单位信息！");
   	   }
      });
    $("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
});
</script>
</body>
</html>