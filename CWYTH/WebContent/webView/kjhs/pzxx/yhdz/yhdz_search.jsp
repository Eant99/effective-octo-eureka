<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<div class="box" style="30px";>
		<div class="box-panel">		
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>查询条件
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">	
				   	   		   
		   <div class="box2-content">
			<div class="container-fluid box-content">
				<input type="hidden" name="start" value="start" />
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>支付时间</span>
							<input type="text" name="zfsj" class="form-control input-radius" value=""/>
						</div>
					</div>				
				</div>
                <div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>金额</span>
							<input type="text" name="je" class="form-control input-radius" value=""/> 
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>客户序列号</span>
						    <input type="text" name="khxlh" class="form-control input-radius" value=""/>
						</div>
					</div>
				</div>
					
				<input type="hidden" name="end" value="end" />
			</div>
			
			<div class="container-fluid box-content">
				<div class="row">
					<div class="pull-center" style="text-align:center;">
	    				<button type="button" class="btn btn-default" id="btn_save" style="background:#00acec;color: white;">确定</button>
	    				<button type="reset" class="btn btn-default" id="btn_reset" style="background:#00acec;color: white;">取消</button>
	 				</div>
				</div>
		   </div>
		   </div>
		   
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//取消按钮
	$("#btn_reset").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
			close(winId);
	});
    //确定
	$("#btn_save").click(function(){
		
		var zfsj = $(".box2-content").find("[name='zfsj']").val();
		var je = $(".box2-content").find("[name='je']").val();
		var khxlh = $(".box2-content").find("[name='khxlh']").val();
         console.log(zfsj+"%%"+je+"*"+khxlh);
		var json = $("#myform").serializeObject("start","end");			
  		var jsonStr = JSON.stringify(json);  //json字符串  

  		$.ajax({
  			url:"${ctx}/yhdz/paramSession",
  			data:"yhdzJson="+jsonStr,
  			dataType:"json",
  			type:"post",
  			complete:function(){
  				getIframWindow("${param.pname}").toUrl(zfsj,je,khxlh);
  				var winId = getTopFrame().layer.getFrameIndex(window.name);
  				close(winId);
  			}
  		});
	});
	
	
});
</script>

</html>