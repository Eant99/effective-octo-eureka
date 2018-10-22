<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产分类（全部）</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class='responsive-table'>
        <div class='scrollable-area'>
        <table id="mydatatables" class="table table-striped table-bordered">
    	<thead>
		    <tr>
		        <th><input type="checkbox" class="select-all"/></th>
		        <th>序号</th>
		        <th>资产编号</th>
		        <th>资产名称</th>
		        <th>分类号</th>
		        <th>分类名称</th>
		        <th>单价</th>
		        <th>使用人</th>
		        <th>使用单位</th>
		        <th>使用方向</th>
		        <th>存放地点</th>
		        <th>调转入日期</th>
		        <th>购置日期</th>
		        <th>计量单位</th>
		        <th>现状</th>
		        <th>资产来源</th>
		        <th>入账日期</th>
		        <th>记账类型</th>
		    </tr>
    	</thead>
	    <tbody>
	    </tbody>
		</table>
        </div>
	</div>
<%@include file="/static/include/public-list-js.inc"%>	
<script>
$(function (){
	//联想输入
	$("[id^=text_sydw]").bindChange("${pageContext.request.contextPath}/suggest/getXx","SD");
	$("[id^=text_syr]").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
	$("[id^=text_bzxx]").bindChange("${pageContext.request.contextPath}/suggest/getXx","P");
	//列表数据
   	var columns = [
		{"data": "YSDH",orderable:false, "render": function (data, type, full, meta){
           return '<input type="checkbox" class="keyId" name="id" value="' + data + '" jzrlx="'+full.JZRLX+'" flh = "'+full.FLH+'" flmc = "'+full.YQBH+'" flgbm = "'+full.FLGBM+'" zt = "'+full.ZTBZ+'">';
      	},"width":20,'searchable': false},
	   	{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   	   return data;
   		},"width":41,"searchable": false,"class":"text-center"},
	   {"data": "YQBH",defaultContent:"","render":function (data, type, full, meta){
        	return '<input type="text" name="yqbh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       }},
	   {"data": "YQMC",defaultContent:"","render":function (data, type, full, meta){
        	return '<input type="text" name="yqmc" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       }},
	   {"data": "FLH",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="bmargin-bottom:0px;"><input type="text" style="width:80px;border:0" id="text_flh'+full._XH+'" name="flh" style="border:0" value="' +(undefined==data?'':data)+ '"/><span class="input-group-btn"><button type="button" class="btn btn-link btn-custom btn_flh">选择</button></span></div>';
	   }},
	   {"data": "FLMC",defaultContent:"","render":function (data, type, full, meta){
        	return '<input type="text" readonly id="flmc'+full._XH+'" name="flmc" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       }},
	   {"data": "DJ",defaultContent:"","render":function (data, type, full, meta){
        	return '<input type="text" id="dj'+full._XH+'" name="dj" style="width:80px;border:0" class="text-right number" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       }},
	   {"data": "SYR",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_syr'+full._XH+'" name="syr" style="border:0" value="' +(undefined==data?'':data)+ '"/><span class="input-group-btn"><button type="button" class="btn btn-link btn_syr">选择</button></span></div>';
	   }},
	   {"data": "SYDW",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_sydw'+full._XH+'" name="sydw" style="border:0" value="' +(undefined==data?'':data)+ '"/><span class="input-group-btn"><button type="button" class="btn btn-link btn-custom btn_sydw">选择</button></span></div>';
	   }},
	   {"data": "SYFX","render":function (data, type, full, meta){
		   return '<div class="input-group" style="width:100px;"><select id="text_syfx'+full._XH+'" name="syfx" class="form-control input-radius"><c:forEach var="syfxlist" items="${syfxlist}"><option value="${syfxlist.DM}" <c:if test="${'+data+' == syfxlist.DM}">selected</c:if>>${syfxlist.MC}</option></c:forEach></select></div>';
	   }},
	   {"data": "BZXX",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_bzxx'+full._XH+'" name="bzxx" style="border:0" value="' +(undefined==data?'':data)+ '"/><span class="input-group-btn"><button type="button" class="btn btn-link btn_bzxx">选择</button></span></div>';
	   }},
	   {"data": "DZRRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_dzrrq'+full._XH+'" name="dzrrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   }},
	   {"data": "GZRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_gzrq'+full._XH+'" name="gzrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   }},
	   {"data": "JLDW",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="width:100px;"><select id="text_jldw'+full._XH+'" name="jldw" class="form-control input-radius"><c:forEach var="jldwlist" items="${jldwlist}"><option value="${jldwlist.DM}" <c:if test="${'+data+' == jldwlist.DM}">selected</c:if>>${jldwlist.MC}</option></c:forEach></select></div>';
	   }},
	   {"data": "XZ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="width:100px;"><select id="text_xz'+full._XH+'" name="xz" class="form-control input-radius"><c:forEach var="xzlist" items="${xzlist}"><option value="${xzlist.DM}" <c:if test="${'+data+' == xzlist.DM}">selected</c:if>>${xzlist.MC}</option></c:forEach></select></div>';
	   }},
	   {"data": "ZCLY",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="width:100px;"><select id="text_zcly'+full._XH+'" name="zcly" class="form-control input-radius"><c:forEach var="zclylist" items="${zclylist}"><option value="${zclylist.DM}" <c:if test="${'+data+' == zclylist.DM}">selected</c:if>>${zclylist.MC}</option></c:forEach></select></div>';
	   }},
	   {"data": "RZRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_rzrq'+full._XH+'" name="rzrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   }},
	   {"data": "JZLX",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="width:100px;"><select id="text_jzlx'+full._XH+'" name="jzlx" class="form-control input-radius"><c:forEach var="jzlxlist" items="${jzlxlist}"><option value="${jzlxlist.DM}" <c:if test="${'+data+' == jzlxlist.DM}">selected</c:if>>${jzlxlist.MC}</option></c:forEach></select></div>';
	   }}
	];
   	var length = getTopFrame().window.sessionRowNumLength;
   	table = $('#mydatatables').DataTable({
        "ajax": {
            url:"${pageContext.request.contextPath}/yssjzl/getPageList"//获取数据的方法
        },
        "lengthMenu": length,//每页显示条数设置
        "order": [2,'asc'],//排序列
        "columns": columns,//列定义
        "dom":"t<'row bottom'<'col-md-5 col-sm-5 col-xs-5'i><'col-md-7 col-sm-7 col-xs-7 pull-right'p>>",
        "drawCallback":function(){
        	$(".date").datetimepicker({
        		format: "yyyy-mm-dd",
        		autoclose: true,
        		todayBtn: true,
        		todayHighlight: true,
        		minView: "month"
        	});
        } 
   	});
  	//使用单位
	$(document).on("click",".btn_sydw",function(){
		var id = $(this).parents("tr").find("[id^=text_sydw]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/zcfltree/goZjbWindow?controlId="+id,"单位信息","920","630");
    });
	//使用人
	$(document).on("click",".btn_syr",function(){
		var id = $(this).parents("tr").find("[id^=text_syr]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId="+id,"人员信息","920","630");
    });
	//存放地点
	$(document).on("click",".btn_bzxx",function(){
		var id = $(this).parents("tr").find("[id^=text_bzxx]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/webView/window/ddxx/window.jsp?controlId="+id,"地点信息","920","630");
    });
	//分类代码弹窗
   	$(document).on("click",".btn_flh",function(){
   		var id = $(this).parents("tr").find("[id^=text_flh]").attr("id");
   		var flmc = $(this).parents("tr").find("[id^=flmc]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/zcfltree/goWindow?controlId="+id+"&flmc="+flmc,"分类信息","920","630");
    });
});
</script>	
</body>
</html>