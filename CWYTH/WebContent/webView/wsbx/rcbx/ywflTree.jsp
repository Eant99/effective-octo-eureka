<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>经济分类科目树</title>
<%@include file="/static/include/public-tree-css.inc"%>
</head>
<body>
<%@include file="/static/include/public-tree-js.inc"%>
<script type="text/javascript">
//配置iframeUlr
var addInfo = [];
var _pageUrl="${pageContext.request.contextPath}/webView/wsbx/rcbx/rcbx_operate.jsp?fymc=${param.fymc}&mkbh=${param.mkbh}";
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
        text:'经济分类科目',
        loader: new Ext.tree.TreeLoader({
        	 url: "${pageContext.request.contextPath}/wsbx/rcbx/JjszTree",
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
	     title:"经济分类科目",
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
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='iframe_list_${param.mkbh}' class='rcbx' name='iframe_list_${param.mkbh}'></iframe>",
         border: true
     });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_treesearch' placeholder='请输入完整功能科目名！' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
    $("#left_tree .x-panel-body").prepend(str);
    $(".btn").click(function(){
 	   var treesearch = $("#txt_treesearch").val();
 	   if(treesearch!=null && treesearch!=""){
 		  $("#iframe_list_${param.mkbh}").attr("src","${pageContext.request.contextPath}/kmsz/goKmszPage?treesearch="+treesearch);  
 	   }else{
 		   alert("请输入检索信息！");
 	   }
    }); 
    var CreateFileset = function(node){
    	var num = 2;
		var $box = $(".rcbx").contents().find('#tbody');
		var flag = true;
		var fljb = node.id;
		var flmc = node.text;
		$.each($box.find("[id^=txt_fymc]"),function(i,v){
			if(flag&&$(this).val()==flmc){
				flag = false;
			}
		});
		if(flag){
			var $parentTr = $box.find(".yc").clone();
			$parentTr.removeClass("yc");
			$parentTr.find(":input").val("");
			$parentTr.find(":input").removeClass("border");
			$parentTr.attr("id","tr_"+num);
			$parentTr.find("[id^=txt_fymc]").attr({"name":"fymc"+num,"id":"txt_fymc"+num});
			$parentTr.find("[id^=txt_fymc]").val(flmc);
			$parentTr.find("[id^=txt_je]").attr({"name":"je"+num,"id":"txt_je"+num});
			$parentTr.find("[id^=txt_pjzs]").attr({"name":"pjzs"+num,"id":"txt_pjzs"+num});
			$box.find(".yc").before($parentTr);
			num++;
		}else{
			alert(flmc+"已存在，不可重复添加！");
		}
	};
});
</script>
</body>
</html>