<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>增加经济科目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.tool-fix{
		display:none;
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
	.tables {
    margin-left: 20px;
    max-width: 100%;
    margin-bottom: 20px;
    width:50%;
   }
 .p1{
    text-align:center;
    font-family:宋体;
    color:red;
    font-size:18px;
   }
 .aa{
text-align:right;
}
 [class^=td_first_]{
 	text-align:center;
 	width:100%;
 	height:30px;
 }
 [class^=td_second_]{
 	width:100%;
 	height:30px;
 }
 [class^=td_three_]{
 	width:100%;
 	height:30px;
 }
 [class$=first_2],[class$=first_1],[class$=first_3]{
 width:20%;
 }
 [class$=second_1],[class$=second_2],[class$=second_3]{
 width:30%;
 }
 .td_input{
 	width:100%;
 	border:none;
 }
 a{
	 text-decoration:none;
 }
 .addBtn0,.addBtn1,.addBtn2,.addBtn3{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn0:after,.addBtn1:after,.addBtn2:after,.addBtn3:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	}
	.delBtn1,.delBtn2,.delBtn3{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.delBtn1:after,.delBtn2:after,.delBtn3:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
	    position: relative;
	    cursor: pointer;
/* 	    top:3px; */
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
<div id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid" value="${kmsz.guid}" />
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
			</span>
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>成本核算</button>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
			   
        </div>
    </div>
<div class="box" style="top:36px">
	<c:if test="${guid == '123'}">
	<form id="table1" class="myform myformPzby" id="addsr">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>成本中心成本</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody class="tbody1">
        				<tr >
<!-- 					    	<td class="td_first_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		操作 -->
<!-- 					    	</td> -->
							<td class="td_first_first_1" style="text-align:center;" valign="middle">
					    		成本中心
					    	</td>
					    	<td class="td_first_second_1" style="text-align:center;" valign="middle">
					    		成本金额
					    	</td>
				   		</tr>
        			 	<tr id="pbtr_1" class="pdtr_1 pd_tr1">
							<td class="td_second_first_2" style="text-align:center;" valign="middle">
					    		教学中心
					    	</td>
					    	<td class="td_second_second_2">
					    		<input type="text" readonly="readonly" style="width:100%;border:none;" name="kmmc" id="txt_srkm" value="12.00"/>
					    	</td>
				   		</tr>
				   		<tr></tr>
        			</tbody>
				</table>
		 </div>
		 </div>
		 </div>
		 </form>
		</c:if>
		<c:if test="${guid =='345'}">
	<form id="table1" class="myform myformPzby" id="addsr">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>成本中心成本</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody class="tbody1">
        				<tr >
<!-- 					    	<td class="td_first_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		操作 -->
<!-- 					    	</td> -->
							<td class="td_first_first_1" style="text-align:center;" valign="middle">
					    		成本中心
					    	</td>
					    	<td class="td_first_second_1" style="text-align:center;" valign="middle">
					    		成本金额
					    	</td>
				   		</tr>
        			 	<tr id="pbtr_1" class="pdtr_1 pd_tr1">
							<td class="td_second_first_2" style="text-align:center;" valign="middle">
					    		教学中心
					    	</td>
					    	<td class="td_second_second_2">
					    		<input type="text" readonly="readonly" style="width:100%;border:none;" name="kmmc" id="txt_srkm" value="12.00"/>
					    	</td>
				   		</tr>
				   		<tr id="pbtr_1" class="pdtr_1 pd_tr1">
							<td class="td_second_first_2" style="text-align:center;" valign="middle">
					    		行政中心
					    	</td>
					    	<td class="td_second_second_2">
					    		<input type="text" readonly="readonly" style="width:100%;border:none;" name="kmmc" id="txt_srkm" value="24.00"/>
					    	</td>
				   		</tr>
				   		<tr></tr>
        			</tbody>
				</table>
		 </div>
		 </div>
		 </div>
		 </form>
		</c:if>
		<c:if test="${guid == '123'}">
	<form id="table1" class="myform myformPzby" id="addsr">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>成本对象成本</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody class="tbody1">
        				<tr >
<!-- 					    	<td class="td_first_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		操作 -->
<!-- 					    	</td> -->
							<td class="td_first_first_1" style="text-align:center;" valign="middle">
					    		成本对象
					    	</td>
					    	<td class="td_first_second_1" style="text-align:center;" valign="middle">
					    		成本金额
					    	</td>
				   		</tr>
        			 	<tr id="pbtr_1" class="pdtr_1 pd_tr1">
							<td class="td_second_first_2" style="text-align:center;" valign="middle">
					    		学生
					    	</td>
					    	<td class="td_second_second_2">
					    		<input type="text" readonly="readonly" style="width:100%;border:none;" name="kmmc" id="txt_srkm" value="1208.00"/>
					    	</td>
				   		</tr>
				   		<tr></tr>
        			</tbody>
				</table>
		 </div>
		 </div>
		 </div>
		 </form>
		</c:if>
		<c:if test="${guid =='345'}">
	<form id="table1" class="myform myformPzby" id="addsr">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>成本中心成本</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody class="tbody1">
        				<tr >
<!-- 					    	<td class="td_first_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		操作 -->
<!-- 					    	</td> -->
							<td class="td_first_first_1" style="text-align:center;" valign="middle">
					    		成本对象
					    	</td>
					    	<td class="td_first_second_1" style="text-align:center;" valign="middle">
					    		成本金额
					    	</td>
				   		</tr>
        			 	<tr id="pbtr_1" class="pdtr_1 pd_tr1">
							<td class="td_second_first_2" style="text-align:center;" valign="middle">
					    		学生
					    	</td>
					    	<td class="td_second_second_2">
					    		<input type="text" readonly="readonly" style="width:100%;border:none;" name="kmmc" id="txt_srkm" value="1208.00"/>
					    	</td>
				   		</tr>
				   		<tr id="pbtr_1" class="pdtr_1 pd_tr1">
							<td class="td_second_first_2" style="text-align:center;" valign="middle">
					    		教师
					    	</td>
					    	<td class="td_second_second_2">
					    		<input type="text" readonly="readonly" style="width:100%;border:none;" name="kmmc" id="txt_srkm" value="2416.00"/>
					    	</td>
				   		</tr>
				   		<tr></tr>
        			</tbody>
				</table>
		 </div>
		 </div>
		 </div>
		 </form>
		</c:if>
		 </div>
		 </div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//选择框
	 $("#btn_jfbykm1").click(function(){
  		select_commonWin("${ctx}/kmsz/window?controlId=kmsz_kmmc","科目信息","920","630");
   }); 
	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/cbhs/goPageList";
	});
	/* //联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	$("#btn_dwld").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dwld","人员信息","920","630");
    });
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          rybh:{validators:{notEmpty: {message: '人员工号不能为空'}}},
          bddh:{validators:{notEmpty: {message: '办公电话不能为空'},tel:{message: '请输入有效的办公电话'}}},
          moblie:{validators:{notEmpty: {message: '手机号码不能为空'},phone:{message: '请输入有效的手机号码'}}},
          email:{validators:{emailAddress:{message: '请输入有效的E-mail地址'}}}
          }
	      }); */
	//保存按钮
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          kmbh:{validators: {notEmpty: {message: '科目编号不能为空'},stringLength:{message:'编号过长',max:'20'}}},
          kmmc:{validators: {notEmpty: {message: '科目名称不能为空'}}},
          }
	      });
	$("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/kmsz/doSave",function(val){
			if(val.success){
				alert(val.msg);
				//window.location.href = "${ctx}/kmsz/goJjkmPage";
				parent.location.href = "${ctx}/window/mainJjkmTree";
			}
		});
	});

	/* //查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}

	if("000000" == "${dwb.SJDW}"){
		$("#txt_sjdw").attr("readonly","true");
		$("#btn_sjdw").hide();
	} */
	//页面加载完后，控制实验室信息模块是否展示
	/* sysbz();
	//onoff按扭切换
	$("#btn_onoff").click(function(){
		if($("[name='sysbz']").val()=='0'){
			$("[name='sysbz']").val("1");
		}else if($("[name='sysbz']").val()=='1'){
			$("[name='sysbz']").val("0");
		}else{
			$("[name='sysbz']").val("0");
		}
		sysbz();
	}); */
});
function sysbz(){
	var $this = $("[name='sysbz']").val();
	if($this == '0'){
		$("#sysxx").show();
	}else{
		$("[name='syslx']").val("");
		$("[name='syslb']").val("");
		$("[name='sysmj']").val("0.00");
		$("[name='sysjb']").val("");
		$("[name='ssxk']").val("");
		$("[name='sysgs']").val("");
		$("#sysxx").hide();
	}
}
</script>

</html>