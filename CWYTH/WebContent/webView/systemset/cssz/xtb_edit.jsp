   <%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>系统运行参数设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-manager-js.inc"%>
<style>
	.yl {
		padding-left: 10px;
	}
	td {
		vertical-align: middle !important;
		text-align: center;
		position:relative;
	}
	td .help-block{
		top:5px !important;
	}
	td input{
		border-radius: 5px !important;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden"  name="guid" value="${xtb.GUID}"/>
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
				系统运行参数信息
			</span>
		</div>
        <div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save">
				<i class="fa icon-save"></i>
				保存
			</button>
        </div>
    </div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		系统运行参数信息
            	</div>
            	<!-- 右侧折叠按钮 -->
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">组织机构代码</span>
							<input type="text" id="txt_asssdm" class="form-control input-radius" name="saasdm" value="${xtb.SAASDM}"/>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>学校代码</span>
							<input type="text" id="txt_xxdm" class="form-control input-radius" name="xxdm" value="${xtb.XXDM}"/>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">学校类型</span>
 							<select class="form-control" name="xxlx">
								<option value="1" <c:if test="${xtb.xxlx==1}">selected</c:if>>综合、师范、民族院校</option>
								<option value="2" <c:if test="${xtb.xxlx==2}">selected</c:if>>工科、农、林院校</option>
								<option value="3" <c:if test="${xtb.xxlx==3}">selected</c:if>>语文、财经政法院校</option>
								<option value="4" <c:if test="${xtb.xxlx==4}">selected</c:if>>医学院校</option>
								<option value="5" <c:if test="${xtb.xxlx==5}">selected</c:if>>体育院校</option>
								<option value="6" <c:if test="${xtb.xxlx==6}">selected</c:if>>艺术院校</option>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">在校生数</span>
							<input type="text" class="form-control input-radius text-right int" name="zxs" value="${xtb.ZXS }"> 
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon" style="border-radius: 5px;">土地是否属于固定资产</span>&emsp;
							<input type="radio" name="td" value="1" <c:if test="${xtb.td==1}">checked</c:if>/>属&emsp;于&emsp;&emsp;
							<input type="radio" name="td" value="0" <c:if test="${xtb.td==0}">checked</c:if>/>不&ensp;属&ensp;于
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">贵重仪器设备价格起点</span>
							<input type="text" id="txt_gzyq" class="form-control input-radius text-right number" name="gzyq" value="<fmt:formatNumber value='${xtb.GZYQ}' pattern='0.00'/>">
						</div>
					</div>

					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">每页行数</span>
							<input type="text" id="txt_rownums" class="form-control input-radius text-right int" name="rownums" value="${xtb.ROWNUMS}">
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">上一次备份日期</span>
							<input type="text" id="txt_bfrq" class="form-control input-radius" name="bfrq" disabled value="<fmt:formatDate value="${xtb.BFRQ}" pattern="yyyy-MM-dd HH:mm:ss"/>" data-format="yyyy-MM-dd HH:mm:ss" placeholder="暂无数据备份记录">
						</div>
					</div>
				</div>
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-3"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">提醒数据备份天数</span> -->
<%-- 							<input type="text" id="txt_pxxh" class="form-control input-radius text-right int" name="bfts" value="${xtb.BFTS}"> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-zichanchuzhi01"></i>
            		单据打印设置
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">验&ensp;收&ensp;单</span>
 							<select class="form-control input-radius" name="ysdmb"<c:if test="${xtb.yshdtd==1}"> disabled="disabled"</c:if> style="width:45%">
								<option value="1" <c:if test="${xtb.ysdmb==1}">selected</c:if>>默认格式</option>
								<option value="2" <c:if test="${xtb.ysdmb==2}">selected</c:if>>模板一</option>
								<option value="4" <c:if test="${xtb.ysdmb==4}">selected</c:if>>模板二</option>
								<option value="5" <c:if test="${xtb.ysdmb==5}">selected</c:if>>模板三加条形码</option>
								<option value="3" <c:if test="${xtb.ysdmb==3}">selected</c:if>>归口通过后打印</option>
							</select>
							<div class="pull-left" style="padding-left: 10px;">
								<input type="radio" name="yshdtd" value="1" <c:if test="${xtb.yshdtd==1}">checked</c:if>> 套打&ensp;
								<input type="radio" name="yshdtd" value="0" <c:if test="${xtb.yshdtd==0}">checked</c:if>> 不套打
							</div>
							<div class="yl pull-left">
							<input type="button" class="ysdyl" value="预览">
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">卡&emsp;&emsp;片</span>
 							<select class="form-control input-radius" name="kpmb"  style="width:80%">
								<option value="1" <c:if test="${xtb.kpmb==1}">selected</c:if>>默认格式</option>
							</select>
							<div class="yl pull-left">
								<input type="button" class="kpyl" value="预览">
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">变&ensp;动&ensp;单</span>
 							<select class="form-control input-radius" name="bdtd" style="width:80%">
								<option value="1" <c:if test="${xtb.bdtd==1}">selected</c:if>>项目变动</option>
								<option value="2" <c:if test="${xtb.bdtd==2}">selected</c:if>>单价变动</option>
								<option value="3" <c:if test="${xtb.bdtd==3}">selected</c:if>>增加附件</option>
								<option value="4" <c:if test="${xtb.bdtd==4}">selected</c:if>>处置附件</option>
							</select>
							<div class="yl pull-left">
								<input type="button" class="bddyl" value="预览">
							</div>
						</div>
					</div>
			   </div>
				  <div class=row>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">处&ensp;置&ensp;单</span>
 							<select class="form-control input-radius" name="cztd" style="width:80%">
								<option value="1" <c:if test="${xtb.cztd==1}">selected</c:if>>默认格式</option>
								<option value="2" <c:if test="${xtb.cztd==2}">selected</c:if>>模板一</option>
								<option value="3" <c:if test="${xtb.cztd==3}">selected</c:if>>模板二</option>
								<option value="4" <c:if test="${xtb.cztd==4}">selected</c:if>>模板三</option>
							</select>
							<div class="yl pull-left">
								<input type="button" class="czdyl" value="预览">
							</div>
						</div>
					</div>
			      </div>
			  </div>
		   </div>
<!-- 		<div class="box-panel"> -->
<!-- 			<div class="box-header clearfix"> -->
<!--             	<div class="sub-title pull-left text-primary"> -->
<!--             		<i class="fa icon-zichanchuzhi01"></i> -->
<!--             		编号区间设置 -->
<!--             	</div> -->
<!--             	<div class="actions"> -->
<!--             		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a> -->
<!--             	</div> -->
<!--         	</div> -->
<!-- 			<hr class="hr-normal"> -->
<!-- 			<div class="container-fluid box-content"> -->
				
<!-- 			  <table class="table table-bordered"> -->
<!-- 			  		<tbody> -->
<!-- 			  			<tr> -->
<!-- 			  				<td rowspan="2" style="background-color: #EEEEEE;">单位流水</td> -->
<!-- 			  				<td > -->
<!-- 			  					<label class="control-label col-md-3">上限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" name="dwlsto" value="${xtb.DWLSTO}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  				<td rowspan="2" style="background-color: #EEEEEE;">变动流水</td> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">上限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" name="bdlsto" value="${xtb.BDLSTO}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  				<td rowspan="2" style="background-color: #EEEEEE;">处置流水</td> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">上限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" name="czlsto" value="${xtb.CZLSTO}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  			</tr> -->
<!-- 			  			<tr> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">下限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" name="dwlsfrom" value="${xtb.DWLSFROM}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">下限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" name="bdlsfrom" value="${xtb.BDLSFROM}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">下限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" name="czlsfrom" value="${xtb.CZLSFROM}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  			</tr> -->
<!-- 			  			<tr> -->
<!-- 			  				<td rowspan="2" style="background-color: #EEEEEE;">资产流水</td> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">上限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" maxlength="4" name="nfqd" value="${xtb.NFQD}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  				<td rowspan="2" style="background-color: #EEEEEE;">验收流水</td> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">上限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" name="lsqd" value="${xtb.LSQD}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  				<td rowspan="2" style="background-color: #EEEEEE;">人员流水</td> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">上限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" maxlength="6" name="blqd" value="${xtb.BLQD}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  			</tr> -->
<!-- 			  			<tr> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">下限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" maxlength="4" name="nfcd" value="${xtb.NFCD}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">下限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" name="lscd" value="${xtb.LSCD}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  				<td> -->
<!-- 			  					<label class="control-label col-md-3">下限：</label> -->
<!-- 								<div class="col-md-9 input-group"> -->
<%-- 									<input type="text" class="form-control text-right int" maxlength="6" name="blcd" value="${xtb.BLCD}"/> --%>
<!-- 								</div> -->
<!-- 			  				</td> -->
<!-- 			  			</tr> -->
<!-- 			  		</tbody> -->
<!-- 			  </table> -->
<!-- 		   </div> -->
<!-- 	    </div>-->
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-zichanchuzhi01"></i>
            		默认选项（固定资产增加默认选项）
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">经费来源</span>
							<select id="drp_jfkm" class="form-control input-radius" name="jfkm">
								<option value="">请选择...</option>
								<c:forEach var="jfkmlist" items="${jfkmlist}"> 
									<option value="${jfkmlist.DM}" <c:if test="${xtsz_map.jfkm == jfkmlist.DM}">selected</c:if>>${jfkmlist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">记账类型</span>
							<select id="drp_jzlx" class="form-control input-radius" name="jzlx">
								<option value="">请选择...</option>
								<c:forEach var="jzlxlist" items="${jzlxlist}"> 
									<option value="${jzlxlist.DM}" <c:if test="${xtsz_map.jzlx == jzlxlist.DM}">selected</c:if>>${jzlxlist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">学科类别</span>
							<select id="drp_xklb" class="form-control input-radius" name="xklb">
								<option value="">请选择...</option>
								<c:forEach var="xklblist" items="${xklblist}">
									<option value="${xklblist.DM}" <c:if test="${xtsz_map.xklb == xklblist.DM }">selected</c:if>>${xklblist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">现&emsp;&emsp;状</span>
							<select id="drp_xz" class="form-control input-radius" name="xz">
								<option value="">请选择...</option>
								<c:forEach var="xzlist" items="${xzlist }">
									<option value="${xzlist.DM }" <c:if test="${xtsz_map.xz == xzlist.DM }">selected</c:if>>${xzlist.MC }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">资产来源</span>
							<select id="drp_zcly" class="form-control input-radius" name="zcly">
								<option value="">请选择...</option>
								<c:forEach var="zclylist" items="${zclylist}"> 
									<option value="${zclylist.DM}" <c:if test="${xtsz_map.zcly == zclylist.DM}">selected</c:if>>${zclylist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">建筑结构</span>
							<select id="drp_jzjg" class="form-control input-radius" name="jzjg">
								<option value="">请选择...</option>
								<c:forEach var="xhlist" items="${xhlist}"> 
									<option value="${xhlist.DM}" <c:if test="${xtsz_map.jzjg == xhlist.DM}">selected</c:if>>${xhlist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">建筑类型</span>
							<select id="drp_jjzlx" class="form-control input-radius" name="jjzlx">
								<option value="">请选择...</option>
								<c:forEach var="zjlist" items="${zjlist }">
									<option value="${zjlist.DM }" <c:if test="${xtsz_map.jjzlx == zjlist.DM }">selected</c:if>>${zjlist.MC }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">完损状况</span>
							<select id="drp_wszk" class="form-control input-radius" name="wszk">
								<option value="">请选择...</option>
								<c:forEach var="wszklist" items="${wszklist}"> 
									<option value="${wszklist.DM}" <c:if test="${xtsz_map.wszk == wszklist.DM}">selected</c:if>>${wszklist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">外币种类</span>
							<select id="drp_wbzl" class="form-control input-radius" name="wbzl">
								<option value="">请选择...</option>
								<c:forEach var="wbzllist" items="${wbzllist}"> 
									<option value="${wbzllist.DM}" <c:if test="${xtsz_map.wbzl == wbzllist.DM}">selected</c:if>>${wbzllist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">使用方向</span>
							<select id="drp_syfx" class="form-control input-radius" name="syfx">
								<option value="">请选择...</option>
								<c:forEach var="syfxlist" items="${syfxlist }">
									<option value="${syfxlist.DM }" <c:if test="${xtsz_map.syfx == syfxlist.DM }">selected</c:if>>${syfxlist.MC }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">采购形式</span>
							<select id="drp_cgzzxs" class="form-control input-radius" name="cgzzxs">
								<option value="">请选择...</option>
								<c:forEach var="cgzzxslist" items="${cgzzxslist }">
									<option value="${cgzzxslist.DM }" <c:if test="${xtsz_map.cgzzxs == cgzzxslist.DM }">selected</c:if>>${cgzzxslist.MC }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-zichanchuzhi01"></i>
            		默认选项（资产处置默认选项）
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">处置方式</span>
							<select id="drp_czfs" class="form-control input-radius" name="czfs">
								<option value="">请选择...</option>
								<c:forEach var="czfslist" items="${czfslist }">
									<option value="${czfslist.DM }" <c:if test="${xtsz_map.czfs == czfslist.DM }">selected</c:if>>${czfslist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-zichanchuzhi01"></i>
            		其他常规设置
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-3">
						<div class="input-group">
							<!-- 不带必填项的label元素 -->
							<span class="input-group-addon">资产分类选择</span>
 							<select id="drp_sfmj" class="form-control input-radius" name="sfmj">
								<option value="1" <c:if test="${xtb.sfmj == 1 }">selected</c:if>>只能选择末级</option>
								<option value="0" <c:if test="${xtb.sfmj == 0 }">selected</c:if>>可以选择非末级</option>
							</select>
						</div>
					 </div>
					<div class="col-md-3">
						<div class="input-group">
							<!-- 不带必填项的label元素 -->
							<span class="input-group-addon">资产添加单位</span>
 							<select id="drp_sftjzmj" class="form-control input-radius" name="sftjzmj">
								<option value="1" <c:if test="${xtb.sftjzmj == 1 }">selected</c:if>>只能添加在末级单位</option>
								<option value="0" <c:if test="${xtb.sftjzmj == 0 }">selected</c:if>>可以添加在非末级单位</option>
								<option value="2" <c:if test="${xtb.sftjzmj == 2 }">selected</c:if>>除房产、土地外只能添加在末级</option>
							</select>
						</div>
					 </div>
				 </div>
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-3"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">查询设置</span> -->
<!--  							<select id="drp_cxdc" class="form-control input-radius" name="cxdc"> -->
<%-- 								<option value="1" <c:if test="${xtb.cxdc == 1 }">selected</c:if>>默认设置</option> --%>
<%-- 								<option value="0" <c:if test="${xtb.cxdc == 0 }">selected</c:if>>不允许导出Excel</option> --%>
<%-- 								<option value="2" <c:if test="${xtb.cxdc == 2 }">selected</c:if>>不允许打印</option> --%>
<%-- 								<option value="3" <c:if test="${xtb.cxdc == 3 }">selected</c:if>>既不允许导出也不允许打印</option> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 					 </div> -->
<!-- 					<div class="col-md-3"> -->
<!-- 						<div class="input-group "> -->
<!-- 							不带必填项的label元素 -->
<!-- 							<span class="input-group-addon">查询状态</span> -->
<!--  							<select id="drp_zzbh" class="form-control" id="drp_cxdc" name="zzbh"> -->
<%-- 								<option value="1" <c:if test="${xtb.zzbh == 1 }">selected</c:if>>默认设置</option> --%>
<%-- 								<option value="0" <c:if test="${xtb.zzbh == 0 }">selected</c:if>>财务审核通过</option> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 					 </div> -->
<!-- 				 </div> -->
				 <div class="row">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon" style="border-radius: 5px;">查&emsp;&emsp;询</span>&emsp;
							<input type="checkbox"  name="mutisearchfirst" value="1" <c:if test="${xtb.mutisearchfirst == 1 }">checked</c:if>>是否先显示综合查询页面
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon" style="border-radius: 5px;">综合查询方案</span>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
							<input type="button" name="clear" value="清除"/>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon" style="border-radius: 5px;">统计分析</span>&emsp;
							<input type="radio" name="cgbh" value="1" <c:if test="${xtb.cgbh == 1 }">checked</c:if>>数&emsp;&emsp;量&emsp;&emsp;
							<input type="radio" name="cgbh" value="0" <c:if test="${xtb.cgbh == 0 }">checked</c:if>>套&ensp;（件）
						</div>
					</div>
					<%-- <div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon" style="border-radius: 5px;">管理员建账设置</span>&emsp;
							<input type="radio" name="ksdh" value="1" <c:if test="${xtb.ksdh == 1 }">checked</c:if>>快速导航&emsp;&emsp;
							<input type="radio" name="ksdh" value="0" <c:if test="${xtb.ksdh == 0 }">checked</c:if>>单位树
						</div>
					</div> --%>
				</div>
			</div>
		</div>
	</div>
</form>
<script>
$(function () {
	var validate = $('#myform').bootstrapValidator({fields: {
// 		saasdm: {validators: {notEmpty: {message: '不能为空'}}},
          xxdm:{validators:{notEmpty:{message:'不能为空'}}} ,
           //zxs:{validators:{notEmpty:{message:'不能为空'}}},
          //gzyq:{validators:{notEmpty:{message:'不能为空'}}},
     //  rownums:{validators:{notEmpty:{message:'不能为空'}}},
          //bfrq:{validators:{notEmpty:{message:'不能为空'}}},
//           bfts:{validators:{notEmpty:{message:'不能为空'}}},
        dwlsto:{validators:{notEmpty:{message:'不能为空'}}},
      dwlsfrom:{validators:{notEmpty:{message:'不能为空'}}},
        bdlsto:{validators:{notEmpty:{message:'不能为空'}}},
      bdlsfrom:{validators:{notEmpty:{message:'不能为空'}}},
        czlsto:{validators:{notEmpty:{message:'不能为空'}}},
      czlsfrom:{validators:{notEmpty:{message:'不能为空'}}},
          nfqd:{validators:{notEmpty:{message:'不能为空'}}},
          nfcd:{validators:{notEmpty:{message:'不能为空'}}},
          lsqd:{validators:{notEmpty:{message:'不能为空'}}},
          lscd:{validators:{notEmpty:{message:'不能为空'}}},
          blqd:{validators:{notEmpty:{message:'不能为空'}}},
          blcd:{validators:{notEmpty:{message:'不能为空'}}}}
    });
	//保存
	$("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/xtb/doSave",function(val){},function(val){});
	});
	
	$(".ysdyl").click(function(){
		window.open("${pageContext.request.contextPath}/xtb/goPrintPage?djlx=ysd&sftd="+$("[name=yshdtd]:checked").val()+"&mbbh="+$("[name=ysdmb]").val());
	});
	$(".kpyl").click(function(){
		window.open("${pageContext.request.contextPath}/xtb/goPrintPage?djlx=kp&sftd=&mbbh="+$("[name=kpmb]").val());
	});
	$(".bddyl").click(function(){
		window.open("${pageContext.request.contextPath}/xtb/goPrintPage?djlx=bdd&sftd=&mbbh="+$("[name=bdtd]").val());
	});
	$(".czdyl").click(function(){
		window.open("${pageContext.request.contextPath}/xtb/goPrintPage?djlx=czd&sftd=&mbbh="+$("[name=cztd]").val());
	});
	
	$(":radio[name='yshdtd']").click(function(){
		if($(this).val() == "1"){
			$("select[name='ysdmb']").val("1");
			$("select[name='ysdmb']").prop("disabled",true);
		}
		else{
			$("select[name='ysdmb']").prop("disabled",false);
		}
	});
	$("#txt_rownums").blur(function(){
		if($(this).val()=='' || $(this).val()==0){
			$(this).val(100);
		}
	});
});
</script>
</body>
</html>