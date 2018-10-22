<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>承包商详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	
	<input type="hidden"  id="guid" name="guid"  value="${map.guid}">
            	
        <div class="search" id="searchBox" style="padding-top: 0px;height: 40px;">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		
			<div class="search-simple"> 
			   <div class="btn-group pull-right" role="group" style="margin-top: 1%;">
	                <button type="button" class="btn btn-primary" id="btn_over">完成支付</button>
	               
				</div>
                
			</div>
		</form>
	</div>
        	
			
			
			<div class="container-fluid box-content">
			
				<div class="row">
					<div class="image" style="height: 270px;text-align: center;">
					   <img  src="${map.imagurl }" height="250px;"width="200px;">
					</div>
				</div>
				<div class="row" style="padding-left: 12%;">
				   <div class="" style="width: 40%;float: left;">
						<div class="input-group">
							<span class="input-group-addon">消费总额(元)</span>
							<input type="text" id="txt_cbsbh" name="xfze" readonly="readonly" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;text-align: right;"  value="${map.XFZE}"/>
						</div>
                    </div>
                     <div class="" style="width: 40%;float: left;margin-left: 5%;">
						<div class="input-group">
							<span class="input-group-addon">支出金额(元)</span>
							<input type="text" id="txt_cbsbh" name="zfje" readonly="readonly" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;text-align: right;"  value="${map.ZFJE}"/>
						</div>
                    </div>
				</div>
				
				</div>
		</form>
			

<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var tag = false;
var operate = "${operateType}";
//var operate_src = ADDRESS+"/jcsz/jsxxs/insert";
$(function(){
$("#btn_over").click(function(){
	var guid = $("[name='guid']").val();
	doSave("","myform","${ctx}/wxzf/doUpdateZfzt?guid="+guid,function(val){
		if(val.success == "true"){
			tag = true;
			var winId = getTopFrame().layer.getFrameIndex(window.name);
			getIframWindow("${param.pname}").table.ajax.reload();
	    	close(winId);
//             parent.location.href = "${ctx}/wxzf/goPageList";
		}
	});
	});   
	
});





	
</script>
</body>
</html>