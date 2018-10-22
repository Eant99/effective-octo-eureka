<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>公务接待费申请</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>

	.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 28px;
    line-height: 28px;
    padding-left: 6px;
}
     .yc{
    display: none;
    }
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid" id="txt_guid"  value="${gwjdfsq.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>查看公务接待审核</span>
		</div>
		<div class="pull-right">
		<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>公务接待详细</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号</span>
                             <input type="text" id="txt_djbh"  readonly="readonly" class="form-control input-radius " name="djbh" value="${gwjdfsq.djbh}"/>                      
						</div>
					</div>
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申请人</span>
                             <input type="text" id="txt_sqr" readonly="readonly" class="form-control input-radius" name="sqr" value="${gwjdfsq.sqr}"/>     
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">所在部门</span>
							 <input type="text" id="txt_szbm" readonly="readonly" class="form-control input-radius" name="szbm" value="${gwjdfsq.szbm}"/>
						</div>
					</div>				
				</div>
			<div class="row">
					  <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">接待日期</span>
                              <input type="text" id="txt_jdrq" readonly="readonly" class="form-control input-radius" name="jdrq" value="${gwjdfsq.jdrq}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">接待部门</span>
					  <input type="text" id="txt_jdbm" readonly="readonly" class="form-control input-radius window" name="jdbm" value="${gwjdfsq.jdbm}"/>
						
						</div>
					</div>
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">来宾单位</span>
                              	<input type="text" id="txt_lbdw" readonly="readonly" class="form-control input-radius" name="lbdw" value="${gwjdfsq.lbdw}"/>
						</div>
					</div>	
								
				</div>
				
				 <div class="row">
                 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">接待地点</span>
                          <input type="text" id="txt_jdfd" readonly="readonly" class="form-control input-radius" name="jdfd" value="${gwjdfsq.jdfd}"/>
						</div>
					</div>	
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">来宾姓名</span>
                             	<input type="text" id="txt_lbxm"  readonly class="form-control input-radius" name="lbxm" value="${gwjdfsq.lbxm}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">来宾职务</span>
                             	<input type="text" id="txt_lbzw"  readonly class="form-control input-radius" name="lbzw" value="${gwjdfsq.lbzw}"/>
						</div>
					</div>
               		<%-- <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否预支付</span>							
                           <c:if test="${gwjdfsq.sfyzf=='01'}"> <input type="text" readonly="readonly" class="form-control input-radius" name="sfyzf"  value="是"></c:if>
			                <c:if test="${gwjdfsq.sfyzf=='02'}"> <input type="text" readonly="readonly" class="form-control input-radius" name="sfyzf" value="否" ></c:if>
						</div>
					</div>
					<div class="col-md-4" id="yzfjeDiv" >
						<div class="input-group">
							<span class="input-group-addon">预支付金额（元）</span>
                           <input type="text" id="txt_yzfje" readonly="readonly" class="form-control  input-radius text-right number" name="yzfje"  value="${gwjdfsq.yzfje}">
						</div>
					</div> --%>
					<%-- <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>是否集中报销</span>
							<div class="radiodiv">&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="sfjzbx" id="sfjzbx_yes"  value="01"> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                  <input type="radio" name="sfjzbx" id="sfjzbx_no" value="02"> 否
							</div>
						</div>
					</div>
                 	 <div id="div_jzbxr" class="col-md-4" style="display:none;">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>集中报销人</span>
                             	<input type="text" id="txt_jzbxr"  class="form-control input-radius" name="jzbxr" 
                             		<c:if test="${gwjdfsq.sfjzbx eq 01 }"> value="(${gwjdfsq.jzbxr })${gwjdfsq.jzbxrxm}" readonly</c:if> />
						</div>
					</div>--%>
                 </div>
				
				
				<div class="row">
                 	<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">联系电话</span>
                             	<input type="text" id="txt_lxdh"  readonly class="form-control input-radius" name="lxdh" value="${gwjdfsq.lxdh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">来函张数</span>
                            <input type="text" id="txt_fjzs"  class="form-control input-radius  text-right int" name="fjzs" value="${gwjdfsq.fjzs}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">联系人</span>
                             	<input type="text" id="txt_lxr"  readonly class="form-control input-radius" name="lxr" value="${gwjdfsq.lxr}"/>
						</div>
					</div>
				</div>
                 <div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">同行人员</span>
                             	<textarea type="text" id="txt_txry" readonly class="form-control input-radius" name="txry">${gwjdfsq.txry}</textarea>
						</div>
					</div>
                 </div>
				 <div class="row">
                 <div class="col-md-12">
						<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>来宾姓名及人数</span>
                               <textarea id="txt_lbxmjrs" readonly="readonly" class="form-control" name="lbxmjrs" >${gwjdfsq.lbxmjrs}</textarea>
						</div>
					</div>	
                 </div>
                  <div class="row">
                 	<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>陪同姓名及人数</span>
                               <textarea id="txt_ptxmjrs" readonly="readonly" class="form-control" name="ptxmjrs" >${gwjdfsq.ptxmjrs}</textarea>
						</div>
					</div>	
                 </div>
				
				
				 <div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>接待事由&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</span>
                               <textarea id="txt_jdsy" readonly="readonly" class="form-control" name="jdsy" >${gwjdfsq.jdsy}</textarea>
						</div>
					</div>	
                 </div>
			</div>
		</div>
		<div class="box-panel" style="margin-left:-12px;">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered xmxx" style="border-collapse: collapse;">
		        	<thead id="thead">
				        <tr>
<!-- 				        <th style="width: 52px; border-bottom-width: 0px; text-align:center;">操作</th> -->
						<th style="  border-bottom-width: 0px; text-align:center;"><span class="required" style="color: red;">*</span>活动项目</th>
						<th style=" border-bottom-width: 0px; text-align:center;"><span class="required" style="color: red;">*</span>活动时间</th>
						<th style=" border-bottom-width: 0px; text-align:center;">活动地点</th>
						<th style=" border-bottom-width: 0px; text-align:center;" >陪同人员</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<c:choose>
                             <c:when test="${not empty mxList}">
  								<c:forEach var="mxList" items="${mxList}"> 
							    	<tr>
<!-- 										<td class="btn_td" style=" vertical-align: middle;"> -->
<!-- 											<div class="deleteBtn" ></div> -->
<!-- 										</td> -->
										<td style="padding: 4px !important">
									    	<input type="hidden" name="start" value=""/>
											<input type="text" name="hdxm" readonly class="form-control input-radius" value="${mxList.hdxm }"/>
										</td>
										<td style="padding: 4px !important" >
											<input type="text" name="hdsj" style="text-align:center;" readonly class="form-control input-radius date" value="${mxList.hdsj}"/>
										</td>
										<td style="padding: 4px !important">
										 	<input type="text" name="hddd" readonly class="form-control input-radius" value="${mxList.hddd}"/>
										</td>
										<td style="padding: 4px !important">
											<input type="text" name="ptry" readonly class="form-control input-radius" value="${mxList.ptry}"/>
											<input type="hidden" name="end" />
										</td>
									</tr>
								</c:forEach>
							</c:when>
						</c:choose>
				    </tbody>
				</table>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>上传来函
				</div>
			</div>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-12">
						<input id="imageFile" name="path" type="file" multiple class="file-loading"/>
						<div id="errorBlock" class="help-block"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
if("${gwjdfsq.sfyzf}"=='01'){
	$("#yzfjeDiv").removeClass("yc");
}
$(function(){
	$(document).ready(function(){
		var sfyzf = "${gwjdfsq.sfyzf}";
		var sfjzbx = "${gwjdfsq.sfjzbx}";
		if(sfyzf=='01'){
			$("#div_yzfje").show();
			$("#sfyzf_yes").attr("checked","checked");
		}else{
			$("#sfyzf_no").attr("checked","checked");
		}
		if(sfjzbx=='01'){
			$("#div_jzbxr").show();
			$("#sfjzbx_yes").attr("checked","checked");
		}else{
			$("#sfjzbx_no").attr("checked","checked");
		}
		$("input,select").attr("disabled","disabled");
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
        initialPreviewShowDelete:false,
        showBrowse:false,
        showCaption:false,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"zcxx","id":"${gwjdfsq.guid}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
    });
var guid =$("#txt_guid").val();
	//弹窗
	$("#btn_pass").click(function(){
		select_commonWin("${ctx}/gwjdfsh/check?guid="+guid+"&type=first","审核意见","920","630");
    });
	$("#btn_refuse").click(function(){
		select_commonWin("${ctx}/gwjdfsh/check?guid="+guid+"&type=two","审核意见","920","630");
    });
	
	//返回按钮
	$("#btn_back").click(function(){
		var before = document.referrer;
		if(before.indexOf("login_toIndex")>0){
			location.href="${ctx}/gwjdfsh/goListPage";
		}else{
			window.history.back(-1);
		}
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