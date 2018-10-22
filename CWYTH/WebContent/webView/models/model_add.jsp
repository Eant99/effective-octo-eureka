<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>添加流程模型定义</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script
	src="${pageContext.request.contextPath}/static/plugins/ckeditor/ckeditor.js"></script>
<script
	src="${pageContext.request.contextPath}/static/plugins/kindeditor-4.1.2/kindeditor.js"></script>
</head>
<body style="background-color: white;">
	<form id="myform" class="form-horizontal" action="" method="post">
		<div class="over-hight">	
		<div class="container box-content">
			<div class="container-fluid dialog-body">
					<div class="row">
					<div class="col-sm-6">
						<div class="input-group">
							<span class="input-group-addon" style="text-align: right;">
							<span class="required">*</span>
								 流程模型名称
							</span> <input type="text" class="form-control input-radius" name="name"
								 />

						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="input-group">
							<span class="input-group-addon" style="text-align: right;">
							<span class="required">*</span>
								 流程模型类型
							</span> 
							<select class="form-control input-radius" name="mxlx">
							 <option value="1">收文流程</option> 
							<option value="2">发文流程</option>
							<option value="3">督察督办</option>
							<!-- <option value="4">公文流转</option> -->
							<option value="5">会议申请</option>
							<option value="9">通知公告</option>
							<option value="10">自定义表单</option>
							
							</select>

						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="input-group">
						
							<span class="input-group-addon" 
								style="text-align: right; height: 100px;">
								&ensp;&emsp;流程模型描述
							</span> 	
								<div id="" style="height:100px">
									<textarea style="width: 99%; height: 100px;" name="desc" id="contentone"></textarea>
								</div>

						</div>
					</div>

				</div>
			</div>
		</div>
		</div>
		<div class='page-bottom clearfix'>
			<!-- 保存和返回按钮 开始 -->
			<div class="pull-right">
				<button type="button" class="btn btn-default" id="btn_save"
					>
					<i class="fa icon-save"></i> 保存
				</button>
				<button type="button" class="btn btn-default btn-without-icon" id="btn_cancelw">
					关闭</button>
			</div>
			<!-- 保存和返回按钮 结束 -->
		</div>
	</form>
	<%@include file="/static/include/public-manager-js.inc"%>
	<script
		src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			setOverHeight();
			//关闭
			$("#btn_cancelw").click(function() {
				getIframWindow("${param.pname}").table.ajax.reload();
				var winId = getTopFrame().layer.getFrameIndex(window.name);
				close(winId);

			});
			//页面验证
			var validate = $('#myform').bootstrapValidator({
				fields : {

					name : {
						validators : {
							notEmpty : {
								message : '流程名称不能为空'
							}
						}
					},
					desc : {
						validators : {
							notEmpty : {
								message : '流程描述不能为空'
							}
						}
					},
				}
			});

			//保存按钮
			$("#btn_save")
					.click(
							function(e) {
								var valid;
								if (validate) {
									validate.bootstrapValidator("validate");
									valid = $('#myform').data(
											'bootstrapValidator').isValid();
								} else {
									valid = true;
								}
								if (valid) {
									var name=$("[name='name']").val();
									var desc=$("[name='desc']").val();
									var mxlx=$("[name='mxlx']").val();
                                   $.post("${pageContext.request.contextPath}/model/saveModel",{'cmd':'add','name':name,'mxlx':mxlx},function(val){
                                	   getIframWindow("${param.pname}").location.href="${pageContext.request.contextPath}/modeler.html?modelId="+val;
                                	   var winId = getTopFrame().layer.getFrameIndex(window.name);
                       				close(winId);
                                   }); 
								}
							});

		});
	</script>
</body>
</html>