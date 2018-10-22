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
		text-align：center;
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
					<button type="button" class="btn btn-default" id="btn_back" >返回</button>
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
							<span class="input-group-addon">初审项目排名</span>
							<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius" value=""/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初&ensp;审&ensp;人</span>
                            <input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初审日期</span>
							 <input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value=""/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">复审项目排名</span>
							<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius" value=""/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申报单位</span>
							<input type="text" id="txt_sbdw" name="sbdw" class="form-control input-radius" value="${map.xmfl}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申报人</span>
							<input type="text" id="txt_sbr" name="sbr" class="form-control input-radius" value="${map.xmfl}"/>
						</div>
                    </div>
					
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目类型</span>
							<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius" value="${map.xmfl}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目编号</span>
                            <input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value="${map.xmbh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
							 <input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value="${map.xmbh}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">服务专业</span>
	                         <input type="text" id="txt_fwzy" class="form-control input-radius" name="fwzy" value="${xsxx.szyx}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">服务学科</span>
	                        <input type="text" id="txt_fwxk" class="form-control input-radius" name="fwxk" value="${xsxx.nj}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申报日期</span>
							 <input type="text" id="txt_csrq" class="form-control" name="csrq" value=""  data-format="yyyy-MM-dd" placeholder="">
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
							 <input type="text" id="txt_csrq" class="form-control" name="csrq" value=""  data-format="yyyy-MM-dd" placeholder="计划执行时间">
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
                  </div>
                  <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">计划结束时间</span>
							 <input type="text" id="txt_jhjssj" class="form-control" name="jhjssj" value=""  data-format="yyyy-MM-dd" placeholder="计划结束时间">
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算金额(元)</span>
                            <input type="text" id="txt_fwxk" class="form-control input-radius number" name="fwxk" value="${xsxx.nj}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否上年度重新论证项目</span>
							<div class="radiodiv">
							<input type="radio" id="txt_xmfl" name="sfsndclzxm" class="" disabled/>是
							<input type="radio" id="txt_xmfl" name="sfsndclzxm" class="" disabled checked/>否
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
							<span class="input-group-addon">预计目标效益</span>
                            <textarea style="width:100%;height:100px;" name="yjmbxy" readonly class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">项目建设主要内容</span>
                            <textarea style="width:100%;height:100px;" name="xmjszynr" readonly class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">项目主要预算构成</span>
                            <textarea style="width:100%;height:100px;" name="xmzyysgc" readonly class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">项目实施主要措施</span>
                            <textarea style="width:100%;height:100px;" name="xmsszycs" readonly class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
                 <div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">初审意见</span>
							<textarea style="width:100%;height:100px;" readonly name="ccsy" class="form-control input-radius">通过</textarea>
						</div>
                    </div>
                 </div>
                 <div class="row">
                    <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">复审意见</span>
							<textarea style="width:100%;height:100px;" readonly name="ccsy" class="form-control input-radius">通过</textarea>
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
										<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius inputs" value="${param.flmc}"/>
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
										<span class="input-group-addon">计量单位</span>
										<select class="form-control input-radius select2" name="jldw" style="width:100%;">
											<option value="">个</option>
											<option value="1">升</option>
											<option value="2">其他</option>
										</select>
									</div>
								</div>
				</div>
				<div class="row">
					<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">数&emsp;&emsp;量</span>
										<input type="text" id="sl" name="sl" class="form-control input-radius inputs int" style="text-align:right;" value=""/>
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
										<input type="radio" id="txt_sfjk1" name="sfjk" value="1" class=""/>是
										<input type="radio" id="txt_sfjkk" name="sfjk" value="0" class="" />否
										</div>
									</div>
   								 </div>
                 
					
				</div>

				<div class="row">
					<div class="col-md-12">
									<div class="input-group">
										<span class="input-group-addon">备注</span>
                           				<textarea style="width:100%;height:100px;" name="bz" class="form-control input-radius" readonly></textarea>
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
        showBrowse:true,
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
	var nextBool = false;
	//保存按钮
	$("#btn_save").click(function(){
		alert("操作成功");
		nextBool = true;
	});
	//返回按钮
	$("#btn_back").click(function(){
		//parent.location.reload(true);
		window.location.href = "${ctx}/xmsb/xmsb/goXmsbPage?type=second";
	});
	//下一步
	$("#btn_next").click(function(){
		if(nextBool){
			parent.window.location.href = "${ctx}/xmsb/xmsb/after";
		}else{
			alert("请先保存该页面数据！");
		}
	});
	//删除
	$("#btn_del").click(function(){
		confirm("确定删除该项目下维护的采购明细？","{title:提示信息}",function(){
			alert("删除成功！");
		});
	});
});
$(function(){
	//项目分类弹窗 
	$("#btn_xmfl").click(function(){
		select_commonWin("${ctx}/xmsb/xmsb/window?controlId=txt_xmfl","项目分类信息","920","630");
    });
	//专业弹窗 
	$("#btn_fwzy").click(function(){
		select_commonWin("${ctx}/xmsb/xmsb/window?controlId=txt_fwzy","专业信息","920","630");
    });
});
</script>
</body>
</html>