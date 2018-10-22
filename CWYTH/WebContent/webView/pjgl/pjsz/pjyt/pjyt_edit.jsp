<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>票据管理</title>
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
	
	<div class="page-header">
		<!--标题-->
		<div class="pull-left">
			<span class="page-title text-primary"><i
				class='fa icon-xiangxixinxi'></i>添加票据用途申请信息</span>
		</div>
		<!-- 操作按钮 -->
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save">
				<i class="fa icon-save"></i>保存
			</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
		</div>
	</div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>票据用途详细信息
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
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>票据用途编码</span>
								<%-- <input type="text" id="" class="form-control input-radius"  name="pjytbh" value="${pjytMap.pjytbh}" > --%>
								<input type="text" id="txt_mbbh" name="pjytbh" class="form-control input-radius"
									style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"	value="${pjytMap.pjytbh}" readonly/>
		                        <input type="hidden" id="guid"	name="guid" value="${guid}"> 
							
						</div>
					</div>
				
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>票据用途名称</span>
							<input type="text" id=""  class="form-control input-radius" name="pjytmc" value="${pjytMap.pjytmc}"/>
						</div>
					</div>
				</div>
				<div class="row">	
					<div class="col-md-6">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;">是否启用</span>
							<div class="radiodiv" >
	                			 &nbsp; &nbsp;
									<input type="radio" name="sfqy" class="rdo" value="${pjytMap.sfqy}" <c:if test="${pjytMap.sfqy=='1' }"> checked</c:if>/>
									<label>是</label>
								 &nbsp; &nbsp; &nbsp;
									<input type="radio" name="sfqy" class="rdo" value="${pjytMap.sfqy}"  <c:if test="${pjytMap.sfqy=='0' }"> checked</c:if> />
									<label>否</label>
							</div>
						</div>
					</div>
				</div>
				<div class="row">	
					
					 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备 &ensp; &ensp;注</span>
                            <textarea id="txt_zynr" class="form-control" name="bz" >${pjytMap.bz}</textarea>
						</div>
					</div>
					
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

var yhmcs=$("[name='klx']");
$.each(yhmcs,function(){
	var yhmc=$(this).val();
	console.log("yhmc------"+yhmc);
	if(yhmc==""||yhmc==null){
		
	}else{
		$(this).attr("disabled",true);
	}
});

$(function(){
	kz();
	$("#btn_jkr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_jkr&ryid=txt_jkr1","人员信息","920","630");
    });
   //返回按钮
 	$("#btn_back").click(function(e){
 			location.href="${ctx}/pjgl/pjyt/goPjytPage";	
 	});
	var validate = $("#myform").bootstrapValidator({fields:{
		pjytbh:{validators:{notEmpty: {message: '票据用途编码不能为空'}}},
		pjytmc:{validators:{notEmpty: {message: '票据用途名称不能为空'}}}
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
	   		console.log("${xmxx.guid}");
			select_commonWin("${ctx}/wsbx/ccyw/ccywsq/jfszdxList?fromPage=jksqEdit&flag=1&cId_1="+txt_ktmc+"&cId_3="+txt_syje+"&cId_4="+txt_jfbh+"&guid="+guid.join("','"),"项目信息","900","620");
		
		 })
		 
		var cc = $("[name^=ryxz]");
		$.each(cc,function(){
// 			console.log($(this).prop("checked"));
			var val = $(this).prop("checked");
			if(val){
				$(this).parents("tr").find("[name='ryxz']").val($(this).val());
			}
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
		//保存按钮
		$("#btn_save").click(function(e){	
			checkAutoInput();
				doSave1(validate,"myform","${ctx}/pjgl/pjyt/doSave?operateType=${operateType}",function(val){
					window.location.href = "${ctx}/pjgl/pjyt/goPjytPage";
					alert("保存成功");
				});
		});	
		function doSave1(_validate, _formId, _url, _success, _fail){
//			checkAutoInput();
			var index;
			var valid;
			if(_validate){
				_validate.bootstrapValidator("validate");
				valid = $("#myform").data('bootstrapValidator').isValid();//ldhldhldh
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
//	 				dataType:"json",
					data:arrayToJson($("#myform").serializeArray()),
					success:function(val){	
						alert("保存成功！"); 
						window.location.href = "${ctx}/pjgl/pjyt/goPjytPage";
					},
					error:function(val){
						console.log("___"+val);
					}	
				});
			}
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
	
});

//添加项目
function addXmxx($checkbox){
	//添加的项目信息
	var addNum = 0;
	$.each($checkbox,function(){
		var $tr = $(".xmxx tbody tr:last-child").clone();
		var ktmc = $(this).find(".keyId").attr("ktmc");
		var syje = $(this).find(".keyId").attr("syje");
		var jfbh = $(this).find(".keyId").attr("gid");
		$tr.find("[name='ktmc']").val(ktmc);
		$tr.find("[name='syje']").val(parseFloat(syje).toFixed(2));
		$tr.find("[name='jfbh']").val(jfbh);
		$tr.find(".addBtn2").attr("class","deleteBtn");
		$(".xmxx tbody tr:last-child").before($tr);
		addNum ++;
	});
	 $(".xmxx tbody tr:last-child").remove();
	 $(".xmxx tbody tr:last-child").find(".deleteBtn").attr("class","addBtn2");
}
function countJe(){
	var jkzje = 0;
	$.each($("[id^=txt_zcje]"),function(){
		var zcje = $(this).val();
		if(zcje==""||zcje==0||isNaN(zcje)){
			zcje = 0.00;
		}
		jkzje = parseFloat(jkzje) + parseFloat(zcje);
	});
	$("#txt_jkzje").val(jkzje.toFixed(2));
};



//默认隐藏的元素
function kz(){
	$("")
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
						//if(i==0){
							var id = v.GUID;
							var mc = v.KHYH;
							var zh = v.KHYHZH;
							if(dfyh==id){
								select.append("<option value="+id+" zh="+zh+" selected>"+mc+"</option>");
							}else{
								select.append("<option value="+id+" zh="+zh+">"+mc+"</option>");
							}
						//}
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
	jkzje();
}
//计算结算总金额
function jkzje(){
	var money = $(":checkbox").filter(":checked").parents("div").find("[class$=tsinput]");
	var count = 0;
	$.each(money,function(){
		if($(this).val()!=""||!isNaN($(this).val)){
			count = Number(count)+Number($(this).val());
		}
	});
	count = parseFloat(count).toFixed(2);
// 	$("[id=txt_jkzje]").val(count);
	if(count>0){
		$("[id=txt_jkzje]").css("color","green");
	}else if(count<0){
		$("[id=txt_jkzje]").css("color","red");
	}else{
		$("[id=txt_jkzje]").css("color","blue");
	}
}
</script>
<!-- 对私支付js -->
<script type="text/javascript">
$(document).on("change","[id^=txt_klx]",function(){
	var zh = $(this).find("option:selected").attr("zh");
	var zhbguid = $(this).find("option:selected").attr("zhbguid");
	$(this).parents("tr").find("[id^=txt_dfzh]").val(zh);
	$(this).parents("tr").find("[id^=txt_guid]").val(zhbguid);
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
		console.log(dqdlrguid);
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
						parentTr.find("[id^=txt_klx]").append("<option value="+mc+" zh="+zh+" zhbguid="+id+">"+mc+"</option>");
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
	$(this).parents("tr").find("[name=ryxz]").val(val);
})
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
    	var ryguid = [];
    	$("[name='jfbh'").each(function(){
			ryguid.push($(this).val());
		});
	    select_commonWin("${ctx}/wsbx/ccbx/window?flag=1&controlId=xmfzr&ccywguid="+ryguid.join("','")+"&id="+id,"项目负责人信息","820","630");
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
	console.log(ryguid);
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
					parentTr.find("[id^=txt_klx]").append("<option value="+mc+" zh="+zh+" zhbguid="+id+">"+mc+"</option>");
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
					parentTr.find("[id^=txt_klx]").append("<option value="+mc+" zh="+zh+" zhbguid="+id+">"+mc+"</option>");
				});
			}
		}
	});
}
//点击对私支付
$(".sszkbox").click(function(){
	$(".xnzzbox").attr("checked",false);
	$(".xnzzbox").attr("name","");
	$(".sszkbox").attr("name","zffs");
	$(".deleteBtn_XN").parents("tr").remove();
	$(".deleteBtn_XN").parents(".style_div").find(".xnzz-content").toggle(500);
	$(".xnzzbox").parent("span").addClass("noselect");
	$(".xnzzbox").parent("span").find("span").text("未选择");
	$(".xnzzbox").find(":text").val("")
	$(".xnzz-content:visible").hide();
	$(".xnzz_xj").find(".tsinput").val(Number(0).toFixed(2));
	xjcolor(0,"xnzz_xj");
// 	$(".sszkbox").click();
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
	$(this).parents(".style_div").find(".sszk-content").toggle(500);
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".sszk-content:hidden").show();
		var txt_jkzje=$("#txt_jkzje").val();//获取总金额
// 		var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
// 		var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqcjkcount = 0;
		var schqcount = 0;
		var schqdgcount = 0;
		var schqgwkcount = 0;
// 		$.each(schqcjkmoney,function(){//冲借款
// 			if($(this).val()!=""||!isNaN($(this).val)){
// 				schqcjkcount = Number(schqcjkcount)+Number($(this).val());
// 			}
// 		});
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
// 		$.each(schqgwkmoney,function(){//公务卡
// 			if($(this).val()!=""||!isNaN($(this).val)){
// 				schqgwkcount = Number(schqgwkcount)+Number($(this).val());
// 			}
// 		});
		var syje;
		syje=Number(txt_jkzje)-Number(schqcount);
		if(syje<0){
			syje=0;
		}
		$("#txt_dsje0").val(Number(syje).toFixed(2));
		sszkmoney();
// 	}else{
// 		$(this).parent("span").addClass("noselect");
// 		$(this).parent("span").find("span").text("未选择");
// 		$(".sszk-content:visible").hide();
// 		$(".sszk_xj").find(".tsinput").val(0);
// 		xjcolor(0,"sszk_xj");
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
/* 	$.each(schqgwkmoney,function(){//公务卡
		if($(this).val()!=""||!isNaN($(this).val)){
			schqgwkcount = Number(schqgwkcount)+Number($(this).val());
		}
	}); */
	var syje;
	syje=Number(txt_jkzje)-Number(schqcount);
	if(syje<0){
		syje=0;
	}
	
	var zfdh = $(this).parents("tr").find("[name=zfdh]").val();
	var parentTr = $("#tb_sszk").find(".tr_0").clone();
	$("#tb_sszk").find(".tr_0").attr("class","tr_"+sszk);
	
	parentTr.find(":text").val("");
	parentTr.find("select").empty();
	parentTr.find("[name=zfdh]").val(zfdh);
	parentTr.find("[class^=txt_ryxz]").attr({"class":"txt_ryxz"+sszk});
	parentTr.find("[class^=txt_ryxz]").attr({"name":"ryxz"+sszk});
	parentTr.find("[id^=txt_ryxm]").attr({"id":"txt_ryxm"+sszk}).val("").val("");
	parentTr.find("[id^=txt_klx]").attr({"id":"txt_klx"+sszk}).empty();
	parentTr.find("[id^=bbtn_ryxm]").attr({"id":"bbtn_ryxm"+sszk});
	parentTr.find("[id^=txt_dfzh]").attr({"id":"txt_dfzh"+sszk}).val("");
	parentTr.find("[id^=txt_dsje]").attr({"id":"txt_dsje"+sszk}).val(Number(syje).toFixed(2));
	$("#tb_sszk").find(".addBtn_ZK").removeClass("addBtn_ZK").addClass("deleteBtn_ZK");
	parentTr.find(".deleteBtn_ZK").removeClass("deleteBtn_ZK").addClass("addBtn_ZK");
	$("#tb_sszk").append(parentTr);
// 	$(".txt_ryxz"+sszk).attr("value",$("[name='ryxz']").val());

	sszk++;
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
	var id = $(this).parents("td").find("[name=dfdw1]").attr("id");
	var gid = $(this).parents("td").find("[name=dfdw]").attr("id");
	console.log(id);
	select_commonWin("${ctx}/wsbx/rcbx/window?flag=1&controlId=xnzz&id="+id+"&gid="+gid,"往来单位","920","630");
});
//点击对公支付
$(".xnzzbox").click(function(){
	$(".sszkbox").attr("checked",false);
	$(".sszkbox").attr("name","");
	$(".deleteBtn_ZK").parents("tr").remove();
	$(".xnzzbox").attr("name","zffs");
	$(".deleteBtn_ZK").parents(".style_div").find(".sszk-content").toggle(500);
	$(".sszkbox").parent("span").addClass("noselect");
	$(".sszkbox").parent("span").find("span").text("未选择");
	$(".sszk-content:visible").hide();
	$(".sszk_xj").find(".tsinput").val(Number("0").toFixed(2));
	$(".sszk-content").find(":text").val("");
	$(".sszk-content").find("select").empty();
	xjcolor(0,"sszk_xj");
	var clw = $(this).parent("span").attr("class");
	if(clw=="noselect"){
	$(this).parents(".style_div").find(".xnzz-content").toggle(500);
		$(this).parent("span").removeClass("noselect");
		$(this).parent("span").find("span").text("已选择");
		$(".xnzz-content:hidden").show();
		var txt_jkzje=$("#txt_jkzje").val();//获取总金额
// 		var schqcjkmoney= $("[id^=txt_cjkje]");//首次获取金额  冲借款   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqmoney= $("[id^=txt_dsje]");//首次获取金额  对私  用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqdgmoney= $("[id^=txt_je]");//首次获取金额  对公   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
// 		var schqgwkmoney= $("[id^=txt_skje]");//首次获取金额  公务卡   用报销总金额-首次获取的金额总数  然后 点击事件 放到 金额(元) 里面  
		var schqcjkcount = 0;
		var schqcount = 0;
		var schqdgcount = 0;
		var schqgwkcount = 0;
// 		$.each(schqcjkmoney,function(){//冲借款
// 			if($(this).val()!=""||!isNaN($(this).val)){
// 				schqcjkcount = Number(schqcjkcount)+Number($(this).val());
// 			}
// 		});
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
// 		$.each(schqgwkmoney,function(){//公务卡
// 			if($(this).val()!=""||!isNaN($(this).val)){
// 				schqgwkcount = Number(schqgwkcount)+Number($(this).val());
// 			}
// 		});
		var syje;
		syje=Number(txt_jkzje)-Number(schqdgcount);
		if(syje<0){
			syje=0;
		}
		$("#txt_je0").val(Number(syje).toFixed(2));
		xnzzmoney();
// 	}else{
// 		$(this).parent("span").addClass("noselect");
// 		$(this).parent("span").find("span").text("未选择");
// 		$(".xnzz-content:visible").hide();
// 		$(".xnzz_xj").find(".tsinput").val(Number(0).toFixed(2));
// 		xjcolor(0,"xnzz_xj");
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
	syje=Number(txt_jkzje)-Number(schqdgcount);
	if(syje<0){
		syje=0;
	}
	var parentTr = $("#tb_xnzz").find(".tr_0").clone();
	$("#tb_xnzz").find(".tr_0").attr("class","tr_"+xnzz);
	parentTr.find("input").val("");
	parentTr.find("[name='zfdh']").val("${zbid}");
	parentTr.find("select").empty();
	$("#tb_xnzz").find("[class^=addBtn_XN]").removeClass("addBtn_XN").addClass("deleteBtn_XN");
	parentTr.find("#txt_dfdw0").attr({"id":"txt_dfdw"+xnzz});
	parentTr.find("#txt_dwmc0").attr({"id":"txt_dwmc"+xnzz});
	parentTr.find("#btn_dfdw0").attr({"id":"btn_dfdw"+xnzz});
	parentTr.find("#txt_dfdz0").attr({"id":"txt_dfdz"+xnzz});
// 	parentTr.find("#txt_dfyh0").empty();
	parentTr.find("#txt_dfyh0").attr({"id":"txt_dfyh"+xnzz});
	parentTr.find("#txt_dfzh0").attr({"id":"txt_dfzh"+xnzz});
	parentTr.find("#txt_je0").attr({"id":"txt_je"+xnzz}).val(Number(syje).toFixed(2));
	parentTr.find("[class^=deleteBtn_XN]").removeClass("deleteBtn_XN").addClass("addBtn_XN");
	$("#tb_xnzz").append(parentTr);
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
</script>
</html>