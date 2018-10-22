<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
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
	<input type="hidden" name="guid"  value="${jsxx.guid}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>评价模型设置</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>评价模型设置</div>
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
							<span class="input-group-addon"><span class="required">*</span>模型编号</span>
							<input type="text" id="txt_mxbh" name="mxbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="系统自动生成" readonly/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>模型名称</span>
                            <input type="text" id="txt_mxmc" class="form-control input-radius" name="mxmc" value="${pzmx.mxmc}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">设置人</span>
						 <input type="text" id="txt_szr" class="form-control input-radius" name="szr" value="${pzmx.szr}"/>	
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">设置时间</span>
							 <input type="text" id="txt_szsj" class="form-control input-radius" name="szsj" value="${pzmx.szsj}"/>	
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="min-width: 150px!important;">是否启用</span>
							<div class="switch">
							   <div class="onoffswitch">
									<input type="checkbox" name="sfqy" id="sfqy" class="onoffswitch-checkbox" value="0">
									<label class="onoffswitch-label" for="sfqy"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
									</label>
								</div>	
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
					    <div class="input-group">
						    <span class="input-group-addon">备注</span>
                          	<textarea id="txt_bz"  class="form-control" name="bz" style="height: 60px;"></textarea>
					    </div>
					</div>
				</div>
			 </div>
		   </form>
		</div><!-- //boxpanel -->
	
		
		<div class="box-panel" id="operate">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>绩效指标</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
	        </div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				 <form id="myform1">
					 <table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
			        	 <thead id="thead">
					        <tr>
					        	<th style="text-align:center;">操作</th>
					            <th style="text-align:center;"><span class="required" style="color:red ;">*</span>名称</th>
					            <th style="text-align:center;"><span class="required" style="color:red ;">*</span>权重</th>
					            <th style="text-align:center;"><span class="required" style="color:red ;">*</span>是否启用</th>
					            <th style="text-align:center;"><span class="required" style="color:red ;">*</span>说明</th>
					        </tr>
						</thead>
					    <tbody id="tbody">
					    	<tr id="tr_1">
					    		<td class="btn_td">
					    			<div class="addBtn" ></div>
					    		</td>				    		
					    		<td>
					    			  <input type="text" id="txt_jxmc" style="background-color: white !important;padding:4px;" class=" input-radius  null" name="jxmc" >
					    		</td>
					    		<td>
						    	      <input type="text" id="txt_jxqz" style="background-color: white !important;padding:4px;" class=" input-radius  null" name="jxqz" >
					    		</td>
					    		<td ><input type="radio" style="width:40px;"  id="txt_jxqy1" name="jxqy" value="1" />是 
									<input type="radio" style="width:40px;" id="txt_jxqy2" name="jxqy" value="0" checked/>否
					    		</td>
					    		<td>
					    			  <input type="text" id="txt_jxsm" style="background-color: white !important;padding:4px;" class=" input-radius  null" name="jxsm" >
					    		</td>
					    	</tr>
					    </tbody>
				   </table>

				 </form>
			</div>
		</div>	
	</div>	
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">

$(function(){

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
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find("[a='a']").val("");
		$parentTr.find(":input").removeClass("border");
		$parentTr.attr("id","tr_"+num);
		num++;
		$(this).parents("tr").after($parentTr);
	});

	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();

	});
	//验证方法
	var validate = $('#myform2').bootstrapValidator({
		fields:{
          mxmc:{validators: {notEmpty: {message: '模型名称不能为空'}}},
        }
	 });
	
	//保存按钮
	$("#btn_save").click(function(e){
		doSave1(validate,"myform2","${ctx}/pjmxsz/addPjmxsz",function(val){

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
// 		var url = "${ctx}/pjmxsz/checkExist";
// 		var wlbh = $("#txt_wlbh").val();
// 		if(checkExist(url,"wlbh="+wlbh)){
// 			alert("该编号已存在！");
// 			return;
// 		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){	
					//next();
					alert("保存成功！"); 
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


	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/pjmxsz/goListPage";
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
function next(){
	//alert(1);
	var json = $('#myform1').serializeObject1("wlbh1","khyhzh1");  //json对象				
	var jsonStr = JSON.stringify(json);  //json字符串
	$.ajax({
		        url:"${ctx}/pjmxsz/addPjmx",
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				alert("保存成功！"); 
			    window.location.href="${ctx}/pjmxsz/goListPage";
			    
	   			}
			}); 
}
</script>
</body>
</html>