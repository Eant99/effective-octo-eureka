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
<title>往来单位信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
.btn_td{
		width:10px!important;
		height:6mm!important;
		text-align: center;
	}
.addBtn{
		text-align:center;
	    width: 20px;
	    height: 20px;
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
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	    margin-top:2px;
	}
	.deleteBtn{
		text-align:center;
	    width: 20px;
	    height: 20px;
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
/* 	    top:3px; */
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
	tr{
background-color: white !important;
}
 #tbody input{
		border:none;
		width:100%;
	}
	tbody td{
      padding:4px !important;
   }
</style>
</head>
<body>
<div id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${jsxx.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑收款单位信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>编辑收款单位信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<form id="myform2">
			  <input type="hidden" name="guid" id="guid" value="${guid}">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位编号</span>
							<input type="text" id="txt_wlbh" name="wlbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${wldwsz.wlbh}" readonly/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>户名</span>
                            <input type="text" id="txt_dwmc" class="form-control input-radius" name="dwmc" value="${wldwsz.dwmc}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位简称</span>
						 <input type="text" id="txt_dwjc" class="form-control input-radius" name="dwjc" value="${wldwsz.dwjc}"/>	
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">单位类型</span>
	                        <select id="drp_dwlx" class="form-control input-radius " name="dwlx"> 
	                                <option value="">未选择</option>
									<option value="01" <c:if test="${wldwsz.dwlx == '01'}">selected</c:if>>供应商</option>
									<option value="02" <c:if test="${wldwsz.dwlx == '02'}">selected</c:if>>主管部门</option>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位地址</span>
	                       <input type="text" id="txt_dwdz" class="form-control input-radius" name="dwdz" value="${wldwsz.dwdz}"/>	
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">联&ensp;系&ensp;人</span>
							  <input type="text" id="txt_lxr" class="form-control input-radius" name="lxr" value="${wldwsz.lxr}"/>	
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">统一社会信用代码</span>
							 <input type="text" id="txt_sh" class="form-control input-radius" name="sh" value="${wldwsz.sh}"/>	
						</div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">办公电话</span>
                           <input type="text" id="txt_bgdh" class="form-control input-radius" name="bgdh" value="${wldwsz.bgdh}"/>	
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">传&emsp;&emsp;真</span>
                            <input type="text" id="txt_cz" class="form-control" name="cz" value="${wldwsz.cz}">          
						</div>
				      </div>	
					</div>
					<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">是否对公支付</span>
	                        <select id="drp_sfdgzf" class="form-control input-radius select2" name="sfdgzf"> 
	                                <option value="">未选择</option>
									<option value="01" <c:if test="${wldwsz.sfdgzf == '01'}">selected</c:if>>是</option>
									<option value="02" <c:if test="${wldwsz.sfdgzf == '02'}">selected</c:if>>否</option>	
							</select>
						</div>
				      </div>
				    </div>
				</div>
				</form>
			</div>
		
		<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>银行清单
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
			<div class="container-fluid box-content">
			 <form id="myform1">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align: center;">操作</th>
				            <th style="width:300px; text-align: center;"><span class="required" style="color:red;">*</span>开户银行名称</th>
				            <th style="width:300px; text-align: center;"><span class="required" style="color:red;">*</span>开户银行帐号</th>
				            <th style="width:300px; text-align: center;">人民银行联行号</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				    <c:choose>
				    <c:when test="${not empty wldwmxb}">
				    <c:forEach var="wldwmxb" items="${wldwmxb}"> 
				    	<tr id="tr_1">
				    		<td class="btn_td" style="padding:8px;">
	                               <div class="addBtn add"></div>
				    		</td>
				    		
				    		<td>
				    		   <input type="hidden" id="txt_guid" class=" input-radius" a="a" name="guid" value="${wldwmxb.guid }">
				    		    <input type="hidden" id="txt_wlbh1" class=" input-radius" name="wlbh1" value="${guid }">
				    			<input type="text" id="txt_khyh1"  class=" input-radius null" name="khyh1" value="${wldwmxb.khyh}">
				    			
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class=" input-radius null" name="khyhzh1" value="${wldwmxb.khyhzh}">
				    		</td>
                            <td>
				    			<input type="text" id="txt_yhlhh1" a="a" class=" input-radius int" name="yhlhh1" value="${wldwmxb.yhlhh}">
				    		</td>
				    	</tr>
				    	</c:forEach> 
				    	</c:when>
				        <c:otherwise>
				          <tr id="tr_1">
				    		<td class="btn_td">
	                               <div class="addBtn"></div>
				    		</td>
				    		
				    		<td>
				    		   <input type="hidden" id="txt_guid" class=" input-radius" a="a" name="guid" value="">
				    		    <input type="hidden" id="txt_wlbh1" class=" input-radius" name="wlbh1" value="${guid }">
				    			<input type="text" id="txt_khyh1"  class=" input-radius null" name="khyh1" value="">
				    			
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class=" input-radius null yhh" name="khyhzh1" value="">
				    		</td>
                             <td>
					    		<input type="text" id="txt_yhlhh1" a="a" class=" input-radius  int" name="yhlhh1" value="">
					    	</td>
				    	</tr>
				        </c:otherwise>
				    	</c:choose>
				    </tbody>
				</table>
				</form>
			</div>
		</div> <!-- //baxpanel -->
	   </div>	
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">

$(function(){
	$("#txt_bgdh").blur(function(){
	 	var bgdh = $("#txt_bgdh").val().length;
	 	if(bgdh==8 || bgdh==12 || bgdh==11 ||bgdh==""){
	 	
	 	}else{
	 		alert("请输入有效的办公电话");
	 		$("#txt_bgdh").val("");
	 	}
	});
	$("[name=yhlhh1]").blur(function(){
	 	var len = $("#txt_yhlhh1").val().length;
	 	if(len> 12){
	 		alert("人民银行联行号过长,12位数");
	 	}
	});
	$("#txt_cz").blur(function(){
	 	var cz = $("#txt_cz").val().length;
	 	if(cz==8 || cz==12 || cz==""){
	 	
	 	}else{
	 		alert("请输入有效的传真");
	 		$("#txt_cz").val("");
	 	}
	});
	//验证银行号是否重复
  	$(document).on("blur","[name=khyhzh1]",function(){
  		var khyhzh = $(this).val();
  		$(this).addClass("zh");
  		var khyhzhcd = khyhzh.length;
//   		var bankCode = bankCardAttribution(khyhzh); //每个银行的标志符  建行 CCB
//   		console.log(bankCode+"**")
// 		var code = bankCode[0]
// 		console.log(code)
// 		if(code!=undefined){
			
// 		}else{
// 			$(this).val("");
//   			alert("请输入有效银行账号");
// 		}
  		 if(khyhzhcd==16||khyhzhcd==19||khyhzhcd==26||khyhzhcd==""){
  			
  		}else{
  			$(this).val("");
  			alert("请输入有效银行账号");
  		} 
  		var a  = $(".yhh:not(.zh)");
	    $.each(a,function(){
	    	var b = $(this).val();
         if(khyhzh==b){
        	 alert("该银行账号已存在");
        	 $(".zh").val("");     
          }
	    });
	    $(this).removeClass("zh")
  	});
	/* $(document).on("blur","#txt_wlbh",function(){
		var wlbh = $(this).val();
		alert(wlbh);
		$("txt_wldw1").val(wlbh);
		
	}); */
// 	$("#txt_wlbh").blur(function(){
// 		var wlbh = $(this).val();
		
// 		$("#txt_wlbh1").val(wlbh);
		
// 	});
	//新增按钮
	function checkExist(url,a_data){
		var tag = false;
		$.ajax({
			type:"post",
			url: url,
			data: a_data,
			async:false,
			success:function(val){
				var result = JSON.getJson(val);
				if(result.exist){
					tag = true;
				}
			}
		});
		return tag;
	}
	var num = 2;
	$(document).on("click","[class*=addBtn]",function(){
		var wlbh = $("#txt_wlbh1").val();
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		$parentTr.find(":input").removeClass("border");
		//$parentTr.find("[id=txt_wlbh]").val(wlbh);
		$parentTr.attr("id","tr_"+num);
		$parentTr.find("#txt_wlbh1").val(wlbh);
		
		
		num++;
		$(this).parents("tr").after($parentTr);
	});

	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();

	});
	//验证方法
	var validate = $('#myform2').bootstrapValidator({fields:{
          dwmc:{validators:{ notEmpty: {message: '户名不能为空'},stringLength:{message:'单位名称过长',max:'200'}}},
          dwdz:{validators: {stringLength:{message:'单位地址过长',max:'200'}}},
          dwjc:{validators:{stringLength:{message:'单位简称过长',max:'100'}}},
          lxr:{validators: {stringLength:{message:'联系人姓名过长',max:'32'}}},
          khyh:{validators:{notEmpty:{message: '开户银行不能为空'},stringLength:{message:'开户银行名称过长',max:'200'}}},
          khyhzh:{validators:{ notEmpty:{message: '开户银行帐号不能为空'},stringLength:{message:'开户银行账号过长',max:'25'}}},
          yhlhh1:{validators: {stringLength:{message:'人民银行联行号过长',max:'12'}}}
          }
	      });
	
	//保存按钮
	$("#btn_save").click(function(e){
		var rownum = $("#myform1").find("#tbody").find("tr").length;
		var deltr = $(".deleteBtn").parents("tr");
		deltr.each(function(){
			$(this).find("#txt_khyh1").addClass("null");
			$(this).find("#txt_khyhzh1").addClass("null");
		});
		
		if(rownum>1){
			var nulltr = $(".addBtn").parents("tr");
			var khyh = nulltr.find("[name='khyh1']").val();
			var khyhzh = nulltr.find("[name='khyhzh1']").val();
			var khyhlhh = nulltr.find("[name='yhlhh1']").val();
			if(khyh=="" && khyhzh=="" && khyhlhh==""){
				nulltr.find("#txt_khyh1").removeClass("null");
				nulltr.find("#txt_khyhzh1").removeClass("null");
			}else{
				nulltr.find("#txt_khyh1").addClass("null");
				nulltr.find("#txt_khyhzh1").addClass("null");
			}
		}
		var flag = true;
		var zds = $("[name='khyhzh1']");//开户银行账号
		var lhh = $("[name='yhlhh1']");//银行联行号
		var zdid = [];  //所有账号
		var lhhid = [];  //所有联行号
		zds.each(function(){
			zdid.push($(this).val());
		});
		lhh.each(function(){
			lhhid.push($(this).val());
		});
		//判断是否是建行  填写银行联行号
		for (var i=0;i<zdid.length;i++) {
			var aa = zdid[i]; //每个银行账号
			var bankCode = bankCardAttribution(aa); //每个银行的标志符  建行 CCB
			var code = bankCode[0]
			if (aa!="" && code == "CCB") {
				var lhhVal = lhhid[i];	
				if(lhhVal==""){
				}else if(lhhVal.length != "12"){
					alert("请输入第"+(i+1)+"行正确的人民银行联行号")
					flag = false;
				}
			}
			if (aa!="" && code != "CCB") {
				var lhhVal = lhhid[i];		
				if(lhhVal==""){
					alert("请输入第"+(i+1)+"行的人民银行联行号");
					flag = false;
				}else if(lhhVal.length != "12"){
					alert("请输入第"+(i+1)+"行正确的人民银行联行号");
					flag = false;
				}
			}
		}
		if(flag){
			doSave1(validate,"myform2","${ctx}/wldwsz/editWldwsz",function(val){
	
			});
		}
	});	
	function doSave1(_validate, _formId, _url, _success, _fail){
	   checkAutoInput();
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $("#myform2").data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(!tag){
			valid = false;
		}
		var url = "${ctx}/wldwsz/checkExist";
		var wlbh = $("#txt_wlbh").val();
		if(checkExist(url,"wldwbh="+wlbh)){
			alert("改编号已存在！");
			return;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){	
					delYhzh();
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
	//
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
	//先删后增
	function delYhzh(){
		var wlbh = $(".tbody").find("[name=wlbh1]").val();
		$.ajax({
   			url:"${ctx}/wldwsz/delYhzh",
   			data:"wlbh="+wlbh,
   			type:"post",
   			async:"false",
   			success:function(val){
   				next();
	   			
   			}
   		});
	}
	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/kjhs/wldwsz/wldwsz_list.jsp";
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
	//开户银行弹框
	$("#btn_khyh").click(function(){
		select_commonWin("${ctx}/webView/kjhs/wldwsz/window.jsp","银行信息","720","630");
    });
	//验证往来编号是否存在
	$("#txt_wlbm").blur(function(){
		$.ajax({
			type: "post",
			url: "${ctx}/wldwsz/checkwlbm",
			async: false,
			data:{ wlbm:wlbm},
			success:function(val){
				
			}
			
		});
	});
	//验证长度
	$(document).on("change","[name='khyh1']",function(){
		var val = $(this).val();
		if(val.length > 20){
			alert("长度不能操作20");
			$(this).val("");
		}
	})
	//验证往来名称是否存在
	$("#txt_wlmc").blur(function(){
		$.ajax({
			type: "post",
			url: "${ctx}/wldwsz/checkwlmc",
			async: false,
			data:{ wlmc:wlmc},
			success:function(val){
				
			}
			
		});
	});	
});
function next(){
	//alert(1);
	var json = $('#myform1').serializeObject1("guid","yhlhh1");  //json对象				
	var jsonStr = JSON.stringify(json);  //json字符串
	var wlbh = $("#txt_wlbh1").val();
	//alert(jsonStr);
	$.ajax({
		        url:"${ctx}/wldwsz/editWldwszmx?wlbh="+wlbh,
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				alert("保存成功！"); 
				 window.location.href="${ctx}/webView/kjhs/wldwsz/wldwsz_list.jsp"
	   			}
			}); 
}
var wlbh = $("#txt_wlbh1").val();
$(function(){
	addbutton();
	function addbutton(){
		var a = $(".tbody").find("tr").length;
		var aaa="${wldwmxb}";
		
		if(aaa.length>0){
			$(".add").removeClass("addBtn")
			
			$(".add").addClass("deleteBtn");		
			//var $parentTr = $("tr:last").clone();
			
			//$parentTr.find("input:not(#txt_wlbh1)").attr("value","");
			
			//$("tr:last").after($parentTr);
			
			$(".tbody tr:last").find(".add").removeClass("deleteBtn");
			$(".tbody tr:last").find(".add").addClass("addBtn");
		}else{
			
		}
	}
	
})
</script>
</body>
</html>