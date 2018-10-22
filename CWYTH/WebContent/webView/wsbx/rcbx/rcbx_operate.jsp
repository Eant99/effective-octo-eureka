<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>日常报销</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
		.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   		border-bottom-width: 1px!important;
    }
     table{
		border-collapse:collapse!important;
	}
	.btn_td{
		text-align:center;
	}

	.btn_td{
		width:14mm!important;
		height:6mm!important;
	}
	.selWindow{
		width:280px!important;
	}
	
	.number,.sz{
		text-align:right;
	}
	.yc{
		display:none;
	}
	tbody input{
		border:none;
		
	}
	thead th{
		text-align:center!important;
	}
	.noBlock{
		margin-bottom: 3px;
    	position: absolute;
    	z-index: 999;
   		display: block;
   		padding-left: 17%;
   		color: red;
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
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
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	}
	#tbody input{
		border:none;
		width:100%;
	}
	.deleteBtn{
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
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
	    position: relative;
	    cursor: pointer;
/* 	    top:3px; */
	}
	.text-green{
		color:green!important;
	}
	tr{
background-color: white !important;
}
tbody td{
	   padding:4px !important;
	}
	.hs{
/* 	  background-color:#cccccc; */
	}
</style>
</head>
<body>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println(path+"@"+basePath);
%>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="flag" id="flag" value="">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" id="dxt_xmid"   name="xmguid" value="${xmguid}">
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>第二步,费用填写</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-green">选择项目&nbsp;></div>
					
					<div class="sub-title pull-left text--primary">
					<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
	    			</div>
					</div>
					<div class="sub-title pull-left  text-primary">费用填写&nbsp;></div>
					
					<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
	    			</div>
					</div>
					<div class="sub-title pull-left">结算方式设置&nbsp;</div>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header1 clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>日常报销信息
            	</div>
            	<div class="pull-right">
            		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
					</button>
			  		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号</span>
							<input type="text" id="txt_djbh" readonly class="form-control input-radius" readonly name="djbh" value="系统自动生成"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报&ensp;销&ensp;人</span>
                            <input type="text" id="txt_bxr" readonly class="form-control input-radius window" name="" value="${login}">
                            <input type="hidden"  name="bxr" value="${loginId}">
                             <input type="hidden"  name="guid" value="${zbid}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">部&emsp;&emsp;门</span>
							<input type="text" id="txt_bmmc" readonly class="form-control input-radius window" name="" value="${loginBm}"/>
							<input type="hidden"  name="szbm" value="${szbm}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">附件总张数（张）</span>
							<input type="text" id="txt_fjzzs" class="form-control input-radius sz" name="fjzzs" value="0" readonly value="${zbxx.fjzzs }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销总金额（元）</span>
							<input type="text" id="txt_bxzje" class="form-control input-radius number sz" name="bxzje" value="${zbxx.bxzjes }" 
							 onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly/>
<%-- 							<input type="hidden" id="txt_bxzjes" class="form-control input-radius number szs" name="bxzjes" value="${zbxx.bxzje }"  --%>
<!-- 							 onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly/> -->
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>报销事由</span>
							<textarea style="width:100%;height:60px;" name="bxsy" class="form-control input-radius">${zbxx.bxsy}</textarea>
							<input type="hidden" name="bz" class="form-control input-radius">${zbxx.bz}
						</div>
					</div>
				</div>
					<div class="box-panel">
						<div class="box-header clearfix">
							<div class="sub-title pull-left text-primary">
							<i class="fa icon-xiangxi"></i>上传附件
							</div>
							<button type="button" class="btn btn-default" style="margin-left:10px;" id="scanToServer">扫描上传</button>
						</div>
							
					<div class="container-fluid box-content">
					<div class="row">
						<div class="col-md-12">
							<input id="imageFile" name="path" type="file" multiple class="file-loading"/>
							<div id="errorBlock" class="help-block"></div>
						</div>
					</div>
					</div>
				</div>
					
				
				</div>
			</div>
		</div>
		</form>
		 <form id="form3" action="">
		<div class="box" id="operate">
			<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>发票信息
	            	</div>
	            	<div class="actions">
	            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	            	</div>
	        </div>
	        <hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables1" class="table table-striped table-bordered" style="width:100%!important;">
		        	<thead id="thead">
				        <tr>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<c:forEach items="${fplist}" var="list" varStatus="i">
				    		<c:if test="${list.zbid!=null and list.fph1!=null}">
			    			<tr class="tr_${i.index+1}">
				    		<td class="btn_td"><div class="deleteBtn"></div></td>
								<td>
									<span>发票号</span>
								</td>
								<td>
								<input type="text" id="dxt_fpxx${i.index+1}" class=" input-radius window null fpxx1" name="fpxx1" value="${list.fph1}">
								</td>
								<td>
								<input type="text" id="dxt_fpxx${i.index+1}" class=" input-radius window null fpxx2" name="fpxx2" value="${list.fph2}">
								</td>
								<td>
								<input type="text" id="dxt_fpxx${i.index+1}" class=" input-radius window null fpxx3" name="fpxx3" value="${list.fph3}">
								</td>
								<td>
								<input type="text" id="dxt_fpxx${i.index+1}" class=" input-radius window null fpxx4" name="fpxx4" value="${list.fph4}">
								</td>
								<td>
								<input type="text" id="dxt_fpxx${i.index+1}" class=" input-radius window null fpxx5" name="fpxx5" value="${list.fph5}">
								</td>
							</tr>
				    		</c:if>
				    	</c:forEach>
				    	<tr>
				    		<td class="btn_td"><div class="addBtn"></div></td>
				    			<td>
				    				<span>发票号</span>
								</td>
								<td>
								<input type="text" id="dxt_fpxx0" class=" input-radius window null fpxx1" a="a" name="fpxx1" value="">
								</td>
								<td>
								<input type="text" id="dxt_fpxx0" class=" input-radius window null fpxx2" a="a" name="fpxx2" value="">
								</td>
								<td>
								<input type="text" id="dxt_fpxx0" class=" input-radius window null fpxx3" a="a" name="fpxx3" value="">
								</td>
								<td>
								<input type="text" id="dxt_fpxx0" class=" input-radius window null fpxx4" a="a" name="fpxx4" value="">
								</td>
								<td>
								<input type="text" id="dxt_fpxx0" class=" input-radius window null fpxx5" a="a" name="fpxx5" value="">
								</td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form> 
		<form id="form2" class="myform2" action="">
		<div class="box" id="operate">
			<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>报销明细信息
	            	</div>
	            	<div class="actions">
	            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	            	</div>
	        </div>
	        <hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">操作</th>
				            <th style="width:300px;"><span class="required" style="color:red">*</span>项目名称</th>
				            <th style="width:300px;"><span class="required" style="color:red">*</span>剩余金额（元）</th>
				            <th style="width:300px;"><span class="required" style="color:red">*</span>费用名称</th>
				            <th style="width:200px;"><span class="required" style="color:red">*</span>报销金额（元）</th>
				            <th style="width:200px;"><span class="required" style="color:red">*</span>票据张数（张）</th>
				            <th style="width:550px;">备注</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				    	<c:forEach items="${xmlist}" var="list" varStatus="i">
				    		<c:if test="${list.xmguid!=null and list.xmmc!=null}">
							<tr class="tr_${i.index+1}">
								<td class="btn_td"><div class="addBtn add"></div></td>
								<td>
								<div class="input-group">
								<input type="hidden" id=""   name="start" value="">
								<input type="text" id="dxt_xmmc${i.index+1}" class=" input-radius window null" readonly name="xmmc" value="${list.xmmc}">
								<input type="hidden"  name="zbid" value="${zbid}">
								<span class="input-group-btn">
					    			<button type="button" id="btn_xmmc1"  class="btn btn-link btn-custom"></button>
				    			</span>
								</div>
								</td>
								<td>
								<input type="text" id="dxt_syje${i.index+1}" class=" null window input-radius number" readonly="readonly" name="syje" value="${list.syje}"
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								</td>
								<td>
								<div class="input-group">
								<input type="text" id="txt_fymc${i.index+1}" a="a" style="background-color: white !important;" name="fymc" readonly class=" input-radius window null" value="${list.fymc}">
								<input type="hidden" id="txt_fyid${i.index+1}" a="a"   name="fyid" value="${list.fyid}">
								<span class="input-group-btn">
					    			<button type="button" id="btn_fymc${i.index+1 }"  class="btn btn-link btn-custom">选择</button>
				    			</span>
								</div>
								</td>
								<td>
								<input type="text" id="txt_bxje${i.index+1}" a="a" class=" input-radius sz number null txt_bxje hs"  name="bxje" value="${list.bxje}"
									onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								</td>
								<td>
								<input type="text" id="txt_pjzs${i.index+1}" a="a"  class=" input-radius sz null int" name="fjzs" value="${list.fjzs}"
										  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								</td>
								<td>
								<input type="hidden"  name="zbid" value="${zbid}">
								<input type="hidden"  name="xmguid" value="${xmguid}">
								<input type="text" id="txt_bz${i.index+1}" a="a"  class=" null window input-radius" name="bz" value="${list.bz}">
								<input type="hidden" id=""   name="end" value="">
								</td>
							</tr>
				    		</c:if>
				    	</c:forEach>
				    </tbody>
				</table>
				<br>
				<div class="container-fluid box-content">
				<div class="row">
					<div class="pull-center" style="text-align:center;">
				  		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
				  		<button type="button" class="btn btn-default" id="btn_next" style="background:#00acec;color: white;">下一步</button>
				 		</div>
					</div>
				</div>
				<br><br>
			</div>
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<script type="text/javascript">
var basePath = "${ctx}/wsbx/ccyw/ccywsq";
var tag = true;
var kyje;
$(function(){
	pjzs();
// 	computer();
	var validate = $('#myform').bootstrapValidator({fields:{
		bxsy:{validators: {notEmpty: {message: '报销事由不能为空'}}},
       	}
	   });
	//控制显示
	kz();
	//返回按钮
	$("#btn_back").click(function(e){
		var look = "${look}";
		if(look=="look"){
			location.href="${ctx}/kylbx/gowdbxListPage";
		}else{
			location.href="${ctx}/wsbx/rcbx/goRcbxListPage?mkbh=${mkbh}";
		}
	});
	//保存费用填写信息
	function doSave(_validate, _formId, _url,toUrl,flag, _success, _fail){
		var kyje = computerKyje();
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
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
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){
					if(true){
						var lastval = $("#form2 tr:last").find("[name^=xmmc]").val();
						if(lastval==""){
							$("#form2 tr:last").remove();
						}else{
							
						}
						var json = $('#form2').serializeObject("start","end");  //json对象				
				  		var jsonStr = JSON.stringify(json);  //json字符串
				  		$.ajax({
				  			url:"${ctx}/wsbx/rcbx/doSaveBxmx?zbid=${zbid}",
				  			data:"jsonStr="+jsonStr,
				  			type:"post",
				  			success:function(val){
							 	if(true){
							 		var lastval = $("#form3 tr:last").find("[name=xmmc]").val();
									if(lastval==""){
										$("#form3 tr:last").remove();
									}else{
										
									}
									var json = $('#form3').serializeObject("fpxx1","fpxx5");  //json对象	
									var jsonStr = JSON.stringify(json);  //json字符串
									$.ajax({
										url:"${ctx}/wsbx/rcbx/doSavefpxx?zbid=${zbid}",
							  			data:"jsonStr="+jsonStr,
							  			type:"post",
							  			complete:function(){ 
							  				var hjje = $("#txt_bxzje").val();
			 				  				var frame = $(".file-live-thumbs").find(".file-preview-frame");
			 				  				var tp_frame = $(".file-initial-thumbs").find(".file-preview-frame");
											if(frame.length > 0||tp_frame.length>0){
												$("#imageFile").fileinput("upload");
											}else{
												if(flag=="after"){
													var src = "${ctx}/webView/wsbx/rcbx/selectXm.jsp?xmguid=${xmguid}&flag=1&zbid=${zbid}";
													location.href = src;
												}else{
													var src = "${ctx}/wsbx/rcbx/jsfs?sfxz=${sfxz}&look=${look}&djbh=${djbh}&zbid=${zbid}&xmmc=${xmmc}&xmguid=${xmguid}&mkbh=${mkbh}&bxzje="+hjje+"&kyje="+kyje;
													location.href = src;
												}
											}
			 				  				tag = true;
							  		 	}
									}); 
								}
				  			}
				  		});
					}
				},
			});
		}
	}
	//图片信息开始
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
    	uploadUrl: '${pageContext.request.contextPath}/file/fileUpload',//上传请求路径
        maxFilePreviewSize: 10240,//最大上传文件的大小
        showUpload:false,//是否显示上传按钮
        initialPreviewShowDelete:true,
        showBrowse:true,
        showCaption:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"zcxx","id":"${zbid}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        allowedFileExtensions :['rar','zip','jpg','png', 'doc','docx','xls','xlsx','pdf',],
        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
    }).on("filebatchpreupload",function(){
    	load = loading(2);
    }).on("filebatchuploadcomplete",function(){
    	close(load);
    	var flag = $("#flag").val();
    	var hjje = $("#txt_bxzje").val();//zongyu总余额
    	if(flag=="2"){
			var src = "${ctx}/webView/wsbx/rcbx/selectXm.jsp?xmguid=${xmguid}&flag=1&zbid=${zbid}";
			location.href = src;
		}else{
			var src = "${ctx}/wsbx/rcbx/jsfs?sfxz=${sfxz}&look=${look}&djbh=${djbh}&zbid=${zbid}&xmmc=${xmmc}&xmguid=${xmguid}&mkbh=${mkbh}&bxzje="+hjje+"&kyje="+computerKyje();
			location.href = src;
		}
    });
	//验证费用名称
	var kmLength="0";
	var	fyIdlen="";
    function FyId(){ 
	   var fyId=$("[name='fyid']");
	       fyIdlen=fyId.length;
	   $.each(fyId,function(){
		   var fyid=$(this).val();
		   if(fyid==""){
			   fyIdlen--;
		   }
		   $.ajax({
	         url:"${ctx}/wsbx/rcbx/doSelectFyid?fyid="+fyid,
	         data:"",
	         type:"post",
	         async:false,
	         success:function(val){
	           if(val=="1"){
	        	   kmLength++;
	           }
	         },
	         error:function(val){
	        	 
	         }
	   	   });
	   });
	}
	//联想输入
	$("#txt_bxr").bindChange("${ctx}/suggest/getXx","R");//报销人
	$("#txt_bmmc").bindChange("${ctx}/suggest/getXx","D");//部门
	//弹窗
	$("#btn_bxr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_bxr","人员","920","630");
    });
	//弹窗
	$("#btn_skr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_skr","人员信息","920","630");
    });
	//部门弹窗选择
	$("#btn_bmmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_bmmc","部门信息","920","630");
    });
	//项目弹窗选择
	$("#btn_xmbh").click(function(){
		select_commonWin("${ctx}/wsbx/rcbx/window?type=xm&controlId=txt_xmbh","信息","920","630");
    });
	//返回上一步
	$("#btn_after").click(function(){
		var money = "${money}";
		var hjje = $("[id='txt_bxzje']").val();//获取总报销金额 
		$("#flag").val("2");
		doSave(validate,"myform","${ctx}/wsbx/rcbx/doSave",hjje,"after",function(val){
		});
	});
	//下一步
	$("#btn_next").click(function(){
		FyId();
		var hjje = $("[id='txt_bxzje']").val();//获取总报销金额 
        if(kmLength==fyIdlen || kmLength=="0"){
			
		}else{
			alert("您选择的含有非经济科目费用名称，请重新选择费用名称！");
			kmLength="0";
			return;
		}
		if(hjje<0 || hjje==0){
			alert("报销总金额必须大于0");
			return;
		}
		if(checkNull($("#mydatatables tbody tr"))){
			alert("必填项不能为空！");
			return;
		}
		var fymc = $("[name='fymc']");
		var flag = true;
		fymc.each(function(){
			if($(this).val()==""){
				flag=false;
			}
		})
		if(flag){
			if(yzfph()){
				$("#flag").val("1");
				doSave(validate,"myform","${ctx}/wsbx/rcbx/doSave",hjje,"",function(val){
				});
			};
		}else{
			alert("存在费用为空的项目信息，请进行必填项的填写！");
		}
	});
	//验证发票号
	function yzfph(){
		var fph = $("input[name^=fpxx]");
		var flag = false;
		var fphs = [];
		fph.each(function(){
			if($(this).val()!=""){
				flag = true;
				fphs.push($(this).val());
			}
		});
		if(!chachong(fphs)){
			alert("发票号重复");
			return false;
		}else if(!ajaxchachong(fphs)){
			return false;
		}else{
			return true;
		}
	}
	//页面查重函数  
	function chachong(arr){  
	    var res=[];  
	    var json={};  
	    for(var i=0;i<arr.length;i++){  
	       if(!json[arr[i]]){  
	          res.push(arr[i]);  
	          json[arr[i]]=1;  
	       }  
	    }  
	    return res.length==arr.length;  
	}  
	//数据库查重
	function ajaxchachong(arr){ 
		var flag;
		var arrs = arr.join(",");
		$.ajax({
   			url:"${ctx}/wsbx/ccbx/checkFph",
   			type:"post",
   			data:"arr="+arrs+"&zbid=${zbid}",
   			async:false, 
   			success:function(data){
   				if(data!="0"){
   					alert("发票号“"+data+"”重复");
   					flag = false;
   				}else{
   					flag = true;
   				}
   			}
		}); 
		return flag;
	}
	//判空校验
	function checkNull($obj){
		var tag = false;
		$.each($obj,function(){
			var fymc = $(this).find("[name='fymc']").val();
			var bxje = $(this).find("[name='bxje']").val();
			var fjzs = $(this).find("[name='fjzs']").val();
			var bz = $(this).find("[name='bz']").val();
			//如果全部为空则不做判断
			if(fymc == ""&&bxje==""&&fjzs==""){
				return;
			}
			if(fymc==""||bxje==""||fjzs==""){
				tag = true;
			}
		});
		return tag;
	}
});
//明细js操作
$(function(){
   	//费用名称
	$(document).on("click","[id^='btn_fymc']",function(){
		if($(this).hasClass("non-edit")){
			return;
		}
		var xmguid = $("#dxt_xmid").val();
		var basePath = getBasePath();
		var id = $(this).parents("td").find("[id^=txt_fymc]").attr("id");
		var fyid = $(this).parents("td").find("[id^=txt_fyid]").attr("id");
		select_commonWin("${ctx}/kjhs/pzxx/pzlr/pageSkip?pageName=jjkmTree&isAll=0&controlId="+id+"&fyid="+fyid+"&xmguid="+xmguid,"经济科目信息","1000","650");
	});
	$("[id^='txt_fymc']").focus();
  //课题名称弹窗
     $(document).on("click",".btn-ktmc",function(){
    	 var txt_ktmc = $(this).parents("td").find("[id^=txt_ktmc]").attr("id");
    	 var txt_syje = $(this).parents("td").siblings().find("[id^=txt_syje]").attr("id");
    	 var txt_jflx = $(this).parents("td").siblings().find("[id^=txt_syje]").attr("id");
    	 var txt_jfbh = $(this).parents("td").find("[id^=txt_jfbh]").attr("id");
    	 var xmguid = "${xmguid}";
    	select_commonWin("${ctx}/wsbx/ccyw/ccywsq/jfszList?cId_1="+txt_ktmc+"&cId_2="+txt_jflx+"&cId_3="+txt_syje+"&cId_4="+txt_jfbh+"&xmguid="+xmguid,"项目信息","900","620");
    }); 
	var num = $("#mydatatables").find("tr").length*1;
	$(document).on("click","[class*=addBtn]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
        $parentTr.find("[a='a']").val("");
		$parentTr.find(":input").removeClass("border");
		$parentTr.attr("id","tr_"+num);
		$parentTr.find("[id^=txt_ktmc]").attr({"id":"txt_ktmc"+num});
		$parentTr.find("[id^=txt_fymc]").attr({"id":"txt_fymc"+num});
		$parentTr.find("[id^=txt_syje]").attr({"id":"txt_syje"+num});
		$parentTr.find("[id^=txt_jflx]").attr({"id":"txt_jflx"+num});
		$parentTr.find("[id^=txt_jfbh]").attr({"id":"txt_jfbh"+num});
		$parentTr.find("[id^=txt_bxje]").attr({"id":"txt_bxje"+num});
		$parentTr.find("[id^=txt_fjzs]").attr({"id":"txt_fjzs"+num});
		$parentTr.find("[id^=txt_fyid]").attr({"id":"txt_fyid"+num});
		num++;
		$(this).parents("tr").after($parentTr);
	});
	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();
		//删除后重新计算金额
		computer();
		pjzs();
	});
	//输入报销金额
	$(document).on("keyup","[id^=txt_bxje]",function(){
		computer(this);
	});
	//计算票据总张数
	$(document).on("keyup","[name^=fjzs]",function(){
		pjzs();
	});
});
$("[name='sklx']").click(function(){
	kz();
});
function kz(){
	var sklx = $("[name='sklx']").filter(":checked").val();
	if(sklx=="1"){
		$(".dw").addClass("yc");
		$(".gr").removeClass("yc");
	}else{
		$(".gr").addClass("yc");
		$(".dw").removeClass("yc");
	}
}
//备注长度大于100截取
$(document).on("keyup","[name='bz']",function(){
	value = $(this).val();
	var length = value.length;
	if(length>100){
		$(this).val(value.substring(0,100));
		return;
	}
});
//鼠标靠近移除样式
$(document).on("focus","[class*=border]",function(){
	$(this).removeClass("border");
});
//业务说明
$("#btn_ywsm").click(function(){
	zysx();
});
//扫码上传
$("#scanToServer").click(function(){
	scanToServer();
});
//扫码上传弹窗
function scanToServer(){
	select_commonWin("${pageContext.request.contextPath}/ywsm/goScanPage?mkbh=${param.mkbh}&sfbc=1&zbid=${zbid}&basePath=<%=basePath%>&fold=zcxx","", "920", "530");
}
//业务说明
function zysx(){
	select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
}
//计算票据总张数
function pjzs(){
	var zzs = 0;
	$.each($("[name^=fjzs]"),function(i,v){
		var zs = $(this).val();
		if(zs!=""&&zs!=0&&!isNaN(zs)){
			zzs = parseInt(zzs) + parseInt(zs);
		}
	});
	$("[name='fjzzs']").val(zzs);
}
//计算金额
function computer(obj){
	var je = 0;
	var hjje = 0;
	//合计金额
	$.each($("[id^=txt_bxje]"),function(){
		var $this = $(this);
		je = $this.val();
		if(isNaN(je)||je==""){
			je = 0;
		}
		hjje = parseFloat(hjje) + parseFloat(je);
	});
	if(hjje<0){
		hjje = 0.00;
	}
	var kyje = computerKyje_2();
	if(hjje>kyje){
		alert("报销总金额已超出目前项目的可用金额");
		console.log(obj);
		var id = obj.id; 
		$("#"+id).val("");
		computer();
	}else{
		$("[name='bxzje']").val(hjje.toFixed(2));
	}
}
function computerKyje(){
	//判断 是否超出 项目 剩余金额+
	var xmguid = $("#dxt_xmid").val();
	var bxje = "${zbxx.bxzje }";
	$.ajax({
		type:"post",
		data:"xmguid="+xmguid,
		url:"${ctx}/wsbx/rcbx/getXmkyje",
		async:false,
		dataType:"json",
		success:function(val){
			if(bxje!=''){
				kyje = parseFloat(val.kyje) + parseFloat(bxje);		
			}else{
				kyje = parseFloat(val.kyje);
			}
		}
	});
	return kyje;
}
function computerKyje_2(){
	//判断 是否超出 项目 剩余金额+
	var xmguid = $("#dxt_xmid").val();
	var bxje = "${zbxx.bxzje }";
	$.ajax({
		type:"post",
		data:"xmguid="+xmguid,
		url:"${ctx}/wsbx/rcbx/getXmkyje_2",
		async:false,
		dataType:"json",
		success:function(val){
			if(bxje!=''){
				kyje = parseFloat(val.kyje) + parseFloat(bxje);		
			}else{
				kyje = parseFloat(val.kyje);
			}
		}
	});
	return kyje;
}

$(function(){
	addbutton();
	function addbutton(){
		var a = $(".tbody").find("tr").length;
		var aaa="${xmlist}";
		if(aaa.length>0){
			$(".add").removeClass("addBtn")			
			$(".add").addClass("deleteBtn");		
			$(".tbody tr:last").find(".add").removeClass("deleteBtn");
			$(".tbody tr:last").find(".add").addClass("addBtn");
		}else{
			
		}
	}
	
});
SmscLoad("${pageContext.request.contextPath}",{"id":"${zbid}","djlx":"000000","fold":"zcxx","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
</script>

</html>