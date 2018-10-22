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
</style>
</head>
<body>
<div id="myform" class="form-horizontal" action="" method="post">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>查看凭证模板信息</span>
		</div>
		<div class="pull-right">
			
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
							<span class="input-group-addon">模板编号</span>
							<input type="text" id="txt_mbbh1" readonly name="mbbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${pzmb.mbbh}" readonly/>
						</div>
                    </div>
                    <div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">模板名称</span>
							<input type="text" id="txt_mbnr" name="mbnr" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${pzmb.mbnr}" readonly/>
						</div>
                    </div>
                  </div>
<!--                   <div class="row"> -->
<!-- 					  <div class="col-md-12"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">凭证摘要</span> -->
<%--                             <textarea id="txt_pzzy" class="form-control" style="padding:0px;" name="pzzy" readonly>${pzmb.pzzy}</textarea> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->

					 <div class="row">
					  <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                            <textarea id="txt_bz" class="form-control" style="padding:0px;" name="bz" readonly>${pzmb.bz}</textarea>
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
				            <th style="text-align:center;">摘要</th>
				            <th style="text-align:center;">科目编号</th>
				            <th style="text-align:center;">科目名称</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				    
				    
				    
				    
				    <c:choose>
                          <c:when test="${not empty pzmbmx}">
  								<c:forEach var="pzmbmx" items="${pzmbmx}"> 
				    	<tr id="tr_1">
				    		<td class="btn_td">
				    			<div class="addBtn add" ></div>
				    		</td>
				    		<td>
				    			 <input type="text" id="txt_zy" style="background-color: white !important;" class="bk input-radius null" name="zy" value="${pzmbmx.zy}"  readonly>
				    			
				    		</td>
				    		
				    		<td>
<!-- 				    		<div class="input-group">
 -->				    			<input type="text" id="txt_kmbh" class="bk input-radius window null" name="kmbh" value="${pzmbmx.kmbh }" readonly>
				    			<input type="hidden" id="txt_mbbh" class="form-control input-radius window" name="mbbh1" value="${pzmbmx.mbbh } ">
				    			<input type="hidden" id="txt_guid" class="form-control input-radius window" name="guid" value="${pzmbmx.guid }">
				    			<span class="input-group-btn">
<!-- 				    			<button type="button" id="btn_kmbh" sj="txt_kmbh" class="btn btn-link btn-custom">选择</button>
 -->				    			</span>
<!-- 				    		</div>
 -->				    		</td>
				    		<td>
				    			 <input type="text" id="txt_kmmc" class="bk null" name="kmmc" value="${pzmbmx.kmmc}" readonly>
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
<!-- 				    		<div class="input-group">
 -->				    			<input type="text" id="txt_kmbh" class="bk input-radius window null" name="kmbh" value="" readonly>
				    			<input type="hidden" id="txt_mbbh" class="form-control input-radius window" name="mbbh1" value="${guid }">
				    			<input type="hidden" id="txt_guid" class="form-control input-radius window" name="guid" value="">
				    			<span class="input-group-btn">
<!-- 				    			<button type="button" id="btn_kmbh" sj="txt_kmbh" class="btn btn-link btn-custom">选择</button>
 -->				    			</span>
<!-- 				    		</div>
 -->				    		</td>
				    		<td>
				    			 <input type="text" id="txt_kmmc" class="bk window null" name="kmmc" value="" readonly>
				    		</td>
				    		<td>
				    			 <input type="text" id="txt_zy" style="background-color: white !important;" class="bk input-radius null" name="zy" value="" readonly >
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
	addbutton();
	function addbutton(){
		var a = $(".tbody").find("tr").length;
		var aaa="${pzmbmx}";
		
		if(aaa.length>0){
			$(".add").removeClass("addBtn")
			
			$(".add").addClass("deleteBtn");		
			var $parentTr = $("tr:last").clone();
			
			$parentTr.find("input:not(#txt_mbbh)").attr("value","");
			
			$("tr:last").after($parentTr);
			
			$(".tbody tr:last").find(".add").removeClass("deleteBtn");
			$(".tbody tr:last").find(".add").addClass("addBtn");
		}else{
			
		}
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
	//保存按钮
	$("#btn_save").click(function(){
		var tr = $("tbody tr").length;
		if(tr>1){
			 $("tbody tr input").removeClass("null")
		}else{
			 $("tbody tr input").addClass("null")
		}
		
		doSave1(validate,"table1","${ctx}/pzmb/updatePzmb",function(val){
			
		});
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
 			}else{
 			}
 		});
 	}
 	//删除按钮
	$(document).on("click","[class=deleteBtn]",function(){
		
	});
 	$(".deleteBtn").click(function(){
 		
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
		 $("tr:last").remove();
	 	var json = $('#table2').serializeObject1("kmbh","kmmc");  //json对象				
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
	var num=1;
	/* function addkjkm1(kmbh,kmmc){
		var guid= $("#txt_guid").val()
		for(var i=0;i<kmbh.length;i++){
		//	$("tr:last").attr("class","tr1");
			$(".tr1").show();
			var $parentTr = $(".tr1").clone();
			$parentTr.removeClass("tr1");
			$parentTr.find("input:not(#txt_mbbh)").attr("value","");
			
			$parentTr.find("[id=txt_kmbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_kmmc]").val(kmmc[i]);
						
			$(".tr1").after($parentTr);
			$parentTr.removeClass("tr1");
			
			$("tr").removeClass("tr1");
		}
		$(".addBtn").attr("class","deleteBtn");
		
		$(".deleteBtn:last").attr("class","addBtn");
		
	} */
	
	
	
});

</script>
</body>
</html>