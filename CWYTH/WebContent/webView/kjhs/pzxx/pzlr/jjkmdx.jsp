<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>经济科目设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
 .dataTables_scrollBody{
    height:435px !important;
 }
     select{ 
 		font-size:12px !important;  
 		max-height: 25px;   
 		padding: 0px !important; 
 		padding-left: 5px !important; 
 	}
	#mydatatables td {
		line-height:10px;
	}
	.bottom {
		bottom: 0px !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-top:8px" onkeydown="if(event.keyCode==13)return false;">
    		<div class="search-simple">
				<div class="form-group">
					<label>科目编号</label>
					<input type="text" id="txt_xm" class="form-control input-radius" name="kmbh"  table="j" placeholder="请输入科目编号" >
				</div>
				 <select class="form-control" name="kmnd"  id="txt_kmnd"  >	
				     <c:choose>
				     	<c:when test="${empty kmnd }">
				               <c:forEach var="nlist" items="${nlist}">  	
  	             			     <option value="${nlist.kmnd}" <c:if test="${nlist.kmnd==jn}">selected</c:if> >${nlist.kmnd }</option> 
  	             			   </c:forEach>
  	             	    </c:when>
  	             	    <c:otherwise>
  	             	           <c:forEach var="nlist" items="${nlist}">  	
  	             			     <option value="${nlist.kmnd}" <c:if test="${nlist.kmnd==kmnd}">selected</c:if> >${nlist.kmnd }</option> 
  	             			   </c:forEach>
  	             	    </c:otherwise>
  	                  </c:choose>
	             </select>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
<!-- 				<button type="button" class="btn btn-primary" id="btn_submit"><i class="fa icon-chaxun"></i>确定</button> -->
			</div>
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
				<div class="alert alert-info">
		          	<strong>提示：</strong>先找到需要的信息，然后<strong>双击</strong>这条信息
<!-- 		          	或先选中需要的信息，然后点击<strong>确定</strong>按钮。 -->
	        	</div>
		    	<input type="hidden" name="guid" value="${guid}"/>
		        <table id="mydatatables" class="table table-striped table-bordered" >
					<thead>
		 				<tr id="header">
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						    <th>科目编号</th>
						    <th>科目名称</th>
							<th>科目级次</th>	
						    <th>是否启用</th>	
			   			</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%> 
<script >
var target = "${ctx}/kjhs/pzxx/pzlr";
var winId = top.layer.getFrameIndex(parent.window.name);

var columns = [
	{"data": "guid",orderable:false, "render": function (data, type, full, meta){
       	return '<input type="checkbox" name="guid" data-jjfl="'+full.KMBH+'"  data-jjflmc="'+full.KMMC+'" data-jjkmjc="'+full.KMJC+'" data-sjkm="'+full.k+'" class="keyId" value="'+data+'" >';
    },"width":10,'searchable': false},
	{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
   		return data;
	},"width":41,"searchable": false,"class":"text-center"},
   	{"data": "KMBH","class":"hj","render":function (data, type, full, meta){
	     	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
        }},
   	{"data": "KMMC",defaultContent:""},
   	{"data": "KMJC",defaultContent:""},
	{"data": "QYF",defaultContent:"","class":"text-center",width:80}
];
  table = getDataTableByListHj("mydatatables",target+"/getJjkmPageList?treeDm=${param.kmbh}&kmnd=${kmnd}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
$(function(){
	var pname = "${param.pname}";

	if(pname=="iframe_110201"){
		$(document).on("dblclick","tr:not(#header)",function(){
			  var jjfl = $(this).find(".keyId").data("jjfl");
			  var jjflmc = $(this).find(".keyId").data("jjflmc");
			  var jjkmjc = $(this).find(".keyId").data("jjkmjc");
	
			  if(jjflmc == null||jjflmc == ""){
				  alert("没有可选的数据");
				  return;
			  }
			  $.ajax({
				  url:"${ctx}/kjhs/pzxx/pzlr/doSelectSfmj?kmbh="+jjfl,
					  data:"",
					  type:"post",
					  async:"false",
					  success:function(val){
					        	if(val=="0"){					        		
					        		getIframeControl("${param.pname}","${param.controlId}").val(jjflmc);
									getIframeControl("${param.pname}","${param.fyid}").val(jjfl);
							      	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
							      	getIframeControl("${param.pname}","${param.fyid}").focus();//手动触发验证
//					 		      	getIframWindow("${param.pname}").triggerChange("${param.controlId}");
							      	close(winId);
								  }else{
									  alert("请选择末级科目级次!");
								  }
					  },
					  error:function(val){
						  
					  }
			  });  		
		})
		$(document).on("click","#btn_qd",function(){
			var checkbox=$(".keyId").filter(":checked");
			if(checkbox.length==0){
				alert("请选择一条信息！");
				return;
			}else if(checkbox.length>1){
				alert("只能选择一条信息！");
				return;
			}else{
				var jjfl=$("#mydatatables").find(".keyId").filter(":checked").data("jjfl");
				var jjflmc=$("#mydatatables").find(".keyId").filter(":checked").data("jjflmc");
				var jjkmjc=$("#mydatatables").find(".keyId").filter(":checked").data("jjkmjc");	  
				    
				    $.ajax({
						  url:"${ctx}/kjhs/pzxx/pzlr/doSelectSfmj?kmbh="+jjfl,
							  data:"",
							  type:"post",
							  async:"false",
							  success:function(val){
							        	if(val=="0"){
							        		getIframeControl("${param.pname}","${param.controlId}").val(jjflmc);
											getIframeControl("${param.pname}","${param.fyid}").val(jjfl);	
									      	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
									      	getIframeControl("${param.pname}","${param.fyid}").focus();//手动触发验证
//							 		      	getIframWindow("${param.pname}").triggerChange("${param.controlId}");
									      	close(winId);
											}else{
												alert("请选择末级科目级次!");
											}
										},
							  error:function(val){
								  
							  }
					  });  
			}
		});
	}else{
		$(document).on("dblclick","tr:not(#header)",function(){
			  var jjfl = $(this).find(".keyId").data("jjfl");
			  var jjflmc = $(this).find(".keyId").data("jjflmc");
			  var jjkmjc = $(this).find(".keyId").data("jjkmjc");
			   
			  if(jjfl == null||jjfl == ""){
				  alert("没有可选的数据");
				  return;
			  }
			  $.ajax({
				  url:"${ctx}/kjhs/pzxx/pzlr/doSelectSfmj?kmbh="+jjfl,
					  data:"",
					  type:"post",
					  async:"false",
					  success:function(val){
					        	if(val=="0"){
					        		getIframeControl("${param.pname}","${param.controlId}").val(jjflmc);
									getIframeControl("${param.pname}","${param.fyid}").val(jjfl);	
							      	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
							      	getIframeControl("${param.pname}","${param.fyid}").focus();///手动触发验证
// 							      	getIframWindow("${param.pname}").triggerChange("${param.controlId}");
							      	close(winId);
								  }else{
										alert("请选择末级科目级次!");
								  }
					  },
					  error:function(val){
						  
					  }
			  });  
		})
		$(document).on("click","#btn_qd",function(){
			var checkbox=$(".keyId").filter(":checked");
			if(checkbox.length==0){
				alert("请选择一条信息！");
				return;
			}else if(checkbox.length>1){
				alert("只能选择一条信息！");
				return;
			}else{
				var jjfl=$("#mydatatables").find(".keyId").filter(":checked").data("jjfl");
				var jjflmc=$("#mydatatables").find(".keyId").filter(":checked").data("jjflmc");
				var jjkmjc=$("#mydatatables").find(".keyId").filter(":checked").data("jjkmjc");
				    
				    $.ajax({
						  url:"${ctx}/kjhs/pzxx/pzlr/doSelectSfmj?kmbh="+jjfl,
							  data:"",
							  type:"post",
							  async:"false",
							  success:function(val){
							        	if(val=="0"){
							        		getIframeControl("${param.pname}","${param.controlId}").val(jjflmc);
											getIframeControl("${param.pname}","${param.fyid}").val(jjfl);	
									      	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
									      	getIframeControl("${param.pname}","${param.fyid}").focus();//手动触发验证
									      	close(winId);
											}else{
												alert("请选择末级科目级次!");
											}
							  },
							  error:function(val){
								  
							  }
					  });  
			}
		});
		 $("#btn_submit").click(function(){
		    	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
		    	console.log("_______________"+winId)
		    	var checkbox = $(".keyId").filter(":checked");
		    	if(checkbox.length==0){
		    		alert("请至少选择一条信息！");
		    		return;
		    	}else{
		    		var xmid = [];
		    		var kmbh = [];
		        	var kmmc = [];
		        	$.each(checkbox,function(i,v){
		        		var xmidList = $(this).val();
		        		var kmbhList = $(this).attr("data-jjfl");
		        		var kmmcList = $(this).attr("data-jjflmc");
		        		
		                xmid.push(xmidList);  
		        		kmbh.push(kmbhList);
		        		kmmc.push(kmmcList);
		        	});
		        	console.log("___________"+kmbh+"____"+kmmc)
		        	getIframWindow("${param.pname}").addxmxx(xmid,kmbh,kmmc);
		        	close(winId);
		    	}
		    	
		    });
	}
	
});
$(document).on("keydown","#txt_xm",function(e){
	if(e.keyCode==13){
	   $("#btn_search").click();
	}
});
</script>
</body>
</html>