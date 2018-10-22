<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>经济科目树</title>
<%@include file="/static/include/public-tree-css.inc"%>
<%@include file="/static/include/public-manager-css.inc"%>s
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
//配置iframeUlr
var urls = "${param.pageUrl}";
if(urls==""||urls=="undefined"||urls==null){
	urls = "/Zz/zzList?type=first&kmbh=${param.kmbh}&kkqjj=${param.kkqjj}&bmbh=${param.bmbh}&xmbh=${param.xmbh}&bhwjzpz=${param.bhwjzpz}";	
}
if(urls=="mxb"){
	urls = "/mxz/toUrl?bbqj=${param.bbqj}&kmbh=${param.kmbh}";
}
var _pageUrl="${pageContext.request.contextPath}"+urls+"?gs=${param.gs}&ly=jjkm&ymtz=yes";
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
        text:'经济科目设置',
        loader: new Ext.tree.TreeLoader({
        	 url: "${pageContext.request.contextPath}/kmsz/JjszTree",
             listeners: {
                    "beforeload": function(treeloader, node) {
                        treeloader.baseParams = {
                            menu:'get-jjkm', 
                            dm: node.id,
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
	     title:"经济科目",
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
//     var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_treesearch' placeholder='请输入完整功能科目名！' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
//     $("#left_tree .x-panel-body").prepend(str);
//     $(".btn").click(function(){
//  	   var treesearch = $("#txt_treesearch").val();
//  	   if(treesearch!=null && treesearch!=""){
//  		  $("#iframe_list_${param.mkbh}").attr("src","${pageContext.request.contextPath}/kmsz/goJjkmPage?treesearch="+treesearch);  
//  	   }else{
//  		   alert("请输入检索信息！");
//  	   }
//     }); 
   // $("#txt_treesearch").bindChange("${ctx}/suggest/getXx","ZD");
    
    /*
     * 左侧树的联想查询
     */
    	var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_treesearch' placeholder='请输入经济科目编号或名称！' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
        $("#left_tree .x-panel-body").prepend(str);
        $(".btn").click(function(){
        	var treesearch = $("#txt_treesearch").val();
       	   var kmbh = treesearch.substr(treesearch.indexOf("(")+1,treesearch.indexOf(")")-1);
       	   if(treesearch!=null && treesearch!=""){
       		   if("${param.mkbh}"=="070114")
       			   {
            		  $("#iframe_list_${param.mkbh}").attr("src",_pageUrl+"&kmbh="+kmbh); 
       			   }
       		   else
       			   {
       		  $("#iframe_list_${param.mkbh}").attr("src","${pageContext.request.contextPath}/kjhs/jjkmmxz1/toUrl?kmbh="+kmbh+"&ymtz=yes");  }
       	   }else{
       		   alert("请输入检索信息！");
       	   }
        }); 
        $("#txt_treesearch").bindChange("${pageContext.request.contextPath}/suggest/getXx","JJKM");

});
</script>
</body>
</html>