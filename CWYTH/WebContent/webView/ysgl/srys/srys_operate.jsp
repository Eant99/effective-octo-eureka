<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入预算申报</title>
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
	
	.number1{
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
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.deleteBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
</style>
</head>
<body>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String date = sdf.format(new Date());
%>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑收入预算申报</span>
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>收入预算申报信息
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
							<span class="input-group-addon"><span class="required">*</span>单据编号</span>
                            <input type="text" id="txt_djbh" class="form-control input-radius" name="djbh" value="20171030">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单位名称</span>
							<input type="text" id="txt_dwmc" class="form-control input-radius window" name="dwmc" value="教务处"/>
							<span class="input-group-btn"><button type="button" id="btn_dwmc" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>编制年度</span>
							<input type="text" id="txt_bznd" class="form-control input-radius year" name="bznd" value="2017" data-format="yyyy"/>
							 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>上年预算总额（万元）</span>
							<input type="text" id="" class="form-control input-radius number1" name="snysze" value=""  "/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>上年收入总额（万元）</span>
							<input type="text" id="" class="form-control input-radius number1" name="snsrze" value=""
							/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>本年预算总额（万元）</span>
							<input type="text" id="" class="form-control input-radius number1" name="bnysze" value="" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>填&ensp;报&ensp;人</span>
							<input type="text" id="txt_tbr" class="form-control input-radius" name="tbr" value="超级管理员" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">填报时间</span>
							<input type="text" id="txt_tbsj" class="form-control input-radius date" name="tbsj" value="<%=date%>" data-format="yyyy-MM-dd"/>
							 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
					</div>
			  </div>
				
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>测算依据</span>
							<textarea style="width:100%;height:100px" name="csyj" class="form-control input-radius" ></textarea>
						</div>
					</div>
				</div>				
			</div>
		</div>
		<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>预算申报明细信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th>操作</th>
				            <th style="width:300px;"><span class="required" style="color:red ;">*</span>预算科目</th>
				            <th style="width:300px;"><span class="required" style="color:red ;">*</span>上年预算（万元）</th>
				            <th style="width:300px;"><span class="required" style="color:red ;">*</span>上年收入（万元）</th>
				            <th style="width:300px;"><span class="required" style="color:red ;">*</span>本年预算（万元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1">
				    		<td class="btn_td">
							<div class="addBtn"></div>
				    		</td>
				    		<td>
				    		<div class="input-group">
				    			<input type="text" id="txt_yskm1" class="form-control input-radius window null" name="yskm1" value="">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_yskm1" sj="txt_yskm1" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_snys1" class="form-control input-radius number1 null" name="snys1" value="" >
				    		</td>
				    		<td>
				    			<input type="text" id="txt_snsr1" class="form-control input-radius number1 null" name="snsr1" value="" >
				    		</td>
				    		<td>
				    			<input type="text" id="txt_bnys1" class="form-control input-radius number1 null" name="bnys1" value="">
				    		</td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var validate = $('#myform').bootstrapValidator({fields:{
			djbh:{validators:{notEmpty: {message: '单据编号不能为空'}}},
       	    dwmc:{validators:{notEmpty: {message: '单位名称不能为空'}}},
       		bznd:{validators:{notEmpty: {message: '编制年度不能为空'}}},
       	 	snysze:{validators:{notEmpty: {message: '上年预算总额不能为空'}}},
       		snsrze:{validators:{notEmpty: {message: '上年收入总额不能为空'}}},
       		bnysze:{validators:{notEmpty: {message: '本年预算总额不能为空'}}},
       		csyj:{validators:{notEmpty: {message: '测算依据不能为空'}}},
       	}
	   });
	//返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/webView/ysgl/srys/srys_list.jsp";
	});
	//保存按钮
	$("#btn_save").click(function(e){
		checkAutoInput();
		doSave(validate,"myform","${ctx}/Jjkm/doSave",function(val){
			//alert("保存成功");
		});
		//alert("保存成功");
		//location.href="${ctx}/wsbx/rcbx/goRcbxListPage";
	});	
	function doSave(_validate, _formId, _url, _success, _fail){
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
				},
				error:function(val){
					alert("保存成功！");					
				}	
			});
		}
	}
	//联想输入
	$("#txt_dwmc").bindChange("${ctx}/suggest/getXx","D");//部门
	$("#btn_dwmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwmc","单位信息","920","630");
    });
});
//明细js操作
$(function(){
	//费用名称
	$(document).on("click","[id^=btn_yskm]",function(){
		var sjid = $(this).attr("sj");
		select_commonWin("${ctx}/srys/srys/kmxx?controlId="+sjid,"科目信息","920","630");
    });
	//新增按钮
	var num = 2;
	$(document).on("click","[class*=addBtn]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		$parentTr.find(":input").removeClass("border");
		$parentTr.attr("id","tr_"+num);
		$parentTr.find("[id^=txt_snys]").attr({"name":"snys"+num,"id":"txt_snys"+num});
		$parentTr.find("[id^=txt_snsr]").attr({"name":"snsr"+num,"id":"txt_snsr"+num});
		$parentTr.find("[id^=txt_bnys]").attr({"name":"bnys"+num,"id":"txt_bnys"+num});
		$parentTr.find("[id^=btn_yskm]").attr({"sj":"txt_yskm"+num,"id":"btn_yskm"+num});
		$parentTr.find("[id^=txt_yskm]").attr({"name":"yskm"+num,"id":"txt_yskm"+num});
		num++;
		$(this).parents("tr").after($parentTr);
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
$(document).on("focus","[class*=border]",function(){
	$(this).removeClass("border");
});
//赋值
function fz(id){
	var num = id.substring(id.indexOf("m")+1);
	$("[id=txt_snys"+num+"]").val("12.2504");
	$("[id=txt_snys"+num+"]").focus();
	$("[id=txt_snsr"+num+"]").val("52.2564");
	$("[id=txt_snsr"+num+"]").focus();
	computer();
}
//上年计算
function computer(){
	var snysze = 0.0000;
	var snsrze = 0.0000;
	//计算上年预算总额
	var $_snys = $("[id^=txt_snys]");
	$.each($_snys,function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		snysze = parseFloat(snysze)+parseFloat(val);
	});
	$("[name='snysze']").val(snysze.toFixed(4));
	//计算上年收入
	var $_snsr = $("[id^=txt_snsr]");
	$.each($_snsr,function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		snsrze = parseFloat(snsrze)+parseFloat(val);
	});
	$("[name='snsrze']").val(snsrze.toFixed(4));
}
//计算本年预算
$(document).on("keyup","[id^=txt_bnys]",function(){
	computerBn();
});
function computerBn(){
	var bnysze = 0.0000;
	$.each($("[id^=txt_bnys]"),function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		console.log(bnysze+"----"+val);
		bnysze = parseFloat(bnysze)+parseFloat(val);
	});
	$("[name='bnysze']").val(bnysze.toFixed(4));
}
//删除
$(document).on("click","[class*=deleteBtn]",function(){
	$(this).parents("tr").remove();
	computerBn();
	computer();
});
$(document).on("blur", ".number1", function(e){
	$(this).Num(4,true,false,e);
	return false;
});

</script>

</html>