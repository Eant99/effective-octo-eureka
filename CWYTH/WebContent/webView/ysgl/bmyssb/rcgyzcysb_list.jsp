<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
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
	#tbody input,bk{
		border:none;
		width:100%;
	    padding:4px !important;
	} 
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		<c:if test="${'sh'!=sh && 'ck'!=ck}">
		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
		</c:if>
		<button type="button" class="btn btn-default" id="btn_back"><i class="fa icon-save"></i>返回</button>
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<center><h4><strong>日常公用支出预算表</strong></h4></center>
			<div class="search-simple">	
				<div class="form-group"> 
					<label>填报部门</label>					
 					<input type="text" class="form-control input-radius bk" name="tbbm"  id="txt_tbbm" readonly="readonly" value="${bmmc}">
				 </div> 
				 <div class="pull-right">
					<label>单位：万元</label>
				 </div>
			</div>
		</form>
	</div>
	<div class="container-fluid" style="margin-top:70px;">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<form id="myform1">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align: center;">操作</th>
				            <th style="text-align: center;">费用名称</th>
				            <th style="text-align: center;">摘要说明</th>
				            <th style="text-align: center;">2017年预算总额(万元)</th>
				            <th style="text-align: center;">2018年预算建议安排数（万元）</th>
				            <th style="text-align: center;">计算过程（依据）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    
				    
				    <c:forEach items="${list}" var="list" varStatus="i">
				    	<tr id="tr_${i.index+1}">
				    		<td class="btn_td">
				    			   <c:if test="${list.fymc!=null}">
	                               		<div class="deleteBtn"></div>
	                               </c:if>
				    		</td>
				    		<td>
				    			<input type="hidden" id="txt_khyh1" a="a" class="form-control input-radius null" name="guid" value="">
				    			<input type="text" id="txt_fymc" a="a" class="form-control input-radius null" name="fymc" value="${list.fymc}" >
<!-- 				    			<span><button type="button" id="btn_xmmc" class="btn btn-link btn-custom">选择</button></span> -->
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class=" input-radius null" name="zysm" value="${list.zysm}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class=" input-radius null number1" style="text-align: right;" name="nysze" value="${list.nysze}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class=" input-radius null number1" style="text-align: right;" name="nysjyaps" value="${list.nysjyaps}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class=" input-radius null " style="text-align: right;" name="jsgc" value="${list.jsgc}">
				    		</td>
				    	</tr>
				    	
				    </c:forEach>
				    <tr id="tr_0">
				    		<td class="btn_td">
	                               		<div class="addBtn"></div>
				    		</td>
				    		<td>
				    			<input type="hidden" id="txt_khyh1" a="a" class=" input-radius null" name="guid" value="">
				    			<input type="text" id="txt_fymc" a="a" class=" input-radius null" name="fymc" value="" >
<!-- 				    			<span><button type="button" id="btn_xmmc" class="btn btn-link btn-custom">选择</button></span> -->
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class=" input-radius null" name="zysm" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class=" input-radius null number1" style="text-align: right;" name="nysze" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class=" input-radius null number1" style="text-align: right;" name="nysjyaps" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class=" input-radius null " style="text-align: right;" name="jsgc" value="">
				    		</td>
				    	</tr>
				    </tbody>
				</table>
			</form>
			</div>
		</div>
	</div>	
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	//弹框
	$("#btn_xmmc").click(function(){
		select_commonWin("${ctx}/xmxxt/goXmxxTree?controlId=txt_xmmc","项目信息","920","630");
	});
	//必填验证
	var fields = {fields:{
		xmmc:{validators:{notEmpty:{message:'不能为空'}}}
	} };
	//保存
    $("#btn_save").click(function(){
	   	var url = "${ctx}/ysgl/bmyssb/doSave?operateType=C&dm=8";
	   	if($("#txt_xmmc").val()==""){
			alert("项目名称不能为空！");
		}else{
			doSave1($("#myform1"),url,function(val){
				var result = JSON.getJson(val);
				if(result.success){
					alert("保存成功！");
					window.location = "${ctx}/ysgl/bmyssb/goListPage?operateType=C&dm=8&bzid=${bzid}";
				}
			});
		}
	});
  //返回按钮
$("#btn_back").click(function(e){
				var sh="${param.sh}";
				var ck="${param.ck}";
				if(sh=="sh"&&ck=="ck"){
					parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
					return;
				}
				if(sh=="sh"&&ck!="ck"){
					parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
					return;
				}
				if(ck=="ck"&&sh!="sh"){
					parent.location.href  = "${ctx}/ysgl/bmyssb/goBmyssbPage";
				}
// 				if(sh==''||sh==null||sh==undefined){
// 					if(ck==''||ck==null||ck==undefined){
// 					parent.location.href  = "${ctx}/ysgl/bmyssb/goBmyssbPage";
// 					}else{
// 						parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
// 					}
// 				}else{
// 				parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
// 				}
		});
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
 
	//保存方法	
    function doSave1($form, _url, _success, _fail){
//     	var validator = $('#myform1').bootstrapValidator(fields);
//     	if(validator){
//     		validator.bootstrapValidator("fields");
//     		var valid = $('#myform1').data('bootstrapValidator').isValid();
//     		if(!valid){
//     			return;
//     		}
//     	}
    	var json = $("#myform1").serializeObject("guid","jsgc");
    	var name = $("#txt_tbbm").attr("name");
    	var value = $("#txt_tbbm").val();
    	json[name]=value;
    	var jsonStr = JSON.stringify(json);
    	console.log("_________"+jsonStr);
    	$.ajax({
    		type:"post",
    		url:_url,
    		data:"json="+jsonStr,
    		success:function(data){
    			_success(data);
    		},
    		error:function(val){
    			alert("抱歉，系统出现问题！");
    		},
    	});	
    }
    //弹框
	$("#btn_dwmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_tbbm","单位信息","920","630");
	});
});
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</body>
</html>