<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>数据备份</title>
<%@include file="/static/include/public-list-css.inc"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/ext/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/ext/button_icon.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-lang-zh_CN.js"></script>
<style type="text/css">
	.ibtn_bf{
		font-size: 14px;
		cursor: pointer;
		padding:10px;
	}
	.row{
    	margin-right:0
    }
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="">
	<div class="page-header">
		<div class="pull-left">
			<span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>&emsp;数据备份</span>
		 </div>
	</div>
	<div class="box" style="margin-top: 17px;">
	    <div class="row">
		    <div class="col-md-7">
				<div class="alert alert-danger" style="font-size:13px;" role="alert">
				<strong >注 意：</strong><br/>
				&emsp;&emsp;1、数据库备份需要在应用程序服务器上创建存放数据库的文件夹，默认文件夹名为 DataBak。如果当应用程序服务器上不存在这样的一个文件夹，将自动创建！<br/>
				&emsp;&emsp;2、点击备份按钮之后，数据库进行备份，消耗时间较长（大约需要3分钟的时间），请耐心等待，备份成功之后系统将会给予提示！<br/>
				&emsp;&emsp;3、当前系统操作类型为：${xtlx},数据库备份路径为${databasedz}
				</div>
			</div>
		</div>
		<div class="row">
        	<div class="col-md-6" style="font-weight:bold;padding:10px;"><span>上次备份时间：</span> <span id="Label3" style="color:red;"><fmt:formatDate value="${xtb.BFRQ}" pattern="yyyy-MM-dd HH:mm"/></span></div>
        </div>   
		<div class="row"> 
			<div class="col-md-2 col-md-offset-1">               
				<button name="ibtnBf" value="" type= "button"id="ibtnBf" class="ibtn_bf btn btn-default btn-lg btn-block"><i class="fa icon-c-databackup" style="font-size: 16px;"></i>数据备份</button>
			</div>                    
    	</div>
    	<div class="row" style="height: 10px;"></div> 
	</div>
</form>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	$("#ibtnBf").click(function(){
		 Ext.Msg.confirm("提示","确认要进行数据备份吗？",function(btn){
			 if(btn == "yes"){
				$.ajax({
	    			type:"post",
	    			url:"${ctx}/dataBack/backupOracleDB",
	    			dataType:"json",
	                success:function(val){	                	
	    	            if(val.success == 'true') {
	    	            	Ext.Msg.alert("提示","备份成功！");
	    	            } else{
	    	            	Ext.Msg.alert("提示","备份失败！");
	    	            }
	                },
	                error:function(){
	                	alert("请稍后再试！");
	                },
	                beforeSend:function(){
	                	i = Ext.MessageBox.show({msg: '正在备份数据,请稍等...',
                            progressText: '正在备份数据...',
                            width: 300,
                            wait: true,
                            waitConfig: { interval: 200 }});
	   				}
	    		});	
		 }
		});
	});	
});
function getZxbf(){
	$.ajax({
		type:"post",
		url:"${ctx}/dataBack/getZxbf",
		success:function(val){
			val = $.trim(val);
			close(index);
		},
	});
};
</script>
</body>
</html>