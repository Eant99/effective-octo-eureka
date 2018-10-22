<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>数据对照</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<style type="text/css">
#center{
	text-align: center;
	margin-top: 0px;
}
.form-horizontal .checkbox, .form-horizontal .radio {
    min-height: 25px !important;
}
#Table4 tbody tr td select{
	width: 120px !important;
}
#Table4 tbody tr.zm_content td{
	text-align: center !important;
}
#myDataGrid tbody tr{
	height: 25px !important;
}	
</style>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" style="overflow:auto;height:580px;">
		<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;table-layout: fixed;">
            <tr>
               <td colspan="2" style="height: 34px; text-align:center;font-weight:bolder;font-size: 14pt;">导 入 数 据 对 照</td>
            </tr>
            <tr>
                <td colspan="2" style="height: 80px; vertical-align:text-top;">
                    <table id="Table4" border="1" class="form-detail-table" cellpadding="0" cellspacing="0" style="width: 99%;margin-left: 5px;">
                        <tr class="zm_content" style="font-weight:bold" >
                            <td>系统数据库</td>
                            <td>Excel数据</td>
                            <td>参照</td>
                            <td>系统数据库</td>
                            <td>Excel数据</td>
                            <td>参照</td>
                            <td>系统数据库</td>
                            <td>Excel数据</td>
                            <td>参照</td>
                        </tr>
                        <tr>
                            <td class="lable" style="color: red;"><span>地点号：</span></td>
                            <td>
                                <select name="drp_ddh">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'地点号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center">
                                <input id="ck_ddh" type="checkbox" name="ck_ddh" value="ck_ddh"/>
                            </td>
                            <td class="lable" colspan="1" rowspan="1" style="color: red;"><span>地点名称：</span></td>
                            <td style="text-align: left;" >
                                <select name="drp_mc">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'地点名称' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_mc" type="checkbox" name="ck_mc" value="ck_mc"/>
                            </td>
                            <td class="lable"><span>所在单位：</span></td>
                            <td>
                                <select name="drp_dwbh">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'所在单位' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_dwbh" type="checkbox" name="ck_dwbh" value="ck_dwbh"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="lable" style="color: red;">上级地点：</td>
                            <td >
                                <select name="drp_sjdd">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'上级地点' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td  style="text-align: center;">
                                <input id="ck_sjdd" type="checkbox" name="ck_sjdd" value="ck_sjdd"/>
                            </td>
                            <td class="lable">地点状态：</td>
                            <td>
                                <select name="drp_ddzt">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'地点状态' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center">
                                <input id="ck_ddzt" type="checkbox" name="ck_ddzt" value="ck_ddzt"/>
                            </td>
                            <td class="lable">排序序号：</td>
                            <td>
                                <select name="drp_pxxh">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'排序序号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center">
                                <input id="ck_pxxh" type="checkbox" name="ck_pxxh" value="ck_pxxh"/>
                            </td>
                        </tr>
                    </table>
                    </td>
	            </tr>
	            <tr>
		            <td colspan="2" style="height: 35px;text-align:center; font-weight:bold">
		                <span style="font-size: 14pt;">Excel 数 据 源</span>
		                <button class="btn btn-default pull-right" type="button" id="btn_dr" style="margin-right: 10px;padding-bottom: 1px;"><i class="faw fa-cloud-upload" style="color: #03A9F4;font-size: 22px;"></i>导入</button>
		            </td>
	            </tr>
	            <tr>
					<td colspan="2" style="height: 35px; text-align:center">
					<div style="width: 99%;height:320px;overflow: auto;">
	            	<table cellspacing="0" rules="all" border="1" id="myDataGrid" style="border-collapse:collapse;width: 100%;margin-left: 5px;">
						<tr>
							<td>序号</td>
							<c:forEach var="listbt" items="${listbt}"> 
			                	<td>${listbt.mc}</td>
							</c:forEach>
						</tr>
						<c:set var="i" value="0"/>
						 <c:forEach var="list" items="${list}"> 
						 	<c:set var="i" value="${i+1}"/>
							<tr>
								<td>${i}</td>
								<c:forEach var="listbt" items="${listbt}"> 
								<c:set var="MC" value="${listbt.mc}"/>
	                				<td>${list[MC]}</td>
								</c:forEach>
							</tr>
						</c:forEach> 
					</table></div>
				</td>
	         </tr>
	         <tr>
	         	<td colspan="2" style="color: red;text-align: center;">
        			<c:forEach var="error" items="${error}">
        				${error}
        			</c:forEach>
        		</td>
	         </tr>
       	 </table>
       	 <div style="height:100px;overflow: auto;margin-left: 5px;margin-right: 5px;margin-top: 2px;" id="yzxx">&ensp;暂无校验信息！</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//取消按钮
	$("#btn_back").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
	//导入按钮
	$("#btn_dr").click(function(){
		var index;
		var file = "${file}";
		var checkbox = $("input[type='checkbox']").filter(":checked");
		var value = [];
		if(checkbox.length){
			checkbox.each(function(){
				value.push($(this).val());
   			});
		}
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/jcsjdr/insertJcsj",
			data:$('#myform').serialize()+"&file="+file+"&value="+value+"&flag=dd",
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				if(data.success){
					alert(data.msg);
				}else{
					alert("导入失败！");
				}
				$("div[id=yzxx]").empty();
				$("div[id=yzxx]").append(data.msg);
			}
		});
	});
});
</script>
</body>
</html>