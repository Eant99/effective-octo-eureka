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
							<span class="input-group-addon"><span class="required">*</span>项目类型编号</span>
							<input type="text" id="txt_fabh" name="fabh" class="form-control input-radius" onkeyup='value=value.replace(/[\W]/g,"")' />
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目类型名称</span>
                            <input type="text" id="txt_famc" class="form-control input-radius" name="famc" />
						</div>
					</div>	   
				</div>
				
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>预算类型</span>
							
							<div class="input-group">
								<select name="yslx" class="form-control" style="border: 1px solid #ccc;border-radius: 4px;">
									<option value="">请选择</option>
									<c:forEach var ="yslxlist" items="${yslxlist}" >
										<option value="${yslxlist.DM}">${yslxlist.MC}</option>
									</c:forEach>
								</select>
							</div>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>是否财政支出</span>
                            <input type="text" id="txt_famc" class="form-control input-radius" name="famc" />
						</div>
					</div>	   
				</div>
				
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>会计科目控制</span>
							<input type="text" id="txt_fabh" name="fabh" class="form-control input-radius" onkeyup='value=value.replace(/[\W]/g,"")' />
						</div>
                    </div>
                    <!-- <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>会计支出科目</span>
                            <input type="text" id="txt_famc" class="form-control input-radius" name="famc" />
						</div>
					</div> -->	   
				</div>
				
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>经济科目控制</span>
							<input type="text" id="txt_fabh" name="fabh" class="form-control input-radius" onkeyup='value=value.replace(/[\W]/g,"")' />
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目类别</span>
                            <input type="text" id="txt_famc" class="form-control input-radius" name="famc" />
						</div>
					</div>	   
				</div>	
				
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目收支说明</span>
							<input type="text" id="txt_fabh" name="fabh" class="form-control input-radius" onkeyup='value=value.replace(/[\W]/g,"")' />
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>备注</span>
                            <input type="text" id="txt_famc" class="form-control input-radius" name="famc" />
						</div>
					</div>	   
				</div>	
					<form id="myform" class="form-horizontal" action="" method="post">
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
		doSave1(validate,"#myform",ADDRESS+"/xmlx/add",function(val){
				
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