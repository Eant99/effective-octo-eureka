<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>转换xml</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
</style>
</head>
<body>
<div class="fullscreen">
			<span style="white-space:pre">    </span>private static Element service,   
            service_Header,  
            <span style="white-space:pre">    </span>service_id,  
            <span style="white-space:pre">    </span>requester_id,  
      	    <span style="white-space:pre">        </span>branch_id,  
            <span style="white-space:pre">    </span>channel_id,  
            <span style="white-space:pre">    </span>version_id,  
            <span style="white-space:pre">    </span>service_time,  
            <span style="white-space:pre">    </span>service_sn,  
            service_Body,  
            <span style="white-space:pre">    </span>request,  
                  func,   
                  cust_acct_no,   
                  cust_name,   
                  flg1,   
                  cert_typ,   
                  cert_id,   
                  chk_pwd,   
                  pswd,   
                  sec_mt_con,   
                  flg3;  
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	var columns = [
        {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
          return '<input type="checkbox" name="guid"  class="keyId" KHYH="'+full.KHYH+'"  HM="'+full.HM+'" ZH="'+full.ZH+'" JE="'+full.JE+'"  value="' + data + '" guid = "'+full.GUID+'">';
        },"width":10,'searchable': false,"class":"text-center"},
        {"data": "_XH",defaultContent:"","class":"text-center","width":20},
        {"data": "PZH",defaultContent:"","class":"text-left","width":50},
        {"data": "PZRQ",defaultContent:"","class":"text-left","width":50},
        {"data": "PZZY",defaultContent:"","class":"text-left","width":300},
        {"data": "JSDH",defaultContent:"","class":"text-left","width":50},
        {"data": "KHYH",defaultContent:"","class":"text-left","width":50},
        {"data": "HM",defaultContent:"","class":"text-left","width":50},
        {"data": "ZH",defaultContent:"","class":"text-left","width":50},
        {"data": "JE",defaultContent:"","class":"text-right","width":50},
        {"data": "GUID",'render':function(data, type, full, meta){
     		   return '<a href="javascript:void(0);" class="btn btn-link btn_zf">支付</a>'; 
       },orderable:false,"class":"text-center","width":50}
    ];
	table = getDataTableByListHj("mydatatables","${ctx}/pzzf/getPageList",[3,'asc'],columns,0,1,setGroup);

});
$(function(){
	$(".btn_zf").click(function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
   		var KHYH = $(this).parents("tr").find("[name='guid']").attr("KHYH");//开户银行
		var HM =   $(this).parents("tr").find("[name='guid']").attr("HM");//户名
		var ZH =   $(this).parents("tr").find("[name='guid']").attr("ZH");//账号
		var JE =   $(this).parents("tr").find("[name='guid']").attr("JE");//金额
		confirm("确认要支付？","",function(){
			$.ajax({
	   				url:"${ctx}/pzzf/socketzf",
	   	   			data:"KHYH="+KHYH+"&HM="+HM+"&ZH="+ZH+"&JE="+JE,
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				
	   	   			}
	   		});
   		});
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
