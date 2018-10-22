<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
var jsons=[];
jsons="${json}".split("&");
//配置iframeUlr
var _pageUrl="${ctx}/webView/window/zjbsydw/dwxxList.jsp?pname=${param.pname}&controlId=${param.controlId}&windowModel=${param.windowModel}&from=${param.from}";
//权限标志
var _type="self";
Ext.onReady(function(){
	var root = new Ext.tree.TreeNode({
		text:'${fns:getRyXm()}(管理单位)',
        id: "root",
        autoScroll: false,
        expanded:true,
        animate: true
    });
	for(var i=0;i<jsons.length;i++){
		var json=jsons[i].split(",");
		//0：单位编号  1：单位名称 2：是否末级 3：上级单位 4：是否可以选择
		var j = new Ext.tree.AsyncTreeNode({id:json[0],text:json[1],leaf:json[2],ischeck:json[4],expanded:true});
		root.appendChild(j);
	}
     var left=new Ext.tree.TreePanel({
    	 id:"left_tree",
	     region:'west',
	     collapsible:true,
	     title:"单位树",
	     width:200,
	     autoScroll:true,
	     split:true,
	     rootVisible:true,
	     border: true,
	     lines: true,
	     frame : true, 
	     singleClickExpand:true,
	     root: root,
	     listeners:{
	    	 "beforeload":function(node){
		            node.loader=new Ext.tree.TreeLoader({  
		                url:"${ctx}/zcfltree/getDwbJson",
		                baseParams:{
		                	DWBH:node.id
		                }
		            });  
		        },
		        "click":function(node,event){
		        	$("#selectDwb").attr("src",""+_pageUrl+"&dwbh="+node.id+"&ischeck="+node.attributes.ischeck);
		        },
		        "dblclick":function(node){
		        	if(node.attributes.ischeck == "1"){
		        		var bh=node.id;
						var mc=node.text;
						getIframeControl("${param.pname}","${param.controlId}").val("("+mc.substring(1,mc.indexOf("]"))+")"+mc.substring(mc.indexOf("]")+1,mc.length));
						var winId = getTopFrame().layer.getFrameIndex(window.name);
					    getTopFrame().close(winId);
		        	}else{
		        		alert("请选择末级分类");
		        	}
		        }
	     }
     });
     var right=new Ext.Panel({
         region: "center",
         title:"",
         autoScroll:false,
         html: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='"+_pageUrl+"' id='selectDwb'></iframe>",
         border: true
     });
	/*创建窗体分割器*/
    new Ext.Viewport({
        layout: "border",
        items: [left,right]
    });
    var str = "<div class='ext-tree-search input-group'><input class='form-control' type='text' id='txt_dwbh' placeholder='输入部门号或单位名称'><span class='input-group-btn'><button type='button' title='点击搜索信息' class='btn'><i class='faw fa-search' aria-hidden='true'></i></button></span></div>";
	//var str = "<div class='input-group'><input type='text' id='txt_dwbh' placeholder='请输入单位信息...' class='form-control'><span class='input-group-btn'><button type='button' class='btn btn-default'>查询</button></span></div>";
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
  						$("#selectDwb").attr("src",_pageUrl+"&dwbh="+result.trim());
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
    $("#txt_dwbh").bindChange("${ctx}/suggest/getXx","SD");
});
</script>
</body>
</html>