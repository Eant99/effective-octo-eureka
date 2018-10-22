<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>会计科目设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	select{
		font-size:12px !important; 
		max-height: 25px !important;  
		padding: 0px !important;
		padding-left: 5px !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-top:8px">
    		<div class="search-simple">
    		<div class="form-group">
					<label>科目编号</label>
					<input type="text" id="txt_xm" class="form-control input-radius" name="kmbh"  table="k" placeholder="请输入科目编号">
				</div>
				<div class="form-group">
					<label>科目名称</label>
					<input type="text" class="form-control input-radius" name="kmmc"  table="k" placeholder="请输入科目名称">
				</div>
				
<!-- 				<div class="form-group"> -->
<!-- 				    <select class="form-control" name="CZRQ"  id="txt_kmnd"  > -->
<%-- 				    <c:choose>	 --%>
<%-- 				    	<c:when test="${empty kmnd }"> --%>
<%-- 	               		   <c:forEach var="nlist" items="${nlist}">  	 --%>
<%--              			     <option value="${nlist.kmnd}" <c:if test="${nlist.kmnd==jn}">selected</c:if> >${nlist.kmnd }</option>  --%>
<%--              			  </c:forEach> --%>
<%--              			</c:when> --%>
<%--              			<c:otherwise> --%>
<%--              				  <c:forEach var="nlist" items="${nlist}">  	 --%>
<%--              			     <option value="${nlist.kmnd}" <c:if test="${nlist.kmnd==kmnd}">selected</c:if> >${nlist.kmnd }</option>  --%>
<%--              			  </c:forEach> --%>
<%--              			</c:otherwise> --%>
<%--              	     </c:choose> --%>
<!-- 	             	</select> -->
<!-- 				</div> -->
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button class="btn btn-default" type="button" id="btn_addtj">增加同级</button>
	                <button class="btn btn-default" type="button" id="btn_addxj">增加下级</button>
	              <!--   <button class="btn btn-default" type="button" id="btn_copy">科目复制</button> -->
            	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
		    	<input type="hidden" name="guid" value="${guid}"/>
		        <table id="mydatatables" class="table table-striped table-bordered">
					<thead>
		 				<tr>
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						    <th>科目编号</th>
						    <th>科目名称</th>
						    <th>科目属性</th>
						    <th>助记符</th>
						    <th>余额方向</th>
<!-- 						    <th>核算类别</th> -->
						    <th>是否启用</th>
<!-- 						    <th>备注</th> -->
						    <th>科目级次</th>
<!-- 						    <th>余是否经济分类科目</th> -->
<!-- 						    <th>是否功能分类科目</th> -->
<!-- 						    <th>部门核算</th> -->
<!-- 						    <th>项目核算</th> -->
<!-- 						    <th>是否网银行</th> -->
						    <th>操作</th>
			   			</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
 <%@include file="/static/include/public-list-js.inc"%> 
 <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> 
<script >
$(function(){
	var columns = [
	       		{"data": "guid",orderable:false, "render": function (data, type, full, meta){
	       	       	return '<input type="checkbox" guid="'+full.GUID+'" kmjc="'+full.KMJC+'" class="keyId" name="guid" kmbh="'+full.KMBH+'" value="' + data + '">';
	       	    },"width":10,'searchable': false},
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},
	       		{"data": "KMBH","class":"hj","render":function (data, type, full, meta){
			     	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'" dwbh = "'+full.GUID+'">'+ data +'</a></div>';
		        }},
	       	   	{"data": "KMMC",defaultContent:"","class":"text-left"},
	       	   	{"data": "KMSXMC",defaultContent:"","width":30},
	       	   	{"data": "ZJF",defaultContent:""},
	       	   	{"data": "YEFXMC",defaultContent:"","width":30,"class":"text-center",},
// 	       	   	{"data": "HSLBMC",defaultContent:""},
	       	   	{"data": "QYFMC",defaultContent:"","width":30,"class":"text-center",},
// 	       	 	{"data": "BZ",defaultContent:""},
	       	   	{"data": "KMJC",defaultContent:"","width":30,"class":"text-center",},
// 	       	   	{"data": "SFJJFLKMMC",defaultContent:""},
// 	       	 	{"data": "SFGNFLKMMC",defaultContent:""},
// 	       		{"data": "BMHSMC",defaultContent:""},
// 	       		{"data": "XMHSMC",defaultContent:""},
// 	       		{"data": "SFWYHMC",defaultContent:""},
	       	   	{"data":"GUID","render":function (data, type, full, meta){
       	   			return '<a href="javascript:void(0);" class="btn btn-link btn_upd" dwbh = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" dwbh = "'+full.GUID+'">删除</a>';
	       	   	},orderable:false,"width":120,"class":"text-center"}	
	       	];
	       	
	var dd="${kmbh}";          

          table = getDataTableByListHj("mydatatables","${ctx}/kjkmsz/getGnkmszPageList?treedwbh=${dwbh}&treesearch=${treesearch}&kmnd=${kmnd}&dm=${dm}&kmmc=${kmmc}${kmjc}&bhww="+dd,[2,'asc'],columns,0,1,setGroup);
	//添加按钮
	$(document).on("click","#btn_addtj",function(){
   		var kmjc = "${param.kmjc}";
 		var dm = "${param.kmbh}";
//  		var l = "${param.l}";
//  		var k = "${param.k}";
 		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
 		var kmjcr = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmjc");
 		var kmbh2 = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmbh");
 		var d = ${dm == null};
 		if(checkbox.length>1){
   			alert("添加时列表中只能选择一个科目节点！");
   		}else if(checkbox.length>0){
   		
   	   		doOperate("${ctx}/kjkmsz/goGnkmszEditPage?operateType=C&dm=${dm}&kmbh="+kmbh2+"&kmjc="+kmjcr+"&flag=0&sfmj=${sfmj}&kmsx=${kmsx}&aType=tj&yefx=${yefx}","C");   			
   		}else{
   			
   			if(dm==""){
   	 			alert("请选择左侧科目或者右侧列表节点！");
   	 			return;
   	 		}	
   			doOperate("${ctx}/kjkmsz/goGnkmszEditPage?operateType=C&dm=${dm}&kmbh=${kmbh}&kmjc=${kmjc}&flag=0&sfmj=${sfmj}&kmsx=${kmsx}&aType=tj&yefx=${yefx}","C");
   		}
   	});
    //年度变化
    /* $("#txt_kmnd").change(function(){
    	var kmnd = $(this).val();doOperate("${ctx}/kjkmsz/goGnkmszEditPage?operateType=C&dm=${dm}&kmbh=${kmbh}&kmjc=${kmjc}&flag=0&sfmj=${sfmj}&kmsx=${kmsx}&aType=tj","C");
    	window.location.href="${ctx}/kjkmsz/goKmszPage?opre=m&kmnd="+kmnd;
    }); */

    $(document).on("click","#btn_addxj",function(){
		var sfmj1 = "${sfmj}";
		var sfmjsj = parseInt(sfmj1);
		var kmjc = "${kmjc}";
		

		var sfmjxj =0;
		if(sfmjsj==0){
			sfmjsj=1;
		}
		var d = ${dm == null};
		var kmjc = "${param.kmjc}";
 		var dm = "${param.kmbh}";
//  		var l = "${param.l}";
//  		var k = "${param.k}";
 		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
 		var kmjcr = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmjc");
 		var kmbh1 = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmbh");
 		var kmjce = Number(kmjc)+1;
 		var d = ${dm == null};
 		if(checkbox.length>1){
   			alert("添加时列表中只能选择一个科目节点！");
   		}else if(checkbox.length>0){
   		
   	   		doOperate("${ctx}/kjkmsz/goGnkmszEditPage?operateType=C&dm=${dm}&kmbh="+kmbh1+"&kmjc="+kmjcr+"&flag=0&sfmj=${sfmj}&kmsx=${kmsx}&aType=xj&yefx=${yefx}","C");   			
   		}else{
   			
   			if(dm==""){
   	 			alert("请选择左侧科目或者右侧列表节点！");
   	 			return;
   	 		}	
   			doOperate("${ctx}/kjkmsz/goGnkmszEditPage?operateType=C&dm=${dm}&kmbh=${kmbh}&kmjc=${kmjc}&flag=0&sfmj=${sfmj}&kmsx=${kmsx}&aType=xj&yefx=${yefx}","C");
   		}
   	});
    $(document).on("click","#btn_copy",function(){
		select_commonWin("${ctx}/kjkmsz/goCopyPage?operateType=C","复制信息","400","450");
	});
	
	/* //单条删除
	$(document).on("click",".btn_delxx",function(){
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var shzt = checkbox.attr("shzt");
   		if(shzt=="已提交"||shzt=="通过"){${ctx}/kjkmsz/goGnkmszEditPage?operateType=C&dm=${dm}&kmbh=${kmbh}&kmjc=${kmjc}&flag=0&sfmj=${sfmj}&kmsx=${kmsx}&aType=xj","C");
   			alert("已提交或者审核通过的无法修改");
   			return;
   		}
		confirm("确定删除？","{title:提示信息}",function(){
			alert("删除成功");
		});
	}); */
	//删除
	$(document).on("click",".btn_delxx",function(){
   		var dwbh = $(this).attr("dwbh");
   		var jb = $(this).parent("tr").find("[name=guid]").attr("jb");
   		$.ajax({
			type :"post",
			url:"${pageContext.request.contextPath}/kjkmsz/doCheck?kmguid="+dwbh+"&type=3",
			dataType:"json",
			async:false,
			success:function(val) {
			  if(val.success == "true"){
				  alert("数据正在使用，无法删除！");
			 }else{
				 doDel("dwbh="+dwbh,"${ctx}/kjkmsz/doDeletekmsz?jb="+jb,function(val){
//		    			location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
		   			//table.ajax.reload();
		   			window.parent.location.href = "${ctx}/window/mainGnkmszTree";
		   		},function(val){
		   		},"1");
			 };	
			},
			error:function() {
               alert("请稍后再试！");
            }
		});
   	});
	
	//编辑
   	$(document).on("click",".btn_upd",function(){
   		var dwbh = $(this).attr("dwbh");
		doOperate("${ctx}/kjkmsz/goGnkmszEditPage?dwbh="+dwbh+"&flag=2","U");
	});
  //查看
   	$(document).on("click",".btn_look",function(){
   		var dwbh = $(this).attr("dwbh");
		doOperate("${ctx}/kjkmsz/goGnkmszLookPage?dwbh="+dwbh+"&flag=2","U");
	});
  
// 	$("#txt_kmnd").change(function(){
// 		var kmnd = $(this).val();
// 		window.location="${ctx}/kmsz/goGnkmszPage?kmnd="+kmnd;
// 	});
	
	$("#txt_kmnd").change(function(){
  		tableRelod(table);
  	});
   	
    var tableRelod = function(table) {
		var kmnd=$("[id='txt_kmnd']").val();
  	    table.ajax.url("${ctx}/kjkmsz/getGnkmszPageList?kmnd="+kmnd);
  	    table.ajax.reload();
  	};
	
});

</script>
</body>
</html>