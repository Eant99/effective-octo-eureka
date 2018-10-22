<%@page import="com.googosoft.commons.LUser"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单位机构设置树</title>
<link href="${pageContext.request.contextPath}/static/css/public/font-awesome.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/public/googosoft.css" />
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/ext/ext-all.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/suggest/jquerySuggest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/javascript/public/public-js.js"></script>
<body>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = "${pageContext.request.contextPath}/static/plugins/ext/resources/images/default/s.gif";
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/syszc/goSysPage?mkbh=${param.mkbh}";
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
        	 url: "${pageContext.request.contextPath}/tree/sysTree",
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
	     title:"单位信息树",
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
	
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_dwbh' placeholder='输入部门号或单位名称' ><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
    $("#left_tree .x-panel-body").prepend(str);
    $(".btn").click(function(){
 	   var dwbhmc = $("#txt_dwbh").val();
 	   if(dwbhmc != null && dwbhmc != ""){
 		  $.ajax({
				url:"${pageContext.request.contextPath}/dwb/getDwbh",
				type:"POST",
				data:"DWMC="+dwbhmc,
				async:false,
				success:function(result){
					if(result.trim() != "" && result.trim() != "F"){
						$("#iframe_list_${param.mkbh}").attr("src",_pageUrl+"&dwbh="+result.trim());
					}else if(result.trim() == "F"){
						alert("您输入的单位信息不存在！");
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
 		   alert("请输入单位信息！");
 	   }
    });
    $("#txt_dwbh").bindChange("${pageContext.request.contextPath}/suggest/getXx","D");
});
</script>
</body>
</html>