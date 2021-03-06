<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>往来单位信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
<style>
.row:last-child .input-group {
    margin-bottom: 8px!important; 
}
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
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">单位编号</span>
							<input type="text" id="txt_wlbh" name="wlbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" readonly  value="系统自动生成"/>
						</div>
                    </div>
                    <div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>户名</span>
                            <input type="text" id="txt_dwmc" class="form-control input-radius" name="dwmc" value="${wldwsz.dwmc}"/>
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">单位简称</span>
						 <input type="text" id="txt_dwjc" class="form-control input-radius" name="dwjc" value="${wldwsz.dwjc}"/>	
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							 <span class="input-group-addon">单位类型</span>
	                        <select id="drp_dwlx" class="form-control input-radius " name="dwlx"> 
	                                <option value="">未选择</option>
									<option value="01" <c:if test="${wldwsz.dwlx == '01'}">selected</c:if>>供应商</option>
									<option value="02" <c:if test="${wldwsz.dwlx == '02'}">selected</c:if>>主管部门</option>
							</select>
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">单位地址</span>
	                       <input type="text" id="txt_dwdz" class="form-control input-radius" name="dwdz" value="${wldwsz.dwdz}"/>	
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">联&ensp;系&ensp;人</span>
							  <input type="text" id="txt_lxr" class="form-control input-radius" name="lxr" value="${wldwsz.lxr}"/>	
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">统一社会信用代码</span>
							 <input type="text" id="txt_sh" class="form-control input-radius" name="sh" value="${wldwsz.sh}"/>	
						</div>
                  </div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">办公电话</span>
                           <input type="text" id="txt_bgdh" class="form-control input-radius" name="bgdh" value="${wldwsz.bgdh}"/>	
						</div>
					</div>
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							<span class="input-group-addon">传&emsp;&emsp;真</span>
                            <input type="text" id="txt_cz" class="form-control" name="cz" value="${wldwsz.cz}">          
						</div>
				      </div>	
					</div>
					<div class="row">
					<div class="col-md-4 col-xs-4">
						<div class="input-group">
							 <span class="input-group-addon">是否对公支付</span>
	                        <select id="drp_sfdgzf" class="form-control input-radius" name="sfdgzf" readonly> 
									<option value="01" <c:if test="${wldwsz.sfdgzf == '01'}">selected</c:if>>是</option>
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
			<hr class="hr-normal">
			<div class="container-fluid box-content">
			 <form id="myform1">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align: center;">操作</th>
				            <th style="width:200px; text-align: center;"><span class="required" style="color:red;">*</span>开户银行名称</th>
				            <th style="width:200px; text-align: center;"><span class="required" style="color:red;">*</span>开户银行帐号</th>
				            <th style="width:200px; text-align: center;">人民银行联行号</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1">
				    		<td class="btn_td">
	                               <div class="addBtn"></div>
				    		</td>
				    		<td>
				    		    <input type="hidden" id="txt_wlbh1" class=" input-radius" name="wlbh1" value="${guid }">
				    			<input type="text" id="txt_khyh1" a="a" class=" input-radius null" name="khyh1" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class=" input-radius null" name="khyhzh1" value="">
				    		</td>
				    		<td>
					    		<input type="text" id="txt_yhlhh1" a="a" class="bk input-radius int" name="yhlhh1" value="">
					    	</td>
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
var id = "${param.id}";
$(function(){
	
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
		console.log(zdid.length)
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
			doSave1(validate,"myform2","${ctx}/wldwsz/addWldwsz",function(val){
	
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
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/wsbx/rcbx/window?controlId=xnzz&id="+id+"&pname=${param.pname}";
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
	var json = $('#myform1').serializeObject1("wlbh1","yhlhh1");  //json对象				
	var jsonStr = JSON.stringify(json);  //json字符串
	$.ajax({
		        url:"${ctx}/wldwsz/addWldwmxb",
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				alert("保存成功！");  
				var winId = getTopFrame().layer.getFrameIndex(window.name);
				close(winId);
	   			}
			}); 
}
</script>
</body>
</html>