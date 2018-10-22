<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支付信息汇总</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;height: 40px;">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			    <div class="form-group">
					<label>支付状态</label>
					<select class="form-control" id="txt_zfzt" name="zfzt">
						<c:forEach items="${zfztList}" var="item">
							<option value="${item.DM }" <c:if test="${item.DM eq param.zfzt }">selected</c:if>>${item.MC }</option>
						</c:forEach>						
					</select>
				</div>
				<div class="form-group">
					<label>支付方式</label>
					<select class="form-control" id="txt_zffs" name="paymethod">
							<option value="">--请选择--</option>
							<option value="wx" <c:if test="${'wx' eq param.zffs }">selected</c:if>>微信</option>
							<option value="ali" <c:if test="${'ali' eq param.zffs }">selected</c:if>>支付宝</option>
					</select>
				</div>
				<div class="form-group">
							<label>消费时间</label>
							<input   type="text" name="XFSJ"  id="txt_km1" class="input_info  form-control"  placeholder="开始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:130px;" value="${xfsj1}"  />
							<label>至</label>
							<input   type="text" name="XFSJ"  id="txt_km2" class="input_info  form-control" placeholder="截止时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:130px;" value="${xfsj2}"  />
				</div>
				<button type="button" class="btn btn-primary xfsj" id=""><i class="fa icon-chaxun"></i>查询</button>
                <div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more" style="width: 450px">
						<div class="form-group">
							<label>承包商名称</label>
							<input type="text" id="txt_cbsmc" class="form-control" name="CBSMC"  placeholder="请输入承包商名称">
							<button type="button" id="btn_cbsmc" class="btn btn-link ">选择</button>
						</div>
						<div class="form-group">
							<label>所属校区</label>
							<select class="form-control" id="txt_zlwz" name="zlwz" table="X">
								<option value="">--请选择--</option>
								<c:forEach items="${xqlist}" var="xqlist">
									<option value="${xqlist.DM }">${xqlist.MC }</option>
								</c:forEach>					
							</select>
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search" >
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
<!-- 								<button type="button" class="btn btn-default" id="btn_cancel"> -->
<!-- 									取 消 -->
<!-- 								</button> -->
							</div>
						</div>
					</div>
				</div>
				   <div class="btn-group pull-right" role="group">
<!-- 		               <button type="button" class="btn btn-default" id="btn_zhifu">微信支付</button> -->
<!-- 		               <button type="button" class="btn btn-default" id="btn_zhifu">支付宝支付</button> -->
<!-- 		               <button type="button" class="btn btn-default" id="btn_zhifu">银行支付</button> -->
<button type="button" class="btn btn-default" id="btn_export">导出Excel</button>
					</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>支付状态</th>
				            <th>支付方式</th>
				            <th>所属校区</th>
				            <th>承包商名称</th>
				            <th>消费总额(元)</th>
				            <th>支付金额(元)</th>				                   
				            
				        </tr>
					</thead>
				    <tbody>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	var zfzt = $("[name='zfzt']").val();
	//列表数据
    var columns = [
       {"data": "ROWNUM",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.ROWNUM+'" xfze="'+full.ZFZE+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "ZFZTMC",defaultContent:""},
       {"data": "ZFFS",defaultContent:""},
       {"data": "ZLWZMC",defaultContent:""},
       {"data": "CBSMC",defaultContent:""},
       {"data": "XFJE",orderable:false,'render':function(data,type,full,meta){
    	   if(full.XFJE==null||full.XFJE==''||full.XFJE=='undefident'){
    		   return '0.00';
    	   }
    	   else {
    		   return full.XFJE;
    	   }
    	   },"class":"text-right"},       
       {"data": "ZFZE",orderable:false,'render':function(data,type,full,meta){
    	   if(full.ZFZE==null||full.ZFZE==''||full.ZFZE=='undefident'){
    		   return '0.00';
    	   }
    	   else {
    		   return full.ZFZE;
    	   }
    	   },"class":"text-right"}       
       
     ];
    	table = getDataTableByListHj("mydatatables","${ctx}/wxzf/getHzPageList?zfzt=${zfzt}&zffs=${zffs}&xfsj1=${xfsj1}&xfsj2=${xfsj2}",[0,'asc'],columns,0,1,setGroup);

 	//汇总按钮
   	$("#btn_back").click(function(){
   		window.location.href = "${ctx}/wxzf/goPageList";
   	});
   	$("#btn_zhifu").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked"); 
   		
   		if(checkbox.length!=1){
   			alert("请选择一条数据进行支付!"); 
   		}else{
   			checkbox.each(function(){  
   				if($(this).attr("xfze") =="" ||$(this).attr("xfze") ==null ||$(this).attr("xfze")=='undefined'){
   					alert("没有可支付金额");
   				}
   				else{
   					select_commonWin("${ctx}/wxzf/goZfPage?guid="+$(this).val(),"微信支付信息","600","500");
   				}
   			});
   			
   		}
   			
   	});
   	$("#btn_over").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
	   	   	confirm("确定已支付成功？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/wxzf/doUpdateZfzt",
	   	   			data:"guid="+guid.join(","),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("完成支付");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	}); 
   //查询弹窗--承包商
   	$("#btn_cbsmc").click(function(){
		select_commonWin("${ctx}/wxzf/ToWindow?controlId=txt_cbsmc","承包商信息","920","630");
    });
   
   //查询弹窗--消费地点
   	$("#btn_xfddmc").click(function(){
		select_commonWin("${ctx}/wxzf/ToWindow?controlId=txt_xfdd","消费地点信息","920","630");
    });
   //
	$(".xfsj").click(function(){
		var zfzt = $("#txt_zfzt").val();
		var zffs = $("#txt_zffs").val();
		var xfsj1 = $("#txt_km1").val();
		var xfsj2 = $("#txt_km2").val();
		location.href=("${ctx}/wxzf/goPageListXfjs?zfzt="+zfzt+"&zffs="+zffs+"&xfsj1="+xfsj1+"&xfsj2="+xfsj2);
    });
});
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
//导出Excel
$(document).on("click","#btn_export",function(){
 	var guid = [];
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		if(checkbox.length > 0){
		checkbox.each(function(){
			guid.push($(this).val());
		});
		guid = guid.join("','");
		}else{
			guid = "";
		}
		$.ajax({
			type : "post",
			data : $("#myform").serialize()+"&guid="+guid,
			url : "${ctx}/wxzf/expExcel",
			success : function(val) {
				var data = JSON.getJson(val);
			 FileDownload("${ctx}/file/fileDownload","消费管理表.xls",data.url);
		   }
	}); 
});
</script>
</body>
</html>