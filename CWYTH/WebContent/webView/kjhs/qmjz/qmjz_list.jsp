<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>期末结账</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.null{
		background-color: wheat;
	}
	.dataTables_scrollHeadInner{
		width:600px ! important;
	}
	table.dataTable{
		width:600px ! important;
	}
</style>
</head>
<body>
<%
SimpleDateFormat ztnd = new SimpleDateFormat("yyyy");
String date1 = ztnd.format(new Date());
SimpleDateFormat jzrq = new SimpleDateFormat("yyyy-MM-dd");
String date = jzrq.format(new Date());
%>
<div class="fullscreen">
<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		
		  <div class="search-simple">
<!-- 		  <div class="form-group"> -->
<!-- 					<label>账套年度</label> -->
<%-- 					<input type="text" id="txt_ztnd" class="form-control input-radius window  nd" name="ztnd" table="A" value="<%=date1 %>" data-format="yyyy"/>							 --%>
<!-- 			</div> -->
			
		<label>账套年度</label>
							<div class="input-group" >
									<select name = "ztnd" class="form-control  input-radius window  nd " table="A" type="E" style="border: 1px solid #ccc;border-radius: 4px;">
<!-- 										<option value="">请选择</option> -->
										<c:forEach var ="ssxtlist" items="${ssxtlist}" >
											<option value="${ssxtlist.ZTND}">${ssxtlist.ZTND}</option>
										</c:forEach>
									</select>
							</div>	
			
				<div class="form-group">
					<label>会计期间</label>
					<input type="text" id="txt_kjqj" class="form-control" name="kjqj"  table="A" placeholder="请输入会计期间">
				</div>
				<div class="form-group">
					<label>结账人员</label>
					<input type="text" id="txt_jzry" class="form-control" name="jzry1"  table="A" placeholder="请输入结账人员">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_jz">结账</button>
	               <button type="button" class="btn btn-default" id="btn_fjz">反结账</button>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<form id="myform1" class="add">
			<input type="hidden" name="start">
				<table id="mydatatables" class="table table-striped table-bordered">			
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th> 
				            <th>账套年度</th>
				            <th>会计期间</th>
				            <th>是否结账</th>
				            <th>结账日期</th>
				            <th>结账人员</th>
				            
				        </tr>			      
					</thead>
				    <tbody id="bod">
				    </tbody>
				</table>
				<input type="hidden" name="end">
			</form>
			</div>
		</div>
	</div>	
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
var dqnd = $("#txt_ztnd").val();
$(function(){
	//列表数据
    var columns = [   	
    	{"data": "GUID", orderable: false, 'render': function(data, type, full, meta){
    		return '<input type="checkbox" name="guid" jzcs="'+full.JZCS+'" kjqj="'+full.KJQJ+'" sfjz="'+full.SFJZ+'" ztnd="'+full.ZTND+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
    		},"width":10,'searchable': false},
    	
    	{"data":"_XH",orderable:false,'render': function(data,type,full,meta){
    		return  '<input type="hidden"  class="uid"  id="' + data + '">'+data+'';
    		},"width":35,"searchable":false,"class":"text-center",},
        {"data": "ZTND",defaultContent:"","name":"ztnd","class":"ztnd text-left","width":40},
    	{"data": "KJQJ",defaultContent:"","name":"kjqj","class":"kjqj","width":40},
        {"data": "SFJZ",defaultContent:"","name":"sfjz","class":"sfjz text-left","width":30},
       	{"data": "JZRQ",defaultContent:"","name":"jzrq","class":"jzrq","width":40,"class":"text-center",},
    	{"data": "JZRY",defaultContent:"","name":"jzry","class":"jzry","width":40,"class":"text-left",},
       ];
    table = getDataTableByListHj("mydatatables","${ctx}/qmjz/getQmjzPageList?dqnd="+dqnd,[3,'asc'],columns,0,1,setGroup);
    
  	//结账按钮
  	$(document).on("click","#btn_jz",function(){
  		debugger
  		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var kjqj = checkbox.parents("tr").find("[name=guid]").attr("kjqj");
   		var sfjz = checkbox.parents("tr").find("[name=guid]").attr("sfjz");
   	    var ztnd =  $("#mydatatables").find("[name='guid']").filter(":checked").attr("ztnd");
   		if(checkbox.length>1){
   			alert("只能选择一条信息");
   		}else if(checkbox.length==0){
   			alert("请先选择一条信息");
   		}else{
   		//验证该会计期间是否结账
   			if(sfjz=="是"){
   				alert("该会计期间已结账");
   			}else{
   			//验证当前会计期间是否记账
   	   			$.ajax({
   	   				url:"${ctx}/qmjz/doSelect?kjqj="+kjqj+"&ztnd="+ztnd,
   	   	   			data:"",
   	   	   			type:"post",
   	   	   			async:"false",
   	   	   			success:function(val){
   	   	   				if(val=="00" || val=="01"){
   	   	   					alert("该会计期间未记账信息，请先记账");   					
   	   	   				}else if(val=="99"){
   	   	   					alert("该会计期间已结账");
   	   	   				}else if(val=="22"){
   	   	   					alert("该会计期间凭证编号存在断号");
   	   	   				}else if(val=="02"){
   	   	   					//判断是否结过账
   	   	   			         $.ajax({
   	   				         url:"${ctx}/qmjz/doSelectJz?kjqj="+kjqj+"&ztnd="+ztnd,
   	   	   			         data:"",
   	   	   			         type:"post",
   	   	   			         async:"false",
   	   	   			         success:function(val){
   	   	   			        	if(val=="1"){
   	   	   			             confirm("确定结账？","",function(){
   	   	   			        	     debugger
   	   	   			            	 var jzyf =  $(".kjqj:last").text();
   	   	   			    	         var jzcs =  $("#mydatatables").find("[name='guid']").filter(":checked").attr("jzcs");
   	   	   			    	        
   	   	   			    	         console.log("ztnd==="+ztnd);
   	   	   			    	         console.log("jzyf==="+jzyf);
   	   	   			                 console.log("jzcs==="+jzcs);
   	   	   			            	 var guidLast = $("#mydatatables").find("[name='guid']").filter(":checked").val();
	 	   	  		                 $.ajax({
		 	   	  				         url:"${ctx}/qmjz/editQmjz?guidLast="+guidLast+"&jzyf="+jzyf+"&jzcs="+jzcs+"&ztnd="+ztnd,
			 	   	  	   			         data:"",
			 	   	  	   			         type:"post",
			 	   	  	   			         async:"false",
			 	   	  	   			         success:function(val){
			 	   	  	   				     alert("结账成功");
			 	   	  	   				     
			 	   	  	   			          window.location.href="${ctx}/qmjz/goQmjzListPage";
		 	   	  	   			       	 }
	 	   	  	   		             });
   	   	   			             });
   	   	   				      }else if(val=="0"){
   	   	   				       confirm("确定结账？","",function(){
   	   	   				    	  doSaveBy();
   	   	   				       });
   	   	   				      }
   	   	   			       }
   	   	   		        }); 	
   	   	   					//
   	   	   				}else{
   	   	   					alert("未找到该会计期间凭证状态信息");
   	   	   				
   	   	   				}
   	   	   			}
   	   		});
   			}
   		}
  	}); 
   
    //验证当前会计期间是否记账
    //function yzpzzt(){
   		//var kjqj = $(".add tr:last").find("[name=kjqj]").val();
   		//var ztnd = $(".add tr:last").find("[name=ztnd]").val();
   		
   		/* $.ajax({
			url:"${ctx}/qmjz/doSelect?kjqj="+kjqj,
   			data:"",
   			type:"post",
   			async:"false",
   			success:function(val){
   				if(val=="00" || val=="01"){
   					alert("该会计期间未记账");   					
   				}else if(val=="99"){
   					alert("该会计期间已结账");
   				}else if(val=="02"){
   					//判断该
   				}else{
   					alert("未找到相关凭证状态信息");
   				}
   			}
   		}); 	
    } */
    //判断该会计期间是否结过账
    /* function yzjz(){
    	var kjqj = checkbox.parents("tr").find("[name=guid]").attr("kjqj");
   		$.ajax({
			url:"${ctx}/qmjz/doSelectJz?kjqj="+kjqj,
   			data:"",
   			type:"post",
   			async:"false",
   			success:function(val){
   				if(val=="1"){
   					ajax();  					
   				}else if(val=="0"){
   					doSaveBy();
   				
   			}
   		}); 	
    } */
  	//判断是否可结账
  	function pd(){
   		var dqsfjz = $(".add tr:last").find("[name=sfjz]").val();
   		if(dqsfjz=="是"){
   			alert("本年度无可结账会计期间");
   			return;
   		}else{
   			doSaveBy();
   		}
  	}
		
    //本月结账保存
	function doSaveBy(){
		var guidLast = $(".add tr:last").find("[name=guid]").val();
		var jzyf =  $(".kjqj:last").text();
	    var jzcs =  $("#mydatatables").find("[name='guid']").filter(":checked").attr("jzcs");
	    var ztnd =  $("#mydatatables").find("[name='guid']").filter(":checked").attr("ztnd");
	    console.log("ztnd==="+ztnd);
	    console.log("jzyf==="+jzyf);
	    console.log("jzcs==="+jzcs);
		$.ajax({
				//url:"${ctx}/qmjz/editQmjz?guidLast="+guidLast,
				url:"${ctx}/qmjz/editQmjz?guidLast="+guidLast+"&jzyf="+jzyf+"&jzcs="+jzcs+"&ztnd="+ztnd,
	   			data:"",
	   			type:"post",
	   			async:"false",
	   			success:function(val){
	   				addNext();
	   			}
	   		}); 		
	 }
    
    //添加下月结账信息
   	function addNext(){   		
		var index = $(".uid:last").attr("id");
		var index1 = $(".kjqj:last").text();
		var index2 = $(".ztnd:last").text();
		console.log("index2+++++++++++"+index2);
		console.log("index1++++++++="+index1);
		if(index==null){
		index = 1;
		}else{
			index++;
		}
		if(index1==null){
			index1 = 1;
		}else if(index1==12){
			index2++;		
			index1=1;
			
		}else{
			index1++;
		}
		var trObj = document.createElement("tr");
		trObj.setAttribute("id",index);
		trObj.setAttribute("value",index1);
		 if(index%2==0 || index==1){
			trObj.setAttribute("class","odd");	
		}else{
			trObj.setAttribute("class","even");
		} 
		
		trObj.setAttribute("role","row");
		
		trObj.innerHTML = "<td><input type='checkbox' name='guid' value='0'></td>"+
		"<td class='text-center'> <input type='hidden' class='uid' id='"+index+"'>"+index+"</td>"+
		"<td><input type='text' name='ztnd'  style='width:100%;border:none;text-align:center;' class='ztnd' value='"+index2+"' data-format='yyyy' readonly/></td>"+ 
		"<td><input type='text' name='kjqj'  style='width:100%;border:none;' class='kjqj' value='"+index1+"' readonly/></td>"+
		"<td><input type='text' name='sfjz'  style='width:100%;border:none;text-align:center;' value='否'/></td>"+
		"<td><input type='text' name='jzrq'  style='width:100%;border:none;text-align:center;' class=' date' data-format='yyyy' value='' readonly/></td>"+
		"<td><input type='text' name='jzry'  style='width:100%;border:none;' value=''/></td>";
		document.getElementById("bod").appendChild(trObj);
		
		doSaveNext();
   	}
    
    //下月结账信息保存
	function doSaveNext(){
		var ztnd = $(".add tr:last").find("[name=ztnd]").val();
   		var kjqj = $(".add tr:last").find("[name=kjqj]").val();
		$.ajax({
				url:"${ctx}/qmjz/addQmjz?ztnd="+ztnd+"&kjqj="+kjqj,
	   			data:"",
	   			type:"post",
	   			async:"false",
	   			success:function(val){
	   				alert("结账成功");
	   			window.location.href="${ctx}/qmjz/goQmjzListPage";
	   			}
	   		}); 		
	 }

	//账套年度改变
	$(function() {
		
		$(document).on("focus", ".nd", function(){
		    $(this).on("click", function() {
		    	WdatePicker({
		    		dateFmt:'yyyy',
		    		onpicked:function(){
		    			var val = $(this).val();
		    			var json = searchJson("searchBox");
		    	    	$('#mydatatables').DataTable().search(json, "json").draw();
		    		},
		    		onclearing:function(){
		    			alert("请选择年度!");
		    			return;
		    		}
		    	});
		    });
		    $(this).on("keypress", function() {
		        if (/[^0-9]/.test(String.fromCharCode(event.keyCode)))
		            event.keyCode = 0;
		    });
		    $(this).on("dragenter", function() {
		        return false;
		    });
		    $(this).on("paste", function() {
		        return false;
		    });
		});
		//反结账按钮
		$(document).on("click","#btn_fjz",function(){
	   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
	   		if(checkbox.length>1){
	   			alert("只能选择一条信息");
	   		}else if(checkbox.length==0){
	   			alert("请先选择一条信息");
	   		}else{
	   			var guid = $("#mydatatables").find("[name='guid']").filter(":checked").val();
	   		    var sfjz = checkbox.parents("tr").next().find("[name=guid]").attr("sfjz");
	   		    if(sfjz=="是"){
	   		    	alert("请先反结账下月信息");
	   		    }else{
	   		    	confirm("确定反结账？","",function(){
	   		    	$.ajax({
	   					url:"${ctx}/qmjz/editFjz",
	   		   			data:"guid="+guid,
	   		   			type:"post",
	   		   			async:"false",
	   		   			success:function(val){
	   		   				alert("反结账成功");
	   		   			    window.location.href="${ctx}/qmjz/goQmjzListPage";
	   		   			},
	   		   			error:function(val){
	   		   				
	   		   			}
	   		   		}); 
	   		    	});
	   		    }
	   		    
	   		}
	   	});
	//排序
   	function px(){
   		var xh = $(".uid").parents("td").length;
   		$.each($(".uid").parents("td"),function(i,v){
   			var index = xh;
   			$(this).html("<input type='hidden' class='uid' id='"+index+"' value='"+index+"'>"+index+"");
   			xh--;
   		});
   	}
	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
// 		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
//    		var shzt = checkbox.attr("shzt");
//    		if(shzt=="已提交"||shzt=="通过"){
//    			alert("已提交或者审核通过的无法修改");
//    			return;
//    		}
// 		confirm("确定删除？","{title:提示信息}",function(){
// 			alert("删除成功");
// 		});
//alert("sss");
		$(this).parents("tr").remove();
		px();
	});
});
function remove(){
	
	
}
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