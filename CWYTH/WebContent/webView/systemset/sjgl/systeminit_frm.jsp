<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>系统初始化</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.ibtn_xtcsh{
		font-size: 14px;
		cursor: pointer;
		padding:10px;
		margin:10px;
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
				系统初始化
			</span>
		 </div>
	</div>
	<div class="box" style="margin-top: 17px;">
		<div class="row">
		    <div class="col-md-7">
				<div class="alert alert-danger" style="font-size:13px;" role="alert">
				<strong >注 意：</strong><br/>
				&emsp;&emsp;1、系统初始化操作，计算机将会自动删除系统的所有数据。建议您先将数据备份到一个安全的地方。<br />
				&emsp;&emsp;2、如有任何疑问，请向系统管理员咨询。
				</div>
			</div>
		</div>
          <div class="row"  > 
         	    <div class="col-md-2 col-md-offset-1">               
                  <button name="ibtnBf" type= "button"id="ibtn_xtcsh" class="ibtn_xtcsh btn btn-default btn-lg">
                      <i class="fa icon-reset2" style="font-size: 16px;"></i>
                                                        系统初始化
                  </button>
              </div>                    
         </div>  
		</div>
</form>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	$("#ibtn_xtcsh").click(function(){
   		confirm("确认要进行系统初始化吗？",{title:"提示"},function(index){
   			$.ajax({
				type :"post",
				url:"${ctx}/xtcsh/doDeleteAll",
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