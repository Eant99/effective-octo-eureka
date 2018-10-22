<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目类型详细页面</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.tables {
    margin-left: 20px;
    max-width: 100%;
    margin-bottom: 20px;
    width:50%;
   }
 .p1{
    text-align:center;
    font-family:宋体;
    color:red;
    font-size:18px;
   }
 [class^=td_first_]{
 	text-align:center;
 	width:15%;
 	height:40px;
 }
 [class^=td_second_]{
 	width:45%;
 	height:30px;
 }
 [class^=td_three_]{
 	width:45%;
 	height:30px;
 }
 .td_input{
 	width:100%;
 	border:none;
 }
 a{
	 text-decoration:none;
 }
 .addBtn0,.addBtn1,.addBtn2,.addBtn3{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn0:after,.addBtn1:after,.addBtn2:after,.addBtn3:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.delBtn1,.delBtn2,.delBtn3{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.delBtn1:after,.delBtn2:after,.delBtn3:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
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
</style>
</head>
<body>
<div id="myform"  >
  
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="czr" value="${loginId}">
	<input type="hidden" name="guid" value="${guid}">
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	查看项目类型信息
			</span>
		</div>
        <div class="pull-right">
<!-- 			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button> -->
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目类型信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<form id ="myform1">
			<div class="container-fluid box-content">
			 <input type="hidden" name="czr" value="${loginId}">
	         <input type="hidden" name="guid" id="guid" value="${guid}">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">				
						    <span class="input-group-addon">项目类型编号</span>
							<input type="text" id="txt_bmh" class="form-control input-radius" readonly="readonly" name="xmlxbh"  value="${map.XMLXBH}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
								<span class="input-group-addon">项目类型名称</span>
							<input type="text" id="txt_mc" class="form-control input-radius" name="xmlxmc" value="${map.XMLXMC}" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否财政支出</span>
                             <div class="radiodiv">
                              <input name="sfczzc" id="checkNoHaveOper"  type="radio" value="1" disabled<c:if test="${map.SFCZZC=='1'}"> checked="checked" </c:if> />
                              <label >是</label>&emsp;
                              <input name="sfczzc" id="checkNoHaveOper"  type="radio" value="0" disabled<c:if test="${map.SFCZZC=='0'}"> checked="checked"</c:if> />
                              <label >否</label>
                              </div>
						</div>
					</div>
				</div>
				<!-- 2  -->
				<div class="row">
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon"><span class="required"></span>预算类型</span> -->
<!--                                 <select id="drp_dwbb" class="form-control input-radius " name="yslx" disabled> -->
<%-- 	                                <c:forEach var="yslxlist" items="${yslxlist}">  --%>
<%-- 	                                   <option value="${yslxlist.DM}" <c:if test="${map.YSLX == yslxlist.DM}">selected</c:if>>${yslxlist.MC}</option> --%>
<%-- 									</c:forEach> --%>
<!-- 	                            </select> -->
<!-- 						</div> -->
<!-- 					</div>				 -->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目类别</span>
                               <select id="drp_dwbb" class="form-control input-radius " name="xmlb" disabled>
                               	 	<c:forEach var="xmlblist" items="${xmlblist}" > 
                                   		<option value="${xmlblist.DM}" <c:if test="${map.XMLB == xmlblist.DM}">selected</c:if>>${xmlblist.MC}</option>
									</c:forEach>
                               </select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">项目收支说明</span>
	                        <textarea id="txt_bz"  class="form-control" name="xmszsm" style=" height: 50px; width: 100%;" readonly>${map.XMSZSM }</textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备注</span>
	                        <textarea id="txt_bz"  class="form-control" name="bz" style=" height: 50px; width: 100%;" readonly>${map.bz }</textarea>
						</div>
					</div>
				</div>
					
			</div>
			</form>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	function addbuttonsr(){
		var a = $(".tbody1").find("tr").length;
		var aaa="${srmap}";
		
		if(aaa.length>2){
			$(".add1").removeClass("addBtn1")
			
			$(".add1").addClass("delBtn1");		
			var $parentTr = $(".tbody1 tr:last").clone();
		//	$parentTr.find("input:not(.a)").attr("value","");
	        $parentTr.find("input:not(.a)").val("");
			$parentTr.attr("id","pbtr_1");
			$(".tbody1 tr:last").after($parentTr);
			
			$(".tbody1 tr:last").find(".add1").removeClass("delBtn1");
			$(".tbody1 tr:last").find(".add1").addClass("addBtn1");
		}else{
			
		}
	}
	function addbuttonzc(){
		var a = $(".tbody2").find("tr").length;
		var aaa="${zcmap}";
		if(aaa.length>2){
			$(".add2").removeClass("addBtn2")
			
			$(".add2").addClass("delBtn2");		
			var $parentTr = $(".tbody2 tr:last").clone();
			$parentTr.find("input:not(.b)").val("");
			$parentTr.attr("id","zctr_1");
			$(".tbody2 tr:last").after($parentTr);
			
			$(".tbody2 tr:last").find(".add2").removeClass("delBtn2");
			$(".tbody2 tr:last").find(".add2").addClass("addBtn2");
		}else{
			
		}
	}
	function addbuttonjjfl(){
		var a = $(".tbody3").find("tr").length;
		var aaa="${jjflmap}";
		
		if(aaa.length>2){
			$(".add3").removeClass("addBtn3")
			
			$(".add3").addClass("delBtn3");		
			var $parentTr = $(".tbody3 tr:last").clone();
			$parentTr.find("input:not(.c)").val("");
			$parentTr.attr("id","jjfltr_1");
			$(".tbody3 tr:last").after($parentTr);
			
			$(".tbody3 tr:last").find(".add3").removeClass("delBtn3");
			$(".tbody3 tr:last").find(".add3").addClass("addBtn3");
		}else{
			
		}
	}
	//返回按钮
	$("#btn_back").click(function(e){
		window.location.href  = "${ctx}/xmlx/goXmlxListPage";
	});
	//联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_fgld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_sjdw").bindChange("${ctx}/suggest/getXx","D");	
	//必填验证
	var validate = $('#myform1').bootstrapValidator({fields:{
				xmlxbh:{validators:{notEmpty:{message:'不能为空'}}},
				xmlxmc:{validators:{notEmpty: {message: '不能为空'}}},
				sfczzc:{validators:{notEmpty: {message: '不能为空'}}},
				srkm:{validators:{notEmpty: {message: '不能为空'}}},
				xmlb:{validators:{notEmpty:{message:'不能为空'}}},
         }});
	//保存按钮
	$("#btn_save").click(function(e){	
		checkAutoInput();
			doSave1(validate,"myform1","${ctx}/xmlx/doSave?operateType=U",function(val){
			
 		});
	});	
	function doSave1(_validate, _formId, _url, _success, _fail){
		checkAutoInput();
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $("#myform1").data('bootstrapValidator').isValid();
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
				data:arrayToJson($("#myform1").serializeArray()),
				success:function(val){	
					next();
					
				},
				error:function(val){
				}	
			});
		}
	}
	//查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	//页面加载完后，控制实验室信息模块是否展示
	sysbz();
	//onoff按扭切换
	$("#btn_onoff").click(function(){
		if($("[name='sysbz']").val()=='0'){
			$("[name='sysbz']").val("1");
		}else if($("[name='sysbz']").val()=='1'){
			$("[name='sysbz']").val("0");
		}else{
			$("[name='sysbz']").val("0");
		}
		sysbz();
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
	    return f;
	};

	//nnoff按扭切换
	$("#btn_nnoff").click(function(){
		if($("[name='sfxy']").val()=='0'){
			$("[name='sfxy']").val("1");
		}else if($("[name='sfxy']").val()=='1'){
			$("[name='sfxy']").val("0");
		}else{
			$("[name='sfxy']").val("1");
		}
	});

	//dnoff按扭切换
	$("#btn_dnoff").click(function(){
		if($("[name='dwzt']").val()=='0'){
			$("[name='dwzt']").val("1");
		}else if($("[name='dwzt']").val()=='1'){
			$("[name='dwzt']").val("0");
		}else{
			$("[name='dwzt']").val("1");
		}
	});
	//收入
	 var num = 2;
// 	 $(document).on("click","[class*=addBtn1]",function(){
		
// 		var $parentTr = $(this).parents("tr").clone();
// 		$(this).removeClass("addBtn1");
// 		$(this).addClass("delBtn");
// 		$parentTr.find("input:not(.a)").val("");
// 		$parentTr.find(":input").removeClass("border");
// 		$parentTr.attr("id","tr_"+num);
// 		//转入
// 		$parentTr.find("[id^=txt_srkm]").attr({"id":"txt_srkm"+num});
// 		$parentTr.find("[id^=txt_srkbh]").attr({"id":"txt_srkbh"+num});
// 		$parentTr.find("[id^=btn_srkm]").attr({"id":"btn_srkm"+num,"mc":"txt_srkm"+num,"bh":"txt_srkbh"+num});
// 		num++;
// 		$(this).parents("tr").after($parentTr);
// 	});
	//支出
	 var num = 2;
// 	 $(document).on("click","[class*=addBtn2]",function(){
		
// 		 var $parentTr = $(this).parents("tr").clone();
// 			$(this).removeClass("addBtn2");
// 			$(this).addClass("delBtn");
// 			$parentTr.find("input:not(.b)").val("");
// 			$parentTr.find(":input").removeClass("border");
// 			$parentTr.attr("id","tr_"+num);
// 			//支出
// 			$parentTr.find("[id^=txt_zckm]").attr({"id":"txt_zckm"+num});
// 			$parentTr.find("[id^=txt_zckbh]").attr({"id":"txt_zckbh"+num});
// 			$parentTr.find("[id^=btn_zckm]").attr({"id":"btn_zckm"+num,"mc":"txt_zckm"+num,"bh":"txt_zckbh"+num});
// 			num++;
// 			$(this).parents("tr").after($parentTr);
		 
// 	 });
	 var num = 2;
// 	 $(document).on("click","[class*=addBtn3]",function(){
		 
// 		 var $parentTr = $(this).parents("tr").clone();
// 			$(this).removeClass("addBtn3");
// 			$(this).addClass("delBtn");
// 			$parentTr.find("input:not(.c)").val("");
// 			$parentTr.find(":input").removeClass("border");
// 			$parentTr.attr("id","tr_"+num);
// 			//支出
// 		$parentTr.find("[id^=txt_jjflkm]").attr({"id":"txt_jjflkm"+num});
// 		$parentTr.find("[id^=txt_jjflkbh]").attr({"id":"txt_jjflkbh"+num});
// 		$parentTr.find("[id^=btn_jjflkm]").attr({"id":"btn_jjflkm"+num,"mc":"txt_jjflkm"+num,"bh":"txt_jjflkbh"+num});
// 			num++;
// 			$(this).parents("tr").after($parentTr);
// 	 });
	 
// 		$(document).on("focus","[class*=border]",function(){
// 			$(this).removeClass("border");
// 		});
// 		 $(document).on("click",".delBtn1",function(){
// 			 $(this).parents("tr").remove();				 
// 		 });
// 		 $(document).on("click",".delBtn2",function(){
// 			 $(this).parents("tr").remove();				 
// 		 });
// 		 $(document).on("click",".delBtn3",function(){
// 			 $(this).parents("tr").remove();				 
// 		 });
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
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&dwbh=${dwb.DWBH}";
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016";
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016";
	    	}
		 }
	});
});
function sysbz(){
	var $this = $("[name='sysbz']").val();
	if($this == '0'){
		$("#sysxx").show();
	}else{
		$("[name='syslx']").val("");
		$("[name='syslb']").val("");
		$("[name='sysmj']").val("0.00");
		$("[name='sysjb']").val("");
		$("[name='ssxk']").val("");
		$("[name='sysgs']").val("");
		$("#sysxx").hide();
	}
}
$(function(){
	$("[name='dwzt']").change(function(){
		if($("[name='dwzt']").val()=='0'){
		 $.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/dwb/getNewStatus",
			data:"dwbh=${dwb.DWBH}",
			success:function(val){
				var data = JSON.getJson(val);
				 if(data.success=='false'){
					alert(data.msg);
					$("#drp_dwzt option").eq(0).attr("selected",true);
					$("#drp_dwzt option").eq(1).attr("selected",false);
				} 
			},
		}); 
			
		}
	});
});
function next(){
	var srlength=$("#pbtr_1").find("[name=kmbh]").val();
// 	var zclength=$("#zctr_1").find("[name=kmbh]").val();
	var jjfllength=$("#jjfltr_1").find("[name=kmbh]").val();
	var srlength1=$("#pbtr_1").find("[name=kmmc]").val();
// 	var zclength1=$("#zctr_1").find("[name=kmmc]").val();
	var jjfllength1=$("#jjfltr_1").find("[name=kmmc]").val();
	if(srlength.length==0 &&srlength1.length==0){
		$("#pbtr_1").remove();
	}
// 	if(zclength.length==0&&zclength1.length==0){
// 		$("#zctr_1").remove();
// 	}
	if(jjfllength.length==0&&jjfllength1.length==0){
		$("#jjfltr_1").remove();
	}
	var mytable = $(".myform");
	var xmlxbh = $("#guid").val();
	var aryId = [];
	$.each(mytable,function(i,v){
		var $this = $(this);
		var id = $this.attr("id");
		var mc = $this.attr("mc")
		
	var json = $("#"+id+"").serializeObject1("kmbh","kmmc");  //json对象		
	var jsonStr = JSON.stringify(json);  //json字符串
	$.ajax({
		        url:"${ctx}/xmlx/"+mc+"?xmlxbh="+xmlxbh,
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				// 
				//window.location.href = "${ctx}/pzlx/gopzlxListPage";
	   			}
	
			}); 
	});
	alert("保存成功！"); 
	window.location.href = "${ctx}/webView/ysgl/xmsz/xmlx/xmlx_list.jsp";
}
function addsrkm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".aa");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
		   var $parentTr = $("#pbtr_1").clone();
			$parentTr.removeAttr("id");
			$parentTr.find("input:not(.a)").val("");
			$parentTr.find("[id=txt_srkbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_srkm]").val(kmmc[i]);		
			$("#pbtr_1").before($parentTr);
			$(".addBtn1").attr("class","delBtn1");		
			$(".delBtn1:last").attr("class","addBtn1");
		 }
	}
	
}
function addzckm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".bb");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
		   var $parentTr = $("#zctr_1").clone();
			$parentTr.removeAttr("id");
			$parentTr.find("input:not(.b)").val("");
			$parentTr.find("[id=txt_zckbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_zckm]").val(kmmc[i]);		
			$("#zctr_1").before($parentTr);
			$(".addBtn2").attr("class","delBtn2");		
			$(".delBtn2:last").attr("class","addBtn2");
		 }
	}
	
}
function addjjflkm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".cc");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
		   var $parentTr = $("#jjfltr_1").clone();
			$parentTr.removeAttr("id");
			$parentTr.find("input:not(.c)").val("");
			$parentTr.find("[id=txt_jjflkbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_jjflkm]").val(kmmc[i]);		
			$("#jjfltr_1").before($parentTr);
			$(".addBtn3").attr("class","delBtn3");		
			$(".delBtn3:last").attr("class","addBtn3");
		 }
	}
	
}
</script>

</html>