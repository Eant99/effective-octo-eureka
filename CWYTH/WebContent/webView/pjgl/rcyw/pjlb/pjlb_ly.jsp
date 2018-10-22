<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>数据字典信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<div style="font-size:20px;font-family:cursive;text-align:center;">票据领用</div>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden"  id="guid"  name="guid" value="${guid}"/>
	<div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon">银行科目</span>
                        <input type="text" id="txt_yhkm" class="form-control input-radius window" name="yhkm" value="${pjxxMap.kmmc} " readonly/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="input-group">
					<!-- 带有必填项的label元素 -->
					<span class="input-group-addon">领用日期</span>
					<input type="text" id="txt_lyrq" class="form-control input-radius" name="lyrq"  value="${lyrq}" readonly/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon">领用部门</span>
                        <input type="text" id="txt_lybm" class="form-control input-radius window" name="lybm" value="${pjxxMap.lybm}" readonly>
						<span class="input-group-btn">
							<button type="button" id="btn_lybm" class="btn btn-link btn-custom">选择</button>
						</span>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon">领用人</span>
                        <input type="text" id="txt_lyr" class="form-control input-radius window" name="lyr" value="${pjxxMap.lyr}" readonly>
						<span class="input-group-btn">
							<button type="button" id="btn_lyr" class="btn btn-link btn-custom">选择</button>
						</span>
				</div>
			</div> 
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon">票据账户</span>
                        <input type="text" id="txt_pjzh" class="form-control input-radius window" name="pjzh" value="${pjxxMap.zhmc}" readonly>
				</div>
			</div> 
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon">票据号</span>
                        <input type="text" id="txt_pjh" class="form-control input-radius window" name="pjh" value="${pjxxMap.pjh}" readonly/>
				</div>
			</div>
		</div>
		<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon">票据用途</span>
                       <select name="pjyt" value="" style="" class="form-control input-radius window" >
                        	<option value="">无</option>
                        	<c:forEach items="${pjytList}" var="item" >
	                        	<option value="${item.guid}">${item.PJYTMC}</option>
                        	</c:forEach>
                        </select> 
                       <%--   <input id="txt_yjje" class="form-control" name="pjyt" value="${pjxxMap.pjyt}" /> --%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon">预计金额</span>
                        <input id="txt_yjje" class="form-control" name="yjje" value="${pjxxMap.lyr}" />
					</div>
				</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
        <div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
				<i class="fa icon-save"></i>
				保存
			</button>
			<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">
				取消
			</button>
        </div>
	</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//点击保存
	var tag = true;
	//保存按钮
	$("#btn_save").click(function(e){	
          
			doSave1(validate,"myform","${ctx}/pjgl/rcyw/pjlb/doSavely?guid=${guid}",function(val){
				window.location.href = "${ctx}/pjgl/pjlx/goPjlxPage";
				alert("保存成功");
			});
	});	
	function doSave1(_validate, _formId, _url, _success, _fail){
//		checkAutoInput();
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
// 				dataType:"json",
				data:arrayToJson($("#myform").serializeArray()),
				success:function(val){	
					alert("领用成功！"); 
		//			window.location.href = "${ctx}/pjgl/rcyw/pjlb/goPjListPage";
					
					getIframWindow("${param.pname}").table.ajax.reload();
					var winId = getTopFrame().layer.getFrameIndex(window.name);
			    	close(winId);	
			    	window.location.href="${ctx}/pjgl/rcyw/pjlb/goPjListPage";
					
				},
				error:function(val){
					console.log("___"+val);
				}	
			});
		}
	}
	 
	var validate = $("#myform").bootstrapValidator({fields:{
		yhkm:{validators:{notEmpty:{message:'不能为空'}}},
		lyrq:{validators:{notEmpty:{message:'不能为空'}}},
        lybm:{validators:{notEmpty:{message:'不能为空'}}},
        lyr:{validators:{notEmpty:{message:'不能为空'}}},
        pjzh:{validators:{notEmpty:{message:'不能为空'}}},
        pjh:{validators:{notEmpty:{message:'不能为空'}}},
        pjyt:{validators:{notEmpty:{message:'不能为空'}}},
        yjje:{validators:{notEmpty:{message:'不能为空'}}}
		
   	}
   });
	
	
	$("#btn_save111").click(function(){
		alert("领用成功！");
		
		$.ajax({
   			url:"${ctx}/pjgl/rcyw/pjlb/doSavely",
   			data:"guid=${guid}",
   			type:"post",
   			dataType:"json",
   			async:"false",
   			success: function(val){
   				console.log("val======="+val);
   				if(val){
   					confirm("确定作废？","",function(){
   						$.ajax({
   				   			url:"${ctx}/xmlx/doDelete",
   				   			data:"dwbh="+guid,
   				   			type:"post",
   				   			dataType:"json",
   				   			async:"false",
   				   			success:function(val){
   				   				if(val.success){
   				   					alert("作废成功！");
   				   				}
   				   				
   				   				table.ajax.reload();
   				   			},
   				   			error:function(){
   				   				alert("抱歉，系统出现问题！");
   				   			}
   				   		});
   					});
   					
   				}else{
   					alert("该项目类型正在使用中，不允许作废！")   					
   				}  				
   			},
   			error:function(val){
   			}
   		});
		
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);	
    	window.location.href="${ctx}/pjgl/rcyw/pjlb/goPjListPage";
	});
	$(document).on("click","#btn_lybm",function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_lybm","部门信息","920","630");
	});
	//领用人
	$("#btn_lyr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_lyr","选择领用人","850","630");
    });
	//上级代码联想
	$("#txt_zl").bindChange("${pageContext.request.contextPath}/suggest/getXx","ZD");
	//关闭按钮
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});	
}); 
</script>
</body>
</html>