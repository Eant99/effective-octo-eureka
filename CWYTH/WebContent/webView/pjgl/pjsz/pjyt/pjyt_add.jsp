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
	                            <!-- <input type="text" id="" class="form-control input-radius"  name="pjytbh" value="" > -->
	                            <input type="text" id="txt_mbbh" name="pjytbh"
									class="form-control input-radius"
									readonly
									style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"
									value="系统自动生成" />
							
						</div>
					</div>
				
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>票据用途名称</span>
							<input type="text" id=""  class="form-control input-radius" name="pjytmc" value=""/>
						</div>
					</div>
				</div>
				<div class="row">	
					<div class="col-md-6">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;">是否启用</span>
							<div class="radiodiv" >
								 &nbsp; &nbsp;
									<input type="radio" name="sfqy" class="rdo" value="1" checked />
									<label>是</label>
								 &nbsp; &nbsp; &nbsp;
									<input type="radio" name="sfqy" class="rdo" value="0"   />
									<label>否</label>
							</div>
						</div>
					</div>
				</div>
				<div class="row">	
					
					 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备 &ensp; &ensp;注</span>
                            <textarea id="txt_zynr" class="form-control" name="bz" ></textarea>
						</div>
					</div>
					
				</div>
		</div>
	</div>	
	</div>	
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
   //返回按钮
 	$("#btn_back").click(function(e){
 			location.href="${ctx}/pjgl/pjyt/goPjytPage";	
 	});
	var validate = $("#myform").bootstrapValidator({fields:{
		pjytbh:{validators:{notEmpty: {message: '票据用途编码不能为空'}}},
		pjytmc:{validators:{notEmpty: {message: '票据用途名称不能为空'}}}
   	}
   });
		//保存按钮
		$("#btn_save").click(function(e){	
			checkAutoInput();
			var mbbh=$("txt_mbbh").val();
				doSave1(validate,"myform","${ctx}/pjgl/pjyt/doSave?operateType=${operateType}&mbbh="+mbbh,function(val){
					window.location.href = "${ctx}/pjgl/pjyt/goPjytPage";
					alert("保存成功");
				});
		});	
		//空验证
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
</script>
</html>