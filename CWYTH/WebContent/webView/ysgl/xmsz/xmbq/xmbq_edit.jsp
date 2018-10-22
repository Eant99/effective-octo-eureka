<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目标签详细信息</title>
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
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑项目标签信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目标签信息</div>
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
							<span class="input-group-addon"><span class="required">*</span>标签编号</span>
							<input type="text" id="txt_bqbh" name="bqbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${xmbq.bqbh}"/>
						</div>
                    </div>
                    <div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>标签名称</span>
							<input type="text" id="txt_bqmc" name="bqmc" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${xmbq.bqmc}"/>
						</div>
                    </div>
                  </div>
                  <div class="row">
					  <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
                            <textarea id="txt_bz" class="form-control" style="padding:0px;" name="bz" >${xmbq.bz}</textarea>
						</div>
					</div>
				</div>
				</div>
				</form>
				</div>
				<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
			<form id="table2">
				<table id="mydatatables" class="table table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align:center;">操作</th>
				        	<th style="text-align:center;"><span class="required" style="color:red ;">*</span>部门名称</th>				       
				            <th style="text-align:center;"><span class="required" style="color:red ;">*</span>项目编号</th>
				            <th style="text-align:center;"><span class="required" style="color:red ;">*</span>项目名称</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				    <c:if test="${ operateType == 'C' }">
				       <tr id="tr_1">
				    		<td class="btn_td">
				    			<div class="addBtn add" ></div>
				    		</td>
				    		<td>
				    		    <input type="text" id="txt_bmmc" class="bk window null" name="bmmc" value="" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_xmbh" class="bk input-radius window null" name="xmbh" value="" readonly>
				    			<input type="hidden" id="txt_zbid" class="form-control input-radius window" name="zbid" value="${guid }">
				    			<input type="hidden" id="txt_guid" class="form-control input-radius window" name="guid" value="">
				    			<input type="hidden" id="txt_xmid" class="form-control input-radius window" name="xmid" value="">
				    			<span class="input-group-btn">
				    			</span>
				    		</td>
				    		<td>
				    			 <input type="text" id="txt_xmmc" class="bk window null" name="xmmc" value="" readonly>
				    			  <input type="hidden" name="end" value="end">
				    		</td>				
				    	</tr>
				    </c:if>
                    <c:if test="${ operateType == 'U' }">
				    <c:choose>
                          <c:when test="${not empty xmbqmx}">
  								<c:forEach var="xmbqmx" items="${xmbqmx}"> 
				    	<tr id="tr_1">
				    		<td class="btn_td">
				    			<div class="addBtn add" ></div>
	                        </td>
	                        <td>
				    		    <input type="text" id="txt_bmmc" class="bk window null" name="bmmc" value="${xmbqmx.bmmc}" readonly>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_xmbh" class="bk input-radius window null" name="xmbh" value="${xmbqmx.xmbh }" readonly>
				    			<input type="hidden" id="txt_zbid" class="form-control input-radius window" name="zbid" value="${xmbqmx.zbid }">
				    			<input type="hidden" id="txt_guid" class="form-control input-radius window" name="guid" value="${xmbqmx.guid }">
				    			<input type="hidden" id="txt_xmid" class="form-control input-radius window" name="xmid" value="${xmbqmx.xmid }">
				    			<span class="input-group-btn">
				    			</span>
				    		</td>
				    		<td>
				    			 <input type="text" id="txt_xmmc" class="bk null" name="xmmc" value="${xmbqmx.xmmc}" readonly>
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
				    			<input type="text" id="txt_xmbh" class="bk input-radius window null" name="xmbh" value="" readonly>
				    			<input type="hidden" id="txt_zbid" class="form-control input-radius window" name="zbid" value="${guid }">
				    			<input type="hidden" id="txt_guid" class="form-control input-radius window" name="guid" value="">
				    			<input type="hidden" id="txt_xmid" class="form-control input-radius window" name="xmid" value="">
				    			<span class="input-group-btn">
				    			</span>
				    		</td>
				    		<td>
				    			 <input type="text" id="txt_xmmc" class="bk window null" name="xmmc" value="" readonly>
				    			  <input type="hidden" name="end" value="end">
				    		</td>				
				    	</tr>
                       </c:otherwise>
                    </c:choose>
                    </c:if>
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
	var zbid = "${guid}";
	//验证方法
	var validate = $('#table1').bootstrapValidator({fields:{       
          bqbh:{validators: {notEmpty: {message: '不能为空'}}},
          bqmc:{validators: {notEmpty: {message: '不能为空'},stringLength:{message:'名称过长',max:'100'}}}, 
          bz:{validators: {stringLength:{message:'备注过长',max:'500'}}} 
          }
	
     });
	addbutton();
	function addbutton(){
		var a = $(".tbody").find("tr").length;
		var aaa="${xmbqmx}";
		
		if(aaa.length>0){
			$(".add").removeClass("addBtn")
			$(".add").addClass("deleteBtn");		
			var $parentTr = $("tr:last").clone();			
			$parentTr.find("input:not(#txt_zbid)").attr("value","");			
			$("tr:last").after($parentTr);			
			$(".tbody tr:last").find(".add").removeClass("deleteBtn");
			$(".tbody tr:last").find(".add").addClass("addBtn");
		}else{
			
		}
	}
	
	$("#txt_bqbh").blur(function(){
		var guid = $("#txt_guid").val();
		var bqbh = $("#txt_bqbh").val();
		$.ajax({
			url:"${ctx}/xmbq/selectBqbh",
			data:"guid="+guid+"&bqbh="+bqbh,
			type:"post",
			dataType:"json",
			success: function(val){
				if(val == false){
					alert("该标签编号已经存在!");
					$("#txt_bqbh").val("");
				}
			},
			error: function(val){
				
			}
		});
	});
	//保存按钮
	$("#btn_save").click(function(){
		var tr = $("tbody tr").length;
		if(tr>1){
			 $("tbody tr input").removeClass("null")
		}else{
			 $("tbody tr input").addClass("null")
		}
		
		doSave1(validate,"table1","${ctx}/xmbq/doSave?operateType="+"${operateType}",function(val){
			
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
					window.location.href = "${ctx}/xmbq/goListPage";
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