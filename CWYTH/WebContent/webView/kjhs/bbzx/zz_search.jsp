<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>总账</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
.box{
	margin-bottom:10px !imnportant;
}
.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 28px;
    line-height: 28px;
    padding-left: 6px;
} 
</style>
</head>
<body >
<form id="myform" class="form-horizontal" action="" method="post" >
<div class="box">
	<div class="box-panel">	
	<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>查询条件${param.ff }
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
    </div>
	<hr class="hr-normal">
	<div class="container-fluid box-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>会计期间</span>
					<input type="hidden" name="start"  value="0">
				 	<input type="text" id="txt_qskjqj" name="qskjqj" style="width:150px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="form-control input-radius" value="${qskjqj}"/>
                    <span style="float:left; height:26px;line-height:26px;color:#337ab7;">&nbsp;&nbsp;至&nbsp;&nbsp;</span>
                    <input type="text" id="txt_jskjqj" name="jskjqj" style="width:150px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="form-control input-radius" value="${jskjqj}"/>
                    
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>会计科目</span>
					<input type="text" id="txt_kjkm" style="width: 150px" name="kjkm" class="form-control input-radius window" value="${kjkm}"/>
					<span class="input-group-btn" style="float:left;line-height:25px;">
						<button type="button" id="btn_kjkm" class="btn btn-link btn-custom">选择</button>
					</span>				     
				</div>
			</div>
		</div>
     	<div class="row">
			<div class="col-sm-6">
				<div class="input-group">
				<span class="input-group-addon">包含未记账凭证&ensp;&ensp;&ensp;&ensp;</span>
				<div class="radiodiv">&nbsp;&nbsp;&nbsp;
                     <input type="radio" name="bhwjzpz"  value="1" checked> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			         <input type="radio" name="bhwjzpz"  value="0"> 否
				</div>
				</div>
			</div>										
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon">是否按日汇总查询&ensp;&ensp;</span>
					<div class="radiodiv">&nbsp;&nbsp;&nbsp;
					      <input type="radio" name="sfarhzcx"  value="1" checked> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          <input type="radio" name="sfarhzcx" value="0" > 否
					</div>
				</div>
				 <input type="hidden" name="end"  value="0">
			</div>
		</div> 

	</div>
	<div class="container-fluid box-content" >
			<div class="row">
				<div class="pull-center" style="text-align:center;">
    				<button type="button" class="btn btn-default" id="btn_save" style="background:#00acec;color: white;">确定</button>
    				<button type="reset" class="btn btn-default" id="btn_reset" style="background:#00acec;color: white;margin-left:20px;">取消</button>
 				</div>
			</div>
	</div>
	</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var winId = getTopFrame().layer.getFrameIndex(window.name);
$("#txt_kjkm").bindChange("${ctx}/suggest/getXx","KJKM");

$(function(){	
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
		 kjkm:{validators:{notEmpty: {message: '不能为空'}}}
          }
	      });
	$("#btn_save").click(function(){	
		var valid;
		if(validate){
			validate.bootstrapValidator("validate");
			valid = $('#myform').data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			doSaveMe();	
		}
	});
	function isNull(str){
		if(str==null||str==""){
			return true;			
		}
		return false;
	}
	function doSaveMe(){
		var kjkm = $("[name='kjkm']").val();
		var kmmc = "${kmmc}";
		var qskjqj = $("[name='qskjqj']").val();
		var jskjqj = $("[name='jskjqj']").val();
		var bhwjzpz = $("[name='bhwjzpz']:checked").val();
		if(!isNull(qskjqj) && !isNull(jskjqj)){
			if(qskjqj.substring(0,4) != jskjqj.substring(0,4)){
				alert("会计期间必须同一年度！");
				return;
			}
		}else if(isNull(qskjqj)&&isNull(jskjqj)){
			alert("请至少填写一个会计期间！");
			return;
		}
		var kjnd = qskjqj.substring(0,4);
// 		qskjqj = parseInt(qskjqj.substring(6,2));
// 		jskjqj = parseInt(jskjqj.substring(5,jskjqj.length));
		var json = $("#myform").serializeObject("start","end");  //json对象				
  		var jsonStr = JSON.stringify(json);  //json字符串
  		var bbqj = qskjqj+"至"+jskjqj;
  		var sfarhzcx=$("input[name='sfarhzcx']:checked").val();
  		$.ajax({
  			url:"${ctx}/Zz/paramSession",
  			data:"params="+jsonStr+"&bbqj="+bbqj,
  			dataType:"json",
  			type:"post",
  			complete:function(){
  				getIframWindow("${param.pname}").kz(kjkm,qskjqj,jskjqj,kmmc,bhwjzpz,kjnd,sfarhzcx);
  				var winId = getTopFrame().layer.getFrameIndex(window.name);
  				close(winId);
  			}
  		});
	}
	$("#btn_kjkm").click(function(){
		select_commonWin("${ctx}/webView/kjhs/bbzx/kmye/kjkmTree.jsp?controlId=txt_kjkm&year=","科目设置","920","630");
    });
	$("#btn_reset").click(function(){
    	close(winId);
	});	
}); 
</script>
</body>
</html>