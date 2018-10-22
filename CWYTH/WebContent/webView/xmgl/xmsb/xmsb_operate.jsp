<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<%-- <link href="${pageContext.request.contextPath}/static/css/public/theme.css" rel="stylesheet" type="text/css" /> --%>
<style type="text/css">
	.text-green{ 
 		color:green!important; 
 	}         
	.input-group-addon:first-child {
   		 min-width: 123px!important;
	}
	.sub-title2{
		font-size:14px;
	}
	.zt_label {
	padding: 3px 0;
	display: inline-block;
}
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	}
	.kv-fileinput-caption{
		 border-top-left-radius: 0!important;
		border-bottom-left-radius:0!important;
	}
	.file-input-new{
		height:28px!important;
	}
	.input-group-addon:first-child {
   		min-width:10px!important;
	}
</style>
<style type="text/css">
	.btn-mark.active {
	    border-color: #F7B432;
	    background-color: #FFF0CC !important;
	    box-shadow: 0 1px 1px rgba(0,0,0,0.075) inset, 0 0 8px rgba(247,180,50,0.6);
	    color: #C29C08;
	}
	.btn-mark:hover, .btn-mark:active, .btn-mark:focus {
	    text-decoration: none;
	    border-radius: 2px;
	    border: 1px solid #eee;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="txt_operateType" value="${operateType}">
	<input type="hidden" name="guid" id="txt_guid"  value="${guid}">
	<input type="hidden" name="mxbid" id="txt_mxbid"  value="${mxbid}">
	<input type="hidden" name="today" id="txt_today"  value="${today}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑项目申报信息</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-primary">
				<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-primary">项目信息&nbsp;></div>
				<div class="sub-title pull-left text-primary">
				<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    			</div>
				</div>
				<div class="sub-title pull-left">采购明细信息</div>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title2 pull-left text-primary"><i class="fa icon-xiangxi"></i>第一步,项目信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
<!--             		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明"> -->
<!-- 						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i> -->
<!-- 						<font style="font-size:14px;">业务说明</font> --> 
<!-- 					</button> -->
<!--             		<button type="button" class="btn btn-default" id="btn_save" >保存</button> -->
<!--             		<button type="button" class="btn btn-default" id="btn_del" style="background:#00acec;color: white;">删除</button> -->
					<button type="button" class="btn btn-default" id="btn_back">返回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目类型</span>
							<input type="text" id="txt_xmlxmc" name="xmlxmc" class="form-control input-radius window"  value="${xmsb.xmlxmc}"
								style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择项目类型"/>
							<input type="hidden" id="txt_xmlx" name="xmlx" class="form-control input-radius" value="${xmsb.xmlx}"
								style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" />
						    <span class="input-group-btn"><button type="button" id="btn_xmlx" class="btn btn-link">选择</button></span>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目编号</span>
							<c:if test="${operateType=='C' }">
                            <input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value="${xmsb.XMBH}"/>
                            </c:if>
                            <c:if test="${operateType=='update' }">
                            <input type="text" id="txt_xmbh" class="form-control input-radius"  name="xmbh" value="${xmsb.XMBH}"/>
                            </c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目名称</span>
							 <input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value="${xmsb.XMMC}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>服务专业</span>
	                         <input type="text" id="txt_fwzy" class="form-control input-radius window" name="fwzy" value="${xmsb.FWZY}">
	                         <span class="input-group-btn"><button type="button" id="btn_fwzy" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>服务学科</span>
	                        <input type="text" id="txt_fwxk" class="form-control input-radius" name="fwxk" value="${xmsb.FWXK}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申报日期</span>
							 <input type="text" id="txt_sbrq" class="form-control input-radius date" name="sbrq" data-format="yyyy-MM-dd" value="${xmsb.SBRQ}" >
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
                  </div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>计划执行时间</span>
							 <input type="text" id="txt_jhzxsj" class="form-control input-radius date" name="jhzxsj" data-format="yyyy-MM-dd" value="${xmsb.JHZXSJ}"  data-format="yyyy-MM-dd" placeholder="计划执行时间">
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
                  </div>
                 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>计划结束时间</span>
							 <input type="text" id="txt_jhjssj" class="form-control input-radius date" name="jhjssj" data-format="yyyy-MM-dd" value="${xmsb.JHJSSJ}"  data-format="yyyy-MM-dd" placeholder="计划结束时间">
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>预算金额(元)</span>
                            <input type="text" id="txt_ysje" class="form-control input-radius number" style="text-align:right;" name="ysje" value="${xmsb.YSJE}"/>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申报人</span>
							<input type="text" id="txt_sbr" name="sbr" class="form-control input-radius window" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value=${xmsb.SBR } >
							<span class="input-group-btn"><button type="button" id="btn_sbr" class="btn btn-link">选择</button></span>
						</div>
                    </div>
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申报单位</span>
							<input type="text" id="txt_sbdw" name="sbdw" class="form-control input-radius window" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value="${xmsb.SBDW }" >
							<span class="input-group-btn"><button type="button" id="btn_sbdw" class="btn btn-link">选择</button></span>
						</div>
                    </div>
					
				    <div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>是否上年度重新论证项目</span>
										<div class="radiodiv">
										<input type="radio" name="sfsndcxlzxm" value="1" <c:if test="${xmsb.SFSNDCXLZXM=='1' }"> checked</c:if>/>是
										<input type="radio" name="sfsndcxlzxm" value="0" checked<c:if test="${xmsb.SFSNDCXLZXM=='0' }"> checked</c:if>/>否
										</div>
									</div>
   								 </div>
				</div>
			<!-- 附件上传 -->
		<div class="box-panel" >
		<div class="box-header clearfix">
		 <div class="row">
			<div class="col-xs-2">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		申报书信息
            	</div>
            </div>
            <div class="col-xs-8">
            	 <!-- 提示信息 -->
					<div class="alert alert-info" style="hight:8px;align:center">
				        <div align="center"><strong>提示：</strong>请上传申报书   &nbsp&nbsp<strong>格式：</strong>doc,docx&nbsp<strong>大小：</strong>200M</div> 
				     </div>
		    </div>
		    <div class="col-xs-2">
		    
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
			</div>            	
          </div>
       	</div>
 			<hr class="hr-normal"/> 
			<div class="container-fluid box-content">
			    <div class="box-header clearfix">
					<div class="row">
						<div class="col-md-12 checkbox">
							<span class="zt_label">选择分类：</span>
							<a type="button" data-value="" class="btn btn-link btn-mark btn-mark-all active">全部<span class="label label-success">&radic;</span></a>
                            <c:if test="${wjlxlist.size()!=0}">
                            	<c:forEach var="wjlx" items="${wjlxlist}" >
		                   			 <a type="button" data-value="${wjlx.guid}" data-sfbt="${wjlx.sfbt }" class="btn btn-link btn-mark btn-type fj">${wjlx.xmmc}<span class="label label-success">&radic;</span></a>
								</c:forEach>
                            </c:if>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="container-fluid">
					<div class="col-md-12">    
	                	<input id="imageFile" name="path" type="file" multiple class="file-loading"/>
						<div id="errorBlock" class="help-block"></div>
						<input type="hidden" id="initialPreview" value="">
	                </div>
	            	</div>
				</div>
			</div>	
		</div>
			<!-- 附件上传结束 -->
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">		
							<span class="input-group-addon"><span class="required">*</span>预计目标效益</span>
                            <textarea style="width:100%;height:100px;" name="yjmbxy" class="form-control input-radius">${xmsb.YJMBXY}</textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目建设主要内容</span>
                            <textarea style="width:100%;height:100px;" name="xmjszynr" class="form-control input-radius">${xmsb.XMJSZYNR}</textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目主要预算构成</span>
                            <textarea style="width:100%;height:100px;" name="xmzyysgc" class="form-control input-radius">${xmsb.XMZYYSGC}</textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目实施主要措施</span>
                            <textarea style="width:100%;height:100px;" name="xmsszycs" class="form-control input-radius">${xmsb.XMSSZYCS}</textarea>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="pull-center" style="text-align:center;">
            		<button type="button" class="btn btn-default" id="btn_next" style="background:#00acec;color: white;">下一步</button>
        		</div>
			</div>
			</div>
		</div>
	</div>
	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var tag = false;
var size = "${wjlxlist.size()}";
if(size==0){
	$(".btn-mark-all").css("display","none");
}
$(function(){
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
        initialPreviewShowDelete:true,
        showBrowse:true,
        showCaption:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	var val = $("#"+id).find(".fileType").val();
        	return {"fold":"FJLX","id":"${guid}","djlx":val};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        allowedFileExtensions : ['pdf', 'rar','doc','xls','xlsx','docx'],//允许的文件类型  
        overwriteInitial: false,  
        deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
    }).on("fileuploaded",function(event,data){
    	//每个文件上传成功后
   	 $(".btn-mark").each(function(){
   		$(this).removeClass("active");
   		$(".btn-mark-all").addClass("active");
   	});
 });
   //扫码上传
//  	SmscLoad("${ctx}",{"id":"${guid}","djlx":"000000","fold":"LDQZ","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
	/*图片信息结束  */ 
	
	var operate = "${operateType}";
	
	//业务说明
	operateYwsm("${ctx}");
	var validate = $('#myform').bootstrapValidator({fields:{
		xmlx:{validators:{notEmpty: {message: '项目类型不能为空'}}},
		xmbh:{validators:{notEmpty: {message: '项目编号不能为空'}}},
   		xmmc:{validators:{notEmpty: {message: '项目名称不能为空'}}},
   		fwzy:{validators:{notEmpty: {message: '服务专业不能为空'}}},
   		fwxk:{validators:{notEmpty: {message: '服务学科不能为空'}}},
   		sbrq:{validators:{notEmpty: {message: '申报时间不能为空'}}},
   		sbdw:{validators:{notEmpty: {message: '申报单位不能为空'}}},
   		sbr:{validators:{notEmpty: {message: '申报人不能为空'}}},
   		jhzxsj:{validators:{notEmpty: {message: '计划执行时间不能为空'}}},
   		jhjssj:{validators:{notEmpty: {message: '计划结束时间不能为空'}}},
   	 	ysje:{validators:{notEmpty: {message: '预算金额不能为空'}}},
   		yjmbxy:{validators:{notEmpty: {message: '预计目标效益不能为空'}}},
   		xmjszynr:{validators:{notEmpty: {message: '项目建设主要内容不能为空'}}},
   		xmzyysgc:{validators:{notEmpty: {message: '项目主要预算构成不能为空'}}},
   		xmsszycs:{validators:{notEmpty: {message: '项目实施主要措施不能为空'}}}
    	}
   });
	//申报单位弹窗
	$(document).on("click","#btn_sbdw",function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_sbdw","申报单位","920","630");
	});
	//申报人弹窗
	$(document).on("click","#btn_sbr",function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_sbr","申报人","920","630");
	});
	//项目类型弹窗
	$(document).on("click","[id^=btn_xmlx]",function(){
		select_commonWin("${ctx}/xmgl/xmsb/getxmlxall?controlId=txt_xmlxmc&id=txt_xmlx","项目信息","920","630");	
		
	});
	
	//保存按钮
	$("#btn_save").click(function(){
		$("#imageFile").fileinput("upload");
		 var sbrq=$("#txt_sbrq").val();
		 var today=$("#txt_today").val();
		 if(!isNull(sbrq)&&sbrq<today){
			alert("申报日期不能小于当前时间！");
			$("#txt_sbrq").val("");
			return;
			}
		doSave(validate,"myform","${ctx}/xmgl/xmsb/doSaveZb",function(val){
			if(val.success) {
				tag = true;
				alert(val.msg);
			}
		});
	});

	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/xmgl/xmsb/goXmsbPage";
	});
	//下一步
 	$(document).on("click","#btn_next",function(){
        var zbid = $("#txt_guid").val();
        var mxbid = $("#txt_mxbid").val();
        var operateType = $("#txt_operateType").val();
        var sbrq=$("#txt_sbrq").val();
		var today=$("#txt_today").val();
		if(!isNull(sbrq)&&sbrq<today){
			alert("申报日期不能小于当前时间！");
			$("#txt_sbrq").val("");
			return;
		}
        	var fj = $(".fj");
        	var n=0;
        	$.each(fj,function(){
        		var sfbt = $(this).data("sfbt");
        		var lxbh = $(this).data("value");        		
        		if(sfbt==1){
        			var m=0;
        			var sclxbhs = $(".fileType");
        			if(sclxbhs.length==0){
        				alert("必填附件不能为空！");
        			}else{
            			$.each(sclxbhs,function(){
                    		var sclxbh = $(this).val();                   
                    		if(sclxbh==lxbh){
                    			m++;                   			
                    		}                 		
                    	})             
        			}
        			if(m==0){
            			alert("必填附件不能为空");
            			n++;
        		     }
        		}
        	})
        	if(n==0){
        	var frame = $(".file-live-thumbs").find(".file-preview-frame");        	
        	if(frame.length>0){
        		$("#imageFile").fileinput("upload");	
        	}
        	doSave(validate,"myform","${ctx}/xmgl/xmsb/doSaveZb",function(val){
        		
    			if(val.success) {
    				window.location.href = "${ctx}/xmgl/xmsb/goNextPage?zbid="+zbid+"&operateType="+operateType;
    			}
    		});	
        	}	
 	});
	//验证开始时间和结束时间
	$(document).on("blur","#txt_jhzxsj,#txt_jhjssj",function(){
		var zxsj=$("#txt_jhzxsj").val();
		var jssj=$("#txt_jhjssj").val();
		var today=$("#txt_today").val();
		if(!isNull(zxsj)&&!isNull(jssj)){
			if(zxsj<today){
				alert("计划执行时间不能小于当前时间！");
				$("#txt_jhzxsj").val("");
			}else if(jssj<today){
				alert("结束时间不能小于当前时间！");
				$("#txt_jhjssj").val("");
			}else if(zxsj>jssj){
				alert("计划执行时间不能小于计划结束时间！");
				$("#txt_jhjssj").val("");
			}
		}
	});
});
function addFj(xmlxbh){
	if(xmlxbh!=""){
		$.ajax({
			url:"${ctx}/xmgl/xmsb/xmlx",
			data:"xmlxbh="+xmlxbh,
			type:"post",
			dataType:"json",
			success:function(data){
				$(".btn-type").remove();
				$(".btn-mark-all").css("display","");
				if(data){
					$.each(data,function(i,v){
						var dm = v.GUID;
						var mc = "("+v.FJBH+")"+v.XMMC;
						var sfbt = v.SFBT;
						if(dm!="undefined"&&mc!="undefined"){
							$(".checkbox").append("<a type='button'"
									+" data-value="+dm+""
									+" data-sfbt='"+sfbt+"'"
									+" class='btn btn-link btn-mark btn-type fj'>"+mc+""
									+" <span class='label label-success'>&radic;</span>"
									+"</a>");
						}
					});
				}
			}
		});
	}
}
$(function(){
	//项目分类弹窗 
	$("#btn_xmfl").click(function(){
		select_commonWin("${ctx}/xmgl/xmsb/window?controlId=txt_xmfl","项目分类信息","920","630");
    });
	//专业弹窗 
	$("#btn_fwzy").click(function(){
		select_commonWin("${ctx}/xmgl/xmsb/window?controlId=txt_fwzy","专业信息","920","630");
    });
});
</script>
<script type="text/javascript">
$(document).on("click",".btn-mark",function(){
	//切换类型时，隐藏进度条
	$(".kv-upload-progress").hide();
	$(".btn-mark").removeClass("active");
	var val = $(this).attr("data-value");
	if(val != ''){
		$(this).addClass("active");
		var i = 0;
		$(".fileType").each(function(i,v){
			if($(this).val()==val){
				$(this).parents(".file-preview-frame").show();
				i++;
			}else{
				$(this).parents(".file-preview-frame").hide();
			}
		});
		if($(".file-preview-frame:visible").length==0&&$(".file-drop-zone-title").length==0){
			$(".file-drop-zone").append("<div class=\"file-drop-zone-title\">拖拽文件到这里 …<br>支持多文件同时上传</div>");
		}else if($(".file-preview-frame:visible").length!=0){
			$(".file-drop-zone-title").remove();
		}
	
	}else{
		$(this).addClass("active");
		$(".fileType").each(function(){
			$(this).parents(".file-preview-frame").show();
		});
	}
});
</script>
</body>
</html>