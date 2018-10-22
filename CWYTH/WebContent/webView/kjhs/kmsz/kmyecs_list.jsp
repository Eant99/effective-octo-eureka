
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%-- <%@include file="/static/include/public-manager-css.inc"%>  --%>
<%@include file="/static/include/public-list-css.inc"%>
<%-- <link href="${pageContext.request.contextPath}/static/css/public/kmyecs.css" rel="stylesheet" type="text/css" />
 --%>
<title>科目余额初始</title>

<style type="text/css">
 	select{
		font-size:12px !important; 
		max-height: 25px !important;  
		padding: 0px !important;
		padding-left: 5px !important;
	}
	.bluestyle{
	background: #80FFFF !important;
	}
	.yellowstyle{
	background: #FFFFE2 !important;
	}
	td{
		padding:2.5px!important;
	}
	table input{
		border:none!important;
	}
	.table {
	width:60% !important;
	} 
</style>
</head>
<body>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
String date = sdf.format(new Date());
int syn = Integer.parseInt(date)-1;
int dyn = Integer.parseInt(date)+1;
int den = Integer.parseInt(date)+2;
int dsn = Integer.parseInt(date)+3;
%>
 <div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
	  <div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
				
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
				<label>选择年度	</label>
							<div class="input-group" >
									<select name = "nd" id="txt_nd" class="form-control  input-radius window  nd " table="A" type="E" style="border: 1px solid #ccc;border-radius: 4px;">
<!-- 										<option value="">请选择</option> -->
										<c:forEach var ="ssxtlist" items="${ssxtlist}" >
											<option value="${ssxtlist.ZTND}" <c:if test="${nd== ssxtlist.ZTND}">selected</c:if>>${ssxtlist.ZTND}</option>
										</c:forEach>
									</select>
							</div>
				
				
				<div class="form-group">
	             	<select class="form-control" id="txt_zkmsx" name="zkmsx">	 
	             	 <option value="null">全部</option>           	       
	             	<c:forEach var="kmsxList" items="${kmsxList }" >
	             			<option value="${kmsxList.dm }"<c:if test="${kmsxList.dm==zkmsx }">selected</c:if> >${kmsxList.mc }</option>	
	             	</c:forEach>
	             	</select>
				</div>
				<div class="form-group">
	             	<font color="red">(白色可以直接填写，蓝色不可填写，黄色辅助填写，填写完之后请点击保存)</font>
				</div>
				
				<div class="btn-group pull-right" role="group">
	                <button class="btn btn-default" type="button" id="btn_save">保存</button>
	                <button type="button" class="btn btn-default" id="btn_ssph">试算平衡</button>
			<!-- 	  <button type="button" class="btn btn-default" id="btn_drkm">导入科目</button>  --> 
	                <button class="btn btn-default" type="button" id="btn_excel">导出Excel</button>
	         	</div>
			</div>
        </form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<form id="myform1">
					<table id="mydatatables" class="table table-striped table-bordered">
			        	<thead>
					        <tr>
					            <!-- <th><input type="checkbox" class="select-all"/></th>
					            <th>序号</th> -->
					            <th style="text-align: center;">科目编号</th>
					            <th style="text-align: center;">科目名称</th>
					            <th style="text-align: center;">余额方向</th>
					            <th width="150px" style="text-align: center;">年初余额（元）</th>
					         </tr>
						</thead>
					    <tbody>
					    	<c:forEach var="km" items="${zkmList }">
					        	<tr>
					            	<td style="display:none"><input type="checkbox"  name="guid" kmnd="${km.ND }" kmmc="${km.KMMC }" kmbh = "${km.KMBH }" sfmj="${km.SFMJ }" sffz="${km.SFFZ }" grfz="${km.GRFZ }" wldwfz="${km.WLDWFZ }" class="keyId" value="${km.GUID }" guid = "${km.GUID }"></td>
					                <%-- <td>${km.XH }</td> --%>
					                <td style="width:385px">${km.KMBH }</td>
					                <td style="width:385px">${km.KMMC }</td>
					                <td>${km.YEFX }</td>
					                <td>
					                	<c:if test="${km.YEFX=='贷方' }">
					                    	<input id="txt_kmzye${km.GUID }" kmbh1="${km.KMBH }"  <c:if test="${km.SFMJ==1 }">fx="df" </c:if>class="sign-number sumje" style="text-align:right" type="" name="kmzye" width="100%" value="${km.KMZYE }">
					                    </c:if>
					                    <c:if test="${km.YEFX=='借方' }">
					                    	<input id="txt_kmzye${km.GUID }" kmbh1="${km.KMBH }" <c:if test="${km.SFMJ==1 }"> fx="jf" </c:if> class="sign-number sumje" style="text-align:right" type="" name="kmzye" width="100%" value="${km.KMZYE }">
					                    </c:if>
					           		</td>
					           	</tr>
					        </c:forEach>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>



<%@include file="/static/include/public-list-js.inc"%>


<script>

$(function () {
// 	change();
	//addColor();
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
//     var columns = [
//        {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
    	
//          return '<input type="checkbox" name="guid" kmmc="'+full.KMMC+'" kmbh = "'+full.KMBH+'" sfmj="'+full.SFMJ+'" sffz="'+full.SFFZ+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
//        },"width":10,'searchable': false},
//        {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
//           	return data;},"width":41,"searchable":false,"class":"text-center"},  
//        {"data": "KMBH",defaultContent:""}, 
//        {"data": "KMMC",defaultContent:""},
//        {"data": "YEFX",defaultContent:""},     
//        {"data": "KMZYE",'render':function(data, type, full, meta){
//      	   if(data==null){
// 			   data="";
//  		   }
//     	   if(full.YEFX=="贷方"){   		   
//         	   return '<input id="txt_kmzye'+full.GUID+'" fx="df" class="number" style="text-align:right" type="text" name="kmzye" width="100%" value="'+data+'">';
//     	   }else{   		  
//     			   return '<input id="txt_kmzye'+full.GUID+'" fx="jf" class="number" style="text-align:right" type="text" name="kmzye" width="100%" value="'+data+'"  >';    		   
//   	   }
//       },orderable:false,"width":100,"class":"text-center"}
//      ];
//     var rowFormat= function ( row, data, index ) {
//      	 var sffz=data.SFFZ;   	 
//      	 var sfmj=data.SFMJ;
//    	 if(sfmj==1&&sffz!=0){
//    		 $(row).addClass('yellowstyle');
//    		 $(row).find("[name=kmzye]").addClass('choose');
//    	   }else if(sfmj==1&&sffz==0){
   		  
//    	   }else if(sfmj==0){
//    		   $(row).addClass('bluestyle');
//    		  // $(row:input).attr("disabled",true);
//    		 $(row).find(":text").attr("disabled","disabled");
//    	   } 
     	  
//         };
//        table = getDataTableByListHj("mydatatables","${ctx}/kmsz/getkmye?nd=${param.nd}&zkmsx=${param.zkmsx}",[2,'asc'],columns,0,1,setGroup,'dog',rowFormat);
      
 	//添加按钮
 	$(document).on("click","#btn_add",function(){
   		doOperate("${ctx}/pzmb/addpzmb");
   	});
 		//导出Excel
//    	$("#btn_excel").click(function(){
//   		  var json = searchJson("searchBox");
//   		  var nd= $("#txt_nd").val();
//   		  var zkmsx = $("#txt_zkmsx").val();
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 		var guid = [];
// 		checkbox.each(function(){
// 			guid.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/kmsz/expkmyeExcel?nd="+nd+"&zkmsx="+zkmsx,"科目余额初始","${ctx}",guid.join(","));  
//    	});
 	
  //导出Excel
  //导出Excel
    $(document).on("click","#btn_excel",function(){
   		var checkbox = $("#jsyh").find("[name='rybh']").filter(":checked");
   	 var nd= $("#txt_nd").val();
	     var zkmsx = $("#txt_zkmsx").val();
					$.ajax({
						type : "post",
						data : {nd:nd,zkmsx:zkmsx},
						url : "${ctx}/kmsz/expExcels",
						success : function(val) {
						   FileDownload("${ctx}/file/fileDownload","科目余额初始.xls",val.url);
						}
					});
			});
  	
 		
   	//修改按钮
	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();

   		doOperate("${ctx}/pzmb/goeditpzmb?guid="+guid,"U");
   	});
   	//试算平衡
   	$(document).on("click","#btn_ssph",function(){
   		var dfze = 0.00;
   		$.each($("[fx=df]"),function(){
   			var val=$(this).val();
   			if(val==""||val==0||isNaN(val)){
   				val = 0.00;
   			}
   			//dfze = parseFloat(dfze)+parseFloat(val);
   			dfze = Number(dfze)+Number(val);
    		});
   		var jfze = 0.00;
   		$.each($("[fx=jf]"),function(){
   			var val = $(this).val();
   			if(val==""||val==0||isNaN(val)){
   				val = 0.00;
   			}
   			//jfze = parseFloat(jfze)+parseFloat(val);
   			jfze = Number(jfze)+Number(val);
   		});
//    		alert("试算不平衡，贷方总额是："+dfze+"（元）,借方总额是："+jfze+"（元）,差额是："+ce+"（元）");
//    		return;
   		if(jfze==dfze){
   			alert("试算平衡");
   		}else {
   			var ce= Math.abs(jfze-dfze);
   			ce = ce.toFixed(2);
   			jfze = jfze.toFixed(2);
   			dfze = dfze.toFixed(2);
//    			alert("借方科目的余额为"+jfze+".00元，贷方科目的余额为"+dfze+".00元，差额"+ce+".00元");
   	   	    alert("试算不平衡，贷方总额是："+dfze+"（元）,借方总额是："+jfze+"（元）,差额是："+ce+"（元）");
   		}
    	});
  
	//批量删除
		$(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var guid = []
   			 if(checkbox.length>0){
   	   			var guid = [];
   	   			checkbox.each(function(){
   	   				guid.push($(this).val());
   	   			});
   	   			 doDel("guid="+guid.join("','"),"${ctx}/pzmb/pzmbsDel",function(val){
   	   			window.location.href = "${ctx}/webView/kjhs/pzmb/pzmb_list.jsp";
   	   	   		},function(val){
   	   	   		},guid.length); 
   	   		}else{
   	   			alert("请选择至少一条信息删除！");
   	   		} 
   	});
 	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();		
	 	confirm("确定删除？","",function(){
			$.ajax({
	   			url:"${ctx}/pzmb/pzmbDel",
	   			data:"guid="+guid,
	   			type:"post",
	   			async:"false",
	   			success:function(val){
	   				alert("删除成功");
   	   			window.location.href = "${ctx}/webView/kjhs/pzmb/pzmb_list.jsp";
	   			}
	   		});
		});
	
	});
	$(document).on("click",".choose",function(){
		var kmmc = $(this).parents("tr").find("[name=guid]").attr("kmmc"); 
		var kmyeguid = $(this).parents("tr").find("[name=guid]").attr("kmbh");
		var nd = $(this).parents("tr").find("[name=guid]").attr("kmnd"); 	
		var id = $(this).parents("tr").find("[id^=txt_kmzye]").attr("id");
		var grfz =  $(this).parents("tr").find("[name=guid]").attr("grfz");
		var wldwfz = $(this).parents("tr").find("[name=guid]").attr("wldwfz");
		select_commonWin("${ctx}/kmsz/kmyemxList?kmyeguid="+kmyeguid+"&id="+id+"&kmmc="+kmmc+"&nd="+nd+"&grfz="+grfz+"&wldwfz="+wldwfz,"科目余额明细信息","1200","630");
	});
	//保存按钮
	$(document).on("click","#btn_save",function(){
			$(":checkbox").attr("checked",true);
			doSave1("myform1","${ctx}/kmsz/updatekmye",function(val){
						
			});
	});
	
	$("#txt_nd,#txt_zkmsx").change(function(){
   		var nd = $("#txt_nd").val();
   		var zkmsx = $("#txt_zkmsx").val();
		window.location.href = "${ctx}/kmsz/kmyecs?nd="+nd+"&zkmsx="+zkmsx;
	});
	
	// 下拉框改变  
	// ajax，仿财务一体化/网上报销/报销业务查询 
// 	$("#txt_nd,#txt_zkmsx").change(function(){
// 		tableRelod(table);
// 	});

// 	var tableRelod = function(table) {
// 		var nd = $("[id='txt_nd']").val();
// 		var zkmsx = $("[id='txt_zkmsx']").val();

// 		table.ajax.url("${ctx}/kmsz/kmyecs?nd="+nd+"&zkmsx="+zkmsx);
// 	    table.ajax.reload();
// 	};
	$(document).on("click","#btn_drkm",function(){
		$.ajax({
			type:"post",
			url:"${ctx}/kmsz/drkm",
			dataType:"json",
			success:function(val){
				alert("导入成功");
   	   			window.location.href = "${ctx}/kmsz/kmyecs";
			},
			error:function(val){
					
			}	
		});
	});
	function doSave1(_formId, _url, _success, _fail){	
		var json = $('#myform1').serializeObject1("guid","kmzye");  //json对象				
		var jsonStr = JSON.stringify(json); 
		var index;
			$.ajax({
				type:"post",
				url:_url,
				dataType:"json",
				data:"json="+jsonStr,
				success:function(val){				  
						alert("保存成功");
						window.location.href="${ctx}/kmsz/kmyecs"
				
				},
				error:function(val){
							alert("保存成功");
							window.location.href="${ctx}/kmsz/kmyecs"
			}	
			});
		
	}

	$.fn.serializeObject1 = function(start,end){
		var f = {"list":[]};
	    var a = this.serializeArray();
	    var o = {};
	    $.each(a, function() {
	    	if (this.name == start) {
	        	o = {};
	        	o[this.name] = this.value;
	        } else if(this.name == end){
	        	o[this.name] = this.value;
	        	f["list"].push(o);
	        }else{
	        	o[this.name] = this.value;
	        }
	    });
	    return f;
	};
	//下级改变计算上级
	$(document).on("keyup",".sumje",function(){
		var kmbh = $(this).attr("kmbh1");
		sumje(kmbh.substring(0,kmbh.length-2),kmbh);
		
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



$(document).ready(function(){
	//change();
	addColor();
	
});
function addnczye(id,nczye){
	nczye = nczye.toFixed(2);
	$("#"+id).val(""+nczye);
	//$("#"+id).blur();
}
function change(){
	   $("#txt_nd,#txt_zkmsx").change(function(){
	    	  var nd = $("#txt_nd").val();
	    	  var zkmsx = $("#txt_zkmsx").val();
		   	  window.location.href = "${ctx}/kmsz/kmyecs?nd="+nd+"&zkmsx="+zkmsx;
	      });
	   
}
function addColor(){
	var checkbox = $(":checkbox");
	$.each(checkbox,function(){
		var sfmj = $(this).attr("sfmj");
		var sffz = $(this).attr("sffz");
		var grfz = $(this).attr("grfz");
		var wldwfz = $(this).attr("wldwfz");
		if(sfmj=='1'&&sffz=='1'){
			//黄色末级且有辅助
// 		if(grfz=='1'||wldwfz=='1'){
			$(this).parents("tr").addClass('yellowstyle');			
		}else if(sfmj=='1'&&sffz=='0'){
			//蓝色末级无辅助
		}else if(sfmj=='0'){
			//蓝色非末级
			$(this).parents("tr").addClass('bluestyle');			
		}
	});
	$(".yellowstyle").find("[name=kmzye]").addClass("choose");
	$(".yellowstyle").find("[name=kmzye]").attr("readonly",true);
	$(".bluestyle").find("[name=kmzye]").attr("readonly",true);
}
function sumje(num,kmbh){
	console.log("num===="+num);
	var c = $("[kmbh1^='"+num+"']").attr("kmbh1").length;
	console.log("c==="+c);
    var km = $("[kmbh1^='"+num+"'] ").filter(":not([kmbh1='"+num+"'])");
    var a = $("[kmbh1^='"+num+"']").val();
    console.log("a==="+a);
    var b = parseInt(kmbh.length)+parseInt(2);
    console.log("kmbh.length+2=="+b);
    var ze=0.00 ;
	$.each(km,function(){
		if((parseInt(num.length)+parseInt(2))==$(this).attr("kmbh1").length){
			je = $(this).val();
			console.log("je==="+je);
			if(je==""||je==0||isNaN(je)){
				je = 0.00;
			}
			ze= parseFloat(je)+parseFloat(ze);
			console.log("ze===="+ze);
		}
		
	});
	$("[kmbh1='"+num+"']").val(ze.toFixed(2));
	if(num.length>4){
		sumje(num.substring(0,num.length-2),num);
	}
	
	
}


	

</script>
</body>
</html>