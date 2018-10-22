<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证退回 公务接待明细</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
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
	.text-green{
		color:green!important; 
	}
	.box-bottom{
		text-align: center;
		padding-top: 20px;
		padding-bottom: 20px;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" id="txt_guid" name="guid" value="${guid }"	/>
	<div class="box">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title2 pull-left text-primary"  style="font-size: 14px;"><i class="fa icon-xiangxi"></i>公务接待明细</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
            	   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			  	   <button type="button" class="btn btn-default" id="btn_back">返回列表</button>
       			</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单据编号</span>
							<input type="text" id="txt_djbh" name="djbh" class="form-control input-radius2" value="${gwjdmx.djbh }" readonly/>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>报销人员</span>
							<input type="text" id="txt_bxry" name="bxry" class="form-control input-radius2" value="${gwjdmx.sqr }"  readonly/>
							<input type="hidden" id="txt_bxryid" name="bxryid" class="form-control input-radius2" value="${gwjdmx.bxryid }"  />							
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>所在部门</span>
							<input type="text" id="txt_szbm" name="szbm" class="form-control input-radius2" value="${gwjdmx.szbm}" readonly/>
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>接待日期</span>
							<input type="text" id="txt_jdrq" name="jdrq" class="form-control input-radius2" readonly value="<fmt:formatDate value="${gwjdmx.jdrq}"/>"  />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>接待部门</span>
							<input type="text" id="txt_jdbm" name="jdbm" class="form-control input-radius2" value="${gwjdmx.jdbm}" readonly />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>来宾单位</span>
							<input type="text" id="txt_lbdw" name="lbdw" class="form-control input-radius2" value="${gwjdmx.lbdw}"  readonly/>
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>接待地点</span>
                            <input type="text" id="txt_jdcs" name="jdcs" class="form-control input-radius2" value="${gwjdmx.jdcs}" readonly/>
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>报销金额(元)</span>
                             <input type="text" id="txt_bxje" name="bxje" class="form-control input-radius2 number text-right" value="<fmt:formatNumber value="${gwjdmx.bxje }" pattern=".00"/>" readonly />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>公务活动项目</span>
                            <input type="text" id="txt_gwhdxm" name="gwhdxm" class="form-control input-radius2" value="${gwjdmx.gwhdxm }" readonly />
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>附件张数</span>
                            <input type="text" id="txt_fyfjzs" name="fyfjzs" class="form-control input-radius2  text-right  int" value="${gwjdmx.fyfjzs}" />
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
                    	<div class="input-group">
							<span class="input-group-addon" style="min-width:120px;"><span class="required">*</span>来宾姓名及人数</span>
                            <textarea id="txt_lbry" name="lbry" class="form-control" style="height: 60px;" readonly>${gwjdmx.lbry}</textarea>
                        </div>
					</div>
				</div>
                <div class="row">
					<div class="col-md-12">
                    	<div class="input-group">
							<span class="input-group-addon" style="min-width:120px;"><span class="required">*</span>陪同姓名及人数</span>
                            <textarea id="txt_ptry" name="ptry" class="form-control" style="height: 60px;" readonly>${gwjdmx.ptry}</textarea>
                        </div>
					</div>
				</div>
                <div class="row">
					<div class="col-md-12">
                    	<div class="input-group">
							<span class="input-group-addon" style="min-width:120px;"><span class="required">*</span>接待事由</span>
                            <textarea id="txt_jdsy" name="jdsy" class="form-control" style="height: 60px;">${gwjdmx.jdsy}</textarea>
                        </div>
					</div>
				</div>
			</div>
			<div class="box-panel">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>上传附件
				</div>
			</div>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-8">
						<input id="imageFile" name="path" type="file" multiple class="file-loading"/>
						<div id="errorBlock" class="help-block"></div>
					</div>
				</div>
			</div>
		</div>
         </div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	
	//是编辑还是查看
	if("${operateType}"=="L"){
		$("#btn_save").hide();
		$("input").attr("readonly","readonly");
		$("textarea").attr("readonly","readonly");
	}
	//图片信息开始
	var fjView = [<%=request.getAttribute("fjView")%>];
	var fjConfig = [<%=request.getAttribute("fjConfig")%>];
	//附件信息
     $("#imageFile").fileinput({
       	fileActionSettings:{
    		showUpload:false//隐藏上传按钮
    	},
    	initialPreview:fjView,
    	initialPreviewAsData:true,
    	initialPreviewConfig:fjConfig,
    	uploadUrl: '${pageContext.request.contextPath}/file/fileUpload',//上传请求路径
        maxFilePreviewSize: 10240,//最大上传文件的大小
        showUpload:false,//是否显示上传按钮
        initialPreviewShowDelete:true,
        showBrowse:true,
        showCaption:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"zcxx","id":"${param.guid}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
    });
	//验证表单
	var fields = {fields:{
		fyfjzs:{validators:{notEmpty: {message: '不能为空'}}},
        jdsy:{validators:{notEmpty: {message: '不能为空'},stringLength:{message:'长度不能打于150',max:'150'}}}
        }
	  };
	var validate = $('#myform').bootstrapValidator(fields);
	//保存
   $("#btn_save").click(function(){
	   doSaveGWJD(validate,"myform","${ctx}/bxpzth/doUpdateGWJD",function(){});
	});
 //保存方法	
   function doSaveGWJD(_validate, $form, _url, _success, _fail){
	   	if(_validate){
		   	$.ajax({
		   		type:"post",
		   		url:_url,
		   		data:arrayToJson($('#' + $form).serializeArray()),
		   		success:function(data){
		   			var result = JSON.getJson(data);
		   			if(result.success){
		   			   	$("#imageFile").fileinput("upload");
		   				location.href="${ctx}/bxpzth/gogwjdbx";
				 		alert("保存成功!");
		   			}
		   		},
		   		error:function(val){
		   			alert("抱歉，系统出现问题！");
		   		},
		   	});	
	   }
   }
 //返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/bxpzth/gogwjdbx";
	});
	//刷新按钮
	$(".reload").click(function(){
		 window.location.reload();
	});
	//扫码上传
	SmscLoad("${pageContext.request.contextPath}",{"id":"${param.guid}","djlx":"000000","fold":"zcxx","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
});
</script>
</body>
</html>