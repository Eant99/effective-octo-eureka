<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>薪资上报录入增加</title>
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
	
	.number,.sz{
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
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
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
	#tbody input{
		border:none;
		width:100%;
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
	.text-green{
		color:green!important;
	}
	tr{
background-color: white !important;
}
tbody td{
	   padding:4px !important;
	}
	.hs{
/* 	  background-color:#cccccc; */
	}
</style>
</head>
<body>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println(path+"@"+basePath);
%>
		<form id="form2" class="myform2" action="">
			<div class="box" id="operate">
				<div class="box-panel">		
		        <hr class="hr-normal">
				<div class="container-fluid box-content">
					<table id="mydatatables" class="table table-striped table-bordered">
			        	<thead id="thead">
					        <tr>
					        	<th style="width:51px;">操作</th>
					        	<th style="width:300px;" id="xh"><span class="required" style="color:red">*</span>项目编号</th>
					            <th style="width:300px;" id="zfje">项目名称</th>
					        	<th style="width:300px;" id="zfje"><span class="required" style="color:red">*</span>项目类型</th>
					            <th style="width:300px;" id="xh">人员编号</th>
					            <th style="width:300px;" id="zfje">人员姓名</th>
					            <th style="width:200px;" id="zfje">部门编号</th>
					            <th style="width:300px;" id="zfje">部门名称</th>
					            <th style="width:200px;" id="zfje"><span class="required" style="color:red">*</span>学校科研基金（元）</th>
					            <th style="width:200px;" id="bqks"><span class="required" style="color:red">*</span>学院科研基金（元）</th>
					        </tr>
						</thead>
					    <tbody id="tbody" class="tbody">
	                     <tr id="tr_1">
				    		<td class="btn_td">
	                               <div class="addBtn add"></div>
				    		</td>
				    		<td class="xhh hide">1</td>
				    		<td>
							    <div class="input-group">
									<input type="text" id="txt_xmbh_1" a="a"  class=" null window input-radius" name="xmbh" value="">
									<input type="hidden" id="txt_xmguid_1" a="a" name="xmguid" value="">
									<span class="input-group-btn">
						    			<button type="button" id="btn_xmbh" onclick="xmxx(this)" class="btn btn-link btn-custom">选择</button>
					    			</span>
								</div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_xmmc_1" name="xmmc" value="" readonly>
				    		</td>
				    		<td>
				    			<select name="xmlx" id="txt_xmlx_1" class="bk input-radius">
				    				<option value="1" selected>横向</option>
				    				<option value="0">纵向</option>
				    			</select>
				    		</td>
				    		<td>
							    <div class="input-group">
									<input type="text" id="txt_fzr_1" a="a"  class=" null  input-radius" name="fzr" value="" readonly>
								</div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_xm_1" a="a" class=" null  input-radius" name="xm" value="" readonly>
				    		</td>
				    		<td>
							    <div class="input-group">
									<input type="text" id="txt_bm_1" a="a"  class=" null  input-radius" name="bm" value="" readonly>
								</div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_bmmc_1" name="bmmc" value="" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_xxjj_1" a="a" class=" null  input-radius sign-number" name="xxjj" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_xyjj_1" a="a" class=" null  input-radius sign-number" name="xyjj" value="">
				    		</td>
				    	</tr>
					    </tbody>
					</table>
				</div>
			</div>
		</div>
		<div class='page-bottom clearfix'>
	        <div class="pull-right">
				<button type='button' class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;"><i class="fa icon-save"></i>保存</button>
				<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">取消</button>
	        </div>
	    </div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<script type="text/javascript">
var target = "${ctx}/kjhs/pzxx/pzlr";
var tag = true;
//明细js操作
$(function(){
	//新增按钮
	$(document).on("click","[class*=addBtn]",function(){
		var num = $("#mydatatables").find("tr").last().find("td").eq(1).text()*1+1*1;
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		$parentTr.find(":input").removeClass("border");
		$parentTr.attr("id","tr_"+num);
		$parentTr.find(".xhh").text(num);
		$parentTr.find("[name='xmbh']").attr("id","txt_xmbh_"+num);  
		$parentTr.find("[name='xmguid']").attr("id","txt_xmguid_"+num);
		$parentTr.find("[name='xmmc']").attr("id","txt_xmmc_"+num);
		$parentTr.find("[name='xmlx']").attr("id","txt_xmlx_"+num);
		$parentTr.find("[name='fzr']").attr("id","txt_fzr_"+num);
		$parentTr.find("[name='fzrguid']").attr("id","fzrguid_"+num);
		$parentTr.find("[name='xm']").attr("id","txt_xm_"+num);
		$parentTr.find("[name='bm']").attr("id","txt_bm_"+num);
		$parentTr.find("[name='bmmc']").attr("id","txt_bmmc_"+num);
		$parentTr.find("[name='xxjj']").attr("id","txt_xxjj_"+num);
		$parentTr.find("[name='xyjj']").attr("id","txt_xyjj_"+num);

		num++;
		$(this).parents("tr").after($parentTr);
	});
	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();
		
	});
});
$(function(){
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
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
	//取消
	$("#btn_cancelw").click(function(){
		//getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
	//保存按钮
	$("#btn_save").click(function(e){
		var xmbh = [];	//项目编号
		var xmlx = []; //项目类型
		var bool = false;
		$("input[id^=txt_xmbh]").each(function(j,item){
			if(item.value == ""){
				bool = true;
				return;
			}else{
				xmbh.push(item.value);
			}
		});
		$("input[id^=txt_xmlx]").each(function(j,item){
			if(item.value == ""){
				bool = true;
				return;
			}else{
				xmlx.push(item.value);
			}	
		});
		$("input[id^=txt_xxjj]").each(function(j,item){
			if(item.value == ""){
				bool = true;
				return;
			}
		});
		$("input[id^=txt_xyjj]").each(function(j,item){
			if(item.value == ""){
				bool = true;
				return;
			}	
		});
	    if(bool){
	    	alert("尚有必填项未进行填写！");
	    	return;
	    }else{
	    	//判断 是否 已 添加这个项目
	    	var xmguid = [];
	    	$("input[id^=txt_xmguid]").each(function(j,item){
                if(item.value != "")	    		
	    			xmguid.push(item.value);
	    	});
	    	$.ajax({
	   			url:"${ctx}/kjhs/pzxx/pzlr/CheckKyglxm",
	   			data:"xmguid="+xmguid.join("','"),   //5B9C567BB9FC40B192F8A399FF072E98','FCB7C1872B4D423E9FB14012F4937D6F
	   			type:"post",
	   			dataType:"json",
	   			async:"false",
	   			success:function(val){
	   				if(val.success){
	   					alert("含有已添加过的项目，请检查修改或删除后重新添加");
	   				}else{
		   				var json = $('#form2').serializeObject1("xmbh","xyjj");  //json对象	
		   		    	var jsonStr = JSON.stringify(json);  //json字符串
		   		    	$.ajax({
		   			        url:target+"/doGlfSave",
		   		   			type:"post",
		   		   			data:"json="+jsonStr,
		   		   			success:function(data){
		   					  alert("保存成功！");  
		   					  var winId = getTopFrame().layer.getFrameIndex(window.name);
		   					   getIframWindow("${param.pname}").table.ajax.reload();
		   				       close(winId);
		   		   			}
		   				}); 
	   				}
	   			}//success
	   		});//ajax
	    }//else
	});	
});
	//选择项目
	 function xmxx(bz){
		var num = $(bz).parents("tr").find(".xhh").text();
		var controlId = "txt_xmbh_"+num;
		var controlIdGuid = "txt_xmguid_"+num;
		var controlId2 = "txt_xmmc_"+num;
		var controlId3 = "txt_fzr_"+num;
		var controlId4 = "txt_xm_"+num;
		var controlId5 = "txt_bm_"+num;
		var controlId6 = "txt_bmmc_"+num;
		select_commonWin("${ctx}/webView/ysgl/xmsz/xmxx/xmxx_listWindow.jsp?mkbh=040101&controlId="+controlId+"&controlIdGuid="+controlIdGuid+"&controlId2="+controlId2+"&controlId3="+controlId3+"&controlId4="+controlId4+"&controlId5="+controlId5+"&controlId6="+controlId6,"项目信息","920","630");
	} 
</script>
</html>