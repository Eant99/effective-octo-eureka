<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>数据恢复</title>
<%@include file="/static/include/public-list-css.inc"%>
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
			<span class="page-title text-primary"> 
				<i class='fa icon-xiangxixinxi'></i> 
				数据恢复
			</span>
		 </div>
	</div>
	<div class="box" style="margin-top: 17px;">
	 	<div class="row">
		    <div class="col-md-7">
				<div class="alert alert-danger" style="font-size:13px;" role="alert">
				<strong >注 意：</strong><br/>
				&emsp;&emsp;1、还原的过程中如果意外中断，会导致数据库处于“正在还原”状态，系统无法正常使用，请慎用！<br/>
				&emsp;&emsp;2、还原操作将使数据恢复到备份时的状态！<br/>
				&emsp;&emsp;3、选择还原的数据库文件，点击 “还原”按钮后，请耐心等待，还原成功后系统将会给予提示！
				</div>
			</div>
		</div>
		<div class="row">
		       <div class="col-md-10" style="font-weight:bold;padding:10px;"><span >上次备份数据：</span> <span id="Label3" style="color:red;">${xtb.BFMC}</span></div>
		</div>             
		 <div class="row"> 
          	    <div class="col-md-2 col-md-offset-1">               
                   <button name="ibtnBf" value="" type= "button"id="ibtnBf" class="ibtn_bf btn btn-default btn-lg btn-block">
                       <i class="fa icon-c-databackup" style="font-size: 16px;"></i>
                                                         数据恢复
                   </button>
               </div>                    
		  </div> 
		  <div class="row" style="height: 10px;"></div> 
	 </div>   
</form>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	$("#ibtnBf").click(function(){
		confirm("确认要进行数据恢复吗？",{title:"提示"},function(index){	
			var fileName = "${xtb.BFMC}";
			$.ajax({
    			type:"post",
    			url:"${ctx}/dataBack/backOracleDB?fileName="+fileName,
    			dataType:"json",
                success:function(val){	                	
    	            if(val.success == 'true') {
                      alert("数据恢复成功！");	    	            	
    	            } else{
    	            	alert("数据恢复失败！");
    	            }
    	            close(index);
                },
                error:function(){
                	alert("请稍后再试！");
                },
                beforeSend:function(){
   					index = loading(2);
   				}
    		});				
		});
	});	
});
</script>
</body>
</html>