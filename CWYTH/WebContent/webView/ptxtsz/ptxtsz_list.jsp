<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>模板选择</title>
<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css"rel="stylesheet">
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/bootstrap/bootstrap.min.js"type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.dataTables_scrollHeadInner{width:100% ! important;} 
 	table.dataTable{ 

 	} 
 	.dataTables_scrollBody {height:347px!important;}
 	.bottom{width:97%!important;}
/*  	.box-panel + .box-panel{    margin-top: 50px!important;} */
 	.addBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.deleteBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.btn_td{
		width:14mm!important;
		height:6mm!important;
	}
	.selWindow{
		width:280px!important;
	}
	.number{
		text-align:right;
	}
</style>
</head>
<body >
<!--1. 账表模板 模块 -->
<div class="fullscreen" style="padding:10px;height:1600px">
<!-- 	<div class="search" id="searchBox"> -->
<!-- 		<form id="myform" class="form-inline" action="" style="padding-bottom: 2px;padding-top: 8px"> -->
<!--     		<div class="search-simple"> -->
<!-- 				<div class="form-group"> -->
<!-- 					<label>帐表名称</label> -->
<!-- 					<input type="text"  class="form-control input-radius" id="txt_gjc" name="ZBMC"   placeholder="请输入帐表名称"> -->
<!-- 					<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!--         </form> -->
<!-- 	</div> -->
		<div class="box-panel" style="width:50%;display: inline-block;">
			<div class="box-header clearfix">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i> 账表模板
					</div>
				</div>
				<div class="container-fluid" style="position: relative; border-bottom: solid 1px #eee;">
					<div class='responsive-table'>
						<div class='scrollable-area'>
							<table id="mydatatables1" class="table table-striped table-bordered">
								<thead>
									<tr id="header">
										<th>
											<input type="checkbox" id="select-all" />
										</th>
										<th>序号</th>
										<th>帐表名称</th>
										<th>使用模板</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

<!--2. 事前审批维护 -->
<!-- 		<form id="myform_2"> -->
		<div class="box-panel" style="width: 50%;float: right;margin-top: 0px;">
			<div class="box-header clearfix">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i> 事前审批维护
					</div>
					<div class="btn-group pull-right" role="group">
<!-- 		               <button type="button" class="btn btn-primary" id="btn_save2">保存</button> -->
		               <button class="btn btn-default" id="btn_save2">	<i class="fa icon-save"></i>保存</button>
					</div>
				</div>
				<form id="myform_2">
				<div class="container-fluid" style="position: relative; border-bottom: solid 1px #eee;">
					<div class='responsive-table'>
						<div class='scrollable-area'>
							<table id="mydatatables2" class="table table-striped table-bordered">
								<thead>
									<tr>
										<th>
											<input type="checkbox" id="select-all1" />
										</th>
										<th>序号</th>
										<th>事前审批名称</th>
										<th>是否启用</th>
										<th>是否停止新增</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				</form>
			</div>
		</div>
		
<!-- 		</form> -->
<!--3. 会计制度启用设置  模块 -->
							
<!-- 		<div class="box-panel" style="width: 50%; display: inline-block;"> -->
<!-- 			<div class="box-header clearfix"> -->
<!-- 				<div class="box-header clearfix"> -->
<!-- 					<div class="sub-title pull-left text-primary"> -->
<!-- 						<i class="fa icon-xiangxi"></i> 会计制度启用设置 -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="container-fluid" style="position: relative; border-bottom: solid 1px #eee;"> -->
<!-- 					<div class='responsive-table'> -->
<!-- 						<div class='scrollable-area'> -->
<!-- 							<table id="mydatatables3" class="table table-striped table-bordered"> -->
<!-- 								<thead> -->
<!-- 									<tr> -->
<!-- 										<th> -->
<!-- 											<input type="checkbox" class="select-all" /> -->
<!-- 										</th> -->
<!-- 										<th>序号</th> -->
<!-- 										<th>账套名称</th> -->
<!-- 										<th>使用制度</th> -->
<!-- 										<th>操作</th> -->
<!-- 									</tr> -->
<!-- 								</thead> -->
<!-- 								<tbody></tbody> -->
<!-- 							</table> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!--4. 凭证自动化 模块 -->
		<div class="box-panel" style="width: 50%;  margin-top: 50px;display: inline-block">
<!-- 		<div class="box-panel" style="width: 50%; float: right;"> -->
			<div class="box-header clearfix">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i> 凭证自动化维护
					</div>
					<div class="pull-right" style="padding-right: 20px;">
							<button class="btn btn-default" id="btn_save4">	<i class="fa icon-save"></i>保存
							</button>
					</div>
				</div>
				<form id="myform1" class="form-horizontal" action="" method="post">
					<input type="hidden" name="operateType" id="operateType" value="${operateType}">
					<input type="hidden" name="id" value="${bzxx.ID}" />
					<div class="container-fluid dialog-body" style="margin-top: 32px">
						<div class="alert alert-info" style="text-align: center;">
							<strong>提示：双击文本框选择信息</strong>
						</div>
						<div class="row" style="width: 43%;margin: auto;float:left;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">网上报销自动生成凭证</span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="zdscpz" id="zdscpz" class="onoffswitch-checkbox" <c:if test="${pz.zdscpz == 'Y'}">checked</c:if> value="Y" />
											<label class="onoffswitch-label" for="zdscpz"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="width:;margin: auto;float:left;margin-top: 15px!important;">
						<div class="col-md-12">
								<div class="input-group" style="width: 260px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>制单人</span>
									<input type="text" class="form-control input-radius window" id="txt_zdr" name="zdr" value="${pz.zdr }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择制单人" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_zdr" class="btn btn-link">选择</button></span>
								</div>
							</div>
						</div>
						<div class="row" style="width: 43%;margin: auto;float:left;margin-top: 15px!important;">
							<div class="col-md-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">&nbsp;&nbsp;&nbsp;&nbsp;凭证录入自动化&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="zdlrpz" id="zdlrpz" class="onoffswitch-checkbox" <c:if test="${pz.zdlrpz == 'Y' }">checked</c:if> value="Y" />
											<label class="onoffswitch-label" for="zdlrpz"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="width:;margin: auto;float:left;margin-top: 15px!important;">
							<div class="col-md-12">
								<div class="input-group" style="width: 260px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required pzlrzdh" <c:if test="${pz.zdlrpz != 'Y'}"> style="color:red;display: none;"</c:if>>*</span>复核人</span>
									<input type="text" class="form-control input-radius window" id="txt_fhr" name="fhr" value="${pz.fhr }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择复核人" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_fhr" class="btn btn-link">选择</button></span>
								</div>
							</div>
						</div>
						<div class="row" style="width: 43%;float:left;margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">是否允许删除凭证</span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="sfyxscpz" id="sfyxscpz" class="onoffswitch-checkbox" <c:if test="${pz.sfyxscpz == 'Y'}">checked</c:if> value="Y" />
											<label class="onoffswitch-label" for="sfyxscpz"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
							
						</div>

						<div class="row" style="width: ;float:left;margin: auto;margin-top: 15px!important;">
							<div class="col-md-6">
								<div class="input-group" style="width: 260px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required pzlrzdh" <c:if test="${pz.zdlrpz != 'Y'}">style="color:red;display: none;"</c:if>>*</span>记账人</span>
									<input type="text" class="form-control input-radius window" id="txt_jzr" name="jzr" placeholder="选择记账人" value="${pz.jzr }" readonly />
									<span class="input-group-btn"><button type="button" id="btn_jzr" class="btn btn-link">选择</button></span>
								</div>
							</div>
						</div>
						<div class="row" style="width: 43%;float:left;margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">凭证单据是否必传</span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="pzdjsfbc" id="pzdjsfbc" class="onoffswitch-checkbox" <c:if test="${pz.pzdjsfbc == 'Y'}">checked</c:if> value="Y" />
											<label class="onoffswitch-label" for="pzdjsfbc"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
							
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<div class="box-panel" style="width: 50%;float: right;margin-top: 50px;display: inline-block">
<!-- 		<div class="box-panel" style="width: 50%;"> -->
			<div class="box-header clearfix">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i> 交通工具维护
					</div>
					<div class="pull-right" style="padding-right: 20px;">
							<button class="btn btn-default" id="btn_save5">	<i class="fa icon-save"></i>保存
							</button>
					</div>
				</div>
				<form id="myform5" class="" action="" method="post">
					<input type="hidden" name="operateType" id="operateType" value="${operateType}">
					<input type="hidden" name="id" value="${bzxx.ID}" />
					<div class="container-fluid dialog-body" style="margin-top: 32px">
						<div class="alert alert-info" style="text-align: center;">
							<strong>提示：选择交通工具后点击保存生效</strong>
						</div>
						<div class="row" style="width: 43%;margin: auto;float:left;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">公交车</span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="gjc" id="gjc" class="onoffswitch-checkbox" <c:if test="${gjc.ms == '1'}">checked value="1"</c:if> <c:if test="${gjc.ms != '1'}">value="0"</c:if> />
											<label class="onoffswitch-label" for="gjc"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="width: 43%;margin: auto;float:left;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">高铁</span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="gt" id="gt" class="onoffswitch-checkbox" <c:if test="${gt1.ms == '1'}">checked value="1"</c:if> <c:if test="${gt1.ms != '1'}">value="0"</c:if> />
											<label class="onoffswitch-label" for="gt"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="width: 43%;margin: auto;float:left;margin-top: 15px!important;">
							<div class="col-md-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">&nbsp;&nbsp;&nbsp;&nbsp;出租车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="czc" id="czc" class="onoffswitch-checkbox" <c:if test="${czc.ms == '1' }">checked value="1"</c:if> <c:if test="${czc.ms != '1'}">value="0"</c:if> />
											<label class="onoffswitch-label" for="czc"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="width: 43%;margin: auto;float:left;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">轮船 </span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="lc" id="lc" class="onoffswitch-checkbox" <c:if test="${lc.ms == '1'}">checked value="1"</c:if> <c:if test="${lc.ms != '1'}">value="0"</c:if> />
											<label class="onoffswitch-label" for="lc"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="width: 43%;float:left;margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">长途汽车 </span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="ctqc" id="ctqc" class="onoffswitch-checkbox" <c:if test="${ctqc.ms == '1'}">checked value="1"</c:if> <c:if test="${ctqc.ms != '1'}">value="0"</c:if> />
											<label class="onoffswitch-label" for="ctqc"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
							
<!-- 						</div> -->
						</div>

						<div class="row" style="width: 43%;margin: auto;float:left;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">飞机 </span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="fj" id="fj" class="onoffswitch-checkbox" <c:if test="${fj.ms == '1'}">checked value="1"</c:if> <c:if test="${fj.ms != '1'}">value="0"</c:if> />
											<label class="onoffswitch-label" for="fj"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="width: 43%;float:left;margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">火车</span>
									<div class="switch">
										<div class="onoffswitch">
											<input type="checkbox" name="hc" id="hc" class="onoffswitch-checkbox" <c:if test="${hc.ms == '1'}">checked value="1"</c:if> <c:if test="${hc.ms != '1'}">value="0"</c:if> />
											<label class="onoffswitch-label" for="hc"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
							
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<div class="box-panel" style="width: 50%;margin-top: 50px;display: inline-block">
			<div class="box-header clearfix">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i> 经济科目维护
					</div>
					<div class="pull-right" style="padding-right: 20px;">
						<button class="btn btn-default" id="btn_save6">	<i class="fa icon-save"></i>保存</button>
					</div>
				</div>
				<form id="myform6" class="" action="" method="post">
					<input type="hidden" name="operateType" id="operateType" value="${operateType}">
					<input type="hidden" name="id" value="${bzxx.ID}" />
					<div class="container-fluid dialog-body" style="margin-top: 32px">
						<div class="alert alert-info" style="text-align: center;">
							<strong>提示：选择科目费用后点击保存生效</strong>
						</div>
					</div>
				<table id="mydatatables" class="table table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align:center;">操作</th>
				        	<th style="text-align:center;"><span class="required" style="color:red ;"></span>费用编号</th>	
				        	<th style="text-align:center;"><span class="required" style="color:red ;"></span>费用名称</th>				       
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				    <c:choose>
                        <c:when test="${not empty fkuc}">
  							<c:forEach var="xmbqmx" items="${fkuc}"> 
						    	<tr id="tr_1">
						    		<td class="btn_td">
						    			<div class="deleteBtn" ></div>
			                        </td>
						    		<td>
						    			<input type="text" id="txt_fybh" class="bk input-radius window null" name="fybh" value="${xmbqmx.kmbh }" readonly>
						    		</td>
						    		<td>
						    			 <input type="text" id="txt_fymc" class="bk null" name="fymc" value="${xmbqmx.kmmc}" readonly>
						    		     <input type="hidden" name="end" value="end">
						    		</td>
						    	</tr>
				    		</c:forEach>
				    		<tr id="tr_1" class="last">
					    		<td class="btn_td"><div class="addBtn add" ></div></td>
					    		<td>
					    			<input type="text" id="txt_fybh1" class="form-control input-radius window" name="fybh" value="" readonly>
					    		</td>
					    		<td>
					    			<input type="text" id="txt_fymc1" class="form-control input-radius window" name="fymc" value="" readonly>
					    			<input type="hidden" name="end" value="end">
					    		</td>	
					    	</tr>
                        </c:when>
                        <c:otherwise>
						<tr id="tr_1" class="last">
				    		<td class="btn_td"><div class="addBtn add" ></div></td>
				    		<td>
				    			<input type="text" id="txt_fybh1" class="form-control input-radius window" name="fybh" value="" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_fymc1" class="form-control input-radius window" name="fymc" value="" readonly>
				    			<input type="hidden" name="end" value="end">
				    		</td>				
				    	</tr>
                        </c:otherwise>
                    </c:choose>
			    </tbody>
				</table>
				<br><br>
				</form>
			</div>
			</div>
			
		   <div class="box-panel" style="width: 50%;float: right;margin-top: 50px;display: inline-block">
<!-- 		<div class="box-panel" style="width: 50%;"> -->
			<div class="box-header clearfix">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i> 凭证设置
					</div>
					<div class="pull-right" style="padding-right: 20px;">
							<button class="btn btn-default" id="btn_save7">	<i class="fa icon-save"></i>保存
							</button>
					</div>
				</div>
				<form id="myform7" class="" action="" method="post">
					<div class="container-fluid dialog-body" style="margin-top: 32px">
						<div class="alert alert-info" style="text-align: center;">
							<strong>提示：选择后点击保存生效</strong>
						</div>
						<div class="row" style="width: 43%;float:left;margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group">
									<span class="input-group-addon" style="min-width: 150px!important;">是否补充前面删除的凭证号</span>
									<div class="switch">
									   <div class="onoffswitch">
											<input type="checkbox" name="zdzj" id="zdzj" class="onoffswitch-checkbox" <c:if test="${pzsz.ms == '1'}">checked value="1"</c:if> <c:if test="${pzsz.ms != '1'}">value="0"</c:if> />
											<label class="onoffswitch-label" for="zdzj"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
											</label>
										</div>	
									</div>
								</div>
							</div>
					    </div>
					    <!-- 借款科目 -->
					    <div class="row" style="margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>借款科目设置</span>
									<input type="hidden" class="form-control" id="txt_jkkmbh" name="jkkmbh" value="${jkkm.kmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_jkkmmc" name="jkkmmc" value="${jkkm.kmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择借款科目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_jkkm" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
<!-- 					    学校横向科目（借）设置 -->
					    <div class="row" style="margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>学校横向科目（借）设置</span>
									<input type="hidden" class="form-control" id="txt_xxjjhj" name="xxjjhj" value="${xxjjhj.kmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_xxjjhjkmmc" name="jkkmmc" value="${xxjjhj.kmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择会计科目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_xxjjhj" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
<!-- 					    学校横向科目（贷）设置 -->
					    <div class="row" style="margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>学校横向科目（贷）设置</span>
									<input type="hidden" class="form-control" id="txt_xxjjhd" name="xxjjhd" value="${xxjjhd.kmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_xxjjhdkmmc" name="jkkmmc" value="${xxjjhd.kmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择会计科目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_xxjjhd" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
<!-- 					    学校纵向科目（借）设置 -->
					    <div class="row" style="margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>学校纵向科目（借）设置</span>
									<input type="hidden" class="form-control" id="txt_xxjjzj" name="xxjjzj" value="${xxjjzj.kmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_xxjjzjkmmc" name="jkkmmc" value="${xxjjzj.kmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择会计科目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_xxjjzj" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
<!-- 					    学校纵向科目（贷）设置 -->
					    <div class="row" style="margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>学校纵向科目（贷）设置</span>
									<input type="hidden" class="form-control" id="txt_xxjjzd" name="xxjjzd" value="${xxjjzd.kmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_xxjjzdkmmc" name="jkkmmc" value="${xxjjzd.kmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择会计科目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_xxjjzd" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
<!-- 					    学院科目（借）设置 -->
					    <div class="row" style="margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>学院科目（借）设置</span>
									<input type="hidden" class="form-control" id="txt_xyjjj" name="xyjjj" value="${xyjjj.kmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_xyjjjkmmc" name="jkkmmc" value="${xyjjj.kmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择会计科目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_xyjjj" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
<!-- 					    学院科目（贷）设置 -->
					    <div class="row" style="margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>学院科目（贷）设置</span>
									<input type="hidden" class="form-control" id="txt_xyjjd" name="xyjjd" value="${xyjjd.kmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_xyjjdkmmc" name="jkkmmc" value="${xyjjd.kmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择会计科目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_xyjjd" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
					    <div class="row" style="margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>管理费经济科目设置</span>
									<input type="hidden" class="form-control" id="txt_glf" name="glf" value="${glf.kmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_glfkmmc" name="jkkmmc" value="${glf.kmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择经济科目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_glf" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
<!-- 					    学校管理收入项目设置 -->
					    <div class="row" style="margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>学校管理收入项目设置</span>
									<input type="hidden" class="form-control" id="txt_xxxm" name="xxxm" value="${xxxm.xmbh}" >
									<input type="hidden" class="form-control" id="txt_xxjjbmbh" name="xxxmbmbh" value="${xxxm.bmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_xxxmmc" name="jkkmmc" value="${xxxm.xmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择经济科目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_xxxm" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
<!-- 					    学院管理收入项目设置 -->
					    <div class="row" style="margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>学院管理收入项目设置</span>
									<input type="hidden" class="form-control" id="txt_xyxm" name="xyxm" value="${xyxm.xmbh}" >
									<input type="hidden" class="form-control" id="txt_xyjjbmbh" name="xyxmbmbh" value="${xyxm.bmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_xyxmmc" name="jkkmmc" value="${xyxm.xmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择经济科目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_xyxm" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
					    <!-- 薪资项目设置 -->
					    <div class="row" style="margin: auto;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 600px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>薪资项目设置</span>
									<input type="hidden" class="form-control" id="txt_xmbh" name="xmbh" value="${xzxm.xmbh}" >
									<input type="hidden" class="form-control" id="txt_bmbh" name="bmbh" value="${xzxm.bmbh}" >
									<input type="text" class="form-control input-radius window" id="txt_xzxmmc" name="xzxmmc" value="${xzxm.bmbhmc }-${xzxm.xmmc }"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;" placeholder="请选择薪资项目" readonly="readonly" />
									<span class="input-group-btn"><button type="button" id="btn_xzxm" class="btn btn-link">选择</button></span>
								</div>
							</div>	
					    </div>
					</div>
				</form>
				
			</div>	
			
		</div>
		
		
		<div class="box-panel" style="width:50%;float:left;margin-top:50px;display: inline-block">
			<div class="box-header clearfix">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i> 转账信息设置
					</div>
					<div class="pull-right" style="padding-right: 20px;">
							<button class="btn btn-default" id="btn_save8">	<i class="fa icon-save"></i>保存
							</button>
					</div>
				</div>
				<form id="myform8" class="" action="" method="post">
					<input type="hidden" name="operateType" id="operateType" value="${operateType}">
					<input type="hidden" name="id" value="${bzxx.ID}" />
					<div class="container-fluid dialog-body" style="margin-top: 32px">
						<div class="alert alert-info" style="text-align: center;">
							<strong>提示：输入后点击保存生效</strong>
						</div>
						<div class="row" style="width: 43%;float:left;margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 260px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>转账摘要</span>
									<input type="text" class="form-control input-radius window" id="txt_zzzy" name="zzzy" value="${zzzy.ms}"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"  />
								</div>
							</div>
					  </div>
					</div>
				</form>
				
			</div>	
		</div>
		
		
		<div class="box-panel" style="width:50%;float:left;margin-top:50px;display: inline-block">
			<div class="box-header clearfix">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i>报销经费信息设置
					</div>
					<div class="pull-right" style="padding-right: 20px;">
							<button class="btn btn-default" id="btn_save99">	<i class="fa icon-save"></i>保存
							</button>
					</div>
				</div>
				<form id="myform99" class="" action="" method="post">
				<table id="mydatatables" class="table table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align:center;">报销项目类型</th>
				        	<th style="text-align:center;">金额一</th>	
				        	<th style="text-align:center;">金额二</th>	
				        	<th style="text-align:center;">金额三</th>				       
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				    	<tr>
					    	<td style="text-align:center;">专项经费</td>
			        		<td style="text-align:center;" title="大于该配置项金额的流程需要校长审核">
					    		<input type="text" id="zy_je1" class="form-control input-radius number" name="zyje1" value="${bxlx.zyje1}">
			        		</td>	
			        		<td style="text-align:center;"></td>	
			        		<td style="text-align:center;"></td>		
		        		</tr>
		        		<tr>
					    	<td style="text-align:center;">部门业务费</td>
			        		<td style="text-align:center;" title="大于该配置项金额的流程需要领导会签审核，小于该金额需要报账员选择审核人">
			        			<input type="text" id="bm_je1" class="form-control input-radius number" name="bmje1" value="${bxlx.bmje1}">
			        		</td>	
			        		<td style="text-align:center;"></td>	
			        		<td style="text-align:center;"></td>		
		        		</tr>
		        		<tr>
					    	<td style="text-align:center;">科研经费</td>
			        		<td style="text-align:center;" title="小于该配置金额的流程审核到部门负责人">
			        			<input type="text" id="ky_je1" class="form-control input-radius number" name="kyje1" value="${bxlx.kyje1}">
			        		</td>	
			        		<td style="text-align:center;" title="小于该配置金额的流程审核到科技分管领导">
			        			<input type="text" id="ky_je2" class="form-control input-radius number" name="kyje2" value="${bxlx.kyje2}">
			        		</td>	
			        		<td style="text-align:center;" title="大于于该配置金额的流程审核到校长">
			        			<input type="text" id="ky_je3" class="form-control input-radius number" name="kyje3" value="${bxlx.kyje3}">
			        		</td>		
		        		</tr>
			        </tbody>
				</table>
				</form>
				
			</div>	
		</div>
<!--打印维护 -->
		<div class="box-panel" style="width:50%;float:left;margin-top:50px;display: inline-block">
			<div class="box-header clearfix">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i> 打印参数设置
					</div>
					<div class="pull-right" style="padding-right: 20px;">
							<button class="btn btn-default" id="btn_saveDy"><i class="fa icon-save"></i>保存
							</button>
					</div>
				</div>
				<form id="myformDy" class="" action="" method="post">
					<div class="container-fluid dialog-body" style="margin-top: 32px">
						<div class="alert alert-info" style="text-align: center;">
							<strong>提示：输入后点击保存生效</strong>
						</div>
						<c:forEach items="${dyxx}" var="dycsxx">
						<div class="row" style="width: 43%;float:left;margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 260px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>${dycsxx.MS}:</span>
									<input type="text" class="form-control input-radius window text-right" id="txt_dycsmc" name="dycsmc" value="${dycsxx.MC}"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"  />
										<input type="hidden" name="dycsdm" value="${dycsxx.DM}">
								</div>
							</div>
					  </div>
					</c:forEach>
					</div>
				</form>
				
			</div>	
		</div>
		
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script  src="${ctx}/webView/zdscpz/bootstrap-switch.min.js"></script>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js" type="text/javascript"></script>
<script>
//1.账表 模块
var target = "${ctx}/webView/mbxz";
$(function() {
	//列表数据
	var columns1 = [
		{"data": "GID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyIdzb" name="gid" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"searchable": false,"class":"text-center","width":40},
		{"data": "ZBMC",defaultContent:"","width":160},
		{"data": "MBMC",defaultContent:"", "render": function (data, type, full, meta){
			if(data=="模板一"){
				return '<select name="mbmc" style="width:183px" ><option value="1" selected >模板一</option><option value="2" >模板二</option> </select>';
			}else if (data=="模板二"){
				return '<select name="mbmc" style="width:183px" ><option value="1" >模板一</option><option value="2" selected>模板二</option> </select>';
			}
	       	return '<select name="mbmc" style="width:183px"><option value="1" >模板一</option><option value="2" >模板二</option> </select>';
	    },"width":40},
	    {"data":"",'render':function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_save1" guid="' + data + '" >保存</a>|<a href="javascript:void(0);" class="btn btn-link btn_look1" xz="'+full.ZBMC+'" xz2="'+full.MBMC+'" guid="' + data + '" >预览</a>';
	      },orderable:false,"width":220,"class":"text-center","width":100}
	];
   	table1 = getDataTableByListHj("mydatatables1","${pageContext.request.contextPath}/mbxz/getPageList",[2,'asc'],columns1,0,1,setGroup,'','',9);
   	
    //账表模板预览
	$(document).on("click",".btn_look1",function(){
		var xz = $(this).attr("xz");
		var xz2 = $(this).parents("tr").find("[name='mbmc']").val();
		console.log(xz2);
		if(xz=="（旧）收入费用表（年度）" && xz2=="1"){
			window.location.href = target+"/srfybyear.jsp";
		}
		if(xz=="（旧）收入费用表（月度）" && xz2=="1"){
			window.location.href = target+"/srfybmonth.jsp";
		}
		if(xz=="净资产变动表"&& xz2=="2"){
			window.location.href = target+"/jzcbdb.jsp";
		}
		if(xz=="旧资产负债表（年度）"&& xz2=="1"){
			window.location.href = target+"/jzcfzbyear.jsp";
		}
		if(xz=="旧资产负债表（月度）"&& xz2=="1"){
			window.location.href = target+"/jzcfzbmonth.jsp";
		}
		if(xz=="科目余额表"&& xz2=="1"){
			window.location.href = target+"/kmyeb.jsp";
		}
		if(xz=="明细帐"&& xz2=="2"){
			window.location.href = target+"/mxz.jsp";
		}
		if(xz=="收入费用表（年度）"&& xz2=="1"){
			window.location.href = target+"/srfybyear.jsp";
		}
		if(xz=="收入费用表（月度）"&& xz2=="1"){
			window.location.href = target+"/srfybmonth.jsp";
		}
		if(xz=="现金流量表"&& xz2=="1"){
			window.location.href = target+"/xjllb.jsp";
		}
		if(xz=="资产负债表（年度）"&& xz2=="1"){
			window.location.href = target+"/zcfzbyear.jsp";
		}
		if(xz=="资产负债表（月度）"&& xz2=="1"){
			window.location.href = target+"/zcfzbmonth.jsp";
		}
		if(xz=="总账"&& xz2=="1"){
			window.location.href = target+"/zz.jsp";
		}
	});
    //账表模板保存
	$(document).on("click",".btn_save1",function(){
		var mb = $(this).parents("tr").find("[name='mbmc']").val();
		var gid = $(this).parents("tr").find("[name='gid']").val();
		confirm("确定使用模板？",{title:"提示信息"},function(){
			$.ajax({
				url:"${pageContext.request.contextPath}/mbxz/doSave",
				data:"mb="+mb+"&gid="+gid,
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.success){
						alert("操作成功！");
					}else{
						alert("操作失败！");
					}
				}
			});
			table1.ajax.reload();
		});
   	});
});
//全选
$(document).on("click","[id=select-all]",function(){
	if($(this).prop("checked")){
		$(":checkbox.keyIdzb").prop("checked", true);
		$(":checkbox.keyIdzb").parents("tr").addClass('selected');
	}else{
		$(":checkbox.keyIdzb").prop("checked", false);
		$(":checkbox.keyIdzb").parents("tr").removeClass('selected');
	}
	//类似于资产闲置申请中选择资产的时候，右下角有个蓝色小圆圈，选中列表上的复选框的时候，要把该资产放到蓝色小圆圈里，
	//所以需要执行一下复选框的change事件，这个事件在页面上，如果没有change事件，写在这儿也不受影响
	$(":checkbox.keyId").change();
});
//全选
$(document).on("click","[id=select-all1]",function(){
	if($(this).prop("checked")){
		$(":checkbox.keyId").prop("checked", true);
		$(":checkbox.keyId").parents("tr").addClass('selected');
	}else{
		$(":checkbox.keyId").prop("checked", false);
		$(":checkbox.keyId").parents("tr").removeClass('selected');
	}
	//类似于资产闲置申请中选择资产的时候，右下角有个蓝色小圆圈，选中列表上的复选框的时候，要把该资产放到蓝色小圆圈里，
	//所以需要执行一下复选框的change事件，这个事件在页面上，如果没有change事件，写在这儿也不受影响
	$(":checkbox.keyId").change();
});
//2. 事前审批维护
var target2 = "${ctx}/sqspwh";
//加载列表数据
   var columns2 = [
      {"data": "MKBH",orderable:false, 'render': function (data, type, full, meta){
   	   return '<input type="checkbox" name="guid" class="keyId" xmguid="'+full.XMGUID+'" value="' + data + '">'
   	   			+'<input type="hidden" name="mkbh" value="'+data+'">';
      },"width":10,'searchable': false},
      {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
         	return data;},"searchable":false,"class":"text-center"},
      {"data": "MKMC",defaultContent:""},
      {"data": "SFQY",defaultContent:"","orderable":false,'render':function(data,type,full){
   	   var sfqy = "";
   	   var xh = full._XH;
   	   //默认为启用
   	   if(data == null||data == "1"){
   		 	sfqy = "checked";
   	   }
   	   return  '<div class="switch">'+
				'<div class="onoffswitch">'+
				'<input type="checkbox" name="sfqy'+xh+'" id="sfqy'+xh+'" class="onoffswitch-checkbox" '+sfqy+' data-sqspmc="'+full.MKMC+'" value="1"/>'+
					 '<label class="onoffswitch-label" for="sfqy'+xh+'">'+
	               		'<span class="onoffswitch-inner"></span>'+
	               		'<span class="onoffswitch-switch"></span>'+
	               	 '</label>'+
				'</div>'+
	            '</div>';
      }},
      {"data": "SFTZXZ",defaultContent:"","orderable":false,'render':function(data,type,full){
   	   var sftzxz = "";
   	   var xh = full._XH;
   	   if(data == "1"){
   		   sftzxz = "checked";
   	   }
   	   return  '<div class="switch">'+
				'<div class="onoffswitch">'+
				'<input type="checkbox" name="sftzxz'+xh+'" id="sftzxz'+xh+'" class="onoffswitch-checkbox" '+sftzxz+' data-sqspmc="'+full.MKMC+'" value="1"/>'+
					 '<label class="onoffswitch-label" for="sftzxz'+xh+'">'+
	               		'<span class="onoffswitch-inner"></span>'+
	               		'<span class="onoffswitch-switch"></span>'+
	               	 '</label>'+
				'</div>'+
	            '</div>'+
	            '<input type="hidden" name="end" />';
      }}
    ];
   table2 = getDataTableByListHj("mydatatables2",target2+"/getListData",[2,'desc'],columns2,0,1,setGroup,'','',9);
   
//查询是否有未审批完成的单据   
function querySqspSfsy(sqspmc){
	var tag = false;
	if(isNull(sqspmc)){
		return tag;
	}
	var type = "";
	switch (sqspmc) {
	case "出差业务":
		type = "cc";
		break;
	case "公务接待业务":
		type = "gwjd";
		break;
	default:
		break;
	}
	var url_ = target2+"/getSqspSfsy";
	$.ajax({
		type:"post",
		url:url_,
		data:"type="+type,
		dataType:"json",
		async:false,
		success:function(data){
			if(data.success){
				tag = true;
			}
		},
		error:function(){
			alert("抱歉，系统出现错误！");
		}
	});
	return tag;
}   
$(function () {
	//当禁用模块时
	$(document).on("click","[name^=sfqy]:not(:checked)",function(){
		var $sftzxz = $(this).parents("tr").find("[name^=sftzxz]:not(:checked)");
		var $sfqy = $(this).parents("tr").find("[name^='sfqy']:not(:checked)");
		var tag = $sftzxz.length > 0;
		var content = tag ? "是否停止新增？" : "";
		var sqspmc = $(this).data("sqspmc");
		if(querySqspSfsy(sqspmc)){
			confirm("该事前审批模块正在使用中，无法禁用！"+content,"W",{title:"提示"},function(){
				$("a.layui-layer-btn1",top.document)[0].click();
				$sfqy.click();
				if(tag){
					$sftzxz.click();
				}
			});
		}
	});
	
	//事前审批维护保存
	$("#btn_save2").click(function(){
		var json = $("#myform_2").serializeObject2("mkbh","end","^sfqy<>^sftzxz","sfqy<>sftzxz");
		var jsonStr = JSON.stringify(json);
		$.ajax({
			type:"post",
			data:"json="+jsonStr,
			url:target2+"/doSave",
			dataType:"json",
			success:function(data){
				if(data.success){
					alert("保存成功！");
					location.reload();
				}else{
					alert("保存失败，请稍后重试！");
				}
			},
			error:function(){
				alert("抱歉，系统出现异常！");
			}
		});
	});
  //列表右侧悬浮按钮
// 	$(window).resize(function(){
//     	$("div.dataTables_wrapper").width($("#searchBox").width());
//     	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
//     	$(".dataTables_scrollBody").height(heights);
//     	table.draw();
// 	});
});
//3. 会计制度启用设置
// $(function() {
// 	//列表数据
// 	var columns3 = [
// 		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
// 	       	return '<input type="checkbox" class="keyId" name="guid" value="' + data + '">';
// 	    },"width":5,'searchable': false,"class":"text-center"},
// 		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
// 	   		return data;
// 		},"searchable": false,"class":"text-center","width":40},
// 		{"data": "ZTMC",defaultContent:"","width":160},
// 		{"data": "ZDM",defaultContent:"", "render": function (data, type, full, meta){
// 			return '<select name="zdm" class="zdm '+full.GUID+'"  style="width:183px">'+
// 			<c:forEach var="l" items="${list}">
// 			'<option class="zdm3" value="${l.guid}" >${l.zdm}</option>'+
// 			</c:forEach>
// 			'</select>';
// 	    },"width":40},
// 	   	{"data":function (data, type, full, meta){
// 	   		return '<a class="btn btn-link btn_save3">保存</a>';
// 		},orderable:false,"width":50,"class":"text-center"}
// 	];
//    	table3 = getDataTableByListHj("mydatatables3","${pageContext.request.contextPath}/kjzdqy/getPageList",
//    			[3,'asc'],columns3,0,1,setGroup,'','',9,function(p1){
//    			$(p1.data).each(function(i,ele){
//    				$('.zdm.'+ele.GUID+' option').each(function(i,e){
//    					if(e.value == ele.ZD){
//    						$(e).prop('selected',true)
//    					}
//    				})
//    			})
//    		});
// $(document).on("click",".btn_save3",function(){
// 		var zdm = $(this).parents("tr").find("[name='zdm']").val();
// 		var guid = $(this).parents("tr").find("[name='guid']").val();
// 		alert(zdm)
// 		confirm("确定使用模板？",{title:"提示信息"},function(){
// 			$.ajax({
// 				url:"${pageContext.request.contextPath}/kjzdqy/doSave",
// 				data:"zdm="+zdm+"&guid="+guid,
// 				type:"post",
// 				dataType:"json",
// 				success:function(data){
// 					if(data.success){
// 						alert("操作成功！");
// 					}else{
// 						alert("操作失败！");
// 					}
// 				}
// 			});
// 			table.ajax.reload();
// 		});
//    	});
// });
$(function(){
	//4.凭证自动化 模块
	var fields;
	var validator;
	$("#zdlrpz").change(function(){
	     if($('#zdlrpz').is(':checked')) {
			 $(".pzlrzdh").removeAttr("style");
			 console.log("1111111111111111111111");
				fields = {fields:{
				    zdr:{validators:{notEmpty: {message: ' '}}},
		 		    fhr:{validators:{notEmpty: {message: ' '}}},
		 		    jzr:{validators:{notEmpty: {message: ' '}}}
				    }
				};
		 }else{
			 $(".pzlrzdh").attr("style","color:red;display: none;");
			 console.log("22222222222222222222222222");
				fields = {fields:{
				    zdr:{validators:{notEmpty: {message: ' '}}}
				    }
				};
		 }
	validator = $('#myform1').bootstrapValidator(fields);
	});
// 	if($('#zdlrpz').is(':checked')) {
		
// 	}else{
		
// 	}
			
	//选择按钮		
		$(document).on("click","#btn_zdr",function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_zdr","制单人","1000","650");
		});
		$(document).on("click","#btn_fhr",function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_fhr","复核人","1000","650");
		});
		$(document).on("click","#btn_jzr",function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_jzr","记账人","1000","650");
		});
		//页面验证
		
		function checkZdFh(){
			var tag = true;
			var zdr = $("#txt_zdr").val();
			var fhr = $("#txt_fhr").val();
			if(zdr == fhr){
				tag = false;
			}
			return tag;
		}
		$("#btn_save4").click(function() {
			if(!checkZdFh()){
				alert("制单人，复核人不能相同！");
				return;
			};
			doSave(validator, "myform1","${pageContext.request.contextPath}/zdscpz/doSave",	function(val) {
							}, function() {
			});
		});
		//onoff按扭切换
		$("#gjc").click(function(){//公交车
			if($("[name='gjc']").val()=='0'){
				$("[name='gjc']").val("1");
			}else if($("[name='gjc']").val()=='1'){
				$("[name='gjc']").val("0");
			}else{
				$("[name='gjc']").val("1");
			}
		});
		$("#czc").click(function(){//出租车
			if($("[name='czc']").val()=='0'){
				$("[name='czc']").val("1");
			}else if($("[name='czc']").val()=='1'){
				$("[name='czc']").val("0");
			}else{
				$("[name='czc']").val("1");
			}
		});
		$("#ctqc").click(function(){//长途汽车
			if($("[name='ctqc']").val()=='0'){
				$("[name='ctqc']").val("1");
			}else if($("[name='ctqc']").val()=='1'){
				$("[name='ctqc']").val("0");
			}else{
				$("[name='ctqc']").val("1");
			}
		});
		$("#hc").click(function(){//火车
			if($("[name='hc']").val()=='0'){
				$("[name='hc']").val("1");
			}else if($("[name='hc']").val()=='1'){
				$("[name='hc']").val("0");
			}else{
				$("[name='hc']").val("1");
			}
		});
		$("#gt").click(function(){//高铁
			if($("[name='gt']").val()=='0'){
				$("[name='gt']").val("1");
			}else if($("[name='gt']").val()=='1'){
				$("[name='gt']").val("0");
			}else{
				$("[name='gt']").val("1");
			}
		});
		$("#lc").click(function(){//轮船
			if($("[name='lc']").val()=='0'){
				$("[name='lc']").val("1");
			}else if($("[name='lc']").val()=='1'){
				$("[name='lc']").val("0");
			}else{
				$("[name='lc']").val("1");
			}
		});
		$("#fj").click(function(){//飞机
			if($("[name='fj']").val()=='0'){
				$("[name='fj']").val("1");
			}else if($("[name='fj']").val()=='1'){
				$("[name='fj']").val("0");
			}else{
				$("[name='fj']").val("1");
			}
		});
		$("#btn_save5").click(function() {
			var gjc = $("[name='gjc']").val();
			var czc = $("[name='czc']").val();
			var ctqc = $("[name='ctqc']").val();
			var hc = $("[name='hc']").val();
			var gt = $("[name='gt']").val();
			var lc = $("[name='lc']").val();
			var fj = $("[name='fj']").val();
			console.log("____________"+gjc+czc+ctqc+hc+gt+lc+fj)
			doSave(validator, "myform5","${pageContext.request.contextPath}/zdscpz/doSave5?a1="+gjc+"&a2="+czc+"&a3="+ctqc+"&a4="+hc+"&a5="+gt+"&a6="+lc+"&a7="+fj,	function(val) {
							}, function() {
			});
		});
		
		//保存按钮
		$("#btn_save6").click(function(){
			
			next();
		});
		
		function next(){
			$("tr:last").remove();
		 	var json = $('#myform6').serializeObject1("fybh","end");  //json对象				
			var jsonStr = JSON.stringify(json);  //json字符串
			console.log("____________"+jsonStr);
			$.ajax({
	   			url:"${ctx}/mbxz/doSaveMxb",
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				alert("保存成功");  
				location.reload();
	   			}
			}); 
		}
		$(document).on("click","[id^='btn_fymc']",function(){
			if($(this).hasClass("non-edit")){
				return;
			}
			var basePath = getBasePath();
			var id = $(this).parents("td").find("[id^=txt_fymc]").attr("id");
			var fyid = $(this).parents("td").find("[id^=txt_fyid]").attr("id");
			select_commonWin("${ctx}/kjhs/pzxx/pzlr/pageSkip?pageName=jjkmTree&controlId="+id+"&fyid="+fyid,"经济科目信息","1000","650");
		});
		$(document).on("click",".addBtn",function(){
			select_commonWin("${ctx}/kjhs/pzxx/pzlr/pageSkip?pageName=jjkmTreedx&controlId=txt_fymc1&fyid=txt_fybh1","经济科目信息","1000","650");

// 			select_commonWin("${ctx}/xmbq/xmxx","项目信息","920","630");
		});
		//删除按钮
		$(document).on("click","[class=deleteBtn]",function(){
			$(this).parents("tr").remove();
		});
	 	$(".deleteBtn").click(function(){
	 		$(this).parents("tr").remove();
	 	});
	});
function addxmxx(xmid,kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var $parentTr = $(".last").clone();
		$parentTr.removeClass("last");
		$parentTr.find("input:not(#txt_zbid)").attr("value","");
		$parentTr.find("[id=txt_xmid]").val(xmid[i]);
		$parentTr.find("[id=txt_fybh]").val(kmbh[i]);
		$parentTr.find("[id=txt_fymc]").val(kmmc[i]);
		$(".last").before($parentTr);
	}
	$(".addBtn").attr("class","deleteBtn");
	$(".deleteBtn:last").attr("class","addBtn");
	$("input").removeClass("border");
	
}
$.fn.serializeObject1 = function(start,end){
 	
 	var f = {"list":[]};
	    var a = this.serializeArray();
	    var o = {};
	    $.each(a, function() {
	    	if (this.name == start) {
	        	o = {};
	        	o[this.name] = this.value;
	        } else if(this.name == end){
	        	o[this.name] = this.value;
	        	f["list"].push(o);
	        }else if(this.name.indexOf("sfjk")>=0){
               o.sfjk=this.value;
               this.name=undefined;
	        }else{
	        	o[this.name] = this.value;
	        }
 });
 return f;
}; 
//凭证删除配置保存
//onoff按扭切换
$("#zdzj").click(function(){
	if($("[name='zdzj']").val()=='0'){
		$("[name='zdzj']").val("1");
	}else if($("[name='zdzj']").val()=='1'){
		$("[name='zdzj']").val("0");
	}else{
		$("[name='zdzj']").val("1");
	}
});

$("#btn_save7").click(function(){
	var ms = $("#zdzj").val();
	var kmbh = 	$("#txt_jkkmbh").val();
	var xmbh = 	$("#txt_xmbh").val();
	var bmbh = 	$("#txt_bmbh").val();
	var xxjjhj = $("#txt_xxjjhj").val();
	var xxjjhd = $("#txt_xxjjhd").val();
	var xxjjzj = $("#txt_xxjjzj").val();
	var xxjjzd = $("#txt_xxjjzd").val();
	var xyjjj = $("#txt_xyjjj").val();
	var xyjjd = $("#txt_xyjjd").val();
	var glf = $("#txt_glf").val();
	var xxxm = $("#txt_xxxm").val();
	var xxxmbmbh = $("#txt_xxjjbmbh").val();
	var xyxm = $("#txt_xyxm").val();
	var xyxmbmbh = $("#txt_xyjjbmbh").val();
	$.ajax({
			url:"${ctx}/mbxz/doSavePzsc",
			type:"post",
			data:"ms="+ms+"&kmbh="+kmbh+"&xmbh="+xmbh+"&bmbh="+bmbh+"&xxjjhj="+xxjjhj+"&xxjjhd="+xxjjhd+"&xxjjzj="+xxjjzj+"&xxjjzd="+xxjjzd+"&xyjjj="+xyjjj+"&xyjjd="+xyjjd+"&glf="+glf+"&xxxm="+xxxm+"&xyxm="+xyxm+"&xxxmbmbh="+xxxmbmbh+"&xyxmbmbh="+xyxmbmbh,
			success:function(data){
				alert("保存成功");  
				location.reload();
			}
	}); 
});
$("#btn_save8").click(function(){
	var ms = $("#txt_zzzy").val();	
	$.ajax({
			url:"${ctx}/mbxz/doSaveZzzy",
			type:"post",
			data:"ms="+ms,
			success:function(data){
				alert("保存成功");  
				location.reload();
			}
	}); 
	
});
//打印信息保存
$("#btn_saveDy").click(function(){
	var json = $('#myformDy').serializeObject("dycsmc","dycsdm");  //json对象				
	var jsonStr = JSON.stringify(json);
	console.log(jsonStr);
	$.ajax({
			url:"${ctx}/mbxz/doSaveDyxx",
			type:"post",
			data:"json="+jsonStr,
			success:function(data){
				alert("保存成功");  
				location.reload();
			}
	}); 
	
});
$(document).on("click","[id='btn_save99']",function(){
	var kyje1 = $("[name='kyje1']").val();
	var kyje2 = $("[name='kyje2']").val();
	var kyje3 = $("[name='kyje3']").val();
	if(kyje1==null||kyje1==""||kyje1=="undefined"||isNaN(kyje1)) kyje1=0;
	if(kyje2==null||kyje2==""||kyje2=="undefined"||isNaN(kyje2)) kyje2=0;
	if(kyje3==null||kyje3==""||kyje3=="undefined"||isNaN(kyje3)) kyje3=0;
	if(Number(kyje3)>Number(kyje2)&&Number(kyje2)>Number(kyje1)){
		var json = $('#myform99').serializeObject1("zxje1","kyje3");  //json对象				
		var jsonStr = JSON.stringify(json);  //json字符串
		$.ajax({
	  		url:"${ctx}/mbxz/doSaveBxlx",
	  		type:"post",
	  		data:"json="+jsonStr,
	  		success:function(data){
				alert("保存成功");  
				location.reload();
	 		}
		}); 
	}else{
		alert("科研类报销金额设置不规范！");
	}
});
//借款科目设置  选择
$(document).on("click","#btn_jkkm",function(){
	select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId=txt_jkkmbh&controlId1=txt_jkkmmc","会计科目控制","920","630");
});
//学校横向科目（借）设置  选择
$(document).on("click","#btn_xxjjhj",function(){
	select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId=txt_xxjjhj&controlId1=txt_xxjjhjkmmc","会计科目控制","920","630");
});
//学校横向科目（贷）设置 选择
$(document).on("click","#btn_xxjjhd",function(){
	select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId=txt_xxjjhd&controlId1=txt_xxjjhdkmmc","会计科目控制","920","630");
});
//学校纵向科目（借）设置  选择
$(document).on("click","#btn_xxjjzj",function(){
	select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId=txt_xxjjzj&controlId1=txt_xxjjzjkmmc","会计科目控制","920","630");
});
//学校纵向科目（贷）设置 选择
$(document).on("click","#btn_xxjjzd",function(){
	select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId=txt_xxjjzd&controlId1=txt_xxjjzdkmmc","会计科目控制","920","630");
});
//学院科目（借）设置  选择
$(document).on("click","#btn_xyjjj",function(){
	select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId=txt_xyjjj&controlId1=txt_xyjjjkmmc","会计科目控制","920","630");
});
//学院科目（贷）设置 选择
$(document).on("click","#btn_xyjjd",function(){
	select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId=txt_xyjjd&controlId1=txt_xyjjdkmmc","会计科目控制","920","630");
});
//管理费经济科目设置 选择
$(document).on("click","#btn_glf",function(){
	select_commonWin("${ctx}/kjhs/pzxx/pzlr/pageSkip?pageName=jjkmTree&fyid=txt_glf&controlId=txt_glfkmmc","经济科目信息","1000","650");
});
//薪资项目设置选择框
$(document).on("click","#btn_xzxm",function(){
	select_commonWin("${ctx}/mbxz/getXmxxWindow?pageName=xmxx&controlId=txt_xzxmmc&controlId1=txt_xmbh&controlId2=txt_bmbh","项目信息","1000","650");
})
//学校项目设置选择框
$(document).on("click","#btn_xxxm",function(){
	select_commonWin("${ctx}/mbxz/getXmxxWindow?pageName=xmxx&controlId=txt_id&controlId3=txt_xxxmmc&controlId1=txt_xxxm&controlId2=txt_xxjjbmbh","项目信息","1000","650");
})
//学院项目设置选择框
$(document).on("click","#btn_xyxm",function(){
	select_commonWin("${ctx}/mbxz/getXmxxWindow?pageName=xmxx&controlId=txt_id&controlId3=txt_xyxmmc&controlId1=txt_xyxm&controlId2=txt_xyjjbmbh","项目信息","1000","650");
})
//本页面 的  添加  借款科目设置  选择
function addBhMcToSz(kmbh,kmmc){
	$("#txt_jkkmbh").val(kmbh);
	$("#txt_jkkmmc").val(kmmc);
	
}

</script>
</body>
</html>