<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金日帐表</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 28px;
    width:119px;
    line-height: 28px;
    padding-left: 6px;
} 
.box{
	margin-bottom:10px !imnportant;
}
.pzpx{
	width:210px;
	float:left;
}
</style>
</head>
<body >
<form id="myform" class="form-horizontal" action="" method="post" >
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>查询条件
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
	<input type="hidden" name="operateType" id="operateType" value="${param.operateType}">
	<input type="hidden"  name="dmxh" value="${param.dmxh}"/>
	<input type="hidden"  id="jb"  name="jb" value="${dmb.jb}"/>
	<hr class="hr-normal" style="position:relative;">
	<div class="container-fluid box-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><b style="padding:3px;color:red;font-size:10px;">*</b>查询日期</span>
					<c:choose>
						<c:when test="${not empty lxsj }">
						 <input type="text" id="txt_lxsj" class="form-control date" name="lxsj" value="${lxsj}"  data-format="yyyy-MM-dd">
						</c:when>
						<c:otherwise>
						 <input type="text" id="txt_lxsj" class="form-control date" name="lxsj" value="${jsxx.LXSJ}"  data-format="yyyy-MM-dd">
						</c:otherwise>
					</c:choose>
                        <span class='input-group-addon'>
                            <i class="glyphicon glyphicon-calendar"></i>
                        </span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<!-- 带有必填项的label元素 -->
					<span class="input-group-addon"><b style="padding:3px;color:red;font-size:10px;">*</b>会计科目</span>
					<c:if test="${kmmc != '()'}">
					<input type="text" id="txt_kjkm" name="zj_kjkm" value="${kmmc }" class="form-control input-radius window" />
					</c:if>
					<c:if test="${kmmc =='()'}">
					<input type="text" id="txt_kjkm" name="zj_kjkm" class="form-control input-radius window" />
					</c:if>
					<span class="input-group-btn"><button type="button" id="btn_kjkm" class="btn btn-link btn-custom">选择</button></span>
				</div>
			</div>
		</div>
<!-- 		<div class="row"> -->
<!-- 			<div class="col-sm-12"> -->
<!-- 						<div class="input-group"> -->
<!-- 						<span class="input-group-addon">出&emsp;纳&emsp;人</span> -->
<!-- 					<input type="text" id="txt_cnr" class="form-control input-radius window" name="zj_cnr" /> -->
<!-- 					<span class="input-group-btn"><button type="button" id="btn_cnr" class="btn btn-link btn-custom">选择</button></span> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 		</div> -->
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon">结算方式</span>
					<select id="drp_jsfs" class="form-control input-radius" name="jsfs">
									<option value="all">未选择</option>
									<c:forEach var="jsfslist" items="${jsfsList}">
									 <option value="${jsfslist.DM}" <c:if test="${dwb.DMSX == jsfslist.DM}">selected</c:if>>${jsfslist.MC}</option>								
									</c:forEach>
								</select>
				</div>
			</div> 
		</div>
     <div class="row" >
			<div class="col-sm-6" style="float:left;">
						<div class="input-group pzpx">
							<span class="input-group-addon">包含全部未记账凭证&ensp;&ensp;&ensp;&ensp;</span>
							<div class="radiodiv">&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="bhqbjzpz"  value="1"  checked> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                <input type="radio" name="bhqbjzpz"  value="0"> 否
							</div>
						</div>
						<div class="input-group pzpx" style="margin-left:20px;">
							<span class="input-group-addon">显示收支明细&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</span>
							<div class="radiodiv">&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="xsszmx" value="1"> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                <input type="radio" name="xsszmx"  value="0" checked> 否
							</div>
						</div>
					</div>	
					
		</div>
<!-- 		 <div class="row"> -->
<!-- 			<div class="col-sm-6" style="float:left;"> -->
<!-- 						<div class="input-group pzpx"> -->
<!-- 							<span class="input-group-addon">包含已复核未记账凭证&ensp;&ensp;</span> -->
<!-- 							<div class="radiodiv">&nbsp;&nbsp;&nbsp; -->
<!--                             <input type="radio" name="bhyfhwjzpz"  value="1" > 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 			                <input type="radio" name="bhyfhwjzpz"  value="0"> 否 -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="input-group pzpx" style="margin-left:20px;"> -->
<!-- 							<span class="input-group-addon">只显示出纳未复核凭证&ensp;&ensp;</span> -->
<!-- 							<div class="radiodiv">&nbsp;&nbsp;&nbsp; -->
<!--                             <input type="radio" name="zxscnwfhpz" value="1"> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 			                <input type="radio" name="zxscnwfhpz"  value="0"> 否 -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div>	 -->
					
<!-- 		</div> -->
		 <div class="row">
			<div class="col-sm-6" >
						<div class="input-group pzpx">
							<span class="input-group-addon">汇总显示时包含所有科目</span>
							<div class="radiodiv">&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="hzxssbhsykm"  value="1" checked> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                <input type="radio" name="hzxssbhsykm"  value="0"> 否
							</div>
						</div>
					</div>
				
					
		</div>

	</div>
	<div class="container-fluid box-content" style="margin-top:10px;">
				<div class="row">
					<div class="pull-center" style="text-align:center;">
	    				<button type="button" class="btn btn-default" id="btn_save" style="background:#00acec;color: white;">确定</button>
	    				<button type="reset" class="btn btn-default" id="btn_cancelw" style="background:#00acec;color: white;">取消</button>
	 				</div>
				</div>
	</div>	
	</div>
	</div>
	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){	
	
	$("#btn_save").click(function(){
		//会计期间的验证
		var syear = $("[name='lxsj']").val();
		if(syear==""){
			alert("日期不能为空!");
			return;
		}
		
		//会计科目的验证
		var startKjkm = $("[name='zj_kjkm']").val();
		if(startKjkm==""){
			alert("会计科目不可为空!");
			return;
		}
				
		
		var json = $("#myform").serializeObject("lxsj","hzxssbhsykm");			
  		var jsonStr = JSON.stringify(json);  //json字符串  
  		var kmbh = $("[name='zj_kjkm']").val();
  		if(kmbh.indexOf('(')>-1)
  		{
  		 kmbh = kmbh.substring(kmbh.indexOf('(')+1,kmbh.indexOf(')'));
  		}
//   		alert(kmbh);return;
  		var lxsj= $("[name='lxsj']").val();
  		$.ajax({
  			url:"${ctx}/zjrbb/paramSession1",
  			data:"json="+jsonStr+"&lxsj="+lxsj,
  			dataType:"json",
  			type:"post",
  			complete:function(){
  				getIframWindow("${param.pname}").toUrl(kmbh,lxsj);
  				var winId = getTopFrame().layer.getFrameIndex(window.name);
  				close(winId);
  			}
  		});
		
	});

//	$("#txt_cnr").bindChange("${ctx}/suggest/getXx","R");
	$("#btn_cnr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_cnr","人员信息","920","630");
    });
	$("#btn_kjkm").click(function(){
		select_commonWin("${ctx}/webView/kjhs/bbzx/rjz/kjkmTree.jsp?controlId=txt_kjkm","科目信息","920","630");
		
    });

	$("#btn_cancelw").click(function(){
		//getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});	
	function loadData(val){
		$('input[name=dmbh]').val(val.DMBH);	
		$('input[name=dmmc]').val(val.DMMC);	
		$('[name=bz]').text(val.BZ);
		$('#txt_zlName').val("("+val.PBH+")"+val.PMC);
		$('#txt_zl').val(val.ZL);	
	}
}); 
</script>
</body>
</html>