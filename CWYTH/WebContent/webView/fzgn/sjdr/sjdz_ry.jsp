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
    min-height: 25px;
}
#Table4 tbody tr td select{
	width: 120px;
}
#Table4 tbody tr.zm_content td{
	text-align: center;
}
#myDataGrid tbody tr{
	height: 25px;
}	
</style>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" style="overflow:auto;height:580px;">
	<div id="centent_body" class="div_detailpage"> 
		<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;table-layout: fixed;">
            <tr>
               <td colspan="2" style="height: 34px; text-align:center;font-weight:bolder;font-size: 14pt;">导 入 数 据 对 照</td>
            </tr>
            <tr>
                <td colspan="2" style="height: 200px; vertical-align:text-top">
                    <table id="Table4" border="1"  class="form-detail-table" cellpadding="0" cellspacing="0" style="width: 99%;margin-left: 5px;">
                        <tr class="zm_content" style=" font-weight:bold;" >
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
                            <td class="lable" style="color: red;"><span>人员编号：</span></td>
                            <td>
                                <select name="drp_rybh">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'人员编号'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center">
                                <input id="ck_rybh" type="checkbox" name="ck_rybh" value="ck_rybh"/>
                            </td>
                            <td class="lable" style="color: red;"><span>人员姓名：</span></td>
                            <td>
                                <select name="drp_xm">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'人员姓名'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_xm" type="checkbox" name="ck_xm" value="ck_xm"/>
                            </td>
                            <td class="lable" style="color: red;">所在单位：</td>
                            <td>
                                <select name="drp_dwbh" id="drp_dwbh" >
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'所在单位'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center;">
                                <input id="ck_dwbh" type="checkbox" name="ck_dwbh" value="ck_dwbh"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="lable"><span>性别：</span></td>
                            <td>
                                <select name="drp_xb">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'性别'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_xb" type="checkbox" name="ck_xb" value="ck_xb"/>
                            </td>
                            <td class="lable">出生日期：</td>
                            <td>
                                <select name="drp_csrq">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'出生日期'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center">
                                <input id="ck_csrq" type="checkbox" name="ck_csrq" value="ck_csrq"/>
                            </td>
                            <td class="lable">毕业日期：</td>
                            <td>
                                <select name="drp_byrq">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'毕业日期'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_byrq" type="checkbox" name="ck_byrq" value="ck_byrq"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="lable">文化程度：</td>
                            <td  >
                                <select name="drp_whcd">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'文化程度'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_whcd" type="checkbox" name="ck_whcd" value="ck_whcd"/>
                            </td>
                            <td class="lable">工作时间：</td>
                            <td >
                                <select name="drp_gzrq">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'工作时间'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_gzrq" type="checkbox" name="ck_gzrq" value="ck_gzrq"/>
                            </td>
                            <td class="lable">所学专业：</td>
                            <td>
                                <select name="drp_sxzy" id="drp_sxzy" >
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'所学专业'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center">
                                <input id="ck_sxzy" type="checkbox" name="ck_sxzy" value="ck_sxzy"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="lable">专业职称：</td>
                            <td>
                                <select name="drp_zyzc">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'专业职称'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_zyzc" type="checkbox" name="ck_zyzc" value="ck_zyzc"/>
                            </td>
                            <td class="lable">人员类型：</td>
                            <td>
                                <select name="drp_zzbz">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'人员类型'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_zzbz" type="checkbox" name="ck_zzbz" value="ck_zzbz"/>
                            </td>
                            <td class="lable">调入日期：</td>
                            <td>
                                <select name="drp_drrq">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'调入日期'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
							<td style="text-align: center" >
                                <input id="ck_drrq" type="checkbox" name="ck_drrq" value="ck_drrq"/>
                            </td>
						</tr>
						<tr>
                            <td class="lable">实验室工龄：</td>
                            <td>
                                <select name="drp_sysgl">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'实验室工龄'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center">
                                <input id="ck_sysgl" type="checkbox" name="ck_sysgl" value="ck_sysgl"/>
                            </td>
                            <td class="lable">主要工作：</td>
                            <td>
                                <select name="drp_zygz" id="drp_zygz" >
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'主要工作'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center">
                                <input id="ck_zygz" type="checkbox" name="ck_zygz" value="ck_zygz"/>
                            </td>
                            <td class="lable">人员状态：</td>
                            <td  >
                                <select name="drp_ryzt">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'人员状态'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_ryzt" type="checkbox" name="ck_ryzt" value="ck_ryzt"/>
                             </td>
                        </tr>
                        <tr>
                            <td class="lable">联系电话：</td>
                            <td>
                                <select name="drp_lxdh">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'联系电话'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center">
                                <input id="ck_lxdh" type="checkbox" name="ck_lxdh" value="ck_lxdh"/>
                            </td>
                            <td class="lable">QQ号码：</td>
                            <td>
                                <select name="drp_qq">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'QQ号码'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
                                <input id="ck_qq" type="checkbox" name="ck_qq" value="ck_qq"/>
                            </td>
                            <td class="lable">E-mail地址：</td>
                            <td>
                                <select name="drp_mail" id="drp_mail" >
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'E-mail地址'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center;">
                                <input id="ck_mail" type="checkbox" name="ck_mail" value="ck_mail"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="lable">在职状态：</td>
                            <td>
                                <select name="drp_zzzt">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'在职状态'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td  style="text-align: center">
                                <input id="ck_zzzt" type="checkbox" name="ck_zzzt" value="ck_zzzt"/>
                            </td>
                            <td class="lable">排序序号：</td>
                            <td>
                                <select name="drp_pxxh">
									<option value="null">无</option>
									<c:forEach var="listbt" items="${listbt}"> 
                            			<option value="${listbt.mc}"<c:if test="${'排序序号'==listbt.mc}">selected</c:if>>${listbt.mc}</option>
									</c:forEach>
								</select>
							</td>
                            <td style="text-align: center" >
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
					<div style="width: 99%;height:190px;overflow: auto;">
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
	</div>
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
			data:$('#myform').serialize()+"&file="+file+"&value="+value+"&flag=ry",
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