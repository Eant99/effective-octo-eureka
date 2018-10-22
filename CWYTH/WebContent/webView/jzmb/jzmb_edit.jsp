<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>结转模板详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
.addBtn {
	text-align: center;
	width: 20px;
	height: 20px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
	margin-left:5px;
	margin-top:2px;
}

.addBtn:after {
	/* 		2722 */
	content: "+";
	color: #9c9c9c !important;
	color: #337ab7 !important;
/* 	font-size: 17px; */
	position: relative;
	cursor: pointer;

}

.deleteBtn {
	text-align: center;
	width: 20px;
	height: 20px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
	margin-left:5px;
	margin-top:2px;
}

.deleteBtn:after {
	/* 		2014 */
	content: "\2014";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 1em;
	position: relative;
	cursor: pointer;
/* 	top: 3px; */
}

.btn_td {
	width: 14mm !important;
	height: 6mm !important;
}

.selWindow {
	width: 280px !important;
}

.number, .sz {
	text-align: right;
}
tr{
background-color: white !important;
}
 #tbody input{
		border:none;
		width:100%;
		 padding:4px !important;
	}
	.bk{
      padding:4px !important;
      border:none;
	  width:100%;
   }
</style>
</head>
<body>
	<div id="myform" class="form-horizontal" action="" method="post">
		<input type="hidden" name="operateType" id="operateType" value="${operateType}"> 
		<input type="hidden" name="czr"	value="${loginId}">
		<div class="page-header">
			<div class="pull-left">
				<span class="page-title text-primary"><i
					class='fa icon-xiangxixinxi'></i>编辑结转模板信息</span>
			</div>
			<div class="pull-right">
				<button type="button" class="btn btn-default" id="btn_save">
					<i class="fa icon-save"></i>保存
				</button>
				<button type="button" class="btn btn-default" id="btn_back">返回</button>
			</div>
		</div>
		<div class="box" style="top: 36px">
			<div class="box-panel">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i>模板信息
					</div>
					<div class="actions">
						<a href="#" class="btn box-collapse btn-mini btn-link"><i
							class="fa"></i></a>
					</div>
				</div>
				<hr class="hr-normal">
				<form id="myform1">
				<div class="container-fluid box-content">
					<div class="row">
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>模板编号</span>
								<input type="text" id="txt_mbbh" name="mbbh" class="form-control input-radius"
									style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"	value="${dwb.mbbh}" readonly/>
		                        <input type="hidden" id="guid"	name="guid" value="${dwb.guid}"> 
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>模板名称</span>
								<input type="text" id="txt_mbmc" name="mbmc"
									class="form-control input-radius window"
									style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"
									value="${dwb.mbmc}" />
							</div>
						</div>
						<%-- <div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>凭证类型</span>
								<select id="txt_xmfl" class="form-control input-radius select2"
									name="pzlx">
									<option value="">未选择</option>
									<c:forEach var="item" items="${jzmblist}">
										<option value="${item.DM}" data-id="${pzlx.lxmc}" <c:if test="${dwb.pzlx == item.DM}">selected</c:if> >${item.MC}</option>
									</c:forEach>
								</select>
							</div>
						</div> --%>
					
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>是否包含未记账凭证</span>
								<c:if test="${operateType == 'U' or operateType == 'C' }">
							 		<input type="hidden" name="sfbhwjzpz" value="${dwb.SFBHWJZPZ}">
	                           		<div class="switch">
	                              		<div class="onoffswitch">
	                                  		<input type="checkbox"   <c:if test="${dwb.SFBHWJZPZ == '0'}">checked</c:if> class="onoffswitch-checkbox" <c:if test="${operateType != 'L'}">id="btn_nnoff" </c:if>>
	                                     			<label class="onoffswitch-label" for="btn_nnoff">
	                                         			<span class="onoffswitch-inner"></span>
	                                         			<span class="onoffswitch-switch"></span>
	                                     		</label>
	                                 		</div>
	                             	</div>
	                            </c:if>
								<c:if test="${operateType == 'L'}">
									<input type="text" class="form-control input-radius" <c:choose>
										<c:when test="${dwb.SFBHWJZPZ == '1'}">value="是"</c:when>
										<c:otherwise>value="否"</c:otherwise>
									</c:choose> >
								</c:if>
							</div>
						</div>
						</div>
					<div class="row">
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>凭证摘要</span>
								<select id="txt_xmfl" class="form-control input-radius select2"
									name="zy">
									<option value="">未选择</option>
									<c:forEach var="item" items="${jzmbzylist}">
										<option value="${item.DM}" data-id="${zy.mc}" <c:if test="${dwb.zy == item.DM}">selected</c:if> >${item.MC}</option>
									</c:forEach>
								</select>
							</div>
						</div>
<!-- 						<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon"><span class="required">*</span>收支结余科目</span> -->
<%-- 							<input type="text" id="txt_fdbrh1" class="form-control input-radius window" name="zckmbh" value="${jzmxlist[fn:length(jzmxlist)-1].ZRKMBH1}" readonly="readonly"> --%>
<!-- 							<input type="hidden" id="txt_fddbrh" class="form-control input-radius window " name="zckmbh" > -->
<!-- 							<span class="input-group-btn"><button type="button" id="btn_fddbrh" class="btn btn-link btn-custom">选择</button></span> -->
<!-- 						</div> -->
						
<!-- 					</div> -->
					<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>凭证类型</span>
								<select id="txt_pzlx" class="form-control input-radius select2" name="pzlx">
									<c:forEach var="item" items="${pzlxlist}">
										<option value="${item.pzlx}" <c:if test="${dwb.pzlx == item.pzlx}">selected</c:if> >${item.pzlx}</option>
<%-- 										<option value="${item.pzlx}">${item.pzlx}</option> --%>
									</c:forEach>
								</select>
							</div>
						</div>
					<%-- 	<div class="col-md-4">
						 <div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>收支结余科目</span>
							<input type="text" id="txt_fdbr" class="form-control input-radius window" name="zckmbh" value="${dwb.zckmbh}">
							<span class="input-group-btn"><button type="button" id="btn_fddbrh" class="btn btn-link btn-custom">选择</button></span>
						</div> 
					</div>--%>
					</div>
				</div>
				</form>
			</div>
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>结转模板明细信息</div>
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
				            <th style="text-align:center;"><span class="required" style="color:red ;">*</span>转出科目</th>
				            <th style="text-align:center;"><span class="required" style="color:red ;">*</span>转入科目</th>
				        </tr>
					</thead>
				    <tbody id="tbody" class="tbody">
				    	  <c:choose>
                             <c:when test="${not empty jzmxlist}">
  								<c:forEach var="jzmxlist" items="${jzmxlist}"> 
				    	         <tr id="tr_1">
				    		         <td class="btn_td">
				    			       <div class="addBtn add" ></div>
				    		         </td>				    		
				    		         <td>
				    		            <div class="input-group"> 
				    			<input type="text" id="txt_zckmm${jzmxlist.GUID }" style="background-color: white !important;" class=" input-radius  null" readonly="readonly" name="zckmm" value="${jzmxlist.ZCKMBH1 }">
 				    			<input type="hidden" id="txt_zckmid${jzmxlist.GUID }" class="form-control input-radius  " name="zckmid" value="${jzmxlist.ZCKMBH }">
 				    			<input type="hidden" id="txt_guid" class="form-control input-radius  " name="guid1" value="${jzmxlist.GUID }">
 				    			
 				    			
 				    			<span class="input-group-btn">
				    			<button type="button" id="btn_kkmbh${jzmxlist.GUID }" sj="txt_zckmm${jzmxlist.GUID }" mc="txt_zckmid${jzmxlist.GUID }" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    			
				    		 </div> 
				    		</td>
				    		<td id="td_1">
				    		<div class="input-group"> 
				    			<input type="text" id="txt_fddbrh1${jzmxlist.GUID }" style="background-color: white !important;" class=" input-radius b null a" readonly="readonly" name="srkmm" value="${jzmxlist.ZRKMBH1 }">
 				    			<input type="hidden" id="txt_fdbrh${jzmxlist.GUID }" class="form-control input-radius   a" name="srkmid" value="${jzmxlist.ZRKMBH }">
 				    			<input type="hidden" id="txt_szjzid" class="form-control input-radius   a" name="szjzid" value="${jzmxlist.ZJID }">
 				    			<span class="input-group-btn">
				    			<button type="button" id="btn_kmmbh${jzmxlist.GUID }" sj="txt_fddbrh1${jzmxlist.GUID }" mc="txt_fdbrh${jzmxlist.GUID }" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    			
				    		 </div> 
				    		</td>
				    		
				    	</tr>
				    	</c:forEach>
                          </c:when>
                          <c:otherwise>
										<tr id="tr_1">
				    		<td class="btn_td">
				    			<div class="addBtn" ></div>
				    		</td>				    		
				    		<td>
				    		<div class="input-group"> 
				    			<input type="text" id="txt_zckmm" class=" input-radius  null" name="zckmm" >
 				    			<input type="hidden" id="txt_zckmid" class=" input-radius  " name="zckmid" >
 				    			
 				    			
 				    			<span class="input-group-btn">
				    			<button type="button" id="btn_kkmbh" sj="txt_zckmm" mc="txt_zckmid" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    			
				    		 </div> 
				    		</td>
				    		<td id="td_1">
				    		<div class="input-group"> 
				    			<input type="text" id="txt_fddbrh1" class=" input-radius  null a" name="srkmm" >
 				    			<input type="hidden" id="txt_fdbrh" class="form-control input-radius   a" name="srkmid">
 				    			<input type="hidden" id="txt_szjzid" class="form-control input-radius   a" name="szjzid" value="${guid }">
 				    			<span class="input-group-btn">
				    			<button type="button" id="btn_kmmbh" sj="txt_fddbrh1" mc="txt_fdbrh" class="btn btn-link btn-custom">选择</button>
				    			</span>
				    			
				    		 </div> 
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
		$(function() { 
			addbutton();
			function addbutton(){
				var a = $(".tbody").find("tr").length;
				var aaa="${jzmxlist}";
				if(aaa.length>0){
					$(".add").removeClass("addBtn")				
					$(".add").addClass("deleteBtn");
					$(".tbody tr:last").find(".deleteBtn").addClass("addBtn");
					$(".tbody tr:last").find(".deleteBtn").removeClass("deleteBtn");
				}else{
					
				}
			}
			//验证方法
		  var validate = $('#myform1').bootstrapValidator({fields:{
          mbmc:{validators:{notEmpty: {message: '模板名称不能为空'}}},
          mbbh:{validators:{notEmpty: {message: '模板编号不能为空'}}},
          pzlx:{validators:{notEmpty: {message: '凭证类型不能为空'}}},
          }
	      });
		 // 明细 验证
		  function checkAutoInput(){
				tag = true;
				var lasttr = $("#table2").find("#tbody").find("tr");
				if(lasttr.length > 1){
					$.each(lasttr,function(){
						   var zckm = $(this).find("[name='zckmm']").val();
						   var zrkm = $(this).find("[name='srkmm']").val();
						   if(zckm=="" && zrkm !=""){
							  alert("一行数据请填写完整") ;
								tag = false;
						   }else  if(zckm !="" && zrkm ==""){
							  alert("一行数据请填写完整") ;
								tag = false;
						   }
						});
				}else{
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
				
			}
			//弹出框
			$("#btn_fddbrh").click(function(){
				select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_fdbrh1&controlId=txt_fddbrh&type=zr","收支结余科目信息","920","630");
		    });
// 			//弹出框
// 			$("#btn_fddbrh").click(function(){
// 				select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId=txt_fdbr","收支结余科目信息","920","630");
// 		    });
			//返回按钮
			$("#btn_back").click(function(){
				window.location.href = "${ctx}/webView/jzmb/jzmb_list.jsp";
			});
			//单选按钮传值
			$("#btn_nnoff").click(function(){
				if($("[name='sfbhwjzpz']").val()=='0'){
					$("[name='sfbhwjzpz']").val("1");
				}else if($("[name='sfbhwjzpz']").val()=='1'){
					$("[name='sfbhwjzpz']").val("0");
				}else{
					$("[name='sfbhwjzpz']").val("1");
				}
			});
			 $(document).on("click",".deleteBtn",function(){
				 $(this).parents("tr").remove();
			 });
			//保存
			$("#btn_save").click(function(e){
				checkAutoInput();
				doSave1(validate,$("#myform1"),"${ctx}/jzmb/doSave?operateType=U",function(val){
				});
			});
			//保存方法	
			function doSave1(_validate, $form, _url, _success, _fail){
				if(_validate){
					_validate.bootstrapValidator("validate");
					var valid = $form.data('bootstrapValidator').isValid();
					if(!valid){
						return;
					}
				}
				if(!tag){
					valid = false;
				}
				if(valid){
					$.ajax({
						type:"post",
						url:_url,
						data:arrayToJson($form.serializeArray()),
						success:function(data){
							next();
						},
						error:function(val){
							alert("抱歉，系统出现问题！");
						},
					});	
				}
				
			}
			function next(){
			 	var json = $("#table2").serializeObject1("zckmm","szjzid");  //json对象				
				var jsonStr = JSON.stringify(json);  //json字符串
				var guid=$("#guid").val();
				$.ajax({
				   			url:"${ctx}/jzmb/editjzmbmx?guid="+guid,
				   			type:"post",
				   			data:"json="+jsonStr,
				   			success:function(data){
				   				if(data == "success"){
				   					alert("保存成功");  
									window.location.href = "${ctx}/webView/jzmb/jzmb_list.jsp";
				   				}else{
				   					alert("保存失败");  
				   				}
				   			}
						}); 
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
			    return f;
			};
			//刷新按钮
			$(".reload").click(function() {
				window.location.reload();
			});
			var num = 2;
			 $(document).on("click","[class*=addBtn]",function(){
				var szjzid = $("#txt_szjzid").val();
				var $parentTr = $(this).parents("tr").clone();
				$(this).removeClass("addBtn");
				$(this).addClass("deleteBtn");
				$parentTr.find(":input").val("");
				$parentTr.find(":input").removeClass("border");
				$parentTr.attr("id","tr_"+num);
				$parentTr.find("#txt_szjzid").val(szjzid);
				$parentTr.find("[id^=txt_zckmm]").attr({"id":"txt_zckmm"+num});
				$parentTr.find("[id^=txt_zckmid]").attr({"id":"txt_zckmid"+num});
				$parentTr.find("[id^=btn_kkmbh]").attr({"sj":"txt_zckmm"+num,"mc":"txt_zckmid"+num});
				$parentTr.find("[id^=txt_fddbrh1]").attr({"id":"txt_fddbrh1"+num});
				$parentTr.find("[id^=txt_fdbrh]").attr({"id":"txt_fdbrh"+num});
				$parentTr.find("[id^=btn_kmmbh]").attr({"sj":"txt_fddbrh1"+num,"mc":"txt_fdbrh"+num});
				num++;
				$(this).parents("tr").after($parentTr);
			});
		});
		//第一个经济科目弹窗
		 $(document).on("click","[id^=btn_kkmbh]",function(){
			var controlId = $(this).attr("mc"); 
			var controlId1 = $(this).attr("sj");
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1="+controlId1+"&controlId="+controlId,"收支结余科目信息","920","630");
	     });
		 //第二个经济科目弹窗
		 $(document).on("click","[id^=btn_kmmbh]",function(){
				var controlId = $(this).attr("mc"); 
				var controlId1 = $(this).attr("sj");
				select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1="+controlId1+"&controlId="+controlId,"收支结余科目信息","920","630");
		 });
		$(document).on("focus","[class*=border]",function(){
			$(this).removeClass("border");
		});
		
		 function addkmmc(kmmc){
			  $(".b").val(kmmc);
		  }
	</script>
</body>
</html>