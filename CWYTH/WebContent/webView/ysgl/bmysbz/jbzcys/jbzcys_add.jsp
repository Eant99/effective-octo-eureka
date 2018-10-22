<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>基本支出预算增加</title>
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
	.btn_td{
		width:14mm!important;
		height:6mm!important;
		
	}
	.selWindow{
		width:280px!important;
	}
	
	.number1{
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
</style>
</head>
<body>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
String date = sdf.format(new Date());
int dyn = Integer.parseInt(date)+1;
int den = Integer.parseInt(date)+2;
int dsn = Integer.parseInt(date)+3;
%>
<div id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>增加基本支出预算</span>
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>基本支出预算信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<form id="table1">
			<input type="hidden" name="guid" id="guid" value="${guid}">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申报人员</span>
                            <input type="text" id="txt_sbry1" class="form-control input-radius" name="sbry1" value="${dlr }" readonly>
                             <input type="hidden" id="txt_sbry2" class="form-control input-radius" name="sbry" value="${rybh }">
<!--                             <span class="input-group-btn"><button type="button" id="btn_sbry" class="btn btn-link btn-custom">选择</button></span>
 -->                            
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申报部门</span>
							<input type="text" id="txt_sbbm1" class="form-control input-radius window" name="sbbm1" value="${dwmc }"/>
							<input type="hidden" id="txt_sbbm2" class="form-control input-radius window" name="sbbm" value="${dwbh }"/>
							<span class="input-group-btn"><button type="button" id="btn_sbbm" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申报年度</span>
							<input type="text" id="txt_sbnd" class="form-control input-radius window year" name="sbnd" value="<%=date %>" data-format="yyyy"/>
							 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><%=dyn %>年支出汇总（万元）</span>
							<input type="text" id="" class="form-control input-radius number1" name="dynzchz" value="" readonly/>
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><%=den %>年支出汇总（万元）</span>
							<input type="text" id="" class="form-control input-radius number1" name="denzchz" value="" readonly/>
						</div>
					</div>
				
				   <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><%=dsn %>年支出汇总（万元）</span>
							<input type="text" id="" class="form-control input-radius number1" name="dsnzchz" value="" readonly/>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required"></span>测算依据</span>
							<textarea id="txt_csyj" style="width:100%;height:80px" name="csyj" class="form-control input-radius" >${xmzc.csyj }</textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required"></span>开支范围</span>
							<textarea id="txt_kzfw" style="width:100%;height:80px" name="kzfw" class="form-control input-radius" ></textarea>
						</div>
					</div>
			  </div>
			   
			 
			</div>
			</form>
		</div>
		<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>基本支出预算明细信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
			<div class="container-fluid box-content">
			<form id="table2">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        <!-- 	<th>操作</th> -->
				            <th style="width:300px;"><span class="required" style="color:red ;">*</span>经济科目</th>
				            <th style="width:250px;"><span class="required" style="color:red ;">*</span><%=dyn %>年支出（万元）</th>
				            <th style="width:250px;"><span class="required" style="color:red ;">*</span><%=den %>年支出（万元）</th>
				            <th style="width:250px;"><span class="required" style="color:red ;">*</span><%=dsn %>年支出（万元）</th>
				            <th style="width:450px;">说明</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1" class="tr1">
				    		<!-- <td class="btn_td">
							  <div class="addBtn"></div>
				    		</td> -->
				    		<td>
				    	<!-- 	<div class="input-group"> -->
				    			<input type="text" id="txt_jjkm" class="form-control input-radius window null" readonly name="jjkm1" value="">
				    			<input type="hidden" id="txt_jjkbh"  name="jjkmbh" value="">		
				    			<input type="hidden" id="txt_jbzcbh"  name="jbzcbh" value="${guid}">
				    			
				    			<!-- <span class="input-group-btn">
				    			<button type="button" id="btn_jjkm" aj="txt_bz" sj="txt_jjkm" bj="txt_jjkbh" class="btn btn-link btn-custom">选择</button>
				    			</span> -->
				    		<!-- </div> -->
				    		</td>
				    		<td>
				    			<input type="text" id="txt_dynzc" class="form-control input-radius number1 null" name="dynzc" value="" >
				    		</td>
				    		<td>
				    			<input type="text" id="txt_denzc" class="form-control input-radius number1 null" name="denzc" value="" >
				    		</td>
				    		<td>
				    			<input type="text" id="txt_dsnzc" class="form-control input-radius number1 null" name="dsnzc" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_bz" class="form-control input-radius bz" name="bz" readonly value="">
				    		</td>
				    	</tr>
				    </tbody>
				</table>
				</form>
			</div>
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var m=1;
$(function(){
	//单位弹窗 
	$("#btn_sbbm").click(function(){
		var a= "jb"
		
		select_commonWin("${ctx}/bmysbz/dwpage?controlId=txt_sbbm1&controlId1=txt_sbbm2&a="+a,"单位信息","920","630");
		//var sbbm = document.getElementById("txt_sbbm1");
		
		m++
		
    });
	//增加明细信息
	/* function addmx(){
		var dwbh =$("#txt_sbbm2").val();
		console.log("dwbh====="+dwbh);
			$.ajax({
				type:"post",
				url:"${ctx}/bmysbz/getJbzcmxListaaa",
				dataType:"json",
				data:"dwbh="+dwbh,
				success:function(val){	
					console.log("val====="+val);
					
				},
				error:function(val){
									
				}	
			});
		
	} */
	//申报人员弹窗 
	$("#btn_sbry").click(function(){
		select_commonWin("${ctx}/bmysbz/dwTree?controlId=txt_sbry1&controlId1=txt_sbry2","人员信息","920","630");	
    });
	//经济科目弹窗 
	$(document).on("click","[id^=btn_jjkm]",function(){
		var sjid = $(this).attr("sj");
		var bjid = $(this).attr("bj");
		var ajid = $(this).attr("aj");
		select_commonWin("${ctx}/bmysbz/jjkmTree?controlId="+sjid+"&controlId1="+bjid+"&controlId2="+ajid,"经济信息","920","630");

	});
	var validate = $("#table1").bootstrapValidator({fields:{
			sbry1:{validators:{notEmpty: {message: '申报人员不能为空'}}},
       	    sbbm1:{validators:{notEmpty: {message: '申报部门不能为空'}}},
       		sbnd:{validators:{notEmpty: {message: '申报年度不能为空'}}},
            csyj:{validators:{stringLength:{message:'测算依据过长',max:'500'}}},
            kzfw:{validators:{stringLength:{message:'开支范围过长',max:'500'}}},
       	 	
       	}
	   });
	//返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/webView/ysgl/bmysbz/jbzcys/jbzcys_list.jsp";
	});
	//保存按钮
	$("#btn_save").click(function(e){
		checkAutoInput();
		doSave1(validate,"table1","${ctx}/bmysbz/addJbzcys",function(val){
		
		});
	});	
	function doSave1(_validate, _formId, _url, _success, _fail){
		checkAutoInput();
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(!tag){
			valid = false;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				dataType:"json",
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){	
					if(val.success=="true"){
						next();
					}else{
						alert(val.msg);
						
					}
					
				},
				error:function(val){
									
				}	
			});
		}
	}
	$.fn.serializeObject1 = function(start,end){
		var f = {"list":[]};
	    var a = this.serializeArray();
	    var o = {};
	    $.each(a, function() {
	    	if (this.name == start) {
	        	o = {};
	        	o[this.name] = this.value;
	        } else if(this.name == end){
	        	o[this.name] = this.value;
	        	f["list"].push(o);
	        }else{
	        	o[this.name] = this.value;
	        }
	    });
	    	console.log(JSON.stringify(f));
	    return f;
	};
	//联想输入
	$("#txt_dwmc").bindChange("${ctx}/suggest/getXx","D");//部门
	$("#btn_dwmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwmc","单位信息","920","630");
    });
});
//明细js操作
$(function(){
	//费用名称
	$(document).on("click","[id^=btn_yskm]",function(){
		var sjid = $(this).attr("sj");
		select_commonWin("${ctx}/srys/srys/kmxx?controlId="+sjid,"科目信息","920","630");
    });
	//新增按钮
	var num=2;
	$(document).on("click","[class*=addBtn]",function(){
		var jbzcbh = $("#txt_jbzcbh").val()
		//alert(srysbh);
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		$parentTr.find(":input").removeClass("border");
		$parentTr.find("[id=txt_jbzcbh]").val(jbzcbh);
		
		$parentTr.find("[id^=btn_jjkm]").attr({"sj":"txt_jjkm"+num,"bj":"txt_jjkbh"+num,"id":"btn_jjkm"+num,"aj":"txt_bz"+num});
		$parentTr.find("[id^=txt_jjkm]").attr({"id":"txt_jjkm"+num});
		$parentTr.find("[id^=txt_jjkbh]").attr({"id":"txt_jjkbh"+num});
		$parentTr.find("[id^=txt_bz]").attr({"id":"txt_bz"+num});
		num++
		$(this).parents("tr").after($parentTr);
	});
	
	
	
	
});
//空验证
function checkAutoInput(){
	tag = true;
	var ys = $(".null");
	var value = "";
	$.each(ys,function(){
		value = $(this).val();
		if(value==""){
			$(this).addClass("border");
			tag = false;
		}
	});
}
//计算第一年
$(document).on("keyup","[id=txt_dynzc]",function(){
	computerBn3();
});
function computerBn3(){
	var dynzchz = 0.0000;
	$.each($("[id=txt_dynzc]"),function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		dynzchz = parseFloat(dynzchz)+parseFloat(val);
	});
	$("[name='dynzchz']").val(dynzchz.toFixed(4));
} 
//计算第二年
$(document).on("keyup","[id=txt_denzc]",function(){
	computerBn2();
});
function computerBn2(){
	var denzchz = 0.0000;
	$.each($("[id=txt_denzc]"),function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		denzchz = parseFloat(denzchz)+parseFloat(val);
	});
	$("[name='denzchz']").val(denzchz.toFixed(4));
} 
//计算第三年
$(document).on("keyup","[id=txt_dsnzc]",function(){
	computerBn1();
});
function computerBn1(){
	var dsnzchz = 0.0000;
	$.each($("[id=txt_dsnzc]"),function(){
		var val = $(this).val();
	
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		dsnzchz = parseFloat(dsnzchz)+parseFloat(val);
		
	});
	$("[name='dsnzchz']").val(dsnzchz.toFixed(4));
} 

$(document).on("focus","[class*=border]",function(){
	$(this).removeClass("border");
});
//赋值
function fz(id){
	var num = id.substring(id.indexOf("m")+1);
	$("[id=txt_snys"+num+"]").val("12.2504");
	$("[id=txt_snys"+num+"]").focus();
	$("[id=txt_snsr"+num+"]").val("52.2564");
	$("[id=txt_snsr"+num+"]").focus();
	computer();
}
//上年计算
function computer(){
	var snysze = 0.0000;
	var snsrze = 0.0000;
	//计算上年预算总额
	var $_snys = $("[id^=txt_snys]");
	$.each($_snys,function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		snysze = parseFloat(snysze)+parseFloat(val);
	});
	$("[name='snysze']").val(snysze.toFixed(4));
	//计算上年收入
	var $_snsr = $("[id^=txt_snsr]");
	$.each($_snsr,function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		snsrze = parseFloat(snsrze)+parseFloat(val);
	});
	$("[name='snsrze']").val(snsrze.toFixed(4));
}
//计算本年预算
$(document).on("keyup","[id^=txt_bnys]",function(){
	computerBn();
});
function computerBn(){
	var bnysze = 0.0000;
	$.each($("[id^=txt_bnys]"),function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		console.log(bnysze+"----"+val);
		bnysze = parseFloat(bnysze)+parseFloat(val);
	});
	$("[name='bnysze']").val(bnysze.toFixed(4));
}
//删除
$(document).on("click","[class*=deleteBtn]",function(){
	$(this).parents("tr").remove();
	computerBn1();
		computerBn2();
		computerBn3();
});
$(document).on("blur", ".number1", function(e){
	$(this).Num(4,true,false,e);
	return false;
});
function next(){
    $(".tr1").remove();
	var json = $('#table2').serializeObject1("jjkmbh","bz");  //json对象				
	var jsonStr = JSON.stringify(json);  //json字符串
	$.ajax({
	   			url:"${ctx}/bmysbz/addJbzcysmx?jbzcbh=${guid}",
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				alert("保存成功！");  	 
				window.location.href = "${ctx}/webView/ysgl/bmysbz/jbzcys/jbzcys_list.jsp";
	   			}
			}); 
}
function addmx(dwbh){
	$("#tr_1:not(.tr1)").remove();
		$.ajax({
			type:"post",
			url:"${ctx}/bmysbz/getJbzcmxListaaa",
			dataType:"json",
			data:"dwbh="+dwbh,
			success:function(val){	
				console.log("长度是=="+val.length);
				if(val.length==0){
					alert("当前部门没有对应的经济科目");
					 $("tbody tr:not(.tr1)").remove();
					 $(".tr1").show();
					 //$("tbody tr:last").hide();
				}else{
					  $(".tr1").show();
					$.each(val,function(i,v){
						 $(".tr1 input:not(.bz)").addClass("null");
						 var $tr = $("#tr_1").clone();
						 console.log("m==="+m);
	  			          if(m==2){
						    $tr.removeClass("tr1");
							$tr.find("[name='jjkm1']").val(v.JJKM);
							$tr.find("[name='jjkmbh']").val(v.GUID);
							$tr.find("[name='bz']").val(v.BZ);
							$("#tr_1").before($tr);					   
					   }else{
						   console.log("12311111");
						   $tr.removeClass("tr1");
						  
						   $tr.find("[name='jjkm1']").val(v.JJKM);
							$tr.find("[name='jjkmbh']").val(v.GUID);
							$tr.find("[name='bz']").val(v.BZ);
							$("#tr_1").before($tr);	
					   }
	  			      
					
					});
					 // $("tbody tr:not(.tr1)").remove();
	  			        $(".tr1").hide();
	  			      $(".tr1 input").removeClass("null");
					
				}
				
				//$("tbody tr:last-child").hide();
				
			},
			error:function(val){
								
			}	
		});
	
}

</script>

</html>