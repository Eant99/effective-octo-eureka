<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>公务接待明细</title>
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
	<input type="hidden" id="txt_guid" name="guid" value="${param.guid }"	/>
	<input type="hidden" id="txt_today" name="today" value="${today }"	/>
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>公务接待费报销申请</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
        				 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    				</div>
				</div>
				<div class="sub-title pull-left text-green">选择公务接待事前审批&nbsp;></div>
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        				 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    				</div>
				</div>
				<div class="sub-title pull-left text-primary">公务接待明细&nbsp;></div>
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
        				 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
    				</div>
				</div>
				<div class="sub-title pull-left" style="padding-right: 20px;">结算方式</div>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title2 pull-left text-primary"  style="font-size: 14px;"><i class="fa icon-xiangxi"></i>第二步，公务接待明细</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
            		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
					</button>
            		<button type="button" class="btn btn-default" id="btn_save" >保存</button>
					<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
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
							<span class="input-group-btn"><button type="button" id="btn-bxry" class="btn btn-link">选择</button></span>
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
							<input type="text" id="txt_jdrq" name="jdrq" class="form-control input-radius2 date" value="<fmt:formatDate value="${gwjdmx.jdrq}"/>"  />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>接待部门</span>
							<input type="text" id="txt_jdbm" name="jdbm" class="form-control input-radius2" value="${gwjdmx.jdbm}" readonly />
							<span class="input-group-btn"><button type="button" id="btn_jdbm" class="btn btn-link btn-custom">选择</button></span>
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>来宾单位</span>
							<input type="text" id="txt_lbdw" name="lbdw" class="form-control input-radius2" value="${gwjdmx.lbdw}"  />
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>接待地点</span>
                            <input type="text" id="txt_jdcs" name="jdcs" class="form-control input-radius2" value="${gwjdmx.jdcs}" />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>报销金额(元)</span>
                             <input type="text" id="txt_bxje" name="bxje" class="form-control input-radius2 number text-right" value="<fmt:formatNumber value="${gwjdmx.bxje }" pattern=".00"/>" />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>公务活动项目</span>
                            <input type="text" id="txt_gwhdxm" name="gwhdxm" class="form-control input-radius2" value="${gwjdmx.gwhdxm }"/>
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
                            <textarea id="txt_lbry" name="lbry" class="form-control" style="height: 60px;">${gwjdmx.lbry}</textarea>
                        </div>
					</div>
				</div>
                <div class="row">
					<div class="col-md-12">
                    	<div class="input-group">
							<span class="input-group-addon" style="min-width:120px;"><span class="required">*</span>陪同姓名及人数</span>
                            <textarea id="txt_ptry" name="ptry" class="form-control" style="height: 60px;">${gwjdmx.ptry}</textarea>
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
			<div class="box-bottom">
				<button type="button" id="btn_next" class="btn btn-primary" >下一步</button>
			</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	var target = "${ctx}/wsbx/gwjdfbx/gwjdfbxsq";
	var suffix = "&mkbh=${param.mkbh}";
	var gui = "${param.guid}";
	var today = $("#txt_doday").val();
//保存方法	
function doSave1(_validate, $form, _url, _success, _fail){
	
	if(_validate){
		_validate.bootstrapValidator("validate");
		var valid = $form.data('bootstrapValidator').isValid();
		if(!valid){
			return;
		}
	}
	$.ajax({
		type:"post",
		url:_url,
		data:arrayToJson($form.serializeArray()),
		success:function(data){
			var result = JSON.getJson(data);
			if(result.success){
				_success(data);
			}
		},
		error:function(val){
			alert("抱歉，系统出现问题！");
		},
	});	
}
//验证空值
function isNull(a_str){
	if(a_str == "" || a_str == undefined || a_str == null){
		return true;
	}
	return false;
}


/* 功能按钮 */
$(function(){
	$(document).ready(function(){
		$(".number,.number1").blur();		
	})
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
    }).on("filebatchpreupload",function(){
    	load = loading(2);
    }).on("filebatchuploadcomplete",function(){
    	close(load);
    	var djbh  = $("#txt_djbh").val();
		var bxzje = $("#txt_bxje").val();
		var guid = $("#txt_guid").val();
    	if("1"=="${sfbj}"){//编辑
			pageSkip(target,"jsfssz&djbh="+djbh+"&bxzje="+bxzje+"&guid="+guid+"&action=edit&look=${look}&sfbj=1",suffix);
		}else{
			pageSkip(target,"jsfssz&djbh="+djbh+"&bxzje="+bxzje+"&guid="+guid+"&action=edit&look=${look}",suffix);
		}
    });
	//验证表单
	var fields = {fields:{
        djbh:{validators:{notEmpty: {message: '不能为空'},stringLength:{message:'单据编号过长',max:'12'}}},
        bxje:{validators:{notEmpty: {message: '不能为空'}}},
        jdcs:{validators:{notEmpty: {message: '不能为空'}}},
        jdrq:{validators:{notEmpty: {message: '不能为空'}}},
        lbry:{validators:{notEmpty: {message: '不能为空'}}},
        ptry:{validators:{notEmpty: {message: '不能为空'}}},
        gwhdxm:{validators:{notEmpty: {message: '不能为空'}}},
        fyfjzs:{validators:{notEmpty: {message: '不能为空'}}},
        jdsy:{validators:{notEmpty: {message: '不能为空'},stringLength:{message:'长度不能打于150',max:'150'}}}
        }
	  };
	var validate = $('#myform').bootstrapValidator(fields);
	//保存
   $("#btn_save").click(function(){
	   	var url = target+"/gwjdfbxsqEdit";
		doSave1(validate,$("#myform"),url,function(val){
			alert("保存成功！");
		});
	});
	//返回
	$("#btn_back").click(function(){
		pageSkip(target,"gwjdfbxsq_list",suffix);
	});
	//下一步
	$("#btn_next").click(function(){
		
		var url = target+"/gwjdfbxsqEdit";
		doSave1(validate,$("#myform"),url,function(val){
			var djbh  = $("#txt_djbh").val();
			var bxzje = $("#txt_bxje").val();
			var guid = $("#txt_guid").val();
			var frame = $(".file-live-thumbs").find(".file-preview-frame");
			if(frame.length > 0){
				$("#imageFile").fileinput("upload");
			}else{
				if("1"=="${sfbj}"){//编辑
					pageSkip(target,"jsfssz&djbh="+djbh+"&bxzje="+bxzje+"&guid="+guid+"&action=edit&look=${look}&sfbj=1",suffix);
				}else{
					pageSkip(target,"jsfssz&djbh="+djbh+"&bxzje="+bxzje+"&guid="+guid+"&action=edit&look=${look}",suffix);
				}
			}
		});
	});
	//获取可用金额
	var Getkyje = function(){
		var kyje = 0;
		var bxje = "${gwjdmx.bxje}";
		$.ajax({
			type:"post",
			data:"",
			url:target+"/getXmkyje",
			async:false,
			dataType:"json",
			success:function(val){
				if(bxje!=''){
					kyje = parseFloat(val.kyje) + parseFloat(bxje);				
				}else{
					kyje = parseFloat(val.kyje);				
				}
			}
		});
		return kyje;
	}
	var kyje = Getkyje();
	//输入报销金额
	$(document).on("keyup","[id=txt_bxje]",function(){
		var bxje = $(this).val();
		if(parseFloat(bxje) > kyje){
			alert("本次报销金额已超出公务接待费项目的可用金额");
			$(this).val("");
		}
	});
	//人员弹窗
	$("#btn-bxry").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_bxry&ryid=txt_bxryid&szbmm=txt_szbm","人员信息","1000","650");
	})
	$("#btn_ywsm").click(function(){
		select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
	})
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