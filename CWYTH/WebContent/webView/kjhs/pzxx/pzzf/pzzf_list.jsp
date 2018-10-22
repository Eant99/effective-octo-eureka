<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% response.setHeader("Access-Control-Allow-Origin", "*"); %>
<%@ include file="/webView/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证支付页面</title>
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
						<label>凭证状态</label>
						<div class="form-group">
							<select style="width:120px;" id="txt_zfzt" class="form-control" table="A" name="zfzt" >
								<option value="0"<c:if test="${zfzt=='0' }">selected</c:if>>未支付</option>
								<option value="1"<c:if test="${zfzt=='1' }">selected</c:if>>已支付</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label>凭证号</label>
					    <input type="text" id="txt_xsxm" class="form-control input-radius" name="PZH"  table="A" placeholder="请输入凭证号">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查 询
					</button>
					<div class="btn-group pull-right" role="group">
               		</div>
               		<c:if test="${param.zfzt!=1}">
               		<div class="btn-group pull-right" role="group">
						<button type="button" class="btn btn-default no" id="btn_tg_sh">批量支付</button>
					</div>
					</c:if>
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
				            <th >凭证号</th>
			                <th >凭证状态</th>
			                <th >凭证类型</th>
				            <th >凭证日期</th>
				            <th >凭证摘要</th>
				            <th >结算单号</th>
                            <th >开户银行</th>
				            <th >户名</th>
				            <th >账号</th>
				            <th >金额(元)</th>
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
<script>
$(function () {
	var columns = [
        {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
          return '<input type="checkbox" name="guid"  class="keyId" xh="'+full._XH+'" PZZY="'+full.PZZY+'" KHYH="'+full.KHYH+'" BMBH="'+full.BMBH+'" KMBH="'+full.KMBH+'"  XMBH="'+full.XMBH+'"   HM="'+full.HM+'" ZH="'+full.ZH+'" JE="'+full.JE+'"  value="' + data + '" guid = "'+full.GUID+'">';
        },"width":10,'searchable': false,"class":"text-center"},
        {"data": "_XH",defaultContent:"","class":"text-center","width":20},
        {"data": "PZH",defaultContent:"","class":"text-left","width":50},
        {"data": "PZZTMC",defaultContent:"","class":"text-center","width":50},
        {"data": "PZLXMC",defaultContent:"","class":"text-left","width":50},
        {"data": "PZRQ",defaultContent:"","class":"text-center","width":50},
        {"data": "PZZY",'render':function(data, type, full, meta){
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
        },"class":"text-left","width":300},
        {"data": "JSDH",defaultContent:"","class":"text-left","width":50},
        {"data": "KHYH",defaultContent:"","class":"text-left","width":50},
        {"data": "HM",defaultContent:"","class":"text-left","width":50},
        {"data": "ZH",defaultContent:"","class":"text-left","width":50},
        {"data": "JE",defaultContent:"","class":"text-right","width":50},
        {"data": "",'render':function(data, type, full, meta){
     		   if(full.ZFZT==0){
     			  return  '<a href="javascript:void(0);" class="btn btn-link btn_zf" onclick="DoZhiFu(this)">支付</a>'; 
     		   }else{
     			  return  '<span style="color:green;">已支付</span>';
     		   }
     		   
       },orderable:false,"class":"text-center","width":50}
    ];
	table = getDataTableByListHj("mydatatables","${ctx}/pzzf/getPageList?zfzt=${param.zfzt}",[3,'asc'],columns,0,1,setGroup);

});
$(function(){
	//支付 未支付 change时间
	$("#txt_zfzt").change(function(){
		var zfzt=$(this).val();
		location.href="${ctx}/pzzf/pzzflist?zfzt="+zfzt;
	});

	//批量支付
	$("#btn_tg_sh").click(function() {
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		if (checkbox.length > 0) {
			var id = [];
			var je = [];
			var kmbh=[];
			var xmbh=[];
			var bmbh=[];
			var zh = [];//账号
			checkbox.each(function() {
				id.push($(this).val());
				je.push($(this).attr("JE"));
				kmbh.push($(this).attr("KMBH"));
				xmbh.push($(this).attr("XMBH"));
				bmbh.push($(this).attr("BMBH"));
				zh.push($(this).attr("ZH"));
			});
			
			confirm("确认要支付？","",function(){
				//支付信息txt 
   				//多个标志 逗号隔开的  ：  CCB,CCB
   				var bankCodes= bankCardAttribution(zh.join(","));
 				var len = bankCodes.length;
 				var flag = false;
 				for(var i =0;i<len;i++){
 					if(bankCodes[i]==""||bankCodes[i]==null||bankCodes[i]==undefined){
 						 alert("请检查银行卡号,支付失败!");
 						 return false;
 					}else{
 						flag = true;
 					}
 				}
				if(flag){
					$.ajax({
						url:"${pageContext.request.contextPath}/pzzf/ExpZfxxTxt",
						data: "guid="+id.join(",")+"&bankCode="+bankCodes,
						type:"post",
		   	   			async:"false",
		   	   		    dataType:"json",
		   	   			success:function(val){
			   	   			var status = val.status; //接口状态
		  	   				var restime = val.restime;   //响应时间
		  	   				var vchid = val.vchid;   //凭证编号
		  	   				if(status == "COMPLETE"){
				   	   			$.ajax({
					   				url:"${ctx}/pzzf/plzf",
					   	   			data:"JE="+je.join(",")+"&KMBH="+kmbh.join(",")+"&XMBH="+xmbh.join(",")+"&BMBH="+bmbh.join(",")+
					   	   			    "&guid="+id.join(",")+"&restime="+restime+"&vchid="+vchid,
					   	   			type:"post",
					   	   			async:"false",
					   	   			success:function(val){
					   	   				if(val==1){
						   	   			table.ajax.reload();
										alert("支付成功!");
					   	   				}
					   	   				
					   	   			}
					   		   });//支付ok
			  	   			}else{
		  	   					alert("连接银行接口出现错误，请稍后再试")
		  	   				}
		   	   			}
					});
				}//txt ok
			});//confirm
		} else {
			alert("请选择至少一条信息！");
		}
	});
	
});
function DoZhiFu(obj){
	var guid = $(obj).parents("tr").find("[name='guid']").val();
	var KHYH = $(obj).parents("tr").find("[name='guid']").attr("KHYH");//开户银行
	var HM =   $(obj).parents("tr").find("[name='guid']").attr("HM");//户名
	var ZH =   $(obj).parents("tr").find("[name='guid']").attr("ZH");//账号
	var JE =   $(obj).parents("tr").find("[name='guid']").attr("JE");//金额
	var KMBH = $(obj).parents("tr").find("[name='guid']").attr("KMBH");//科目编号
	var XMBH = $(obj).parents("tr").find("[name='guid']").attr("XMBH");//项目编号  项目编号为空 则不减  项目基本信息表
	var BMBH = $(obj).parents("tr").find("[name='guid']").attr("BMBH");//部门编号
	confirm("确认要支付？","",function(){
		//支付信息txt 
	    var bankCode = bankCardAttribution(ZH);
	    if(bankCode==""||bankCode==null||bankCode==undefined){
	        alert("请检查银行卡号,支付失败!");
	    }else{
			$.ajax({
				url:"${pageContext.request.contextPath}/pzzf/ExpZfxxTxt",
				data: "guid="+guid+"&ZH="+ZH+"&bankCode="+bankCode,
				type:"post",
				dataType:"json",
  	   			async:"false",
  	   			success:function(val){
  	   				var status = val.status; //接口状态
  	   				var restime = val.restime;   //相应时间
  	   				var vchid = val.vchid;   //凭证编号
  	   				if(status == "COMPLETE"){
	  	   			    //支付
		   	 			$.ajax({
		   	 	   				url:"${ctx}/pzzf/zfztxg",
		   	 	   	   			data:"KHYH="+KHYH+"&HM="+HM+"&ZH="+ZH+"&JE="+JE+"&guid="+guid+"&kmbh="+KMBH+"&XMBH="+XMBH+"&BMBH="+BMBH+"&restime="+restime+"&vchid="+vchid,
		   	 	   	   			type:"post",
		   	 	   	   			async:"false",
		   	 	   	   			success:function(val){
		   	 	   	   				if(val==1){
			   	 		   	   			table.ajax.reload();
			   	 						alert("支付成功!");
		   	 	   	   				}
		   	 	   	   			}
		   	 	   		});//支付
  	   				}else{
  	   					alert("连接银行接口出现错误，请稍后再试")
  	   				}
  	   			}//success
			});
	    }//else
	});//confirm
	
}	
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
