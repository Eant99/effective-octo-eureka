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
<title>收入预算编辑</title>
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
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑收入预算</span>
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>收入预算信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
			<form id="table1">
			  <input type="hidden" name="guid" id="guid" value="${guid}">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申报人员</span>
                            <input type="text" id="txt_sbry1" class="form-control input-radius" name="sbry1" value="${srys.SBRY1 }" readonly>
                             <input type="hidden" id="txt_sbry" class="form-control input-radius" name="sbry" value="${srys.SBRY }">
<!--                             <span class="input-group-btn"><button type="button" id="btn_sbry" class="btn btn-link btn-custom">选择</button></span>
 -->                            
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申报部门</span>
							<input type="text" id="txt_sbbm1" class="form-control input-radius window" name="sbbm1" value="${srys.SBBM1 }"/>
							<input type="hidden" id="txt_sbbm" class="form-control input-radius window" name="sbbm" value="${srys.SBBM }"/>
							<span class="input-group-btn"><button type="button" id="btn_sbbm" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申报年度</span>
							<input type="text" id="txt_sbnd" class="form-control input-radius year" name="sbnd" value="${srys.SBND }" data-format="yyyy"/>
							 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><%=dyn %>年收入汇总（万元）</span>
 							<input type="text" id="" class="form-control input-radius number1" name="dynsrhz" value="<fmt:formatNumber value="${srys.DYNSRHZ}"  pattern=".0000"/>" readonly/>
                        </div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><%=den %>年收入汇总（万元）</span>
							<input type="text" id="" class="form-control input-radius number1" name="densrhz" value="<fmt:formatNumber value="${srys.DENSRHZ}"  pattern=".0000"/>" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><%=dsn %>年收入汇总（万元）</span>
							<input type="text" id="" class="form-control input-radius number1" name="dsnsrhz" value="<fmt:formatNumber value="${srys.DSNSRHZ}"  pattern=".0000"/>" readonly/>
						</div>
					</div>
				
				</div>
			
				<div class="row">
				      
				    <div class="col-md-12">
						 <div class="input-group">
							<span class="input-group-addon"><span class="required"></span>测算依据</span>
							<textarea id="txt_csyj" style="width:100%;height:80px" name="csyj" class="form-control input-radius" >${srys.CSYJ }</textarea>
						</div>
					</div> 
					
					<%-- <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">确认状态</span>
							<input type="text" id="txt_qrzt" class="form-control input-radius window" name="qrzt" value="${srys.qrzt }"/>														
						</div>
					</div> --%>
			  </div>
			  </form>
			</div>
		</div>
		<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>收入预算明细信息
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
				        	<th>操作</th>
				            <th style="width:300px;"><span class="required" style="color:red ;">*</span>收入项目</th>
				            <th style="width:300px;"><span class="required" style="color:red ;">*</span><%=dyn %>年收入（万元）</th>
				            <th style="width:300px;"><span class="required" style="color:red ;">*</span><%=den %>年收入（万元）</th>
				            <th style="width:300px;"><span class="required" style="color:red ;">*</span><%=dsn %>年收入（万元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				     <c:forEach var="srysmx" items="${srysmx}"> 
				    	<tr id="tr_1">
				    	
				    	     <td class="btn_td">
							    <div class="addBtn add" ></div>
				    		</td>
				    		 <%-- <c:forEach var="srysmx" items="${srysmxlist}">
				    		  ${srysmx.SRXMBH }</br>
				    		  ${srysmx.DYNSR }</br>
				    		 </c:forEach> --%>
				      
				    	   
				    	   
				    		<td>
				    	<!-- 	<div class="input-group"> -->
				    			<input type="text" id="txt_srxm${srysmx.guid }" class="form-control input-radius window null" name="srxmbh1" value="${srysmx.srxmbh1 }">				  
				    			<input type="hidden" id="txt_srxbh"  name="srxmbh" value="${srysmx.srxmbh }">
				    			<input type="hidden" id="txt_guid"  name="guid" value="${srysmx.guid }">
 				    			<input type="hidden" id="txt_srysbh"  name="srysbh" value="${guid }">
				    			<span class="input-group-btn">
<%-- 				    			<button type="button" id="btn_srxm" sj="txt_srxm${srysmx.guid }" bj="txt_srxbh"  class="btn btn-link btn-custom">选择</button>
 --%>				    			</span>
				    	<!-- 	</div> -->
				    		</td> 
				    		<td>
				    			<input type="text" id="txt_dynsr" class="form-control input-radius number1 null" name="dynsr" value="<fmt:formatNumber value="${srysmx.DSNSR}"  pattern=".0000"/>" />
				    			
				    		</td>
				    		<td>
				    			<input type="text" id="txt_densr" class="form-control input-radius number1 null" name="densr" value="<fmt:formatNumber value="${srysmx.DENSR}"  pattern=".0000"/>" />
				    		</td>
				    		<td>
				    			<input type="text" id="txt_dsnsr" class="form-control input-radius number1 null" name="dsnsr" value="<fmt:formatNumber value="${srysmx.DSNSR}"  pattern=".00"/>" />
				    		</td>
   			    	</tr>
				    	</c:forEach> 
				    </tbody>
				</table>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	    addbutton();
		function addbutton(){
			var a = $(".tbody").find("tr").length;
			if(a>1){
				$(".add").removeClass("addBtn")
				
				$(".add").addClass("deleteBtn");
				
				$(".tbody tr:last").find(".add").removeClass("deleteBtn");
				$(".tbody tr:last").find(".add").addClass("addBtn")
			}
		}
	
	//收入项目弹窗
	/* $(document).on("click","[id^=btn_srxm]",function(){
		var sjid = $(this).attr("sj");
		var bjid = $(this).attr("bj");
		select_commonWin("${ctx}/bmysbz/srxmPage?controlId="+sjid+"&controlId2="+bjid,"收入项目信息","920","630");
	}) */
	$(document).on("click",".addBtn",function(){
		select_commonWin("${ctx}/bmysbz/srxmPage","收入项目信息","920","630");
	})
	//单位弹窗 
	$("#btn_sbbm").click(function(){
		var a = "sr"
		select_commonWin("${ctx}/bmysbz/dwpage?controlId=txt_sbbm1&controlId1=txt_sbbm&a="+a,"单位信息","920","630");	
    });
	//申报人员弹窗 
	$("#btn_sbry").click(function(){
		select_commonWin("${ctx}/bmysbz/dwTree?controlId=txt_sbry1&controlId1=txt_sbry","人员信息","920","630");	
    });
	
	
	
	var validate = $("#table1").bootstrapValidator({fields:{
			sbry1:{validators:{notEmpty: {message: '申报人员不能为空'}}},
       	    sbbm1:{validators:{notEmpty: {message: '申报部门不能为空'}}},
       		sbnd:{validators:{notEmpty: {message: '申报年度不能为空'}}},
            csyj:{validators:{stringLength:{message:'测算依据过长',max:'500'}}},

       	
       	}
	});
	//保存按钮
	$("#btn_save").click(function(e){
		doSave1(validate,"table1","${ctx}/bmysbz/editSrys",function(val){
			
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
 		        }else if(this.name.indexOf("sfjk")>=0){
 	                  o.sfjk=this.value;
 	                  this.name=undefined;
 		        }else{
 		        	o[this.name] = this.value;
 		        }
 	    });
 	    return f;
 	}; 
	//返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/webView/ysgl/bmysbz/srys/srys_list.jsp";
	});
	
	//联想输入
	$("#txt_dwmc").bindChange("${ctx}/suggest/getXx","D");//部门
	$("#btn_dwmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwmc","单位信息","920","630");
    });

//明细js操作

	//费用名称
	$(document).on("click","[id^=btn_yskm]",function(){
		var sjid = $(this).attr("sj");
		select_commonWin("${ctx}/srys/srys/kmxx?controlId="+sjid,"科目信息","920","630");
    });
	//新增按钮
	var num=2;
	/* $(document).on("click","[class*=addBtn]",function(){
		var srysbh = $("#txt_srysbh").val()
		//alert(srysbh);
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		
		//$parentTr.attr("id","tr_"+num);
		$parentTr.find(":input").val("");
		$parentTr.find(":input").removeClass("border");
		$parentTr.find("[id=txt_srysbh]").val(srysbh);
		$parentTr.find("[id^=btn_srxm]").attr({"sj":"txt_srxm"+num,"bj":"txt_srxbh"+num,"id":"btn_srxm"+num});
		$parentTr.find("[id^=txt_srxm]").attr({"id":"txt_srxm"+num});
		$parentTr.find("[id^=txt_srxbh]").attr({"id":"txt_srxbh"+num});
		num++
		$(this).parents("tr").after($parentTr);
	
	}); */
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
//计算第一年
$(document).on("keyup","[id=txt_dynsr]",function(){
	computerBn();
});
function computerBn(){
	var dynsrhz = 0.0000;
	$.each($("[id=txt_dynsr]"),function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		console.log(dynsrhz+"----"+val);
		dynsrhz = parseFloat(dynsrhz)+parseFloat(val);
	});
	$("[name='dynsrhz']").val(dynsrhz.toFixed(4));
} 
//计算第二年
$(document).on("keyup","[id=txt_densr]",function(){
	computerBn2();
});
function computerBn2(){
	var densrhz = 0.0000;
	$.each($("[id=txt_densr]"),function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		
		densrhz = parseFloat(densrhz)+parseFloat(val);
	});
	$("[name='densrhz']").val(densrhz.toFixed(4));
} 
//计算第三年
$(document).on("keyup","[id=txt_dsnsr]",function(){
	computerBn3();
});
function computerBn3(){
	var dsnsrhz = 0.0000;
	$.each($("[id=txt_dsnsr]"),function(){
		var val = $(this).val();
		if(val==""||val==0||isNaN(val)){
			val = 0.0000;
		}
		
		dsnsrhz = parseFloat(dsnsrhz)+parseFloat(val);
	});
	$("[name='dsnsrhz']").val(dsnsrhz.toFixed(4));
} 
//删除
$(document).on("click","[class*=deleteBtn]",function(){
	$(this).parents("tr").remove();
	var guid = $(this).parents().find("[name=guid]").val();
	$.ajax({
 			url:"${ctx}/bmysbz/deleteSrysmx",
 			type:"post",
 			data:"guid="+guid,
 			success:function(data){
 			
 				computerBn();
 				computerBn2();
 				computerBn3();
 						}
   
		}); 
	
});
$(document).on("blur", ".number1", function(e){
	$(this).Num(4,true,false,e);
	return false;
});
function next(){
	 	var json = $('#table2').serializeObject1("srxmbh","dsnsr");  //json对象				
		var jsonStr = JSON.stringify(json);  //json字符串
		$.ajax({
  	   			url:"${ctx}/bmysbz/editSrysmx",
  	   			type:"post",
  	   			data:"json="+jsonStr,
  	   			success:function(data){
					alert("保存成功");  	
					window.location.href = "${ctx}/webView/ysgl/bmysbz/srys/srys_list.jsp";
  	   			}
		   
 			}); 
		
}
var num=2;
function addsrys(guid,srxm){
	console.log("guid==="+guid);
	console.log("srxm==="+srxm);
	console.log("guid==="+guid.length);
	var srysbh = $("#txt_srysbh").val()
	//$("#tbody tr").removeAttr("id");
	for(var i=0;i<guid.length;i++){
		$("tr:last").attr("class","tr1");
		$(".tr1").show();
		var $parentTr = $(".tr1").clone();
		$parentTr.removeClass("tr1");
		console.log("srxm[i]====="+srxm[i]);
		//$parentTr.attr("id","tr_"+num);
		$parentTr.find("input:not(.srysbh)").attr("value","");
		$parentTr.find(":input").removeClass("border");
		$parentTr.find("[id=txt_srysbh]").val(srysbh);
		$parentTr.find("[id^=txt_srxm]").val(srxm[i]);
		$parentTr.find("[id=txt_srxbh]").val(guid[i]);
		$parentTr.find("[id^=btn_srxm]").attr({"sj":"txt_srxm"+num,"bj":"txt_srxbh"+num,"id":"btn_srxm"+num});
		$parentTr.find("[id^=txt_srxm]").attr({"id":"txt_srxm"+num});
		$parentTr.find("[id^=txt_srxbh]").attr({"id":"txt_srxbh"+num});
		num++
		$(".tr1").after($parentTr);
		$parentTr.removeClass("tr1");
		
	//	$parentTr.removeAttr("id");
		//$(".tr1").hide();
		$("tr").removeClass("tr1");
		
		
		
	}
	$(".addBtn").attr("class","deleteBtn");
	
	$(".deleteBtn:last").attr("class","addBtn");
	
	//$(this).removeClass("addBtn");
	//$(this).addClass("deleteBtn");
	
}

</script>

</html>