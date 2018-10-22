<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>教师详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.sub-title2{
		font-size:14px;
	}
	.number{
	text-align:right;
	}
	.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius: 4px;
	border-bottom-right-radius:4px;
	    /*border-radius: 4px; */
    height: 25px;
    line-height: 25px;
    padding-left: 6px;
   
    }
	.text-color{
	    color:red!important;
	}
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
		width:10px!important;
		height:6mm!important;
		text-align: center;
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="C">
	<input type="hidden"  id="guid" name="guid"  value="${map.GUID}">
	<input type="hidden"  id="txt_jsbh" name="jsbh"  value="${map.GUID}">
	<input type="hidden"  id="jzgguid" name="jzgguid"  value="${map.JZGGUID}">
	
	
	
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑教师信息</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
<!-- 				<div class="sub-title pull-left text-primary"> -->
<!-- 				<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;"> -->
<!--         			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span> -->
<!--     			</div> -->
<!-- 				</div> -->
<!-- 				<div class="sub-title pull-left text-primary">教师基本信息&nbsp;></div> -->
<!-- 				<div class="sub-title pull-left text-primary"> -->
<!-- 				<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;"> -->
<!--         			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span> -->
<!--     			</div> -->
<!-- 				</div> -->
<!-- 				<div class="sub-title pull-left">教师扩展信息&nbsp;</div> -->
			
        </div>
		
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title2 pull-left text-primary"><i class="fa icon-xiangxi"></i>教师基本信息</div>
            	
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
			         <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			         <button type="button" class="btn btn-default" id="btn_back">返回</button>
                 </div>
        	</div>
			<hr class="hr-normal">
			
			<div class="container-fluid box-content">
			
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>工&emsp;&emsp;号</span>
							<input type="text" id="txt_xh" name="xh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${jsxx.XH}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>姓&emsp;&emsp;名</span>
                            <input type="text" id="txt_xm" class="form-control input-radius" name="xm" value="${jsxx.XM}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>性&emsp;&emsp;别</span>
							<select id="drp_xb" class="form-control input-radius select2" name="xbm"> 
								<c:forEach var="sexList" items="${sexList}">
	                        		<option value="${sexList.DM}" <c:if test="${jsxx.XB == sexList.DM}">selected</c:if>>${sexList.MC}</option>
	                        	</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>所在单位</span>
	                         <input type="text" id="txt_szdw" class="form-control input-radius window" name="szdw" value="${jsxx.SZDW}">                        
	                         <span class="input-group-btn"><button type="button" id="btn_szdw" class="btn btn-link btn-custom">选择</button></span>
						
						    
						
						
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>职&emsp;&emsp;称</span>
	                       <select id="drp_zc" class="form-control input-radius select2" name="zc"> 
								<c:forEach var="zcList" items="${zcList}">
	                        		<option value="${zcList.DM}" <c:if test="${jsxx.ZC == zcList.DM}">selected</c:if>>${zcList.MC}</option>
	                        	</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>职&emsp;&emsp;务</span>
							 <select id="drp_zw" class="form-control input-radius select2" name="zw"> 
								<c:forEach var="zwList" items="${zwList}">
	                        		<option value="${zwList.DM}" <c:if test="${jsxx.ZW == zwList.DM}">selected</c:if>>${zwList.MC}</option>
	                        	</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">出&ensp;生&ensp;地</span>
<!-- 							<select id="drp_csdm" class="form-control input-radius select2" name="csdm"> -->
<%-- 								<c:forEach var="qydmList" items="${qydmList}"> --%>
<%-- 	                        		<option value="${qydmList.DM}" <c:if test="${jsxx.CSDM == qydmList.DM}">selected</c:if>>${qydmList.MC}</option> --%>
<%-- 	                        	</c:forEach> --%>
<!-- 							</select> -->
							<input type="text" id="drp_csdm" class="form-control input-radius window" name="csdm" value="${jsxx.CSDM}"> 
						</div>
                  </div>
                  <div class="col-md-4">
                  	<div class="input-group">
							<span class="input-group-addon">健康状况</span>
                            <select id="txt_jkzkm" class="form-control input-radius select2" name="jkzkm">
                               <c:forEach var="jkzkList" items="${jkzkList}">
                           			<option value="${jkzkList.DM}" <c:if test="${jsxx.JKZKM == jkzkList.DM}">selected</c:if>>${jkzkList.MC}</option>
                            	</c:forEach>
                            </select>
					</div>                	
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">民&emsp;&emsp;族</span>
                           <select id="txt_mz" class="form-control input-radius select2" name="mzm">
                               <c:forEach var="mzList" items="${mzList}">
                           			<option value="${mzList.DM}" <c:if test="${jsxx.MZ == mzList.DM}">selected</c:if>>${mzList.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">国&emsp;&emsp;籍</span>
                            <select id="txt_gjdqm" class="form-control input-radius select2" name="gjdqm">
                               <c:forEach var="gjList" items="${gjList}">
                           			<option value="${gjList.DM}" <c:if test="${jsxx.GJDQM == gjList.DM}">selected</c:if>>${gjList.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">来校时间</span>
                            <input type="text" id="txt_lxsj" class="form-control date" name="lxsj" value="${jsxx.LXSJ}"  data-format="yyyy-MM-dd" placeholder="来校时间">
                        <span class='input-group-addon'>
                            <i class="glyphicon glyphicon-calendar"></i>
                        </span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">籍&emsp;&emsp;贯</span>
							<input type="text" id="txt_jg" class="form-control input-radius" name="jg" value="${jsxx.JG}" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">婚姻状况</span>
                             <select id="txt_hyzkm" class="form-control input-radius select2" name="hyzkm">
                               <c:forEach var="hyzkList" items="${hyzkList}">
                           			<option value="${hyzkList.DM}" <c:if test="${jsxx.HYZKM == hyzkList.DM}">selected</c:if>>${hyzkList.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">身份证件类型</span>
                            <select id="txt_sfzjlxm" class="form-control input-radius select2" name="sfzjlxm">
                               <c:forEach var="zjlxmList" items="${zjlxmList}">
                           			<option value="${zjlxmList.DM}" <c:if test="${jsxx.SFZJLXM == zjlxmList.DM}">selected</c:if>>${zjlxmList.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">证件号</span>
							<input type="text" id="txt_sfzh" class="form-control input-radius" name="sfzh" value="${jsxx.SFZH}" />
						</div>
					</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="input-group">
							<span class="input-group-addon">文化程度</span>
                            <select id="txt_whcd" class="form-control input-radius select2" name="whcd">
                               <c:forEach var="whcdList" items="${whcdList}">
                           			<option value="${whcdList.DM}" <c:if test="${jsxx.WHCD == whcdList.DM}">selected</c:if>>${whcdList.MC}</option>
                            	</c:forEach>
                            </select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
							<span class="input-group-addon">政治面貌</span>
                            <select id="txt_zzmmm" class="form-control input-radius select2" name="zzmmm">
                               <c:forEach var="zzmmList" items="${zzmmList}">
                           			<option value="${zzmmList.DM}" <c:if test="${jsxx.ZZMMM == zzmmList.DM}">selected</c:if>>${zzmmList.MC}</option>
                            	</c:forEach>
                            </select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
                  		<span class="input-group-addon">出生日期</span>
                        <input type="text" id="txt_csrq" class="form-control date" name="csrq" value="${jsxx.CSRQ}"  data-format="yyyy-MM-dd" placeholder="出生日期">
                        <span class='input-group-addon'>
                            <i class="glyphicon glyphicon-calendar"></i>
                        </span>
                  	</div>
				</div>
			</div>
			<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">联系方式</span>
                            <input type="text" id="txt_lxfs" class="form-control input-radius" name="lxfs" value="${jsxx.LXFS}" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">邮&emsp;&emsp;箱</span>
                            <input type="text" id="txt_yx" class="form-control input-radius" name="yx" value="${jsxx.YX}" />
						</div>
					</div>
					
                     
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">在职类型</span>
							<select id="txt_zzlx" class="form-control input-radius select2" name="zzlx" >
								<option value="0" <c:if test="${jsxx.zzlx=='0'}"> selected </c:if>>正式 </option>
								<option value="1" <c:if test="${jsxx.zzlx=='1'}">selected </c:if>>试用 </option>
								<option value="2" <c:if test="${jsxx.zzlx=='2'}">selected </c:if>>实习 </option>
								<option value="3" <c:if test="${jsxx.zzlx=='3'}"> selected </c:if>>兼职 </option>								
							</select>							
						</div>
					</div>
                    </div>
                    <div class="row">
                     
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">在职人员来源</span>
							<select id="txt_zzryly" class="form-control input-radius select2" name="zzryly" >
								<option value="0" <c:if test="${jsxx.zzryly=='0'}"> selected </c:if>  >校招</option>
								<option value="1" <c:if test="${jsxx.zzryly=='1'}"> selected </c:if>  >网聘 </option>
							</select>
							
						</div>
					</div>
                    
                   <div class="col-md-4">
					<div class="input-group">
                  		<span class="input-group-addon">参加工作时间</span>
                        <input type="text" id="txt_cjgzsj" class="form-control date" name="cjgzsj" value="${jsxx.CJGZSJ}"  data-format="yyyy-MM-dd" placeholder="参加工作时间">
                        <span class='input-group-addon'>
                            <i class="glyphicon glyphicon-calendar"></i>
                        </span>
                  	</div>
				</div>
              
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">工&emsp;&emsp;龄</span>
							<input type="text" id="txt_gl" name="gl" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"   value="${jsxx.GL}"/>
						</div>
                    </div>
				</div>
				<div class="row">
				   <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">职务或职称级别</span>
							<select id="txt_zwhzcjb" class="form-control input-radius select2" name="zwhzcjb" >
								<option <c:if test="${jsxx.zwhzcjb}"> selected </c:if>  >初级</option>
								<option>中级 </option>
								<option>高级</option>
																
							</select>							
						</div>
					</div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">隶属处室</span>
							<input type="text" id="txt_lscs" class="form-control input-radius window" name="lscs" value="${jsxx.LSCS}" >
							<span class="input-group-btn"><button type="button" id="btn_lscs" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
                    
               
                   <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否少数民族</span>
							<div class="radiodiv">&nbsp;&nbsp;&nbsp;
							   <input type="radio" name="sfssmz" value="1" >是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   <input type="radio" name="sfssmz" value="0" >否
						    </div>
						</div>
					 </div>
                   </div>
                   <div class="row">
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">人员身份</span>
							<select id="txt_rysf" class="form-control input-radius select2" name="rysf" >
								<option <c:if test="${jsxx.rysf}"> selected </c:if>  >正式 </option>
								<option>试用 </option>
								<option>实习 </option>
								<option>兼职 </option>								
							</select>							
						</div>
					</div>
				
				 
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">执行待遇职务或职称级别</span>
							<select id="txt_zxdyzwhzcjb" class="form-control input-radius select2" name="zxdyzwhzcjb" >
								<option <c:if test="${jsxx.zxdyzwhzcjb}"> selected </c:if>  >初级</option>
								<option>中级 </option>
								<option>高级</option>
																
							</select>							
						</div>
					</div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>教职工类型</span>
							<select id="txt_zzjzglx" class="form-control input-radius select2" name="zzjzglx" >
							    <option value="" >未选择</option>
							    <option value="1" <c:if test="${jsxx.zzjzglx=='1'}"> selected </c:if>  >在职</option>
								<option value="2" <c:if test="${jsxx.zzjzglx=='2'}"> selected </c:if>  >临时工</option>
								<option value="3" <c:if test="${jsxx.zzjzglx=='3'}"> selected </c:if>  >遗属</option>
								<option value="4" <c:if test="${jsxx.zzjzglx=='4'}"> selected </c:if>  >退休人员</option>
								<option value="5" <c:if test="${jsxx.zzjzglx=='5'}"> selected </c:if>  >离休人员</option>
							</select>
							<span></span>
						</div>
					</div>						
				 </div>
				  <div class="row">
					  <div class="col-md-4">
						<div class="input-group">
								<span class="input-group-addon">报销级别</span>
	                            <select id="txt_whcd" class="form-control input-radius select2" name="bxjb">
	                               <c:forEach var="bxjbList" items="${bxjbList}">
	                           			<option value="${bxjbList.DM}" <c:if test="${jsxx.BXJB == bxjbList.DM}">selected</c:if>>${bxjbList.MC}</option>
	                            	</c:forEach>
	                            </select>
						</div>
					  </div>
					  <div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>排序序号</span>
								<input type="text" id="txt_pxxh" class="form-control input-radius " name="pxxh" value="${jsxx.PXXH}"/>
							</div>
					  </div>
					  <div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">学科领域</span>
								<select id="txt_xkly" class="form-control input-radius select2" name="xkly">
	                               <c:forEach var="xklyList" items="${xklyList}">
	                           			<option value="${xklyList.DM}" <c:if test="${jsxx.XKLY == xklyList.DM}">selected</c:if>>${xklyList.MC}</option>
	                            	</c:forEach>
	                            </select>
							</div>
					  </div>
				 </div>
				 <div class="row">
				     <div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">一级学科</span>
								<select id="txt_yjxk" class="form-control input-radius select2" name="yjxk">
	                               <c:forEach var="yjxkList" items="${yjxkList}">
	                           			<option value="${yjxkList.DM}" <c:if test="${jsxx.YJXK == yjxkList.DM}">selected</c:if>>${yjxkList.MC}</option>
	                            	</c:forEach>
	                            </select>
							</div>
					  </div>
				 </div>
				 <div class="row">
				     <div class="col-md-12">
							<div class="input-group">
								<span class="input-group-addon">研究方向</span>
								<textarea rows="3" class="form-control" name="yjfx">${jsxx.YJFX}</textarea>
							</div>
					  </div>
				 </div>
		  </div>
		 </div>
		<div class="box-panel">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>上传电子签章
				</div>
			</div>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-8">
						<input id="imageFile" name="path" type="file" multiple class="file-loading"/>
						<div id="errorBlock" class="help-block"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

	<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title2 pull-left text-primary"><i class="fa icon-xiangxi"></i>银行清单
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
        
			<div class="container-fluid box-content">
			 <form id="myform1" class="myform">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align: center;">操作</th>
				            <th style="width:300px; text-align: center;">开户银行名称</th>
				            <th style="width:300px;text-align: center;">开户银行帐号</th>
				             <th style="width:300px;text-align: center;">人民银行联行号</th>
				        </tr>
					</thead>
				     <tbody id="tbody" class="tbody">   
				    <c:choose>
                          <c:when test="${not empty jsyhzh}">
  								<c:forEach var="jsyhzh" items="${jsyhzh}"> 
							    	<tr id="tr_1">
							    		<td class="btn_td">
				                               <div class="addBtn add"></div>
							    		</td>
							    		<td>
							    		   <input type="hidden" id="txt_guid" class="form-control input-radius" a="a" name="guid" value="${jsyhzh.guid }">
							    		    <input type="hidden" id="txt_jsbh1" class="form-control input-radius" name="jsbh1" value="${map.GUID }">
							    			<input type="text" id="txt_khyh1"  class="form-control input-radius null" name="khyh1" value="${jsyhzh.khyh}">
							    			
							    		</td>
							    		<td>
							    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="khyhzh1" value="${jsyhzh.khyhzh}">
							    		</td>
							    		<td>
							    			<input type="text" id="txt_yhlhh1" a="a" class="form-control input-radius int" name="yhlhh1" value="${jsyhzh.yhlhh}">
							    		</td>
							    	</tr>
				    			</c:forEach>
                          </c:when>
                          <c:otherwise>
	                          	<tr id="tr_1">
						    		<td class="btn_td">
			                               <div class="addBtn add"></div>
						    		</td>
						    		<td>
						    		   <input type="hidden" id="txt_guid" class="form-control input-radius" a="a" name="guid" value="">
						    		    <input type="hidden" id="txt_jsbh1" class="form-control input-radius" name="jsbh1" value="${map.GUID }">
						    			<input type="text" id="txt_khyh1"  class="form-control input-radius null" name="khyh1" value="">
						    		</td>
						    		<td>
						    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null khyhzh1" name="khyhzh1" value="">
						    		</td>
						    		<td>
							    		<input type="text" id="txt_yhlhh1" a="a" class="form-control input-radius int" name="yhlhh1" value="">
							    	</td>
					    		</tr>
                           </c:otherwise>
                    </c:choose>
				    </tbody>
				</table>
				</form>
			</div>
		</div>   
<!--                  <div class="row"> -->
<!-- 				<div class="pull-center" style="text-align:center;"> -->
<!--             		<button type="button" class="btn btn-default" id="btn_next" style="background:#00acec;color: white;">下一步</button> -->
<!--         		</div> -->
<!-- 			</div> -->

<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var tag = false;
var operate = "${operateType}";
//var operate_src = ADDRESS+"/jcsz/jsxxs/insert";
$(function(){
    /* 图片信息开始 */
	var fjView = [<%=request.getAttribute("fjView")%>];
	var fjConfig = [<%=request.getAttribute("fjConfig")%>];
	//附件信息
     $("#imageFile").fileinput({
       	fileActionSettings:{
    		showUpload:false//隐藏上传按钮
    	},
    	initialPreview:fjView,
    	initialPreviewAsData:true,
    	initialPreviewConfig:fjConfig,
    	uploadUrl: '${ctx}/file/fileUpload',//上传请求路径
        maxFilePreviewSize: 10240,//最大上传文件的大小
        showUpload:false,//是否显示上传按钮
        initialPreviewShowDelete:true,
        showBrowse:true,
        showCaption:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"LDQZ","id":"${jsxx.GUID}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
    });
   //扫码上传
 	SmscLoad("${ctx}",{"id":"${jsxx.GUID}","djlx":"000000","fold":"LDQZ","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
	/*图片信息结束  */ 
	//身份证号获取出生年月	
	$("#txt_sfzh").blur(function(){		
		var IDcard = $(this).val();
		var length=IDcard.length;
		//身份证号输完自动填写出生日期
		if(length==18){
		var birthday = IDcard.substr(6, 4)+ "-" + IDcard.substr(10, 2) + "-" + IDcard.substr(12, 2);		
		$("#txt_csrq").val(birthday);
        }        
	});  
});
//自动获取工龄
 $(function(){
   $("#txt_cjgzsj").blur(function(){
   var cjgzsj = $(this).val();
 
 if(cjgzsj.length==0){
}else{
   var gzyear= cjgzsj.substr(0,4);
   var gzmonth=cjgzsj.substr(5,2);
   var gzday=cjgzsj.substr(8,2);

   var date = new Date();
   
   var year = date.getFullYear();
   var month=date.getMonth();
   var day=date.getDay();

  
   if(month<gzmonth){
		month=month+12;
		year=year-1;
	}
   glmonth=month-gzmonth;
   glyear=year-gzyear;
   if(glyear<0){
    glyear=0;
  }

   $("#txt_gl").val(glyear);
}

});
});
//
$(function(){
	var xh=$("#txt_xh").val();
	var operate = "${operateType}";
	//联想输入提示
	$("#txt_szdw").bindChange("${ctx}/suggest/getXx","D");
	$("#txt_lscs").bindChange("${ctx}/suggest/getXx","D");
	
	//单位弹窗 
	$("#btn_szdw").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_szdw","单位信息","920","630");	
    });
$("#btn_lscs").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_lscs","处室信息","920","630");
    });
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          xh:{validators:{notEmpty: {message: '人员工号不能为空'},regexp: {regexp: /^[a-zA-Z0-9_]+$/,message: '人员工号只能包含大写、小写、数字和下划线'},stringLength:{message:'工号过长',max:'20'}}},
          xm:{validators:{ notEmpty: {message: '姓名不能为空'},stringLength:{message:'姓名过长',max:'8'}}},
          szdw:{validators: {notEmpty: {message: '所在单位不能为空'}}},
          zc:{validators: {notEmpty: {message: '职称不能为空'}}},
          zw:{validators: {notEmpty: {message: '职务不能为空'}}},
          pxxh:{validators: {notEmpty: {message: '排序序号不能为空'}}},
//           xkly:{validators: {notEmpty: {message: '学科领域不能为空'}}},
//           yjxk:{validators: {notEmpty: {message: '一级学科不能为空'}}},
          
//           sfzjlxm:{validators:{notEmpty:{message: '身份证件类型不能为空'}}},
//           xbm:{validators:{ notEmpty:{message: '性别不能为空'},}},
//          // sfzh:{validators:{cardId:{message: '请输入有效的身份证号'},notEmpty:{message:"身份证件号不能为空"}}},
//           zzlx:{validators: {notEmpty: {message: '在职类型不能为空'}}},
//           zzryly:{validators: {notEmpty: {message: '在职人员来源不能为空'}}},
//           zwhzcjb:{validators: {notEmpty: {message: '职务或职称级别不能为空'}}},
//           lscs:{validators: {notEmpty: {message: '隶属处室不能为空'}}},
//           sfssmz:{validators: {notEmpty: {message: '是否少数民族不能为空'}}},
//           rysf:{validators: {notEmpty: {message: '人员身份不能为空'}}},
          zzjzglx:{validators: {notEmpty: {message: '教职工类型不能为空'}}},
//           csdm:{validators: {notEmpty: {message: '出生地不能为空'}}},
		yhlhh1:{validators: {stringLength:{message:'人民银行联行号过长',max:'12'}}}
	}
	});
	$("[name=yhlhh1]").blur(function(){
	 	var len = $("#txt_yhlhh1").val().length;
	 	if(len> 12){
	 		alert("人民银行联行号过长,12位数");
	 	}
	});
	 //保存按钮
	$("#btn_save").click(function(){
		$("#imageFile").fileinput("upload");
		doSave(validate,"myform","${ctx}/jsxxs/doSave",function(val){
			if(val.success == "true"){
				tag = true;
// 				alert(val.msg);
				$("#operateType").val("C");
				next();
			}
		});
	}); 
	//下一步
	$("#btn_next").click(function(){
		var guid = $("[name='guid']").val();
		var jzgguid =  $("[name='jzgguid']").val();
		$("#imageFile").fileinput("upload");
		var _url = "${ctx}/jsxxs/after?guid="+guid+"&jzgguid="+jzgguid;
		if(tag){
			window.location.href = _url;
		}else{
			doSaveFb(validate,"myform","${ctx}/jsxxs/doSave",function(val){
				next();
				if(val.success){
					window.location.href = _url;
				}
			});
		}
	});
	function doSaveFb(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){
					next();
					close(index);
					var data = JSON.getJson(val);
					if(data.success == "true"){
						$("#operateType").val("U");
						if(_success != "" && _success != "" && _success != ""){
							_success(data);
						}
					} else {
						alert(data.msg);
					}
				},
				error:function(val){
					close(index);
					alert(getPubErrorMsg());
				},
				beforeSend:function(val){
					index = loading(2);
				}
			});
		}
	}
	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/jsxxs/goJsxxListPage";
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}"
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"
	    	}
		 }
	});
	$("[name='zzzt']").change(function(){
		if($(this).val() == '3'){
			$("#txrq").show();
		}else{
			$("#txrq").hide();
		}
	});
	//页面加载完后，退休日期是否可编辑
	txrq();
	function txrq(){
		var $this = $("[name='zzzt']").val();
		if($this == '3'){
			$("#txrq").show();
		}else{
			$("#txrq").hide();
		}
	}

});

$(document).on("blur", ".number", function(e){
	$(this).Num(2,true,false,e);
	return false;
});
//新增按钮

var num = 2;
$(document).on("click","[class*=addBtn]",function(){
	var jsbh = $("#txt_jsbh1").val();
	var $parentTr = $(this).parents("tr").clone();
	$(this).removeClass("addBtn");
	$(this).addClass("deleteBtn");
	$parentTr.find(":input").val("");
	$parentTr.find(":input").removeClass("border");
	//$parentTr.find("[id=txt_wlbh]").val(wlbh);
	$parentTr.attr("id","tr_"+num);
	$parentTr.find("#txt_jsbh1").val(jsbh);
	
	
	num++;
	$(this).parents("tr").after($parentTr);
});

//删除按钮
$(document).on("click","[class*=deleteBtn]",function(){
	$(this).parents("tr").remove();
	
});
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
        }else{
        	o[this.name] = this.value;
        }
    });
    	console.log(JSON.stringify(f));
    return f;
};
function next(){
	var lastval = $(".myform tr:last").find("[name=khyh1]").val();
	if(lastval==""){
		$(".myform tr:last").remove();
	}else{
		
	}
	var json = $('#myform1').serializeObject1("guid","yhlhh1");  //json对象	
	
	var jsonStr = JSON.stringify(json);  //json字符串
	var jsbh = $("#txt_jsbh").val(); //guid
	var zds = $("[name='khyhzh1']");//开户银行账号
	var lhh = $("[name='yhlhh1']");//银行联行号
	var zdid = [];  //所有账号
	var lhhid = [];  //所有联行号
	zds.each(function(){
		zdid.push($(this).val());
	});
	lhh.each(function(){
		lhhid.push($(this).val());
	});
	var xx = "tg";
	if(zdid==""){
		xx = "tg";
	}else{
		for (var i=0;i<zdid.length;i++) {
			var aa = zdid[i];
				if (aa.length!="12"&&aa.length!="16"&&aa.length!="19"&&aa.length!="22") {
					xx = "th";
					alert("请输入正确的银行卡号或者存折！");
				}
		}
	}
	//判断是否是建行  填写银行联行号
	for (var i=0;i<zdid.length;i++) {
		var aa = zdid[i]; //每个银行账号
		var bankCode = bankCardAttribution(aa); //每个银行的标志符  建行 CCB
		var code = bankCode[0]
		if (aa!="" && code == "CCB") {
			var lhhVal = lhhid[i];	
			if(lhhVal==""){
			}else if(lhhVal.length != "12"){
				alert("请输入第"+(i+1)+"行正确的人民银行联行号")
				xx = "th";
			}
		}
		if (aa!="" && code != "CCB") {
			var lhhVal = lhhid[i];		
			console.log(lhhVal.length)
			if(lhhVal==""){
				alert("请输入第"+(i+1)+"行的人民银行联行号");
				xx = "th";
			}else if(lhhVal.length != "12"){
				alert("请输入第"+(i+1)+"行正确的人民银行联行号")
				xx = "th";
			}
		}
	}
	if(xx=="tg"){
		$.ajax({
		        url:"${ctx}/jsxxs/editJsyhzh?jsbh="+jsbh,
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				  alert("保存成功！");  	
				  window.location.href = "${ctx}/jsxxs/goJsxxListPage";
	   			}
		}); 
	}
}
var jsbh = $("#txt_jsbh1").val();
$(function(){
	addbutton();
	function addbutton(){
		var a = $(".tbody").find("tr").length;
		var aaa="${jsyhzh}";
		
		if(aaa.length>0){
			$(".add").removeClass("addBtn")
			
			$(".add").addClass("deleteBtn");		
			var $parentTr = $("tr:last").clone();
			
			$parentTr.find("input:not(#txt_jsbh1)").attr("value","");
			
			$("tr:last").after($parentTr);
			
			$(".tbody tr:last").find(".add").removeClass("deleteBtn");
			$(".tbody tr:last").find(".add").addClass("addBtn");
		}else{
			
		}
	}
	
});
	
</script>
</body>
</html>