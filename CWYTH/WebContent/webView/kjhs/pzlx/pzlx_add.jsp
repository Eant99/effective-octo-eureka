<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证类型详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
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
 [class^=td_first_]{
 	text-align:center;
 	width:15%;
 	height:40px;
 }
 [class^=td_second_]{
 	width:45%
 }
 [class^=td_three_]{
 	width:45%
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
	    width: 26px;
	    height: 26px;
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
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.delBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.delBtn:after{
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
<div id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
<%-- 	<input type="hidden" name="guid"  value="${zffssz.guid}">
	<input type="hidden" name="czr"  value="${loginId}"> --%>
	
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑凭证类型信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>编辑凭证类型信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<form id="myform1">
			<input type="hidden" name="guid" id="txt_guid" value="${guid}">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>类型编号</span>
							<input type="text" id="txt_lxbh" name="lxbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value="${lxbh}"/>
						</div>
                    </div>
                     <div class="col-md-4">
                  	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>类型名称</span>
                            <input type="text" id="txt_lxmc" name="lxmc" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value=""/>
					</div>                	
                  </div>
                   <div class="col-md-4">
                  	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>凭&ensp;证&ensp;字</span>
                            <input type="text" id="txt_pzz" name="pzz" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value=""/>
					</div>                	
                  </div>
				</div>
			</div>
			</form>
			</div>			  
			<!-- 借方必有科目 -->
			<form id="myform2" class="myform myformJf" mc="addjf" xz="jf">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>借方必有科目</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody>
        				<tr >
					    	<td class="td_first_1"  style="text-align:center;" valign="middle">
					    		操作
					    	</td>
					    	<td class="td_second_1" style="text-align:center;" valign="middle">
					    		科目编号
					    	</td>
					    	<td class="td_three_1"  style="text-align:center;" valign="middle">
					    		科目名称
					    	</td>
				   		</tr>
        			 	<tr id="jbtr_1">
					    	<td class="td_first_1">
					    		<div class="addBtn0"></div>
					    	</td>
					    	<td class="td_second_1">
					    	    <input type="hidden" name="pzlxm" id="txt_pzlxm1" value="${guid}">
					    	    <input type="hidden" name="xzlx" id="txt_xzlx1" value="Jf">
					    	    <input type="hidden" name="kmbh" id="txt_kmbh1" value="">
					    		<input type="text" name="jbkm_1" class="td_input" value=""/>
					    	</td>
					    	<td class="td_three_1">
					    		<input type="text" name="jbmc_1" class="td_input yz" value=""/>
					    	</td>
				   		</tr>
        			</tbody>
				</table>
		 </div>
		 </div>
		 </div>
		 </form>		 
		 <!-- 贷方必有科目 -->
		    <form id="myform3" class="myform myformDf" mc="adddf" xz="df">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>贷方必有科目 </div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody>
        				<tr >
					    	<td class="td_first_1"  style="text-align:center;" valign="middle">
					    		操作
					    	</td>
					    	<td class="td_second_1" style="text-align:center;" valign="middle">
					    		科目编号
					    	</td>
					    	<td class="td_three_1"  style="text-align:center;" valign="middle">
					    		科目名称
					    	</td>
				   		</tr>
        			 	<tr id="dbtr_1">
					    	<td class="td_first_1">
					    		<div class="addBtn1"></div>
					    	</td>
					    	<td class="td_second_1">
					    	  <input type="hidden" name="pzlxm" id="txt_pzlxm2" value="${guid}">
					    	    <input type="hidden" name="xzlx" id="txt_xzlx2" value="Df">
					    	    <input type="hidden" name="kmbh" id="txt_kmbh2" value="">
					    		<input type="text" name="dbkm_1" class="td_input" value=""/>
					    	</td>
					    	<td class="td_three_1">
					    		<input type="text" name="dbmc_1" class="td_input yz" value=""/>
					    	</td>
				   		</tr>
        			</tbody>
				</table>
		 </div>
		 </div>
		 </div>
		 </form>
		 <!-- 凭证必有科目 -->
		 <form id="myform4" class="myform myformPzby" mc="addpzby" xz="pzby">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>凭证必有科目</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody>
        				<tr >
					    	<td class="td_first_1"  style="text-align:center;" valign="middle">
					    		操作
					    	</td>
					    	<td class="td_second_1" style="text-align:center;" valign="middle">
					    		科目编号
					    	</td>
					    	<td class="td_three_1"  style="text-align:center;" valign="middle">
					    		科目名称
					    	</td>
				   		</tr>
        			 	<tr id="pbtr_1">
					    	<td class="td_first_1">
					    		<div class="addBtn2"></div>
					    	</td>
					    	<td class="td_second_1">
					    	     <input type="hidden" name="pzlxm" id="txt_pzlxm3" value="${guid}">
					    	    <input type="hidden" name="xzlx" id="txt_xzlx3" value="Pzby">
					    	    <input type="hidden" name="kmbh" id="txt_kmbh3" value="">
					    		<input type="text" name="pbkm_1" class="td_input" value=""/>
					    	</td>
					    	<td class="td_three_1">
					    		<input type="text" name="pbmc_1" class="td_input yz" value=""/>
					    	</td>
				   		</tr>
        			</tbody>
				</table>
		 </div>
		 </div>
		 </div>
		 </form>
		 <!-- 凭证必无科目 -->
		 <form id="myform5" class="myform myformPzbw" mc="addpzbw" xz="pzbw">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>凭证必无科目</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
		
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody>
        				<tr >
					    	<td class="td_first_1"  style="text-align:center;" valign="middle">
					    		操作
					    	</td>
					    	<td class="td_second_1" style="text-align:center;" valign="middle">
					    		科目编号
					    	</td>
					    	<td class="td_three_1"  style="text-align:center;" valign="middle">
					    		科目名称
					    	</td>
				   		</tr>
        			 	<tr id="pwtr_1">
					    	<td class="td_first_1">
					    		<div class="addBtn3"></div>
					    	</td>
					    	<td class="td_second_1">
					    	    <input type="hidden" name="pzlxm" id="txt_pzlxm4" value="${guid}">
					    	    <input type="hidden" name="xzlx" id="txt_xzlx4" value="Pzbw">
					    	    <input type="hidden" name="kmbh" id="txt_kmbh4" value="">
					    		<input type="text" name="pwkm_1" class="td_input" value=""/>
					    	</td>
					    	<td class="td_three_1">
					    		<input type="text" name="pwmc_1" class="td_input yz" value=""/>
					    	</td>
				   		</tr>
        			</tbody>
				</table>
		 </div>
		 </div>
	 </div>
	 </form>
	</div>
  </div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//验证凭证类型是否重复
	$("#txt_lxmc").blur(function(){
		var lxmc = $("#txt_lxmc").val();
		var guid = $("#txt_guid").val();
		$.ajax({
			url:"${ctx}/pzlx/selectLxmc",
			data:"lxmc="+lxmc+"&guid="+guid,
			type:"post",
			dataType:"json",
			success: function(val){
				if(val==false){
					alert("该类型名称已存在");
					$("#txt_lxmc").val("");
				}
			},
			error: function(val){
				
			}
		});
	});
	
	//验证凭证字是否重复
	$("#txt_pzz").blur(function(){
		var guid = $("#txt_guid").val();
		var pzz = $("#txt_pzz").val();
		$.ajax({
			url:"${ctx}/pzlx/selectPzz",
			data:"giud="+guid+"&pzz="+pzz,
			type:"post",
			dataType:"json",
			success: function(val){
				if(val == false){
					alert("该凭证字已经存在");
					$("#txt_pzz").val("");
				}
			},
				error: function(val){
					
				}
		});
	});

	//验证方法
	var validate = $('#myform1').bootstrapValidator({fields:{       
          lxbh:{validators: {notEmpty: {message: '类型编号不能为空'}}},
          lxmc:{validators: {notEmpty: {message: '类型名称不能为空'}}},
          pzz:{validators: {notEmpty: {message: '凭证字不能为空'}}} 
          }
	      });
	//必有科目修改
	$("")
	//保存按钮
	$("#btn_save").click(function(e){
		$("#tr_1").remove();
		//alert(doSave1);
		doSave1(validate,"myform1","${ctx}/pzlx/addPzlx",function(val){

		});
	});	
	function doSave1(_validate, _formId, _url, _success, _fail){
	//	checkAutoInput();
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
// 		if(!tag){
// 			valid = false;
// 		}
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
	function next(){
		$(".myformJf tr:last").remove();
		$(".myformDf tr:last").remove();
		$(".myformPzby tr:last").remove();
		$(".myformPzbw tr:last").remove();
		//alert(1);
		var mytable = $(".myform");
		var aryId = [];
		$.each(mytable,function(i,v){
			var $this = $(this);
			var id = $this.attr("id");
			var xz = $this.attr("xz")
			console.log("id===="+id);			
		var json = $("#"+id+"").serializeObject1("pzlxm","kmbh");  //json对象		
		var jsonStr = JSON.stringify(json);  //json字符串
		console.log("jsonStr==="+jsonStr);
		$.ajax({
			        url:"${ctx}/pzlx/addPzkmdzb?xz="+xz,
		   			type:"post",
		   			data:"json="+jsonStr,
		   			success:function(data){
					alert("保存成功！");  
					window.location.href = "${ctx}/pzlx/gopzlxListPage";
		   			}
		
				}); 
		});
	}
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
	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/kjhs/pzlx/pzlx_list.jsp";
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
$(function(){
	//借方必有贷款增加
	$(document).on("click","[class*=addBtn0]",function(){
		var tr = $(this).parents("tr").attr("id");
		select_commonWin("${ctx}/pzlx/window?controlId="+tr+"&type=jb","借方必有贷款科目信息","920","630");
	});
	//贷方必有贷款增加
	$(document).on("click","[class*=addBtn1]",function(){
		var tr = $(this).parents("tr").attr("id");
		select_commonWin("${ctx}/pzlx/window?controlId="+tr+"&type=db","贷方必有贷款科目信息","920","630");
	});
	//凭证必有贷款增加
	$(document).on("click","[class*=addBtn2]",function(){
		var tr = $(this).parents("tr").attr("id");
		select_commonWin("${ctx}/pzlx/window?type=pb&controlId="+tr+"&type=pb","凭证必有贷款科目信息","920","630");
	});
	//凭证必无贷款增加
	$(document).on("click","[class*=addBtn3]",function(){
		var tr = $(this).parents("tr").attr("id");
		select_commonWin("${ctx}/pzlx/window?type=pw&controlId="+tr,"凭证必无贷款科目信息","920","630");
	});
	//删除
	$(document).on("click","[class*=delBtn]",function(){
		$(this).parents("tr").remove();
	});
});

var num = 2;
var num1 = 2;
var num2 = 2;
var num3 = 2;
//借方必有
 function addJb(kmxx,tr,guid){
	console.log(kmxx+"-------"+tr+"----"+guid);
	if(kmxx.length==0){
		return;
	}
	var pzlxm = $("#txt_pzlxm1").val();
	var kmbh = "";
	var kmmc = "";
	$.each(kmxx,function(i,v){
		kmbh = v.substring(0,v.indexOf("="));
		kmmc = v.substring(v.indexOf("=")+1);
		var parentTr = $("[id='"+tr+"']").clone();
		var a  = $(".yz");
		var m=0;
	    $.each(a,function(){
	    	var b = $(this).val();	
	    
        if(kmmc==b){
        m++;
         }
	    });
	    if(m>0){
	    	alert(kmmc+"已存在");
	    }else{
		parentTr.find("input:not(#txt_pzlxm1,#txt_xzlx1)").val("");
		parentTr.find(".addBtn0").attr("class","delBtn");
		parentTr.attr("id","jbtr_"+num);
		parentTr.find("[name^=jbkm_]").attr({"name":"jbkm_"+num});
		parentTr.find("[name^=kmbh]").val(guid[i]);
		parentTr.find("[name^=jbkm_]").val(kmbh);
		parentTr.find("[name^=jbmc_]").val(kmmc);
		parentTr.find("[name^=jbmc_]").attr({"name":"jbmc_"+num});
		$("[id='"+tr+"']").before(parentTr);
		num++;
	    }
	});
} 
//贷方必有
function addDb(kmxx,tr,guid){
	if(kmxx.length==0){
		return;
	}
	var pzlxm = $("#txt_pzlxm2").val();
	var kmbh = "";
	var kmmc = "";
	$.each(kmxx,function(i,v){
		kmbh = v.substring(0,v.indexOf("="));
		kmmc = v.substring(v.indexOf("=")+1);
		var parentTr = $("[id='"+tr+"']").clone();
		var a  = $(".yz");
		var m=0;
	    $.each(a,function(){
	    	var b = $(this).val();	
	    
        if(kmmc==b){
        m++;
         }
	    });
	    if(m>0){
	    	alert(kmmc+"已存在");
	    }else{
		parentTr.find("input:not(#txt_pzlxm2,#txt_xzlx2)").val("");
		parentTr.find(".addBtn1").attr("class","delBtn");
		parentTr.attr("id","dbtr_"+num1);
		parentTr.find("[name^=dbkm_]").attr({"name":"dbkm_"+num1});
		parentTr.find("[name^=kmbh]").val(guid[i]);
		parentTr.find("[name^=dbkm_]").val(kmbh);
		parentTr.find("[name^=dbmc_]").val(kmmc);
		parentTr.find("[name^=dbmc_]").attr({"name":"dbmc_"+num1});
		$("[id='"+tr+"']").before(parentTr);
		num1++;
	    }
	});
}
//凭证必有
function addPb(kmxx,tr,guid){
	if(kmxx.length==0){
		return;
	}
	var pzlxm = $("#txt_pzlxm3").val();
	var kmbh = "";
	var kmmc = "";
	$.each(kmxx,function(i,v){
		kmbh = v.substring(0,v.indexOf("="));
		kmmc = v.substring(v.indexOf("=")+1);
		var parentTr = $("[id='"+tr+"']").clone();
		var a  = $(".yz");
		var m=0;
	    $.each(a,function(){
	    	var b = $(this).val();	
	    
        if(kmmc==b){
        m++;
         }
	    });
	    if(m>0){
	    	alert(kmmc+"已存在");
	    }else{
		parentTr.find("input:not(#txt_pzlxm3,#txt_xzlx3)").val("");
		parentTr.find(".addBtn2").attr("class","delBtn");
		parentTr.attr("id","pbtr_"+num2);
		parentTr.find("[name^=pbkm_]").attr({"name":"pbkm_"+num2});
		parentTr.find("[name^=kmbh]").val(guid[i]);
		parentTr.find("[name^=pbkm_]").val(kmbh);
		parentTr.find("[name^=pbmc_]").val(kmmc);
		parentTr.find("[name^=pbmc_]").attr({"name":"pbmc_"+num2});
		$("[id='"+tr+"']").before(parentTr);
		num2++;
	    }
	});
}
//凭证必无
function addPw(kmxx,tr,guid){
	console.log("guid==="+guid);
	console.log("kmxx==="+kmxx);
	if(kmxx.length==0){
		return;
	}
	var pzlxm = $("#txt_pzlxm4").val();
	var kmbh = "";
	var kmmc = "";
	$.each(kmxx,function(i,v){
		console.log("guid=="+guid[i]);
		kmbh = v.substring(0,v.indexOf("="));
		kmmc = v.substring(v.indexOf("=")+1);
		var parentTr = $("[id='"+tr+"']").clone();
		var a  = $(".yz");
		var m=0;
	    $.each(a,function(){
	    	var b = $(this).val();	
	    
        if(kmmc==b){
        m++;
         }
	    });
	    if(m>0){
	    	alert(kmmc+"已存在");
	    }else{
		parentTr.find("input:not(#txt_pzlxm4,#txt_xzlx4)").val("");
		parentTr.find(".addBtn3").attr("class","delBtn");
		parentTr.attr("id","pwtr_"+num3);
		parentTr.find("[name^=pwkm_]").attr({"name":"pwkm_"+num3});
		parentTr.find("[name^=kmbh]").val(guid[i]);
		parentTr.find("[name^=pwkm_]").val(kmbh);
		parentTr.find("[name^=pwmc_]").val(kmmc);
		parentTr.find("[name^=pwmc_]").attr({"name":"pwmc_"+num3});
		$("[id='"+tr+"']").before(parentTr);
		num3++;
	    }
	});
}
</script>
</body>
</html>