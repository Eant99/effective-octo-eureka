<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>经济科目设置</title>

<%@include file="/static/include/public-manager-css.inc"%> 
<style type="text/css">
	select{
		font-size:12px !important; 
		max-height: 25px;  
		padding: 0px !important;
		padding-left: 5px !important;
	}
	#mydatatables td 
	{
	line-height:10px;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-top:8px">
    		<div class="search-simple">
				
				<div class="form-group">
					<label>要素编号</label>
					<input type="text" id="txt_xm" class="form-control input-radius" name="kmbh"  table="j" placeholder="请输入科目编号">
				</div>
				<div class="form-group">
					<label>要素名称</label>
					<input type="text" class="form-control input-radius" name="kmmc"  table="j" placeholder="请输入科目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
					<button type="button" class="btn btn-default" id="btn-del">批量删除</button>
	                <button class="btn btn-default" type="button" id="btn_addtj">增加同级</button>
	                <button class="btn btn-default" type="button" id="btn_addxj">增加下级</button>
	       		<!-- 	<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button> -->
	         	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
		    	<input type="hidden" name="guid" value="${guid}"/>
		        <table id="mydatatables" class="table table-striped table-bordered" >
					<thead>
		 				<tr >
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						    <th>要素编号</th>
						    <th>要素名称</th>
							<th>要素级次</th>	
						    <th>是否启用</th>	
						    <th>说明</th>						    					  
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
<%@include file="/static/include/public-list-css.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script >
$(function(){
	//保存按钮
	$(document).on("click","#btn_save",function(){
   		alert("保存成功");
   	});
	//批量删除
	$(document).on("click","#btn-del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			   var guid = [];
	   	   		checkbox.each(function(){
	   	   			guid.push($(this).attr("guid"));
	   	   		});
	   	   	$.ajax({
				type :"post",
				data:"kmguid="+guid.join(','),
				url:"${pageContext.request.contextPath}/kmsz/doCheck",
				dataType:"json",
				async:false,
				success:function(val) {
				  if(val.success == "true"){
					  alert("有数据正在使用，无法删除！");
				 }else{
					 confirm("确定删除所选"+checkbox.length+"条信息？","{title:'提示'}",function(){
			   	   			$.ajax({
			   	   	   			type:"post",
			   	   	   			url:"${ctx}/kmsz/hhhh",
			   	   	   			data:"guid="+guid.join("','"),
			   	   	   			success:function(data){
			   	   	   				var result = JSON.getJson(data);
			   	   	   				if(result.success){
			   							alert("删除成功！");  	   					
				   	   	   				table.ajax.reload();
			   	   	   				}else{
			   	   	   					alert("删除失败，请稍后重试！");
			   	   	   				}
			   	   	   			},
			   	   			error:function(){
			   	   				alert("抱歉，系统出现错误！");
			   	   			}
			   	   	   		});
			   	   		});
				 };	
				},
				error:function() {
	               alert("请稍后再试！");
	            }
			});
		
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
	//添加同级按钮
	$(document).on("click","#btn_addtj",function(){
 		var kmjc = "${param.kmjc}";
 		var dm = "${param.kmbh}";
 		var l = "${param.l}";
 		var k = "${param.k}";
//  		if(l=="null" || l==null || k==null || k=="null"){
//  			l = "";
//  			k = "";
//  		}
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var kmjcr = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmjc");
		var dm2 = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmbh");
   		if(checkbox.length>1){
   			alert("添加时列表中只能选择一个要素节点！");
   		}else if(checkbox.length>0){
   			var len = dm2.length;
   			k=dm2.substr(0,len-2);
   			dm=dm2;
   	   		doOperate("${ctx}/cbys/goEditCbysPage?type=tj&kmjc="+kmjcr+"&dm="+dm+"&l="+l+"&k="+k,"C");   			
   		}else{
   			alert("请选择右侧列表节点！");
   			return;
//    			if(dm==""){
//    	 			alert("请选择左侧科目或者右侧列表节点！");
//    	 			return;
//    	 		}	
//    	   		doOperate("${ctx}/cbys/goEditCbysPage?type=tj&kmjc="+kmjc+"&dm="+dm+"&l="+l+"&k="+k,"C");
   		}
 		

   	});
	//修改
 	 $(document).on("click",".btn_upt",function(){
 		var guid = $(this).attr("guid");
  		doOperate("${ctx}/cbys/goEditCbysPage?guid="+guid,"U");
	});
	
 	//查看
 	 $(document).on("click",".btn_look",function(){
 		var guid = $(this).attr("guid");
  		doOperate("${ctx}/cbys/goLookCbysPage?guid="+guid,"U");
	});
 	//增加下级
 	$(document).on("click","#btn_addxj",function(){
		var kmjc = "${param.kmjc}";
		//var kmjc = parseInt(kmjc1)+1;
		//console.log("kmjc==="+kmjc);
		var dm = "${param.kmbh}";
		console.log("kmbh==="+dm);
		var l = "${param.l}";
 		var k = "${param.k}";
//  		if(l=="null" || l==null){
//  			l = "";
//  		}
		if(kmjc==""||kmjc==null||kmjc=="undefined"||isNaN(kmjc)){
			kmjc = 1;
			l = "";
			k = "";
			dm = "";
		}else{
			kmjc = Number(kmjc)+1;
		}
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var kmjcr = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmjc");
		var dm2 = $("#mydatatables").find("[name='guid']").filter(":checked").attr("kmbh");
		if(checkbox.length>1){
   			alert("添加时列表中只能选择一个科目节点！");
    	}else if(checkbox.length>0){
    		dm=dm2;
    		doOperate("${ctx}/cbys/goEditCbysPage?type=xj&kmjc="+(parseInt(kmjcr)+1)+"&dm="+dm+"&l="+l+"&k="+k,"C");
   		}else{
   			alert("请选择右侧列表节点！");
   			return;
//    			if(dm==""){
//    	 			alert("请选择左侧科目或者右侧列表节点！");
//    	 			return;
//    	 		}	
//    	   		doOperate("${ctx}/cbys/goEditCbysPage?type=xj&kmjc="+kmjc+"&dm="+dm+"&l="+l+"&k="+k,"C");
   		}
		

   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).attr("guid");
		var kmbh = $(this).attr("kmbh");
		console.log(guid);
		$.ajax({
			type :"post",
			url:"${pageContext.request.contextPath}/kmsz/doCheck?kmguid="+guid,
			dataType:"json",
			async:false,
			success:function(val) {
			  if(val.success == "true"){
				  alert("数据正在使用，无法删除！");
			 }else{
				 doDel("guid="+guid+"&kmbh="+kmbh,"${ctx}/cbys/doDelete",function(val){
			   			parent.location.href = "${ctx}/window/mainCbysTree";
			   		},function(val){
			   		},"1");
			 };	
			},
			error:function() {
               alert("请稍后再试！");
            }
		});
		
	});
	
	var columns = [
		{"data": "guid",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" name="guid" kmbh="'+full.KMBH+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'" kmjc = "'+full.KMJC+'">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	//{"data": "KMBH",defaultContent:"","class":"hj"},
	   	{"data": "KMBH","class":"hj","render":function (data, type, full, meta){
		     	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
	        }},
	   	{"data": "KMMC",defaultContent:""},
	   	{"data": "KMJC",defaultContent:""},
		{"data": "QYF",defaultContent:""},
		{"data": "SM",defaultContent:""},
	   
	   	{"data":"GUID","render":function (data, type, full, meta){
	   		if(full.BZS != "1"){
	   			return '<a href="javascript:void(0);" class="btn btn-link btn_upt" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" kmbh="'+full.KMBH+'"  guid = "'+full.GUID+'">删除</a>';
	   		}else return '';
	   	},orderable:false,"width":90}
	];
   table = getDataTableByListHj("mydatatables","${ctx}/cbys/getCbysPageList?treeDm=${param.kmbh}&kmnd=${kmnd}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
   	//table = getDataTableByListHj("mydatatables","${ctx}/json/kjhs/kmsz/jjkmsz/jjkmsz_list.json?treeDm=${param.dm}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
   /* 	$("#btn_sskj").click(function(){
		select_commonWin("${pageContext.request.contextPath}/window/gnszpage?controlId=txt_sskj","会计科目信息","920","630");
    });  */
    $(document).on("click","#btn_sskj",function(){
   		select_commonWin("${ctx}/pzlx/window?controlId=txt_sskj","科目信息","920","630");
    });
    
//     $("#txt_kmnd").change(function(){
//     	var kmnd = $(this).val();
//     	window.location="${ctx}/kmsz/goJjkmPage?kmnd="+kmnd;
    	
//     });
	
    $("#txt_kmnd").change(function(){
    	tableRelod(table);
    });

    var tableRelod = function(table) {
    	var kmnd=$("[id='txt_kmnd']").val();

        table.ajax.url("${ctx}/cbys/getCbysPageList?kmnd="+kmnd);
        table.ajax.reload();
    };
    
});

</script>
</body>
</html>