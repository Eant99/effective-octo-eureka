<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>日常报销 凭证退回</title>
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
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
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
	tr{
        background-color: white !important;
    }
    tbody td{
	   padding:4px !important;
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
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden"  name="guid" value="${guid}">
	<div class="box">
		<div class="box-panel">		
			<div class="box-header1 clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>日常报销信息
            	</div>
            	<div class="pull-right">
<!--             		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明"> -->
<!-- 						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i> -->
<!-- 						<font style="font-size:14px;">业务说明</font> -->
<!-- 					</button> -->
			  		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			  		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号</span>
							<input type="text" id="txt_djbh" readonly class="form-control input-radius" readonly name="djbh" value="${zbxx.djbh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报&ensp;销&ensp;人</span>
                            <input type="text" id="txt_bxr" readonly class="form-control input-radius window" name="" value="${zbxx.bxrname}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">部&emsp;&emsp;门</span>
							<input type="text" id="txt_bmmc" readonly class="form-control input-radius window" name="" value="${zbxx.dwname}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">附件总张数（张）</span>
							<input type="text" id="txt_fjzzs" class="form-control input-radius sz" name="fjzzs" readonly value="${zbxx.fjzzs }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销总金额（元）</span>
							<input type="text" id="txt_bxzje" class="form-control input-radius number sz" name="bxzje" value="${zbxx.bxzje }" 
							 onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" readonly/>
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
<!-- 				    		<td class="btn_td"><div class="deleteBtn"></div></td> -->
								<td>
									<span>发票号</span>
<%-- 								<input type="text" id="dxt_fp${i.index+1}" class=" input-radius window null" readonly name="fp" value=""> --%>
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
<!-- 				    	<tr> -->
<!-- <!-- 				    		<td class="btn_td"><div class="addBtn"></div></td> -->
<!-- 				    			<td> -->
<!-- 				    				<span>发票号</span> -->
<!-- 								</td> -->
<!-- 								<td> -->
<!-- 								<input type="text" id="dxt_fpxx0" class=" input-radius window null fpxx1" name="fpxx1" value=""> -->
<!-- 								</td> -->
<!-- 								<td> -->
<!-- 								<input type="text" id="dxt_fpxx0" class=" input-radius window null fpxx2" name="fpxx2" value=""> -->
<!-- 								</td> -->
<!-- 								<td> -->
<!-- 								<input type="text" id="dxt_fpxx0" class=" input-radius window null fpxx3" name="fpxx3" value=""> -->
<!-- 								</td> -->
<!-- 								<td> -->
<!-- 								<input type="text" id="dxt_fpxx0" class=" input-radius window null fpxx4" name="fpxx4" value=""> -->
<!-- 								</td> -->
<!-- 								<td> -->
<!-- 								<input type="text" id="dxt_fpxx0" class=" input-radius window null fpxx5" name="fpxx5" value=""> -->
<!-- 								</td> -->
<!-- 				    	</tr> -->
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
								<td>
									<div class="input-group">
										<input type="hidden" id=""   name="start" value="">
										<input type="text" id="dxt_xmmc${i.index+1}" class=" input-radius window null" readonly name="xmmc" value="${list.xmmc}">
										<input type="hidden" id="dxt_xmid${i.index+1}"   name="xmguid" value="${list.xmguid}">
										<input type="hidden" name="jsbxzje" value="">
										<input type="hidden" name="jyzjehj" value="">
										<input type="hidden"  name="guid" value="${list.guid}">
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
								    <input type="text" id="txt_bxje${i.index+1}"  readonly a="a" class=" input-radius sz number null txt_bxje hs"  name="bxje" value="${list.bxje}"
									   onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								</td>
								<td>
									<input type="text" id="txt_pjzs${i.index+1}" a="a"  class=" input-radius sz null int" name="fjzs" value="${list.fjzs}"
									   onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
								</td>
								<td>
									<input type="text" id="txt_bz${i.index+1}" a="a"  class=" null window input-radius" name="bz" value="${list.bz}">
									<input type="hidden" id=""   name="end" value="">
								</td>
							</tr>
				    		</c:if>
				    	</c:forEach>
				    </tbody>
				</table>
				<br>
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
$(function(){
// 	//业务说明
// 	$("#btn_ywsm").click(function(){
// 		zysx();
// 	});	
// 	function zysx(){
// 		select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
// 	}
	//是编辑还是查看
	$("input[name^='fpxx']").attr("readonly","readonly");
	if("${operateType}"=="L"){
		$("#btn_save").hide();
		$(".btn-custom").hide();
		$("input").attr("readonly","readonly");
		$("textarea").attr("readonly","readonly");
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
        	return {"fold":"zcxx","id":"${guid}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
    });
	//附件总张数
	//验证
	var validate = $('#myform').bootstrapValidator({fields:{
		bxsy:{validators: {notEmpty: {message: '报销事由不能为空'}}},
       	}
    });
	//返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/bxpzth/gorcbx";
	});
	// 发票信息 加减行数
	var num = $("#mydatatables1").find("tr").length;
	$(document).on("click","[class*=addBtn]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").removeClass("border");
		num++;
		$(this).parents("tr").after($parentTr);
	});
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();
	});
	//保存费用填写信息
	$(document).on("click","#btn_save",function(){
		doSaveRC(validate,"myform","${ctx}/bxpzth/doUpdateRC",function(val){});
	});
	function doSaveRC(_validate, _formId, _url, _success, _fail){
		if(_validate){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				dataType:"json",
				success:function(val){					
						var lastval = $("#form2 tr:last").find("[name^=xmmc]").val();
						if(lastval==""){
							$("#form2 tr:last").remove();
						}else{
							
						}
						var json = $('#form2').serializeObject("start","end");  //json对象				
				  		var jsonStr = JSON.stringify(json);  //json字符串
				  		$.ajax({
				  			url:"${ctx}/bxpzth/doUpdateRcBxmx",
				  			data:"jsonStr="+jsonStr,
				  			type:"post",
				  			success:function(val){
							 		$("#imageFile").fileinput("upload");//上传附件信息	
							 		location.href="${ctx}/bxpzth/gorcbx";
							 		alert("保存成功!");
				  			}
				  		});
				},//success
			});
		}//if
	}	
	//验证费用名称
	
	var kmLength="0";
	var	fyIdlen="";
    function FyId(){ 
	   var fyId=$("[name='fyid']");
	       fyIdlen=fyId.length;
	   $.each(fyId,function(){
		   var fyid=$(this).val();
		   console.log("fyid-----"+fyid);
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
		console.log(arr);
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
// 			var bcbxje = $(this).find("[name='bcbxje']").val();
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
		var basePath = getBasePath();
		var id = $(this).parents("td").find("[id^=txt_fymc]").attr("id");
		var fyid = $(this).parents("td").find("[id^=txt_fyid]").attr("id");
		select_commonWin("${ctx}/kjhs/pzxx/pzlr/pageSkip?pageName=jjkmTree&isAll=0&controlId="+id+"&fyid="+fyid,"经济科目信息","1000","650");
	});
	$("[id^='txt_fymc']").focus();
	//计算票据总张数
	$(document).on("keyup","[name^=fjzs]",function(){
		pjzs();
	});
});
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
//扫码上传
$("#scanToServer").click(function(){
	scanToServer();
});
//扫码上传弹窗
function scanToServer(){
	select_commonWin("${pageContext.request.contextPath}/ywsm/goScanPage?mkbh=${param.mkbh}&sfbc=1&zbid=${guid}&basePath=<%=basePath%>&fold=zcxx","", "920", "530");
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
SmscLoad("${pageContext.request.contextPath}",{"id":"${guid}","djlx":"000000","fold":"zcxx","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
</script>

</html>