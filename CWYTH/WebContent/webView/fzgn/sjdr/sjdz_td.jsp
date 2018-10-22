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
td {
	white-space:nowrap;
}
#center{
	text-align: center;
	margin-top: 0px;
}
.form-horizontal .checkbox, .form-horizontal .radio {
    min-height: 25px;
}	
</style>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post">
	 <div id="centent_body"  class="div_detailpage" >
         <div style="height: 25px;margin-left: 18px;">
                <table border="0" cellpadding="0" cellspacing="0" style="width: 100%">
                <tr>
                    <td style=" height: 25px; text-align: left">
                        <button type="button" id="btn_dr">导入</button>
                    </td>
                    <td style=" height: 25px; text-align: left">
                        <table id="rdo_DealMethod" border="0">
							<tr>
								<td>
									<input id="rdo_DealMethod_0" type="radio" name="rdo_DealMethod" value="0" checked="checked" />
									<label for="rdo_DealMethod_0">向目标数据库表中追加行<font color="red">(如重复导入数据将导致无法正常导入)</font></label>
								</td>
							</tr>
						</table>
                    </td>
                </tr>
               </table>
               </div>
               <div style="height:5px;"></div>
            <div class="div_table" >
                <table border="1" cellpadding="0" cellspacing="0" style="width: 96%; margin:0 auto;" class="form-detail-table">
                    <tr>
                        <td style="text-align:center">系统数据库</td>
                        <td style="text-align:center">Excel数据</td>
                        <td style="text-align:center">系统数据库</td>
                        <td style="text-align:center">Excel数据</td>
                        <td style="text-align:center">系统数据库</td>
                        <td style="text-align:center">Excel数据</td>
                    </tr>
                    <tr>
                        <td class="lable" style=" color:Red">资产编号</td>
                        <td align="left">
                            <select name="yqbh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'资产编号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable"  style=" color:Red">分类代码</td>
                        <td align="left">
                            <select name="flh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'分类代码' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">分类名称</td>
                        <td align="left">
                            <select name="flmc">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'分类名称' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable"  style=" color:Red">资产名称</td>
                        <td align="left">
                            <select name="yqmc">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'资产名称' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">价值类型</td>
                        <td align="left">
                            <select name="jzxs">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'价值类型' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
						<td class="lable"  style=" color:Red">总&emsp;&emsp;价</td>
                        <td align="left">
                            <select name="zzj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'总价' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">计量单位</td>
                        <td align="left">
                            <select name="jldw">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'计量单位' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">经费来源</td>
                        <td align="left">
                            <select name="jfkm">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'经费来源' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">资产来源</td>
                        <td align="left">
                            <select name="zcly">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'资产来源' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">使用方向</td>
                        <td align="left">
                            <select name="syfx">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'使用方向' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable" style=" color:Red;">使用单位</td>
                        <td align="left">
                            <select name="sydw">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'使用单位' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">使&ensp;用&ensp;人</td>
                        <td align="left">
                            <select name="syr">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'使用人' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">坐落位置</td>
                        <td align="left">
                            <select name="sccj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'坐落位置' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">入账形式</td>
                        <td align="left">
                            <select name="jzlx">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'入账形式' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable"  style=" color:Red">入账日期 </td>
                        <td align="left">
                            <select name="rzrq">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'入账日期' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">预算项目编号</td>
                        <td align="left">
                            <select name="xmh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'预算项目编号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">会计凭证号</td>
                        <td align="left">
                            <select name="pzh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'会计凭证号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable" style=" color:Red">现&emsp;&emsp;状</td>
                        <td align="left">
                            <select name="xz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'现状' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
                    <tr>
                        <td class="lable">调转入日期</td>
                        <td align="left">
                            <select name="dzrrq">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'调转入日期' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">采购组织形式</td>
                        <td align="left">
                            <select name="cgzzxs">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'采购组织形式' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">入账科目</td>
                        <td align="left">
                            <select name="td_rzkm">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'入账科目' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">产权形式</td>
                        <td align="left">
                            <select name="cqxs">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'产权形式' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
						<td class="lable">权属性质</td>
                        <td align="left">
                            <select name="qsxz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'权属性质' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable">权属证明</td>
                        <td align="left">
                            <select name="td_qszm">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'权属证明' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">使用权面积</td>
                        <td align="left">
                            <select name="zmmj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'使用权面积' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable">土地使用权人</td>
                        <td align="left">
                            <select name="djh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'土地使用权人' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">使用权类型</td>
                        <td align="left">
                            <select name="fgxs">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'使用权类型' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">购置日期</td>
                        <td align="left">
                            <select name="gzrq">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'购置日期' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">发证日期</td>
                        <td align="left">
                            <select name="td_fzrq">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'发证日期' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">投入使用日期</td>
                        <td align="left">
                            <select name="qsrq">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'投入使用日期' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">独用面积</td>
                        <td align="left">
                            <select name="td_dymj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'独用面积' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable">分摊面积</td>
                        <td align="left">
                            <select name="td_ftmj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'分摊面积' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable">实际面积</td>
                        <td align="left">
                            <select name="fjzj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'实际面积' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">用&emsp;&emsp;途</td>
                        <td align="left">
                            <select name="td_yt">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'用途' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">自用面积</td>
                        <td align="left">
                            <select name="td_zymj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'自用面积' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">自用价值</td>
                        <td align="left">
                            <select name="zyjz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'自用价值' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">校区编号</td>
                        <td align="left">
                            <select name="htbh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'校区编号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">出租面积</td>
                        <td align="left">
                            <select name="td_czmj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'出租面积' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable">出租价值</td>
                        <td align="left">
                            <select name="czjz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'出租价值' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">宗地编号</td>
                        <td align="left">
                            <select name="jsh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'宗地编号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">出借面积</td>
                        <td align="left">
                            <select name="td_cjmj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'出借面积' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">出借价值</td>
                        <td align="left">
                            <select name="cjjz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'出借价值' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
                    <tr>
                        <td class="lable">土地等级</td>
                        <td align="left">
                            <select name="tddj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'土地等级' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">对外投资面积</td>
                        <td align="left">
                            <select name="td_jymj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'对外投资面积' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">对外投资价值</td>
                        <td align="left">
                            <select name="jyjz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'对外投资价值' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
                    <tr>
                        <td class="lable">四角坐标</td>
                        <td align="left">
                            <select name="xss">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'四角坐标' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">其他面积</td>
                        <td align="left">
                            <select name="td_qtmj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'其他面积' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">其他价值</td>
                        <td align="left">
                            <select name="qtjz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'其他价值' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
                    <tr>
                    	<td class="lable">地下设施</td>
                        <td align="left">
                            <select name="gzyq">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'地下设施' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable">权属年限</td>
                        <td align="left">
                            <select name="td_qsnx">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'权属年限' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    	<td class="lable">均&emsp;&emsp;价</td>
                        <td align="left">
                            <select name="jj" id="drp_jj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'均价' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
                    <tr>
                    	<td class="lable">备&emsp;&emsp;注</td>
                        <td align="left">
                            <select name="bz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'备注' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
                </table>
            </div>
       	</div>
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//导入按钮
	$("#btn_dr").click(function(){
		var index;
		var file = "${file}";
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/yssjdr/insertYssjDr",
			data:$('#myform').serialize()+"&file="+file,
			success:function(val){
				close(index);
				var data = $.trim(val);
				if(data==''){
					alert("导入失败！");
				}else{
					alert(data);
				}
			}
		});
	});
});
</script>
</body>
</html>