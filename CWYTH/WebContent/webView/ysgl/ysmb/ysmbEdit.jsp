<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>预算模板详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
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
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
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
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${zffssz.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑预算模板信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>模板信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>模板编号</span>
							<input type="text" id="txt_mbbh" name="mbbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${ysmb.mbbh}"/>
						</div>
                    </div>
                    <div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>模板名称</span>
							<input type="text" id="txt_mbnr" name="mbnr" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${ysmb.mbmc}"/>
						</div>
                    </div>
                  </div>
                 

					 <div class="row">
					  <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                            <textarea id="txt_bz" class="form-control" name="bz" >${ysmb.bz}</textarea>
						</div>
					</div>
				</div>
				</div>
				</div>
				<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>科目信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse;">
				     <tr>
				        <th style="text-align:center;">操作</th>
				        <th style="text-align:center;"><span class="required" style="color:red ;">*</span>科目编号</th>
				        <th style="text-align:center;"><span class="required" style="color:red ;">*</span>科目名称</th>
				     </tr>
				    	<tr id="tr_1">
				    		<td class="btn_td">
				    			<div class="addBtn"></div>
				    		</td>
				    		<td>
				    		<div class="input-group">
				    			<input type="text" id="txt_kmbh" class="form-control input-radius null window" name="kmbh" value="">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_kmbh" sj="txt_kmbh" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
				    		</td>
				    		<td>
				    			 <input type="text" id="txt_kmmc" class="form-control null" name="kmmc" value="${null}">
				    		</td>
				    		
				    	</tr>
				</table>
			</div>
		</div>
		
			
		
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){

	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{       
          mbbh:{validators: {notEmpty: {message: '模板编号不能为空'}}},
          mbnr:{validators: {notEmpty: {message: '模板内容不能为空'}}}      
          }
	      });
	//保存按钮
	$("#btn_save").click(function(){
		checkAutoInput();
		doSave1(validate,"myform","${ctx}/pzmb/doSave",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
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
	function doSave1(_validate, _formId, _url, _success, _fail){
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
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/ysgl/ysmb/ysmb.jsp";
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}"
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"
	    	}
		 }
	});
 //科目编号弹框
// 	$("#btn_kmbh").click(function(){		
//        select_commonWin("${ctx}/pzmb/window?controlId=txt_kmbh","科目信息","920","630");
//     });
//科目编号弹框
	$(document).on("click","[id^=btn_kmbh]",function(){
		var controlId = $(this).attr("sj");
		select_commonWin("${ctx}/pzmb/window?&controlId="+controlId,"科目信息","920","630");
 });
	//新增按钮
	var num = 2;
	$(document).on("click","[class=addBtn]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		$parentTr.attr("id","tr_"+num);
		$parentTr.find("[id^=txt_kmbh]").attr({"name":"kmbh"+num,"id":"txt_kmbh"+num});
		$parentTr.find("[id^=btn_kmbh]").attr({"id":"btn_kmbh"+num,"sj":"txt_kmbh"+num});
		$parentTr.find("[id^=btn_kmbh]").attr({"id":"btn_kmbh"+num,"sj":"txt_kmbh"+num});
		$parentTr.find("[id^=txt_kmmc]").attr({"name":"kmmc"+num,"id":"txt_kmmc"+num});		
		num++;
		$(this).parents("tr").after($parentTr);
	});
	//删除按钮
	$(document).on("click","[class=deleteBtn]",function(){
		$(this).parents("tr").remove();
	});
});
</script>
</body>
</html>