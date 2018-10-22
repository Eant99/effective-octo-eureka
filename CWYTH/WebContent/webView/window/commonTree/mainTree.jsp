<%@page import="com.googosoft.commons.LUser"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>部门信息</title>
<%@include file="/static/include/public-tree-css.inc"%>
<style type="text/css">
#left .x-panel-body {
	position: static;
}
</style>
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}${param.pageUrl}";
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
        icon:"${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/home.gif",
        text:'<%=LUser.getRyxm()%>(管理单位)',
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
	     title:"单位树",
	     autoScroll:true,
	     split:true,
	     rootVisible:true,
	     border: false,
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
         border: false
     });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });

    var str = "<div class='input-group input-group'><input class='form-control' type='text' id='txt_dwbh' placeholder='请输入检索信息...' class='form-control'><span class='input-group-btn'><button type='button' title='点击搜索信息' id='btn_search' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
    $("#left_tree .x-panel-body").prepend(str);
    $(".btn").click(function(){
 	   var dwmc = $("#txt_dwbh").val();
 	   if(dwmc!=null && dwmc!=""){
   		   $.ajax({
				url:"${pageContext.request.contextPath}/glqxb/getRybh",
				type:"POST",
				data:"DWXX="+dwmc,
				async:false,
				success:function(result){
					if(result.trim() != ""&&result.trim() != "F"){
						$("#iframe_list_${param.mkbh}").attr("src","${pageContext.request.contextPath}/glqxb/getGlqxszList?dwbh="+result);
					}else if(result.trim() == "F"){
						alert("您输入的单位信息不存在！");
						return;
					}else{
						alert("设置信息权限不足！");
						return;
					}
				 },
				 error:function(){
					 close(index);
					 alert(getPubErrorMsg());
				 },
				 beforeSend:function(){
					 
				 }
			  });   
 	   }else{
 		   alert("请输入人员信息！");
 	   }
    });
    $("#txt_dwbh").bindChange("${pageContext.request.contextPath}/suggest/getXx","D");
	
});
</script>
</body>
</html>