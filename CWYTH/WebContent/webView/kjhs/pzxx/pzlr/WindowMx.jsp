<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.addBtn{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	.addBtn:after{
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    position: relative;
	    cursor: pointer;
	}
	.deleteBtn{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	.deleteBtn:after{
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    position: relative;
	    cursor: pointer;
	}
	.td_input{
		width:100%!important;
		border:none;
	}
	.number{
		text-align:right;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group" style="visibility:hidden;">
					
	                <label>姓&emsp;&emsp;名</label>
	             	<input name="xm" class="input_info date form-control" style="width:130px;" placeholder="请输入姓名" value="${kssj }" data-format="yyyy-MM-dd" readonly/>
					<label>银行账号</label>
					<input name="yhzh" class="input_info date form-control" placeholder="请选择结束时间" style="width:130px;" value="${jssj }" data-format="yyyy-MM-dd" readonly/>
					<button type="button" class="btn btn-primary" id="btn_search">
					<i class="fa icon-chaxun"></i>
					查 询
				 	</button>
				</div>
				<div class="btn-group pull-right form-group" >
					<button type="button" class="btn btn-default" id="btn_save">保存</button>
					<button type="button" class="btn btn-default" id="btn_add">新增</button>
					<button type="button" class="btn btn-default" id="btn_imp">导入Excel</button>
        		</div>
			</div>
		</form>
	</div>
	<form id="tableForm">
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				           	<th>操作</th>
				            <th style="text-align:center;">姓名</th>
				            <th style="text-align:center;">银行名称</th>
				            <th style="text-align:center;">账号</th>
				            <th style="text-align:center;">金额</th>
				            <th style="text-align:center;">人民银行联行号</th>
				        </tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="list" varStatus="i">
							<tr class="tr_${i.index+1}">
					        	<input type="hidden" name="start" value="start" />
					           	<td>
					           		<div class="deleteBtn"></div>
					           	</td>
					            <td>
					            	<input type="text" name="xm" value="${list.xm}" class="td_input"/>
					            </td>
					            <td>
					            	<input type="text" name="yhmc" value="${list.yhmc}" class="td_input"/>
					            </td>
					            <td>
					            	<input type="text" name="yhzh" value="${list.yhzh}" class="td_input"/>
					            </td>
					            <td>
					            	<input type="text" name="je" value="${list.je}" class="td_input number"/>
					            </td>
					             <td>
					            	<input type="text" name="yhlhh" value="${list.yhlhh}" class=" int"/>
					            </td>
					           <input type="hidden" name="end" value="end" />
				           </tr>
						</c:forEach>
				        <tr class="tr_0">
				        	<input type="hidden" name="start" value="start" />
				           	<td>
				           		<div class="addBtn"></div>
				           	</td>
				            <td>
				            	<input type="text" name="xm" value="" class="td_input"/>
				            </td>
				            <td>
				            	<input type="text" name="yhmc" value="" class="td_input"/>
				            </td>
				            <td>
				            	<input type="text" name="yhzh" value="" class="td_input"/>
				            </td>
				            <td>
				            	<input type="text" name="je" value="" class="td_input number"/>
				            </td>
				             <td>
				            	<input type="text" name="yhlhh" value="" class=" int"/>
				            </td>
				           <input type="hidden" name="end" value="end" />
				        </tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
var size = "${list.size()+1}"
$(function () {
	$(document).on("click","#btn_add",function(){
		var name = prompt("请输入您要新增的行数", "1");
		if(name.indexOf(".")>0||isNaN(name)){
			alert("请输入合法的正整数！");
			return;
		}
		if(name){
			for(var i=0;i<name;i++){
				console.log(name);
				addTr();
			}
		}
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
//新增删除行操作
$(document).on("click",".addBtn",function(){
	addTr();
});
$(document).on("click",".deleteBtn",function(){
	$(this).parents("tr").remove();
});
function addTr(){
	var cloneTr = $(".tr_0").clone();
	$(".tr_0").find("input").val("");
	cloneTr.removeClass("tr_0").addClass("tr_"+size);
	cloneTr.find(".addBtn").removeClass("addBtn").addClass("deleteBtn");
	var tr = $(".tr_0");
	tr.before(cloneTr);
	//tr.after(cloneTr);
	size++;
}
//数据导入
$(document).on("click","#btn_imp",function(){
	select_commonWin("${pageContext.request.contextPath}/webView/kjhs/pzxx/pzlr/txl_imp.jsp?zbid=${param.zbid}&mxid=${param.mxid}", "导入明细信息", 450,350);
	return false;
});
//保存
$(document).on("click","#btn_save",function(){
	var flag = isNUllToAlert();
	var json = $("#tableForm").serializeObject("start","end");  //json对象				
	var jsonStr = JSON.stringify(json);  //json字符串
	var zbid = "${zbid}";
	var mxid = "${mxid}";
	
	var flagL = true;
	var zds = $("[name='yhzh']");//开户银行账号
	var lhh = $("[name='yhlhh']");//银行联行号
	var zhid = [];  //所有账号
	var lhhid = [];  //所有联行号
	zds.each(function(){
		zhid.push($(this).val());
	});
	lhh.each(function(){
		lhhid.push($(this).val());
	});
	
	if(!flag){  //flag=false 有没填的
		var tag = check();
	    console.log(tag);
		if( tag =="true1" ){ //tag=false 什么都没写
		   alert("请至少填写一条数据!");
		}else if(!tag){
		   alert("存在必填项未填写!");
		}else{
			//判断是否是建行  填写银行联行号
			for (var i=0;i<zhid.length-1;i++) {
				var aa = zhid[i+1]; //每个银行账号
				var bankCode = bankCardAttribution(aa); //每个银行的标志符  建行 CCB
				var code = bankCode[0]
				console.log(code)
				if(aa!="" && code==undefined){
					alert("请输入第"+(i+1)+"行正确的银行账户");
					flagL = false;
				}else{
					if (aa!="" && code != "CCB") {
						var lhhVal = lhhid[i];
						if(lhhVal==""){
							alert("请输入第"+(i+1)+"行的人民银行联行号");
							flagL = false;
						}else if(lhhVal.length != 12){
							alert("请输入第"+(i+1)+"行正确的人民银行联行号");
							flagL = false;
						}//else
					}//if
				}//else
			}//for
			
			if(flagL){
				var je = "${je}";
				var mxje = 0;
				$("[name='je']").each(function(){
					if($(this).val() != ""){
						mxje += parseFloat($(this).val());
					}
				});
				if(je != "0.00"){
					if(Math.abs(je) != mxje.toFixed(2)){
						alert("银行明细金额综合与分录输入的借贷方金额不一致！");
						return;
					}
				}
				$.ajax({
					url:"${ctx}/kjhs/pzxx/pzlr/saveYhmx",
					data:"jsonStr="+jsonStr+"&zbid="+zbid+"&mxid="+mxid,
					type:"post",
					dataType:"json",
					success:function(data){
						if(data.success){
							alert("操作成功！");
						}else{
							window.alert("操作失败！");
						}
					},
					error:function(){
						alert("系统异常，请联系管理员！");
					}
				});
			}//if
			
		}
	}else{//else
		
		//判断是否是建行  填写银行联行号
		for (var i=0;i<zhid.length-1;i++) {
			var aa = zhid[i+1]; //每个银行账号
			var bankCode = bankCardAttribution(aa); //每个银行的标志符  建行 CCB
			var code = bankCode[0]
			console.log(code)
			if(aa!="" && code==undefined){
				alert("请输入第"+(i+1)+"行正确的银行账户");
				flagL = false;
			}else{
				if (aa!="" && code != "CCB") {
					var lhhVal = lhhid[i];
					if(lhhVal==""){
						alert("请输入第"+(i+1)+"行的人民银行联行号");
						flagL = false;
					}else if(lhhVal.length != 12){
						alert("请输入第"+(i+1)+"行正确的人民银行联行号");
						flagL = false;
					}//else
				}//if
			}//else
		}//for
		
		if(flagL){
			var je = "${je}";
			var mxje = 0;
			$("[name='je']").each(function(){
				if($(this).val() != ""){
					mxje += parseFloat($(this).val());
				}
			});
			if(je != "0.00"){
				if(Math.abs(je) != mxje.toFixed(2)){
					alert("银行明细金额总和与分录输入的借贷方金额不一致！");
					return;
				}
			}
			$.ajax({
				url:"${ctx}/kjhs/pzxx/pzlr/saveYhmx",
				data:"jsonStr="+jsonStr+"&zbid="+zbid+"&mxid="+mxid,
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.success){
						alert("操作成功！");
					}else{
						window.alert("操作失败！");
					}
				},
				error:function(){
					alert("系统异常，请联系管理员！");
				}
			});
		}//if
	}//else
});
//验证必填项
function isNUllToAlert(){
	var info = $(".td_input");
	var flag = true;
	$.each(info,function(i,v){
		var val = $(this).val();
		if(val==""&&flag){
			flag = false;
		}
	});
	return flag;
}
function check(){
	var ipptr = $(".td_input").parents("tr");
	var ipp = $(".td_input").parents("tr").find(".td_input:visible");
	var num = 0;
	var tag = true;
	$.each(ipp,function(i,v){
		var val = $(this).val();
		if(val==""){
			num++;
		}
	});
	console.log(num+"**"+ipp.length)
	console.log(num%4)
	if(num == ipp.length){
		tag = "true1";
	}else if(num%4 != 0 ){
		tag = false;
	}
	return tag;
}
function reload(){
	window.location.reload();
};
</script>
</body>
</html>