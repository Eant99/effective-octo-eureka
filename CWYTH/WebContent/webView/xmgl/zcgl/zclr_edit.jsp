<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支出管理信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    line-height: 25px;
    padding-left: 6px;
}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${zcgl.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑支出管理信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_commit">提交</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>支出内容</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>物品名称</span>
							<input type="text" id="txt_wpmc" name="wpmc" class="form-control input-radius"  value="${zclr.wpmc}"/>
							<span class="input-group-btn"><button type="button" id="btn_wpmc" class="btn btn-link btn-custom">选择</button></span>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>采购目录</span>
                            <input type="text" id="txt_cgml" class="form-control input-radius" name="cgml" value="${cgmlsz.mlmc}"/>                         
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">计量单位</span>
                            <select id="drp_jldw" class="form-control input-radius select2" name="jldw">
	                                <c:forEach var="jldwlist" items="${jldwlist}"> 
	                                   <option value="${jldwlist.DM}" <c:if test="${cgmlsz.jldw == jldwlist.DM}">selected</c:if>>${jldwlist.MC}</option>
									</c:forEach>
	                            </select>
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单价(元)</span>
							<input type="text" id="txt_dj" class="form-control input-radius text-right number" name="dj" value="<fmt:formatNumber value='${xtb.GZYQ}' pattern='0.00'/>">
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">计划金额(元)</span>
                           <input type="text" id="txt_jjje" class="form-control input-radius text-right number" name="jjje" value="<fmt:formatNumber value='${xtb.GZYQ}' pattern='0.00'/>">
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>采购金额(元)</span>
                            <input type="text" id="txt_cgje" class="form-control input-radius text-right number" name="cgje" value="<fmt:formatNumber value='${xtb.GZYQ}' pattern='0.00'/>">
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>支出日期</span>
							<input type="text" id="txt_zcrq" class="form-control input-radius date" name="zcrq" value="<fmt:formatDate value="${xtb.BFRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="支出日期">
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>资金编号</span>
                            <input type="text" id="txt_mlmc" class="form-control input-radius" name="zjbh" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>资金来源</span>
                            <select id="drp_zjly" class="form-control input-radius select2" name="zjly">
	                                   <option value="">未选择</option>	
	                                   <option value="1">财政拨款</option>	
	                                   <option value="2">纳入财政管理的资金</option>	
	                                   <option value="3">企业资助资金</option>	
	                                   <option value="4">学校自筹资金</option>			
	                            </select>
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>采购日期</span>
							<input type="text" id="txt_cgrq" class="form-control input-radius date" name="cgrq"  value="<fmt:formatDate value="${xtb.BFRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="采购日期">
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>采购合同编号</span>
                            <input type="text" id="txt_mlmc" class="form-control input-radius" name="cghtbh" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>合同名称</span>
                            <input type="text" id="txt_sjml" class="form-control input-radius" name="htmc" value="${cgmlsz.sjml}" />
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>经手人</span>
							<input type="text" id="txt_jsr" name="jsr" class="form-control input-radius"  value="${cgmlsz.mldm}"/>
							<span class="input-group-btn"><button type="button" id="btn_jsr" class="btn btn-link btn-custom">选择</button></span>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">供应商</span>
                            <input type="text" id="txt_mlmc" class="form-control input-radius" name="gys" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">品牌</span>
                            <input type="text" id="txt_sjml" class="form-control input-radius" name="pp" value="${cgmlsz.sjml}" />
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">产品型号</span>
							<input type="text" id="txt_cpxh" name="cpxh" class="form-control input-radius"  value="${cgmlsz.mldm}"/>
							
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">采购数量</span>
                            <input type="text" id="txt_cgsl" class="form-control input-radius" name="cgsl" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">产地类型</span>
                             <select id="drp_cdlx" class="form-control input-radius select2" name="cdlx">
	                                   <option value="" >未选择</option>	
	                                   <option value="1">中国</option>	
	                                   <option value="0">国外</option>	
	                                  		
	                            </select>
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">存放地点</span>
							<input type="text" id="txt_cfdd" name="cfdd" class="form-control input-radius"  value="${cgmlsz.mldm}"/>							
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">组织形式</span>
                             <select id="drp_zzxs" class="form-control input-radius select2" name="zzxs">
	                                <c:forEach var="zzxslist" items="${zzxslist}"> 
	                                   <option value="${zzxslist.DM}" <c:if test="${cgmlsz.zzxs == zzxslist.DM}">selected</c:if>>${zzxslist.MC}</option>
									</c:forEach>
	                            </select>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">采购机构</span>
                              <select id="drp_cgjg" class="form-control input-radius select2" name="cgjg">
	                                <c:forEach var="cgjglist" items="${cgjglist}"> 
	                                   <option value="${cgjglist.DM}" <c:if test="${cgmlsz.cgjg == cgjglist.DM}">selected</c:if>>${cgjglist.MC}</option>
									</c:forEach>
	                            </select>
						</div>
					</div>		
				</div>		
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>采购方式</span>
							<select id="drp_cgfs" class="form-control input-radius select2" name="cgfs">
	                                <c:forEach var="cgfslist" items="${cgfslist}"> 
	                                   <option value="${cgfslist.DM}" <c:if test="${cgmlsz.cgfs == cgfslist.DM}">selected</c:if>>${cgfslist.MC}</option>
									</c:forEach>
	                            </select>
						</div>
                    </div>					
				</div>		
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                           <textarea id="txt_bz" class="form-control" name="bz" >${cgmlsz.bz}</textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-zichanchuzhi01"></i>
            		项目信息
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
							<span class="input-group-addon">项目编号</span>
							<input type="text" id="txt_mldm" name="xmbh" readonly class="form-control input-radius"  value="001"/>
						
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
                            <input type="text" id="txt_mlmc" readonly class="form-control input-radius" name="xmmc" value="测试项目"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目分类</span>
                            <input type="text" id="txt_sjml" readonly class="form-control input-radius" name="xmfl" value="测试类" />
						</div>
					</div>		
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申报单位</span>
							<input type="text" id="txt_sbdw" name="sbdw" readonly class="form-control input-radius"  value="${cgmlsz.mldm}"/>
							
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">服务专业</span>
                            <input type="text" id="txt_fwzy" readonly class="form-control input-radius" name="fwzy" value="${cgmlsz.mlmc}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">服务学科</span>
                            <input type="text" id="txt_sjml" readonly class="form-control input-radius" name="fwxk" value="${cgmlsz.sjml}" />
						</div>
					</div>		
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算金额(元)</span>
							<input type="text" id="txt_cgje" readonly class="form-control input-radius text-right number" name="ysje" value="<fmt:formatNumber value='${xtb.GZYQ}' pattern='0.00'/>">
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">分配总金额(元)</span>
                           <input type="text" id="txt_cgje" readonly class="form-control input-radius text-right number" name="fpzje" value="<fmt:formatNumber value='${xtb.GZYQ}' pattern='0.00'/>">
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">已支出金额(元)</span>
                           <input type="text" id="txt_cgje" readonly class="form-control input-radius text-right number" name="yzcje" value="<fmt:formatNumber value='${xtb.GZYQ}' pattern='0.00'/>">
						</div>
					</div>		
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否上年度重新论证项目</span>
							<div class="radiodiv">
							<input type="radio" name="cgbh" value="1" checked="checked" <c:if test="${xtb.cgbh == 1 }">checked</c:if>>是&emsp;&emsp;
							<input type="radio" name="cgbh" value="0" <c:if test="${xtb.cgbh == 0 }">checked</c:if>>否&ensp;
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
$(function(){
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{   
          wpmc:{validators: {notEmpty: {message: '不能为空'}}},
          cgml:{validators: {notEmpty: {message: '不能为空'}}},
          cgje:{validators: {notEmpty: {message: '不能为空'}}},
          zcrq:{validators: {notEmpty: {message: '不能为空'}}},
          zjbh:{validators: {notEmpty: {message: '不能为空'}}},
          zjly:{validators: {notEmpty: {message: '不能为空'}}},
          cgrq:{validators: {notEmpty: {message: '不能为空'}}},
          cghtbh:{validators: {notEmpty: {message: '不能为空'}}},
          htmc:{validators: {notEmpty: {message: '不能为空'}}},
          jsr:{validators: {notEmpty: {message: '不能为空'}}},
          xmbh:{validators: {notEmpty: {message: '不能为空'}}},
          xmmc:{validators: {notEmpty: {message: '不能为空'}}},
          xmfl:{validators: {notEmpty: {message: '不能为空'}}}
         
          }
	      });
	//保存按钮
	$("#btn_save").click(function(){
		doSave1(validate,"myform","${ctx}/cgmlsz/doSave",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
	});
	//弹窗
	$("#btn_jsr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_jsr","人员信息","920","630");
    });
	$("#btn_wpmc").click(function(){
		select_commonWin("${ctx}/webView/xmgl/zcgl/cgtree.jsp?controlId=txt_wpmc","物品信息","920","630");
    });
	function doSave1(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){					
				},
				error:function(val){
					alert("保存成功！");					
				}	
			});
		}
	}
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/xmgl/zcgl/zclr_list.jsp";
	});
	//提交按钮
	$("#btn_commit").click(function(){
		doSave1(validate,"myform","${ctx}/cgmlsz/doSave",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
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

})

</script>
</body>
</html>