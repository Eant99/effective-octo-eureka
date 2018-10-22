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
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		<c:if test="${'sh'!=sh && 'ck'!=ck}">
		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
		</c:if>
		<button type="button" class="btn btn-default" id="btn_back"><i class="fa icon-save"></i>返回</button>
<%-- 		<input type="hidden" name="operateType" id="operateType" value="${operateType}"> --%>
<%-- 		<input type="hidden" name="guid" value="${dwb.guid}">  --%>
<%-- 		<input type="hidden" name="czr"	value="${loginId}"> --%>
		<center><h4><strong>通用设备采购</strong></h4></center>
			<div class="search-simple">	
				<div class="form-group"> 
					<label>填报部门</label>					
 					<input type="text" class="form-control input-radius" name="tbbm"  id="txt_tbbm" readonly value="${bmmc }">
<!--  					<button type="button" id="btn_dwmc" class="btn btn-link btn-custom">选择</button> -->
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
		        			<td rowspan="2"></td>
		        			<th style="text-align: center;" colspan="3" rowspan="2">采购项目</th>
		        			<th style="text-align: center;" colspan="5">2018年预算安排</th>
		        			<th style="text-align: center;" rowspan="3">供货时间要求</th>
				            <th style="text-align: center;" rowspan="3">备注</th>
				            <th style="text-align: center;" rowspan="3">价格测算</th>
		        		</tr>
		        		<tr>
		        			<th style="text-align: center;" colspan="2">资产数量</th>
		        			<th style="text-align: center;" rowspan="2">计划购置数</th>
		        			<th style="text-align: center;" rowspan="2">单价（元）</th>
				            <th style="text-align: center;" rowspan="2">采购预算金额</th>
		        		</tr>
				        <tr>
				        	<th style="text-align: center;">操作</th>
				            <th style="text-align: center;">项目编码</th>
				            <th style="text-align: center;">项目名称</th>
<!-- 				            <th style="text-align: center;">设备分类</th> -->
				            <th style="text-align: center;">规格要求</th>
				            <th style="text-align: center;">单位现有</th>
				            <th style="text-align: center;">配置限额</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				     
				    <c:forEach items="${list}" var="list" varStatus="i">
				    	<tr id="tr_${i.index+1}">
				    		<c:if test="${list.PMBM!=null}">
				    		<td class="btn_td">
	                               <div class="deleteBtn"></div>
				    		</td>
				    		</c:if>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="PMBM" value="${list.PMBM }">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="PMMC" value="${list.PMMC }">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="SBFL" value="${list.SBFL }">
				    		</td>
<!-- 				    		<td> -->
<!-- 				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="GGYQ" value=""> -->
<!-- 				    		</td> -->
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null " name="DWXYZJSL" style="text-align: right;" value="${list.DWXYZJSL }">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null number" name="PZXEZJSL" style="text-align: right;" value="${list.PZXEZJSL }">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null " name="JHPZS" style="text-align: right;" value="${list.JHGZS }">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null number" name="DJ" style="text-align: right;" value="${list.DJ }">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null number" name="CGYSJE" style="text-align: right;" value="${list.CGYSJE }">
				    		</td>
				    		<td>
				    			<div class="input-group">
									<input type="text" id="txt_jlrq" a="a" class="form-control date" name="GHSJYQ"  value="${list.GHSJYQ}">
									<span class='input-group-addon'>
										<i class="glyphicon glyphicon-calendar"></i>
									</span>
								</div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="BZ" value="${list.BZ }">
				    		</td>
				    		<td>
				    			<input type="button" id="jgcs${i.index+2}" name="jgcs" value="价格测算" />
				    		</td>
				    	</tr>
				    	</c:forEach>
				    	<tr id="tr_1">
				    		<td class="btn_td">
	                               <div class="addBtn"></div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="PMBM" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="PMMC" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="SBFL" value="">
				    		</td>
<!-- 				    		<td> -->
<!-- 				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="GGYQ" value=""> -->
<!-- 				    		</td> -->
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null " style="text-align: right;" name="DWXYZJSL" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null number" style="text-align: right;" name="PZXEZJSL" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null " style="text-align: right;" name="JHPZS" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null number" style="text-align: right;" name="DJ" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null number" style="text-align: right;" name="CGYSJE" value="">
				    		</td>
				    		<td>
				    			<div class="input-group">
									<input type="text" id="txt_jlrq" a="a" class="form-control date" name="GHSJYQ"  value="">
									<span class='input-group-addon'>
										<i class="glyphicon glyphicon-calendar"></i>
									</span>
								</div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="BZ" value="">
				    		</td>
				    		<td>
				    			<input type="button" id="jgcs1" name="jgcs" value="价格测算" />
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
	//必填验证
	var fields = {fields:{
		xmmc:{validators:{notEmpty:{message:'不能为空'}}}
	} };
	//保存
    $("#btn_save").click(function(){
	   	var url = "${ctx}/ysgl/bmyssb/doSave?operateType=C&dm=10";
		doSave1($("#myform1"),url,function(val){
			var result = JSON.getJson(val);
			if(result.success){
				alert("保存成功！");
				window.location = "${ctx}/ysgl/bmyssb/goListPage?operateType=C&dm=10";
			}
		});
	});
	//返回
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
    function doSave1($form, _url, _success, _fail){
//     	var validator = $('#myform1').bootstrapValidator(fields);
//     	if(validator){
//     		validator.bootstrapValidator("fields");
//     		var valid = $('#myform1').data('bootstrapValidator').isValid();
//     		if(!valid){
//     			return;
//     		}
//     	}
        var tbbm = $("[name='tbbm']").val();
      	var json = $("#myform1").serializeObject("PMBM","BZ");
        var name = $("#txt_tbbm").attr("name");
      	var value = $("#txt_tbbm").val();
      	json[name]=value;
      	var jsonStr = JSON.stringify(json);
      	//var formJson = arrayToJson($("#myform1").serializeArray());
      	//formJson.json = jsonStr;
      	$.ajax({
      		type:"post",
      		url:_url,
      		data:"json="+jsonStr+"&tbbm="+tbbm,
      		success:function(data){
      			_success(data);
      		},
      		error:function(val){
      			alert("抱歉，系统出现问题！");
      		},
      	});	
      }
  	//新增按钮
	var num = "${list.size()+2}";
	$(document).on("click","[class*=addBtn]",function(){
		//var wlbh = $("#txt_wlbh").val()
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find("[a='a']").val("");
		$parentTr.find(":input").removeClass("border");
		//$parentTr.find("[id=txt_wlbh]").val(wlbh);
		$parentTr.attr("id","tr_"+num);
		$parentTr.find("[name='jgcs']").attr("id","jgcs"+num);
		num++;
		$(this).parents("tr").after($parentTr);
	});

	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();

	});
	$(document).on("click","[id^=jgcs]",function(){
// 	$("#jgcs").click(function() {
// 		window.location.href = "${ctx}/webView/ysgl/bmyssb/zfcgxmjgcs_list.jsp";
		doOperate("${ctx}/ysgl/bmyssb/tosecond?dm=10","C");
	});
  	
    
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/kjhs/yhzhgl/yhzhgl_edit.jsp","C");
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