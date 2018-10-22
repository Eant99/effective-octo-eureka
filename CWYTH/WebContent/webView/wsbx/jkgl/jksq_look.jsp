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
		padding-top: 5px;
		padding-left: 15px;
		padding-bottom: 10px;
	}

</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="gid"  value="${param.gid}">
	<div class="page-header">
		<!--标题-->
		<div class="pull-left">
			<span class="page-title text-primary"><i
				class='fa icon-xiangxixinxi'></i>借款业务申请信息</span>
		</div>
		<!-- 操作按钮 -->
		<div class="pull-right">
<!-- 			<button type="button" class="btn btn-default" id="btn_save"> -->
<!-- 				<i class="fa icon-save"></i>保存 -->
<!-- 			</button> -->
<!-- 			<button type="button" class="btn btn-default" id="btn_back">返回</button> -->
         
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
                    <c:if test="${flag == 1}">
                    <button type="button" class="btn btn-default" id="btn_tg"><i class="fa icon-tg"></i>通过</button>
			  		 <button type="button" class="btn btn-default" id="btn_th"><i class="fa icon-th"></i>退回</button>
			  		 </c:if>
			   		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
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
							<input type="text" id="txt_jkr" readonly class="form-control input-radius" readonly name="jkrmc" value="${jksq.JKRMC}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">所在部门</span>
							<input type="text" id="txt_szbm" readonly class="form-control input-radius" readonly name="szbm" value="${jksq.SZBM}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>借款时间</span>
							<input type="text" id="txt_jksj" name="jksj" class="form-control input-radius2 date" readonly value="${jksq.JKSJ}"  />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>附件张数</span>
							<input type="text" id="txt_fjzs" name="fjzs" class="form-control input-radius2 int" readonly value="${jksq.FJZS}"  />
                        </div>
					</div>
					<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">借款总金额(元)</span>
						<input type="text" id="txt_jkzje" readonly class="form-control input-radius number text-right"  readonly name="jkzje" value="${jksq.JKZJE}">
					</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>借款事由</span>
							<textarea style="width:100%;height:60px;" name="jksy" readonly class="form-control input-radius">${jksq.JKSY}</textarea>
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
						<input id="imageFile" name="path" type="file" multiple class="file-loading" disabled/>
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
						<th style="width: 50%; border-bottom-width: 0px;"><span class="required" style="color: red;">*</span>项目名称</th>
						<th style="width: 25%; border-bottom-width: 0px;">剩余金额（元）</th>
						<th style="width: 15%; border-bottom-width: 0px;" >支出金额（元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
  						<c:forEach var="xmxx" items="${xmxxlist}"> 
				    	    <tr id="tr_1">
				    		<td>
				    		<input type="text" id="txt_ktmc" name="ktmc" readonly  class="bk" value="${xmxx.mc}" style="background-color: white !important ;" />
                            <input type="hidden" id="txt_jfbh" name="jfbh" value="${xmxx.guid}"/>
				    		</td>
				    		<td >
				    		<input type="text" id="txt_syje" name="syje" readonly="readonly"  class="bk text-right number" style="background-color: white !important;" value="${xmxx.ye}" readonly/>
				    		</td>
				    		<td >
				    		 <input type="text" id="txt_zcje" name="zcje" readonly="readonly"  class="bk text-right number" style="background-color: white !important;" placeholder="0.00" value="${xmxx.zcje}" readonly/>
				    		</td>
				    	</tr>
				    	</c:forEach>
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
            	<div class="sub-title pull-left text-primary" style="font-size:15px;"><i class="fa icon-xiangxixinxi" style="font-size:20px;"></i>结算方式<span style="color:green;"></span>
            	</div>
	        </div>
	        <hr class="hr-normal">
			<!-- 对私支付 -->
	      
			<div class="style_div">
			 <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            	<span class="noselect">
            		<input type="radio" mc="addDs" class="sszkbox" value="" />
            	</span>
            	对私支付
            	&nbsp;&nbsp;
            	&nbsp;&nbsp;
            	<span class="tyspan sszk_xj"><strong>小计（元）:</strong><input type="" class="tsinput" readonly value="0.00" /></span>
            	</div>
	        </div>
	        <div class="container-fluid box-content sszk-content">
	       			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse;"s>
		        	<thead id="thead">
				        <tr>
<!-- 				        	<th style="width:5%;">操作</th> -->
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
<!-- 							<td class="btn_td"><div class="deleteBtn_ZK"></div></td> -->
							<td>&nbsp;&nbsp;
							  <input type="hidden" name="start" value=""/>
				    			<input type="radio" disabled="disabled" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='0'}">checked</c:if>
				    				class="form-control input-radius" value="0">个人&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" disabled="disabled" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='1'}">checked</c:if>
				    				class="form-control input-radius" value="1">项目负责人&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" disabled="disabled" class="txt_ryxz${ds.index+1}" name="ryxz${ds.index+1}" <c:if test="${dsList.ryxz=='2'}">checked</c:if>
				    				class="form-control input-radius" value="2">其他人
				    				<input type="hidden" name="zfdh" value="${zbid}" />
				    				<input type="hidden" name="ryxz" value="${dsList.ryxz}" />
				    		</div>
				    		</td>
				    		<td>
				    		<div class="input-group" style="width:100%;" >
<%-- 				    			<input type="" id="txt_ryxm${ds.index+1}" name="ryxm" class="form-control input-radius" value="${dsList.ryxm}"> --%>
<%-- 				    			<span class="input-group-btn <c:if test="${dsList.ryxz!='2'}">yc</c:if>"> --%>
<%-- 				    			<button type="button" id="bbtn_ryxm${ds.index+1}" class="btn btn-link btn-custom ">选择</button> --%>
<!-- 				    			</span> -->
								<input type="" id="txt_ryxm${ds.index+1}" name="ryxm" disabled="disabled"  class="bk input-radius" style="height:27px;" value="${dsList.ryxm}">
<!-- 				    			<span class="input-group-btn "> -->
<%-- 				    			<button type="button" id="bbtn_ryxm${ds.index+1}" class="btn btn-link btn-custom <c:if test="${dsList.ryxz=='0'}">yc</c:if>">选择</button> --%>
<!-- 				    			</span> -->
				    		</div>
							</td>
							<td>
<%-- 							<input type="text" id="txt_klx${ds.index+1}" name="klx" class="bk input-radius" value="${dsList.klx}"> --%>
								<select id="txt_klx${dg.index+1}" name="klx" disabled="disabled" class="bk input-radius">
										<c:forEach items="${dlryhklist}" var="item">
										<option value="${item.khyh}" zh="${item.khyhzh}" <c:if test="${item.khyhzh == dsList.dfzh }">selected</c:if>>${dsList.klx}</option>
										</c:forEach>
							</select>
							</td>
							<td>
							<input type="text" id="txt_dfzh${ds.index+1}" name="dfzh" readonly="readonly"  class="bk input-radius null" readonly="readonly" value="${dsList.dfzh}">
							</td>
							<td>
							<input type="text" id="txt_dsje${ds.index+1}" name="je"  readonly="readonly"  class="bk input-radius number text-right" value="${dsList.je}" 
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
									<input type="hidden" name="end" />
							</td>
						</tr>
				    	</c:forEach>
				    
<!-- 						<tr class="tr_0"> -->
<!-- 							<td class="btn_td"><div class="addBtn_ZK"></div></td> --> 
<!-- 							<td>&nbsp;&nbsp; -->
<!-- 							  	<input type="hidden" name="start" value=""/> 
<!-- 				    			<input type="hidden" name="ryxz" value="0"/>
<!-- 				    			<input type="radio" class="txt_ryxz0" name="ryxz0" class="form-control input-radius"  value="0">个人&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				    			<input type="radio" class="txt_ryxz0" name="ryxz0" class="form-control input-radius" checked value="1">项目负责人&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				    			<input type="radio" class="txt_ryxz0" name="ryxz0" class="form-control input-radius"  value="2">其他人 -->
<!-- 				    		</div> -->
<!-- 				    		</td> -->
<!-- 				    		<td> -->
<!-- 				    		<div class="input-group" style="width:100%;"> 
<%-- 				    			<input type="" id="txt_ryxm0" name="ryxm" class="form-control input-radius" value="${loginInfo}"> --%>
<!-- 				    			<span class="input-group-btn  yc"> --> 
<%-- 				    			<input type="hidden" name="zfdh" value="${zbid}" /> --%> 
<!--  				    			<button type="button" id="bbtn_ryxm0" class="btn btn-link btn-custom">选择</button> --> 
<!-- 				    			</span> -->
<%-- 								<input type="" id="txt_ryxm0" name="ryxm" class="bk input-radius " style="height:27px;" value="${loginInfo}"> --%>
<!-- 								<input type="" id="txt_ryxm0" name="ryxm" disabled="disabled" class="bk input-radius " style="height:27px;" value=""> 
<!-- 				    			<span class="input-group-btn"> -->
<%-- 				    			<input type="hidden" name="zfdh" value="${zbid}" /> --%>
<!-- 			    			<button type="button" id="bbtn_ryxm" class="btn btn-link btn-custom  bbtn_ryxm0">选择</button> -->
<!-- 				    			</span> -->
<!-- 				    		</div> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 						<input type="text" id="txt_klx0" name="klx" class="bk input-radius"  value="">
<!-- 								<select id="txt_klx0" name="klx"  disabled="disabled"  class="bk input-radius">
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<input type="text" id="txt_dfzh0" name="dfzh" class="bk input-radius null" readonly="readonly" value=""> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<input type="text" id="txt_dsje0" name="je" class="bk input-radius number text-right" readonly="readonly"   value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"> -->
<!-- 								<input type="hidden" name="end" /> -->
<!-- 							</td> -->
<!-- 						</tr> -->
						
				    </tbody>
				</table>
			</div>
			</div>
		
		<div class="style_div">
	        <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
<!--             	<i class="fa icon-xiangxi"></i> -->
            	<span class="noselect">
            		<input type="radio" mc="addDg" class="xnzzbox" value="1" />
<!--             		<span>未选择</span> -->
            	</span>
            	对公支付
            	&nbsp;&nbsp;
            	&nbsp;&nbsp;
            	<span class="tyspan xnzz_xj"><strong>小计（元）:</strong><input type="" class="tsinput" readonly value="0.00" /></span>
            	</div>
<!--             	<div class="actions"> -->
<!--             		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa yc" id="xnzz"></i></a> -->
<!--             	</div> -->
	        </div>
			<div class="container-fluid box-content xnzz-content gwk-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse;">
		        	<thead id="thead">
				        <tr>
<!-- 				        	<th style="width:5%;">操作</th> -->
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
<!-- 							<td class="btn_td"><div class="deleteBtn_XN"></div></td> -->
							<td>
							<div class="input-group" style="width:100%;">
							   <input type="hidden" name="start1" value=""/>
								<input type="hidden" name="zfdh" value="${zbid}" />
								<input type="text" id="txt_dwmc${dg.index+1}" name="dfdw1" readonly="readonly"  class="bk input-radius"  value="${dgList.dwmc}">
				    			<input type="hidden" id="txt_dfdw${dg.index+1}" name="dfdw" class="form-control input-radius"  value="${dgList.wlbh}">
<!-- 				    			<span class="input-group-btn"> -->
<%-- 				    			<button type="button" id="btn_dfdw${dg.index+1}" class="btn btn-link btn-custom">选择</button> --%>
<!-- 				    			</span> -->
				    		</div>
							</td>
							<td>
							<input type="text" id="txt_dfdq${dg.index+1}" readonly="readonly"  style="background-color: white !important;" name="dfdq" class="bk input-radius" readonly value="${dgList.dfdq}">
							</td>
							<td>
							<select id="txt_dfyh${dg.index+1}" name="dfyh"  disabled="disabled"   dfyh="${dgList.dfyh}" class="bk input-radius">
							</select>
							</td>
							<td>
							<input type="text" id="txt_dfzh${dg.index+1}"   disabled="disabled"  style="background-color: white !important;" name="dfzh" readonly class="bk input-radius "  value="${dgList.dfzh}">
							</td>
							<td>
							<input type="text" id="txt_je${dg.index+1}" name="je" readonly="readonly"  class="bk input-radius text-right number"  value="${dgList.je}">
							<input type="hidden" name="end" value="" />
							</td>
							
						</tr>
				    	</c:forEach>
				    
<!-- 						<tr class="tr_0"> -->
<!-- 							<td class="btn_td"><div class="addBtn_XN"></div></td> -->
<!-- 							<td> -->
<!-- 							<div class="input-group" style="width:100%;"> -->
<!-- 							   <input type="hidden" name="start1" value=""/> -->
<%-- 								<input type="hidden" name="zfdhdg" value="${zbid}" /> --%>
<!-- 								<input type="text" name="dfdw1" id="txt_dwmc0"  readonly="readonly"  class="bk input-radius"  value=""> -->
<!-- 				    			<input type="hidden" id="txt_dfdw0" name="dfdwdg" class="form-control input-radius"  value=""> -->
<!-- 				    			<span class="input-group-btn"> -->
<!-- 				    			<button type="button" id="btn_dfdw0" class="btn btn-link btn-custom">选择</button> -->
<!-- 				    			</span> -->
<!-- 				    		</div> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<input type="text" id="txt_dfdz0" readonly="readonly"  style="background-color: white !important;" name="dfdzdg" class="bk input-radius" readonly value=""> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<select id="txt_dfyh0" name="dfyhdg"  disabled="disabled"  class="bk input-radius"> -->
<!-- 							</select> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<input type="text" id="txt_dfzh0" name="dfzhdg"   style="background-color: white !important; " readonly class="bk input-radius"  value=""> -->
<!-- 							</td> -->
<!-- 							<td> -->
<!-- 							<input type="text" id="txt_je0" name="jedg" readonly="readonly"  class="bk input-radius number text-right"  value=""> -->
<!-- 							<input type="hidden" name="end1" value="" /> -->
<!-- 							</td> -->
							
<!-- 						</tr> -->
						
				    </tbody>
				</table>
			</div>
			</div>
	     
			
			
			</form>
		</div>
	</div>	
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<script type="text/javascript">
var tag = true;
var look="${look}";

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
//扫码上传
	//SmscLoad("${ctx}",{"id":"${jsxx.GUID}","djlx":"000000","fold":"LDQZ","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
/*图片信息结束  */ 
$(function(){
	kz();
	$("#btn_jkr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_jkr","人员信息","920","630");
    });
   //返回按钮
 	$("#btn_back").click(function(e){
 		var flag="${param.flag}";
 		if(flag ==1){
 			location.href="${ctx}/jksh/goJkshPage";	
 		}else if(flag==3){
 			location.href="${ctx}/jksh/goJkshPage";	
 		}else{
 			location.href="${ctx}/jksq/goJksqPage";
 		}
 	});
 	//通过
 	$("#btn_tg").click(function(e){
 		select_commonWin("${ctx}/jksh/check1?type=first&gid=${gid}&procinstid=${param.procinstid}"+"&operateType=TG","通过页面","500","300");
 		//location.href="${ctx}/wsbx/ccbx/goCheckListPage";
 	});	
 	//退回
 	$("#btn_th").click(function(e){
 		select_commonWin("${ctx}/jksh/check1?type=second&gid=${gid}&procinstid=${param.procinstid}"+"&operateType=TH","退回页面","500","300");
 		//location.href="${ctx}/wsbx/ccbx/goCheckListPage";
 	});	
	var validate = $('#myform').bootstrapValidator({fields:{
		jkr:{validators:{notEmpty: {message: '借款人不能为空'}}},
		szbm:{validators:{notEmpty: {message: '所在部门不能为空'}}},
		jksj:{validators:{notEmpty: {message: '借款时间不能为空'}}},
   		jksy:{validators:{notEmpty: {message: '借款事由不能为空'}}},
   	}
   });
	
	var num = 2;
	 $(document).on("click","[class*=addBtn2]",function(){
			var $parentTr = $(this).parents("tr").clone();
			$parentTr.find(":input").val("");
			$(this).removeClass("addBtn2");
			$(this).addClass("deleteBtn");
			$parentTr.find("[id^=txt_ktmc]").attr({"id":"txt_ktmc"+num});
			$parentTr.find("[id^=txt_syje]").attr({"id":"txt_syje"+num});
			$parentTr.find("[id^=txt_zcje]").attr({"id":"txt_zcje"+num});
			$parentTr.find("[id^=txt_jfbh]").attr({"id":"txt_jfbh"+num});
			num++;
			
			$(this).parents("tr").after($parentTr);
		});
	//删除按钮
		$(document).on("click","[class*=deleteBtn]",function(){
			$(this).parents("tr").remove();
		});
		//课题名称弹窗
		 $(document).on("click",".btn-ktmc",function(){
			var $parentTr = $(this).parents("tr").clone();
	 		var txt_ktmc = $parentTr.find("[id^=txt_ktmc]").attr("id");
	 		var txt_syje = $parentTr.find("[id^=txt_syje]").attr("id");
	 		var txt_jfbh = $parentTr.find("[id^=txt_jfbh]").attr("id");
	 		var checkbox =$("[name='jfbh']");
	   		if(checkbox.length>0){
	   			var guid = [];
		   	   	checkbox.each(function(){
		   	   		if($(this).val() !=''){
		   	   		guid.push($(this).val());
		   	   		}
		   	   	});
		   	   	}
			select_commonWin("${ctx}/wsbx/ccyw/ccywsq/jfszList?flag=1&cId_1="+txt_ktmc+"&cId_3="+txt_syje+"&cId_4="+txt_jfbh+"&guid="+guid.join("','"),"项目信息","900","620");
		 })
		$("#btn_save").click(function(){	   
	   		var url = "${ctx}/jksq/doSaveMx";
		   
			doSave1($("#myform"),url,function(val){
				var result = JSON.getJson(val);
				if(result.success){
					alert("保存成功！");
				}
			});
		});
		 
		 function doSave1($form, _url, _success, _fail){
				var json = $form.serializeObject("jfbh","zcje");
				var jsonStr = JSON.stringify(json);
				var formJson = arrayToJson($form.serializeArray());
				formJson.json = jsonStr;
				
				
				
				var json2 = $form.serializeObject("start","end");
				var jsonStr2 = JSON.stringify(json2);
				formJson.json2 = jsonStr2;
				
				var json3 = $form.serializeObject("start1","end1");
				var jsonStr3 = JSON.stringify(json3);
				formJson.json3 = jsonStr3;
				$.ajax({
					type:"post",
					url:_url,
					data:formJson,
					success:function(data){
						_success(data);
					},
					error:function(val){
						alert("抱歉，系统出现问题！");
					},
				});	
				
			}
		 
		 //添加项目
	function addXmxx($checkbox){
		//添加的项目信息
		var addNum = 0;
		$.each($checkbox,function(){
			var $tr = $(".xmxx tbody tr:last-child").clone();
			var ktmc = $(this).find(".keyId").attr("ktmc");
			var syje = $(this).find(".keyId").attr("syje");
			var zcje = $(this).find(".keyId").attr("zcje");
			var jfbh = $(this).find(".keyId").attr("gid");
			$tr.find("[name='ktmc']").val(ktmc);
			$tr.find("[name='syje']").val(syje);
			$tr.find("[name='zcje']").val(zcje);
			$tr.find("[name='jfbh']").val(jfbh);
			$tr.find(".addBtn2").attr("class","deleteBtn");
			$(".xmxx tbody tr:last-child").before($tr);
			addNum ++;
		});
		 $(".xmxx tbody tr:last-child").remove();
		 $(".xmxx tbody tr:last-child").find(".deleteBtn").attr("class","addBtn2");
	}

	 $(document).on("keyup","[id^=txt_zcje]",function(){
			countJe();
		});
	
	//保存按钮

	//联想输入
	$("#txt_bxr").bindChange("${ctx}/suggest/getXx","R");//报销人
	$("#txt_bmmc").bindChange("${ctx}/suggest/getXx","D");//部门
	$("#btn_bmmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_bmmc","部门信息","920","630");
    });
	$("#btn_xmbh").click(function(){
		select_commonWin("${ctx}/wsbx/rcbx/window?type=xm&controlId=txt_xmbh","信息","920","630");
    });
	$("#btn_after").click(function(){
		location.href = back_url;
		//location.href = "${ctx}/webView/wsbx/ccbx/selectXm.jsp?operate=${operate}&mkbh=${param.mkbh}&xmguid="+$("#txt_xmguid").val()+"&zbguid="+$("#guid").val()+"&look="+"${look}&ccywguid=${ccywguid}";
	});
	$("#btn_next").click(function(){
		//var src_ = "${ctx}/webView/wsbx/ccbx/jsfs_operate.jsp?mkbh=${param.mkbh}&hjje="+$("[id=txt_hjje]").val();
		//checkAutoInput();
		var money=$("[name='jsbxzje']").val();//本次报销和
		var jyzjehj=$("[name='jyzjehj']").val();//项目剩余金额
// 		var money = "${money}";
		var bxzjes = $("[name='bxzje']").val();
// 		if(Number(money)<Number(bxzjes)){
// 			alert("报销总金额不可大于项目剩余金额，项目剩余金额为"+money+"元");
// 			return;
// 		}
// 		if(Number(money)!=Number(hjje)){
// 			alert("本次报销金额和需等于报销总金额!");
// 			return;
// 		}
// 		var hjje = $("[id='txt_bxzje']").val();//获取总报销金额 
		if(bxzjes<0 || bxzjes==0){
			alert("报销总金额必须大于0");
			return;
		}
		if(Number(jyzjehj)<Number(bxzjes)){
			alert("报销金额不可大于项目剩余金额,剩余金额为"+jyzjehj+"元");
			return;
		};
		if(Number(money)!=Number(bxzjes)){
			alert("各项目的本次报销金额之和，必须等于报销总金额！");
			return;
		}
		var picture = $(".file-preview-frame");
		if(picture.length==0){
		}else{
			$("#imageFile").fileinput("upload");
		}
		var type = "";
		if("${operate}"=="U"||"${param.operateType}"=="U"){
			type = "U";
		}
		
	
		
		//location.href = "${ctx}/webView/wsbx/ccbx/jsfs_operate.jsp?mkbh=${param.mkbh}&hjje="+$("[id=txt_hjje]").val();
	});
});


function countJe(){
	var jkzje = 0;
	$.each($("[id^=txt_zcje]"),function(){
		var zcje = $(this).val();
		if(zcje==""||zcje==0||isNaN(zcje)){
			zcje = 0.00;
		}
		jkzje = parseInt(jkzje) + parseInt(zcje);
	});
	$("#txt_jkzje").val(jkzje.toFixed(2));
};


</script>
<!-- 各种计算js -->
<script type="text/javascript">

//默认隐藏的元素
function kz(){
	
	//对私
	if("${jksq.ZFFS}" == "0"){
		$(".sszk-content").css("display","none");
		$(".sszkbox").click();
	}else{
		
		$(".sszk-content").css("display","none");
	}
	//对公
	if("${jksq.ZFFS}"=="1"){
		$(".xnzz-content").css("display","none");
		$(".xnzzbox").click();
	}else{
		$(".xnzz-content").css("display","none");
	}

	//控制往来银行的显示
	$.each($("[id^=txt_dfdw]"),function(){
		var $this = $(this);
		var wlbh = $(this).val();
		var select = $this.parents("tr").find("select");
		if(wlbh!=""){
			select.empty();
			var dfyh = select.attr("dfyh");
			$.getJSON("${ctx}/wsbx/rcbx/dfyh","wlbh="+wlbh,function(m){
				if(m){
					$.each(m,function(i,v){
						if(i==0){
							var id = v.GUID;
							var mc = v.KHYH;
							var zh = v.KHYHZH;
							if(dfyh==id){
								select.append("<option value="+id+" zh"+zh+" selected>"+mc+"</option>");
							}else{
								select.append("<option value="+id+" zh"+zh+">"+mc+"</option>");
							}
						}
					});
				}
			});
		}
	});
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
	jszje();
}
//计算结算总金额
function jszje(){
	var money = $(":checkbox").filter(":checked").parents("div").find("[class$=tsinput]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
	$("[id=txt_jszje]").val(count);
	if(count>0){
		$("[id=txt_jszje]").css("color","green");
	}else if(count<0){
		$("[id=txt_jszje]").css("color","red");
	}else{
		$("[id=txt_jszje]").css("color","blue");
	}
}
</script>
<!-- 对私支付js -->
<script type="text/javascript">

$(document).on("change","[id^=txt_klx]",function(){
	var zh = $(this).find("option:selected").attr("zh");
	$(this).parents("tr").find("[id^=txt_dfzh]").val(zh);
});
//部门
$(document).on("click","[id^=btn_bm]",function(){
	var id = $(this).parents("td").find("[id^=txt_bm]").attr("id");
	select_commonWin("${ctx}/window/dwpage?controlId="+id,"部门信息","920","630");
});
$(document).on("click","[class*=txt_ryxz]",function(){
	var parentTr=$(this).parents("tr");
	var val = $(this).val();
	if(val=="0"){
		var dqdlrguid="${dqdlrguid}";
		$(this).parents("tr").find("[id^=txt_ryxm]").val("${loginInfo}");
		$(this).parents("tr").find("[id^=bbtn_ryxm]").addClass("yc");
		$.ajax({
			url:"${ctx}/wsbx/rcbx/dszfyhxx",
			data:"dqdlrguid="+dqdlrguid,
			dataType:"json",
			async:false,
			success:function(datas){
				parentTr.find("[id^=txt_klx]").empty();
				if(datas){
					parentTr.find("[id^=txt_klx]").append("<option value=''>请选择...</option>");
					$.each(datas,function(i,v){
						var id = v.GUID;
						var mc = v.KHYH;
						var zh = v.KHYHZH;
						parentTr.find("[id^=txt_klx]").append("<option value="+mc+" zh="+zh+">"+mc+"</option>");
					});
				}
			}
		});
	}else if(val=="1"){
		$(this).parents("tr").find("[id^=txt_ryxm]").val("${xmfzr}");
// 		$(this).parents("tr").find("[id^=bbtn_ryxm]").addClass("yc");
		$(this).parents("tr").find("[id^=bbtn_ryxm]").removeClass("yc");
		$(this).parents("tr").find("[id^=txt_dfzh]").val("");
		parentTr.find("[id^=txt_klx]").empty();
	}else{
		$(this).parents("tr").find("[id^=txt_ryxm]").val("");
		$(this).parents("tr").find("[id^=bbtn_ryxm]").removeClass("yc");
		$(this).parents("tr").find("[id^=txt_dfzh]").val("");
		parentTr.find("[id^=txt_klx]").empty();
	}
});
//人员弹窗
// $(document).on("click","[id^=bbtn_ryxm]",function(){
// 	var id = $(this).parents("td").find("[id^=txt_ryxm]").attr("id");
// 	select_commonWin("${ctx}/window/rypage?controlId="+id,"人员信息","920","630");
// });
//项目负责人弹窗
$(document).on("click","[id^=bbtn_ryxm]",function(){
	console.log($(this).parents("td").siblings().children("[name^='ryxz']:checked").val());
    if( $(this).parents("td").siblings().children("[name^='ryxz']:checked").val()=="1"){
    	var id = $(this).parents("td").find("[id^=txt_ryxm]").attr("id");
	    select_commonWin("${ctx}/wsbx/ccbx/window?controlId=xmfzr&ccywguid=${ccywguid}&id="+id,"项目负责人信息","820","630");
	}	
    if($(this).parents("td").siblings().children("[name^='ryxz']:checked").val()=="2"){
    	var id = $(this).parents("td").find("[id^=txt_ryxm]").attr("id");
//     	select_commonWin("${ctx}/window/rypage?controlId="+id,"人员信息","920","630");
    	select_commonWin("${ctx}/wsbx/rcbx/rypage?controlId="+id,"人员信息","920","630");
	}	
});
//项目负责人弹窗 查找上级方法
function xmfzrdsqtr(ryguid,cdid){
	var parentTr=$("#"+cdid).parents("tr");
	$.ajax({
		url:"${ctx}/wsbx/rcbx/dszfyhxx",
		data:"dqdlrguid="+ryguid,
		dataType:"json",
		async:false,
		success:function(datas){
			parentTr.find("[id^=txt_klx]").empty();
			if(datas){
				parentTr.find("[id^=txt_klx]").append("<option value=''>请选择...</option>");
				$.each(datas,function(i,v){
					var id = v.GUID;
					var mc = v.KHYH;
					var zh = v.KHYHZH;
					parentTr.find("[id^=txt_klx]").append("<option value="+mc+" zh="+zh+">"+mc+"</option>");
				});
			}
		}
	});
}
//人员弹窗 查找上级方法
function dsqtr(guid,dwmc,ry,cdid){
	var parentTr=$("#"+cdid).parents("tr");
	$.ajax({
		url:"${ctx}/wsbx/rcbx/dszfyhxx",
		data:"dqdlrguid="+guid,
		dataType:"json",
		async:false,
		success:function(datas){
			parentTr.find("[id^=txt_klx]").empty();
			if(datas){
				parentTr.find("[id^=txt_klx]").append("<option value=''>请选择...</option>");
				$.each(datas,function(i,v){
					var id = v.GUID;
					var mc = v.KHYH;
					var zh = v.KHYHZH;
					parentTr.find("[id^=txt_klx]").append("<option value="+mc+" zh="+zh+">"+mc+"</option>");
				});
			}
		}
	});
}
$(".sszkbox").click(function(){
	$(".xnzzbox").attr("checked",false);
	$(this).parents(".style_div").find(".sszk-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".sszk-content:hidden").show();
		var txt_jkzje=$("#txt_jkzje").val();//获取总金额
		var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqcjkcount = 0;
		var schqcount = 0;
		var schqdgcount = 0;
		var schqgwkcount = 0;
		$.each(schqcjkmoney,function(){//冲借款
			if($(this).val()!=""||!isNaN($(this).val)){
				schqcjkcount = Number(schqcjkcount)+Number($(this).val());
			}
		});
		$.each(schqmoney,function(){//对私
			if($(this).val()!=""||!isNaN($(this).val)){
				schqcount = Number(schqcount)+Number($(this).val());
			}
		});
		$.each(schqdgmoney,function(){//对公
			if($(this).val()!=""||!isNaN($(this).val)){
				schqdgcount = Number(schqdgcount)+Number($(this).val());
			}
		});
		$.each(schqgwkmoney,function(){//公务卡
			if($(this).val()!=""||!isNaN($(this).val)){
				schqgwkcount = Number(schqgwkcount)+Number($(this).val());
			}
		});
		var syje;
		syje=Number(txt_jkzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqdgcount)-Number(schqgwkcount);
		if(syje<0){
			syje=0;
		}
		$("#txt_dsje0").val(syje);
		sszkmoney();
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".sszk-content:visible").hide();
		$(".sszk_xj").find(".tsinput").val(Number("0").toFixed(2));
		xjcolor(0,"sszk_xj");
	}
});
//
var sszk = "${dsList.size()+1}";
$(document).on("click",".addBtn_ZK",function(){
	
	var txt_jkzje=$("#txt_jkzje").val();//获取总金额
	var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqcjkcount = 0;
	var schqcount = 0;
	var schqdgcount = 0;
	var schqgwkcount = 0;
	$.each(schqcjkmoney,function(){//冲借款
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcjkcount = Number(schqcjkcount)+Number($(this).val());
		}
	});
	$.each(schqmoney,function(){//对私
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcount = Number(schqcount)+Number($(this).val());
		}
	});
	$.each(schqdgmoney,function(){//对公
		if($(this).val()!=""||!isNaN($(this).val)){
			schqdgcount = Number(schqdgcount)+Number($(this).val());
		}
	});
	$.each(schqgwkmoney,function(){//公务卡
		if($(this).val()!=""||!isNaN($(this).val)){
			schqgwkcount = Number(schqgwkcount)+Number($(this).val());
		}
	});
	var syje;
	syje=Number(txt_jkzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqdgcount)-Number(schqgwkcount);
	if(syje<0){
		syje=0;
	}
// 	var parentTr = $("#tb_sszk").find(".tr_0").clone();
// 	$("#tb_sszk").find(".tr_0").find(":text").val("");
// 	parentTr.attr("class","tr_"+sszk);
// 	parentTr.find(".addBtn_ZK").removeClass("addBtn_ZK").addClass("deleteBtn_ZK");
// 	parentTr.find("[class^=txt_ryxz]").attr({"class":"txt_ryxz"+sszk});
// 	parentTr.find("[class^=txt_ryxz]").attr({"name":"ryxz"+sszk});
// 	parentTr.find("[id^=txt_ryxm]").attr({"id":"txt_ryxm"+sszk,"value":""});
// 	parentTr.find("[id^=txt_ryxm]").attr({"id":"txt_ryxm"+sszk,"value":"${loginInfo}"});
// 	parentTr.find("[id^=txt_klx]").attr({"id":"txt_klx"+sszk});
// 	parentTr.find("[id^=bbtn_ryxm]").attr("id","bbtn_ryxm"+sszk);
// 	parentTr.find("[id^=txt_dfzh]").attr({"id":"txt_dfzh"+sszk});
// 	parentTr.find("[id^=txt_dsje]").attr({"id":"txt_dsje"+sszk});
// 	$("[id=txt_dsje0]").val(syje);
// 	$("#tb_sszk").find(".tr_0").before(parentTr);
// 	sszk++;
	sszkmoney();
});
$(document).on("click",".deleteBtn_ZK",function(){
	$(this).parents("tr").remove();
	sszkmoney();
});
//实时转卡输入
$(document).on("keyup","[id^=txt_dsje]",function(){
	sszkmoney();
});
//实时转卡金额计算
function sszkmoney(){
	var money = $("[id^=txt_dsje]");
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
</script>

<!-- 对公支付 -->
<script type="text/javascript">
$(document).on("change","[id^=txt_dfyh]",function(){
	var zh = $(this).find("option:selected").attr("zh");
	$(this).parents("tr").find("[id^=txt_dfzh]").val(zh);
});
//转账弹窗
$(document).on("click","[id^=btn_dfdw]",function(){
	var id = $(this).attr("id");
	select_commonWin("${ctx}/wsbx/rcbx/window?controlId=xnzz&id="+id,"往来单位","920","630");
});
$(".xnzzbox").click(function(){
	$(".sszkbox").attr("checked",false);
	$(this).parents(".style_div").find(".xnzz-content").toggle(500);
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".xnzz-content:hidden").show();
		var txt_jkzje=$("#txt_jkzje").val();//获取总金额
		var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqcjkcount = 0;
		var schqcount = 0;
		var schqdgcount = 0;
		var schqgwkcount = 0;
		$.each(schqcjkmoney,function(){//冲借款
			if($(this).val()!=""||!isNaN($(this).val)){
				schqcjkcount = Number(schqcjkcount)+Number($(this).val());
			}
		});
		$.each(schqmoney,function(){//对私
			if($(this).val()!=""||!isNaN($(this).val)){
				schqcount = Number(schqcount)+Number($(this).val());
			}
		});
		$.each(schqdgmoney,function(){//对公
			if($(this).val()!=""||!isNaN($(this).val)){
				schqdgcount = Number(schqdgcount)+Number($(this).val());
			}
		});
		$.each(schqgwkmoney,function(){//公务卡
			if($(this).val()!=""||!isNaN($(this).val)){
				schqgwkcount = Number(schqgwkcount)+Number($(this).val());
			}
		});
		var syje;
		syje=Number(txt_jkzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqdgcount)-Number(schqgwkcount);
		if(syje<0){
			syje=0;
		}
		$("#txt_je0").val(syje);
		xnzzmoney();
	}else{
		$(this).parent("span").addClass("noselect");
		$(this).parent("span").find("span").text("未选择");
		$(".xnzz-content:visible").hide();
		$(".xnzz_xj").find(".tsinput").val(Number(0).toFixed(2));
		xjcolor(0,"xnzz_xj");
	}
});
var xnzz = "${dgList.size()+1}";
$(document).on("click","[class*=addBtn_XN]",function(){
	var txt_jkzje=$("#txt_jkzje").val();//获取总金额
	var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
	var schqcjkcount = 0;
	var schqcount = 0;
	var schqdgcount = 0;
	var schqgwkcount = 0;
	$.each(schqcjkmoney,function(){//冲借款
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcjkcount = Number(schqcjkcount)+Number($(this).val());
		}
	});
	$.each(schqmoney,function(){//对私
		if($(this).val()!=""||!isNaN($(this).val)){
			schqcount = Number(schqcount)+Number($(this).val());
		}
	});
	$.each(schqdgmoney,function(){//对公
		if($(this).val()!=""||!isNaN($(this).val)){
			schqdgcount = Number(schqdgcount)+Number($(this).val());
		}
	});
	$.each(schqgwkmoney,function(){//公务卡
		if($(this).val()!=""||!isNaN($(this).val)){
			schqgwkcount = Number(schqgwkcount)+Number($(this).val());
		}
	});
	var syje;
	syje=Number(txt_jkzje)-Number(schqcjkcount)-Number(schqcount)-Number(schqdgcount)-Number(schqgwkcount);
	if(syje<0){
		syje=0;
	}
	var parentTr = $("#tb_xnzz").find(".tr_0").clone();
	$("#tb_xnzz").find(".tr_0").find("input").val("");
	$("#tb_xnzz").find(".tr_0").find("[name='zfdh']").val("${zbid}");
	$("#tb_xnzz").find(".tr_0").find("select").empty();
	parentTr.attr("class","tr_"+xnzz);
	parentTr.find("[class^=addBtn]").removeClass("addBtn_XN").addClass("deleteBtn_XN");
	parentTr.find("#txt_dfdw0").attr({"id":"txt_dfdw"+xnzz});
	parentTr.find("#txt_dwmc0").attr({"id":"txt_dwmc"+xnzz});
	parentTr.find("#btn_dfdw0").attr({"id":"btn_dfdw"+xnzz});
	parentTr.find("#txt_dfdz0").attr({"id":"txt_dfdz"+xnzz});
// 	parentTr.find("#txt_dfyh0").empty();
	parentTr.find("#txt_dfyh0").attr({"id":"txt_dfyh"+xnzz});
	parentTr.find("#txt_dfzh0").attr({"id":"txt_dfzh"+xnzz});
	parentTr.find("#txt_je0").attr({"id":"txt_je"+xnzz});
	$("#txt_je0").val(syje);
	$("#tb_xnzz").find(".tr_0").before(parentTr);
	xnzz++;
	xnzzmoney();
});
function xnzzJs(wlbh,dwmc,dwdz,id){
	var parentTr = $("[id='"+id+"']").parents("tr");
	$.ajax({
		url:"${ctx}/wsbx/rcbx/dfyh",
		data:"wlbh="+wlbh,
		dataType:"json",
		async:false,
		success:function(datas){
			parentTr.find("[id^=txt_dfyh]").empty();
			if(datas){
				parentTr.find("[id^=txt_dfyh]").append("<option value=''>请选择...</option>");
				$.each(datas,function(i,v){
					var id = v.GUID;
					var mc = v.KHYH;
					var zh = v.KHYHZH;
					parentTr.find("[id^=txt_dfdw]").val(wlbh);
					parentTr.find("[id^=txt_dwmc]").val(dwmc);
					parentTr.find("[id^=txt_dfdz]").val(dwdz);
					parentTr.find("[id^=txt_dfyh]").append("<option value="+id+" zh="+zh+">"+mc+"</option>");
				});
			}
		}
	});
}
$(document).on("click",".deleteBtn_XN",function(){
	$(this).parents("tr").remove();
	xnzzmoney();
});
$(document).on("keyup","[id^=txt_je]",function(){
	xnzzmoney();
});
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
//把所有undefined改为空值
$('input[value=undefined]').val('');
window.onresize=function(){
$('input[value=undefined]').val('');
}
</script>
</html>