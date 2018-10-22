\<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.text-green{
		color:green!important;
	}
/* 	select{ */
/* 		width:220px!important; */
/* 	} */
	
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	}
	
	.select2{
	    border-radius: 4px;
	}
	/* .yc{
		display:none;
	} */
	.table-bordered {
    	border: none;
	}
	table{
		 border-collapse: collapse;
		 width:100%;
	}
	/* [class^=col-md-]{
		width:100%!important;
	} */
</style>
</head>
<body>
<div  class="form-horizontal" >
<form action="" id="myform">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="zbid" id="txt_zbid" value="${zbid}">
	<input type="hidden" name="guid" id="txt_guid" value="${guid}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑采购明细信息</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-primary">
				<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-green">项目信息&nbsp;></div>
				
				<div class="sub-title pull-left ">
				<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-primary">采购明细信息</div>
				
				
        </div>
	</div>
	<div class="box" style="top:36px" >
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>第二步,采购明细信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
<!--             	<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明"> -->
<!-- 						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i> -->
<!-- 						<font style="font-size:14px;">业务说明</font> -->
<!-- 					</button> -->
            		<button type="button" class="btn btn-default" id="btn_save" >保存</button>
            		<!-- <button type="button" class="btn btn-default" id="btn_del" style="background:#00acec;color: white;">删除</button> -->
					<button type="button" class="btn btn-default" id="btn_back" >返回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class=required>*</span>采购目录</span>
								<input type="text" id="txt_cgmlmc" class="form-control input-radius window" name="cgmlmc" value="${mxbxx.cgmlmc}">
								<input type="hidden" name='cgml' id="txt_cgml" readonly value="${mxbxx.cgml}">
							<span class="input-group-btn"><button type="button" id="btn_cgml" class="btn btn-link btn-custom">选择</button></span>
						</div>
						</div>
                    <div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>物品名称</span>
                       					<input type="text" id="txt_wpmc" class="form-control input-radius inputs" name="wpmc" value="${mxbxx.wpmc }"/>
									</div>
								</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required"></span>计量单位</span>
                                <select id="drp_dwbb" class="form-control input-radius " name="jldw">
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
										<span class="input-group-addon"><span class="required">*</span>数&emsp;&emsp;量</span>
										<input type="text" id="txt_sl" name="sl" class="form-control input-radius inputs int" style="text-align:right;" value="${mxbxx.sl }">
									</div>
                				</div>
					 <div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>单&emsp;价(元)</span>
                       					<input type="text" id="txt_dj" class="form-control input-radius number inputs" style="text-align:right;" name="dj" value="${mxbxx.dj }">
									</div>
								</div>
					<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>金&emsp;额(元)</span>
										<input type="text" id="txt_je" class="form-control input-radius number inputs" readonly style="text-align:right;" name="je" value="${mxbxx.je }">
									</div>
								</div>
				</div>
				<div class="row">
					<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>是否进口</span>
										<div class="radiodiv">
										<input type="radio" name="sfjk" value="1" <c:if test="${mxbxx.SFJK=='1' }"> checked</c:if>/>是
										<input type="radio" name="sfjk" value="0" checked<c:if test="${mxbxx.SFJK=='0' }"> checked</c:if>/>否
										</div>
									</div>
   								 </div>
                 
					
				</div>

				<div class="row">
					<div class="col-md-12">
									<div class="input-group">
										<span class="input-group-addon">备注</span>
<!-- 										<textarea style="width:100%;height:100px;" class="form-control input-radius"></textarea> -->
                           				<textarea style="width:100%;height:100px;" name="bz" class="form-control input-radius">${mxbxx.bz }</textarea>
									</div>
   								 </div>
				</div>
			
			
			</div>
			
			</div>
			<div class="container-fluid box-content">
				<div class="row">
				<div class="pull-center" style="text-align:center;">
            		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
            		<button type="button" class="btn btn-default" id="btn_finish" style="background:#00acec;color: white;">完成</button>
        		</div>
			</div>
			</div>
	</div>
</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var tage=false;
$(function(){
	var validate = $('#myform').bootstrapValidator({fields:{
		cgml:{validators:{notEmpty: {message: '采购目录不能为空'}}},
		wpmc:{validators:{notEmpty: {message: '物品名称不能为空'}}},
   		jldw:{validators:{notEmpty: {message: '计量单位不能为空'}}},
   		sl:{validators:{notEmpty: {message: '数量不能为空'}}},
   		dj:{validators:{notEmpty: {message: '单价不能为空'}}},
   		je:{validators:{notEmpty: {message: '金额不能为空'}}}
   		//sfjk:{validators:{notEmpty: {message: '是否进口不能为空'}}} 		
   	}
   });
	
  var operate="${param.operate}";
	//保存按钮
	$("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/xmsb/xmsb/doSaveMxb",function(val){
			if(val.success){
				tage = true;
				alert(val.msg);
			}
		});
   	});
	
	//完成
	$("#btn_finish").click(function(){
		if(tage){
		window.location.href = "${ctx}/xmgl/xmsb/goXmsbPage";
		}else{
			doSave(validate,"myform","${ctx}/xmgl/xmsb/doSaveMxb",function(val){
				if(val.success){
					alert(val.msg);
					window.location.href = "${ctx}/xmgl/xmsb/goXmsbPage";
				}
			});
		}
	});
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/xmgl/xmsb/goXmsbPage";
	});
	//上一步
	$("#btn_after").click(function(){
		var guid = $("[name='zbid']").val();
		doOperate("${ctx}/xmgl/xmsb/goEditPage?guid="+guid,"update");	
	});
	//删除
	$("#btn_del").click(function(){
		var guid=$("#guid").val();
		confirm("确定删除该项目下维护的采购明细？","{title:提示信息}",function(){
			$.ajax({
   	   			url:ADDRESS+"/xmgl/xmsb/cgmxDeleteFlow",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert(val);
   	   			window.location.href = "${ctx}/webView/xmgl/xmsb/afterList.jsp";
   	   			}
   	   		});
		});
	});
});
  //计算金额
  $(document).on("keyup","#txt_sl",function(){
	  var sl=$("#txt_sl").val();
	  var dj=$("#txt_dj").val();
	  if(isNull(sl)||isNull(dj)||isNaN(sl)||isNaN(dj)){
		  return;
	  }else if(sl==0){
		  alert("数量不能为0");
		  $("#txt_sl").val("");
		  return;
	  }
	  money();
  });
  $(document).on("keyup","#txt_dj",function(){
	  var sl=$("#txt_sl").val();
	  var dj=$("#txt_dj").val();
	  if(isNull(sl)||isNull(dj)||isNaN(sl)||isNaN(dj)){
		  return;
	  }else if(dj==0){
		  alert("单价不能为0");
		  $("#txt_dj").val("");
	  }
	  money();
  });
  function money(){
	  var sl=$("#txt_sl").val();
	  var dj=$("#txt_dj").val();
	  var je=dj*sl;
	  $("#txt_je").val(je.toFixed(2));
  }
//采购目录弹窗
$("#btn_cgml").click(function(){
	select_commonWin("${ctx}/cgmlsz/window?controlId=txt_cgmlmc&text_id=txt_cgml","采购目录","920","630");
});
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
</body>
</html>