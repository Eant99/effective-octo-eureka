<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入预算修改</title>
<%@include file="/static/include/public-manager-css.inc"%>

</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${srysxg.guid}">
	<input type="hidden" name="czr"  value="${srysxg.czr}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>收入预算修改审核</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_TG">通过</button>
			<button type="button" class="btn btn-default" id="btn_TH">退回</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>收入预算详细</div>
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
                             <input type="text" id="txt_djbh"  readonly class="form-control input-radius " name="djbh" value="${srysxg.djbh}"/>                      
						</div>
					</div>
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位名称</span>
                             <input type="text" id="txt_dwmc" readonly class="form-control input-radius" name="dwmc" value="${srysxg.ysdw}"/>
                             
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">编制年度</span>
							 <input type="text" id="txt_bxbm" readonly class="form-control input-radius int" name="bznd" value="${gwjdfsq.bxbm}"/>
						</div>
					</div>				
				</div>
			<div class="row">
					  <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">上年预算总额（万元）</span>
                              <input type="text" id="txt_snysze" readonly class="form-control input-radius text-right number" name="snysze" value="${gwjdfsq.bxr}"/>

						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">上年收入总额（万元）</span>
					  <input type="text" id="txt_snsrze" readonly class="form-control input-radius  text-right number" name="snsrze" value="${gwjdfsq.bxrs}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">本年预算总额（万元）</span>
                            <input type="text" id="txt_bnysze" readonly class="form-control input-radius text-right number" name="bnysze" value="${gwjdfsq.jdcs}"/>
						</div>
					</div>				
				</div>
				<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">修改人</span>
                          <input type="text" id="txt_xgr" readonly class="form-control input-radius" name="xgr" value="${gwjdfsq.ptrs}"/>
					</div>
				</div>
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">修改时间</span>
                              <input type="text" id="txt_xgsj" readonly class="form-control input-radius " name="xgsj" >
						</div>
					</div>	
					
				</div>
				<div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>审核意见</span>
                               <textarea id="txt_csyj" class="form-control" name="csyj" >${gwjdfsq.bz}</textarea>
						</div>
					</div>	
                 </div>
			</div>
		</div>
<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>明细信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse;">
		        	<thead id="thead">
				        <tr>
				            <th style="width:300px;text-align: center;">预算科目</th>
				            <th style="width:300px;text-align: center;">上年预算（万元）</th>
				            <th style="width:300px;text-align: center;">上年收入（万元）</th>
				            <th style="width:300px;text-align: center;">本年预算（万元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1">   
				    		<td>				  
				    			<input type="text" id="txt_yskm1" readonly class="form-control input-radius window null" name="yskm1" value="">				    			
				    		</td>
				    		<td>
				    			<input type="text" id="txt_snys1" readonly class="form-control input-radius null text-right number1" name="snys1" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_snsr1" readonly class="form-control input-radius null text-right number1" name="snsr1" value="" >
				    		</td>
				    		<td>
				    			<input type="text" id="txt_bnys1" readonly class="form-control input-radius null text-right number1" name="bnys1" value="" >
				    		</td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
		
	
	
	//保存按钮
	$("#btn_TG").click(function(){		
		alert("已通过");
	});
	$("#btn_TH").click(function(){		
		alert("已退回");
	});
	
	//联想输入提示
	$("#txt_kmbh").bindChange("#","D");
	
	$("#btn_kmbh").click(function(){
		select_commonWin("${ctx}/window/zdpage?controlId=txt_zl","字典信息","720","630");
    });
	//弹窗
	$("#btn_dwmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwmc","单位信息","920","630");
    });
	//科目编号弹框
	$(document).on("click","[id^=btn_yskm]",function(){
		var controlId = $(this).attr("sj");
		select_commonWin("${ctx}/kmsz/window?controlId="+controlId,"科目信息","920","630");
    });

	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/ysgl/twosjd/srysxgsh_list.jsp";
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