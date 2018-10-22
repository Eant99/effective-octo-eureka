<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>在职薪资管理</title>
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
/*  @media screen and (min-width: 770px) {   */
/*  .abc {height: 800px}   */
/*  }  */
   
 /* 设置了浏览器高度不大于900px时 abc 显示630px高度 */  
/*  @media screen and (max-heighe: 900px) {   */
/*  .abc {height: 630px;}   */
/*  }   */

</style>
</head>
<body style="height: 100%">
<div class="" style="background-color: white;">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 20px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
	                <label>人员编号</label>
	             	<input  type="text" placeholder="请输入人员编号" id="cx" value="${rybh}" class="input_info nd form-control" style="border:1px solid #ccc;"  />
					<button type="button" class="btn btn-primary" id="btn_chaxun"><i class="fa icon-chaxun"></i>查 询</button>
					<br>
				</div>
				<div class="btn-group pull-right" role="group">
				    <button type='button' class="btn btn-default" id="btn_hs">核算</button>
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
				            <th style="text-align:center;">姓&nbsp;&nbsp;&nbsp;&nbsp;名</th>
				            <th style="text-align:center;">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门</th>			            
				            <th style="text-align:center;">&nbsp;人员类别&nbsp;</th>			            
				            <th style="text-align:center;">人&nbsp;员&nbsp;类&nbsp;型</th>			            
				            <th style="text-align:center;">岗位工资</th>			            
				            <th style="text-align:center;">薪级工资</th>			            
				            <th style="text-align:center;">新住房贴</th>			            
				            <th style="text-align:center;">物业补贴</th>			            
				            <th style="text-align:center;">独生子费</th>			            
				            <th style="text-align:center;">基础绩效</th>			            
				            <th style="text-align:center;">奖励绩效1</th>			            
				            <th style="text-align:center;">博士补贴</th>			            
				            <th style="text-align:center;">岗位补贴</th>			            
				            <th style="text-align:center;">北校区值班补贴</th>			            
				            <th style="text-align:center;">生育补贴</th>			            
				            <th style="text-align:center;">教护补贴</th>			            
				            <th style="text-align:center;">驻济补贴</th>			            
				            <th style="text-align:center;">合同补贴</th>			            
				            <th style="text-align:center;">其他补贴</th>			            
				            <th style="text-align:center;">补工资</th>			            
				            <th style="text-align:center;">监考费</th>			            
				            <th style="text-align:center;">辅助工作量</th>			            
				            <th style="text-align:center;">招生奖励</th>			            
				            <th style="text-align:center;">辅导员夜间值班补贴</th>			            
				            <th style="text-align:center;">考核奖</th>			            
				            <th style="text-align:center;">电话费</th>			            
				            <th style="text-align:center;">补贴</th>			            
				            <th style="text-align:center;">租房补贴</th>			            
				            <th style="text-align:center;">应发合计</th>			            
				            <th style="text-align:center;">讲课费</th>			            
				            <th style="text-align:center;">补工资扣税</th>			            
				            <th style="text-align:center;">计税项</th>			            
				            <th style="text-align:center;">全年计税项</th>			            
				            <th style="text-align:center;">全年计税项1</th>			            
				            <th style="text-align:center;">全年计税项2</th>			            
				            <th style="text-align:center;">全年计税项3</th>			            
				            <th style="text-align:center;">补基础性绩效工资2014计税基数</th>			            
				            <th style="text-align:center;">北校补贴2014年1到10月计税基数</th>			            
				            <th style="text-align:center;">计税基数</th>			            
				            <th style="text-align:center;">住房积金</th>			            
				            <th style="text-align:center;">聘公积金</th>			            
				            <th style="text-align:center;">医疗保险</th>			            
				            <th style="text-align:center;">补公积金</th>			            
				            <th style="text-align:center;">代扣税</th>			            
				            <th style="text-align:center;">本年税额</th>			            
				            <th style="text-align:center;">上年税额</th>			            
				            <th style="text-align:center;">补&nbsp;&nbsp;税</th>			            
				            <th style="text-align:center;">房&nbsp;&nbsp;租</th>			            
				            <th style="text-align:center;">失业金</th>			            
				            <th style="text-align:center;">暖气费</th>			            
				            <th style="text-align:center;">暖气费1</th>			            
				            <th style="text-align:center;">物业费</th>			            
				            <th style="text-align:center;">社保金</th>			            
				            <th style="text-align:center;">四季度菜款</th>			            
				            <th style="text-align:center;">扣款</th>			            
				            <th style="text-align:center;">养老金</th>			            
				            <th style="text-align:center;">借&nbsp;&nbsp;款</th>			            
				            <th style="text-align:center;">爱心一日捐</th>			            
				            <th style="text-align:center;">扣款合计</th>			            
				            <th style="text-align:center;">实发合计</th>			            
				            <th style="text-align:center;">工资月份</th>			            
				            <th style="text-align:center;">编&nbsp;&nbsp;号</th>			            				            
				            <th style="text-align:center;">序&nbsp;&nbsp;号</th>			            
				            <th style="text-align:center;">交通费</th>			            
				            <th style="text-align:center;">年终奖</th>			            
				            <th style="text-align:center;">年终奖代扣税</th>			            
				            <th style="text-align:center;">工资代扣税</th>			            
				            <th style="text-align:center;">扣税合计</th>			            
				            <th style="text-align:center;">工&nbsp;&nbsp;号</th>			            
				            <th style="text-align:center;">是否在编</th>			            
				            <th style="text-align:center;">补扣医疗保险</th>			            
				            <th style="text-align:center;">补扣失业金</th>			            
				            <th style="text-align:center;">补扣养老金</th>			            
				            <th style="text-align:center;">补扣社保金</th>			            
				            <th style="text-align:center;">是否党员</th>			            
				            <th style="text-align:center;">入党情况</th>			            
				        </tr>
					</thead>
				    <tbody>
				     <tbody>
				     <c:choose>
				       <c:when test="${not empty list}">
				     
				    <c:forEach var="list" items="${list}" varStatus="i">
				 		<tr class="tr_${i.index+1}">
				 	    	<td><input type="hidden" name="rybh" class="form-control " value="${list.rybh}">${list.rybh}</td>
				 			<td><input type="hidden" name="xm" value="${list.xm}"/>${list.xm}<input type="hidden" name="guid" value="${list.guid}"/></td>
				 			<td><input type="hidden" name="bm" class="form-control " value="${list.bm}">${list.bm}</td>
				 			<td><input type="hidden" name="rylb" class="form-control " value="${list.rylb}">${list.rylb}</td>
				 			<td><input type="hidden" name="rylx" class="form-control " value="${list.rylx}">${list.rylx}</td>
				 			<td><input type="text" name="gwgz" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.gwgz}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="xjgz" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.xjgz}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="xzft" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.xzft}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="wybt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.wybt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="dszf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.dszf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jcjx" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jcjx}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jljx1" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jljx1}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bsbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bsbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="gwbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.gwbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bxqzbbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bxqzbbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="sybt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.sybt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jhbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jhbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="zjbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.zjbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="htbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.htbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="qtbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.qtbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bgz" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bgz}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jkf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jkf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="fzgzl" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.fzgzl}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="zsjl" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.zsjl}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="fdyyjzbbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.fdyyjzbbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="khj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.khj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="dhf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.dhf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="zfbt" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.zfbt}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="yfhj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.yfhj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jiangkef" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jiangkef}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bgzks" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bgzks}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jsx" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jsx}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="qnjsx" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.qnjsx}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="qnjsx1" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.qnjsx1}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="qnjsx2" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.qnjsx2}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="qnjsx3" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.qnjsx3}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bjcxjxgz2014jsjs" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bjcxjxgz2014jsjs}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bxbt2014n1d10yjsjs" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bxbt2014n1d10yjsjs}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jsjs" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jsjs}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="zfjj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.zfjj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="pgjj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.pgjj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="ylbx" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.ylbx}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bgjj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bgjj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="dks" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.dks}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bnse" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bnse}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="snse" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.snse}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bs" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bs}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="fz" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.fz}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="syj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.syj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="nqf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.nqf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="nqf1" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.nqf1}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="wyf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.wyf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="sbj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.sbj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="sjdck" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.sjdck}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="kk" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.kk}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="ylj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.ylj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="jk" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jk}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="axyrj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.axyrj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="kkhj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.kkhj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="sfhj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.sfhj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="gzyf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.gzyf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bh" style="text-align:right;" class="form-control number" value="${list.bh}"></td>
				 			<td><input type="text" name="xh" style="text-align:right;" class="form-control " value="${list.xh}"></td>
				 			<td><input type="text" name="jtf" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.jtf}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="nzj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.nzj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="nzjdks" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.nzjdks}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="gzdks" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.gzdks}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="kshj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.kshj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="gh" style="text-align:right;" class="form-control number" value="${list.gh}"></td>
				 			<td><input type="text" name="sfzb"  class="form-control " value="${list.sfzb}"></td>
				 			<td><input type="text" name="bkylbx" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bkylbx}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bksyj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bksyj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bkylj" style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bkylj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="bksbj"  style="text-align:right;" class="form-control number" value="<fmt:formatNumber value='${list.bksbj}' pattern='0.00'/>"></td>
				 			<td><input type="text" name="sfdy" class="form-control " value="${list.sfdy}"></td>
				 			<td><input type="text" name="rdqk" class="form-control " value="${list.rdqk}"></td>
				 		</tr>
				 	</c:forEach>
				 	</c:when>
				 	<c:otherwise>
				 	<tr>
				 	<td colspan="77" style="height: 33px;font-size: 13px">暂无数据
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

<!-- <input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value=""> -->
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
          select_commonWin("${pageContext.request.contextPath}/webView/xzgl/xzgl_imp.jsp","导入工资信息","550","450");
           return false;
     });

	 //核算
	 $("#btn_hs").click(function(){
// 		 var json = $("#mysave").serializeObject("rybh","rdqk");  //json对象				
// 		 var jsonStr = JSON.stringify(json);
			confirm("确定核算？","",function(){
			$.ajax({
				url:"${ctx}/xzgl/doHs",
// 				data:"jsonStr="+jsonStr,
				dataType:"json",
				type:"post",
				success:function(date){
					location.reload();
					alert("核算成功！");
				}
			});
			});
	 });
 	//点击保存按钮
		$("#btn_save").click(function(){
			var json = $("#mysave").serializeObject("rybh","rdqk");  //json对象				
			var jsonStr = JSON.stringify(json);
			confirm("确定保存？","",function(){
			$.ajax({
				url:"${ctx}/xzgl/doSave",
				data:"jsonStr="+jsonStr,
				dataType:"json",
				type:"post",
				success:function(msg){
					if($.trim(msg)=="true"){
						window.location.href="${ctx}/webView/xzgl/xzgl_show.jsp";
						alert("保存成功!");
					}else{
						alert("保存失败!");
					}
				}
			});
			});
		});
		//查询
		 $("#btn_chaxun").click(function(){
			var rybh = $("#cx").val();
// 			var rybh = $(this).val();
		 	location.href="${ctx}/xzgl/xzgl_list?rybh="+rybh;
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