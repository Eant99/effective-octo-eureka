<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>常用分类设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="form" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="gid" value="${tpb.gid}">
	<div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon">名称</span> 
					<input type="text" class="form-control" name="mc" value="${tpb.mc}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
                    <span class="input-group-addon"><span class="required">*</span>分类号</span>
                    	<input type="text" id="txt_flh" readonly="readonly" class="form-control input-radius" name="flh" value="${tpb.flh}" placeholder="请选择分类号">
					<span class="input-group-btn"><button type="button" id="btn_flh" class="btn btn-link ">选择</button></span>
	            </div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>分类名称</span>
		            <input type="text" id="txt_flmc" class="form-control" name="flmc"  value="${tpb.flmc}" placeholder="分类名称自动填入" readonly>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>排序序号</span>
					<input type="number" min="1" class="form-control" name="pxxh" value="${tpb.pxxh}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
			    <div class="input-group">
				    <span class="input-group-addon">类型</span>
				    <input type="hidden" name="lx" value="${lx}"/>
				    <c:if test="${lx == 1}">
				    	<input type="text" class="form-control" readonly value="管理员"/>
				    </c:if>
				    <c:if test="${lx == 0}">
				    	<input type="text" class="form-control" readonly value="领用人"/>
				    </c:if>
			    </div>
		    </div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
	     <div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">取消</button>
	      </div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var mkbh = "${gid}";
	$(document).on("click","#btn_flh",function() {
   		var yqmc = $("#txt_yqmc").val();
		select_commonWin("${pageContext.request.contextPath}/webView/systemset/cssz/cyflFlxx_List.jsp?controlId=txt_flh&yqmc="+yqmc+"&mkbh="+mkbh,"分类信息","800","600");
    });
	//查看页面时处理函数
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	var validate = $('#form').bootstrapValidator({fields: {
         	flh:{validators:{notEmpty:{message:'不能为空'}}},
         	flmc:{validators:{notEmpty:{message:'不能为空'}}},
         	pxxh:{validators:{notEmpty:{message:'不能为空'}}}
		}
    });
	//保存按钮
	$("#btn_save").click(function(e){
		$.ajax({
			type:"post",
			data:"lx=${lx}&operateType=${operateType}",
			url:"${ctx}/cyflsz/doCheck",
			dataType:"json",
			success:function(val){
				if(val.success=="true"){
					doSave(validate,"form","${ctx}/cyflsz/savecyflsz",function(val){//成功
						getIframWindow("${param.pname}").table.ajax.reload();
						var winId = getTopFrame().layer.getFrameIndex(window.name);
				    	close(winId);
						$("#operateType").val("U");
						getIframDoc("${param.pname}").table.ajax.reload();
					},function(val){//失败
					
					});
				}else{
					alert("同类型最多只能添加五条数据！");
				}
			}
		});
	}); 
	//取消
	$("#btn_cancelw").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
</script>
</body>
</html>