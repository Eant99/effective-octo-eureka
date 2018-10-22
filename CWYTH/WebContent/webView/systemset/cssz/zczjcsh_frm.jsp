<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产折旧初始化</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	/*资产折旧初始化*/
	.ibtn_zjcsh
	{
		font-size: 14px;
		cursor: pointer;
	}
</style>
</head>
<body>
    <form id="myform" class="form-horizontal" action="">
    	<div class="page-header">
			<div class="pull-left">
				<span class="page-title text-primary"> 
					<i class='fa icon-xiangxixinxi'></i> 
					资产折旧初始化
				</span>
		 	</div>
	  	</div>
	  	<div class="box" style="margin-left:15px;margin-right: 15px;padding-top:6px;padding-left:8px;">
	  		<div class="row" style="padding-top: 2px;">
			    <div class="col-md-7">
					<div class="alert alert-danger" style="font-size:13px;" role="alert">
					<strong>注 意：</strong><br/>
						&emsp;&emsp;1.资产折旧初始化操作，计算机将会自动删除系统的所有折旧数据。建议您先将数据备份到一个安全的地方。<br>
						&emsp;&emsp;2.如有任何疑问，请向系统管理员咨询。<br />
					</div>
				</div>
			</div>
        	<div class="row"> 
	        	<div class="col-md-2 col-md-offset-1 " style="margin-top: 20px;">         
	                <button id="ibtn_zjcsh" type="button" style="width:173px;" class="ibtn_zjcsh btn btn-default btn-lg btn-block" >
	                    <i class="fa icon-reset2" style="font-size: 22px;"></i>资产折旧初始化
	                </button>
	            </div>                               
       		</div>
       		<div class="row" style="height: 10px;"></div>                          
        </div>
	</form>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	$("#ibtn_zjcsh").click(function(){
   		confirm("确认要进行资产折旧初始化吗？",{title:"提示"},function(index){
   			$.ajax({
				type :"post",
				url:"${pageContext.request.contextPath}/zjcsh/doDeleteAll",
				dataType:"json",
				success:function(val){					
	   					if(val.success == 'true'){
							alert(val.msg);
						}else{
							alert(val.msg);
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