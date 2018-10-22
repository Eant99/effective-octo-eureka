<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支出预算修改</title>
<%@include file="/static/include/public-manager-css.inc"%>

</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${zcysxg.guid}">
	<input type="hidden" name="czr"  value="${zcysxg.czr}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>支出预算修改</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>报销详细</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号</span>
                             <input type="text" id="txt_djbh" readonly class="form-control input-radius " name="djbh" value="001"/>                      
						</div>
					</div>
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位名称</span>
                             <input type="text" id="txt_dwmc" readonly class="form-control input-radius" name="dwmc" value="${srysxg.ysdw}"/>
                             
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">编制年度</span>
							 <input type="text" id="txt_bxbm" readonly class="form-control input-radius int" name="bznd" value="${gwjdfsq.bxbm}"/>
						</div>
					</div>				
				</div>
			<div class="row">
					  <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">上年预算总额（万元）</span>
                              <input type="text" id="txt_snysze" readonly class="form-control input-radius text-right number1" name="snysze" value="200.0000" readonly/>

						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">上年支出总额（万元）</span>
					  <input type="text" id="txt_snzcze" readonly class="form-control input-radius  text-right number1" name="snzcze" value="150.0000" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">本年预算总额（万元）</span>
                            <input type="text" id="txt_bnysze" readonly class="form-control input-radius text-right number1" name="bnysze" value="" readonly/>
						</div>
					</div>				
				</div>
				<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">项目名称</span>
                          <input type="text" id="txt_xmmc" readonly class="form-control input-radius" name="xmmc" value="${gwjdfsq.ptrs}"/>
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">修改人</span>
                          <input type="text" id="txt_xgr" readonly class="form-control input-radius" name="xgr" value="${gwjdfsq.ptrs}"/>
					</div>
				</div>
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">修改时间</span>
                              <input type="text" id="txt_xgsj" readonly class="form-control input-radius " name="xgsj" >
						</div>
					</div>	
				</div>
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目分类</span>
							<input type="text" id="txt_xmfl" readonly class="form-control input-radius " name="xmfl" value="一级分类" >
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目类型</span>
							<input type="text" id="txt_xmlx" readonly class="form-control input-radius " name="xmlx" value="出差类" >
						</div>
					</div>
				
				</div>
				<div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">测算依据</span>
                               <textarea id="txt_csyj" readonly class="form-control" name="csyj" >${gwjdfsq.bz}</textarea>
						</div>
					</div>	
                 </div>
			</div>
		</div>
		<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>明细信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse;">
		        	<thead id="thead">
				        <tr>
				            <th style="width:300px;text-align: center;">预算科目编号</th>
				            <th style="width:300px;text-align: center;">上年预算（万元）</th>
				            <th style="width:300px;text-align: center;">上年支出（万元）</th>
				            <th style="width:300px;text-align: center;">本年预算（万元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1">
				    		
				    		<td>
				    			<input type="text" id="txt_yskm1" readonly class="form-control input-radius window null" name="yskm1" value="">

				    		</td>
				    		<td>
				    			<input type="text" id="txt_snys1" readonly class="form-control input-radius text-right number1" name="snys1" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_snzc1" readonly class="form-control input-radius text-right number1" name="snzc1" value="" >
				    		</td>
				    		<td>
				    			<input type="text" id="txt_bnys1"  class="form-control input-radius text-right number1 money" name="bnys1" value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');" >
				    		</td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var d = new Date();
	var date = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
	$("#txt_xgsj").val(date);
		//新增按钮
		var num = 2;
		$(document).on("click","[class*=addBtn]",function(){
			var $parentTr = $(this).parents("tr").clone();
			$(this).removeClass("addBtn");
			$(this).addClass("deleteBtn");
			$parentTr.find(":input").val("");
			$parentTr.find(":input").removeClass("border");
			$parentTr.attr("id","tr_"+num);
			$parentTr.find("[id^=txt_yskm]").attr({"name":"yskm"+num,"id":"txt_yskm"+num});
			$parentTr.find("[id^=txt_snys]").attr({"name":"snys"+num,"id":"txt_snys"+num});
			$parentTr.find("[id^=txt_snzc]").attr({"name":"snzc"+num,"id":"txt_snzc"+num});
			$parentTr.find("[id^=txt_bnys]").attr({"name":"bnys"+num,"id":"txt_bnys"+num});
			$parentTr.find("[id^=btn_yskm]").attr({"id":"btn_yskm"+num,"sj":"txt_yskm"+num});
			num++;
			$(this).parents("tr").after($parentTr);
		});
		//删除按钮
		$(document).on("click","[class*=deleteBtn]",function(){
			$(this).parents("tr").remove();

		});
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
		
          }
	      });
	
	//根据本年预算计算本年预算总额
	$(document).on("keyup","[class*=money]",function(){
		var bnys = $(this).val();
		//alert(ss);
		computer();
	});
	function computer(){
		var bnysze=0;
		$.each($(".money"),function(i,v){
			var bnys = $(this).val();
			
			//alert(bnys);
			if(isNaN(bnys)||bnys==""){
				bnys = 0;		
			}
			if(isNaN(bnysze)||bnysze==""){
				bnysze = 0;			
			}
			 bnysze = parseFloat(bnysze) + parseFloat(bnys);
			
		});
		$("[name='bnysze']").val(bnysze.toFixed(4));
		
	}
	//保存按钮
	$("#btn_save").click(function(){		
		doSave1(validate,"myform","*",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
	});
	function doSave1(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){					
				},
				error:function(val){
					alert("保存成功！");					
				}	
			});
		}
	}
	//联想输入提示
	$("#txt_kmbh").bindChange("#","D");
	
	$("#btn_kmbh").click(function(){
		select_commonWin("${ctx}/window/zdpage?controlId=txt_zl","字典信息","720","630");
    });
	//弹窗
	$("#btn_dwmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwmc","单位信息","920","630");
    });
	//科目编号弹框
	$(document).on("click","[id^=btn_yskm]",function(){
		var controlId = $(this).attr("sj");
		select_commonWin("${ctx}/kmsz/window?controlId="+controlId,"科目信息","920","630");
    });
	
	//
	$(document).on("blur", ".number1", function(e){
		$(this).Num(4,true,false,e);
		return false;
	});

	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/ysgl/twoxjd/zcysxz_list.jsp";
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

</script>
</body>
</html>