<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门预算申报树</title>
<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/public/googosoft.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugins/ext/ext-all.css"/>
<link href="${ctxStatic}/css/public/font-awesome.css" rel="stylesheet" type="text/css" />
<%-- <%@include file="/static/include/public-tree-css.inc"%> --%>
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
<%-- <%@include file="/static/include/public-tree-js.inc"%> --%>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = "${ctxStatic}/plugins/ext/resources/images/default/s.gif";
//配置iframeUlr
var _pageUrl="${pageContext.request.contextPath}/ysgl/bmyssb/goListPage?sh=${param.sh}&ck=${param.ck}&guid=${param.guid}&SRYSHZ=${param.SRYSHZ}&ZCYSHZ=${param.ZCYSHZ}&procinstid=${param.procinstid}&bzid=${bzid}&dm=1&flag=${flag}";//${ctx}=${pageContext.request.contextPath},${param.pageUrl}=/dwb/goDwbPage
            //${ctx}${param.pageUrl}?mkbh=${param.mkbh}
//权限标志
var _type="self";//
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
        text:'${fns:getRyXm()}(部门预算申报目录)',//
        loader: new Ext.tree.TreeLoader({
        	//单位权限
        	 url: "${ctx}/ysgl/bmyssb1/bmysTree?bzid=${bzid}",
             listeners: {
                    "beforeload": function(treeloader, node) { 
                        treeloader.baseParams = {
                            menu:'get-xjdw',  
                            dwbh: node.id,//地点树为ddbh
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
	     title:"部门预算申报树",//
	     autoScroll:true,
	     split:true,
	     rootVisible:true,
	     border: true,
	     lines: true,
	     frame : true, 
	     singleClickExpand:true,
	     root: root
     });
     var right=new Ext.Panel({//要跳转的右侧页面
         region: "center",
         title:"", 
         autoScroll:true, 
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='iframe_list_${param.mkbh}' name='iframe_list_010301'></iframe>",
         border: true
     });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });
	
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_dwbh' placeholder='输入项目号或项目名称' ><span class='input-group-btn'><button type='button' title='点击搜索信息' id='btn_search' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
    $("#left_tree .x-panel-body").prepend(str);
    
    $("#btn_search").click(function(){
 	   var dwbhmc = $("#txt_dwbh").val();
 	   if(dwbhmc != null && dwbhmc != ""){
 		  $.ajax({
				url:"${ctx}/glqxb/doCheckDwqx",
				type:"POST",
				data:"DWMC="+dwbhmc,
				async:false,
				success:function(result){
					if(result.trim()== "-1"){
						alert("您输入的项目信息不存在！");
						return;
					}else if(result.trim() == "-2"){
						alert("设置项目信息权限不足！");
						return;
					}else{
						$("#iframe_list_${param.mkbh}").attr("src",_pageUrl+"&dwbh="+result.trim());//传的值为(000010)赵常明，截串后dwbh =000010,
						return;
					}
				 },
				 error:function(){
					 alert(getPubErrorMsg());
				 }
			  }); 
 	   }else{
 		   alert("请输入项目信息！");
 	   }
    });
    $("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");//联想输入
});
</script>
</body>
</html>