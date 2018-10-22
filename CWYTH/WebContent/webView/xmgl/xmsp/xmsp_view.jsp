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
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   	 	border-bottom-width: 0!important;
	}
	th{
		text-align:center;
	}
	tr th:first-child,td:first-child{
		text-align:center;
	}
	.sub-title2{
		font-size:14px;
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
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" id="txt_xmpm" name="xmpm"/>
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>项目申报信息</span>
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
            	<div class="pull-right">
					<button type="button" class="btn btn-default" id="btn_back" >返回</button>
					<button type="button" class="btn btn-primary" id="btn_pass" style="display:none;">通过</button>
					<button type="button" class="btn btn-primary" id="btn_reject" style="display:none;">退回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
                     <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目编号</span>
                            <input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
							 <input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目类型</span>
							<input type="text" id="txt_xmlx" name="xmlx" class="form-control input-radius window" value=""/>
 						</div>
                    </div>
				</div>
				<div class="row">
                    <div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">服务专业</span>
	                         <input type="text" id="txt_fwzy" class="form-control input-radius window" name="fwzy" value="">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">服务学科</span>
	                        <input type="text" id="txt_fwxk" class="form-control input-radius" name="fwxk" value=""/>
						</div>
					</div>
					<div class="col-md-4">                 	                 	
	                  	<div class="input-group">
							<span class="input-group-addon">申报日期</span>
                            <input type="text" id="txt_sbrq" class="form-control date" name="sbrq" value="" >
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
							 <input type="text" id="txt_jhzxsj" class="form-control " name="jhzxsj" value=""  >
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
                  </div>
                  <div class="col-md-4">                 	                 	
                  	<div class="input-group">
							<span class="input-group-addon">计划结束时间</span>
                            <input type="text" id="txt_jhjssj" class="form-control" name="jhjssj" value=""  >
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
					</div>
                   </div>
                   <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算金额(元)</span>
                            <input type="text" id="txt_ysje" class="form-control input-radius number text-right" name="ysje" value=""/>
						</div>
					</div>
				</div>
				<div class="row">
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申&ensp;报&ensp;人</span>
                            <input type="text" id="txt_sbr" class="form-control input-radius" name="sbr" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申报单位</span>
							 <input type="text" id="txt_sbdw" class="form-control input-radius" name="sbdw" value=""/>
						</div>
                 	 </div>
	                 <div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">是否上年度重新论证项目</span>
								<div class="radiodiv">
								<input type="radio" name="sfsndcxlzxm" value="1"/>是
								<input type="radio" name="sfsndcxlzxm" value="0"/>否
								</div>
							</div>
					 </div>
                </div>
				<div class="row" id="row-csr" style="display:none;">
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初&ensp;审&ensp;人</span>
                            <input type="text" class="form-control input-radius" name="csr" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初审日期</span>
							 <input type="text" class="form-control" name="csrq" value=""/>
						</div>
                 	 </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初审项目排名</span>
							 <input type="text" class="form-control" name="xmpm" value=""/>
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
				        <div align="center"><strong>提示：</strong>请上传申报书   &nbsp;&nbsp;<strong>格式：</strong>doc,docx&nbsp;<strong>大小：</strong>200M</div> 
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
            <div class="row" id="row-csyj" style="display:none;">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">初审意见</span>
                            <textarea style="width:100%;height:100px;" name="csyj"  class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
            <div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">预计目标效益</span>
                            <textarea style="width:100%;height:100px;" name="yjmbxy"  class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">项目建设主要内容</span>
                            <textarea style="width:100%;height:100px;" name="xmjszynr"  class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">项目主要预算构成</span>
                            <textarea style="width:100%;height:100px;" name="xmzyysgc"  class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">项目实施主要措施</span>
                            <textarea style="width:100%;height:100px;" name="xmsszycs"  class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
			</div>
			</div>
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title2 pull-left text-primary"><i class="fa icon-xiangxi"></i>项目入库明细信息
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
								<input type="text" id="txt_cgml" class="form-control input-radius window" name="cgml" value="">
								<input type="hidden" name='cgml' id="txt_cgml"  value="">
							<!-- <span class="input-group-btn"><button type="button" id="btn_cgml" class="btn btn-link btn-custom">选择</button></span> -->
						</div>
                	</div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">物品名称</span>
                       		  <input type="text" id="txt_wpmc" class="form-control input-radius inputs" name="wpmc" value=""/>
						</div>
					</div>
					
						<div class="col-md-4">
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
					
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">数&emsp;&emsp;量</span>
								<input type="text" id="txt_sl" name="sl" class="form-control input-radius inputs int" style="text-align:right;" value=""/>
							</div>
                		</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单&emsp;价(元)</span>
                       			<input type="text" id="txt_dj" class="form-control input-radius number inputs" style="text-align:right;" name="dj" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">金&emsp;额(元)</span>
								<input type="text" id="txt_je" class="form-control input-radius number inputs" style="text-align:right;" name="je" value=""/>
						</div>
				   </div>
				 </div>
				 <div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否进口</span>
							  <div class="radiodiv">
								<input type="radio" name="sfjk" value="1"/>是
								<input type="radio" name="sfjk" value="0"/>否
							  </div>
						</div>
   				  </div>	
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
                           		<textarea style="width:100%;height:100px;" name="bz" class="form-control input-radius"></textarea>
						</div>
   				  </div>
			   </div>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var basePath = "${ctx}/xmgl/xmsp";
var pagePath = "${ctx}/webView/xmgl/xmsp";
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
        showUpload:true,
        initialPreviewShowDelete:true,
        showBrowse:false,//是否显示上传按钮
        showCaption:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"LDQZ","id":"${jsxx.GUID}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
    });
	/* 图片信息结束 */
   //加载页面数据
 	function loadPageData(){
 		var url = basePath + "/getDetailPageData";
 		doGetData(url,"guid=${param.guid}",function(data){
 			for(var name in data){
 				var value = data[name];
 				name = name.toLowerCase();
 				//如果是单选按钮
 				if(name == "sfjk" || name == "sfsndcxlzxm"){
 					$("[name='"+name+"'][value='"+value+"']").prop("checked",true);
 					continue;
 				}
 				$("[name='"+name+"']").val(value);
 			}
 		});
 	}
	$(document).ready(function(){
		//控件失效
		$("input,textArea").attr("disabled","disabled");
		//隐藏扫描上传按钮
		$("btn_smsc_show").hide();
		//如果审批类型是复审，则让初审人，初审意见显示
		if("${param.splx}" == "fs"){
			$("#row-csr,#row-csyj").show();
		}
		//如果是审核，则让通过，退回按妞显示，否则让审核意见显示
		if("${param.operateType}" == "check"){
			$("#btn_pass,#btn_reject").show();
		};
		//加载页面数据
		loadPageData();
	});
	$("#btn_pass").click(function(){
		var csxmpm = $("#txt_xmpm").val();
		select_commonWin(pagePath+"/check_pass.jsp?guid=${param.guid}&splx=${param.splx}&csxmpm="+csxmpm,"初审通过","500","300");
	});
	$("#btn_reject").click(function(){
		var csxmpm = $("#txt_xmpm").val();
		select_commonWin(pagePath+"/check_reject.jsp?guid=${param.guid}&splx=${param.splx}&csxmpm="+csxmpm,"初审退回","500","300");
	});
	//返回按钮
	$("#btn_back").click(function(){
		history.back(true);
	});
});
</script>
</body>
</html>