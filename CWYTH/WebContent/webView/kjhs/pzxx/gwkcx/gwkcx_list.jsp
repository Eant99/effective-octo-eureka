<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>公务卡查询页面</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
			<form id="myform" class="form-inline" role="form" action="">
				<div class="search-simple">
					<div class="form-group">
						<label>导出状态</label>
						<div class="form-group">
							<select style="width:70px;" id="txt_dczt" class="form-control" table="A" name="dczt" >
								<option value="0"<c:if test="${dczt=='0'}">selected</c:if>>未导出</option>
								<option value="1"<c:if test="${dczt=='1'}">selected</c:if>>已导出</option>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label>支付状态</label>
						<div class="form-group">
							<select style="width:70px;" id="txt_zfzt" class="form-control" table="A" name="zfzt" >
								<option value="1"<c:if test="${zfzt=='1'}">selected</c:if>>已支付</option>
								<option value="0"<c:if test="${zfzt!='1'}">selected</c:if>>未支付</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label>凭证状态</label>
						<div class="form-group">
							<select style="width:70px;" id="txt_pzzt" class="form-control"  name="pzzt" >
								<option value="0"<c:if test="${pzzt=='0'}">selected</c:if>>已保存</option>
								<option value="1"<c:if test="${pzzt!='0'}">selected</c:if>>已复核</option>
							</select>
						</div>
					</div>
					<div class="form-group">
		                <label>凭证号</label>
		             	<input  name="pzh1" types="CX12" table="K" class="input_info  form-control" style="width:110px;" placeholder="请输入凭证号" value="${pzh1}"  />
						<label>至</label>
						<input  name="pzh2" types="CX22" table="K" class="input_info  form-control" placeholder="请输入凭证号" style="width:110px;" value="${pzh2}"  />
					</div>
					<div class="form-group">
		                <label>凭证日期</label>
		             	<input  name="pzrq1" types="CX1" table="K" class="input_info date form-control" style="width:110px;" placeholder="请输入凭证日期" value="${pzrq1}" data-format="yyyy-MM-dd" />
						<label>至</label>
						<input  name="pzrq2" types="CX2" table="K" class="input_info date form-control" placeholder="请输入凭证日期" style="width:110px;" value="${pzrq2}" data-format="yyyy-MM-dd" />
					</div>
					<button type="button" class="btn btn-primary" id="btn_search1">
						<i class="fa icon-chaxun"></i>查 询
					</button>
					<div class="btn-group pull-right" role="group">
               		</div>
<%--                		<c:if test="${param.zfzt!=1}"> --%>
               		<div class="btn-group pull-right" role="group">
               		<c:if test="${zfzt=='1'}">
               			<button type="button" class="btn btn-default no" id="btn_plfzf">批量反支付</button>
               		</c:if>
						<button type="button" class="btn btn-default no" id="btn_export">导出Excel</button>
					</div>
<%-- 					</c:if> --%>
				</div>
			</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<form id="myform1" class="add">
				<table id="mydatatables" class="table table-striped table-bordered">			
		        	<thead>
				       <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th >序号</th>
				            <th >导出状态</th>
				            <th >支付状态</th>
				            <th >凭证状态</th>
			                <th >凭证类型</th>
				            <th >凭证号</th>
				            <th >凭证日期</th>
				            <th >凭证摘要</th>
				            <th >姓名</th>
                            <th >银行名称</th>
				            <th >卡号</th>
				            <th >金额</th>
				            <th >刷卡金额</th>
				            <th >刷卡时间</th>
				            <th >操作</th>
				        </tr>	     
					</thead>
				    <tbody id="bod">
				    </tbody>
				</table>
			</form>
			</div>
		</div>
	</div>	
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
$(function () {
	var columns = [
		{"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	          return '<input type="checkbox" name="guid"  class="keyId" KHYH="'+full.KHYH+'" 111="'+full.ZFZT+'" BMBH="'+full.BMBH+'" KMBH="'+full.KMBH+'"  XMBH="'+full.XMBH+'"   HM="'+full.HM+'" ZH="'+full.ZH+'" JE="'+full.JE+'"  value="' + data + '" guid = "'+full.GUID+'">';
	        },"width":10,'searchable': false,"class":"text-center"},
	        {"data": "_XH",defaultContent:"","class":"text-center","width":20},
	        {"data": "DCZTMC",defaultContent:"","class":"text-left","width":50},
	        {"data": "ZFZTMC",defaultContent:"","class":"text-left","width":50},
	        {"data": "PZZTMC",defaultContent:"","class":"text-left","width":50},
	        {"data": "PZLXMC",defaultContent:"","class":"text-left","width":50},
	        {"data": "PZBH",defaultContent:"","class":"text-left","width":50},
	        {"data": "PZRQ",defaultContent:"","class":"text-center","width":50},
	        {"data": "ZY",defaultContent:"","class":"text-left",'render':function(data, type, full, meta){
         	   var maxlength=20;
      			if(data==""||data==null||data=="undefined"){
      				return "";
      			}else{
      				if(data.length>=maxlength){
      					return '<div class="change"><span title="'+data+'">'+data.substr(0,maxlength-3)+'...</sapn></div>'
      				}else{
      					return '<div class="change"><span title="'+data+'">'+ data +'</span></div>'
      				}
      			}
            }},
	        {"data": "XM",defaultContent:"","class":"text-left","width":50},
	        {"data": "YHMC",defaultContent:"","class":"text-left","width":50},
	        {"data": "YHZH",defaultContent:"","class":"text-left","width":50},
	        {"data": "JE",defaultContent:"","class":"text-right","width":50},
	        {"data": "SKJE",defaultContent:"","class":"text-right","width":50},
	        {"data": "SKSJ",defaultContent:"","class":"text-center"},
	        {"data": "GUID",'render':function(data, type, full, meta){
	     		   if(full.ZFZT =='1'){
// 	     			  return  '<a href="javascript:void(0);" class="btn btn-link btn_zf">支付</a>'; 
	     			  return  '<a href="javascript:void(0);" class="btn btn-link btn_zf" guid="' + data + '">反支付</a>';
	     		   }else{
		     			  return  '';

	     		   }
	     		   
	     		   
	       },orderable:false,"class":"text-center","width":50}    
    ];
	table = getDataTableByListHj("mydatatables","${ctx}/gwkcx/getPageList?dczt=${param.dczt}&pzzt=${param.pzzt}&zfzt=${param.zfzt}&pzh11=${param.pzh1}&pzh22=${pzh2}&pzrq11=${pzrq1}&pzrq22=${pzrq2}",[6,'asc ,PZRQ asc'],columns,0,1,setGroup);

});
$(function(){
	$(document).on("click",".btn_zf",function(){
		var guid = $(this).attr("guid");
		confirm("确认要反支付该条信息？",{title:"提示"},function(index){
			$.ajax({
				type:"post",
				url:"${ctx}/gwkcx/fanhuisj",
				data:"guid="+guid,
				async:false,
				success:function(val){
					table.ajax.reload();
						alert("反支付成功！");
				}
			});
		});
	});
	$("#txt_dczt").change(function(){
		 var dczt=$("[name='dczt']").val();
		 var zfzt=$("[name='zfzt']").val();
		  var pzzt=$("[name='pzzt']").val();
		
	  var pzh1=$("[name='pzh1']").val();
	  var pzh2=$("[name='pzh2']").val();
	  var pzrq1=$("[name='pzrq1']").val();
	  var pzrq2=$("[name='pzrq2']").val();
		
		location.href="${ctx}/gwkcx/dcztlist?dczt="+dczt+"&zfzt="+zfzt+"&pzzt="+pzzt+"&pzh1="+pzh1+"&pzh2="+pzh2+"&pzrq1="+pzrq1+"&pzrq2="+pzrq2;
	});
	
	//凭证状态
	$("#txt_pzzt").change(function(){
		 var dczt=$("[name='dczt']").val();
		  var zfzt=$("[name='zfzt']").val();
		  var pzzt=$("[name='pzzt']").val();
	  var pzh1=$("[name='pzh1']").val();
	  var pzh2=$("[name='pzh2']").val();
	  var pzrq1=$("[name='pzrq1']").val();
	  var pzrq2=$("[name='pzrq2']").val();
		location.href="${ctx}/gwkcx/pzztlist?dczt="+dczt+"&zfzt="+zfzt+"&pzzt="+pzzt+"&pzh1="+pzh1+"&pzh2="+pzh2+"&pzrq1="+pzrq1+"&pzrq2="+pzrq2;
	});
	//支付状态
	$("#txt_zfzt").change(function(){
		 var dczt=$("[name='dczt']").val();
		  var zfzt=$("[name='zfzt']").val();
		  var pzzt=$("[name='pzzt']").val();
		
	  var pzh1=$("[name='pzh1']").val();
	  var pzh2=$("[name='pzh2']").val();
	  var pzrq1=$("[name='pzrq1']").val();
	  var pzrq2=$("[name='pzrq2']").val();
		location.href="${ctx}/gwkcx/zfztlist?dczt="+dczt+"&zfzt="+zfzt+"&pzzt="+pzzt+"&pzh1="+pzh1+"&pzh2="+pzh2+"&pzrq1="+pzrq1+"&pzrq2="+pzrq2;
	});
	//批量反支付
	  $("#btn_plfzf").click(function(){
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
		if (checkbox.length > 0) {
			$.ajax({
				type:"post",
				url:"${ctx}/gwkcx/fanhuisj",
				data:"guid="+guid.join("','"),
				async:false,
				success:function(val){
					table.ajax.reload();
					alert("反支付成功！");
				}
			});
		} else {
			alert("请选择至少一条信息！");
		}
	});
  $("#btn_export").click(function(){
	  var dczt=$("[name='dczt']").val();
	  var zfzt=$("[name='zfzt']").val();
	  var pzzt=$("[name='pzzt']").val();
	  var pzh1=$("[name='pzh1']").val();
	  var pzh2=$("[name='pzh2']").val();
	  var pzrq1=$("[name='pzrq1']").val();
	  var pzrq2=$("[name='pzrq2']").val();
	var json = searchJson("searchBox");
	var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
	var allbox = $("#mydatatables").find("[name='guid']");
	var guid = [];
	if (checkbox.length > 0) {
		checkbox.each(function(){
			guid.push($(this).val());
		 });
    }else{
	    allbox.each(function(){
	    	guid.push($(this).val());
	     });
    }
	
		doExp1(json,"${ctx}/gwkcx/expExcel?guid="+guid.join("','")+"&dczt="+dczt+"&zfzt="+zfzt+"&pzzt="+pzzt+"&pzh1="+pzh1+"&pzh2="+pzh2+"&pzrq1="+pzrq1+"&pzrq2="+pzrq2,"零余额查询信息","${ctx}",guid.join(","));
	
	/* else {
		alert("请选择至少一条信息！");
	} */
});
  function doExp1(_data, _url, _fileName, _path, _selectedId,xx){
		var index;
		if(xx == undefined || xx == ""){
			xx = "确认导出全部信息？";
			if(_selectedId !=''&& _selectedId !=undefined){
				xx = "确认要导出选中的信息？";
			}
		}
		confirm(xx, {title:"提示"}, function(z){
// 			$("#btn_search1").click();
			$.ajax({
				type:"post",
				data:"searchJson=" + _data + "&xlsname=" + _fileName + "&id=" + _selectedId,
				url:_url,
				dataType:"json",
				success:function(val){
					close(index);
					close(z);
					FileDownload(_path + "/file/fileDownload", _fileName + ".xls", val.url);
					table.ajax.reload();
				},
				error:function(){
					close(index);
					alert(getPubErrorMsg());
				},
				beforeSend:function(){
					index = loading();
				}
			});
		}); 
	}
  $("#btn_search1").click(function(){
	  var dczt=$("[name='dczt']").val();
	  var zfzt=$("[name='zfzt']").val();
	  var pzzt=$("[name='pzzt']").val();
	  
	  var pzh1=$("[name='pzh1']").val();
	  var pzh2=$("[name='pzh2']").val();
	  var pzrq1=$("[name='pzrq1']").val();
	  var pzrq2=$("[name='pzrq2']").val();
	  
	  console.log(pzh1+"_________"+pzh2);
	  
	  location.href="${ctx}/gwkcx/checklist?dczt="+dczt+"&zfzt="+zfzt+"&pzzt="+pzzt+"&pzh1="+pzh1+"&pzh2="+pzh2+"&pzrq1="+pzrq1+"&pzrq2="+pzrq2;
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
</script>
</body>
</html>
