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
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>绩效指标信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>编辑绩效指标信息</div>
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
							<span class="input-group-addon"><span class="required">*</span>指标编号</span>
							<input type="text" id="txt_zbbh" name="zbbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="系统自动生成" readonly/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>指标名称</span>
                            <input type="text" id="txt_zbmc" class="form-control input-radius" name="zbmc" value="${wldwsz.dwmc}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>上级指标</span>
						 <input type="text" id="txt_sjzb" class="form-control input-radius" name="sjzb" value="${wldwsz.dwjc}"/>	
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>指标分值</span>
							 <input type="text" id="txt_zbfz" class="form-control input-radius" name="zbfz" value="${wldwsz.sh}"/>	
						</div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>指标权重</span>
	                       <input type="text" id="txt_zbqz" class="form-control input-radius" name="zbqz" value="${wldwsz.dwdz}"/>	
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>考核周期</span>
	                        <select id="drp_khzq" class="form-control input-radius select2" name="khzq"> 
	                                <option value="">未选择</option>
									<option value="01" <c:if test="${wldwsz.dwlx == '01'}">selected</c:if>>月度</option>
									<option value="02" <c:if test="${wldwsz.dwlx == '02'}">selected</c:if>>季度</option>	
									<option value="03" <c:if test="${wldwsz.dwlx == '03'}">selected</c:if>>年度</option>	
							</select>
						</div>
					</div>	
				</div>
				
				</div>
				</form>
			</div>
	
		
		<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>绩效指标
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
			 <form id="myform1">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
<!-- 				        <tr> -->
<!-- 				        	<th style="text-align: center;">操作</th> -->
<!-- 				            <th style="width:300px; text-align: center;"><span class="required" style="color:red;">*</span>开户银行名称</th> -->
<!-- 				            <th style="width:300px; text-align: center;"><span class="required" style="color:red;">*</span>开户银行帐号</th> -->
<!-- 				        </tr> -->
					</thead>
				    <tbody id="tbody">
				    	<!------------------------------------------以下是长期绩效指标--------------------------------------------------------------------------  -->
								<tr>
									<td id="cqjxzb" rowspan="19" style="text-align: center; vertical-align: middle;">绩效指标</td>
									<td style="text-align: center;">一级指标</td>
									<td style="text-align: center;">二级指标</td>
									<td style="text-align: center;">指标内容</td>
									<td style="text-align: center;">指标值</td>
									<td style="text-align: center;">备注</td>
								</tr>
								<tr>
<!-- 									<td id="cqjxzb1" rowspan="9" style="text-align: center;">长期绩效指标 <br><br><br><br><br><br><br><div class="addBtn2"></div> -->
<!-- 									<br><br><br><br><br><br><br><br><br><div id="cqjxzb2" ></div> -->
<!-- 									</td> -->
									<td id="xyzb" rowspan="8" style="text-align: center; vertical-align: middle;">产出指标</td>
									<td rowspan="2" style="text-align: center;">数量指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb1" value="${info.jjxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb2" value="${info.jjxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb3" value="${info.jjxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb4" value="${info.jjxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb5" value="${info.jjxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb6" value="${info.jjxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">质量指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb1" value="${info.shxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb2" value="${info.shxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb3" value="${info.shxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb4" value="${info.shxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb5" value="${info.shxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb6" value="${info.shxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">时效指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb1" value="${info.stxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb2" value="${info.stxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb3" value="${info.stxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb4" value="${info.stxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb5" value="${info.stxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb6" value="${info.stxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">成本指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb1" value="${info.kcxyxzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb2" value="${info.kcxyxzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb3" value="${info.kcxyxzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb4" value="${info.kcxyxzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb5" value="${info.kcxyxzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb6" value="${info.kcxyxzb6}">
									</td>
								</tr>
								

								</tr>
								<tr>
<!-- 									<td id="cqjxzb1" rowspan="10" style="text-align: center;vertical-align: middle;">长期绩效指标 <div class="addBtn3"></div> -->
<!-- 									<div id="cqjxzb3" ></div> -->
<!-- 									</td> -->
									<td id="xyzb" rowspan="10" style="text-align: center; vertical-align: middle;">效益指标</td>
									<td rowspan="2" style="text-align: center;">经济效益指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb1" value="${info.jjxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb2" value="${info.jjxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb3" value="${info.jjxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb4" value="${info.jjxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb5" value="${info.jjxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="jjxyzb6" value="${info.jjxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">社会效益指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb1" value="${info.shxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb2" value="${info.shxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb3" value="${info.shxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb4" value="${info.shxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb5" value="${info.shxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="shxyzb6" value="${info.shxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">生态效益指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb1" value="${info.stxyzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb2" value="${info.stxyzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb3" value="${info.stxyzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb4" value="${info.stxyzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb5" value="${info.stxyzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="stxyzb6" value="${info.stxyzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">可持续影响指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb1" value="${info.kcxyxzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb2" value="${info.kcxyxzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb3" value="${info.kcxyxzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb4" value="${info.kcxyxzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb5" value="${info.kcxyxzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb6" value="${info.kcxyxzb6}">
									</td>
								</tr>
								<tr>
									<td rowspan="2" style="text-align: center;">社会公众或服务对象满意度指标指标</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb1" value="${info.kcxyxzb1}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb2" value="${info.kcxyxzb2}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb3" value="${info.kcxyxzb3}">
									</td>
								</tr>
								<tr>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb4" value="${info.kcxyxzb4}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb5" value="${info.kcxyxzb5}">
									</td>
									<td><input type="text" id="txt_khyh1" a="a"
										class=" input-radius null" name="kcxyxzb6" value="${info.kcxyxzb6}">
									</td>
								</tr>

								</tr>
				    </tbody>
				</table>
				</form>
			</div>
		</div>	
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
	/* $(document).on("blur","#txt_wlbh",function(){
		var wlbh = $(this).val();
		alert(wlbh);
		$("txt_wldw1").val(wlbh);
		
	}); */
// 	$("#txt_wlbh").blur(function(){
// 		var wlbh = $(this).val();
		
// 		$("#txt_wlbh1").val(wlbh);
		
// 	});
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
	var validate = $('#myform2').bootstrapValidator({fields:{
         // wlbh:{validators:{notEmpty: {message: '往来编号不能为空'},stringLength:{message:'往来编号过长',max:'4'}}},
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
		
		
// 		doSave1(validate,"myform2","${ctx}/wldwsz/addWldwsz",function(val){

// 		});
      alert("保存成功！")
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
		if(checkExist(url,"wlbh="+wlbh)){
			alert("该编号已存在！");
			return;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){	
					next();
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
	//验证长度
	$(document).on("change","[name='khyh1']",function(){
		var val = $(this).val();
		if(val.length > 20){
			alert("长度不能操作20");
			$(this).val("");
		}
	})
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/jxzbsd/goListPage";
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
// 	$("#txt_bgdh").blur(function(){
// 	    var isMob="/^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/";
// 	    var value=$(this).val();
// 	    if(isMob.test(value)){
// 	    	console.log("11111111");
// 	        return true;
// 	    }
// 	    else{
// 	    	console.log("2222222");

// 	        return false;
// 	    }
		
// 	});
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
	var json = $('#myform1').serializeObject1("wlbh1","khyhzh1");  //json对象				
	var jsonStr = JSON.stringify(json);  //json字符串
	$.ajax({
		        url:"${ctx}/wldwsz/addWldwmxb",
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				alert("保存成功！"); 
			    window.location.href="${ctx}/webView/kjhs/wldwsz/wldwsz_list.jsp"
			    
	   			}
			}); 
}
</script>
</body>
</html>