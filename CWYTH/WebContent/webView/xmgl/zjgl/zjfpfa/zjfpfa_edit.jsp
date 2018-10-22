<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金分配方案详细信息</title>

<%@include file="/static/include/public-manager-css.inc"%>
<style type='text/css'>
.bttn{
    background-color: #00acec;
    color: white;
}
.error,.null{
	background-color: wheat!important;
}
table{
	border-collapse: collapse!important;
}
.yc{

display:none!important;
}
.text-require{
	color: red;
	padding: 4px;
}
</style>

</head>
<body>
	<input type="hidden" name="czr"  value="${loginID}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑资金分配方案信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default"  id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>分配方案</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>方案编号</span>
							<input type="text" id="txt_fabh" name="fabh" class="form-control input-radius" onkeyup='value=value.replace(/[\W]/g,"")' value="${fabh }" />
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>方案名称</span>
                            <input type="text" id="txt_famc" class="form-control input-radius" name="famc" value="${famc }"/>
						</div>
					</div>	   
				</div>
				
				<div class="row" style="margin-top:20px;">
					<div class="col-md-6">
						<div class="input-group">
							 <span>项目名称&ensp;&ensp;</span>
	                         <button type="button" class="btn btn-primary bttn" id="btn_select">选&ensp;&ensp;择</button>
						</div>
					</div>
					
					<div class="col-md-6">
						<div class="input-group" style="float:right">
							<button type="button" class="btn btn-primary bttn" id="btn_FZ">批量赋值</button>&ensp;&ensp;
							<button type="button" class="btn btn-primary bttn" id="btn_del">删&ensp;&ensp;除</button>
						</div>
					</div>
				</div>
				
					<table id="mytable"
						class="table table-striped table-bordered" style="display: none" data-line="0">
						<tr>
							<td style="width: 6px;" rowspan="4">
								<div class="input-group">
									<input type="checkbox" class="keyId" id="txt_guid"
										style="margin-top: 50px">
									<input type="hidden" name="fabh" value="${fabh }"/>
									<input type="hidden" name="famc" value="${famc }"/>
									<input type="hidden" name="xmbh" value="${item.XMBH }" class="" id="txt_xmbh"/>
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">项目名称</span> <input type="text"
										id="txt_xmmc" name="xmmc" class="form-control input-radius"
										readonly />
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">申报单位</span> <input type="text"
										id="txt_sbdw" name="sbdw" class="form-control input-radius"
										readonly />
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">计划执行时间</span> <input
										type="text" id="txt_jhzxsj" name="jhzxsj"
										class="form-control input-radius" readonly />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="input-group">
									<span class="input-group-addon">预算金额（元）</span> <input
										type="text" id="txt_ysje" name="ysje"
										class="form-control input-radius text-right number"
										onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
										readonly />
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon"><label class="text-require">*</label>资金来源</span> 
									<select
										id="txt_zjly" class="form-control input-radius necessary" name="zjly">
										<c:forEach items="${zjlyList }" var="item">
											<option value="${item.DM }">${item.MC }</option>
										</c:forEach>
									</select>
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon"><label class="text-require">*</label>资金编号</span> 
									<input type="text"
										id="txt_zjbh" name="zjbh" class="form-control input-radius necessary"
										onkeyup="value=value.replace(/(\W)/g,'');" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="input-group">
									<span class="input-group-addon">已分配金额（元）</span> <input
										type="text" id="txt_yfpje" name="yfpje"
										class="form-control input-radius text-right number"
										onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
										readonly /> <input type="hidden" value="" name="yfpje_hidden">
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">分配金额</span> <input type="text"
										id="txt_fpje" name="fpje" placeholder="0.00"
										class="form-control input-radius text-right number fpje"/>
								</div>
							</td>
						</tr>
					</table>
					<form id="myform" class="form-horizontal" action="" method="post">
					<c:forEach items="${info }" var="item">
					<table id="mydatatables"
						class="table table-striped table-bordered">
						<tr>
							<td style="width: 6px;" rowspan="4">
								<div class="input-group">
									<input type="checkbox" class="keyId" id="txt_guid"
										style="margin-top: 50px">
									<input type="hidden" name="fabh" value="${fabh }"/>
									<input type="hidden" name="famc" value="${famc }"/>
									<input type="hidden" name="xmbh"  value="${item.XMBH }"class="" id="txt_xmbh"/>
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">项目名称</span> <input type="text"
										id="txt_xmmc" name="xmmc" class="form-control input-radius" value="${item.XMMC }"
										readonly />
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">申报单位</span> <input type="text"
										id="txt_sbdw" name="sbdw" class="form-control input-radius" value="${item.SBDW }"
										readonly />
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">计划执行时间</span> <input
										type="text" id="txt_jhzxsj" name="jhzxsj" value="${item.JHZXSJ }"
										class="form-control input-radius" readonly />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="input-group">
									<span class="input-group-addon">预算金额（元）</span> <input
										type="text" id="txt_ysje" name="ysje"
										class="form-control input-radius text-right number"
										onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" value="${item.YSJE }"
										readonly />
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon"><label class="text-require">*</label>资金来源</span> 
									<select
										id="txt_zjly" class="form-control input-radius necessary" name="zjly">
										<c:forEach items="${zjlyList }" var="item2">
											<option value="${item2.DM }" <c:if test="${item2.DM == item.ZJLY }">selected</c:if>>${item2.MC }</option>
										</c:forEach>
									</select>
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon"><label class="text-require">*</label>资金编号</span> 
									<input type="text"
										id="txt_zjbh" name="zjbh" class="form-control input-radius necessary" value="${item.ZJBH }"
										onkeyup="value=value.replace(/(\W)/g,'');" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="input-group">
									<span class="input-group-addon">已分配金额（元）</span> <input
										type="text" id="txt_yfpje" name="yfpje"
										class="form-control input-radius text-right number"
										onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
										readonly  value="${item.YFPJE }"/> <input type="hidden" value="${item.YFPJE }" name="yfpje_hidden">
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">分配金额</span> <input type="text"
										id="txt_fpje" name="fpje" placeholder="0.00"
										class="form-control input-radius text-right number fpje" value=""/>
								</div>
							</td>
						</tr>
					</table>
					</c:forEach>
					</form>
		</div>
	</div>
	</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
function addItem(items){
	$.each(items,function(){
		var $table = $("#mytable").clone();
		$table.show();
		$table.find("[name=xmbh]").val($(this).find(".guid").text());
		$table.find("[name=xmmc]").val($(this).find(".xmmc").text());
		$table.find("[name=sbdw]").val($(this).find(".sbdw").text());
		$table.find("[name=jhzxsj]").val($(this).find(".jhzxsj").text());
		$table.find("[name=ysje]").val($(this).find(".ysje").text());
		$table.find("[name=zjly]").val($(this).find(".zjly").text());
		$table.find("[name=zjbh]").val($(this).find(".zjbh").text());
		$table.find("[name=yfpje_hidden]").val($(this).find(".yfpje").text());
		$table.find("[name=yfpje]").val($(this).find(".yfpje").text());
		$table.find("[name=fpje]").val($(this).find(".fpje").text());
		$("#myform").append($table);
	})
}
//批量赋值
function fz(zjbh,zjly,fpje){	
	 $("[name=zjbh]:visible").val(zjbh);
	 $("[name=zjly]:visible").val(zjly);
	 $("[name=fpje]:visible").val(fpje);
	 $.each($(".fpje"),function(){
		var fpje = $(this).val();
		var yfpje_hidden = $(this).parents("table").find("[name='yfpje_hidden']").val();
		var yfpje = parseFloat(fpje) + parseFloat(yfpje_hidden);
		var ysje = $(this).parents("table").find("[name='ysje']").val();
		if(yfpje>ysje){
			$(this).parents("table").find("[name='yfpje']").addClass("error");
			alert("已分配金额不能大于预算金额！");
		}else{
			$(this).parents("table").find("[name='yfpje']").removeClass("error");
		}
		$(this).parents("table").find("[name='yfpje']").val(yfpje.toFixed(2));
	 })
}
function checkMoney(){
	var tag = true;
	$.each($(".yfpje"),function(){
		var yfpje = $(this).val();
		var ysje = $(this).parents("table").find("[name='ysje']").val();
		if(yfpje>ysje){
			$(this).parents("table").find("[name='yfpje']").addClass("error");
			tag = false;
			alert("已分配金额不能大于预算金额！");
		}else{
			$(this).parents("table").find("[name='yfpje']").removeClass("error");
		}
	 })
	return tag;
}
function checkNull(items){
	var tag = false;
	$.each(items,function(){
		var val = $(this).val();
		if(val == ""||val == null){
			tag = true;
			$(this).addClass("null");
		}
	})
	return tag;
}
//
$(function(){
	$(document).on("change","#txt_fabh",function(){
		var fabh = $(this).val();
		$.each($("[name=fabh]"),function(){
			$(this).val(fabh);
		})
	})
	$(document).on("change","#txt_famc",function(){
		var famc = $(this).val();
		$.each($("[name=famc]"),function(){
			$(this).val(famc);
		})
	})
	$(document).on("click",".necessary",function(){
		$(this).removeClass("null");
	})
	//验证方法
	var validate = $('body').bootstrapValidator({fields:{ 
		 fabh:{validators: {notEmpty: {message: '不能为空'}}},
		 famc:{validators: {notEmpty: {message: '不能为空'}}},
          }});
	//计算金额
	$(document).on("change",".fpje",function(){
		var fpje = $(this).val();
		var yfpje_hidden = $(this).parents("table").find("[name='yfpje_hidden']").val();
		var yfpje = parseFloat(fpje) + parseFloat(yfpje_hidden);
		var ysje = $(this).parents("table").find("[name='ysje']").val();
		if(yfpje>ysje){
			$(this).parents("table").find("[name='yfpje']").addClass("error");
			alert("已分配金额不能大于预算金额！");
		}else{
			$(this).parents("table").find("[name='yfpje']").removeClass("error");
		}
		$(this).parents("table").find("[name='yfpje']").val(yfpje.toFixed(2));
	});

	//保存按钮
	$("#btn_save").click(function(){
		doSave1(validate,"#myform",ADDRESS+"/zjfpfa/add",function(val){
				
		});
	});
	function doSave1(_validate, _formId, _url, _success, _fail){
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $("body").data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			if(checkNull($(".necessary:visible"))){
				alert("必填项不能为空！");
				return;
			}
			if(!checkMoney()){
				return;
			}
			var jsonStr = JSON.stringify($(_formId).serializeObject("fabh","yfpje"));
			var fabh = $("#txt_fabh").val();
			var famc = $("#txt_famc").val();
			$.ajax({
				type:"post",
				url:_url,
				data:"json="+jsonStr,
				success:function(val){	
					var result = JSON.getJson(val);
					if(result.success){
						alert("保存成功！");							
					}
				},
				error:function(val){
					alert("保存失败，请稍后重试！");					
				}	
			});
		}
	}
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/xmgl/zjgl/zjfpfa/zjfpfa_list.jsp";
	});
	//删除按钮
	$("#btn_del").click(function(){
		var a=$("[class^=keyId]").filter(":checked");
		if(a.length==0){
			alert("请至少选择一条记录删除");
			return;
		}
		$.each(a,function(){
			$(this).parents("tr").parents("table").remove();
		});
	});
	//选择按钮
	$("#btn_select").click(function(){
		var num = "";
		select_commonWin("${ctx}/webView/xmgl/zjgl/zjfpfa/xm_list.jsp?","项目信息","1120","630");
	});
	//批量赋值按钮
	$("#btn_FZ").click(function(){
		var a=$("[class^=keyId]").filter(":checked");
		if(a.length==0){
			alert("请至少选择一条记录复值");
			return;
		}
		select_commonWin("${ctx}/webView/xmgl/zjgl/zjfpfa/zjfpfa_fz.jsp","项目批量复值","520","630");
	});
	//刷新按钮
	$(".reload").click(function(){
		 window.location.reload();
	});
	
});

</script>
</body>
</html>