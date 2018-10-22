<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>设置选项</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background:white">
<form id="myform" class="form-horizontal" action="" method="post">
	<div class="box" style="margin-bottom: 0;">
		<div class="box-panel">
			<div class="container-fluid box-content">
				<c:if test="${param.cxtj=='tmdy'}">
					<div class="row">
						<div class="col-sm-12">
							<div id="d_wxts" class="alert alert-info" style="padding: 0px auto;margin:2px 4px;">
						       	<span><b>温馨提示：</b>打印多个条形码时，资产编号需要用英文逗号分隔</span>
						    </div>
						</div>
					</div>
				</c:if>
				<c:if test="${param.cxtj=='txlcx'}">
					<div class="row">
						<div class="col-sm-12">
							<div id="d_wxts" class="alert alert-info" style="padding: 0px auto;margin:2px 4px;">
						       	<span><b>温馨提示：</b>不输入查询条件，将显示所有信息</span>
						    </div>
						</div>
					</div>
				</c:if>
				<div class="row">
					<div class="col-sm-12">
						<div class="input-group">
							<span class="input-group-addon" id="name" data-tj="${param.cxtj}"
								<c:if test="${param.cxtj=='tmdy'}">title="资产编号" data-title2="打印条形码" data-path="/zcxxcx/doPrintTxm?lb=1&id="</c:if>
								<c:if test="${param.cxtj=='zccx'}">title="资产编号" data-title2="资产信息" data-path="/zjb/goLookPage?yqbh="</c:if>
								<c:if test="${param.cxtj=='ysdcx'}">title="验收单号" data-title2="验收单信息" data-path="/ysdcx/goYsdxxcxPage?tj=ysdcx&bh="</c:if>
								<c:if test="${param.cxtj=='bddcx'}">title="变动单号" data-title2="变动单信息" data-path="/zcbdcx/goZcbdckPage?tj=bddcx&bh="</c:if>
								<c:if test="${param.cxtj=='czdcx'}">title="处置单号" data-title2="处置单信息" data-path="/czbgbHz/goEditPage?operateType=CK&czbgbh="</c:if>
								<c:if test="${param.cxtj=='txlcx'}">title="人员工号或姓名" data-title2="通讯录信息" data-path="/webView/system/index/txl_list.jsp?key="</c:if>
							>
							</span>
							<input type="text" id="txt_bh" class="form-control input-radius" name="bh" value=""/>
						</div>
					</div>									
				</div>
				<div class="row">
					<div class="col-sm-12">
						 <div class="anzy" style="text-align:center">
							<button type='button' class="btn btn-default" id="btn_cx" style="background-color: #00acec;color: white;">
								查询
							</button>
						 </div>
					</div>									
				</div>													
			</div>
	       
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var name = $("#name").attr("title");
	$("#name").html(name);
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	
	$("#btn_cx").click(function(e){
		var bh = $("#txt_bh").val();
		var $name_data = $("#name").data();
		var tj = $name_data.tj;
   		var path = $name_data.path;
   		var name2 = $name_data.title2;
   		
   		if(bh == ""){
   			if("${param.cxtj}" == "txlcx"){
   				select_commonWin("${pageContext.request.contextPath}" + path,name2,"900","630","yes");
   				close(winId);
   			}
   			else{
   				alert("请先输入要查询的"+name);
   			}
   		}else{
   			$.ajax({
   				type:"post",
   				url:"${pageContext.request.contextPath}/index/check?title=" + name + "&cxtj=" + tj + "&dbh="+bh,
   				dataType:"json",
   				success:function(val){
   					close(index);
   					if(val.success){
   			   			select_commonWin("${pageContext.request.contextPath}" + path + bh,name2,"900","630","yes");
   			    	    close(winId);
   					}else{
   						alert(val.msg);
   					}
   				},
   				error:function(){
   					close(index);
   					alert(getPubErrorMsg());
   				},
   				beforeSend:function(){
   					index = loading(2);
   				}
   			});
   		}
   		return false;
	}); 
});
</script>
</body>
</html>