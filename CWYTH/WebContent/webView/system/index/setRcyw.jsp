<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设置日常业务</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.box{
		padding:0px;
		margin-bottom:0px;
	}
	.row{
		margin:0px;
	}
</style>
</head>
<body style="background-color: white;">
 	<div class='row' id='content-wrapper'>
        <div class='col-md-12'>
             <div class="box" >
                 <div class='box-content' style="padding-bottom: 0; overflow:visible;">
                 	<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong>最多可选择展示<strong>12</strong>条业务种类
			        </div>
				</div>
			</div>
		</div>
	</div>
<form id="myform" class="form-horizontal" action="" method="post" >
		<div class="container-fluid dialog-body">
			<div class="row">
				<div class="col-md-12">
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th>主菜单</th>
             					<th>二级菜单</th>
             					<th>菜单名称</th>
								<th>是否展示</th>
								<th>自定义业务名称</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="menu_yj" items="${menu_list}" varStatus="i"> 
							<tr >
								<td rowSpan="${menu_yj.size}">
	                           		<c:out value="${menu_yj.MKMC}"/>
	                          	</td>
	                          	<c:forEach var="menu_ej" items="${menu_yj.childList}" varStatus="j">
		                           	<c:if test="${index!=0}"><%="<tr>"%></c:if>
										<td rowSpan="${menu_ej.size}" class="col-md-2">
		                           	 		<c:out value="${menu_ej.MKMC}"/>
		                           		</td>
		                           		<c:forEach var="menu_sj" items="${menu_ej.childList}" varStatus="k">
		                           			<c:if test="${index!=0}"><%="<tr>"%></c:if>
											<td  class="rcyw" data-mkbh="${menu_sj.MKBH}">
		                              			<c:out value="${menu_sj.MKMC}"/>
		                            		</td>
											<td>
												<div class="switch">
									               	<div class="onoffswitch">
														<input type="hidden" class="sfzs" value="${menu_sj.SFZS}">
									                   	<input type="checkbox" <c:if test="${menu_sj.SFZS == '1'}">checked</c:if> id="btn_onoff${i.index}-${j.index}-${k.index}" class="onoffswitch-checkbox check_sfzs">
									                   	<label class="onoffswitch-label" for="btn_onoff${i.index}-${j.index}-${k.index}">
									                       <span class="onoffswitch-inner"></span>
									                       <span class="onoffswitch-switch"></span>
									                   	</label>
									               	</div>
									        	</div>
											</td>
											<td><input type="text" value="${menu_sj.MKMCNEW}" class="mkmc" placeholder="请输入自定义业务名称" data-mkmc="${menu_sj.MKBH}"></td>
									     </c:forEach>
								     </c:forEach>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>    
    		</div>
		</div>
		<div class='page-bottom clearfix'>
			        <div class="pull-right">
						<button class="btn btn-default" type="button" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
							<i class="fa icon-save"></i>
							保存
						</button>
						<button type='button' class="btn btn-default " id="btn_back">
							关闭
						</button>
			        </div>
			    </div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//关闭按钮
	$("#btn_back").click(function(){
		getTopFrame().closeAll();
	});
	$("[id^=btn_onoff]").on("click",function(){
		var sfzs = $(this).prev().val()=="1"?"0":"1";
		$(this).prev().val(sfzs);
	});
	//保存按钮
	$("#btn_save").click(function(){
		var data = [];
		var flag = true;
		if($(".sfzs[value=1]").length>12){
			alert("我的日常业务最多可显示12条，目前您已选择" + $(".sfzs[value=1]").length + "条，请注意修正！");
			flag = false;
		}
		if(flag){
			$(".rcyw").each(function(){
				if($(this).parent().find(".sfzs").val()=='1'){
					var mkbh = $(this).attr("data-mkbh");
					var sfzs = $(this).parent().find(".sfzs").val();
					var mkmcnew = $(this).parent().find(".mkmc").val();
					data.push(mkbh+"##"+mkmcnew+"##"+sfzs);
				}
			});
			var index;
			var pname = "${param.pname}";
			$.ajax({
				type:"post",
				data:"data="+data.join(","),
				url:"${pageContext.request.contextPath}/desk/doSaveRcyw",
				dataType:"json",
				success:function(val){
					if(val.success=='true'){
					close(index);
					alert(val.msg);
					var win = getIframWindow(pname);
					win.location.href = win.location.href; 
					var winId = getTopFrame().layer.getFrameIndex(window.name);
		 	    	close(winId);
					}else{
						alert(val.msg);
					}
				},
				error:function(){
					alert(getPubErrorMsg());
				},
				beforeSend:function(){
					index = loading(2);
				}
			});
		}
	});
});
</script>
</html>