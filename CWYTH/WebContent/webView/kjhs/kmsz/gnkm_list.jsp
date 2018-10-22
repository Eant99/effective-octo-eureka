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
					<label>科目名称</label>
					<input type="text" class="form-control input-radius" name="kmmc"  table="k" placeholder="请输入科目名称">
				</div>
				<div class="form-group">
					<label>科目编号</label>
					<input type="text" id="txt_xm" class="form-control input-radius" name="kmbh"  table="k" placeholder="请输入科目编号">
				</div>
				<div class="form-group">
				  
<!-- 				    <select class="form-control" name="kmnd"  id="txt_kmnd"  >	 -->
				      
<%-- 				            <c:choose >   --%>
<%-- 				              <c:when test="${empty kmnd }">  --%>
<%-- 				               <c:forEach var="nlist" items="${nlist}">  	 --%>
<%--   	             			     <option value="${nlist.kmnd}" <c:if test="${nlist.kmnd==jn}">selected</c:if> >${nlist.kmnd }</option>  --%>
<%--   	             			   </c:forEach> --%>
<%--   	             			  </c:when>  --%>
  	             			  
<%--    	             			  <c:otherwise>  --%>
<%--    	             			     <c:forEach var="nlist" items="${nlist}">   --%>
<%--    	             			         <option value="${nlist.kmnd}" <c:if test="${nlist.kmnd==kmnd}">selected</c:if> >${nlist.kmnd }</option>   --%>
<%--   	             			     </c:forEach> --%>
<%--    	             			  </c:otherwise>  --%>
<%--   	             	      </c:choose>   --%>
 	             	      
<!-- 	             	</select> -->
<!-- 						 <select class="form-control" id="txt_kjzd"  > -->
<!-- 						 			   <option value="all">全部</option> -->
<%-- 						 			<c:forEach var="zdlist" items="${zdlist }"> --%>
<%-- 						 				<option value="${zdlist.GUID }" <c:if test="${zdlist.GUID==kjzd }">selected</c:if>>${zdlist.ZDM }</option> --%>
<%-- 						 			</c:forEach> --%>
<!-- 						 </select> -->
	             	   
					<%-- <label>年度</label>
					<input type="text" id="txt_sbnd" class="form-control input-radius window year" name="nd" table="k" value="<%=date %>" data-format="yyyy"/>				 --%>
				</div>
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
						    <th>核算类别</th>
						    <th>是否启用</th>
						   
						    <th>科目级次</th>
						    <th>是否经济分类科目</th>
						    <th>是否功能分类科目</th>
						    <th>部门核算</th>
						    <th>项目核算</th>
						    <th>是否网银</th>
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
	var yefx1="${yefx}";
	var kmsx1="${kmsx}";
	var columns = [
	       		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	       	return '<input type="checkbox" kmmc="'+full.KMMC+'"  kjzd="'+full.KJZD+'"  kmbh= "'+full.KMBH+'" sfkjzd="'+full.SFKJZD+'" sfmj="'+full.SFMJ+'" jb="'+full.JB+'" class="keyId" name="guid" sjfl="'+full.SJFL+'" yefx="'+full.YEFX+'" kmjc="'+full.KMJC+'" kmsx="'+full.KMSX+'" bz="'+full.BZS+'" value="' + data + '">';
	       	    },"width":10,'searchable': false},
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},
	       	    {"data": "KMBH","render":function (data, type, full, meta){
	 		     	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'" sfkjzd="'+full.SFKJZD+'" kmjc="'+full.KMJC+'" dwbh = "'+full.GUID+'">'+ data +'</a></div>';
	 	        }},
	       	   	{"data": "KMMC",defaultContent:"","class":"text-left"},
	       	   	{"data": "KMSXMC",defaultContent:""},
	       	   	{"data": "ZJF",defaultContent:""},
	       	   	{"data": "YEFXMC",defaultContent:"","class":"text-center",},
	       	   	{"data": "HSLBMC",defaultContent:""},
	       	   	{"data": "QYFMC",defaultContent:"","class":"text-center",},
	       	   	{"data": "KMJC",defaultContent:"","class":"text-center",},
	       	   	{"data": "SFJJFLKMMC",defaultContent:"","class":"text-center",},
	       	 	{"data": "SFGNFLKMMC",defaultContent:"","class":"text-center",},
	       		{"data": "BMHSMC",defaultContent:"","class":"text-center",},
	       		{"data": "XMHSMC",defaultContent:"","class":"text-center",},
	       		{"data": "SFWYHMC",defaultContent:"","class":"text-center",},
	       	   	{"data":"GUID","render":function (data, type, full, meta){
       	   			return '<a href="javascript:void(0);" class="btn btn-link btn_upd" sfmj="'+full.SFMJ+'" kjzd="'+full.KJZD+'" sfkjzd="'+full.SFKJZD+'" kmjc="'+full.KMJC+'" dwbh = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" sjfl="'+full.SJFL+'" kmmc="'+full.KMMC+'" kjzd="'+full.KJZD+'" kmbh="'+full.KMBH+'" sfkjzd="'+full.SFKJZD+'" dwbh = "'+full.GUID+'">删除</a>';
	       	   	},orderable:false,"width":90}	
	       	];
	       	
          table = getDataTableByListHj("mydatatables","${ctx}/kjkmsz/getPageList?treedwbh=${dwbh}&kmnd=${kmnd}&dm=${dm}&jb=${jb}&kmjz=${kjzd}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
    $(document).on("click","#btn_addtj",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var mkbh="${mkbh}"
   			var kmbh = "${param.kmbh}";
			var kmbh1 = null;
	 		//var bh = "${kmbh}";
	 		if(kmbh==null || kmbh.length==0){
	 			kmbh1 = checkbox.attr("kmbh");
	 		}else{
	 			kmbh1= kmbh;
	 		}
        var d = ${dm == null};
 	
 		var kmjcr = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmjc");
 		var jb1 = $("#mydatatables").find("[name='guid']").filter(":checked").attr("jb");	
 		var sjfl = $("#mydatatables").find("[name='guid']").filter(":checked").attr("sjfl");
 		var yefx2 = $("#mydatatables").find("[name='guid']").filter(":checked").attr("yefx");
 		var kmsx2 = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmsx");
 		var zd = "${kjzd}";
 		var kjzd = null;
 		if(yefx1==""){
 			yefx3=yefx2;
 		}else{
 			yefx3=yefx1;
 		}
 		if(kmsx1==""){
 			kmsx3=kmsx2;
 		}else{
 			kmsx3=kmsx1;
 		}
 		if(zd==null || zd.length==0){
 			kjzd = checkbox.attr("kjzd");
 		}else{
 			kjzd = zd;
 		}
 		if(checkbox.length>1){
    			alert("添加时列表中只能选择一个科目节点！");
    		}else if(checkbox.length>0){
    		 if("${dm}"=="root"){
 			alert("根目录不能增加同级！");
    		 }else if(kmjcr=="1"){
    	   			alert("1级科目不能增加同级");
    	   		}
    	   		else{
    			doOperate("${ctx}/kjkmsz/goEditPage?operateType=C&dm="+sjfl+"&jb="+jb1+"&kmjc="+kmjcr+"&flag=0&sfmj=${sfmj}&kmsx="+kmsx3+"&kjzd="+kjzd+"&mkbh="+mkbh+"&kmbh="+kmbh1+"&yefx="+yefx3,"C");			
    	      	 }
    		 }else{
    		  		if(d ){
    		   			alert("请先选择会计科目！");
    		   		}else if("${dm}"=="root"){
    		   			alert("根目录不能增加同级！");
    		   		}else if("${kmjc}"=="1"){
    		   			alert("第一级不能增加同级");
    		   		}
    		   		else{
    			   		doOperate("${ctx}/kjkmsz/goEditPage?operateType=C&dm=${dm}&jb=${jb}&kmjc=${kmjc}&flag=0&sfmj=${sfmj}&kmsx="+kmsx3+"&kjzd="+kjzd+"&mkbh="+mkbh+"&kmbh="+kmbh1+"&yefx="+yefx3,"C");
    		   		}
     		}
    	});
    //年度变化
    /* $("#txt_kmnd").change(function(){
    	var kmnd = $(this).val();
    	window.location.href="${ctx}/kjkmsz/goKmszPage?opre=m&kmnd="+kmnd;
    }); */
    $(document).on("click","#btn_addxj",function(){
		var mkbh="${mkbh}"
		
		var checkbox =$("#mydatatables").find("[name='guid']").filter(":checked");
		var kmjcr = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmjc");
 		var jb = $("#mydatatables").find("[name='guid']").filter(":checked").attr("jb");
 		var yefx2 = $("#mydatatables").find("[name='guid']").filter(":checked").attr("yefx");
 		var kmsx2 = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmsx");
 		var sfmja = $("#mydatatables").find("[name='guid']").filter(":checked").attr("sfmj");
 		var sfmjb = "${sfmj}";
 		if(sfmjb==""){
 			sfmjc=sfmja;
 		}else{
 			sfmjc=sfmjb;
 		}
 		var guid = null;
 		var id = "${id}";
 		if(id==null || id.length==0){
 			guid = checkbox.val();
 		}else{
 			guid = id;
 		}
 		var zd = "${kjzd}";
 		var kjzd = null;
 		if(yefx1==""){
 			yefx3=yefx2;
 		}else{
 			yefx3=yefx1;
 		}
 		if(kmsx1==""){
 			kmsx3=kmsx2;
 		}else{
 			kmsx3=kmsx1;
 		}
 		if(zd==null || zd.length==0){
 			kjzd = checkbox.attr("kjzd");
 		}else{
 			kjzd = zd;
 		}
 		var kmnd = "${kmnd}"				
 		if(jb==null){
 			jb="${jb}";
 			kmjcr="${kmjc}";
 		}
 		if(kmjcr==null || kmjcr.length==0){
 			alert("请选择一个科目");
 		}else if("${dm}"=="root") {
 			alert("根目录不能增加下级");
 		}else{
 			var sfmj1 = "${sfmj}";
 			var sfmjsj = parseInt(sfmj1);
 			var kmjc = "${kmjc}";
 			var kmbh = "${param.kmbh}";
 			var kmbh1 = null;
 	 		//var bh = "${kmbh}";
 	 		if(kmbh==null || kmbh.length==0){
 	 			kmbh1 = checkbox.attr("kmbh");
 	 		}else{
 	 			kmbh1= kmbh;
 	 		}


 			var sfmjxj =0;
 			if(sfmjsj==0){
 				sfmjsj=1;
 			}			
 			//alert(d);
 	   		if(jb==null){
 	   			//alert("请先选择会计科目！");
 	   		}else{
 	   		$.ajax({
 	 			url:"${ctx}/kjkmsz/docheckye?kmnd="+kmnd+"&kmbh="+kmbh1,
 	 			data:"guid="+guid,
 	 		    type:"post",
 	 		    dataType:"json",
 	  			async:"false",
 	  			success:function(val){

 	  				if((val.flag==null || val.flag.length==0||val.flag=="0"||sfmjc=="0")&&(val.bdje==null || val.bdje.length==0||val.bdje=="0")){
 	  			   		doOperate("${ctx}/kjkmsz/goEditPage2?operateType=C&dm=${dm}&bdje="+val.bdje+"&kmzye="+val.flag+"&kmbh="+kmbh1+"&jb="+jb+"&kmjc="+kmjcr+"&flag=1&kmsx="+kmsx3+"&guid="+guid+"&sfmjsj="+sfmjsj+"&sfmjxj="+sfmjxj+"&kjzd="+kjzd+"&mkbh="+mkbh+"&yefx="+yefx3+"&sfmjc="+sfmjc,"C");
 	  				}else{
 	  					confirm("当前科目的余额为"+val.flag+",变动金额为"+val.bdje+",确定要增加该科目的下级吗？","",function(z){
 	  						close(z);
 	 	  			   	doOperate("${ctx}/kjkmsz/goEditPage2?operateType=C&dm=${dm}&bdje="+val.bdje+"&kmzye="+val.flag+"&kmbh="+kmbh1+"&jb="+jb+"&kmjc="+kmjcr+"&flag=1&kmsx="+kmsx3+"&guid="+guid+"&sfmjsj="+sfmjsj+"&sfmjxj="+sfmjxj+"&kjzd="+kjzd+"&mkbh="+mkbh+"&yefx="+yefx3+"&sfmjc="+sfmjc,"C");
 	  					});
 	  				}
 	  				
 	  			},
 	  			error:function(){
 	  			}
 	 			
 	 		});
 	   	

 	   		}
 		}
		
   	});
    $(document).on("click","#btn_copy",function(){		
		select_commonWin("${ctx}/kjkmsz/goCopyPage?operateType=C","复制信息","400","450");
	});
	//删除
	$(document).on("click",".btn_delxx",function(){
		var mkbh = "${mkbh}"
   		var dwbh = $(this).attr("dwbh");
   		var sjfl = $(this).attr("sjfl");
   	    var kmmc = $(this).attr("kmmc");
   	    var kmbh = $(this).attr("kmbh");
   		var sfkjzd = $(this).attr("sfkjzd");
   		var kjzd = $(this).attr("kjzd");
 
   		if(sfkjzd=="1"){
   		   alert("会计科目制度中的科目不允许删除");
   		}else{
   			$.ajax({
   				type :"post",
   				url:"${pageContext.request.contextPath}/kjkmsz/doCheck?kmguid="+dwbh+"&type=1",
   				dataType:"json",
   				async:false,
   				success:function(val) {
   				  if(val.success == "true"){
   					  alert("数据正在使用，无法删除！");
   				 }else{
   					 doDel("dwbh="+dwbh,"${ctx}/kjkmsz/doDelete?sjfl="+sjfl+"&kmmc="+kmmc+"&kmbh="+kmbh+"&kjzd="+kjzd+"&mkbh="+mkbh,function(val){

   			   		//	table.ajax.reload();//此方法只会刷新右边界面，左边依然有删除的科目
   			   			window.parent.location.href = "${ctx}/window/mainKjkmszTree?mkbh="+mkbh;
   			   		},function(val){
   			   		},"1"); 
   				 };	
   				},
   				error:function() {
   	               alert("请稍后再试！");
   	            }
   			});
   		}
   	
   	});
	
	//查看
   	$(document).on("click",".btn_look",function(){
   		var dwbh = $(this).attr("dwbh");
   		var kmjc = $(this).attr("kmjc");
   		var sfkjzd = $(this).attr("sfkjzd");
   			doOperate("${ctx}/kjkmsz/goLookPage?dwbh="+dwbh+"&flag=2&kmjc="+kmjc+"&sfkjzd="+sfkjzd,"U");
	});
  //编辑
   	$(document).on("click",".btn_upd",function(){
   		var mkbh = "${mkbh}"
   		var kjzd = $(this).attr("kjzd");
   		var dwbh = $(this).attr("dwbh");
   		var kmjc = $(this).attr("kmjc");
   		var sfkjzd = $(this).attr("sfkjzd");
   		var sfmj = $(this).attr("sfmj");
   		var jb = "${jb}";
   		console.log("______"+jb);
 //  		if(sfkjzd=="1"){
//    			alert("会计制度中的科目不允许修改");
//    		}else{
   			doOperate("${ctx}/kjkmsz/goEditPage?dwbh="+dwbh+"&flag=2&kmjc="+kmjc+"&sfkjzd="+sfkjzd+"&mkbh="+mkbh+"&kjzd="+kjzd+"&sfmj="+sfmj+"&jb="+jb,"U");
  // 		}
	
	});
	
});
// $("#txt_kmnd").change(function(){
// 	var kmnd = $(this).val();
// 	window.location="${ctx}/kjkmsz/goKmszPage?kmnd="+kmnd;
	
// });
// $("#txt_kjzd").change(function(){
// 	var kjzd = $(this).val();
// 	window.location="${ctx}/kjkmsz/goKmszPage?kjzd="+kjzd;
	
// });

// 下拉框改变  
// ajax刷新，仿财务一体化/网上报销/报销业务查询 
$("#txt_kjzd").change(function(){
	tableRelod(table);
});

var tableRelod = function(table) {
	var kjzd = $("[id='txt_kjzd']").val();
    table.ajax.url("${ctx}/kjkmsz/getPageList?kjzd="+kjzd);
    table.ajax.reload();
};

</script>
</body>
</html>