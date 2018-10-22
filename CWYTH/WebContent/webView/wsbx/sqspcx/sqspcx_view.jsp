<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>事前审批查询查看</title>
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
	table,tr{
		border-collapse: collapse;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" id="txt_guid" name="guid" value="${param.guid }"	/>
	<input type="hidden" id="txt_today" name="today" value="${today }"	/>
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>事前审批查询查看</span>
		</div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>事前审批明细</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
					
					<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
       			</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
				<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">审核状态</span>
                            <input type="text" id="txt_shzt" name="shzt" class="form-control input-radius2" value="${sqspcx.shztmc }" readonly/>
                        </div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号</span>
							<input type="text" id="txt_djbh" name="djbh" class="form-control input-radius2" value="${sqspcx.djbh }" readonly/>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申请人</span>
							<input type="text" id="txt_sqr" name="sqr" class="form-control input-radius2" value="${sqspcx.sqrmc }"  readonly/>
													
						</div>
                    </div>
					</div>
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">所在部门</span>
							<input type="text" id="txt_szbm" name="szbm" class="form-control input-radius2" value="${sqspcx.szbm}" readonly/>
						</div>
                    </div>
				
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">事前审批类型</span>
                             <input type="text" id="txt_sqsplx" name="sqsplx" class="form-control input-radius2 " value="${sqspcx.sqsplx }" readonly />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">申请日期</span>
                            <input type="text" id="txt_sqrq" name="sqrq" class="form-control input-radius2" value="${sqspcx.czrq}" readonly/>
                        </div>
					</div>
					
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">接待部门</span>
                            <input type="text" id="txt_jdbm" name="jdbm" class="form-control input-radius2" value="${sqspcx.jdbm }" readonly/>
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">是否预支付</span>
							<input type="text" id="txt_sfyzf" name="sfyzf" class="form-control input-radius2" value="${sqspcx.sfyzf }" readonly/>
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">预支付金额</span>
                            <input type="text" id="txt_yzfje" name="yzfje" class="form-control input-radius2" value="${sqspcx.yzfje }" readonly/>
                        </div>
					</div>
				</div>
				<div class="row">
				<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">接待日期</span>
							<input type="text" id="txt_jdrq" name="jdrq" class="form-control input-radius2 date" value="${sqspcx.jdrq }" readonly />
                        </div>
					</div>
				
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">来宾单位</span>
                            <input type="text" id="txt_lbdw" name="lbdw" class="form-control input-radius2" value="${sqspcx.lbdw }" readonly/>
                        </div>
					</div>
					
				
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">接待地点</span>
							<input type="text" id="txt_jdfd" name="jdfd" class="form-control input-radius2" value="${sqspcx.jdfd }" readonly/>
                        </div>
					</div>
					</div>
					<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">审核意见</span>
                            <input type="text" id="txt_shyj" name="shyj" class="form-control input-radius2" value="${sqspcx.shyj }" readonly/>
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
                    	<div class="input-group">
							<span class="input-group-addon">来宾姓名及人数</span>
                            <textarea id="txt_lbxmjrs" name="lbxmjrs" class="form-control" style="height: 150px;">${sqspcx.lbxmjrs }</textarea>
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
                    	<div class="input-group">
							<span class="input-group-addon">陪同姓名及人数</span>
                            <textarea id="txt_ptxmjrs" name="ptxmjrs" class="form-control" style="height: 150px;">${sqspcx.ptxmjrs }</textarea>
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
                    	<div class="input-group">
							<span class="input-group-addon">接待事由</span>
                            <textarea id="txt_jdsy" name="jdsy" class="form-control" style="height: 150px;">${sqspcx.jdsy }</textarea>
                        </div>
					</div>
				</div>
				</div>
			</div>
			
         </div>
			
	</div>
	
	

	
	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var target = "${ctx}/wsbx/gwjdfbx/gwjdfbxsq/";
var guid = "${param.guid}";
var action = "${param.action}";
var link = "${param.link}";
var pid = "${param.procinstid}";
var data = "guid="+guid+"&action="+action+"&link="+link;
$(function(){
$(document).ready(function(){
	//控制审核按钮显示
	if(action=="sh"){
		$(".plugin").show();
	}
	//控制文本不可编辑
	$("input,select,textarea").attr("disabled","disabled");
	//调整金额为浮点型
	$(".number,.number1").blur();
	//冲销显示
	if("${gwjdmx.SFCJK}"!="1"){
		$("#cxjk").css("display","none");
	}
	//对私显示
	if("${gwjdmx.SFDSZF}"!="1"){
		$("#dszf").css("display","none");
	}
	//公务卡显示
	if("${gwjdmx.SFGWK}"!="1"){
		$("#gwk").css("display","none");
	}
	//对公显示
	if("${gwjdmx.SFDGZF}"!="1"){
		$("#dgzf").css("display","none");
	}
});
//返回
$("#btn_back").click(function(){
	if(link == "sh"){
		pageSkip(target,"gwjdfbxsh_list");
	}else{
		pageSkip(target,"gwjdfbxsq_list");
	}
});
//刷新按钮
$(".reload").click(function(){
	 window.location.reload();
});
//通过，退回
$("#btn_tg").click(function(){
	select_commonWin(target+"/pageSkip?pageName=check1&guid="+guid+"&procinstid="+pid,"通过页面","500","270");
});
$("#btn_th").click(function(){
	select_commonWin(target+"/pageSkip?pageName=check2&guid="+guid+"&procinstid="+pid,"退回页面","500","270");
});
/* 图片信息开始 */
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
	uploadUrl: '${ctx}/file/fileUpload',//上传请求路径
    maxFilePreviewSize: 10240,//最大上传文件的大小
    showUpload:false,//是否显示上传按钮
    initialPreviewShowDelete:false,
    showBrowse: false,
    showCaption: true,
    showClose:false,
    uploadExtraData:function(id,index){
    	return {"fold":"zcxx","id":"${param.guid}","djlx":"000000"};
    },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
    //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
    overwriteInitial: false,  
    deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
});
})
</script>
</body>
</html>