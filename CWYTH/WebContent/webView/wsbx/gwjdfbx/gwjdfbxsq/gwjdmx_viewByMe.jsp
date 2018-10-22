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
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
  			border-bottom-width: 1px!important;
   	}
	table,tr{
		border-collapse: collapse;
	}
	.number{
		text-align:right;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" id="txt_guid" name="guid" value="${param.guid }"	/>
	<input type="hidden" id="txt_today" name="today" value="${today }"	/>
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>公务接待费报销查看</span>
		</div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>公务接待明细</div>
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
							<span class="input-group-addon"><span class="required">*</span>单据编号</span>
							<input type="text" id="txt_djbh" name="djbh" class="form-control input-radius2" value="${gwjdmx.djbh }" readonly/>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>报销人员</span>
							<input type="text" id="txt_bxry" name="bxry" class="form-control input-radius2" value="${gwjdmx.bxry }${gwjdmx.sqr }"  readonly/>
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
							<span class="input-group-addon"><span class="required">*</span>报销金额(元)</span>
                             <input type="text" id="txt_bxje" name="bxje" class="form-control input-radius2 number text-right" value="${gwjdmx.bxje }" />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>接待场所</span>
                            <input type="text" id="txt_jdcs" name="jdcs" class="form-control input-radius2" value="${gwjdmx.jdcs}" />
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>接待日期</span>
							<input type="text" id="txt_jdrq" name="jdrq" class="form-control input-radius2 date" value="<fmt:formatDate value="${gwjdmx.jdrq}"/>" />
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>来宾人员</span>
                            <input type="text" id="txt_lbry" name="lbry" class="form-control input-radius2" value="${gwjdmx.lbry }"/>
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>陪同人员</span>
							<input type="text" id="txt_ptry" name="ptry" class="form-control input-radius2" value="${gwjdmx.ptry }"/>
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
							<span class="input-group-addon"><span class="required">*</span>接待事由</span>
                            <textarea id="txt_jdsy" name="jdsy" class="form-control" style="height: 150px;">${gwjdmx.jdsy }</textarea>
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
	<div class="box" id="cxjk">
			<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>冲销借款
	            	</div>
	            	<div class="actions">
	            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	            	</div>
	        </div>
	        <hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">序号</th>
				            <th style="width:300px;">借款单号</th>
				            <th style="width:300px;">借款人</th>
				            <th style="width:300px;">所在部门</th>
				            <th style="width:100px;">借款金额(元)</th>
				            <th style="width:100px;">冲借款金额(元)</th>
				        </tr>
					</thead>
				    <tbody id="tb_cxjk">
				    	<c:forEach items="${cjkList}" var="item" varStatus="status">
				    		<tr class="tr_${status.count}">
							<td class="btn_td">${status.count}</td>
							<td>
							<input type="text" name="djbh" class="form-control input-radius" readonly value="${item.djbh}">
							</td>
							<td>
							<input type="text" id="txt_jkrxm${status.count}" class="form-control input-radius" readonly value="${item.jkrxm}">
							</td>
							<td>
							<input type="text" id="txt_szdw${status.count}" class="form-control input-radius" readonly value="${item.dwmc}">
							</td>
							<td>
							<input type="text" id="txt_jkje${status.count}" name="jkje" class="form-control input-radius number" readonly value="${item.jkje}">
							</td>
							<td>
							<input type="text" id="txt_cjkje${status.count}" name="cjkje" class="form-control input-radius number"  readonly value="${item.cjkje}">
							</td>
						</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
	
	<!-- 对公支付 -->
	<div class="box" id="dgzf">
			<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>对公支付
	            	</div>
	            	<div class="actions">
	            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	            	</div>
	        </div>
	        <hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">序号</th>
				            <th style="width:200px;">对方单位</th>
				            <th style="width:300px;">对方地区</th>
				            <th style="width:300px;">对方银行</th>
				            <th style="width:300px;">对方账号</th>
				            <th style="width:100px;">金额(元)</th>
				        </tr>
					</thead>
				    <tbody id="tb_xnzz">
				    	<c:forEach items="${dgzfList}" var="item" varStatus="status">
				    		<tr class="tr_${status.count}">
							<td class="btn_td">${status.count}</td>
							<td>
								<input type="text" id="txt_dwmc${status.count}" class="form-control input-radius"  value="${item.dwmc}">
							</td>
							<td>
							<input type="text" id="txt_dfdq${status.count}" name="dfdq" class="form-control input-radius" readonly value="${item.dfdq}">
							</td>
							<td>
							<input type="text" class="form-control input-radius" readonly value="${item.yhname}">
							</td>
							<td>
							<input type="text" id="txt_dfzh${status.count}" name="dfzh" readonly class="form-control input-radius"  value="${item.dfzh}">
							</td>
							<td>
							<input type="text" id="txt_je${status.count}" name="je" class="form-control input-radius number"  value="${item.je}">
							</td>
						</tr>
				    	</c:forEach>
						
				    </tbody>
				</table>
			</div>
		</div>
	</div>
	
	<!-- 对私支付 -->
	<div class="box" id="dszf">
			<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>对私支付
	            	</div>
	            	<div class="actions">
	            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	            	</div>
	        </div>
	        <hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">序号</th>
				            <th style="width:300px;">人员选择</th>
				            <th style="width:300px;">人员姓名</th>
				            <th style="width:500px;">卡类型/银行</th>
				            <th style="width:500px;">卡号</th>
				            <th style="width:100px;">金额</th>
				        </tr>
					</thead>
				    <tbody id="tb_sszk">
				    
				    	<c:forEach items="${dszfList}" var="dsList" varStatus="ds">
				    		<tr class="tr_${ds.index+1}">
							<td class="btn_td">${ds.index+1}</td>
							<td>
								<input type="text" class="form-control input-radius" style="width:100%" <c:if test="${dsList.ryxz=='0'}">value="个人"</c:if> 
									<c:if test="${dsList.ryxz=='1'}">value="项目负责人"</c:if>
									<c:if test="${dsList.ryxz=='2'}">value="其他人"</c:if>
								/>
				    		</td>
				    		<td>
				    		<div class="">
				    			<input type="text" readonly style="width:100%" class="form-control input-radius" value="${dsList.ryxm}">
				    		</div>
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius" value="${dsList.klx}">
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius" value="${dsList.dfzh}">
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius number" value="${dsList.je}" >
							</td>
						</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="box" id="gwk">
			<div class="box-panel">		
			<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>公务卡信息
	            	</div>
	            	<div class="actions">
	            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	            	</div>
	        </div>
	        <hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="width:51px;">序号</th>
				            <th style="width:300px;">人员姓名</th>
				            <th style="width:220px;">刷卡日期</th>
				            <th style="width:200px;">刷卡金额(元)</th>
				            <th >卡号</th>
				        </tr>
					</thead>
				    <tbody id="tb_gwk">
				    	<c:forEach items="${gwkList}" var="gwk" varStatus="status">
				    		<tr class="tr_${status.count}">
							<td class="btn_td" style="vertical-align: middle;">${status.count}</td>
							<td>
			    			<input type="text" readonly class="form-control input-radius" value="${gwk.ryxm}">
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius date" readonly value="${gwk.skrq }" />
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius number"  value="${gwk.skje}">
							</td>
							<td>
							<input type="text" readonly class="form-control input-radius"  value="${gwk.kh}">
							</td>
						</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="box" <c:if test="${gwjdmx.shyj == null }">style="display: none;"</c:if>>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>审核意见</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				
				<div class="row">
					<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>审核意见</span>
                       	<textarea id="txt_ccnr"  class="form-control" name="ccnr" style="height: 150px;">${gwjdmx.shyj }</textarea>
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
	window.history.back(-1);
// 	location.href="${ctx}/kylbx/gowdbxListPage";
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