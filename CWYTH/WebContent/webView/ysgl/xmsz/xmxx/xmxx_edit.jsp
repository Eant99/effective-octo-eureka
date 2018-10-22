<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>编辑项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
/* 	.radio-group{ */
/* 		height: 25px!important; */
/* 		line-height: 25px; */
/* 		vertical-align: middle; */
/* 		display: inline-block; */
/* 	} */
/* 	.radio-group > .rdo{ */
/* 		margin: 0px 0px 0px 10px; */
/* 		height: 25px; */
/* 	} */

.tables {
    margin-left: 20px;
    max-width: 100%;
    margin-bottom: 20px;
    width:50%;
   }
 .p1{
    text-align:center;
    font-family:宋体;
    color:red;
    font-size:18px;
   }
   .aa{
text-align:right;
}
[class^=addBtn]{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	[class^=addBtn]:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	}
	[class^=deleteBtn]{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	[class^=deleteBtn]:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
	    position: relative;
	    cursor: pointer;
/* 	    top:3px; */
	}
 [class^=td_first_]{
 	text-align:center;
 	width:100%;
 	height:30px;
 }
 [class^=td_second_]{
 	width:100%;
 	height:30px;
 }
 [class^=td_three_]{
 	width:100%;
 	height:30px;
 }
 [class$=first_2],[class$=first_1],[class$=first_3]{
 width:20%;
 }
 [class$=second_1],[class$=second_2],[class$=second_3]{
 width:30%;
 }
 [class$=operation_1],[class$=operation_2],[class$=operation_3]{
 width:10%;
 }
 .td_input{
 	width:100%;
 	border:none;
 }
 a{
	 text-decoration:none;
 }
 .addBtn0,.addBtn1,.addBtn2,.addBtn3{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn0:after,.addBtn1:after,.addBtn2:after,.addBtn3:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	}
	.delBtn1,.delBtn2,.delBtn3{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.delBtn1:after,.delBtn2:after,.delBtn3:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
	    position: relative;
	    cursor: pointer;
/* 	    top:3px; */
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
<div id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="czr"  value="${loginId}">
	<input type="hidden" name="czrq" value=""/>
	<input type="hidden" id="txt_xmbh" name="xmbh" value="${xmbh }">
	       <input type="hidden" id="txt_xmmc" name="xmmc" value="${xmmc }">
	        <input type="hidden" id="txt_bmmc" name="bmmc" value="${bmmc }">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑项目信息</span>
		</div>
        <div class="pull-right">
            <button type="button" class="btn btn-default" id="btn_save" >保存</button>
            <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	
	<div class="box" style="top:36px">
	<form id="myform1">
	     <input type="hidden" id="guid" name="guid" value="${guid}">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>编辑项目基本信息</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>部门名称</span>
							<input type="text" id="txt_bmbh" value="${xmxx.BMBH }" name="bmbh" class="form-control input-radius window" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入部门名称"/>
						    <span class="input-group-btn"><button type="button" id="btn_bmbh" class="btn btn-link">选择</button></span>
						    
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目编号</span>
							<input type="text" id="txt_xmbh" name="xmbh"  value="${xmxx.XMBH }" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入项目编号"/>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目名称</span>
							<input type="text" id="txt_xmmc" name="xmmc" value="${xmxx.XMMC }" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入项目名称" />
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目大类</span>
                            <select id="txt_xmdl" class="form-control input-radius select2" name="xmdl">
                            	<option value="">未选择</option>
                               <c:forEach var="item" items="${xmdlList}">
                           			<option value="${item.DM}"  <c:if test="${xmxx.XMDL==item.DM }">selected</c:if> >${item.MC}</option>
                            	</c:forEach>
                            </select>
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目类别</span>
                            <select id="txt_xmlb" class="form-control input-radius select2" name="xmlb">
                            	<option value="">未选择</option>
                               <c:forEach var="item" items="${xmlbList}">
                           			<option value="${item.DM}" <c:if test="${xmxx.XMLB==item.DM }">selected</c:if> >${item.MC}</option>
                            	</c:forEach>
                            </select>
                        </div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目类型</span>
							<input type="text" id="txt_xmlx" value="${xmxx.XMLX }" name="xmlx" class="form-control input-radius window" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择项目类型"/>
							<input type="hidden" id="txt_xmlxid" value="${xmxx.XMLXID }" name="xmlxid" class="form-control input-radius " style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"/>
						    <span class="input-group-btn"><button type="button" id="btn_xmlx" class="btn btn-link">选择</button></span>
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>负责人</span>
							<input type="text" id="txt_fzr" value="${xmxx.FZRMC }" name="fzr" class="form-control input-radius window" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择负责人" />
<%-- 							<input type="text" id="txt_fzr" value="${xmxx.FZR }" name="fzr" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择负责人" /> --%>
							
							<span class="input-group-btn"><button type="button" id="btn-fzr" class="btn btn-link">选择</button></span>
						</div>
                    </div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">项目属性</span>
                            <select id="txt_xmsx" class="form-control input-radius select2" name="xmsx">
                           		<option value="01" <c:if test="${xmxx.XMSX=='01' }">selected</c:if> >部门经费</option>
                           		<option value="02" <c:if test="${xmxx.XMSX=='02' }">selected</c:if> >个人经费</option>
                            
                            </select>
                        </div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>归口部门</span>
						
							<input type="text" id="txt_gkbm" name="gkbm" value="${xmxx.GKBMMC }" class="form-control window input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择归口部门" />
<%-- 						    <input type="text" id="txt_gkbm" name="gkbm" value="${xmxx.GKBM }" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择归口部门" /> --%>
						    
							<span class="input-group-btn"><button type="button" id="btn_gkbm" class="btn btn-link">选择</button></span>
						</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;">是否启用</span>
							<div class="radiodiv">
<!-- 							<span class="radio-group"> -->
<%-- 								<input type="radio" name="sfqy" class="rdo" value="1" <c:if test="${xmxx.SFQY=='1' }"> checked</c:if>/> --%>
<!-- 								<span style="float: right">是</span> -->
<!-- 							</span> -->
<!-- 							<span class="radio-group"> -->
<%-- 								<input type="radio" name="sfqy" class="rdo" value="0"  <c:if test="${xmxx.SFQY=='0' }"> checked</c:if> /> --%>
<!-- 								<span style="float: right">否</span> -->
<!-- 							</span> -->
                 &nbsp; &nbsp;
								<input type="radio" name="sfqy" class="rdo" value="1" <c:if test="${xmxx.SFQY=='1' }"> checked</c:if>/>
								<label>是</label>
						
							 &nbsp; &nbsp; &nbsp;
								<input type="radio" name="sfqy" class="rdo" value="0"  <c:if test="${xmxx.SFQY=='0' }"> checked</c:if> />
								<label>否</label>
							
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;">是否完工</span>
							<div class="radiodiv">
<!-- 							<span class="radio-group"> -->
<%-- 								<input type="radio" name="sfwg" class="rdo" value="1" <c:if test="${xmxx.SFWG=='1' }"> checked</c:if> /> --%>
<!-- 								<span style="float: right">是</span> -->
<!-- 							</span> -->
<!-- 							<span class="radio-group"> -->
<%-- 								<input type="radio" name="sfwg" class="rdo" value="0" <c:if test="${xmxx.SFWG=='0' }"> checked</c:if> /> --%>
<!-- 								<span style="float: right">否</span> -->
<!-- 							</span> -->
                                &nbsp; &nbsp;
								<input type="radio" name="sfwg" class="rdo" value="1" <c:if test="${xmxx.SFWG=='1' }"> checked</c:if> />
								<label>是</label>
							
							     &nbsp; &nbsp; &nbsp;
								<input type="radio" name="sfwg" class="rdo" value="0" <c:if test="${xmxx.SFWG=='0' }"> checked</c:if> />
									<label>否</label>
							
							</div>
						</div>
					</div>							
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>开始时间</span>
							<input type="text" id="txt_kssj" name="kgrq" class="form-control input-radius date window" 
							style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value="${xmxx.KGRQ }" placeholder="请输入开始时间" />
						</div>
                    </div>
				</div>
				<div class="row">
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;"><span class="required">*</span>结束时间</span>
							<input type="text" id="txt_jssj" name="wgrq" class="form-control input-radius date window"  
							style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value="${xmxx.WGRQ }"  placeholder="请输入结束时间" />
						</div>							
					</div>							
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;"><span class="required">*</span>预算金额（元）</span>
							<input type="text" id="txt_xmmc" name="ysje" class="form-control input-radius number"  
							style="border-bottom-right-radius: 4px;text-align:right;border-top-right-radius: 4px;" value="${xmxx.YSJE }"   />
						</div>
					</div>							
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>剩余金额（元）</span>
							<input type="text" id="txt_xmmc" name="syje" class="form-control input-radius number" 
							style="border-bottom-right-radius: 4px;text-align:right;border-top-right-radius: 4px;" value="${xmxx.SYJE }"   />
						</div>
                    </div>
				</div>
				<div class="row">
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>是否科研</span> -->
<!-- 							<div class="radiodiv">&nbsp; &nbsp; -->
<%-- 								<input type="radio" name="sfky" class="rdo" value="1" <c:if test="${xmxx.sfky=='1' }"> checked</c:if> /> --%>
<!-- 								 <label >是</label> &nbsp; &nbsp; &nbsp; -->
<%-- 								<input type="radio" name="sfky" class="rdo" value="0" <c:if test="${xmxx.sfky=='0' }"> checked</c:if> /> --%>
<!-- 								 <label >否</label> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!--                     </div> -->
<!--                     <div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>会计科目允许禁止</span> -->
<!-- 							<div class="radiodiv">&nbsp; &nbsp; -->
<%-- 								<input type="radio" name="kjkmyxjz" class="rdo" value="1" <c:if test="${xmxx.kjkmyxjz=='1' }"> checked</c:if> /> --%>
<!-- 								 <label >允许</label> &nbsp; &nbsp; &nbsp; -->
<%-- 								<input type="radio" name="kjkmyxjz" class="rdo" value="0" <c:if test="${xmxx.kjkmyxjz=='0' }"> checked</c:if> /> --%>
<!-- 								 <label >禁止</label> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!--                     </div> -->
<!--                     <div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>经济科目允许禁止</span> -->
<!-- 							<div class="radiodiv">&nbsp; &nbsp; -->
<%-- 								<input type="radio" name="jjkmyxjz" class="rdo" value="1" <c:if test="${xmxx.jjkmyxjz=='1' }"> checked</c:if> /> --%>
<!-- 								 <label >允许</label> &nbsp; &nbsp; &nbsp; -->
<%-- 								<input type="radio" name="jjkmyxjz" class="rdo" value="0" <c:if test="${xmxx.jjkmyxjz=='0' }"> checked</c:if> /> --%>
<!-- 								 <label >禁止</label> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!--                     </div> -->
                        <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>项目经费是否允许超标</span>
							<div class="radiodiv">&nbsp; &nbsp;
								<input type="radio" name="xmjfsfyxcb" class="rdo" value="1" <c:if test="${xmxx.xmjfsfyxcb=='1' }"> checked</c:if>/>
								 <label >是</label> &nbsp; &nbsp; &nbsp;
								<input type="radio" name="xmjfsfyxcb" class="rdo" value="0" <c:if test="${xmxx.xmjfsfyxcb=='0' }"> checked</c:if> />
								 <label >否</label>
							</div>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; ">超标比例（百分比）</span>
							<input type="text" id="txt_cbbl" name="cbbl" class="form-control input-radius" value="${xmxx.cbbl }" onKeyUp="this.value=this.value.replace(/[^\d\.]/g,'')"
							style="border-bottom-right-radius: 4px;text-align:right;border-top-right-radius: 4px;"  />
						</div>
                    </div>
                     <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>是否进行经济分类费用控制</span>
							<div class="radiodiv">&nbsp; &nbsp;
								<input type="radio" name="sfjxjjflfykz" class="rdo" value="1" <c:if test="${xmxx.sfjxjjflfykz=='1' }"> checked</c:if>/>
								 <label >是</label> &nbsp; &nbsp; &nbsp;
								<input type="radio" name="sfjxjjflfykz" class="rdo" value="0" <c:if test="${xmxx.sfjxjjflfykz=='0' }"> checked</c:if> />
								 <label >否</label>
							</div>
						</div>
                    </div>
                </div>
                 <div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>政府采购金额（元）</span>
							<input type="text" id="txt_zfcgje" name="zfcgje" class="form-control input-radius number" 
							style="border-bottom-right-radius: 4px;text-align:right;border-top-right-radius: 4px;" value="${xmxx.zfcgje }" />
							<input type="hidden" id="txt_zfcgsyje" name="zfcgsyje" class="form-control input-radius number"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>非政府采购金额（元）</span>
							<input type="text" id="txt_fzfcgje" name="fzfcgje" class="form-control input-radius number" 
							style="border-bottom-right-radius: 4px;text-align:right;border-top-right-radius: 4px;"  value="${xmxx.fzfcgje }"/>
							<input type="hidden" id="txt_fzfcgsyje" name="fzfcgsyje" class="form-control input-radius number"/> 
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">上级项目</span>
							<c:choose>
								<c:when test="${xmxx.sjxm == '()' }">
									<input type="text" id="txt_ktmc" name="ktmc" class="form-control input-radius window" value="" placeholder="请选择项目名称" />
								</c:when>
								<c:otherwise>
									<input type="text" id="txt_ktmc" name="ktmc" class="form-control input-radius window" value="${xmxx.sjxm }" placeholder="请选择项目名称" />
								</c:otherwise>
							</c:choose>
                            <input type="hidden" id="txt_jfbh" name="jfbh" value="${xmxx.jfbh }"/>
                            <span class="input-group-btn">
                           		 <button type="button" id="btn-ktmc"  class="btn btn-link btn-ktmc">选择</button>
                            </span>
						</div>
                    </div>
                </div>
			</div>
         </div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>添加项目控制信息</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
<!--                     <div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">预算类型</span> -->
<!-- <!-- 							<input type="text" id="txt_yslx" name="yslx" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择预算类型" /> --> 
<!-- <!-- 							<span class="input-group-btn"><button type="button" id="btn-yslx" class="btn btn-link">选择</button></span> --> 
<!-- 								<select id="txt_xmsx" class="form-control input-radius select2" name="yslx"> -->
<!-- 	                            	<option value="">未选择</option> -->
<%-- 	                               <c:forEach var="item" items="${yslxList}"> --%>
<%-- 	                           			<option value="${item.DM}" <c:if test="${xmxx.YSLX==item.DM }"> selected</c:if>>${item.MC}</option> --%>
<%-- 	                            	</c:forEach> --%>
<!-- 	                            </select> -->
<!-- 						</div> -->
<!--                     </div> -->
					<div class="col-md-4">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;">是否财政支出</span>
							<div class="radiodiv">
							 &nbsp; &nbsp; 
								<input type="radio" name="sfczzc" class="rdo cz" value="1" <c:if test="${xmxx.SFCZZC=='1' }"> checked</c:if>/>
								<label>是</label>
						
							 &nbsp; &nbsp; &nbsp;
								<input type="radio" name="sfczzc" class="rdo cz" value="0" <c:if test="${xmxx.SFCZZC=='0' }"> checked</c:if> />
								<label>否</label>
						
							</div>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;">是否国库</span>
							<div class="radiodiv">
							&nbsp; &nbsp; 
								<input type="radio" name="sfgk" class="rdo" value="1" <c:if test="${xmxx.SFGK=='1' }"> checked</c:if>/>
								<label>是</label>
							
							&nbsp; &nbsp; &nbsp;
								<input type="radio" name="sfgk" class="rdo" value="0" <c:if test="${xmxx.SFGK=='0' }"> checked</c:if> />
								<label>否</label>
							</div>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">国库信息码</span>
							<input type="text" id="txt_gkxxm" name="gkxxm" value="${xmxx.GKXXM }" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入国库信息码" />
						</div>
                    </div>
				</div>
			</div>
         </div>
         </form>
          <form id="table1" class="myform myformPzby" id="editsr">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>会计科目控制</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody class="tbody1">
        				<tr >
<!-- 					    	<td class="td_first_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		操作 -->
<!-- 					    	</td> -->
<td class="td_first_operation_1" style="text-align:center;" valign="middle">
					    		操作
					    	</td>
							<td class="td_first_first_1" style="text-align:center;" valign="middle">
					    		预算类型
					    	</td>
					    	<td class="td_first_second_1" style="text-align:center;" valign="middle">
					    		科目编号
					    	</td>
					    	<td class="td_first_three_1"  style="text-align:center;" valign="middle">
					    		科目名称
					    	</td>
				   		</tr>
				   		<c:forEach var="srmap" items="${srmap }">
        			 	<tr id="pbtr_1" class="pdtr_1 pd_tr1">
<!-- 					    	<td class="td_first_1"> -->
<!-- 					    		<div class="addBtn1 add1"></div> -->
<!-- 					    	</td> -->
<td class="td_second_operation_2" style="text-align:center;" valign="middle">
					    		<div class="deleteBtn_jb"></div>
					    	</td>
							<td class="td_second_first_2" style="text-align:center;" valign="middle">
					    		基本支出
					    	</td>
					    	<td class="td_second_second_2">
					    		<div class="input-group">
						    	    <input type="text"  readonly="readonly" style="width:100%;border:none;text-align:left;" class="aa" name="kmbh" id="txt_srkbh" value="${srmap.jbzcsrkmbh}" />
						    	    <input type="hidden" name="xmbh" id="txt_xmbh" class="a" value="${guid }">
						    	    <span class="input-group-btn">
	  				    				<button type="button" id="btn_srkm" class="btn btn-link btn-custom">选择</button>
	 				    			</span> 
 				    			</div>
					    	</td>
					    	<td class="td_second_three_2">
					    		<input type="text" readonly="readonly" style="width:100%;border:none;" name="kmmc" id="txt_srkm" value="${srmap.jbzckmmc}" />
					    	</td>
				   		</tr>
				   		<tr id="pbtr_2" class="pdtr_2 pd_tr2">
<!-- 					    	<td class="td_first_1"> -->
<!-- 					    		<div class="addBtn1 add1"></div> -->
<!-- 					    	</td> -->
<td class="td_second_operation_2" style="text-align:center;" valign="middle">
					    		<div class="deleteBtn_xm"></div>
					    	</td>
							<td class="td_three_first_3" style="text-align:center;" valign="middle">
					    		项目支出
					    	</td>
					    	<td class="td_three_second_3">
					    		<div class="input-group">
						    	    <input type="text" readonly="readonly" style="width:100%;border:none;text-align:left;" class="aa" name="kmbh2" id="txt_srkbh2" value="${srmap.xmzcsrkmbh}">
						    	    <input type="hidden" name="xmbh2" id="txt_xmbh2" class="a" value="${guid }">
						    	    <span class="input-group-btn" >
	  				    				<button type="button" id="btn_srkm2" class="btn btn-link btn-custom">选择</button>
	 				    			</span> 
 				    			</div>
					    	</td>
					    	<td class="td_three_three_3">
					    		<input type="text" readonly="readonly" style="width:100%;border:none;" name="kmmc2" id="txt_srkm2" value="${srmap.xmzckmmc}"/>
					    	</td>
				   		</tr>
				   		</c:forEach>
        			</tbody>
				</table>
		 </div>
		 </div>
		 </div>
		 </form>
		 <%-- <!-- 支出 -->
		 <form id="table2" class="myform myformPzby" mc="editzc">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>支出科目</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody class="tbody2">
        				<tr >
					    	<td class="td_first_1"  style="text-align:center;" valign="middle">
					    		操作
					    	</td>
					    	<td class="td_second_1" style="text-align:center;" valign="middle">
					    		科目编号
					    	</td>
					    	<td class="td_three_1"  style="text-align:center;" valign="middle">
					    		科目名称
					    	</td>
				   		</tr>
				   		<c:choose>
				   			<c:when test="${not empty zcmap }">
				   				<c:forEach var="zcma" items="${zcmap}">
        			 	<tr id="zctr_1" class="zctr_11">
					    	<td class="td_first_1">
					    		<div class="addBtn2 add2"></div>
					    	</td>
					    	<td class="td_second_1">
					    	
					    	    <input type="text" style="width:100%;border:none;" class="bb" name="kmbh" id="txt_zckbh" value="${zcma.ZCKMBH }">
					    	    <input type="hidden" style="width:20%;border:none;" name="zcguid" id="txt_zcguid" value="${zcma.GUID }" >
					    	     <input type="hidden" name="xmbh" id="txt_xmbh" class="b" value="${guid }">
<!-- 					    	    <span class="input-group-btn"> -->
<!--  				    			<button type="button" id="btn_zckm" bh="txt_zckbh" mc="txt_zckm" class="btn btn-link btn-custom">选择</button> -->
<!--  				    			</span> -->
 				    		
					    	</td>
					    	<td class="td_three_1">
					    		<input type="text" style="width:100%;border:none;" name="kmmc" id="txt_zckm" value="${zcma.KMMC }" />
					    	</td>
				   		</tr>
				   		</c:forEach>
				   			</c:when>
				   			<c:otherwise>
				   			
				   				
        			 	<tr id="zctr_1" class="zctr_11 zc_1">
					    	<td class="td_first_1">
					    		<div class="addBtn2 add2"></div>
					    	</td>
					    	<td class="td_second_1">
					    	 
					    	    <input type="text" style="width:100%;border:none;" class="bb" name="kmbh" id="txt_zckbh" value="">
					    	    <input type="hidden" style="width:20%;border:none;" name="zcguid" id="txt_zcguid" value="" >
					    	     <input type="hidden" name="xmbh" id="txt_xmbh" class="b" value="${guid }">
<!-- 					    	    <span class="input-group-btn"> -->
<!--  				    			<button type="button" id="btn_zckm" bh="txt_zckbh" mc="txt_zckm" class="btn btn-link btn-custom">选择</button> -->
<!--  				    			</span> -->
<!--  				    			</div> -->
					    	</td>
					    	<td class="td_three_1">
					    		<input type="text" style="width:100%;border:none;" name="kmmc" id="txt_zckm" value="" />
					    	</td>
				   		</tr>
				   		
				   			</c:otherwise>
				   		</c:choose>
				   		
        			</tbody>
				</table>
		 </div>
		 </div>
		 </div>
		 </form> --%>
		 <!-- 经济分类 -->
<!-- 		  <form id="table3" class="myform myformPzby" mc="editjjfl"> -->
<!-- 			<div class="box-panel"> -->
<!-- 			<div class="box-header clearfix"> -->
<!--             	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>经济科目控制</div> -->
<!--             	<div class="actions"> -->
<!--             		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a> -->
<!--             	</div> -->
<!--         	</div> -->
<!-- 			<hr class="hr-normal"> -->
<!-- 			<div class="container-fluid box-content"> -->
<!--         	<div class="row"> -->
<!--         			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse"> -->
<!--         			<tbody class="tbody3"> -->
<!--         				<tr > -->
<!-- <!-- 					    	<td class="td_first_1"  style="text-align:center;" valign="middle"> --> 
<!-- <!-- 					    		操作 --> 
<!-- <!-- 					    	</td> --> 
<!-- 					    	<td class="td_second_1" style="text-align:center;" valign="middle"> -->
<!-- 					    		科目编号 -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_three_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		科目名称 -->
<!-- 					    	</td> -->
<!-- 				   		</tr> -->
<%-- 				   		<c:choose> --%>
<%-- 				   			<c:when test="${not empty jjflmap }"> --%>
<%-- 				   				<c:forEach var="jjflmap" items="${jjflmap }"> --%>
<!--         			 	<tr id="jjfltr_1" class="jjfltr_11"> -->
<!-- <!-- 					    	<td class="td_first_1"> --> 
<!-- <!-- 					    		<div class="addBtn3 add3"></div> --> 
<!-- <!-- 					    	</td> --> 
					  
<!-- 					    	<td class="td_second_1"> -->
<!-- 					    		<div class="input-group"> -->
<%-- 						    	    <input type="text" style="width:100%;border:none;" class="cc" name="kmbh" id="txt_jjflkbh" value="${ jjflmap.JJFL}"> --%>
<%-- 						    	    <input type="hidden" style="width:20%;border:none;" name="jjflguid" id="txt_jjflguid" value="${jjflmap.GUID }"> --%>
<%-- 						    	    <input type="hidden" name="xmbh" id="txt_xmbh" class="c" value="${guid }"> --%>
<!-- 						    	    <span class="input-group-btn"> -->
<!-- 	 				    				<button type="button" id="btn_jjflkm" class="btn btn-link btn-custom">选择</button> -->
<!-- 	 				    			</span> -->
<!--  				    			</div>	 -->
<!-- 					    	</td> -->
					    
<!-- 					    	<td class="td_three_1"> -->
<%-- 					    		<input type="text" style="width:100%;border:none;" name="kmmc" id="txt_jjflkm" value="${jjflmap.KMMC }" /> --%>
<!-- 					    	</td> -->
<!-- 				   		</tr> -->
<%-- 				   		</c:forEach> --%>
<%-- 				   			</c:when> --%>
<%-- 				   			<c:otherwise> --%>
<!--         			 	<tr id="jjfltr_1" class="jjfltr_11 jjfl_1"> -->
<!-- <!-- 					    	<td class="td_first_1"> --> 
<!-- <!-- 					    		<div class="addBtn3 add3"></div> --> 
<!-- <!-- 					    	</td> --> 
					  
<!-- 					    	<td class="td_second_1"> -->
<!-- 					    		<div class="input-group"> -->
<!-- 						    	    <input type="text" style="width:100%;border:none;" class="cc" name="kmbh" id="txt_jjflkbh" value=""> -->
<!-- 						    	    <input type="hidden" style="width:20%;border:none;" name="jjflguid" id="txt_jjflguid" value=""> -->
<%-- 						    	    <input type="hidden" name="xmbh" id="txt_xmbh" class="c" value="${guid }"> --%>
<!-- 						    	    <span class="input-group-btn"> -->
<!-- 	 				    				<button type="button" id="btn_jjflkm" class="btn btn-link btn-custom">选择</button> -->
<!-- 	 				    			</span> -->
<!-- 	 				    		</div> -->
<!-- 					    	</td> -->
					    
<!-- 					    	<td class="td_three_1"> -->
<!-- 					    		<input type="text" style="width:100%;border:none;" name="kmmc" id="txt_jjflkm" value="" /> -->
<!-- 					    	</td> -->
<!-- 				   		</tr> -->
<%-- 				   			</c:otherwise> --%>
<%-- 				   		</c:choose> --%>
				   		
<!--         			</tbody> -->
<!-- 				</table> -->
<!-- 		 </div> -->
<!-- 		 </div> -->
<!-- 		 </div> -->
<!-- 		 </form> -->
	</div>
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">

	var target = "${ctx}/ysgl/xmsz/xmxx";
	var xmym = "${xmym}";
//保存方法
// addbuttonsr();
	addbuttonzc();
// 	addbuttonjjfl();
	function addbuttonsr(){
		var a = $(".tbody1").find("tr").length;
		var aaa="${srmap}";
		
		if(aaa.length>2){
			$(".add1").removeClass("addBtn1")
			
			$(".add1").addClass("delBtn1");		
			var $parentTr = $(".tbody1 tr:last").clone();
			$parentTr.find("input:not(.a)").val("");
			
			$(".tbody1 tr:last").after($parentTr);
			
			$(".tbody1 tr:last").find(".add1").removeClass("delBtn1");
			$(".tbody1 tr:last").find(".add1").addClass("addBtn1");
			$(".tbody1 tr:last").addClass("pd_tr1");
		}else{
			
		}
	}
	
	 $("#txt_bmbh").bindChange("${ctx}/suggest/getXx","CD");
	 $("#txt_fzr").bindChange("${ctx}/suggest/getXx","R");
	 $("#txt_xmlx").bindChange("${ctx}/suggest/getXx","XMLX",null,null,function(){
		 var xmbh = $("#txt_xmlx").val();
		    var sr = $(".pdtr_1");
// 			var zc = $(".zctr_11");
			var jjfl=$(".jjfltr_11");
			if(sr.length>1){
				$(".pdtr_1:not(.pd_tr1)").remove();
			}
// 			if(zc.length>1){
// 				$(".zctr_11:not(.zc_1)").remove();
// 			}
			if(jjfl.length>1){
				$(".jjfltr_11:not(.jjfl_1)").remove();
			}
			$.ajax({
				type:"post",
				url:"${ctx}/ysgl/xmsz/xmxx/getxmlxxx1",
				dataType:"json",
				data:"xmbh="+xmbh,
				success:function(val){
						var yslx = val.YSLX;
						var sfczzc = val.SFCZZC;
						$(".cz[value='"+sfczzc+"']").attr("checked",'true');
						$("#txt_yslx option").removeAttr("selected");
						$("#txt_yslx option[value='"+yslx+"']").attr("selected",true);
					}
				
				
			});
			//会计科目控制信息
			$.ajax({
				type:"post",
				url:"${ctx}/ysgl/xmsz/xmxx/getsrkm1",
				dataType:"json",
				data:"xmbh="+xmbh,
				success:function(val){				
						$.each(val,function(i,v){
// 							 var $tr = $(".pd_tr1").clone();
// 							     $tr.removeClass("pd_tr1");	
							    // $tr.removeClass("pd_tr1");	
							    $("#pbtr_1").find("[name='kmbh']").val(v.SRKMBH);
								$("#pbtr_1").find("[name='kmmc']").val(v.KMMC);
								
// 								$tr.find("[name='kmbh']").attr("id","txt_srkbh"+v.GUID);
// 								$tr.find("[name='kmmc']").attr("id","txt_srkm"+v.GUID);
								
// 								$tr.find("[type='button']").attr("id","btn_srkm"+v.GUID);
// 								$tr.find("[type='button']").attr("mc","txt_srkm"+v.GUID);
// 								$tr.find("[type='button']").attr("bh","txt_srkbh"+v.GUID);
// 								$(".pd_tr1").before($tr);	
						});
						//添加删除样式
// 						$(".add1").removeClass("addBtn1")				
// 						$(".add1").addClass("delBtn1");	
// 						$(".tbody1 tr:last").find(".add1").removeClass("delBtn1");
// 						$(".tbody1 tr:last").find(".add1").addClass("addBtn1");
					//	$(".tbody1 tr").removeClass("pd_tr1");
					//	$(".tbody1 tr:last").addClass("pd_tr1");
					
				},
				error:function(val){
									
				}	
			});
			/* //zc科目信息
			$.ajax({
				type:"post",
				url:"${ctx}/ysgl/xmsz/xmxx/getzckm1",
				dataType:"json",
				data:"xmbh="+xmbh,
				success:function(val){				
						$.each(val,function(i,v){
							 var $tr = $(".zc_1").clone();
							     $tr.removeClass("zc_1");					   
							    $tr.find("[name='kmbh']").val(v.ZCKMBH);
								$tr.find("[name='kmmc']").val(v.KMMC);
								
								$tr.find("[name='kmbh']").attr("id","txt_zckbh"+v.GUID);
								$tr.find("[name='kmmc']").attr("id","txt_zckm"+v.GUID);
								
								$tr.find("[type='button']").attr("id","btn_zckm"+v.GUID);
								$tr.find("[type='button']").attr("mc","txt_zckm"+v.GUID);
								$tr.find("[type='button']").attr("bh","txt_zckbh"+v.GUID);

								
								
								$("#zctr_1").before($tr);	
						});
						//添加删除样式
						$(".add2").removeClass("addBtn2")				
						$(".add2").addClass("delBtn2");	
						$(".tbody2 tr:last").find(".add2").removeClass("delBtn2");
						$(".tbody2 tr:last").find(".add2").addClass("addBtn2");
						//$(".tbody2 tr").removeClass("zc_1");
						//$(".tbody2 tr:last").addClass("zc_1");
					
				},
				error:function(val){
									
				}	
			}); */
			//经济分类科目信息
			$.ajax({
				type:"post",
				url:"${ctx}/ysgl/xmsz/xmxx/getjjflkm1",
				dataType:"json",
				data:"xmbh="+xmbh,
				success:function(val){				
						$.each(val,function(i,v){
// 							 var $tr = $("#jjfltr_1").clone();
// 							     $tr.removeClass("jjfltr_1");					   
							    $("#jjfltr_1").find("[name='kmbh']").val(v.JJFL);
								$("#jjfltr_1").find("[name='kmmc']").val(v.KMMC);
// 								$("#jjfltr_1").before($tr);	
						});
						//添加删除样式
// 						$(".add3").removeClass("addBtn3")				
// 						$(".add3").addClass("delBtn3");	
// 						$(".tbody3 tr:last").find(".add3").removeClass("delBtn3");
// 						$(".tbody3 tr:last").find(".add3").addClass("addBtn3");
// 						$(".tbody3 tr").removeClass("jjfl_1");
// 						$(".tbody3 tr:last").addClass("jjfl_1");
					
				},
				error:function(val){
									
				}	
			});
		 
		 
		 
		 
		 
	 });
	 $("#txt_gkbm").bindChange("${ctx}/suggest/getXx","CD");
	//选择 项目名称
	 $(document).on("click",".btn-ktmc",function(){
		var $parentTr = $(this).parents("div");
 		var txt_ktmc = $parentTr.find("[id^=txt_ktmc]").attr("id");
 		var txt_jflx = $parentTr.find("[id^=txt_jflx]").attr("id");
 		var txt_syje = $parentTr.find("[id^=txt_syje]").attr("id");
 		var txt_jfbh = $parentTr.find("[id^=txt_jfbh]").attr("id");
 		var checkbox =$("[name='guid']");
   		if(checkbox.length>0){
   			var guid = [];
	   	   	checkbox.each(function(){
	   	   		if($(this).val() !=''){
	   	   		guid.push($(this).val());
	   	   		}
	   	   	});
	   	   	}
		select_commonWin("${ctx}/wsbx/ccyw/ccywsq/jfszList?cId_1="+txt_ktmc+"&cId_2="+txt_jflx+"&cId_3="+txt_syje+"&cId_4="+txt_jfbh+"&guid="+guid.join("','")+"&ryid="+$("#txt_sqrid").val()+"&type=xmxx","项目信息","900","620");
	 })
	function addbuttonzc(){
		var a = $(".tbody2").find("tr").length;
		var aaa="${zcmap}";
		
		if(aaa.length>2){
			$(".add2").removeClass("addBtn2")
			
			$(".add2").addClass("delBtn2");		
			var $parentTr = $(".tbody2 tr:last").clone();
			$parentTr.find("input:not(.b)").val("");
			
			$(".tbody2 tr:last").after($parentTr);
			
			$(".tbody2 tr:last").find(".add2").removeClass("delBtn2");
			$(".tbody2 tr:last").find(".add2").addClass("addBtn2");
			$(".tbody2 tr:last").addClass("zc_1");

		}else{
			
		}
	}
	function addbuttonjjfl(){
		var a = $(".tbody3").find("tr").length;
		var aaa="${jjflmap}";
		
		if(aaa.length>2){
			$(".add3").removeClass("addBtn3")
			
			$(".add3").addClass("delBtn3");		
			var $parentTr = $(".tbody3 tr:last").clone();
			$parentTr.find("input:not(.c)").val("");
			
			$(".tbody3 tr:last").after($parentTr);
			
			$(".tbody3 tr:last").find(".add3").removeClass("delBtn3");
			$(".tbody3 tr:last").find(".add3").addClass("addBtn3");
			$(".tbody3 tr:last").addClass("jjfl_1");
		}else{
			
		}
	}
	//添加修改按钮
	$(".deleteBtn_jb").click(function(){
		$('#txt_srkbh').val('');
		$('#txt_srkm').val('');
	}
			);
	$(".deleteBtn_xm").click(function(){
		$('#txt_srkbh2').val('');
		$('#txt_srkm2').val('');
		}
				);
 $("#btn_save").click(function(e){
	 var xmlb = $("#txt_xmlb").val();
		var srkmbh = $(".pd_tr1").find("[name=kmbh]").val();
		var srkmbh2 = $(".pd_tr2").find("[name=kmbh2]").val();
		
		var srkmbhs = srkmbh.substring(0, 4); 
		var srkmbh2s = srkmbh2.substring(0, 4);
		if(srkmbh==""&&srkmbh2 ==""){
			alert("会计科目控制请至少选择一条科目！");
			return;
		}else if(srkmbh==srkmbh2){
			alert("会计科目控制不允许重复");
			return;
		}
		if(xmlb == '01'){
			if(srkmbh==""&&srkmbh2 !=""){
				if(srkmbh2s != '5001'){
					alert("项目支出请选择当前项目类别——“教育”下的科目编号！");
					return;
				}
			}else if(srkmbh != ""&&srkmbh2 == ""){
				if(srkmbhs != '5001'){
					alert("基本支出请选择当前项目类别——“教育”下的科目编号！");
					return;
				}
			}else if(srkmbh!=""&&srkmbh2 !=""){
				if(srkmbh2s != '5001'&&srkmbhs != '5001'){
					alert("支出请选择当前项目类别——“教育”下的科目编号！");
					return;
				}}
		}else if(xmlb == '02'){
			if(srkmbh==""&&srkmbh2 !=""){
				if(srkmbh2s != '5002'){
					alert("项目支出请选择当前项目类别——“科研”下的科目编号！");
					return;
				}
			}else if(srkmbh != ""&&srkmbh2 == ""){
				if(srkmbhs != '5002'){
					alert("基本支出请选择当前项目类别——“科研”下的科目编号！");
					return;
				}
			}else if(srkmbh!=""&&srkmbh2 !=""){
				if(srkmbh2s != '5002'&&srkmbhs != '5002'){
					alert("支出请选择当前项目类别——“教育”下的科目编号！");
					return;
				}}
		}else if(xmlb == '03'){
			if(srkmbh==""&&srkmbh2 !=""){
				if(srkmbh2s != '5003'){
					alert("项目支出请选择当前项目类别——“行政”下的科目编号！");
					return;
				}
			}else if(srkmbh != ""&&srkmbh2 == ""){
				if(srkmbhs != '5003'){
					alert("基本支出请选择当前项目类别——“行政”下的科目编号！");
					return;
				}
			}else if(srkmbh!=""&&srkmbh2 !=""){
				if(srkmbh2s != '5003'&&srkmbhs != '5003'){
					alert("支出请选择当前项目类别——“教育”下的科目编号！");
					return;
				}}
		}else if(xmlb == '04'){
			if(srkmbh==""&&srkmbh2 !=""){
				if(srkmbh2s != '5004'){
					alert("项目支出请选择当前项目类别——“后勤”下的科目编号！");
					return;
				}
			}else if(srkmbh != ""&&srkmbh2 == ""){
				if(srkmbhs != '5004'){
					alert("基本支出请选择当前项目类别——“后勤”下的科目编号！");
					return;
				}
			}else if(srkmbh!=""&&srkmbh2 !=""){
				if(srkmbh2s != '5004'&&srkmbhs != '5004'){
					alert("支出请选择当前项目类别——“教育”下的科目编号！");
					return;
				}}
		}else if(xmlb == '05'){
			if(srkmbh==""&&srkmbh2 !=""){
				if(srkmbh2s != '5005'){
					alert("项目支出请选择当前项目类别——“离退休”下的科目编号！");
					return;
				}
			}else if(srkmbh != ""&&srkmbh2 == ""){
				if(srkmbhs != '5005'){
					alert("基本支出请选择当前项目类别——“离退休”下的科目编号！");
					return;
				}else if(srkmbh!=""&&srkmbh2 !=""){
					if(srkmbh2s != '5005'&&srkmbhs != '5005'){
						alert("支出请选择当前项目类别——“教育”下的科目编号！");
						return;
					}}
			}
		}
		doSave1(validate,"myform1",target+"/doSaveU",function(val){
		});
	});
	function doSave1(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $("#myform1").data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				dataType:"json",
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){						
						next();
				},
				error:function(val){
				}	
			});
		}
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
	        }else{
	        	o[this.name] = this.value;
	        }
	    });
	    return f;
	};
	var validate = $("#myform1").bootstrapValidator({fields:{
        bmbh:{validators:{notEmpty: {message: '部门名称不能为空'}}},
        xmbh:{validators:{notEmpty: {message: '项目编号不能为空'},regexp: {regexp: /^[\w_]+$/,message: '项目编号只能包含大写、小写、数字和下划线'},stringLength:{message:'项目编号过长',max:'50'}}},
        xmmc:{validators:{notEmpty: {message: '项目名称不能为空'},stringLength:{message:'项目编号过长',max:'50'}}},
        xmdl:{validators: {notEmpty: {message: '项目大类不能为空'}}},
        xmlb:{validators: {notEmpty: {message: '项目类别不能为空'}}},
        xmlx:{validators: {notEmpty: {message: '项目类型不能为空'}}},
        fzr:{validators: {notEmpty: {message: '负责人不能为空'}}},
        srkm:{validators:{notEmpty:{message: '会计科目控制不能为空'}}},
//         zckm:{validators:{ notEmpty:{message: '支出科目不能为空'}}},
        jjflzckm:{validators:{notEmpty:{message:"经济分类支出科目不能为空"}}},
        kgrq:{validators:{ notEmpty:{message: '开始时间不能为空'}}},
        wgrq:{validators:{ notEmpty:{message: '结束时间不能为空'}}},
//         syje:{validators:{ notEmpty:{message: '剩余金额不能为空'}}},
//         ysje:{validators:{ notEmpty:{message: '预算金额不能为空'}}},
        gkxxm:{validators:{stringLength:{message:'国库信息吗过长',max:'32'}}},
        gkbm:{validators: {notEmpty: {message: '归口部门不能为空'}}}
        }
	      });
//弹窗
//会计科目控制弹窗
	$(document).on("click","#btn_srkm",function(){
		select_commonWin("${ctx}/xmlx/window?lx=sr&dx=T","会计科目控制信息","920","630");
	});
	$(document).on("click","#btn_srkm2",function(){
		select_commonWin("${ctx}/xmlx/window?lx=sr2&dx=T","会计科目控制信息","920","630");
	});
	//支出科目弹窗
	$(document).on("click",".addBtn2",function(){
		select_commonWin("${ctx}/xmlx/window?lx=zc","支出科目信息","920","630");
	});
	//经济科目弹窗
	$(document).on("click","#btn_jjflkm",function(){
		select_commonWin("${ctx}/xmlx/jjflwindow?dx=T","经济科目信息","920","630");
	});
// $(document).on("click","[id^=btn_srkm]",function(){
// 	var bh = $(this).attr("bh");
// 	var mc = $(this).attr("mc");
// 	select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId="+bh+"&controlId1="+mc,"会计科目控制","920","630");
// });
// $(document).on("click","[id^=btn_zckm]",function(){
// 	var bh = $(this).attr("bh");
// 	var mc = $(this).attr("mc");
// 	select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId="+bh+"&controlId1="+mc,"支出科目","920","630");
// });
// $(document).on("click","[id^=btn_jjflkm]",function(){
// 	var bh = $(this).attr("bh");
// 	var mc = $(this).attr("mc");
// 	select_commonWin("${ctx}/xmlx/mainJjkmszTree?controlId="+bh+"&controlId1="+mc,"经济分类科目","920","630");
// });
//收入
var num = 2;
// $(document).on("click","[class*=addBtn1]",function(){
	
// 	var $parentTr = $(this).parents("tr").clone();
// 	$(this).removeClass("addBtn1");
// 	$(this).addClass("delBtn");
// 	$parentTr.find("input:not(.a)").val("");
// 	$parentTr.find(":input").removeClass("border");
// 	$parentTr.attr("id","tr_"+num);
// 	//转入
// 	$parentTr.find("[id^=txt_srkm]").attr({"id":"txt_srkm"+num});
// 	$parentTr.find("[id^=txt_srkbh]").attr({"id":"txt_srkbh"+num});
// 	$parentTr.find("[id^=btn_srkm]").attr({"id":"btn_srkm"+num,"mc":"txt_srkm"+num,"bh":"txt_srkbh"+num});
// 	num++;
// 	$(this).parents("tr").after($parentTr);
// 	$(".tbody1 tr").removeClass("pd_tr1");
// 	$(".tbody1 tr:last").addClass("pd_tr1");

// });
//支出
var num = 2;
// $(document).on("click","[class*=addBtn2]",function(){
	
// 	 var $parentTr = $(this).parents("tr").clone();
// 		$(this).removeClass("addBtn2");
// 		$(this).addClass("delBtn");
// 		$parentTr.find("input:not(.b)").val("");
// 		$parentTr.find(":input").removeClass("border");
// 		$parentTr.attr("id","tr_"+num);
// 		//支出
// 		$parentTr.find("[id^=txt_zckm]").attr({"id":"txt_zckm"+num});
// 		$parentTr.find("[id^=txt_zckbh]").attr({"id":"txt_zckbh"+num});
// 		$parentTr.find("[id^=btn_zckm]").attr({"id":"btn_zckm"+num,"mc":"txt_zckm"+num,"bh":"txt_zckbh"+num});
// 		num++;
// 		$(this).parents("tr").after($parentTr);
// 		$(".tbody2 tr").removeClass("zc_1");
// 		$(".tbody2 tr:last").addClass("zc_1");
	 
// });
var num = 2;
// $(document).on("click","[class*=addBtn3]",function(){
	 
// 	 var $parentTr = $(this).parents("tr").clone();
// 		$(this).removeClass("addBtn3");
// 		$(this).addClass("delBtn");
// 		$parentTr.find("input:not(.c)").val("");
// 		$parentTr.find(":input").removeClass("border");
// 		$parentTr.attr("id","tr_"+num);
// 		//支出
// 	$parentTr.find("[id^=txt_jjflkm]").attr({"id":"txt_jjflkm"+num});
// 	$parentTr.find("[id^=txt_jjflkbh]").attr({"id":"txt_jjflkbh"+num});
// 	$parentTr.find("[id^=btn_jjflkm]").attr({"id":"btn_jjflkm"+num,"mc":"txt_jjflkm"+num,"bh":"txt_jjflkbh"+num});
// 		num++;
// 		$(this).parents("tr").after($parentTr);
// 		$(".tbody3 tr").removeClass("jjfl_1");
// 		$(".tbody3 tr:last").addClass("jjfl_1");
// });
$(document).on("click",".delBtn1",function(){
	 $(this).parents("tr").remove();				 
});
$(document).on("click",".delBtn2",function(){
	 $(this).parents("tr").remove();				 
});
$(document).on("click",".delBtn3",function(){
	 $(this).parents("tr").remove();				 
});
//检查项目编号是否已经存在
function checkXmbhExist(){
	var tag = false;
	var xmbh = $("#txt_xmbh").val();
	$.ajax({
		type:"post",
		//url:ADDRESS+"/xmxx/checkXmbh",
		url:target+"/checkXmbhExist",
		data:"xmbh="+xmbh,
		async:false,
		success:function(val){
			var result = JSON.getJson(val);
			if(result.exist){
				tag = true;
				alert("该项目编号已经存在！");
			}
		}
	})
	return tag;
}
$(function(){
	//验证方法
	
	//保存按钮
//    $("#btn_save").click(function(){
// 	   	var url = target+"/xmxxAdd";
// 		doSave1(validate,$("#myform"),url,function(val){
// 			var result = JSON.getJson(val);
// 			if(result.success){
// 				alert("保存成功！");
// 			}
// 		});
// 	});
  	
	//返回按钮
	$("#btn_back").click(function(){
		var xmbh=$("#txt_xmbh").val();
   		var xmmc=$("#txt_xmmc").val();
   		var bmmc=$("#txt_bmmc").val();
		if("${type}"=="wdxm"){
		window.location.href = target+"/goWdxmPage";
		}else{
		window.location.href = target+"/goXmxxPage?xmbh="+xmbh+"&xmmc="+xmmc+"&bmmc="+bmmc;
		}
	});
	//刷新按钮
	$(".reload").click(function(){
		 window.location.reload();
	});
	
	//负责人弹窗
	$(document).on("click","#btn-fzr",function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_fzr","负责人","920","630");
	});
	//项目弹窗
	$(document).on("click","[id^=btn_xmlx]",function(){
		select_commonWin("${ctx}/ysgl/xmsz/xmxx/getxmlxall?controlId=txt_xmlx&id=txt_xmlxid","项目信息","920","630");	
		
	});
	//归口部门弹窗
	$(document).on("click","#btn_gkbm",function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_gkbm","归口部门","920","630");
	});
	$(document).on("click","#btn_bmbh",function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_bmbh","部门信息","920","630");
	});
	
	
	//会计科目控制弹窗
	$(document).on("click","#btn-srkm",function(){
		select_commonWin("${ctx}/window/goSrkmPage?controlId=txt_srkm","会计科目控制","920","630");
	});
	//支出科目弹窗
	$(document).on("click","#btn-zckm",function(){
		select_commonWin("${ctx}/window/goZckmPage?controlId=txt_zckm","支出科目","920","630");
	});
	//经济分类支出科目弹窗
	$(document).on("click","#btn-jjflzckm",function(){
		select_commonWin("${ctx}/window/goJjflkmPage?controlId=txt_jjflzckm","经济分类支出科目","920","630");
	});
	//日期验证
	$("#txt_kssj,#txt_jssj").blur(function(){
		dateVerify();
	})
	//验证日期
	function dateVerify(){
		var kssj = $("#txt_kssj").val();
		var jssj = $("#txt_jssj").val();

		if(!isNull(kssj) && !isNull(jssj)){
			
			 if(jssj < kssj){
				alert("结束时间不能小于开始时间！");
				$("#txt_jssj").val("");
			}
		}
	}
});
function addkm(guid){
	var sr = $(".pdtr_1");
	var zc = $(".zctr_11");
	var jjfl=$(".jjfltr_11");
	if(sr.length>1){
		$(".pdtr_1:not(.pd_tr1)").remove();
	}
	if(zc.length>1){
		$(".zctr_11:not(.zc_1)").remove();
	}
	if(jjfl.length>1){
		$(".jjfltr_11:not(.jjfl_1)").remove();
	}
	$.ajax({
		type:"post",
		url:"${ctx}/ysgl/xmsz/xmxx/getxmlxxx",
		dataType:"json",
		data:"guid="+guid,
		success:function(val){
				var yslx = val.YSLX;
				var sfczzc = val.SFCZZC;
				$(".cz[value='"+sfczzc+"']").attr("checked",'true');
				$("#txt_yslx option").removeAttr("selected");
				$("#txt_yslx option[value='"+yslx+"']").attr("selected",true);
			}
		
		
	});
	
	//会计科目控制信息
	$.ajax({
		type:"post",
		url:"${ctx}/ysgl/xmsz/xmxx/getsrkm",
		dataType:"json",
		data:"xmlxbh="+guid,
		success:function(val){				
				$.each(val,function(i,v){
// 					 var $tr = $(".pd_tr1").clone();
// 					     $tr.removeClass("pd_tr1");	
					    // $tr.removeClass("pd_tr1");	
					    $("#pbtr_1").find("[name='kmbh']").val(v.SRKMBH);
						$("#pbtr_1").find("[name='kmmc']").val(v.KMMC);
						
// 						$tr.find("[name='kmbh']").attr("id","txt_srkbh"+v.GUID);
// 						$tr.find("[name='kmmc']").attr("id","txt_srkm"+v.GUID);
						
// 						$tr.find("[type='button']").attr("id","btn_srkm"+v.GUID);
// 						$tr.find("[type='button']").attr("mc","txt_srkm"+v.GUID);
// 						$tr.find("[type='button']").attr("bh","txt_srkbh"+v.GUID);
// 						$(".pd_tr1").before($tr);	
				});
				//添加删除样式
// 				$(".add1").removeClass("addBtn1")				
// 				$(".add1").addClass("delBtn1");	
// 				$(".tbody1 tr:last").find(".add1").removeClass("delBtn1");
// 				$(".tbody1 tr:last").find(".add1").addClass("addBtn1");
			//	$(".tbody1 tr").removeClass("pd_tr1");
			//	$(".tbody1 tr:last").addClass("pd_tr1");
			
		},
		error:function(val){
							
		}	
	});
	/* //zc科目信息
	$.ajax({
		type:"post",
		url:"${ctx}/ysgl/xmsz/xmxx/getzckm",
		dataType:"json",
		data:"xmlxbh="+guid,
		success:function(val){				
				$.each(val,function(i,v){
					 var $tr = $(".zc_1").clone();
					     $tr.removeClass("zc_1");					   
					    $tr.find("[name='kmbh']").val(v.ZCKMBH);
						$tr.find("[name='kmmc']").val(v.KMMC);
						
						$tr.find("[name='kmbh']").attr("id","txt_zckbh"+v.GUID);
						$tr.find("[name='kmmc']").attr("id","txt_zckm"+v.GUID);
						
						$tr.find("[type='button']").attr("id","btn_zckm"+v.GUID);
						$tr.find("[type='button']").attr("mc","txt_zckm"+v.GUID);
						$tr.find("[type='button']").attr("bh","txt_zckbh"+v.GUID);

						
						
						$("#zctr_1").before($tr);	
				});
				//添加删除样式
				$(".add2").removeClass("addBtn2")				
				$(".add2").addClass("delBtn2");	
				$(".tbody2 tr:last").find(".add2").removeClass("delBtn2");
				$(".tbody2 tr:last").find(".add2").addClass("addBtn2");
				//$(".tbody2 tr").removeClass("zc_1");
				//$(".tbody2 tr:last").addClass("zc_1");
			
		},
		error:function(val){
							
		}	
	}); */
	//经济分类科目信息
	$.ajax({
		type:"post",
		url:"${ctx}/ysgl/xmsz/xmxx/getjjflkm",
		dataType:"json",
		data:"xmlxbh="+guid,
		success:function(val){				
				$.each(val,function(i,v){
// 					 var $tr = $("#jjfltr_1").clone();
// 					     $tr.removeClass("jjfltr_1");					   
					    $("#jjfltr_1").find("[name='kmbh']").val(v.JJFL);
						$("#jjfltr_1").find("[name='kmmc']").val(v.KMMC);
// 						$("#jjfltr_1").before($tr);	
				});
				//添加删除样式
// 				$(".add3").removeClass("addBtn3")				
// 				$(".add3").addClass("delBtn3");	
// 				$(".tbody3 tr:last").find(".add3").removeClass("delBtn3");
// 				$(".tbody3 tr:last").find(".add3").addClass("addBtn3");
// 				$(".tbody3 tr").removeClass("jjfl_1");
// 				$(".tbody3 tr:last").addClass("jjfl_1");
			
		},
		error:function(val){
							
		}	
	});
	
}
function next(){
// 	var srkmbh = $(".pd_tr1").find("[name=kmbh]").val();
// 	var zckmbh = $(".zc_1").find("[name=kmbh]").val();
// 	var jjflkmbh = $(".jjfl_1").find("[name=kmbh]").val();
// 	var srkmbh1 = $(".pd_tr1").find("[name=kmmc]").val();
// 	var zckmbh1 = $(".zc_1").find("[name=kmmc]").val();
// 	var jjflkmbh1 = $(".jjfl_1").find("[name=kmmc]").val();
// 	if(srkmbh.length==0 &&srkmbh1.length==0){
// 		$(".pd_tr1").remove();
// 	}
// 	if(zckmbh.length==0 &&zckmbh1.length==0){
// 		$(".zc_1").remove();
// 	}
// 	if(jjflkmbh.length==0&&jjflkmbh1.length==0){
// 		$(".jjfl_1").remove();
// 	}
	
	var srkmbhs = [];
	var srkmmcs = [];
	var yslxs = [];
	var srkmbh = $(".pd_tr1").find("[name=kmbh]").val();
	srkmbhs.push(srkmbh);
// 	var zckmbh = $(".zc_1").find("[name=kmbh]").val();
// 	var jjflkmbh = $(".jjfl_1").find("[name=kmbh]").val();
	var srkmmc = $(".pd_tr1").find("[name=xmbh]").val();
	srkmmcs.push(srkmmc);
// 	var zckmbh1 = $(".zc_1").find("[name=kmmc]").val();
// 	var jjflkmbh1 = $(".jjfl_1").find("[name=kmmc]").val();
	if(srkmbh.length==0 &&srkmmc.length==0){
		$(".pd_tr1").remove();
	}
// 	if(zckmbh.length==0&&zckmbh1.length==0){
// 		$(".zc_1").remove();
// 	}
// 	if(jjflkmbh.length==0&&jjflkmbh1.length==0){
// 		$(".jjfl_1").remove();
// 	}
// 	var mytable = $(".myform");
// 	//var aryId = [];
// 	$.each(mytable,function(i,v){
// 		//var bbbb= $("#pbtr_1:last").find("[name=kmbh]").val();
// 		var $this = $(this);
// 		var id = $this.attr("id");
// 		var mc = $this.attr("mc");
// 	var json = $("#"+id+"").serializeObject1("kmbh","kmmc");  //json对象		
// 	var jsonStr = JSON.stringify(json);  //json字符串
	var srkmbh2 = $(".pd_tr2").find("[name=kmbh2]").val();
	srkmbhs.push(srkmbh2);
	var srkmmc2 = $(".pd_tr2").find("[name=xmbh2]").val();
	srkmmcs.push(srkmmc2);
	var yslx = "01";
	yslxs.push(yslx);
	var yslx2 = "02";
	yslxs.push(yslx2);
	if(srkmbh==null &&srkmbh==""||srkmbh2 ==null&&srkmbh2 ==""){
		alert("会计科目控制请至少选择一条科目！");
		return;
	}
	
			$.ajax({
		        url:"${ctx}/ysgl/xmsz/xmxx/editsr",
	   			type:"post",
	   			data:"srkmbhs="+srkmbhs.join(",")+"&srkmmcs="+srkmmcs.join(",")+"&yslxs="+yslxs.join(","),
	   			success:function(data){
				// 
				//window.location.href = "${ctx}/pzlx/gopzlxListPage";
	   			}
	
	});
	alert("保存成功！");
	var xmbh=$("#txt_xmbh").val();
	var xmmc=$("#txt_xmmc").val();
	var bmmc=$("#txt_bmmc").val();
	if(xmym=="1"){
		window.location.href  = target+"/goXmxxPage?xmbh="+xmbh+"&xmmc="+xmmc+"&bmmc="+bmmc;
	}else{
		window.location.href  = target+"/goWdxmPage";
	}
}
function addsrkm(kmbh,kmmc){
	$("#pbtr_1").find("[id=txt_srkbh]").val(kmbh);
	$("#pbtr_1").find("[id=txt_srkm]").val(kmmc);	
// 	for(var i=0;i<kmbh.length;i++){
// 		var a = $(".aa");
// 		var kmbh1="";
// 		$.each(a,function(){
// 			kmbh1 +=$(this).val()+",";
// 		});
// 	   if(kmbh1.indexOf(kmbh[i])=="-1"){
// 		   var $parentTr = $(".pd_tr1").clone();
// 			$parentTr.removeClass("pd_tr1");

// 			$parentTr.find("input:not(.a)").val("");
// 			$parentTr.find("[id=txt_srkbh]").val(kmbh[i]);
// 			$parentTr.find("[id=txt_srkm]").val(kmmc[i]);		
// 			$(".pd_tr1 ").before($parentTr);
// 			$(".add1").addClass("delBtn1");	
// 			$(".add1").removeClass("addBtn1");	
// 			$(".add1:last").addClass("addBtn1");
// 			$(".add1:last").removeClass("delBtn1");
// 		 }
		
// 	}
	
}
function addsrkm2(kmbh,kmmc){
	$("#pbtr_2").find("[id=txt_srkbh2]").val(kmbh);
	$("#pbtr_2").find("[id=txt_srkm2]").val(kmmc);		
}
function addzckm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".bb");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
		   var $parentTr = $(".zc_1").clone();
			$parentTr.removeAttr("id");
			$parentTr.removeClass("zc_1");
			$parentTr.find("input:not(.b)").val("");
			$parentTr.find("[id=txt_zckbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_zckm]").val(kmmc[i]);		
			$(".zc_1").before($parentTr);
			$(".add2").addClass("delBtn2");
			$(".add2").removeClass("addBtn2");	
			$(".add2:last").addClass("addBtn2");
			$(".add2:last").removeClass("delBtn2");
		 }
	}
	
}
function addjjflkm(kmbh,kmmc){
	$("#jjfltr_1").find("[id=txt_jjflkbh]").val(kmbh);
	$("#jjfltr_1").find("[id=txt_jjflkm]").val(kmmc);	
// 	for(var i=0;i<kmbh.length;i++){
// 		var a = $(".cc");
// 		var kmbh1="";
// 		$.each(a,function(){
// 			kmbh1 +=$(this).val()+",";
// 		});
// 	   if(kmbh1.indexOf(kmbh[i])=="-1"){
// 			var $parentTr = $(".jjfl_1 ").clone();
// 			$parentTr.removeAttr("id");
// 			$parentTr.removeClass("jjfl_1");
// 			$parentTr.find("input:not(.c)").val("");
// 			$parentTr.find("[id=txt_jjflkbh]").val(kmbh[i]);
// 			$parentTr.find("[id=txt_jjflkm]").val(kmmc[i]);		
// 			$(".jjfl_1 ").before($parentTr);
// 			$(".add3").addClass("delBtn3");	
// 			$(".add3").removeClass("addBtn3");	
// 			$(".add3:last").addClass("addBtn3");
// 			$(".add3:last").removeClass("delBtn3");
// 		 }
// 	}
	
}
</script>
</body>
</html>