<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目类型详细页面</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.tables {
    margin-left: 20px;
    max-width: 100%;
    margin-bottom: 20px;
    width:50%;
   }
.tables1 {
    margin-left: 20px;
    max-width: 100%;
    margin-bottom: 20px;
    width:60%;
   }
 .p1{
    text-align:center;
    font-family:宋体;
    color:red;
    font-size:18px;
   }
 [class^=td_first_]{
 	text-align:center;
 	width:8%;
 	height:30px;
 }
 [class^=td_second_]{
 	width:17%;
 	height:30px;
 }
 [class^=td_three_]{
 	width:17%;
 	height:30px;
 }
 [class^=td_four_]{
 	width:17%;
 	height:30px;
 }
  [class^=td_five_]{
 	width:17%;
 	height:30px;
 }
 [class^=td_six_]{
 	width:17%;
 	height:30px;
 }
 .td_input{
 	width:100%;
 	border:none;
 }
 a{
	 text-decoration:none;
 }
 .addBtn0,.addBtn1,.addBtn2,.addBtn3,.addBtn4{
		text-align:center;
	    width: 20px ;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn0:after,.addBtn1:after,.addBtn2:after,.addBtn3:after,.addBtn4:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	}
	.delBtn1,.delBtn2,.delBtn3,.delBtn4{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.delBtn1:after,.delBtn2:after,.delBtn3:after,.delBtn4:after{
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
<div id="myform"  >
  
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="czr" value="${loginId}">
	<input type="hidden" name="guid" value="${guid}">
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	<c:choose>
            		<c:when test="${operateType == 'L'}">查看项目类型信息</c:when>
            		<c:otherwise>编辑项目类型信息</c:otherwise>
            	</c:choose>
			</span>
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目类型信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<form id ="myform1">
			<div class="container-fluid box-content">
			 <input type="hidden" name="czr" value="${loginId}">
	         <input type="hidden" name="guid" id="guid" value="${guid}">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">				
						    <span class="input-group-addon"><span class="required">*</span>项目类型编号</span>
							<input type="text" id="txt_bmh" class="form-control input-radius" readonly="readonly" name="xmlxbh"  value="${map.XMLXBH}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>项目类型名称</span>
							<input type="text" id="txt_mc" class="form-control input-radius" name="xmlxmc" value="${map.XMLXMC}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>是否财政支出</span>
                             <div class="radiodiv">&nbsp;&nbsp;
                              <input name="sfczzc" id="checkNoHaveOper"  type="radio" value="1" <c:if test="${map.SFCZZC=='1'}"> checked="checked" </c:if> />
                              <label >是</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                              <input name="sfczzc" id="checkNoHaveOper"  type="radio" value="0" <c:if test="${map.SFCZZC=='0'}"> checked="checked"</c:if> />
                              <label >否</label>
                              </div>
						</div>
					</div>
				</div>
				<!-- 2  -->
				<div class="row">
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon"><span class="required"></span>预算类型</span> -->
<!--                                 <select id="drp_dwbb" class="form-control input-radius " name="yslx"> -->
<%-- 	                                <c:forEach var="yslxlist" items="${yslxlist}">  --%>
<%-- 	                                   <option value="${yslxlist.DM}" <c:if test="${map.YSLX == yslxlist.DM}">selected</c:if>>${yslxlist.MC}</option> --%>
<%-- 									</c:forEach> --%>
<!-- 	                            </select> -->
<!-- 						</div> -->
<!-- 					</div>				 -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目类别</span>
                               <select id="drp_dwbb" class="form-control input-radius " name="xmlb">
                               	 	<c:forEach var="xmlblist" items="${xmlblist}"> 
                                   		<option value="${xmlblist.DM}" <c:if test="${map.XMLB == xmlblist.DM}">selected</c:if>>${xmlblist.MC}</option>
									</c:forEach>
                               </select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">项目收支说明</span>
	                        <textarea id="txt_bz"  class="form-control" name="xmszsm" style=" height: 50px; width: 100%;">${map.XMSZSM }</textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
	                        <textarea id="txt_bz"  class="form-control" name="bz" style=" height: 50px; width: 100%;">${map.bz }</textarea>
						</div>
					</div>
				</div>
					
			</div>
			</form>
		</div>
<!-- 	       <form id="table1" class="myform myformPzby" mc="editsr"> -->
<!-- 				<div class="box-panel"> -->
<!-- 					<div class="box-header clearfix"> -->
<!-- 		            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>会计科目控制</div> -->
<!-- 		            	<div class="actions"> -->
<!-- 		            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a> -->
<!-- 		            	</div> -->
<!-- 		        	</div> -->
<!-- 					<hr class="hr-normal"> -->
<!-- 					<div class="container-fluid box-content"> -->
<!-- 		        		<div class="row"> -->
<!-- 		        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse"> -->
<!-- 			        			<tbody class="tbody1"> -->
			        			
<!-- 			        				<tr > -->
<!-- 			<!-- 					    	<td class="td_first_1"  style="text-align:center;" valign="middle"> --> 
<!-- 			<!-- 					    		操作 --> 
<!-- 			<!-- 					    	</td> --> 
<!-- 								    	<td class="td_second_1" style="text-align:center;" valign="middle"> -->
<!-- 								    	<span style="color: red;">*</span> -->
<!-- 								    		科目编号 -->
<!-- 								    	</td> -->
<!-- 								    	<td class="td_three_1"  style="text-align:center;" valign="middle"> -->
<!-- 								    		科目名称 -->
<!-- 								    	</td> -->
<!-- 							   		</tr> -->
<%-- 							   		<c:choose> --%>
<%-- 							   			<c:when test="${not empty srmap }"> --%>
<%-- 											<c:forEach var="srmap" items="${srmap }"> --%>
<!-- 												<tr id="pbtr_1"> -->
<!-- 			<!-- 					    	<td class="td_first_1"> --> 
<!-- 			<!-- 					    		<div class="addBtn1 add1"></div> --> 
<!-- 			<!-- 					    	</td> --> 
<!-- 											    	<td class="td_second_1"> -->
<!-- 												    	<input type="hidden" name="start" > -->
<!-- 												    	<div class="input-group"> -->
<%-- 												    	    <input type="text" style="width:100%;border:none;" class="aa" name="kmbh" id="txt_srkbh" value="${srmap.SRKMBH }"> --%>
<%-- 												    	    <input type="hidden" name="xmlxbh" id="txt_xmlxbh" class="a" value="${guid }"> --%>
<%-- 												    	     <input type="hidden" name="guid" id="txt_guid" class="" value="${srmap.GUID }"> --%>
<!-- 												    	    <span class="input-group-btn"> -->
<!-- 												    	    	<button type="button" id="btn_srkm" class="btn btn-link btn-custom">选择</button> -->
<!-- 							 				    			</span> -->
<!-- 						 				    			</div> -->
<!-- 											    	</td> -->
<!-- 											    	<td class="td_three_1"> -->
<%-- 											    		<input type="text" style="width:100%;border:none;" name="kmmc" id="txt_srkm" value="${srmap.KMMC }" /> --%>
<!-- 											    		<input type="hidden" name="end" > -->
<!-- 											    	</td> -->
<!-- 							   		   			</tr> -->
<%-- 							   		 		</c:forEach> --%>
<%-- 							   			</c:when> --%>
<%-- 							   			<c:otherwise> --%>
<!-- 					        			 	<tr id="pbtr_1"> -->
<!-- 										    	<td class="td_first_1"> -->
<!-- 										    		<div class="addBtn1 add1"></div> -->
<!-- 										    	</td> -->
<!-- 										    	<td class="td_second_1"> -->
<!-- 										    		<input type="hidden" name="start" > -->
<!-- 												    <div class="input-group"> -->
<!-- 											    	    <input type="text" style="width:100%;border:none;" class="aa" name="kmbh" id="txt_srkbh" value=""> -->
<%-- 											    	    <input type="hidden" name="xmlxbh" id="txt_xmlxbh" class="a" value="${guid }"> --%>
<!-- 											    	     <input type="hidden" name="guid" id="txt_guid" class="" value=""> -->
<!-- 											    	    <span class="input-group-btn"> -->
<!-- 											    	    	<button type="button" id="btn_srkm" class="btn btn-link btn-custom">选择</button> -->
<!-- 						 				    			</span> -->
<!-- 					 				    			</div> -->
<!-- 										    	</td> -->
<!-- 										    	<td class="td_three_1"> -->
<!-- 										    		<input type="text" style="width:100%;border:none;" name="kmmc" id="txt_srkm" value="" /> -->
<!-- 										    	</td> -->
<!-- 									   		</tr> -->
<%-- 							   			</c:otherwise> --%>
<%-- 							   		</c:choose> --%>
<!-- 			        			</tbody> -->
<!-- 							</table> -->
<!-- 					 	</div> -->
<!-- 					 </div> -->
<!-- 				</div> -->
<!-- 			</form> -->
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
				   			<c:when test="${not empty zcmap}">
				   				<c:forEach var="zcmap" items="${zcmap }">
        			 	   <tr id="">
					    	<td class="td_first_1">
					    	<input type="hidden" name="start" >
					    		<div class="addBtn2 add2"></div>
					    	</td>
					    	<td class="td_second_1">
					    	  
					    	    <input type="text" style="width:100%;border:none;" class="bb" name="kmbh" id="txt_zckbh"  value="${zcmap.ZCKMBH }">
					    	    <input type="hidden" name="xmlxbh" id="txt_xmlxbh" class="b" value="${guid }">
					    	    <input type="hidden" name="guid" id="txt_guid" class="" value="${zcmap.GUID }">
					    	    
					    	    <span class="input-group-btn">
 				    			</span>
 				    			
					    	</td>
					    	<td class="td_three_1">
					    		<input type="text" style="width:100%;border:none;" name="kmmc" id="txt_zckm" value="${zcmap.KMMC }" />
					    		<input type="hidden" name="end" >
					    	</td>
				   		</tr>
				   		</c:forEach>
				   			</c:when>
				   			<c:otherwise>
				 
        			 	<tr id="zctr_1">
					    	<td class="td_first_1">
					    		<div class="addBtn2 add2"></div>
					    	</td>
					    	<td class="td_second_1">
					    	 
					    	    <input type="text" style="width:100%;border:none;" class="bb" name="kmbh" id="txt_zckbh"  value="">
					    	    <input type="hidden" name="xmlxbh" id="txt_xmlxbh" class="b" value="${guid }">
					    	    <input type="hidden" name="guid" id="txt_guid" class="" value="">
					    	    
					    	    <span class="input-group-btn">
 				    			</span>
 				    			
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
<%-- 				   			     <c:forEach var="jjflmap" items="${jjflmap }"> --%>
<!-- 			        			 	<tr id="jjfltr_1"> -->
<!-- 			<!-- 					    	<td class="td_first_1"> --> 
<!-- 			<!-- 					    		<div class="addBtn3 add3"></div> -->
<!-- 			<!-- 					    	</td> --> 
								  
<!-- 								    	<td class="td_second_1"> -->
<!-- 									    	<input type="hidden" name="start" > -->
<!-- 								    	  	<div class="input-group"> -->
<%-- 									    	    <input type="text" style="width:100%;border:none;" class="cc" name="kmbh" id="txt_jjflkbh" value="${jjflmap.JJFL }"> --%>
<%-- 									    	    <input type="hidden" name="xmlxbh" id="txt_xmlxbh" class="c" value="${guid}"> --%>
<%-- 									    	     <input type="hidden" name="guid" id="txt_guid" value="${jjflmap.GUID}"> --%>
<!-- 									    	     <span class="input-group-btn"> -->
<!-- 									    	     	<button type="button" id="btn_jjflkm" class="btn btn-link btn-custom">选择</button> -->
<!-- 				 				    			</span> -->
<!-- 			 				    			</div> -->
<!-- 								    	</td> -->
								    
<!-- 								    	<td class="td_three_1"> -->
<%-- 								    		<input type="text" style="width:100%;border:none;" name="kmmc" id="txt_jjflkm" value="${jjflmap.KMMC }"/> --%>
<!-- 								    		<input type="hidden" name="end" > -->
<!-- 								    	</td> -->
<!-- 							   		</tr> -->
<%-- 				   				</c:forEach> --%>
<%-- 				   			</c:when> --%>
<%-- 				   			<c:otherwise> --%>
				   			
<!-- 		        			 	<tr id="jjfltr_1"> -->
<!-- <!-- 							    	<td class="td_first_1"> --> 
<!-- <!-- 							    		<div class="addBtn3 add3"></div> --> 
<!-- <!-- 							    	</td> -->
							  
<!-- 							    	<td class="td_second_1"> -->
<!-- 							    		<input type="hidden" name="start" > -->
<!-- 								    	<div class="input-group"> -->
<!-- 								    	    <input type="text" style="width:100%;border:none;" class="cc" name="kmbh" id="txt_jjflkbh" value=""> -->
<%-- 								    	    <input type="hidden" name="xmlxbh" id="txt_xmlxbh" class="c" value="${guid}"> --%>
<!-- 								    	     <input type="hidden" name="guid" id="txt_guid" value=""> -->
<!-- 								    	     <span class="input-group-btn"> -->
<!-- 								    	     	<button type="button" id="btn_jjflkm" class="btn btn-link btn-custom">选择</button> -->
<!-- 			 				    			</span> -->
<!-- 			 				    		</div> -->
<!-- 							    	</td> -->
							    
<!-- 							    	<td class="td_three_1"> -->
<!-- 							    		<input type="text" style="width:100%;border:none;" name="kmmc" id="txt_jjflkm" value=""/> -->
<!-- 							    	</td> -->
<!-- 						   		</tr> -->
				   		
				   			
<%-- 				   			</c:otherwise> --%>
<%-- 				   		</c:choose> --%>
<!--         			</tbody> -->
<!-- 				</table> -->
<!-- 		 </div> -->
<!-- 		 </div> -->
<!-- 		 </div> -->
<!-- 		 </form> -->
		  <!-- 附件设置 -->
<!-- 		  <form id="table4" class="myform myformPzby" mc="editfjsz"> -->
<!-- 			<div class="box-panel"> -->
<!-- 			<div class="box-header clearfix"> -->
<!--             	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>附件设置</div> -->
<!--             	<div class="actions"> -->
<!--             		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a> -->
<!--             	</div> -->
<!--         	</div> -->
<!-- 			<hr class="hr-normal"> -->
<!-- 			<div class="container-fluid box-content"> -->
<!--         	<div class="row"> -->
<!--        			<table id="mydatatables" class="tables1 table-striped table-bordered" style="border-collapse: collapse"> -->
<!--         			<tbody id="tbody4"> -->
<!--         				<tr > -->
<!-- 					    	<td class="td_first_1"  style="text-align:center;width:10%;" valign="middle"> -->
<!-- 					    		操作 -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_second_1" style="text-align:center;" valign="middle"> -->
<!-- 					    		附件编号 -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_three_1" style="text-align:center;" valign="middle"> -->
<!-- 					    		附件名称项目名称  -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_four_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		附件类型项目类别  -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_five_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		数据状态 -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_six_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		是否必传是否必填 -->
<!-- 					    	</td> -->
<!-- 				   		</tr> -->
<%--         			 	 <c:forEach var="fjszmap" items="${fjszmap}"> --%>
<%--        					<c:if test="${not empty fjszmap.fjbh }"> --%>
<!--         			 	<tr id="fjsztr_1" a='a'> -->
<!-- 					    	<td class="td_first_1"> -->
<!-- 					    	<input type="hidden" name="start" > -->
<!--                           		<div class="delBtn4"></div> -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_second_1"> -->
<%--         			 	 		<input type="text" id="txt_fjbh" class="form-control input-radius fjbh" name="fjbh" style="border: 0px;" value="${fjszmap.fjbh}"/> --%>
<!-- 					    	</td> -->
<!-- 					    	<td class="td_three_1"> -->
<!-- 				    			<input type="hidden" style="width:100%;border:none;" id="txt_khyh1" a="a" class="form-control input-radius " name="guid" value=""> -->
<!-- 				    			<div class="input-group" style="width: 100%;"> -->
<%-- 				    			<input type="text" id="txt_xmlx_1" a="a" class="form-control input-radius "  name="xmmc" value="${fjszmap.xmmc}" style="width: 100%;border:none;"/> --%>
<%-- 				    			<input type="hidden" name="xmlxbh" id="txt_xmlxbh4" class="d" value="${guid}"> --%>
<!-- 				    			<input type="hidden" id="txt_xmlxid"  name="xmlxid" class="form-control input-radius " style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" /> -->
<!-- 				    			</div> -->
<!-- 					    	</td> -->
					    
<!-- 					    	<td class="td_four_1"> -->
<!-- 					    		<input type="hidden" style="width:100%;border:none;" name="kmmc" id="txt_jjflkm" /> -->
<!-- 					    		<select id="drp_dwbb" class="form-control input-radius " name="xmlb"> -->
<%-- 									<c:forEach var="xmlbmap" items="${xmlbmap}">  --%>
<%--                                    		<option value="${xmlbmap.DM}" <c:if test="${fjszmap.xmlb == xmlbmap.DM}">selected</c:if>>${xmlbmap.MC}</option> --%>
<%-- 									</c:forEach> --%>
<!--                                </select> -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_five_1"> -->
<!--         			 	 		<div class="radiodiv" >&nbsp;&nbsp; -->
<%--                               <input name="sjzt_${fjszmap.xh}" id="checkNoHaveOper"  type="radio" value="1" <c:if test="${fjszmap.sjzt=='1'}"> checked="checked" </c:if>/> --%>
<!--                               <label >启用</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<%--                               <input name="sjzt_${fjszmap.xh}" id="checkNoHaveOper"  type="radio" value="0" <c:if test="${fjszmap.sjzt=='0'}"> checked="checked" </c:if>/> --%>
<!--                               <label >禁用</label> -->
<!--                               </div> -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_six_1"> -->
<!-- 					    		<input type="hidden" style="border:none;" name="kmmc" id="txt_jjflkm" /> -->
<!-- 								<div class="radiodiv"  >&nbsp;&nbsp; -->
<%--                               <input name="sfczzc_${fjszmap.xh}" id="checkNoHaveOper"  type="radio" value="1" <c:if test="${fjszmap.sfbt=='1'}"> checked="checked" </c:if> /> --%>
<!--                               <label >是</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<%--                               <input name="sfczzc_${fjszmap.xh}" id="checkNoHaveOper"  type="radio" value="0" <c:if test="${fjszmap.sfbt=='0'}"> checked="checked" </c:if>/> --%>
<!--                               <label >否</label> -->
<!--                               <input type="hidden" name="end" > -->
<!--                               </div> -->
<!-- 					    	</td> -->
<!-- 				   		</tr> -->
<%-- 				   		</c:if> --%>
<%-- 				   		</c:forEach> --%>
<!-- 				   		<tr id="" > -->
<!-- 					    	<td class="td_first_1"> -->
<!-- 					    	<input type="hidden" name="start" > -->
<!--                           		<div class="addBtn4"></div> -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_second_1"> -->
<!--         			 	 		<input type="text" id="txt_fjbh" class="form-control input-radius fjbh" a="a" name="fjbh" style="border: 0px;" value=""/> -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_three_1"> -->
<!-- 				    			<input type="hidden" style="width:100%;border:none;" id="txt_khyh1" a="a" class="form-control input-radius " name="guid" value=""> -->
<!-- 				    			<div class="input-group" style="width: 100%;"> -->
<!-- 				    			<input type="text" id="txt_xmlx_1" a="a" class="form-control input-radius "  name="xmmc" value="" style="width: 100%;border:none;"/> -->
<%-- 				    			<input type="hidden" name="xmlxbh" id="txt_xmlxbh5" class="f" value="${guid}"/> --%>
<!-- 				    			<input type="hidden" id="txt_xmlxid"  name="xmlxid" class="form-control input-radius " style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" /> -->
<!-- 				    			</div> -->
<!-- 					    	</td> -->
					    
<!-- 					    	<td class="td_four_1"> -->
<!-- 					    		<input type="hidden" style="width:100%;border:none;" name="kmmc" id="txt_jjflkm" /> -->
<!-- 					    		<select id="drp_dwbb" class="form-control input-radius " name="xmlb"> -->
<%-- 									<c:forEach var="xmlbmap" items="${xmlbmap}">  --%>
<%--                                    		<option value="${xmlbmap.DM}" >${xmlbmap.MC}</option> --%>
<%-- 									</c:forEach> --%>
<!--                                </select> -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_five_1"> -->
<!--         			 	 		<div class="radiodiv" >&nbsp;&nbsp; -->
<%--                               <input name="sjzt_${max+1}" id="checkNoHaveOper"  type="radio" value="1" checked="checked"/> --%>
<!--                               <label >启用</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<%--                               <input name="sjzt_${max+1}" id="checkNoHaveOper"  type="radio" value="0" > --%>
<!--                               <label >禁用</label> -->
<!--                               </div> -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_six_1"> -->
<!-- 					    		<input type="hidden" style="border:none;" name="kmmc" id="txt_jjflkm" /> -->
<!-- 								<div class="radiodiv"  >&nbsp;&nbsp; -->
<%--                               <input name="sfczzc_${max+1}" id="checkNoHaveOper"  type="radio" value="1"  /> --%>
<!--                               <label >是</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<%--                               <input name="sfczzc_${max+1}" id="checkNoHaveOper"  type="radio" value="0" checked="checked"/> --%>
<!--                               <label >否</label> -->
<!--                               </div> -->
<!--                               <input type="hidden" name="end" > -->
<!-- 					    	</td> -->
<!-- 				   		</tr> -->
<!--         			</tbody> -->
<!-- 				</table> -->
<!-- 		 </div> -->
<!-- 		 </div> -->
<!-- 		 </div> -->
<!-- 		 </form> -->
		  
	</div>
</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	
// 	addbuttonsr();
	addbuttonzc();
// 	addbuttonjjfl();
	function addbuttonsr(){
		var a = $(".tbody1").find("tr").length;
		var aaa="${srmap}";
		
		if(aaa.length>2){
			$(".add1").removeClass("addBtn1")
			
			$(".add1").addClass("delBtn1");		
			var $parentTr = $(".tbody1 tr:last").clone();
		//	$parentTr.find("input:not(.a)").attr("value","");
	        $parentTr.find("input:not(.a)").val("");
			$parentTr.attr("id","pbtr_1");
			$(".tbody1 tr:last").after($parentTr);
			
			$(".tbody1 tr:last").find(".add1").removeClass("delBtn1");
			$(".tbody1 tr:last").find(".add1").addClass("addBtn1");
		}else{
			
		}
	}

	var nums =${max+2};
	console.log("===nums=="+nums);
	$(document).on("click", "[class=addBtn4]", function() {
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn4").addClass("delBtn4");
		$parentTr.find("[a='a']").val("");
		$parentTr.find(":input").removeClass("border");
		$parentTr.find("[id^='txt_xmlx_']").attr("id","txt_xmlx_"+nums);
		$parentTr.find("[id^='btn_xmlx_']").attr("sjid","txt_xmlx_"+nums);
		$parentTr.find("[id^='btn_xmlx_']").attr("id","btn_xmlx_"+nums);
		$parentTr.attr("id", "tr_" + nums);
		$parentTr.find("[name^='sfczzc_']").attr("name","sfczzc_"+nums);
		$parentTr.find("[name^='sjzt_']").attr("name","sjzt_"+nums);
		nums++;
		console.log("__"+nums);
		$(this).parents("tr").after($parentTr);
// 		$("#cczb3").attr("rowspan", nums3);
// 		$("#ndjxzb").attr("rowspan", nums3+11);
	});
	function addbuttonzc(){
		var a = $(".tbody2").find("tr").length;
		var aaa="${zcmap}";
		if(aaa.length>2){
			$(".add2").removeClass("addBtn2")
			
			$(".add2").addClass("delBtn2");		
			var $parentTr = $(".tbody2 tr:last").clone();
			$parentTr.find("input:not(.b)").val("");
			$parentTr.attr("id","zctr_1");
			$(".tbody2 tr:last").after($parentTr);
			
			$(".tbody2 tr:last").find(".add2").removeClass("delBtn2");
			$(".tbody2 tr:last").find(".add2").addClass("addBtn2");
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
			$parentTr.attr("id","jjfltr_1");
			$(".tbody3 tr:last").after($parentTr);
			
			$(".tbody3 tr:last").find(".add3").removeClass("delBtn3");
			$(".tbody3 tr:last").find(".add3").addClass("addBtn3");
		}else{
			
		}
	}
	//返回按钮
	$("#btn_back").click(function(e){
		window.location.href  = "${ctx}/xmlx/goXmlxListPage";
	});
	//联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_fgld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_sjdw").bindChange("${ctx}/suggest/getXx","D");	
// 	//弹窗
// 	$(document).on("click","[id^=btn_srkm]",function(){
// 		var bh = $(this).attr("bh");
// 		var mc = $(this).attr("mc");
// 		select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId="+bh+"&controlId1="+mc,"会计科目控制","920","630");
// 	});
// 	$(document).on("click","[id^=btn_zckm]",function(){
// 		var bh = $(this).attr("bh");
// 		var mc = $(this).attr("mc");
// 		select_commonWin("${ctx}/xmlx/mainKjkmszTree?controlId="+bh+"&controlId1="+mc,"支出科目","920","630");
// 	});
// 	$(document).on("click","[id^=btn_jjflkm]",function(){
// 		var bh = $(this).attr("bh");
// 		var mc = $(this).attr("mc");
// 		select_commonWin("${ctx}/xmlx/mainJjkmszTree?controlId="+bh+"&controlId1="+mc,"经济分类科目","920","630");
// 	});
//会计科目控制弹窗
	$(document).on("click","#btn_srkm",function(){
		select_commonWin("${ctx}/xmlx/window?lx=sr&dx=T","会计科目控制信息","920","630");
	})
	//支出科目弹窗
	$(document).on("click",".addBtn2",function(){
		select_commonWin("${ctx}/xmlx/window?lx=zc","支出科目信息","920","630");
	})
	//经济科目弹窗
	$(document).on("click","#btn_jjflkm",function(){
		select_commonWin("${ctx}/xmlx/jjflwindow?dx=T","经济科目信息","920","630");
	})
	//项目弹窗
	$(document).on("click","[id^='btn_xmlx_']",function(){
		var id = $(this).attr("sjid");
		select_commonWin("${ctx}/xmlx/getxmlxall2?controlId="+id+"&id=txt_xmlxid","项目信息","920","630");	
	})
	

	//必填验证
	var validate = $('#myform1').bootstrapValidator({fields:{
				xmlxbh:{validators:{notEmpty:{message:'不能为空'}}},
				xmlxmc:{validators:{notEmpty: {message: '不能为空'}}},
				sfczzc:{validators:{notEmpty: {message: '不能为空'}}},
				srkm:{validators:{notEmpty: {message: '不能为空'}}},
				xmlb:{validators:{notEmpty:{message:'不能为空'}}},
         }});
	//保存按钮
	$("#btn_save").click(function(e){	
		checkAutoInput();
			doSave1(validate,"myform1","${ctx}/xmlx/doSave?operateType=U",function(val){
			window.location.href = "${ctx}/webView/ysgl/xmsz/xmlx/xmlx_list.jsp";
			alert("保存成功");
 		});
	});	
	function doSave1(_validate, _formId, _url, _success, _fail){
		checkAutoInput();
		var index;
		var valid;
		if(_validate){
			 _validate.bootstrapValidator("validate");
			valid = $("#myform1").data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(!tag){
			valid = false;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				dataType:"json",
				data:arrayToJson($("#myform1").serializeArray()),
				success:function(val){	
					next();
				},
				error:function(val){
					console.log("___"+val);
					next();
				}	
			});
		}
	}
	//附件编号是否重复
	$(document).on("blur","[name=fjbh]",function(){
		var fjbh = $(this).val();
		if(!isNull(fjbh)){
			$(this).addClass("fj");
			var a = $(".fjbh:not(.fj)");
			$.each(a,function(){
				var b=$(this).val();
				if(fjbh==b){
					alert("附件编号不能重复！");
					$(".fj").val("");
				}
			})
			$(this).removeClass("fj");
		}
	});
	//查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	//页面加载完后，控制实验室信息模块是否展示
	sysbz();
	//onoff按扭切换
	$("#btn_onoff").click(function(){
		if($("[name='sysbz']").val()=='0'){
			$("[name='sysbz']").val("1");
		}else if($("[name='sysbz']").val()=='1'){
			$("[name='sysbz']").val("0");
		}else{
			$("[name='sysbz']").val("0");
		}
		sysbz();
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
	    return f;
	};

	//nnoff按扭切换
	$("#btn_nnoff").click(function(){
		if($("[name='sfxy']").val()=='0'){
			$("[name='sfxy']").val("1");
		}else if($("[name='sfxy']").val()=='1'){
			$("[name='sfxy']").val("0");
		}else{
			$("[name='sfxy']").val("1");
		}
	});

	//dnoff按扭切换
	$("#btn_dnoff").click(function(){
		if($("[name='dwzt']").val()=='0'){
			$("[name='dwzt']").val("1");
		}else if($("[name='dwzt']").val()=='1'){
			$("[name='dwzt']").val("0");
		}else{
			$("[name='dwzt']").val("1");
		}
	});
	
	 
		$(document).on("focus","[class*=border]",function(){
			$(this).removeClass("border");
		});
		 $(document).on("click",".delBtn1",function(){
			 $(this).parents("tr").remove();				 
		 });
		 $(document).on("click",".delBtn2",function(){
			 $(this).parents("tr").remove();				 
		 });
		 $(document).on("click",".delBtn3",function(){
			 $(this).parents("tr").remove();				 
		 });
		 $(document).on("click",".delBtn4",function(){
			 $(this).parents("tr").remove();				 
		 });
		 function checkAutoInput(){
				tag = true;
				var ys = $(".null");
				var value = "";
				$.each(ys,function(){
					value = $(this).val();
					if(value==""){
						$(this).addClass("border");
						tag = false;
					}
				});
			}
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&dwbh=${dwb.DWBH}";
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016";
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016";
	    	}
		 }
	});
});
function sysbz(){
	var $this = $("[name='sysbz']").val();
	if($this == '0'){
		$("#sysxx").show();
	}else{
		$("[name='syslx']").val("");
		$("[name='syslb']").val("");
		$("[name='sysmj']").val("0.00");
		$("[name='sysjb']").val("");
		$("[name='ssxk']").val("");
		$("[name='sysgs']").val("");
		$("#sysxx").hide();
	}
}
$(function(){
	$("[name='dwzt']").change(function(){
		if($("[name='dwzt']").val()=='0'){
		 $.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/dwb/getNewStatus",
			data:"dwbh=${dwb.DWBH}",
			success:function(val){
				var data = JSON.getJson(val);
				 if(data.success=='false'){
					alert(data.msg);
					$("#drp_dwzt option").eq(0).attr("selected",true);
					$("#drp_dwzt option").eq(1).attr("selected",false);
				} 
			},
		}); 
			
		}
	});
});
/* function next(){
	var srlength=$("#pbtr_1").find("[name=kmbh]").val();
	var zclength=$("#zctr_1").find("[name=kmbh]").val();
	var jjfllength=$("#jjfltr_1").find("[name=kmbh]").val();
	var fjszlength=$("#fjsztr_1").find("[name=xmmc]").val();
	if(srlength.length==0){
		$("#pbtr_1").remove();
	}
	if(zclength.length==0){
		$("#zctr_1").remove();
	}
	if(jjfllength.length==0){
		$("#jjfltr_1").remove();
	}
	/* if(fjszlength.length==0){
		$("#fjsztr_1").remove();
	} 
	var mytable = $(".myform");
	var aryId = [];
	$.each(mytable,function(i,v){
		var $this = $(this);
		var id = $this.attr("id");
		var mc = $this.attr("mc")
		var json = $("#"+id+"").serializeObject1("start","end");  //json对象		
		var jsonStr = JSON.stringify(json);  //json字符串
		console.log("===jsonStr"+jsonStr);
		$.ajax({
	        url:"${ctx}/xmlx/"+mc,
   			type:"post",
   			data:"json="+jsonStr,
   			success:function(data){
			// 
			//window.location.href = "${ctx}/pzlx/gopzlxListPage";
   			}

		}); 
	});
	alert("保存成功！"); 
	window.location.href = "${ctx}/webView/ysgl/xmsz/xmlx/xmlx_list.jsp";
} */
function next(){
	var jjfllength=$("#jjfltr_1").find("[name=kmbh]").val();
	var jjfllength1=$("#jjfltr_1").find("[name=kmmc]").val();
	var srlength=$("#pbtr_1").find("[name=kmbh]").val();
	var srlength1=$("#pbtr_1").find("[name=kmmc]").val();
// 	var zclength1=$("#zctr_1").find("[name=kmmc]").val();
// 	var zclength=$("#zctr_1").find("[name=kmbh]").val();
// 	console.log("srlength==="+srlength);
// 	if(zclength.length==0&&zclength1.length==0){
// 		$("#zctr_1").remove();
// 	}
	if(srlength.length==0 &&srlength1.length==0){
		$("#pbtr_1").remove();
	}
	if(jjfllength.length==0&&jjfllength1.length==0){
		$("#jjfltr_1").remove();
	}
	var mytable = $(".myform");
	var xmlxbh = $("#guid").val();
	var aryId = [];
	$.each(mytable,function(i,v){
		var $this = $(this);
		var id = $this.attr("id");
		var mc = $this.attr("mc")
		
	var json = $("#"+id+"").serializeObject1("start","end","^sjzt_<>^sfczzc_","sjzt_<>sfczzc_");  //json对象		
	var jsonStr = JSON.stringify(json);  //json字符串

	$.ajax({
		        url:"${ctx}/xmlx/"+mc+"?xmlxbh="+xmlxbh,
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				// 
				//window.location.href = "${ctx}/pzlx/gopzlxListPage";
	   			}
	
			}); 
	});
	alert("保存成功！"); 
	window.location.href = "${ctx}/webView/ysgl/xmsz/xmlx/xmlx_list.jsp";
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
// 		   var $parentTr = $("#pbtr_1").clone();
// 			$parentTr.removeAttr("id");
// 			$parentTr.find("input:not(.a)").val("");
// 			$parentTr.find("[id=txt_srkbh]").val(kmbh[i]);
// 			$parentTr.find("[id=txt_srkm]").val(kmmc[i]);		
// 			$("#pbtr_1").before($parentTr);
// 			$(".addBtn1").attr("class","delBtn1");		
// 			$(".delBtn1:last").attr("class","addBtn1");
// 		 }
// 	}
	
}
function addzckm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".bb");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
		   var $parentTr = $("#zctr_1").clone();
			$parentTr.removeAttr("id");
			$parentTr.find("input:not(.b)").val("");
			$parentTr.find("[id=txt_zckbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_zckm]").val(kmmc[i]);		
			$("#zctr_1").before($parentTr);
			$(".addBtn2").attr("class","delBtn2");		
			$(".delBtn2:last").attr("class","addBtn2");
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
// 		   var $parentTr = $("#jjfltr_1").clone();
// 			$parentTr.removeAttr("id");
// 			$parentTr.find("input:not(.c)").val("");
// 			$parentTr.find("[id=txt_jjflkbh]").val(kmbh[i]);
// 			$parentTr.find("[id=txt_jjflkm]").val(kmmc[i]);		
// 			$("#jjfltr_1").before($parentTr);
// 			$(".addBtn3").attr("class","delBtn3");		
// 			$(".delBtn3:last").attr("class","addBtn3");
// 		 }
// 	}
	
}
</script>

</html>