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
                            <select name="yqbh" id="drp_yqbh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'资产编号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable"  style=" color:Red">分类代码</td>
                        <td align="left">
                            <select name="flh" id="drp_flh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'分类代码' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">分类名称</td>
                        <td align="left">
                            <select name="flmc" id="drp_flmc">
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
                            <select name="yqmc" id="drp_yqmc">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'资产名称' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">价值类型</td>
                        <td align="left">
                            <select name="jzxs" id="drp_jzxs">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'价值类型' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable"  style=" color:Red">现&emsp;&emsp;状</td>
                        <td align="left">
                            <select name="xz" id="drp_xz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'现状' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable"  style=" color:Red">单&emsp;&emsp;价</td>
                        <td align="left">
                            <select name="dj" id="drp_dj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'单价' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">折旧状态</td>
                        <td align="left">
                            <select name="zjjt" id="drp_zjjt">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'折旧状态' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">累计折旧</td>
                        <td align="left">
                            <select name="ljzj" id="drp_ljzj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'累计折旧' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable"> 计量单位</td>
                        <td align="left">
                            <select name="jldw" id="drp_jldw">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'计量单位' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">经费来源</td>
                        <td align="left">
                            <select name="jfkm" id="drp_jfkm">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'经费来源' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">资产来源</td>
                        <td align="left">
                            <select name="zcly" id="drp_zcly">
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
                            <select name="syfx" id="drp_syfx">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'使用方向' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable" style=" color:Red;">使用单位</td>
                        <td align="left">
                            <select name="sydw" id="drp_sydw">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'使用单位' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">使&ensp;用&ensp;人</td>
                        <td align="left">
                            <select name="syr" id="drp_syr">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'使用人' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">存放地点</td>
                        <td align="left">
                            <select name="bzxx" id="drp_cgzzxs">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'存放地点' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">入账形式</td>
                        <td align="left">
                            <select name="jzlx" id="drp_jzlx">
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
                            <select name="xmh" id="drp_xmh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'预算项目编号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">会计凭证号</td>
                        <td align="left">
                            <select name="pzh" id="drp_pzh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'会计凭证号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
						<td class="lable"  style=" color:Red">调转入日期</td>
                        <td align="left">
                            <select name="dzrrq" id="drp_dzrrq">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'调转入日期' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable"  style=" color:Red">学&emsp;&emsp;科</td>
                        <td align="left">
                            <select name="xk" id="drp_synx">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'学科' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">学科类别</td>
                        <td align="left">
                            <select name="xklb" id="drp_cb">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'学科类别' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">采购组织形式</td>
                        <td align="left">
                            <select name="cgzzxs" id="drp_fw_qsrq">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'采购组织形式' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
                    <tr>
                        <td class="lable" style=" color:Red">采&ensp;购&ensp;人</td>
                        <td align="left">
                            <select name="cgr" id="drp_cj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'采购人' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
						<td class="lable">出厂编号</td>
                        <td align="left">
                            <select name="cch" id="drp_fjs">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'出厂编号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable">销&ensp;售&ensp;商</td>
                        <td align="left">
                            <select name="xss" id="drp_cgr">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'销售商' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable" style=" color:Red">生产厂家</td>
                        <td align="left">
                            <select name="sccj" id="drp_htbh">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'生产厂家' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable" style=" color:Red">出厂日期</td>
                        <td align="left">
                            <select name="ccrq" id="drp_xss">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'出厂日期' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">购置日期</td>
                        <td align="left">
                            <select name="gzrq" id="drp_gzyq">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'购置日期' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">投入使用日期</td>
                        <td align="left">
                            <select name="qsrq" id="drp_fgxs">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'投入使用日期' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">保修截止日期</td>
                        <td align="left">
                            <select name="bxjzrq" id="drp_fw_cqxs">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'保修截止日期' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">账面净值</td>
                        <td align="left">
                            <select name="zmjz" id="drp_gbm">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'账面净值' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">型&emsp;&emsp;号</td>
                        <td align="left">
                            <select name="xh" id="drp_fjzj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'型号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable">规&emsp;&emsp;格</td>
                        <td align="left">
                            <select name="gg" id="drp_zj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'规格' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable">机&ensp;身&ensp;号</td>
                        <td align="left">
                            <select name="jsh" id="drp_jcnf">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'机身号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">预计使用年限</td>
                        <td align="left">
                            <select name="synx" id="drp_gnmj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'预计使用年限' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">品&emsp;&emsp;牌</td>
                        <td align="left">
                            <select name="pp" id="drp_fw_dsmj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'品牌' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">功&emsp;&emsp;率</td>
                        <td align="left">
                            <select name="sb_gl" id="drp_fw_dxmj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'功率' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">合同编号</td>
                        <td align="left">
                            <select name="htbh" id="drp_symj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'合同编号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">单&ensp;据&ensp;号</td>
                        <td align="left">
                            <select name="djh" id="drp_blbz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'单据号' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                        <td class="lable">用&emsp;&emsp;途</td>
                        <td align="left">
                            <select name="td_yt">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'用途' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td class="lable">科研项目</td>
                        <td align="left">
                            <select name="kyxm">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'科研项目' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">重点实验室</td>
                        <td align="left">
                            <select name="zdsys">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'重点实验室' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">项目实验室负责人</td>
                        <td align="left">
                            <select name="fzr">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'项目实验室负责人' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
                    <tr>
                        <td class="lable">进口总价</td>
                        <td align="left">
                            <select name="jkdj" id="drp_zyyt">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'进口总价' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">外币种类</td>
                        <td align="left">
                            <select name="wbzl" id="drp_dj">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'外币种类' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
						</td>
                        <td class="lable">国&emsp;&emsp;别</td>
                        <td align="left">
                            <select name="gbm" id="drp_bz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'国别' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
                    <tr>
                    	<td class="lable">随机资料</td>
                        <td align="left">
                            <select name="gzyq" id="drp_bz">
								<option value="null">无</option>
								<c:forEach var="listbt" items="${listbt}"> 
                           			<option value="${listbt.mc}"<c:if test="${'随机资料' == listbt.mc}">selected</c:if>>${listbt.mc}</option>
								</c:forEach>
							</select>
                        </td>
                    	<td class="lable">备&emsp;&emsp;注</td>
                        <td align="left">
                            <select name="bz" id="drp_bz">
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