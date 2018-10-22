<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.test1::before {
    	content: "正常" ! important;
	}
	.test1::after {
    	content: "禁用" ! important;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="czr" value="${loginId}">
	<input type="hidden" name="czrq" value="${dwb.czrq}">
	<input type="hidden" name="guid" value="${dwb.guid}">
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	<c:choose>
            		<c:when test="${operateType == 'L'}">查看单位信息</c:when>
            		<c:otherwise>编辑单位信息</c:otherwise>
            	</c:choose>
			</span>
		</div>
        <div class="pull-right">
			<c:if test="${operateType == 'U' or operateType == 'C'}">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			</c:if>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>单位信息
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
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><span class="required">*</span>单&ensp;位&ensp;号</span>
							</c:if>
							<c:if test="${operateType == 'L'}">
								<span class="input-group-addon">单&ensp;位&ensp;号</span>
							</c:if>
							<input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value="${dwb.BMH}"/>
							<input type="hidden" name="dwbh"  value="${dwb.DWBH}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><span class="required">*</span>单位名称</span>
							</c:if>
							<c:if test="${operateType == 'L'}">
								<span class="input-group-addon">单位名称</span>
							</c:if>
							<input type="text" id="txt_mc" class="form-control input-radius" name="mc" value="${dwb.MC}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位简称</span>
							<input type="text" id="txt_jc" class="form-control input-radius" name="jc" value="${dwb.JC}"/>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单位类别</span>
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<select id="drp_dwxz" class="form-control input-radius select2" name="dwxz">
	                                <c:forEach var="dwxzlist" items="${dwxzlist}"> 
	                                   <option value="${dwxzlist.DM}" <c:if test="${dwb.DWXZ == dwxzlist.DM}">selected</c:if>>${dwxzlist.MC}</option>
									</c:forEach>
	                            </select>
							</c:if>
							<c:if test="${operateType == 'L' }">
								<input type="text" class="form-control input-radius" value="${dwxzmap}"/>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单位地址</span>
							<input type="text" id="txt_dz" class="form-control input-radius" name="dz" value="${dwb.DZ}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">

							<span class="input-group-addon"><span class="required">*</span>部门负责人</span>
                            
                            <input type="text" id="txt_dwld" class="form-control input-radius window" name="dwld" value="${dwb.DWLDMC}">
							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"><button type="button" id="btn_dwld" class="btn btn-link btn-custom">选择</button></c:if></span>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><c:if test="${dwb.SJDW!='000000'}"><span class="required">*</span></c:if>隶属单位</span>
							</c:if>
							<c:if test="${operateType == 'L'}">
								<span class="input-group-addon">隶属单位</span>
							</c:if>
							<c:if test="${dwb.SJDW=='000000'}">
								<input type="text" readonly class="form-control input-radius window" value="">
								<input type="hidden" name='sjdw' readonly value="000000">
							</c:if>
							<c:if test="${dwb.SJDW!='000000'}">
								<input type="text" name='sjdw' id="txt_sjdw"  class="form-control input-radius window" value="${dwb.SJDWMC}">
							</c:if>
							<span class="input-group-btn">
								<c:if test="${(operateType == 'U' or operateType == 'C')&& dwb.SJDW!='000000'}">
									<button type="button" id="btn_sjdw" class="btn btn-link btn-custom">选择</button>
								</c:if>
							</span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon">建立年月</span>
							</c:if>
							<c:if test="${operateType == 'L'}">
								<span class="input-group-addon">建立年月</span>
							</c:if>
							<input type="text" id="txt_jlrq" class="form-control date" name="jlrq" value="<fmt:formatDate value="${dwb.JLRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="单位建立年月">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class='input-group-addon'>
								<i class="glyphicon glyphicon-calendar"></i>
							</span>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">

							<span class="input-group-addon"><span class="required">*</span>部门分管负责人</span>
                            
                            <input type="text" id="txt_fgld" class="form-control input-radius window" name="fgld" value="${dwb.FGLDMC}">
							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"><button type="button" id="btn_fgld" class="btn btn-link btn-custom">选择</button></c:if></span>
						</div>
					</div>
				</div>
					
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单位办别</span>
                                  <select id="drp_dwbb" class="form-control input-radius " name="dwbb">
	                                <c:forEach var="dwbblist" items="${dwbblist}"> 
	                                   <option value="${dwbblist.DM}" <c:if test="${dwb.DWBB == dwbblist.DM}">selected</c:if>>${dwbblist.MC}</option>
									</c:forEach>
	                            </select>
						</div>
					</div>				
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>是否学院</span>
							 <c:if test="${operateType == 'U' or operateType == 'C' }">
							  <input type="hidden" name="sfxy" value="${dwb.SFXY}">
                              <div class="switch">
                                   <div class="onoffswitch">
                                       <input type="checkbox"   <c:if test="${dwb.SFXY == '1'}">checked</c:if> class="onoffswitch-checkbox" <c:if test="${operateType != 'L'}">id="btn_nnoff" </c:if>>
                                       <label class="onoffswitch-label" for="btn_nnoff">
                                           <span class="onoffswitch-inner"></span>
                                           <span class="onoffswitch-switch"></span>
                                       </label>
                                   </div>
                               </div>
                               </c:if>
							<c:if test="${operateType == 'L'}">
								<input type="text" class="form-control input-radius" <c:choose>
									<c:when test="${dwb.SFXY == 1}">value="是"</c:when>
									<c:otherwise>value="否</c:otherwise>
								</c:choose> >
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位有效标识</span>
							<c:if test="${operateType == 'U' or operateType == 'C' }">
							  <input type="hidden" name="dwzt" value="${dwb.DWZT}">
                              <div class="switch">
                                   <div class="onoffswitch">
                                       <input type="checkbox"   <c:if test="${dwb.DWZT == '1'}">checked</c:if> class="onoffswitch-checkbox" <c:if test="${operateType != 'L'}">id="btn_dnoff" </c:if>>
                                       <label class="onoffswitch-label" for="btn_dnoff">
                                           <span class="onoffswitch-inner test1"></span>
                                           <span class="onoffswitch-switch"></span>
                                       </label>
                                   </div>
                               </div>
                               </c:if>
							<c:if test="${operateType == 'L'}">
								<input type="text" class="form-control input-radius" <c:choose>
									<c:when test="${dwb.DWZT == 1}">value="正常"</c:when>
									<c:otherwise>value="禁用"</c:otherwise>
								</c:choose> >
							</c:if>
						</div>
					</div>
				</div>	
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">排序序号</span>
							<input type="text" id="txt_pxxh" class="form-control input-radius int" name="pxxh" value="${dwb.PXXH}"/>
						</div>
					</div>
<%-- 					<div class="col-md-4" id="yzdiv" <c:if test="${dwb.sfxy==null or dwb.sfxy=='0' }">style="display:none;"</c:if>> --%>
<!-- 						<div class="input-group" > -->

<!-- 							<span class="input-group-addon"><span class="required"></span>院&emsp;&emsp;长</span> -->
                            
<%--                             <input type="text" id="txt_yz" class="form-control input-radius window" name="yz" value="${dwb.YZMC}"> --%>
<%-- 							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"> --%>
<%-- 							<button type="button" id="btn_yz" class="btn btn-link btn-custom">选择</button></c:if></span> --%>
<!-- 						</div> -->
<!-- 					</div> -->
                       <div class="col-md-4">
						 <div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>书&emsp;&emsp;记</span>             
                             <input type="text" id="txt_sj" class="form-control input-radius window" name="sj" value="${dwb.SJMC}">
							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }">
							<button type="button" id="btn_sj" class="btn btn-link btn-custom">选择</button></c:if></span>
						 </div>
					   </div>
					   <!--    归档范围 -->
<!-- 					   <div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">归档范围</span>              -->
<%--                              <input type="text" id="txt_gdfw" class="form-control input-radius window" name="gdfwmc" value="${dwb.GDFWMC}"> --%>
<%-- 							 <input type="hidden" id="txt_gdfl" name="gdfw" value="${dwb.GDFW}"> --%>
<%-- 							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"> --%>
<%-- 							<button type="button" id="btn_gdfw" class="btn btn-link btn-custom">选择</button></c:if></span> --%>
<!-- 						</div> -->
<!-- 					  </div> -->
					
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
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
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
        	return {"fold":"LDQZ","id":"${dwb.DWBH}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
    });
   //扫码上传
 	SmscLoad("${ctx}",{"id":"${dwb.BMH}","djlx":"000000","fold":"LDQZ","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
	/*图片信息结束  */ 
	//返回按钮
	$("#btn_back").click(function(e){
		parent.window.location.href  = "${ctx}/window/commonDwTreePage?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
		return false;
	});
	//联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_fgld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_sjdw").bindChange("${ctx}/suggest/getXx","D");	
	//弹窗
	$("#btn_sjdw").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_sjdw","单位信息","920","630");
    });
	$("#btn_dwld").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dwld","人员信息","920","630");
    });
	$("#btn_bmfgfzr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_bmfgfzr","人员信息","920","630");
    });
	$("#btn_fgld").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_fgld","人员信息","920","630");
    });
	$("#btn_yz").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_yz","人员信息","920","630");
    });
	$("#btn_sj").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_sj","人员信息","920","630");
    });
	//归档范围
	$("#btn_gdfw").click(function(){
		select_commonWin("${ctx}/window/goGdfwflTree?controlId=txt_gdfw&controlName=txt_gdfl","归档范围","980", "630");
	});
	//必填验证
	var validate = $('#myform').bootstrapValidator({fields:{
        	    bmh:{validators:{notEmpty:{message:'不能为空'}}},
        	    dwjc:{validators:{notEmpty: {message: '不能为空'}}},
        	    dwxz:{validators:{notEmpty: {message: '不能为空'}}},
        	    dwyxbs:{validators:{notEmpty: {message: '不能为空'}}},
        	    dwbb:{validators:{notEmpty: {message: '不能为空'}}},
        	    sfxy:{validators:{notEmpty: {message: '不能为空'}}},
                 mc:{validators:{notEmpty:{message:'不能为空'}}},
               sjdw:{validators:{notEmpty:{message:'不能为空'}}},
               //jlrq:{validators:{notEmpty:{message:'不能为空'}}},
                 dz:{validators:{notEmpty:{message:'不能为空'}}},
                fgld:{validators:{notEmpty:{message:'不能为空'}}},
//                 gdfwmc:{validators:{notEmpty:{message:'不能为空'}}},
               // pxxh:{validators:{notEmpty:{message:'不能为空'}}},
               dwld:{validators:{notEmpty:{message:'不能为空'}}},
               sj:{validators:{notEmpty:{message:'不能为空'}}}
         }});
	
	
	//保存按钮
	$("#btn_save").click(function(e){
		$("#imageFile").fileinput("upload");
		doSave(validate,"myform","${ctx}/dwb/doSave",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
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

	//nnoff按扭切换
	$("#btn_nnoff").click(function(){
		if($("[name='sfxy']").val()!='1'){
			$("[name='sfxy']").val("1");
			$("#yzdiv").attr("style","display:block;");
			$("#sjdiv").attr("style","display:block;");
		}else if($("[name='sfxy']").val()=='1'){
			$("[name='sfxy']").val("0");
			$("#yzdiv").attr("style","display:none;");
			$("#sjdiv").attr("style","display:none;");
		}else{
			$("[name='sfxy']").val("1");
		}
	});
	
	console.log("${dwb.SFXY}");
// 	if("${dwb.SFXY=='0'}"){
		
// 		$("#yzdiv").attr("style","display:none;");
// 		$("#sjdiv").attr("style","display:none;");
// 	}
	
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
			
		}//
	});
});
</script>

</html>