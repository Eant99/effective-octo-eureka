<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>其他经费报销申请</title>
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
	.add{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.add:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.delete{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.delete:after{
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
	.bm_input{
		width:500px!important;
		border:none;
	}
	#tbody input{
		border:none;
		width:80px;
	}
	tr th{
	text-align: center;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${qtjfbxsq.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑其他经费报销申请</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>报销详细</div>
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
                             <input type="text" id="txt_pjbh" class="form-control input-radius" name="pjbh" value="${qtjfbxsq.pjdh}"/>
                            
						</div>
					</div>
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>经费代码</span>
                             <input type="text" id="txt_jfdm" class="form-control input-radius" name="jfdm" value="${qtjfbxsq.jfdm}"/>
                              <span class="input-group-btn"><button type="button" id="btn_jfdm" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">经费名称</span>
					<input type="text" id="txt_jfmc" class="form-control input-radius" name="jfmc" value="${qtjfbxsq.jfmc}"/>
                           
						</div>
					</div>				
				</div>
			<div class="row">
					  <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>报&ensp;销&ensp;人</span>
                              <input type="text" id="txt_bxr" class="form-control input-radius" name="bxr" value="${qtjfbxsq.bxr}"/>
                             <span class="input-group-btn"><button type="button" id="btn_bxr" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销部门</span>
					  <input type="text" id="txt_bxbm" class="form-control input-radius" name="bxbm" value="${qtjfbxsq.bxbm}"/>
					  <span class="input-group-btn"><button type="button" id="btn_bxbm" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销日期</span>
                           <input type="text" id="txt_bxrq" class="form-control date" name="bxrq" value="<fmt:formatDate value="${qtjfbxsq.bxrq}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="报销日期">
						</div>
					</div>				
				</div>
				<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">附件张数（张）</span>
                          <input type="text" id="txt_fjzs" class="form-control input-radius  text-right int" name="fjzs" value="${qtjfbxsq.fjzs}"/>
					</div>
				</div>
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算年度</span>
                              <input type="text" id="txt_ysnd" class="form-control input-radius text-right number" name="ysnd" value="<fmt:formatNumber value='${qtjfbxsq.ysnd}' pattern='0.00'/>">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申请单据编号</span>
                            <input type="text" id="txt_bxsy" class="form-control input-radius" name="sqdjbh" value="${qtjfbxsq.sqdjbh}"/>
						</div>
					</div>	
				</div>
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销金额（元）</span>
                             <input type="text" id="txt_bxje" class="form-control input-radius text-right number" name="bxje" value="<fmt:formatNumber value='${qtjfbxsq.bxje}' pattern='0.00'/>">
						</div>
					</div>
				
				</div>
                 <div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">报销事由</span>
                               <textarea id="txt_bxsy" class="form-control" name="bxsy" >${qtjfbxsq.bxsy}</textarea>
						</div>
					</div>	
                 </div>
                 <div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                               <textarea id="txt_bz" class="form-control" name="bz" >${qtjfbxsq.bz}</textarea>
						</div>
					</div>	
                 </div>
                 
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>报销信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="width: 60%;border-collapse: collapse;">
		        	<thead id="thead">
				        <tr>
				        	<th style="width: 20px;text-align: center;">操作</th>
				        	<th>报销内容</th>			 
				            <th>金额（元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="trr_1">
				    		<td class="btn_td_">
								<div class="addBtn"></div>
				    		</td>
				        	<td>
				        		<input type="text" name="bxnr_1" style="width:700px;border:none"  value="" />
				        	</td>
				            <td>
				            	<input type="text" name="je_1" class="form-control input-radius text-right number" style="width:300px;border:none" value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>收款信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="width: 100%;border-collapse: collapse;">
		        	<thead id="thead">
				        <tr>
				        	<th style="width: 20px">操作</th>
				        	<th>姓名</th>
				        	<th>卡号</th>		 
				            <th>金额（元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="trr_1">
				    		<td class="btn_add">
				    			<div class="add"></div>
				    		</td>
				        	<td>
				        		<input type="text" name="bxnr_1" class="bm_input" value="" />
				        	</td>
				        	<td>
				        		<input type="text" name="kh_1" class="bm_input" value="" />
				        	</td>
				            <td>
				            	<input type="text" name="je_1" class=" bm_input form-control input-radius text-right number" value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
//获取json数据
$.getJSON("${ctx}/json/wsbx/qtjfbx/qtjfbxsqUpdate.json",function(data){
	$("#txt_djbh").val(data["DJBH"]);
	$("#txt_jfdm").val(data["JFDM"]);
	$("#txt_jfmc").val(data["JFMC"]);
	$("#txt_bxr").val(data["BXR"]);
	$("#txt_bxbm").val(data["BXBM"]);
	$("#txt_bxrq").val(data["BXRQ"]);
	$("#txt_fjzs").val(data["FJZS"]);
	$("#txt_ysnd").val(data["YSND"]);
	$("#txt_sqdjbh").val(data["SQDJBH"]);
	$("#txt_bxje").val(data["BXJE"]);
	$("#txt_bxsy").val(data["BXSY"]);
	$("#txt_bz").val(data["BZ"]);
});
//明细js操作
$(function(){
	
	//新增按钮
	var num = 2;
	$(document).on("click","[class=addBtn]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		$parentTr.attr("id","tr_"+num);
		num++;
		$(this).parents("tr").after($parentTr);
	});
	
	//删除按钮
	$(document).on("click","[class=deleteBtn]",function(){
		$(this).parents("tr").remove();
		//删除后重新计算金额
		computer();
		pjzs();
	});
	//输入还款金额
	$(document).on("keyup","[name=hkje]",function(){
		computer();
	});
});
$(function(){
	//新增按钮
	var num = 2;
	$(document).on("click","[class=add]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("add");
		$(this).addClass("delete");
		$parentTr.find(":input").val("");
		$parentTr.attr("id","tr_"+num);
		num++;
		$(this).parents("tr").after($parentTr);
	});
	//删除按钮
	$(document).on("click","[class=delete]",function(){
		$(this).parents("tr").remove();
		
	});
	//输入还款金额
	$(document).on("keyup","[name=hkje]",function(){
		computer();
	});
});
$(function(){
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
		 djph:{validators:{notEmpty:{message:'不能为空'}}},
 	    jfdm:{validators:{notEmpty: {message: '不能为空'}}},
 	    bxr:{validators:{notEmpty: {message: '不能为空'}}},
 	   pjbh:{validators:{notEmpty: {message: '不能为空'}}}
          }
	      });
	//保存按钮
	$("#btn_save").click(function(){		
		doSave1(validate,"myform","*",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
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
	//联想输入提示
	$("#txt_kmbh").bindChange("#","D");
	
	$("#btn_kmbh").click(function(){
		select_commonWin("${ctx}/window/zdpage?controlId=txt_zl","字典信息","720","630");
    });
	//弹窗
	$("#btn_ysdw").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_ysdw","单位信息","920","630");
    });
	$("#btn_bxbm").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_bxbm","单位信息","920","630");
    });
	$("#btn_bxr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_bxr","人员信息","920","630");
    });
	$("#btn_jfdm").click(function(){
		select_commonWin("${ctx}/webView/wsbx/qtjfbx/jfb_list.jsp?controlId=txt_jfdm","单位信息","920","630");
    });
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/wsbx/qtjfbx/qtjfbxsq_list.jsp";
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

	
});

</script>
</body>
</html>