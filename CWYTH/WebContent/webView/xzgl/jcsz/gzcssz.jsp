<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>工资参数设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
     .border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
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
	 #tbody input{
		border:none;
		width:100%;
	}
	.bk{
      padding:4px !important;
   }
</style>
</head>
<body>
<div id="myform" class="form-horizontal" action="" method="post">
<input type="hidden" id="txt_operateType" class="" name="operateType" value="${operateType }">
<input type="hidden" id="txt_guid" name="guid" value="${guid}"> 
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>工资参数设置</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
<!-- 			<button type="button" class="btn btn-default" id="btn_back">返回</button> -->
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>工资参数设置</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<form id="table1">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">发放月份</span>
							<input type="text" id="txt_ffyf" name="ffyf" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="form-control input-radius" value="${gzcs.ffyf }"/>
						</div>
                    </div>
                    </div>
                    <div class="row">
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">纳税基数（元）</span>
							<input type="text" id="txt_nsjs" name="nsjs" class="form-control input-radius number" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${gzcs.nsjs}"/>
						</div>
                    </div>
                    </div>
                  <div class="row">	
					  <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">年终奖比例(百分比)</span>
                            <input type="text" id="txt_nzjbl" name="nzjbl" class="form-control input-radius " style="border-bottom-right-radius: 4px;text-align:right;border-top-right-radius: 4px;"  value="${gzcs.nzjbl}"/>
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
	var zbid = "${guid}";
	//验证方法
	var validate = $('#table1').bootstrapValidator({fields:{       
          ffyf:{validators: {notEmpty: {message: '不能为空'}}},
          nsjs:{validators: {notEmpty: {message: '不能为空'}}}, 
          nzjbl:{validators: {notEmpty: {message: '不能为空'}}}
          }
	
     });
	
	//保存按钮
	//点击保存按钮
		$("#btn_save").click(function(){
			var json = $("#table1").serializeObject("ffyf","nzjbl");  //json对象				
			var jsonStr = JSON.stringify(json);
			console.log(jsonStr);
			confirm("确定保存？","",function(){
			$.ajax({
				url:"${ctx}/gzcssz/doSave",
				data:"jsonStr="+jsonStr,
				dataType:"json",
				async:"false",
				type:"post",
				success:function(msg){
					if($.trim(msg)=="true"){
// 						window.location.href="${ctx}/webView/xzgl/xzgl_show.jsp";
						alert("保存成功!");
					}else{
						alert("保存失败!");
					}
				}
			});
			});
		});
// 	$("#btn_save").click(function(){
// 		var tr = $("tbody tr").length;
// 		if(tr>1){
// 			 $("tbody tr input").removeClass("null")
// 		}else{
// 			 $("tbody tr input").addClass("null")
// 		}
		
// 		doSave1(validate,"table1","${ctx}/xmbq/doSave?operateType="+"${operateType}",function(val){
			
// 		});
// 	});
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
					alert("操作成功");
				},
				error:function(val){
					alert("操作失败");			
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
 	function next(){
		$("tr:last").remove();
	 	var json = $('#table2').serializeObject1("bmmc","end");  //json对象				
		var jsonStr = JSON.stringify(json);  //json字符串
		$.ajax({
		   			url:"${ctx}/xmbq/doSaveMxb?zbid="+zbid,
		   			type:"post",
		   			data:"json="+jsonStr,
		   			success:function(data){
					alert("保存成功");  
					window.location.href = "${ctx}/xzgl/jcsz/gzcssz.jsp";
		   			}
				}); 
	}
 	function checkAutoInput(){
 		tag = true;
 		var ys = $(".null[name!=end]");
 		var value = "";
 		$.each(ys,function(){
 			value = $(this).val();
 			if(value==""){
 				$(this).addClass("border");
 				tag = false;
 			}else{
 			}
 		});
 	}
 	//删除按钮
	$(document).on("click","[class=deleteBtn]",function(){
		$(this).parents("tr").remove();
	});
 	$(".deleteBtn").click(function(){
 		$(this).parents("tr").remove();
 	});

	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/xmbq/goListPage";
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
	
	$(document).on("click",".addBtn",function(){
		select_commonWin("${ctx}/xmbq/xmxx","项目信息","920","630");
	})
	
	
});
function addxmxx(xmid,xmbh,xmmc,bmmc){
	var guid= $("#txt_guid").val();
	$("tr:last").attr("class","tr1");
	for(var i=0;i<xmbh.length;i++){
		
		$(".tr1").show();
		var $parentTr = $(".tr1").clone();
		$parentTr.removeClass("tr1");
		$parentTr.find("input:not(#txt_zbid)").attr("value","");
		$parentTr.find("[id=txt_xmid]").val(xmid[i]);
		$parentTr.find("[id=txt_xmbh]").val(xmbh[i]);
		$parentTr.find("[id=txt_xmmc]").val(xmmc[i]);
		$parentTr.find("[id=txt_bmmc]").val(bmmc[i]);
		$(".tr1").before($parentTr);
		$parentTr.removeClass("tr1");
		
		//$("tr").removeClass("tr1");
	}
	$(".addBtn").attr("class","deleteBtn");
	
	$(".deleteBtn:last").attr("class","addBtn");
	$("input").removeClass("border");
	
}
</script>
</body>
</html>