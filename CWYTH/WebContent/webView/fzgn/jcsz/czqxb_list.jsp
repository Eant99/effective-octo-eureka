<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>操作权限设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.table{
	    font-size:13px;
		margin-bottom: 0;
	}
	.table>tbody>tr>td, .table td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
		text-align: center;
		vertical-align: middle;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
    <!-- MKBH模块编号 -->
	<input type="hidden" name="MKBH" value="">
	<input type="hidden" name="operateType" id="operateType" value="U">
    <!-- RYBH人员编号 -->
	<input type="hidden" name="RYBH" value="${rymap.RYBH}">
    <div class='page-header'>
		<div class="pull-left">
            <span class="page-title text-primary">
            	<i class='faw fa-user' style="font-size:17px;"></i>
				<span style="color: red;"><c:out value="(${rymap.RYGH})${rymap.XM}"/></span>的操作权限
			</span>
		</div>
		<div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save">
				<i class="faw fa-check"></i>
				保存
			</button>
        </div>
     </div>
     <div class="box" style="top:36px;margin-left: 9px;margin-right: 9px;">
         <div class="box-content">
             <div class="row">
             	<div class="col-md-12">
             		<table class="table table-bordered">
             			<thead>
             				<tr>
             					<th class="col-md-1"></th>
             					<th class="col-md-2">主菜单</th>
             					<th class="col-md-2">二级菜单</th>
             					<th class="col-md-3">菜单名称</th>
             					<th class="col-md-2"><input name="radioAll" id="checkNoHaveOper"  type="radio" value="on" /><label for="checkNoHaveOper">无权</label></th>
             					<th class="col-md-2"><input name="radioAll" id="checkHaveOper" type="radio" value="on" /><label for="checkHaveOper">有权</label></th>
             				</tr>
             			</thead>
             		</table>
            		<c:forEach var="menu_yj" items="${menu_list}">
            			<c:if test="${menu_yj.size > 0}">
	               			<table class="table table-bordered">
              					<tr>
									<td rowSpan="${menu_yj.size}" class="col-md-1">
								  		<span class="selectAll" >
								  			<input name="all" id="all" type="checkbox" value="on" /> 
							  				<label for="all">全选</label> 
								  		</span>
									</td>
									<td rowSpan="${menu_yj.size}" class="col-md-2">${menu_yj.MKMC}</td>
		                          	<c:forEach var="menu_ej" items="${menu_yj.childList}">
			                           	<c:if test="${menu_ej.idx > 0}"><tr></c:if>
										<td rowSpan="${menu_ej.size}" class="col-md-2">${menu_ej.MKMC}</td>
		                           		<c:forEach var="menu_sj" items="${menu_ej.childList}">
		                           			<c:if test="${menu_sj.idx > 0}"><tr></c:if>
												<td class="col-md-3">${menu_sj.MKMC}</td>
	                            				<td class="col-md-2">
												  	<input type="radio" name="${menu_sj.MKBH}"  class="noHaveOper" id="${menu_sj.MKBH}" value="${menu_sj.MKBH}"
													<c:if test="${menu_sj.operate=='false'}"> checked="checked" </c:if> /><label>无权</label>		
												</td>
												<td class="col-md-2">
												   	<input type="radio" name="${menu_sj.MKBH}"  class="haveOper" id="${menu_sj.MKBH}"  value="${menu_sj.MKBH}"
												  	<c:if test="${menu_sj.operate=='true'}"> checked="checked" </c:if> /><label>有权</label> 
												</td>
	                            			</tr>
										</c:forEach>
									</c:forEach>
								</tr>
	                  		</table>
                  		</c:if>
				 	</c:forEach>
                 </div>
             </div>
         </div>
     </div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function(){
		//页面验证
		var validate = $('#myform').bootstrapValidator({fields: {}});
		//保存按钮
		$("#btn_save").click(function(e){
			var $checkeds=$("table").find(".haveOper").parent().find(":radio").filter(":checked");
			var mkbhs =[];
			$checkeds.each(function() {
				$this = $(this);
				var mkbh = $this.val();
				mkbhs.push(mkbh);
			});
			$("[name='MKBH']").val(mkbhs);
			doSave(validate,"myform","${ctx}/czqxb/doSave",function(val){
				getIframWindow("${param.pname}").table.ajax.reload();
				var winId = getTopFrame().layer.getFrameIndex(window.name);
		    	close(winId);
			},function(val){
			});
		}); 
		/*左侧列表全选复选框按钮*/
		$("[id='all']:checkbox").click(function() {
	        var table = $(this).parents("table");
	        if($(this).attr("id") === "all") {
	            table.find(":radio").prop("checked",true);
	            $(this).attr({id:"noAll"});
	        }else {
	        	table.find(".noHaveOper").prop("checked",true);
	        	$(this).attr({id:"all"});
	        }
	    });
		
		/*顶部有权单选按钮：有权*/
		$("#checkHaveOper").click(function(){
            $(this).prop("checked","checked");
            $("input[type='radio']").each(function(){
                  if($(this).prop("class")=="haveOper"){
                           $(this).prop("checked",true);
                       }
                 });
        });
		
		/*顶部有权单选按钮：无权*/
        $("#checkNoHaveOper").click(function(){
			$(this).prop("checked","checked");
			$("input[type='radio']").each(function(){
				if($(this).prop("class")=="noHaveOper"){
					$(this).prop("checked",true);
				}
			});
        });
	});
	</script>
</body>
</html>