<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>差旅报销</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.btn-del{
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
	.btn-del:after{
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
	    position: relative;
	    cursor: pointer;
/* 	    top:3px; */
	}
	.addBtn{
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
	.addBtn:after{
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	}
	.btn_td{
		width:14mm!important;
		height:6mm!important;
		text-align: center;
	}
	.radio-group{
		height: 25px!important;
		line-height: 25px;
		vertical-align: middle;
		display: inline-block;
	}
	.radio-group > .rdo{
		margin: 0px 0px 0px 10px;
		height: 25px;
	}
	.input-radius2{
		border-bottom-right-radius: 4px!important;
		border-top-right-radius: 4px!important;
	}
	th,td{
		text-align: center;
	}
	tbody > tr > td{
		padding: 0px!important;
	}
	.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    padding-left: 6px;
}
tr{
background-color: white !important;
}

.addBtn2 {
	text-align: center;
	width: 20px;
	height: 20px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
	margin-left:5px;
	margin-top:2px;
}

.addBtn2:after {
	/* 		2722 */
	content: "+";
	color: #9c9c9c !important;
	color: #337ab7 !important;
/* 	font-size: 17px; */
	position: relative;
	cursor: pointer;

}

.deleteBtn {
	text-align: center;
	width: 20px;
	height: 20px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
	margin-left:5px;
	margin-top:2px;
}

.deleteBtn:after {
	/* 		2014 */
	content: "\2014";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 1em;
	position: relative;
	cursor: pointer;
/* 	top: 3px; */
}
 .bk{
		border:none;
		width:100%;
		padding:4px !important;
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
	.tsinput{
		color:blue;
		border:none;
		padding-left:5px;
		font-size:18px!important;
	}
	.style_div{
		border: 1px solid #dddddd;
		border-radius: 4px;
		margin-top: 20px;
		padding-top: 4px;
		padding-left: 15px;
		padding-bottom: 10px;
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
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${guid}">
	
	<input type="hidden" name="ccywguid" id="" value="${ccywguid}">
	<div class="page-header">
		<!--标题-->
		<div class="pull-left">
			<span class="page-title text-primary"><i
				class='fa icon-xiangxixinxi'></i>借款业务申请信息</span>
		</div>
		<!-- 操作按钮 -->
		<div class="pull-right">
		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
		</div>
	</div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>借款业务详细信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
<!--             		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明"> -->
<!-- 						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i> -->
<!-- 						<font style="font-size:14px;">业务说明</font> -->
<!-- 					</button> -->
<!-- 			   		<button type="button" class="btn btn-default" id="btn_back">返回列表</button> -->
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单据编号</span>
                            <input type="text" id="txt_djbh" class="form-control input-radius" readonly name="djbh" value="${jksq.DJBH}" placeholder="系统自动生成">
							
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>借款人</span>
							<input type="text" id="txt_jkr" readonly class="form-control input-radius" name="jkrmc" value="${jksq.JKRMC}"/>
							<input type="hidden" id="txt_jkr1"class="form-control input-radius" name="jkr" value="${jksq.JKR}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">所在部门</span>
							<input type="text" id="txt_szbm" readonly class="form-control input-radius" name="szbm" value="${jksq.SZBM}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>借款时间</span>
							<input type="text" id="txt_jksj" name="jksj" class="form-control input-radius2"  readonly value="${jksq.JKSJ}"  />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>附件张数</span>
							<input type="text" id="txt_fjzs" name="fjzs" class="form-control input-radius2 text-right int" value="${jksq.FJZS}"  />
                        </div>
					</div>
					<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">借款总金额(元)</span>
						<input type="text" id="txt_jkzje" readonly class="form-control input-radius number text-right" name="jkzje" value="${jksq.JKZJE}">
					</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>借款事由</span>
							<textarea style="width:100%;height:60px;" name="jksy" class="form-control input-radius">${jksq.JKSY}</textarea>
						</div>
					</div>
				</div>
		<div class="box-panel">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>上传附件
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
	
		<div class="box-panel" style="margin-left:-12px;">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content" style="width:50%;float:left;">
				<table id="mydatatables" class="table table-striped table-bordered xmxx" style="border-collapse: collapse;">
		        	<thead id="thead">
				        <tr>
<!-- 				        <th style="width: 52px; border-bottom-width: 0px;">操作</th> -->
<!-- 						<th style="width: 5%;">序号</th> -->
						<th style="width: 50%; border-bottom-width: 0px;"><span class="required" style="color: red;">*</span>项目名称</th>
						<th style="width: 25%; border-bottom-width: 0px;">剩余金额（元）</th>
						<th style="width: 15%; border-bottom-width: 0px;" >支出金额（元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				     <c:choose>
                             <c:when test="${not empty xmxxlist}">
  								<c:forEach var="xmxx" items="${xmxxlist}"> 
				    	         <tr id="tr_1">
<!-- 				    		         <td class="btn_td"> <div class="deleteBtn add" ></div> </td>				    		 -->
				    		<td>
				    		 <div class="input-group"> 
				    		<input type="text" id="txt_ktmc" name="ktmc" placeholder="请选择项目名称" class="bk" value="${xmxx.mc}" style="background-color: white !important ;width=300px" />
                            <input type="hidden" id="txt_jfbh" name="jfbh" value="${xmxx.guid}"/>
                          
                            <span class="input-group-btn"> <button type="button" id="btn-ktmc"  class="btn btn-link btn-custom">选择</button></span>
				    		</div>
				    		</td>
				    		<td >
				    		<input type="text" id="txt_syje" name="syje" class="bk text-right number" style="background-color: white !important;" value="${xmxx.ye}" readonly/>
				    		</td>
				    		<td >
				    		 <input type="text" id="txt_zcje" name="zcje" class="bk text-right number" style="background-color: white !important;" placeholder="0.00" value="${xmxx.zcje}" readonly />
				    		</td>
				    	</tr>
				    	</c:forEach>
				    	
                          </c:when>
        				<c:otherwise>
				    	<tr>
<!-- 						<td class="btn_td" style=" vertical-align: middle;"> -->
<!-- 							<div class="addBtn2" ></div> -->
<!-- 						</td> -->
<!-- 						<td class="xh" style="vertical-align: middle;">1</td> -->
						<td style="padding: 4px !important" >
						  <div class="input-group"> 
						<input type="text" id="txt_ktmc" name="ktmc" placeholder="请选择项目名称" class="bk null" style="background-color: white !important ;width=300px" />
                            <input type="hidden" id="txt_jfbh" name="jfbh"/>
                            <span class="input-group-btn">
                            <button type="button" id="btn-ktmc"  class="btn btn-link btn-custom">选择</button>
                            </span>
						</div>
						</td>
						<td style="padding: 4px !important">
						 <input type="text" id="txt_syje" name="syje" class="bk text-right number" placeholder="0.00" readonly/>
						</td>
						<td style="padding: 4px !important">
						<input type="text" id="txt_zcje" name="zcje"  class=" bk text-right number" value="" />
						</td>
					</tr>
        				</c:otherwise>
                    </c:choose>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
	</div>
	</div>
	<div class="box" id="operate">
			<div class="box-panel">	
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary" style="font-size:15px;">
            	<i class="fa icon-xiangxixinxi" style="font-size:20px;"></i>结算方式<span style="color:green;"></span>
            	</div>
            	 <div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
                 </div>
	        </div>
	        <hr class="hr-normal">
			<!-- 对私支付 -->
	      
			<div class="style_div">
			 <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            	<span class="noselect">
            		<input type="radio" id="addDs" class="sszkbox" name='' value="0" ${jksq.ZFFS=='0'?"checked='checked'":"" }/>
            	</span>
            	对私支付
            	&nbsp;&nbsp;
            	&nbsp;&nbsp;
            	<span class="tyspan sszk_xj"><strong>小计（元）:</strong><input type="text" class="tsinput" readonly value="0.00" /></span>
            	</div>
	        </div>
	        <div class="container-fluid box-content sszk-content">
	       			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse;">
		        	<thead id="thead">
				        <tr>
				            <th style="width:20%;">人员选择</th>
				            <th style="width:20%;">人员姓名</th>
				            <th style="width:20%;">银行名称</th>
				            <th style="width:20%;">卡号</th>
				            <th style="width:15%;">金额（元）</th>
				        </tr>
					</thead>
				    <tbody id="tb_sszk">
				    
				    	<c:forEach items="${dsList}" var="dsList" varStatus="ds">
				    		<tr class="tr_${ds.index+1}">
							<td>&nbsp;&nbsp;<input type="hidden" name="start" value=""/>
				    			<input type="radio" disabled="disabled" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='0'}">checked</c:if>
				    				class="form-control input-radius" value="0">个人&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" disabled="disabled" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='1'}">checked</c:if>
				    				class="form-control input-radius" value="1">项目负责人&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" disabled="disabled" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='2'}">checked</c:if>
				    				class="form-control input-radius" value="2">其他人
				    				<input type="hidden" name="zfdh" value="${zbid}" />
				    				<input type="hidden" name="ryxz" value="${dsList.ryxz}" />
				    		</td>
				    		<td>
				    		<div class="input-group" style="width:100%;" >
								<input type="text" id="txt_ryxm${ds.index+1}" name="ryxm" class="bk input-radius" style="height:27px;" value="${dsList.ryxm}">
				    			<span class="input-group-btn ">
				    			</span>
				    		</div>
							</td>
							<td>
								<select id="txt_klx${dg.index+1}" name="klx" class="bk input-radius">
										<c:forEach items="${dlryhklist}" var="item">
										<option value="${item.khyh}" zh="${item.khyhzh}" <c:if test="${item.khyhzh == dsList.dfzh }">selected</c:if>>${dsList.klx}</option>
										</c:forEach>
								</select>
							</td>
							<td>
							<input type="text" id="txt_dfzh${ds.index+1}" name="dsdfzh" class="bk input-radius null" readonly="readonly" value="${dsList.dfzh}">
							<input type="hidden" id="txt_guid${ds.index+1}" name="zhbguid" class="bk input-radius null" readonly="readonly" value="${dsList.zhbguid}">
							</td>
							<td>
							<input type="text" id="txt_dsje${ds.index+1}" name="dsje" class="bk input-radius number text-right" value="${dsList.je}" 
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								<input type="hidden" name="end" />
							</td>
						</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
			</div>
		<!-- 对公支付 -->
		<div class="style_div">
	        <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            	<span class="noselect">
            		<input type="radio" id="addDg" class="xnzzbox" name='' value="1" ${jksq.ZFFS=='1'?"checked='checked'":"" } />
            	</span>
            	对公支付
            	&nbsp;&nbsp;
            	&nbsp;&nbsp;
            	<span class="tyspan xnzz_xj"><strong>小计（元）:</strong><input type="text" class="tsinput" readonly value="0.00" /></span>
            	</div>
	        </div>
			<div class="container-fluid box-content xnzz-content gwk-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse;">
		        	<thead id="thead">
				        <tr>
				            <th style="width:20%;">对方单位</th>
				            <th style="width:20%;">对方地区</th>
				            <th style="width:20%;">对方银行</th>
				            <th style="width:20%;">对方账号</th>
				            <th style="width:15%;">金额(元)</th>
				        </tr>
					</thead>
				    <tbody id="tb_xnzz">
				    	<c:forEach items="${dgList}" var="dgList" varStatus="dg">
				    		<tr class="tr_${dg.index+1}">
							<td>
							<div class="input-group" style="width:100%;">
							   <input type="hidden" name="start1" value=""/>
								<input type="hidden" name="zfdh" value="${zbid}" />
								<input type="text" id="txt_dwmc${dg.index+1}" name="dfdw1" class="bk input-radius"  value="${dgList.dwmc}">
				    			<input type="hidden" id="txt_dfdw${dg.index+1}" name="dfdw" class="form-control input-radius"  value="${dgList.wlbh}">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_dfdw${dg.index+1}" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_dfdq${dg.index+1}" style="background-color: white !important;" name="dfdq" class="bk input-radius" readonly value="${dgList.dfdq}">
							</td>
							<td>
							<select id="txt_dfyh${dg.index+1}" name="dfyh" dfyh="${dgList.dfyh}" class="bk input-radius">
							</select>
							</td>
							<td>
							<input type="text" id="txt_dfzh${dg.index+1}" style="background-color: white !important;" name="dgdfzh" readonly class="bk input-radius "  value="${dgList.dfzh}">
							</td>
							<td>
							<input type="text" id="txt_je${dg.index+1}" name="dgje" class="bk input-radius number text-right "  value="${dgList.je}">
							<input type="hidden" name="end1" value="" />
							</td>
							
						</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
			</div>	
		</div>
		</div>	     						
	</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<script type="text/javascript">
var tag = true;
$(function(){
	sszkmoney();
	function sszkmoney(){
		var money = $("[id^=txt_dsje]");
		console.log(money)
		var count = 0;
		$.each(money,function(){
			if($(this).val()!=""||!isNaN($(this).val)){
				count = Number(count)+Number($(this).val());
			}
		});
		count = parseFloat(count).toFixed(2);
		$(".sszk_xj").find(".tsinput").val(count);
		xjcolor(count,"sszk_xj");
	}
	xnzzmoney();
	function xnzzmoney(){
		var money = $("[id^=txt_je]");
		var count = 0;
		$.each(money,function(){
			if($(this).val()!=""||!isNaN($(this).val)){
				count = Number(count)+Number($(this).val());
			}
		});
		count = parseFloat(count).toFixed(2);
		$(".xnzz_xj").find(".tsinput").val(count);
		xjcolor(count,"xnzz_xj"); 
	}
	//小计字体颜色的变化
	function xjcolor(val,clw){
		if(isNaN(val)||val==""){
			val = 0;
		}
		if(val==0){
			$("[class*='"+clw+"']").find("input").css("color","blue");
		}
		if(val>0){
			$("[class*='"+clw+"']").find("input").css("color","green");
		}
		if(val<0){
			$("[class*='"+clw+"']").find("input").css("color","red");
		}
	}
	
	$(".btn-custom").hide();
	$("#txt_klx1").attr("disabled",true);
	$("[id^='txt_dsje']").attr("readonly","readonly");
	$("[id^='txt_je']").attr("readonly","readonly");
	if("${operateType}"=="L"){
		$("#btn_save").hide();
		$("input").attr("readonly","readonly");
		$("textarea").attr("readonly","readonly");
	}

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
    	return {"fold":"JKFJ","id":"${guid}","djlx":"000000"};
    },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
    //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
    overwriteInitial: false,  
    deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
   });
});


$(function(){
   //返回按钮
 	$("#btn_back").click(function(e){
 		location.href="${ctx}/bxpzth/gojkbx";
 	});
	var validate = $("#myform").bootstrapValidator({fields:{
		jkr:{validators:{notEmpty: {message: '借款人不能为空'}}},
		szbm:{validators:{notEmpty: {message: '所在部门不能为空'}}},
		jksj:{validators:{notEmpty: {message: '借款时间不能为空'}}},
		fjzs:{validators:{notEmpty: {message: '附件张数不能为空'}}},
   		jksy:{validators:{notEmpty: {message: '借款事由不能为空'}}}
   	}
   });			 

   $("#btn_save").click(function(){	 
	   var fjzs = $("#txt_fjzs").val();
	   var jksy = $("[name='jksy']").val();
		$.ajax({
			type:"post",
			url:"${ctx}/bxpzth/doUpdateJK",
			data:"fjzs="+fjzs+"&gid=${guid}&jksy="+jksy,
			dataType:"json",
			success:function(data){
				if(data.success){
					$("#imageFile").fileinput("upload");
					location.href="${ctx}/bxpzth/gojkbx";
			 		alert(data.msg);
				}
			},
			error:function(val){
				alert("抱歉，系统出现问题！");
			}
		});	
   });
});
</script>
</html>