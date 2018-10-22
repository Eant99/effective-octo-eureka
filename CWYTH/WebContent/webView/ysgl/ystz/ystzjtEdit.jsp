<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>编辑项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		<div class="search pull-right" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				
				<button type="button" class="btn btn-default" id="btn_save">
					<i class="fa icon-save"></i>保存
				</button>
				
				<button type="button" class="btn btn-default" id="btn_back">
					返回
				</button>
				
				
				
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
									<th style="text-align: center;" colspan="6"><h3>财政支出预算目标制定表</h3></th>
								</tr>
							</thead>
							<tbody id="tbody">
                                <tr>
									<td style="text-align: center;">项目名称</td>
									<td><input type="text" name="xmmc" class="form-control input-radius" id="txt_xmmc" readonly="readonly" value="gt116">
									<td style="text-align: center;" colspan="3">项目类别</td>
									<td>
									<input type="text" id="txt_qyf2" class="form-control input-radius"  name="xmlb" value="教育" />
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
									<td colspan="4"><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="cqmb" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndmb" value="">
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
									<td><input type="text" id="txt_khyh1" name="slzb1" 
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1" name="slzb2" 
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="slzb3"
										class="form-control input-radius null"  value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1"  name="slzb4"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="slzb5"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="slzb6"
										class="form-control input-radius null"  value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">质量指标</td>
									<td><input type="text" id="txt_khyh1"  name="zlzb1"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="zlzb2"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="zlzb3"
										class="form-control input-radius null"  value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1"  name="zlzb4"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="zlzb5"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="zlzb6"
										class="form-control input-radius null"  value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">时效指标</td>
									<td><input type="text" id="txt_khyh1"  name="sxzb1"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="sxzb2"
										class="form-control input-radius null" value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="sxzb3"
										class="form-control input-radius null"  value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1"  name="sxzb4"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="sxzb5"
										class="form-control input-radius null" value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="sxzb6"
										class="form-control input-radius null"  value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">成本指标</td>
									<td><input type="text" id="txt_khyh1"  name="cbzb1"
										class="form-control input-radius null" value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="cbzb2"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="cbzb3"
										class="form-control input-radius null"  value="">
									</td>
								</tr>
								<tr>
									<td class="btn_td">
										<div class="addBtn1"></div>
									</td>
									<td><input type="text" id="txt_khyh1"  name="cbzb4"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="cbzb5"
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1"  name="cbzb6"
										class="form-control input-radius null"  value="">
									</td>
								</tr>
								<tr>
									
<!-- 									<td><input type="text" id="txt_khyh1"  -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
<!-- 									<td><input type="text" id="txt_khyh1"  -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
<!-- 									<td><input type="text" id="txt_khyh1"  -->
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
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="jjxyzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="jjxyzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="jjxyzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="jjxyzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="jjxyzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="jjxyzb6" value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">社会效益指标</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="shxyzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="shxyzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="shxyzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="shxyzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="shxyzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="shxyzb6" value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">生态效益指标</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="stxyzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="stxyzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="stxyzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="stxyzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="stxyzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="stxyzb6" value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">可持续影响指标</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="kcxyxzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="kcxyxzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="kcxyxzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="kcxyxzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="kcxyxzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="kcxyxzb6" value="">
									</td>
								</tr>
<!-- 								<tr> -->
<!-- 									<td class="btn_td"> -->
<!-- 										<div class="addBtn2"></div> -->
<!-- 									</td> -->
<!-- 									<td><input type="text" id="txt_khyh1"  -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
<!-- 									<td><input type="text" id="txt_khyh1"  -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
<!-- 									<td><input type="text" id="txt_khyh1"  -->
<!-- 										class="form-control input-radius null" name="xmssnr" value=""> -->
<!-- 									</td> -->
<!-- 								</tr> -->
								</tr>
								<tr>
									<td style="text-align: center;" id="shgztitle">社会公众或服务对象满意度指标</td>
									<td><input type="text" id="txt_khyh1" name="shgz1" 
										class="form-control input-radius null" name="shgg1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz2" 
										class="form-control input-radius null" name="shgg2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz3" 
										class="form-control input-radius null" name="shgg3" value="">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz4" 
										class="form-control input-radius null" name="shgg4" value="">
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
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndslzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndslzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndslzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndslzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndslzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndslzb6" value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">质量指标</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndzlzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndzlzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndzlzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndzlzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndzlzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndzlzb6" value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">时效指标</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndsxzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndsxzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndsxzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndsxzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndsxzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndsxzb6" value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">成本指标</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndcbzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndcbzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndcbzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndcbzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndcbzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndcbzb6" value="">
									</td>
								</tr>
								<tr>
									<td class="btn_td">
										<div class="addBtn3"></div>
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="xmssnr" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="xmssnr" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="xmssnr" value="">
									</td>
								</tr>
								<tr>
									<td id="xyzb4" rowspan="9" style="text-align: center;">效益指标</td>
									<td rowspan="2" style="text-align: center;">经济效益指标</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndjjxyzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndjjxyzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndjjxyzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndjjxyzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndjjxyzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndjjxyzb6" value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">社会效益指标</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndshxyzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndshxyzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndshxyzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndshxyzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndshxyzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndshxyzb6" value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">生态效益指标</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndstxyzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndstxyzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndstxyzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndstxyzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndstxyzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndstxyzb6" value="">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">可持续影响指标</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndkcxyxzb1" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndkcxyxzb2" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndkcxyxzb3" value="">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndkcxyxzb4" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndkcxyxzb5" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="ndkcxyxzb6" value="">
									</td>
								</tr>
								<tr>
									<td class="btn_td">
										<div class="addBtn4"></div>
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="xmssnr" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="xmssnr" value="">
									</td>
									<td><input type="text" id="txt_khyh1" 
										class="form-control input-radius null" name="xmssnr" value="">
									</td>
								</tr>
								</tr>
								<tr>
									<td style="text-align: center;">社会公众或服务对象满意度指标</td>
									<td><input type="text" id="txt_khyh1" name="shgz5" 
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz6" 
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null"  value="">
									</td>
									<td><input type="text" id="txt_khyh1" name="shgz8" 
										class="form-control input-radius null"  value="">
									</td>
								</tr>
								<tr>
									<td style="text-align: center;">其他需要说明的问题</td>
									<td colspan="5"><input type="text" id="txt_khyh1" name="qtwt" 
										class="form-control input-radius null" name="xmssnr" value="">
									</td>
								</tr>
								<tr id="hidd" type="hidden" style="display: none;">
									<td></td>
									<td align="center">
										<select style="width: 100%;" class="sele">
											 
												<option value="01">生产指标</option>
											 
												<option value="03">预算指标</option>
											 
												<option value="02">填报指标</option>
											
										</select>
									</td>
									<td id="erzb_1" class="title" style="text-align: center;"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd2" style="display: none;">
									<td align="center"><div class="addBtn1"></div></td>
									<td></td>
									<td></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd3" style="display: none;">
									<td></td>
									<td align="center">
<!-- 										<select style="width: 100%;" class="sele"> -->



<!-- 										</select> -->
									</td>
									<td id="kerzb_1"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd4" style="display: none;">
									<td align="center"></td>
									<td></td>
									<td  class="title" style="text-align: center;" id=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd5" type="hidden" style="display: none;">
									<td align="center">
										<select style="width: 100%;" class="sele2">
											 
												<option value="01">生产指标</option>
											 
												<option value="03">预算指标</option>
											 
												<option value="02">填报指标</option>
											
										</select>
									</td>
									<td id="" class="title" style="text-align: center;"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd6" type="hidden" style="display: none;">
									<td></td>
									<td id="" class="" style="text-align: center;"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd7" style="display: none;" style="display: none;">
									<td></td>
									<td id="zb_1"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>
								<tr id="hidd8" style="display: none;">
									<td align="center"></td>
									<td class="title" style="text-align: center;" id="kzb_1"></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
									<td><input type="text" id="txt_khyh1" name="shgz7" 
										class="form-control input-radius null" name="xmssnr" value=""></td>
								</tr>

							</tbody>
						</table>
					</form>
				</div>
			</div>
    </div>
    </div>
    <%@include file="/static/include/public-manager-js.inc"%>
    <script>
	//保存
	$("#btn_save").click(function() {
		alert("保存成功！");
		history.back(-1);
	});
	//返回按钮
	$("#btn_back").click(function(e){
		history.back(-1);
	});
	
	</script>
</body>

</html>