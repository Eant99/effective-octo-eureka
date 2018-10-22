<%@page import="com.googosoft.constant.Constant"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>校外人员信息采集</title>
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
/* 	    margin-left:5px; */
	    margin-top:2px;
	}
	.addBtn:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
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
	 
	    margin-top:2px;
	}
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
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
	<input type="hidden" name="guid"  value="${guid}">
	<input type="hidden" name="czr"  value="${xwryOb.czr}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>校外人员信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>编辑校外人员信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<form id="myform2">
			
			<input type="hidden" name="guid" id="guid" value="${xwryOb.guid}">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>人员编号</span>
							<c:if test="${operateType == 'U'}">
							<input type="text" id="txt_bzbh" class="form-control input-radius" readonly="readonly" name="bzbh"  value="${xwryOb.xh}"/>
							</c:if>
							<c:if test="${operateType != 'U'}">
							<input type="text" id="txt_XH" class="form-control input-radius" readonly name=XH  value="系统自动生成"/>
							</c:if>
						</div>
                    </div>
                    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>姓&ensp;&ensp;&ensp;&ensp;名</span>
                            <input type="text" id="txt_XM" class="form-control input-radius" name="XM" value="${xwryOb.xm}"/>
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>性&ensp;&ensp;&ensp;&ensp;别</span>
							<c:if test="${operateType == 'U'}">
	                        <select id="drp_XBM" class="form-control input-radius " name="XBM">
                              	 	<option value="">请选择</option>
								<option value="1" <c:if test="${xwryOb.XBM == 1}">selected</c:if>>男</option>
								<option value="2" <c:if test="${xwryOb.XBM == 2}">selected</c:if>>女</option>
								<option value="3" <c:if test="${xwryOb.XBM == 3}">selected</c:if>>未知</option>
                             </select>
                             </c:if>
                             <c:if test="${operateType != 'U'}">
	                        <select id="drp_XBM" class="form-control input-radius " name="XBM">
                              	 	<c:forEach var="xbList" items="${xbList}"> 
                                  		<option value="${xbList.DM}">${xbList.MC}</option>
								</c:forEach>
                             </select>
                             </c:if>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">出生年月日</span>
							<input type="text" id="txt_CSRQ" class="form-control date" name="CSRQ" value="<fmt:formatDate value="${xwryOb.csrq}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="出生年月日">
					    	<span class='input-group-addon'>
								<i class="glyphicon glyphicon-calendar"></i>
					        </span>
				    	</div>   
						
					</div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">出生地</span>
							<c:if test="${operateType == 'U'}">
							<select id="drp_CSDM" class="form-control input-radius " name="CSDM">
                               <c:forEach var="item" items="${dqlist}">
                           			<option value="${item.provinceid}" <c:if test="${xwryOb.CSDM == item.provinceid }">selected</c:if>>${item.province}</option>
                            	</c:forEach>
                             </select>
<%-- 							<input type="text" id="txt_CSDM"  class="form-control input-radius" name="CSDM" value="${xwryOb.CSDM}"/> --%>
                             </c:if>
							<c:if test="${operateType != 'U'}">
	                        <select id="drp_CSDM" class="form-control input-radius " name="CSDM">
                              <option value="">请选择</option>
                               <c:forEach var="item" items="${dqlist}">
                           			<option value="${item.provinceid}" >${item.province}</option>
                            	</c:forEach>
                             </select>
                             </c:if>         
						</div>
				    </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">籍贯</span>
                            <input type="text" id="txt_JG" class="form-control" name="JG" value="${xwryOb.jg}">        
						</div>
				    </div>
				</div>
				<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>证件类型</span>
							<c:if test="${operateType == 'U'}">
                            <select id="drp_SFZJLXM" class="form-control input-radius " name="SFZJLXM">
                            <option value="01" <c:if test="${xwryOb.sfzjlxm == '01'}">selected</c:if>>身份证</option>
                            <option value="02" <c:if test="${xwryOb.sfzjlxm == '02'}">selected</c:if>>学生证</option>
                            <option value="03" <c:if test="${xwryOb.sfzjlxm == '03'}">selected</c:if>>军官证</option>
                            <option value="04" <c:if test="${xwryOb.sfzjlxm == '04'}">selected</c:if>>护照</option>
                            <option value="05" <c:if test="${xwryOb.sfzjlxm == '05'}">selected</c:if>>费用类别</option>
                             </select>
                             </c:if>
							<c:if test="${operateType != 'U'}">
                            <select id="drp_SFZJLXM" class="form-control input-radius " name="SFZJLXM">
                              	 	<c:forEach var="zjlxList" items="${zjlxList}"> 
                                  		<option value="${zjlxList.DM}">${zjlxList.MC}</option>
								</c:forEach>
                             </select>
                             </c:if>
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>证件号</span>
	                       <input type="text" id="txt_SFZH" class="form-control input-radius2 text-right int" name="SFZH" value="${xwryOb.sfzh}"/>	
						</div>          
					</div>
	                <div class="col-md-4 col-xs-4">
							<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>民&ensp;&ensp;&ensp;&ensp;族</span>
							<c:if test="${operateType == 'U'}">
								<select id="drp_MZM" class="form-control input-radius" name="MZM">
	                              	 	<c:forEach var="mzlist" items="${mzlist}"> 
	                                  		<option value="${mzlist.DM}"<c:if test="${xwryOb.MZM == mzlist.MC}">selected</c:if>>${mzlist.MC}</option>
									</c:forEach>
	                              </select>
<%-- 	                        <input type="text" id="txt_MZM" class="form-control input-radius" name="MZM" value="${xwryOb.MZM}"/>    --%>
	                        </c:if>
							<c:if test="${operateType != 'U'}">
	                              <select id="drp_MZM" class="form-control input-radius " name="MZM">
	                              	 	<c:forEach var="mzlist" items="${mzlist}"> 
	                                  		<option value="${mzlist.DM}">${mzlist.MC}</option>
									</c:forEach>
	                              </select>
	                        </c:if>   
							</div>
	                 </div>
				</div>
				<div class="row">
					<div class="col-md-4 col-xs-4">
							<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>国&ensp;&ensp;&ensp;&ensp;籍</span>
								  <c:if test="${operateType == 'U'}">
								  <select id="drp_GJDQM" class="form-control input-radius " name="GJDQM">
	                              	 	<c:forEach var="gjList" items="${gjList}"> 
	                                  		<option value="${gjList.DM}"<c:if test="${xwryOb.GJDQM == gjList.MC}">selected</c:if>>${gjList.MC}</option>
									</c:forEach>
	                              </select>
	                             </c:if>
	                              <c:if test="${operateType != 'U'}">
	                              <select id="drp_GJDQM" class="form-control input-radius " name="GJDQM">
	                              		<option value="">请选择</option>
	                              	 	<c:forEach var="gjList" items="${gjList}"> 
	                                  		<option value="${gjList.DM}">${gjList.MC}</option>
									</c:forEach>
	                              </select>
	                              </c:if>
							</div>
	                 </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>手机号</span>
                            <input type="text" id="txt_LXFS" class="form-control" name="LXFS" value="${xwryOb.lxfs}">          
						</div>
				    </div>
				    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">E-mail</span>
                            <input type="text" id="txt_YX" class="form-control" name="YX" value="${xwryOb.yx}">          
						</div>
				    </div>   
				</div> 
				<div class="row">
					<div class="col-md-4 col-xs-4">
					    <div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>开户行账号</span>
							  <input type="text" id="txt_KHYHH" class="form-control input-radius" name="KHYHH" value="${xwryOb.KHYHH}"/>	
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>银行名称</span>
							  <input type="text" id="txt_YHMC" class="form-control input-radius" name="YHMC" value="${xwryOb.yhmc}"/>	
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>联行号</span>
							  <input type="text" id="txt_LHH" class="form-control input-radius" name="LHH" value="${xwryOb.lhh}"/>	
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>银行代码</span>
							  <input type="text" id="txt_YHDM" class="form-control input-radius" name="YHDM" value="${xwryOb.yhdm}"/>	
						</div>
					</div>
				</div>
				<div class="row">
				    <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
	                        <textarea id="txt_bz"  class="form-control" name="BZ" style=" height: 50px; width: 100%;">${xwryOb.bz}</textarea>
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
	$("#txt_bgdh").blur(function(){
	 	var bgdh = $("#txt_bgdh").val().length;
	 	if(bgdh==8 || bgdh==12 || bgdh==11 ||bgdh=="" ){
	 	
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
	$("[name=yhlhh1]").blur(function(){
	 	var len = $("#txt_yhlhh1").val().length;
	 	if(len> 12){
	 		alert("人民银行联行号过长,12位数");
	 	}
	});
  	//验证银行号是否重复
  	$(document).on("blur","[name=khyhzh1]",function(){
  		var khyhzh = $(this).val();
  		$(this).addClass("zh");
  		var khyhzhcd = khyhzh.length;
  		if(khyhzhcd==16||khyhzhcd==19||khyhzhcd==""||khyhzhcd==22){
  			
  		}else{
  			$(this).val("");
  			alert("请输入正确的银行卡号或者存折！");
  		}
  		var a  = $(".yhh:not(.zh)");
	    $.each(a,function(){
	    var b = $(this).val();
         if(khyhzh==b){
        	 alert("该银行账号已存在");
        	 $(".zh").val("");     
          }
	    });
	    $(this).removeClass("zh");
  	});
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
	//新增按钮
	var num = 2;
	$(document).on("click","[class*=addBtn]",function(){
		//var wlbh = $("#txt_wlbh").val()
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find("[a='a']").val("");
		$parentTr.find(":input").removeClass("border");
		//$parentTr.find("[id=txt_wlbh]").val(wlbh);
		$parentTr.attr("id","tr_"+num);
		num++;
		$(this).parents("tr").after($parentTr);
		
	});

	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();
	});
	//验证方法
	var validate = $("#myform2").bootstrapValidator({fields:{
		XM:{validators:{notEmpty: {message: '姓名不能为空'}}},
        SFZH:{validators: {notEmpty: {message: '身份证号不能为空'},stringLength:{message:'身份证号过长',max:'18'}}},
        LXFS:{validators: {notEmpty: {message: '手机号不能为空'},stringLength:{message:'手机号过长',max:'11'}}},
        KHYHH:{validators: {notEmpty: {message: '开户行账号不能为空'},stringLength:{message:'银行账号过长',max:'12'}}},
        YHMC:{validators: {notEmpty: {message: '银行名称不能为空'}}},
        LHH:{validators: {notEmpty: {message: '联行号称不能为空'},stringLength:{message:'联行号过长',max:'12'}}},
        YHDM:{validators: {notEmpty: {message: '银行代码不能为空'}}}
   	}
   });
	
	//保存按钮
	$("#btn_save").click(function(e){
		var operateType =  $("[name='operateType']").val();
		var bz ="${bz}";//保存时的页面跳转
		if(operateType == 'C'){
			if(bz == "xzsblr"){
				doSave(validate,"myform2","${ctx}/xwryxxcj/doSave?operateType=C",function(val){
					if(val.success){
					alert(val.msg);
					window.location.href = "${ctx}/webView/srgl/xwryxxcj/xwryxxcj_select_list.jsp";//跳转到收入申报录入页面
					}
				});
			}else{
				doSave(validate,"myform2","${ctx}/xwryxxcj/doSave?operateType=C",function(val){
					if(val.success){
					alert(val.msg);
					window.location.href = "${ctx}/webView/srgl/xwryxxcj/xwryxxcj_list.jsp";
					}
				});
			}
		}else{
			doSave(validate,"myform2","${ctx}/xwryxxcj/doSave?operateType=U",function(val){
				if(val.success){
				alert(val.msg);
				window.location.href = "${ctx}/webView/srgl/xwryxxcj/xwryxxcj_list.jsp";
				}
			});	
		}
	});	

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
	//验证长度
	$(document).on("change","[name='khyh1']",function(){
		var val = $(this).val();
		if(val.length > 20){
			alert("长度不能操作20");
			$(this).val("");
		}
	});
	//返回按钮
	$("#btn_back").click(function(){
		var bz = "${bz}";
		if(bz == "xzsblr"){
			window.location.href = "${ctx}/webView/srgl/xwryxxcj/xwryxxcj_select_list.jsp";//跳转到收入申报录入页面
		}else{
			window.location.href = "${ctx}/webView/srgl/xwryxxcj/xwryxxcj_list.jsp";
		}
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}";
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016";
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016";
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
	var json = $('#myform1').serializeObject1("wlbh1","yhlhh1");  //json对象				
	var jsonStr = JSON.stringify(json);  //json字符串
	$.ajax({
		        url:"${ctx}/wldwsz/addWldwmxb",
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				alert("保存成功！"); 
			    window.location.href="${ctx}/webView/kjhs/wldwsz/wldwsz_list.jsp";
			    
	   			}
			}); 
}
</script>
</body>
</html>