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
.number{
		text-align:right;
		}
		.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	   
	    }
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="C">
	<input type="hidden" id="xh" name="xh"  value="${xh}">
	<input type="hidden" name="czr"  value="${loginId}">
	<input type="hidden" name="guid" id="guid"  value="${map.JZGGUID}">
	<input type="hidden" name="jzgguid" id="jzgguid"  value="${map.GUID}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑教师信息</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-primary">
				<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-green" style="color:green;">教师基本信息&nbsp;></div>
				
				<div class="sub-title pull-left ">
				<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-primary">教师扩展信息&nbsp;</div>
				
				
        </div>
	
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>教师扩展信息</div>
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
							<span class="input-group-addon"><span class="required">*</span>工资关系保留人员</span>
							<div class="radiodiv">&nbsp;&nbsp;&nbsp;
							   <input type="radio" name="gzgxblry" value="1" <c:if test="${jsxx.GZGXBLRY==1}">checked</c:if>/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   <input type="radio" name="gzgxblry" value="0" checked <c:if test="${jsxx.GZGXBLRY==0}">checked</c:if>/>否
						   </div>
						</div>
					 </div>
					 <div class="col-md-4">
					   <div class="input-group">
					      <span class="input-group-addon"><span class="required">*</span>工资类别</span>
					         <select id="drp_xb" class="form-control input-radius select2" name="gzlb"> 
					           <c:forEach var="gzlbList" items="${gzlbList}">
	                        		<option value="${gzlbList.DM}" <c:if test="${jsxx.GZLB == gzlbList.DM}">selected</c:if>>${gzlbList.MC}</option>
	                        
	                        	</c:forEach>
	                        </select>		
					   </div>
					 </div>
					 <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">国家和省级项目年度合计（元）</span>
							<input type="text" id="txt_gjhsjxmndhj" name="gjhsjxmndhj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.GJHSJXMNDHJ}"  pattern=".00"/>" />
						</div>
                    </div>
				</div>
                    <div class="row">
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">按月开支工资津补贴项目合计（元）</span>
							<input type="text" id="txt_aykzgzjbtxmhj" name="aykzgzjbtxmhj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.AYKZGZJBTXMHJ}" pattern=".00"/>"/>
						</div>                                                                                                                                                                                           
                    </div>
				
				 <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">基本工资合计（元）</span>
							<input type="text" id="txt_jbgzhj" name="jbgzhj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value="<fmt:formatNumber value="${jsxx.JBGZHJ}"  pattern=".00"/>"   />
						</div>
                    </div>
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">职务工资（元）</span>
							<input type="text" id="txt_zwgz" name="zwgz" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.ZWGZ}"  pattern=".00"/>"/>
						</div>
                    </div>
                    </div>
                    <div class="row">
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">级别工资（元）</span>
							<input type="text" id="txt_jbgz" name="jbgz" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.JBGZ}"  pattern=".00"/>"/>
						</div>
                    </div>
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">技术等级工资（元）</span>
							<input type="text" id="txt_jsdjgz" name="jsdjgz" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.JSDJGZ}"  pattern=".00"/>"/>
						</div>
                    </div>
				
				
				 <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">试用期工资（元）</span>
							<input type="text" id="txt_syqgz" name="syqgz" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.SYQGZ}"  pattern=".00"/>"/>
						</div>
                    </div>
              </div>
                <div class="row">
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">按月开支津贴补贴合计（元）</span>
							<input type="text" id="txt_aykzjbthj" name="aykzjtbthj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.AYKZJTBTHJ}"  pattern=".00"/>"/>
						</div>
                    </div>
                     
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">省级干部自雇费（元）</span>
							<input type="text" id="txt_sjgbzgf" name="sjgbzgf" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.SJGBZGF}"  pattern=".00"/>"/>
						</div>
                    </div>
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">独生子女补贴（元）</span>
							<input type="text" id="txt_dsznbt" name="dsznbt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.DSZNBT}"  pattern=".00"/>"/>
						</div>
                    </div>
				</div>
				<div class="row">
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">少数民族补贴（元）</span>
							<input type="text" id="txt_ssmzbt" name="ssmzbt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.SSMZBT}"  pattern=".00"/>"/>
						</div>
                    </div>
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">其他特殊岗位津贴（元）</span>
							<input type="text" id="txt_qttsgwjt" name="qttsgwjt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.QTTSGWJT}"  pattern=".00"/>"/>
						</div>
                    </div>                    				
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">基础性绩效工资（元）</span>
							<input type="text" id="txt_jcxjxgz" name="jcxjxgz" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.JCXJXGZ}"  pattern=".00"/>"/>
						</div>
                    </div>
                    </div>
				<div class="row">
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">住房补贴（元）</span>
							<input type="text" id="txt_zfbt" name="zfbt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.ZFBT}"  pattern=".00"/>"/>
						</div>
                    </div>                    				
				 <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">住宅物业服务补贴（元）</span>
							<input type="text" id="txt_zzwyfwbt" name="zzywfwbt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.ZZYWFWBT}"  pattern=".00"/>"/>
						</div>
                    </div>
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">无房户补贴（元）</span>
							<input type="text" id="txt_wfhbt" name="wfhbt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.WFHBT}"  pattern=".00"/>"/>
						</div>
                    </div>
                    </div>
				<div class="row">
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">按规定发放的其他改革性补贴（元）</span>
							<input type="text" id="txt_agdffdqtggxbt" name="agdffdqtggxbt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.AGDFFDQTGGXBT}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">住宅采暖补贴（元）</span>
							<input type="text" id="txt_zzcnbt" name="zzcnbt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.ZZCNBT}"  pattern=".00"/>"/>
						</div>
                    </div>                    
				
				 <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">奖励性绩效工资（元）</span>
							<input type="text" id="txt_jlxjxgz" name="jlxjxgz" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.JLXJXGZ}"  pattern=".00"/>"/>
						</div>
                    </div>
                    </div>
				<div class="row">
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">精神文明奖（元）</span>
							<input type="text" id="txt_jswmj" name="jswmj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.JSWMJ}"  pattern=".00"/>"/>
						</div>
                    </div>
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">经批准的绩效工资上浮部分（元）</span>
							<input type="text" id="txt_jpzdjxgzsfbf" name="jpzdjxgzsfbf" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.JPZDJXGZSFBF}" pattern=".00"/>"/>
						</div>
                    </div>                    
				
				 <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">其他按年发放项目（元）</span>
							<input type="text" id="txt_qtanffxm" name="qtanffxm" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.QTANFFXM}"  pattern=".00"/>"/>
						</div>
                    </div>
                    </div>
				<div class="row">
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">按月缴纳项目合计（元）</span>
							<input type="text" id="txt_ayjnxmhj" name="ayjnxmhj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.AYJNXMHJ}"  pattern=".00"/>"/>
						</div>
                    </div>                    
				
				 <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">住房公积金（元）</span>
							<input type="text" id="txt_zfgjj" name="zfgjj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.ZFGJJ}"  pattern=".00"/>" />
						</div>
                    </div>
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">失业保险（元）</span>
							<input type="text" id="txt_sybx" name="sybx" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.SYBX}"  pattern=".00"/>"/>
						</div>
                    </div>
                    </div>
				<div class="row">
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">职业年金（元）</span>
							<input type="text" id="txt_zynj" name="zynj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.ZYNJ}"  pattern=".00"/>"/>
						</div>
                    </div>                    
				
				 <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">养老保险（元）</span>
							<input type="text" id="txt_ylbx" name="ylbx" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.YLBX}"  pattern=".00"/>"/>
						</div>
                    </div>
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">医疗保险（含工伤、生育保险）（元）</span>
							<input type="text" id="txt_ylbxhgssy" name="ylbxhgssy" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.YLBXHGSSY}"  pattern=".00"/>"/>
						</div>
                    </div>
                    </div>
				<div class="row">
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">按年缴纳项目合计（元）</span>
							<input type="text" id="txt_anjnxmhj" name="anjnxmhj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.ANJNXMHJ}"  pattern=".00"/>"/>
						</div>
                    </div>                    
				
				 <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">大病保险（元）</span>
							<input type="text" id="txt_dbbx" name="dbbx" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.DBBX}"  pattern=".00"/>"/>
						</div>
                    </div>
                     <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">其他保险（元）</span>
							<input type="text" id="txt_qtbx" name="qtbx" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.QTBX}"  pattern=".00"/>"/>
						</div>
                    </div>
                          
				</div>
				<div class="row">
				                 
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">上浮缴纳公积金和社会保障缴费（元）</span>
							<input type="text" id="txt_sfjngjjhshbzjf" name="sfjngjjhshbzjf" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.SFJNGJJHSHBZJF}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">单位从公用经费中开支项目合计（元）</span>
							<input type="text" id="txt_dwcgyjfzkzxmhj" name="dwcgyjfzkzxmhj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.DWCGYJFZKZXMHJ}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                
				<div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">按月发放项目合计（元）</span>
							<input type="text" id="txt_ayffxmhj" name="ayffxmhj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.AYFFXMHJ}"  pattern=".00"/>" />
						</div>
                    </div> 
                    </div>  
                <div class="row">                   
                    <div class="col-md-4">
					   <div class="input-group">
					      <span class="input-group-addon"><span class="required">*</span>来源类型</span>
					        <select id="drp_xb" class="form-control input-radius select2" name="lylx"> 
					           <c:forEach var="lylxList" items="${lylxList}">
	                        		<option value="${lylxList.DM}" <c:if test="${jsxx.LYLX == lylxList.DM}">selected</c:if>>${lylxList.MC}</option>
	                        
	                        	</c:forEach>
	                        </select>	
					   </div>
					 </div>                    
                       
                
				<div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">国家和省级规定的津补贴合计（元）</span>
							<input type="text" id="txt_gjhsjgddjbthj" name="gjhsjgddjbthj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.GJHSJGDDJBTHJ}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                                      
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">批复的绩效工资（元）</span>
							<input type="text" id="txt_pfdjxgz" name="pfdjxgz" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.PFDJXGZ}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                </div> 
                <div class="row">
				<div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">绩效工资上浮部分（元）</span>
							<input type="text" id="txt_jxgzsfbf" name="jxgzsfbf" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.JXGZSFBF}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">年度项目总计（元）</span>
							<input type="text" id="txt_ndxmzj" name="ndxmzj" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.NDXMZJ}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">财政保障控制数计算基数</span>
							<input type="text" id="txt_czbzkzsjsjs" name="czbzkzsjsjs" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.CZBZKZSJSJS}"  pattern=".00"/>" />
						</div>
                    </div>                    
                </div> 
                
                 <div class="row">
				<div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">警衔津贴，警察执勤（元）</span>
							<input type="text" id="txt_jxjtjczq" name="jxjtjczq" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.JXJTJCZQ}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">检察官检查、检察院办案人员补贴（元）</span>
							<input type="text" id="txt_jcgjcjcybarybt" name="jcgjcjcybarybt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.JCGJCJCYBARYBT}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">纪检、监察机关办案补贴（元）</span>
							<input type="text" id="txt_jjjcjgbabt" name="jjjcjgbabt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.JJJCJGBABT}"  pattern=".00"/>"  />
						</div>
                    </div>                    
                </div>
                <div class="row">
				<div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">国家税务工作人员税务征收津贴（元）</span>
							<input type="text" id="txt_gjswgzryswzsjt" name="gjswgzryswzsjt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.GJSWGZRYSWZSJT}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">审计机关审计人员工作补贴（元）</span>
							<input type="text" id="txt_sjjgsjrygzbt" name="sjjgsjrygzbt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.SJJGSJRYGZBT}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">密码人员岗位津贴（元）</span>
							<input type="text" id="txt_mmrygwjt" name="mmrygwjt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.MMRYGWJT}"  pattern=".00"/>"  />
						</div>
                    </div>                    
                </div> 
                 <div class="row">
				<div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">法医、毒物化验保健津贴（元）</span>
							<input type="text" id="txt_fydwhybjjt" name="fydwhybjjt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.FYDWHYBJJT}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">信访工作人员岗位津贴（元）</span>
							<input type="text" id="txt_xfgzrygwjt" name="xfgzrygwjt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.XFGZRYGWJT}"  pattern=".00"/>"/>
						</div>
                    </div>                    
                    <div class="col-md-4">
				 	       <div class="input-group">
							<span class="input-group-addon">法官审判、法院办案人员津贴（元）（元）</span>
							<input type="text" id="txt_fgspfybaryjt" name="fgspfybaryjt" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="<fmt:formatNumber value="${jsxx.FGSPFYBARYJT}"  pattern=".00"/>" />
						</div>
                    </div>                    
                </div> 
                
                 <div class="row">
                    <div class="col-md-4">
				        <div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
							<input type="text" id="txt_bz" name="bz" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${jsxx.BZ}"/>
						</div>
                    </div> 
                 </div>
                 <div class="container-fluid box-content" id="group_panel">
				<div class="row">
				<div class="pull-center" style="text-align:center;">
            		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
            		<button type="button" class="btn btn-default" id="btn_fin" style="background:#00acec;color: white;">完成</button>
        		</div>
			</div>
			</div>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var operate = "${operateType}";
var tag = false;
$(function(){
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
$(function(){
	//联想输入提示
	$("#txt_szdw").bindChange("${ctx}/suggest/getXx","D");
	//单位弹窗 
	$("#btn_szdw").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_szdw","单位信息","920","630");
    });
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
         
          gzgxblry:{validators: {notEmpty: {message: '工资关系保留人员不能为空'}}},
          gzlb:{validators: {notEmpty: {message: '工资类别不能为空'}}},
          lylx:{validators:{notEmpty:{message: '来源类型不能为空'}}},
          }
	     });
	//保存
	$("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/jsxxs/doSave1",function(val){
			if(val.success){
				tag = true;
				alert(val.msg);
			}
		});
	}); 
//完成
   $("#btn_fin").click(function(){
	   if(tag){
		   alert("操作成功！");
		   window.location.href = "${ctx}/jsxxs/goJsxxListPage";
	   }else{
		   doSave(validate,"myform","${ctx}/jsxxs/doSave1",function(val){
				if(val.success){
					tag = true;
					alert(val.msg);
					window.location.href = "${ctx}/jsxxs/goJsxxListPage";
				}
			});
	   }
	}); 
 //返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/jsxxs/goJsxxListPage";
	});
   //上一步
     $("#btn_after").click(function(){
    	if(tag){
    		window.location.href = "${ctx}/jsxxs/before?guid="+$("[name='guid']").val()+"&jzgguid="+$("[name='jzgguid']").val();
    	}else{
    		doSaveFb(validate,"myform","${ctx}/jsxxs/doSave1",function(val){
				if(val.success){
					tag = true;
					window.location.href = "${ctx}/jsxxs/before?guid="+$("[name='guid']").val()+"&jzgguid="+$("[name='jzgguid']").val();
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

//弹窗
$("#btn_xmbh").click(function(){
		select_commonWin("${ctx}/wsbx/rcbx/window?type=xm&controlId=txt_xmbh","信息","920","630");
    });
</script>
</body>
</html>