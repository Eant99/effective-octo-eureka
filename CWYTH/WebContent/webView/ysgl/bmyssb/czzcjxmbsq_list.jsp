<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
.btn_td {
	width: 10px !important;
	height: 6mm !important;
	text-align: center;
}

.addBtn {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}
.addBtn:after {
	/* 		2722 */
	content: "+";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 17px;
	position: relative;
	cursor: pointer;
}
.addBtn1 {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.addBtn1:after {
	/* 		2722 */
	content: "+";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 17px;
	position: relative;
	cursor: pointer;
}
.addBtn2 {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.addBtn2:after {
	/* 		2722 */
	content: "+";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 17px;
	position: relative;
	cursor: pointer;
}
.addBtn3 {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.addBtn3:after {
	/* 		2722 */
	content: "+";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 17px;
	position: relative;
	cursor: pointer;
}
.addBtn4 {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.addBtn4:after {
	/* 		2722 */
	content: "+";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 17px;
	position: relative;
	cursor: pointer;
}

.deleteBtn {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.deleteBtn:after {
	/* 		2014 */
	content: "\2014";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 1em;
	position: relative;
	cursor: pointer;
	top: 3px;
}
.deleteBtn1 {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.deleteBtn1:after {
	/* 		2014 */
	content: "\2014";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 1em;
	position: relative;
	cursor: pointer;
	top: 3px;
}
.deleteBtn2 {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.deleteBtn2:after {
	/* 		2014 */
	content: "\2014";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 1em;
	position: relative;
	cursor: pointer;
	top: 3px;
}
.deleteBtn3 {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.deleteBtn3:after {
	/* 		2014 */
	content: "\2014";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 1em;
	position: relative;
	cursor: pointer;
	top: 3px;
}
.deleteBtn4 {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.deleteBtn4:after {
	/* 		2014 */
	content: "\2014";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 1em;
	position: relative;
	cursor: pointer;
	top: 3px;
}

.border {
	border: 1px solid #a94442;
	background: url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right
		no-repeat;;
}
     #tbody2 input{
		border:none;
	
	    padding:4px !important;
	}  
    #tbody3 input{
		border:none;
	
	    padding:4px !important;
	}  
	 #tbody1 input{
		border:none;
		width:100%;
	    padding:4px !important;
	} 
	.bk{
		border:none;
	
	    padding:4px !important;
	} 
</style>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				<c:if test="${'sh'!=sh && 'ck'!=ck}">
				<button type="button" class="btn btn-default" id="btn_save">
					<i class="fa icon-save"></i>保存
				</button>
				</c:if>
				<button type="button" class="btn btn-default" id="btn_back">
					<i class="fa icon-save"></i>返回
				</button>
				<%-- 		<input type="hidden" name="operateType" id="operateType" value="${operateType}"> --%>
				<%-- 		<input type="hidden" name="guid" value="${dwb.guid}">  --%>
				<%-- 		<input type="hidden" name="czr"	value="${loginId}"> --%>
			</form>
		</div>
		<div class="container-fluid">
			<div class='responsive-table'>
				<div class='scrollable-area'>
					<form id="myform1">
						<table id="mydatatables"
							class="table table-striped table-bordered"
							style="border-collapse: collapse">
							<thead id="thead">
								<tr>
									<th style="text-align: center;" colspan="6"><h3>财政支出绩效目标申请表</h3></th>
								</tr>
								<div class="search-simple">	
									<div > 
										<label>填报部门:</label>					
					 					<input type="text" class=" input-radius bk" name="tbbm" style="width: 140px;"  id="txt_tbbm" readonly="readonly" value="${bmmc}">
									</div> 
									 <div class="pull-right">
										<label>单位：万元</label>
									 </div>
								</div>
							</thead>
							<tbody id="tbody">
							<tbody id="tbody2">
								<tr>
									<td style="text-align: center;">项目名称</td>
									<td><input type="text" name="xmmc" id="txt_xmmc" readonly="readonly" value="${info.xmmc }">
										<input type="hidden" id="hdn_xmmc" name="xmid"  value="${info.xmid }">
										<span><button type="button" id="btn_xmmc" class="btn btn-link btn-custom">选择</button></span></td>
									<td style="text-align: center;" colspan="3">项目类别</td>
									<td><input type="radio" id="txt_qyf1" name="xmlb" value="1" />投资类项目 
									<input type="radio" id="txt_qyf2" name="xmlb" value="0" />发展类项目</td>
								</tr>
								<tr>
									<td style="text-align: center;">主管部门</td>
									<td><input type="text" name="zgbm" id="txt_zgbm" readonly value="${info.zgbm }">
									<span><button type="button" id="btn_zgbm" class="btn btn-link btn-custom">选择</button></span></td></td>
									<td style="text-align: center;" colspan="3" value="${info.zgbmbm }">主管部门编码</td>
									<td><input type="text" name="zgbmbm"></td>
								</tr>
								<tr>
									<td style="text-align: center;">项目实施单位</td>
									<td><input type="text" name="xmssdw" readonly id="txt_xmssdw" value="${info.xmssdw }">
									<span><button type="button" id="btn_xmssdw" class="btn btn-link btn-custom">选择</button></span></td>
									<td style="text-align: center;">项目负责人</td>
									<td><input type="text" name="xmfzr" readonly id="txt_xmfzr" value="${info.xmssdw }">
									<span><button type="button" id="btn_xmfzr" class="btn btn-link btn-custom">选择</button></span></td>
									<td style="text-align: center;" value="${info.xmssdw }">联系电话</td>
									<td><input type="text" name="lxdh"></td>
								</tr>
								<tr>
									<td style="text-align: center;">项目类型</td>
									<td colspan="6"><input type="radio" id="txt_qyf1" name="xmlx" value="1" />上年原有项目 
									<input type="radio" id="txt_qyf2" name="xmlx" value="0" />新增固定项目 
									<input type="radio" id="txt_qyf1" name="xmlx" value="1" />新增一次性项目 
									<input type="radio" id="txt_qyf2" name="xmlx" value="0" />其他项目</td>
								</tr>
								</tbody>
								<tr>
									<td style="text-align: center;">项目期限</td>
									<td colspan="6">
										<div class="input-group">
											<span class="input-group-addon">开始时间：</span> <input type="text" id="txt_jlrq" class="form-control date sj"
												name="kssj" value="${info.KSSJ}"> <span
												class='input-group-addon'> <i
												class="glyphicon glyphicon-calendar"></i>
											</span>
										</div>
										<div class="input-group">
											<span class="input-group-addon">结束时间：</span> 
											<input type="text" id="txt_jlrq" class="form-control date sj" name="jssj" value="${info.wcsj}" > 
												<span class='input-group-addon'> 
												<i class="glyphicon glyphicon-calendar"></i>
											</span>
										</div>
									</td>
								</tr>
								<tbody id="tbody3">
								<tr>
									<td style="text-align: center;" rowspan="5">项目资金申请（万元）</td>
									<td style="text-align: center;">资金总额：</td>
									<td colspan="4"><input type="text" name="zjze" class="number" style="text-align: right;" value="${info.zjze}"></td>
								</tr>
								<tr>
									<td style="text-align: center;">财政拨款：</td>
									<td colspan="4"><input type="text" name="czbk" class="number" style="text-align: right;" value="${info.czbk}"></td>
								</tr>
								<tr>
									<td style="text-align: center;">事业收入：</td>
									<td colspan="4"><input type="text" name="sysr" class="number" style="text-align: right;" value="${info.SYSR}"></td>
								</tr>
								<tr>
									<td style="text-align: center;">经营性收入：</td>
									<td colspan="4"><input type="text" name="jysr" class="number" style="text-align: right;" value="${info.JYXSR}"></td>
								</tr>
								<tr>
									<td style="text-align: center;">其他：</td>
									<td colspan="4"><input type="text" name="qt" class="number" style="text-align: right;" value="${info.qt}"></td>
								</tr>
								<tr>
									<td style="text-align: center;">测算依据及说明</td>
									<td colspan="6"><input type="text" name="csyjjsm" style="width: 100%;" value="${info.csyjjsm}"/></td>
								</tr>
								<tr>
									<td style="text-align: center;">项目单位职能概述</td>
									<td colspan="6"><input type="text" name="xmdwzngs" style="width: 100%;" value="${info.XMDWZNMS}"/></td>
								</tr>
								<tr>
									<td style="text-align: center;">项目概况、主要内容及用途</td>
									<td colspan="6"><input type="text" name="xmgkzynrjyt" style="width: 100%;" value="${info.xmgkzynrjyt}"/>
									</td>
								</tr>
								<tr>
									<td style="text-align: center;" rowspan="2">项目立项情况</td>
									<td>项目立项的依据</td>
									<td colspan="4"><input type="text" name="xmlxdyj" style="width: 100%;" value="${info.xmlxdyj}"/></td>
								</tr>
								<tr>
									<td>项目申报的可行性和必要性</td>
									<td colspan="4"><input type="text" name="xmsbdkxxhbyx" style="width: 100%;" value="${info.xmsbdkxxhbyx}"/>
									</td>
								</tr>
								</tbody>
								<tbody id="tbody1">
								<tr>
									<td id="xmssjh" style="text-align: center;" rowspan="2">
										<div style="border: 0px;">项目实施进度计划</div>
									</td>
									<td style="text-align: center;">操作</td>
									<td style="text-align: center;" colspan="2">项目实施内容</td>
									<td style="text-align: center;">开始时间</td>
									<td style="text-align: center;">完成时间</td>
								</tr>
								<tr>
									<td class="btn_td">
										<div class="addBtn"></div>
									</td>
									<td colspan="2">
										<input type="text" id="txt_khyh1" a="a" class=" input-radius null" name="xmssnr" value="">
									</td>
									<td>
										<div class="input-group">
											<input type="text" id="txt_jlrq" class=" date" name="kssj" value="<fmt:formatDate value="${dwb.JLRQ}" pattern="yyyy-MM-dd"/>"
												data-format="yyyy-MM-dd" placeholder=""> 
												<span class='input-group-addon'> <i class="glyphicon glyphicon-calendar"></i>
											</span>
										</div>
									</td>
									<td>
										<div class="input-group">
											<input type="text" id="txt_jlrq" class=" date"
												name="wcsj"
												value="<fmt:formatDate value="${dwb.JLRQ}" pattern="yyyy-MM-dd"/>"
												data-format="yyyy-MM-dd" placeholder=""> <span
												class='input-group-addon'> <i
												class="glyphicon glyphicon-calendar"></i>
											</span>
										</div>
									</td>
								</tr>
								<tr>
									<td id="xmssjh" style="text-align: center;" rowspan="2">
										<div style="border: 0px;">项目绩效目标</div>
									</td>
									<td colspan="4" style="text-align: center;">长期目标</td>
									<td style="text-align: center;">年度目标</td>
								</tr>
								<tr>
									<td colspan="4"><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="cqmb" value="${info.cqzb}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndmb" value="${info.ndzb}">
									</td>

								</tr>
								<tr>
									<td id="cqjxzb" rowspan="8" style="text-align: center;">长期绩效指标</td>
									<td style="text-align: center;">一级指标</td>
									<td style="text-align: center;">二级指标</td>
									<td style="text-align: center;">指标内容</td>
									<td style="text-align: center;">指标值</td>
									<td style="text-align: center;">备注</td>
								</tr>
								<tr>
									<td  id="cczb" rowspan="8" style="text-align: center;">产出指标</td>
									<td rowspan="2" style="text-align: center;">数量指标</td>
									<td><input type="text" id="txt_khyh1" name="slzb1" a="a1"
										class=" input-radius null"  value="${info.slzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" name="slzb2" a="a1"
										class=" input-radius null"  value="${info.slzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="slzb3"
										class=" input-radius null"  value="${info.slzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a1" name="slzb4"
										class=" input-radius null"  value="${info.slzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="slzb5"
										class=" input-radius null"  value="${info.slzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="slzb6"
										class=" input-radius null"  value="${info.slzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">质量指标</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="zlzb1"
										class=" input-radius null"  value="${info.zlzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="zlzb2"
										class=" input-radius null"  value="${info.zlzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="zlzb3"
										class=" input-radius null"  value="${info.zlzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a1" name="zlzb4"
										class=" input-radius null"  value="${info.zlzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="zlzb5"
										class=" input-radius null"  value="${info.zlzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="zlzb6"
										class=" input-radius null"  value="${info.zlzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">时效指标</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="sxzb1"
										class=" input-radius null"  value="${info.sxzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="sxzb2"
										class=" input-radius null" value="${info.sxzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="sxzb3"
										class=" input-radius null"  value="${info.sxzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a1" name="sxzb4"
										class=" input-radius null"  value="${info.sxzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="sxzb5"
										class=" input-radius null" value="${info.sxzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="sxzb6"
										class=" input-radius null"  value="${info.sxzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">成本指标</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="cbzb1"
										class=" input-radius null" value="${info.cbzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="cbzb2"
										class=" input-radius null"  value="${info.cbzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="cbzb3"
										class=" input-radius null"  value="${info.cbzb3}">
									</td>
								</tr>
								<tr>
									<td class="btn_td">
										<div class="addBtn1"></div>
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="cbzb4"
										class=" input-radius null"  value="${info.cbzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="cbzb5"
										class=" input-radius null"  value="${info.cbzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a1" name="cbzb6"
										class=" input-radius null"  value="${info.cbzb6}">
									</td>
								</tr>
								<tr>
									
<!-- 									<td><input type="text" id="txt_khyh1" a="a1" -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
<!-- 									<td><input type="text" id="txt_khyh1" a="a1" -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
<!-- 									<td><input type="text" id="txt_khyh1" a="a1" -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
								</tr>
								<!------------------------------------------以下是长期绩效指标--------------------------------------------------------------------------  -->
								<tr>
									<td id="cqjxzb1" rowspan="9" style="text-align: center;">长期绩效指标 <br><br><br><br><br><br><br><div class="addBtn2"></div>
									<br><br><br><br><br><br><br><br><br><div id="cqjxzb2" ></div>
									</td>
									<td id="xyzb" rowspan="8" style="text-align: center;">效益指标</td>
									<td rowspan="2" style="text-align: center;">经济效益指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb1" value="${info.jjxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb2" value="${info.jjxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb3" value="${info.jjxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb4" value="${info.jjxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb5" value="${info.jjxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb6" value="${info.jjxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">社会效益指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb1" value="${info.shxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb2" value="${info.shxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb3" value="${info.shxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb4" value="${info.shxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb5" value="${info.shxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb6" value="${info.shxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">生态效益指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb1" value="${info.stxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb2" value="${info.stxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb3" value="${info.stxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb4" value="${info.stxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb5" value="${info.stxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb6" value="${info.stxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">可持续影响指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb1" value="${info.kcxyxzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb2" value="${info.kcxyxzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb3" value="${info.kcxyxzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb4" value="${info.kcxyxzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb5" value="${info.kcxyxzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb6" value="${info.kcxyxzb6}">
									</td>
								</tr>
<!-- 								<tr> -->
<!-- 									<td class="btn_td"> -->
<!-- 										<div class="addBtn2"></div> -->
<!-- 									</td> -->
<!-- 									<td><input type="text" id="txt_khyh1" a="a" -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
<!-- 									<td><input type="text" id="txt_khyh1" a="a" -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
<!-- 									<td><input type="text" id="txt_khyh1" a="a" -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
<!-- 								</tr> -->
								</tr>
								<tr>
									<td style="text-align: center;" id="shgztitle">社会公众或服务对象满意度指标</td>
									<td><input type="text" id="txt_khyh1" name="shgz1" a="a"
										class=" input-radius null" name="shgg1" value="${info.shgg1}">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz2" a="a"
										class=" input-radius null" name="shgg2" value="${info.shgg2}">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz3" a="a"
										class=" input-radius null" name="shgg3" value="${info.shgg3}">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz4" a="a"
										class=" input-radius null" name="shgg4" value="${info.shgg4}">
									</td>
								</tr>
								<!-------------------------------------------------------------------年度绩效指标-------------------------------------------------------------------------  -->
								<tr>
									<td id="ndjxzb" rowspan="20" style="text-align: center;">年度绩效指标</td>
									<td style="text-align: center;">一级指标</td>
									<td style="text-align: center;">二级指标</td>
									<td style="text-align: center;">指标内容</td>
									<td style="text-align: center;">指标值</td>
									<td style="text-align: center;">备注</td>
								</tr>
								<tr>
									<td id="cczb3" rowspan="9" style="text-align: center;">产出指标</td>
									<td rowspan="2" style="text-align: center;">数量指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndslzb1" value="${info.ndslzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndslzb2" value="${info.ndslzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndslzb3" value="${info.ndslzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndslzb4" value="${info.ndslzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndslzb5" value="${info.ndslzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndslzb6" value="${info.ndslzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">质量指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndzlzb1" value="${info.ndzlzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndzlzb2" value="${info.ndzlzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndzlzb3" value="${info.ndzlzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndzlzb4" value="${info.ndzlzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndzlzb5" value="${info.ndzlzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndzlzb6" value="${info.ndzlzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">时效指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndsxzb1" value="${info.ndsxzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndsxzb2" value="${info.ndsxzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndsxzb3" value="${info.ndsxzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndsxzb4" value="${info.ndsxzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndsxzb5" value="${info.ndsxzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndsxzb6" value="${info.ndsxzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">成本指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndcbzb1" value="${info.ndcbzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndcbzb2" value="${info.ndcbzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndcbzb3" value="${info.ndcbzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndcbzb4" value="${info.ndcbzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndcbzb5" value="${info.ndcbzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndcbzb6" value="${info.ndcbzb6}">
									</td>
								</tr>
								<tr>
									<td class="btn_td">
										<div class="addBtn3"></div>
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="xmssnr" value="">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="xmssnr" value="">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="xmssnr" value="">
									</td>
								</tr>
								<tr>
									<td id="xyzb4" rowspan="9" style="text-align: center;">效益指标</td>
									<td rowspan="2" style="text-align: center;">经济效益指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndjjxyzb1" value="${info.ndjjxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndjjxyzb2" value="${info.ndjjxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndjjxyzb3" value="${info.ndjjxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndjjxyzb4" value="${info.ndjjxyzb3}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndjjxyzb5" value="${info.ndjjxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndjjxyzb6" value="${info.ndjjxyzb5}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">社会效益指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndshxyzb1" value="${info.ndshxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndshxyzb2" value="${info.ndshxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndshxyzb3" value="${info.ndshxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndshxyzb4" value="${info.ndshxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndshxyzb5" value="${info.ndshxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndshxyzb6" value="${info.ndshxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">生态效益指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndstxyzb1" value="${info.ndstxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndstxyzb2" value="${info.ndstxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndstxyzb3" value="${info.ndstxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndstxyzb4" value="${info.ndstxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndstxyzb5" value="${info.ndstxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndstxyzb6" value="${info.ndstxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">可持续影响指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndkcxyxzb1" value="${info.ndkcxyxzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndkcxyxzb2" value="${info.ndkcxyxzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndkcxyxzb3" value="${info.ndkcxyxzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndkcxyxzb4" value="${info.ndkcxyxzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndkcxyxzb5" value="${info.ndkcxyxzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="ndkcxyxzb6" value="${info.ndkcxyxzb6}">
									</td>
								</tr>
								<tr>
									<td class="btn_td">
										<div class="addBtn4"></div>
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="xmssnr" value="">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="xmssnr" value="">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="xmssnr" value="">
									</td>
								</tr>
								</tr>
								<tr>
									<td style="text-align: center;">社会公众或服务对象满意度指标</td>
									<td><input type="text" id="txt_khyh1" name="shgz5" a="a"
										class=" input-radius null"  value="${info.shgz5}">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz6" a="a"
										class=" input-radius null"  value="${info.shgg6}">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class=" input-radius null"  value="${info.shgg7}">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz8" a="a"
										class=" input-radius null"  value="${info.shgg8}">
									</td>
								</tr>
								<tr>
									<td style="text-align: center;">其他需要说明的问题</td>
									<td colspan="5"><input type="text" id="txt_khyh1" name="qtwt" a="a"
										class=" input-radius null" name="xmssnr" value="${info.shgg4}">
									</td>
								</tr>
								
								<tr id="hidd" type="hidden" style="display: none;">
									<td></td>
									<td align="center">
										<select style="width: 100%;" class="sele">
											<c:forEach var="item" items="${xlklist}"> 
												<option value="${item.DM}">${item.MC}</option>
											</c:forEach>
										</select>
									</td>
									<td id="erzb_1" class="title" style="text-align: center;"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd2" style="display: none;">
									<td align="center"><div class="addBtn1"></div></td>
									<td></td>
									<td></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd3" style="display: none;">
									<td></td>
									<td align="center">
<!-- 										<select style="width: 100%;" class="sele"> -->
<%-- 											<c:forEach var="item" items="${xlklist}">  --%>
<%-- 												<option value="${item.DM}">${item.MC}</option> --%>
<%-- 											</c:forEach> --%>
<!-- 										</select> -->
									</td>
									<td id="kerzb_1"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd4" style="display: none;">
									<td align="center"></td>
									<td></td>
									<td  class="title" style="text-align: center;" id=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd5" type="hidden" style="display: none;">
									<td align="center">
										<select style="width: 100%;" class="sele2">
											<c:forEach var="item" items="${xlklist}"> 
												<option value="${item.DM}">${item.MC}</option>
											</c:forEach>
										</select>
									</td>
									<td id="" class="title" style="text-align: center;"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd6" type="hidden" style="display: none;">
									<td></td>
									<td id="" class="" style="text-align: center;"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd7" style="display: none;" style="display: none;">
									<td></td>
									<td id="zb_1"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd8" style="display: none;">
									<td align="center"></td>
									<td class="title" style="text-align: center;" id="kzb_1"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" a="a"
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
                              </tbody>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/static/include/public-list-js.inc"%>
	<script>
		$(function() {
			//长期绩效指标的一级指标
			$(document).on("change",".sele",function(){
				console.log("____________aaaaa___");
				var val = $(this).val();
				$("td[id^='erzb_']").parent("tr:gt(0)").remove();
				$("td[id^='kerzb_']").parent("tr:gt(0)").remove();
				$.ajax({
					type:"post",
					url:"${ctx}/ysgl/bmyssb/getLengthBydm",
					data:"dm="+val,
					async:false,
					success:function(val){
						 $.each(JSON.parse(val),function(i,value) {
								if(i>0){
					           		console.log(i+' '+value.EJZB);
									var $parentTr = $("#hidd3").clone();
									var $parentTr2 = $("#hidd4").clone();
									$(".sele").parents("tr").after($parentTr2);
									$(".sele").parents("tr").after($parentTr);
									$parentTr2.find(".title").attr("id","erzb_"+(i+1));
									$("#erzb_"+i).text(value.EJZB);
								}else{
									$(".title").text(value.EJZB);
								}
					     });  
					}
				})
			});
			//长期绩效指标的一级指标
			
			$(document).on("change",".sele2",function(){
				var val = $(this).val();
				var num = parseInt($("#cqjxzb1").attr("rowspan"));
				console.log("_________________1___"+num);
				$("td[id^='klzb_']").parent("tr").remove();
				$("td[id^='lzb_']").parent("tr").remove();
// 				$("#erzb^ td:gt(1)").remove();
				$.ajax({
					type:"post",
					url:"${ctx}/ysgl/bmyssb/getLengthBydm",
					data:"dm="+val,
					async:false,
					success:function(val){
						 $.each(JSON.parse(val),function(i,value) {
				           		console.log(i+' '+value.EJZB);
								if(i>0){
									var $parentTr = $("#hidd7").clone();
									var $parentTr2 = $("#hidd8").clone();
									$(".sele2").parents("tr").after($parentTr2.removeAttr("style"));
									$(".sele2").parents("tr").after($parentTr.removeAttr("style"));
									$parentTr2.find(".title").attr("id","kzb_"+(i+1));
									$("#kzb_"+i).attr("id","klzb_"+i)
									$("#zb_"+i).attr("id","lzb_"+i)
									$("#klzb_"+i).text(value.EJZB);
									console.log("___2__"+num)
									 $("#cqjxzb1").attr("rowspan",num);
									 num=num+2;
								}else{
									console.log("___________fffff");
									$(".title").text(value.EJZB);
									console.log("___2__"+num)
									num=num+2;
								}
					     });  
					}
				})
			});
			//必填验证
			var fields = { fields : { 
					xmmc : {
						validators : {
							notEmpty : {
								message : '不能为空'
							}
						}
					}
				}
			};
			//保存
			$("#btn_save").click(function() {
				var url = "${ctx}/ysgl/bmyssb/doSave?operateType=C&dm=7";
				doSave1($("#myform1"),url,function(val) {
					var result = JSON.getJson(val);
					if (result.success) {
						alert("保存成功！");
						window.location = "${ctx}/ysgl/bmyssb/goListPage?operateType=C&dm=7";
					}
				});
			});
			//返回按钮
		$("#btn_back").click(function(e){
				var sh="${param.sh}";
				var ck="${param.ck}";
				if(sh=="sh"&&ck=="ck"){
					parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
					return;
				}
				if(sh=="sh"&&ck!="ck"){
					parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
					return;
				}
				if(ck=="ck"&&sh!="sh"){
					parent.location.href  = "${ctx}/ysgl/bmyssb/goBmyssbPage";
				}
// 				if(sh==''||sh==null||sh==undefined){
// 					if(ck==''||ck==null||ck==undefined){
// 					parent.location.href  = "${ctx}/ysgl/bmyssb/goBmyssbPage";
// 					}else{
// 						parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
// 					}
// 				}else{
// 				parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
// 				}
		});
			//保存方法	
			function doSave1($form, _url, _success, _fail) {
				//     	var validator = $('#myform1').bootstrapValidator(fields);
				//     	if(validator){
				//     		validator.bootstrapValidator("fields");
				//     		var valid = $('#myform1').data('bootstrapValidator').isValid();
				//     		if(!valid){
				//     			return;
				//     		}
				//     	}
				var json = $("#myform1").serializeObject("xmssnr","wcsj");
				
			   	var name = $("#txt_tbbm").attr("name");
			   	var value = $("#txt_tbbm").val();
			   	json[name]=value;
			   	var jsonStr = JSON.stringify(json);
			   	console.log("_________"+JSON.stringify(arrayToJson($('#myform1').serializeArray())));
				$.ajax({
					type : "post",
					url : _url,
					data :"json="+jsonStr+"&jsonStr="+JSON.stringify(arrayToJson($('#myform1').serializeArray())),
					success : function(data) {
						_success(data);
					},
					error : function(val) {
						alert("抱歉，系统出现问题！");
					},
				});
			}
			//弹框
			$("#btn_xmmc").click(function() {
				select_commonWin("${ctx}/xmxxt/goXmxxTree?controlId=txt_xmmc&id=hdn_xmmc","项目信息", "920", "630");
			});
			//弹框
			$("#btn_zgbm").click(function() {
				select_commonWin("${ctx}/window/dwpage?controlId=txt_zgbm","项目信息", "920", "630");
			});
			//弹框
			$("#btn_xmssdw").click(function() {
				select_commonWin("${ctx}/window/dwpage?controlId=txt_xmssdw","项目信息", "920", "630");
			});
			//弹框
			$("#btn_xmfzr").click(function() {
				select_commonWin("${ctx}/window/rypage?controlId=txt_xmfzr","项目信息", "920", "630");
			});
			//新增按钮0
			var num = 2;
			$(document).on("click", "[class=addBtn]", function() {
				var $parentTr = $(this).parents("tr").clone();
				$(this).removeClass("addBtn");
				$(this).addClass("deleteBtn");
				$parentTr.find("[a='a']").val("");
				$parentTr.find(":input").removeClass("border");
				$parentTr.attr("id", "tr_" + num);

				num++;
				$(this).parents("tr").after($parentTr);
				$("#xmssjh").attr("rowspan", num);
			});
			//长期绩效指标 新增按钮1
			var nums = 1;
			$(document).on("click", "[class=addBtn1]", function() {
				$("#hidd").find(".title").text("")
				var $parentTr = $("#hidd").clone();
				var $parentTr2 = $("#hidd2").clone();
				$(this).removeClass("addBtn1").addClass("deleteBtn1");
				$parentTr.attr("id","cqzb_"+nums);
				$(this).parents("tr").after($parentTr2.removeAttr("style"));
				$(this).parents("tr").after($parentTr.removeAttr("style"));
			});
			//长期绩效指标 新增按钮2
			var nums2 = 9;
			$(document).on("click", "[class=addBtn2]", function() {
				var $parentTr = $("#hidd5").clone();
				var $parentTr2 = $("#hidd6").clone();
				$("#shgztitle").parent("tr").before($parentTr.removeAttr("style"));
				$("#shgztitle").parent("tr").before($parentTr2.removeAttr("style"));
				$(this).removeClass("addBtn2").addClass("deleteBtn2");
				$("#cqjxzb1").attr("rowspan",nums2+2);
				$("#cqjxzb2").removeClass("deleteBtn2").addClass("addBtn2");
				$("#cqjxzb1").append("<br><br><br><br><br><br><br><div id='cqjxzb_'"+parseInt(num2)-6+"></div>");
				nums2++;
			});
			//新增按钮3
			var nums3 = 9;
			$(document).on("click", "[class=addBtn3]", function() {
				var $parentTr = $(this).parents("tr").clone();
				$(this).removeClass("addBtn3");
				$(this).addClass("deleteBtn3");
				$parentTr.find("[a='a']").val("");
				$parentTr.find(":input").removeClass("border");
				$parentTr.attr("id", "tr_" + nums3);
				nums3++;
				$(this).parents("tr").after($parentTr);
				$("#cczb3").attr("rowspan", nums3);
				$("#ndjxzb").attr("rowspan", nums3+11);
			});
			//新增按钮4
			var nums4 = 9;
			$(document).on("click", "[class=addBtn4]", function() {
				var $parentTr = $(this).parents("tr").clone();
				$(this).removeClass("addBtn4");
				$(this).addClass("deleteBtn4");
				$parentTr.find("[a='a']").val("");
				$parentTr.find(":input").removeClass("border");
				$parentTr.attr("id", "tr_" + nums4);
				nums4++;
				$(this).parents("tr").after($parentTr);
				$("#xyzb4").attr("rowspan", nums4);
				$("#ndjxzb").attr("rowspan", nums4+11);
			});

			//删除按钮
			$(document).on("click", "[class=deleteBtn]", function() {
				$(this).parents("tr").remove();
				num--;
				$("#xmssjh").attr("rowspan", num);

			});
			//删除按钮1
			$(document).on("click", "[class=deleteBtn1]", function() {
				$(this).removeClass("deleteBtn1").addClass("addBtn1");
				$("td[id^='erzb_']").parent("tr").remove();
// 				$("td[id^='erzb_']").parent("tr").next("tr").remove();
				$("td[id^='kerzb_']").parent("tr").remove();
				$(this).parents("tr").next("tr").remove();
// 				nums--;
				
// 				$("#cczb").attr("rowspan", nums);

// 				$("#cqjxzb").attr("rowspan", nums+1);

			});
			//删除按钮2
			$(document).on("click", "[class=deleteBtn2]", function() {
				$(this).parents("tr").remove();
				nums2--;
				$("#xyzb").attr("rowspan", nums2);
				$("#cqjxzb1").attr("rowspan", nums2+1);

			});
			//删除按钮3
			$(document).on("click", "[class=deleteBtn3]", function() {
				$(this).parents("tr").remove();
				nums3--;
				$("#cczb3").attr("rowspan", nums3);
				$("#ndjxzb").attr("rowspan", nums3+11);

			});
			//删除按钮4
			$(document).on("click", "[class=deleteBtn4]", function() {
				$(this).parents("tr").remove();
				nums4--;
				$("#xyzb4").attr("rowspan", nums4);
				$("#ndjxzb").attr("rowspan", nums4+11);

			});

			$("#btn_add").click(function() {
				//doOperate("${ctx}/webView/kjhs/yhzhgl/yhzhgl_edit.jsp","C");
				//alert("增加完成后，请勾选新增记录前的多选框然后单击页面右上方的‘保存’按钮。");

				var index = $(".uid:last").attr("id");
				if (index == null) {
					index = 0;
				} else {
					index++;
				}
				var trObj = document.createElement("tr");
				trObj.setAttribute("id", index);
				if (index % 2 == 0 || index == 1) {
					trObj.setAttribute("class", "odd");
				} else {
					trObj.setAttribute("class", "even");
				}

				trObj.setAttribute("role", "row");

				trObj.innerHTML = "<td><input type='checkbox' name='guid' value='0'></td>"
						+ "<td class='text-center'> <input type='hidden' class='uid' id='++index'></td>"
						+ "<td><input type='text' name='yhdm'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td><input type='text' name='yhmc'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td><input type='text' name='yhdm'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td><input type='text' name='yhmc'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"
						+ "<td class='text-center'><a  class='btn btn-link btn_delxx'>删除</a></td>";
				/* trObj.innerHTML = "<td><input type='checkbox' name='guid' value='0'></td>"+
				"<td class='text-center'> <input type='hidden' class='uid' id='"+index+"'>"+index+"</td>
				<td><input type='text' name='yhdm'  style='text-align: center;width:100%;border:none;' value=''/></td>"+ 
				"<td><input type='text' name='yhmc'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
				"<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
				"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
				"<td class='text-center'><a  class='btn btn-link btn_delxx'>删除</a></td>"; */
				document.getElementById("bod").appendChild(trObj);
			});

			//修改按钮
			$(document).on("click", ".btn_upd", function() {
				var guid = $(this).parents("tr").find("[name='guid']").val();
				doOperate("${ctx}/webView/kjhs/yhzhgl/yhzhgl_edit.jsp", "C");
			});

			//批量删除按钮
			$("#btn_del").click(function() {
				var checkbox = $("#mydatatables").find("[name='guid']")
						.filter(":checked");
				if (checkbox.length > 0) {
					var guid = [];
					checkbox.each(function() {
						$(this).parents("tr").remove();
						px();
					});

				} else {
					alert("请选择至少一条信息删除!");
				}
			});
			//单条删除
			$(document).on("click", ".btn_delxx", function() {
				// 		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
				//    		var shzt = checkbox.attr("shzt");
				//    		if(shzt=="已提交"||shzt=="通过"){
				//    			alert("已提交或者审核通过的无法修改");
				//    			return;
				//    		}
				// 		confirm("确定删除？","{title:提示信息}",function(){
				// 			alert("删除成功");
				// 		});
				//alert("sss");
				$(this).parents("tr").remove();
				px();
			});
		});
		function remove() {

		}
// 		$(function() {
// 			//列表右侧悬浮按钮
// 			$(window).resize(
// 					function() {
// 						$("div.dataTables_wrapper").width(
// 								$("#searchBox").width());
// 						heights = $(window).outerHeight()
// 								- $(".search").outerHeight()
// 								- $(".row.bottom").outerHeight() - 20
// 								- $(".dataTables_scrollHead").outerHeight();
// 						$(".dataTables_scrollBody").height(heights);
// 						table.draw();
// 					});
// 		});
	</script>
</body>
</html>