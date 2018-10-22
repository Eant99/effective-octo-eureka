<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
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
		<form id="myform2" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		<input type="hidden" name="start" value="1111" />
		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
		<button type="button" class="btn btn-default" id="btn_back"><i class="fa icon-save"></i>返回</button>
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<input type="hidden" name="type" value="${type}"> 
		<center><h4><strong>政府采购项目价格测算表</strong></h4></center>
			<div class="search-simple">	
			<input type="hidden" name="end" value="1111" />
				<div class="form-group"> 
					<label>填报部门</label>
 					<input type="text" class="form-control input-radius" name="tbbm"  id="txt_tbbm" readonly="readonly" value="${bmmc}">
				 </div> 
				 <div class="pull-right">
					<label>单位：万元</label>
				 </div>
			</div>
			
		
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
		        		<tr>
		        			<th style="text-align: center;" colspan="2">采购单位（公章）：</th>
		        			<th colspan="6">
		        				<input name="cgdw" type="text" value="${map.cgdw}">
		        			</th>
		        		</tr>
		        		<tr>
		        			<th style="text-align: center;" colspan="2">项目名称</th>
		        			<th colspan="2">
		        				<input id="txt_xmmc" name="xmmc" type="text" value="${map.xmmc}">
		        				<input type="hidden" id="hdn_xmmc" name="xmid" value="${map.XMID }">
		        				<button type="button" id="btn_xmmc" class="btn btn-link btn-custom">选择</button>
		        			</th>
		        			<th style="text-align: center;">单位预算编码</th>
		        			<th colspan="3">
		        				<input type="text" name="dwysbm"  value="${map.dwysbm}" >
		        			</th>
		        		</tr>
		        		<tr>
		        			<th style="text-align: center;" colspan="2">项目前期调研负责人</th>
		        			<th colspan="2">
		        				<input type="text" name="xmqqdyfzr"  value="${map.xmqqdyfzr}">
		        			</th>
		        			<th style="text-align: center;">联系方式</th>
		        			<th colspan="3">
		        				<input type="text" name="lxfs" value="${map.lxfs}">
		        			</th>
		        		</tr>
		        		
				        <tr>
				        	<th style="text-align: center;">操作</th>
				        	<th style="text-align: center;">序号</th>
				            <th style="text-align: center;">分项名称</th>
				            <th style="text-align: center;">归档区间</th>
				            <th style="text-align: center;">价格测算区间</th>
				            <th style="text-align: center;">咨询单位</th>
				            <th style="text-align: center;">联系人</th>
				            <th style="text-align: center;">电话</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    <c:forEach items="${list}" var="list" varStatus="i">
				    	<tr id="tr_${i.index+1}">
				    	<input type="hidden" name="start" value="1111" />
				    		<c:if test="${list.fxmc!=null}">
				    		<td class="btn_td">
	                               <div class="deleteBtn"></div>
				    		</td>
				    		</c:if> 
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="xh" value="${list.xh}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="fXmc" value="${list.fxmc}">
				    		</td>
				    		<td>
				    			<select id="txt_khyh1" a="a" class="form-control input-radius null" name="gdqj" value="${list.gdqj}">
				    				<option>${list.gdqj}</option>
				    				<option>高档</option>
				    				<option>中档</option>
				    				<option>低档</option>
				    			</select>
<%-- 				    			<select id="txt_xmfl" class="form-control input-radius select2"	name="ssxmfl" value="${dwb.ssxmfl}"> --%>
									
<%-- 									<c:forEach var="item" items="${ssxmfl}"> --%>
<%-- 										<option value="${item.DM}" >${item.MC}</option> --%>
<%-- 									</c:forEach> --%>
<!-- 								</select> -->
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="jgcsqj" value="${list.jgcsqj}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="zxdw" value="${list.zxdw}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="lxr" value="${list.lxr}">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="dh" value="${list.dh}">
				    		</td>
				    		<input type="hidden" name="end" value="1111" />
				    	</tr>
				    	</c:forEach>
				    	<tr id="tr_0">
				    	<input type="hidden" name="start" value="1111" />
				    		<td class="btn_td">
	                               <div class="addBtn"></div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="xh" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="flmc" value="">
				    		</td>
				    		<td>
				    			<select id="txt_khyh1" a="a" class="form-control input-radius null" name="gdqj" value="请选择">
				    				<option>请选择</option>
				    				<option>高档</option>
				    				<option>中档</option>
				    				<option>低档</option>
				    			</select>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="jgcsqj" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="zxdw" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="lxr" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyh1" a="a" class="form-control input-radius null" name="dh" value="">
				    		</td>
				    		<input type="hidden" name="end" value="1111" />
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
	
	//弹框
	$("#btn_dwmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_tbbm","单位信息","920","630");
	});
	$("#btn_xmmc").click(function(){
		select_commonWin("${ctx}/xmxxt/goXmxxTree?controlId=txt_xmmc&id=hdn_xmmc","项目信息","920","630");
	});
  	//新增按钮
	var num = 2;
	$(document).on("click","[class*=addBtn]",function(){
		//var wlbh = $("#txt_wlbh").val()
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find("[a='a']").val("");
		$parentTr.find(":input").removeClass("border");
		//$parentTr.find("[id=txt_wlbh]").val(wlbh);
		$parentTr.attr("id","tr_"+num);
		
		num++;
		$(this).parents("tr").after($parentTr);
	});

	//保存
    $("#btn_save").click(function(){
	   	var url = "${ctx}/ysgl/bmyssb/doSave?operateType=C&dm=${dm}&bzid=${bzid}";
		doSave1($("#myform1"),url,function(val){
			var result = JSON.getJson(val);
			if(result.success){
				alert("保存成功！");
// 				window.location = "${ctx}/ysgl/bmyssb/goListPage?operateType=C&dm=6&bzid=${bzid}";
			}
		});
	});
  //保存方法	
    function doSave1($form, _url, _success, _fail){
    	
    	var json = $("#myform2").serializeObject("start","end");
    	var jsonStr = JSON.stringify(json);
    	
    	var name = $("#txt_tbbm").attr("name");
    	var value = $("#txt_tbbm").val();
    	json[name]=value;
    	console.log("______1___"+json);
    	console.log("______2___"+jsonStr);
    	console.log("______3___"+JSON.stringify(arrayToJson($('#myform2').serializeArray())));
    	
    	$.ajax({
    		type:"post",
    		url:_url,
    		data :"json="+jsonStr+"&jsonStr="+JSON.stringify(arrayToJson($('#myform2').serializeArray())),
    		//data:"jsonStrByZb="+jsonStrByZb+"&jsonStr="+jsonStr+"&guid=${guid}&zbid=${zbid}",
    		//data :"json="+jsonStr+"&"+arrayToJson($('#myform2').serializeArray()),
    		success:function(data){
    			_success(data);
    		},
    		error:function(val){
    			alert("抱歉，系统出现问题！");
    		},
    	});	
    }
  	//返回按钮
	$("#btn_back").click(function(e){
		window.location = "${ctx}/ysgl/bmyssb/goListPage?dm=${dm-3}";
// 		window.location = "${ctx}/ysgl/bmyssb/goListPage?dm=";
// 		parent.location.reload();
	});
	
	
  	
	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();

	});
  	
  	
   	$("#btn_add").click(function(){
   		//doOperate("${ctx}/webView/kjhs/yhzhgl/yhzhgl_edit.jsp","C");
   		
		//alert("增加完成后，请勾选新增记录前的多选框然后单击页面右上方的‘保存’按钮。");
   		
		var index = $(".uid:last").attr("id");
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
		
		trObj.innerHTML = "<td><input type='checkbox' name='guid' value='0'></td>"+
		"<td class='text-center'> <input type='hidden' class='uid' id='++index'></td>"+
		"<td><input type='text' name='yhdm'  style='text-align: center;width:100%;border:none;' value=''/></td>"+ 
		"<td><input type='text' name='yhmc'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='yhdm'  style='text-align: center;width:100%;border:none;' value=''/></td>"+ 
		"<td><input type='text' name='yhmc'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td class='text-center'><a  class='btn btn-link btn_delxx'>删除</a></td>";
		/* trObj.innerHTML = "<td><input type='checkbox' name='guid' value='0'></td>"+
		"<td class='text-center'> <input type='hidden' class='uid' id='"+index+"'>"+index+"</td>
		<td><input type='text' name='yhdm'  style='text-align: center;width:100%;border:none;' value=''/></td>"+ 
		"<td><input type='text' name='yhmc'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td class='text-center'><a  class='btn btn-link btn_delxx'>删除</a></td>"; */
		document.getElementById("bod").appendChild(trObj);
   	});
  	
 
    /* //保存按钮
	$("#btn_save").click(function(e){
		
		
		doSave1(validate,"myform1","${ctx}/yhzhgl/addYhzhb",function(val){

		});
	}); */
    
	//排序
   	function px(){
   		var xh = $(".uid").parents("td").length;
   		$.each($(".uid").parents("td"),function(i,v){
   			var index = xh;
   			$(this).html("<input type='hidden' class='uid' id='"+index+"' value='"+index+"'>"+index+"");
   			xh--;
   		});
   	}
   
	
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/kjhs/yhzhgl/yhzhgl_edit.jsp","C");
   	});
  
    //批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		$(this).parents("tr").remove();
   	     	px();
   	   		});
	   		
   		}else{
   			alert("请选择至少一条信息删除!");
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