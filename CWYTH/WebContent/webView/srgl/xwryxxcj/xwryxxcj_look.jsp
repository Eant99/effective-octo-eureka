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
<title>查看校外人员信息采集</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
.btn_td{
		width:10px!important;
		height:6mm!important;
		text-align: center;
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
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
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
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>查看校外人员信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>查看校外人员信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<form id="myform2">
			<input type="hidden" name="guid" id="guid" value="${guid}">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>人员编号</span>
							<input type="text" id="txt_dwmc" class="form-control input-radius" name="dwmc" value="${wldwsz.dwmc}"/>						</div>
                    </div>
                    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>姓&ensp;&ensp;&ensp;&ensp;名</span>
                            <input type="text" id="txt_dwmc" class="form-control input-radius" name="dwmc" value="${wldwsz.dwmc}"/>
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>性&ensp;&ensp;&ensp;&ensp;别</span>
	                        <select id="drp_dwbb" class="form-control input-radius " name="">
                              	 	<c:forEach var="xbList" items="${xbList}"> 
                                  		<option value="${xbList.DM}">${xbList.MC}</option>
								</c:forEach>
                             </select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>证件类型</span>
                            <select id="drp_dwbb" class="form-control input-radius " name="">
                              	 	<c:forEach var="zjlxList" items="${zjlxList}"> 
                                  		<option value="${zjlxList.DM}">${zjlxList.MC}</option>
								</c:forEach>
                             </select>
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>证件号</span>
	                       <input type="text" id="txt_dwdz" class="form-control input-radius" name="dwdz" value="${wldwsz.dwdz}"/>	
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>银行账号</span>
							  <input type="text" id="txt_lxr" class="form-control input-radius" name="lxr" value="${wldwsz.lxr}"/>	
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 col-xs-4">
							<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>国&ensp;&ensp;&ensp;&ensp;籍</span>
	                              <select id="drp_dwbb" class="form-control input-radius " name="">
	                              	 	<c:forEach var="gjList" items="${gjList}"> 
	                                  		<option value="${gjList.DM}">${gjList.MC}</option>
									</c:forEach>
	                              </select>
							</div>
	                 </div>
	                 <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>职业代码</span>
                            <input type="text" id="txt_cz" class="form-control" name="cz" value="${wldwsz.cz}">          
						</div>
				    </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>工作单位</span>
                            <input type="text" id="txt_cz" class="form-control" name="cz" value="${wldwsz.cz}">          
						</div>
				    </div>
				</div>
				<div class="row">
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>手机号</span>
                            <input type="text" id="txt_cz" class="form-control" name="cz" value="${wldwsz.cz}">          
						</div>
				    </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">E-mail</span>
                            <input type="text" id="txt_cz" class="form-control" name="cz" value="${wldwsz.cz}">          
						</div>
				    </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">出生年月日</span>
							<input type="text" id="txt_jsrq" class="form-control date"   name="jsrq" value="<fmt:formatDate value="${qjxx.jsrq}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="出生年月日">
					    	<span class='input-group-addon'>
								<i class="glyphicon glyphicon-calendar"></i>
					        </span>
				    	</div>          
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
						<span class="input-group-addon">是否残疾烈属孤老</span>
                           <select id="drp_dwbb" class="form-control input-radius " name="">
                               		<option value="是">是</option>
                               		<option value="否">否</option>
	                       </select>        
						</div>
				    </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
						<span class="input-group-addon">是否雇员</span>
                           <select id="drp_dwbb" class="form-control input-radius " name="">
                               		<option value="是">是</option>
                               		<option value="否">否</option>
	                       </select>        
						</div>
				    </div>
				     <div class="col-md-4 col-xs-4">
						<div class="input-group">
						<span class="input-group-addon">是否股东投资者</span>
                           <select id="drp_dwbb" class="form-control input-radius " name="">
                         		<option value="是">是</option>
                         		<option value="否">否</option>
	                       </select>        
						</div>
				    </div>
				</div>
				<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">来华时间</span>
							<input type="text" id="txt_jsrq" class="form-control date"   name="jsrq" value="<fmt:formatDate value="${qjxx.jsrq}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="来华时间">
					    	<span class='input-group-addon'>
								<i class="glyphicon glyphicon-calendar"></i>
					        </span>
				    	</div>          
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">任职期限</span>
                            <input type="text" id="txt_cz" class="form-control" name="cz" value="${wldwsz.cz}">          
						</div>
				    </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">预计离校时间</span>
							<input type="text" id="txt_jsrq" class="form-control date"   name="jsrq" value="<fmt:formatDate value="${qjxx.jsrq}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="预计离校时间">
					    	<span class='input-group-addon'>
								<i class="glyphicon glyphicon-calendar"></i>
					        </span>
				    	</div>          
					</div>
				 </div>
				 <div class="row">
				 	<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">预计离校地点</span>
                            <input type="text" id="txt_cz" class="form-control" name="cz" value="${wldwsz.cz}">          
						</div>
				    </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">支付地</span>
							<select id="drp_dwbb" class="form-control input-radius " name="">
                            <option value="境内支付">境内支付</option>
                            <option value="境外支付">境外支付</option>
                            </select>        
						</div>
				    </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">所属地区</span>
                            <input type="text" id="txt_cz" class="form-control" name="cz" value="${wldwsz.cz}">          
						</div>
				    </div>
				 </div>
				<div class="row">
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">开户行</span>
                            <input type="text" id="txt_cz" class="form-control" name="cz" value="${wldwsz.cz}">          
						</div>
				    </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">是否跨行</span>
							<select id="drp_dwbb" class="form-control input-radius " name="">
                            <option value="是">是</option>
                            <option value="否">否</option>
                            </select>        
						</div>
				    </div>
				</div>
				<div class="row">
				    <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
	                        <textarea id="txt_bz"  class="form-control" name="bz" style=" height: 50px; width: 100%;">${map.bz }</textarea>
						</div>
				    </div>		
				</div>
			</div>
			</form>
		</div>
	</div>
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">

$(function(){
	$(".bc").attr("disabled",true);
	$("#txt_bgdh").blur(function(){
	 	var bgdh = $("#txt_bgdh").val().length;
	 	if(bgdh==8 || bgdh==12 || bgdh==11 ||bgdh==""){
	 	
	 	}else{
	 		alert("请输入有效的办公电话");
	 		$("#txt_bgdh").val("");
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
  		if(khyhzhcd==16||khyhzhcd==19||khyhzhcd==""){
  			
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
	
	

	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		

	});
	//验证方法
	var validate = $('#myform2').bootstrapValidator({fields:{
          wlbh:{validators:{notEmpty: {message: '往来编号不能为空'},stringLength:{message:'往来编号过长',max:'200'}}},
          dwmc:{validators:{ notEmpty: {message: '单位名称不能为空'},stringLength:{message:'单位名称过长',max:'200'}}},
          dwdz:{validators: {notEmpty: {message: '单位地址不能为空'},stringLength:{message:'单位地址过长',max:'200'}}},
          dwjc:{validators:{stringLength:{message:'单位简称过长',max:'100'}}},
          dwlx:{validators: {notEmpty: {message: '单位类型不能为空'}}},
          lxr:{validators: {notEmpty: {message: '联系人不能为空'},stringLength:{message:'联系人姓名过长',max:'32'}}},
          khyh:{validators:{notEmpty:{message: '开户银行不能为空'},stringLength:{message:'开户银行名称过长',max:'200'}}},
          khyhzh:{validators:{ notEmpty:{message: '开户银行帐号不能为空'},stringLength:{message:'开户银行账号过长',max:'25'}}}
        
          }
	      });
	
	//保存按钮
	$("#btn_save").click(function(e){
		
		
		doSave1(validate,"myform2","${ctx}/wldwsz/editWldwsz",function(val){

		});
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
		console.log("wlbh+++++++++++++++++"+wlbh);		
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
		window.location.href = "${ctx}/webView/srgl/xwryxxcj/xwryxxcj_list.jsp";
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
	var json = $('#myform1').serializeObject1("guid","khyhzh1");  //json对象				
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