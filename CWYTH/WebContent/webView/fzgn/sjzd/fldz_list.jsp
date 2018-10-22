<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产分类对照</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
        <div class="clearfix">
            <div class="pull-left sub-title text-primary">资产分类对照信息</div>
            <div class="btn-group pull-right" role="group">
                <button class="btn btn-default" id="btn_save" type="button"><i class="fa icon-save"></i>保存</button>
            </div>
            <div class="input-group zhxxcx">
				<input type="text" class="form-control" id="zhcx" placeholder="请输入分类号或资产代码名称 ">
				<span class="input-group-addon"><i class="fa icon-chaxun"></i></span>
			</div>
        </div>
    </div>
    <div class="container-fluid">
        <div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
				        <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>分类号</th>
				        <th>资产代码名称</th>
				        <th>财政分类号</th>
				        <th>财政分类名称</th>
				        <th>财政六分类号</th>
				        <th>财政六分类名称</th>
				    </tr>
				    </thead>
				    <tbody>
				    </tbody>
				</table>
            </div>
        </div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	//综合查询	
	$("#zhcx").blur(function(){
		$('#mydatatables').DataTable().search($(this).val(),"json").draw();
	});
	//更改财政六大类信息
   	$(document).on("click",".btn_czfl",function(){
   		var xhmc = $(this).parents("tr").find("[id^=text_czfl]").attr("id")+"";
   		var czmc = $(this).parents("tr").find("[id^=text_czmc]").attr("id")+"";
   		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/sjzd/fldz_czWindow.jsp?controlId="+xhmc+"&czmc="+czmc,"财政分类","920","630");
	});
	//更改财政十大类信息
   	$(document).on("click",".btn_czten",function(){
   		var xhmc = $(this).parents("tr").find("[id^=text_czten]").attr("id");
   		var zcmc = $(this).parents("tr").find("[id^=text_zcmm]").attr("id");
   		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/sjzd/fldz_czTenWindow.jsp?controlId="+xhmc+"&zcmc="+zcmc,"财政十分类","920","630");
	});
   	//保存按钮
	$("#btn_save").click(function(){
		var checkbox = $("#mydatatables").find("[name='bzdm']").filter(":checked");
		if(checkbox.length>0){
			var flag =true;
			checkbox.each(function(){
				var xh = $(this).attr("xh");
				var text_czten =  $("#text_czten_"+xh).val();
				var text_czfl = $("#text_czfl_"+xh).val();
				if(text_czten==''||text_czfl=="" ){
					flag=false;
				}
			});
			var bzdm = [];
			var ffldm = [];
			var fflmc = [];
			var zcdm = [];
			checkbox.each(function(){
			var $bzdm = $(this).parents("tr").find("[name='bzdm']").val();
			var $ffldm = $(this).parents("tr").find("[name='ffldm']").val();
			var $fflmc = $(this).parents("tr").find("[name='fflmc']").val();
			var $zcdm = $(this).parents("tr").find("[name='zcdm']").val();
			bzdm.push($bzdm);
			ffldm.push($ffldm);
			fflmc.push($fflmc);
			zcdm.push($zcdm);
			}); 
			if(flag){
				$.ajax({
			       	type:"post",
			       	data:"bzdm="+bzdm.join(",")+"&ffldm="+ffldm.join(",")+"&fflmc="+fflmc.join(",")+"&zcdm="+zcdm.join(","), 
			       	url:"${pageContext.request.contextPath}/fldz/doSave",
			       	dataType:"json",
			       	success:function(val){
			       		if(val.success == 'true'){
	  						alert(val.msg);
	  					}else if(val.success == 'false'){
	  						alert(val.msg);
	  					}
			       		close(index);
			       	},
			    	error:function(val){
	  					close(index);
	  					alert(getPubErrorMsg());      					 
	  				},
	  				beforeSend:function(val){
	  					index = loading(2);
	  				} 
	        	}); 
			}else{
				alert("财政分类号，财政六分类号为空的信息无法保存！");
			}
		}else{
			alert("请选择至少一条信息保存！");
		}
	});
	//列表数据
   	var columns = [
		{"data": "BZDM",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="bzdm" xh="'+full._XH+'" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "FLH",defaultContent:""},
	   	{"data": "FLMC",defaultContent:""},
		{"data": "ZCDM","render":function (data, type, full, meta){
		return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_czten_'+full._XH+'" name="zcdm" style="width:120px;border:1" value="' +(undefined==data?'':data)+ '"/><span class="input-group-btn"><button type="button" class="btn btn-link btn_czten">选择</button></span></div>'; 
		}},
		{"data": "ZCMC","render":function (data, type, full, meta){
		 return '<input type="text" id="text_zcmm_'+full._XH+'" name="zcmc" style="border:0"  readonly value="' +(undefined==data?'':data)+ '"/>'; 
		},orderable:false},
        {"data": "FFLDM","render":function (data, type, full, meta){
      	 return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_czfl_'+full._XH+'" name="ffldm" style="width:120px;border:1" value="' +(undefined==data?'':data)+ '"/><span class="input-group-btn"><button type="button" class="btn btn-link btn_czfl">选择</button></span></div>'; 
   		}},
   	    {"data": "FFLMC","render":function (data, type, full, meta){
      	 return '<input type="text" id="text_czmc_'+full._XH+'" name="fflmc" style="border:0"  readonly value="' +(undefined==data?'':data)+ '"/>'; 
   		}},
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/fldz/getPageList?flh=${flh}&mc=${mc}",[2,'asc'],columns,0,1,setGroup);
});
function setFlxx10(xh,e){
	  var flxx = $("#text_czten_"+xh).val();
	  //触发0被按下的事件
	
	   if(flxx.indexOf(')') != -1){
		   var flh=flxx.substring(1,flxx.indexOf(')'))+"";
	       var flmc=flxx.substring(flxx.indexOf(')')+1)+"";
 	       $("#text_czten_"+xh).val(flh);
 	       $("#text_zcmm_"+xh).val(flmc);
	  } 
}

function setFlxx6(xh){
	 var flxx = $("#text_czfl_"+xh).val();
	 if(flxx.indexOf(')') != -1){
			var flh=flxx.substring(1,flxx.indexOf(')'))+"";
	        var flmc=flxx.substring(flxx.indexOf(')')+1)+"";
	        $("#text_czfl_"+xh).val(flh);
	        $("#text_czmc_"+xh).val(flmc);
	}else{
		console.log(flxx);
	}
}
$(function() {	
	 //财政十大类
	$(document).on("focus",":text[id*='text_czten']",function(e){
	    $(this).bindChange("${pageContext.request.contextPath}/suggest/getXx","CFS",setFlxx10([$(this).parents("tr").find("[name='bzdm']").attr("xh")],[e]));
	   });
	$(document).on("focus",":text[id*='text_czfl']",function(e){
	    $(this).bindChange("${pageContext.request.contextPath}/suggest/getXx","CZFLDZ",setFlxx6([$(this).parents("tr").find("[name='bzdm']").attr("xh")],[e]));
	   });
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