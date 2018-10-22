<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.input-group-addon:first-child {
   		 min-width: 123px!important;
	}
	table{
		border-collapse:collapse!important;
	}
	.sub-title2{
		font-size:14px;
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   	 	border-bottom-width: 0!important;
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
	th{
		text-align:center;
		}
    .number{
       text-align:right;
    }
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>项目申报信息查看</span>
		</div>
		<div class="pull-right">
					<button type="button" class="btn btn-default" id="btn_back">返回</button>
        		</div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
		<div class="box-header clearfix">
            	<div class="sub-title2 pull-left text-primary"><i class="fa icon-xiangxi"></i>项目信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
					<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目排名</span>
							<input type="text" id="txt_xmpm" name="xmpm" class="form-control input-radius" value="${xmcx.XMPM}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初&ensp;审&ensp;人</span>
                            <input type="text" id="txt_csr" class="form-control input-radius" name="csr" value="${xmcx.CSR}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初审日期</span>
							 <input type="text" id="txt_csrq" class="form-control input-radius" name="csrq" value="${xmcx.CSRQ}"/>
						</div>
					</div>
				</div>
				<div class="row">
					
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">复&ensp;审&ensp;人</span>
                            <input type="text" id="txt_fsr" class="form-control input-radius" name="fsr" value="${xmcx.FSR}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">复审日期</span>
							 <input type="text" id="txt_fsrq" class="form-control input-radius" name="fsrq" value="${xmcx.FSRQ}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
						 <span class="input-group-addon">是否上年度重新论证项目</span>
						  <div class="radiodiv">
						   <input type="radio" name="sfsndcxlzxm" value="1" disabled<c:if test="${xmcx.SFSNDCXLZXM=='1' }"> checked</c:if>/>是
						   <input type="radio" name="sfsndcxlzxm" value="0" disabled checked<c:if test="${xmcx.SFSNDCXLZXM=='0' }"> checked</c:if>/>否
						 </div>
						</div>
   					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目类型</span>
							<input type="text" id="txt_xmlx" name="xmlx" class="form-control input-radius" value="${xmcx.XMLXMC}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目编号</span>
                            <input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value="${xmcx.XMBH}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
							 <input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value="${xmcx.XMMC}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">服务专业</span>
	                         <input type="text" id="txt_fwzy" class="form-control input-radius" name="fwzy" value="${xmcx.FWZY}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">服务学科</span>
	                        <input type="text" id="txt_fwxk" class="form-control input-radius" name="fwxk" value="${xmcx.FWXK}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申报日期</span>
							 <input type="text" id="txt_sbrq" class="form-control" name="sbrq" value="${xmcx.SBRQ }"  >
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">计划执行时间</span>
							 <input type="text" id="txt_jhzxsj" class="form-control" name="jhzxsj" value="${xmcx.JHZXSJ }"  >
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
                  </div>
                 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">计划结束时间</span>
							 <input type="text" id="txt_jhjssj" class="form-control" name="jhjssj" value="${xmcx.JHJSSJ }"  >
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算金额(元)</span>
                            <input type="text" id="txt_ysje" class="form-control input-radius number" name="ysje" value="${xmcx.YSJE}"/>
						</div>
					</div>
				</div>
				<div class="row">
					 
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
		                   			 <a type="button" data-value="${wjlx.guid}" date-sfbt="${wjlx.sfbt }" class="btn btn-link btn-mark btn-type">${wjlx.xmmc}<span class="label label-success">&radic;</span></a>
								</c:forEach>
                            </c:if>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="container-fluid">
					<div class="col-md-12">
	                	<input id="imageFile" name="path" type="file" multiple class="file-loading" disabled/>
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
                            <textarea style="width:100%;height:100px;" name="yjmbxy" readonly class="form-control input-radius">${xmcx.YJMBXY }</textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目建设主要内容</span>
                            <textarea style="width:100%;height:100px;" name="xmjszynr" readonly class="form-control input-radius">${xmcx.XMJSZYNR }</textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目主要预算构成</span>
                            <textarea style="width:100%;height:100px;" name="xmzyysgc" readonly class="form-control input-radius">${xmcx.XMZYYSGC }</textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目实施主要措施</span>
                            <textarea style="width:100%;height:100px;" name="xmsszycs" readonly class="form-control input-radius">${xmcx.XMSSZYCS }</textarea>
					</div>
				</div>
			</div>
                 <div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">初审意见</span>
							<textarea style="width:100%;height:100px;" readonly name="csyj" class="form-control input-radius">${xmcx.CSYJ }</textarea>
						</div>
                    </div>
                 </div>
                 <div class="row">
                    <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">复审意见</span>
							<textarea style="width:100%;height:100px;" readonly name="fsyj" class="form-control input-radius">${xmcx.FSYJ }</textarea>
						</div>
                    </div>
                 </div>
			</div>
			</div>
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title2 pull-left text-primary"><i class="fa icon-xiangxi"></i>采购目录明细信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">采购目录</span>
										<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius inputs" value="${mxbxx.CGMLMC}"/>
									</div>
                				</div>
                    <div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">物品名称</span>
                       					<input type="text" id="txt_wpmc" class="form-control input-radius inputs" name="wpmc" value="${mxbxx.WPMC}"/>
									</div>
								</div>
					<div class="input-group">
							<span class="input-group-addon"><span class="required"></span>计量单位</span>
                                <select id="drp_dwbb" class="form-control input-radius " name="jldw" disabled>
                                    <option value="">未选择</option>
	                                <c:forEach var="jldwList" items="${jldwList}"> 
	                                   <option value="${jldwList.DM}" <c:if test="${mxbxx.JLDW == jldwList.DM}">selected</c:if>>${jldwList.MC}</option>
									</c:forEach>
	                            </select>
						</div>
				</div>
				<div class="row">
					<div class="col-md-4">
					  <div class="input-group">
						 <span class="input-group-addon">数&emsp;&emsp;量</span>
							<input type="text" id="sl" name="sl" class="form-control input-radius inputs int" style="text-align:right;" value="${mxbxx.SL }"/>
						 </div>
                   </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单&emsp;价(元)</span>
                       		   <input type="text" id="txt_dj" class="form-control input-radius number inputs" style="text-align:right;" name="dj" value="${mxbxx.dj }"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
						  <span class="input-group-addon">金&emsp;额(元)</span>
							 <input type="text" id="txt_je" class="form-control input-radius number inputs" style="text-align:right;" name="je" value="${mxbxx.je }"/>
						 </div>
					</div>
				  </div>
				  <div class="row">
					<div class="col-md-4">
						<div class="input-group">
						    <span class="input-group-addon">是否进口</span>
								<div class="radiodiv">
								   <input type="radio" name="sfjk" value="1" disabled<c:if test="${mxbxx.SFJK=='1' }"> checked</c:if>/>是
									<input type="radio" name="sfjk" value="0" disabled checked<c:if test="${mxbxx.SFJK=='0' }"> checked</c:if>/>否
							  </div>
					   </div>
   					</div>
                </div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                           	  <textarea style="width:100%;height:100px;" name="bz" class="form-control input-radius" readonly>${mxbxx.BZ }</textarea>
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
        showBrowse:false,
        showCaption:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"LDQZ","id":"${jsxx.GUID}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
    });
   //扫码上传
 	SmscLoad("${ctx}",{"id":"${jsxx.GUID}","djlx":"000000","fold":"LDQZ","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
	/*图片信息结束  */ 
	$("input").attr("readonly","readonly");
	
	//返回按钮
	$("#btn_back").click(function(){
		//parent.location.reload(true);
		window.location.href = "${ctx}/xmcx/goListPage";
	});
	$(document).ready(function(){
		$("#btn_smsc_show").remove();
	});
	
});

</script>
</body>
</html>