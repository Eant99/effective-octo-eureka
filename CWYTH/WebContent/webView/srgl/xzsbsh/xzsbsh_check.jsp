<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>薪资申报审核</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
		.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   		border-bottom-width: 1px!important;
    }
     table{
		border-collapse:collapse!important;
	}
	.btn_td{
		text-align:center;
	}
/* 	.addBtn{ */
/* 		width:14mm; */
/* 		height:6mm; */
/* 		background-image: url("${ctx}/webView/wsbx/rcbx/add.gif"); */
/* 	} */
/* 	.deleteBtn{ */
/* 		width:14mm; */
/* 		height:6mm; */
/* 		background-image: url("${ctx}/webView/wsbx/rcbx/del.gif"); */
/* 	} */
	.btn_td{
		width:14mm!important;
		height:6mm!important;
	}
	.selWindow{
		width:280px!important;
	}
	
	.number,.sz{
		text-align:right;
	}
	.yc{
		display:none;
	}
	tbody input{
		border:none;
		
	}
	thead th{
		text-align:center!important;
	}
	.noBlock{
		margin-bottom: 3px;
    	position: absolute;
    	z-index: 999;
   		display: block;
   		padding-left: 17%;
   		color: red;
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
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
	.addBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.deleteBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.text-green{
		color:green!important;
	}
	tr{
	background-color: white !important;
	}
	.bk{
		border:none;
		width:100%;
		padding:4px !important;
	}
</style>
</head>
<body>
	<div class="box">
		<div class="box-panel1">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>薪资申报信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
            		<button type="button" class="btn btn-default" id="btn_tg">通过</button>
            		<button type="button" class="btn btn-default" id="btn_th">退回</button>
			  		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">流水号</span>
							<input type="text" id="txt_djbh" readonly class="form-control input-radius" readonly name="djbh" value="${zbMap.djbh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">录入日期</span>
                            <input type="text" id="txt_bxr" readonly class="form-control input-radius window" name="" value="${zbMap.bxrname}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">发放方式</span>
							<input type="text" id="txt_bmmc" readonly class="form-control input-radius window" name="" value="${zbMap.dwname}"/>
							<input type="hidden" id="txt_shzt" value="${zbMap.shzt}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">发放年月</span>
							<input type="text" readonly id="txt_fjzzs" class="form-control input-radius sz" name="fjzzs" value="${zbMap.fjzzs}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">发放人员</span>
							<input type="text" id="txt_bxzje" class="form-control input-radius number sz" name="bxzje" value="${zbMap.bxzje}" 
							  readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">人员类型</span>
							<input type="text" id="txt_bxzje" class="form-control input-radius number sz" name="bxzje" value="${zbMap.bxzje}" 
							readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">发放方案</span>
							<input type="text" id="txt_bxzje" class="form-control input-radius number sz" name="bxzje" value="${zbMap.bxzje}" 
							 readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">财务项目</span>
							<input type="text" id="txt_bxzje" class="form-control input-radius number sz" name="bxzje" value="${zbMap.bxzje}" 
							 readonly/>
						</div>
					</div>
					</div>
					<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">摘要</span>
							<textarea style="width:100%;height:60px;" readonly class="form-control input-radius">${zbMap.shyj}</textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
							<textarea style="width:100%;height:60px;" readonly class="form-control input-radius">${zbMap.shyj}</textarea>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
	
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<script type="text/javascript">
$(function(){
	//验证是否是查看，查看的话隐藏按钮
	if("${type}"=="look"){
		$("#btn_tg").hide();
		$("#btn_th").hide();
	}
	//返回按钮
	$("#btn_back").click(function(){
		location.href="${ctx}/srgl/xzsbsh/getPageList";
	});
});
//通过按钮
$("#btn_tg").click(function(){
	/* var guid = "${guid}";
	var procinstid = "${procinstid}";
	var shzt = $("[id='txt_shzt']").val();
	var money = $("[name='bxzje']").val();
	var flag = false;
	if((shzt==="01"||shzt==="04")&&money<=5000){
		$.ajax({
			url:"${ctx}/wsbx/process/selectWho",
			data:"guid="+guid+"&type=rcbx",
			type:"post",
			async:false,
			dataType:"json",
			success:function(data){
				if(data){
					flag = true;
				}
			}
		});
	} */
	select_commonWin("${ctx}/srgl/xzsbsh/check?type=first","通过页面","500","300");
	//location.href = "${ctx}/wsbx/rcbx/check?type=first&guid="+guid+"&procinstid="+procinstid;
});
$("#btn_th").click(function(){
	/* var guid = "${guid}";
	var procinstid = "${procinstid}"; */
	select_commonWin("${ctx}/srgl/xzsbsh/check?type=second","退回页面","500","200");
	//location.href = "${ctx}/wsbx/rcbx/check?type=second&guid="+guid+"&procinstid="+procinstid;
});
</script>

</html>