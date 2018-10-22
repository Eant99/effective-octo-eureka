<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证模板详细信息</title>
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
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑凭证模板信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>模板信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<form id="table1">
			<input type="hidden" name="guid" id="txt_guid" value="${guid }">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>模板编号</span>
							<input type="text" id="txt_mbbh1" readonly name="mbbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${pzmb.mbbh}"/>
						</div>
                    </div>
                    <div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>模板名称</span>
							<input type="text" id="txt_mbnr" name="mbnr" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${pzmb.mbnr}"/>
						</div>
                    </div>
                  </div>
<!--                   <div class="row"> -->
<!-- 					  <div class="col-md-12"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">凭证摘要</span> -->
<%--                             <textarea id="txt_pzzy" class="form-control" style="padding:0px;" name="pzzy" >${pzmb.pzzy}</textarea> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->

					 <div class="row">
					  <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                            <textarea id="txt_bz" class="form-control" style="padding:0px;" name="bz" >${pzmb.bz}</textarea>
						</div>
					</div>
				</div>
				</div>
				</form>
				</div>
				<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>科目信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
			<form id="table2">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align:center;">操作</th>
				            <th style="text-align:center;"><span class="required" style="color:red ;">*</span>科目编号</th>
				            <th style="text-align:center;"><span class="required" style="color:red ;">*</span>科目名称</th>
				            <th style="text-align:center;">摘要</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				    <c:choose>
                        <c:when test="${not empty pzmbmx}">
  						<c:forEach var="pzmbmx" items="${pzmbmx}" varStatus="i"> 
				    	<tr>
				    		<td class="btn_td">
				    			<div class="addBtn add" ></div>
				    		</td>
				    		
				    		<td>
 				    		<div class="input-group">
				    			<input type="text" id="txt_kmbh" class="bk input-radius window null" name="kmbh" value="${pzmbmx.kmbh }" readonly>
				    			<input type="hidden" id="txt_mbbh" class="form-control input-radius window" name="mbbh1" value="${pzmbmx.mbbh }">
				    			<input type="hidden" id="txt_guid" class="form-control input-radius window" name="guid" value="${pzmbmx.guid }">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_kmbh${i.index+1}" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
				    		</td>
				    		<td>
				    			 <input type="text" id="txt_kmmc" class="bk null" name="kmmc" value="${pzmbmx.kmmc}" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_zy" style="background-color: white !important;" class="bk input-radius null" name="zy" value="${pzmbmx.zy}" >
			                     <input type="hidden" name="end" value="end">
				    		</td>
				    	</tr>
				    	</c:forEach>
                        </c:when>
                        <c:otherwise>
							<tr id="tr_1">
				    		<td class="btn_td">
				    			<div class="addBtn add" ></div>
				    		</td>
				    		<td>
				    		<div class="input-group">
				    			<input type="text" id="txt_kmbh" class="bk input-radius window null" name="kmbh" value="" readonly>
				    			<input type="hidden" id="txt_mbbh" class="form-control input-radius window" name="mbbh1" value="${guid }">
				    			<input type="hidden" id="txt_guid" class="form-control input-radius window" name="guid" value="">
				    			<span class="input-group-btn">
				    			<button type="button" id="btn_kmbh1" sj="txt_kmbh" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    		</div>
				    		</td>
				    		<td>
				    			 <input type="text" id="txt_kmmc" class="bk window null" name="kmmc" value="" readonly>
				    		</td>
				    		<td>
				    			 <input type="text" id="txt_zy" style="background-color: white !important;" class="bk input-radius null" name="zy" value="" >
				    			 <input type="hidden" name="end" value="end">
				    		</td>
				    	    </tr>
                        </c:otherwise>
                    </c:choose>
			    </tbody>
				</table>
				</form>
			</div>
		</div>
		
			
		
	</div>
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var mbbh = $("#txt_mbbh").val();
$(function(){
	$(document).on("click","[name='zy']",function(){
		$(this).removeClass("border");
	});
	$(document).on("click","[id^='btn_kmbh']",function(){
		var xzid = $(this).attr("id");
		select_commonWin("${ctx}/pzmb/window?xzid="+xzid,"会计科目信息","920","630");
	});
	
	addbutton();
	function addbutton(){
		var xzarr =  $("[id^=btn_kmbh]");
		var arr = [];
		$.each(xzarr,function(){
			var num = $(this).attr("id").replace(/[^0-9]+/g, '');
			arr.push(num);
		});
		var newarr = arr.sort();
		var len = newarr.length;
		var maxnum = newarr[len-1];
		maxnum = parseInt(maxnum)+1;
		var a = $(".tbody").find("tr");
		$(".add").removeClass("addBtn")		
		$(".add").addClass("deleteBtn");		
		var $parentTr = $("tr:last").clone();	
		$parentTr.attr("id","tr_1");
		$parentTr.find("input").removeClass("null");
		$parentTr.find("input:not(#txt_mbbh)").attr("value","");	
		$parentTr.find("[id^='btn_kmbh']").attr("id","btn_kmbh"+maxnum);
		$("tr:last").after($parentTr);		
		$(".tbody tr:last").find(".add").removeClass("deleteBtn");
		$(".tbody tr:last").find(".add").addClass("addBtn");
	}
	//验证模板编号是否存在
	$("#txt_mbbh1").blur(function(){
		var guid = $("#txt_guid").val();
		var mbbh = $("#txt_mbbh1").val();
		$.ajax({
			url:"${ctx}/pzmb/selectMbbh",
			data:"guid="+guid+"&mbbh="+mbbh,
			type:"post",
			dataType:"json",
			success: function(val){
				if(val == false){
					alert("该模板编号已经存在");
					$("#txt_mbbh1").val("");
				}
			},
			error: function(val){
				
			}
		});
	});
	//验证方法
	var validate = $('#table1').bootstrapValidator({fields:{       
          mbbh:{validators: {notEmpty: {message: '模板编号不能为空'}}},
          mbnr:{validators: {notEmpty: {message: '模板内容不能为空'},stringLength:{message:'模板名称过长',max:'50'}}}, 
          bz:{validators: {stringLength:{message:'备注过长',max:'500'}}} 
          }
	
     });
	//加行
	$(document).on("click",".addBtn",function(){
		var xzarr =  $("[id^=btn_kmbh]");
		var arr = [];
		$.each(xzarr,function(){
			var num = $(this).attr("id").replace(/[^0-9]+/g, '');
			arr.push(num);
		});
		var newarr = arr.sort();
		var len = newarr.length;
		var maxnum = newarr[len-1];
		maxnum = parseInt(maxnum)+1;
		var guid= $("#txt_guid").val()
		$("#tr_1 input").addClass("null");
		var $parentTr = $("#tr_1").clone();
		$parentTr.removeAttr("id");
		$parentTr.find("input:not(#txt_mbbh)").attr("value","");	
		$parentTr.find("#txt_guid").val(guid);
		$parentTr.find(":input").removeClass("border");
		$parentTr.find("[name='zy']").val("");
		$parentTr.find("[name='end']").removeClass("null");
		$parentTr.find("[id^='btn_kmbh']").attr("id","btn_kmbh"+maxnum);
		$("#tr_1").before($parentTr);
		//$("#tr_1").hide();
		$("#tr_1 input").removeClass("null");
		$(".addBtn").attr("class","deleteBtn");		
		$(".deleteBtn:last").attr("class","addBtn");
		$("input").removeClass("border");
		maxnum++;
	});
	//保存按钮
	$("#btn_save").click(function(){
		var tr = $("#tbody tr").length;
		if(tr ==1 ){
		 $("tbody tr input").addClass("null");
		}
		doSave1(validate,"table1","${ctx}/pzmb/updatePzmb",function(val){});
	});
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
						next();
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
 	//删除按钮
	$(document).on("click",".deleteBtn",function(){
		$(this).parents("tr").remove();
	});

	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/kjhs/pzmb/pzmb_list.jsp";
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
	function next(){
		$("#tbody tr:last").remove();
	 	var json = $('#table2').serializeObject1("kmbh","end");  //json对象				
		var jsonStr = JSON.stringify(json);  //json字符串
		$.ajax({
   			url:"${ctx}/pzmb/editpzmbmx?mbbh="+mbbh,
   			type:"post",
   			data:"json="+jsonStr,
   			success:function(data){
			alert("保存成功");  
			window.location.href = "${ctx}/webView/kjhs/pzmb/pzmb_list.jsp";
   			}
		}); 
	}	
	
});
function addkjkm(kmbh,kmmc,xzid){
	$('#'+xzid+'').parents("tr").find("#txt_kmbh").removeClass("border");
	$('#'+xzid+'').parents("tr").find("#txt_kmmc").removeClass("border");
	var zy = $('#'+xzid+'').parents("tr").find("#txt_zy").val();
	var xzkmbh = $('#'+xzid+'').parents("tr").find("#txt_kmbh").val();
	var xzarr =  $("[id^=btn_kmbh]");
	var arr = [];
	$.each(xzarr,function(){
		var num = $(this).attr("id").replace(/[^0-9]+/g, '');
		arr.push(num);
	});
	var newarr = arr.sort();
	var len = newarr.length;
	var maxnum = newarr[len-1];
	maxnum = parseInt(maxnum)+1;
    var nulllen = 0;
    $.each($("[name='kmbh']"),function(){
    	if($(this).val()=="")
    		nulllen += 1; 
    });
	var guid= $("#txt_guid").val();	
	//获取最后一行选择按钮的id
	var lastzxid = $("#tbody").find("tr:last").find(".btn-custom").attr("id");
	var flag = true;
	if(lastzxid == xzid){
		flag = false;
	}
	for(var i=0;i<kmbh.length;i++){
		$("#tr_1").show();
		if(i==0 && (xzkmbh!=""||nulllen>1) &&flag ){
			$('#'+xzid+'').parents("tr").find("#txt_kmbh").val(kmbh[i]);
			$('#'+xzid+'').parents("tr").find("#txt_kmmc").val(kmmc[i]);
		  // $parentTr.find("[name='zy']").val(zy);
		}else{
			$("#tr_1 input").addClass("null");
			var $parentTr = $("#tr_1").clone();
			$parentTr.removeAttr("id");
			$parentTr.find("input:not(#txt_mbbh)").attr("value","");	
			$parentTr.find("#txt_guid").val(guid);
			$parentTr.find(":input").removeClass("border");
			$parentTr.find("[id=txt_kmbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_kmmc]").val(kmmc[i]);
			$parentTr.find("[name='zy']").val("");
			$parentTr.find("[name='end']").removeClass("null");
			$parentTr.find("[id^='btn_kmbh']").attr("id","btn_kmbh"+maxnum);
			$("#tr_1").before($parentTr);
			$("#tr_1 input").removeClass("null");
			$(".addBtn").attr("class","deleteBtn");		
			$(".deleteBtn:last").attr("class","addBtn");
			$("input").removeClass("border");
	        maxnum++
		}
	}	
}

</script>
</body>
</html>