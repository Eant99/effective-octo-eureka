<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入增长比例设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>单位名称</label>
					<input type="text" id="txt_zybm" class="form-control" name="zybm" table="K" placeholder="请输入单位名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_save">保存</button>
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel表</button>
	               
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>

				            <th>单位名称</th>
				            <th>预算年度</th>
				            <th>上年收入预算（万元）</th>
							<th>上年执行（万元）</th>
							<th>增长比例</th>
							<th>本年预算（万元）</th>		
										          
			                <th>操作</th>
				        </tr>
					</thead>
				    <tbody id="bod">
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	
	//列表数据
	var columns = [
		{"data": "DWBH",orderable:false, "render": function (data, type, full, meta){
			if(data=='000001'){
				return '<input type="checkbox" class="keyId djdw" name="keyId" value="' + data + '">';
			}else{
				return '<input type="checkbox" class="keyId" name="keyId" value="' + data + '">';
			}
	    },"width":10,'searchable': false},
	   
	    {"data": "MC",defaultContent:"",'render': function (data, type, full, meta){
	         return '<input display="none" type="type" name="ZYBM" style="width:75%;border:none;" class="mc" value="' + data + '" guid = "'+full.ZYBM+'"><button type="button" id="btn_sjdw" class="btn btn-link btn-custom">选择</button>';
	       },"width":400},
	   	{"data": "BMH",defaultContent:"",'render': function (data, type, full, meta){
	         return '<input  disabled="disabled" display="none"  type="type" name="ZYBM" style="width:100%;border:none;border-radius: 4px;text-align: center;"  value="2016-10-10" guid = "">';
	       },"width":300},
	 
	   	{"data": "BMH",defaultContent:"",'render': function (data, type, full, meta){
	         return '<input disabled="disabled" display="none"  type="type" name="ZYBM" style="width:100%;border:none;text-align:right;" class="" value="100.0000" guid = "">';
	       },"width":300},
		{"data": "JC",defaultContent:"",'render': function (data, type, full, meta){
	         return '<input disabled="disabled" display="none"  type="type" name="ZYBM" style="width:100%;border:none;text-align:right;" class="number1"  value="<fmt:formatNumber value="90" pattern="0.0000"/>" guid = "'+full.ZYBM+'">';
	       },"width":300},
	   	{"data": "JC",defaultContent:"",'render': function (data, type, full, meta){
	         return '<input  display="none"  type="type" name="ZYBM" style="width:100%;border:none;text-align:right;" class="number checkval" value=""  guid = "">';
	       },"width":300},
	   	{"data": "DZ",defaultContent:"",'render': function (data, type, full, meta){
	         return '<input  display="none"  type="type" name="ZYBM" style="width:100%;border:none;text-align:right;" class="bnys number1" value="" guid = "'+full.ZYBM+'">';
	       },"width":300},
      	{"data": "DWBH",'render':function (data, type, full, meta){
	   			return '<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>' ;
		},orderable:false,"width":95,"class":"text-center"}
	];
	//表数据
   	table = getDataTableByListHj("mydatatables","${ctx}/jcsz/getPageList?treedwbh=${param.dwbh}",[2,'asc'],columns,0,1,setGroup);
    
    
    
  //保存按钮
   	$("#btn_save").click(function(){
   		alert("保存成功！");
   	});
  	//添加按钮
   	$("#btn_add").click(function(){
   		if(true){
   			i++;
   		}
   		var index = $(".uid:last").attr("id");
   		var mc=$(".mc").val();
		if(index==null){
		index = 0;
		}else{
			index++;
		}
		var trObj = document.createElement("tr");
		trObj.setAttribute("id",index);
		if(index%2==0 || index==1){
			trObj.setAttribute("class","odd");	
		}else{
			trObj.setAttribute("class","even");
			
		}
		trObj.setAttribute("role","row");
		trObj.innerHTML = "<td><input type='checkbox' class='keyId' name='guid' value=''></td>"+
		"<td><input class='mc'  ' type='text' value='"+mc+"' id='dwmc' name='dwmc' style='width:75%;border:none;' /><button type='button' id='btn_sjdw' class='btn btn-link btn-custom'>选择</button></td>"+ 
		"<td><input display='none'  id='date"+i+"'  type='type' name='ZYBM' style='width:100%;text-align: center;border:none;padding:0px 0px;font-size: inherit;line-height: inherit;' class='form-control date'/></td>"+
		"<td><input type='text'  name='zynr' class='number1'  style='width:100%;border:none;text-align:right;' /></td>"+
		"<td><input type='text'  name='zynr' class='number1'  style='width:100%;border:none;text-align:right;' /></td>"+
		"<td><input type='text' name='zynr' class='number1 checkval1'  style='width:100%;border:none;text-align:right;'/></td>"+
		
		"<td><input type='text' name='zynr' class='number1'  style='width:100%;border:none;text-align:right;' /></td>"+
		"<td style='text-align: center;'><a  class='btn btn-link btn_delxx'>删除</a></td>";
		document.getElementById("bod").appendChild(trObj);
		var mydate=new Date();
		var year=mydate.getFullYear();
		var month=mydate.getMonth()+1;		
		var day=mydate.getDate();
		date1=year+"-"+month+"-"+day;
		$("#date"+i+"").val(date1);
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/jsxxs/expExcel?treedwbh=${dwbh}","教师信息","${ctx}",guid.join(","));
   	});
  //单条删除
	$(document).on("click",".btn_delxx",function(){

		$(this).parents("tr").remove();
	});
	
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/kjhs/cyzysz/cyzysz_edit.jsp", "C");
   	});
 	//数值校验
   	$(".checkval").blur(function(){
   		if(true){
   			i++;
   		}
   		$(this).parent().prev().find("input:first").attr("id","snzx"+i+"");
   		$(this).parent().next().find("input:first").attr("id","bnys"+i+"");
   		var snzx=Number($("#snzx"+i+"").val());  		
   		var val1=$(this).val();
   		if(val1>1||val1<-1){
   			$(".checkval").val("")
   			alert("增长比例应该大于-1小于1");	
   		}else if(val1.length>0){
   			$("#bnys"+i+"").val(snzx*1+snzx*val1);
   			$("#bnys"+i+"").focus();
   		}
   	}); 
  //数值校验
   	$(document).on("blur",".checkval1",function(){
   		if(true){
   			i++;
   		}
   		$(this).parent().prev().find("input:first").attr("id","snzx1"+i+"");
   		$(this).parent().next().find("input:first").attr("id","bnys1"+i+"");
   		var snzx=Number($("#snzx1"+i+"").val());  		
   		var val1=$(this).val();
   		if(val1>1||val1<-1){
   			$(".checkval1").val("")
   			alert("增长比例应该大于-1小于1");	
   		}else if(val1.length>0){
   			$("#bnys1"+i+"").val(snzx*1+snzx*val1);
   			$("#bnys1"+i+"").focus();
   		}
   		
	});
	//弹窗信息
    var i=0;
	 $(document).on("click",".btn-custom",function(){		    
		    if(true){
		    	i++;
		    }
			var dwmc=$(this).parent().find("input:first").attr("id","dwmc"+i+"");
			select_commonWin("${ctx}/jcsz/dwpage?controlId=dwmc"+i+"","单位信息","920","630");
		
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