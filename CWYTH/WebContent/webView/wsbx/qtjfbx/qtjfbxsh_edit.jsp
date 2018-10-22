<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>其他经费报销审核</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${qtjfbxsq.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑其他经费报销审核</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_refuse">退回</button>
			<button type="button" class="btn btn-default" id="btn_pass">通过</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>报销详细</div>
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
                             <input type="text" id="txt_ysdw" readonly class="form-control input-radius" name="pjbh" value="001"/>
                            
						</div>
					</div>
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">经费代码</span>
                             <input type="text" id="txt_jfdm" readonly class="form-control input-radius" name="jfdm" value="001"/>
                              
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">经费名称</span>
							 <input type="text" id="txt_jfmc" readonly class="form-control input-radius" name="jfmc" value="测试"/>
                           
						</div>
					</div>				
				</div>
			<div class="row">
					  <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报&ensp;销&ensp;人</span>
                              <input type="text" id="txt_bxr" readonly class="form-control input-radius" name="bxr" value="张三"/>
                            
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销部门</span>
					  <input type="text" id="txt_bxbm"  readonly class="form-control input-radius" name="bxbm" value="刑部"/>
					  
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销日期</span>
                           <input type="text" id="txt_bxrq" readonly class="form-control" name="bxrq" value="2017-10-19" >
						</div>
					</div>			
				</div>
				<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">附件张数（张）</span>
                          <input type="text" id="txt_fjzs" readonly class="form-control input-radius  text-right int" name="fjzs" value="6"/>
					</div>
				</div>
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算年度</span>
                              <input type="text" id="txt_ysnd"  readonly class="form-control input-radius text-right number" name="ysnd" value="200.00">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申请单据编号</span>
                            <input type="text" id="txt_sqdjbh"  readonly class="form-control input-radius" name="sqdjbh" value="001"/>
						</div>
					</div>	
				</div>
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">报销金额（元）</span>
                             <input type="text" id="txt_bxje" readonly class="form-control input-radius text-right number" name="bxje" value="200.00">
						</div>
					</div>
				
				</div>
				<div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">报销事由</span>
                               <textarea id="txt_bxsy"  readonly class="form-control" name="bxsy" >${qtjfbxsq.bxsy}</textarea>
						</div>
					</div>	
                 </div>
                 <div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                               <textarea id="txt_bz" readonly class="form-control" name="bz" >测试ing</textarea>
						</div>
					</div>	
                 </div>
                 <div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
                               <textarea id="txt_shyj"  class="form-control" name="shyj" >${qtjfbxsq.shyj}</textarea>
						</div>
					</div>	
                 </div>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
//获取json数据
$.getJSON("${ctx}/json/wsbx/qtjfbx/qtjfbxshUpdate.json",function(data){
	$("#txt_djbh").val(data["DJBH"]);
	$("#txt_jfdm").val(data["JFDM"]);
	$("#txt_jfmc").val(data["JFMC"]);
	$("#txt_bxr").val(data["BXR"]);
	$("#txt_bxbm").val(data["BXBM"]);
	$("#txt_bxrq").val(data["BXRQ"]);
	$("#txt_fjzs").val(data["FJZS"]);
	$("#txt_ysnd").val(data["YSND"]);
	$("#txt_sqdjbh").val(data["SQDJBH"]);
	$("#txt_bxje").val(data["BXJE"]);
	$("#txt_bxsy").val(data["BXSY"]);
	$("#txt_bz").val(data["BZ"]);
});
//
$(function(){
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
		 djph:{validators:{notEmpty:{message:'不能为空'}}},
 	    jfdm:{validators:{notEmpty: {message: '不能为空'}}},
 	    bxr:{validators:{notEmpty: {message: '不能为空'}}}
 	   
          }
	      });
	//退回按钮
	$("#btn_refuse").click(function(){		
		select_commonWin("${ctx}/qtjfbxsq/check?type=second","退回页面","500","300");
	});
	//通过按钮
	$("#btn_pass").click(function(){		
		select_commonWin("${ctx}/qtjfbxsq/check?type=first","通过页面","500","300");
	});
	//联想输入提示
	$("#txt_kmbh").bindChange("#","D");
	
	$("#btn_kmbh").click(function(){
		select_commonWin("${ctx}/window/zdpage?controlId=txt_zl","字典信息","720","630");
    });
	//弹窗
	$("#btn_ysdw").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_ysdw","单位信息","920","630");
    });
	$("#btn_bxbm").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_bxbm","单位信息","920","630");
    });
	$("#btn_bxr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_bxr","人员信息","920","630");
    });
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/wsbx/qtjfbx/qtjfbxsh_list.jsp";
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