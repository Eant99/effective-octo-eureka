<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>管理权限设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugins/ext/ext-all.css"/>
<style type="text/css">
	.title {
		font-size:14px;
		font-weight:300;
		padding: 10px 15px 20px;
	}
	.btn-default {
		background-color:#00acec;
		color:white;
		min-width: 76px;
	}
	.zc_right,.dw_right{
		height: 480px; 
		border: 1px solid #66afe8;
		overflow: auto;
		overflow-x:hidden;
		background-color: white;
		border-radius: 5px;
		box-shadow: 0 1px 1px rgba(0,0,0,0.075) inset,0 0 8px rgba(102,175,233,0.6);
		outline: 0,none;
		padding: 10px;
	}
	.sc_left{
		height: 480px; 
		border: 1px solid #66afe8;
		overflow: auto;
		overflow-x:hidden;
		background-color: white;
		border-radius: 5px;
		box-shadow: 0 1px 1px rgba(0,0,0,0.075) inset,0 0 8px rgba(102,175,233,0.6);
		outline: 0,none;
		padding: 10px;
	}
	.sc_selected{
		background-color: #d9e8fb;
	}
	.mid_top{
		padding-top:50px;
		text-align: center;
	}
	.mid_top input{
		margin-top: 30px;
	}
	#ext-gen4{
		height: 458px;
	}
	.x-panel-body-noborder{
	 height: 100% !important;
	 width: 100% !important;
 }
</style>
</head>
<body>
<div class="fullscreen">
<div class="container-fluid">
	<div class="row" style="margin-top: 2px;">
		<div class="col-md-12 ">
			<ul id="tabTop" class="nav nav-tabs" role="tablist">
				<!-- <li role="presentation" class="active">
					<a href="#glqxsz" role="tab" data-toggle="tab" aria-controls="glqxsz" aria-expanded="true">管理权限设置</a>
				</li> -->
				<!-- 
				<li role="presentation">
					<a href="#zclbqxsz" role="tab" data-toggle="tab" aria-controls="zclbqxsz" aria-expanded="false">资产类别权限设置</a>
				</li>
				 -->
			</ul>
		</div>
	</div>
<div class="list" style="border-top:none;!important">	
	<div id="myTabContent" class="tab-content">
		<div role="tabpanel" class="tab-pane fade active in" id="glqxsz" aria-labelledby="home-tab">
        	<form id="glqxsz" class="form-horizontal" action="" method="post" >
				<!-- RYBH人员编号 -->
				<input type="hidden" name="RYBH" value="${rybh}">
				<div class="row">
					<div class="col-md-12">
						<div class="title">
							<i class='faw fa-user' style="font-size:18px;"></i>
							<span><span id="sp_ry" style="color:Red;"><c:out value="${ryxx}"/></span>的部门管理权限</span>
						</div>
					</div>
				</div>
				<div class="row" style="height: 600px">
					<div class="col-md-3 col-md-offset-2 sc_left" id="d_dwtree"></div>
					<div class="col-md-2">
						<div class="mid_top">
							<input type="button" class="btn btn-default" id="dw_button_left" onclick="left('D');" value="取消 ←"><br/>
							<input type="button" class="btn btn-default" id="dw_button_right" onclick="validateBybh('D');" value="选择 →"><br/>
							<input type="button" class="btn btn-default" id="dw_button_all" onclick="save('D');" value="保存"><br/>
							<input type="button" class="btn btn-default" id="dw_button_delete" onclick="deleteall('D');" value="重新选择">
						</div>
					</div>
					<div class="col-md-3 dw_right">
						<c:forEach var="glqxTree" items="${glqxTree}">
				        	<div class="sc_inline number_${glqxTree.DWBH}" style="cursor:pointer;">
								<div align="left" style="padding: 4px"><c:out value="(${glqxTree.BMH})${glqxTree.DWMC}"></c:out></div>
								<input type="hidden" name="BHH" value="${glqxTree.DWBH}"/>
							</div>         
						</c:forEach>
					</div>
				</div>
			</form>	
      	</div>
      	<div role="tabpanel" class="tab-pane fade" id="zclbqxsz" aria-labelledby="home-tab">
      		<form id="zclbqxsz" class="form-horizontal" action="" method="post" >
				<!-- RYBH人员编号 -->
				<input type="hidden" name="RYBH" value="${rybh}"/>
				<div class="row">
					<div class="col-md-12">
						<div class="title">
							<i class='faw fa-user' style="font-size:18px;"></i>
							<span><span id="sp_ry" style="color:Red;"><c:out value="${ryxx}"/></span>的资产类别管理权限</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3 col-md-offset-2 sc_left" id="d_zclbtree"></div>
					<div class="col-md-2">
						<div class="mid_top">
							<input type="button" class="btn btn-default" id="zclb_button_left" onclick="left('Z');" value="取消 ←"><br/>
							<input type="button" class="btn btn-default" id="zclb_button_right" onclick="validateBybh('Z');" value="选择 →"><br/>
							<input type="button" class="btn btn-default" id="zclb_button_all" onclick="save('Z');" value="保存"><br/>
							<input type="button" class="btn btn-default" id="zclb_button_delete" onclick="deleteall('Z');" value="重新选择">
						</div>
					</div>
					<div class="col-md-3 zc_right">
						<c:forEach var="zclbTree" items="${zclbTree}">
				        	<div class="sc_inline number_${zclbTree.BZDM}" style="cursor:pointer;">
								<div align="left" style="padding: 4px"><c:out value="${zclbTree.FLBHMC}"></c:out></div>
								<input type="hidden" name="BZDM" value="${zclbTree.BZDM}"/>
							</div>         
						</c:forEach>
					</div>
				</div>
			</form>	
      	</div>
	</div>
</div>	
</div>
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${ctxStatic}/javascript/bootstrap/bootstrap.min.js"></script>
<script src="${ctxStatic}/plugins/ext/ext-base.js"></script>
<script src="${ctxStatic}/plugins/ext/ext-all.js"></script>
<script src="${ctxStatic}/plugins/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = "${ctxStatic}/plugins/ext/resources/images/default/s.gif";
Ext.onReady(function(){
	//单位权限树
	var dwRoot = new Ext.tree.AsyncTreeNode({
		id: "root",
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: true,
	    containerScroll: true,
	    rootVisible: true,
	    expanded : true,
        singleClickExpand:true,
        icon:"${ctxStatic}/plugins/ext/resources/images/default/home.gif",
        text:"【${ryxx}】（单位权限）",
        loader: new Ext.tree.TreeLoader({
            url: "${ctx}/glqxb/deptTree",
            listeners: {
                "beforeload": function(treeloader, node) { 
                    treeloader.baseParams = {
                        menu:'get-xjdw', 
                        dwbh: node.id,
                        method: 'POST',
                        type:"self"
                     };
                }
            }
        })
	});
	var dwTree=new Ext.tree.TreePanel({
		renderTo: 'd_dwtree',
		header: false,
        collapsible:true,
        autoScroll:true,
        split:true,
        rootVisible:true,
        border: false,
        lines: true,
        frame : false, 
        singleClickExpand:true,
        root: dwRoot
    });
	//资产类别树
	var zclbRoot = new Ext.tree.AsyncTreeNode({
		id: "00",
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: true,
	    containerScroll: true,
	    rootVisible: true,
	    expanded : true,
        singleClickExpand:true,
        icon:"${ctxStatic}/plugins/ext/resources/images/default/home.gif",
        text:"【${ryxx}】（资产类别权限）",
        loader: new Ext.tree.TreeLoader({
            url: "${ctx}/glqxb/zclbTree",
            listeners: {
                "beforeload": function(treeloader, node){
                    treeloader.baseParams = {
                        method: 'POST',
                     };
                }
            }
        })
	});
	var zclbTree=new Ext.tree.TreePanel({
		renderTo: 'd_zclbtree',
		header: false,
        collapsible:true,
        autoScroll:true,
        split:true,
        rootVisible:true,
        border: false,
        lines: true,
        frame : false, 
        singleClickExpand:true,
        root: zclbRoot
    });
	dwTree.getRootNode().expand();
});
$(function(){
	//单击变色
	$(document).on("click",".sc_inline", function(){
		if(checkRybh()){
			$(".sc_inline").removeClass("sc_selected");
			$(this).addClass("sc_selected");
		}
	});
	
	$(document).on("dblclick","#glqxsz .x-tree-selected", function(){
		validateBybh("D");
	});
	
	//双击右侧框内容，取消
	$(document).on("dblclick","#glqxsz .sc_selected", function(){
		left("D");
	}); 
	
	//双击添加到右侧框
	$(document).on("dblclick","#zclbqxsz .x-tree-selected", function(){
		validateBybh("Z");
	});
	
	//双击右侧框内容，取消
	$(document).on("dblclick","#zclbqxsz .sc_selected", function(){
		left("Z");
	});
});
//左移
function left(val){
	if(val == 'D'){
		var selected = $(".dw_right").find(".sc_selected");
		if(selected.html() != null ){
			selected.remove();
		}
	}else if(val=='Z'){
		var selected = $(".zc_right").find(".sc_selected");
		if(selected.html() != null ){
			selected.remove();
		}
	}
}
//保存
function save(val){
	var resource = [];
	if(val=="D"){
		var $checkeds = $("[name*=BHH]");
		$checkeds.each(function(){
			resource.push($(this).val());
		});
	}else if(val=="Z"){
		var $checkeds = $("[name*=BZDM]");
		$checkeds.each(function(){
			resource.push($(this).val());
		});
	}
	$.ajax({
		type:"post",
		url:"${ctx}/glqxb/doSave",
		data:"qxsz="+resource.join(",")+"&szry=${rybh}&flag="+val,
		dataType:"json",
		success:function(val){
			close(index);
			if(val.success == 'true'){
				alert(val.msg);
				$("#operateType").val("U");
			}else if(val.success == 'false'){
				alert(val.msg);
			}
		},
		error:function(val){
			close(index);
			alert(getPubErrorMsg());
		},
		beforeSend:function(val){
			index = loading(2);
		}
	});
}
//验证选择节点
function validateBybh(val){
	var right="";
	if(val == "D"){
		var dwbhs = $("[name*=BHH]");
		for(var i=0;i<dwbhs.length;i++){
			right+=$(dwbhs[i]).val();
			right+=",";
		}
		
		var left = $("#d_dwtree").find(".x-tree-selected").attr("ext:tree-node-id").substr(1);
		$.ajax({
			type:"post",
			url:"${ctx}/glqxb/checkBydwbh",
			dataType:"text",
			data:"left="+left+"&right="+right,
			success:function(result){
				result = $.trim(result);
				if(result=="FAIL"){
					$(".sc_inline").removeClass("sc_selected");
					alert("该节点已被选择或者该节点的父节点已被选择,请重新选择");
				}else{
					if($("#d_dwtree").find(".x-tree-selected").html() != null){
						$(".dw_right").empty();
						$(".dw_right").append(result);
						console.log("11"+result);
					}
					$(".sc_inline").removeClass("sc_selected");
				}
			},
			error:function(){
				alert(getPubErrorMsg());
			}
		});
	}else if(val == "Z"){
		var bzdms = $("[name*=BZDM]");
		var left = $("#d_zclbtree").find(".x-tree-selected").attr("ext:tree-node-id");
		var b = true;
		bzdms.each(function(){
			if($(this).val()==left || $(this).val() == "00"){
				b = false;
			}
		});
		if(b){
			var text = $("#d_zclbtree").find(".x-tree-selected").find(".x-tree-node-anchor").text();
			var right = '<div class="sc_inline number_'+left+'" style="cursor:pointer;"><div align="left" style="padding-left: 8px">'+text+'</div><input type="hidden" name="BZDM" value="'+left+'"></div>';
			$(".zc_right").append(right);
		}else{
			alert("不能重复添加节点");
		}
	}
}
//重新选择
function deleteall(val){
	if(val=='D'){
		$(".dw_right").empty();
	}else if(val == 'Z'){
		$(".zc_right").empty();
	}
}
//验证是否选择人员
function checkRybh(){
	var bool=true;
	if("${rymap.RYGH}"==null){
		alert("请选择人员");
		bool = false;
	}
	return bool;
}
</script>
</body>
</html>