<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>银行帐号管理</title>
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
</style>
</head>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		<c:if test="${'sh'!=sh && 'ck'!=ck}">
		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
		</c:if>
		<button type="button" class="btn btn-default" id="btn_back"><i class="fa icon-save"></i>返回</button>
<%-- 		<input type="hidden" name="operateType" id="operateType" value="${operateType}"> --%>
		<center><h4><strong>维修租赁费支出预算表</strong></h4></center>
			<div class="search-simple">	
				<div class="form-group"> 
					<label>填报部门</label>					
 					<input type="text" class="form-control input-radius" name="tbbm"  id="txt_tbbm" readonly="readonly" value="${bmmc}">
				 </div> 
				 <div class="pull-right">
					<label>单位：万元</label>
				 </div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area' style="overflow-x:scroll;width: 100%;"> 
			<form id="myform1">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse;overflow-x:scroll;width: 1800px;">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align: center;">操作</th>
				            <th style="text-align: center;">项目名称</th>
				            <th style="text-align: center;">项目主要依据及摘要说明</th>
				            <th style="text-align: center;">项目开始时间</th>
				            <th style="text-align: center;">项目截止时间</th>
				            <th style="text-align: center;">项目总投资（万元）</th>
				            <th style="text-align: center;">2017年安排投资数</th>
				            <th style="text-align: center;">2018年预算建议安排数</th>
				            <th style="text-align: center;">项目付款时间要求（月份）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    
				    
				    <c:forEach items="${list}" var="list" varStatus="i">
				    	<tr id="tr_${i.index+1}">
				    		<c:if test="${list.xmmc!=null}">
				    		<td class="btn_td">
	                               <div class="deleteBtn"></div>
				    		</td>
				    		</c:if>
				    		<td>
				    			<div class="input-group">
				    				<input type="hidden" name="guid" value="">
				    				<input type="text" id="txt_xmmc${i.index+2}" a="a" class="form-control input-radius null" style="width:100%;" readonly="readonly" name="XMMC" value="${list.xmmc}">
				    				<input type="hidden" id="hdn_xmmc${i.index+2}" name="XMID" value="${list.xmid}">
				    				<span><button type="button" id="btn_xmmc${i.index+2}" name="btn_xz" class="btn btn-link btn-custom">选择</button></span>
				    			</div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh${i.index+2}" a="a" class="form-control input-radius null" name="XMZYYJJJYSM" value="${list.XMZYYJJJYSM}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh${i.index+2}" a="a" class="form-control input-radius null date" name="XMQZSJ" value="${list.XMQZSJ}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh${i.index+2}" a="a" class="form-control input-radius null date" name="XMJSSJ" value="${list.XMJSSJ}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh${i.index+2}" a="a" class="form-control input-radius null number1" style="text-align: right"  name="XMZTZ" value="${list.XMZTZ}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh${i.index+2}" a="a" class="form-control input-radius null number1"  style="text-align: right"  name="NAPZJS" value="${list.NAPZJS}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh${i.index+2}" a="a" class="form-control input-radius null number1"  style="text-align: right"  name="NYSJYAPS" value="${list.NYSJYAPS}">
				    		</td>
				    		<td>
				    			<div class="input-group">
									<input type="text" id="txt_jlrq" class="form-control date" name="XMFKSJYQ" value="${list.XMFKSJYQ}" >
									<span class='input-group-addon'>
										<i class="glyphicon glyphicon-calendar"></i>
									</span>
								</div>
				    		</td>
				    	</tr>
				    	</c:forEach>
				    	<tr id="tr_1">
				    		<td class="btn_td">
	                               <div class="addBtn"></div>
				    		</td>
				    		<td>
				    			<div class="input-group">
				    				<input type="hidden" name="guid" value="">
				    				<input type="text" id="txt_xmmc1" a="a" class="form-control input-radius null" style="width:100%;" readonly="readonly" name="XMMC" value="">
				    				<input type="hidden" id="hdn_xmmc1" name="XMID" value="">
				    				<span><button type="button" id="btn_xmmc1" name="btn_xz" class="btn btn-link btn-custom">选择</button></span>
				    			</div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="XMZYYJJJYSM" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null date" name="XMQZSJ" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null date" name="XMJSSJ" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null number1" style="text-align: right"  name="XMZTZ" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null number1"  style="text-align: right"  name="NAPZJS" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null number1"  style="text-align: right"  name="NYSJYAPS" value="">
				    		</td>
				    		<td>
				    			<div class="input-group">
									<input type="text" id="txt_jlrq" class="form-control date" name="XMFKSJYQ" value="" >
									<span class='input-group-addon'>
										<i class="glyphicon glyphicon-calendar"></i>
									</span>
								</div>
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
	
  	//新增按钮
	var num = "${list.size()+2}";
	$(document).on("click","[class*=addBtn]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find("[a='a']").val("");
		$parentTr.find(":input").removeClass("border");
// 		$parentTr.attr("id","tr_"+num);
		$parentTr.find("[name='XMID']").attr("id","hdn_xmmc"+num);
		$parentTr.find("[name='XMMC']").attr("id","txt_xmmc_"+num);
		$parentTr.find("[name='btn_xz']").attr("id","btn_xmmc_"+num);
		$parentTr.find("[id^=btn_xmmc]").attr({"sj":"txt_xmmc_"+num,"mc":"txt_zckmid"+num,id:"btn_xmmc"+num});
		
		num++;
		$(this).parents("tr").after($parentTr);
	});

	$(document).on('click',"[id^=btn_xmmc]",function(){
		var controlId = $(this).parents("tr").find("[name='XMMC']").attr("id");
		var id = $(this).parents("tr").find("[name='XMID']").attr("id");
		select_commonWin("${ctx}/xmxxt/goXmxxTree?controlId="+controlId+"&id="+id,"项目信息","920","630");
	});
	
	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();

	});
 
	//必填验证
	var fields = {fields:{
		xmmc:{validators:{notEmpty:{message:'不能为空'}}}
	} };
	//保存
    $("#btn_save").click(function(){
	   	var url = "${ctx}/ysgl/bmyssb/doSave?operateType=C&dm=5&bzid=${bzid}";
	   	if($("#txt_xmmc").val()==""){
			alert("项目名称不能为空！");
		}else{
			doSave1($("#myform1"),url,function(val){
				var result = JSON.getJson(val);
				if(result.success){
					alert("保存成功！");
					window.location = "${ctx}/ysgl/bmyssb/goListPage?operateType=C&dm=5&bzid=${bzid}";
				}
			});
		}
	});
//   //弹框
// 	$("#btn_xmmc").click(function(){
// 		select_commonWin("${ctx}/xmxxt/goXmxxTree?controlId=txt_xmmc","项目信息","920","630");
// 	});
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
    //保存方法	
//     function doSave1($form, _url, _success, _fail){
//     	var json = $("#myform1").serializeObject("guid","xmfksjyq");
//     	var name = $("#txt_tbbm").attr("name");
//     	var value = $("#txt_tbbm").val();
//     	json[name]=value;
//     	var jsonStr = JSON.stringify(json);
//     	console.log("________________1___________"+jsonStr);
//     	$.ajax({
//     		type:"post",
//     		url:_url,
//     		data:"json="+jsonStr,
//     		success:function(data){
//     			_success(data);
//     		},
//     		error:function(val){
//     			alert("抱歉，系统出现问题！");
//     		},
//     	});	
//     }
  //保存方法	
    function doSave1($form, _url, _success, _fail){
    	var json = $("#myform1").serializeObject("guid","XMFKSJYQ");
    	console.log("json==="+json);
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
	
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/kjhs/yhzhgl/yhzhgl_edit.jsp","C");
   	});
   	$(document).on("blur", ".number1", function(e){
		$(this).Num(4,true,false,e);
		return false;
	});

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