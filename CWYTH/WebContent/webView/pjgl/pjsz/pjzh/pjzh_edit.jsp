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
				class='fa icon-xiangxixinxi'></i>添加票据账户信息</span>
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
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>票据账户详细信息
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
			<div class="container-fluid box-content " >
				<!-- <div class="row">
					<div class="col-md-12" align="center">
						<h4>票据账号</h4>
					</div>
				</div> -->
				<div class="row">
					<div class="col-md-4 col-md-offset-4">
						<div class="input-group ">
							<span class="input-group-addon"><span class="required">*</span>账户名称</span>
							
							<c:if test="${operateType == 'C'}">
	                            <input type="text" id="txt_djbh" class="form-control input-radius"  name="zhmc" value="" >
							</c:if>
							<c:if test="${operateType == 'U'}">
	                            <input type="text" id="txt_djbh" class="form-control input-radius"  name="zhmc" value="${pjzhMap.zhmc}" >
							</c:if>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 col-md-offset-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>票据类型</span>
							<c:if test="${operateType == 'C'}">
							<input type="text" id="txt_jkr" readonly="readonly" class="form-control input-radius" name="pjlx" value=""/>
							<input type="hidden" id="txt_pid" readonly="readonly" class="form-control input-radius" name="pid" value=""/>
							</c:if>
							<c:if test="${operateType == 'U'}">
							<input type="text" id="txt_jkr" readonly="readonly" class="form-control input-radius" name="pjlx" value="${pjzhMap.pjlxmc}"/>
							<input type="hidden" id="txt_pid" readonly="readonly" class="form-control input-radius" name="pid" value="${pjzhMap.pid}"/>
							</c:if>
							 <span class="input-group-btn"><button type="button" id="btn_xmlx" class="btn btn-link">选择</button></span>
						</div>
					</div>
				</div>
				<div class="row">	
					<div class="col-md-4 col-md-offset-4">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;">是否启用</span>
							<div class="radiodiv">
                			 <c:if test="${operateType == 'C'}">
								 &nbsp; &nbsp;
									<input type="radio" name="sfqy" class="rdo" value="1" checked />
									<label>是</label>
								 &nbsp; &nbsp; &nbsp;
									<input type="radio" name="sfqy" class="rdo" value="0"   />
									<label>否</label>
							</c:if>
							<c:if test="${operateType == 'U'}">
	                			 &nbsp; &nbsp;
									<input type="radio" name="sfqy" class="rdo" value="${pjzhMap.sfqy}" <c:if test="${pjzhMap.sfqy=='1' }"> checked</c:if>/>
									<label>是</label>
								 &nbsp; &nbsp; &nbsp;
									<input type="radio" name="sfqy" class="rdo" value="${pjzhMap.sfqy}"  <c:if test="${pjzhMap.sfqy=='0' }"> checked</c:if> />
									<label>否</label>
							</c:if>
							</div>
						</div>
					</div>
				</div>
				<%-- <div class="row">	
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
							<c:if test="${operateType == 'C'}">
							<input type="text" id="txt_szbm"  class="form-control input-radius" name="szbm" value=""/>
							</c:if>
							<c:if test="${operateType == 'U'}">
							<input type="text" id="txt_szbm"  class="form-control input-radius" name="szbm" value=""/>
							</c:if>
						</div>
					</div>
				</div> --%>
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

$(function(){
	$("#btn_jkr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_jkr&ryid=txt_jkr1","人员信息","920","630");
    });
   //返回按钮
 	$("#btn_back").click(function(e){
 			location.href="${ctx}/pjgl/pjzh/goPjzhPage";	
 	});
	var validate = $("#myform").bootstrapValidator({fields:{
		zhmc:{validators:{notEmpty: {message: '账户名称不能为空'}}},
		pjlx:{validators:{notEmpty: {message: '票据类型不能为空'}}}
   	}
   });
	
	//删除按钮
		$(document).on("click","[class*=deleteBtn]",function(){
			$(this).parents("tr").remove();
		});
		 
		var cc = $("[name^=ryxz]");
		$.each(cc,function(){
// 			console.log($(this).prop("checked"));
			var val = $(this).prop("checked");
			if(val){
				$(this).parents("tr").find("[name='ryxz']").val($(this).val());
			}
		}); 
		
		//保存按钮
		$("#btn_save").click(function(e){	
	          
				doSave1(validate,"myform","${ctx}/pjgl/pjzh/doSave?operateType=${operateType}",function(val){
					window.location.href = "${ctx}/pjgl/pjzh/goPjzhPage";
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
						window.location.href = "${ctx}/pjgl/pjzh/goPjzhPage";
					},
					error:function(val){
						console.log("___"+val);
					}	
				});
			}
		}
		 
		//项目弹窗
		$(document).on("click","[id^=btn_xmlx]",function(){
			select_commonWin("${ctx}/pjgl/pjzh/getxmlxall?controlId=txt_jkr&id=txt_xmlx&pid=txt_pid","票据类型信息","920","630");	
		});

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


</script>

</script>
</html>