<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>离职薪资管理</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.table>thead>tr>th {
    padding: 12px;
/*     padding-top: 7px; */
/*     padding-right: 7px; */
/*     padding-bottom: px; */
/*     padding-left: 7px; */
}
	
	.td_input{
		border:none;
		width:100%;
	}
	.text-green{
		color:green!important;
	}
	th{
		text-align:center;
	}
	.table>tbody>tr.selected:nth-of-type(odd) {
 	background-color: #f9f9f9!important;
	}
	.table>tbody>tr.selected:nth-of-type(even) {
 	background-color: #f3f3f3!important;
	}
/* 设置了浏览器高度不小于1201px时 abc 显示800px高度 */  
/*  @media screen and (min-width: 1201px) {   */
/*  .abc {height: 800px}   */
/*  }  */
   
 /* 设置了浏览器高度不大于900px时 abc 显示630px高度 */  
/*  @media screen and (max-heighe: 800px) {   */
/*  .abc {height: 630px;}   */
/*  }   */

	
</style>
</head>
<body>
<div class="" style="background-color: white;">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 20px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
 	             	 <label>人员编号</label>
	             	<input  type="text" placeholder="请输入人员编号" id="cx" value="${rybh}" class="input_info nd form-control" style="border:1px solid #ccc;"  />
				</div>
				<div class="btn-group pull-right" role="group">
				    <button type='button' class="btn btn-default" id="btn_save">保存</button>
<!-- 					<button type='button' class="btn btn-default" id="btn_print">打印预览</button> -->
					<button type='button' class="btn btn-default" id="btn_imp">导入</button>
<!-- 					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button> -->
				</div>
			</div>
		</form>
	</div>

<form id="mysave" name="mysave" method="post"  >
<div class="container-fluid abc" style="overflow: auto;">
		<div class='responsive-table' >
			<div class='scrollable-area'  > 
			<table id="mydatatables" class="table table-striped table-bordered" >
		        	<thead>
				        <tr>
				            <th style="text-align:center;">人员编号</th>
				            <th style="text-align:center;">姓&nbsp;&nbsp;名</th>
				            <th style="text-align:center;">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门</th>			            
				            <th style="text-align:center;">人员类别</th>			            
				            <th style="text-align:center;">基本工资</th>			            
				            <th style="text-align:center;">增加离退费</th>			            
				            <th style="text-align:center;">原职务补贴</th>			            
				            <th style="text-align:center;">岗位补贴</th>			            
				            <th style="text-align:center;">新住房贴</th>			            
				            <th style="text-align:center;">回族补贴</th>			            
				            <th style="text-align:center;">退休提高部分</th>			            
				            <th style="text-align:center;">生活补贴</th>			            
				            <th style="text-align:center;">新增补贴</th>			            
				            <th style="text-align:center;">物价补贴</th>			            
				            <th style="text-align:center;">特需护理</th>			            
				            <th style="text-align:center;">教护补贴</th>			            
				            <th style="text-align:center;">其他补贴</th>			            
				            <th style="text-align:center;">离退补贴</th>			            
				            <th style="text-align:center;">月增补贴</th>			            
				            <th style="text-align:center;">基础绩效</th>			            
				            <th style="text-align:center;">交通费</th>			            
				            <th style="text-align:center;">补&nbsp;&nbsp;贴</th>			            		            
				            <th style="text-align:center;">租房补贴</th>			            		            
				            <th style="text-align:center;">补工资</th>			            		            
				            <th style="text-align:center;">物业补贴</th>			            		            
				            <th style="text-align:center;">监考费</th>			            		            
				            <th style="text-align:center;">过节费</th>			            		            
				            <th style="text-align:center;">电话费</th>			            		            
				            <th style="text-align:center;">应发合计</th>			            		            
				            <th style="text-align:center;">房&nbsp;&nbsp;租</th>			            		            
				            <th style="text-align:center;">社保金</th>			            		            
				            <th style="text-align:center;">暖气费</th>			            		            
				            <th style="text-align:center;">暖气费1</th>			            		            
				            <th style="text-align:center;">物业费</th>			            		            
				            <th style="text-align:center;">借&nbsp;&nbsp;款</th>			            		            
				            <th style="text-align:center;">养老金</th>			            		            
				            <th style="text-align:center;">补公积金</th>			            		            
				            <th style="text-align:center;">补&nbsp;&nbsp;税</th>			            		            
				            <th style="text-align:center;">四季度菜款</th>			            		            
				            <th style="text-align:center;">失业金</th>			            		            
				            <th style="text-align:center;">扣款合计</th>			            		            
				            <th style="text-align:center;">实发合计</th>			            		            
				            <th style="text-align:center;">工资月份</th>			            		            
				            <th style="text-align:center;">编&nbsp;&nbsp;号</th>			            		            
				            <th style="text-align:center;">计税项</th>			            		            
				            <th style="text-align:center;">住房积金</th>			            		            
				            <th style="text-align:center;">年终奖</th>			            		            
				            <th style="text-align:center;">扣&nbsp;&nbsp;款</th>			            		            
				        </tr>
					</thead>
				    <tbody>
				     <tbody>
				     <c:choose>
				     <c:when test="${not empty list}">
				    <c:forEach var="list" items="${list}" varStatus="i">
				 		<tr class="tr_${i.index+1}">
				 		    <td><input type="hidden" name="rybh" class="form-control " value="${list.rybh}">${list.rybh}</td>
				 			<td><input type="hidden" name="xm" value="${list.xm}"/>${list.xm}
				 			<input type="hidden" name="guid" value="${list.guid}"/></td>
				 			<td><input type="text" name="bm" class="form-control " value="${list.bm}"></td>
				 			<td><input type="text" name="rylb" class="form-control " value="${list.rylb}"></td>
				 			<td><input type="text" name="jbgz" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jbgz}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="zjltf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.zjltf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="yzwbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.yzwbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="gwbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.gwbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="xzft" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.xzft}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="hzbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.hzbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="txtgbf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.txtgbf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="shbt" style="text-align:right;"  class="form-control number" value="<fmt:formatNumber value='${list.shbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="xzbt"  style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.xzbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="wjbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.wjbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="txhl" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.txhl}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jhbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jhbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="qtbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.qtbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="ltbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.ltbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="yzbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.yzbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jcjx" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jcjx}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jtf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jtf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="zfbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bgz}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bgz" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bgz}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="wybt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.wybt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jkf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jkf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="gjf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.gjf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="dhf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.dhf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="yfhj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.yfhj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="fz" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.fz}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="sbj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.sbj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="nqf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.nqf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="nqf1" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.nqf1}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="wyf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.wyf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jk" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jk}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="ylj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.ylj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bgjj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bgjj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bs" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bs}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="sjdck" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.sjdck}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="syj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.syj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="kkhj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.kkhj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="sfhj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.sfhj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="gzyf" style="text-align:right;" class="form-control " value="${list.gzyf}"></td>
				 			<td><input type="text" name="bh" style="text-align:right;" class="form-control " value="${list.bh}"></td>
				 			<td><input type="text" name="jsx" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jsx}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="zfjj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.zfjj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="nzj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.nzj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="kk" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.kk}' pattern='0.00'/>"></td>
				 		</tr>
				 	</c:forEach>
				 	</c:when>
				 	<c:otherwise>
				 	<tr>
				 	<td colspan="48" style="height: 33px;font-size: 13px">暂无数据
				 	</td>
				 	</tr>
				 	</c:otherwise>
				 	  </c:choose>
				    </tbody>
				   </tbody>
				</table>					
		</div>
			</div>
		</div>	
	</form>		
		</div>

<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
height();
$(window).resize(function(){
	height();
});
function height(){
	$(".abc").height($(window).height()-$("#searchBox").outerHeight(true)-50);
}
 $(function () {
	 //导入数据
     $("#btn_imp").click(function(){
          select_commonWin("${pageContext.request.contextPath}/webView/xzgl/lzxzgl_imp.jsp","导入离职工资信息","550","450");
           return false;
     });

 	//点击保存按钮
		$("#btn_save").click(function(){
			var json = $("#mysave").serializeObject("rybh","kk");  //json对象				
			var jsonStr = JSON.stringify(json);
			confirm("确定保存？","",function(){
			$.ajax({
				url:"${ctx}/xzgl/doSaveLz",
				data:"jsonStr="+jsonStr,
				dataType:"json",
				type:"post",
				success:function(msg){
					if($.trim(msg)=="true"){
						window.location.href="${ctx}/webView/xzgl/lzxzgl_show.jsp";
						alert("保存成功!");
					}else{
						alert("保存失败!");
					}
				}
			});
				
			});
		});
		//查询
		 $("#cx").change(function(){
			var rybh = $(this).val();
		 	location.href="${ctx}/xzgl/lzxz_list?rybh="+rybh;
		 });

			//列表右侧悬浮按钮
			$(window).resize(function(){
		    	$("div.dataTables_wrapper").width($("#searchBox").width());
		    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
		    	$(".dataTables_scrollBody").height(heights);
		    	table.draw();
			});

//导出Excel
$("#btn_exp").click(function() {
		var jzpz = $("#jzpz").val();
		var sysDate = $(".nd").val();
		var sfjz = $("#sfjz").val();
		var ztgid = $("#ztgid").val();
		var bbnd = $("[name='bbnd']").val();
		$.ajax({
				type : "post",
			    data : {sysDate : sysDate,jzpz : jzpz},
				url : "${ctx}/czbzsrzc/expExcel2",
				success : function(val) {
						FileDownload("${ctx}/file/fileDownload","财政补助收入支出表.xls", val.url);
				}
			});
	});
	});
</script>
</body>
</html>