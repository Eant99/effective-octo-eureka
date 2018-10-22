<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目分类</title>
<%@include file="/static/include/public-tree-css.inc"%>
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//配置iframeUlr
var _pageUrl="";
var num = 2;
var right_url = "${ctx}/webView/xmgl/xmsb/afterList.jsp?mkbh=${param.mkbh}&guid=${param.guid}&xmbh=${param.xmbh}&operate=${operate}";
//权限标志
var _type="self";
Ext.onReady(function(){
	var root = new Ext.tree.AsyncTreeNode({
        id: "root",
        autoScroll: false,
        expanded:true,
        animate: true,
        pageUrl:_pageUrl,
        target:"iframe_list_${param.mkbh}",
        enableDD: false,
        containerScroll: false,
        border: false, 
        icon:"${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/home.gif",
        text:'采购分类',
        loader: new Ext.tree.TreeLoader({
        	 url: "${pageContext.request.contextPath}/xmsb/xmsb/cgflTree",
             listeners: {
                    "beforeload": function(treeloader, node) {
                        treeloader.baseParams = {
                            menu:'get-cgfl', 
                            cgfl: node.id,
                            pageUrl:_pageUrl,
                            target:"iframe_list_${param.mkbh}",
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
	     title:"采购分类",
	     autoScroll:true,
	     split:true,
	     rootVisible:true,
	     border: true,
	     lines: true,
	     frame : true, 
	     singleClickExpand:true,
	     root: root,
	     listeners:{
      	   "click":function(node,event){
                 if (node.isLeaf())
                 {
                     CreateFileset(node);
                 }
                 else
                     node.toggle();
              }
         }
     });
     var right=new Ext.Panel({
         region: "center",
         title:"",
         autoScroll:false,
         html: "<iframe id='cgml' frameborder='0' width='100%' height='100%' scrolling='auto' src='"+right_url+"' id='iframe_list_${param.mkbh}' name='iframe_list_${param.mkbh}'></iframe>",
         border: true
     });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_treesearch' placeholder='输入采购编号或名称' /><span class='input-group-btn'><button type='button' title='点击搜索信息' id='btn_search' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>"; 
    $("#left_tree .x-panel-body").prepend(str);;
    $(".btn").click(function(){
 	   var treesearch = $("#txt_treesearch").val();
 	   if(treesearch!=null && treesearch!=""){
 		  $("#iframe_list_${param.mkbh}").attr("src","${pageContext.request.contextPath}/xmsb/xmsb/goXmsbPage?treesearch="+treesearch);  
 	   }else{
 		   alert("请输入检索信息！");
 	   }
    });
    $("#txt_treesearch").bindChange("${pageContext.request.contextPath}/suggest/getXx","ZD");
    var CreateFileset = function(node){
		//var src = "${ctx}/webView/xmgl/xmsb/add.jsp";
		var $box = $("#cgml").contents().find('#group_panel').find("table:visible").find("[name='xmfl']");
		var flag = true;
		var fljb = node.id;
		var flmc = node.text;
		$.each($box,function(){
			var val = $(this).val();
			if(flag&&val==flmc){
				flag = false;
			}
		});
		if(flag){
			var cloneTable = $("#cgml").contents().find('#group_panel').find("table:hidden").clone();
			cloneTable.removeClass("yc");
			cloneTable.find("[name='xmfl']").val(flmc);
			cloneTable.find("[id^=txt_sfjk1]").attr({"id":"txt_sfjk1"+num,"name":"sfjk"+num});
			cloneTable.find("[id^=txt_sfjkk]").attr({"id":"txt_sfjkk"+num,"name":"sfjk"+num,"checked":"checked"});   
			
			$("#cgml").contents().find('#group_panel').find("#myform").append(cloneTable);
			num++;
		}else{
			alert("此分类已经存在，不可重复添加！");
			return;
		}
	};
});
</script>
</body>
</html>