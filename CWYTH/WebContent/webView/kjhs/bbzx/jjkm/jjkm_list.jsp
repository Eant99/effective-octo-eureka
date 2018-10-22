<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
				
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			
<!-- 								<label>会计期间</label> -->
<!-- 								<div class="input-group"> -->
<!-- 									<input type="text" id="txt_ksrq_begin" class="form-control date" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" name="kjqj" types="TL" table="k" data-format="yyyy-MM" placeholder=""> -->
<!-- 									<span class='input-group-addon'> -->
<!-- 								    	<i class="glyphicon glyphicon-calendar"></i> -->
<!-- 								   	</span> -->
<!-- 							   	</div> -->
<!-- 								<label>至</label> -->
<!-- 								<div class="input-group"> -->
<!-- 									<input type="text" id="txt_ksrq_end" class="form-control date" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" name="kjqj" types="TH" table="k" data-format="yyyy-MM" placeholder=""> -->
<!-- 									<span class='input-group-addon'> -->
<!-- 								    	<i class="glyphicon glyphicon-calendar"></i> -->
<!-- 							   		</span> -->
<!-- 								</div> -->
             <div class="form-group" style="width:350px;">
					<label>会计期间</label>
					<input type="text" class="form-control input-radius year" style="width: 80px!important;" name="pznd" value="${year}"/>
					年
					<select class="form-control input-radius select" types="NL" name="kjqj" id="kjqj1" style="width:50px;"> 
						<c:forEach var="months" items="${months}">
							<option value="${months.month}">${months.month}</option>
						</c:forEach>
 					</select>
 					月&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;
 					<select class="form-control input-radius select" types="NH" name="kjqj" id="kjqj2" style="width:50px;">
						<c:forEach var="months" items="${months}">
							<option value="${months.month} " <c:if test="${mm eq months.month}">selected</c:if> >${months.month}</option>
						</c:forEach>
					</select>
					月
				</div>
						
				<div class="form-group">
					<label>包含全部未记账凭证</label>
					<select class="form-control" id="txt_sfbhwjzpz" name="pzzt" types="SFBHWJZ">	
					        <option value="00,01,02,99" >是</option>					
							<option value="02,99" >否</option>																			
					</select>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more" style="width: 450px">
						<div class="form-group">
							<label>摘要内容</label>
							<br>
							<input  type="text" name="ZY" id="txt_ZY" class="input_info  form-control" style="width:280px;" placeholder="摘要内容" value=""  />
						</div>
						<div class="form-group">
							<label>会计科目</label>
							<br>
							<input  type="text" name="kmbh" types="ToBhss" id="txt_km" class="input_info  form-control" style="width:130px;" placeholder="科目编号" value=""  />
							<button type="button" id="btn_kjkm" class="btn btn-link ">选择</button>
						</div>
						<div class="form-group">
							<label>起止会计科目</label>
							<br>
							<input   type="text" name="kmbh" types="BHL" id="txt_km1" class="input_info  form-control" style="width:130px;" placeholder="科目编号" value=""  />
							<button type="button" id="btn_kjkm1" class="btn btn-link ">选择</button>
							<label>至</label>
							<input   type="text" name="kmbh"  types="BHH" id="txt_km2" class="input_info  form-control" placeholder="科目编号" style="width:130px;" value=""  />
							<button type="button" id="btn_kjkm2" class="btn btn-link ">选择</button>
						</div>
						<div class="form-group">
							<label>凭证号区间</label>
							<br>
							<input   type="text" name="PZBH" types="NL" id="txt_PZBH1" class="input_info  form-control" style="width:130px;" placeholder="凭证号" value=""  />
							<label>至</label>
							<input   type="text" name="PZBH"  types="NH" id="txt_PZBH2" class="input_info  form-control" placeholder="凭证号" style="width:130px;" value=""  />
						</div>
						<div class="form-group">
							<label>凭证日期区间</label>
							<br>
							<input   type="text" name="PZRQ" types="TL_date" id="txt_PZRQ1" class="input_info  form-control"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:130px;" placeholder="凭证日期" value=""  />
							<label>至</label>
							<input   type="text" name="PZRQ"  types="TH_date" id="txt_PZRQ2" class="input_info  form-control"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="凭证日期" style="width:130px;" value=""  />
						</div>
						<div class="form-group">
							<label>借方金额区间</label>
							<br>
							<input   type="text" name="JFJE" types="NL" id="txt_JFJE1" class="input_info  form-control" style="width:130px;" placeholder="借方金额" value=""  />
							<label>至</label>
							<input   type="text" name="JFJE"  types="NH" id="txt_JFJE2" class="input_info  form-control" placeholder="借方金额" style="width:130px;" value=""  />
						</div>
						<div class="form-group">
							<label>贷方金额区间</label>
							<br>
							<input   type="text" name="DFJE" types="NL" id="txt_DFJE1" class="input_info  form-control" style="width:130px;" placeholder="贷方金额" value=""  />
							<label>至</label>
							<input   type="text" name="DFJE"  types="NH" id="txt_DFJE2" class="input_info  form-control" placeholder="贷方金额" style="width:130px;" value=""  />
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search" name="btncx">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
<!-- 								<button type="button" class="btn btn-default" id="btn_cancel"> -->
<!-- 									取 消 -->
<!-- 								</button> -->
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
	                <button class="btn btn-default" type="button" id="dyyl">打印</button>
	                <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
            	</div>
			</div>
		</form>
	</div>
	<div class="container-fluid" id="div_print">
<!-- 	<div class="container-fluid"> -->
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr id="header">
				            <th ><input type="checkbox" class="select-all"/></th>
				            <th >序号</th>
				            <th >凭证日期</th>
				            <th >凭证类型</th>
				            <th >凭证号</th>
				            <th >摘要</th>
				            <th >会计科目</th>
				            <th >部门名称</th>
				            <th >项目名称</th>
				            <th >借方金额（元）</th>
				            <th >贷方金额（元）</th>
				            <th >余额（元）</th>
				        </tr>
					</thead>
				    <tbody>
				    </tbody>
				</table>
				<div id="div_table2" style="display: none;">
				<table id="mytable2" border="1" bordercolor="#000000">
				<thead>
				    <tr> 
				            <th >序号</th>
				            <th >凭证日期</th>
				            <th >凭证类型</th>
				            <th >凭证号</th>
				            <th >摘要</th>
				            <th >会计科目</th>
				            <th >部门名称</th>
				            <th >项目名称</th>
				            <th >借方金额（元）</th>
				            <th >贷方金额（元）</th>
				            <th >余额（元）</th>
				        </tr>
				        </thead>
				   <tbody id="tbody2">
				   </tbody>
				</table>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
$(function () {
	 $(document).on("click","[id=btn_kjkm]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId=km&controlId1=txt_km&flag=zhcx","科目信息","920","630");
	 });
	 $(document).on("click","[id=btn_kjkm1]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_km1&controlId=txt_km1&flag=zhcx","科目信息","920","630");
	 });
	 $(document).on("click","[id=btn_kjkm2]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_km2&controlId=txt_km2&flag=zhcx","科目信息","920","630");
	 });
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//单位弹窗 
		$("#btn_bmbh").click(function(){
			select_commonWin("${ctx}/window/dwpage?controlId=txt_bm","单位信息","920","630");	
	    });
	//项目弹窗
		$("#btn_xmbh").click(function(){
			select_commonWin("${ctx}/webView/kjhs/bbzx/xmxx_list.jsp?controlId=txt_xm","项目信息","920","630");	
	    });
	   
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" xh="'+full.XH+'" pzid = "'+full.PZID+'" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":40,"searchable":false,"class":"text-center"},      
       {"data": "PZRQ",defaultContent:"","class":"text-center",orderable:false},
       {"data": "PZLXMC",defaultContent:"",orderable:false},
       {"data": "PZBH",defaultContent:"",orderable:false},
       {"data": "ZY",defaultContent:"",orderable:false,"width":300},
       {"data": "KMMC",defaultContent:"",orderable:false},
       {"data": "BMMC",defaultContent:"",orderable:false},
       {"data": "XMMC",defaultContent:"",orderable:false},
       {"data": "JFJE",defaultContent:"","width":100,"class":"text-right",orderable:false},
       {"data": "DFJE",defaultContent:"","width":100,"class":"text-right",orderable:false},
       {"data": "YE",defaultContent:"","width":100,"class":"text-right",orderable:false},       
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/kjhs/jjkmmxz/getPageList?treedwbh=${kmbh}",[2,'desc'],columns,0,1,setGroup);
});

	$(document).on("click","#dyyl",function(){
	// $("#dyyl").click(function(){
		var kjqj1 = $("#kjqj1").val();
		var kjqj2 = $("#kjqj2").val();
		var kmbh=$("#txt_km").val();
		var pzzt = $("[name='pzzt']").val();
		var data = $("#myform").serialize();
		 $("#mytable2").find("tbody").html("");
		 $.ajax({
				type:"post",
				data:data,
				async : false, 
				url:"${ctx}/mxz/getJjkmPageList?treesearch=${treesearch}&treekmbh="+kmbh+"&year=${year}&kjqj1="+kjqj1+"&kjqj2="+kjqj2,
				dataType: "json",
				success:function(val){
					for (var i=0;i<val.length;i++){
						 var PZRQ=val[i].PZRQ;if(PZRQ==null) PZRQ='';
						 var PZZ=val[i].PZZ;if(PZZ==null) PZZ='';
						 var GUID=val[i].GUID;if(GUID==null) GUID='';
						 var PZBHGUID=val[i].PZBHGUID;if(PZBHGUID==null) PZBHGUID='';
						 var PZLX=val[i].PZLXMC;if(PZLX==null) PZLX='';
						 var ZY=val[i].ZY;if(ZY==null) ZY='';
						 var KMMC=val[i].KMMC;if(KMMC==null) KMMC='';
						 var BMMC=val[i].BMMC;if(BMMC==null) BMMC='';
						 var XMMC=val[i].XMMC;if(XMMC==null) XMMC='';
						 var JFJE=val[i].JFJE;if(JFJE==null) JFJE='';
						 var DFJE=val[i].DFJE;if(DFJE==null) DFJE='';
						
						 var YE=val[i].YE;if(YE==null) YE='';
						 var index = Number(i)+1;
						
						 $("#tbody2").append(
									"<tr >"+
	// 								"<td ><input type='checkbox' class='select-all'/></td>"+
									"<td style='text-align:center'>"+index+"</td>"+
									"<td id='pzrq'>"+PZRQ+"</td>"+
									"<td name='pzlx' value='"+PZLX+"'>"+PZLX+"</td>"+
									"<td name='pzbh' value='"+PZBHGUID+"'>"+PZZ+"</td>"+
									"<td id='zy'>"+ZY+"</td>"+
									"<td >"+KMMC+"</td>"+
									"<td >"+BMMC+"</td>"+
									"<td >"+XMMC+"</td>"+
									"<td style='text-align:right' id='jfje' >"+JFJE+"</td>"+
									"<td style='text-align:right' id='dfje'>"+DFJE+"</td>"+							
									"<td style='text-align:right' id='ye'>"+YE+"</td>"+
									"</tr>"
						);}					
				},
			}); 
		
		
	 	PreviewMytable();
	 });


function PreviewMytable(){
// 	var str = "<table>"+document.getElementById("header").innerHTML+$("#mytable2").find("tbody").html()+"</table>";
	var str = $("#div_table2").html();
	var LODOP=getLodop();  
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
	var strStyle="<style> table,td,th,tr {border-width: 1px;border-style: solid;border-collapse: collapse}table{width:95%!important;}</style>"
	LODOP.ADD_PRINT_HTM("5%","5%","90%","85%","<h1 style='text-align:center'>经济科目明细账</h1>");
	LODOP.ADD_PRINT_TABLE("5%","5%","90%","85%",strStyle+str);
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
	LODOP.SET_PRINT_PAGESIZE(1, 2100, 2970,"打印");
	LODOP.PREVIEW();	
};
//导出Excel
	$(document).on("click","#btn_exp",function(){
// 	$("#btn_exp").click(function(){
		var json = searchJson("searchBox");
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
	var guid = [];
	checkbox.each(function(){
		guid.push($(this).val());
	});
		doExp(json,"${ctx}/kjhs/jjkmmxz/expExcel?treedwbh=${kmbh}&guid="+guid.join("','"),"经济科目明细账","${pageContext.request.contextPath}",guid.join(","));
	});
	$(document).on("dblclick","tbody tr",function(){
		var vamc = $(this).find("[name='guid']").attr("pzid");
	 	pageSkip("${ctx}/kjhs/pzxx/pzlr","pzlrck&guid="+vamc);
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